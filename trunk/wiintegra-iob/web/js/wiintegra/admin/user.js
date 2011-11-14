// Global vars
var UsersDataStore;
var UsersColumnModel;
var UserListingEditorGrid;
var UserListingWindow;
// Our new vars
var UserCreateForm;
var UserCreateWindow;
var UserNameField;
var PasswordField;
var RealNameField;
var LevelField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // untuk mengubah data user
  function saveTheUser(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'userJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         userName: oGrid_event.record.data.userName,
         password: oGrid_event.record.data.password,
         realName: oGrid_event.record.data.realName,
         level   : oGrid_event.record.data.level,
         isLogin : oGrid_event.record.data.isLogin
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            UsersDataStore.commitChanges();
            UsersDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'User ID sudah ada!');
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

  // untuk menambah data user baru
  function createTheUser(){
     if(isUserFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'userJSON',
        params: {
          task: "CREATE",
          userName:      UserNameField.getValue(),
          password:      PasswordField.getValue(),
          realName:      RealNameField.getValue(),
          level:         LevelField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data User baru berhasil ditambahkan!');
            UsersDataStore.reload();
            UserCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','User ID tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data User baru gagal!');
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

  function resetUserForm(){
    UserNameField.setValue('');
    PasswordField.setValue('');
    RealNameField.setValue('');
    LevelField.setValue('');
  }

  function isUserFormValid(){
      return(UserNameField.isValid() && PasswordField.isValid() && RealNameField.isValid() && LevelField.isValid());
  }

  function displayFormWindow(){
     if(!UserCreateWindow.isVisible()){
       resetUserForm();
       UserCreateWindow.show();
     } else {
       UserCreateWindow.toFront();
     }
  }

  function confirmDeleteUsers(){
    if(UserListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data user. Lanjutkan?', deleteUsers);
    } else if(UserListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+UserListingEditorGrid.selModel.getCount()+' data user. Lanjutkan?', deleteUsers);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data User terlebih dahulu!');
    }
  }

  function deleteUsers(btn){
    if(btn=='yes'){
         var selections = UserListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< UserListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'userJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  
                UsersDataStore.reload();
                break;
              case 0:  
                Ext.MessageBox.alert('Peringatan','Tidak diijinkan menghapus data User!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data User!');
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

  function confirmLogoutUsers(){
    if(UserListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan me-logout 1 data user. Lanjutkan?', logoutUsers);
    } else if(UserListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan me-logout '+UserListingEditorGrid.selModel.getCount()+' data user. Lanjutkan?', logoutUsers);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data User terlebih dahulu!');
    }
  }

  function logoutUsers(btn){
    if(btn=='yes'){
         var selections = UserListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< UserListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'userJSON',
            params: {
               task: "LOGOUT",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  
                UsersDataStore.reload();
                break;
              case -1:  
                Ext.MessageBox.alert('Error','Anda tidak dapat me-logout account Anda sendiri!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat me-logout semua data User!');
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

  UsersDataStore = new Ext.data.Store({
      id: 'UsersDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'userJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"},
      reader: new Ext.data.JsonReader({
        root: 'daftarUser',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'userName', type: 'string', mapping: 'userName'},
        {name: 'password', type: 'string', mapping: 'password'},
        {name: 'realName', type: 'string', mapping: 'realName'},
        {name: 'level', type: 'int', mapping: 'level'},
        {name: 'lastLogin', type: 'string', mapping: 'lastLogin'},
        {name: 'isLogin', type: 'bool', mapping: 'isLogin'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   var levelStore = new Ext.data.SimpleStore({
    fields:['levelValue', 'levelName'],
    data: [['2','Akademik'],['1','Administrator']]
    });
    
    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Is Login?',
       dataIndex: 'isLogin',
       width: 55
    });

  UsersColumnModel = new Ext.grid.ColumnModel(
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
        header: 'User ID',
        dataIndex: 'userName',
        width: 150,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20
          })
      },{
        header: 'Password',
        dataIndex: 'password',
        width: 150,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20
          })
      },{
        header: 'Nama',
        dataIndex: 'realName',
        width: 300,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 150
          })
      },{
        header: 'Level',
        dataIndex: 'level',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: levelStore,
               mode: 'local',
               displayField: 'levelName',
               valueField: 'levelValue',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return levelStore.queryBy(function(rec){
            return rec.data.levelValue == val;
            }).itemAt(0).data.levelName;
            }
      },{
        header: 'Last Login',
        dataIndex: 'lastLogin',
        width: 250,
        renderer: function(value, cell){
         cell.css = "readonlycell";
         return value;
        }
      }, checkColumn
     ]
    );
    UsersColumnModel.defaultSortable= true;

   UserListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:1100,
      height:480,
      title:'Data User',
      frame:true,
      id: 'UserListingEditorGrid',
      store: UsersDataStore,
      iconCls : 'grid',
      cm: UsersColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data User',
            iconCls:'add',                      
            handler: displayFormWindow
          }, '-', { 
            text: 'Hapus',
            tooltip: 'Hapus Data User',
            handler: confirmDeleteUsers,   
            iconCls:'remove'
          }, '-', { 
            text: 'Logout',
            tooltip: 'Logout User',
            handler: confirmLogoutUsers,   
            iconCls:'logout'
          }, '-', { 
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   
            iconCls:'help'
      }]
    });

  UserListingWindow = new Ext.Window({
      id: 'UserListingWindow',
      title: 'Data User',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: UserListingEditorGrid
    });

  UserListingEditorGrid.render("grid");
  UsersDataStore.load({params:{start:0, limit:25}});
  UserListingEditorGrid.on('afteredit', saveTheUser);

  UserNameField = new Ext.form.TextField({
    id: 'UserNameField',
    fieldLabel: 'User ID',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  PasswordField = new Ext.form.TextField({
    id: 'PasswordField',
    fieldLabel: 'Password',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    password:true,
    maskRe: /([a-zA-Z0-9\s]+)$/
      });
      
  RealNameField = new Ext.form.TextField({
    id: 'RealNameField',
    fieldLabel: 'RealName',
    maxLength: 150,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  LevelField = new Ext.form.ComboBox({
     id:'LevelField',
     fieldLabel: 'Level',
     store: levelStore,
     mode: 'local',
     displayField: 'levelName',
     allowBlank: false,
     valueField: 'levelValue',
     anchor:'95%',
     triggerAction: 'all'
      });

  UserCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [UserNameField, PasswordField, RealNameField, LevelField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheUser
        },{
          text: 'Batal',
          handler: function(){
            UserCreateWindow.hide();
          }
        }]
    });

  UserCreateWindow= new Ext.Window({
      id: 'UserCreateWindow',
      title: 'Tambahkan data User baru',
      closable:false,
      iconCls : 'form',
      width: 300,
      height: 300,
      plain:true,
      layout: 'fit',
      items: UserCreateForm
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