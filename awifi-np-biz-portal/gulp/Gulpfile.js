/**
 * Created by zhuxh on 15-12-2.
 */

'use strict';

/*
gulp.task(name, fn) - 定义任务，第一个参数是任务名，第二个参数是任务内容
gulp.src(path) - 选择文件，传入参数是文件路径
gulp.dest(path) - 输出文件
gulp.pipe() - 管道，你可以暂时将 pipe 理解为将操作加入执行队列

var uglify = require("gulp-uglify");  // js混淆压缩模块
var minify_css = require("gulp-minify-css");  // css混淆压缩模块
var minify_html = require("gulp-minify-html");  // html混淆压缩模块
var gulp_concat = require('gulp-concat');  // 文件合并模块
var rename = require('gulp-rename');  // 文件重命名模板

====
插件
====

----
# gulp-minify-html
----
 Options

 All options are false by default.

 empty - do not remove empty attributes
 cdata - do not strip CDATA from scripts
 comments - do not remove comments
 conditionals - do not remove conditional internet explorer comments
 spare - do not remove redundant attributes
 quotes - do not remove arbitrary quotes
 loose - preserve one whitespace

var minifyHTML = require('gulp-minify-html');
gulp.task('minify-html', function() {
    var opts = {
        conditionals: true,
        spare:true
    };
    return gulp.src('./static/html/*.html')
        .pipe(minifyHTML(opts))
        .pipe(gulp.dest('./dist/'));
});

----
# gulp-minify-css
----

var gulp = require('gulp');
var minifyCss = require('gulp-clean-css');
gulp.task('minify-css', function() {
    return gulp.src('styles/*.css')
        .pipe(cleanCss({compatibility: 'ie8'}))
        .pipe(gulp.dest('dist'));
});

*/

var gulp = require('gulp'),
    plug = require('gulp-load-plugins')(),
    config = require('./config'),
    babel = require("gulp-babel"),
    es2015 = require("babel-preset-es2015-without-strict"),
    removeUseStrict = require("gulp-remove-use-strict");
// js文件压缩配置
var uglify_option = {
    mangle: false, // 类型：Boolean 默认：true 是否修改变量名
    compress: false //类型：Boolean 默认：true 是否完全压缩
};

var minifyHtml_option = {
    empty: true,  // KEEP empty attributes
    cdata: false,  // KEEP CDATA from scripts
    comments: false,  // KEEP comments
    ssi: false,  // KEEP Server Side Includes
    conditionals: false,  // KEEP conditional internet explorer comments
    spare: true,  // KEEP redundant attributes
    quotes: true,  // KEEP arbitrary quotes
    loose: false  // KEEP one whitespace
};

// css文件压缩比输出
var cleanCssDetails = function(details) {
    var r = ((details.stats.originalSize - details.stats.minifiedSize) / details.stats.originalSize) * 100;
    console.log(details.name + ': 压缩前 ' + details.stats.originalSize + ' / 压缩后 ' + details.stats.minifiedSize + ' / 压缩比率 ' + r.toFixed(2) + '%');
};

gulp.task('default', function () {
    console.log('toe_admin default');
    console.log(config);
});

// 验证js文件是否符合jshint
gulp.task('jshint', function () {
    gulp.src(config.js.hint)
    // asi => 值为false时，如果代码末尾省略了分号，则JSHint会给出警告；值为true时，则不会
    // eqnull => 值为false时，如果代码中使用"=="来比较变量与null，则JSHint会给出警告；值为true时，则不会
    // sub => 值为true时，允许用obj['name']和obj.name两种方式访问对象的属性；值为false时，不允许使用obj['name']方式，除非只能使用这种方式访问
        .pipe(plug.jshint({asi: true, eqnull: true, sub: true})) // 检查js
        .pipe(plug.jshint.reporter()); // 输出检查结果信息
});

