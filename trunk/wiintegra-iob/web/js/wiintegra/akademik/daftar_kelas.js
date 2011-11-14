var JadwalDataStore;
var JadwalColumnModel;
var JadwalListingEditorGrid;
var HelpWindow;

var JadwalCreateForm;
var JadwalCreateWindow;
var semester;
var panel;
var idJadwal;

Ext.onReady(function(){
var data;

  Ext.QuickTips.init();

//----------------------- store buat grid jadwal -------------------------------
  JadwalDataStore = new Ext.data.Store({
      id: 'JadwalDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'daftarKelasAkademikJSON',
                method: 'POST'
            }),
            baseParams:{task: "JADWAL"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarJadwal',
        totalProperty: 'total'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'},
        {name: 'dosen', type: 'string', mapping: 'dosen'},
        {name: 'waktu', type: 'string', mapping: 'waktu'},
        {name: 'ruang', type: 'string', mapping: 'ruang'},
        {name: 'kapasitas', type: 'int', mapping: 'kapasitas'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });
//------------------------------------------------------------------------------

//-------------------- store buat semua fakultas -------------------------------
  var fakultasStore = new Ext.data.Store({
      id: 'FakultasDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'daftarKelasAkademikJSON',
                method: 'POST'
            }),
            baseParams:{task: "FAKULTAS"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        fields: ['id', 'nama'],
        root: 'daftarFakultas'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'nama', type: 'string', mapping: 'nama'}
          ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });
    
  var progdiSearchStore = new Ext.data.Store({
      id: 'ProgdiDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'daftarKelasAkademikJSON',
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

//------------------------------------------------------------------------------
fakultasStore.load();
progdiSearchStore.load();


  FakultasSearchField = new Ext.form.ComboBox({
     id:'FakultasSearchField',
     emptyText: 'Pilih Fakultas',
     store: fakultasStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            JadwalDataStore.reload({params:{start:0, limit:25, fakultas:r.data.id}});
            progdiSearchStore.reload({params:{fakultas:r.data.id}});
            //MatakuliahDataStore.reload({params:{fakultas:r.data.id}});
            //JadwalCreateWindow.setTitle("Tambahkan data Jadwal baru Fakultas " + r.data.nama);
        }
     }
  });
  
  ProgdiSearchField = new Ext.form.ComboBox({
     id:'ProgdiSearchField',
     emptyText: 'Pilih Program Studi',
     store: progdiSearchStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all',
     listeners: {
        select: function(f,r,i){
            JadwalDataStore.load({params:{start:0, limit:25, fakultas:FakultasSearchField.getValue(), progdi:r.data.id}});
        }
     }
  });

//-------------------------- grid jadwal ---------------------------------------
  JadwalColumnModel = new Ext.grid.ColumnModel(
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
        width: 100
      },{
        header: 'Nama',
        dataIndex: 'nama',
        width: 250
      },{
        header: 'Dosen',
        dataIndex: 'dosen',
        width: 150
      },{
        header: 'Waktu',
        dataIndex: 'waktu',
        width: 100
      },{
        header: 'Ruang',
        dataIndex: 'ruang',
        width: 50
      },{
        header: 'Kapasitas',
        dataIndex: 'kapasitas',
        width: 50
      }]
    );
    JadwalColumnModel.defaultSortable= true;

   JadwalListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:800,
      height:450,
      title:'Data Jadwal',
      frame:true,
      id: 'JadwalListingEditorGrid',
      store: JadwalDataStore,
      iconCls : 'grid',
      cm: JadwalColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
      tbar: [
          FakultasSearchField
          , '-',
          ProgdiSearchField
          , '-', { // Added in Tutorial 6
            text: 'Detail',
            tooltip: 'Daftar Mahasiswa yang mengambil jadwal yang Anda pilih',
            iconCls:'contact',                      // reference to our css
            handler: detail
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            //handler: function(){showHelpWindow(1)},   // Confirm before deleting
            handler: showHelpWindow,
            iconCls:'help'
      }],
    bbar: new Ext.PagingToolbar({
        pageSize: 25,
        store: JadwalDataStore,
        displayInfo: true,
        displayMsg: 'Menampilkan data Jadwal {0} - {1} of {2}',
        emptyMsg: "Data Jadwal kosong!"
        })
    });

    JadwalDataStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {fakultas: FakultasSearchField.getValue(), progdi:ProgdiSearchField.getValue()});
            }
        }
    });
  //JadwalListingEditorGrid.render("grid");
  panel = new Ext.Panel({
      //bodyStyle:'background-color: #C3D9FF',
      width : 800,
      border: false,
      hideBorders: true,
      items : JadwalListingEditorGrid
  });
  panel.render("grid");
//------------------------------------------------------------------------------

