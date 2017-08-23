/*!
 * jQuery Browser Plugin 0.1.0
 * https://github.com/gabceb/jquery-browser-plugin
 *
 * Original jquery-browser code Copyright 2005, 2015 jQuery Foundation, Inc. and other contributors
 * http://jquery.org/license
 *
 * Modifications Copyright 2015 Gabriel Cebrian
 * https://github.com/gabceb
 *
 * Released under the MIT license
 *
 * Date: 05-07-2015
 */
/*global window: false */

(function(factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['jquery'], function($) {
            return factory($);
        });
    } else if (typeof module === 'object' && typeof module.exports === 'object') {
        // Node-like environment
        module.exports = factory(require('jquery'));
    } else {
        // Browser globals
        factory(window.jQuery);
    }
}(function(jQuery) {
    "use strict";

    function uaMatch(ua) {
        // If an UA is not provided, default to the current browser UA.
        if (ua === undefined) {
            ua = window.navigator.userAgent;
        }
        ua = ua.toLowerCase();

        var match = /(edge)\/([\w.]+)/.exec(ua) ||
            /(opr)[\/]([\w.]+)/.exec(ua) ||
            /(chrome)[ \/]([\w.]+)/.exec(ua) ||
            /(iemobile)[\/]([\w.]+)/.exec(ua) ||
            /(version)(applewebkit)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec(ua) ||
            /(webkit)[ \/]([\w.]+).*(version)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec(ua) ||
            /(webkit)[ \/]([\w.]+)/.exec(ua) ||
            /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) ||
            /(msie) ([\w.]+)/.exec(ua) ||
            ua.indexOf("trident") >= 0 && /(rv)(?::| )([\w.]+)/.exec(ua) ||
            ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];

        var platform_match = /(ipad)/.exec(ua) ||
            /(ipod)/.exec(ua) ||
            /(windows phone)/.exec(ua) ||
            /(iphone)/.exec(ua) ||
            /(kindle)/.exec(ua) ||
            /(silk)/.exec(ua) ||
            /(android)/.exec(ua) ||
            /(win)/.exec(ua) ||
            /(mac)/.exec(ua) ||
            /(linux)/.exec(ua) ||
            /(cros)/.exec(ua) ||
            /(playbook)/.exec(ua) ||
            /(bb)/.exec(ua) ||
            /(blackberry)/.exec(ua) || [];

        var browser = {},
            matched = {
                browser: match[5] || match[3] || match[1] || "",
                version: match[2] || match[4] || "0",
                versionNumber: match[4] || match[2] || "0",
                platform: platform_match[0] || ""
            };

        if (matched.browser) {
            browser[matched.browser] = true;
            browser.version = matched.version;
            browser.versionNumber = parseInt(matched.versionNumber, 10);
        }

        if (matched.platform) {
            browser[matched.platform] = true;
        }

        // These are all considered mobile platforms, meaning they run a mobile browser
        if (browser.android || browser.bb || browser.blackberry || browser.ipad || browser.iphone ||
            browser.ipod || browser.kindle || browser.playbook || browser.silk || browser["windows phone"]) {
            browser.mobile = true;
        }

        // These are all considered desktop platforms, meaning they run a desktop browser
        if (browser.cros || browser.mac || browser.linux || browser.win) {
            browser.desktop = true;
        }

        // Chrome, Opera 15+ and Safari are webkit based browsers
        if (browser.chrome || browser.opr || browser.safari) {
            browser.webkit = true;
        }

        // IE11 has a new token so we will assign it msie to avoid breaking changes
        if (browser.rv || browser.iemobile) {
            var ie = "msie";

            matched.browser = ie;
            browser[ie] = true;
        }

        // Edge is officially known as Microsoft Edge, so rewrite the key to match
        if (browser.edge) {
            delete browser.edge;
            var msedge = "msedge";

            matched.browser = msedge;
            browser[msedge] = true;
        }

        // Blackberry browsers are marked as Safari on BlackBerry
        if (browser.safari && browser.blackberry) {
            var blackberry = "blackberry";

            matched.browser = blackberry;
            browser[blackberry] = true;
        }

        // Playbook browsers are marked as Safari on Playbook
        if (browser.safari && browser.playbook) {
            var playbook = "playbook";

            matched.browser = playbook;
            browser[playbook] = true;
        }

        // BB10 is a newer OS version of BlackBerry
        if (browser.bb) {
            var bb = "blackberry";

            matched.browser = bb;
            browser[bb] = true;
        }

        // Opera 15+ are identified as opr
        if (browser.opr) {
            var opera = "opera";

            matched.browser = opera;
            browser[opera] = true;
        }

        // Stock Android browsers are marked as Safari on Android.
        if (browser.safari && browser.android) {
            var android = "android";

            matched.browser = android;
            browser[android] = true;
        }

        // Kindle browsers are marked as Safari on Kindle
        if (browser.safari && browser.kindle) {
            var kindle = "kindle";

            matched.browser = kindle;
            browser[kindle] = true;
        }

        // Kindle Silk browsers are marked as Safari on Kindle
        if (browser.safari && browser.silk) {
            var silk = "silk";

            matched.browser = silk;
            browser[silk] = true;
        }

        // Assign the name and platform variable
        browser.name = matched.browser;
        browser.platform = matched.platform;

        // ==== zhuxh 添加手机系统版本号
        browser.mobileOS = browser.platform;
        if (browser.android) {
            var num = ua.match(/Android ([\d].\d[.\d]*);/i);
            if (num) {
                browser.mobileOS += '-' + num[1];
            }
        }
        if (browser.iphone || browser.ipad) {
            var num = ua.match(/OS ([\d]_\d[_\d]*) like Mac OS X/i);
            if (num) {
                browser.mobileOS += '-' + num[1].replace(/_/g, '.');
            }
        }

        // ==== zhuxh 20161116
        // 终端品牌
        var terminalBrandMatch = /;([\w\- ]+)build\//.exec(ua) || // android手机
            /cpu([\w\- ]+)like/.exec(ua) || // iphone手机
            [];
        var terminalBrand = terminalBrandMatch[1] || terminalBrandMatch[0];
        terminalBrand = terminalBrand ? terminalBrand.replace(/(^\s*)|(\s*$)/g, "") : browser.platform;
        browser.terminalBrand = terminalBrand;

        // 终端版本
        var terminalVersionMatch = /windows([\w\-\. ]+)/i.exec(ua) || //桌面win
                /linux([\w\-\. ]+)/i.exec(ua) || //桌面linux
                /macintosh([;\w\-\. ]+)/i.exec(ua) || //桌面mac
                /(iphone|ipad)([;\w\-\. ]+)/i.exec(ua) || //手机iphone/ipad
                /linux([;\w\-\. ]+)/i.exec(ua) || //手机android
            [];
        var terminalVersion = terminalVersionMatch[2] || terminalVersionMatch[1] || terminalVersionMatch[0];
        var terminalVersionNumber = /[\d_\-\.]+/.exec(terminalVersion) || []; // 匹配数值
        terminalVersionNumber = terminalVersionNumber[0];
        terminalVersion = terminalVersionNumber ? terminalVersionNumber.replace(/[\-_]/g, ".") : terminalVersion;
        terminalVersion = terminalVersion ? terminalVersion.replace(/(^\s*)|(\s*$)/g, "") : terminalVersion;
        browser.terminalVersion = terminalVersion;

        // 终端类型
        browser.terminalType = browser.platform;

        // console.log("browser=>", browser);
        // console.log("终端系统=>", browser.terminalType, " 终端品牌=>", browser.terminalBrand, " 终端版本=>", browser.terminalVersion);
        // console.log("\r\n\r\n");

        return browser;
    }

    // Run the matching process, also assign the function to the returned object
    // for manual, jQuery-free use if desired
    window.jQBrowser = uaMatch(window.navigator.userAgent);
    window.jQBrowser.uaMatch = uaMatch;

    // Only assign to jQuery.browser if jQuery is loaded
    if (jQuery) {
        jQuery.browser = window.jQBrowser;
    }

    return window.jQBrowser;
}));

