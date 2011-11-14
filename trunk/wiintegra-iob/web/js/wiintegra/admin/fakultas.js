// Global vars
var FakultassDataStore;
var FakultassColumnModel;
var FakultasListingEditorGrid;
var FakultasListingWindow;
// Our new vars
var FakultasCreateForm;
var FakultasCreateWindow;
var KodeField;
var NamaField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  function saveTheFakultas(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'fakultasJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         kode: oGrid_event.record.data.kode,
         nama: oGrid_event.record.data.nama
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            FakultassDataStore.commitChanges();
            FakultassDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'Kode Fakultas sudah ada!');
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

  function createTheFakultas(){
     if(isFakultasFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'fakultasJSON',
        params: {
          task: "CREATE",
          kode:      KodeField.getValue(),
          nama:       NamaField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Fakultas baru berhasil ditambahkan!');
            FakultassDataStore.reload();
            FakultasCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','Kode tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Fakultas baru gagal!');
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

  function resetFakultasForm(){
    KodeField.setValue('');
    NamaField.setValue('');
  }

  function isFakultasFormValid(){
      return(KodeField.isValid() && NamaField.isValid());
  }

  function displayFormWindow(){
     if(!FakultasCreateWindow.isVisible()){
       resetFakultasForm();
       FakultasCreateWindow.show();
     } else {
       FakultasCreateWindow.toFront();
     }
  }

  function confirmDeleteFakultass(){
    if(FakultasListingEditorGrid.selModel.getCount() == 1) 
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data fakultas. Lanjutkan?', deleteFakultass);
    } else if(FakultasListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+FakultasListingEditorGrid.selModel.getCount()+' data fakultas. Lanjutkan?', deleteFakultass);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Fakultas terlebih dahulu!');
    }
  }

  function deleteFakultass(btn){
    if(btn=='yes'){
         var selections = FakultasListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< FakultasListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'fakultasJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1: 
                FakultassDataStore.reload();
                break;
              case -1: 
                Ext.MessageBox.alert('Peringatan','Sebagian data Fakultas yang Anda pilih tidak dapat dihapus! Pastikan data Fakultas yang Anda ingin hapus tidak dipakai di data Mahasiswa, Matakuliah, dan Program Studi!');
                FakultassDataStore.reload();
                break;
              case 0:
                Ext.MessageBox.alert('Peringatan','Seluruh data Fakultas yang Anda pilih tidak dapat dihapus! Pastikan data Fakultas yang Anda ingin hapus tidak dipakai di data Mahasiswa, Matakuliah, dan Program Studi!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Fakultas!');
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

  FakultassDataStore = new Ext.data.Store({
      id: 'FakultassDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'fakultasJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"},
      reader: new Ext.data.JsonReader({
        root: 'daftarFakultas',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  FakultassColumnModel = new Ext.grid.ColumnModel(
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
        width: 400,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 200,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      }]
    );
    FakultassColumnModel.defaultSortable= true;

   FakultasListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:700,
      height:480,
      title:'Data Fakultas',
      frame:true,
      id: 'FakultasListingEditorGrid',
      store: FakultassDataStore,
      iconCls : 'grid',
      cm: FakultassColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Fakultas',
            iconCls:'add',                      
            handler: displayFormWindow
          }, '-', { 
            text: 'Hapus',
            tooltip: 'Hapus Data Fakultas',
            handler: confirmDeleteFakultass,  
            iconCls:'remove'
          }, '-', { 
            text: 'Print',
            tooltip: 'Print Data Fakultas',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,  
            iconCls:'help'
      }]
    });

  FakultasListingWindow = new Ext.Window({
      id: 'FakultasListingWindow',
      title: 'Data Fakultas',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: FakultasListingEditorGrid
    });

  FakultasListingEditorGrid.render("grid");
  FakultassDataStore.load({params:{start:0, limit:25}});
  FakultasListingEditorGrid.on('afteredit', saveTheFakultas);

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
      
  FakultasCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [KodeField, NamaField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheFakultas
        },{
          text: 'Batal',
          handler: function(){
            FakultasCreateWindow.hide();
          }
        }]
    });

  FakultasCreateWindow= new Ext.Window({
      id: 'FakultasCreateWindow',
      title: 'Tambahkan data Fakultas baru',
      closable:false,
      iconCls : 'form',
      width: 300,
      height: 250,
      plain:true,
      layout: 'fit',
      items: FakultasCreateForm
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
        window.open('printFakultas', '_blank', '');
    }
});