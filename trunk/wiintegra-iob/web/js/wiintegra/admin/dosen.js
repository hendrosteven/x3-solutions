// Global vars
var DosensDataStore;
var DosensColumnModel;
var DosenListingEditorGrid;
var DosenListingWindow;
// Our new vars
var DosenCreateForm;
var DosenCreateWindow;
var UserNameField;
var NamaField;
var PasswordField;
var AlamatField;
var SubDistrikField;
var DistrikField;
var KodeposField;
var TelponField;
var HandphoneField;
var EmailField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  function saveTheDosen(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'dosenJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         userName: oGrid_event.record.data.userName,
         password: oGrid_event.record.data.password,
         nama: oGrid_event.record.data.nama,
         alamat: oGrid_event.record.data.alamat,
         subDistrik: oGrid_event.record.data.subDistrik,
         distrik: oGrid_event.record.data.distrik,
         kodepos: oGrid_event.record.data.kodepos,
         telpon: oGrid_event.record.data.telpon,
         handphone: oGrid_event.record.data.handphone,
         email: oGrid_event.record.data.email,
         jenisKelamin: oGrid_event.record.data.jenisKelamin,
         pendidikanTerakhir: oGrid_event.record.data.pendidikanTerakhir,
         isLogin : oGrid_event.record.data.isLogin
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            DosensDataStore.commitChanges();
            DosensDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'Nomor Pegawai sudah ada!');
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

  function createTheDosen(){
     if(isDosenFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'dosenJSON',
        params: {
          task: "CREATE",
         userName: UserNameField.getValue(),
         password: PasswordField.getValue(),
         nama: NamaField.getValue(),
         alamat: AlamatField.getValue(),
         subDistrik: SubDistrikField.getValue(),
         distrik: DistrikField.getValue(),
         kodepos: KodeposField.getValue(),
         telpon: TelponField.getValue(),
         handphone: HandphoneField.getValue(),
         email: EmailField.getValue(),
         jenisKelamin: JenisKelaminField.getValue(),
         pendidikanTerakhir: PendidikanTerakhirField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Dosen baru berhasil ditambahkan!');
            DosensDataStore.reload();
            DosenCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','Nomor Pegawai tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Dosen baru gagal!');
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

  function resetDosenForm(){
     UserNameField.setValue('');
     PasswordField.setValue('');
     NamaField.setValue('');
     AlamatField.setValue('');
     SubDistrikField.setValue('');
     DistrikField.setValue('');
     KodeposField.setValue('');
     TelponField.setValue('');
     HandphoneField.setValue('');
     EmailField.setValue('');
     JenisKelaminField.setValue('');
     PendidikanTerakhirField.setValue('');
  }

  function isDosenFormValid(){
      return(UserNameField.isValid() && PasswordField.isValid() && NamaField.isValid() && AlamatField.isValid()
            && SubDistrikField.isValid() && DistrikField.isValid()
            && KodeposField.isValid() && TelponField.isValid() && HandphoneField.isValid() && EmailField.isValid()
            && JenisKelaminField.isValid() && PendidikanTerakhirField.isValid()
        );
  }

  function displayFormWindow(){
     if(!DosenCreateWindow.isVisible()){
       resetDosenForm();
       DosenCreateWindow.show();
     } else {
       DosenCreateWindow.toFront();
     }
  }

  function confirmDeleteDosens(){
    if(DosenListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data dosen. Lanjutkan?', deleteDosens);
    } else if(DosenListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+DosenListingEditorGrid.selModel.getCount()+' data dosen. Lanjutkan?', deleteDosens);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Dosen terlebih dahulu!');
    }
  }

  function deleteDosens(btn){
    if(btn=='yes'){
         var selections = DosenListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< DosenListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'dosenJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1: 
                DosensDataStore.reload();
                break;
              case -1:
                Ext.MessageBox.alert('Peringatan','Sebagian data Dosen yang Anda pilih tidak dapat dihapus! Pastikan data Dosen yang Anda ingin hapus tidak dipakai di data Jadwal!');
                DosensDataStore.reload();
                break;
              case 0:
                Ext.MessageBox.alert('Peringatan','Seluruh data Dosen yang Anda pilih tidak dapat dihapus! Pastikan data Dosen yang Anda ingin hapus tidak dipakai di data Jadwal!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Dosen!');
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

  function confirmLogoutDosens(){
    if(DosenListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan me-logout 1 data Dosen. Lanjutkan?', logoutDosens);
    } else if(DosenListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan me-logout '+DosenListingEditorGrid.selModel.getCount()+' data user. Lanjutkan?', logoutDosens);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Dosen terlebih dahulu!');
    }
  }

  function logoutDosens(btn){
    if(btn=='yes'){
         var selections = DosenListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< DosenListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'dosenJSON',
            params: {
               task: "LOGOUT",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:
                DosensDataStore.reload();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat me-logout semua data Dosen!');
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

  DosensDataStore = new Ext.data.Store({
      id: 'DosensDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dosenJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_LIMIT"},
      reader: new Ext.data.JsonReader({
        root: 'daftarDosen',
        totalProperty: 'total'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'userName', type: 'string', mapping: 'userName'},
        {name: 'password', type: 'string', mapping: 'password'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'alamat', type: 'string', mapping: 'alamat'},
        {name: 'subDistrik', type: 'string', mapping: 'subDistrik'},
        {name: 'distrik', type: 'int', mapping: 'distrik'},
        {name: 'kodepos', type: 'string', mapping: 'kodepos'},
        {name: 'telpon', type: 'string', mapping: 'telpon'},
        {name: 'handphone', type: 'string', mapping: 'handphone'},
        {name: 'email', type: 'string', mapping: 'email'},
        {name: 'jenisKelamin', type: 'int', mapping: 'jenisKelamin'},
        {name: 'pendidikanTerakhir', type: 'string', mapping: 'pendidikanTerakhir'},
        {name: 'isLogin', type: 'bool', mapping: 'isLogin'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var distrikStore = new Ext.data.Store({
      id: 'DistrikDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'dosenJSON',
                method: 'POST'
            }),
            baseParams:{task: "DISTRIK"},
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarDistrik'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   var jenisKelaminStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['0','perempuan'],['1','laki-laki']]
    });

   var pendidikanTerakhirStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['Sarjana','Sarjana'],['Master','Master'],['Doktor','Doktor']]
    });


  distrikStore.load();

    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Is Login?',
       dataIndex: 'isLogin',
       width: 55
    });

  DosensColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        readOnly: true,
        dataIndex: 'id',
        width: 30,
        hidden: true
      },{
        header: 'Nomor Pegawai',
        dataIndex: 'userName',
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
        header: 'Jenis Kelamin',
        dataIndex: 'jenisKelamin',
        width: 150,
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
        header: 'Pendidikan Terakhir',
        dataIndex: 'pendidikanTerakhir',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: pendidikanTerakhirStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return pendidikanTerakhirStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Alamat',
        dataIndex: 'alamat',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 255
          })
      },{
        header: 'SubDistrik',
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
               mode: 'local',
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
      }, checkColumn]
    );
    DosensColumnModel.defaultSortable= true;

   DosenListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:1150,
      height:480,
      title:'Data Dosen',
      frame:true,
      id: 'DosenListingEditorGrid',
      autoScroll: true,
      store: DosensDataStore,
      iconCls : 'grid',
      cm: DosensColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Dosen',
            iconCls:'add',                      
            handler: displayFormWindow
          }, '-', { 
            text: 'Hapus',
            tooltip: 'Hapus Data Dosen',
            handler: confirmDeleteDosens,  
            iconCls:'remove'
          }, '-', {
            text: 'Logout',
            tooltip: 'Logout Dosen',
            handler: confirmLogoutDosens, 
            iconCls:'logout'
          }, '-', { 
            text: 'Print',
            tooltip: 'Print Data Dosen',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow, 
            iconCls:'help'
      }],
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: DosensDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Dosen {0} - {1} of {2}',
        emptyMsg: "Data Dosen kosong!"
        })
    });

  DosenListingWindow = new Ext.Window({
      id: 'DosenListingWindow',
      title: 'Data Dosen',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: DosenListingEditorGrid
    });

  DosenListingEditorGrid.render("grid");
  DosensDataStore.load({params:{start:0, limit:25}});
  DosenListingEditorGrid.on('afteredit', saveTheDosen);

  UserNameField = new Ext.form.TextField({
    id: 'UserNameField',
    fieldLabel: 'Nomor Pegawai',
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

  JenisKelaminField = new Ext.form.ComboBox({
     id:'JenisKelaminField',
     fieldLabel: 'Jenis Kelamin',
     store: jenisKelaminStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  PendidikanTerakhirField = new Ext.form.ComboBox({
     id:'PendidikanTerakhirField',
     fieldLabel: 'Pendidikan Terakhir',
     store: pendidikanTerakhirStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  AlamatField = new Ext.form.TextField({
    id: 'AlamatField',
    fieldLabel: 'Alamat',
    maxLength: 255,
    anchor : '95%'
      });

  SubDistrikField = new Ext.form.TextField({
    id: 'SubDistrikField',
    fieldLabel: 'SubDistrik',
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

  var fieldset1 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Pribadi",
      autoHeight: true,
      collapsible: true,
      layout: 'form',
      items:[
              UserNameField,
              PasswordField,
              NamaField,
              JenisKelaminField,
              PendidikanTerakhirField
      ]
  });

  var fieldset2 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Alamat",
      autoHeight: true,
      collapsible: true,
      layout: 'form',
      items:[
              AlamatField,
              SubDistrikField,
              DistrikField,
              KodeposField,
              TelponField,
              HandphoneField,
              EmailField
      ]
  });

  DosenCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        autoScroll: true,
        items: [fieldset1, fieldset2],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheDosen
        },{
          text: 'Batal',
          handler: function(){
            DosenCreateWindow.hide();
          }
        }]
    });

  DosenCreateWindow= new Ext.Window({
      id: 'DosenCreateWindow',
      title: 'Tambahkan data Dosen baru',
      closable:false,
      iconCls : 'form',
      width: 300,
      height: 600,
      plain:true,
      layout: 'fit',
      items: DosenCreateForm
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

    function print(){
        window.open('printDosen', '_blank', '');
    }

    function hideHelpWindow() {
        HelpWindow.hide();
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