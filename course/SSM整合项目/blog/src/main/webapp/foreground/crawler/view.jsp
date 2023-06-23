<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/alt.js"></script>
<script type="text/javascript">
    SyntaxHighlighter.all();
    function showOtherComment(){
    	$(".otherComment").show();// 显示更多评论内容
    }
    
    function loadimage(){
		document.getElementById("randImage").src="${pageContext.request.contextPath}/image.jsp?"+Math.random();
	}
    
    
    function submitData(){
    	var content=$("#content").val();
    	var imageCode=$("#imageCode").val();
    	if(content==null || content==""){
    		commonUtil.message("请输入评论内容！","danger");
    	}else if(imageCode==null || imageCode==""){
    		commonUtil.message("请填写验证码！","danger");
    	}else{
    		$.post("${pageContext.request.contextPath}/comment/save.html",{"content":content,'imageCode':imageCode,'blog.id':'${blog.id}'},function(result){
    			if(result.success){
    				// 重新加载当前页面
    				//window.location.reload();
    				commonUtil.message("评论已提成成功，审核通过后显示！","success");
    				$("#content").val('');
    				$("#imageCode").val('');
    				
    			}else{
    				alert(result.errorInfo);
    			}
    		},"json");
    	}
    }
    
    
    
    
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
<!--

.data_list .blog_title{
	margin-top:20px;
	text-align: center;
}

.data_list .blog_info{
	text-align: center;
}

.data_list .blog_content{
	margin-top:20px;
	padding-bottom: 30px;
	padding-left:50px;
	
	word-break:break-all; 
	width:100%;
}

.data_list .blog_content img{
	width:100%;
}

.data_list .blog_share{
	padding-left: 330px;
	padding-bottom: 20px;
	padding-top: 10px
}

.data_list .blog_keyword{
	margin-top:20px;
	padding-bottom: 30px;
	padding-left: 30px;
}

.data_list .blog_keyword a{
	color: black;
	font-style: italic;
}

.data_list .blog_lastAndNextPage{
	border-top:1px dotted black;
	padding: 10px;
}

-->
</style>

<div class="col-md-8">
<div class="data_list">
	<div class="data_list_title">
		<img src="/static/images/blog_show_icon.png"/>
		博客信息
	</div>
	<div>
		<div class="blog_title"><h3><strong>${blog.title }</strong></h3></div>
		<div class="blog_info">
			发布时间：『 <fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>』&nbsp;&nbsp;博客类别：${blog.blogType.typeName }&nbsp;&nbsp;阅读(${blog.clickHit }) <%-- 评论(${blog.replyHit }) --%>
		</div>
		<div class="blog_content">
		${blog.content }
		</div>
		<div class="blog_keyword">
			<strong>Keywords&nbsp;:&nbsp;</strong>
			<c:choose>
				<c:when test="${keywords==null }"></c:when>
				<c:otherwise>
					<c:forEach var="keyword" items="${keywords }">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/blog/search.html?keyword=${keyword }"><strong>${keyword }</strong></a>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="blog_lastAndNextPage">
			${pageCode }
		</div>
	</div>
</div>
</div>