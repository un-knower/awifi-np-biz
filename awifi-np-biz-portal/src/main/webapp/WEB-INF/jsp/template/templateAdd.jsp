<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>模板管理_爱WiFi行业客户Portal平台</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/template-add.min.css" />
</head>
<body>
<header class="header">
    <div class="header-left-bg"></div>
    <div class="header-left-text">爱WiFi行业客户Portal平台</div>
    <div class="header-right">
        <div class="header-icon-border">
            <img src="/images/akamaru.jpg"/>
        </div>
        <div class="header-user"></div>
        <div class="header-profile">
            <ul>
                <li><a class="icon-power" href="/logout">退出系统</a></li>
            </ul>
        </div>
    </div>
</header>
<section class="s-wrap">
    <h1 class="s-title">请填写模板信息</h1>
    <form action="" id="this-form" name=""></form>
    <ul class="f-list controls">
        <li>
            <label class="f-label"><span class="c-red">*</span>模板名称</label>
            <div class="f-node">
                <input type="text" class="f-ipt-b" id="template_name" name="templateName" value="" placeholder="请输入模板名称" maxlength="50" />
            </div>
        </li>
        <li>
            <label class="f-label"><span class="c-red">*</span>选择行业</label>
            <div class="f-node">
                <select name="primaryIndustry" id="primary_industry" class="f-sel-b">
                    <option value="">一级行业</option>
                </select>
                <select name="secondaryIndustry" id="secondary_industry" class="f-sel-b">
                    <option value="">二级行业</option>
                </select>
            </div>
        </li>
        <li>
            <label class="f-label"><span class="c-red">*</span>项目名称</label>
            <div class="f-node">
                <select name="fkProjectId" id="fk_project_id" class="f-sel-b">
                    <%--<option value="0">默认</option>--%>
                </select>
                <span style="color:#999;padding-left:15px;">默认表表示项目可使用任意模板</span>
            </div>
        </li>
        <li>
            <label class="f-label">备注</label>
            <div class="f-node">
                <textarea class="f-txt-b" name="remark" id="remark" cols="30" rows="10" placeholder="请输入备注信息" maxlength="100"></textarea>
            </div>
        </li>
        <li>
            <div class="f-btn-wp">
                <input type="button" class="f-btn-sure" value="保存并继续" disabled />
                <input type="button" class="f-btn-cancel" value="取消" />
                <span id="result_tips"></span>
            </div>
        </li>
    </ul>
</section>
<script src="<%=request.getContextPath()%>/js/template-add.min.js"></script>
</body>
</html>