//获取url参数
function getQueryStringRegExp(name) {
	var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
	if (reg.test(location.href))
		return unescape(RegExp.$2.replace(/\+/g, " "));
	return "";
};

/**
 * 将一个表单的数据返回成JSON对象
 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		var val = $.trim(this.value);
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(val || '');
		} else {
			o[this.name] = val || '';
		}
	});
	return o;
};

(function($) {
	$.fn.serializeJson = function(prefix) {
		var result = {};
		var n = prefix ? (prefix + '.') : "";
		var arrayTypes = this["arrayTypes"];
		var array = this.serializeArray();
		var datetimeTypes = this["datetimeTypes"] || [];
		for (var i = 0; i < array.length; i++) {
			if ($.inArray(array[i].name, datetimeTypes) >= 0) {
				if (array[i].value) {
					array[i].value = new Date(array[i].value.replace(/-/g, "/"))
							.getTime();
				} else {
					delete array[i].name;
				}
			}
		}
		$(array).each(function() {
			if (!this.name || this.name.indexOf(n) != 0)
				return;
			var name = this.name.substring(n.length);
			var idot = name.indexOf('.');
			if (idot > 0) {
				var n1 = name.substring(0, idot);
				var n2 = name.substring(idot + 1);
				if (!result[n1])
					result[n1] = {};
				result[n1][n2] = this.value;
			} else if (result[name]) {
				if ($.isArray(result[name])) {
					result[name].push(this.value);
				} else {
					result[name] = [ result[name], this.value ];
				}
			} else {
				result[name] = this.value;
			}
		});
		if (!arrayTypes)
			return result;
		for (var i = 0; i < arrayTypes.length; i++) {
			var n = arrayTypes[i];
			if (!result[n]) {
				delete result[n];
			} else if (!$.isArray(result[n])) {
				result[n] = [ result[n] ];
			}
		}
		return result;
	};

	var privateJsonString = function(object) {
		var type = typeof object;
		if ('object' == type && Array == object.constructor) {
			type = 'array';
		}
		switch (type) {
		case 'number':
			return object ? ("" + object) : "0";
		case 'string':
			return '"' + object.replace(/(\\|\")/g, "\\$1") + '"';
		case 'object':
			var results = [];
			for ( var property in object) {
				results.push(property + ':'
						+ privateJsonString(object[property]));
			}
			return '{' + results.join(',') + '}';
		case 'array':
			var results = [];
			for (var i = 0; i < object.length; i++) {
				results.push(object[i] ? privateJsonString(object[i]) : "null");
			}
			return '[' + results.join(',') + ']';
		default:
			return (object === undefined) ? ("" + object) : "null";
		}
	};
	$.objectToString = privateJsonString;
	$.fn.serializeJsonString = function(prefix) {
		return privateJsonString(this.serializeJson(prefix));
	};

})(jQuery);



/**
 * 格式化日期 调用方式： var str = new Date().Format("yyyy-MM-dd HH:mm:ss");
 */
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};



/**
 * 修改baseObj对象中name字段的日期时间格式值为long型
 */
function dateToLong(baseObj, name) {
	if (baseObj[name] && baseObj[name] != "")
		baseObj[name] = strToDate(baseObj[name]).getTime();
	else
		baseObj[name] = "";
}

// 修改bean对象中name字段的long时间格式值为format格式的日期
function longToDateStr(bean, name, format) {
	// var time1 = new Date().Format("yyyy-MM-dd");
	// var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
	if (bean[name] && bean[name] != "")
		bean[name] = new Date(bean[name]).Format(format);
	else
		bean[name] = "";
}

// 获取对象类型
function getType(a) {
	return Object.prototype.toString.call(a);
}

// 根据id设置元素的值
function setValueById(id, value) {
	if ($("#" + id).length > 0) {
		if ($("#" + id).get(0).tagName === "SPAN") {
			if (value != null) {
				$("#" + id).text(value);
			}
		} else if ($("#" + id).get(0).tagName === "SELECT") {
			$("#" + id).val(value);
		} else if ($("#" + id).get(0).tagName === "TEXTAREA") {
			$("#" + id).val(value);
		} else if ($("#" + id).get(0).tagName === "DIV") {
			$("#" + id).html(value);
		} else if ($("#" + id).get(0).tagName === "LABEL") {
			$("#" + id).text(value);
		} else if ($("#" + id).get(0).tagName === "INPUT") {
			$("#" + id).val(value);
		}
	} else if ($("input[name=" + id + "]").length > 0) {
		if ($("input[name=" + id + "]")[0].type == "radio") {
			$('input[type="radio"][name="' + id + '"][value=' + value + ']')
					.prop("checked", 'checked');
		} else if ($("input[name=" + id + "]")[0].type == "checkbox") {
			$('input[type="checkbox"][name="' + id + '"][value=' + value + ']')
					.prop("checked", 'checked');
		}
	}
}