// login.js 文件压缩
gulp.task('js_login', function () {
    gulp.src(config.js.login)
        .pipe(plug.concat('login.js')) // 多文件合并成1个文件
        //.pipe(plug.jshint())
        //.pipe(plug.jshint.reporter())
        .pipe(plug.uglify(uglify_option))
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// login.css 文件压缩
gulp.task('css_login', function () {
    gulp.src(config.css.login)
        .pipe(plug.concat('login.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({suffix: '.min'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// template-add.js 文件压缩
gulp.task('js_template_add', function () {
    gulp.src(config.js.template_add)
        .pipe(plug.concat('template-add.js')) // 多文件合并成1个文件
        //.pipe(plug.jshint())
        //.pipe(plug.jshint.reporter())
        .pipe(plug.uglify(uglify_option))
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// template-add.css 文件压缩
gulp.task('css_template_add', function () {
    gulp.src(config.css.template_add)
        .pipe(plug.concat('template-add.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({suffix: '.min'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// site-add.js 文件压缩
gulp.task('js_site_add', function () {
    gulp.src(config.js.site_add)
        .pipe(plug.concat('site-add.js')) // 多文件合并成1个文件
        //.pipe(plug.jshint())
        //.pipe(plug.jshint.reporter())
        .pipe(plug.uglify(uglify_option))
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// site-default-add.js 文件压缩
gulp.task('js_site_default_add', function () {
    gulp.src(config.js.site_default_add)
        .pipe(plug.concat('site-default-add.js')) // 多文件合并成1个文件
        //.pipe(plug.jshint())
        //.pipe(plug.jshint.reporter())
        .pipe(plug.uglify(uglify_option))
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// site-add.css 文件压缩
gulp.task('css_site_add', function () {
    gulp.src(config.css.site_add)
        .pipe(plug.concat('site-add.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({suffix: '.min'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});
/**
 * css_preview css 文件压缩
 * */
gulp.task('css_preview', function(){
    gulp.src(config.css.preview)
        .pipe(plug.concat('preview.css'))
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails))
        .pipe(plug.rename({suffix: '.min'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest));
});

/**
 * js_template_preview js文件压缩
 * */
gulp.task('js_template_preview', function(){
    gulp.src(config.js.template_preview)
        .pipe(plug.concat('template-preview.js'))
        //.pipe(plug.jshint())
        //.pipe(plug.jshint.reporter())
        .pipe(plug.uglify(uglify_option))
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest));
});
/**
 * js_site_preview js文件压缩
 * */
gulp.task('js_site_preview', function(){
    gulp.src(config.js.site_preview)
        .pipe(plug.concat('site-preview.js')) // 多文件合并成1个文件
        //.pipe(plug.jshint())
        //.pipe(plug.jshint.reporter())
        .pipe(plug.uglify(uglify_option))
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});


// js/index 文件压缩
gulp.task('js_index', function () {
   gulp.src(config.js.index)
       .pipe(plug.concat(config.name + '.js')) // 多文件合并成1个文件
       .pipe(plug.uglify(uglify_option))// 文件压缩:
       .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
       .pipe(plug.header(config.banner))
       .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// js/lib 文件压缩
gulp.task('js_index_lib', function () {
    gulp.src(config.js.index_lib)
        .pipe(plug.concat(config.name + '_lib.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩:
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

//js/lib 文件压缩(预览功能)
gulp.task('js_portal_lib', function () {
	//console.log('js_portal_lib= ' + js_portal_lib);
    gulp.src(config.js.portal_lib)
        .pipe(plug.concat('portal_lib.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩:
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// css/index 文件压缩
gulp.task('css_index', function () {
    gulp.src(config.css.index)
        .pipe(plug.concat(config.name + '.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// css/index-lib
gulp.task('css_index_lib', function () {
    gulp.src(config.css.index_lib)
        .pipe(plug.concat(config.name + '_lib.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

//css/index-lib(预览)
gulp.task('css_portal_lib', function () {
    gulp.src(config.css.portal_lib)
        .pipe(plug.concat('portal_lib.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// css/tool-lib
gulp.task('css_tool_lib', function () {
    gulp.src(config.css.tool_lib)
        .pipe(plug.concat('tool-lib.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// css/tool 文件压缩
gulp.task('css_tool', function () {
    gulp.src(config.css.tool)
        .pipe(plug.less())
        .pipe(plug.concat('tool.css')) // 多文件合并成1个文件
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails)) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// js/tool 文件压缩
gulp.task('js_tool', function () {
    gulp.src(config.js.tool)
        .pipe(plug.concat('tool.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// js/tool-lib 文件压缩
gulp.task('js_tool_lib', function () {
    gulp.src(config.js.tool_lib)
        .pipe(plug.concat('tool-lib.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(plug.header(config.banner))
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// index.html 文件压缩
gulp.task('html_index', function () {
    gulp.src(config.html.src + '/index.html')
        .pipe(plug.minifyHtml(minifyHtml_option))
        .pipe(plug.replace("?v=@release", "?v="+config.release))
        .pipe(gulp.dest(config.webapp))
});

// html/template 文件压缩
gulp.task('html_template', function () {
    gulp.src(config.html.template)
        .pipe(plug.minifyHtml(minifyHtml_option))
        .pipe(gulp.dest(config.html.dest + '/template'))
});

// html/error
gulp.task('html_error', function () {
    gulp.src(config.html.error)
        .pipe(plug.minifyHtml(minifyHtml_option))
        //.pipe(plug.cleanCss({debug: true}, cleanCssDetails))
        .pipe(gulp.dest(config.html.dest + '/error'))
});

// html/tool
gulp.task('html_tool', function () {
    gulp.src(config.html.tool)
        .pipe(plug.minifyHtml(minifyHtml_option))
        // 删除js注释
        .pipe(plug.replace(/\/\*[\s\S]*?\*\//g, ''))
        // 删除换行符
        .pipe(plug.replace(/\n|\r|(\r\n)|(\u0085)|(\u2028)|(\u2029)/g, ''))
        //.pipe(plug.cleanCss({debug: true}, cleanCssDetails))
        .pipe(gulp.dest(config.html.dest + '/tool'))
});

// 删除css
//gulp.task('clean_css', function () {
//   return gulp.src(config.css.dest + '/*.css')
//       .pipe(plug.clean({force: true}));
//});

// 删除js
//gulp.task('clean_js', function () {
//    return gulp.src(config.js.dest + '/*.js')
//        .pipe(plug.clean({force: true}));
//});

// 删除html
//gulp.task('clean_html', function () {
//    return gulp.src([html_dest, webapp + '/index.html'])
//        .pipe(plug.clean({force: true}));
//});

// 删除清空component/build目录 (解决：删除组件中包含test目录不为空时报错)
gulp.task('clean_comp_build', function () {
    return gulp.src(config.comp.dest)
        .pipe(plug.clean({force: true}));
});

// 组件复制
gulp.task('comp_copy', function () {
    return gulp.src([config.comp.src + '/**/*', '!'+config.comp.src+'/**/test', '!'+config.comp.src+'/**/test/*.*'])
        .pipe(gulp.dest(config.comp.dest))
});

// 组件react jsx
gulp.task('comp_react_jsx', function() {
    return gulp.src(config.comp.src + '/**/*.js')
        //.pipe(plug.jshint({asi: true, eqnull: true, sub: true})) // 检查js
        //.pipe(plug.jshint.reporter()) // 输出检查结果信息
        .pipe(plug.react())
        .pipe(babel({presets:[es2015]}))
        .pipe(removeUseStrict())
        .pipe(plug.uglify(uglify_option))
        .pipe(gulp.dest(config.comp.dest));
});

// 组件css压缩
gulp.task('comp_css_min', function() {
    return gulp.src(config.comp.src + '/**/*.css')
        .pipe(plug.cleanCss({debug: true}, cleanCssDetails))
        .pipe(gulp.dest(config.comp.dest));
});

// 图片复制
gulp.task('img_copy', function () {
    return gulp.src(config.img.src + '/**/*')
        .pipe(gulp.dest(config.img.dest))
});

// login 任务
gulp.task('login', ['css_login', 'js_login']);
// template_add
gulp.task('template_add', ['css_template_add', 'js_template_add']);
// site_add
gulp.task('site_add', ['css_site_add', 'js_site_add', 'js_site_default_add']);

/**
 * template_preview   site_preview
 */
gulp.task('template_preview', ['css_preview', 'js_template_preview']);
gulp.task('site_preview', ['css_preview', 'js_site_preview']);

// index 任务
gulp.task('index', ['css_index', 'js_index']);
// html 任务
gulp.task('html', ['html_index', 'html_template']);
// clean 任务
//gulp.task('clean', ['clean_css', 'clean_js', 'clean_html']);
// portal tool 任务
gulp.task('tool', ['css_tool_lib', 'css_tool', 'js_tool_lib', 'js_tool']);

//portal 预览
gulp.task('portal_preview', ['css_portal_lib', 'js_portal_lib']);

// build 任务
gulp.task('build', ['build_init', 'login', 'template_add', 'site_add', 'template_preview', 'site_preview', 'portal_preview', 'tool', 'index', 'html']);

// build init
gulp.task('build_init', ['img_copy', 'html_error', 'html_tool', 'css_index_lib', 'js_index_lib']);

// build comp 组件打包任务
gulp.task('build_comp', ['comp_copy'], function () {
    gulp.run(['comp_react_jsx', 'comp_css_min']);
});