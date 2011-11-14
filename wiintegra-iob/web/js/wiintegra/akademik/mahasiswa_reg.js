// Global vars
var PembayaransDataStore;
var PembayaransColumnModel;
var PembayaranListingEditorGrid;
var PembayaranListingWindow;
// Our new vars
var PembayaranCreateForm;
var PembayaranCreateWindow;
var NamaField;
var KeteranganField;
var BiayaField;
var HelpWindow;

Ext.onReady(function(){

  Ext.QuickTips.init();

  function confirmRegistrasi(){
    /*if(PembayaranListingEditorGrid.selModel.getCount() == 1) // only one Pembayaran is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data pembayaran. Lanjutkan?', registrasi);
    } else*/ if(MahasiswaListingEditorGrid.selModel.getCount() >= 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan meregistrasikan '+MahasiswaListingEditorGrid.selModel.getCount()+' mahasiswa untuk semester ini. Lanjutkan?', registrasi);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Mahasiswa terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function registrasi(btn){
    if(btn=='yes'){
         var selections = MahasiswaListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< MahasiswaListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'mahasiswaRegJSON',
            params: {
               task: "REGISTRASI",
               ids:  encoded_array
               //idSemester : SemesterField.getValue()
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                MahasiswasDataStore.reload();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Pembayaran!');
                break;
              }
            },
            failure: function(response){
              var result=response.responseText;
              Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
              }
         });
      }
  }

  MahasiswasDataStore = new Ext.data.Store({
      id: 'MahasiswasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'mahasiswaRegJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTINGREG_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMahasiswa',
        totalProperty: 'total'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'noInduk', type: 'string', mapping: 'noInduk'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'fakultas', type: 'string', mapping: 'fakultas'},
        {name: 'progdi', type: 'string', mapping: 'progdi'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  //------------------------------- fakultas -----------------------------------
  var fakultasStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'mahasiswaRegJSON',
                method: 'POST'
            }),
            baseParams:{task: "FAKULTAS"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarFakultas'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var progdiStore = new Ext.data.Store({
      id: 'progdiStore',
      autoLoad: true,
      proxy: new Ext.data.HttpProxy({
                url: 'mahasiswaRegJSON',
                method: 'POST'
            }),
            baseParams:{task: "PROGDI"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarProgdi'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'},
            {name: 'fakultas', type: 'int', mapping: 'fakultas'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    fakultasStore.load();
    progdiStore.load({params: {fakultas: ''}});

  FakultasSearchField = new Ext.form.ComboBox({
     id:'FakultasSearchField',
     emptyText: 'Pilih Fakultas',
     store: fakultasStore,
     //mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            MahasiswasDataStore.load({params:{start:0, limit:25, fakultas:r.data.id}});
            //progdiStore.filter('fakultas',FakultasSearchField.getValue(),true,false);
            progdiStore.reload({params:{fakultas:r.data.id}});
        }
     }
      });

  ProgdiSearchField = new Ext.form.ComboBox({
     id:'ProgdiSearchField',
     emptyText: 'Pilih Program Studi',
     store: progdiStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            MahasiswasDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField, progdi:r.data.id}});
        }
     }
  });

  //----------------------------------------------------------------------------

  var NimField = new Ext.form.TextField({
        //renderTo: 'filter-data-company',
        emptyText: 'Cari berdasarkan NIM',
        width:150,
        enableKeyEvents:true,
          listeners: {
            'render': function(c) {
              c.getEl().on('keyup', function() {
                MahasiswaListingEditorGrid.store.filter('noInduk',NimField.getValue(),true,false);
              }, c);
            }
          }
    });

  MahasiswasColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        dataIndex: 'id',
        width: 30,
        hidden: true
      },{
        header: 'Nomor Induk',
        dataIndex: 'noInduk',
        width: 100
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 200
      },{
        header: 'Fakultas',
        dataIndex: 'fakultas',
        width: 250
      },{
        header: 'Program Studi',
        dataIndex: 'progdi',
        width: 250
      }]
    );
    MahasiswasColumnModel.defaultSortable= true;

   MahasiswaListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...', removeMask:true},
      width:1150,
      height:480,
      title:'Data Mahasiswa',
      frame:true,
      iconCls : 'grid',
      id: 'MahasiswaListingEditorGrid',
      autoScroll: true,
      store: MahasiswasDataStore,
      cm: MahasiswasColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          FakultasSearchField
          , '-', // Added in Tutorial 6
          ProgdiSearchField
          , '-',
          NimField
          , '-', { // Added in Tutorial 6
            text: 'Registrasi',
            tooltip: 'Registrasi Mahasiswa ke Semester sekarang',
            iconCls:'registrasi',                      // reference to our css
            handler: confirmRegistrasi
          }, '-', { // Added in Tutorial 6
            text: 'Print',
            tooltip: 'Print Data Mahasiswa yang belum Terregistrasi di Semester sekarang',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }],
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: MahasiswasDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Mahasiswa {0} - {1} of {2}',
        emptyMsg: "Data Mahasiswa kosong!"
        })
    });
  //Ext.MessageBox.alert('', 'hai');

    MahasiswasDataStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {fakultas: FakultasSearchField.getValue(), progdi:ProgdiSearchField.getValue()});
            }
        },
        'load':{
            fn: function(store, options){
                if(NimField.getValue() != '')
                    MahasiswaListingEditorGrid.store.filter('noInduk',NimField.getValue(),true,false);
            }
        }
    });

//------------ ambil data current semester dulu baru dirender ke panel ---------
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'mahasiswaRegJSON',
          params: {
             task: "CURRENT"
          },
          success: function(response){
             //Ext.MessageBox.alert('', 'sini');
             var result=response.responseText;
             semester = result;
             MahasiswaListingEditorGrid.setTitle('Data Mahasiswa yang belum Terregistrasi pada '+semester);
             MahasiswaListingEditorGrid.render("grid");
             MahasiswasDataStore.load({params:{start:0, limit:25}});
             //MahasiswaListingEditorGrid.on('afteredit', saveTheJadwal);
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
//------------------------------------------------------------------------------

  //MahasiswasDataStore.load({params:{start:0, limit:25}});
  //PembayaranListingWindow.show();
  //MahasiswaListingEditorGrid.render("grid");

  var teks = 'Untuk meregistrasikan mahasiswa pada semester terbaru, klik Mahasiswa-mahasiswa yang akan diregistrasikan dan klik tombol Registrasi.<br>' +
             'Untuk melakukan pencarian berdasarkan Fakultas, gunakan drop down Fakultas di sebelah atas tabel.<br>' +
             'Untuk melakukan pencarian berdasarkan Nomor Induk, ketikkan pada subDistrikk yang tersedia di sebelah atas tabel.';
  HelpWindow = new Ext.Window({
      id: 'HelpWindow',
      title: 'Help',
      closable:false,
      width: 400,
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
        window.open('printMahasiswaReg?idFakultas=' + FakultasSearchField.getValue(), '_blank', '');
    }
});