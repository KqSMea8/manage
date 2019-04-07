/*
 * public文件
 * 处理系统业务功能的公共处理方法集合,可不断进行添加扩展
 * 
 */

/**
 * 冻结操作功能
 * @param orderNo 订单号
 * @param payType 交易方式
 * @param batchNo 冻结批次号
 */
function handleOrderFreeze(orderNo ,payType ,batchNo ,returnUrl){
	if(orderNo!=null && $.trim(orderNo)!=''
		&&payType !=null &&$.trim(payType)!=''
		&&batchNo !=null &&$.trim(batchNo)!='')
	if(confirm("确认冻结吗?")){
		//查询条件(用于操作成功之后的返回)
		var params = $("#searchForm").serialize();
		
		openLoading("正在处理，请稍等...");
    	$.ajax({
    		type:"post",
    		url:staticPath+"/freeze/handleOrderFreeze",
    		data:{"orderNo":$.trim(orderNo),
    			"payType":$.trim(payType),"batch_no":$.trim(batchNo)},
    		dataType:"json",
    		success:function(res){
    			closeLoading();
    			var obj = eval(res);
    			if(obj.result=='1'){
    				alert("冻结成功");
    				window.location.href = staticPath+returnUrl+"?"+params;
    			}else {
    				alert("冻结失败");
    			}
    		},
    		error:function(e){
    			closeLoading();
    			alert("操作失败");
	    		}
	    	});
		}
}


/**
 * 访问订单/退款单详情信息
 * 注意:此方法为js公共访问方法
 * @param staticPath
 * @param orderNo
 * @param refundNo
 */
function queryOrderOrRefundDetail(orderNo ,refundNo){
	if(orderNo!=null &&orderNo.length>0){
		var parameters = "orderNo="+orderNo;
		if(refundNo!=null &&refundNo.length>0)
			parameters += "&refundNo="+refundNo;
		window.location.href = staticPath+"/order/queryOrderDetail?"+parameters;
	}
}

/**
 * 支付宝签约弹出页面公共方法
 * @param account
 */
function alipaySignProtocolWithPartner(account){
	
	if(account!=null &&account!=''){
		$.ajax({
			type:"post",
			url:staticPath+"finance/queryAlipayTrade",
			data:{"type":"2","accountType":"A","account":account},
			dataType:"json",
			success:function(res){
				var obj = eval(res);
				if(obj.signUrl!=null &&obj.signUrl!=""){
					createNewMarkPage(obj.signUrl);
				}
			}
		});
	}
}

/* 用户锁单(出票订单/退款管理)  */
function lockOrder(staticPath ,orderNo ,refundNo){
	if(orderNo.length>0){
		var urlOrder = staticPath+"/order/handleOrderLock";
		var parameters = "orderNo="+orderno;
		if(refundNo.length>0){
			urlOrder = staticPath+"/refund/handleRefundLock";
			parameters = "orderNo="+orderno+"&refundNo="+refundno;
		}
		$.ajax({
			type: "POST",
			url: urlOrder,
			data: parameters,
			dataType:"json",
			success: function(result){
				var obj = eval(result);
				if(obj.result=="1"){
					queryOrderOrRefundDetail(staticPath ,orderNo ,refundNo);
				}else if(obj.result=="0"){
					alert("订单锁定失败");
				}else if(obj.result=="2"){
					alert("该订单已被"+obj.userLock+"锁定");
					queryOrderOrRefundDetail(staticPath ,orderNo ,refundNo);
				}
			},
			error:function(){
				alert("订单操作失败!");
			}
		}); 
	}
}

