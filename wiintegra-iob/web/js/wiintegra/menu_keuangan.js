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
        text:'Menu Keuangan',
        icon:'/wiintegra-iob/page/images/icon_menu/finance.png',

                            children:[{
                                text:'Komponen Biaya',
                                icon:'/wiintegra-iob/page/images/icon_menu/komponen_biaya.png',
                                href:'/wiintegra-iob/biayalist.do'
                            },{
                                text:'Pembayaran Kuliah',
                                icon:'/wiintegra-iob/page/images/icon_menu/pembayaran_kuliah.png',
                                href:'/wiintegra-iob/regmhs_search.do'
                            },{
                                text:'Rekap. Pembayaran',
                                icon:'/wiintegra-iob/page/images/icon_menu/rekap_pembayaran.png',
                                href:'/wiintegra-iob/go_rekap_semester.do'
                            },{
                                text:'Logout',
                                icon:'/wiintegra-iob/page/images/icon_menu/logout.png',
                                href:'/wiintegra-iob/logout.do'
                            }]



    });
    tree.setRootNode(root);

  var panelDataLogin = new Ext.Panel({
        layout: 'fit',
        border:true,
        hideBorders: false,
        height:30,
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
         panelDataLogin.html = '&nbsp;<span class=\"data-login\">Selamat Datang, ' + result.responseText + '</span>';
         panel.render('west');
         root.expand();
      },
      failure: function(result, request){
         var hasil=result.responseText;
         Ext.MessageBox.alert('Error','Koneksi ke database gagal! Silakan mencoba beberapa saat lagi...');
      }
   });
});
