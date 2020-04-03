<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	//获取用户信息,从session 里面
	HttpSession httpSession = request.getSession();
	String oppind = (String) httpSession.getAttribute("oppind");
	request.setAttribute("oppind", oppind);
	long kjscb_time = new Date().getTime();
	request.setAttribute("kjscb_time", kjscb_time);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>正在登录微信……</title>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/js/jquery-1.8.2.min.js?${kjscb_time}"></script>
<script type="text/javascript">
	function shouquan() {
		var opind = "<%=session.getAttribute("oppind")%>";
		location.href = "https://www.kjscb.com/wx/weixinweb/haibao.jsp?"+opind+";";
	}
</script>
</head>
<body onload="shouquan()">
	<div id="tt"></div>
</body>
</html>