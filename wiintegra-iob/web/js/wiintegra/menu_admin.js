/*
 * Ext JS Library 2.0
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://extjs.com/license
 */
//Ext.BLANK_IMAGE_URL = 'js/ext/resources/images/default/s.gif';
function go(){
    window.location="http://www.google.co.id";
}
Ext.onReady(function(){
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
        text:'Menu Administrator',
        icon:'/wiintegra-iob/page/images/icon_menu/administrator.png',

                            children:[{
                                text:'Users',
                                icon:'/wiintegra-iob/page/images/icon_menu/user.png',
                                href:'/wiintegra-iob/admin?action=user'
                            },{
                                text:'Fakultas',
                                icon:'/wiintegra-iob/page/images/icon_menu/fakultas.png',
                                href:'/wiintegra-iob/admin?action=fakultas'
                            },{
                                text:'Program Studi',
                                icon:'/wiintegra-iob/page/images/icon_menu/progdi.png',
                                href:'/wiintegra-iob/admin?action=progdi'
                            },{
                                text:'Dosen',
                                icon:'/wiintegra-iob/page/images/icon_menu/dosen.png',
                                href:'/wiintegra-iob/admin?action=dosen'
                            },{
                                text:'Matakuliah',
                                icon:'/wiintegra-iob/page/images/icon_menu/matakuliah.png',
                                href:'/wiintegra-iob/admin?action=matakuliah'
                            },{
                                text:'Ruang',
                                icon:'/wiintegra-iob/page/images/icon_menu/ruang.png',
                                href:'/wiintegra-iob/admin?action=ruang'
                            },{
                                text:'Distrik',
                                icon:'/wiintegra-iob/page/images/icon_menu/distrik.png',
                                href:'/wiintegra-iob/admin?action=distrik'
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