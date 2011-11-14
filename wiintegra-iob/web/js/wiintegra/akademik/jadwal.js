var JadwalDataStore;
var JadwalColumnModel;
var JadwalListingEditorGrid;

var JadwalCreateForm;
var JadwalCreateWindow;
var MatkulField;
var KelasParalelField;
var DosenField;
var HariField;
var JamMulaiField;
var JamSelesaiField;
var RuangField;
var KapasitasField;

var MatakuliahDataStore;
var DosenDataStore;
var HariStore;
var JamMulaiStore;
var JamSelesaiStore;
var RuangDataStore;

Ext.onReady(function(){

  Ext.QuickTips.init();

//----------------------- store buat grid --------------------------------------
  JadwalDataStore = new Ext.data.Store({
      id: 'JadwalDataStore',
      //waitMsg : 'Harap tunggu...',
      proxy: new Ext.data.HttpProxy({
                url: 'jadwalJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_BY_FAKULTAS_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarJadwal',
        totalProperty: 'total'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'matkul', type: 'int', mapping: 'matkul'},
        {name: 'kelas', type: 'string', mapping: 'kelas'},
        {name: 'dosen', type: 'int', mapping: 'dosen'},
        {name: 'hari', type: 'string', mapping: 'hari'},
        {name: 'mulai', type: 'string', mapping: 'mulai'},
        {name: 'selesai', type: 'string', mapping: 'selesai'},
        {name: 'ruang', type: 'string', mapping: 'ruang'},
        {name: 'kapasitas', type: 'int', mapping: 'kapasitas'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

//------------------------------------------------------------------------------

//-------------------- store buat semua combobox -------------------------------
  var fakultasStore = new Ext.data.Store({
      id: 'fakultasStore',
      proxy: new Ext.data.HttpProxy({
                url: 'jadwalJSON',
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

  var progdiSearchStore = new Ext.data.Store({
      id: 'ProgdiDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'jadwalJSON',
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

  MatakuliahDataStore = new Ext.data.Store({
      id: 'MatakuliahDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'jadwalJSON',
                method: 'POST'
            }),
            baseParams:{task: "MATAKULIAH_BY_FAKULTAS"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMatakuliah'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  DosenDataStore = new Ext.data.Store({
      id: 'DosenDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'jadwalJSON',
                method: 'POST'
            }),
            baseParams:{task: "DOSEN"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarDosen'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   HariStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['Senin','Senin'], ['Selasa','Selasa'], ['Rabu','Rabu'], ['Kamis','Kamis'], ['Jumat','Jumat'], ['Sabtu','Sabtu'], ['Minggu','Minggu']]
    });

   JamMulaiStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['07.00','07.00'], ['08.00','08.00'], ['09.00','09.00'], ['10.00','10.00'], ['11.00','11.00'], ['12.00','12.00'],
        ['13.00','13.00'], ['14.00','14.00'], ['15.00','15.00'], ['16.00','16.00'], ['17.00','17.00'], ['18.00','18.00'],
        ['19.00','19.00'], ['20.00','20.00'], ['21.00','21.00']
    ]
    });

   JamSelesaiStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['08.00','08.00'], ['09.00','09.00'], ['10.00','10.00'], ['11.00','11.00'], ['12.00','12.00'],
        ['13.00','13.00'], ['14.00','14.00'], ['15.00','15.00'], ['16.00','16.00'], ['17.00','17.00'], ['18.00','18.00'],
        ['19.00','19.00'], ['20.00','20.00'], ['21.00','21.00']
    ]
    });

  RuangDataStore = new Ext.data.Store({
      id: 'RuangDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'jadwalJSON',
                method: 'POST'
            }),
            baseParams:{task: "RUANG"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarRuang'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    MatakuliahDataStore.load({params:{fakultas:''}});
    DosenDataStore.load();
    RuangDataStore.load();
    fakultasStore.load();
    progdiSearchStore.load();

  FakultasSearchField = new Ext.form.ComboBox({
     id:'FakultasSearchField',
     emptyText: 'Pilih Fakultas',
     store: fakultasStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            progdiSearchStore.reload({params:{fakultas:r.data.id}});
            JadwalDataStore.reload({params:{start:0, limit:25, fakultas:r.data.id}});
            MatakuliahDataStore.reload({params:{fakultas:r.data.id}});
            JadwalCreateWindow.setTitle("Tambahkan data Jadwal baru Fakultas " + r.data.nama);
        }
     }
  });

  ProgdiSearchField = new Ext.form.ComboBox({
     id:'ProgdiSearchField',
     emptyText: 'Pilih Program Studi',
     store: progdiSearchStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            JadwalDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue(), progdi:r.data.id}});
        }
     }
  });

