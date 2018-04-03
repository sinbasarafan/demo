require(['require.config'], function() {
	require(['jquery', "layer"], function ($, layer) {
		getQrCode();
		// 到后台加载二维码
		layer.msg('load finished');
	})
});

function getQrCode() {
	$.post(basePath + '/getQrCode', function (data) {
		if (!data || !data.loginId || !data.image) {return;}
		$('#qrcode').attr("src", 'data:image/png;base64,' + data.image);
		// 获取登录响应
		getResponse(data.loginId);
	});
}

function getResponse(loginId) {
	$.post(basePath + '/getResponse/' + loginId, function (data) {
		console.log('返回数据： ' + JSON.stringify(data));
		var status = data.status;
		if (status == 'waiting') {
			setTimeout(function() {
				getResponse(loginId);
			}, 1000);
		} else if (status == 'ok') {
			window.location.reload();
		} else if (status == 'refresh') {
			getQrCode();
		}
	});
}