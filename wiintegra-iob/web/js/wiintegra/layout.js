Ext.onReady(function(){
  var viewport = new Ext.Viewport({
  layout: 'border',
  renderTo: Ext.getBody(),
  items: [{
    region: 'north',
    xtype: 'panel',
    height: 160,
    collapsible: true,
    split: true,
    minSize: 80,
    maxSize: 160,
    bodyStyle:'background-color: #eef4f8',
    contentEl: 'north-div'
  },{
    region: 'south',
    xtype: 'panel',
    split: true,
    minSize: 40,
    maxSize: 70,
    bodyStyle:'background-color: #eef4f8',
    contentEl: 'south-div'
  },{
    region: 'west',
    xtype: 'panel',
    split: true,
    width: 252,
    contentEl: 'west-div',
    collapsible: true,
    border:true,
    autoScroll: true,
    title: 'Data Login',
    //bodyStyle:'background-color: #C3D9FF',
    bodyStyle:'background-color: #eef4f8',
    labelWidth:100,
    minSize: 202,
    maxSize: 400,
    margins :{
        top:0,
        bottom:0,
        left:5,
        right:0
    }
  },{
    region: 'center',
    xtype: 'panel',
    split: true,
    contentEl: 'center-div',
    autoScroll: true,
    bodyStyle:'background-color: #eef4f8',
    margins :{
        top:0,
        bottom:0,
        left:0,
        right:5
    }
  }]
});

});