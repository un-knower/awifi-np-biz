<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>站点管理_爱WiFi行业客户Portal平台</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/site-add.min.css" />
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
    <h1 class="s-title">请填写客户及站点信息</h1>
    <ul class="f-list">
        <li>
            <label class="f-label"><span class="c-red">*</span>客户名称</label>
            <div class="f-node">
                <select name="customer_name" id="customer_name" class="f-sel-b">
                    <option value="">选择客户</option>
                </select>
            </div>
        </li>
        <li>
            <label class="f-label"><span class="c-red">*</span>站点名称</label>
            <div class="f-node">
                <input type="text" class="f-ipt-b" id="template_name" name="template_name" value="" placeholder="请输入站点名称" maxlength="50" />
            </div>
        </li>
    </ul>

    <h1 class="s-title">请选择模板</h1>

    <ul class="f-list">
        <li id="search-keywords">
            <label class="f-label">模板名称</label>
            <div class="f-node">
                <input type="text" class="f-ipt-b" id="keywords" name="keywords" value="" placeholder="请输入模板名称关键字" maxlength="50" />
                <input type="button" class="f-btn-search" value="查询" id="" name="" />
            </div>
        </li>
        <li>
            <input type="hidden" id="list_page" value="1" />
            <input type="hidden" id="select-tid" value="" />
            <div id="template-list" class="template-list">
                <div class="template-item-no">请选择客户</div>
                <!--div class="template-item">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div>
                <div class="template-item template-checked">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div>
                <div class="template-item">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div>
                <div class="template-item">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div>
                <div class="template-item">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div>
                <div class="template-item">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div>
                <div class="template-item">
                    <img src="<%=request.getContextPath()%>/images/sitepic.png" alt="" />
                    <p>模板名称</p>
                    <i class="icon-checkbox"></i>
                </div-->
            </div>
            <div class="pager" id="pager">
                <a href="javascript:;" id="pager-prev">上一页</a>
                <a href="javascript:;" id="pager-next">下一页</a>
            </div>
        </li>
        <li>
            <div class="f-btn-wp">
                <input type="button" class="f-btn-sure" value="保存并继续" />
                <input type="button" class="f-btn-cancel" value="取消" />
                <span id="result_tips"></span>
            </div>
        </li>
    </ul>
</section>
<script src="<%=request.getContextPath()%>/js/site-add.min.js"></script>
</body>
</html>