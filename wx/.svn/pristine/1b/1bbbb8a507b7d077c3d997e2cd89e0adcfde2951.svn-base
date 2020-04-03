<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	long kjscb_time = new Date().getTime();
	request.setAttribute("kjscb_time", kjscb_time);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${ContextPath}/weixinweb/css/bootstrap.min.css" />
<style>
.content {
	width: 18.75rem;
	height: 18.75rem;
	margin: 6.25rem auto;
}

.content img {
	width: 18.75rem;
	height: 18.75rem;
}
</style>
</head>
<body>
	<div class="container">
		<button class="btn btn-danger" id="btn">获取永久二维码</button>
		<div class="content" id="content"></div>
	</div>
</body>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/js/jquery-1.8.2.min.js?${kjscb_time}"></script>
<script type="text/javascript">

	$("#btn").click(function(){
		$.ajax({
			url : getRootPath() + "share/selectImg",
			type : "post",
			dataType : "json",
			success : function(data) {
				if(data.message=200){
					$("#content").append("<img src='"+data.code+"' />")
				}
			}
		})
	})
	
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