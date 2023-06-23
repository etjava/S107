<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友情链接管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function formatUrl(val,row){
		return "<a href='"+val+"' target='_blank' >"+val+"</a>";
	}
	
	function searchLink(){
		$("#dg").datagrid('load',{
			"linkName":$("#s_linkName").val()
		});
	}
	
	function addLinkDlg(){
		resetValue();
		$("#dlg").dialog("open").dialog("setTitle","添加友情链接信息");
		url = "${pageContext.request.contextPath}/admin/link/save.html";
	}
	
	function modifyLinkDlg(){
		resetValue();
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要修改的链接信息！");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","修改链接信息");
		
		// 当前窗口中的弹窗 可以直接将值复制到form表单中
		$("#fm").form("load",row);
		url = "${pageContext.request.contextPath}/admin/link/save.html?id="+row.id;
	}
	
	function save(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				// 调用form表单中设置的验证方法
				return $(this).form("validate");
			},
			success:function(result){
				var result2 = eval('('+result+')');
				if(result2.success){
					$.messager.alert("系统提示","数据保存成功");
					resetValue();// 清空表单
					$("#dlg").dialog("close");
					// 刷新列表
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示",result2.errorInfo);
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#linkName").val('');
		$("#linkUrl").val('');
		$("#orderNo").val('');
	}
	
	
	function closedDlg(){
		$("#dlg").dialog("close");
	}
	
	function deleteLink(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/admin/link/delete.html",{ids:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","数据已成功删除！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示",result.errInfo);
					}
				},"json");
			}
		});
	}
	
	
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="友情链接管理" class="easyui-datagrid" 
  fitColumns="true" pagination="true" rownumbers="true"
  url="${pageContext.request.contextPath}/admin/link/list.html" fit="true" 
  toolbar="#tb" scrollbarSize=0  
  data-options="striped:true">
  <!-- 
  	属性striped设置为true，即striped:true
  	如果想更改颜色，可以更改easyui.css中的.datagrid-row-alt样式。
  	,singleSelect:true 设置为单选
   -->
  <thead>
  	<tr>
  		<th field="cb" checkbox="true" align="center"></th>
  		<th field="id" width="20" align="center">编号</th>
  		<th field="linkName" width="50" align="center">链接名称</th>
  		<th field="linkUrl" width="100" align="center" formatter="formatUrl">链接地址</th>
  		<th field="orderNo" width="50" align="center">排序序号</th>
  	</tr>
  </thead>
</table>
<div id="tb">
	<div>
		&nbsp;&nbsp;链接名称 &nbsp;&nbsp;<input type="text" id="s_linkName" size="20" onkeydown="if(event.keyCode==13) searchLink()"/>
		<a href="javascript:searchLink()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div>
		<a href="javascript:addLinkDlg()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:modifyLinkDlg()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteLink()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
</div>

<!-- 修改博客类别弹窗 modal=true 遮层只在dialog弹窗中有效 如果是datagrid 参考 https://www.cnblogs.com/s09122289/p/4569766.html -->
<div id="dlg" class="easyui-dialog" style="width:600px; height:250px;padding: 10px 20px"
	modal=true
	closed="true" buttons="#dlg-button">
	<form id="fm" method="post">
		<table cellspacing="10px">
			<tr>
				<td>链接名称：</td>
				<td>
					<input type="text" style="width:400px;height:25px;" id="linkName" name="linkName" class="easyui-validatebox" required="true" />
				</td>
			</tr>
			<tr>
				<td>链接地址：</td>
				<td>
					<input type="text" style="width:400px;height:25px;"  id="linkUrl" name="linkUrl" class="easyui-validatebox" required="true" />
				</td>
			</tr>
			<tr>
				<td>排序：</td>
				<td>
					<input type="text" style="width:40px;height:25px;"  id="orderNo" name="orderNo" class="easyui-numberbox" required="true" style="width:50px" />&nbsp;&nbsp;(类别按从小到大排序)
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg-button">
	<a href="javascript:save()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
	<a href="javascript:closedDlg()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
</div>
</body>
</html>