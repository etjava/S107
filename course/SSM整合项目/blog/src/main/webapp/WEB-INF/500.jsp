<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>500 - ETJAVA</title>
<style type="text/css">
	body{
		background-color: #223266;
		
	}
</style>
</head>
<body>
<div>
	<img alt="" src="${pageContext.request.contextPath}/static/images/500.jpg">
	<div style="float:right; padding-top: 10%; padding-right: 22%">
	<h1><font color="#FFF">Your may need</font></h1>
	<h3 ><font color="#FFF">${errorMsg }</font></h3>
	</div>
</div>
</body>
</html>