// ------------------------------- help window ---------------------------------
  var teks = 'Untuk melihat daftar mahasiswa peserta kuliah untuk matakuliah tertentu, klik matakuliah pada tabel dan klik button Detail.';
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

    function showHelpWindow(nilai) {
        /*if(nilai == 1)
            teks = 'Untuk melihat daftar mahasiswa peserta kuliah untuk matakuliah tertentu, klik matakuliah pada tabel dan klik button Detail.';
        else if(nilai == 2)
            teks = 'Untuk melihat daftar mahasiswa peserta kuliah untuk matakuliah tertentu, klik matakuliah pada tabel dan klik button Detail.';
            */
        //HelpWindow.setHtml(teks);
        if(!HelpWindow.isVisible()){
            HelpWindow.show();
        } else {
            HelpWindow.toFront();
        }
    }

    function hideHelpWindow() {
        HelpWindow.hide();
    }
// -----------------------------------------------------------------------------

//------------ ambil data current semester dulu baru dirender ke panel ---------
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'daftarKelasAkademikJSON',
          params: {
             task: "CURRENT"
          },
          success: function(response){
             var result=response.responseText;
             semester = result;
             JadwalListingEditorGrid.setTitle('Data Jadwal '+semester);
             JadwalDataStore.load({params:{start:0, limit:25, fakultas:''}});
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
//------------------------------------------------------------------------------

//--------------------- store buat grid daftar mahasiswa -----------------------
      DaftarMahasiswaStore = new Ext.data.Store({
          id: 'DaftarMahasiswaStore',
          proxy: new Ext.data.HttpProxy({
                    url: 'daftarKelasAkademikJSON',
                    method: 'POST'
                }),
                baseParams:{task: "MAHASISWA_LIMIT"}, // this parameter is passed for any HTTP request
          reader: new Ext.data.JsonReader({
            root: 'daftarMahasiswa',
            totalProperty: 'total'
          },[
            {name: 'id', type: 'int', mapping: 'id'},
            {name: 'noInduk', type: 'string', mapping: 'noInduk'},
            {name: 'nama', type: 'string', mapping: 'nama'},
            {name: 'status', type: 'string', mapping: 'status'}
          ]),
          sortInfo:{field: 'id', direction: "ASC"}
        });
//------------------------------------------------------------------------------

//--------------------- store buat grid daftar mahasiswa -----------------------
      DaftarMahasiswaColumnModel = new Ext.grid.ColumnModel(
        [
          new Ext.grid.RowNumberer(),
          {
            header: 'ID',
            readOnly: true,
            dataIndex: 'id',
            width: 30,
            hidden: true
          },{
            header: 'Nomor Induk',
            dataIndex: 'noInduk',
            width: 300
          },{
            header: 'Nama',
            dataIndex: 'nama',
            width: 300
          },{
            header: 'B/U/P',
            dataIndex: 'status',
            width: 50
          }]
        );
        DaftarMahasiswaColumnModel.defaultSortable= true;

       DaftarMahasiswaListingEditorGrid =  new Ext.grid.EditorGridPanel({
          loadMask: {msg: 'Loading...'},
          width:800,
          height:480,
          title:'Daftar Mahasiswa yang mengambil ',
          frame:true,
          id: 'DaftarMahasiswaListingEditorGrid',
          store: DaftarMahasiswaStore,
          iconCls : 'grid',
          cm: DaftarMahasiswaColumnModel,
          enableColLock:false,
          clicksToEdit:1,
          selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
          tbar: [
              {
                text: 'Print Absensi',
                tooltip: 'Print Absensi untuk jadwal yang Anda pilih',
                iconCls:'print',                      // reference to our css
                handler: print
              }, '-', {
                text: 'Cetak ke Excel',
                tooltip: 'Print Absensi untuk jadwal yang Anda pilih ke file Excel',
                iconCls:'excel',                      // reference to our css
                handler: printExcel
          }],
            // paging bar on the bottom
            bbar: new Ext.PagingToolbar({
                pageSize: 25,
                store: DaftarMahasiswaStore,
                displayInfo: true,
                displayMsg: 'Menampilkan data Mahasiswa {0} - {1} of {2}',
                emptyMsg: "Data Mahasiswa kosong!"
            })
        });

    DaftarMahasiswaStore.on({
        'beforeload':{
            fn: function(store, options){
                Ext.apply(options.params, {id:idJadwal});
            }
        }
    });
//------------------------------------------------------------------------------

//-------------------------- fungsi-fungsi -------------------------------------
  // This saves the Jadwal after a cell has been edited
  function detail(){
    if(JadwalListingEditorGrid.selModel.getCount() <= 0) // only one Biaya is selected here
    {
      Ext.MessageBox.alert('Peringatan','Anda harus memilih data kelas matakuliah terlebih dahulu!');
    }
    else{
      var judul = 'Daftar Mahasiswa yang mengambil ' + JadwalListingEditorGrid.selModel.getSelections()[0].data.kode + " " + JadwalListingEditorGrid.selModel.getSelections()[0].data.nama + " " + semester;
      idJadwal = JadwalListingEditorGrid.selModel.getSelections()[0].data.id;
        data={
            'semester': semester,
            'matkul': JadwalListingEditorGrid.selModel.getSelections()[0].data.kode + " " + JadwalListingEditorGrid.selModel.getSelections()[0].data.nama ,
            'dosen': JadwalListingEditorGrid.selModel.getSelections()[0].data.dosen,
            'waktu': JadwalListingEditorGrid.selModel.getSelections()[0].data.waktu,
            'ruang': JadwalListingEditorGrid.selModel.getSelections()[0].data.ruang
        };
      DaftarMahasiswaListingEditorGrid.setTitle(judul);
      idJadwal = JadwalListingEditorGrid.selModel.getSelections()[0].data.id;
      panel.remove('JadwalListingEditorGrid');
      panel.add(DaftarMahasiswaListingEditorGrid);
      panel.doLayout();
      DaftarMahasiswaStore.load({params:{start:0, limit:25, id:idJadwal}});
    }
  }

    function print(){
        //Ext.MessageBox.alert('', 'sini ' + KelulusanListingEditorGrid.selModel.getSelections()[0].data.id);
        window.open('printAbsensi?idJadwal=' + idJadwal, '_blank', '');
        //win.show();
    }

    function printExcel(){
        //Ext.MessageBox.alert('', 'sini ' + KelulusanListingEditorGrid.selModel.getSelections()[0].data.id);
        window.open('printAbsensiExcel?idJadwal=' + idJadwal, '_blank', '');
        //win.show();
    }

    function print2() {
        Ext.ux.GridPrinter.stylesheetPath = 'js/wiintegra/gridPrint.css';
        //Ext.ux.GridPrinter.print(KSTListingEditorGrid, SemesterField.getRawValue(), NomorIndukField.getRawValue(), NamaField.getRawValue(), namaFakultas);
        Ext.ux.GridPrinter.print(DaftarMahasiswaListingEditorGrid, data);
    }
//------------------------------------------------------------------------------

});

