<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<%@ include file="/common/common.jsp"%>
<title>新加数据分词</title>	
<link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css" /> 
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>

<script language="javascript">

$(function(){   
	$("#updateBtn").click(function(){
	//ajax 异步提交
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
		});
	})
	
    //更改弹出框
    $('#modifyDiv').dialog({
    			title:'人工分词',
    			closed:true,//初始时不显示
				buttons:[{
					text:'提交',
					iconCls:'icon-ok',
					handler:modifyManalWords
				},{
					text:'取消',
					handler:function(){
						$('#modifyDiv').dialog('close');
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

function modifyManalWords(){
	var Id = $("#rId").val();
	var pid = $("#IdInput").val();
	var name = $("#nameInput").val();
	var aliasName = $("#aNameInput").val();
	var type = $("#rType").val();
	var createTime = $("#cTime").val();
	var manualWords = $("#manualWordsInput").val();
	
	var data = "Id="+Id+"&pid="+pid+"&name="+name+"&aliasName="+aliasName+"&type="+type+"&createTime="+createTime+"&manualWords="+manualWords;
	var win = $.messager.progress({
        title:'Please waiting',
        msg:'Loading data...'
    });
	
	//ajax 异步提交
	$.ajax({
			url:rootpath+"/admin/pm/updatePart",
			type:"post",
			data:data,
			dataType:"json",
			cache:false,
			success:function(response){
				$.messager.progress('close');
				if(response.errorCode == 0){
					$('#modifyDiv').dialog('close');
					$.messager.alert('成功','更新成功!','info');
					//重新加载数据
					$("#projTab").datagrid('reload');  
				}else{
					 $('#modifyDiv').dialog('close');
					 $.messager.alert('错误','编辑失败1!','error');
				}
			},
			error:function(e){
				$('#modifyDiv').dialog('close');
				$.messager.alert('错误','编辑失败2!','error');
			}
	});
}

//表格操作区域展示
function formatAction(value,row,index){
	var a = '<a href="javascript:void(0)" id="a'+index+'">编辑</a>&nbsp;&nbsp;';
	//var b = '<a href="javascript:void(0)" id="b'+index+'">删除</a>';
	var b = '<a href="javascript:void(0)" onclick="del('+ index +')">删除</a>';
	var name = row.name;
	var aliasName = row.aliasName;
  	$(document).delegate("#a"+index,"click",function(){
  		var result = '';
  		var URL = "${ikurl}"+row.name+row.aliasName;

  		$.ajax({
			url:URL,
			type:"get",
			dataType:"json",
			cache:false,
			success:function(data){
				var length = data.tokens.length;
				result += data.tokens[0].token;
				if(length>1){
					for(var i=1;i<length;i++){
						result += '｜';
						result += data.tokens[i].token;
					}
				}
				
				$("#manualWordsInput_ik").val(result);
			},
			error:function(e){
				$.messager.alert('错误','编辑失败3!','error');
			}
	     });  		
  				
  		$("#IdInput").val(row.pid);
		$("#nameInput").val(row.name);
		$("#aNameInput").val(row.aliasName);
		$("#rId").val(row.id);
		$("#rType").val(row.type);
		$("#cTime").val(row.createTime);
				
		$("#manualWordsInput").val(row.manualWords);
		$('#modifyDiv').dialog('open');
  	});

	return a+'/ '+b;
}

function del(index){  //删除操作  
	$.messager.confirm('确认','此删除为物理删除，删除后不可恢复，请谨慎选择是否删除?',function(row1){
			if(row1)
			{
  			var selectedRow = $('#projTab').datagrid('getData').rows[index];//$('#projTab').datagrid('getSelected');  //获取选中行
            var pid = selectedRow.pid;
      		var name = selectedRow.name;
      		var data = "pid="+pid+"&name="+name;
            $.ajax({
                url:rootpath+"/admin/pm/delPar",
                type:"post",
  				data:data,
  				dataType:"json",
  				cache:false,
                success:function(response)
                {
                	if(response.errorCode == 0){
  						$.messager.alert('成功','删除成功!','info');
  						$('#projTab').datagrid('deleteRow',index);
  						$("#projTab").datagrid('reload');
  					}
  					else{
  						 $.messager.alert('错误','删除失败!','error');
  					}
                	//$("#projTab").datagrid('reload');
                },
                error:function(e){
  					$.messager.alert('错误','删除失败3!','error');
  				}
            }); 

			}
			//$("#projTab").datagrid('reload');
		})
	//$("#projTab").datagrid('reload');
}

//exportParticipleDic
$(function(){
	$("#exportParticipleDic").click(function(){
		//alert("功能暂未开放！");
		var isExport = confirm("确认导出分词?");
		if (isExport == true){
			$.ajax({
				url:rootpath+"/admin/pm/exportParticiple",
				type:"post",
				dataType:"json",
				cache:false,
				success:function(response){
					//$.messager.progress('close');
					if(response.errorCode == 0){
						//$('#modifyDiv').dialog('close');
						$.messager.alert('成功',response.errorMsg,'info');						
					}else{
						 $.messager.alert('错误',response.errorMsg,'error');
					}
				},
				error:function(e){					
					$.messager.alert('错误','导出失败2!','error');
				}
		});
		}
	});
});

$(function(){
	$('#pp1').tooltip({
		position: 'right',
		content: '<span style="color:#fff">This is the tooltip message.</span>',
		onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
		}
	});
});

</script>
</head>

<body onload="hideExport()">
<script>window.onload=hideExport</script>
<script language="javascript" for="window" event="onload">
function hideExport() {       
	var name = "${user.userName}";
	if(name != "admin1")
	{
		$("#exportParticipleDic").hide();
	}
};
</script>

	<table class="easyui-datagrid" id="projTab" style="width:100%;height:497px"
			url="<%=basePath %>/admin/pm/select4new" 
			title="新加数据分词" toolbar="#tb"fitColumns="true" pagination="true">
		<thead>
			<tr>
				<th field="pid" width="80" align="center">记录id</th>
				<th field="name" width="160" align="center" id="projName">名称</th>
				<th field="aliasName"  width="160" align="center">别名</th>
				<th field="participles" width="80" align="center">索引分词</th>
			    <th field="editor" width="80" align="center">编辑者</th>
				<th field="type" width="80" align="center">类型</th>
				<th field="createTime" width="160" align="center">创建时间</th>
				<th field="updateTime" width="160" align="center">最后更新时间</th>
				<th field="action" width="120" align="center" formatter="formatAction">编辑</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="javascript:void(0)" id="updateBtn" class="easyui-linkbutton" iconCls="icon-search">更新词库</a>
			<a href="javascript:void(0)" id="exportParticipleDic" class="easyui-linkbutton" iconCls="icon-export">导出分词词库</a>
			<!-- <form id="exportForm"  method="get" style="float:right">
			<a href="javascript:void(0)" id="exportParticipleDic" class="easyui-linkbutton" iconCls="icon-export">导出分词词库</a>
			</form> -->
		</div>
	</div>
	
	<div id="modifyDiv"  style="padding:5px;width:420px;height:320px;">
	    <p>记录Id:&nbsp;&nbsp;<label id="IdLabel"></label><input  id="IdInput" style="width:150px" readonly="true"/></p>
	    <br/>
		<p>名&nbsp;&nbsp;&nbsp;&nbsp;称:&nbsp;&nbsp;<label id="nameLabel"></label><input  id="nameInput" style="width:180px" readonly="true" /></p>
		<br/>
		<p>别&nbsp;&nbsp;&nbsp;&nbsp;名:&nbsp;&nbsp;<label id="aNameLabel"></label><input  id="aNameInput" style="width:180px" readonly="true" /></p>
		<br/>
		<p style="color:#008800">机器分词:<input id="manualWordsInput_ik" style="width:320px;color:#008800" readonly="true"/></p><!-- style="color:red" -->
		<br/>
		<p>人工分词:<input id="manualWordsInput" style="width:320px"/></p>
		<p><input id="rId" style="width:10px;display:none;"readonly="true"/></p>
		<p><input id="rType" style="width:10px;display:none;"readonly="true"/></p>
		<p><input id="cTime" style="width:10px;display:none;"readonly="true"/></p>
		<br/>
		<p><font size="2" color="blue">注:多个分词请用英文逗号连接  如:万科,北京,金科</font></p>
	</div>
</body>
</html>