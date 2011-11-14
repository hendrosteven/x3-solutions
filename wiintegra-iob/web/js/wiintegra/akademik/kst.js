var KSTsDataStore;
var KSTsColumnModel;
var KSTListingEditorGrid;

var NomorIndukField;
var NamaField;
var DataMahasiswaForm;
var namaFakultas;

var valid;

Ext.onReady(function(){

  Ext.QuickTips.init();

//----------------------- untuk menambahkan matkul -----------------------------
  JadwalDataStore = new Ext.data.Store({
      id: 'JadwalDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kstAkademikJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING_MATKUL"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarJadwal'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

   var statusStore = new Ext.data.SimpleStore({
    fields:['id', 'nama'],
    data: [['B','B'], ['U','U'], ['P','P']]
    });

  JadwalField = new Ext.form.ComboBox({
     id:'JadwalField',
     fieldLabel: 'Jadwal',
     store: JadwalDataStore,
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

  JadwalCreateForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        //items: [NamaField, KeteranganField, BiayaField],
        items: [JadwalField, StatusField],
        buttons: [{
          text: 'Tambah dan Tutup',
          handler: tampilDetailJadwal
        },{
          text: 'Batal',
          handler: function(){
            // because of the global vars, we can only instantiate one window... so let's just hide it.
            JadwalCreateWindow.hide();
          }
        }]
    });

  JadwalCreateWindow= new Ext.Window({
      id: 'JadwalCreateWindow',
      title: 'Tambahkan Jadwal baru',
      closable:false,
      iconCls : 'form',
      width: 310,
      height: 200,
      plain:true,
      layout: 'fit',
      items: JadwalCreateForm
    });

  //----------------------------------------------------------------------------
    
  // ------------------------ tabel jadwal -------------------------------------
  KSTsDataStore = new Ext.data.Store({
      id: 'KSTsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kstAkademikJSON',
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
        {name: 'sksBayar', type: 'int', mapping: 'sksBayar'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
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
        width: 50
      },{
        header: 'SKS Akademik',
        dataIndex: 'sksAkademik',
        width: 100
      },{
        header: 'SKS Bayar',
        dataIndex: 'sksBayar',
        width: 100
      }]
    );
    KSTsColumnModel.defaultSortable= true;

   KSTListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:655,
      height:445,
      frame:true,
      id: 'KSTListingEditorGrid',
      store: KSTsDataStore,
      viewConfig: {
          forceFit:true
      },
      cm: KSTsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
      tbar: [
          {
            text: 'Print',
            tooltip: 'Print Kartu Rencana Studi',
            handler: print,
            iconCls:'print'
          }, '-', {
            text: 'Cetak ke Excel',
            tooltip: 'Cetak Kartu Rencana Studi ke file Excel',
            iconCls:'excel',                      // reference to our css
            handler: printExcel
          } , '-', { // Added in Tutorial 6
            text: 'Tambah',
            tooltip: 'Menambah data matakuliah bagi Mahasiswa terpilih pada Semester ini',
            iconCls:'add',                      // reference to our css
            handler: showTambahMatkulWindow
          } , '-', { // Added in Tutorial 6
            text: 'Hapus',
            tooltip: 'Menghapus data matakuliah yang telah diambil Mahasiswa terpilih pada Semester ini',
            iconCls:'remove',                      // reference to our css
            handler: confirmHapusMatkul
          }, '-', { // Added in Tutorial 6
            text: 'Help',
            tooltip: 'Petunjuk Penggunaan',
            handler: showHelpWindow,   // Confirm before deleting
            iconCls:'help'
          }
      ]
    });

   //---------------------------------------------------------------------------

   //------------------------- form total --------------------------------------
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
    fieldLabel: 'Beban SKS Maksimal',
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
              TotalSKSAkademikField, BebanMaxField//, TotalSKSBayarField
      ]
  });
   //---------------------------------------------------------------------------

  // ---------------------- search form tagihan --------------------------------
    MahasiswaDataStore = new Ext.data.Store({
      id: 'MahasiswaDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'kstAkademikJSON',
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
                url: 'kstAkademikJSON',
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
      //bodyStyle: 'background-color: #eef4f8',
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

  DataForm = new Ext.FormPanel({
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        border: false,
        height: 445,
        autoScroll: true,
        //height: 600,
        items: [ fieldsetMahasiswa, fieldsetNilai]
    });

  panel = new Ext.Panel({
        layout: 'fit',
        width: 960,
        border: false,
        hideBorders: true,
        //bodyStyle: 'background-color: #eef4f8',
        title:'Kartu Rencana Studi',
        items: [{
            layout:'column',
            //width: 606,
            items:[{
                items: [DataForm]
            },{
                items: [KSTListingEditorGrid]
            }]
        }]
        //items: [DataMahasiswaForm, TranskripListingEditorGrid, DataTotalForm]
  });
  panel.render('grid');

    function tampilDataTagihan() {
        KSTsDataStore.load({
            params:{
                start:0, limit:25, idMahasiswa: NomorIndukField.getValue(), idSemester: SemesterField.getValue()
            },
            callback : panggilDataTotal
        });
    }

   function panggilDataTotal(){
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'kstAkademikJSON',
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

    function print(){
        if(NomorIndukField.getValue() == '' || SemesterField.getValue() == '')
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa dan Semester yang akan dilihat!');
        else
        window.open('printKst?idMhs=' + NomorIndukField.getValue() + '&idSemester=' + SemesterField.getValue(), '_blank', '');
        //win.show();
    }

    function printExcel(){
        if(NomorIndukField.getValue() == '' || SemesterField.getValue() == '')
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa dan Semester yang akan dilihat!');
        else
        window.open('printKstExcel?idMhs=' + NomorIndukField.getValue() + '&idSemester=' + SemesterField.getValue(), '_blank', '');
        //win.show();
    }

    function print2() {
        var data={
            'semester': SemesterField.getRawValue(),
            'noInduk': NomorIndukField.getRawValue(),
            'namaMhs': NamaField.getValue(),
            'fakultas': namaFakultas,
            'totSksAkademik': TotalSKSAkademikField.getValue(),
            'totSksBayar': TotalSKSBayarField.getValue()
        };
        Ext.ux.GridPrinter.stylesheetPath = 'js/wiintegra/gridPrint.css';
        //Ext.ux.GridPrinter.stylesheetPath = 'js/wiintegra/cuscosky.css';
        //Ext.ux.GridPrinter.print(KSTListingEditorGrid, SemesterField.getRawValue(), NomorIndukField.getRawValue(), NamaField.getRawValue(), namaFakultas);
        Ext.ux.GridPrinter.print(KSTListingEditorGrid, data);
    }
    
  // This was added in Tutorial 6
  function confirmHapusMatkul(){
        if(NomorIndukField.getValue() == '' || SemesterField.getValue() == '')
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa dan Semester yang akan dilihat!');
        else{
            if(KSTListingEditorGrid.selModel.getCount() == 1) // only one Angkatan is selected here
            {
              Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus 1 data matakuliah. Lanjutkan?', hapusMatkul);
            } else if(KSTListingEditorGrid.selModel.getCount() > 1){
              Ext.MessageBox.confirm('Konfirmasi','Anda akan menghapus '+KSTListingEditorGrid.selModel.getCount()+' matakuliah. Lanjutkan?', hapusMatkul);
            } else {
              Ext.MessageBox.alert('Peringatan','Anda harus memilih data Matakuliah terlebih dahulu!');
            }
        }
  }
  
    function hapusMatkul(btn) {
        if(btn=='yes'){
             var selections = KSTListingEditorGrid.selModel.getSelections();
             var prez = [];
             for(i = 0; i< KSTListingEditorGrid.selModel.getCount(); i++){
              prez.push(selections[i].json.id);
             }
             var encoded_array = Ext.encode(prez);

               Ext.Ajax.request({
                  waitMsg: 'Harap tunggu...',
                  url: 'kstAkademikJSON',
                  params: {
                     task: "DELETE",
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

  // check if the form is valid
  function isJadwalFormValid(){
      return(JadwalField.isValid() && StatusField.isValid());
  }
    function tambahMatkul() {
         if(isJadwalFormValid()){
          Ext.Ajax.request({
            waitMsg: 'Harap tunggu...',
            url: 'kstAkademikJSON',
            params: {
              task: "TAMBAH",
              idMahasiswa: NomorIndukField.getValue(),
              idSemester: SemesterField.getValue(),
              idJadwal: JadwalField.getValue(),
              status: StatusField.getValue()
              //biaya:      BiayaField.getValue()
            },
            success: function(response){
              var result=eval(response.responseText);
              switch(result){
              case 1:
                Ext.MessageBox.alert('Penambahan Jadwal Sukses','Data Jadwal berhasil ditambahkan kepada Mahasiswa terpilih!');
                KSTsDataStore.reload();
                JadwalCreateWindow.hide();
                break;
              default:
                Ext.MessageBox.alert('Peringatan','Penambahan Jadwal gagal!');
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
    
    function tampilDetailJadwal() {
       //var id = JadwalField.getValue();
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'kstAkademikJSON',
          params: {
             task: "DETAIL",
             idJadwal: JadwalField.getValue(),
             idMahasiswa: NomorIndukField.getValue()
          },
          success: function(result, request){
              jsonData = Ext.util.JSON.decode(result.responseText);

              if(jsonData.bisaambil == 0){
                  JadwalCreateForm.buttons[0].disable();
                  Ext.MessageBox.alert('Peringatan','Maaf! jumlah SKS melebihi beban maximum Mahasiswa');
              }
              else{
                  if(jsonData.sudahdiambil == 0){
                      if(jsonData.ambildulu == 0){
                          if(jsonData.tersedia <= 0){
                              Ext.MessageBox.alert('Peringatan','Maaf!, Kapasitas kelas sudah penuh');
                              //JadwalCreateForm.buttons[0].disable();
                          }
                          else{
                              JadwalCreateForm.buttons[0].enable();
                              tambahMatkul();
                          }
                      }
                      else if(jsonData.ambildulu == 1){
                          Ext.MessageBox.alert('Peringatan','Matakuliah ini memiliki prasyarat, silahkan mengambil '+jsonData.prasyarat+' terlebih dahulu.');
                          //JadwalCreateForm.buttons[0].disable();
                      }
                  }
                  else if(jsonData.sudahdiambil == 1){
                      Ext.MessageBox.alert('Peringatan','Mahasiswa sudah mengambil matakuliah ini pada semester ini');
                      //JadwalCreateForm.buttons[0].disable();
                  }
              }
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
    }

    function showTambahMatkulWindow() {
        if(NomorIndukField.getValue() == '' || SemesterField.getValue() == '')
            Ext.MessageBox.alert('Peringatan', 'Pilih dahulu Data Mahasiswa dan Semester yang akan dilihat!');
        else{
            JadwalDataStore.load({
                params:{
                    idMahasiswa: NomorIndukField.getValue(), idSemester: SemesterField.getValue()
                }
            });

            JadwalField.setValue('');
            StatusField.setValue('B');
            JadwalCreateForm.buttons[0].enable();
            if(!JadwalCreateWindow.isVisible()){
                JadwalCreateWindow.show();
            } else {
                JadwalCreateWindow.toFront();
            }
        }
    }

  var teks = 'Untuk menambahkan data, klik button Tambah.<br>' +
             'Untuk menghapus data, klik row yg akan dihapus dan klik button Hapus.<br>' +
             'Untuk mengubah data, klik pada cell yang ingin diubah. Setelah melakukan perubahan data, tekan enter atau klik di luar cell.<br>' +
             'Untuk mencetak KST Mahasiswa dalam format PDF, klik tombol "Print".<br/>' +
             'Untuk mencetak KST Mahasiswa ke dalam Microsoft Excel, klik tombol "Cetak Excel".<br/>' +
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
          '<title>Kartu Rencana Studi</title>',
        '</head>',
        '<body>',
        'Bagian Admisi dan Registrasi',
        '<center><h1>Kartu Rencana Studi<br>',
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
                    'Total SKS Akademik : '+json.totSksAkademik + ' sks',
                '</td>',
                '<td align=\"center\">',
                    'Total SKS Bayar : '+json.totSksBayar + ' sks',
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