//---------------------------- fungsi buat ngeprint ----------------------------
Ext.ux.GridPrinter = {
  /**
   * Prints the passed grid. Reflects on the grid's column model to build a table, and fills it using the store
   * @param {Ext.grid.GridPanel} grid The grid to print
   */
  print: function(grid, json) {
    //We generate an XTemplate here by using 2 intermediary XTemplates - one to create the header,
    //the other to create the body (see the escaped {} below)
    var columns = grid.getColumnModel().config;

    //build a useable array of store data for the XTemplate
    var data = [];
    grid.store.data.each(function(item) {
      var convertedData = [];

      //apply renderers from column model
      for (var key in item.data) {
        var value = item.data[key];

        Ext.each(columns, function(column) {
          if (column.dataIndex == key) {
            convertedData[key] = column.renderer ? column.renderer(value) : value;
          }
        }, this);
      }

      data.push(convertedData);
    });

    //use the headerTpl and bodyTpl XTemplates to create the main XTemplate below
    var headings = Ext.ux.GridPrinter.headerTpl.apply(columns);
    var body     = Ext.ux.GridPrinter.bodyTpl.apply(columns);

    var html = new Ext.XTemplate(
      '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',
      '<html>',
        '<head>',
          '<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />',
          '<link href="' + Ext.ux.GridPrinter.stylesheetPath + '" rel="stylesheet" type="text/css" media="screen,print" />',
          //'<title>' + grid.getTitle() + '</title>',
          '<title>Daftar Peserta Kuliah</title>',
        '</head>',
        '<body>',
        'Bagian Admisi dan Registrasi',
        '<center><h1>Daftar Peserta Kuliah<br>',
        'Matakuliah ' + json.matkul + "<br>",
        json.semester,
        '</h1></center>',
        '<table border=0>',
            '<tr>',
                '<td align=\"left\">',
                    'Dosen Pengajar\t: '+json.dosen,
                '</td>',
                '<td align=\"right\">',
                    'Ruang : '+json.ruang,
                '</td>',
            '</tr>',
            '<tr>',
                '<td colspan=2 align=\"left\">',
                    'Waktu\t: '+json.waktu,
                '</td>',
            '</tr>',
        '</table>',
          '<table>',
            headings,
            '<tpl for=".">',
              body,
            '</tpl>',
          '</table>',
        '</body>',
      '</html>'
    ).apply(data);

    //open up a new printing window, write to it, print it and close
    var win = window.open('', 'printgrid');

    win.document.write(html);

    //win.print();
    //win.close();
  },

  /**
   * @property stylesheetPath
   * @type String
   * The path at which the print stylesheet can be found (defaults to '/stylesheets/print.css')
   */
  //stylesheetPath: '/stylesheets/print.css',

  /**
   * @property headerTpl
   * @type Ext.XTemplate
   * The XTemplate used to create the headings row. By default this just uses <th> elements, override to provide your own
   */
  headerTpl:  new Ext.XTemplate(
    '<tr>',
      '<tpl for=".">',
        '<th>{header}</th>',
      '</tpl>',
    '</tr>'
  ),

   /**
    * @property bodyTpl
    * @type Ext.XTemplate
    * The XTemplate used to create each row. This is used inside the 'print' function to build another XTemplate, to which the data
    * are then applied (see the escaped dataIndex attribute here - this ends up as "{dataIndex}")
    */
  bodyTpl:  new Ext.XTemplate(
    '<tr>',
      '<tpl for=".">',
        '<td>\{{dataIndex}\}</td>',
      '</tpl>',
    '</tr>'
  )
};