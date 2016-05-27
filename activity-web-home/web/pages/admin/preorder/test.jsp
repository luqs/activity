<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>红包预下单维护</title>
	<style type="text/css">
		
	</style>
</head>
<body>
	<h2>Basic TextBox</h2>
	<p>The textbox allows a user to enter information.</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="Register" style="width:400px;padding:30px 60px">
		<div style="margin-bottom:20px">
			<div>Email:</div>
			<input id="vv" class="easyui-validatebox" data-options="validType:'email'" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>First Name:</div>
			<input class="easyui-textbox" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>Last Name:</div>
			<input class="easyui-textbox" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>Company:</div>
			<input class="easyui-textbox" style="width:100%;height:32px">
		</div>
		
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:100%;height:32px">Register</a>
		</div>
	</div>
	
	    <form id="ff" method="post">
        <div>
    		<label for="name">Name:</label>
    		<input class="easyui-validatebox" type="text" name="name" data-options="required:true" />
        </div>
        <div>
    		<label for="email">Email:</label>
    		<input class="easyui-validatebox" type="text" name="email" data-options="validType:'email'" />
        </div>
        ...
    </form>
    
	<script type="text/javascript">
    /* $('#vv').validatebox({
        required: true,
        validType: 'email'
    }); */
    $(function(){
    	$('#ff').form({});
	});
    	
	</script>
</body>
</html>