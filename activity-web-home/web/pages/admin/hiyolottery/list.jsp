<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>嗨摇红包活动维护</title>
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
						<col width="130"/>
					</colgroup>
					<tr>
						<td>
							<label for="name">红包活动处理状态</label>
						</td>
						<td>
							<select id="processStatus" class="easyui-combobox" name="processStatus" style="width:140px;height:32px;">
			    				<option value="1"  selected="selected">处理中</option>
			    				<option value="0">初始化</option>
								<option value="2">处理成功</option>
								<option value="3">处理失败</option>
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
	
	<table id="lotteryGrid" class="easyui-datagrid" title="嗨摇红包活动列表" style="height:400px;"
			data-options="rownumbers:true,singleSelect:true,collapsible:false,url:'${ctx}/lottery/query-by-page',method:'get',
			pagination:true,pageSize:10,fitColumns:false,toolbar:'#lotteryToolBar'">
		<thead>
			<tr>
				<th data-options="field:'id',align:'center',width:60">ID</th>
				<th data-options="field:'title',align:'center',width:100">抽奖活动名称</th>
				<th data-options="field:'description',align:'center',width:100">活动描述</th>
				<th data-options="field:'perMinAmount',align:'center',width:140">单个红包最低金额（分）</th>
				<th data-options="field:'perMaxAmount',align:'center',width:140">单个红包最大金额（分）</th>
				<th data-options="field:'total',align:'center',width:80">红包总数</th>
				<th data-options="field:'totalTicket',align:'center',width:80">实际红包数量</th>
				<th data-options="field:'amountType',align:'center',width:100,formatter:rendorAmountType">红包金额类型</th>
				<th data-options="field:'hbType',align:'center',width:80,formatter:renderHbType">红包类型</th>
				<th data-options="field:'processStatus',align:'center',width:80,formatter:renderProcessStatus">处理状态</th>
				<th data-options="field:'beginTime',align:'center',formatter:dateFormatter,width:150">活动开始时间</th>
				<th data-options="field:'expireTime',align:'center',formatter:dateFormatter,width:150">活动结束时间</th>
				<th data-options="field:'createTime',align:'center',formatter:dateFormatter,width:150">创建时间</th>
				<th data-options="field:'updateTime',align:'center',formatter:dateFormatter,width:150">更新时间</th>
			</tr>
		</thead>
	</table>
	 
	<div id="lotteryToolBar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="addLottery()">新增嗨摇红包活动</a>
	</div>
	
	<div id="win" class="easyui-window" title="嗨摇红包活动详情" 
	 	data-options="iconCls:'icon-save',closed:true,modal:true,fit:true" style="width:1000px;height:350px;padding:5px;">
	</div>
	
	<script type="text/javascript">
	
	
		function addLottery(){
			$('#win').window("setTitle", "添加嗨摇红包活动").window('resize',{
		    	width: 1000,
		    	height: 500
		    }).window("open");
		    $('#win').window("refresh", "add.jsp");	
		}
	
 		function submitForm(){
			var processStatus = $("#processStatus").combobox('getValue');
		    $('#lotteryGrid').datagrid('load', {
		    	processStatus: processStatus
		    });
		}
		
 		

	</script>
</body>
</html>