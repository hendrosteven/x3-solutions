/*
 * Ext JS Library 2.0
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://extjs.com/license
 */
function go(){
    window.location="http://www.google.co.id";
}
Ext.onReady(function(){
    // shorthand
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel({
        autoScroll:true,
        width: 250,
        height: '100%',
        title:'Menu',
        border:true,
        anchor:'100%',
        hideBorders: false,
        animate:true,
        enableDD:true,
        containerScroll: true,
        layout:'fit',
        loader: new Tree.TreeLoader({
            dataUrl:'get-nodes.php'
        })
    });

    // set the root node
    var root = new Tree.AsyncTreeNode({
        text:'Menu Akademik',
        icon:'/wiintegra-iob/page/images/icon_menu/academic.png',

                            children:[{
                                text:'Angkatan',
                                //icon:'/wiintegra-iob/page/images/icon/Angkatan.png',
                                icon:'/wiintegra-iob/page/images/icon_menu/angkatan.png',
                                href:'/wiintegra-iob/akademik?action=angkatan'
                            },{
                                text:'Semester',
                                icon:'/wiintegra-iob/page/images/icon_menu/semester.png',
                                href:'/wiintegra-iob/akademik?action=semester'
                            },{
                                text:'Jadwal Kuliah',
                                icon:'/wiintegra-iob/page/images/icon_menu/jadwal_kuliah.png',
                                href:'/wiintegra-iob/akademik?action=jadwal'
                            },{
                                text:'Data Mahasiswa',
                                icon:'/wiintegra-iob/page/images/icon_menu/data_mahasiswa.png',
                                href:'/wiintegra-iob/akademik?action=mahasiswa'
                            },{
                                text:'Registrasi Mahasiswa',
                                icon:'/wiintegra-iob/page/images/icon_menu/pembayaran_kuliah.png',
                                href:'/wiintegra-iob/akademik?action=mahasiswareg'
                            },{
                                text:'Daftar Mahasiswa Terregistrasi',
                                icon:'/wiintegra-iob/page/images/icon_menu/registrasi_mahasiswa_terdaftar.png',
                                href:'/wiintegra-iob/akademik?action=mahasiswaunreg'
                            },{
                                text:'IPS Mahasiswa (KHS)',
                                icon:'/wiintegra-iob/page/images/icon_menu/IPS_mahasiswa.png',
                                href:'/wiintegra-iob/akademik?action=ips'
                            },{
                                text:'IPK Mahasiswa (Transkrip)',
                                icon:'/wiintegra-iob/page/images/icon_menu/IPK_mahasiswa.png',
                                href:'/wiintegra-iob/akademik?action=ipk'
                            },{
                                text:'Kartu Rencana Studi',
                                icon:'/wiintegra-iob/page/images/icon_menu/kartu_studi_tetap.png',
                                href:'/wiintegra-iob/akademik?action=krs'
                            },{
                                text:'Daftar Kelas',
                                icon:'/wiintegra-iob/page/images/icon_menu/daftar_kelas_akademik.png',
                                href:'/wiintegra-iob/akademik?action=kelas'
                            },{
                                text:'Nilai Mahasiswa',
                                icon:'/wiintegra-iob/page/images/icon_menu/nilai_mahasiswa.png',
                                href:'/wiintegra-iob/akademik?action=nilai'
                            },{
                                text:'Data Kelulusan Mahasiswa (Transkrip Akhir)',
                                icon:'/wiintegra-iob/page/images/icon_menu/nilai_mahasiswa.png',
                                href:'/wiintegra-iob/akademik?action=kelulusan'
                            },{
                                text:'Logout',
                                icon:'/wiintegra-iob/page/images/icon_menu/logout.png',
                                href:'/wiintegra-iob/logout'
                            }]



    });
    tree.setRootNode(root);

  var panelDataLogin = new Ext.Panel({
        layout: 'fit',
        border:true,
        hideBorders: false,
        height:40,
        anchor:'100%'
  });

  var panel = new Ext.Panel({
        layout: 'fit',
        border: false,
        hideBorders: true,
        bodyStyle:'background-color: #C3D9FF',
        items: [panelDataLogin, tree]
  });

   Ext.Ajax.request({
      waitMsg: 'Harap tunggu...',
      url: 'getActiveUser',
      params: {
         task: "LOGIN_DATA"
      },
      success: function(result, request){
         panelDataLogin.html = '&nbsp;<span class=\"data-login\">&nbsp;Selamat Datang, ' + result.responseText + '</span>';
         panel.render('west');
         root.expand();
      },
      failure: function(result, request){
         var hasil=result.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
});
