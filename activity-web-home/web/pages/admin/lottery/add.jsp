<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加红包活动</title>
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
					抽奖活动名称
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
					抽奖活动描述
				</td>
				<td>
					<input  type="text" class="easyui-textbox" name="desc" 
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
					<select class="easyui-combobox" name="onoff" style="width:200px;height:32px;">
						<option value="1" selected="selected">开</option>
						<option value="0">关</option>
					</select>
				</td>
				<td class="tip">
				</td>
			</tr>
			<tr>
				<td>
					抽奖活动开始时间
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
					抽奖活动结束时间
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
					红包总数
				</td>
				<td>
					<input type="text" name="total" class="easyui-numberbox"  style="width:200px;height:32px;"
						value="1" data-options="min:1,precision:0" required="required">
				</td>
				<td class="tip">
					红包总数>=预下单红包ticket总数，相等时100%概率
				</td>
			</tr>
			
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
	
	    $.extend($.fn.validatebox.defaults.rules, {
	    	validateChLength: {
	    		validator: function(value, param){
	    			var curLen = value.replace(/[^\x00-\xff]/g,"xx").length;
	    			/* 
	    			alert(param[0]);
	    			alert(param[1]); */
	    			if (curLen > param[1] || curLen <param[0]) {
	    				return false;
	    			} else {
	    				return true;
	    			}
	    		},
	    		message: '最长{1}个英文字母，中文为两个字符'
	        }
	    });
	
		$(function(){
			// window里面使用该方法有问题？
			//$('#beginTime').datetimebox('setValue', '6/1/2012 12:30:56');
			$('#preorderForm').form({
			    url: '${ctx}' + '/wxhb/lottery/add',
			    onSubmit: function(){	
			    },
			    success:function(data){	    	
					if (data != null ) {
						var res = JSON.parse(data);
						if (res.success) {
							$.messager.alert('提示','添加红包活动成功','info');
							$('#preorderForm').form("reset");
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
			var isValid = $('#preorderForm').form("validate");
			if (isValid) {
				$('#preorderForm').submit();
			}	
		}
	</script>
</body>
</html>