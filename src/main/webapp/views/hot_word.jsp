<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<%@ include file="/common/common.jsp"%>
<title>添加热词</title>	
<link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css" /> 
<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>

<script language="javascript">
//动态绘制数据表格的代码
$(function(){
	$('#projTab').datagrid({
        idField:'id',  
        url:'<%=basePath %>/hot/loadHot',  
        singleSelect:true,  
        columns:[[  
          {field:'id',title:'id',width:80,align:'center'},
          {field:'name',title:'名称',width:120,align:'center'},
          {field:'type',title:'类型',width:80,align:'center'},
          {field:'status',title:'状态',width:80,align:'center'},
          {field:'editor',title:'编辑者',width:80,align:'center'},
          {field:'createTime',title:'创建时间',width:160,align:'center'},
          {field:'updateTime',title:'最后更新时间',width:160,align:'center'},
          {field:'opt',title:'操作',width:100,align:'center',  
            formatter:function(value,rec,index){
                var d = '<a href="javascript:void(0)" mce_href="#" onclick="del(\''+ index +'\')">删除</a> ';  
                return d;  
            }  
          }  
        ]],  
       
       pagination:true  
    });  
})

function del(index){  //删除操作  
    $.messager.confirm('确认','确认删除?',function(row){  
        if(row){  
            var selectedRow = $('#projTab').datagrid('getSelected');  //获取选中行
            var aid = selectedRow.id;
      		var name = selectedRow.name;
      		var data = "id="+aid+"&name="+name;
      		//$.messager.alert(data);
            $.ajax({  
                url:rootpath+"/hot/delHot",
                type:"post",
  				data:data,
  				dataType:"json",
  				cache:false,
                success:function(response)
                {
                	if(response.errorCode == 1){
                		 $.messager.alert('错误','删除失败!','error'); 
                		 $("#projTab").datagrid('reload'); 						
  					}
  					else{
						$('#projTab').datagrid('deleteRow',index);
  						$.messager.alert('成功','删除成功!','info');	
  						$("#projTab").datagrid('reload');
  					}
                },
                error:function(e){
  					$.messager.alert('错误','删除失败3!','error');
  				}
            });
        }  
    }) 
    $("#projTab").datagrid('reload');
  } 

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
		
  	$(document).delegate("#a"+index,"click",function(){
  		var aid = row.id;
  		var name = row.name;
  		var data = "id="+aid+"&name="+name;
  		//$.messager.alert(data);
  		$.messager.alert(data);
  		var isDel = confirm("确定删除请选择确定，否则取消！");
  		if (isDel == true)
  		{
  			$.ajax({
  	  			
  				url:rootpath+"/hot/delHot?time="+Math.random(),
  				type:"post",
  				data:data,
  				dataType:"json",
  				cache:false,
  				success:function(response){								
  					if(response.errorCode == 0){
  						$.messager.alert('成功','删除成功!','info');
  						$("#projTab").datagrid('reload');
  						$('#projTab').datagrid('clearSelections');
  					}
  					else{
  						 $.messager.alert('错误','删除失败!','error');
  						$("#projTab").datagrid('reload');
  						$('#projTab').datagrid('clearSelections');
  					}
  				},
  				error:function(e){
  					$.messager.alert('错误','删除失败3!','error');
  				}
  		});
  			isDel.close();
  			$("#projTab").datagrid('reload');
			$('#projTab').datagrid('clearSelections'); 
  		}
  		else
  		{
  			isDel.close();
  		}  		
  	});
	return a;
}

function addHotWords(){
	var type = $("#typeInput").val();
	var stopWords = $("#hotWordsInput").val();
	
	stopWords = stopWords.replace("+", "%2B");
	
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
					$.messager.alert('成功',response.errorMsg,'info');
					//$.messager.alert('成功','添加成功!','info');
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

//exportHotDic
/* $(function(){
	$("#exportHotDic").click(function(){
		//alert("功能暂未开放！");
		var isExport = confirm("确认导出热词?");
		if (isExport == true){
			$.ajax({
				url:rootpath+"/hot/exportHot",
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
}); */

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
/* 	var name = "${user.userName}";
	if(name != "admin1")
	{
		$("#exportHotDic").hide();
	} */
};
</script>

	 <table id="projTab" class="easyui-datagrid" style="width:100%;height:497px"
	 title="添加热词" toolbar="#tb"fitColumns="true" pagination="true">
	 </table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="addBtn">添加热词</a>
			<a href="javascript:void(0)" id="updateHotDic" class="easyui-linkbutton" iconCls="icon-add">更新词库</a>
			<a href="/hot/exportHot" id="exportHotDic" class="easyui-linkbutton" iconCls="icon-export">导出楼盘类热词词库</a>
			<a href="/hot/exportHot?type=2" id="exportHotDic" class="easyui-linkbutton" iconCls="icon-export">导出新闻类热词词库</a>
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
		<p><font size="2" color="blue">注:多个热词请用英文逗号空格或中文逗号空格连接  如:万科,北京,金科</font></p>
	</div>
</body>
</html>