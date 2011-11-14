// Global vars
var BiayasDataStore;
var BiayasColumnModel;
var BiayaListingEditorGrid;
var BiayaListingWindow;
// Our new vars
var BiayaCreateForm;
var BiayaCreateWindow;
var KodeField;
var NamaField;
var KeteranganField;
var JumlahField;
var IsSksField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // This saves the Biaya after a cell has been edited
  function saveTheBiaya(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'biayaJSONListAction',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         kode: oGrid_event.record.data.kode,
         nama: oGrid_event.record.data.nama,
         keterangan: oGrid_event.record.data.keterangan,
         jumlah: oGrid_event.record.data.jumlah,
         isSks: oGrid_event.record.data.isSks
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            BiayasDataStore.commitChanges();
            BiayasDataStore.reload();
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

  // this creates a new Biaya
  function createTheBiaya(){
     if(isBiayaFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'biayaJSONListAction',
        params: {
          task: "CREATE",
          kode:      KodeField.getValue(),
          nama:       NamaField.getValue(),
          keterangan:      KeteranganField.getValue(),
          jumlah:       JumlahField.getValue(),
          isSks:      IsSksField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Biaya baru berhasil ditambahkan!');
            BiayasDataStore.reload();
            BiayaCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Biaya baru gagal!');
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
  function resetBiayaForm(){
    KodeField.setValue('');
    NamaField.setValue('');
    KeteranganField.setValue('');
    JumlahField.setValue('');
    IsSksField.setValue('');
  }

  // check if the form is valid
  function isBiayaFormValid(){
      return(KodeField.isValid() && NamaField.isValid() && KeteranganField.isValid() && JumlahField.isValid() && IsSksField.isValid());
  }

  // display or bring forth the form
  function displayFormWindow(){
     if(!BiayaCreateWindow.isVisible()){
       resetBiayaForm();
       BiayaCreateWindow.show();
     } else {
       BiayaCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteBiayas(){
    if(BiayaListingEditorGrid.selModel.getCount() == 1) // only one Biaya is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data biaya. Lanjutkan?', deleteBiayas);
    } else if(BiayaListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+BiayaListingEditorGrid.selModel.getCount()+' data biaya. Lanjutkan?', deleteBiayas);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Biaya terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteBiayas(btn){
    if(btn=='yes'){
         var selections = BiayaListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< BiayaListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'biayaJSONListAction',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                BiayasDataStore.reload();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Biaya!');
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


   var isSksStore = new Ext.data.SimpleStore({
    fields:['isSksValue', 'isSksName'],
    data: [['1','Ya'], ['0','Bukan']]
    });

  // << CONFIG >>
  BiayasDataStore = new Ext.data.Store({
      id: 'BiayasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'biayaJSONListAction',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarBiaya'
        //totalProperty: 'total',
        //id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'keterangan', type: 'string', mapping: 'keterangan'},
        {name: 'jumlah', type: 'string', mapping: 'jumlah'},
        {name: 'isSks', type: 'int', mapping: 'isSks'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  BiayasColumnModel = new Ext.grid.ColumnModel(
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
            maxLength: 20,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 200,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 20,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Jumlah (dalam $)',
        dataIndex: 'jumlah',
        width: 150,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 20,
          //maskRe: /([0-9]+(\.[0-9]{1,2})?)$/
          maskRe: /^\d{0,2}(\.\d{1,2})?$/
          })
      },{
        header: 'Keterangan',
        dataIndex: 'keterangan',
        width: 250,
        editor: new Ext.form.TextField({
          allowBlank: true,
          maxLength: 20,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Biaya SKS?',
        dataIndex: 'isSks',
        width: 50,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: isSksStore,
               mode: 'local',
               displayField: 'isSksName',
               valueField: 'isSksValue',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return isSksStore.queryBy(function(rec){
            return rec.data.isSksValue == val;
            }).itemAt(0).data.isSksName;
            }
      }]
    );
    BiayasColumnModel.defaultSortable= true;

   BiayaListingEditorGrid =  new Ext.grid.EditorGridPanel({
      width:800,
      height:480,
      title:'Data Komponen Biaya',
      frame:true,
      id: 'BiayaListingEditorGrid',
      store: BiayasDataStore,
      cm: BiayasColumnModel,
      iconCls : 'grid',
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Komponen Biaya',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Komponen Biaya',
            handler: confirmDeleteBiayas,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }]
    });

  BiayasDataStore.load({params:{start:0, limit:25}});
  BiayaListingEditorGrid.render("grid");
  BiayaListingEditorGrid.on('afteredit', saveTheBiaya);

  KodeField = new Ext.form.TextField({
    id: 'KodeField',
    fieldLabel: 'Kode',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

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
    allowBlank: true,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  JumlahField = new Ext.form.TextField({
    id: 'JumlahField',
    fieldLabel: 'Jumlah',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /^\d{0,2}(\.\d{1,2})?$/
      });

  IsSksField = new Ext.form.ComboBox({
     id:'IsSksField',
     fieldLabel: 'Biaya SKS?',
     store: isSksStore,
     mode: 'local',
     displayField: 'isSksName',
     allowBlank: false,
     valueField: 'isSksValue',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            if (i == 1){
                JumlahField.setDisabled(false);
            }
            else if (i == 0){
                JumlahField.setDisabled(true);
            }
        }
     }
  });

  BiayaCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [KodeField, NamaField, KeteranganField, JumlahField, IsSksField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheBiaya
        },{
          text: 'Batal',
          handler: function(){
            // because of the global vars, we can only instantiate one window... so let's just hide it.
            BiayaCreateWindow.hide();
          }
        }]
    });

  BiayaCreateWindow= new Ext.Window({
      id: 'BiayaCreateWindow',
      title: 'Tambahkan data Biaya baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 400,
      plain:true,
      layout: 'fit',
      items: BiayaCreateForm
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