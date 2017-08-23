var webapp = '../src/main/webapp';
var static = '../static';

// js
var js = {
        src: static + '/js',
        dest: webapp + '/js'
    };

js.portal_lib = [
        js.src + '/lib/jquery/jquery-1.11.3.min.js',
        js.src + '/lib/jquery/jquery.browser.js',
        js.src + '/lib/react/react.min.js',
        js.src + '/lib/react/react-dom.min.js',
        js.src + '/lib/bootstrap/bootstrap.min.js',
        js.src + '/pv/pv.js',
        js.src + '/heartbeat/heartbeat.js',
        js.src + '/geolocation/geolocation.js'
    ];

js.ie8_auth = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/ie8/ie8.js',
    js.src + '/pv/pv.js'
];

js.ie8_navigation = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/pv/pv.js'
];

js.ie8_timebuy = [
    js.src + '/lib/jquery/jquery-1.11.3.min.js',
    js.src + '/ie8/timebuy.js'
];

// css
var css = {
        src: static + '/css',
        dest: webapp + '/css'
    };

css.portal_lib = [
    css.src + '/lib/bootstrap/bootstrap.min.css',
    css.src + '/lib/portal.css'
];

css.ie8_auth = [
    css.src + '/ie8/auth.css'
];

css.ie8_navigation = [
    css.src + '/ie8/navigation.css'
];

css.ie8_timebuy = [
    css.src + '/ie8/timebuy.css'
];

// html
var html = {
        src: static + '/html',
        dest: webapp + '/html'
    };

html.src_files = html.src + '/**/*.html';// html源文件

// 图片
var img = {
        src: static + '/images',
        dest: webapp + '/images'
    };

module.exports = {
    js: js,
    css: css,
    html: html,
    img: img
};