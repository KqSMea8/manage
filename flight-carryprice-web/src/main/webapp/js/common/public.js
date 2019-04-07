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
 * 适用于订单各种需要使用DIV元素进行dialog弹出窗口的信息显示
 * 注意:此方法的参数可以不断扩展
 * @param divId 使用元素DIV的id
 * @param parameter1 参数一
 * @param parameter2 参数二
 * @param w 对话框宽度
 * @param h 对话框高度
 * @param type 1表示查询RT(PNR)信息;2表示查看订座指令信息;3表示查看UN项;4表示查看退票凭证;5表示查看暂不能退废票图片
 */
function displayVariousDialogInfo(divId ,parameter1 ,parameter2 ,w ,h ,type){
	if(divId!=null &&divId!=''
		&&parameter1!=null &&parameter1!=''
			&&parameter2!=null &&parameter2!=''
				&&type!=null &&type!=''){
		
		if(w==null ||w=='')
			w = 600;
		if(h==null ||h=='')
			h = 350;
		
		var title = '';
		var url = null;
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
		}
		
		if(url!=null &&url!=''){
			$("#"+divId).empty();
			$("#"+divId).load(url ,function(){
				$("#"+divId).dialog({
					title : title,
					width : w,
					height : h,
					autoOpen : false ,
					modal : true,
					close:function(){
						$(this).dialog("close");
					}
				});
				$("#"+divId).dialog("open");
			});
		}
	}
}