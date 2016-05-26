<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<%@ include file="/common/common.jsp"%>
<title>分词</title>	
<script type="text/javascript" src="/static/js/ptcheck.js"></script>
<link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css" /> 
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>

<script>
$(function(){
    //搜索按钮点击事件
    $("#searchBtnProj").click(searchBtnProjClick);
    $("#searchBtnNews").click(searchBtnNewsClick);
    $("#exportBtn").click(exportBtnClick);
});

function searchBtnClickProj() {
   var words =  $("#wordText").textbox('getText');

   if(words==null || words==""){
	   $.messager.alert('','请输入搜索query!','info');
	   return;
   }
   
	//为loadGrid()添加参数  
   var queryParams = $('#wordTab').datagrid('options').queryParams;  
   queryParams.words = words;
   queryParams.type =1;

    //查询后返回首页
   $('#wordTab').datagrid('options').pageNumber = 1;  
   var p = $('#wordTab').datagrid('getPager');
	p.pagination({pageNumber: 1}); 

   //重新加载datagrid的数据  
   $("#wordTab").datagrid('reload');  
   $("#wordText").val("");//清空  
}

function searchBtnClickNews() {
	   var words =  $("#wordText").textbox('getText');

	   if(words==null || words==""){
		   $.messager.alert('','请输入搜索query!','info');
		   return;
	   }
	   
		//为loadGrid()添加参数  
	   var queryParams = $('#wordTab').datagrid('options').queryParams;  
	   queryParams.words = words;
	   queryParams.type =2;

	    //查询后返回首页
	   $('#wordTab').datagrid('options').pageNumber = 1;  
	   var p = $('#wordTab').datagrid('getPager');
		p.pagination({pageNumber: 1}); 

	   //重新加载datagrid的数据  
	   $("#wordTab").datagrid('reload');  
	   $("#wordText").val("");//清空  
	}

function exportBtnClick(){
   var words =  $("#wordText").textbox('getText');

   if(words==null || words==""){
	   $.messager.alert('','请输入搜索query!','info');
	   return;
   }
	   
	var url= rootpath+"/admin/pm/export"
	$("#hiddenForm").attr("action",url); 
	$("#hiddenForm").submit();
}

</script>

</head>
<body>
    <br/>
    <h2>请输入搜索query,多个query以换行符标识</h2>
	<div style="margin:20px 0;"></div>
	<div>
	<input class="easyui-textbox" data-options="multiline:true" id="wordText" value="" style="width:500px;height:200px">
    <a href="javascript:void(0)" id="searchBtnProj" class="easyui-linkbutton" iconCls="icon-search">提交到楼盘集群</a>
    <a href="javascript:void(0)" id="searchBtnNews" class="easyui-linkbutton" iconCls="icon-search">提交到新闻集群</a>
    <!-- <form id="hiddenForm"  method="get" ><a href="javascript:void(0)" id="exportBtn" class="easyui-linkbutton" iconCls="icon-search">导出</a></form> -->
    
    </div>
    

		
	<table class="easyui-datagrid" id="wordTab" style="width:90%;height:600px"
			url="<%=basePath %>/admin/pm/check"
			title="分词结果" toolbar="#tb" fitColumns="true" pagination="true">
		<thead>
			<tr>
				<th field="word" width="50">单词</th>
				<th field="indexPplWord"  width="80" >索引分词</th>
				<th field="searchPplWord" width="80">搜索分词</th>
			</tr>
		</thead>
	</table>
	
</body>
</html>