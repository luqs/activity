<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>微信红包活动维护</title>
	<style type="text/css">
		
	</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:50px;background:#B3DFDA;font-size:18px;line-height:48px;padding-left:30px;">
		嗨摇微信红包活动后台管理
	</div>
	<div data-options="region:'south',split:true" style="height:50px;">
		
	</div>
	
	<div data-options="region:'west',split:true" title="管理中心" style="width:200px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="红包预下单管理" style="padding:10px;">
				<a href="javascript:void(0)" style="width:140px;height:32px" onclick="addTab('红包预下单列表', '${ctx}/pages/admin/preorder/list.jsp')">红包预下单列表</a>
			</div>
			<div title="红包活动管理" style="padding:10px;">
				<a href="javascript:void(0)" style="width:140px;height:32px" onclick="addTab('红包活动列表', '${ctx}/pages/admin/lottery/list.jsp')">红包活动列表</a>
			</div>
		</div>
	</div>
	<div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
		<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
			
		</div>
	</div>
	
	<script type="text/javascript">
	    function addTab(title, url){
	    	if ($('#tt').tabs('exists', title)){
	    		$('#tt').tabs('select', title);
	    	} else {
	    		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	    		$('#tt').tabs('add',{
	    			title:title,
	    			content:content,
	    			closable:true
	    		});
	    	}
	    }
    
	</script>
</body>
</html>