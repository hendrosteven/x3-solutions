var KSTsDataStore;
var KSTsColumnModel;
var KSTListingEditorGrid;

var NomorIndukField;
var NamaField;
var DataMahasiswaForm;

var valid;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // ------------------------ tabel jadwal -------------------------------------
  KSTsDataStore = new Ext.data.Store({
      id: 'KSTsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kstMahasiswaJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarMatkul'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'status', type: 'string', mapping: 'status'},
        {name: 'sksAkademik', type: 'int', mapping: 'sksAkademik'}//,
        //{name: 'sksBayar', type: 'int', mapping: 'sksBayar'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   var statusStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['B','B'], ['U','U'], ['P','P']]
    });

  KSTsColumnModel = new Ext.grid.ColumnModel(
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
        header: 'Status',
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
        header: 'SKS Akademik',
        dataIndex: 'sksAkademik',
        width: 100
      /*},{
        header: 'SKS Bayar',
        dataIndex: 'sksBayar',
        width: 100*/
      }]
    );
    KSTsColumnModel.defaultSortable= true;

   KSTListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:655,
      height:445,
      //title:'Data Matakuliah',
      frame:true,
      id: 'KSTListingEditorGrid',
      store: KSTsDataStore,
      viewConfig: {
          forceFit:true
      },
      cm: KSTsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          {
            id: 'btnHapus',
            text: 'Hapus',
            tooltip: 'Hapus Matakuliah',
            ref: '../btnHapus',
            handler: confirmHapusMatkul,   // Confirm before deleting
            iconCls:'remove'
      }]
    });

   //---------------------------------------------------------------------------

   //------------------------- keterangan mahasiswa ----------------------------
  NomorIndukField = new Ext.form.TextField({
    id: 'NomorIndukField',
    fieldLabel: 'Nomor Induk',
    maxLength: 20,
    readOnly: true,
    anchor : '95%'
      });

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama Mahasiswa',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });
      
  var fieldsetMahasiswa = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Mahasiswa",
      autoHeight: true,
      //collapsible: true,
      layout: 'form',
      items: [NomorIndukField, NamaField]
  });

   //---------------------------------------------------------------------------

   //------------------------- form keterangan --------------------------------------
  TotalSKSAkademikField = new Ext.form.TextField({
    id: 'TotalSKSAkademikField',
    fieldLabel: 'Total SKS Akademik',
    maxLength: 20,
    readOnly: true,
    anchor : '95%'
      });

  TotalSKSBayarField = new Ext.form.TextField({
    id: 'TotalSKSBayarField',
    fieldLabel: 'Total SKS Bayar',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  BebanMaxField = new Ext.form.TextField({
    id: 'BebanMaxField',
    fieldLabel: 'Beban SKS Maximum',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  var fieldsetKeterangan = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Keterangan",
      autoHeight: true,
      //collapsible: true,
      layout: 'form',
      items: [TotalSKSAkademikField, BebanMaxField]
  });

  DataForm = new Ext.FormPanel({
        //title: 'Data Mahasiswa',
        labelAlign: 'top',
        border: false,
        bodyStyle:'padding:5px',
        width: 300,
        height: 445,
        items: [fieldsetMahasiswa, fieldsetKeterangan]
    });
   //---------------------------------------------------------------------------


  panel = new Ext.Panel({
    title:'Kartu Rencana Studi',
    border: false,
    width: 960,
    hideBorders: true,
    items: [{
        layout:'column',
        items:[{
            items: [DataForm]
        },{
            items: [KSTListingEditorGrid]
        }]
    }]
  });
  panel.render("grid");

   //------------------------- cek validitas matakuliah ------------------------
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'kstMahasiswaJSON',
      params: {
         task: "CEK"
      },
      success: function(result, request){
          var jsonData = Ext.util.JSON.decode(result.responseText);
          panel.setTitle('Kartu Rencana Studi Semester '+jsonData.currentSemester);
          NomorIndukField.setValue(jsonData.noIndukMhs);
          NamaField.setValue(jsonData.namaMhs);
          
          KSTListingEditorGrid.on('afteredit', ubahStatus);
          
          valid = jsonData.valid;

          if(valid == 0){
              KSTListingEditorGrid.topToolbar.items.get('btnHapus').disable();
          }
          else {
              KSTListingEditorGrid.topToolbar.items.get('btnHapus').enable();
          }
          KSTsDataStore.load({
              params:{start:0, limit:25},
              callback: panggilDataTotal
          });
          //panggilDataTotal();
      },
      failure: function(response){
         var result=response.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
   //---------------------------------------------------------------------------

   function panggilDataTotal(){
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'kstMahasiswaJSON',
          params: {
             task: "TOTAL"
          },
          success: function(result, request){
              var jsonData = Ext.util.JSON.decode(result.responseText);
              TotalSKSAkademikField.setValue(jsonData.totSksAkademik);
              //TotalSKSBayarField.setValue(jsonData.totSksBayar);
              BebanMaxField.setValue(jsonData.maxBeban);
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
   }

    function ubahStatus(oGrid_event) {
       if(valid == 0){
            Ext.MessageBox.alert('Peringatan', 'Anda tidak dijinkan melakukan pengubahan data!');
       }else{
           Ext.Ajax.request({
              waitMsg: 'Harap tunggu...',
              url: 'kstMahasiswaJSON',
              params: {
                 task: "UPDATE",
                 id: oGrid_event.record.data.id,
                 status: oGrid_event.record.data.status
              },
              success: function(response){
                 var result=eval(response.responseText);
                 switch(result){
                 case 1:
                    KSTsDataStore.commitChanges();
                    KSTsDataStore.reload();
                    Ext.MessageBox.alert('Hasil', 'Pengubahan data berhasil!');
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
    }

  // This was added in Tutorial 6
  function confirmHapusMatkul(){
    if(KSTListingEditorGrid.selModel.getCount() == 1) // only one Angkatan is selected here
    {
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 matakuliah. Lanjutkan?', hapusMatkul);
    } else if(KSTListingEditorGrid.selModel.getCount() > 1){
      Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+KSTListingEditorGrid.selModel.getCount()+' matakuliah. Lanjutkan?', hapusMatkul);
    } else {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data matakuliah terlebih dahulu!');
    }
  }
   // This was added in Tutorial 6
  function hapusMatkul(btn){
    if(btn=='yes'){
    //function hapusMatkul() {
        //Ext.MessageBox.confirm('', msg, fn, scope)
         var selections = KSTListingEditorGrid.selModel.getSelections();
         var prez = [];
         for(i = 0; i< KSTListingEditorGrid.selModel.getCount(); i++){
          prez.push(selections[i].json.id);
         }
         var encoded_array = Ext.encode(prez);
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'kstMahasiswaJSON',
          params: {
             task: "DELETE",
             //id: KSTListingEditorGrid.selModel.getSelections()[0].data.id
             ids:  encoded_array
          },
          success: function(response){
             var result=eval(response.responseText);
             switch(result){
             case 1:
                Ext.MessageBox.alert('Hasil', 'Penghapusan matakuliah berhasil!');
                KSTsDataStore.reload();
                panggilDataTotal();
                break;
             case -1 :
                Ext.MessageBox.alert('Hasil', 'Anda tidak dapat menghapus matakuliah yang Anda ambil karena melampaui batas waktu registrasi untuk Semester ini.');
                break;
             default:
                Ext.MessageBox.alert('Penghapusan data gagal!');
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
});