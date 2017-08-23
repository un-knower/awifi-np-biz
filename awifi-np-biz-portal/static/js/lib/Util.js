/**
 * Util 工具方法
 * @version 1.0.2
 * @create luhaining1226@126.com
 */
(function(){

	/**
	 * Util 为公共方法类
	 * @name Util
	 * @class
	 * @property {string} this.name 
	 * @property {string} this.version
	 * @type object
	 * @author luhaining1226@126.com
	 * @date 2011-12-19
	 */
	var Util = function(){
		this.name = 'Util';
		this.version = '1.0.2';
	};
	this.Util = new Util();
	Util = this.Util;
	/**
	 * Util.emptyFn 空方法
	 * @class
	 * @name emptyFn
	 * @author luhaining1226@126.com
	 * @date 2013-05-22
	 */
	Util.emptyFn = function(){};
	
	/**
	 * Util.isString 判断对象是否为字符串
	 * @class
	 * @name isString
	 * @author luhaining1226@126.com
	 * @date 2013-11-11
	 */
	Util.isString = function(o){
		return Object.prototype.toString.call(o) === '[object String]';
	};
	/**
	 * Util.isNumber 判断对象是否为数字
	 * @class
	 * @name isNumber
	 * @author luhaining1226@126.com
	 * @date 2015-3-17
	 */
	Util.isNumber = function(o){
		return Object.prototype.toString.call(o) === '[object Number]';
	};
	/**
	 * Util.isArray 判断对象是否为数组
	 * @class
	 * @name isArray
	 * @author luhaining1226@126.com
	 * @date 2013-11-28
	 */
	Util.isArray = function(o){
		return Object.prototype.toString.call(o) === '[object Array]';
	};
	/**
	 * Util.isObject 判断对象是否为对象
	 * @class
	 * @name isObject
	 * @author luhaining1226@126.com
	 * @date 2014-09-17
	 */
	Util.isObject = function(o){
		return Object.prototype.toString.call(o) === '[object Object]';
	};
	/**
	 * Util.isFn 判断对象是否为方法
	 * @class
	 * @name isFn
	 * @author luhaining1226@126.com
	 * @date 2013-11-26
	 */
	Util.isFn = function(o){
		return Object.prototype.toString.call(o) === '[object Function]';
	};
	
	Util.Browser = {
        browser: navigator.userAgent.toLowerCase(),
        version: (navigator.userAgent.toLowerCase().match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [ 0, "0" ])[1],
        safari: /webkit/i.test(navigator.userAgent.toLowerCase()) && !this.chrome,
        opera: /opera/i.test(navigator.userAgent.toLowerCase()),
        firefox: /firefox/i.test(navigator.userAgent.toLowerCase()),
        ie: /msie/i.test(navigator.userAgent.toLowerCase()) && !/opera/.test(navigator.userAgent.toLowerCase()),
        mozilla: /mozilla/i.test(navigator.userAgent.toLowerCase()) && !/(compatible|webkit)/.test(navigator.userAgent.toLowerCase()) && !this.chrome,
        chrome: /chrome/i.test(navigator.userAgent.toLowerCase()) && /webkit/i.test(navigator.userAgent.toLowerCase()) && /mozilla/i.test(navigator.userAgent.toLowerCase())
    };
        
	/**
	 * Util.Date 日期操作类
	 * @class
	 * @name Date
	 * @date 2011-12-19
	 */
	Util.Date = {};
	/**
	 * 将日期格式转换为标准格式的字符串，例如"2013-05-06 10:13:27 616"
	 * @memberof Date
	 * @param date 日期，为null则默认为new Date();
	 * @author luhaining1226@126.com
	 */
    Util.Date.getTime = function(date) {
        date = date ? date : new Date();
        var y = date.getFullYear();
        var M = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var m = date.getMinutes();
        var s = date.getSeconds();
        var S = date.getTime() % 1e3;
        var html = y + "-";
        if (M < 10) {
            html += "0";
        }
        html += M + "-";
        if (d < 10) {
            html += "0";
        }
        html += d + " ";
        if (h < 10) {
            html += "0";
        }
        html += h + ":";
        if (m < 10) {
            html += "0";
        }
        html += m + ":";
        if (s < 10) {
            html += "0";
        }
        html += s;
        html += " ";
        if (S < 100) {
            html += "0";
        }
        if (S < 10) {
            html += "0";
        }
        html += S;
        return html;
    };
    /**
	* 时间格式化工具
	* @memberof Date
	* @param date 日期
	* @param pattern 日期格式，例如yyyy-MM-dd，如果为null默认为 yyyy-MM-dd HH:mm:ss SSS格式
	* @author luhaining1226@126.com
	*/
    Util.Date.format = function(date, pattern) {
        var formatSymbols = "yMdHmsS";
        if (pattern == null || pattern == undefined) {
            pattern = "yyyy-MM-dd HH:mm:ss SSS";
        }
        var time = this.getTime(date);
        // 标记存入数组
        var cs = formatSymbols.split("");
        // 格式存入数组
        var fs = pattern.split("");
        // 构造数组
        var ds = time.split("");
        // 标志年月日的结束下标
        var y = 3;
        var M = 6;
        var d = 9;
        var H = 12;
        var m = 15;
        var s = 18;
        var S = 22;
        // 逐位替换年月日时分秒和毫秒
        for (var i = fs.length - 1; i > -1; i--) {
            switch (fs[i]) {
              case cs[0]:
                fs[i] = ds[y--];
                break;

              case cs[1]:
                fs[i] = ds[M--];
                break;

              case cs[2]:
                fs[i] = ds[d--];
                break;

              case cs[3]:
                fs[i] = ds[H--];
                break;

              case cs[4]:
                fs[i] = ds[m--];
                break;

              case cs[5]:
                fs[i] = ds[s--];
                break;

              case cs[6]:
                fs[i] = ds[S--];
                break;
            }
        }
        return fs.join("");
    };
    /**
	* 时间格式解析工具
	* @memberof Date
	* @param date 日期
	* @param pattern 日期格式，例如yyyy-MM-dd，如果为null默认为 yyyy-MM-dd HH:mm:ss SSS格式
	* @author luhaining1226@126.com
	*/
    Util.Date.parse = function(date, pattern) {
        var formatSymbols = "yMdHmsS";
        if (pattern == null || pattern == undefined) {
            pattern = "yyyy-MM-dd HH:mm:ss SSS";
        }
        var y = "";
        var M = "";
        var d = "";
        var H = "";
        var m = "";
        var s = "";
        var S = "";
        // 标记存入数组
        var cs = formatSymbols.split("");
        // 格式存入数组
        var ds = pattern.split("");
        // date   = "2005-08-22 12:12:12 888";
        // format = "yyyy-MM-dd HH:mm:ss SSS";
        // sign   = "yMdHmsS";
        var size = Math.min(ds.length, date.length);
        for (var i = 0; i < size; i++) {
            switch (ds[i]) {
              case cs[0]:
                y += date.charAt(i);
                break;
              case cs[1]:
                M += date.charAt(i);
                break;
              case cs[2]:
                d += date.charAt(i);
                break;
              case cs[3]:
                H += date.charAt(i);
                break;
              case cs[4]:
                m += date.charAt(i);
                break;
              case cs[5]:
                s += date.charAt(i);
                break;
              case cs[6]:
                S += date.charAt(i);
                break;
            }
        }
        if (y.length < 1) y = 0; else y = parseInt(y, 10);
        if (M.length < 1) M = 0; else M = parseInt(M, 10);
        if (d.length < 1) d = 0; else d = parseInt(d, 10);
        if (H.length < 1) H = 0; else H = parseInt(H, 10);
        if (m.length < 1) m = 0; else m = parseInt(m, 10);
        if (s.length < 1) s = 0; else s = parseInt(s, 10);
        if (S.length < 1) S = 0; else S = parseInt(S, 10);
        var d = new Date(y, M - 1, d, H, m, s, S);
        return d;
    };

	/**
	 * Util.Decoder 编码格式转换类
	 * @class
	 * @name Decoder
	 * @author luhaining1226@126.com
	 * @date 2011-12-19
	 */
	Util.Decoder = (function() {
		/** @lends Decoder */
		return{
			/**
			 * native 转 ascii。
			 * @type function
			 * @param strNative 要转换的native。
			 * @author luhaining1226@126.com
			 * @date 2011-12-19
			 */
			native2ascii: function(strNative) {
				var output = "";
				for (var i = 0; i < strNative.length; i++) {
					var c = strNative.charAt(i);
					var cc = strNative.charCodeAt(i);
					if (cc > 255) output += "\\u" + this.toHex(cc >> 8) + this.toHex(cc & 255); else output += c;
				}
				return output;
			},
			/**
			 * native 转 hex
			 * @type function
			 * @param n 要转换的native。
			 * @author luhaining1226@126.com
			 * @date 2011-12-19
			 */
			toHex: function(n) {
				var hexChars = "0123456789ABCDEF";
				var nH = n >> 4 & 15;
				var nL = n & 15;
				return hexChars.charAt(nH) + hexChars.charAt(nL);
			},
			/**
			 * ascii 转 native
			 * @type function
			 * @param strAscii 要转换的ascii字符串。
			 * @author luhaining1226@126.com
			 * @date 2011-12-19
			 */
			ascii2native: function(strAscii) {
				if(!strAscii){
					return '';
				}
				var output = "";
				var posFrom = 0;
				var posTo = strAscii.indexOf("\\u", posFrom);
				while (posTo >= 0) {
					output += strAscii.substring(posFrom, posTo);
					output += this.toChar(strAscii.substr(posTo, 6));
					posFrom = posTo + 6;
					posTo = strAscii.indexOf("\\u", posFrom);
				}
				output += strAscii.substr(posFrom);
				return output;
			},
			/**
			 * 转换为字节。
			 * @type function
			 * @param str 要转换的字符串。
			 * @author luhaining1226@126.com
			 * @date 2011-12-19
			 */
			toChar: function(str) {
				if (str.substr(0, 2) != "\\u") return str;
				var code = 0;
				for (var i = 2; i < str.length; i++) {
					var cc = str.charCodeAt(i);
					if (cc >= 48 && cc <= 57) cc = cc - 48; else if (cc >= 65 && cc <= 90) cc = cc - 65 + 10; else if (cc >= 97 && cc <= 122) cc = cc - 97 + 10;
					code <<= 4;
					code += cc;
				}
				if (code < 255) return str;
				return String.fromCharCode(code);
			}
		};
	})();

	/**
	 * 封装装载XML的方法,并返回XML文档的根元素节点。
	 * @param isPath true时参数xml表示xml文档的名称；false时参数xml是一个字符串，其内容是一个xml文档
	 * @param xml 根据isPath参数的不同表示xml文档的名称或一个xml文档的字符串表示
	 * @type function
	 * @memberof Util
	 * @author luhaining1226@126.com
	 * @date 2012-11-28
	 */
	Util.loadXML = function(xml, isPath) {
		var xmlDoc;
		//针对IE浏览器
		if (window.ActiveXObject) {
			var aVersions = [ "MSXML2.DOMDocument.6.0", "MSXML2.DOMDocument.5.0", "MSXML2.DOMDocument.4.0", "MSXML2.DOMDocument.3.0", "MSXML2.DOMDocument", "Microsoft.XmlDom" ];
			for (var i = 0; i < aVersions.length; i++) {
				try {
					//建立xml对象
					xmlDoc = new ActiveXObject(aVersions[i]);
					break;
				} catch (oError) {}
			}
			if (xmlDoc != null) {
				//同步方式加载XML数据
				xmlDoc.async = false;
				//根据XML文档名称装载
				if (isPath === true) {
					xmlDoc.load(xml);
				} else {
					//根据表示XML文档的字符串装载
					xmlDoc.loadXML(xml);
				}
				//返回XML文档的根元素节点。
				return xmlDoc;
			}
		} else {
			//针对非IE浏览器
			if (document.implementation && document.implementation.createDocument) {
				/*
			 第一个参数表示XML文档使用的namespace的URL地址
			 第二个参数表示要被建立的XML文档的根节点名称
			 第三个参数是一个DOCTYPE类型对象，表示的是要建立的XML文档中DOCTYPE部分的定义，通常我们直接使用null
			 这里我们要装载一个已有的XML文档，所以首先建立一个空文档，因此使用下面的方式
			 */
				xmlDoc = document.implementation.createDocument("", "", null);
				if (xmlDoc != null) {
					//根据XML文档名称装载
					if (isPath === true) {
						//同步方式加载XML数据
						xmlDoc.async = false;
						xmlDoc.load(xml);
					} else {
						//根据表示XML文档的字符串装载
						var oParser = new DOMParser();
						xmlDoc = oParser.parseFromString(xml, "text/xml");
					}
					//返回XML文档的根元素节点。
					return xmlDoc;
				}
			}
		}
		return null;
	}
	/**
	 * 将字符串的首字母转换为大写。
	 * @param v 传入的字符串
	 * @memberof Util
	 * @author luhaining1226@126.com
	 */
	Util.firstCaseUpper = function(v) {
		v = String(v);
		return v.substring(0, 1).toUpperCase() + v.substring(1);
	}
	/**
	 * 将字节数据转换为带单位的格式。例如：1024 -> 1Kb（小于1Kb的文件返回'1Kb'）
	 * @param bytes 传入字节整数，必须为number类型。
	 * @return 返回带单位格式字符串，如果bytes为非number类型，则返回为""
	 * @memberof Util
	 * @author luhaining1226@126.com
	 */
	Util.bytesSize = function( bytes ) {
    	bytes = parseInt(bytes);
		if(bytes.toString() == 'NaN'){
			return 'NaN';
		}
		var i = 0;
		while(1023 < bytes){
		    bytes /= 1024;
		    ++i;
		};
		return  i ? bytes.toFixed(2) + ["", "Kb", "Mb", "Gb", "Tb"][i] : "1Kb";
	};
	/**
	 * @author luhaining1226@126.com
	 * @date 2013-07-24
	 * @params number
	 * @return bool 
	 * @comments 用于判断年份是否是闰年
	 * 第一个参数用来表示年份
	 * 返回true 表示是闰年，反之，表示非闰年
	 */	
	Util.isLeapYear = function(year){
		if(!isNaN(parseInt(year))){
			if((year%4==0 && year%100!=0)||(year%100==0 && year%400==0)){
				return true;
			}
		}
		return false;
	};
	/**
	 * @author luhaining1226@126.com
	 * @date 2013-07-24
	 * @params long (number), string (type),string (convertType)
	 * @return String 日期    转换之后的时间格式字符串 
	 * @comments 用于根据当前的日期，算出之前的日期，比如：3个月前的日期
	 * 第一个参数是数字，表示天数或者月数
	 * 第二个参数是类型，如：day、month、year
	 * 第三个参数是指定转换的格式，如：-、/
	 * 默认返回的时间格式 YYYYMMDD
	 */	
	Util.getBeforeDate = function(number, type, convertType) {
		var date = new Date();
		var year = date.getFullYear();	
		var month = date.getMonth()+1;
		var day = date.getDate();
		if(type == 'day'){
			day = day - number + 1;
			if(day < 1){
				month = month - 1;
				if(month < 1){
					year = year - 1;
					month = month + 12;
				}
				switch(month){
					case 1:case 3:case 5:case 7:case 8:case 10:case 12:day = day + 31;break;
					case 4:case 6:case 9:case 11:day = day + 30;break;
					case 2:
						if(this.isLeapYear(year)){
							day = day + 29;break;
						}else{
							day = day + 28;break;
						}
				}
			}
		}else if(type == 'month'){
			month = month - number;
			if(month < 1){
				year = year - 1;
				month = month + 12;
			}
		}else if(type == 'year'){
			year = year - number;		
		}
		
		if(month < 10){
			month = '0' + month;
		}
			
		if(day < 10){
			day = '0' + day;
		}
		
		if(typeof convertType == 'undefined'){
			convertType = '';
		}			
		result = year + convertType + month + convertType + day;
		return result;
	};
	/**
	 * @author luhaining1226@126.com
	 * @date 2013-12-31
	 * @params string
	 * @return number
	 * @comments 获取字符串的真实长度
     * 替换双字节字符（包括汉字）为"**"，然后计算长度
	 */	
	Util.getRealLen = function(str) {
		if(!this.isString(str)){
			return -1;
		}
        return str.replace(/[^\x00-\xff]/g, '**').length;
    };
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-01-28
	 * @params string
	 * @return string
	 * @comments 根据名称获取地址栏中的参数
	 */	
	Util.queryUrl = function (name){
		var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
		var r = window.location.search.substr(1).match(reg);
		if (r!=null){
			return unescape(r[2]);
		}
		return null;
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-12
	 * @params string
	 * @return boolean
	 * @comments 检验参数是否为手机号码
     * 1[34578]开头的11位数字
	 */
	Util.isCellphone = function (phone){
        var reg = new RegExp(/^1[34578]\d{9}$/);
        return reg.test(phone);
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-12
	 * @params string
	 * @return boolean
	 * @comments 检验参数是否为固定电话（国内）
     * 区号0开头(2到3位)-电话号码(7到8位)-分机号(1到6位)
	 */
	Util.isTelephone = function (phone){
        var reg = new RegExp(/^\d{3,5}-(\d{7,8})(-\d{1,6})?$/);
        return reg.test(phone);
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-12
	 * @params string
	 * @return boolean
	 * @comments 检验参数是否为邮箱地址
	 */
	Util.isEmail = function (email){
        var reg = new RegExp(/\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/);
        return reg.test(email);
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-12
	 * @params string
	 * @return boolean
	 * @comments 检验参数是否为邮编
	 */
	Util.isPostcode = function (code){
        var reg = new RegExp(/\d{6}/);
        return reg.test(code);
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-16
	 * @params string
	 * @return boolean
	 * @comments 检验参数是否为QQ
	 */
	Util.isQQ = function (num){
        var reg = new RegExp(/^\d{5,}$/);
        return reg.test(num);
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-16
	 * @params string
	 * @return boolean
	 * @comments 检验参数是否为微信号
     * 使用6—20个字母、数字、下划线和减号，必须以字母开头
	 */
	Util.isWeChat = function (str){
        var reg = new RegExp(/^[a-zA-Z]{1}[a-zA-Z0-9_\-]{5,19}$/);
        return reg.test(str);
	}
	/**
	 * @author luhaining1226@126.com
	 * @date 2015-03-17
	 * @params Object
	 * @return String
	 * @comments 将Object的属性转为URl地址中以&拼接key=value的格式
     * 其中数组以逗号分割显示
     * 注意，此方法只做一层遍历，不涉及嵌套结构
	 */
	Util.obj2Url = function (obj){
        if(!this.isObject(obj)){
            return '';
        }
        var str = '';
        for(var k in obj){
            if(this.isString(obj[k]) || this.isNumber(obj[k])){
                str += k + '=' + obj[k];
            }else if(this.isArray(obj[k])){
                str += k + '=' + obj[k].join(',');
            }
            str += '&';
        }
        return str.replace(/&$/, '');
	}
})();