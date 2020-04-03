<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0" />
		<title></title>
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			
			.top {
				width: 100%;
				height: 50px;
				background-color: cornflowerblue;
				text-align: center;
				font-size: 20px;
				line-height: 50px;
				color: white;
				position: fixed;
				top: 0;
				left: 0;
				z-index: 10000;
			}
			
			.content {
				width: 100%;
				height: 160px;
				background-color: pink;
				margin-top: 20px;
				position: relative;
				border-radius: 10px;
			}
			
			.content .left {
				width: 80px;
				height: 30px;
				background-color: coral;
				color: white;
				text-align: center;
				line-height: 30px;
				position: absolute;
				left: 10px;
				top: 5px;
				border-radius: 2px;
			}
			
			.content .container {
				width: 40%;
				height: 80px;
				position: absolute;
				top: 30px;
				left: 30%;
				text-align: center;
				line-height: 80px;
				font-size: 30px;
				color: red;
			}
			
			.content .container span {
				font-weight: bold;
				font-size: 35px;
			}
			
			.content .right {
				color: #686868;
				position: absolute;
				right: 10px;
				top: 10px;
				font-size: 20px;
			}
			
			.content .botton {
				position: absolute;
				bottom: 8px;
				left: 10px;
				font-size: 16px;
				color: #3e3e3e;
			}
		</style>
	</head>

	<body>
		<div class="top">我的代金劵</div>
		<div id="wrap-content" style="margin-top: 70px;margin-bottom: 20px;">
			<!-- <div class="content">
				<div class="left">新获得</div>
				<div class="container">￥<span>50</span>元</div>
				<div class="right">审车优惠劵</div>
				<div class="botton">有效期：<span>2020.02.20-2020.12.30</span></div>
			</div>

			<div class="content" style="background-color: #CCCCCC;">
				<div class="left" style="color: white;background-color: #686868;">已失效</div>
				<div class="container" style="color: white;">￥<span>50</span>元</div>
				<div class="right">审车优惠劵</div>
				<div class="botton">有效期：<span>2020.02.20-2020.12.30</span></div>
			</div> 
			<div style="width: 100%;text-align: center;margin-top: 50%;color: #686868;">未查到您有优惠券，请您稍后再试~</div>
			-->
		</div>
		<input id="input1" type="text" value="${oppind}"
			style="display: none;" />
	</body>
	<script src="${ContextPath}/UI-lib/plugins/jQuery/jquery-2.2.3.min.js?${kjscb_time}"></script>
	<script src="${ContextPath}/UI-lib/plugins/layer/layer.js?${kjscb_time}"></script>
	<script type="text/javascript">
	
		$(function () {
			getCoupon();
		});
		
		function getCoupon() {
			var para = {
				id: $("#input1").val() //需要传入用户ID
			};
			$.ajax({
				type : "post",
				url : getRootPath() + "userCoupon/findById",
				data : para,
				dataType : "json",
				async : true,
				success : function(ret) {
					var data = ret.data;
					if (ret.status == 1) {
						layer.msg(ret.message, {
				            time: 2000
				        });
						renderData(data);
					} else {
						layer.msg(ret.message, {
				            time: 2000
				        });
						$("#wrap-content").html('<div style="width: 100%;text-align: center;margin-top: 50%;color: #686868;">未查到您有代金券，请您稍后再试~</div>');
					}
				}
			});
		}
		
		function renderData(data){
			//先清空
			$("#wrap-content").html("");
			//再添加数据
			var html = '';
			for(var i = 0; i < data.length;++i){
				
				var cp_status = data[i].cp_status;
				var cp_start_time = json2TimeStamp(data[i].cp_start_time);
				var cp_end_time = json2TimeStamp(data[i].cp_end_time);
				if(cp_status == 1){
					html += '<div class="content">' 
						+ '<div class="left">新获得</div>'
						+ '<div class="container">￥<span>'+data[i].cp_amount+'</span>元</div>'
						+ '<div class="right">'+data[i].cp_name+'</div>'
						+ '<div class="botton">有效期：<span>'+cp_start_time+'-'+cp_end_time+'</span></div>'
					    + '</div>';
				}else{
					html += '<div class="content" style="background-color: #CCCCCC;">' 
						+ '<div class="left" style="color: white;background-color: #686868;">已失效</div>'
						+ '<div class="container" style="color: white;" >￥<span>'+data[i].cp_amount+'</span>元</div>'
						+ '<div class="right">'+data[i].cp_name+'</div>'
						+ '<div class="botton">有效期：<span>'+cp_start_time+'-'+cp_end_time+'</span></div>'
					    + '</div>';
				}
			}
			
			$("#wrap-content").html(html);
		}
		
		//毫秒转时间YYYY-MM-DD hh:mm:ss
		function json2TimeStamp(milliseconds){
			if(milliseconds==null || milliseconds==""){
				return "";
			}
		    var datetime = new Date();
		    datetime.setTime(milliseconds);
		    var year=datetime.getFullYear();
		         //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
		    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
		    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
		    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
		    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
		    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
		    return year + "." + month + "." + date+" "+hour+":"+minute+":"+second;
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