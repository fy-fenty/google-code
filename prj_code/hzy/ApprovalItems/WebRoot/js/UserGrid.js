Ext.ns('App', 'App.user');
App.user.Grid = Ext.extend(Ext.grid.EditorGridPanel, {
    frame: true,
    title: '报销单明细',
    height: 300,
    initComponent : function() {
        // typical viewConfig
        this.viewConfig = {
            forceFit: true
        };
        // relay the Store's CRUD events into this grid so these events can be conveniently listened-to in our application-code.
        this.relayEvents(this.store, ['destroy', 'save', 'update']);
        // build toolbars and buttons.
        this.tbar = this.buildTopToolbar();
        // super
        App.user.Grid.superclass.initComponent.call(this);
    },
    buildTopToolbar : function() {
        return [{
            text: '添加明细',
            handler: this.onAdd,
            scope: this
        }, '-', {
            text: '删除明细',
            handler: this.onDelete,
            scope: this
        }, '-'];
    },
    onAdd : function(btn, ev) {
        var u = new this.store.recordType({
			dsId : 1,
			heetId:1,
			money:'',
			costExplain:'',
			flag:true,
			itemId:'',
			accessory:''
        });
        this.stopEditing();
        this.store.insert(0, u);
        this.startEditing(0, 0);
    },
    onDelete : function(btn, ev) {
        var index = this.getSelectionModel().getSelectedCell();
        if (!index) {
            return false;
        }
        var rec = this.store.getAt(index[0]);
        this.store.remove(rec);
    }
});