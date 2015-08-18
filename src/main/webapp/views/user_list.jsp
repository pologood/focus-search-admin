<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<%@ include file="/common/common.jsp"%>
<title>用户列表</title>	
<link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css" /> 
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>

<script language="javascript">

$(function(){  
	$("#modifyPwB").click(function(){
		var name = "${cUserName}";
		$("#nameInput").val(name);
		$("#oPwInput").val('');
		$("#nPwInput").val('');
		$("#nPwCfInput").val('');
		$('#modifyPwDia').dialog('open');
	})
	
	var name = "${cUserName}";
	if(name!= "admin1"){
		$("#addUserB").attr("disabled","disabled"); 
		$("#addUserB").hide();
	}
	else{
		$("#addUserB").removeAttr("disabled");//启用按钮
		$("#addUserB").click(function(){
			$("#uNameInput").val('');
			$("#uPwInput").val('');
			$('#addUserDia').dialog('open');
		})
	}
		
	//更改弹出框
    $('#addUserDia').dialog({
    			title:'添加新用户',
    			closed:true,//初始时不显示
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:addNewUser
				},{
					text:'取消',
					handler:function(){
						$('#addUserDia').dialog('close');
					}
				}]
	 });
	
    //更改弹出框
    $('#modifyPwDia').dialog({
    			title:'密码修改',
    			closed:true,//初始时不显示
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:modifyUPw
				},{
					text:'取消',
					handler:function(){
						$('#modifyPwDia').dialog('close');
					}
				}]
	 });
	
});

/***
 * 用户修改个人密码
 */
function modifyUPw(){
	var name = $("#nameInput").val();
	var oPw = $("#oPwInput").val();
	var nPw = $("#nPwInput").val();
	var nPwCf = $("#nPwCfInput").val();
	if(oPw == ''||nPw == ''||nPwCf == ''){
		$.messager.alert('错误','填写不完整！请确认是否已全部填写！','error');
		return;
	}
	else if(oPw == nPw){
		$.messager.alert('错误','新密码不能与原密码相同!请重新输入！','error');
		return;
	}
	else if(nPw!=nPwCf){
		$.messager.alert('错误','两次新密码输入不一致!请重新输入!','error');
		return;
	}

	var data = "name="+name+"&oPw="+oPw+"&nPw="+nPw;
	var win = $.messager.progress({
        title:'Please waiting',
        msg:'Loading data...'
    });
	
	//ajax 异步提交
	$.ajax({
			url:rootpath+"/user/um/updatePw",
			type:"post",
			data:data,
			dataType:"json",
			cache:false,
			success:function(response){
				$.messager.progress('close');
				if(response.errorCode == 0){
					$('#modifyPwDia').dialog('close');
					$.messager.alert('成功','密码修改成功!','info');
					//重新加载数据
					$("#projTab").datagrid('reload');  
				}else{
					 $('#modifyPwDia').dialog('close');
					 $.messager.alert('错误','密码修改失败!请确认密码是否输入正确！','error');
				}
			},
			error:function(e){
				$('#modifyPwDia').dialog('close');
				$.messager.alert('错误','密码修改失败!请确认密码是否输入正确！','error');
			}
	});
}
/***
 * 添加新用户
 */
function addNewUser(){
	var name = $("#uNameInput").val();
	var password = $("#uPwInput").val();
	if(name == ''||password == ''){
		$.messager.alert('错误','填写不完整！请确认是否已全部填写！','error');
		return;
	}

	var data = "name="+name+"&password="+password;
	var win = $.messager.progress({
        title:'Please waiting',
        msg:'Loading data...'
    });
	
	//ajax 异步提交
	$.ajax({
			url:rootpath+"/user/um/addNewUser",
			type:"post",
			data:data,
			dataType:"json",
			cache:false,
			success:function(response){
				$.messager.progress('close');
				if(response.errorCode == 0){
					$('#addUserDia').dialog('close');
					$.messager.alert('成功','用户添加成功!','info');
					//重新加载数据
					$("#projTab").datagrid('reload');  
				}else{
					 $('#addUserDia').dialog('close');
					 $.messager.alert('错误','用户添加失败!','error');
				}
			},
			error:function(e){
				$('#addUserDia').dialog('close');
				$.messager.alert('错误','用户添加失败!','error');
			}
	});
}

/***
 * 表格操作区域功能填充
 */
function formatAction(value,row,index){
	var cName = "${cUserName}";
	var id = row.id;

	var a = '<a href="javascript:void(0)" onclick="reset('+ index +','+ id +')">重置密码</a> ';
	var b = '<a href="javascript:void(0)" onclick="del('+ index +','+ id +')">删除用户</a> ';
	var c = '当前用户无权操作';
	
	if(cName == "admin1"){
		return a+'/ '+b;
	}		
	else{
		 $("#projTab").datagrid('hideColumn', 'action');
		return c;
	}
}
/***
 * 重置用户密码
 */