/**
 * 适用于各种需要使用DIV元素进行dialog弹出窗口的信息显示
 * 注意:此方法的参数可以不断扩展
 * @param divId 使用元素DIV的id
 * @param w 对话框宽度
 * @param h 对话框高度
 * @param type 1表示查询RT(PNR)信息;2表示查看订座指令信息;3表示查看UN项;4表示查看退票凭证;5表示查看暂不能退废票图片;6表示修改票号;
 * 7表示添加B2B账号;8表示修改B2B账号;9表示添加自动出票规则;10表示修改自动出票规则;11表示查看自动出票规则;12表示添加自愿退票规则;13表示修改自愿退票规则;
 * 14表示查看自愿退票规则;15表示添加联系方式;16表示修改联系方式;17表示添加工作时间;18表示修改工作时间;19表示添加用户;20表示修改用户;
 * 21表示批量修改政策弹窗(政策查询页/当日上传政策页);22表示政策对比(政策查询页/当日上传政策页);
 * @param parameter1 参数一
 * @param parameter2 参数二
 * @param parameter1 参数三
 * @param parameter2 参数四
 */
function displayVariousDialogInfo(divId ,w ,h ,type ,parameter1 ,parameter2 ,parameter3 ,parameter4){
	if(divId!=null &&divId!=''
		&&type!=null &&type!=''){
		
		if(w==null ||w=='')
			w = 600;
		if(h==null ||h=='')
			h = 350;
		
		var title = '';
		var url = null;
		var data = '';
		if(type=='1'){
			title = '查看PNR信息';
			url = "order_queryRt.do?eterm.id="+parameter1+"&eterm.passengerType="+parameter2;
		}else if(type=='2'){
			title = '查看订座指令';
			url = "order_queryReserveInstruct.do?order.orderNo="+parameter1+"&order.cid="+parameter2;
		}else if(type=='3'){
			title = '查看UN项';
			url = "refund_queryUN.do?refund.refundNo="+parameter1;
		}else if(type=='4'){
			title = '查看退票凭证';
			url = "refund_queryRefundCertificate.do?refund.orderNo="+parameter1+"&refund.refundNo="+parameter2;
		}else if(type=='5'){
			title = '查看暂不能图片';
			url = "refund_queryRefundFile.do?refund.orderNo="+parameter1+"&refund.refundNo="+parameter2;
		}else if(type=='6'){
			title = '修改票号';
			url = "order_queryTicketNo.do?passengerInfo.passengerId="+parameter1;
		}else if(type=='7'){
			title = '添加B2B账号';
			url = "auto_addB2BAccount.do";
		}else if(type=='8'){
			title = '修改B2B账号';
			url = "auto_updateB2BAccount.do?id="+parameter1;
		}else if(type=='9'){
			title = "添加规则";
			url = "auto_addRule.do?page="+parameter1;
		}else if(type=='10'){
			title = "修改规则";
			url = "auto_updateRule.do?id="+parameter1+"&page="+parameter2;
		}else if(type=='11'){
			title = "查看规则";
			url = "auto_queryRule.do?id="+parameter1+"&page="+parameter2;
		}else if(type=='12'){
			title = "添加自愿退票规则";
			url = "auto_addRefundRule.do?ghAutoRefundRule.page="+parameter1;
		}else if(type=='13'){
			title = "修改自愿退票规则";
			url = "auto_addRefundRule.do?ghAutoRefundRule.id="+parameter1+"&ghAutoRefundRule.page="+parameter2;
		}else if(type=='14'){
			title = "查看自愿退票规则";
			url = "auto_queryAutoRefundRule.do?ghAutoRefundRule.id="+parameter1;
		}else if(type=='15'){
			title = "添加联系方式";
			url = "user_addContact.do";
		}else if(type=='16'){
			title = "修改联系方式";
			url = "user_updateContact.do";
			data = parameter1;
		}else if(type=='17'){
			title = "添加工作时间";
			url = "user_addWorktime.do?companyId="+parameter1;
		}else if(type=='18'){
			title = "修改工作时间";
			url = "user_updateWorktime.do?companyId="+parameter1+"&dateType="+parameter2;
			if(parameter3!=null && parameter3!=''){
				url = url +"&beginTime="+parameter3 ;
			}
			if(parameter4!=null && parameter4!=''){
				url = url +"&endTime="+parameter4 ;
			}
		}else if(type=='19'){
			title = "添加用户";
			url = "user_addUser.do";
		}else if(type=='20'){
			title = "修改用户";
			url = "user_updateUser.do?id="+parameter1;
		}else if(type=='21'){
			title = "批量修改政策项";
			url = "rate_batchEditRateDialog.do";
		}else if(type=='22'){
			title = "政策查询维护之政策对比";
			url = "rate_queryCompareRate.do";
			data = parameter1;
		}else if(type=='23'){
			title = "供应商配置信息";
			url = staticPath + "user/providerConfig";
			data = "id="+parameter1+"&flag="+parameter2;
		}
		
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
				if(type=='7' || type=='19'){
					$("#"+parameter1).focus();
				}else if(type=='8' || type=='20'){
					$("#"+parameter2).focus();
				}else if(type=='22'){
					$("#compareRate_"+parameter2).focus();
				}
				
				$("#"+divId).dialog("open");
			});
		}
	}
}