//------------------------------------------------------------------------------

//-------------------------- grid jadwal ---------------------------------------
  JadwalColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        readOnly: true,
        dataIndex: 'id',
        hidden: true
      },{
        header: 'Kode',
        dataIndex: 'kode',
        width: 150
      },{
        header: 'Matakuliah',
        dataIndex: 'matkul',
        width: 250,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: MatakuliahDataStore,
               //mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return MatakuliahDataStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Nama Kelas Paralel',
        dataIndex: 'kelas',
        width: 100,
        editor: new Ext.form.TextField({
            //allowBlank: false,
            maxLength: 20,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Dosen',
        dataIndex: 'dosen',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: DosenDataStore,
               //mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return DosenDataStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Hari',
        dataIndex: 'hari',
        width: 100,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: HariStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return HariStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Jam Mulai',
        dataIndex: 'mulai',
        width: 50,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: JamMulaiStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return JamMulaiStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Jam Selesai',
        dataIndex: 'selesai',
        width: 50,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: JamSelesaiStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return JamSelesaiStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Ruang',
        dataIndex: 'ruang',
        width: 50,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: RuangDataStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return RuangDataStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Kapasitas',
        dataIndex: 'kapasitas',
        width: 50,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20,
            maskRe: /([0-9\s]+)$/
          })
      }]
    );
    JadwalColumnModel.defaultSortable= true;

   JadwalListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:1000,
      height:450,
      title:'Data Jadwal',
      frame:true,
      id: 'JadwalListingEditorGrid',
      store: JadwalDataStore,
      iconCls : 'grid',
      cm: JadwalColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          FakultasSearchField
          , '-',
          ProgdiSearchField
          , '-', { // Added in Tutorial 6
            text: 'Tambah',
            tooltip: 'Tambah Data Jadwal',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Jadwal',
            handler: confirmDeleteJadwal,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Print',
            tooltip: 'Print Data Jadwal',
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
        store: JadwalDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Jadwal {0} - {1} of {2}',
        emptyMsg: "Data Jadwal kosong!"
        })
    });

    JadwalDataStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {fakultas: FakultasSearchField.getValue(), progdi:ProgdiSearchField.getValue()});
            }
        }
    });

//  JadwalDataStore.load({params:{start:0, limit:25}});
//  JadwalListingEditorGrid.render("grid");
//  JadwalListingEditorGrid.on('afteredit', saveTheJadwal);
//------------------------------------------------------------------------------

