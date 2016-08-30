<%@page import="org.springframework.util.StringUtils, java.util.UUID"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试Session共享</title>
</head>
<body>
<%
	if (StringUtils.isEmpty(session.getAttribute("Hello"))) {
		session.setAttribute("Hello", UUID.randomUUID());
	}
%>
	<font>显示当前用户：${sessionScope.Hello}</font>

</body>
</html>