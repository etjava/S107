<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
      <form action="/upload2" enctype="multipart/form-data" method="post">
        <input type="file" name="file"/>
        <input type="submit" value="upload">
      </form>
    <br>
      <a href="/download">点击下载</a>
  </body>
</html>
