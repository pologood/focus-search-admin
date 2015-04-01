/*$(function() {
	 $("#IbtnEnter").click(searchBtnClick);
}
)

function searchBtnClick() {
	
	var name = $("#TxtUserName").val();
	var password = $("#TxtPassword").val();
	alert(name+"---"+password);
	
	$.ajax({
		   type: "POST",
		   url: "http://localhost:8080/search/admin/login",
		   data: "userName=admin&passWord=123",
		   dataType:'json',
		   success: function(msg){
		     alert( "Data Saved: " + msg );
		   }
	});
	
}*/