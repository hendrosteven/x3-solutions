var NilaisDataStore;
var NilaisColumnModel;
var NilaiListingEditorGrid;

var NomorIndukField;
var NamaField;
var MatkulField;
var StatusField;
var NilaiField;
var DataMahasiswaForm;
var MatkulDataStore;
var namaFakultas;
var semester;

var valid = false;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // ------------------------ tabel jadwal -------------------------------------
  NilaisDataStore = new Ext.data.Store({
      id: 'NilaisDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'nilaiJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarNilai'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'status', type: 'string', mapping: 'status'},
        {name: 'nilai', type: 'string', mapping: 'nilai'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   var nilaiStore = new Ext.data.SimpleStore({
        fields:['id', 'nama'],
        data: [['A','A'],['B','B'],['C','C'],['D','D'],['E','E']]
    });

   var statusStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['B','B'], ['U','U'], ['P','P']]
    });

  NilaisColumnModel = new Ext.grid.ColumnModel(
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
        width: 100
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 300
      },{
        header: 'B/U/P',
        dataIndex: 'status',
        width: 50,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: statusStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
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
      }]
    );
    NilaisColumnModel.defaultSortable= true;

   NilaiListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:655,
      height:445,
      //title:'Data Matakuliah',
      frame:true,
      id: 'NilaiListingEditorGrid',
      store: NilaisDataStore,
      viewConfig: {
          forceFit:true
      },
      cm: NilaisColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            /*text: 'Tambah',
            tooltip: 'Tambah Data Nilai Matakuliah',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Nilai Matakuliah',
            handler: confirmDeleteNilais,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6*/
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }]
    });

    NilaiListingEditorGrid.on('afteredit', saveTheNilai);
   //---------------------------------------------------------------------------

  // ---------------------- search form tagihan --------------------------------
    MahasiswaDataStore = new Ext.data.Store({
      id: 'MahasiswaDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'nilaiJSON',
                method: 'POST'
            }),
            baseParams:{task: "MAHASISWA"}, // this parameter is passed for any HTTP request
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
            //namaFakultas = r.data.namaFakultas;
            valid = false;
            NilaisDataStore.removeAll();
            //NilaisDataStore.removeAll();
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
 //-----------------------------------------------------------------------------

  DataForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        autoScroll: true,
        //height: 445,
        items: [ NomorIndukField, NamaField ],
        buttons: [{
          text: 'Tampilkan',
          handler: tampilDataNilai
        }]
    });

  var panel_data = new Ext.Panel({
        //layout: 'fit',
        //bodyStyle:'background-color: #eef4f8',
        width: 300,
        height : 445,
        border: false,
        hideBorders: true,
        //title:'Nilai Mahasiswa',
        items: [DataForm]
        //items: [DataMahasiswaForm, TranskripListingEditorGrid, DataTotalForm]
  });

    panel = new Ext.Panel({
        //layout: 'fit',
        bodyStyle:'background-color: #eef4f8',
        width: 960,
        border: false,
        hideBorders: true,
        title:'Nilai Mahasiswa',
        items: [{
            layout:'column',
            //width: 606,
            items:[{
                items: [panel_data]
            },{
                items: [NilaiListingEditorGrid]
            }]
        }]
        //items: [DataMahasiswaForm, TranskripListingEditorGrid, DataTotalForm]
  });
  panel.render("grid");

//----------------------- window buat nambah item nilai ------------------------

  MatkulDataStore = new Ext.data.Store({
      id: 'MatkulDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'nilaiJSON',
                method: 'POST'
            }),
            baseParams:{task: "MATKUL"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMatkul'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  MatkulField = new Ext.form.ComboBox({
     id:'MatkulField',
     fieldLabel: 'Matakuliah',
     store: MatkulDataStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  StatusField = new Ext.form.ComboBox({
     id:'StatusField',
     fieldLabel: 'Status',
     store: statusStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
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

  NilaiCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [MatkulField, StatusField, NilaiField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheNilai
        },{
          text: 'Batal',
          handler: function(){
            // because of the global vars, we can only instantiate one window... so let's just hide it.
            NilaiCreateWindow.hide();
          }
        }]
    });

  NilaiCreateWindow= new Ext.Window({
      id: 'NilaiCreateWindow',
      title: 'Tambahkan data Nilai baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 250,
      plain:true,
      layout: 'fit',
      items: NilaiCreateForm
    });

//------------------------------------------------------------------------------

//-------------------------- fungsi-fungsi -------------------------------------
    function tampilDataNilai() {
        NilaisDataStore.load({
            params:{
                start:0, limit:25, idMahasiswa: NomorIndukField.getValue()
            }
        });
        MatkulDataStore.load({
            params:{
                start:0, limit:25, idMahasiswa: NomorIndukField.getValue()
            }
        });
        valid = true;
    }

  // This saves the Nilai after a cell has been edited
  function saveTheNilai(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'nilaiJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         status: oGrid_event.record.data.status,
         nilai: oGrid_event.record.data.nilai
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            NilaisDataStore.commitChanges();
            NilaisDataStore.reload();
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

  // this creates a new Nilai
  function createTheNilai(){
     if(isNilaiFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'nilaiJSON',
        params: {
          task: "CREATE",
          matakuliah:       MatkulField.getValue(),
          status: StatusField.getValue(),
          nilai:      NilaiField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Nilai baru berhasil ditambahkan!');
            NilaisDataStore.reload();
            NilaiCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Nilai baru gagal!');
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
  function resetNilaiForm(){
    MatkulField.setValue('');
    StatusField.setValue('');
    NilaiField.setValue('');
  }

  // check if the form is valid
  function isNilaiFormValid(){
      return(MatkulField.isValid() && StatusField.isValid() && NilaiField.isValid());
  }

  // display or bring forth the form
  function displayFormWindow(){
    if(NomorIndukField.getValue() == '')
        Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa yang akan dilihat!');
    else{
         if(!NilaiCreateWindow.isVisible() && valid){
           resetNilaiForm();
           NilaiCreateWindow.show();
         } else {
           NilaiCreateWindow.toFront();
         }
    }
  }

  // This was added in Tutorial 6
  function confirmDeleteNilais(){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data nilai. Lanjutkan?', deleteNilais);
  }
   // This was added in Tutorial 6
  function deleteNilais(btn){
    if(btn=='yes'){
         var selections = NilaiListingEditorGrid.selModel.getSelections();
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'nilaiJSON',
            params: {
               task: "DELETE",
               id:  selections[0].data.id
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                NilaisDataStore.reload();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Nilai!');
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

  var teks = 'Untuk melihat daftar nilai mahasiswa, pilih Mahasiswa dengan mengklik drop down Nomor Induk kemudian klik tombol "Tampilkan".';
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