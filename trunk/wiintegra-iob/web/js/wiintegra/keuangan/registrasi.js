// Global vars
var TagihansDataStore;
var SemesterDataStore;
var TagihansColumnModel;
var TagihanListingEditorGrid;
var TagihanListingWindow;
// Our new vars
var TagihanCreateForm;
var TagihanSearchForm;
var PembayaranForm;
var TagihanCreateWindow;
var NomorIndukField;
var NamaField;
var SemesterField;
var BayarAwalField;
var BayarLunasField;
var TotalField;
var IsDispenAwalField;
var IsDispenLunasField;
var JenisBiayaField;
var TagihanField;
var JumlahSKSField;
var panel;
var total;
var jsonData;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // ---------------------- search form tagihan --------------------------------
    MahasiswaDataStore = new Ext.data.Store({
      id: 'MahasiswaDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'mahasiswaJSONListAction',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMahasiswa'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'noInduk', type: 'string', mapping: 'noInduk'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    MahasiswaDataStore.load();

    SemesterDataStore = new Ext.data.Store({
      id: 'SemesterDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'semesterJSONListAction',
                method: 'POST'
            }),
            baseParams:{task: "COMBO"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarSemester'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    SemesterDataStore.load();

  NomorIndukField = new Ext.form.ComboBox({
     id:'NomorIndukField',
     fieldLabel: 'Nomor Induk',
     store: MahasiswaDataStore,
     mode: 'local',
     displayField: 'noInduk',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     typeAhead: true,
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            NamaField.setValue(r.data.nama);
        }
     }
  });
      
  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 20,
    allowBlank: false,
    readOnly: true,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  SemesterField = new Ext.form.ComboBox({
     id:'SemesterField',
     fieldLabel: 'Semester',
     store: SemesterDataStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  TagihanSearchForm = new Ext.FormPanel({
        //labelAlign: 'top',
        title: 'Form Pencarian',
        frame: true,
        bodyStyle:'padding:5px',
        iconCls : 'search',
        width: 320,
        height: 160,
        items: [NomorIndukField, NamaField, SemesterField],
        buttonAlign: 'right',
        buttons: [{
          text: 'Tampilkan',
          handler: tampilDataTagihan
        }]
    });

 //-----------------------------------------------------------------------------

 //Ext.MessageBox.alert('1');


 //----------------------------- tabel tagihan ---------------------------------
  // << CONFIG >>
  TagihansDataStore = new Ext.data.Store({
      id: 'TagihansDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'registrasiJSONAction',
                method: 'POST'
            }),
      baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        //totalProperty: 'total',
        root: 'tagihan'
      },[//{name: 'total', type: 'float', mapping: 'total'},[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'jumlah', type: 'float', mapping: 'jumlah'},
        {name: 'sksKontrak', type: 'int', mapping: 'sksKontrak'},
        {name: 'sksAmbil', type: 'float', mapping: 'sksAmbil'},
        {name: 'biayaPerSks', type: 'float', mapping: 'biayaPerSks'},
        {name: 'besarTagihan', type: 'float', mapping: 'besarTagihan'}//]
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  TagihansColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        dataIndex: 'id',
        width: 30,
        hidden: true
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 100
      },{
        header: 'SKS Kontrak',
        dataIndex: 'sksKontrak',
        width: 100
      },{
        header: 'SKS Ambil',
        dataIndex: 'sksAmbil',
        width: 100
      },{
        header: 'Biaya Per SKS',
        dataIndex: 'biayaPerSks',
        width: 200
      },{
        header: 'Besar Tagihan',
        dataIndex: 'besarTagihan',
        width: 200
      }]
    );
    TagihansColumnModel.defaultSortable= true;

   TagihanListingEditorGrid =  new Ext.grid.EditorGridPanel({
      width:750,
      height:300,
      title:'Data Tagihan',
      frame:true,
      id: 'TagihanListingEditorGrid',
      store: TagihansDataStore,
      iconCls : 'grid',
      cm: TagihansColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Tagihan',
            handler: tampilTambahTagihan,   
            iconCls:'add'
          },{
            text: 'Hapus',
            tooltip: 'Hapus Data Tagihan',
            handler: confirmDelete,
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }]
    });

  //----------------------------------------------------------------------------

  //------------------------ form pembayaran -----------------------------------
  BayarAwalField = new Ext.form.TextField({
    id: 'BayarAwalField',
    fieldLabel: 'Pembayaran Awal',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%'
      });

  BayarLunasField = new Ext.form.TextField({
    id: 'BayarLunasField',
    fieldLabel: 'Pembayaran Pelunasan',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%'
      });

  TotalField = new Ext.form.TextField({
    id: 'TotalField',
    fieldLabel: 'Total Tagihan',
    maxLength: 20,
    allowBlank: false,
    readOnly: true,
    anchor : '95%'
      });

   var dispenStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['0','Bayar Tunai'], ['1','Dispensasi']]
    });

  IsDispenAwalField = new Ext.form.ComboBox({
     id:'IsDispenAwalField',
     hideLabel: true,
     store: dispenStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  IsDispenLunasField = new Ext.form.ComboBox({
     id:'IsDispenLunasField',
     hideLabel: true,
     store: dispenStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  PembayaranForm = new Ext.FormPanel({
        //labelAlign: 'top',
        title: 'Form Pembayaran',
        frame: true,
        bodyStyle:'padding:5px 5px 0',
        width: 420,
        height: 160,
        labelWidth: 150,
        items: [TotalField,{
            layout:'column',
            items:[{
                columnWidth:.75,
                layout: 'form',
                items: [BayarAwalField, BayarLunasField]
            },{
                columnWidth:.25,
                layout: 'form',
                items: [IsDispenAwalField, IsDispenLunasField]
            }]
        }],

        buttonAlign: 'left',
        buttons: [{
          text: 'Simpan Pembayaran',
          handler: simpanPembayaran
        }]
    });

  //----------------------------------------------------------------------------


  //------------------------window tambah data tagihan -------------------------
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

  JenisBiayaField = new Ext.form.ComboBox({
     id:'JenisBiayaField',
     fieldLabel: 'Jenis Biaya',
     store: BiayasDataStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            TagihanField.setValue(r.data.jumlah);
            if(r.data.kode == 'SKS'){
                //Ext.MessageBox.alert('', r.data.kode);
                TagihanField.setDisabled(true);
                JumlahSKSField.setDisabled(false);
            }
            else{
                TagihanField.setDisabled(false);
                JumlahSKSField.setDisabled(true);
            }
        }
     }
  });

  TagihanField = new Ext.form.TextField({
    id: 'TagihanField',
    fieldLabel: 'Tagihan',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%'
      });

  JumlahSKSField = new Ext.form.TextField({
    id: 'JumlahSKSField',
    fieldLabel: 'Jumlah SKS Kontrak',
    maxLength: 20,
    allowBlank: false,
    readOnly: true,
    anchor : '95%'
      });

  TagihanCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [JenisBiayaField, TagihanField, JumlahSKSField],
    buttons: [{
      text: 'Simpan dan Tutup',
      handler: tambahTagihan
    },{
      text: 'Batal',
      handler: function(){
        // because of the global vars, we can only instantiate one window... so let's just hide it.
        TagihanCreateWindow.hide();
      }
    }]
    });

  TagihanCreateWindow= new Ext.Window({
      id: 'TagihanCreateWindow',
      title: 'Tambahkan data tagihan baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 250,
      plain:true,
      layout: 'fit',
      items: TagihanCreateForm
    });

  //----------------------------------------------------------------------------


  panel = new Ext.Panel({
        width: 750,
        items: [{
            layout:'column',
            items:[{
                items: [TagihanSearchForm]
            },{
                items: [PembayaranForm]
            }]
        },TagihanListingEditorGrid]
  });
  panel.render("grid");

  /**
 * Comment
 */
    function tampilDataTagihan() {
        TagihansDataStore.load({
            params:{
                start:0, limit:25, idMhs: NomorIndukField.getValue(), idSem: SemesterField.getValue()
            },
            callback : function(){
               Ext.Ajax.request({
                  waitMsg: 'Harap tunggu...',
                  url: 'registrasiJSONAction',
                  params: {
                     task: "DATA_PEMBAYARAN"
                  },
                  success: function(result, request){
                     jsonData = Ext.util.JSON.decode(result.responseText);
                     tampilDataPembayaran();
                  },
                  failure: function(result, request){
                     var hasil=result.responseText;
                     Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
                  }
               });
            }
        });
    }

    function tampilDataPembayaran(){
        BayarAwalField.setValue(jsonData.bayarAwal);
        BayarLunasField.setValue(jsonData.bayarLunas);
        TotalField.setValue(jsonData.total);
        IsDispenAwalField.setValue(jsonData.isDispenAwal);
        IsDispenLunasField.setValue(jsonData.isDispenLunas);
    }

  function simpanPembayaran(){
    if(JenisBiayaFIeld.isValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'registrasiJSONAction',
        params: {
          task: "SIMPAN",
          bayarAwal:      BayarAwalField.getValue(),
          bayarLunas:     BayarLunasField.getValue(),
          total:          TotalField.getValue(),
          isDispenAwal:   IsDispenAwalField.getValue(),
          isDispenLunas:  IsDispenLunasField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Pengubahan Data Sukses','Data Pembayaran berhasil diubah!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Pengubahan data Pembayaran gagal!');
            break;
          }
        },
        failure: function(response){
          var result=response.responseText;
          Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
        }
      });
    }
    else{
        Ext.MessageBox.alert('Error', 'Pilih Jenis Biaya');
    }
  }

  function tambahTagihan(){
   TagihanCreateWindow.hide();
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'registrasiJSONAction',
      params: {
         task: "UPDATE",
         jenisBiaya: JenisBiayaField.getValue(),
         tagihan: TagihanField.getValue(),
         jumlahSks: JumlahSKSField.getValue()
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Tagihan baru berhasil ditambahkan!');
            TagihansDataStore.commitChanges();
            TagihansDataStore.reload();
            break;
         default:
            Ext.MessageBox.alert('Penambahan data gagal!');
            break;
         }
      },
      failure: function(response){
         var result=response.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
  }

    function confirmDelete() {
        Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data tagihan. Lanjutkan?', deleteTagihan);
    }

  function deleteTagihan(btn){
    if(btn=='yes'){
         var rec = TagihanListingEditorGrid.getSelectionModel().getSelected();
         var idx = TagihanListingEditorGrid.getStore().indexOf(rec);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'registrasiJSONAction',
            params: {
               task: "DELETE",
               id:  idx
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                TagihansDataStore.reload();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus data Tagihan!');
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

  function resetFormField() {
    JenisBiayaField.setValue('');
    TagihanField.setValue('');
    JumlahSKSField.setValue('');
  }

  function tampilTambahTagihan(){
    resetFormField();
    BiayasDataStore.load();
    TagihanCreateWindow.show();
  }

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