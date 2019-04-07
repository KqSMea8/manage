/*
 * common20150210文件
 * 处理页面元素的公共处理方法集合,可不断进行添加扩展
 * 
 */
$(function(){
	initLoading();
	$.fn.tipsy.defaults = {
		    delayIn: 0,      // delay before showing tooltip (ms)
		    delayOut: 0,     // delay before hiding tooltip (ms)
		    fade: false,     // fade tooltips in/out?
		    fallback: '',    // fallback text to use when no tooltip text
		    gravity: 'n',    // gravity
		    html: false,     // is tooltip content HTML?
		    live: true,     // use live event support?
		    offset: 0,       // pixel offset of tooltip from element
		    opacity: 0.8,    // opacity of tooltip
		    title: 'title',  // attribute/callback containing tooltip text
		    trigger: 'hover' // how tooltip is triggered - hover | focus | manual
	};
	$(".tooltip").tipsy();
});
function initLoading() {
	$("#_loading").dialog({
		resizable : false,
		autoOpen : false,
		height : 65,
		width : 300,
		modal : true
	});
	$("#_loading").prev(".ui-dialog-titlebar").hide();
}
/*
 * 城市(机场)多选控件
 * inputId,选择完成之后，要把城市写到哪个input
 */
var _cityMultiSelectDialogDiv = "_cityMultiSelectDiv";

function _cityMultiSelectDialog(inputId){
	var inputText = $.trim($("#"+inputId).val());
	$("#"+_cityMultiSelectDialogDiv).empty().load("airportMutiSelect",{'airPortCodes':inputText},function(){
		$("#_cityMap_confirm_btn").bind("click",function(){
			_cityMap_confirm(inputId);
			$(document).unbind("keypress");
			$("#"+_cityMultiSelectDialogDiv).empty().dialog("close");
		});
		$("#_cityMap_cancel_btn").bind("click",function(){
			$(document).unbind("keypress");
			$("#"+_cityMultiSelectDialogDiv).empty().dialog("close");
		});
		_cityMultiSelectDialogInit();
		$("#"+_cityMultiSelectDialogDiv).dialog("open");
	});
}

function _cityMultiSelectDialogInit(){
	$("#"+_cityMultiSelectDialogDiv).dialog({
		 autoOpen: false,
		 width: 750,
		 height: 400,
		 modal: true,
		 close: function() {
			$(document).unbind("keypress");
			$("#"+_cityMultiSelectDialogDiv).empty().dialog("close");
		 }
	});
	$("#"+_cityMultiSelectDialogDiv).prev(".ui-dialog-titlebar").hide();
}

