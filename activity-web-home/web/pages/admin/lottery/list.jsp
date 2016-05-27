<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>红包活动维护</title>
	<style type="text/css">
		.easyui-linkbutton{
			margin:0px 5px;
		}
		
		.search-button{
			margin:0px;
		}
		
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
		
		.queryTable{
			line-height:35px;
		}
		
		.queryTable td {
			font-size:14px;
		}
	</style>
</head>
<body>
	
	<div class="easyui-panel" title="搜索" style="margin-bottom:20px;">
		<div style="padding:10px 60px 20px 60px">
			<form id="ff" method="post">
				<table class="queryTable">
					<colgroup>
						<col width="100"/>
					</colgroup>
					<tr>
						<td>
							<label for="name">抽奖开关</label>
						</td>
						<td>
							<select id="status" class="easyui-combobox" name="status" style="width:140px;height:32px;">
			    				<option value="2" selected="selected">全部</option>
								<option value="1">开</option>
								<option value="0">关</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
							<a href="javascript:void(0)" class="easyui-linkbutton search-button" iconCls="icon-search" style="width:140px;height:32px" onclick="submitForm()">搜索</a>
						</td>
					</tr>	
				</table>
		    </form>
    	   
	    </div>
	</div> 
	
	<table id="lotteryGrid" class="easyui-datagrid" title="红包活动列表" style="height:400px;"
			data-options="rownumbers:true,singleSelect:true,collapsible:false,url:'${ctx}/wxhb/lottery/query-by-page',method:'get',
			pagination:true,pageSize:10,fitColumns:false,toolbar:'#lotteryToolBar'">
		<thead>
			<tr>
				<th data-options="field:'id',align:'center',width:60">ID</th>
				<th data-options="field:'title',align:'center',width:100">抽奖活动名称</th>
				<th data-options="field:'description',align:'center',width:100">抽奖活动描述</th>
				<th data-options="field:'total',align:'center',width:80">红包总数</th>
				<th data-options="field:'lotteryId',align:'center',width:200">微信红包活动ID</th>
				<th data-options="field:'result',align:'center',width:100">红包活动生成结果</th>
				<th data-options="field:'beginTime',align:'center',formatter:dateFormatter,width:150">抽奖活动开始时间</th>
				<th data-options="field:'expireTime',align:'center',formatter:dateFormatter,width:150">抽奖活动结束时间</th>
				<th data-options="field:'createTime',align:'center',formatter:dateFormatter,width:150">创建时间</th>
				<th data-options="field:'updateTime',align:'center',formatter:dateFormatter,width:150">更新时间</th>
				<th data-options="field:'status',align:'center',width:100,formatter:renderStatus">抽奖开关</th>
				<th data-options="field:'op',align:'center',width:140,formatter:renderOperationCol">操作</th>
			</tr>
		</thead>
	</table>
	 
	<div id="lotteryToolBar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="addLottery()">新增红包活动</a>
	</div>
	
	<div id="win" class="easyui-window" title="红包活动详情" 
	 	data-options="iconCls:'icon-save',closed:true,modal:true,fit:true" style="width:1000px;height:350px;padding:5px;">
	</div>
	
	<script type="text/javascript">
		function addLottery(){
			$('#win').window("setTitle", "新增红包活动").window('resize',{
		    	width: 1000,
		    	height: 500
		    }).window("open");
		    $('#win').window("refresh", "add.jsp");	
		}
	
 		function submitForm(){
			var status = $("#status").combobox('getValue');
		    $('#lotteryGrid').datagrid('load', {
		        status: status
		    });
		}
		
		function renderOperationCol(value,row,index){
			var v2 = '';
			if (row.status == 0) {
				v2 = '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="switchLottery('+ row.id +', 1)">开启</a>';
			} else if (row.status == 1){
				v2 = '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="switchLottery('+ row.id +', 0)">关闭</a>';
			}
			var v = '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="detail('+ row.id +')">详情</a>';
			var v3 = '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="setPrizeBucket('+ row.id +')">录入红包</a>';
			
			return v2 + v + v3; 
		}
		
		// 设置红包活动开关
		function switchLottery(id, status) {
			var tip = status == 0 ? '关闭':'开启';
			$.messager.confirm("提示", "您确定要"+tip+"吗？", function (data) {
	            if (data) {
	    			var param = {};
	    			param.hbLotteryId = id;
	    			param.onoff = status;
	    		
	    			$.ajax({
	    				type:'post',
	    				url: '${ctx}' + '/wxhb/lottery/set-lottery-switch',
	    				data: JSON.stringify(param),
	    				dataType: "json",
	    				contentType: "application/json; charset=utf-8",
	    				success:function(data){
	    					if (data != null ) {
	    						if (data.success) {
	    							// 成功
	    							$.messager.alert('提示','设置红包活动开关成功','info', function (){
	    								submitForm();
	    							});
	    						} else {
	    							// 失败
	    							$.messager.alert('异常',data.resultMsg,'error');
	    						}
	    					} else {
	    						// 异常
	    						$.messager.alert('异常','设置红包活动开关异常!','error');
	    					}
	    				}
	    			});
	            }            
	        });
		}

		function detail(id){
			$('#win').window("setTitle", "红包活动详情").window('resize',{
		    	width: 1000,
		    	height: 350
		    }).window("open");
		    $('#win').window("refresh", "detail.jsp?id="+id);	
		}
		
		function setPrizeBucket(id){
		    $('#win').window("setTitle", "录入红包信息").window('resize',{
		    	width: 1000,
		    	height: 600
		    }).window("open");
		    $('#win').window("refresh", "lottery_ticket.jsp?id="+id);
		}

		$(function(){
		}); 
	</script>
</body>
</html>