// 根据nObj中的属性设置html元素的值
function loadBeanValue(nObj) {
	if (!nObj)
		return;
	var Obj = nObj;
	if (getType(Obj) === '[object String]')
		Obj = eval('(' + nObj + ')');

	for (pro in Obj) {
		if (getType(Obj[pro]) === '[object String]') {
			setEleValue(pro, Obj[pro]);
		} else if (getType(Obj[pro]) === '[object Number]') {
			setEleValue(pro, Obj[pro]);
		} else if (getType(Obj[pro]) === '[object Boolean]') {
			setEleValue(pro, Obj[pro]);
		} else if (getType(Obj[pro]) === '[object Date]') {
			setEleValue(pro, Obj[pro].Format("yyyy-MM-dd HH:mm:ss"));
		} else if (getType(Obj[pro]) === '[object Array]') {
			/*
			 * for(var i=0;i<Obj[pro].length;i++){
			 * setEleValue(pro,Obj[pro][i]); }
			 */
		} else if (getType(Obj[pro]) === '[object Function]') {

		} else if (getType(Obj[pro]) === '[object Object]') {
			// loadBeanValue(Obj[pro]);
		}
	}
}

// 未定义字段处理
function dealNull(value) {
	if (typeof (value) == "undefined")
		return "";
	if (value == null)
		return "";
	if (value == "null")
		return "";
	if (value == "undefined")
		return "";
	return value;
}

// 校验变量值是否为null或空字符（undefined、null、"" 返回false,其他返回true）
function isNotBlank(value) {
	if (value == undefined) {
		return false;
	}
	if (value == "undefined") {
		return false;
	}
	if (value == null) {
		return false;
	}
	if (value == "null") {
		return false;
	}
	if (value === "") {
		return false;
	}
	return true;
}

// 校验变量值是否为null或空字符（undefined、null、"" 返回true,其他返回false）
function isBlank(value) {
	if (value == undefined) {
		return true;
	}
	if (value == "undefined") {
		return true;
	}
	if (value == null) {
		return true;
	}
	if (value == "null") {
		return true;
	}
	if (value === "") {
		return true;
	}
	return false;
}


/**
 * 扩展字符串的属性：len，获取该字符串的长度
 */
String.prototype.len = function() {   
	var arr=this.match(/[^\x00-\xff]/ig);   
	return this.length+(arr==null?0:arr.length);   
}   

/**  
* 扩展字符串的属性：trim,去除左右空格（原型扩展或重载）  
* @type string  
* @returns 处理后的字符串  
*/   
String.prototype.trim = function() {   
  return this.replace(/(^\s*)|(\s*$)/g,"");   
} 

//js增加replaceAll方法
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}

//根据下标移除数组中的元素
Array.prototype.remove = function(dx) {
	if (isNaN(dx) || dx > this.length) {
		return false;
	}
	for (var i = 0, n = 0; i < this.length; i++) {
		if (this[i] != this[dx]) {
			this[n++] = this[i]
		}
	}
	this.length -= 1
}



// 数字转大写
function numToChinese() {
	var num = $("#htJinE").val();
	if (!/^\d*(\.\d*)?$/.test(num)) {
		alertMessage("数字格式有误!");
		return "Number is wrong!";
	}
	var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
	var BB = new Array("", "拾", "佰", "仟", "萬", "億", "点", "");
	var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = "";
	for (var i = a[0].length - 1; i >= 0; i--) {
		switch (k) {
		case 0:
			re = BB[7] + re;
			break;
		case 4:
			if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$")
					.test(a[0]))
				re = BB[4] + re;
			break;
		case 8:
			re = BB[5] + re;
			BB[7] = BB[5];
			k = 0;
			break;
		}
		if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0)
			re = AA[0] + re;
		if (a[0].charAt(i) != 0)
			re = AA[a[0].charAt(i)] + BB[k % 4] + re;
		k++;
	}

	if (a.length > 1) // 加上小数部分(如果有小数部分)
	{
		re += BB[6];
		for (var i = 0; i < a[1].length; i++)
			re += AA[a[1].charAt(i)];
	}
	$("#chineseNum").val(re);
}

