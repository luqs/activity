<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>红包预下单</title>
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
		}
		
	</style>
	
</head>
<body>
	<form id="preorderForm" method="post">
		<table class="preorderTable">
			<colgroup>
				<col width="140"/>
				<col width="300"/>
				<col width="200"/>
			</colgroup>
			<tr>
				<td>
					红包类型
				</td>
				<td>
					<select class="easyui-combobox" name="hbType" style="width:200px;height:32px;">
						<option value="NORMAL" selected="selected">普通红包</option>
						<option value="GROUP">裂变红包</option>
					</select>
				</td>
				<td class="tip">
					裂变红包可分享红包给好友，无关注公众号能力
				</td>
			</tr>
			<tr>
				<td>
					红包金额设置方式
				</td>
				<td>
					<input id="amtType" type="text" class="easyui-textbox" name="amtType" 
						value="ALL_RAND" style="width:200px;height:32px;" readonly="readonly">
				</td>
				<td class="tip">
					只对裂变红包生效。ALL_RAND—全部随机 
				</td>
			</tr>
			<tr>
				<td>
					总金额
				</td>
				<td>
					<input type="text" name="totalAmount" class="easyui-numberbox"  style="width:200px;height:32px;"
						value="100" data-options="min:100,precision:0" required="required">
				</td>
				<td class="tip">
					总付款金额，单位分 
				</td>
			</tr>
			<tr>
				<td>
					红包发放总人数
				</td>
				<td>
					<input type="text" name="totalNum" class="easyui-numberbox" style="width:200px;height:32px;"
						value="1" data-options="min:1,precision:0" required="required">
				</td>
				<td class="tip">
					总共有多少人可以领到该组红包（包括分享者）。普通红包1，裂变红包必须大于1。
				</td>
			</tr>
			<tr>
				<td>
					红包祝福语
				</td>
				<td>
					<input class="easyui-textbox" name="wishing" style="width:200px;height:32px;" required="required"
							data-options="validType:['length[1,16]']">
				</td>
				<td class="tip">
					展示在红包页面
				</td>
			</tr>
			<tr>
				<td>
					活动名称
				</td>
				<td>
					<input class="easyui-textbox" name="actName" style="width:200px;height:32px;" required="required"
							data-options="validType:['length[1,16]']">
				</td>
				<td class="tip">
					在不支持原生红包的微信版本中展示在红包消息 
				</td>
			</tr>
			<tr>
				<td>
					备注
				</td>
				<td>
					<input class="easyui-textbox" name="remark" style="width:200px;height:32px;"required="required"
							data-options="validType:['length[1,16]']">
				</td>
				<td class="tip">
					在不支持原生红包的微信版本中展示在红包消息
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td colspan="2" align="center">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" style="width:100px;height:32px" onclick="submit()">预下单提交</a>
				</td>		
			</tr>
		</table>
	</form>


	<script type="text/javascript">
		$(function(){
			//$('#amtType').textbox('setValue', 'ALL_RAND');
			$('#preorderForm').form({
			    url: '${ctx}' + '/wxhb/preorder/add',
			    onSubmit: function(){	
			    },
			    success:function(data){  	
					if (data != null ) {
						var res = JSON.parse(data);
						if (res.success) {
							$.messager.alert('提示','预下单成功','info');
						} else {
							$.messager.alert('异常',res.resultMsg,'error');
						}
					} else {
						$.messager.alert('异常','红包预下单异常!','error');
					}
			    }
			});
		}); 
	
		// 表单提交
		function submit(){
			var isValid = $('#preorderForm').form("validate");
			if (isValid) {
				$('#preorderForm').submit();
			}
		}
	</script>
</body>
</html>