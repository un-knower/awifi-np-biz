<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Portal制作</title>
    js.tool_lib = [
    //js.src + '/lib/jquery/inheritance.js',
    js.src + '/lib/jquery/jquery-1.11.2.min.js',
    js.src + '/lib/jquery/jquery.form.min.js',
    //js.src + '/lib/jquery/jquery.json.min.js',
    js.src + '/lib/jquery/jquery-ui.min.js',
    js.src + '/lib/jquery/jquery.browser.js',
    //js.src + '/lib/jquery/jquery.tooltipster.min.js',
    js.src + '/lib/bootstrap/bootstrap-colorpicker.min.js',
    js.src + '/lib/html2canvas.min.js',
    js.src + '/lib/jdialog.js',
    js.src + '/lib/react/react.min.js',
    js.src + '/lib/react/react-dom.min.js'
    ];

    js.tool = [
    js.src + '/tool/portal-tool.js'
    ];

    css.tool_lib = [
    css.src + '/lib/bootstrap/bootstrap.min.css',
    css.src + '/lib/bootstrap/bootstrap-colorpicker.min.css',
    css.src + '/lib/jquery-ui/jquery-ui.css',
    css.src + '/lib/tooltipster.css',
    css.src + '/lib/jdialog.css'
    ];

    css.tool = [
    css.src + '/tool/bootstrap.min.css',
    css.src + '/tool/jquery-ui.min.css',
    css.src + '/tool/setting.css',
    css.src + '/tool/tool.less'
    ];

    <link rel="stylesheet" href="static/css/lib/bootstrap/bootstrap.min.css" />

   <link rel="stylesheet" href="<%=request.getContextPath()%>/css/tool-lib.min.css" />
   <link rel="stylesheet" href="<%=request.getContextPath()%>/css/tool.min.css" />
</head>
<body>
<header id="header">
   <div id="logo">
       <h2>Portal制作12wer39</h2>
   </div>
   <div id="head-rgt">
       <ul>
           <li id="go-back"><a href="javascript:history.back()" class="go-back">返回</a></li>
       </ul>
   </div>
</header>

<menu id="menu">
   <h2 class="title"><i class="icon-page"></i>页面管理</h2>
   <div id="page_wrap">
       <div id="page-type1" class="page">
           <div class="page-type"><a href="javascript:;" title="请点击右侧+号" style="cursor:default">引导页</a> <a href="javascript:;" class="page-add" data-page-type="1" title="新增引导页"></a></div>
           <%--<div class="page-item"><span><a href="javascript:;">引导第1页</a></span> <a href="javascript:;" class="page-del" title="删除当前引导页"></a></div>--%>
            <%--<div class="page-item"><span><a href="javascript:;">引导第2页</a></span> <a href="javascript:;" class="page-del" title="删除当前引导页"></a></div>--%>
        </div>
        <div id="page-type2" class="page">
            <div class="page-type"><a href="javascript:;" class="page-title" data-page-id="page2" data-page-type="2">认证页</a></div>
        </div>
        <div id="page-type3" class="page">
            <div class="page-type"><a href="javascript:;" title="请点击右侧+号" style="cursor:default">过渡页</a> <a href="javascript:;" class="page-add" data-page-type="3" title="新增过渡页"></a></div>
            <%--<div class="page-item"><span><a href="javascript:;">过渡第1页</a></span> <a href="javascript:;" class="page-del" title="删除当前过渡页"></a></div>--%>
            <%--<div class="page-item"><span><a href="javascript:;">过渡第2页</a></span> <a href="javascript:;" class="page-del" title="删除当前过渡页"></a></div>--%>
        </div>dsfd
        <div id="page-type4" class="page">
            <div class="page-type"><a href="javascript:;" class="page-title" data-page-id="page4" data-page-type="4">导航页</a></div>
        </div>
    </div>

    <h2 class="title" id="comp-title" style="display: none"><i class="icon-comp"></i>组件中心</h2>
    <p class="comp-tip" id="comp-tip" style="display: none">请选择所需组件，并拖动至相应位置</p>
    <ul id="comp-list" class="comp-list clfix">
        <%--<li data-cpt="aaa" data-type="1" data-one="1" title="图片轮播">--%>
        <%--<i style="background-image: url('/images/tool/comp-bg.png')"></i>--%>
        <%--<p>图片轮播</p>--%>
        <%--</li>--%>
        <%--<li data-cpt="bbb" data-type="2" data-one="0" title="友情链接">--%>
        <%--<i style="background-image: url('/images/tool/comp-bg.png')"></i>--%>
        <%--<p>友情链接</p>--%>
        <%--</li>--%>
        <%--<li data-cpt="_2K2ZqKtAe6MaJeH6k1eFiY6l" data-type="1" data-one="0" title="超链接">--%>
        <%--<i style="background-image: url('/images/tool/comp-bg.png')"></i>--%>
        <%--<p>超链接</p>--%>
        <%--</li>--%>
        <%--<li data-cpt="_gY0T4GhYxXaE2XpAoCl8SpS1" data-type="1" data-one="0" title="图片轮播">--%>
        <%--<i style="background-image: url('/images/tool/comp-bg.png')"></i>--%>
        <%--<p>纯文本</p>--%>
        <%--</li>--%>
    </ul>
</menu>

<div id="main" class="clfix">
    <div id="action">
        <div id="page-doing">请选择页面</div>
        <ul class="action-btn">
            <li><a href="javascript:;" id="btn-preview" class="btn-preview"><i></i>预览站点</a></li>
            <li><button type="button" class="btn-save">保存</button></li>
        </ul>
    </div>

    <div id="component-wrap">
        <div id="comp-main-wrap">
            <ul id="comp-main" class="entities"></ul>
            <div id="comp-preview-open">
                <a href="javascript:previewOpen();" class="preview-icon" title="打开预览"></a>
            </div>
        </div>

        <div id="comp-setting">
            <h2 class="title">组件内容管理</h2>
            <div id="comp-set"></div>
            <div id="entity-checkbox">
                <h3>组件内容权限控制</h3>
                <input type="checkbox" value="1" id="canChildEdit" name="canChildEdit" /> <label for="canChildEdit">强制下级单位使用该内容</label>
                <%--<input type="checkbox" value="1" id="canEdit" name="canEdit" /><label for="canEdit">是否可以修改该组件</label>--%>
                <p>* <span class="cred">选中</span>，下级单位在该组件内容的定制将不生效，而是显示当前定制的内容</p>
            </div>
        </div>

        <div id="comp-main-wrap-preview">
            <ul id="comp-main-preview"></ul>
            <div id="comp-preview-close">
                <a href="javascript:previewClose();" class="preview-icon" title="关闭预览"></a>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/tool-lib.min.js"></script>
<script src="<%=request.getContextPath()%>/js/tool.min.js"></script>

</body>
</html>