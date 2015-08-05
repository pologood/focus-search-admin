<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<%@ include file="/common/common.jsp"%>
<title>添加热词</title>	
<link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css" /> 
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>

<script language="javascript">

$(function(){   
	$("#updateHotDic").click(function(){
	//ajax 异步提交
		$.ajax({
				url:rootpath+"/hot/updateHot",
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
    			title:'添加热词',
    			closed:true,//初始时不显示
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:addHotWords
				},{
					text:'取消',
					iconCls:'icon-cancel',
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

//表格操作区域展示
function formatAction(value,row,index){
	a = '<a href="javascript:void(0)" id="a'+index+'">删除</a>&nbsp;&nbsp;'
	var id = row.id;
	var name = row.name;	
	var data = "id="+id+"&name="+name;
	
  	$(document).delegate("#a"+index,"click",function(){
  		
  		$.ajax({
  			
			url:rootpath+"/hot/delHot",
			type:"post",
			data:data,
			dataType:"json",
			cache:false,
			success:function(response){								
				if(response.errorCode == 0){
					$.messager.alert('成功','删除成功!','info');
					//重新加载数据
					$("#projTab").datagrid('reload');
				}else{
					 $.messager.alert('错误','删除失败!','error');
				}
			},
			error:function(e){
				$.messager.alert('错误','删除失败3!','error');
			}
	});
  	});
	return a;
}

function addHotWords(){
	var type = $("#typeInput").val();
	var stopWords = $("#hotWordsInput").val();
	
	var data = "type="+type+"&hotWords="+stopWords;
	var win = $.messager.progress({
        title:'Please waiting',
        msg:'Loading data...'
    });
	
	//ajax 异步提交
	$.ajax({
			url:rootpath+"/hot/addHot",
			type:"post",
			data:data,
			dataType:"json",
			cache:false,
			success:function(response){
				$.messager.progress('close');
				if(response.errorCode == 0){
					$('#modifyDiv').dialog('close');
					$.messager.alert('成功','添加成功!','info');
					//重新加载数据
					$("#projTab").datagrid('reload');  
				}else{
					 $('#modifyDiv').dialog('close');
					 $.messager.alert('错误','添加失败1!','error');
				}
			},
			error:function(e){
				$('#modifyDiv').dialog('close');
				$.messager.alert('错误','添加失败2!','error');
			}
	});
}

//addBtn
$(function(){
	$("#addBtn").click(function(){
		$('#modifyDiv').dialog('open');
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

<body>

	<table class="easyui-datagrid" id="projTab" style="width:95%;height:600px"
			url="<%=basePath %>/hot/loadHot" 
			title="添加热词" toolbar="#tb"fitColumns="true" pagination="true">
		<thead>
			<tr>
				<th field="id" width="80" align="center">id</th>
				<th field="name" width="160" align="center" id="projName">名称</th>
				<th field="type" width="80" align="center">类型</th>
				<th field="status" width="80" align="center">状态</th>
				<th field="editor" width="80" align="center">编辑者</th>
				<th field="createTime" width="160" align="center">创建时间</th>
				<th field="updateTime" width="160" align="center">最后更新时间</th>
				<th field="action" width="80" align="center" formatter="formatAction">删除</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="addBtn">添加热词</a>
			<a href="javascript:void(0)" id="updateHotDic" class="easyui-linkbutton" iconCls="icon-add">更新热词词库</a>
		</div>
	</div>
	
	<div id="modifyDiv"  style="padding:5px;width:420px;height:320px;">
		<br/>
		<p>类型:<label id="TypeLabel"></label><input  id="typeInput" style="width:150px"/></p>
		<br/>
		<p><font size="2" color="blue">注:1代表楼盘，2代表新闻，只能填写一个类型号！</font></p>
		<br/>
		<p>热词:<input id="hotWordsInput" style="width:320px"/></p>
		<!-- <p><input id="rId" style="width:10px;display:none;"readonly="trye"/></p> -->
		<p><input id="rType" style="width:10px;display:none;"readonly="true"/></p>
		<p><input id="cTime" style="width:10px;display:none;"readonly="true"/></p>
		<br/>
		<p><font size="2" color="blue">注:多个停用词请用英文逗号空格或中文逗号空格连接  如:万科,北京,金科</font></p>
	</div>
</body>
</html>