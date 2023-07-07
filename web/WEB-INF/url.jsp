<%--
  Created by IntelliJ IDEA.
  User: cod01
  Date: 2022/6/6
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
         import="com.dhee.entity.UserEntity,com.dhee.entity.URLEntity"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>url管理 ' starting page</title>

    <link rel="stylesheet" type="text/css" href="cssable.css">
    <link rel="stylesheet" type="text/css" href="cssutton.css">

    <script type="text/javascript">
        function del(id){
            if(confirm("确定删除吗？")){
                location.href="delUrl?urlId="+id;
            }
        }

        function output(){
            location.href="getFile";
        }

    </script>
</head>

<h2>web漏洞扫描</h2>
<%
    if (session.getAttribute("userInfo") != null) {
        UserEntity user = (UserEntity) session.getAttribute("userInfo");
%>
<h2>
    欢迎<%=user.getName()%>登录！
</h2>
<%
    }
%>

</body>
</html>

