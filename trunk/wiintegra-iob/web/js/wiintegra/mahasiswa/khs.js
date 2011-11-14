var KHSsDataStore;
var KHSsColumnModel;
var KHSListingEditorGrid;

var NomorIndukField;
var NamaField;
var DataMahasiswaForm;

var valid;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // ------------------------ tabel jadwal -------------------------------------
  KHSsDataStore = new Ext.data.Store({
      id: 'KHSsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'khsJSON',
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
        {name: 'sksAkademik', type: 'int', mapping: 'sksAkademik'},
        {name: 'sksBayar', type: 'int', mapping: 'sksBayar'},
        {name: 'nilai', type: 'string', mapping: 'nilai'},
        {name: 'angka', type: 'int', mapping: 'angka'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  KHSsColumnModel = new Ext.grid.ColumnModel(
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
        header: 'SKS',
        dataIndex: 'sksAkademik',
        width: 100
      },{
        header: 'B/U/P',
        dataIndex: 'status',
        width: 50
      },{
        header: 'Nilai',
        dataIndex: 'nilai',
        width: 50
      },{
        header: 'Angka',
        dataIndex: 'angka',
        width: 100
      }]
    );
    KHSsColumnModel.defaultSortable= true;

   KHSListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:655,
      height:445,
      //title:'Data Matakuliah',
      frame:true,
      id: 'KHSListingEditorGrid',
      store: KHSsDataStore,
      viewConfig: {
          forceFit:true
      },
      cm: KHSsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false})
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

   //------------------------- form total --------------------------------------
  TotalAngkaField = new Ext.form.TextField({
    id: 'TotalAngkaField',
    fieldLabel: 'Total Angka',
    maxLength: 20,
    readOnly: true,
    anchor : '95%'
      });

  TotalSKSField = new Ext.form.TextField({
    id: 'TotalSKSField',
    fieldLabel: 'Total SKS',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  IPSField = new Ext.form.TextField({
    id: 'IPSField',
    fieldLabel: 'Index Prestasi Semester',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  IPKField = new Ext.form.TextField({
    id: 'IPKField',
    fieldLabel: 'Index Prestasi Kumulatif',
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
      items: [TotalAngkaField, TotalSKSField, IPSField, IPKField]
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
        //layout: 'fit',
        //width: 1050,
        title:'Kartu Hasil Studi',
        width: 960,
        border: false,
        hideBorders: true,
        items: [{
            layout:'column',
            //width: 606,
            items:[{
                items: [DataForm]
            },{
                items: [KHSListingEditorGrid]
            }]
        }]
        //items: [DataMahasiswaForm, KHSListingEditorGrid, DataTotalForm]
  });
  panel.render("grid");

   //------------------------- cek validitas matakuliah ------------------------
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'khsJSON',
      params: {
         task: "CURRENT"
      },
      success: function(result, request){
          var jsonData = Ext.util.JSON.decode(result.responseText);
          panel.setTitle('Kartu Hasil Studi Semester '+jsonData.currentSemester);
          NomorIndukField.setValue(jsonData.noIndukMhs);
          NamaField.setValue(jsonData.namaMhs);

          KHSsDataStore.load({
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
          url: 'khsJSON',
          params: {
             task: "TOTAL"
          },
          success: function(result, request){
              var jsonData = Ext.util.JSON.decode(result.responseText);
              TotalAngkaField.setValue(jsonData.totAngka);
              TotalSKSField.setValue(jsonData.totSksAkademik);
              IPSField.setValue(jsonData.ips);
              IPKField.setValue(jsonData.ipk);
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
   }

});