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
		$('#modifyPwDia').dialog('open');
	//ajax 异步提交
	/*
		$.ajax({
				url:rootpath+"/admin/pm/updateIK",
				type:"post",
				dataType:"text",
				cache:false,
				success:function(data){
					if(data == "success"){
						$.messager.alert('成功','更新成功!','info');
						//重新加载数据
					}else{
						 $.messager.alert('错误','更新失败!','error');
					}
				},
				error:function(e){
					$.messager.alert('错误','更新失败!','error');
				}
		});*/
	})
	/*
	$('#pp1').tooltip({
		position: 'right',
		content: '<span style="color:#fff">This is the tooltip message.</span>',
		onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
		}
	});*/
	
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

function searchBtnClick(){

	
	//为loadGrid()添加参数  
    var queryParams = $('#projTab').datagrid('options').queryParams;  
    queryParams.groupId = groupId;
    queryParams.projName = projName;  
    
     //查询后返回首页
    $('#projTab').datagrid('options').pageNumber = 1;  
    var p = $('#projTab').datagrid('getPager');
	p.pagination({pageNumber: 1}); 
 
    //重新加载datagrid的数据  
    $("#projTab").datagrid('reload'); 
}

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
		$.messager.alert('错误','两次新密码输入不一致!请重新输入','error');
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

//表格操作区域展示
function formatAction(value,row,index){
	var cName = "${cUserName}";
	a = '<a href="javascript:void(0)" id="a'+index+'">重置密码</a>&nbsp;';
	b = '<a href="javascript:void(0)" id="b'+index+'">删除用户</a>&nbsp;';
	c = '当前用户无权操作';
	var id = row.id;
	var name = row.userName;
	var accessToken = row.accessToken;
	var password = row.password;
	var createTime = row.createTime;
	
  	$(document).delegate("#a"+index,"click",function(){
  		var type = 1;
  		var result = '';
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
  	});
	
	$(document).delegate("#b"+index,"click",function(){
		var type = 0;
		$.messager.confirm('确认','确认删除用户'+name+'?',function(row){  
	        if(row){  
	      		var data = "id="+id+"&name="+name+"&accessToken="+accessToken+"&password="+password+"&createTime="+createTime+"&type="+type;
	      		//$.messager.alert(data);
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
  	});
	if(cName == "admin1"){
		return a+'/ '+b;
	}		
	else{
		 $("#projTab").datagrid('hideColumn', 'action');
		return c;
	}
}

</script>
</head>

<body>

	<table class="easyui-datagrid" id="projTab" style="width:95%;height:600px"
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
		</div>
	</div>
	
	<div id="modifyPwDia"  style="padding:5px;width:420px;height:320px;">
		<p>用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名:<label id="nameLabel"></label><input  id="nameInput" style="width:180px" readonly="true" /></p>
		<br/>
		<p>原&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码:<label id="oPwLabel"></label><input  id="oPwInput" style="width:180px" type=password /></p>
		<br/>
		<p>新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码:<label id="nPwLabel"></label><input  id="nPwInput" style="width:180px" type=password /></p>
		<br/>
		<p>新密码确认:<label id="nPwCfLabel"></label><input  id="nPwCfInput" style="width:180px" type=password /></p>
		<p><input id="rId" style="width:10px;display:none;"readonly="true"/></p>
		<p><input id="rType" style="width:10px;display:none;"readonly="true"/></p>
		<p><input id="cTime" style="width:10px;display:none;"readonly="true"/></p>
		<br/>
	</div>
</body>
</html>