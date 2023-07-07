<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>爬虫功能</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
        }

        .container {
            display: flex;
            flex-direction: row;
            height: 100vh;
        }

        .left {
            display: flex;
            flex-direction: column;
            background-color: #333;
            color: #fff;
            width: 200px;
            padding: 20px;
            box-sizing: border-box;
        }

        .left h1 {
            margin: 0;
            padding: 0;
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
        }

        .left button {
            background-color: #4CAF50;
            color: #fff;
            border: none;
            padding: 10px;
            margin-bottom: 10px;
            cursor: pointer;
            border-radius: 5px;
        }

        .left button:hover {
            background-color: #3e8e41;
        }

        .right {
            flex: 1;
            padding: 20px;
            box-sizing: border-box;
        }

        .right h1 {
            margin-top: 0;
            font-size: 24px;
            margin-bottom: 20px;
        }

        .right input[type="text"] {
            padding: 10px;
            border-radius: 5px;
            border: none;
            margin-bottom: 10px;
            width: 100%;
            box-sizing: border-box;
        }

        .right button {
            background-color: #4CAF50;
            color: #fff;
            border: none;
            padding: 10px;
            margin-bottom: 10px;
            cursor: pointer;
            border-radius: 5px;
        }

        .right button:hover {
            background-color: #3e8e41;
        }

        .right textarea {
            width: 100%;
            height: 800px;
            border-radius: 5px;
            border: none;
            padding: 10px;
            box-sizing: border-box;
            resize: none;
        }
    </style>
    <script src="js/jquery-1.7.2.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#spider").click(function () {
                $.ajax({
                    url: "getallurls",
                    type: "post",
                    data: {"url":$("#url").val()},
                    dataType: "json",
                    success: function(result) {
                        var links = result.spiderContent.split(',');

                        var contentText = "";
                        links.forEach(function(link) {
                            contentText += link + '\n';
                        });

                        $("#content").val(contentText);
                    }
                });
            })
        });
    </script>
</head>
<body>

<body>
<div class="container">
    <div class="left">
        <h1>Web安全检测</h1>
        <button onclick="window.location.href='spider.jsp'">爬虫功能</button>
        <button onclick="window.location.href='getallurls.jsp'">爬取页面全部链接</button>
        <button onclick="window.location.href='sqli.jsp'">SQL注入探测</button>
        <button onclick="window.location.href='xss.jsp'">XSS漏洞探测</button>
        <button onclick="window.location.href='index.jsp'">退出登录</button>
    </div>
    <div class="right">
        <input id="url" type="text" placeholder="请输入网址">
        <button id="spider">开始爬取页面全部链接</button>
        <textarea id="content"></textarea>
    </div>
</div>
</body>


</html>
