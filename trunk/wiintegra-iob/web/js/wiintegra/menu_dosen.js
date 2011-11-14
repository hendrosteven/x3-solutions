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
        text:'Menu Dosen',
        icon:'/wiintegra-iob/page/images/icon_menu/dosen.png',

                            children:[{
                                text:'Profile',
                                icon:'/wiintegra-iob/page/images/icon_menu/profile.png',
                                href:'/wiintegra-iob/dosen?action=profil'
                            },{
                                text:'Daftar Kelas',
                                icon:'/wiintegra-iob/page/images/icon_menu/daftar_kelas.png',
                                href:'/wiintegra-iob/dosen?action=kelas'
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
