<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>红包活动详情</title>
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
					抽奖活动名称
				</td>
				<td>
					<input class="easyui-textbox" name="title" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					抽奖活动描述
				</td>
				<td>
					<input class="easyui-textbox" name="description" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					抽奖活动开始时间
				</td>
				<td>
					<input class="easyui-textbox" name="beginTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					抽奖活动结束时间
				</td>
				<td>
					<input class="easyui-textbox" name="expireTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					微信红包活动ID
				</td>
				<td>
					<input class="easyui-textbox" name="lotteryId" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					红包活动生成结果
				</td>
				<td>
					<input class="easyui-textbox" name="result" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					活动状态
				</td>
				<td>
					<input class="easyui-textbox" name="statusStr" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					红包活动创建时间
				</td>
				<td>
					<input class="easyui-textbox" name="createTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
				<td>
					红包活动更新时间
				</td>
				<td>
					<input class="easyui-textbox" name="updateTime" style="width:250px;height:32px;" readonly="readonly">
				</td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		
		function loadForm(id){
			if (id != null && id !='' && id !='null') {
				$('#detailForm').form(
					'load', 
					'${ctx}' + '/wxhb/lottery/detail/' + id
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