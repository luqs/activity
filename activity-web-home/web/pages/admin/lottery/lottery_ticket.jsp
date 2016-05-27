<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>录入红包信息</title>
	<style type="text/css">
		.easyui-linkbutton{
			margin:0px 5px;
		}
		
		.placeHolder{
			margin:10px 0px;
		}
		
		body{margin:0px; padding:10px;}
	</style>
</head>
<body>
	
	<table id="lotteryTicketGrid" class="easyui-datagrid" title="已录入红包列表" style="height:350px;"
			data-options="rownumbers:true,singleSelect:true,collapsible:false,url:'${ctx}/wxhb/lottery/query-bind-ticket',method:'get',pagination:true,pageSize:10,fitColumns:false,
					queryParams:{hbLotteryId:${param.id}}">
		<thead>
			<tr>
				<th data-options="field:'hbLotteryId',align:'center',width:60">红包活动ID</th>
				<th data-options="field:'title',align:'center',width:100">抽奖活动名称</th>
				<th data-options="field:'ticket',align:'center',width:300">Ticket</th>
				<th data-options="field:'createTime',align:'center',formatter:dateFormatter,width:150">录入时间</th>
				<th data-options="field:'updateTime',align:'center',formatter:dateFormatter,width:150">更新时间</th>	
			</tr>
		</thead>
	</table>
	
	<div class="placeHolder">&nbsp;</div>
	
	<table id="unbindTicketGrid" class="easyui-datagrid" title="未使用的红包列表" style="height:350px;"
			data-options="rownumbers:true,singleSelect:false,collapsible:false,url:'${ctx}/wxhb/lottery/query-unbind-ticket',method:'get',pagination:true,fitColumns:false,
					queryParams:{hbLotteryId:${param.id}},checkOnSelect:true,toolbar:'#bindToolBar'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',align:'center',width:60">预下单ID</th>
				<th data-options="field:'hbType',align:'center',width:100,formatter:renderHbType">红包类型</th>
				<th data-options="field:'totalAmount',align:'center',width:100">总金额(分)</th>
				<th data-options="field:'totalNum',align:'center',width:100">红包总发放人数</th>
				<th data-options="field:'ticket',align:'center',width:300">Ticket</th>
				<th data-options="field:'createTime',align:'center',formatter:dateFormatter,width:150">创建时间</th>
				<th data-options="field:'expireTime',align:'center',formatter:dateFormatter,width:150">失效时间</th>	
			</tr>
		</thead>
	</table>
	
	<div id="bindToolBar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="bind()">录入红包信息</a>
	</div>
	
	<script type="text/javascript">
		var selectedTicketArray = [];
		var hbLotteryId = '${param.id}';
		// 绑定红包到该活动
		function bind(){
			selectedTicketArray = [];
			var rows = $('#unbindTicketGrid').datagrid('getSelections');
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				selectedTicketArray.push(row.ticket);
			}
			//alert(ss);
			
			if (selectedTicketArray.length > 0 && hbLotteryId != null && hbLotteryId !='null') {
				// 绑定
				var param = {};
				param.hbLotteryId = hbLotteryId;
				param.ticket = selectedTicketArray;
				
				$.messager.confirm("提示", "确定录入"+selectedTicketArray.length+"个红包吗？", function (data) {
		            if (data) {
						$.ajax({
		    				type:'post',
		    				url: '${ctx}' + '/wxhb/lottery/set-prize-bucket',
		    				data: JSON.stringify(param),
		    				dataType: "json",
		    				contentType: "application/json; charset=utf-8",
		    				success:function(data){
		    					if (data != null ) {
		    						if (data.success) {
		    							// 成功
		    							$.messager.alert('提示','录入红包信息成功','info', function (){
		    								queryAllGrid();
		    							});
		    						} else {
		    							// 失败
		    							$.messager.alert('异常',data.resultMsg,'error');
		    						}
		    					} else {
		    						// 异常
		    						$.messager.alert('异常','录入红包信息异常!','error');
		    					}
		    				}
						});
		        }});
			}
		}
		
		function queryAllGrid(){
			$('#lotteryTicketGrid').datagrid('load', {
		        
		    });
		    $('#unbindTicketGrid').datagrid('load', {
		        
		    });
		}
	</script>
</body>
</html>