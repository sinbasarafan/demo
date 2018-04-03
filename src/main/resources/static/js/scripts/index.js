require(['require.config'], function() {
	require(['jquery', 'bootstrap', "layer", ], function ($, bootstrap, layer) {
		layer.msg('load finished');
	});
});