function _cityMultiSelectDiv (divId,inputId){
	if($("#_cityMap_confirm_btn").length){
		$("div.select-city-wrap").parent("div").empty();
	}
	$("#"+divId).empty().load("airportMutiSelect",function(){
		$("#_cityMap_confirm_btn").bind("click",function(){
			_cityMap_confirm(inputId);
			$("#"+divId).empty().hide();
		});
		$("#_cityMap_cancel_btn").bind("click",function(){
			$(document).unbind("keypress");
			$("#"+divId).empty().hide();
		});
		$("#"+divId).show();
	});
}
function openLoading(message) {
	$("#loadingMessage").html(message);
	$("#_loading").dialog("open");
}
function closeLoading() {
	$("#_loading").dialog("close");
}
function goHomepage(){
	window.location.href=staticPath+"common/home";
}
/*显示城市中文名 开始*/
//依赖cityname.js  引用时确保已引用
function getCityName(cityStr,id){
    if(cityStr.lastIndexOf("/") == cityStr.length-1){//若三字码串以/结尾，除去末尾的/
        cityStr = cityStr.substr(0,cityStr.length-1);
    }
    var cityArray = cityStr.split("/");
    var cityNameStr = "";
    for(var i = 0;i<cityArray.length;){//根据三字码组装中文名
        for(var j = 0;j<citysname.length;){
            var newArray = citysname[j].split("|");
            if($.trim(cityArray[i]) == newArray[3]){
                cityNameStr = cityNameStr.concat(newArray[0]).concat("[").concat(newArray[3]).concat("]");
                if(cityArray.length-1 != i){
                   cityNameStr = cityNameStr.concat("/");
                }
            }
            j=j+1;
        }
        i=i+1;
    }
    var cityArrayCn = cityNameStr.split("/");
    var element = $("#"+id);
    if(cityArrayCn.length==1||cityArrayCn.length==0){
    	element.text(cityNameStr);
    }else{//若不止一个城市，取第一个城市显示，若是全国，显示“全国”
    	if(cityArrayCn.length == citysname.length){
    		element.text("全国");
        }else{
        	element.text(cityArrayCn[0].concat("..."));
	    	element.attr("title",cityNameStr);
        }
    }
}
//以空格作为分隔
function getCityNameNew(cityStr,id){
    var cityArray = $.trim(cityStr).split(" ");
    var cityNameStr = "";
    for(var i = 0;i<cityArray.length;){//根据三字码组装中文名
        for(var j = 0;j<citysname.length;){
            var newArray = citysname[j].split("|");
            if($.trim(cityArray[i]) == newArray[3]){
                cityNameStr = cityNameStr.concat(newArray[0]).concat("[").concat(newArray[3]).concat("]");
                if(cityArray.length-1 != i){
                   cityNameStr = cityNameStr.concat(" ");
                }
            }
            j=j+1;
        }
        i=i+1;
    }
    var cityArrayCn = cityNameStr.split(" ");
    var element = $("#"+id);
    if(cityArrayCn.length==1||cityArrayCn.length==0){
    	element.text(cityNameStr);
    }else{//若不止一个城市，取第一个城市显示，若是全国，显示“全国”
    	if(cityArrayCn.length == citysname.length){
    		element.text("全国");
        }else{
        	element.text(cityArrayCn[0].concat("..."));
        	element.attr("title",cityNameStr);
        }
    }
}
/*显示城市中文名 结束*/
/*显示航空公司中文名 开始*/
//依赖aircompany.js  引用时确保已引用
function getAircomName(key,id){
	    var airComStr = "";
        for(var j = 0;j<aircomArray.length;){
            var newArray = aircomArray[j].split("|");
            if($.trim(key) == newArray[3]){
                airComStr = airComStr.concat(newArray[0]).concat("[").concat(newArray[3]).concat("]");
            }
            j=j+1;
        }
        $("#"+id).text(airComStr);
}
/*显示航空公司中文名 结束*/
/* 公共form表单提交时,去空操作，即trim  开始 */
function formToTrim(formId){
	if(formId.length==0){
		return false;
	}
	var arrayObj = $("#"+formId+" input[type='text']");
	if(arrayObj.length>0){
		$(arrayObj).each(function(){
			var value = $.trim($(this).val());
			$(this).val(value);
		});
	}
	return true;
}
/* 公共form表单提交时,去空操作，即trim  结束 */
/*清空表单数据 开始*/
function clearForm(formId){
	 var inputObjs=jQuery("#"+formId+" input[type='text']"); 
	 for(var i=0;i<inputObjs.length;){
		var inputObj = inputObjs[i]; 
		inputObj.value="";
		i=i+1;
	 } 
	 var selectObjs = jQuery("#"+formId+" select");
	 for(var i=0;i<selectObjs.length;){
		var selectObj = selectObjs[i]; 
		selectObj.value="";
		i=i+1;
	 }
}
/*清空表单数据 结束*/
var ajaxPagingCallBack = function (){};
function gotoPage(val){
	switch(val){
		case 'first':gotoPageFirst(ajaxPagingCallBack);
		break;
		case 'next':gotoPageNext(ajaxPagingCallBack);
		break;
		case 'prev':gotoPagePrev(ajaxPagingCallBack);
		break;
		case 'last':gotoPageLast(ajaxPagingCallBack);
		break;
		case 'sure':gotoButtonClicked(ajaxPagingCallBack);
		break;
		case 'size':gotoPageIndex(1,ajaxPagingCallBack);
		break;
		default:gotoPageIndex(val,ajaxPagingCallBack);
	}
}
/*表单增加每页条数隐藏域   formName表单ID*/
function addAjaxPageSize(formName){
	if($("a[id='pageShowIDO']") != null){
		var curPageIndex = 1;
		$("a[id='pageShowIDO']").each(function(){
			if($(this).attr("class") == 'current'){
				curPageIndex = $(this).text();
			}
		});
		if($("#"+formName).find("input[name='pageIndex']").length){
			$("#"+formName).find("input[name='pageIndex']").val(curPageIndex);
		}else{
			$("#"+formName).append("<input type='hidden' name='pageIndex' value='"+curPageIndex+"'/>");
		}
	}
	var selectObj = document.getElementById("everyPageRecordCount");
	if(selectObj != null){
		var everyPageRecordCount = selectObj.options[selectObj.options.selectedIndex].value;
		if($("#"+formName).find("input[name='pageSize']").length){
			$("#"+formName).find("input[name='pageSize']").val(everyPageRecordCount);
		}else{
			$("#"+formName).append("<input type='hidden' name='pageSize' value='"+everyPageRecordCount+"'/>");
		}
	}
}
/*****Tab选项卡效果 start*/
function tabSelect(id){
	var titId=$('#'+id+' .tab-tit-mod li');
	var tabList=$('#'+id+' .tab-con-mod').children('.tab-list');
	for(var i=0;i<titId.length;i++){
		titId[i].onclick=function(){
			$(this).addClass('current').siblings().removeClass('current');
			$(tabList).eq($(this).index()).addClass('current-tab').siblings().removeClass('current-tab');
		};
	}
}
/*****Tab选项卡效果 end*/

