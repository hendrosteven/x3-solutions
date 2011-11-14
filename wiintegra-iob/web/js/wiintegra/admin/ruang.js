// Global vars
var RuangsDataStore;
var RuangsColumnModel;
var RuangListingEditorGrid;
var RuangListingWindow;
// Our new vars
var RuangCreateForm;
var RuangCreateWindow;
var KodeField;
var NamaField;
var KapasitasField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  function saveTheRuang(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'ruangJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         kode: oGrid_event.record.data.kode,
         nama: oGrid_event.record.data.nama,
         kapasitas: oGrid_event.record.data.kapasitas
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            RuangsDataStore.commitChanges();
            RuangsDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'Kode Ruang sudah ada!');
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

  function createTheRuang(){
     if(isRuangFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'ruangJSON',
        params: {
          task: "CREATE",
          kode:      KodeField.getValue(),
          nama:       NamaField.getValue(),
          kapasitas:       KapasitasField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Ruang baru berhasil ditambahkan!');
            RuangsDataStore.reload();
            RuangCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','Kode tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Ruang baru gagal!');
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

  function resetRuangForm(){
    KodeField.setValue('');
    NamaField.setValue('');
    KapasitasField.setValue('');
  }

  function isRuangFormValid(){
      return(KodeField.isValid() && NamaField.isValid() && KapasitasField.isValid());
  }

  function displayFormWindow(){
     if(!RuangCreateWindow.isVisible()){
       resetRuangForm();
       RuangCreateWindow.show();
     } else {
       RuangCreateWindow.toFront();
     }
  }

  function confirmDeleteRuangs(){
    if(RuangListingEditorGrid.selModel.getCount() == 1) 
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data ruang. Lanjutkan?', deleteRuangs);
    } else if(RuangListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+RuangListingEditorGrid.selModel.getCount()+' data ruang. Lanjutkan?', deleteRuangs);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Ruang terlebih dahulu!');
    }
  }

  function deleteRuangs(btn){
    if(btn=='yes'){
         var selections = RuangListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< RuangListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'ruangJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:
                RuangsDataStore.reload();
                break;
              case -1: 
                Ext.MessageBox.alert('Peringatan','Sebagian data Ruang yang Anda pilih tidak dapat dihapus! Pastikan data Ruang yang Anda ingin hapus tidak dipakai di data Jadwal!');
                RuangsDataStore.reload();
                break;
              case 0:
                Ext.MessageBox.alert('Peringatan','Seluruh data Ruang yang Anda pilih tidak dapat dihapus! Pastikan data Ruang yang Anda ingin hapus tidak dipakai di data Jadwal!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Ruang! ' + result);
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

  RuangsDataStore = new Ext.data.Store({
      id: 'RuangsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'ruangJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"},
      reader: new Ext.data.JsonReader({
        root: 'daftarRuang',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'kapasitas', type: 'int', mapping: 'kapasitas'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  RuangsColumnModel = new Ext.grid.ColumnModel(
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
          maxLength: 150,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Kapasitas',
        dataIndex: 'kapasitas',
        width: 100,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 3,
          maskRe: /([0-9\s]+)$/
          })
      }]
    );
    RuangsColumnModel.defaultSortable= true;

   RuangListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:700,
      height:480,
      title:'Data Ruang',
      frame:true,
      id: 'RuangListingEditorGrid',
      store: RuangsDataStore,
      iconCls : 'grid',
      cm: RuangsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Ruang',
            iconCls:'add',                      
            handler: displayFormWindow
          }, '-', { 
            text: 'Hapus',
            tooltip: 'Hapus Data Ruang',
            handler: confirmDeleteRuangs,   
            iconCls:'remove'
          }, '-', { 
            text: 'Print',
            tooltip: 'Print Data Ruang',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,  
            iconCls:'help'
      }]
    });

  RuangListingWindow = new Ext.Window({
      id: 'RuangListingWindow',
      title: 'Data Ruang',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: RuangListingEditorGrid
    });

  RuangListingEditorGrid.render("grid");
  RuangsDataStore.load({params:{start:0, limit:25}});
  RuangListingEditorGrid.on('afteredit', saveTheRuang);

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
    maxLength: 150,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  KapasitasField = new Ext.form.TextField({
    id: 'KapasitasField',
    fieldLabel: 'Kapasitas',
    maxLength: 3,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([0-9\s]+)$/
      });

  RuangCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [KodeField, NamaField, KapasitasField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheRuang
        },{
          text: 'Batal',
          handler: function(){
            RuangCreateWindow.hide();
          }
        }]
    });

  RuangCreateWindow= new Ext.Window({
      id: 'RuangCreateWindow',
      title: 'Tambahkan data Ruang baru',
      closable:false,
      iconCls : 'form',
      width: 300,
      height: 250,
      plain:true,
      layout: 'fit',
      items: RuangCreateForm
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
        window.open('printRuang', '_blank', '');
    }

});