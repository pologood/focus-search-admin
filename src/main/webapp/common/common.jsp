<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+ ((request.getServerPort()==80)?"":":"+request.getServerPort()) +path;
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!-- jQuery库 -->
<link rel="stylesheet" type="text/css" href="/static/css/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="/static/css/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/static/css/themes/icon.css" />
<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/static/js/locale/easyui-lang-zh_CN.js"></script>
<link rel="icon" href="http://src.focus.cn/favicon.ico" type="image/x-icon" />
<script type="text/javascript">
var rootpath ="<%=path%>";
</script>
