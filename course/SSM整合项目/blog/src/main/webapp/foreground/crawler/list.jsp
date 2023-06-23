<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<div class="col-md-8">
<!-- ../blog/list.jsp 由于嵌套include 因此这里都集成到一起 -->
<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/list_icon.png"/>
		Latest</div>
		
<div class="datas">
	<ul>
		<c:forEach var="blog" items="${blogList }">
			<li style="margin-bottom: 30px">
				<span class="date"><a href="${pageContext.request.contextPath}/crawler/blog/articles/${blog.id }.html"><fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy年MM月dd日" />  </a></span>
				<span class="title"><a href="${pageContext.request.contextPath}/crawler/blog/articles/${blog.id }.html">${blog.title }</a></span>
				<span class="summary">
					<a href="${blog.originalUrl }" target="_blank">转自互联网</a>
				</span>
				<span class="summary" style="word-break:break-all; width:100%;">${blog.summary }...</span>
				
				<c:forEach var="image" items="${blog.imageList }">
					<a href="/crawler/blog/articles/${blog.id}.html">${image }</a>
						&nbsp;&nbsp;
				</c:forEach>
				
				 
				 
				 <span class="info">发表于 <fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm" /> 阅读(${blog.clickHit }) </span>
			</li>
			<hr style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />
		</c:forEach>
	</ul>
	</div>
</div>

<div>
	<nav>
	  <ul class="pagination pagination-sm">
	  	${pageCode }
	  </ul>
	</nav>
</div>
</div>
<div class="col-md-1">&nbsp;</div>