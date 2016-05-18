<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="initial-scale=1.0,maximum-scale=1,width=device-width">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="white">
<title>嗨摇红包</title>
<style>
.unstart {
	font-size: 1.6rem;
	text-align: center;
	color: #000;
	position: absolute;
	top: 40%;
	left: 0;
	right: 0;
}

</style>

<!-- <script type="text/javascript" src="http://zb.weixin.qq.com/nearbycgi/addcontact/BeaconAddContactJsBridge.js"></script> -->
<script type="text/javascript" src="http://zb.weixin.qq.com/app/shakehb/BeaconShakehbJsBridge.js"></script>
<script type="text/javascript">
	BeaconShakehbJsBridge.ready(function(){
		var lotteryId = '${lotteryId}'; // 微信活动ID
		var openid = '${openid}'; // 用户openId
		var noncestr = '${noncestr}'; // 字符串
		var sign = '${sign}'; // 签名
		//alert(lotteryId);
 
		if (lotteryId != null && lotteryId !='NULL' && lotteryId !='null' && lotteryId !='') {
			// 跳转到抽红包页面
			// 启动微信native页面
		    BeaconShakehbJsBridge.invoke('jumpHongbao',{
			     lottery_id:lotteryId,
			     noncestr:noncestr,
			     openid:openid,
			     sign:sign
			});
		}
	});

</script>
</head>
<body>
	<div class="wrapper">
		<div class="img-box">
			<p class="unstart">${errorMsg}</p>
		</div>
	</div>
	<!-- <script type="text/javascript">
		BeaconAddContactJsBridge.ready(function(){
			//判断是否关注
			BeaconAddContactJsBridge.invoke('checkAddContactStatus',{} ,function(apiResult){
				if(apiResult.err_code == 0){
					var status = apiResult.data;
					if(status == 1){
						alert('已关注');
					}else{
						alert('未关注');
						//跳转到关注页
					  BeaconAddContactJsBridge.invoke('jumpAddContact');
					}
				}else{
					alert(apiResult.err_msg)
				}
			}); 
		}); 
	</script> -->
</body>
</html>