// 获取浏览器版本号
function getBrowserInfo() {
	var agent = navigator.userAgent.toLowerCase();
	var regStr_ie = /msie [\d.]+;/gi;
	var regStr_ff = /firefox\/[\d.]+/gi
	var regStr_chrome = /chrome\/[\d.]+/gi;
	var regStr_saf = /safari\/[\d.]+/gi;
	// IE
	if (agent.indexOf("msie") > 0) {
		return agent.match(regStr_ie);
	}
}



/**
 * 适用于各种需要使用DIV元素进行dialog弹出窗口的信息显示
 * @param divId 使用元素DIV的id
 * @param titie 弹出层标题
 * @param url 访问路径
 * @param w 对话框宽度
 * @param h 对话框高度
 */
function showDialog(divId,title,url ,w ,h){
	if(isBlank(divId)){
		return;
	}
	if(isBlank(w)){
		w = 700;
	}
	if(isBlank(h)){
		h = 500;
	}
	
	var title = title;
	var url = url;
	var data = '';
	
	if(url!=null &&url!=''){
		$("#"+divId).empty();
		$("#"+divId).load(url ,data ,function(){
			$("#"+divId).dialog({
				title : title,
				width : w,
				height : h,
				autoOpen : false,
				modal : true,
				close:function(){
					$(this).dialog("close");
				}
			});
			
			$("#"+divId).dialog("open");
		});
	}
}



// ie8不支持console.log()的解决
window.console = window.console
		|| (function() {
			var c = {};
			c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function() {
			};
			return c;
		})();



// 设置AJAX的全局默认选项
$.ajaxSetup({
	cache : false,
	async : false, // 默认同步加载
//	contentType : "application/json", 
	dataType : "json",
	type : "POST", // 默认使用POST方式
	error : function(jqXHR, textStatus, errorThrown) { // 出错时默认的处理函数
		alert('发送AJAX请求到 ' + this.url + ' 时出错[' + jqXHR.status + ']：' + jqXHR.responseText);
	}
});

//防止ajax重复提交
var pendingRequests = {};
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    var key = options.url;
//    console.log(key);
    if (!pendingRequests[key]) {
        pendingRequests[key] = jqXHR;
    }else{
        jqXHR.abort();    //放弃后触发的提交
//        pendingRequests[key].abort();   // 放弃先触发的提交
    }

    var complete = options.complete;
    options.complete = function(jqXHR, textStatus) {
        pendingRequests[key] = null;
        if ($.isFunction(complete)) {
        	complete.apply(this, arguments);
        }
    };
});


//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//fixed 精度
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2,fixed){
	if(!fixed){
		fixed = 0;
	}
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2))
	return ((arg1*m+arg2*m)/m).toFixed(fixed);
}

//减法函数，用来得到精确的减法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。
//调用：accSub(arg1,arg2)
//返回值：arg1减去arg2的精确结果
function accSub(arg1,arg2,fixed){
	if(!fixed){
		fixed = 0;
	}
	var r1,r2,m,n;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2));
	//last modify by deeka
	//动态控制精度长度
//	n=(r1>=r2)?r1:r2;
	return ((arg1*m-arg2*m)/m).toFixed(fixed);
}

//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//mathType : 0：保留2位小数 1:四舍五入取整  2：向下取整  3：向上取整
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2,mathType){
	if(!mathType){
		mathType = 0;
	}
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length}catch(e){}
	try{t2=arg2.toString().split(".")[1].length}catch(e){}
	with(Math){
	  r1=Number(arg1.toString().replace(".",""))
	  r2=Number(arg2.toString().replace(".",""))
	  var tmp = ((r1/r2)*pow(10,t2-t1)).toFixed(2);
	  if(mathType == 0){
		  return tmp;
	  }else if(mathType == 1){
		  return Math.round(tmp);
	  }else if(mathType == 2){
		  return Math.floor(tmp);
	  }else if(mathType == 3){
		  return Math.ceil(tmp)
	  } 
	}
}


//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2,fixed) {
	if(!fixed){
		fixed = 0;
	}
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){}
	try{m+=s2.split(".")[1].length}catch(e){}
	return  (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(fixed);
}


//重写弹出层
window.alert = function(str){
	layer.alert(str)
}

