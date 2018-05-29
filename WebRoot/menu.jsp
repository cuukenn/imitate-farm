<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单</title>
<script type="text/javascript" src="<%=basePath%>ext/easyui/jquery.min.js"></script>
<style>
html, body {
	width: 100%;
	height: 100%;
}

body {
	margin: 0px;
}

.bar {
	background-image: url(images/topbar.png);
	background-size: 25% 60px;
	background-repeat: repeat-x;
}

.shadow {
	-moz-box-shadow: 2px 2px 5px #333333;
	-webkit-box-shadow: 2px 2px 5px #333333;
	box-shadow: 2px 2px 5px #333333;
}

.menu {
	margin: 3px 5px 5px 5px;
}

.userBox {
	width: 300px;
	height: 60px;
	position: relative;
	font-size: 6px;
	float:left;
}

.userBoxHeads {
	width: 20%;
	height: 40%;
	float:left;
}

.userBoxHeads>img {
	width: 60px;
	height:60px;
	height: auto;
}

.userBoxContent {
	position:relative;
	float:left;
}
.username{
	position:absolute;
	width:200px;
	font-size: 4em;
	left:20px;
	top:-20px;
	color:yellow;
}
.userDetail{
	position:absolute;
	width:165px;
	left:20px;
	top:25px;
	font-size: 2em;
	border-radius:10px;
	border:1px solid black;
	background-color:#3EC6FC;
	color:white;
	padding-left:5px;
}

</style>
</head>
<body class="bar">
	<div align="left">
		<div class="userBox">
			<div class="userBoxHeads">
				<img class="menu shadow"  src="images/unknow.png" />
			</div>
			<div class="userBoxContent">
				<p class='username'>姓名</p>
				<p class='userDetail'>信息</p>
			</div>
		</div>
	</div>
	<div align="right" width="60%">
		<a href="welcome.jsp" target="workspace"><img class="menu shadow" src="images/landHead.png" width="50px" height="50px"></a>
		<a href="user/userSelect" target="workspace"><img class="menu shadow" src="images/user.png" width="45px" height="50px"></a>
		<a href="shop/grid" target="workspace"><img class="menu shadow" src="images/shop.png" width="50px" height="50px"></a>
		<a href="user/grid" target="workspace"><img class="menu shadow" src="images/menu-1.png" width="50px" height="50px"></a>
		<a href="seed/grid" target="workspace"><img class="menu shadow" src="images/seedManager.png" width="50px" height="50px"></a>
	<div>
</body>
<script>
	init();
	function changeInfo(data){
		if(data){
			var rs=data.username+'|'
			+'经验:'+data.exp+'|'
			+'金币:'+data.price+'|'
			+'积分:'+data.score;
			$('.userBoxContent>p:nth-child(1)').text(data.username);
			$('.userBoxContent>p:nth-child(2)').text(rs);
			$('.userBoxHeads>img').attr('src','<%=basePath%>images/headImages/'+data.heads);
		}
	}
	function init(){
		$.post('<%=basePath%>user/getCurUser',function(data){
			changeInfo(data);
		})
	}
</script>
</html>