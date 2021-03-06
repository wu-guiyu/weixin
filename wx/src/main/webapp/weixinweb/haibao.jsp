<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	//获取用户信息,从session 里面
	//HttpSession httpSession = request.getSession();
	//String oppind = (String) httpSession.getAttribute("oppind");
	//request.setAttribute("oppind", oppind);
	long kjscb_time = new Date().getTime();
	request.setAttribute("kjscb_time", kjscb_time);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>毕节快捷审车帮</title>
<style>
* {
	margin: 0;
	padding: 0;
}

.context {
	width: 100%;
	height: 100%;
}

.top {
	width: 100%;
	height: 150px;
	background-color: #76fcfb;
	position: relative;
}

.left {
	width: 6.25rem;
	height: 6.25rem;
	background-color: #008000;
	border-radius: 3.125rem;
	margin-left: 10px;
	float: left;
	margin-top: 25px;
}

.left img {
	width: 6.25rem;
	height: 6.25rem;
	border-radius: 3.125rem;
}

.right {
	width: 45%;
	height: 6.25rem;
	float: left;
	padding-left: 10px;
	margin-top: 25px;
}

.right h4 {
	height: 3.125rem;
	line-height: 3.125rem;
}

.right p {
	height: 3.125rem;
}

.rga {
	width: 100px;
	height: 80px;
	margin-top: 25px;
	float: left;
	text-align: center;
	position: absolute;
	right: 35px;
}

.rga span {
	color: #FF0000;
	font-weight: bold;
}

.center {
	width: 100%;
	height: 380px;
	position: relative;
}

.center img {
	width: 100%;
	height: 100%;
}

.center .ewm {
	width: 6.25rem;
	height: 6.25rem;
	background: red;
	position: absolute;
	top: 310px;
	left: 1.875rem;
}

.center .ewm img {
	width: 6.25rem;
	height: 6.25rem;
}

.center .rig {
	width: 58%;
	height: 6.25rem;
	position: absolute;
	top: 320px;
	left: 130px;
	text-align: center;
	color: black;
}

.center .rig span {
	font-weight: bold;
	font-size: 1.75rem;
	color: red;
}
</style>
</head>
<body>
	<div class="context" id="waep">
		
		<input id="input1" type="text" value="${oppind}"
			style="display: none;" />
	</div>
</body>
<script
	src="${ContextPath}/UI-lib/plugins/jQuery/jquery-2.2.3.min.js?${kjscb_time}"></script>

<script>
	$(function() {
		init();
	});

	function init() {
		//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		var curWwwPath = window.document.location.href;
		var i =curWwwPath.indexOf("?");
		var j =curWwwPath.indexOf(";");
		var opind = curWwwPath.substring(i+1,j);
		$.ajax({
			url : "https://www.kjscb.com/wx/share/selectImgLogo",
			type : "post",
			dataType : "json",
			data : {
				"opind" : opind
			},
			success : function(me) {
				if(me.message=200){
					$("#waep").append(
							"<div class='top'>"
							+ "<div class='left'>"
							+ "	<img src='"+me.logoPath+"' />"
							+ "</div>"
							+ "<div class='right'>"
							+ "	<h4>"+me.name+"</h4>"
							+ "	<p>邀请你扫码支持我！</p>"
							+ "</div>"
							+ "<div class='rga'>"
							+ "	<p>"
							+ "		人气：<span>"+me.cont+"</span>"
							+ "	</p>"
							+ "</div>"
							+ "</div>"
							+ "<div class='center'>"
							+ "<img src='"+me.hb+"'>"
							+ "<div class='ewm'>"
							+ "	<img src='"+me.QRcode+"' />"
							+ "</div>"
							+ "<div class='rig'>"
							+ "	<p>"
							+ "		邀请10个人关注即可获得<br /> <span>50元</span><br />审车代金卷"
							+ "	</p>"
							+ "</div>"
							+ "</div>");  
				}else if(me.message=400) {
					alter(me.content);
				}
				
			}
		})
	}

	function getRootPath() {
		//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		var curWwwPath = window.document.location.href;
		//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		//获取主机地址，如： http://localhost:8083
		var localhostPaht = curWwwPath.substring(0, pos);
		//获取带"/"的项目名，如：/uimcardprj
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 2);
		return (localhostPaht + projectName);
	}
</script>
</html>