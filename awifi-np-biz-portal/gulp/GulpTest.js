/**
 * Created by zhuxh on 15-12-2.
 */

'use strict';

var gulp = require('gulp'),
    plug = require('gulp-load-plugins')();

gulp.task('default', function () {
    console.log('GulpTest');
    console.log('> 后端生成GulpTest.js任务文件');
    console.log('> 后端调用gulp --gulpfile=./GulpTest.js执行任务');
    console.log('> 后端删除GulpTest.js任务文件');
});

// 执行某文件 gulp --gulpfile=./gulpTest.js
