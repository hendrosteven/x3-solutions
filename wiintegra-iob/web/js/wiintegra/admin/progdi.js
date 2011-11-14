// Global vars
var ProgdisDataStore;
var ProgdisColumnModel;
var ProgdiListingEditorGrid;
var ProgdiListingWindow;
// Our new vars
var ProgdiCreateForm;
var ProgdiCreateWindow;
var KodeField;
var NamaField;
var FakultasField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  function saveTheProgdi(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'progdiJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         kode: oGrid_event.record.data.kode,
         nama: oGrid_event.record.data.nama,
         fakultas: oGrid_event.record.data.fakultas
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            ProgdisDataStore.commitChanges();
            ProgdisDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'Kode Progdi sudah ada!');
            break;
         default:
            Ext.MessageBox.alert('Error', 'Pengubahan data gagal!');
            break;
         }
      },
      failure: function(response){
         var result=response.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
  }

  function createTheProgdi(){
     if(isProgdiFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'progdiJSON',
        params: {
          task: "CREATE",
          kode:      KodeField.getValue(),
          nama:       NamaField.getValue(),
          fakultas : FakultasField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Progdi baru berhasil ditambahkan!');
            ProgdisDataStore.reload();
            ProgdiCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','Kode tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Progdi baru gagal!');
            break;
          }
        },
        failure: function(response){
          var result=response.responseText;
          Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
        }
      });
    } else {
      Ext.MessageBox.alert('Peringatan', 'Data tidak valid!');
    }
  }

  function resetProgdiForm(){
    KodeField.setValue('');
    NamaField.setValue('');
    FakultasField.setValue('');
  }

  function isProgdiFormValid(){
      return(KodeField.isValid() && NamaField.isValid() && FakultasField.isValid());
  }

  function displayFormWindow(){
     if(!ProgdiCreateWindow.isVisible()){
       resetProgdiForm();
       ProgdiCreateWindow.show();
     } else {
       ProgdiCreateWindow.toFront();
     }
  }

  function confirmDeleteProgdis(){
    if(ProgdiListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data progdi. Lanjutkan?', deleteProgdis);
    } else if(ProgdiListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+ProgdiListingEditorGrid.selModel.getCount()+' data progdi. Lanjutkan?', deleteProgdis);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Progdi terlebih dahulu!');
    }
  }

  function deleteProgdis(btn){
    if(btn=='yes'){
         var selections = ProgdiListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< ProgdiListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'progdiJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1: 
                ProgdisDataStore.reload();
                break;
              case -1:
                Ext.MessageBox.alert('Peringatan','Sebagian data Progdi yang Anda pilih tidak dapat dihapus! Pastikan data Progdi yang Anda ingin hapus tidak dipakai di data Mahasiswa!');
                ProgdisDataStore.reload();
                break;
              case 0:
                Ext.MessageBox.alert('Peringatan','Seluruh data Progdi yang Anda pilih tidak dapat dihapus! Pastikan data Progdi yang Anda ingin hapus tidak dipakai di data Mahasiswa!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Progdi!');
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

  var fakultasStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'progdiJSON',
                method: 'POST'
            }),
            baseParams:{task: "FAKULTAS"},
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarFakultas'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  fakultasStore.load();

  ProgdisDataStore = new Ext.data.Store({
      id: 'ProgdisDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'progdiJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"},
      reader: new Ext.data.JsonReader({
        root: 'daftarProgdi',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'fakultas', type: 'int', mapping: 'fakultas'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  ProgdisColumnModel = new Ext.grid.ColumnModel(
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
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 10,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 300,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 200,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Fakultas',
        dataIndex: 'fakultas',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: fakultasStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return fakultasStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      }]
    );
    ProgdisColumnModel.defaultSortable= true;

   ProgdiListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:600,
      height:480,
      title:'Data Program Studi',
      frame:true,
      id: 'ProgdiListingEditorGrid',
      store: ProgdisDataStore,
      iconCls : 'grid',
      cm: ProgdisColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Progdi',
            iconCls:'add',                      
            handler: displayFormWindow
          }, '-', {
            text: 'Hapus',
            tooltip: 'Hapus Data Progdi',
            handler: confirmDeleteProgdis,   
            iconCls:'remove'
          }, '-', {
            text: 'Print',
            tooltip: 'Print Data Program Studi',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,
            iconCls:'help'
      }]
    });

  ProgdiListingWindow = new Ext.Window({
      id: 'ProgdiListingWindow',
      title: 'Data Progdi',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: ProgdiListingEditorGrid
    });

  ProgdiListingEditorGrid.render("grid");
  ProgdisDataStore.load({params:{start:0, limit:25}});
  ProgdiListingEditorGrid.on('afteredit', saveTheProgdi);

  KodeField = new Ext.form.TextField({
    id: 'KodeField',
    fieldLabel: 'Kode',
    maxLength: 10,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 200,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  FakultasField = new Ext.form.ComboBox({
     id:'FakultasField',
     fieldLabel: 'Fakultas',
     store: fakultasStore,
     //mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            progdiStore.reload({params:{idFakultas: r.data.id}});
        }
     }
      });

  ProgdiCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [KodeField, NamaField, FakultasField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheProgdi
        },{
          text: 'Batal',
          handler: function(){
            ProgdiCreateWindow.hide();
          }
        }]
    });

  ProgdiCreateWindow= new Ext.Window({
      id: 'ProgdiCreateWindow',
      title: 'Tambahkan data Progdi baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 250,
      plain:true,
      layout: 'fit',
      items: ProgdiCreateForm
    });

  var teks = 'Untuk menambahkan data, klik button Tambah.<br>' +
             'Untuk menghapus data, klik row yg akan dihapus dan klik button Hapus.<br>' +
             'Untuk mengubah data, klik pada cell yang ingin diubah. Setelah melakukan perubahan data, tekan enter atau klik di luar cell.';
  HelpWindow = new Ext.Window({
      id: 'HelpWindow',
      title: 'Help',
      closable:false,
      width: 300,
      height: 150,
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
        window.open('printProgdi', '_blank', '');
    }
});