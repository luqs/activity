<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>红包预下单详情</title>
	<style type="text/css">
		.detail_table{
			line-height:35px;
		}
		
		.detail_table td{
			padding:5px;
		}
		
	</style>
	
</head>
<body>

	<form id="detailForm" method="get">
		<table class="detail_table">
			<colgroup>
				<col width="140"/>
				<col width="350"/>
				<col width="140"/>
				<col width="350"/>
			</colgroup>
			
			<tr>
				<td>
					商户号
				</td>
				<td>
					<input class="easyui-textbox" name="mchId" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					商户订单号
				</td>
				<td>
					<input class="easyui-textbox" name="mchBillNo" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					商户名称
				</td>
				<td>
					<input class="easyui-textbox" name="mchName" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					红包类型
				</td>
				<td>
					<input class="easyui-textbox" name="hbType" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					总付金额
				</td>
				<td>
					<input class="easyui-textbox" name="totalAmount" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					总人数
				</td>
				<td>
					<input class="easyui-textbox" name="totalNum" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					红包金额设置方式
				</td>
				<td>
					<input class="easyui-textbox" name="amtType" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					红包祝福语
				</td>
				<td>
					<input class="easyui-textbox" name="wishing" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					活动名称
				</td>
				<td>
					<input class="easyui-textbox" name="actName" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					备注信息
				</td>
				<td>
					<input class="easyui-textbox" name="remark" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					红包内部订单号
				</td>
				<td>
					<input class="easyui-textbox" name="detailId" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					红包发放时间
				</td>
				<td>
					<input class="easyui-textbox" name="sendTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td>
					创建时间
				</td>
				<td>
					<input id="createTime" class="easyui-textbox" name="createTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					更新时间
				</td>
				<td>
					<input id="updateTime" class="easyui-textbox" name="updateTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					预下单结果
				</td>
				<td>
					<input class="easyui-textbox" name="result" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					失效时间
				</td>
				<td>
					<input id="expireTime" class="easyui-textbox" name="expireTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					ticket
				</td>
				<td colspan="3">
					<input class="easyui-textbox" data-options="multiline:true"  name="ticket" style="width:740px;height:100px;"  readonly="readonly">
				</td>
				
			</tr>
			
		</table>
		
	</form>


	<script type="text/javascript">
		// 加载表单
		function loadForm(id){
			if (id != null && id !='' && id !='null') {
				$('#detailForm').form(
					'load', 
					'${ctx}' + '/wxhb/preorder/detail/' + id
				);
			}	
		}
		
		$(function(){
			var id = '${param.id}';
			loadForm(id);
		});
	
	</script>
</body>
</html>