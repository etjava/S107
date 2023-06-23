<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="static/favicon.ico" rel="shortcut icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/blog.css">
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/alt.js"></script>
<title>${pageTitle }</title>
<style type="text/css">
	html {
	overflow-y: scroll;
	}
	body{
		padding-top: 2px;
		padding-bottom: 40px;
		background-color: #3F6371;
		overflow: hidden;
		/* background-image: url('${pageContext.request.contextPath}/static/images/Java-vert-wht-resized-v2.svg');
		background-size:cover; */
	}
</style>
</head>
<body>
<div class="container--fiuled"> <!-- container--fiuled 填充整个屏幕-->
	<div>
		<!-- 头部信息 -->
		<jsp:include page="foreground/common/header.jsp"></jsp:include>
		<!-- 导航 -->
		<jsp:include page="foreground/common/navigation.jsp"></jsp:include>
	</div>
	
	<div class="row">
	<!-- 左侧信息栏 -->
	<jsp:include page="foreground/common/left.jsp"></jsp:include>
	<!-- 博客信息 -->
	<%-- <jsp:include page="foreground/common/content.jsp"></jsp:include> --%>
	<jsp:include page="${mainPage }"></jsp:include>
	</div>
	
	<!-- 版本信息 -->
	<jsp:include page="foreground/common/foot.jsp"></jsp:include>
</div>
</body>
</html>