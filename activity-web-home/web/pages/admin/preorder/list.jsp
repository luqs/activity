<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>红包预下单维护</title>
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
							预下单状态
						</td>
						<td>
							<select id="status" class="easyui-combobox" name="status" style="width:140px;height:32px;">
			    				<option value="0" selected="selected">全部</option>
								<option value="1">有效</option>
								<option value="2">无效</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							红包类型
						</td>
						<td>
							<select id="hbType"  class="easyui-combobox" name="hbType" style="width:140px;height:32px;">
				    			<option value="ALL">全部</option>
								<option value="NORMAL" selected="selected">普通红包</option>
								<option value="GROUP">裂变红包</option>
							</select>
						</td>
					</tr>	
					<tr>
						<td>
						</td>
						<td>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="width:140px;height:32px" onclick="submitForm()">搜索</a>
						</td>
					</tr>	
				</table>
		    </form>
    	    
	    </div>
	</div>
	
	<table id="preorderGrid" class="easyui-datagrid" title="红包预下单列表" style="height:390px;"
			data-options="rownumbers:true,singleSelect:true,collapsible:false,url:'${ctx}/wxhb/preorder/query-by-page'
				,method:'get',pagination:true,pageSize:10,fitColumns:false,toolbar:'#preorderToolBar'">
		<thead>
			<tr>
				<th data-options="field:'id',align:'center',width:60">ID</th>
				<th data-options="field:'mchId',align:'center',width:100">商户号</th>
				<th data-options="field:'mchBillNo',align:'center',width:250">商户订单号</th>
				<th data-options="field:'mchName',align:'center',width:100">商户名称</th>
				<th data-options="field:'hbType',align:'center',width:80,formatter:renderHbType">红包类型</th>
				<th data-options="field:'amtType',align:'center',width:100">红包金额方式</th>
				<th data-options="field:'totalAmount',align:'center',width:80">总金额(分)</th>
				<th data-options="field:'totalNum',align:'center',width:100">总发放人数</th>
				<th data-options="field:'actName',align:'center',width:100">活动名称</th>
				<th data-options="field:'createTime',align:'center',formatter:dateFormatter,width:150">创建时间</th>
				<th data-options="field:'expireTime',align:'center',formatter:dateFormatter,width:150">失效时间</th>
				<th data-options="field:'status',align:'center',formatter:renderExpireStatus,width:80">状态</th>
				<th data-options="field:'op',align:'center',width:60,formatter:renderOperationCol">操作</th>
			</tr>
		</thead>
	</table>
	 
	<div id="preorderToolBar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="preorder()">红包预下单</a>
	</div>
	
    <div id="win" class="easyui-window" title="预下单详情" 
	 	data-options="iconCls:'icon-save',closed:true" style="width:1000px;height:500px;padding:5px;">
	</div>
	
	<script type="text/javascript">
		function preorder(){
			$('#win').window("setTitle", "红包预下单").window('resize',{
		    	width: 1000,
		    	height: 500
		    }).window("open");
		    $('#win').window("refresh", "add.jsp");	
		}
	
		function submitForm(){
			var status = $("#status").combobox('getValue');
			var hbType = $("#hbType").combobox('getValue');
		    $('#preorderGrid').datagrid('load', {
		        status: status,
		        hbType: hbType
		    });
		}
		
		function renderOperationCol(value,row,index){
			var v = '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="detail('+ row.id +')">详情</a>';
			return v; 
		}
		
		function renderExpireStatus(value,row,index){
			if (row.expireTime != null) {
				var now = new Date();
				var exp = new Date(row.expireTime);
				if (exp > now) {
					return '有效';
				} else {
					return '<span style="color:red;">无效</span>';
				}
			} else {
				return '<span style="color:red;">无效</span>';
			}
		}
		
		function detail(id){
		    //$('#win').window("refresh", "detail.jsp?id="+id);	
		    $('#win').window("open");
		    $('#win').window("refresh", "detail.jsp?id="+id);	
		}
	
		$(function(){
			
		});
	</script>
</body>
</html>