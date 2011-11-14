// Global vars
var MatakuliahsDataStore;
var MatakuliahsColumnModel;
var MatakuliahListingEditorGrid;
var MatakuliahListingWindow;
// Our new vars
var MatakuliahCreateForm;
var MatakuliahCreateWindow;
var KodeField;
var NamaField;
var SKSAkademikField;
var SKSBayarField;
var PrasyaratField;
var FakultasField;

Ext.onReady(function(){

  Ext.QuickTips.init();

  function saveTheMatakuliah(oGrid_event){
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'matakuliahJSON',
      params: {
         task: "UPDATE",
         id: oGrid_event.record.data.id,
         kode: oGrid_event.record.data.kode,
         nama: oGrid_event.record.data.nama,
         sksAkademik: oGrid_event.record.data.sksAkademik,
         //sksBayar: oGrid_event.record.data.sksBayar,
         prasyarat: oGrid_event.record.data.prasyarat,
         fakultas: oGrid_event.record.data.fakultas,
         progdi: oGrid_event.record.data.progdi
      },
      success: function(response){
         var result=eval(response.responseText);
         switch(result){
         case 1:
            MatakuliahsDataStore.commitChanges();
            MatakuliahsDataStore.reload();
            break;
         case -1:
            Ext.MessageBox.alert('Error', 'Kode Matakuliah sudah ada!');
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

  function createTheMatakuliah(){
     if(isMatakuliahFormValid()){
      Ext.Ajax.request({
        waitMsg: 'Harap tunggu...',
        url: 'matakuliahJSON',
        params: {
          task: "CREATE",
         kode: KodeField.getValue(),
         nama: NamaField.getValue(),
         sksAkademik: SKSAkademikField.getValue(),
         //sksBayar: SKSBayarField.getValue(),
         prasyarat: PrasyaratField.getValue(),
         fakultas: FakultasField.getValue(),
         progdi: ProgdiField.getValue()
        },
        success: function(response){
          var result=eval(response.responseText);
          switch(result){
          case 1:
            Ext.MessageBox.alert('Penambahan Data Sukses','Data Matakuliah baru berhasil ditambahkan!');
            MatakuliahsDataStore.reload();
            MatakuliahCreateWindow.hide();
            break;
          case -1:
            Ext.MessageBox.alert('Peringatan!','Kode tidak boleh sama!');
            break;
          default:
            Ext.MessageBox.alert('Peringatan','Penambahan data Matakuliah baru gagal!');
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

  function resetMatakuliahForm(){
     KodeField.setValue('');
     NamaField.setValue('');
     SKSAkademikField.setValue('');
     SKSBayarField.setValue('');
     PrasyaratField.setValue('');
     FakultasField.setValue('');
     ProgdiField.setValue('');
  }

  function isMatakuliahFormValid(){
      return(KodeField.isValid() && NamaField.isValid() && SKSAkademikField.isValid() //&& SKSBayarField.isValid()
            && PrasyaratField.isValid() && FakultasField.isValid() && ProgdiField.isValid());
  }

  function displayFormWindow(){
     if(!MatakuliahCreateWindow.isVisible()){
       resetMatakuliahForm();
       MatakuliahCreateWindow.show();
     } else {
       MatakuliahCreateWindow.toFront();
     }
  }

  function confirmDeleteMatakuliahs(){
    if(MatakuliahListingEditorGrid.selModel.getCount() == 1)
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data matakuliah. Lanjutkan?', deleteMatakuliahs);
    } else if(MatakuliahListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+MatakuliahListingEditorGrid.selModel.getCount()+' data matakuliah. Lanjutkan?', deleteMatakuliahs);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data Matakuliah terlebih dahulu!');
    }
  }

  function deleteMatakuliahs(btn){
    if(btn=='yes'){
         var selections = MatakuliahListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< MatakuliahListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
         Ext.Ajax.request({
            waitMsg: 'Harap tunggu',
            url: 'matakuliahJSON',
            params: {
               task: "DELETE",
               ids:  encoded_array
              },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:
                MatakuliahsDataStore.reload();
                break;
              case -1:
                Ext.MessageBox.alert('Peringatan','Sebagian data Matakuliah yang Anda pilih tidak dapat dihapus! Pastikan data Matakuliah yang Anda ingin hapus tidak dipakai di data Jadwal!');
                MatakuliahsDataStore.reload();
                break;
              case 0:
                Ext.MessageBox.alert('Peringatan','Seluruh data Matakuliah yang Anda pilih tidak dapat dihapus! Pastikan data Matakuliah yang Anda ingin hapus tidak dipakai di data Jadwal!');
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Tidak dapat menghapus semua data Matakuliah!');
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


  var fakultasSearchStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'matakuliahJSON',
                method: 'POST'
            }),
            baseParams:{task: "FAKULTAS"},
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
                url: 'matakuliahJSON',
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
      autoLoad: true,
      proxy: new Ext.data.HttpProxy({
                url: 'matakuliahJSON',
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

  fakultasSearchStore.load({params: {fakultas: ''}});
  progdiStore.load();
  progdiSearchStore.load();

    var FakultasSearchField = new Ext.form.ComboBox({
     id:'FakultasSearchField',
     emptyText: 'Pilih Fakultas',
     store: fakultasSearchStore,
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            MatakuliahsDataStore.load({params:{start:0, limit:25, fakultas:r.data.id}});
            progdiSearchStore.reload({params:{fakultas:r.data.id}});
            //progdiSearchStore.filter('fakultas',FakultasSearchField.getValue(),true,false);
        }
     }
      });

  var ProgdiSearchField = new Ext.form.ComboBox({
     id:'ProgdiSearchField',
     emptyText: 'Pilih Program Studi',
     store: progdiSearchStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     forceSelection: true,
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            //MatakuliahsDataStore.filter('progdi',ProgdiSearchField.getValue(),true,false);
            MatakuliahsDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue(), progdi:r.data.id}});
        }
     }
  });

  MatakuliahsDataStore = new Ext.data.Store({
      id: 'MatakuliahsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'matakuliahJSON',
                method: 'POST'
            }),
      baseParams:{task: "LISTING_BY_FAKULTAS_LIMIT", fakultas:FakultasSearchField.getValue()},
      reader: new Ext.data.JsonReader({
        root: 'daftarMatakuliah',
        totalProperty: 'total',
        id: 'id'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'sksAkademik', type: 'int', mapping: 'sksAkademik'},
        {name: 'prasyarat', type: 'int', mapping: 'prasyarat'},
        {name: 'fakultas', type: 'int', mapping: 'fakultas'},
        {name: 'progdi', type: 'int', mapping: 'progdi'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var fakultasStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'matakuliahJSON',
                method: 'POST'
            }),
            baseParams:{task: "FAKULTAS"},
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarFakultas'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  var prasyaratStore = new Ext.data.Store({
      id: 'PrasyaratFakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'matakuliahJSON',
                method: 'POST'
            }),
            baseParams:{task: "PRASYARAT"},
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarMatakuliah'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  fakultasStore.load();
  prasyaratStore.load();

  MatakuliahsColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        readOnly: true,
        dataIndex: 'id',
        width: 30,
        hidden: true
      },{
        header: 'Kode',
        dataIndex: 'kode',
        width: 100,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 10
          })
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 200,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 100,
            maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'SKS Akademik',
        dataIndex: 'sksAkademik',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 3,
            maskRe: /([0-9\s]+)$/
          })
      /*},{
        header: 'SKS Bayar',
        dataIndex: 'sksBayar',
        width: 250,
        editor: new Ext.form.TextField({
            allowBlank: false,
            maxLength: 20,
            maskRe: /([0-9\s]+)$/
          })*/
      },{
        header: 'Prasyarat',
        dataIndex: 'prasyarat',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: prasyaratStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val){
            return prasyaratStore.queryBy(function(rec){
            return rec.data.id == val;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Fakultas',
        dataIndex: 'fakultas',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: fakultasStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val2){
            return fakultasStore.queryBy(function(rec2){
//                if(rec2.data.id == val2)
//                    progdiStore.reload({params:{fakultas:rec2.data.id}});
                return rec2.data.id == val2;
            }).itemAt(0).data.nama;
            }
      },{
        header: 'Program Studi',
        dataIndex: 'progdi',
        width: 150,
        editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               store: progdiStore,
               mode: 'local',
               displayField: 'nama',
               valueField: 'id',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            }),
        renderer: function (val2){
            return progdiStore.queryBy(function(rec2){
            return rec2.data.id == val2;
            }).itemAt(0).data.nama;
            }
      }]
    );
    MatakuliahsColumnModel.defaultSortable= true;

   MatakuliahListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:1100,
      height:500,
      title:'Data Matakuliah',
      frame:true,
      id: 'MatakuliahListingEditorGrid',
      autoScroll: true,
      store: MatakuliahsDataStore,
      iconCls : 'grid',
      cm: MatakuliahsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          FakultasSearchField, '-',
          ProgdiSearchField, '-',
          {
            text: 'Tambah',
            tooltip: 'Tambah Data Matakuliah',
            iconCls:'add',                     
            handler: displayFormWindow
          }, '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Hapus Data Matakuliah',
            handler: confirmDeleteMatakuliahs, 
            iconCls:'remove'
          }, '-', {
            text: 'Print',
            tooltip: 'Print Data Matakuliah',
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
        store: MatakuliahsDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Matakuliah {0} - {1} of {2}',
        emptyMsg: "Data Matakuliah kosong!"
    })
    });

    MatakuliahsDataStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {fakultas: FakultasSearchField.getValue(), progdi:ProgdiSearchField.getValue()});
            }
        }
    });

    MatakuliahListingWindow = new Ext.Window({
      id: 'MatakuliahListingWindow',
      title: 'Data Matakuliah',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: MatakuliahListingEditorGrid
    });

  MatakuliahListingEditorGrid.render("grid");
  MatakuliahsDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue()}});
  MatakuliahListingEditorGrid.on('afteredit', saveTheMatakuliah);

  KodeField = new Ext.form.TextField({
    id: 'KodeField',
    fieldLabel: 'Kode',
    maxLength: 10,
    allowBlank: false,
    anchor : '95%'
      });

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama',
    maxLength: 150,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([a-zA-Z0-9\s]+)$/
      });

  SKSAkademikField = new Ext.form.TextField({
    id: 'SKSAkademikField',
    fieldLabel: 'SKS Akademik',
    maxLength: 3,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([0-9\s]+)$/
      });

  SKSBayarField = new Ext.form.TextField({
    id: 'SKSBayarField',
    fieldLabel: 'SKS Bayar',
    maxLength: 3,
    allowBlank: false,
    anchor : '95%',
    maskRe: /([0-9\s]+)$/
      });

  PrasyaratField = new Ext.form.ComboBox({
     id:'PrasyaratField',
     fieldLabel: 'Prasyarat',
     store: prasyaratStore,
     displayField: 'nama',
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
      });

  FakultasField = new Ext.form.ComboBox({
     id:'FakultasField',
     fieldLabel: 'Fakultas',
     store: fakultasStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            //progdiStore.filter('fakultas',FakultasField.getValue(),true,false);
            progdiStore.reload({params:{fakultas:r.data.id}});
        }
     }
      });

  var ProgdiField = new Ext.form.ComboBox({
     id:'ProgdiField',
     fieldLabel: 'Program Studi',
     store: progdiStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  MatakuliahCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        autoScroll: true,
        items: [KodeField, NamaField, SKSAkademikField, PrasyaratField, FakultasField, ProgdiField],
        buttons: [{
          text: 'Simpan dan Tutup',
          handler: createTheMatakuliah
        },{
          text: 'Batal',
          handler: function(){
            MatakuliahCreateWindow.hide();
          }
        }]
    });

  MatakuliahCreateWindow= new Ext.Window({
      id: 'MatakuliahCreateWindow',
      title: 'Tambahkan data Matakuliah baru',
      closable:false,
      iconCls : 'form',
      width: 300,
      height: 400,
      plain:true,
      layout: 'fit',
      items: MatakuliahCreateForm
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

    function print(){
        window.open('printMatakuliah?idFakultas=' + FakultasSearchField.getValue(), '_blank', '');
    }
});