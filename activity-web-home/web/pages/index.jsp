<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="initial-scale=1.0,maximum-scale=1,width=device-width">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="white">

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
</head>
<body>
	<div class="wrapper">
		<div class="img-box">
			<p class="unstart">活动参与人数过多，请稍后再试！</p>
		</div>
	</div>
	${lotteryId}
</body>
</html>