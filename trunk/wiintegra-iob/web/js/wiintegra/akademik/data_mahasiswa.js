// Global vars
var MahasiswasDataStore;
var MahasiswasColumnModel;
var MahasiswaListingEditorGrid;
var MahasiswaListingWindow;
// Our new vars
var MahasiswaCreateForm;
var MahasiswaCreateWindow;

var NomorIndukField;
var PasswordField;
var NamaField;
var FakultasField;
var ProgdiField;
var AngkatanField;

var JalanField;
//var KelurahanField;
//var KecamatanField;
var SubDistrikField;
var DistrikField;
var KodeposField;
var TelponField;
var HandphoneField;
var EmailField;

var NamaAyahField;
var NamaIbuField;
var AlamatOrtuField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // This saves the Mahasiswa after a cell has been edited
  function saveTheMahasiswa(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'dataMahasiswaJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         noInduk: oGrid_event.record.data.noInduk,
         password: oGrid_event.record.data.password,
         nama: oGrid_event.record.data.nama,
         fakultas: oGrid_event.record.data.fakultas,
         progdi: oGrid_event.record.data.progdi,
         angkatan: oGrid_event.record.data.angkatan,
         jenjang: oGrid_event.record.data.jenjang,
         jenisKelamin : oGrid_event.record.data.jenisKelamin,
         tempatLahir: oGrid_event.record.data.tempatLahir,
         tanggalLahir: oGrid_event.record.data.tanggalLahir.format('m/d/Y'),
         jalan: oGrid_event.record.data.jalan,
         subDistrik: oGrid_event.record.data.subDistrik,
         distrik: oGrid_event.record.data.distrik,
         kodepos: oGrid_event.record.data.kodepos,
         telpon: oGrid_event.record.data.telpon,
         handphone: oGrid_event.record.data.handphone,
         email: oGrid_event.record.data.email,
         namaAyah: oGrid_event.record.data.namaAyah,
         namaIbu: oGrid_event.record.data.namaIbu,
         alamatOrtu: oGrid_event.record.data.alamatOrtu
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            MahasiswasDataStore.commitChanges();
            MahasiswasDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'Nomor Induk Mahasiswa sudah ada!');
            break;
         default:
            Ext.MessageBox.alert('Error' + 'Pengubahan data gagal!');
            break;
         }
      },
      failure: function(response){
         var result=response.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
  }

  // this creates a new Mahasiswa
  function createTheMahasiswa(){
     if(isMahasiswaFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'dataMahasiswaJSON',
        params: {
             task: "CREATE",
             noInduk: NomorIndukField.getValue(),
             password: PasswordField.getValue(),
             nama: NamaField.getValue(),
             fakultas: FakultasField.getValue(),
             progdi: ProgdiField.getValue(),
             angkatan: AngkatanField.getValue(),
             jenjang: JenjangField.getValue(),
             jenisKelamin : JenisKelaminField.getValue(),
             tempatLahir: TempatLahirField.getValue(),
             tanggalLahir: TanggalLahirField.getValue().format('m/d/Y'),
             jalan: JalanField.getValue(),
             //kelurahan: KelurahanField.getValue(),
             //kecamatan: KecamatanField.getValue(),
             subDistrik: SubDistrikField.getValue(),
             distrik: DistrikField.getValue(),
             kodepos: KodeposField.getValue(),
             telpon: TelponField.getValue(),
             handphone: HandphoneField.getValue(),
             email: EmailField.getValue(),
             namaAyah: NamaAyahField.getValue(),
             namaIbu: NamaIbuField.getValue(),
             alamatOrtu: AlamatOrtuField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Mahasiswa baru berhasil ditambahkan!');
            FakultasSearchField.setValue(FakultasField.getValue());
            MahasiswasDataStore.reload({params:{start:0, limit:25, fakultas:FakultasField.getValue()}});
            //MahasiswasDataStore.reload();
            MahasiswaCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','Nomor Induk Mahasiswa tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Mahasiswa baru gagal!');
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
  function resetMahasiswaForm(){
     NomorIndukField.setValue('');
     PasswordField.setValue('');
     NamaField.setValue('');
     FakultasField.setValue('');
     ProgdiField.setValue('');
     AngkatanField.setValue('');
     JenjangField.setValue('');
     JenisKelaminField.setValue('');
     TempatLahirField.setValue('');
     TanggalLahirField.setValue('');
     JalanField.setValue('');
     //KelurahanField.setValue('');
     //KecamatanField.setValue('');
     SubDistrikField.setValue('');
     DistrikField.setValue('');
     KodeposField.setValue('');
     TelponField.setValue('');
     HandphoneField.setValue('');
     EmailField.setValue('');
     NamaAyahField.setValue('');
     NamaIbuField.setValue('');
     AlamatOrtuField.setValue('');
  }

  // check if the form is valid
  function isMahasiswaFormValid(){
      return(NomorIndukField.isValid() && PasswordField.isValid() && NamaField.isValid() && FakultasField.isValid() && JenjangField.isValid() && JenisKelaminField.isValid()
            && ProgdiField.isValid() && AngkatanField.isValid() && JalanField.isValid() /*&& KelurahanField.isValid()*/
            && TempatLahirField.isValid() && TanggalLahirField.isValid() /*&& KecamatanField.isValid()*/ && SubDistrikField.isValid() && DistrikField.isValid()
            && KodeposField.isValid() && TelponField.isValid() && HandphoneField.isValid() && EmailField.isValid()
            && NamaAyahField.isValid() && NamaIbuField.isValid() && AlamatOrtuField.isValid()
        );
  }

  // display or bring forth the form
  function displayFormWindow(){
     if(!MahasiswaCreateWindow.isVisible()){
       resetMahasiswaForm();
       MahasiswaCreateWindow.show();
     } else {
       MahasiswaCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteMahasiswas(){
    if(MahasiswaListingEditorGrid.selModel.getCount() == 1) // only one Mahasiswa is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data mahasiswa. Lanjutkan?', deleteMahasiswas);
    } else if(MahasiswaListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+MahasiswaListingEditorGrid.selModel.getCount()+' data mahasiswa. Lanjutkan?', deleteMahasiswas);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Mahasiswa terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteMahasiswas(btn){
    if(btn=='yes'){
         var selections = MahasiswaListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< MahasiswaListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'dataMahasiswaJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                MahasiswasDataStore.reload({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue()}});
                break;
              case -1:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Sebagian data Mahasiswa yang Anda pilih tidak dapat dihapus! Pastikan data Mahasiswa yang Anda ingin hapus tidak dipakai di data Jadwal Mahasiswa!');
                MahasiswasDataStore.reload();
                break;
              case 0:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Seluruh data Mahasiswa yang Anda pilih tidak dapat dihapus! Pastikan data Mahasiswa yang Anda ingin hapus tidak dipakai di data Jadwal Mahasiswa!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Mahasiswa!');
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


  function confirmLogoutMahasiswas(){
    if(MahasiswaListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan me-logout 1 data Mahasiswa. Lanjutkan?', logoutMahasiswas);
    } else if(MahasiswaListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan me-logout '+MahasiswaListingEditorGrid.selModel.getCount()+' data Mahasiswa. Lanjutkan?', logoutMahasiswas);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Mahasiswa terlebih dahulu!');
    }
  }

  function logoutMahasiswas(btn){
    if(btn=='yes'){
         var selections = MahasiswaListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< MahasiswaListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'dataMahasiswaJSON',
            params: {
               task: "LOGOUT",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:
                MahasiswasDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue(), progdi:ProgdiSearchField.getValue()}});
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat me-logout semua data Mahasiswa!');
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
  MahasiswasDataStore = new Ext.data.Store({
      id: 'MahasiswasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMahasiswa',
        totalProperty: 'total'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'noInduk', type: 'string', mapping: 'noInduk'},
        {name: 'password', type: 'string', mapping: 'password'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'fakultas', type: 'int', mapping: 'fakultas'},
        {name: 'progdi', type: 'int', mapping: 'progdi'},
        {name: 'angkatan', type: 'int', mapping: 'angkatan'},
        {name: 'jenjang', type: 'string', mapping: 'jenjang'},
        {name: 'jenisKelamin', type: 'int', mapping: 'jenisKelamin'},
        {name: 'tempatLahir', type: 'string', mapping: 'tempatLahir'},
        {name: 'tanggalLahir', type: 'date', mapping: 'tanggalLahir', dateFormat: 'm/d/Y'},
        {name: 'jalan', type: 'string', mapping: 'jalan'},
        //{name: 'kelurahan', type: 'string', mapping: 'kelurahan'},
        //{name: 'kecamatan', type: 'string', mapping: 'kecamatan'},
        {name: 'subDistrik', type: 'string', mapping: 'subDistrik'},
        {name: 'distrik', type: 'int', mapping: 'distrik'},
        {name: 'kodepos', type: 'string', mapping: 'kodepos'},
        {name: 'telpon', type: 'string', mapping: 'telpon'},
        {name: 'handphone', type: 'string', mapping: 'handphone'},
        {name: 'email', type: 'string', mapping: 'email'},
        {name: 'namaAyah', type: 'string', mapping: 'namaAyah'},
        {name: 'namaIbu', type: 'string', mapping: 'namaIbu'},
        {name: 'alamatOrtu', type: 'string', mapping: 'alamatOrtu'},
        {name: 'isLogin', type: 'bool', mapping: 'isLogin'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var distrikStore = new Ext.data.Store({
      id: 'DistrikDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
                method: 'POST'
            }),
            baseParams:{task: "DISTRIK"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarDistrik'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var fakultasStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
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
      id: 'ProgdiDataStore',
      autoLoad: true,
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
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

  var progdiSearchStore = new Ext.data.Store({
      id: 'ProgdiDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
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

  var gridProgdiStore = new Ext.data.Store({
      id: 'ProgdiDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
                method: 'POST'
            }),
            baseParams:{task: "PROGDI"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarProgdi'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var angkatanStore = new Ext.data.Store({
      id: 'AngkatanDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dataMahasiswaJSON',
                method: 'POST'
            }),
            baseParams:{task: "ANGKATAN"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarAngkatan'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   var jenjangStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['Diploma','Diploma'],['Strata 1','Strata 1']]
    });

   var jenisKelaminStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['0','perempuan'],['1','laki-laki']]
    });

  distrikStore.load();
  fakultasStore.load();
  progdiStore.load({params: {fakultas: ''}});
  gridProgdiStore.load();
  angkatanStore.load();
  progdiSearchStore.load();

    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Is Login?',
       dataIndex: 'isLogin',
       width: 55
    });

  MahasiswasColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        readOnly: true,
        dataIndex: 'id',
        width: 30,
        hidden: true
      },{
        header: 'Nomor Induk',
        dataIndex: 'noInduk',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20
          })
      },{
        header: 'Password',
        dataIndex: 'password',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20
          })
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 200,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 200
          })
      },{
        header: 'Fakultas',
        dataIndex: 'fakultas',
        width: 250,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: fakultasStore,
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
      },{
        header: 'Program Studi',
        dataIndex: 'progdi',
        width: 250,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: gridProgdiStore,
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return gridProgdiStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Angkatan',
        dataIndex: 'angkatan',
        width: 250,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: angkatanStore,
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return angkatanStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Jenis Kelamin',
        dataIndex: 'jenisKelamin',
        width: 250,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: jenisKelaminStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return jenisKelaminStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Jenjang',
        dataIndex: 'jenjang',
        width: 250,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: jenjangStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return jenjangStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Tempat Lahir',
        dataIndex: 'tempatLahir',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 100,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
       header: 'Tanggal Lahir',
       dataIndex: 'tanggalLahir',
       width: 100,
       renderer: Ext.util.Format.dateRenderer('d/m/Y'),
       editor: new Ext.form.DateField({
            format: 'd/m/Y',
            disabledDays: [0, 6],
            disabledDaysText: 'Plants are not available on the weekends'
        }),
        hidden: false
      },{
        header: 'Jalan',
        dataIndex: 'jalan',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 255
          })
      /*},{
        header: 'Kelurahan',
        dataIndex: 'kelurahan',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 200,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Kecamatan',
        dataIndex: 'kecamatan',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 200,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })*/
      },{
        header: 'Sub Distrik',
        dataIndex: 'subDistrik',
        width: 150,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 100
          })
      },{
        header: 'Distrik',
        dataIndex: 'distrik',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: distrikStore,
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return distrikStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Kodepos',
        dataIndex: 'kodepos',
        width: 50,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 10
          })
      },{
        header: 'No. Telpon',
        dataIndex: 'telpon',
        width: 70,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 50
          })
      },{
        header: 'No. Handphone',
        dataIndex: 'handphone',
        width: 70,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 50
          })
      },{
        header: 'Email',
        dataIndex: 'email',
        vtype:'email',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 100
          })
      },{
        header: 'Nama Ayah',
        dataIndex: 'namaAyah',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 200
          })
      },{
        header: 'Nama Ibu',
        dataIndex: 'namaIbu',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 200
          })
      },{
        header: 'Alamat Orang Tua',
        dataIndex: 'alamatOrtu',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 255
          })
      }, checkColumn
     ]
    );
    MahasiswasColumnModel.defaultSortable= true;

  FakultasSearchField = new Ext.form.ComboBox({
     id:'FakultasSearchField',
     emptyText: 'Pilih Fakultas',
     store: fakultasStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            MahasiswasDataStore.load({params:{start:0, limit:25, fakultas:r.data.id}});
            //progdiSearchStore.filter('fakultas',FakultasSearchField.getValue(),true,false);
            progdiSearchStore.reload({params:{fakultas:r.data.id}});
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
            MahasiswasDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue(), progdi:r.data.id}});
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
                MahasiswaListingEditorGrid.store.filter('noInduk',NimSearchField.getValue(),true,false);
              }, c);
            }
          }
    });

  var NamaSearchField = new Ext.form.TextField({
        emptyText: 'Cari berdasarkan Nama',
        width:150,
        enableKeyEvents:true,
          listeners: {
            'render': function(c) {
              c.getEl().on('keyup', function() {
                MahasiswaListingEditorGrid.store.filter('nama',NamaSearchField.getValue(),true,false);
              }, c);
            }
          }
    });

   MahasiswaListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
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
          , '-',
          ProgdiSearchField
          , '-',
          NimSearchField
          , '-',
          NamaSearchField
          , '-', { // Added in Tutorial 6
            text: 'Tambah',
            tooltip: 'Tambah Data Mahasiswa',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Mahasiswa',
            handler: confirmDeleteMahasiswas,   // Confirm before deleting
            iconCls:'remove'
          }, '-', {
            text: 'Logout',
            tooltip: 'Logout Mahasiswa',
            handler: confirmLogoutMahasiswas,
            iconCls:'logout'
          }, '-', { 
            text: 'Print Mahasiswa',
            tooltip: 'Print Data Mahasiswa',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Print Surat Cuti',
            tooltip: 'Print Surat Cuti',
            handler: printSuratCuti,
            iconCls:'print'
          }, '-', {
            text: 'Print Surat Keterangan Kuliah',
            tooltip: 'Print Surat Keterangan Kuliah',
            handler: printSuratKeterangan,
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

    MahasiswasDataStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {fakultas: FakultasSearchField.getValue(), progdi:ProgdiSearchField.getValue()});
            }
        },
        'load':{
            fn: function(store, options){
                if(NimSearchField.getValue() != '')
                    MahasiswaListingEditorGrid.store.filter('noInduk',NimSearchField.getValue(),true,false);
                if(NamaSearchField.getValue() != '')
                    MahasiswaListingEditorGrid.store.filter('nama',NamaSearchField.getValue(),true,false);
            }
        }
    });

  MahasiswaListingWindow = new Ext.Window({
      id: 'MahasiswaListingWindow',
      title: 'Data Mahasiswa',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: MahasiswaListingEditorGrid
    });

  MahasiswaListingEditorGrid.render("grid");
  MahasiswasDataStore.load({params:{start:0, limit:25}});
  MahasiswaListingEditorGrid.on('afteredit', saveTheMahasiswa);

  NomorIndukField = new Ext.form.TextField({
    id: 'NomorIndukField',
    fieldLabel: 'Nomor Induk',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%'
      });

  PasswordField = new Ext.form.TextField({
    id: 'PasswordField',
    fieldLabel: 'Password',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    password:true
      });

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 200,
    allowBlank: false,
    anchor : '95%'
      });

  FakultasField = new Ext.form.ComboBox({
     id:'FakultasField',
     fieldLabel: 'Fakultas',
     store: fakultasStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     mode: 'local',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            progdiStore.reload({params:{fakultas: r.data.id}});
            //progdiStore.filter('fakultas',FakultasField.getValue(),true,false);
        }
     }
  });

  ProgdiField = new Ext.form.ComboBox({
     id:'ProgdiField',
     fieldLabel: 'Program Studi',
     store: progdiStore,
     forceSelection : true,
     displayField: 'nama',
     allowBlank: false,
     mode: 'local',
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  AngkatanField = new Ext.form.ComboBox({
     id:'AngkatanField',
     fieldLabel: 'Angkatan',
     store: angkatanStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  JenjangField = new Ext.form.ComboBox({
     id:'JenjangField',
     fieldLabel: 'Jenjang',
     store: jenjangStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  TempatLahirField = new Ext.form.TextField({
    id: 'TempatLahirField',
    fieldLabel: 'Tempat Lahir',
    maxLength: 100,
    allowBlank: false,
    anchor : '95%'
      });

  TanggalLahirField = new Ext.form.DateField({
    id: 'TanggalLahirField',
    fieldLabel: 'Tanggal Lahir',
    allowBlank: false,
    format: 'd/m/Y',
    maxLength: 20
      });

  JenisKelaminField = new Ext.form.ComboBox({
     id:'JenisKelaminField',
     fieldLabel: 'Jenis Kelamin',
     store: jenisKelaminStore,
     width: 10,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  JalanField = new Ext.form.TextField({
    id: 'JalanField',
    fieldLabel: 'Jalan',
    maxLength: 255,
    anchor : '95%'
      });

  /*KelurahanField = new Ext.form.TextField({
    id: 'KelurahanField',
    fieldLabel: 'Kelurahan',
    maxLength: 200,
    //allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  KecamatanField = new Ext.form.TextField({
    id: 'KecamatanField',
    fieldLabel: 'Kecamatan',
    maxLength: 200,
    //allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });*/

  SubDistrikField = new Ext.form.TextField({
    id: 'SubDistrikField',
    fieldLabel: 'Sub Distrik',
    maxLength: 100,
    anchor : '95%'
      });

  DistrikField = new Ext.form.ComboBox({
     id:'DistrikField',
     fieldLabel: 'Distrik',
     store: distrikStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  KodeposField = new Ext.form.TextField({
    id: 'KodeposField',
    fieldLabel: 'Kodepos',
    maxLength: 10,
    anchor : '95%'
      });

  TelponField = new Ext.form.TextField({
    id: 'TelponField',
    fieldLabel: 'Telpon',
    maxLength: 50,
    anchor : '95%'
      });

  HandphoneField = new Ext.form.TextField({
    id: 'HandphoneField',
    fieldLabel: 'Handphone',
    maxLength: 50,
    anchor : '95%'
      });

  EmailField = new Ext.form.TextField({
    id: 'EmailField',
    fieldLabel: 'Email',
    maxLength: 100,
    anchor : '95%',
    vtype: 'email'
  });

  NamaAyahField = new Ext.form.TextField({
    id: 'NamaAyahField',
    fieldLabel: 'Nama Ayah',
    maxLength: 200,
    allowBlank: false,
    anchor : '95%'
      });

  NamaIbuField = new Ext.form.TextField({
    id: 'NamaIbuField',
    fieldLabel: 'Nama Ibu',
    maxLength: 200,
    allowBlank: false,
    anchor : '95%'
      });

  AlamatOrtuField = new Ext.form.TextField({
    id: 'AlamatOrtuField',
    fieldLabel: 'Alamat Orang Tua',
    maxLength: 255,
    anchor : '95%'
  });

  var fieldset1 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Mahasiswa",
      autoHeight: true,
      collapsible: true,
      layout: 'form',
      items:[
              NomorIndukField,
              PasswordField,
              NamaField,
              TempatLahirField,
              TanggalLahirField,
              JenisKelaminField
      ]
  });

  var fieldset2 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Alamat Mahasiswa",
      autoHeight: true,
      collapsible: true,
      layout: 'form',
      items:[
              JalanField,
              /*KelurahanField,
              KecamatanField,*/
              SubDistrikField,
              DistrikField,
              KodeposField,
              TelponField,
              HandphoneField,
              EmailField
      ]
  });

  var fieldset3 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Orang Tua",
      autoHeight: true,
      collapsible: true,
      layout: 'form',
      items:[
              NamaAyahField,
              NamaIbuField,
              AlamatOrtuField
      ]
  });

  var fieldset4 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Akademis",
      autoHeight: true,
      collapsible: true,
      layout: 'form',
      items:[
              FakultasField,
              ProgdiField,
              AngkatanField,
              JenjangField
      ]
  });

  MahasiswaCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:10px 10px 10px 10px',
        width: 800,
        autoScroll: true,
        //height: 600,
        items: [{
            layout:'column',
            border:false,
            items:[{
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [fieldset1, fieldset4]
            },{
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [fieldset2, fieldset3]
            }]
        }],
    buttons: [{
      text: 'Simpan dan Tutup',
      handler: createTheMahasiswa
    },{
      text: 'Batal',
      handler: function(){
        // because of the global vars, we can only instantiate one window... so let's just hide it.
        MahasiswaCreateWindow.hide();
      }
    }]
    });

  MahasiswaCreateWindow= new Ext.Window({
      id: 'MahasiswaCreateWindow',
      title: 'Tambahkan data Mahasiswa baru',
      closable:false,
      iconCls : 'form',
      width: 800,
      height: 600,
      plain:true,
      layout: 'fit',
      items: MahasiswaCreateForm
    });
    
  var teks = 'Untuk menambahkan data, klik button Tambah.<br>' +
             'Untuk menghapus data, klik row (boleh multiple) yg akan dihapus dan klik button Hapus.<br>' +
             'Untuk mengubah data, klik pada cell yang ingin diubah. Setelah melakukan perubahan data, tekan enter atau klik di luar cell.<br>' +
             'Untuk melakukan pencarian berdasarkan Fakultas, gunakan drop down Fakultas di sebelah atas tabel.<br>' +
             'Untuk melakukan pencarian berdasarkan Nomor Induk atau Nama, ketikkan pada subDistrikk yang tersedia di sebelah atas tabel.';
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
        window.open('printMahasiswa?idFakultas=' + FakultasSearchField.getValue(), '_blank', '');
    }

    function printSuratKeterangan(){
        if(MahasiswaListingEditorGrid.selModel.getCount() == 1) // only one Mahasiswa is selected here
        {
            window.open('printKeterangan?idMahasiswa=' + MahasiswaListingEditorGrid.selModel.getSelections()[0].data.id, '_blank', '');
        } else {
            Ext.MessageBox.alert('Peringatan','Anda harus memilih 1 data Mahasiswa!');
        }
    }

       var semesterStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['1','1'], ['2','2']]
        });
  
      var NamaSemesterField = new Ext.form.ComboBox({
         id:'NamaSemesterField',
         fieldLabel: 'Semester',
         store: semesterStore,
         mode: 'local',
         displayField: 'id',
         allowBlank: false,
         valueField: 'nama',
         anchor:'95%',
         triggerAction: 'all'
      });

      var TahunAjaranField = new Ext.form.TextField({
        id: 'TahunAjaranField',
        fieldLabel: 'Tahun Ajaran',
        maxLength: 20,
        allowBlank: false,
        anchor : '95%',
        tooltip: 'misal 2007/2008'
          });

      var SemesterForm = new Ext.FormPanel({
            labelAlign: 'top',
            bodyStyle:'padding:5px',
            width: 300,
            items: [NamaSemesterField, TahunAjaranField],
            buttons: [{
              text: 'Simpan dan Tutup',
              handler: function(){
                  if(NamaSemesterField.getValue() == '' || TahunAjaranField.getValue() == '')
                      Ext.MessageBox.alert('Peringatan','Isi dahulu data Semester kapan Mahasiswa akan mengambil cuti!');
                  else{
                      window.open('printCuti?idMahasiswa=' + MahasiswaListingEditorGrid.selModel.getSelections()[0].data.id + '&semester=' + NamaSemesterField.getValue() + '&tahun=' + TahunAjaranField.getValue(), '_blank', '');
                      SemesterWindow.hide();
                  }
              }
            },{
              text: 'Batal',
              handler: function(){
                // because of the global vars, we can only instantiate one window... so let's just hide it.
                SemesterWindow.hide();
                NamaSemesterField.setValue('');
                TahunAjaranField.setValue('');
              }
            }]
        });

      var SemesterWindow= new Ext.Window({
          id: 'SemesterCreateWindow',
          title: 'Masukkan data Semester kapan Mahasiswa mengambil cuti',
          closable:false,
          iconCls : 'form',
          width: 310,
          height: 250,
          plain:true,
          layout: 'fit',
          items: SemesterForm
        });

    function printSuratCuti() {
        if(MahasiswaListingEditorGrid.selModel.getCount() == 1) // only one Mahasiswa is selected here
        {
             if(!SemesterWindow.isVisible()){
               //resetMahasiswaForm();
               SemesterWindow.show();
             } else {
               SemesterWindow.toFront();
             }
        } else {
            Ext.MessageBox.alert('Peringatan','Anda harus memilih 1 data Mahasiswa!');
        }
    }
});



Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td';
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'"> </div>';
    }
};