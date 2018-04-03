require(['require.config'], function() {
	require(['jquery', "jquery-qrcode", "layer", "vconsole"], function ($, qrcode, layer, vconsole) {
	    $('#qrcode').qrcode({
	        render: "canvas", //也可以替换为table
	        width: 100,
			height: 100,
	        foreground: "#C00",
	        background: "#FFF",
	        text: "http://www.baidu.com"
	    });
	    window.vconsole = new vconsole();
		layer.msg('load finished');
	})
});