// PC端
// ua = "mozilla/5.0 (macintosh; intel mac os x 10_12_1) applewebkit/537.36 (khtml, like gecko) chrome/54.0.2840.71 safari/537.36";
// 终端类型=>mac 终端品牌=>macintosh; intel mac os x 10_12_1 终端版本=>10.12.1
// ua = "mozilla/5.0 (windows nt 6.1) applewebkit/537.36 (khtml, like gecko) chrome/53.0.2785.101 safari/537.36";
// 终端类型=>windows 终端品牌=>windows nt 6.1 终端版本=>6.1
// ua = "mozilla/5.0 (x11; linux x86_64) applewebkit/537.36 (khtml, like gecko) chrome/53.0.2785.143 safari/537.36";
// 终端类型=>linux 终端品牌=>linux x86_64 终端版本=>86.64

// 手机端
// ua = "mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko) version/9.0 mobile/13b143 safari/601.1";
// 终端类型=>iphone 终端品牌=>iphone os 9_1 终端版本=>9.1
// ua = "mozilla/5.0 (linux; android 6.0; nexus 5 build/mra58n) applewebkit/537.36 (khtml, like gecko) chrome/48.0.2564.23 mobile safari/537.36";
// 终端类型=>android 终端品牌=>nexus 5 终端版本=>6.0
// ua = "mozilla/5.0 (linux; android 5.0; sm-g900p build/lrx21t) applewebkit/537.36 (khtml, like gecko) chrome/48.0.2564.23 mobile safari/537.36";
// 终端类型=>android 终端品牌=>sm-g900p 终端版本=>5.0
// ua = "mozilla/5.0 (linux; u; android 4.4.4; zh-cn; hm note 1s build/ktu84p) applewebkit/537.36 (khtml, like gecko) version/4.0 chrome/46.0.2490.85 mobile safari/537.36 xiaomi/miuibrowser/8.4.3";
// 终端类型=>android 终端品牌=>hm note 1s 终端版本=>4.4.4

