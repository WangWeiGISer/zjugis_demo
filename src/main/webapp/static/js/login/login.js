$(document).ready(function(){
	$('input').iCheck({
		radioClass: 'iradio_square-blue',
		increaseArea: '20%' // optional
	});
});

$(function(){
	$("#loginSubmit").click(function () {
		login();
	});

	$(document).keydown(function(event){
		if(event.keyCode==13){
			login();
		}
	});
})



//登录跳转
function login() {
	var userName =  $('#userName').val();
	var password = $('#password').val();
	if(userName==""){
		toastr.error("请输入用户名！");
		if(password==""){
			toastr.warning("请输入密码！");
		}
		return;
	}else if(password==""){
		toastr.warning("请输入密码！");
		return;
	}


	$.post('user/login', { username: userName, password: password },
		function (data) {
			if (data=='0') {
				toastr.error("用户名或密码不正确！");
			} else {
                window.location = 'user/success';
			}
		});
}