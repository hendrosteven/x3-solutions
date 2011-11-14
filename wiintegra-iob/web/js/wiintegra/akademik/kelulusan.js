// Global vars
var KelulusansDataStore;
var KelulusansColumnModel;
var KelulusanListingEditorGrid;
var KelulusanListingWindow;
// Our new vars
var KelulusanCreateForm;
var KelulusanCreateWindow;
var NamaField;
var KeteranganField;
var BiayaField;
var HelpWindow;

Ext.onReady(function(){

  Ext.QuickTips.init();
  
  var fakultasStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kelulusanJSON',
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
                url: 'kelulusanJSON',
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


  // This saves the Kelulusan after a cell has been edited
  function saveTheKelulusan(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'kelulusanJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         tglUjian: oGrid_event.record.data.tglUjian.format('m/d/Y'),
         tglYudisium: oGrid_event.record.data.tglYudisium.format('m/d/Y'),
         judulSkripsi: oGrid_event.record.data.judulSkripsi,
         nilai: oGrid_event.record.data.nilai//,
         //predikat: oGrid_event.record.data.predikat
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            KelulusansDataStore.commitChanges();
            KelulusansDataStore.reload();
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

  // this creates a new Kelulusan
  function createTheKelulusan(){
     if(isKelulusanFormValid()){
      Ext.MessageBox.alert('','sini');
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'kelulusanJSON',
        params: {
             task: "CREATE",
             id: NomorIndukField.getValue(),
             tglUjian: TglUjianField.getValue().format('m/d/Y'),
             tglYudisium: TglYudisiumField.getValue().format('m/d/Y'),
             judulSkripsi: JudulSkripsiField.getValue(),
             nilai: NilaiField.getValue()
             //predikat: PredikatField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Kelulusan baru berhasil ditambahkan!');
            KelulusansDataStore.reload();
            KelulusanCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Kelulusan baru gagal!');
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
  function resetKelulusanForm(){
    NomorIndukField.setValue('');
    NamaField.setValue('');
    TglUjianField.setValue('');
    TglYudisiumField.setValue('');
    JudulSkripsiField.setValue('');
    NilaiField.setValue('');
    //PredikatField.setValue('');
  }

  // check if the form is valid
  function isKelulusanFormValid(){
      return(NomorIndukField.isValid() && NamaField.isValid() && TglUjianField.isValid() && TglYudisiumField.isValid()
            && TglYudisiumField.isValid() && JudulSkripsiField.isValid() && NilaiField.isValid());
  }

  // display or bring forth the form
  function displayFormWindow(){
     if(!KelulusanCreateWindow.isVisible()){
       resetKelulusanForm();
       KelulusanCreateWindow.show();
     } else {
       KelulusanCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteKelulusans(){
    if(KelulusanListingEditorGrid.selModel.getCount() == 1) // only one Kelulusan is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data kelulusan. Lanjutkan?', deleteKelulusans);
    } else if(KelulusanListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+KelulusanListingEditorGrid.selModel.getCount()+' data kelulusan. Lanjutkan?', deleteKelulusans);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Kelulusan terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteKelulusans(btn){
    if(btn=='yes'){
         var selections = KelulusanListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< KelulusanListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'kelulusanJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                KelulusansDataStore.reload();
                MahasiswaDataStore.reload();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Kelulusan!');
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

   var nilaiStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['A','A'],['B','B'],['C','C'],['D','D'],['E','E']]
    });

   /*var predikatStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['Memuaskan','Memuaskan'],['Sangat Memuaskan','Sangat Memuaskan'],['Dengan Pujian','Dengan Pujian']]
    });*/

  // << CONFIG >>
  KelulusansDataStore = new Ext.data.Store({
      id: 'KelulusansDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kelulusanJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarDataKelulusan',
        totalProperty: 'total',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'noInduk', type: 'string', mapping: 'noInduk'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'jurusan', type: 'string', mapping: 'jurusan'},
        //{name: 'id', type: 'int', mapping: 'id'},
        {name: 'tglUjian', type: 'date', mapping: 'tglUjian', dateFormat: 'm/d/Y'},
        {name: 'tglYudisium', type: 'date', mapping: 'tglYudisium', dateFormat: 'm/d/Y'},
        {name: 'judulSkripsi', type: 'string', mapping: 'judulSkripsi'},
        {name: 'nilai', type: 'string', mapping: 'nilai'},
        {name: 'predikat', type: 'string', mapping: 'predikat'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  KelulusansColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        dataIndex: 'id',
        hidden: true
      },{
        header: 'No. Induk',
        dataIndex: 'noInduk',
        width: 100
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 150
      },{
        header: 'Jurusan',
        dataIndex: 'jurusan',
        width: 250
      },{
       header: 'Tanggal Ujian',
       dataIndex: 'tglUjian',
       width: 100,
       renderer: Ext.util.Format.dateRenderer('d/m/Y'),
       editor: new Ext.form.DateField({
            format: 'd/m/Y',
            //minValue: '01/01/06',
            disabledDays: [0, 6],
            disabledDaysText: 'Plants are not available on the weekends'
        }),
        hidden: false
      },{
       header: 'Tanggal Yudisium',
       dataIndex: 'tglYudisium',
       width: 100,
       renderer: Ext.util.Format.dateRenderer('d/m/Y'),
       editor: new Ext.form.DateField({
            format: 'd/m/Y',
            //minValue: '01/01/06',
            disabledDays: [0, 6],
            disabledDaysText: 'Plants are not available on the weekends'
        }),
        hidden: false
      },{
        header: 'Judul Skripsi',
        dataIndex: 'judulSkripsi',
        width: 200,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Nilai',
        dataIndex: 'nilai',
        width: 50,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: nilaiStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               allowBlank: false,
               listClass: 'x-combo-list-small'
            })
      },{
        header: 'Predikat',
        dataIndex: 'predikat',
        width: 100//,
        /*editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: predikatStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               allowBlank: false,
               listClass: 'x-combo-list-small'
            })*/
      }]
    );
    KelulusansColumnModel.defaultSortable= true;

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
            KelulusansDataStore.load({params:{start:0, limit:25, fakultas:r.data.id}});
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
            KelulusansDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField, progdi:r.data.id}});
        }
     }
  });

  var NimSearchField = new Ext.form.TextField({
        emptyText: 'Cari berdasarkan No. Induk',
        width:150,
        enableKeyEvents:true,
          listeners: {
            'render': function(c) {
              c.getEl().on('keyup', function() {
                KelulusanListingEditorGrid.store.filter('noInduk',NimSearchField.getValue(),true,false);
              }, c);
            }
          }
    });

   KelulusanListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...', removeMask:true},
      width:1100,
      height:480,
      title:'Data Kelulusan',
      frame:true,
      id: 'KelulusanListingEditorGrid',
      store: KelulusansDataStore,
      cm: KelulusansColumnModel,
      iconCls : 'grid',
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
      tbar: [
          FakultasSearchField
          , '-', 
          ProgdiSearchField
          , '-',
          NimSearchField
          , '-', {
            text: 'Tambah',
            tooltip: 'Tambah Data Kelulusan',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Kelulusan',
            handler: confirmDeleteKelulusans,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Print Transkrip',
            tooltip: 'Print Transkrip Akhir',
            handler: print,   // Confirm before deleting
            iconCls:'print'
          }, '-', {
            text: 'Cetak ke Excel',
            tooltip: 'Print Transkrip Akhir ke file Excel',
            iconCls:'excel',                      // reference to our css
            handler: printExcel
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }],
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: KelulusansDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Mahasiswa {0} - {1} of {2}',
        emptyMsg: "Data Mahasiswa kosong!"
        })
    });


    KelulusansDataStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {fakultas: FakultasSearchField.getValue()});
            }
        },
        'load':{
            fn: function(store, options){
                if(NimSearchField.getValue() != '')
                    KelulusanListingEditorGrid.store.filter('noInduk',NimSearchField.getValue(),true,false);
            }
        }
    });

  //KelulusanListingWindow.show();
  KelulusanListingEditorGrid.render("grid");
  KelulusansDataStore.load({params:{start:0, limit:25, fakultas:''}});
  KelulusanListingEditorGrid.on('afteredit', saveTheKelulusan);

  // ---------------------- search form tagihan --------------------------------
    MahasiswaDataStore = new Ext.data.Store({
      id: 'MahasiswaDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kelulusanJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_BLM_LULUS"}, // this parameter is passed for any HTTP request
            //baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMahasiswa'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'noInduk', type: 'string', mapping: 'noInduk'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'namaFakultas', type: 'string', mapping: 'namaFakultas'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    MahasiswaDataStore.load();

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

  var fieldsetMahasiswa = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Mahasiswa",
      autoHeight: true,
      //collapsible: true,
      layout: 'form',
      items:[
              NomorIndukField, NamaField
      ]
  });
 
  TglUjianField = new Ext.form.DateField({
    id: 'TglUjianField',
    fieldLabel: 'Tanggal Ujian',
    allowBlank: false,
    format: 'd/m/Y',
    maxLength: 20
      });

  TglYudisiumField = new Ext.form.DateField({
    id: 'TglYudisiumField',
    fieldLabel: 'Tanggal Yudisium',
    allowBlank: false,
    format: 'd/m/Y',
    maxLength: 20
      });

  JudulSkripsiField = new Ext.form.TextField({
    id: 'JudulSkripsiField',
    fieldLabel: 'Judul Skripsi',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  NilaiField = new Ext.form.ComboBox({
     id:'NilaiField',
     fieldLabel: 'Nilai',
     store: nilaiStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  /*PredikatField = new Ext.form.ComboBox({
     id:'PredikatField',
     fieldLabel: 'Predikat',
     store: predikatStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });*/

  //Ext.MessageBox.alert('','sini');
  var fieldsetKelulusan = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Kelulusan",
      autoHeight: true,
      layout: 'form',
      items:[
              TglUjianField, TglYudisiumField, JudulSkripsiField, NilaiField//, PredikatField
      ]
  });
 //-----------------------------------------------------------------------------
 
  KelulusanCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [fieldsetMahasiswa, fieldsetKelulusan],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheKelulusan
        },{
          text: 'Batal',
          handler: function(){
            // because of the global vars, we can only instantiate one window... so let's just hide it.
            KelulusanCreateWindow.hide();
          }
        }]
    });

  KelulusanCreateWindow= new Ext.Window({
      id: 'KelulusanCreateWindow',
      title: 'Tambahkan data Kelulusan baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 500,
      plain:true,
      layout: 'fit',
      items: KelulusanCreateForm
    });

  var teks = 'Untuk menambahkan data, klik button Tambah.<br>' +
             'Untuk menghapus data, klik row yg akan dihapus dan klik button Hapus.<br>' +
             'Untuk mengubah data, klik pada cell yang ingin diubah. Setelah melakukan perubahan data, tekan enter atau klik di luar cell.<br>' +
             'Untuk melakukan pencarian berdasarkan Fakultas, gunakan drop down Fakultas di sebelah atas tabel.<br>' +
             'Untuk mencetak transkrip akhir Mahasiswa dalam format PDF, klik baris Mahasiswa yang dimaksud pada tabel dan klik tombol "Print Transkrip".' +
             'Untuk mencetak transkrip akhir Mahasiswa ke dalam Microsoft Excel, klik baris Mahasiswa yang dimaksud pada tabel dan klik tombol "Cetak Excel".';
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
        if(KelulusanListingEditorGrid.selModel.getCount() != 1)
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu 1 Data Mahasiswa!');
        else
            var win = window.open('printTranskripLulus?id=' + KelulusanListingEditorGrid.selModel.getSelections()[0].data.id, '_blank', '');
        //win.show();
    }
    
    function printExcel(){
        if(KelulusanListingEditorGrid.selModel.getCount() != 1)
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu 1 Data Mahasiswa!');
        else
            var win = window.open('printTranskripLulusExcel?id=' + KelulusanListingEditorGrid.selModel.getSelections()[0].data.id, '_blank', '');
        //win.show();
    }
});