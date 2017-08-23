/**
 * 基础类
 * @author: 卢海宁<luhaining1226@126.com>
 * @create: 2015-01-26
 */
var Base = Class.extend({
	
	config: null,

	init: function(cfg){
		this.config = {};
		for(var attr in cfg){
			if( cfg[attr] == undefined ){
				cfg[attr] = null;
			}
			this.config[attr] = cfg[attr];
		}
	},
	
	set: function(key, value){
		if(!key){
			return null;
		}
		if( value == undefined ){
			value = null;
		}
		this.config[key] = value;
	},
	
	get: function(key){
		if(key == undefined){
			return null;
		}
		var value = this.config[key];
		if( value == undefined ){
			value = null;
		}
		return value;
	}
});