<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <style type="text/css">
        body {
            background-color: #f1f1f1;
        }
        h1 {
            text-align: center;
            color: #333;
            font-family: Arial, sans-serif;
        }
        form {
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.3);
            width: 300px;
            margin: 0 auto;
            padding: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-family: Arial, sans-serif;
            color: #333;
        }
        input[type="text"],
        input[type="password"] {
            padding: 8px;
            width: 100%;
            border-radius: 2px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-bottom: 20px;
            font-size: 16px;
        }
        input[type="submit"] {
            background-color: #333;
            color: #fff;
            border: none;
            border-radius: 2px;
            padding: 8px;
            font-size: 16px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #444;
        }
    </style>
    <script src="js/jquery-1.7.2.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#login").click(function () {
                $.ajax({
                    url: "login",
                    type: "post",
                    data: {"userName":$("#un").val(),"password":$("#pw").val()},
                    dataType: "json",
                    success: function (result) {
                        if(result!=null){
                            alert("登录成功！");
                            location.href="spider.jsp";
                        }else{
                            alert("用户名密码错误！");
                        }
                    }
                });
            })
        });
    </script>
</head>
<body>

<h1>Login Page</h1>
<form>
    <label for="un">Username:</label>
    <input type="text" id="un" name="username" placeholder="Enter your username">

    <label for="pw">Password:</label>
    <input type="password" id="pw" name="password" placeholder="Enter your password">

    <input id="login" type="button" value="登录">
</form>





</html>
