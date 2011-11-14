// Global vars
var SemestersDataStore;
var SemestersColumnModel;
var SemesterListingEditorGrid;
var SemesterListingWindow;
// Our new vars
var SemesterCreateForm;
var SemesterCreateWindow;
var NamaField;
var TahunAjaranField;
var BatasRegField;
var semesterStore;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // This saves the Semester after a cell has been edited
  function saveTheSemester(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'semesterJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         nama: oGrid_event.record.data.nama,
         tahunAjaran: oGrid_event.record.data.tahunAjaran,
         batas: oGrid_event.record.data.batas.format('m/d/Y')
         //batasRegistrasi: "iniiii..."
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            SemestersDataStore.commitChanges();
            SemestersDataStore.reload();
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

  // this creates a new Semester
  function createTheSemester(){
     if(isSemesterFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'semesterJSON',
        params: {
          task: "CREATE",
          nama:       NamaField.getValue(),
          tahunAjaran: TahunAjaranField.getValue(),
          batas:      BatasRegField.getValue().format('m/d/Y')
          //batasRegistrasi: "ini..."
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Semester baru berhasil ditambahkan!');
            SemestersDataStore.reload();
            SemesterCreateWindow.hide();
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Semester baru gagal!');
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
  function resetSemesterForm(){
    NamaField.setValue('');
    TahunAjaranField.setValue('');
    BatasRegField.setValue('');
  }

  // check if the form is valid
  function isSemesterFormValid(){
      return(NamaField.isValid() && TahunAjaranField.isValid() && BatasRegField.isValid());
  }

  // display or bring forth the form
  function displayFormWindow(){
     if(!SemesterCreateWindow.isVisible()){
       resetSemesterForm();
       SemesterCreateWindow.show();
     } else {
       SemesterCreateWindow.toFront();
     }
  }

  // This was added in Tutorial 6
  function confirmDeleteSemesters(){
    if(SemesterListingEditorGrid.selModel.getCount() == 1) // only one Semester is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data semester. Lanjutkan?', deleteSemesters);
    } else if(SemesterListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+SemesterListingEditorGrid.selModel.getCount()+' data semester. Lanjutkan?', deleteSemesters);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Semester terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function deleteSemesters(btn){
    if(btn=='yes'){
         var selections = SemesterListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< SemesterListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'semesterJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:  // Success : simply reload
                SemestersDataStore.reload();
                break;
              case -1:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Sebagian data Semester yang Anda pilih tidak dapat dihapus! Pastikan data Semester yang Anda ingin hapus tidak dipakai di data Mahasiswa dan Jadwal!');
                SemestersDataStore.reload();
                break;
              case 0:  // Success : simply reload
                Ext.MessageBox.alert('Peringatan','Seluruh data Semester yang Anda pilih tidak dapat dihapus! Pastikan data Semester yang Anda ingin hapus tidak dipakai di data Mahasiswa dan Jadwal!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Semester!');
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
  SemestersDataStore = new Ext.data.Store({
      id: 'SemestersDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'semesterJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_LIMIT"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarSemester',
        totalProperty: 'total'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'tahunAjaran', type: 'string', mapping: 'tahunAjaran'},
        {name: 'batas', type: 'date', mapping: 'batas', dateFormat: 'm/d/Y'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   semesterStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['1','1'], ['2','2']]
    });

  SemestersColumnModel = new Ext.grid.ColumnModel(
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
        header: 'Semester',
        dataIndex: 'nama',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: semesterStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               //lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return semesterStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'TahunAjaran',
        dataIndex: 'tahunAjaran',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
       header: 'Batas Registrasi',
       //xtype: 'datecolumn',
       dataIndex: 'batas',
       width: 95,
       //renderer: formatDate,
       renderer: Ext.util.Format.dateRenderer('d/m/Y'),
       editor: new Ext.form.DateField({
            format: 'd/m/Y',
            //minValue: '01/01/06',
            disabledDays: [0, 6],
            disabledDaysText: 'Plants are not available on the weekends'
        }),
        hidden: false
      }]
    );
    SemestersColumnModel.defaultSortable= true;

   SemesterListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:700,
      height:480,
      title:'Data Semester',
      frame:true,
      id: 'SemesterListingEditorGrid',
      store: SemestersDataStore,
      iconCls : 'grid',
      cm: SemestersColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Semester',
            iconCls:'add',                      // reference to our css
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Semester',
            handler: confirmDeleteSemesters,   // Confirm before deleting
            iconCls:'remove'
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }],
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: SemestersDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Semester {0} - {1} of {2}',
        emptyMsg: "Data Semester kosong!"
        })
    });

  SemesterListingWindow = new Ext.Window({
      id: 'SemesterListingWindow',
      title: 'Data Semester',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: SemesterListingEditorGrid
    });

  //semesterStore.load();
  //SemesterListingWindow.show();
  SemesterListingEditorGrid.render("grid");
  SemestersDataStore.load({params:{start:0, limit:25}});
  SemesterListingEditorGrid.on('afteredit', saveTheSemester);

  NamaField = new Ext.form.ComboBox({
     id:'NamaField',
     fieldLabel: 'Semester',
     store: semesterStore,
     mode: 'local',
     displayField: 'id',
     allowBlank: false,
     valueField: 'nama',
     anchor:'95%',
     triggerAction: 'all'
  });

  TahunAjaranField = new Ext.form.TextField({
    id: 'TahunAjaranField',
    fieldLabel: 'Tahun Ajaran',
    maxLength: 20,
    allowBlank: false,
    anchor : '95%',
    tooltip: 'misal 2007/2008'
    //maskRe: /([a-zA-Z0-9\s]+)$/
      });

  BatasRegField = new Ext.form.DateField({
    id: 'BatasRegField',
    fieldLabel: 'Batas Registrasi',
    allowBlank: false,
    format: 'd/m/Y',
    maxLength: 20
      });

  SemesterCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [NamaField, TahunAjaranField, BatasRegField],
        /*items: [{
            layout:'column',
            border:false,
            items:[{
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [NamaField, TahunAjaranField, BatasRegField]
            }]
        }],*/
    buttons: [{
      text: 'Simpan dan Tutup',
      handler: createTheSemester
    },{
      text: 'Batal',
      handler: function(){
        // because of the global vars, we can only instantiate one window... so let's just hide it.
        SemesterCreateWindow.hide();
      }
    }]
    });

  SemesterCreateWindow= new Ext.Window({
      id: 'SemesterCreateWindow',
      title: 'Tambahkan data Semester baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 250,
      plain:true,
      layout: 'fit',
      items: SemesterCreateForm
    });
    
    function formatDate(value){
        //Ext.MessageBox.alert('Error', 'sini..');
        return value ? value.dateFormat('d M, Y') : '';
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