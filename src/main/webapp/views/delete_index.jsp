<%--
  Created by IntelliJ idEA.
  User: haotianliang
  Date: 27/03/2017
  Time: 11:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <%@ include file="/common/common.jsp" %>
    <title>删除</title>
    <link rel="stylesheet" type="text/css" href="http://developer.amap.com/Public/css/demo.Default.css"/>
    <script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=8089bac0001203d3175a245d7db66ef5"></script>
    <script>
        $(function () {
            $("#deleteByBaseIdDia").dialog({
                title: "基于楼盘id删除索引",
                closed: true,//初始时不显示
                buttons: [{
                    text: "确定",
                    iconCls: "icon-ok",
                    handler: useBaseId
                }, {
                    text: "取消",
                    iconCls: "icon-no",
                    handler: function () {
                        $("#deleteByBaseIdDia").dialog("close");
                    }
                }]
            });
            $("#deleteEndTimeStampDia").dialog({
                title: "删除指定时间之前的索引",
                closed: true,//初始时不显示
                buttons: [{
                    text: "确定",
                    iconCls: "icon-ok",
                    handler: useCityId
                }, {
                    text: "取消",
                    iconCls: "icon-no",
                    handler: function () {
                        $("#deleteEndTimeStampDia").dialog("close");
                    }
                }]
            });
        })

        $(function () {
            $("#deleteByBaseId").click(function () {
                $("#deleteByBaseIdDia").dialog("open");

            });
            $("#deleteByCityId").click(function () {
                $("#deleteEndTimeStampDia").dialog("open");
            });
        });

        function useBaseId() {
            var baseId = $("#baseIdInput").val();
            if (baseId == null || baseId == "") {
                $.messager.alert("错误", "未填写楼盘id！", "error");
                return;
            }
            var data = "BaseId=" + baseId;
            $.ajax({
                url: rootpath + "/deleteIndex/deleteIndexByBaseId",
                type: "post",
                data: data,
                dataType: "json",
                cache: false,
                success: function (response) {
                    $.messager.progress('close');
                    if (response.errorCode == 0) {
                        $('#deleteByBaseIdDia').dialog('close');
                        $.messager.alert('成功', '通过楼盘id删除条目成功!', 'info');
                    } else {
                        $('#deleteByBaseIdDia').dialog('close');
                        $.messager.alert('错误', '通过楼盘id删除条目失败!', 'error');
                    }
                },
                error: function (e) {
                    $('#deleteByBaseIdDia').dialog('close');
                    $.messager.alert('错误', '通过楼盘id删除条目失败!', 'error');
                }
            });
        }
        function useCityId() {
            var EndTimeStampInput = $("#endTimeStampInput").val();
            if (EndTimeStampInput == null || EndTimeStampInput == "") {
                $.messager.alert("错误", "请选择正确的时间戳！", "error");
                return;
            }
            var data = "CityId=" + CityId;
            $.ajax({
                url: rootpath + "/deleteIndex/deleteIndexByCityId",
                type: "post",
                data: data,
                dataType: "json",
                cache: false,
                success: function (response) {
                    $.messager.progress('close');
                    if (response.errorCode == 0) {
                        $('#deleteEndTimeStampDia').dialog('close');
                        $.messager.alert('成功', '通过城市id删除条目成功!', 'info');
                    } else {
                        $('#deleteEndTimeStampDia').dialog('close');
                        $.messager.alert('错误', '通过城市id删除条目失败!', 'error');
                    }
                },
                error: function (e) {
                    $('#deleteEndTimeStampDia').dialog('close');
                    $.messager.alert('错误', '通过城市id删除条目失败!', 'error');
                }
            });
        }
    </script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
    <div>
        <a href="javascript:void(0)" id="deleteByBaseId" class="easyui-linkbutton" iconCls="icon-edit">输入楼盘id</a>
        <a href="javascript:void(0)" id="deleteByCityId" class="easyui-linkbutton" iconCls="icon-edit">输入城市id</a>
    </div>
</div>

<div id="deleteByBaseIdDia" style="padding:5px;width:250px;height:165px;">
    <br/>
    <p>&nbsp;请输入楼盘id:&nbsp;<label id="baseIdLabel"></label><input id="baseIdInput" align="center" style="width:120px"/>
    </p>
    <br/>
</div>
<div id="deleteEndTimeStampDia" style="padding:5px;width:250px;height:165px;">
    <br/>
    <p>&nbsp;请输入日期:&nbsp;<label id="cityIdLabel"></label><input id="endTimeStampInput" type="text" align="center" class="easyui-datebox" required="required" style="width:120px"/>
    </p>
    <br/>
</div>