<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<h2>Map:</h2>
	<li>${mapData.key1 }</li>
	<li>${mapData.key2 }</li>
	<li>${mapData.key3 }</li>
	
<h2>List:</h2>
<c:forEach items="${listData}" var="data" varStatus="row">
	<li>${data}</li>
</c:forEach>


</body>
</html>