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

  // This saves the Dosen after a cell has been edited
  function saveTheDosen(){
     if(isDosenFormValid()){
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'profilDosenJSON',
          params: {
             task: "UPDATE",
             userName: UserNameField.getValue(),
             password: PasswordField.getValue(),
             nama: NamaField.getValue(),
             alamat: AlamatField.getValue(),
             subDistrik: SubDistrikField.getValue(),
             distrik: DistrikField.getValue(),
             kodepos: KodeposField.getValue(),
             telpon: TelponField.getValue(),
             handphone: HandphoneField.getValue(),
             email: EmailField.getValue()
          },
          success: function(response){
             var result=eval(response.responseText);
             switch(result){
             case 1:
                Ext.MessageBox.alert('Sukses', 'Pengubahan data berhasil!');
                DosensDataStore.reload();
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
     else{
         Ext.MessageBox.alert('Error','Isi data dosen dengan benar!');
     }
  }

  // this creates a new Dosen
  function fillTextboxValue(){
     UserNameField.setValue(DosensDataStore.getAt(0).data.userName);
     PasswordField.setValue(DosensDataStore.getAt(0).data.password);
     NamaField.setValue(DosensDataStore.getAt(0).data.nama);
     AlamatField.setValue(DosensDataStore.getAt(0).data.alamat);
     SubDistrikField.setValue(DosensDataStore.getAt(0).data.subDistrik);
     DistrikField.setValue(DosensDataStore.getAt(0).data.distrik);
     KodeposField.setValue(DosensDataStore.getAt(0).data.kodepos);
     TelponField.setValue(DosensDataStore.getAt(0).data.telpon);
     HandphoneField.setValue(DosensDataStore.getAt(0).data.handphone);
     EmailField.setValue(DosensDataStore.getAt(0).data.email);
  }

  // reset the Form before opening it
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
  }

  // check if the form is valid
  function isDosenFormValid(){
      return(UserNameField.isValid() && PasswordField.isValid() && NamaField.isValid() && AlamatField.isValid()
            && SubDistrikField.isValid() && DistrikField.isValid()
            && KodeposField.isValid() && TelponField.isValid() && HandphoneField.isValid() && EmailField.isValid());
  }

  var distrikStore = new Ext.data.Store({
      id: 'DistrikDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'profilDosenJSON',
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

  distrikStore.load();
  // << CONFIG >>
  DosensDataStore = new Ext.data.Store({
      id: 'DosensDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'profilDosenJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarDosen'
        //totalProperty: 'total',
        //id: 'id'
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
        {name: 'email', type: 'string', mapping: 'email'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  DosensDataStore.on('load', fillTextboxValue);

  UserNameField = new Ext.form.TextField({
    id: 'UserNameField',
    fieldLabel: 'Nomor Pegawai',
    maxLength: 20,
    allowBlank: false,
    readOnly: true
      });

  PasswordField = new Ext.form.TextField({
    id: 'PasswordField',
    fieldLabel: 'Password',
    maxLength: 20,
    allowBlank: false,
    password:true
      });

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 20,
    allowBlank: false
      });

  AlamatField = new Ext.form.TextField({
    id: 'AlamatField',
    fieldLabel: 'Alamat',
    maxLength: 20
      });

  SubDistrikField = new Ext.form.TextField({
    id: 'SubDistrikField',
    fieldLabel: 'Sub Distrik',
    maxLength: 20
      });

  DistrikField = new Ext.form.ComboBox({
     id:'DistrikField',
     fieldLabel: 'Distrik',
     store: distrikStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     triggerAction: 'all'
      });

  KodeposField = new Ext.form.TextField({
    id: 'KodeposField',
    fieldLabel: 'Kodepos',
    maxLength: 20
      });

  TelponField = new Ext.form.TextField({
    id: 'TelponField',
    fieldLabel: 'Telpon',
    maxLength: 20
      });

  HandphoneField = new Ext.form.TextField({
    id: 'HandphoneField',
    fieldLabel: 'Handphone',
    maxLength: 20
      });

  EmailField = new Ext.form.TextField({
    id: 'EmailField',
    fieldLabel: 'Email',
    maxLength: 20,
    vtype: 'email'
      });

  var fieldset1 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Dosen",
      autoHeight: true,
      collapsible: true,
      defaults: {width: 210},
      items:[
              UserNameField,
              NamaField,
              PasswordField
      ]
  });

  var fieldset2 = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Alamat",
      autoHeight: true,
      collapsible: true,
      defaults: {width: 210},
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
        title: 'Data Dosen',
        labelWidth: 90, // label settings here cascade unless overridden
        bodyStyle:'padding:5px 5px 0',
        width: 350,
        frame:true,
        iconCls : 'form',
        autoScroll: true,
        items: [fieldset1, fieldset2],
        buttons: [{
          text: 'Update',
          handler: saveTheDosen
        }]
    });
  DosensDataStore.load();
  DosenCreateForm.render("form");
});