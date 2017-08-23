//Get Mouse Position
jQuery.getMousePosition = function(e){
	var posx = 0;
	var posy = 0;
	if(!e) var e = window.event;
	if(e.pageX || e.pageY){
		posx = e.pageX;
		posy = e.pageY;
	}else if(e.clientX || e.clientY){
		posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
		posy = e.clientY + document.body.scrollTop  + document.documentElement.scrollTop;
	}
	return {'x':posx, 'y':posy};
};

/* jDrag For jQuery 1.8+ */
;(function($){
	var jDragObj = null;
	var jDragContainer = '';
	var jDragMouseX, jDragMouseY, jDragElemTop, jDragElemLeft;
	var jDragOnDrop = null;
	var jDragOnMove = null;
	$.jDragSetPos = function(event){
		var pos = $.getMousePosition(event);
		var X = (pos.x - jDragMouseX);
		var Y = (pos.y - jDragMouseY);
		if(jDragContainer != '' && $(jDragContainer).length == 1){
			var conpos = $(jDragContainer).position();
			var minleft = conpos.left;
			minleft += isNaN(parseInt($(jDragContainer).css('margin-left'))) ? 0 : parseInt($(jDragContainer).css('margin-left'));
			minleft += isNaN(parseInt($(jDragContainer).css('border-left-width'))) ? 0 : parseInt($(jDragContainer).css('border-left-width'));
			var maxleft = minleft + $(jDragContainer).innerWidth() - $(jDragObj).outerWidth();
			var mintop = conpos.top;
			mintop += isNaN(parseInt($(jDragContainer).css('margin-top'))) ? 0 : parseInt($(jDragContainer).css('margin-top'));
			mintop += isNaN(parseInt($(jDragContainer).css('border-top-width'))) ? 0 : parseInt($(jDragContainer).css('border-top-width'));
			var maxtop = mintop + $(jDragContainer).innerHeight() - $(jDragObj).outerHeight();
			var newY = jDragElemTop + Y;
			var newX =jDragElemLeft + X;
			if(newX < minleft) newX = minleft;
			if(newX > maxleft) newX = maxleft;
			if(newY < mintop) newY = mintop;
			if(newY > maxtop) newY = maxtop;
			$(jDragObj).css("top", newY);
			$(jDragObj).css("left", newX);
		}else{
			$(jDragObj).css("top", (jDragElemTop + Y));
			$(jDragObj).css("left", (jDragElemLeft + X));
		}
	};
	$(document).mousemove(function(event){
		event.stopPropagation();
		if(jDragObj != null){
			$.jDragSetPos(event);
			if(typeof(jDragOnMove) == 'function') jDragOnMove();
			return false;
		}
	});
	$(document).mouseup(function(event){
		event.stopPropagation();
		if(jDragObj != null){
			jDragObj = null;
			if(typeof(jDragOnDrop) == 'function') jDragOnDrop();
			return false;
		}
	});
	$.fn.extend({
		jDrag: function(options){
			var defaults = {
				container	: '',
				handle		: '',
				cursor		: 'move',
				ondrag		: null,
				onmove		: null,
				ondrop		: null
			};
			var opts = $.extend(defaults, options);
			return this.each(function(){
				//$(this).css('z-index', '10000');
				var $handle = (opts.handle == '') ? $(this) : $(this).find(opts.handle);
				$handle.css('cursor', opts.cursor);
				var dragobj = this;
				$handle.mousedown(function(event){
					event.stopPropagation();
					jDragObj = dragobj;
					jDragContainer = opts.container;
					$(dragobj).css('position','absolute');
					var pos = $.getMousePosition(event);
					jDragMouseX = pos.x;
					jDragMouseY = pos.y;
					jDragElemTop  = dragobj.offsetTop;
					jDragElemLeft = dragobj.offsetLeft;
					$.jDragSetPos(event);
					if(typeof(opts.ondrop) == 'function') jDragOnDrop = opts.ondrop;
					if(typeof(opts.onmove) == 'function') jDragOnMove = opts.onmove;
					if(typeof(opts.ondrag) == 'function') opts.ondrag();
					return false;
				});
			});
		}
	});
})(jQuery);

