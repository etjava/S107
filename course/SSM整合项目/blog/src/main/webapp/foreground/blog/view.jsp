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



.data_list .commentDatas{
	padding: 10px;
	word-break:break-all; 
	width:100%;
}

.data_list .commentDatas .comment{
	margin-top:10px;
	margin-bottom: 5px;
	padding-bottom:15px;
	border-bottom: 1px dotted gray;
}

.data_list .commentDatas .comment font{
	font-weight: bold;
}



.data_list .commentDatas .otherComment{
	display: none;
	margin-top:10px;
	margin-bottom: 5px;
	padding-bottom:15px;
	border-bottom: 1px dotted gray;
	color: blue;
}

.data_list .publish_comment{
	padding-top: 10px;
	padding-bottom: 50px;
}

.data_list .publish_comment .publishButton{
	padding-top:10px;
	float: right;
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
			发布时间：『 <fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>』&nbsp;&nbsp;博客类别：${blog.blogType.typeName }&nbsp;&nbsp;阅读(${blog.clickHit }) 评论(${blog.replyHit })
		</div>
		<!-- <div class="blog_share">
		<div class="bshare-custom icon-medium"><a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto" class="bshare-more">分享到</a><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
		</div> -->
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
<div class="data_list">
	<div class="data_list_title">
		<img src="/static/images/comment_icon.png"/>
		Comment's
		<c:if test="${commentList.size()>10 }">
			<a href="javascript:showOtherComment()" style="float: right;padding-right: 40px;">more</a>
		</c:if>
	</div>
	<div class="commentDatas">
		<c:choose>
			<c:when test="${commentList.size()==0 }"></c:when>
			<c:otherwise>
				<c:forEach var="comment" items="${commentList}" varStatus="status">
					<c:choose>
						<c:when test="${status.index<10 }">
							<div class="comment">
								<span><font>${status.index+1 }&nbsp;#F&nbsp;&nbsp;&nbsp;&nbsp;${comment.userAddr }&nbsp;:&nbsp;&nbsp;</font>${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.commonDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>&nbsp;]</span>			
							</div>
						</c:when>
						<c:otherwise>
							<div class="otherComment">
								<span><font>${status.index+1 }&nbsp;#F&nbsp;&nbsp;&nbsp;&nbsp;${comment.userAddr }&nbsp;:&nbsp;&nbsp;</font>${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.commonDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>&nbsp;]</span>			
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<hr />
<div class="data_list" >
	<div class="data_list_title">
		<img src="/static/images/publish_comment_icon.png"/>
		发表评论
	</div>
	<div class="publish_comment">
			<div>
				<textarea style="width: 100%" rows="3" id="content" name="content" placeholder="来说两句吧..."></textarea>
			</div>
			<div class="verCode">
				<strong>Verification code&nbsp;&nbsp;:&nbsp;&nbsp;</strong><input type="text" value="" name="imageCode"  id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()"/>&nbsp;<img onclick="javascript:loadimage();"  title="换一张试试" name="randImage" id="randImage" src="/image.jsp" width="60" height="20" border="1" align="absmiddle"> 
			</div>
			<div class="publishButton">
				<button class="btn btn-success" type="button" onclick="submitData()">Comment</button>
			</div>
	</div>
</div>
</div>