<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>爱WiFi行业客户Portal平台-站点预览</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/preview.min.css" />
</head>
<body>
<div class="main">
  <div class="previewBody">
    <div class="previewContent">
      <div class="content">
        <div class="top">
          <div class="voiceBtn"></div>
        </div>
        <div class="middle">
          <iframe id="iframeURL" src="" frameborder="0"></iframe>
        </div>
        <div class="bottom">
          <div class="homeBtn"></div>
        </div>
      </div>
      <div class="nextLink">
        <div class="upBtn" id="upBtn" style="display: none">上<br>一<br>页</div>
        <div class="downBtn" id="downBtn" style="display: none">下<br>一<br>页</div>
      </div>
    </div>
  </div>
</div>
<script src="<%=request.getContextPath()%>/js/site-preview.min.js"></script>
</body>
</html>