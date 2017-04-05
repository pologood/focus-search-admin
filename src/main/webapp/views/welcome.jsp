<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎页面</title>
</head>
<body>
<div id="p" class="easyui-panel" title="Basic Panel" style="width:700px;height:200px;padding:10px;">
        <ul>
            <li>技术支持</li>
            <li>梁皓天</li>
            <li>email:haotianliang@sohu-inc.com</li>
            <br/>
            <br/>
            <li>补充说明：</li>
            <li>1. 热词status值为0时，分词效果为不分词，若status值为1时，正常分词。</li>
            <li>2. 只能导出status值为0的热词，导出之后，status值置为1。导出词典之后，需要手动将词典上传到服务器ik分词器目录下面，以保证分词效果。</li>
            <li>3. 更新索引和删除索引都是对elasticsearch上面的索引进行更新或者删除操作。</li>
        </ul>
    </div>
</body>
</html>