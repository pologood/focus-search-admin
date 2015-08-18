<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<%@ include file="/common/common.jsp"%>
<title>修改楼盘分词</title>	
<link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css" /> 
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>

<script language="javascript">

$(function(){
    //搜索按钮点击事件
    $("#searchBtn").click(searchBtnClick);
    
    //更改弹出框
    $('#modifyDiv').dialog({
    			title:'更改 人工分词',
    			closed:true,//初始时不显示
				buttons:[{
					text:'确定',
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
	var groupId = $("#projIdText").val();
	var projName = $("#projNameText").val();
	
	if((groupId==null || groupId=="") && (projName==null || projName=="")){
		alert("请输入楼盘id或楼盘名")
		return;
	}
	
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
	var groupId = $("#groupIdInput").val();
	var manualWords = $("#manualWordsInput").val();
	
	var data = "groupId="+groupId+"&manualWords="+manualWords
	var win = $.messager.progress({
        title:'Please waiting',
        msg:'Loading data...'
    });
	
	//ajax 异步提交
	$.ajax({
			url:rootpath+"/admin/pm/modify_proj",
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
					 $.messager.alert('错误','编辑失败!','error');
				}
			},
			error:function(e){
				$('#modifyDiv').dialog('close');
				$.messager.alert('错误','编辑失败!','error');
			}
	});
}

//表格操作区域展示
function formatAction(value,row,index){
	a = '<a href="javascript:void(0)" id="a'+index+'">编辑</a>&nbsp;&nbsp;'
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
						result += ',';
						result += data.tokens[i].token;
					}
				}
				
				$("#manualWordsInput_ik").val(result);
			},
			error:function(e){
				$.messager.alert('错误','编辑失败3!','error');
			}
	     });  		
  		$("#groupIdInput").val(row.pid);
		$("#projNameInput").val(row.name);
		$("#manualWordsInput").val(row.participles);
		$('#modifyDiv').dialog('open');
  	});
	return a;
}


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

	<table class="easyui-datagrid" id="projTab" style="width:100%;height:497px"
			url="<%=basePath %>/admin/pm/proj_query" 
			title="修改楼盘分词" toolbar="#tb" fitColumns="true" pagination="true">
		<thead>
			<tr>
				<th field="id" width="50" align="center">记录ID</th>
				<th field="pid" width="50" align="center" id="projName">楼盘ID</th>
				<th field="name"  width="80" align="center">楼盘名称</th>
				<th field="aliasName" align="center" width="80" align="center">别名</th>
				<th field="participles" width="120" align="center">楼盘分词</th>
			    <th field="editor" width="50" align="center">编辑者</th>
				<th field="type" width="30" align="center">类型</th>
				<th field="status" width="30" align="center">状态</th>
				<th field="createTime" width="95" align="center">创建时间</th>
				<th field="updateTime" width="95" align="center">更新时间</th>
				<th field="action" width="40" align="center" formatter="formatAction">更改</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div>
			楼盘ID:
			<input type="text" id="projIdText" style="width:130px" ></input>
			&nbsp;&nbsp;&nbsp;&nbsp;
			楼盘名称:
			<input type="text" id="projNameText" style="width:130px" ></input>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
	
	<div id="modifyDiv"  style="padding:5px;width:420px;height:320px;">
	    <p>楼盘   ID:<label id="groupIdLabel"></label><input  id="groupIdInput" style="width:150px" readonly="trye"/></p>
	    &nbsp;&nbsp;&nbsp;&nbsp;
		<p>楼盘  名称:<label id="projNameLabel"></label><input  id="projNameInput" style="width:180px" readonly="trye" /></p>
		&nbsp;&nbsp;&nbsp;&nbsp;
	    <p>机器分词:<input id="manualWordsInput_ik" style="width:320px" readonly="true"/></p>
	    &nbsp;&nbsp;&nbsp;&nbsp;
		<p>人工 分词:<input id="manualWordsInput" style="width:320px"/></p>
		
		<br />
		<p><font size="2" color="blue">注：多个分词请用英文逗号连接  如： 万科,北京,金科</font></p>
	</div>
</body>
</html>