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
	
	function changeReview(val,row){
		debugger
		var v = val.substr(0,50);
		return "<a href='javascript:;' onclick='openReviewDialog("+row+")' >"+v+"</a>";
	}
	
	function openReviewDialog(o){
		debugger
		$("#dlg").dialog("open").dialog("setTitle","查看评论信息");
		$("#content").val(o.content);
		$("#blogName").val('dasda');
	}
	
	
	function closedDlg(){
		$("#dlg").dialog("close");
		$("#blogName").val('');
		$("#content").val('');
		$("#dg").datagrid("reload");
		
	}
	function formatState(val,row){
		if(val==0){
			return "<font color=blue >待审核</font>"
		}else if(val==1){
			return "<font color=green >审核通过</font>"
		}else if(val==2){
			return "<font color=red >审核驳回</font>"
		}
	}
	
	function deleteReview(state){
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
				$.post("${pageContext.request.contextPath}/admin/comment/delete.html",{ids:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","删除成功！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","删除失败");
					}
				},"json");
			}
		});
	}
	
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="博客评论管理" class="easyui-datagrid" 
  fitColumns="true" pagination="true" rownumbers="true"
  url="${pageContext.request.contextPath}/admin/comment/list.html" fit="true" 
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
  		<th field="content" width="200" align="center" formatter="changeReview">评论内容</th>
  		<th field="commonDate" width="50" align="center">评论时间</th>
  		<th field="state" width="50" align="center" formatter="formatState">评论状态</th>
  	</tr>
  </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:deleteReview()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除评论信息</a>
	</div>
	<div>
		<center><font color=red >**博客评论内容应弹窗查看 这里暂时忽略 后续完善</font></center>
	</div>
</div>

<!-- 查看评论信息弹窗 modal=true 遮层只在dialog弹窗中有效 如果是datagrid 参考 https://www.cnblogs.com/s09122289/p/4569766.html -->
<div id="dlg" class="easyui-dialog" style="width:1024px; height:500px;padding: 10px 20px"
	modal=true
	closed="true" buttons="#dlg-button">
	<form id="fm" method="post">
		<table cellspacing="10px">
			<tr>
				<td>博客名称：</td>
				<td>
					<input type="text" id="blogName"  />
				</td>
			</tr>
			<tr>
				<td align="top">评论信息：</td>
				<td>
					<textarea rows="18" cols="116" id="content"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg-button">
	<a href="javascript:closedDlg()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
</div>
</body>
</html>