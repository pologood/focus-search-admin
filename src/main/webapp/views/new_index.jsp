<%--
  Created by IntelliJ IDEA.
  User: haotianliang
  Date: 24/03/2017
  Time: 2:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <%@ include file="/common/common.jsp"%>
    <title>更新索引</title>
    <link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css"/>
    <script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>
    <script>
        $(function(){
            $("#updateByBaseIdDia").dialog({
                title:"基于楼盘ID更新索引",
                closed:true,//初始时不显示
                buttons:[{
                    text:"确定",
                    iconCls:"icon-ok",
                    handler:useBaseId
                },{
                    text:"取消",
                    iconCls:"icon-no",
                    handler:function(){
                        $("#updateByBaseIdDia").dialog("close");
                    }
                }]
            });
            $("#updateByCityIdDia").dialog({
                title:"基于城市ID更新索引",
                closed:true,//初始时不显示
                buttons:[{
                    text:"确定",
                    iconCls:"icon-ok",
                    handler:useCityId
                },{
                    text:"取消",
                    iconCls:"icon-no",
                    handler:function(){
                        $("#updateByCityIdDia").dialog("close");
                    }
                }]
            });
            $("#updateAllDia").dialog({
                title:"全部更新更新索引",
                closed:true,//初始时不显示
                buttons:[{
                    text:"确定",
                    iconCls:"icon-ok",
                    handler:useAll
                },{
                    text:"取消",
                    iconCls:"icon-no",
                    handler:function(){
                        $("#updateAllDia").dialog("close");
                    }
                }]
            });
        })

        $(function () {
            $("#updateByBaseId").click(function(){
                $("#updateByBaseIdDia").dialog("open");

            });
            $("#updateByCityId").click(function(){
                $("#updateByCityIdDia").dialog("open");
            });
            $("#updateByAll").click(function(){
                $("#updateAllDia").dialog("open");
            });
        });

        function useBaseId(){
            var baseId = $("#baseIdInput").val();
            if(baseId == null || baseId == ""){
                $.messager.alert("错误","未填写楼盘ID！","error");
                return;
            }
            var data = "baseId=" + baseId;
            $.ajax({
                url:rootpath+"/newIndex/updateIndexByBaseId",
                type:"post",
                data:data,
                dataType:"json",
                cache:false,
                success:function(response){
                    $.messager.progress('close');
                    if(response.errorCode == 0){
                        $('#updateByBaseIdDia').dialog('close');
                        $.messager.alert('成功','通过楼盘ID更新条目成功!','info');
                    }else{
                        $('#updateByBaseIdDia').dialog('close');
                        $.messager.alert('错误','通过楼盘ID更新条目失败!','error');
                    }
                },
                error:function(e){
                    $('#updateByBaseIdDia').dialog('close');
                    $.messager.alert('错误','通过楼盘ID更新条目失败!','error');
                }
            });
        }
        function useCityId() {
            var CityId = $("#cityIdInput").val();
            if(CityId == null || CityId == ""){
                $.messager.alert("错误","未填写城市ID！","error");
                return;
            }
            var data = "CityId=" + CityId;
            $.ajax({
                url:rootpath+"/newIndex/updateIndexByCityId",
                type:"post",
                data:data,
                dataType:"json",
                cache:false,
                success:function(response){
                    $.messager.progress('close');
                    if(response.errorCode == 0){
                        $('#updateByCityIdDia').dialog('close');
                        $.messager.alert('成功','通过城市ID更新条目成功!','info');
                    }else{
                        $('#updateByCityIdDia').dialog('close');
                        $.messager.alert('错误','通过城市ID更新条目失败!','error');
                    }
                },
                error:function(e){
                    $('#updateByCityIdDia').dialog('close');
                    $.messager.alert('错误','通过城市ID更新条目失败!','error');
                }
            });
        }
        function useAll() {
            var data = "";
            $.ajax({
                url:rootpath+"/newIndex/updateAll",
                type:"post",
                dataType: "json",
                data: data,
                cache:false,
                success:function(response){
                    $.messager.progress('close');
                    if(response.errorCode == 0){
                        $('#updateAllDia').dialog('close');
                        $.messager.alert('成功','全部索引更新成功!','info');
                    }else{
                        $('#updateAllDia').dialog('close');
                        $.messager.alert('错误','全部索引更新失败!','error');
                    }
                },
                error:function(e){
                    $('#updateAllDia').dialog('close');
                    $.messager.alert('错误','全部索引更新失败!','error');
                }
            });
        }
    </script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
    <div>
        <a href="javascript:void(0)" id="updateByBaseId" class="easyui-linkbutton" iconCls="icon-edit">输入楼盘id</a>
        <a href="javascript:void(0)" id="updateByCityId" class="easyui-linkbutton" iconCls="icon-edit">输入城市id</a>
        <a href="javascript:void(0)" id="updateByAll" class="easyui-linkbutton" iconCls="icon-reload">全量更新</a>
    </div>
</div>

<div id="updateByBaseIdDia" style="padding:5px;width:250px;height:165px;">
    <br/>
    <p>&nbsp;请输入楼盘id:&nbsp;<label id="baseIdLabel"></label><input id="baseIdInput" align="center" style="width:120px"/>
    </p>
    <br/>
</div>
<div id="updateByCityIdDia" style="padding:5px;width:250px;height:165px;">
    <br/>
    <p>&nbsp;请输入楼盘id:&nbsp;<label id="cityIdLabel"></label><input id="cityIdInput" align="center" style="width:120px"/>
    </p>
    <br/>
</div>
<div id="updateAllDia" style="padding:5px;width:250px;height:165px;">
    <br/>
    <p align="center">是否更新全部索引</p>
    <br/>
</div>
</body>
</html>
