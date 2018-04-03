require(['require.config'], function() {
	require(['jquery', 'bootstrap'], function ($, bootstrap) {
		$("#submit").click(function() {
			$.ajax({
				url: basePath + '/login',
				type: 'POST',
				dataType: 'JSON',
				cache: false,
				data: {
					username: $('#username').val(),
					password: $('#password').val()
				},
				success: function (data) {
					console.log('登录： ' + JSON.stringify(data));
					var status = data.code;
		        	if (status == '1') {
		        		window.location.reload();
		        	} else {
		        		alert(data.msg);
					}
				},
				error: function(error) {
					console.log(error);
					alert("数据提交失败，服务器可能出现错误，请稍后再试。");
				}
			});
		});
	})
});
