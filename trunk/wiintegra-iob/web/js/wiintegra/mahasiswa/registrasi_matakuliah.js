var RegMatkulsDataStore;
var RegMatkulsColumnModel;
var RegMatkulListingEditorGrid;
var ambil = false;
var panel;
var id;

Ext.onReady(function(){

  Ext.QuickTips.init();

  // ------------------------ tabel jadwal -------------------------------------
  RegMatkulsDataStore = new Ext.data.Store({
      id: 'RegMatkulsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'regMatakuliahJSON',
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter is passed for any HTTP request
      reader: new Ext.data.JsonReader({
        root: 'daftarJadwal'
      },[
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'kode', type: 'string', mapping: 'kode'},
        {name: 'nama', type: 'string', mapping: 'nama'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });

  RegMatkulsColumnModel = new Ext.grid.ColumnModel(
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
        width: 400
      }]
    );
    RegMatkulsColumnModel.defaultSortable= true;

    var sm = new Ext.grid.RowSelectionModel({
        singleSelect:true,
        listeners: {
            selectionchange: tampilDetailJadwal
        }
    });

   RegMatkulListingEditorGrid =  new Ext.grid.EditorGridPanel({
      loadMask: {msg: 'Loading...'},
      width:700,
      height:470,
      title:'Data Matakuliah',
      frame:true,
      id: 'RegMatkulListingEditorGrid',
      store: RegMatkulsDataStore,
      cm: RegMatkulsColumnModel,
      enableColLock:false,
      clicksToEdit:1,
      sm: sm
    });
    
   //---------------------------------------------------------------------------

   //------------------------- cek validitas registrasi ------------------------
   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'regMatakuliahJSON',
      params: {
         task: "CEK"
      },
      success: function(result, request){
          jsonData = Ext.util.JSON.decode(result.responseText);
          if(jsonData.valid == 0){
              Ext.MessageBox.alert('error', 'Anda Sudah Tidak Bisa Melakukan Registrasi');
          }
          else{
              if(jsonData.sudahbayar == 0){
                  Ext.MessageBox.alert('error', 'Anda Belum Melakukan Registrasi Awal Semester');
              }
              else{
                  RegMatkulListingEditorGrid.setTitle('Data Matakuliah yang dibuka pada Semester ' + jsonData.currentSemester);
                  RegMatkulsDataStore.load({params:{start:0, limit:25}});
              }
          }
      },
      failure: function(response){
         var result=response.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
   //---------------------------------------------------------------------------

  //-------------------------- window detail jadwal ----------------------------
  KodeField = new Ext.form.TextField({
    id: 'KodeField',
    fieldLabel: 'Kode',
    maxLength: 20,
    readOnly: true,
    anchor : '95%'
      });

  NamaField = new Ext.form.TextField({
    id: 'NamaField',
    fieldLabel: 'Nama Matakuliah',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  SKSAkademikField = new Ext.form.TextField({
    id: 'SKSAkademikField',
    fieldLabel: 'SKS Akademik',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  SKSBayarField = new Ext.form.TextField({
    id: 'SKSBayarField',
    fieldLabel: 'SKS Bayar',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  RuangField = new Ext.form.TextField({
    id: 'RuangField',
    fieldLabel: 'Ruang',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  SisaKursiField = new Ext.form.TextField({
    id: 'SisaKursiField',
    fieldLabel: 'Sisa Kursi',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  DosenField = new Ext.form.TextField({
    id: 'DosenField',
    fieldLabel: 'Dosen',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  WaktuField = new Ext.form.TextField({
    id: 'WaktuField',
    fieldLabel: 'Waktu',
    maxLength: 50,
    readOnly: true,
    anchor : '95%'
      });

  DetailJadwalForm = new Ext.FormPanel({
        title: 'Detail Jadwal',
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        width: 300,
        items: [KodeField, NamaField, SKSAkademikField, RuangField, SisaKursiField, DosenField, WaktuField],
    buttons: [{
      id: 'ambil',
      text: 'Ambil',
      handler: ambilMatakuliah,
      disabled: true
    }]
    });

  var panel_data = new Ext.Panel({
        //layout: 'fit',
        //bodyStyle:'background-color: #eef4f8',
        width: 300,
        height : 470,
        border: false,
        hideBorders: true,
        //title:'Nilai Mahasiswa',
        items: [DetailJadwalForm]
        //items: [DataMahasiswaForm, TranskripListingEditorGrid, DataTotalForm]
  });

    //--------------------------------------------------------------------------
    
  panel = new Ext.Panel({
        //layout: 'fit',
        width: 1005,
        border: false,
        hideBorders: true,
        items: [{
            layout:'column',
            items:[{
                items: [RegMatkulListingEditorGrid]
            },{
                items: [panel_data]
            }]
        }]
  });
  panel.render("grid");

  var loading = new Ext.Window({
      id: 'loading',
      title: 'Loading...',
      closable:false,
      width: 200,
      height: 100,
      html: '<br><div align="center">loading...<br><img src="/wiintegra-iob/page/images/loading.gif"></div>'
    });

    function tampilDetailJadwal() {
       id = RegMatkulListingEditorGrid.selModel.getSelections()[0].data.id;
       
        if(!loading.isVisible()){
            loading.show();
        } else {
            loading.toFront();
        }
    
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'regMatakuliahJSON',
          params: {
             task: "DETAIL",
             id: id
          },
          success: function(result, request){
              jsonData = Ext.util.JSON.decode(result.responseText);
              setFieldValue(jsonData);
              
              if(jsonData.bisaambil == 0){
                  DetailJadwalForm.buttons[0].disable();
                  Ext.MessageBox.alert('Peringatan','Maaf! jumlah SKS melebihi beban maximum Anda');
              }
              else{
                  if(jsonData.sudahdiambil == 0){
                      if(jsonData.ambildulu == 0){
                          if(jsonData.tersedia <= 0){
                              Ext.MessageBox.alert('Peringatan','Maaf!, Kapasitas kelas sudah penuh');
                              DetailJadwalForm.buttons[0].disable();
                          }
                          else{
                              DetailJadwalForm.buttons[0].enable();
                          }
                      }
                      else if(jsonData.ambildulu == 1){
                          Ext.MessageBox.alert('Peringatan','Matakuliah ini memiliki prasyarat, silahkan mengambil '+jsonData.prasyarat+' terlebih dahulu.');
                          DetailJadwalForm.buttons[0].disable();
                      }
                  }
                  else if(jsonData.sudahdiambil == 1){
                      Ext.MessageBox.alert('Peringatan','Anda sudah mengambil matakuliah ini pada semester ini');
                      DetailJadwalForm.buttons[0].disable();
                  }
              }
              loading.hide();
          },
          failure: function(response){
             var result=response.responseText;
             Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
          }
       });
    }

    function ambilMatakuliah() {
       Ext.Ajax.request({
          waitMsg: 'Harap tunggu...',
          url: 'regMatakuliahJSON',
          params: {
             task: "INSERT",
             id: id
          },
          success: function(response){
             var result=eval(response.responseText);
             switch(result){
             case 1:
                //Ext.MessageBox.alert('Penambahan matakuliah berhasil!');
                window.location='/wiintegra-iob/mahasiswa?action=krs';
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

    function setFieldValue(data) {
        if(data == ''){
            KodeField.setValue('');
            NamaField.setValue('');
            SKSAkademikField.setValue('');
            //SKSBayarField.setValue('');
            RuangField.setValue('');
            SisaKursiField.setValue('');
            DosenField.setValue('');
            WaktuField.setValue('');
        }
        else{
            KodeField.setValue(data.kode);
            NamaField.setValue(data.nama);
            SKSAkademikField.setValue(data.sksAkademik);
            //SKSBayarField.setValue(data.sksBayar);
            RuangField.setValue(data.ruang);
            SisaKursiField.setValue(data.tersedia);
            DosenField.setValue(data.dosen);
            WaktuField.setValue(data.waktu);
        }
    }
});