<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
//解决工具栏错位问题 ，在需要的地方引入即可 或修改ueditor.config.js
var ue = UE.getEditor('profile',{
	autoFloatEnabled:false//是否保持toolbar位置不动
});
    
    function submitData(){
        var nickName=$("#nickName").val();
        var sign=$("#sign").val();
        var profile=UE.getEditor('profile').getContent()
        
        if(nickName==null || nickName==''){
            alert("请输入昵称！");
        }else if(sign==null || sign==''){
            alert("个性签名不能为空！");
        }else if(profile==null || profile==''){
            alert("请填写简介内容！");
        }else{
            // form方式提交表单内容
            //$("#pf").val(profile);// 将值放到隐藏域中提交 因为编辑器中的内容无法通过表单直接提交 可以通过ajax方式提交
            $("#fm1").submit();
            setTimeout("asdsadsa",2000);
        }
    }
    
    function resultValue(){
        $("#nickName").val("");
        $("#sign").val("");
        UE.getEditor('profile').setContent('');
       // 放在Controller中拼接 window.parent.$('#tabs').tabs('close', '修改个人信息');// main.jsp中设置的tab页名称
    }
</script>
</head>
<body style="margin: 10px">

<div id="p" class="easyui-panel" title="个人信息管理" style="padding: 10px">
	<form id="fm1" action="${pageContext.request.contextPath}/admin/user/update.html" method="post" enctype="multipart/form-data">
    <table cellspacing="20px">
        <tr>
            <td width="80px"><strong>用户名：</strong></td>
            <td>
            	<input type="hidden" name="id" id="id" value="${currentUser.id }" />
                <input type="text" id="userName" name="userName" value="${currentUser.userName }" style="width: 600px;height:30px;" readonly="readonly"/>
            </td>
        </tr>
        <tr>
            <td width="80px"><strong>昵称：</strong></td>
            <td>
                <input type="text" id="nickName" name="nickName" style="width: 600px;height:30px;"/>
            </td>
        </tr>
        <tr>
            <td width="80px"><strong>个性签名：</strong></td>
            <td>
                <input type="text" id="sign" name="sign" style="width: 600px;height:30px;"/>
            </td>
        </tr>
        <tr>
            <td width="80px"><strong>头像：</strong></td>
            <td>
            	<img src="${pageContext.request.contextPath}/static/userImages/${currentUser.imageName}" style="width:10%"/>
                <input type="file" id="imageFileXXXXX" name="imageFileXXXXX"/>
            </td>
        </tr>
        <tr>
            <td valign="top"><strong>个人简介：</strong></td>
            <td>
                <script id="profile" name="profile" type="text/plain" style="width:97%;height:450px;"></script>
            	<!-- <input type="hidden" id="pf" name="profile" /> -->
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">提交修改</a>
            </td>
        </tr>
    </table>
    </form>
</div>

<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('profile');
    
    // ueditor 监听  - 请求后台获取数据
    ue.addListener("ready",function(){
    	// 通过ajax请求数据 - 只能是ueditor封装的ajax才可以
    	UE.ajax.request("${pageContext.request.contextPath}/admin/user/find.html",
    			{
    				method:"post",
    				async:false, // 默认异步，必须带async 否则请求不到后台
    				data:{"id":"1"},
    				onsuccess:function(result){
    					result=eval("("+result.responseText+")");
    					$("#nickName").val(result.nickName);
    					$("#sign").val(result.sign);
    					UE.getEditor('profile').setContent(result.profile);
    				}
   			});
    });
</script>


</body>
</html>