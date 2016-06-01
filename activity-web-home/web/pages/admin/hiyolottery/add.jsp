<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加萃泽红包活动</title>
	<style type="text/css">

		.preorderTable{
			line-height:35px;
			text-align:center; 
			margin-left:auto; 
			margin-right:auto; 
		}
		
		.preorderTable td{
			text-align: left;
		}
		
		.preorderTable td{
			padding:5px;
		}
		
		td.tip{
			font-size:12px;
			line-height: 20px;
			text-align: left;
			
		}
		
		.preorderTable td:first-child{
			text-align: right;
			margin-right:5px;
		}
		
		fieldset{
			margin:10px;
		}
	</style>
	
</head>
<body>

	<form id="lotteryForm" method="post">
		<fieldset >
			<legend>红包类型</legend>
			<table class="preorderTable">
				<colgroup>
					<col width="140"/>
					<col width="210"/>
					<col width="200"/>
				</colgroup>
				<tr>
					<td>
						红包类型
					</td>
					<td>
						<select class="easyui-combobox" name="amountType" style="width:200px;height:32px;">
							<option value="FIX" selected="selected">金额固定红包</option>
							<option value="RANDOM">随机金额红包</option>
						</select>
					</td>
					<td class="tip">
						暂支持固定红包，每份金额必须在1-200元（包含）之间<!-- ，裂变红包可分享红包给好友，无关注公众号能力 -->
					</td>
				</tr>
				<tr>
					<td>
						每个红包最小金额
					</td>
					<td>
						<input id="perMinAmount" type="text" name="perMinAmount" class="easyui-numberbox"  style="width:200px;height:32px;"
							value="100" data-options="min:100,precision:0,validType:'validatePerAmountMinValue'" required="required">
					</td>
					<td class="tip">
						<font color="red">分</font>
						（在100-20000分之间）
					</td>
				</tr>
				<tr>
					<td>
						每个红包最大金额
					</td>
					<td>
						<input id="perMaxAmount" type="text" name="perMaxAmount" class="easyui-numberbox"  style="width:200px;height:32px;"
							value="100" data-options="min:100,max:20000,precision:0,validType:'validatePerAmountMaxValue'" required="required">
					</td>
					<td class="tip">
						<font color="red">分</font>
						（在100-20000分之间）
					</td>
				</tr>
				<tr>
					<td>
						红包最大容量
					</td>
					<td>
						<input id="total"type="text" name="total" class="easyui-numberbox"  style="width:200px;height:32px;"
							value="1" data-options="min:1,precision:0,onChange:calculateProbability,validType:'validateMaxValue'" required="required">
					</td>
					<td class="tip">
						红包活动最大容纳的红包数量
					</td>
				</tr>
				<tr>
					<td>
						发放总人数
					</td>
					<td>
						<input id="totalTicket" type="text" name="totalTicket" class="easyui-numberbox"  style="width:200px;height:32px;"
							value="1" data-options="min:1,precision:0,onChange:calculateProbability,validType:'validateMinValue'" required="required">
					</td>
					<td class="tip">
						红包活动的红包数量，必须小于或等于红包最大容量
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">中奖概率</font>
					</td>
					<td>
						<input id="probability" type="text"  class="easyui-numberbox"  style="width:200px;height:32px;"
							value="1" data-options="suffix:'%'" readonly="readonly">
					</td>
					<td class="tip">
						概率 = 发放总人数/红包最大容量，当二者相等时，概率为100%
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset >
			<legend>红包信息</legend>
			<table class="preorderTable">
				<colgroup>
					<col width="140"/>
					<col width="210"/>
					<col width="200"/>
				</colgroup>
				<tr>
					<td>
						活动名称
					</td>
					<td>
						<input  type="text" class="easyui-textbox" name="title" 
							 style="width:200px;height:32px;" required="required"
							 data-options="validType:'validateChLength[1,12]'">
					</td>
					<td class="tip">
						最长6个汉字，12个英文字母
					</td>
				</tr>
				<tr>
					<td>
						活动描述
					</td>
					<td>
						<input  type="text" class="easyui-textbox" name="description" 
							 style="width:200px;height:32px;" required="required"
							  data-options="validType:'validateChLength[1,14]'">
					</td>
					<td class="tip">
						最长7个汉字，14个英文字母
					</td>
				</tr>
				<tr>
					<td>
						抽奖开关
					</td>
					<td>
						<select class="easyui-combobox" name="status" style="width:200px;height:32px;">
							<option value="1" selected="selected">开</option>
							<option value="0">关</option>
						</select>
					</td>
					<td class="tip">
						默认开启活动
					</td>
				</tr>
				<tr>
					<td>
						活动开始时间
					</td>
					<td>
						<input id="beginTime" type="text" class="easyui-datetimebox" name="beginTime" 
							 style="width:200px;height:32px;" required="required">
					</td>
					<td class="tip">
					</td>
				</tr>
				<tr>
					<td>
						活动结束时间
					</td>
					<td>
						<input id="expireTime" type="text" class="easyui-datetimebox" name="expireTime" 
							 style="width:200px;height:32px;" required="required">
					</td>
					<td class="tip">
						红包活动有效期最长为91天 
					</td>
				</tr>
				<tr>
					<td>
						祝福语
					</td>
					<td>
						<input  type="text" class="easyui-textbox" name="wishing" 
							 style="width:200px;height:32px;" required="required" data-options="validType:['length[1,16]']">
					</td>
					<td class="tip">
						最大长度为16
					</td>
				</tr>
				
				
			</table>
		</fieldset>	
		<table class="preorderTable">
			<colgroup>
				<col width="140"/>
				<col width="210"/>
				<col width="200"/>
			</colgroup>
			<tr>
				<td>
				</td>
				<td colspan="2" align="center">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" style="width:100px;height:32px" onclick="submit()">添加</a>
				</td>		
			</tr>
		</table>
	</form>

	<script type="text/javascript">
	
		function calculateProbability(){
			var totalTicket = $("#totalTicket").val();
			var total = $("#total").val();
			if (totalTicket != null && totalTicket!='' && totalTicket!='null'
					&& total != null && total!='' && total!='null') {
				var res = (totalTicket / total).toFixed(4) * 100;
				$("#probability").textbox("setValue", res + "%");  
			}
			
			//$('#lotteryForm').form("validate");
		}
		
		
	    $.extend($.fn.validatebox.defaults.rules, {
	    	validateChLength: {
	    		validator: function(value, param){
	    			var curLen = value.replace(/[^\x00-\xff]/g,"xx").length;
	    			if (curLen > param[1] || curLen <param[0]) {
	    				return false;
	    			} else {
	    				return true;
	    			}
	    		},
	    		message: '最长{1}个英文字母，中文为两个字符'
	        },
	        validateMaxValue:{
	        	validator: function(value, param){
	    			// 校验最大值
	        		var totalTicket = $("#totalTicket").val();
	    			var isValid = parseInt(totalTicket) < parseInt(value) || parseInt(totalTicket) == parseInt(value);
	        		
	        		return isValid;
	    		},
	    		message: '不能小于红包最小容量'
	        },
	        validateMinValue:{
	        	validator: function(value, param){
	    			// 校验最小值
	        		var total = $("#total").val();
	        		var isValid = parseInt(total) > parseInt(value) || parseInt(total) == parseInt(value);
	        		
	    			return isValid;
	    		},
	    		message: '不能超过红包最大容量'
	        },
	        validatePerAmountMaxValue:{
	        	validator: function(value, param){
	    			// 校验最大值
	        		var perMinAmount = $("#perMinAmount").val();//alert(value);alert(total);
	    			if (parseInt(perMinAmount) > parseInt(value)) {
	    				return false;
	    			} else {
	    				return true;
	    			}
	    		},
	    		message: '最大金额不能小于最小金额'
	        },
	        validatePerAmountMinValue:{
	        	validator: function(value, param){
	    			// 校验最大值
	        		var perMaxAmount = $("#perMaxAmount").val();//alert(value);alert(total);
	    			if (parseInt(perMaxAmount) < parseInt(value)) {
	    				return false;
	    			} else {
	    				return true;
	    			}
	    		},
	    		message: '最小金额不能大于最大金额'
	        }
	    });
	
		$(function(){
			// window里面使用该方法有问题？
			//$('#beginTime').datetimebox('setValue', '6/1/2012 12:30:56');
			$('#lotteryForm').form({
			    url: '${ctx}' + '/lottery/add',
			    onSubmit: function(){	
			    },
			    success:function(data){	    	
					if (data != null ) {
						var res = JSON.parse(data);
						if (res.success) {
							$.messager.alert('提示','添加红包活动成功','info');
							$('#lotteryForm').form("reset");
						} else {
							$.messager.alert('异常',res.resultMsg,'error');
						}
					} else {
						$.messager.alert('异常','添加红包活动异常!','error');
					}
			    }
			});
		}); 
	
		function submit(){
			var isValid = $('#lotteryForm').form("validate");
			if (isValid) {
				$('#lotteryForm').submit();
			}	
		}
	</script>
</body>
</html>