
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>用户登录</TITLE><LINK 
href="/static/images/login/Default.css" type=text/css rel=stylesheet><LINK 
href="/static/images/login/xtree.css" type=text/css rel=stylesheet><LINK 
href="/static/images/login/User_Login.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="/static/js/login.js"></script>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="MSHTML 6.00.6000.16674" name=GENERATOR>

<script>

</script>
</HEAD>


<BODY id=userlogin_body>
<DIV></DIV>
 <form name='frm1' action="<%=basePath %>/search/admin/login" method="post" >
<DIV id=user_login>
<DL>
  <DD id=user_top>
  <UL>
    <LI class=user_top_l></LI>
    <LI class=user_top_c></LI>
    <LI class=user_top_r></LI></UL>
  <DD id=user_main>
  <UL>
    <LI class=user_main_l></LI>
    <LI class=user_main_c>
    <DIV class=user_main_box>
    
   
    <UL>
      <LI class=user_main_text>用户名： </LI>
      <LI class=user_main_input><INPUT class=TxtUserNameCssClass id=TxtUserName 
      maxLength=20 name=userName> </LI></UL>
    <UL>
      <LI class=user_main_text>密 码： </LI>
      <LI class=user_main_input><INPUT class=TxtPasswordCssClass id=TxtPassword 
      type=password name=password> </LI></UL>
    <UL>
      <LI class=user_main_text> </LI>
      <LI class=user_main_input style="color:red">${flag}</LI></UL>
    </DIV></LI>
    
  <LI class=user_main_r><INPUT class=IbtnEnterCssClass id=IbtnEnter 
    style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px" 
    onclick="javascript:void(0)"
    type=image src="/static/images/login/user_botton.gif" name=IbtnEnter> </LI></UL>

  <DD id=user_bottom>
  <UL>
    <LI class=user_bottom_l></LI>
    <li class="user_bottom_c"></li>

    <LI class=user_bottom_r></LI></UL></DD></DL></DIV><SPAN id=ValrUserName 
style="DISPLAY: none; COLOR: red"></SPAN><SPAN id=ValrPassword 
style="DISPLAY: none; COLOR: red"></SPAN><SPAN id=ValrValidateCode 
style="DISPLAY: none; COLOR: red"></SPAN>
<DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></DIV>
<DIV></DIV>

</FORM></BODY></HTML>
