<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<title>搜索后台管理系统</title>
<%@ include file="/common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style type="text/css">
<!--
.easyui-accordion div p{
	cursor:pointer;
}
.manageHeader{
	background:url(/static/images/bg.png) #FFF repeat-x center top;
	height:77px;
}
.welcomeTitle{
	float:left;
	width:auto;
	margin-top:20px;
	margin-left: 10px;
}
.welcomeTitle span{
	float:left;
	margin:-1px 5px 0 5px;
}
.loginUser{
	float:right;
	width:auto;	
	margin-top: 30px;
	margin-right:60px;
}
.logout{
	float:right;
	width:auto;	
	margin-top: 30px;
	margin-right:50px;
}
.clickBefore{
	padding:0 15px;
}
-->
</style>
<script type="text/javascript">
$(function(){
	$(".easyui-accordion p").click(function(){
		var sideBar = $(this).html();
		if($(this).attr("src")){
			if($('#tt').tabs('exists',sideBar)){
				$('#tt').tabs('select',sideBar);
			}else{
				$("#tt").tabs("add",{
					title:sideBar,
					content:"<iframe frameborder='0' id='"+$(this).attr("id")+"' width='100%' height='100%' src='"+$(this).attr("src")+"'></iframe>",
					closable:true
				});
			}
		}
	});
});

$(function(){   
	$("#loadDicBtn").click(function(){
		var isUpdate = confirm('该操作将耗费很多服务器资源，请慎重操作！');
		if (isUpdate == true)
		{
			//ajax 异步提交
			$.ajax({
					url:rootpath+"/reloadRemoteDic",
					type:"post",
	  				dataType:"json",
					cache:false,
					success:function(data){
						if(data.errorMsg == "success"){
							$.messager.alert('成功','加载成功!','info');
							//重新加载数据
						}else{
							 $.messager.alert('错误','加载失败!','error');
						}
					},
					error:function(e){
						$.messager.alert('错误','加载失败!','error');
					}
			});
		}		
	})
});
</script>
</head>
<body class="easyui-layout">
	<div region="north"  class="manageHeader">
		<div class="welcomeTitle"><h2><font color="#FFFFFF">欢迎来到搜索后台管理系统!</font></h2></div>
		<div class="logout"><a href="/search/admin/logout"><font color="#FFFFFF">退出登录</font></a></div>
		<div class="loginUser"><font color="#FFFFFF">用户信息：${userName }</font></div>
	</div>
	<div region="west" split="true" title="菜单" style="width:280px;padding:0px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="分词管理" style="padding:10px">
			    <p class="clickBefore" src="/user/um/userList">用户信息管理</p>
				<p class="clickBefore" src="/admin/pm/ptindex">分词效果</p>
				<p class="clickBefore" src="/admin/pm/hot">添加热词</p>
				<p class="clickBefore" src="/admin/pm/stop">添加停止词</p>
				<p class="clickBefore" id="loadDicBtn">更新词库</p>
			</div>
		</div>
	</div>
	<div region="center" title="搜索后台管理系统" style="overflow:hidden;" id="menuTitle">
		<div id="tt" tools="#tab-tools" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎页面" style="padding:20px;overflow:hidden;" id="tabContent"> 
				<iframe frameborder='0' width='100%' height='100%' src='/views/welcome.jsp'></iframe>
			</div>
		</div>
	</div>
	<div style="height:5px;background:#efefef;border:none;" region="south"></div>
</body>
</html>