//$(function(){
//    //搜索按钮点击事件
//    $("#searchBtn").click(searchBtnClick);
//});
//
//function searchBtnClick() {
//   var words =  $("#wordText").textbox('getText');
//   alert(words);
//   if(words==null || words==""){
//	   alert(" 请输入搜索query");
//	   return;
//   }
//   
//	//为loadGrid()添加参数  
//   var queryParams = $('#wordTab').datagrid('options').queryParams;  
//   queryParams.words = words;
//
//    //查询后返回首页
//   $('#wordTab').datagrid('options').pageNumber = 1;  
//   var p = $('#wordTab').datagrid('getPager');
//	p.pagination({pageNumber: 1}); 
//
//   //重新加载datagrid的数据  
//   $("#wordTab").datagrid('reload');  
//   $("#wordText").val("");//清空  
//}