var jDialog = {
	_canClose: true,
	show: function(options){
		this.hide();
		var _this = this;
		var defaults = {
			content: '',  //dialog html code
			width: 400,  //dialog container width
			height: 68,  //dialog container height
			title: '提示',  //dialog title
			scroll: 0,  //is container can scroll
			okButton: 1,  //is show ok button
			okLabel: '确定',  //ok button label
			cancelButton: 0,  //is show cancel button
			cancelLabel: '取消',  //cancel button label
			onBegin: function(){},  //before the dialog showing, do something...
			onEnd: function(){},  //when the dialog closed, do something...
			onOK: function(){return true;},  //press ok button, do something...
			onCancel: function(){return true;}  //press cancel button, do something...
		};
		var opts = $.extend(defaults, options);
		if(!document.getElementById('jDialog')){
			$('body').append('<div id="jDialog_Overlay"></div><div id="jDialog"><div id="jDialog-Title" class="jDialog-Title"><span></span><a href="javascript:;"></a></div><div class="jDialog-Container"></div><div class="jDialog-Buttons"><span></span></div></div>');
			$('#jDialog').jDrag({container:'#jDialog_Overlay', handle:'#jDialog-Title'});
		}else{
			$('#jDialog_Overlay').show();
			$('#jDialog').show();
		}
		this.onEnd = typeof opts.onEnd == 'function' ? opts.onEnd : null;
		if(!opts.okButton && !opts.cancelButton) $('#jDialog .jDialog-Buttons').hide();
		$('#jDialog .jDialog-Title span').html(opts.title);
		$('#jDialog .jDialog-Title a').click(this.hide);
		$('#jDialog .jDialog-Container').height(opts.height).html(opts.content);
		if(typeof opts.onBegin == 'function') opts.onBegin();
		if(opts.scroll) $('#jDialog .jDialog-Container').addClass('Scroll');
		if(this._canClose){
			if(opts.okButton) $('#jDialog .jDialog-Buttons').append('<button class="jDialog-OK">'+opts.okLabel+'</button>');
			if(opts.cancelButton) $('#jDialog .jDialog-Buttons').append('<button class="jDialog-Cancel">'+opts.cancelLabel+'</button>');
		}
		var winWidth = $(window).width(),
			winHeight = $(window).height(),
			titleHeight = $('#jDialog .jDialog-Title').outerHeight(),
			btnHeight = $('#jDialog .jDialog-Buttons').outerHeight(),
			scrollTop = $(window).scrollTop();
		$('#jDialog').css({
			width: opts.width,
			height:opts.height+titleHeight+btnHeight,
			left: (winWidth-opts.width)/2,
			top: (winHeight-opts.height-titleHeight-btnHeight)/2+scrollTop
		});
		$('#jDialog-Title a').click(function(){
			if(opts.onCancel()) _this.hide();
		});
		$('#jDialog .jDialog-Buttons button:first').focus();
		$('#jDialog .jDialog-Buttons button.jDialog-OK').click(function(){
			if(opts.onOK()) _this.hide();
		});
		$('#jDialog .jDialog-Buttons button.jDialog-Cancel').click(function(){
			if(opts.onCancel()) _this.hide();
		});
	},
	alert: function(title, str, fn){
		this.show({
			title: title,
			content: '<span class="jDialog-Text">'+str+'</span>',
			onOK: fn
		});
	},
	confirm: function(title, str, fn){
		this.show({
			title: title,
			content: '<span class="jDialog-Text">'+str+'</span>',
			cancelButton: 1,
			onOK: fn
		});
	},
	open: function(title, htmlcode, width, height, onok, onbegin, onend, oncancel){
		this.show({
			title: title,
			scroll: 1,
			content: htmlcode,
			width: width,
			height: height,
			cancelButton: 1,
			onOK: onok,
			onCancel: oncancel,
			onBegin: onbegin,
			onEnd: onend
		});
	},
	openurl: function(title, url, width, height, onok, onbegin, onend, oncancel){
		this.show({
			title: title,
			scroll: 1,
			content: '<div class="loading"></div>',
			width: width,
			height: height,
			cancelButton: 1,
			onOK: onok,
			onCancel: oncancel,
			onEnd: onend
		});
		$.ajax({
			type: 'GET',
			url: url,
			dataType: 'html',
			success: function(data){
				$('#jDialog .jDialog-Container').html(data);
				onbegin();
			},
			error: function(){
				$('#jDialog .jDialog-Container').html('<div class="loadfailed">加载页面失败</div>');
			}
		});
	},
	hide: function(){
		if(this._canClose){
			$('#jDialog .jDialog-Container').html('').removeClass('Scroll');
			$('#jDialog .jDialog-Buttons').html('<span></span>');
			$('#jDialog').hide();

			$('#jDialog_Overlay').hide();
			if(typeof this.onEnd == 'function') this.onEnd();
		}
	}
};
$(document).keydown(function(event){
	if(event.keyCode == 27){
		jDialog.hide();
	}
});