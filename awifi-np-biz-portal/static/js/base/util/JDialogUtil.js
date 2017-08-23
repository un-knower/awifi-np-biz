var jDialog = {
    _onEnd: null,
    _canClose: true,
    _tmr: null,
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
            //$('#jDialog').jDrag({container:'#jDialog_Overlay', handle:'#jDialog-Title'});
        }else{
            $('#jDialog_Overlay').show();
            $('#jDialog').show();
        }
        if(typeof opts.onBegin == 'function') opts.onBegin();
        this.onEnd = typeof opts.onEnd == 'function' ? opts.onEnd : null;
        if(!opts.okButton && !opts.cancelButton) $('#jDialog .jDialog-Buttons').hide();
        $('#jDialog .jDialog-Title span').html(opts.title);
        $('#jDialog .jDialog-Title a').click(this.hide);
        $('#jDialog .jDialog-Container').height(opts.height).html(opts.content);
        if(opts.scroll) $('#jDialog .jDialog-Container').addClass('Scroll');
        if(this._canClose){
            if(opts.okButton) $('#jDialog .jDialog-Buttons').append('<button class="jDialog-OK">'+opts.okLabel+'</button>');
            if(opts.cancelButton) $('#jDialog .jDialog-Buttons').append('<button class="jDialog-Cancel">'+opts.cancelLabel+'</button>');
            if(opts.otherButton) $('#jDialog .jDialog-Buttons').append('<button class="jDialog-Other">'+opts.otherLabel+'</button>');
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
    addButton: function(btn, cls, fun){
        var _this = this;
        $('#jDialog .jDialog-Buttons').append('<button class="jDialog-'+cls+'">'+btn+'</button>');
        $('#jDialog .jDialog-Buttons button.jDialog-'+cls).click(function(){
            if(fun()) _this.hide();
        });
    },
    message: function(str){
        clearTimeout(this._tmr);
        $('#jDialog .jDialog-Buttons span').html(str);
        this._tmr = setTimeout(function(){
            $('#jDialog .jDialog-Buttons span').html('');
        }, 5000);
    },
    alert: function(title, str, fun){
        this.show({
            title: title,
            content: '<span class="jDialog-Text">'+str+'</span>'
        });
        if(typeof fun == 'function') fun();
    },
    confirm: function(title, str, fun){
        this.show({
            title: title,
            content: '<span class="jDialog-Text">'+str+'</span>',
            cancelButton: 1,
            onOK: fun
        });
    },
    openform: function(title, url, width, height, fun){
        this.show({
            title: title,
            scroll: 1,
            content: '<div class="loading"></div>',
            width: width,
            height: height,
            cancelButton: 1,
            onOK: fun
        });
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'html',
            success: function(data){
                $('#jDialog .jDialog-Container').html('<div class="winpage">'+data+'</div>');
            },
            error: function(){
                $('#jDialog .jDialog-Container').html('<div class="loadfailed">加载页面失败</div>');
            }
        });
    },
    openurl: function(title, url, width, height){
        this.show({
            title: title,
            scroll: 1,
            content: '<div class="loading"></div>',
            width: width,
            height: height
        });
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'html',
            success: function(data){
                $('#jDialog .jDialog-Container').html('<div class="winpage">'+data+'</div>');
            },
            error: function(){
                $('#jDialog .jDialog-Container').html('<div class="loadfailed">加载页面失败</div>');
            }
        });
    },
    openhtmlform: function(title, htmlcode, width, height, fun){
        this.show({
            title: title,
            scroll: 1,
            content: '<div class="winpage">'+htmlcode+'</div>',
            width: width,
            height: height,
            cancelButton: 1,
            onOK: fun
        });
    },
    openhtml: function(title, htmlcode, width, height){
        this.show({
            title: title,
            scroll: 1,
            content: '<div class="winpage">'+htmlcode+'</div>',
            width: width,
            height: height
        });
    },
    openhtmlcb: function(title, htmlcode, width, height){
        this.show({
            title: title,
            scroll: 1,
            content: '<div class="winpage">'+htmlcode+'</div>',
            width: width,
            height: height,
            cancelButton: 1,
            okButton: 0
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