/**
 * 获取正则表达式
 * 注意:此正则表达式为通用,当遇到特殊情况,请另行处理
 * @param type 1表示出发到达城市;2表示航司;3表示舱位;4表示返点;5表示OFFICE;
 * 		6表示自动出票Z值;7表示自动出票支付账号;8表示订单号;9表示PNR;10表示票号;
 * 		11表示用户注册负责人;12表示手机号码;13表示支付宝账号;14表示公司名称;15表示用户名;
 * 		16表示用户密码;17表示金额;18表示政策添加舱位;19表示贴点;20表示数字
 * @returns
 */
function getReg(type){
	var reg = null;
	if(type==1)
		reg = /^(([a-zA-Z]{3}\/)*)?([a-zA-Z]{3}(\/)?)$/;
	else if(type==2)
		reg = /^([a-zA-Z]{2}|[0-9]{1}[a-zA-Z]{1}|[a-zA-Z]{1}[0-9]{1})$/;
	else if(type==3)
		reg = /^([a-zA-Z](1)?\/)*?([a-zA-Z]{1}(1)?(\/)?)$/;
	else if(type==4)
		reg = /^(-)?(0+|0(\.[0-9]{1})|[1-9]\d?(\.\d{1})?)$/;
	else if(type==5)
		reg = /^[a-zA-Z]{3}[0-9]{3}$/;
	else if(type==6)
		reg = /^[0-9]\d?(\.\d{1})?$/;
	else if(type==7)
		reg = /^1[0-9]{10}|[1-9][0-9]{4,}|(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/;
	else if(type==8)
		reg = /^[a-zA-Z]{2}[0-9]{18}$/;
	else if(type==9)
		reg = /^[0-9a-zA-Z]{6}$/;
	else if(type==10)
		reg = /^((\d{13})|(\d{3}-\d{10}))$/;
	else if(type==11)
		reg = /^([a-zA-Z]{2,10}|[\u4e00-\u9fa5]+)$/;
	else if(type==12)
		reg = /^1[0-9]{10}$/;
	else if(type==13)
		reg = /^1[0-9]{10}|[1-9][0-9]{4,}|(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/;
	else if(type==14)
		reg = /^([0-9a-zA-Z]|[\u4e00-\u9fa5]){4,}$/;
	else if(type==15)
		reg = /^[0-9a-zA-Z]{6,16}$/;
	else if(type==16)
		reg = /^[0-9a-zA-Z]{6,16}$/;
	else if(type==17)
		reg = /^\d*(\.\d*)?$/;
	else if(type==18)
		reg = /^[a-zA-Z]{1}(1)?$/;
	else if(type==19)
		reg = /^((\+)\d)?\d*$/;
	else if(type==20)
		reg = /^(-)?\d+$/;
	return reg;
}