//此方法字符串中将'/'线,变为空格
function getCityName2(cityStr,id){
    if(cityStr.lastIndexOf("/") == cityStr.length-1){//若三字码串以/结尾，除去末尾的/
        cityStr = cityStr.substr(0,cityStr.length-1);
    }
    var cityArray = cityStr.split("/");
    var cityNameStr = "";
    for(var i = 0;i<cityArray.length;){//根据三字码组装中文名
        for(var j = 0;j<citysname.length;){
            var newArray = citysname[j].split("|");
            if(cityArray[i] == newArray[3]){
                cityNameStr = cityNameStr.concat(newArray[0]).concat("[").concat(newArray[3]).concat("]");
                if(cityArray.length-1 != i){
                   cityNameStr = cityNameStr.concat("/");
                }
            }
            j=j+1;
        }
        i=i+1;
    }
    var cityArrayCn = cityNameStr.split("/");
    var element = $("#"+id);
    if(cityArrayCn.length==1||cityArrayCn.length==0){
    	element.text(cityNameStr.replace(/\//g, ' '));
    }else{//若不止一个城市，取第一个城市显示，若是全国，显示“全国”
    	if(cityArrayCn.length == citysname.length){
    		element.text("全国");
        }else{
        	element.text(cityArrayCn[0].concat("..."));
	    	element.attr("title",cityNameStr.replace(/\//g, ' '));
        }
    }
}
// 此方法字符串中将'/'线,变为空格
function getCityNameForInput(cityStr,inputId){
	if(cityStr.lastIndexOf("/") == cityStr.length-1){//若三字码串以/结尾，除去末尾的/
		cityStr = cityStr.substr(0,cityStr.length-1);
	}
	var cityArray = cityStr.split("/");
	var cityNameStr = "";
	for(var i = 0;i<cityArray.length;){//根据三字码组装中文名
		for(var j = 0;j<citysname.length;){
			var newArray = citysname[j].split("|");
			if(cityArray[i] == newArray[3]){
				cityNameStr = cityNameStr.concat(newArray[0]);
			}
			j=j+1;
		}
		i=i+1;
	}
	$("#"+inputId).val(cityNameStr);
}

//依赖aircompany.js  引用时确保已引用
function getAircomNameForInput(key,inputId){
    var airComStr = "";
    for(var j = 0;j<aircomArray.length;){
        var newArray = aircomArray[j].split("|");
        if($.trim(key) == newArray[3]){
            airComStr = airComStr.concat(newArray[0]);
        }
        j=j+1;
    }
    $("#"+inputId).val(airComStr);
}

/**
 * 浏览器生成新标签公共方法
 * 注意：此方法适用于除ie、firefox浏览器之外的浏览器
 * 
 * @param url 传递链接
 */
function createNewMarkPage(url){
	if(url!=null &&url!=''){
		var sys = judgeBrower();
		if(sys.ie || sys.firefox){
			window.open(url ,"" ,'height=1000,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
		}else {
			var a = $("<a href='"+url+"' target='_blank'>Apple</a>").get(0);  
			var e = document.createEvent('MouseEvents');  
			e.initEvent('click', true, true);  
			a.dispatchEvent(e);
		}
	}
}

/**
 * 判断浏览器类型和版本
 * 判断某种浏览器只需用if(Sys_brower.ie)或if(Sys_brower.firefox)等形式
 * 判断浏览器版本只需用if(Sys_brower.ie == '8.0')或if(Sys_brower.firefox == '3.0')等形式
 * 
 * @returns {___anonymous2505_2506}
 */
function judgeBrower(){
	var Sys_brower = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys_brower.ie = s[1] :
		(s = ua.match(/firefox\/([\d.]+)/)) ? Sys_brower.firefox = s[1] :
			(s = ua.match(/chrome\/([\d.]+)/)) ? Sys_brower.chrome = s[1] :
				(s = ua.match(/opera.([\d.]+)/)) ? Sys_brower.opera = s[1] :
					(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys_brower.safari = s[1] : 0;
					return Sys_brower;
}
