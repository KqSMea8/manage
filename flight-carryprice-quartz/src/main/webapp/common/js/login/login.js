$(function(){
	inputFocus();
	$("#btn_login").bind("click",login);
	$("#loginForm input").keyup(function(e){
		if(e.which == '13')
			 $("#btn_login").focus().triggerHandler("click");
	});
});

function inputFocus(){
	var loginResult = $("#loginResult");
	if(!loginResult.length){
		if(!$("#userName").val().length){
			$("#userName").focus();
			return;
		}
	}else{
		var text = $.trim(loginResult.text());
		if(text == '用户不存在'){
			$("#userName").focus();
		}else if(text == '密码错误'){
			$("#password").focus();
		}else if(text == '验证码错误'){
			$("#captcha").focus();
		}
	}
}

function login(){
	var userName = $.trim($("#userName").val());
	if(!userName.length){
		$("#userName").focus();
		return;
	}
	var password = $.trim($("#password").val());
	if(!password.length){
		$("#password").focus();
		return;
	}
	var captcha = $.trim($("#captcha").val());
	if(!captcha.length){
		$("#captcha").focus();
		return;
	}
	$("#loginForm").submit();
}

function captchaFlush(img){
	var src = img.src;
	if(src!=null){
		var newSrc = src;
		var i = src.indexOf("?");
		if(i>0)
			newSrc = src.substring(0,i);
		newSrc += "?t="+new Date().getTime();
		img.src = newSrc;
	}
}