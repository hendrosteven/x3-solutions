// Global vars
var RekapsDataStore;
var SemesterDataStore;
var RekapsColumnModel;
var RekapListingEditorGrid;
var RekapListingWindow;
// Our new vars
var RekapCreateForm;
var RekapSearchForm;
var PembayaranForm;
var RekapCreateWindow;
var NomorIndukField;
var NamaField;
var SemesterField;
var BayarAwalField;
var BayarLunasField;
var TotalField;
var IsDispenAwalField;
var IsDispenLunasField;
var JenisBiayaField;
var RekapField;
var JumlahSKSField;
var TotalBayarAwalField;
var TotalBayarLunasField;
var TotalTagihField;
var TotalTunggakanField;
var panel;
var total;
var jsonData;

Ext.onReady(function(){

  Ext.QuickTips.init();

 //----------------------------- tabel rekap -----------------------------------
    SemesterDataStore = new Ext.data.Store({
      id: 'SemesterDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'semesterJSONListAction',
                method: 'POST'
            }),
            baseParams:{task: "COMBO"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarSemester'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    SemesterDataStore.load();
    
  SemesterField = new Ext.form.ComboBox({
     id:'SemesterField',
     fieldLabel: 'Semester',
     emptyText: 'Pilih Semester',
     store: SemesterDataStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            tampilDataRekap(r.data.nama);
        }
     }
  });

  RekapsDataStore = new Ext.data.Store({
      id: 'RekapsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'rekapitulasiJSONAction',
                method: 'POST'
            }),
      baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarRekap'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'noInduk', type: 'string', mapping: 'noInduk'},
        {name: 'total', type: 'float', mapping: 'total'},
        {name: 'isDispenAwal', type: 'string', mapping: 'isDispenAwal'},
        {name: 'biayaAwal', type: 'float', mapping: 'biayaAwal'},
        {name: 'isDispenLunas', type: 'string', mapping: 'isDispenLunas'},
        {name: 'biayaLunas', type: 'float', mapping: 'biayaLunas'},
        {name: 'tunggakan', type: 'float', mapping: 'tunggakan'}//]
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  RekapsColumnModel = new Ext.grid.ColumnModel(
    [
      new Ext.grid.RowNumberer(),
      {
        header: 'ID',
        dataIndex: 'id',
        //width: 0,
        hidden: true
      },{
        header: 'Nomor Induk',
        dataIndex: 'noInduk',
        width: 100
      },{
        header: 'Tagihan',
        dataIndex: 'total',
        width: 100,
        //align: 'right',
        renderer: 'usMoney'
      },{
        header: 'Dispen Registrasi?',
        dataIndex: 'isDispenAwal',
        width: 100
      },{
        header: 'Jumlah Registrasi',
        dataIndex: 'biayaAwal',
        width: 100,
        //align: 'right',
        renderer: 'usMoney'
      },{
        header: 'Dispen Pelunasan?',
        dataIndex: 'isDispenLunas',
        width: 100
      },{
        header: 'Jumlah Pelunasan',
        dataIndex: 'biayaLunas',
        width: 100,
        //align: 'right',
        renderer: 'usMoney'
      },{
        header: 'Tunggakan',
        dataIndex: 'tunggakan',
        width: 100,
        //align: 'right',
        renderer: 'usMoney'
      }]
    );
    RekapsColumnModel.defaultSortable= true;

   RekapListingEditorGrid =  new Ext.grid.EditorGridPanel({
      width:750,
      height:445,
      frame:true,
      id: 'RekapListingEditorGrid',
      store: RekapsDataStore,
      cm: RekapsColumnModel,
      enableColLock:false,
      //autoExpandColumn: 'common',
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
      tbar: [
          SemesterField
      ]
    });

  //----------------------------------------------------------------------------

  //----------------------- form rekapitulasi total ----------------------------
    TotalBayarAwalField = new Ext.form.NumberField({
    id: 'TotalBayarAwalField',
    fieldLabel: 'Total Registrasi Awal',
    maxLength: 20,
    readOnly: true,
    allowBlank: false,
    anchor : '95%',
    decimalPrecision:2,
    renderer: 'usMoney'
      });

    TotalBayarLunasField = new Ext.form.NumberField({
    id: 'TotalBayarLunasField',
    fieldLabel: 'Total Pelunasan',
    maxLength: 20,
    readOnly: true,
    allowBlank: false,
    anchor : '95%',
    decimalPrecision:2,
    renderer: 'usMoney'
      });

    TotalTagihField = new Ext.form.NumberField({
    id: 'TotalTagihField',
    fieldLabel: 'Total Tagihan',
    maxLength: 20,
    readOnly: true,
    allowBlank: false,
    anchor : '95%',
    decimalPrecision:2,
    renderer: 'usMoney'
      });

    TotalTunggakanField = new Ext.form.NumberField({
    id: 'TotalTunggakanField',
    fieldLabel: 'Total Tunggakan',
    maxLength: 20,
    readOnly: true,
    allowBlank: false,
    anchor : '95%',
    decimalPrecision:2,
    renderer: 'usMoney'
      });

  TotalForm = new Ext.FormPanel({
        bodyStyle:'padding:5px 5px 0',
        title: 'Total Rekapitulasi',
        width: 420,
        height: 150,
        labelWidth: 150,
        items: [TotalBayarAwalField, TotalBayarLunasField, TotalTagihField, TotalTunggakanField]
    });

  //----------------------------------------------------------------------------
  
  panel = new Ext.Panel({
        title:'Rekapitulasi Pembayaran Kuliah',
        width: 1180,
		//layout: 'border',
        items: [{
            layout:'column',
            items:[{
                items: [RekapListingEditorGrid]
            },{
                items: [TotalForm]
            }]
        }]
  });
  panel.render("grid");

    function tampilDataRekap(nama) {
        //Ext.MessageBox.alert('', SemesterField.getValue());
        panel.setTitle('Rekapitulasi Pembayaran Kuliah Semester ' + nama);
        RekapsDataStore.load({
            params:{
                start:0, limit:25, id: SemesterField.getValue()
            },
            callback : function(){
               Ext.Ajax.request({
                  waitMsg: 'Harap tunggu...',
                  url: 'rekapitulasiJSONAction',
                  params: {
                     task: "TOTAL"
                  },
                  success: function(result, request){
                     jsonData = Ext.util.JSON.decode(result.responseText);
                     tampilDataPembayaran();
                  },
                  failure: function(result, request){
                     var hasil=result.responseText;
                     Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
                  }
               });
            }
        });
    }
    
    function tampilDataPembayaran(){
        TotalBayarAwalField.setValue(jsonData.totalBayar);
        TotalBayarLunasField.setValue(jsonData.totalLunas);
        TotalTagihField.setValue(jsonData.totalTagih);
        TotalTunggakanField.setValue(jsonData.totalTunggakan);
    }
});