// jQBrowser.uaMatch("mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko) version/9.0 mobile/13b143 safari/601.1");
// jQBrowser.uaMatch("mozilla/5.0 (linux; android 6.0; nexus 5 build/mra58n) applewebkit/537.36 (khtml, like gecko) chrome/48.0.2564.23 mobile safari/537.36");
// jQBrowser.uaMatch("mozilla/5.0 (linux; android 5.0; sm-g900p build/lrx21t) applewebkit/537.36 (khtml, like gecko) chrome/48.0.2564.23 mobile safari/537.36");
// jQBrowser.uaMatch("mozilla/5.0 (linux; u; android 4.4.4; zh-cn; hm note 1s build/ktu84p) applewebkit/537.36 (khtml, like gecko) version/4.0 chrome/46.0.2490.85 mobile safari/537.36 xiaomi/miuibrowser/8.4.3");
//
// jQBrowser.uaMatch("mozilla/5.0 (macintosh; intel mac os x 10_12_1) applewebkit/537.36 (khtml, like gecko) chrome/54.0.2840.71 safari/537.36");
// jQBrowser.uaMatch("mozilla/5.0 (windows nt 6.1) applewebkit/537.36 (khtml, like gecko) chrome/53.0.2785.101 safari/537.36");
// jQBrowser.uaMatch("mozilla/5.0 (x11; linux x86_64) applewebkit/537.36 (khtml, like gecko) chrome/53.0.2785.143 safari/537.36");
