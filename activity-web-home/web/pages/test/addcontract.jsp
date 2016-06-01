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
<title>公众号关注测试</title>
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
<script type="text/javascript"
	src="http://zb.weixin.qq.com/app/shakehb/BeaconShakehbJsBridge.js"></script>
<script type="text/javascript">
	/* BeaconShakehbJsBridge.ready(function(){
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
	}); */
</script>
</head>
<body>
	<%-- <div class="wrapper">
		<div class="img-box">
			<p class="unstart">${errorMsg}</p>
		</div>
	</div> --%>
	<!-- <img src="../../images/shakehand.png"/> -->
	<img src="../../images/hiyo515_344.jpg" />

	<!-- 
		gh_d7524734b515 嗨摇
		oLt3csxCMi1lfnXXSuVF7hs32Ybo 老徐
	 -->
	<a data-cke-saved-href="#" href="#" onclick="WeiXinAddContact('gh_d7524734b515')">
		关注
	</a>
	
	<p><a href="weixin://contacts/profile/gh_4356331966ed">黄陂微生活</a></p>

	<a href="oLt3csxCMi1lfnXXSuVF7hs32Ybo">别人能看到的链接文字</a>

	<script type="text/javascript">
	
		// 这个接口微信官方没有发布，而是被人挖掘出来的。后来被微信屏蔽了。
		function WeiXinAddContact(wxid) {
			alert('开始');
			if (typeof WeixinJSBridge == 'undefined'){
				alert('没有');
				return false;
			}
			alert('调用');
			WeixinJSBridge.invoke('addContact', {
				webtype : '1',
				username : wxid
			}, function(d) {
				//alert(JSON.stringify(d));
				// 返回d.err_msg取值，d还有一个属性是err_desc //    add_contact:cancel 用户取消 //  add_contact:fail 关注失败   
				// add_contact:ok 关注成功   // add_contact:added 已经关注   
				WeixinJSBridge.log(d.err_msg);
				alert(d.err_msg);
				/* cb && cb(d.err_msg); */
			});
		};

		//通过微信分享   
		function WeiXinShareBtn() {
			if (typeof WeixinJSBridge == "undefined") {
				alert("请先通过微信xxxx");
			} else {
				WeixinJSBridge
						.invoke(
								'shareTimeline',
								{
									"title" : "微信资讯网",
									"link" : "http://www.wechatstyle.com",
									"desc" : "欢迎关注微时代为您搜罗最新最尖端的微信资讯。内容包括微信公众平台开发、微信营销方案策略、微信操作指南，   好玩有趣的微信公众账号导航、微信游戏攻略、微信最新版本，最新功能等更多更全面的微信资讯信息。",
									"img_url" : "http://www.wechatstyle.com"
								});
			}
		}
	</script>
</body>
</html>