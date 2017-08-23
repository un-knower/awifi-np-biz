'use strict';

var gulp = require('gulp'),
    plug = require('gulp-load-plugins')(),
    config = require('./config');

// js文件压缩配置
var uglify_option = {
    mangle: false,
    compress: false
};

// html文件压缩配置
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

// 删除
gulp.task('clean', function () {
    return gulp.src([config.html.dest])
        .pipe(plug.clean({force: true}));
});

// html文件压缩
gulp.task('html', function () {
    gulp.src(config.html.src_files)//要压缩的html文件
        .pipe(plug.minifyHtml(minifyHtml_option))//压缩
        // 删除js注释
        .pipe(plug.replace(/\/\*[\s\S]*?\*\//g, ''))
        // 删除换行符
        .pipe(plug.replace(/\n|\r|(\r\n)|(\u0085)|(\u2028)|(\u2029)/g, ''))
        .pipe(gulp.dest(config.html.dest))
});

// js
// portal_lib.min.js
gulp.task('js_portal_lib', function () {
    gulp.src(config.js.portal_lib)
        .pipe(plug.concat('portal_lib.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩:
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// ie8.min.js
gulp.task('js_ie8_auth', function () {
    gulp.src(config.js.ie8_auth)
        .pipe(plug.concat('ie8.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩:
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// ie8_navigation.min.js
gulp.task('js_ie8_navigation', function () {
    gulp.src(config.js.ie8_navigation)
        .pipe(plug.concat('ie8_navigation.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩:
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// ie8_timebuy.min.js
gulp.task('js_ie8_timebuy', function () {
    gulp.src(config.js.ie8_timebuy)
        .pipe(plug.concat('ie8_timebuy.js')) // 多文件合并成1个文件
        .pipe(plug.uglify(uglify_option))// 文件压缩:
        .pipe(plug.rename({extname: '.min.js'})) // 文件重命名为min.js
        .pipe(gulp.dest(config.js.dest)); // 输出到此目录
});

// css
// portal_lib.min.css
gulp.task('css_portal_lib', function () {
    gulp.src(config.css.portal_lib)
        .pipe(plug.concat('portal_lib.css')) // 多文件合并成1个文件
        .pipe(plug.minifyCss()) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// ie8.min.css
gulp.task('css_ie8', function () {
    gulp.src(config.css.ie8_auth)
        .pipe(plug.concat('ie8.css')) // 多文件合并成1个文件
        .pipe(plug.minifyCss()) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// ie8_navigation.min.css
gulp.task('css_ie8_navigation', function () {
    gulp.src(config.css.ie8_navigation)
        .pipe(plug.concat('ie8_navigation.css')) // 多文件合并成1个文件
        .pipe(plug.minifyCss()) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

// ie8_timebuy.min.css
gulp.task('css_ie8_timebuy', function () {
    gulp.src(config.css.ie8_timebuy)
        .pipe(plug.concat('ie8_timebuy.css')) // 多文件合并成1个文件
        .pipe(plug.minifyCss()) // 文件压缩
        .pipe(plug.rename({extname: '.min.css'})) // 文件重命名为min.css
        .pipe(gulp.dest(config.css.dest)); // 输出到此目录
});

gulp.task('css', ['css_portal_lib', 'css_ie8', 'css_ie8_navigation', 'css_ie8_timebuy']);

gulp.task('js', ['js_portal_lib', 'js_ie8_auth', 'js_ie8_navigation', 'js_ie8_timebuy']);

// build 任务
gulp.task('build', ['html','js','css']);