//------------ ambil data current semester dulu baru dirender ke panel ---------
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'jadwalJSON',
          params: {
             task: "CURRENT"
          },
          success: function(response){
             //Ext.MessageBox.alert('', 'sini');
             var result=response.responseText;
             semester = result;
             JadwalListingEditorGrid.setTitle('Data Jadwal '+semester);
             //JadwalDataStore.load({params:{start:0, limit:25}});
             JadwalListingEditorGrid.render("grid");
             JadwalDataStore.load({params:{start:0, limit:25, fakultas:''}});
             JadwalListingEditorGrid.on('afteredit', saveTheJadwal);
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
//------------------------------------------------------------------------------

//------------------------- form tambah jadwal ---------------------------------
  MatkulField = new Ext.form.ComboBox({
     id:'MatkulField',
     fieldLabel: 'Matakuliah',
     store: MatakuliahDataStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  KelasParalelField = new Ext.form.TextField({
    id: 'KelasParalelField',
    fieldLabel: 'Nama Kelas Paralel',
    maxLength: 2,
    //allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  DosenField = new Ext.form.ComboBox({
     id:'DosenField',
     fieldLabel: 'Dosen',
     store: DosenDataStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  HariField = new Ext.form.ComboBox({
     id:'HariField',
     fieldLabel: 'Hari',
     store: HariStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  JamMulaiField = new Ext.form.ComboBox({
     id:'JamMulaiField',
     fieldLabel: 'Jam Mulai',
     store: JamMulaiStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  JamSelesaiField = new Ext.form.ComboBox({
     id:'JamSelesaiField',
     fieldLabel: 'Jam Selesai',
     store: JamSelesaiStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  RuangField = new Ext.form.ComboBox({
     id:'RuangField',
     fieldLabel: 'Ruang',
     store: RuangDataStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  KapasitasField = new Ext.form.TextField({
    id: 'KapasitasField',
    fieldLabel: 'Kapasitas',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([0-9\s]+)$/
      });

  JadwalCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [MatkulField, KelasParalelField, DosenField, HariField, JamMulaiField, JamSelesaiField, RuangField, KapasitasField],
        buttons: [{
              text: 'Simpan dan Tutup',
              handler: createTheJadwal
            },{
              text: 'Batal',
              handler: function(){
                // because of the global vars, we can only instantiate one window... so let's just hide it.
                JadwalCreateWindow.hide();
              }
        }]
    });

  JadwalCreateWindow= new Ext.Window({
      id: 'JadwalCreateWindow',
      title: 'Tambahkan data Jadwal baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 500,
      plain:true,
      layout: 'fit',
      items: JadwalCreateForm
    });
    //------------------------------------------------------------------------------


//-------------------------- fungsi-fungsi -------------------------------------
  // This saves the Jadwal after a cell has been edited
  function saveTheJadwal(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'jadwalJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         matkul: oGrid_event.record.data.matkul,
         kelas: oGrid_event.record.data.kelas,
         dosen: oGrid_event.record.data.dosen,
         hari: oGrid_event.record.data.hari,
         mulai: oGrid_event.record.data.mulai,
         selesai: oGrid_event.record.data.selesai,
         ruang: oGrid_event.record.data.ruang,
         kapasitas: oGrid_event.record.data.kapasitas
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            JadwalDataStore.commitChanges();
            JadwalDataStore.reload();
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

  // this creates a new Jadwal
  function createTheJadwal(){
     if(isJadwalFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'jadwalJSON',
        params: {
          task: "CREATE",
          matkul:       MatkulField.getValue(),
          kelas:        KelasParalelField.getValue(),
          dosen:        DosenField.getValue(),
          hari:         HariField.getValue(),
          mulai:        JamMulaiField.getValue(),
          selesai:      JamSelesaiField.getValue(),
          ruang:        RuangField.getValue(),
          kapasitas:    KapasitasField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Jadwal baru berhasil ditambahkan!');
            JadwalDataStore.reload();
            JadwalCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Jadwal baru gagal!');
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

  function resetJadwalForm(){
    MatkulField.setValue('');
    KelasParalelField.setValue('');
    DosenField.setValue('');
    HariField.setValue('');
    JamMulaiField.setValue('');
    JamSelesaiField.setValue('');
    RuangField.setValue('');
    KapasitasField.setValue('');
  }

  // check if the form is valid
  function isJadwalFormValid(){
      return(MatkulField.isValid() && KelasParalelField.isValid() && DosenField.isValid()
         && HariField.isValid() && JamMulaiField.isValid()
         && JamSelesaiField.isValid() && RuangField.isValid()
          && KapasitasField.isValid()
        );
  }

  // display or bring forth the form
  function displayFormWindow(){
     JadwalCreateWindow.show();
     resetJadwalForm();
     if(!JadwalCreateWindow.isVisible()){
       JadwalCreateWindow.show();
     } else {
       JadwalCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteJadwal(){
    if(JadwalListingEditorGrid.selModel.getCount() == 1) // only one Jadwal is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data Jadwal. Lanjutkan?', deleteJadwal);
    } else if(JadwalListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+JadwalListingEditorGrid.selModel.getCount()+' data Jadwal. Lanjutkan?', deleteJadwal);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Jadwal terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteJadwal(btn){
    if(btn=='yes'){
         var selections = JadwalListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< JadwalListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'jadwalJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                JadwalDataStore.reload();
                break;
              case -1:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Sebagian data Jadwal yang Anda pilih tidak dapat dihapus! Pastikan data Jadwal yang Anda ingin hapus tidak dipakai oleh Mahasiswa!');
                JadwalDataStore.reload();
                break;
              case 0:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Seluruh data Jadwal yang Anda pilih tidak dapat dihapus! Pastikan data Jadwal yang Anda ingin hapus tidak dipakai oleh Mahasiswa!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Jadwal!');
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
//------------------------------------------------------------------------------

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
        window.open('printJadwal?idFakultas=' + FakultasSearchField.getValue(), '_blank', '');
    }
});