<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Java开源博客系统后台管理页面-Powered by etjava</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function openTab(text,url,iconCls){
		if($("#tabs").tabs("exists",text)){
			$("#tabs").tabs("select",text);
		}else{
			var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='/admin/"+url+"'></iframe>";
			$("#tabs").tabs("add",{
				title:text,
				iconCls:iconCls,
				closable:true,
				content:content
			});
		}
	}
	
	function openPasswordModifyDialog(){
		$("#dlg").dialog("open").dialog("setTitle","修改密码");
	}

	function closedDlg(){
		$("#dlg").dialog("close");
		$("#userName").val('');
		$("#password").val('');
		$("#newPassword").val('');
	}
	
	function save(){
		$("#fm").form("submit",{
			url:"${pageContext.request.contextPath}/admin/user/updatePassword.do",
			onSubmit:function(){
				var newPassword=$("#password").val();
				var newPassword2=$("#newPassword").val();
				if(!$(this).form("validate")){
					return false;
				}
				if(newPassword!=newPassword2){
					$.messager.alert("系统提示","两次密码不一致！");
					return false;
				}
				return true;
			},
			success:function(result){
				var result2 = eval('('+result+')');
				if(result2.success){
					$.messager.alert("系统提示","密码修改成功 下次登录生效");
					closedDlg();
				}else{
					$.messager.alert("系统提示",result2.errorInfo);
					return;
				}
			}
		});
	}
	
	function refreshSystem(){
		$.post("${pageContext.request.contextPath}/sys/refresh.html",
				{},function(result){
            if(result.success){
                alert("缓存刷新成功！");
                resultValue();
            }else{
                alert("缓存刷新失败！");
            }
        },"json");
	}
	
	function logout(){
		$.messager.confirm("系统提示","您确定要退出系统吗?",function(r){
			if(r){
				window.location.href="${pageContext.request.contextPath}/admin/user/logout.do";
			}
		});
	}
</script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 78px;background-color: #E0ECFF">
	<table style="padding: 5px" width="100%">
		<tr>
			<td width="50%">
				<img alt="logo" src="/static/images/logo.png" style="height:60px">
			</td>
			<td valign="bottom" align="right" width="50%">
				<font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${currentUser.userName }</font>
			</td>
		</tr>
	</table>
</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页" data-options="iconCls:'icon-home'">
			<div align="center" style="padding-top: 100px"><font color="red" size="10">欢迎使用</font></div>
		</div>
	</div>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
	<div class="easyui-accordion" data-options="fit:true,border:false">
		<div title="常用操作" data-options="selected:true,iconCls:'icon-item'" style="padding: 10px">
			<a href="javascript:openTab('写博客','writeBlog.jsp','icon-writeblog')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">写博客</a>
			<a href="javascript:openTab('评论审核','commentReview.jsp','icon-review')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
		</div>
		<div title="博客管理"  data-options="iconCls:'icon-bkgl'" style="padding:10px;">
			<a href="javascript:openTab('写博客','writeBlog.jsp','icon-writeblog')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px;">写博客</a>
			<a href="javascript:openTab('博客信息管理','blogManage.jsp','icon-bkgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-bkgl'" style="width: 150px;">博客信息管理</a>
		</div>
		<div title="转载文章管理"  data-options="iconCls:'icon-reprintedmanage'" style="padding:10px;">
			<a href="javascript:openTab('已发布文章管理','releaseCrawlerManage.jsp','icon-reprintedblog')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reprintedblog'" style="width: 150px;">已发布文章管理</a>
			<a href="javascript:openTab('被驳回文章管理','blogRejectCrawlerManage.jsp','icon-reprintedmanage')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reprintedmanage'" style="width: 150px;">被驳回文章管理</a>
			<a href="javascript:openTab('未审核文章管理','blogCrawlerManage.jsp','icon-reprintedmanage')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reprintedmanage'" style="width: 150px;">未审核文章管理</a>
		</div>
		<div title="博客类别管理" data-options="iconCls:'icon-bklb'" style="padding:10px">
			<a href="javascript:openTab('博客类别信息管理','blogTypeManage.jsp','icon-bklb')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-bklb'" style="width: 150px;">博客类别信息管理</a>
		</div>
		<div title="评论管理"  data-options="iconCls:'icon-plgl'" style="padding:10px">
			<a href="javascript:openTab('评论审核','commentReview.jsp','icon-review')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
			<a href="javascript:openTab('评论信息管理','commentManage.jsp','icon-plgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-plgl'" style="width: 150px;">评论信息管理</a>
		</div>
		<div title="个人信息管理"  data-options="iconCls:'icon-grxx'" style="padding:10px">
			<a href="javascript:openTab('修改个人信息','modifyInfo.jsp','icon-grxxxg')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-grxxxg'" style="width: 150px;">修改个人信息</a>
		</div>
		<div title="系统管理"  data-options="iconCls:'icon-system'" style="padding:10px">
		    <a href="javascript:openTab('友情链接管理','linkManage.jsp','icon-link')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-link'" style="width: 150px">友情链接管理</a>
			<a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
			<a href="javascript:refreshSystem()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
			<a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
		</div>
	</div>
</div>
<div region="south" style="height: 25px;padding: 5px" align="center">
	Copyright © 2012-2026
</div>

<div id="dlg" class="easyui-dialog" style="width:600px; height:250px;padding: 10px 20px"
	modal=true
	closed="true" buttons="#dlg-button">
	<form id="fm" method="post">
		<table cellspacing="10px">
			<tr>
				<td>用户名：</td>
				<td>
					<input type="hidden" id="id" name="id" value="${currentUser.id }" />
					<input type="text" style="width:400px;height:25px;" id="userName" value="${currentUser.userName }" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>新密码：</td>
				<td>
					<input type="password" style="width:400px;height:25px;"  id="password" name="password" class="easyui-validatebox" required="true" />
				</td>
			</tr>
			<tr>
				<td>确认新密码：</td>
				<td>
					<input type="password" style="width:400px;height:25px;"  id="newPassword" name="newPassword" class="easyui-validatebox" required="true" />
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