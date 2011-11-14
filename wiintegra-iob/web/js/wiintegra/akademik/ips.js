var IPSsDataStore;
var IPSsColumnModel;
var IPSListingEditorGrid;

var NomorIndukField;
var NamaField;
var DataMahasiswaForm;
var namaFakultas;

var valid;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // ------------------------ tabel jadwal -------------------------------------
  IPSsDataStore = new Ext.data.Store({
      id: 'IPSsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'ipsJSON',
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

  IPSsColumnModel = new Ext.grid.ColumnModel(
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
    IPSsColumnModel.defaultSortable= true;

   IPSListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:655,
      height:480,
      //title:'Data Matakuliah',
      frame:true,
      id: 'IPSListingEditorGrid',
      store: IPSsDataStore,
      viewConfig: {
          forceFit:true
      },
      cm: IPSsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
      tbar: [
          {
            text: 'Print',
            tooltip: 'Print Data IPS',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Cetak ke Excel',
            tooltip: 'Cetak Kartu Hasil Studi ke file Excel',
            iconCls:'excel',                      // reference to our css
            handler: printExcel
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
      }]
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

  var fieldsetNilai = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Nilai",
      autoHeight: true,
      //collapsible: true,
      layout: 'form',
      items:[
              TotalAngkaField, TotalSKSField, IPSField, IPKField
      ]
  });
   //---------------------------------------------------------------------------

  // ---------------------- search form tagihan --------------------------------
    MahasiswaDataStore = new Ext.data.Store({
      id: 'MahasiswaDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'ipsJSON',
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

    SemesterDataStore = new Ext.data.Store({
      id: 'SemesterDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'ipsJSON',
                method: 'POST'
            }),
            baseParams:{task: "SEMESTER"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarSemester'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

    SemesterDataStore.load();

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
            namaFakultas = r.data.namaFakultas;
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

  SemesterField = new Ext.form.ComboBox({
     id:'SemesterField',
     fieldLabel: 'Semester',
     store: SemesterDataStore,
     mode: 'local',
     displayField: 'nama',
     allowBlank: false,
     valueField: 'id',
     anchor:'95%',
     triggerAction: 'all'
  });

  var fieldsetMahasiswa = new Ext.form.FieldSet({
      xtype:'fieldset',
      title: "Data Mahasiswa",
      autoHeight: true,
      //collapsible: true,
      layout: 'form',
      items:[
              NomorIndukField, NamaField, SemesterField
      ],
        buttons: [{
          text: 'Tampilkan',
          handler: tampilDataTagihan
        }]
  });
 //-----------------------------------------------------------------------------

  TagihanForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        border: false,
        height: 480,
        //bodyStyle:'background-color: #C3D9FF',
        autoScroll: true,
        //height: 600,
        items: [ fieldsetMahasiswa, fieldsetNilai]
    });

  panel = new Ext.Panel({
        //layout: 'fit',
        width: 960,
        border: false,
        hideBorders: true,
        bodyStyle: 'background-color: #C3D9FF',
        title:'Index Prestasi Semester / Kartu Hasil Studi',
        items: [{
            layout:'column',
            //width: 606,
            items:[{
                items: [TagihanForm]
            },{
                items: [IPSListingEditorGrid]
            }]
        }]
        //items: [DataMahasiswaForm, TranskripListingEditorGrid, DataTotalForm]
  });
    //panel.setBodyStyle('background-color: #C3D9FF');
    panel.render("grid");

    function tampilDataTagihan() {
        IPSsDataStore.load({
            params:{
                start:0, limit:25, idMahasiswa: NomorIndukField.getValue(), idSemester: SemesterField.getValue()
            },
            callback : panggilDataTotal
        });
    }

   function panggilDataTotal(){
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'ipsJSON',
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

  var teks = 'Untuk menambahkan data, klik button Tambah.<br/>' +
             'Untuk menghapus data, klik row yg akan dihapus dan klik button Hapus.<br/>' +
             'Untuk mengubah data, klik pada cell yang ingin diubah. Setelah melakukan perubahan data, tekan enter atau klik di luar cell.<br/>' +
             'Untuk mencetak KHS Mahasiswa dalam format PDF, klik tombol "Print".<br/>' +
             'Untuk mencetak KHS Mahasiswa ke dalam Microsoft Excel, klik tombol "Cetak Excel".<br/>' +
             'Pastikan Anda telah menentukan Mahasiswa dan Semester yang dimaksud terlebih dahulu dengan memilih drop down Nomor Induk dan Semester dan mengklik tombol "Tampilkan".';
  HelpWindow = new Ext.Window({
      id: 'HelpWindow',
      title: 'Help',
      closable:false,
      width: 500,
      height: 200,
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
        if(NomorIndukField.getValue() == '' || SemesterField.getValue() == '')
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa dan Semester yang akan dilihat!');
        else
            window.open('printKhs?idMhs=' + NomorIndukField.getValue() + '&idSemester=' + SemesterField.getValue(), '_blank', '');
        //win.show();
    }

    function printExcel(){
        if(NomorIndukField.getValue() == '' || SemesterField.getValue() == '')
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa dan Semester yang akan dilihat!');
        else
            window.open('printKhsExcel?idMhs=' + NomorIndukField.getValue() + '&idSemester=' + SemesterField.getValue(), '_blank', '');
        //win.show();
    }

    function print2() {
        var data={
            'semester': SemesterField.getRawValue(),
            'noInduk': NomorIndukField.getRawValue(),
            'namaMhs': NamaField.getValue(),
            'fakultas': namaFakultas,
            'totAngka': TotalAngkaField.getValue(),
            'totSks': TotalSKSField.getValue(),
            'ipk': IPKField.getValue()
        };
        Ext.ux.GridPrinter.stylesheetPath = 'js/wiintegra/gridPrint.css';
        //Ext.ux.GridPrinter.print(KSTListingEditorGrid, SemesterField.getRawValue(), NomorIndukField.getRawValue(), NamaField.getRawValue(), namaFakultas);
        Ext.ux.GridPrinter.print(IPSListingEditorGrid, data);
    }
});

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
          '<title>Index Prestasi Semester</title>',
        '</head>',
        '<body>',
        'Bagian Admisi dan Registrasi',
        '<center><h1>Index Prestasi Semester<br>',
        'Semester ' + json.semester,
        '</h1></center>',
        '<table border=0>',
            '<tr>',
                '<td align=\"left\">',
                    'Nomor Induk\t: '+json.noInduk,
                '</td>',
                '<td align=\"right\">',
                    'Fakultas : '+json.fakultas,
                '</td>',
            '</tr>',
            '<tr>',
                '<td colspan=2 align=\"left\">',
                    'Nama Mahasiswa\t: '+json.namaMhs,
                '</td>',
            '</tr>',
        '</table>',
          '<table>',
            headings,
            '<tpl for=".">',
              body,
            '</tpl>',
          '</table>',
        '<table border=0>',
            '<tr>',
                '<td align=\"center\">',
                    'Total Angka : '+json.totAngka,
                '</td>',
                '<td align=\"center\">',
                    'Total SKS : '+json.totSks,
                '</td>',
                '<td align=\"center\">',
                    'Index Prestasi : '+json.ipk,
                '</td>',
            '</tr>',
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