function reset(index,id){
	var type = 1;
	var selectedRow = $('#projTab').datagrid('getData').rows[index];  //获取选中行
	var id = selectedRow.id;
	var name = selectedRow.userName;
	var accessToken = selectedRow.accessToken;
	var password = selectedRow.password;
	var createTime = selectedRow.createTime;
	var data = "id="+id+"&name="+name+"&accessToken="+accessToken+"&password="+password+"&createTime="+createTime+"&type="+type;
	$.ajax({
		url:rootpath+"/user/um/updateUser",
        type:"post",
		data:data,
		dataType:"json",
		cache:false,
		success:function(response){
			if(response.errorCode == 0){
				$.messager.alert('成功','密码重置成功!','info');
				//重新加载数据
			}else{
				 $.messager.alert('错误','密码重置失败!','error');
			}			
		},
		error:function(e){
			$.messager.alert('错误','密码重置失败!','error');
		}
	});
}
/***
 * 删除用户
 */
function del(index,id){  //删除操作  	
	var type = 0;
	$.messager.confirm('确认','确认删除用户'+name+'?',function(row){  
		if(row){
			var selectedRow = $('#projTab').datagrid('getData').rows[index];  //获取选中行
			var id = selectedRow.id;
  			var name = selectedRow.userName;
  			var accessToken = selectedRow.accessToken;
  			var password = selectedRow.password;
  			var createTime = selectedRow.createTime;
      		var data = "id="+id+"&name="+name+"&accessToken="+accessToken+"&password="+password+"&createTime="+createTime+"&type="+type;
            $.ajax({  
                url:rootpath+"/user/um/updateUser",
                type:"post",
    			data:data,
    			dataType:"json",
    			cache:false,
    			success:function(response){
    				if(response.errorCode == 0){
    					$.messager.alert('成功','用户删除成功!','info');
    					//重新加载数据
    					$("#projTab").datagrid('reload');  
    				}else{
    					 $.messager.alert('错误','用户删除失败!','error');
    				}
    			},
                error:function(e){
  					$.messager.alert('错误','用户删除失败!','error');
  				}
            });
		}
    }) 
    $("#projTab").datagrid('reload');
  } 


</script>
</head>

<body>

	<table class="easyui-datagrid" id="projTab" style="width:95%;height:497px"
			url="<%=basePath %>/user/um/showAllUsers" 
			title="查看用户列表" toolbar="#tb" fitColumns="true" pagination="true">
		<thead>
			<tr>
				<th field="id" width="60" align="center">记录id</th>
				<th field="userName" width="140" align="center" id="projName">用户名</th>
				<th field="createTime" width="160" align="center">创建时间</th>
				<th field="updateTime" width="160" align="center">最后更新时间</th>
				<th field="action" width="160" align="center" formatter="formatAction">用户管理</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="javascript:void(0)" id="modifyPwB" class="easyui-linkbutton" iconCls="icon-edit">修改密码</a>
			<a href="javascript:void(0)" id="addUserB" class="easyui-linkbutton" iconCls="icon-add">添加用户</a>
		</div>
	</div>
	
	<div id="modifyPwDia"  style="padding:5px;width:300px;height:260px;">
		<br/>
		<p>&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名:&nbsp;<label id="nameLabel"></label><input  id="nameInput" style="width:180px" readonly="true" /></p>
		<br/>
		<p>&nbsp;&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码:&nbsp;<label id="oPwLabel"></label><input  id="oPwInput" style="width:180px" type=password /></p>
		<br/>
		<p>&nbsp;&nbsp;&nbsp;新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码:&nbsp;<label id="nPwLabel"></label><input  id="nPwInput" style="width:180px" type=password /></p>
		<br/>
		<p>&nbsp;&nbsp;&nbsp;新密码确认:<label id="nPwCfLabel"></label><input  id="nPwCfInput" style="width:180px" type=password /></p>
		<br/>
	</div>
	
	<div id="addUserDia"  style="padding:5px;width:300px;height:200px;">
		<br/>
		<p>&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名:&nbsp;&nbsp;<label id="uNameLabel"></label><input  id="uNameInput" style="width:180px" /></p>
		<br/>
		<p>&nbsp;&nbsp;&nbsp;初&nbsp;始&nbsp;密&nbsp;码:&nbsp;&nbsp;<label id="uPwLabel"></label><input  id="uPwInput" style="width:180px" type=password /></p>
		<br/>
	</div>
	
</body>
</html>