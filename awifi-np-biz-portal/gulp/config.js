/**
 * Created by zhuxh on 15-12-2.
 */

var webapp = '../src/main/webapp',

    static = '../static',

    js = {
        src: static + '/js',
        dest: webapp + '/js'
    },

    css = {
        src: static + '/css',
        dest: webapp + '/css'
    },

    html = {
        src: static + '/html',
        dest: webapp + '/html'
    },

    img = {
        src: static + '/images',
        dest: webapp + '/images'
    },

    comp = {
        src: static + '/component/src',
        dest: static + '/component/build'
    };

// css
css.login = [
    css.src + '/login/login.css',
    css.src + '/lib/poshytip/tip-yellow.css'
];

css.template_add = [
    css.src + '/template/template-add.css',
    css.src + '/lib/poshytip/tip-yellow.css',
    css.src + '/lib/jdialog.css',
    css.src + '/lib/select2/select2.min.css'
];

css.site_add = [
    css.src + '/lib/jdialog.css',
    css.src + '/site/site-add.css',
    css.src + '/lib/poshytip/tip-yellow.css',
    css.src + '/lib/select2/select2.min.css'
];

css.preview = [
    css.src + '/lib/jdialog.css',
    css.src + '/preview/preview.css',
    css.src + '/lib/poshytip/tip-yellow.css',
    css.src + '/lib/select2/select2.min.css'
];

css.index_lib = [
    css.src + '/lib/jquery-ui/jquery-ui.css',
    css.src + '/lib/poshytip/tip-yellow.css',
    css.src + '/lib/bootstrap/bootstrap.min.css',
    css.src + '/lib/datetimepicker/jquery.datetimepicker.css',
    css.src + '/lib/select2/select2.min.css'
];

css.index = [
    css.src + '/index/**/*.css',
    css.src + '/base/**/*.css'
];

css.portal_lib = [//portal预览
    css.src + '/tool/bootstrap.min.css',
    css.src + '/lib/portal.css'
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

// js
js.login = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/base/util/JTipsUtil.js',
    js.src + '/login/login.js'
];

js.template_add = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/base/util/JTipsUtil.js',
    js.src + '/lib/jdialog.js',
    js.src + '/lib/select2/select2.min.js',
    js.src + '/portal-comm.js',
    js.src + '/template/template-add.js'
];

js.site_add = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/lib/select2/select2.min.js',
    js.src + '/base/util/JTipsUtil.js',
    js.src + '/lib/jdialog.js',
    js.src + '/portal-comm.js',
    js.src + '/site/site-add.js'
];

js.template_preview= [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/lib/jdialog.js',
    js.src + '/template/template-preview.js'
];

js.site_preview = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/lib/jdialog.js',
    js.src + '/site/site-preview.js'
];

js.site_default_add = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/base/util/JTipsUtil.js',
    js.src + '/lib/jdialog.js',
    js.src + '/portal-comm.js',
    js.src + '/site/site-default-add.js'
];

js.hint = js.src + '/**/*.js';

js.lib = [
    js.src + '/lib/**/*.js'
];

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

js.index_lib = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    //js.src + '/lib/jquery/jquery.browser.js',
    js.src + '/lib/jquery/jquery.form.min.js',
    js.src + '/lib/jquery/jquery-ui.min.js',
    js.src + '/lib/jquery/jquery.poshytip.min.js',
    js.src + '/lib/jquery/jquery.datetimepicker.js',
    js.src + '/lib/angular/angular.min.js',
    js.src + '/lib/angular/angular-route.min.js',
    js.src + '/lib/angular/ng-file-upload.min.js',
    js.src + '/lib/bootstrap/ui-bootstrap-tpls-0.13.3.min.js',
    js.src + '/lib/select2/select2.min.js'
];

js.index = [
    js.src + '/index/index/index.js',
    js.src + '/base/**/*.js',
    js.src + '/index/**/*.js'
];

js.portal_lib = [//portal预览
     js.src + '/lib/jquery/jquery-1.11.3.min.js',
     js.src + '/lib/jquery/jquery.browser.js',
     js.src + '/lib/react/react.min.js',
     js.src + '/lib/react/react-dom.min.js'
 ];

// html
html.template = html.src + '/template/**/*.html';

html.error = html.src + '/error/**/*.html';

html.tool = html.src + '/tool/**/*.html';

var now = new Date();
var version = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate() + ' ' + now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
var release = now.getTime();
var banner = ['/**',
    ' @Copyright 爱WiFi TOE项目组',
    ' @version v' + version,
    ' */',
    '\n'].join('');

module.exports = {
    name: "awifi_portal",
    webapp: webapp,
    banner: banner,
    release: release,
    js: js,
    css: css,
    html: html,
    img: img,
    comp: comp
};