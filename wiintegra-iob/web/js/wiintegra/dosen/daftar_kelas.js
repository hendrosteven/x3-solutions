// Global vars
var DaftarKelassDataStore;
var DaftarKelassColumnModel;
var DaftarKelasListingEditorGrid;
var DaftarKelasListingWindow;
var SemesterDataStore;
// Our new vars
var DaftarKelasCreateForm;
var DaftarKelasCreateWindow;
var semester = "";
var panel;
var id;

Ext.onReady(function(){

  Ext.QuickTips.init();

       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'daftarKelasJSON',
          params: {
             task: "CURRENT"
          },
          success: function(response){
             var result=response.responseText;
             semester = result;
             isi();
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
       
  function isi(){
    DaftarKelassDataStore = new Ext.data.Store({
      id: 'DaftarKelassDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'daftarKelasJSON',
                method: 'POST'
            }),
            baseParams:{task: "JADWAL"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarJadwal',
        //totalProperty: 'total',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'waktu', type: 'string', mapping: 'waktu'},
        {name: 'ruang', type: 'string', mapping: 'ruang'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  DaftarKelassColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        readOnly: true,
        dataIndex: 'id',
        width: 30,
        renderer: function(value, cell){
         cell.css = "readonlycell";
         return value;
        },
        hidden: true
      },{
        header: 'Kode',
        dataIndex: 'kode',
        width: 100
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 400
      },{
        header: 'Waktu',
        dataIndex: 'waktu',
        width: 200
      },{
        header: 'Ruang',
        dataIndex: 'ruang',
        width: 100
      }]
    );
    DaftarKelassColumnModel.defaultSortable= true;

   DaftarKelasListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:850,
      height:480,
      title:'Data Daftar Kelas yang Anda ampu pada ' + semester,
      frame:true,
      id: 'DaftarKelasListingEditorGrid',
      store: DaftarKelassDataStore,
      iconCls : 'grid',
      cm: DaftarKelassColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
      tbar: [
          {
            text: 'Input Nilai',
            tooltip: 'Input Nilai Mahasiswa pada Jadwal yang Anda pilih',
            iconCls:'add',                      // reference to our css
            handler: displayMahasiswa
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }]
    });

  panel = new Ext.Panel({
      border: false,
      hideBorders: true,
      width: 850,
      items : DaftarKelasListingEditorGrid
  });
  panel.render("grid");

  DaftarKelassDataStore.load({params:{start:0, limit:25}});
  }

  function displayMahasiswa(){
    if(DaftarKelasListingEditorGrid.selModel.getCount() <= 0) // only one Biaya is selected here
    {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data kelas matakuliah terlebih dahulu!');
    }
    else{
        id = DaftarKelasListingEditorGrid.selModel.getSelections()[0].data.id;
      DaftarMahasiswaStore = new Ext.data.Store({
          id: 'DaftarMahasiswaStore',
          proxy: new Ext.data.HttpProxy({
                    url: 'daftarKelasJSON',
                    method: 'POST'
                }),
                baseParams:{task: "MAHASISWA_LIMIT", id:id}, // this parameter is passed for any HTTP request
          reader: new Ext.data.JsonReader({
            root: 'daftarMahasiswa',
            totalProperty: 'total'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'},
            {name: 'status', type: 'string', mapping: 'status'},
            {name: 'nilai', type: 'string', mapping: 'nilai'}
          ]),
          sortInfo:{field: 'id', direction: "ASC"}
        });

       var nilaiStore = new Ext.data.SimpleStore({
            fields:['nilaiValue', 'nilaiName'],
            data: [['A','A'],['B','B'],['C','C'],['D','D'],['E','E']]
        });

      DaftarMahasiswaColumnModel = new Ext.grid.ColumnModel(
        [
          new Ext.grid.RowNumberer(),
          {
            header: 'ID',
            readOnly: true,
            dataIndex: 'id',
            width: 30,
            renderer: function(value, cell){
             cell.css = "readonlycell";
             return value;
            },
            hidden: true
          },{
            header: 'Nama',
            dataIndex: 'nama',
            width: 300
          },{
            header: 'Status',
            dataIndex: 'status',
            width: 50
          },{
            header: 'Nilai',
            dataIndex: 'nilai',
            width: 50,
            //allowBlank: false,
            editor: new Ext.form.ComboBox({
                   typeAhead: true,
                   triggerAction: 'all',
                   store: nilaiStore,
                   mode: 'local',
                   displayField: 'nilaiName',
                   valueField: 'nilaiValue',
                   lazyRender:true,
                   allowBlank: false,
                   listClass: 'x-combo-list-small'
                })/*,
            renderer: function (val){
                return nilaiStore.queryBy(function(rec){
                return rec.data.nilaiValue == val;
                }).itemAt(0).data.nilaiName;
                }*/
          }]
        );
        DaftarMahasiswaColumnModel.defaultSortable= true;

       var judul = DaftarKelasListingEditorGrid.selModel.getSelections()[0].data.kode + " " + DaftarKelasListingEditorGrid.selModel.getSelections()[0].data.nama + " " + semester;
       DaftarMahasiswaListingEditorGrid =  new Ext.grid.EditorGridPanel({
          loadMask: {msg: 'Loading...'},
          width:850,
          height:480,
          title:'Daftar Mahasiswa yang mengambil ' + judul,
          frame:true,
          id: 'DaftarMahasiswaListingEditorGrid',
          store: DaftarMahasiswaStore,
          iconCls : 'grid',
          cm: DaftarMahasiswaColumnModel,
          enableColLock:false,
          clicksToEdit:1,
          selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
          tbar: [
              {
                text: 'Print',
                tooltip: 'Print Data Nilai',
                handler: print,
                iconCls:'print'
          }],
          bbar: new Ext.PagingToolbar({
            pageSize: 25,
            store: DaftarMahasiswaStore,
            displayInfo: true,
            displayMsg: 'Menampilkan data Mahasiswa {0} - {1} of {2}',
            emptyMsg: "Data Mahasiswa kosong!"
            })
        });

    DaftarMahasiswaStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {id: id});
            }
        }
    });

      DaftarMahasiswaStore.load({params:{start:0, limit:25, id:id}});
      panel.remove('DaftarKelasListingEditorGrid');
      panel.add(DaftarMahasiswaListingEditorGrid);
      panel.doLayout();
      DaftarMahasiswaListingEditorGrid.on('afteredit', saveNilai);
    }
  }
  
  // This saves the User after a cell has been edited
  function saveNilai(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'daftarKelasJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         nilai: oGrid_event.record.data.nilai
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            DaftarMahasiswaStore.commitChanges();
            DaftarMahasiswaStore.reload();
            break;
         default:
            Ext.MessageBox.alert('Pengubahan data gagal!');
            break;
         }
      },
      failure: function(response){
         var result=response.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
  }

  var teks = 'Untuk menginput nilai, klik pada salah satu matakuliah dan klik tombol "Input Nilai".<br>' +
             'Kemudian edit nilai dengan mengklik cell nilai dan menggantinya dengan nilai yang baru.';
  HelpWindow = new Ext.Window({
      id: 'HelpWindow',
      title: 'Help',
      closable:false,
      width: 500,
      height: 200,
      html: teks,
      iconCls : 'help',
      buttonAlign : 'center',
      buttons: [{
          text: 'Tutup',
          handler: hideHelpWindow
      }]
    });

    function showHelpWindow() {
        if(!HelpWindow.isVisible()){
            HelpWindow.show();
        } else {
            HelpWindow.toFront();
        }
    }

    function hideHelpWindow() {
        HelpWindow.hide();
    }

    function print(){
        window.open('printNilai?idJadwal='+id, '_blank', '');
    }

});