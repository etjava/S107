<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论审核页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	
	function formatBlogTitle(val,row){
		if(val==null){
			return "<font color=red>文章已删除</font>"
		}else{
			return "<a target='_blank' href='${pageContext.request.contextPath}/blog/articles/"+val.id+".html' >"+val.title+"</a>"
		}
	}
	
	function commentReview(state){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要审核的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确定要审核这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/admin/comment/review.html",{ids:ids,state:state},function(result){
					if(result.success){
						$.messager.alert("系统提示","审核完成！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","提交失败");
					}
				},"json");
			}
		});
	}
	
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="博客评论审核管理" class="easyui-datagrid" 
  fitColumns="true" pagination="true" rownumbers="true"
  url="${pageContext.request.contextPath}/admin/comment/list.html?state=0" fit="true" 
  toolbar="#tb" scrollbarSize=0  
  data-options="striped:true">
  <!-- 
  	属性striped设置为true，即striped:true
  	如果想更改颜色，可以更改easyui.css中的.datagrid-row-alt样式。
  	singleSelect:true 设置为单选
   -->
  <thead>
  	<tr>
  		<th field="cb" checkbox="true" align="center"></th>
  		<th field="id" width="20" align="center">编号</th>
  		<th field="blog" width="200" align="center" formatter="formatBlogTitle">博客名称</th>
  		<th field="userAddr" width="50" align="center">用户IP</th>
  		<th field="content" width="200" align="center">评论内容</th>
  		<th field="commonDate" width="50" align="center">评论时间</th>
  	</tr>
  </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:commentReview(1)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">通过审核</a>
		<a href="javascript:commentReview(2)" class="easyui-linkbutton" iconCls="icon-no" plain="true">驳回审核</a>
	</div>
</div>
</body>
</html>