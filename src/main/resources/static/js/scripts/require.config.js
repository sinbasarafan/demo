define(function() {
	require.config({
		baseUrl: 'js/lib/',
		paths: {
			"jquery": ["jquery-3.2.1.min"],
			"bootstrap": ["bootstrap.min"],
			"jquery-qrcode": ["http://apps.bdimg.com/libs/jquery-qrcode/1.0.0/jquery.qrcode.min", "jquery.qrcode.min"],
			"layer-mobile" : ["layer.mobile"],
			"layer": ["layer"],
			"vconsole": ["vconsole.min"],
			"chart": ["chart.min"],
			"moment": ["moment.min"]
		},
	    shim: {
	    	"bootstrap": {
	    		deps: ["jquery", "css!../../css/bootstrap.min.css"]
	    	},
	    	"jquery-qrcode": {
	    		deps: ["jquery"]
	    	},
	    	"layer-mobile": {
	    		deps: ["jquery", "css!../../css/layer.mobile.css"]
	    	},
	    	"layer": {
	    		deps: ["jquery", "css!../../css/layer.css"]
	    	}
		},
		map: {
	        '*': {
	            css: 'js/lib/css.min.js'
	        }
	    }
	});
});