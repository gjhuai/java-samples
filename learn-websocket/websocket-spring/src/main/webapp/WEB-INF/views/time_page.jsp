<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String wsPath = "ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>Timer</title>
</head>
<body>
<h1>
	This is a timer!
	<div id="message">
		
	</div>
</h1>

<script type="text/javascript" src="js/jquery-1.12.2.min.js"></script>
<script type="text/javascript" src="js/sockjs.min.js"></script>


<script type="text/javascript">
	
	$(function(){
		//建立socket连接
		var sock;
		if ('WebSocket' in window) {
			sock = new WebSocket("<%=wsPath%>timer");
	    } else if ('MozWebSocket' in window) {
	    	sock = new MozWebSocket("<%=wsPath%>timer");
	    } else {
	    	sock = new SockJS("<%=basePath%>sockjs/timer");
	    }
		sock.onopen = function (e) {
			console.log(e);
	    };
	    sock.onmessage = function (e) {
	    	console.log(e)
	        $("#message").html("<p><font color='red'>"+e.data+"</font>")
	    };
	    sock.onerror = function (e) {
	    	console.log(e);
	    };
	    sock.onclose = function (e) {
	    	console.log(e);
	    }
	});
	
</script>

</body>
</html>
