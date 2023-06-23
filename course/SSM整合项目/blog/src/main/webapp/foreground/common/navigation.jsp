<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/alt.js"></script>    
<script type="text/javascript">
	
	function checkData(){
		var keyword=document.getElementById("keyword").value.trim();
		if(keyword==null || keyword==""){
			commonUtil.message("请输入您要查询的关键字！","danger");
			return false;
		}else{
			return true;
		}
	}

</script>
<div class="row">
		<div class="col-md-12" style="padding-top: 5px;"><!-- navbar-fixed-top  固定在顶部 -->
			<nav class="navbar navbar-default " style="background:#211E1C;border: 1px solid #211E1C">
			  <div class="container-fluid">
			    <!-- Brand and toggle get grouped for better mobile display -->
			    <div class="navbar-header">
			      <a class="navbar-brand" href="${pageContext.request.contextPath}/index.html"><font color="#FFF"><strong>Home</strong></font></a>
			    </div>
			
			    <!-- Collect the nav links, forms, and other content for toggling -->
			    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-12">
			      <ul class="nav navbar-nav">
			        <li><a href="${pageContext.request.contextPath}/blogger/about.html"><font color="#FFF"><strong>About</strong></font></a></li>
			        <li><a href="${pageContext.request.contextPath}/download.html"><font color="#FFF"><strong>Download</strong></font></a></li>
			        <li><a href="${pageContext.request.contextPath}/reprint.html"><font color="#FFF"><strong>Reprinted</strong></font></a></li>
			        <li><a href="${pageContext.request.contextPath}/music.html"><font color="#FFF"><strong>Music</strong></font></a></li>
			      </ul>
			      <form action="${pageContext.request.contextPath}/blog/search.html" 
			      	class="navbar-form navbar-right" role="search" 
			      	method="post" onsubmit="return checkData()">
			        <div class="form-group">
			          <input type="text" id="keyword" name="keyword" value="${keyword }" class="form-control" placeholder="请输入要查询的关键字...">
			        </div>
			        <button type="submit"  class="btn btn-primary">搜索</button>
			      </form>
			    </div><!-- /.navbar-collapse -->
			  </div><!-- /.container-fluid -->
			  <div class="row no-gutters" style="height:80px; background-color: #665F5B;">
				<div class="col-md-11" >
					
				</div>
				<div class="col-md-1">
				<!-- <iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=80 height=86 src="//music.163.com/outchain/player?type=2&id=1964496436&auto=0&height=66"></iframe> -->
				</div>
			</div>
			</nav>
		</div>
	</div>
