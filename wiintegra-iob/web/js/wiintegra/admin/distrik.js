// Global vars
var DistriksDataStore;
var DistriksColumnModel;
var DistrikListingEditorGrid;
var DistrikListingWindow;
// Our new vars
var DistrikCreateForm;
var DistrikCreateWindow;
var NamaField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // This saves the Distrik after a cell has been edited
  function saveTheDistrik(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'distrikJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         nama: oGrid_event.record.data.nama
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            DistriksDataStore.commitChanges();
            DistriksDataStore.reload();
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

  // this creates a new Distrik
  function createTheDistrik(){
     if(isDistrikFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'distrikJSON',
        params: {
          task: "CREATE",
          nama:       NamaField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Distrik baru berhasil ditambahkan!');
            DistriksDataStore.reload();
            DistrikCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Distrik baru gagal!');
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

  // reset the Form before opening it
  function resetDistrikForm(){
    NamaField.setValue('');
  }

  // check if the form is valid
  function isDistrikFormValid(){
      return(NamaField.isValid());
  }

  // display or bring forth the form
  function displayFormWindow(){
     if(!DistrikCreateWindow.isVisible()){
       resetDistrikForm();
       DistrikCreateWindow.show();
     } else {
       DistrikCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteDistriks(){
    if(DistrikListingEditorGrid.selModel.getCount() == 1) // only one Distrik is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data distrik. Lanjutkan?', deleteDistriks);
    } else if(DistrikListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+DistrikListingEditorGrid.selModel.getCount()+' data distrik. Lanjutkan?', deleteDistriks);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Distrik terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteDistriks(btn){
    if(btn=='yes'){
         var selections = DistrikListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< DistrikListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'distrikJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                DistriksDataStore.reload();
                break;
              case -1:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Sebagian data Distrik yang Anda pilih tidak dapat dihapus! Pastikan data Distrik yang Anda ingin hapus tidak dipakai di data Mahasiswa dan Dosen!');
                DistriksDataStore.reload();
                break;
              case 0:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Seluruh data Distrik yang Anda pilih tidak dapat dihapus! Pastikan data Distrik yang Anda ingin hapus tidak dipakai di data Mahasiswa dan Dosen!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Distrik!');
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


  // << CONFIG >>
  DistriksDataStore = new Ext.data.Store({
      id: 'DistriksDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'distrikJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarDistrik',
        totalProperty: 'total',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  DistriksColumnModel = new Ext.grid.ColumnModel(
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
        width: 400,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 20,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      }]
    );
    DistriksColumnModel.defaultSortable= true;

   DistrikListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:700,
      height:480,
      title:'Data Distrik',
      frame:true,
      id: 'DistrikListingEditorGrid',
      store: DistriksDataStore,
      cm: DistriksColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Distrik',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Distrik',
            handler: confirmDeleteDistriks,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Print',
            tooltip: 'Print Data Distrik',
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
        store: DistriksDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Distrik {0} - {1} of {2}',
        emptyMsg: "Data Distrik kosong!"
        })
    });

  DistrikListingWindow = new Ext.Window({
      id: 'DistrikListingWindow',
      title: 'Data Distrik',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: DistrikListingEditorGrid
    });

  //DistrikListingWindow.show();
  DistrikListingEditorGrid.render("grid");
  DistriksDataStore.load({params:{start:0, limit:25}});
  DistrikListingEditorGrid.on('afteredit', saveTheDistrik);

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  DistrikCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [NamaField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheDistrik
        },{
          text: 'Batal',
          handler: function(){
            // because of the global vars, we can only instantiate one window... so let's just hide it.
            DistrikCreateWindow.hide();
          }
        }]
    });

  DistrikCreateWindow= new Ext.Window({
      id: 'DistrikCreateWindow',
      title: 'Tambahkan data Distrik baru',
      closable:false,
      iconCls : 'form',
      width: 300,
      height: 150,
      plain:true,
      layout: 'fit',
      items: DistrikCreateForm
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
        window.open('printDistrik', '_blank', '');
    }
});