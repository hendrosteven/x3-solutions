var valid = false;

//validation vtype
Ext.apply(Ext.form.VTypes, {
    password :
        function(val, field) {
            if (field.initialPassField) {
                var password = Ext.getCmp(field.initialPassField);
                /*if(val == password.getValue())
                    valid = true;
                else
                    valid = false;*/
                return (val == password.getValue());
            }
        return true;
        },
    passwordText : 'Password tidak sama!!' //alert if you enter a password that is not the same
});

Ext.onReady(function(){
    Ext.QuickTips.init();

  var OldPassField = new Ext.form.TextField({
    id: 'OldPassField',
    fieldLabel: 'Password Lama',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    inputType : 'password',
    listeners: {
        change: cekOldPassword
    }
    //vtype: 'password'
      });

  var NewPassField = new Ext.form.TextField({
    id: 'NewPassField',
    fieldLabel: 'Password Baru',
    name: 'password',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    inputType : 'password'
      });

  var ConfNewPassField = new Ext.form.TextField({
    id: 'ConfNewPassField',
    fieldLabel: 'Konfirmasi Password Baru',
    name:'password1',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    inputType : 'password',
    vtype: 'password',
    initialPassField: 'NewPassField'
      });

    var password = new Ext.FormPanel({
        title: 'Ganti Password',
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        //labelWidth:90,
        //url:'mahasiswaJSONListAction',
        frame:true,
        width:320,
        autoHeight:true,
        //padding:200,
        //defaultType:'textfield',

        items:[OldPassField, NewPassField, ConfNewPassField],

        buttons:[{
            text:'Ganti',

            handler:function(){
                if(OldPassField.getValue() == '' || NewPassField.getValue() == '' || ConfNewPassField.getValue() == ''){
                    Ext.Msg.alert('Warning!', 'Isi Password Lama dan Password Baru dulu!');
                }
                else if(isValid()){
                   Ext.Msg.alert('Status', 'sini!');
                   Ext.Ajax.request({
                      waitMsg: 'Harap tunggu...',
                      url: 'gantiPassword',
                      params: {
                         task: "PASSWORD",
                         password: NewPassField.getValue()
                         //biaya: oGrid_event.record.data.biaya
                      },
                      success: function()
                      {
                            Ext.Msg.alert('Status', 'Pengubahan Password sukses!');
                      },
                      failure: function(response){
                         var result=response.responseText;
                         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
                      }
                   });
                }
                else{
                    Ext.Msg.alert('Warning!', 'Password Lama salah atau Konfirmasi Password baru tidak sama!!');
                }
                }
            },
            {
                text: 'Reset',
                handler: function(){
                    password.getForm().reset();
                }
            }]
    });

    password.render('password');
    /*var createwindow = new Ext.Window({
    frame:true,
    title:'Simple Form Login',
    width:330,
    height:250,
    closable: false,
    items: login
    });

    createwindow.show();*/

    function isValid() {
        return(valid && OldPassField.isValid() && NewPassField.isValid() && ConfNewPassField.isValid());
    }
    function cekOldPassword() {
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'gantiPassword',
          params: {
             task: "CEK_PASS_LAMA",
             oldPass: OldPassField.getValue()
          },
          success: function(response){
             var result=eval(response.responseText);
             switch(result){
             case 1:
                Ext.MessageBox.alert('', 'Password lama benar!');
                valid = true;
                break;
             case 2:
                Ext.MessageBox.alert('', 'Password lama salah!');
                valid = false;
                break;
             default:
                Ext.MessageBox.alert('', 'Pengecekan data gagal!');
                break;
             }
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
    }
});
