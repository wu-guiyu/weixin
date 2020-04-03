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
<link rel="stylesheet"
	href="${ContextPath}/weixinweb/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<input type="file" id="ipt" accept="image/*" name="file"
			style="margin-top: 50px;" onchange="uploadImage(this)" />
		<button id="btn" class="btn btn-warning" style="margin-top: 1.25rem;">上传海报</button>
	</div>
</body>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/js/jquery-1.8.2.min.js?${kjscb_time}"></script>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/js/lrz.bundle.js?${kjscb_time}"></script>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/layer_mobile/layer.js?${kjscb_time}"></script>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/js/workday.js?${kjscb_time}"></script>
<script type="text/javascript"
	src="${ContextPath}/weixinweb/app/js/jweixin-1.4.0.js?${kjscb_time}"></script>
<script type="text/javascript">
	function uploadImage(el) {
		var index = showLoading("图片上传中……");
		var that = el;
		lrz(that.files[0],{width : 300}).then(function(rst) {
			var submitData = {
				image : rst.base64,
				name : rst.origin.name,
				fileLength : rst.base64.length
			};

			$.ajax({
				type : "POST",
				url : getRootPath() + "share/upimg",
				data : submitData,
				dataType : "json",
				success : function(data) {
					// 关闭loading
					closeLoading(index);
					if ("success" == data.mse) {
						layerTip("上传成功");
						return;
					}
					layerTip("上传失败");
				}
			});
			return rst;
		});
	}
	// 封装的loading方法
	function showLoading(text) {
		// loading带文字
		var index = layer.open({
			type : 2,
			content : text,
			shadeClose : false
		});
		return index;
	}

	// 关闭loading
	function closeLoading(index) {
		layer.close(index);
	}

	// 封装的提示方法
	function layerTip(text) {
		// 2秒后自动关闭
		layer.open({
			content : text,
			skin : 'msg',
			time : 2
		});
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