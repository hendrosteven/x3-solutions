// Global vars
var AngkatansDataStore;
var AngkatansColumnModel;
var AngkatanListingEditorGrid;
var AngkatanListingWindow;
// Our new vars
var AngkatanCreateForm;
var AngkatanCreateWindow;
var NamaField;
var KeteranganField;
var BiayaField;
var HelpWindow;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // This saves the Angkatan after a cell has been edited
  function saveTheAngkatan(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'angkatanJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         nama: oGrid_event.record.data.nama,
         keterangan: oGrid_event.record.data.keterangan
         //biaya: oGrid_event.record.data.biaya
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            AngkatansDataStore.commitChanges();
            AngkatansDataStore.reload();
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

  // this creates a new Angkatan
  function createTheAngkatan(){
     if(isAngkatanFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'angkatanJSON',
        params: {
          task: "CREATE",
          nama:       NamaField.getValue(),
          keterangan: KeteranganField.getValue()
          //biaya:      BiayaField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Angkatan baru berhasil ditambahkan!');
            AngkatansDataStore.reload();
            AngkatanCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Angkatan baru gagal!');
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
  function resetAngkatanForm(){
    NamaField.setValue('');
    KeteranganField.setValue('');
    BiayaField.setValue('');
  }

  // check if the form is valid
  function isAngkatanFormValid(){
      return(NamaField.isValid() && KeteranganField.isValid());// && BiayaField.isValid());
  }

  // display or bring forth the form
  function displayFormWindow(){
     if(!AngkatanCreateWindow.isVisible()){
       resetAngkatanForm();
       AngkatanCreateWindow.show();
     } else {
       AngkatanCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteAngkatans(){
    if(AngkatanListingEditorGrid.selModel.getCount() == 1) // only one Angkatan is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data angkatan. Lanjutkan?', deleteAngkatans);
    } else if(AngkatanListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+AngkatanListingEditorGrid.selModel.getCount()+' data angkatan. Lanjutkan?', deleteAngkatans);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Angkatan terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteAngkatans(btn){
    if(btn=='yes'){
         var selections = AngkatanListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< AngkatanListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'angkatanJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                AngkatansDataStore.reload();
                break;
              case -1:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Sebagian data Angkatan yang Anda pilih tidak dapat dihapus! Pastikan data Angkatan yang Anda ingin hapus tidak dipakai di data Mahasiswa!');
                AngkatansDataStore.reload();
                break;
              case 0:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Seluruh data Angkatan yang Anda pilih tidak dapat dihapus! Pastikan data Angkatan yang Anda ingin hapus tidak dipakai di data Mahasiswa!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Angkatan!');
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
  AngkatansDataStore = new Ext.data.Store({
      id: 'AngkatansDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'angkatanJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarAngkatan',
        totalProperty: 'total',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'keterangan', type: 'string', mapping: 'keterangan'}
        //{name: 'biaya', type: 'float', mapping: 'biaya'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  AngkatansColumnModel = new Ext.grid.ColumnModel(
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
      },{
        header: 'Keterangan',
        dataIndex: 'keterangan',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      /*},{
        xtype: 'numbercolumn',
        header: 'Biaya',
        dataIndex: 'biaya',
        format: '$0,0.00',
        width: 100,
        sortable: true,
        editor: {
            xtype: 'numberfield',
            allowBlank: false
            //minValue: 1,
            //maxValue: 150000
        }*/
      }]
    );
    AngkatansColumnModel.defaultSortable= true;

   AngkatanListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:700,
      height:480,
      title:'Data Angkatan',
      frame:true,
      id: 'AngkatanListingEditorGrid',
      store: AngkatansDataStore,
      cm: AngkatansColumnModel,
      iconCls : 'grid',
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Angkatan',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Angkatan',
            handler: confirmDeleteAngkatans,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }],
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: AngkatansDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Angkatan {0} - {1} of {2}',
        emptyMsg: "Data Angkatan kosong!"
        })
    });

  AngkatanListingWindow = new Ext.Window({
      id: 'AngkatanListingWindow',
      title: 'Data Angkatan',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: AngkatanListingEditorGrid
    });

  //AngkatanListingWindow.show();
  AngkatanListingEditorGrid.render("grid");
  AngkatansDataStore.load({params:{start:0, limit:25}});
  AngkatanListingEditorGrid.on('afteredit', saveTheAngkatan);

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  KeteranganField = new Ext.form.TextField({
    id: 'KeteranganField',
    fieldLabel: 'Keterangan',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  BiayaField = new Ext.form.TextField({
    id: 'BiayaField',
    fieldLabel: 'Biaya',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([0-9\s]+)$/
      });

  AngkatanCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        //items: [NamaField, KeteranganField, BiayaField],
        items: [NamaField, KeteranganField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheAngkatan
        },{
          text: 'Batal',
          handler: function(){
            // because of the global vars, we can only instantiate one window... so let's just hide it.
            AngkatanCreateWindow.hide();
          }
        }]
    });

  AngkatanCreateWindow= new Ext.Window({
      id: 'AngkatanCreateWindow',
      title: 'Tambahkan data Angkatan baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 200,
      plain:true,
      layout: 'fit',
      items: AngkatanCreateForm
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
});