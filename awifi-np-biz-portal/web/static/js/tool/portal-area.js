/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-05-11 09:16
 * 创建作者: haoxu
 * 文件名称: portal-area
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

/**
 *认证组件全局变量
 * @auth 许小满
 * @date 2016-01-10
 */
var _GLOBAL_KEY = '{@globalKey@}';/**全局日志key*/
var _GLOBAL_VALUE = '{@globalValue@}';/**全局日志value*/
var _DEV_ID = '{@devId@}';/**设备id*/
var _DEV_MAC = '{@devMac@}';/**设备MAC*/
var _SSID = '{@ssid@}';/**ssid*/

var _USER_IP = '{@userIp@}';/**用户ip*/
var _USER_MAC = '{@userMac@}';/**用户mac地址*/
var _USER_PHONE = '{@userPhone@}';/**用户手机号*/
var _URL = '{@url@}';/**用户浏览器输入的被拦截前的url原始地址*/
var _LOGIN_TYPE = '{@loginType@}';/**登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网*/
var _GW_ADDRESS = '{@gwAddress@}';/**胖AP类设备网关*/
var _GW_PORT = '{@gwPort@}';/**胖AP类设备端口*/
var _NAS_NAME = '{@nasName@}';/**nas设备名称，NAS认证必填*/
var _TOKEN = '{@token@}';/**用户token*/

var _CUSTOMER_ID = '{@customerId@}';/**客户id*/
var _CASCADE_LABEL = '{@cascadeLabel@}';/**客户层级*/

var _SITE_ID = '{@siteId@}';/**站点id*/
var _SITE_PAGE_ID = '{@sitePageId@}';/**站点页面id*/
var _SITE_NAME = '{@siteName@}';/**站点名称*/
var _PAGE_TYPE = '{@pageType@}';/**站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页*/
var _NUM = '{@num@}';/**站点页面序号*/
var _DISCLAIMER = 'none';/** none 代表 无免责声明组件、agree 代表 免责声明组件已勾选同意、refuse 代表 免责声明组件未勾选同意  */


var hasEdit = 0; // 默认没有触发编辑操作
var gobal_projectId = ""; // 项目ID


// 通过get方式
var _debug = false,
    _console = {
        log: function () {
            if (_debug) {
                console.log(arguments)
            }
        }
    };

// 判断js对象是否为空值
function objectIsEmpty(obj) {
    var name;
    for (name in obj) {
        return false;
    }
    return true;
}

/**
 * 获取component参数
 * @param path
 * @param entity
 * @param setting
 * @returns {{path: *, entity: *, setting: *}}
 */
var ComponentClass = function () {
    /**
     portal页面
     */
    var pageMap = {
        'page1': [], // 引导页
        'page2': [], // 认证页
        'page3': [], // 过渡页
        'page4': [] // 导航页
    };

    // 存放所有组件json数据
    var componentJson = [];

    /**
     * 组件列表
     * @type {Array} { page_id: xx, comp_id: xx, option: xx, is_edit: 1|0, is_del: 1|0 }
     */
    var entitylist = [];

    /**
     * 当前页面或组件
     */
    var pageDoing = '',
        entityDoing = '';

    // 初始化认证、导航页面
    pageMap['page2'].push({
        page_id: 'page2',
        pageType: '2',  // 页面类型
        num: '2', // 第几页
        pageOperation: 'add',  // 页面操作 edit编辑 add添加(默认) remove删除
        pageId: '0'  // 页面Id add=0, edit/remove=实际值
    });

    pageMap['page4'].push({
        page_id: 'page4',
        pageType: '4',  // 页面类型
        num: '4', // 第几页
        pageOperation: 'add',  // 页面操作 edit编辑 add添加(默认) remove删除
        pageId: '0'  // 页面Id add=0, edit/remove=实际值
    });

    /**
     * 获取组件对象json数据
     * @param id
     * @returns {{}}
     */
    function getComponentJsonItem(id) {
        var result = {};
        for (var i=0,len=componentJson.length; i<len; i++) {
            var item = componentJson[i];
            if (item.id == id) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * 设置组件对象json数据
     * @param item
     */
    function setComponentJsonItem(item) {
        console.log(item);
        //如何 componentJson中不包含item，就把item放到数组中
        if (Object.keys(getComponentJsonItem(item.id)).length == 0) {
            componentJson.push(item);
        }
    }

    /**
     * 返回所有组件对象json数据
     * @returns {Array}
     */
    function getComponentJsons() {
        return componentJson;
    }

    /**
     * 设置当前编辑的页面标识
     * @param pageId
     */
    function setPageDoing(pageId) {
        pageDoing = pageId;
        console.log('设备当前页面pageDoing=>', pageDoing);
    }

    /**
     * 获取当前编辑的页面标识
     * @returns {string}
     */
    function getPageDoing() {
        _console.log('获取当前页面getPageDoing=>', pageDoing);
        return pageDoing;
    }

    /**
     * 设置当前编辑的组件标识
     * @param comp_id
     */
    function setEntityDoing(comp_id) {
        entityDoing = comp_id;
        console.log('设置当前组件实体setEntityDoing=>', entityDoing);
    }

    /**
     * 获取当前编辑的组件标识
     */
    function getEntityDoing() {
        console.log('获取当前组件实体getEntityDoing=>', entityDoing);
        return entityDoing;
    }

    /**
     * 返回页面名称
     * @param page_type
     */
    function pageTypeName(page_type) {
        if (page_type == '1') {
            return '引导';
        } else if (page_type == '2') {
            return '认证';
        } else if (page_type == '3') {
            return '过渡';
        } else {
            return '导航';
        }
    }

    /**
     * 生成页面标识page_id
     */
    function addPageId() {
        var page_id = 'page_' + (new Date().getTime()).toString();
        return page_id;
    }

    /**
     * 生成组件标识 comp_id
     */
    function addEntityId() {
        var comp_id = 'comp_' + (new Date().getTime()).toString();
        return comp_id;
    }

    /**
     * 添加portal页面
     * @param page_type
     */
    function pageAdd(page_type, page_id, pageOperation, pageId) {
        pageOperation = pageOperation || 'add';
        pageId = pageId || '0';
        var page = pageMap['page'+page_type];

        page.push({
            page_id: page_id.toString(),
            pageType: page_type.toString(),  // 页面类型
            num: 1, // 第几页
            pageOperation: pageOperation,  // 页面操作 edit编辑 add添加(默认) remove删除
            pageId: pageId.toString()  // 页面Id add=0, edit/remove=实际值
        });
        //  <div class="page-item"><span><a href="javascript:;">引导第1页</a></span> <a href="javascript:;" class="page-del" title="删除当前引导页"></a></div>
        var page_div = $('#page-type' + page_type),
            page_item_len = page_div.find('.page-item').size();
        var title = (page_type == '1' ? '引导' : '过渡') + '第' + (page_item_len + 1) + '页';
        var _style = isSite() ? '' : 'style="display:inline-block">';
        page_div.append('<div class="page-item">'+
            '<span><a class="page-title" href="javascript:;" data-page-id="'+page_id+'" data-page-type="'+page_type+'">' + title + '</a></span> '+
            '<a href="javascript:;" class="page-del" data-page-id="'+page_id+'" data-page-type="'+page_type+'" title="删除当前引导页" '+ _style +
            '</a></div>');

        console.log('添加页面结果add_page=>pagemap: ', pageMap);
    }

    /**
     * 删除portal页面
     * @param page_type
     * @param page_id
     */
    function pageDel(page_type, page_id) {
        var pages = pageMap['page'+page_type];
        for (var i=0,len=pages.length; i<len; i++) {
            var item = pages[i];
            if (item.page_id == page_id) {
                if (item.pageOperation == 'edit') {
                    item.pageOperation = 'remove';
                } else {
                    var p = pages.splice(i, 1);
                }
                break;
            }
        }
        console.log('删除页面结果pageDel=>pagemap: ', pageMap);
    }

    /**
     * 返回页面列表数据
     * @returns {{page1: Array, page2: Array, page3: Array, page4: Array}}
     */
    function getPageMap(pageType) {
        pageType = pageType || '';
        return (pageType ? pageMap['page'+pageType] : pageMap);
    }

    /**
     * 添加单个组件数据
     */
    function addEntityData(data) {
        // -1不选中：不强制
        var canChildEdit = !!data.canChildEdit ? data.canChildEdit : '-1',
        //canEdit = !!data.canEdit ? data.canEdit : '-1',
            dataJson = data.json ? JSON.parse(data.json) : {},
        // tpcId = !!data.id ? data.id : '-1',
            componentId = data.componentId ? data.componentId : '',
            componentOperation = data.componentOperation ? data.componentOperation : 'add',
            orderNo = !!data.orderNo ? data.orderNo : '0';
        _console.log('添加组件实体addEntityData=>', data);
        var item = {
            page_id: getPageDoing().toString(),
            comp_id: getEntityDoing().toString(),
            //tpcId: tpcId.toString(),
            // canEdit: canEdit.toString(),
            // canChildEdit: canChildEdit.toString(),
            componentId: componentId.toString(),
            componentOperation: componentOperation, //edit编辑(默认) add添加 remove删除
            json: dataJson,
            orderNo: orderNo.toString()
        };
        entitylist.push(item);
        console.log('添加组件实体结果addEntityData=>', entitylist);
    }

    /**
     * 返回所有组件实体Entity列表
     * @returns {Array}
     */
    function getEntityLists() {
        return entitylist;
    }

    /**
     * 获取指定单个组件实体Entity的数据
     * @param comp_id
     */
    function getEntityData(comp_id) {
        var comps = getEntityLists();
        var comp = {};
        for (var i=0,len=comps.length; i<len; i++) {
            var item = comps[i];
            if (item.comp_id == comp_id) {
                comp = item;
                break;
            }
        }
        _console.log('获取组件实体数据getEntityData=>', comp_id, comp);
        return comp;
    }

    /**
     * 获取指定组件实体Entity的状态数据
     */
    function getEntityState(compId) {
        try {
            return window[compId].state;
        } catch (e) {
            console.log('[fault]getEntityState=>', compId);
            return {};
        }
    }

    /**
     * 设置指定组件实体Entity的数据
     */
    function setEntityData(compId, key, value) {
        var entity = getEntityData(compId);
        if (key == 'componentOperation' && entity[key] == 'add' && value == 'remove') {
            // 删除添加的组件数据
            delEntityData(compId);
        } else {
            entity[key] = value;
        }
    }

    /**
     * 删除手工添加的组件实体数据
     * @param compId
     */
    function delEntityData(compId) {
        var entity = getEntityLists();
        for (var i=0,len=entity.length; i<len; i++) {
            var item = entity[i];
            // splice删除元素后数组长度少1位
            if (item && item.comp_id == compId) {
                entity.splice(i, 1);
                break;
            }
        }
    }

    /**
     * 获取指定页面Id（page_1451530827401）的所有组件列表数据
     * @param pageId
     */
    function getPageEntityData(pageId, getState) {
        console.log("pageId=====");
        console.log(pageId);
        getState = getState || false;
        var components = [];
        var compLists = getEntityLists();

        for (var i=0,len=compLists.length; i<len; i++) {
            var item = compLists[i];
            if (item.page_id == pageId) {
                // 设备组件的state
                if (getState) {
                    var compId = item.comp_id;
                    item.json = getEntityState(compId);
                }
                components.push(item);
            }
        }
        // 从小到大排序组件实体
        components.sort(entitySort);
        return components;
    }

    /**
     * 编辑页面组件对象
     * @param pageId
     * @returns {Array}
     */
    function editPageEntityList(pageId, isCreate) {
        isCreate = isCreate || false;
        var entitys = getPageEntityData(pageId, true);
        console.log('获取页面所有组件实体列表editPageEntityList=>', pageId, entitys);
        //var cpt_param = getComponentJsonItem(item.componentId);

        for (var i=0,len=entitys.length; i<len; i++) {
            var item = entitys[i];
            if (isCreate) {
                console.log('第一次组件要执行setState');
                // 第一次组件要执行setState()
                if (item.componentOperation != 'remove') {
                    setTimeout(function () {
                        console.log("222222222");
                        createReactEntity(item);
                    }, 1000);
                    // createReactEntity(item);
                }
            }
        }
        return entitys;
    }

    /**
     * 返回模板数据（接口提交数据）
     */
    function getTemplateInfo() {
        var list = [];
        var pageMap = getPageMap();
        for (var page in pageMap) {
            var num = 1;
            var pages = pageMap[page];
            for (var i=0,len=pages.length; i<len; i++) {
                var item = pages[i];
                var pageId = item.page_id;
                // 重新算第几页
                item.num = num.toString();
                var components = getPageEntityData(pageId, true);

                //== 页面组件数据验证开始 ==
                // 页面不能为空且必须含有跳转组件
                var hasComp = [];
                var hasCompRedirect = [];
                var hasAuth = [];
                var isPage13 = /[13]/.test(item.pageType.toString());
                if (isPage13) {
                    var pageName = pageTypeName(item.pageType)+'第'+(i+1)+'页';
                } else {
                    var pageName = pageTypeName(item.pageType);
                }

                for (var c = 0, clen = components.length; c < clen; c++) {
                    var citem = components[c];
                    var compJson = getComponentJsonItem(citem.componentId);

                    // 删除组件不计数
                    if (citem.componentOperation != 'remove') {
                        // 判断页面是否为空
                        hasComp.push(citem.componentId);

                        // 判断认证页是否含用认证组件
                        if (item.pageType == '2' && compJson.classify == 2) {
                            hasAuth.push(citem.componentId);
                        }

                        // 判断页面是否含有跳转组件
                        if (isPage13 && item.pageOperation != 'remove' && compJson.classify == 3) {
                            hasCompRedirect.push(citem.componentId);
                        }
                    }
                }

                // 页面不能为空
                if (hasComp.length == 0) {
                    return {
                        code: true,
                        data: pageName + '不能为空'
                    };
                }
                // 认证页必须含有认证组件
                if (item.pageType == 2 && hasAuth.length == 0) {
                    return {
                        code: true,
                        data: '认证页必须含有认证组件'
                    };
                }
                // 每页必须含有跳转组件
                if (isPage13 && item.pageOperation != 'remove' && hasCompRedirect.length == 0) {
                    return {
                        code: true,
                        data: pageName + '必须包含页面链接组件'
                    };
                }
                //== 页面组件数据验证结束 ==

                item.components = components;
                list.push(item);
                num++;
            }
        }
        _console.log('获取页面所有json数据getTemplateInfo=>', list);
        return {
            code: false,
            data: list
        };
    }

    /**
     * 保存时组件实体数据验证是否有效
     */
    function entityValidform() {
        // 认证页必须含有认证组件
        var page2Entitys = getPageEntityData('page2', true);
        _console.log('验证页面数据entityValidform=>', page2Entitys);
        var hasAuth = 1;
        for (var i=0,len=page2Entitys.length; i<len; i++) {
            var item = page2Entitys[i];
            var compJson = getComponentJsonItem(item.componentId);
            if (item.componentOperation != 'remove' && compJson.classify == 2) {
                hasAuth = 0;
                break;
            }
        }

        // 页面不能为空且必须含有跳转组件

        return {
            code: hasAuth ? true : false,
            message: hasAuth ? '认证页必须含有认证组件' : '认证页已含有认证组件'
        }
    }

    /**
     * 获取URL参数 {健值对}
     */
    function getArgs() {
        var args = {};
        var match = null;
        var search = location.search.substring(1);
        var reg = /(?:([^&]+)=([^&]+))/g;
        while((match = reg.exec(search))!==null){
            args[match[1]] = match[2];
        }
        return args;
    }

    /**
     * 创建遮照层
     */
    function layerCreate(showLoading) {
        showLoading = showLoading || 'show';
        var htm = '<div id="layer_base" style="display:none;z-index:998;position:absolute;left:0;right:0;top:0;bottom:0;width:100%;height:100%;background-color:#ededed;background-color:rgba(0,0,0,0.3)">'+
            (showLoading === 'show' ? '<div style="position:absolute;z-index:999;left:50%;top:10%;width:42px;height:42px;margin-left:-21px;background-image:url(/images/loading.gif);">' : '')+
            '</div></div>';
        $('body').append(htm);
        $('#layer_base').fadeIn();
    }

    /**
     * 删除遮照层
     */
    function layerRemove() {
        var layer_base = $('#layer_base')
        layer_base.fadeOut(function () {
            layer_base.remove();
        });
    }

    /**
     * 数据保存接口
     */
    function apiSave() {
        var province = $("#province").val();
        var city = $("#city").val();
        var siteName =$("#site-name").val();

        // 验证数据
        //var validate = entityValidform();
        //if (validate.code) {
        //    alert(validate.message);
        //    return ;
        //}
        var btn_save = $('.btn-save');
        var templateInfo = getTemplateInfo();
        console.log("save=---------------");
        console.log(templateInfo);
        console.log("save=---------------");
        if (templateInfo.code) {
            jDialog.alert('提示', templateInfo.data);
            return ;
        }
        var resp_data = {
            "provinceId":province,
            "cityId":city,
            "siteName":siteName,
            "pages": templateInfo.data
        };
        //编辑保存接口
        if (siteId && siteId < 0) {
            var url = '/portalsrv/site/'+siteId+'/location?access_token='+accessToken;
            $.ajax({
                url: url,
                type: 'put',
                data: JSON.stringify(resp_data),
                dataType: 'json',
                contentType: "application/json",
                beforeSend: function () {
                    layerCreate();
                    btn_save.attr('disabled', 'disabled');
                },
                success: function (resp) {
                    if (resp.code == '0') {
                        $('#page-type2 .page-title').click();
                        thumbImgData(resp);
                    } else {
                        jDialog.alert('提示', resp.message);
                    }
                },
                complete: function () {
                    btn_save.removeAttr('disabled');
                    layerRemove();
                }
            });
        }else{  //添加保存接口
            var url = '/portalsrv/site/location/?access_token='+accessToken;
            $.ajax({
                url: url,
                type: 'post',
                data: JSON.stringify(resp_data),
                dataType: 'json',
                contentType: "application/json",
                beforeSend: function () {
                    layerCreate();
                    btn_save.attr('disabled', 'disabled');
                },
                success: function (resp) {
                    if (resp.code == '0') {
                        $('#page-type2 .page-title').click();
                        thumbImgData(resp);
                    } else {
                        jDialog.alert('提示', resp.message);
                    }
                },
                complete: function () {
                    btn_save.removeAttr('disabled');
                    layerRemove();
                }
            });

        }
    }

    /**
     * 页面组件实体初始化
     * @param data
     */
    function pageEntityInit(data) {
        console.log("data========");
        console.log(data);
        var page_id = data.pageId,
            page_type = data.pageType;
        // 引导N页或过渡N页 添加页面操作
        if (page_type == '1' || page_type == '3') {
            // 初始化编历页面
            setPageDoing(page_id);
            pageAdd(page_type, page_id, 'edit', page_id);
        } else {
            // 认证页page_id="page2" 导航页page_id="page4"
            setPageDoing('page'+page_type);
            var page =pageMap['page' + page_type][0];
            page.pageOperation = 'edit'; // edit编辑
            page.pageId = page_id.toString(); // edit/remove=实际值
        }

        // 初始化页面组件数据
        var componets = data.componets;

        for (var i=0,len=componets.length; i<len; i++) {
            var item = componets[i];
            var comp_id = addEntityId();
            console.log("comp_id====" +comp_id);
            // 设置组件实体为编辑操作
            item['componentOperation'] = 'edit';
            setEntityDoing(comp_id);
            addEntityData(item);
            console.log("item.componentId===");
            console.log(item.componentId);
            // 渲染组件
            var componentJson = getComponentJsonItem(item.componentId);

            // 引用资源文件
            try {
                console.log("componentJson==========");
                console.log(componentJson);
                addHeader(componentJson);
            } catch (e) {
                console.log('[fault]pageEntityInit.addHeader=>', e);
                entityInitFault();
                return;
            }
            if (page_type == '1' || page_type == '3') {
                var pageCompItems = getPageEntityData(page_id);
            } else {
                var pageCompItems = getPageEntityData('page'+page_type);
            }
            for (var j=0,jlen=pageCompItems.length; j<jlen; j++) {
                var compItem = pageCompItems[j];
                try {
                    setTimeout(function () {
                        // 创建组件、设置React
                        console.log("$$$$$$$$$$$$$$$$$$$$");
                        createReactEntity(compItem, true);
                    }, 1000);

                } catch (e) {
                    _console.log('[fault]pageEntityInit.createReactEntity=>', e);
                    entityInitFault();
                }
            }
        }
    }

    /**
     * 接口返回模板页或站点页页面和组件数据
     */
    function apiGetComponentData() {
        var _type = 'get';
        if (siteId && siteId > 0) {
            var url = '/portalsrv/site/'+siteId+'?access_token='+accessToken;
            var resp_data = {
            };

        } else if (siteDefaultId && siteDefaultId > 0) {
            var url = '/siteDefault/queryDefaultSitePage';
            var resp_data = {
            };
            _type = 'post';
        }
        $.ajax({
            url: url,
            type: _type,
            data: resp_data,
            dataType: 'json',
            async: false,
            beforeSend: function () {
                layerCreate();
            },
            success: function (resp) {
                console.log('获取不同类型组件json apiGetComponentData=>', resp);
                var data = resp.data;
                gobal_projectId = resp.projectId;

                // 传值projectId

                // TODO 加载所有组件数据
                apiPageComponentListTodo(1);
                apiPageComponentListTodo(2);
                apiPageComponentListTodo(3);
                apiPageComponentListTodo(4);

                // 还原组件数据
                var pages = data.pages;
                for (var i=0,len=pages.length; i<len; i++) {
                    var item = pages[i];
                    pageEntityInit(item);
                }
            },
            complete: function() {
                var pageType1 = $('#page-type1'),
                    pageType1Page = pageType1.find('.page-title'),
                    pageType2 = $('#page-type2');
                if (pageType1Page.size() > 0) {
                    pageType1Page.eq(0).click();
                } else {
                    pageType2.find('.page-title').eq(0).click();
                }

                // 仅模板可管理页面
                if (isSite()) {
                    $('.page-del, .page-add').show();
                }
                layerRemove();
            }
        });
    }

    /**
     * 是否已引用资源文件
     * @param path
     * @returns {boolean}
     */
    function hasFile(path) {
        var re = new RegExp(path, 'mg');
        var head = $('head').html();
        return re.test(head);
    }

    /**
     * 引用资源文件
     * @param cpt
     */
    function addHeader(cpt_param) {
        console.log("cpt_param==============");
        console.log(cpt_param);

        ////just for test by haoxu
        //if(cpt_param.id ==13) return;
        ////just for test by haoxu

        var head_files = [],
            _dt = new Date().getTime(),
            path = cpt_param.componentPath,
            component_css = path + "/css/component.css?_t=" + _dt,
            setting_css = path + "/css/setting.css?_t=" + _dt,
            entity_js = path + "/script/Entity.js?_t=" + _dt,
            setting_js = path + "/script/Setting.js?_t=" + _dt;
        // 组件删除后，模板或站点含有该组件时报错 path值为undefined
        if (!objectIsEmpty(cpt_param)) {
            console.log("22222222222eeeee22222222");
            if (!hasFile(component_css)) {
                head_files.push('<link rel="stylesheet" type="text/css" href="' + component_css + '"/>');
            }
            if (!hasFile(setting_css)) {
                head_files.push('<link rel="stylesheet" type="text/css" href="' + setting_css + '"/>');
            }
            if (!hasFile(entity_js)) {
                head_files.push('<script src="' + entity_js + '"></script>');
            }
            if (!hasFile(setting_js)) {
                head_files.push('<script src="' + setting_js + '"></script>');
            }
            $('head').append(head_files.join(''));
            console.log($('head').html());
        }
    }

    /**
     * 获取不同页面的组件列表ComponentJson
     * @param type
     */
    function apiPageComponentListTodo(type,merchantId) {
        type = type || 1;
        var params = {
            "merchantId": merchantId || ''
        };
        var resp_data = {
            params: JSON.stringify(params)
        };
        $.ajax({
            url: '/portalsrv/components/type/'+type+'?access_token='+accessToken,
            type: 'get',
            data: resp_data,
            dataType: 'json',
            async: false,
            success: function (resp) {
                if (resp.code === '0') {
                    var data = resp.data;
                    for (var i in data) {
                        console.log('1111111111111');
                        console.log(i);
                        var item = data[i];
                        // 设置componentJson数据
                        setComponentJsonItem(item);
                    }
                    console.log("3333333");
                    console.log(componentJson);
                } else {
                    jDialog.alert('提示', resp.message);
                }
            }
        });
    }

    /**
     * 获取不同页面的组件列表
     * @param pageType
     */
    function apiPageComponentList(pageType, pageId, merchantId) {
        console.log("pageType="+pageType +"pageId==="+pageId);
        pageType = pageType || 1;
        var params = {
            "merchantId": merchantId || ''
        };
        var resp_data = {
            params: JSON.stringify(params)
        };
        $.ajax({
            url: '/portalsrv/components/type/'+pageType+'?access_token='+accessToken,
            type: 'get',
            data:resp_data,
            dataType: 'json',
            async: false,
            success: function (resp) {
                if (resp.code === '0') {
                    var data = resp.data,
                        htm = [];
                    for (var i in data) {
                        var item = data[i];
                        htm.push('<li data-unique="'+item.canUnique+'" data-classify="'+item.classify+'" data-id="'+item.id+'" title="'+item.name+'">'+
                            '<i style="background-image: url('+item.iconPath+')"></i>'+
                            '<p>'+item.name+'</p>'+
                            '</li>');
                    }

                    $('#comp-list').html(htm.join(''));

                    // 调用sortable方法
                    componentSortable(pageId);
                } else {
                    jDialog.alert('提示', resp.message);
                }
            }
        });
    }

    /**
     * 创建渲染组件、配置ReactClass
     * @param el 拖拉元素
     */
    function createReact(el, comp_id, cpt_param) {
        // 组件
        var entity = getEntityData(comp_id);
        var entity_name = comp_id;
        var setting_name = entity_name + 's';
        // 站点没有删除，模板有删除
        var entity_del_icon = isSite() ? '' : '<div class="entity-del"></div>';
        var entity_htm = '<div class="entity-cover"></div>'+ entity_del_icon +
            '<div id="' + entity_name + '"></div>'+
            "<script type='text/javascript'>var " + entity_name + " = " + cpt_param.code + "(null, '" + entity_name + "').render()<\/script>";

        var setting_htm = '<script>var ' + setting_name + ' = ' + cpt_param.setCode + '(' + entity_name + ', "comp-set"); ' + setting_name + '.render();</script>';
        // cid: 组件id  sid: 配置id
        el.attr('data-cid', entity_name);
        el.siblings('.active').removeClass('active');
        el.addClass('active');
        el.css({'min-height': 'auto'});
        el.html(entity_htm);
        $('#comp-set').html(setting_htm);
        compClass.setEntityData(comp_id, window[entity_name].state);
        // 设置checkbox
        propClass.init(entity);
    }

    /**
     * 创建渲染组件ReactClass
     * @param data 单个组件数据
     */
    function createReactEntity(data) {
        console.log(data);
        var componentId = data.componentId;
        var compJson = getComponentJsonItem(data.componentId),
            comp_id = data.comp_id;
        if (!objectIsEmpty(compJson)) {
            console.log("compJson==========33333=");
            console.log(compJson);
            // 组件
            var entity = getEntityData(comp_id);
            console.log("createReactEntity====");
            console.log(data.json);
            var jsons =JSON.stringify(data.json);
            console.log("5555555555");
            console.log(jsons);
            // 站点没有删除，模板有删除
            var entity_del_icon = isSite() ? '' : '<div class="entity-del"></div>';
            var htm = '<li data-unique="' + compJson.canUnique + '" data-classify="' + compJson.classify + '" data-id="' + componentId + '" data-code="' + compJson.code + '" data-set-code="' + compJson.setCode + '" ' +
                ' class="ui-draggable" data-cid="' + comp_id + '" style="min-height:auto">' +
                '<div class="entity-cover"></div>' + entity_del_icon +
                '<div id="' + comp_id + '"></div>' +
                '<script type="text/javascript">var ' + comp_id + ' = ' + compJson.code + '(' + jsons + ', "' + comp_id + '").render();</script></li>';
            console.log('创建ReactJS createReactEntity=>', htm);
        } else {
            var fault_text = isSite() ? "该组件未找到，请重新创建站点或删除该组件" : "该组件未找到，请使用其它组件替代或删除该组件";
            var htm = '<li data-id="'+componentId+'" data-cid="'+comp_id+'" style="min-height:inherit"><div class="entity-cover"></div><div class="entity-del"></div><p class="entity-fault">'+fault_text+'</p></div></li>';
        }
        $('#comp-main').append(htm);

    }

    var get_args = getArgs(),
        siteId = get_args['siteId'], // 站点id
        templateId = get_args['templateId'], // 站点id
        accessToken = get_args['access_token'], //获取access_token
        siteDefaultId = get_args['siteDefaultId']; // 默认站点id
    _debug = get_args['debug'] ? true : false;

    // access_token为空时，跳转至登录页
    if (!accessToken) {
        top.location.href = '/login';
    }
    //显示省市区
    getProvince(accessToken);

    /**
     * 是否是站点portal页面
     * @returns {*|boolean}
     */
    function isSite() {
        return !(!!templateId && templateId > 0);
    }

    function init() {
        _console.log('是否站点页isSite()=>', isSite());
        if (!!siteId && siteId > 0) {
            var _siteId = siteId;
        } else {
            var _siteId = siteDefaultId;
        }
        $('#go-back').hide();
        $('#btn-preview').prop({'href': '/site/forward?siteId='+_siteId, 'target': '_blank'});



        // 创建模板或站点首次进入认证页

        setTimeout(function () {
            apiGetComponentData();
        }, 1000);

    }

    /**
     * 重新计算拖拉组件实体的顺序
     */
    function entitySortableSort() {
        $('#comp-main li').each(function(k, v) {
            var compId = $(v).attr('data-cid');
            var num = (k + 1).toString();
            setEntityData(compId, 'orderNo', num);
        });
    }

    /**
     * 从小到大排序组件实体的顺序 字符串转化为数值比较
     */
    function entitySort(a, b) {
        var a_orderNo = parseFloat(a.orderNo);
        var b_orderNo = parseFloat(b.orderNo);

        if (a_orderNo > b_orderNo) {
            return 1;
        } else if (a_orderNo < b_orderNo) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 同类组件实体只能拖拉一个
     * @param classify: 组件分类（通用默认值，非通用分类时，同类组件也限制）
     * @param id: 组件id（非通用分类时，只限制当前组件）
     * @param action: 是否禁用
     */
    function entityUnique(classify, id, action) {
        action = action || 'disable';
        // 禁用拖拉
        if (action == 'disable') {
            if (classify > 1) {
                if (classify == 2) {
                    // 认证组件至少包含一个，多个认证类组件可并存
                    var comp_list_unique = $('#comp-list li[data-id="' + id + '"][data-unique="1"]');
                } else {
                    var comp_list_unique = $('#comp-list li[data-classify="' + classify + '"][data-unique="1"]');
                }
            } else {
                var comp_list_unique = $('#comp-list li[data-id="' + id + '"][data-unique="1"]');
            }
            comp_list_unique.draggable('disable').css('cursor', 'not-allowed');
        } else {
            if (classify > 1) {
                var comp_list_unique = $('#comp-list li[data-classify="' + classify + '"][data-unique="1"]');
            } else {
                var comp_list_unique = $('#comp-list li[data-id="' + id + '"][data-unique="1"]');
            }
            comp_list_unique.draggable('enable').css('cursor', '');
        }
    }

    function thumbImgData(result_data) {
        result_data = result_data || {};
        html2canvas(document.getElementById('comp-main'), {
            background: '#fff',
            //width: 210,
            //height: 270,
            onrendered: function (canvas) {
                var imgData = canvas.toDataURL("image/png");
                _console.log('canvas.toDataURL=>', imgData);
                if (isSite()) {
                    var url = '/thumb/saveSiteThumb';
                    var id = !!siteDefaultId ? siteDefaultId : siteId;
                } else {
                    var url = 'thumb/saveTemplateThumb';
                    var id = templateId;
                }
                $.post(url, {imageStr: imgData, id: id}, function (data) {
                    _console.log('thumbImgData=>', data);
                    if (result_data.result == 'OK' && data.result == 'OK') {
                        jDialog.alert('提示', result_data.message, wclose);
                        setTimeout('wclose()', 2500);
                    } else {
                        jDialog.alert('提示', data.message, wclose);
                    }
                }, 'json');
            }
        });
    }

    /**
     * 返回对象方法
     */
    return {
        init: init,
        addHeader: addHeader,
        createReact: createReact,
        isSite: isSite,

        // 页面page操作相关
        getPageDoing: getPageDoing,
        setPageDoing: setPageDoing,
        addPageId: addPageId,
        pageAdd: pageAdd,
        pageDel: pageDel,
        pageTypeName: pageTypeName,
        getPageMap: getPageMap,
        getPageEntityData: getPageEntityData,

        // 组件component操作相关
        getComponentJsonItem: getComponentJsonItem,
        setComponentJsonItem: setComponentJsonItem,
        getComponentJson: getComponentJsons,

        // 组件实体entity操作相关
        getEntityDoing: getEntityDoing,
        setEntityDoing: setEntityDoing,
        addEntityId: addEntityId,
        getEntityLists: getEntityLists,
        addEntityData: addEntityData,
        getEntityData: getEntityData,
        setEntityData: setEntityData,
        editPageEntityList: editPageEntityList,
        entitySortableSort: entitySortableSort,
        entitySort: entitySort,
        entityUnique: entityUnique,
        // 遮照层
        layerCreate: layerCreate,
        layerRemove: layerRemove,

        // API相关
        getTemplateInfo: getTemplateInfo,
        apiPageComponentList: apiPageComponentList,
        apiSave: apiSave,
        thumbImgData: thumbImgData
    }
};

/**
 * 页面其他属性设置
 */
var PropertyClass = function () {
    // checkbox元素
    var canEdit = $('#canEdit'),
        canChildEdit = $('#canChildEdit'),
        entity_checkbox = $('#entity-checkbox');

    // 初始化
    function init(entity) {
        //canEdit.prop('checked', entity.canEdit == '1' ? true : false);
        canChildEdit.prop('checked', entity.canChildEdit == '1' ? true : false);
        events();
        // 显示checkbox
        entity_checkbox.show();
    }

    /**
     * 组件属性事件组
     */
    function events() {
        //canEdit.change(function () {
        //    var compId = compClass.getEntityDoing(),
        //        val = $(this).is(':checked') ? '1' : '-1';
        //    compClass.setEntityData(compId, 'canEdit', val);
        //});

        canChildEdit.change(function () {
            var compId = compClass.getEntityDoing(),
                val = $(this).is(':checked') ? '1' : '-1';
            compClass.setEntityData(compId, 'canChildEdit', val);
        });
    }

    return {
        init: init
    }
};

/**
 * 关闭当前窗口并刷新父窗口
 */
function wclose() {
    try {
        window.opener.location.reload();
        window.opener = null;
        window.close();
    } catch (e) {
        // 刷新当前页面，防止添加组件内容提交多次
        location.reload();
        //jDialog.alert('提示', '尝试关闭该页面失败，请手动关闭哦');
        _console.log(e);
        return true;
    }
}

/**
 * 创建组件类
 */
var compClass = ComponentClass();
compClass.init();

var propClass = PropertyClass();

function entityFault(el) {
    var entity_htm_fault = '<div class="entity-cover"></div><div class="entity-del-fault"></div><p class="entity-fault">该组件存在内部错误，请删除</p></div>';
    el.css({'min-height': 'inherit'})
        .html(entity_htm_fault);
    $('#entity-checkbox').hide();
    $('#comp-set').empty();
}

function entityInitFault() {
    var entity_htm_fault = '<li style="min-height:inherit"><div class="entity-cover"></div><div class="entity-del-fault"></div><p class="entity-fault">该组件存在内部错误，请删除</p></div></li>';
    $('#comp-main').append(entity_htm_fault);
}

/**
 * 组件拖拉事件
 */
function componentSortable(pageId) {
    // 重置样式和拖动
    $('#comp-list li.ui-draggable-disabled').draggable('enable').css('cursor', '');

    $("#comp-main").sortable({
        placeholder: 'cpt-placeholder', // 占位Dom样式，默认空白
        containment: '#comp-main', // 约束排序动作只能在一个指定的范围内发生。可选值：DOM对象, 'parent', 'document', 'window', 或jQuery对象
        forceHelperSize: true,
        revert: false, // 设置成true，则被拖拽的元素在返回新位置时，会有一个动画效果
        delay: 150, // 以毫秒为单位，设置延迟多久才激活排序动作。此参数可防止误点击
        stop: function (e, ui) {
            var el = ui.item;
            // 判断是否选择页面
            var page_id = compClass.getPageDoing();
            if (!page_id) {
                el.remove();
                jDialog.alert('提示', '请先选择页面，再拖动所需组件');
                return;
            }
            var comp_id = compClass.addEntityId();
            compClass.setEntityDoing(comp_id);

            // 已拖拉生成的组件实体，不进行重绘操作
            if (el.attr('data-cid')) {
                // 重新计算排序
                compClass.entitySortableSort();
                return;
            }
            el.attr('class', 'ui-sortable-handle').removeAttr('style');
            var componentJson = compClass.getComponentJsonItem(el.attr('data-id'));
            // 引用资源文件
            try {
                compClass.addHeader(componentJson);
            } catch (e) {
                _console.log('[fault]compClass.addHeader=>', e);
                entityFault(el);
                return;
            }
            try {
                // 创建组件、设置React
                compClass.createReact(el, comp_id, componentJson);
            } catch (e) {
                _console.log('[fault]compClass.createReact=>', e);
                entityFault(el);
                return;
            }
            // 如果出错不创建组件实体对象
            compClass.addEntityData({componentId: el.attr('data-id')});
            // 重新计算排序
            compClass.entitySortableSort();
            // 判断当前组件是否唯一
            if (el.attr('data-unique') == 1) {
                compClass.entityUnique(componentJson.classify, componentJson.id);
            }
            window.hasEdit += 1;
            bindUnload();
        }
    });

    // 组件类型选中并拖动
    $('#comp-list li').draggable({
        connectToSortable: '#comp-main', // 允许draggable被拖拽到指定的sortables中，如果使用此选项helper属性必须设置成clone才能正常工作
        scroll: false, // 如果设置为true，元素拖拽至边缘时，父容器将自动滚动
        revert: false, // 当元素拖拽结束后，元素回到原来的位置
        // 拖拽元素时的显示方式（如果是函数，必须返回值是一个DOM元素）可选值：'original', 'clone', Function
        helper: function (e) {
            return $(this).clone().prependTo('#comp-list');
        },
        cursor: 'move', // 指定在做拖拽动作时，鼠标的CSS样式
        // 当鼠标开始拖拽时，触发此事件
        start: function(e, ui) {
            // ui.helper 表示被拖拽的元素的JQuery对象
            ui.helper.addClass('cpt-dragging');
        }
    });

    // 禁用选择匹配的元素集合内的文本内容
    // $('#comp-main, #comp-list li').disableSelection();

    var pageEntitys = compClass.editPageEntityList(pageId, false);
    // 遍历页面所有组件中是否含有唯一组件
    for (var i=0,len=pageEntitys.length; i<len; i++) {
        var item = pageEntitys[i];
        var compJson = compClass.getComponentJsonItem(item.componentId);
        if (compJson.canUnique == 1 && item.componentOperation != 'remove') {
            compClass.entityUnique(compJson.classify, compJson.id);
        }
    }
}

// 编辑组件实体委托事件
$('.entities').on('click', 'li .entity-cover', function(e) {
    window.hasEdit += 1;
    bindUnload();

    var that = $(this).parents('li'),
        entity_name = that.attr('data-cid'),
        setting_name = entity_name+'s';
    that.addClass('active').siblings('.active').removeClass('active');
    if (that.find('.entity-del-fault').size() > 0) {
        $('#comp-set').empty();
        $('#entity-checkbox').hide();
        return ;
    }
    var componentJson = compClass.getComponentJsonItem(that.attr('data-id'));
    //var componentJson = get_component_param(that.attr('data-path'), that.attr('data-code'), that.attr('data-set-code'));
    _console.log('编辑组件componentJson=>', componentJson);
    // 更新当前操作组件
    compClass.setEntityDoing(entity_name);
    var entity = compClass.getEntityData(entity_name);
    _console.log('编辑组件结果componentJson=>', entity);
    // 当前组件不能编辑
    if (compClass.isSite() && entity.canEdit == '1') {
        $('#comp-set').html('<div class="cannotEdit">没有编辑组件的权限</div>');
        $('#entity-checkbox').hide();
    } else {
        // 设置checkbox
        propClass.init(entity);
        // 渲染 settingReact
        try {
            $('#comp-set').html('<script>var ' + setting_name + ' = ' + componentJson.setCode + '(' + entity_name + ', "comp-set"); ' + setting_name + '.render();</script>');
        } catch (e) {
            _console.log('编辑组件实体SettingReact异常=>', e);
        }
    }
});

// 删除组件实体委托事件
$('.entities').on('click', 'li .entity-del', function(e){
    window.hasEdit += 1;
    bindUnload();

    var el = $(this).parents('li'),
        cid = el.attr('data-cid'),
        sid = cid+'s';
    // 阻止冒泡
    e.stopPropagation();
    el.remove();
    // 删除settingReact对象
    delete window[sid];
    $('#comp-set').empty();
    // 设置组件实体操作为删除
    compClass.setEntityData(cid, 'componentOperation', 'remove');
    var compJson = compClass.getComponentJsonItem(el.attr('data-id'));
    // 判断当前组件是否唯一
    if (el.attr('data-unique') == 1) {
        compClass.entityUnique(compJson.classify, compJson.id, 'enable');
    }
    $('#entity-checkbox').hide();
});

// 删除错误组件实体委托事件
$('.entities').on('click', 'li .entity-del-fault', function(e){
    var el = $(this).parents('li');
    // 阻止冒泡
    e.stopPropagation();
    el.remove();
});

// 新增页面事件
$('.page-add').click(function () {
    var that = $(this),
        page_type = that.attr('data-page-type');
    var page_id = compClass.addPageId();
    // 新增页面
    compClass.pageAdd(page_type, page_id);
});

// 删除页面事件
$(document).on('click', '.page-del', function () {
    var that = $(this),
        page_id = that.attr('data-page-id'),
        page_type = that.attr('data-page-type'),
        page_item = that.parent('.page-item'),
        page_div = that.parents('.page'),
        page_items = page_div.find('.page-item');
    page_item.remove();
    page_div.find('.page-item').each(function (k, v) {
        $(this).find('span a').text('引导第'+(++k)+'页');
    });
    // 删除页面
    compClass.pageDel(page_type, page_id);
    // 清空组件、配置区域
    $('#comp-main').empty();
    $('#comp-set').empty();
    $('#entity-checkbox').hide();
});

// 当前操作页面中
$(document).on('click', '.page-title', function () {
    var that = $(this),
        page_type = that.attr('data-page-type'),
        pageId = that.attr('data-page-id');
    compClass.setPageDoing(pageId);
    // 获取页面类型的组件列表
    compClass.apiPageComponentList(page_type, pageId);
    // 清空组件、配置区域
    $('#comp-main').empty();
    $('#comp-set').empty();
    // 获取页面在浏览器中保存或接口返回的组件列表
    var pageEntitys = compClass.editPageEntityList(pageId, true);
    // 提示当前操作页面
    $('#page-doing').text('当前正在操作：'+that.text());
    $('#entity-checkbox').hide();

    // 当前页面样式
    $('#page_wrap .page-active').removeClass('page-active');
    if (/[13]/.test(page_type.toString())) {
        that.parents('.page-item').addClass('page-active');
    } else {
        that.parents('.page-type').addClass('page-active');
    }
});

/**
 * 绑定页面离开事件 jquery离开页面弹出提示代码
 */
function bindUnload() {
    // 判断用户是否手动编辑过内容
    if (window.hasEdit == 1 && location.pathname.indexOf('test.html') == -1) {
        $(window).bind('beforeunload', function () {
            return '您输入的内容尚未保存，确定离开此页面吗？';
        });
    }
}

// 保存数据事件
$('.btn-save').click(function () {
    $(window).unbind('beforeunload');
    ////just for test haoxu
    ///**
    // * 获取URL参数
    // * @returns {{}}
    // */
    //function getArgs() {
    //    var args = {};
    //    var match = null;
    //    var search = location.search.substring(1);
    //    var reg = /(?:([^&]+)=([^&]+))/g;
    //    while((match = reg.exec(search))!==null){
    //        args[match[1]] = match[2];
    //    }
    //    return args;
    //}
    //var get_args = getArgs(),
    //    siteId = get_args['siteId'], // 站点id
    //    accessToken = get_args['access_token']; //获取access_token
    //window.location.href="/static/html/review.html?siteId="+siteId+"&access_token=" +accessToken;
    compClass.apiSave();
});

// 该方法仅限Setting区域使用
var awifiUtils = {
    alert_tips: function (id, content) {
        var htm = '<div class="awifi_alert awifi_alert_warn">'+
            '<span class="awifi_alert_message">'+content+'</span>'+
            '<a class="awifi_alert_close-icon"><i class="icon_cross"></i></a></div>';
        $('#' + id).append(htm);
        setTimeout(function () {
            $('#' + id).find('.awifi_alert').remove();
        }, 2500);
    },

    /**
     * @param id DivID名称
     * @param reg 正则
     * @param reg_empty_msg 为空提示内容
     * @param reg_fault_msg 正则不匹配提示内容
     * @param default_value 默认值
     * @param default_value_msg 等于默认值提示内容
     * @returns {boolean}
     */
    verify_field: function (id, reg, reg_empty_msg, reg_fault_msg, default_value, default_value_msg) {
        reg = reg || /^\S+$/gm; // 默认非空通过
        reg_empty_msg = reg_empty_msg || '字段不能为空';
        reg_fault_msg = reg_fault_msg || '字段不合法';
        default_value = default_value || '';
        default_value_msg = default_value_msg || '请修改内容';
        var id_val = $.trim($('#' + id).val());
        if (id_val.length == 0) {
            this.alert_tips('comp-setting', reg_empty_msg);
            return true;
        }
        if (default_value && id_val == default_value) {
            this.alert_tips('comp-setting', default_value_msg);
            return true;
        }
        if (!reg.test(id_val)) {
            this.alert_tips('comp-setting', reg_fault_msg);
            return true;
        }
        return false;
    }
};

// 打开预览效果
function previewOpen() {
    // 显示背景层
    compClass.layerCreate('hidden');

    // 创建预览组件
    function createReactEntityPreview(data) {
        var componentId = data.componentId;
        var compJson = compClass.getComponentJsonItem(data.componentId),
            comp_id = data.comp_id;
        // 站点没有删除，模板有删除
        var htm = '<li data-id="'+componentId+'" data-code="'+compJson.code+'" data-set-code="'+compJson.setCode+'" data-cid="'+comp_id+'2" style="min-height:auto;">'+
            '<div id="'+comp_id+'2"></div>'+
            '<script type="text/javascript">var '+comp_id+'2 = '+compJson.code+'('+JSON.stringify(data.json)+', "'+comp_id+'2").render();</script></li>';
        _console.log('创建ReactJS createReactEntity=>', htm);
        $('#comp-main-preview').append(htm);
    }

    // 当前页组件
    var pageId = compClass.getPageDoing();
    var entitys = compClass.getPageEntityData(pageId, true);
    console.log(entitys);
    for (var i=0,len=entitys.length; i<len; i++) {
        var item = entitys[i];
        // 第一次组件要执行setState()
        if (item.componentOperation != 'remove') {
            createReactEntityPreview(item);
        }
    }

    // 显示预览模拟框
    $('#comp-main-wrap-preview').fadeIn();
    $('#comp-preview-open').fadeOut();
}

// 关闭预览功能
function previewClose() {
    // 清空预览模拟框内容
    $('#comp-main-preview').html('');
    // 隐藏预览模拟框
    $('#comp-main-wrap-preview').fadeOut();
    $('#comp-preview-open').fadeIn();
    // 删除背景层
    compClass.layerRemove();
}

//切换站点信息和portal制作
$('#portalInfo').on('click', function(e){
    $("#portalInfo").addClass("active");
    $("#portalMaking").removeClass("active");
    $("#site-info").show();
    $("#menu").hide();
});
$('#portalMaking').on('click', function(e){
    $("#portalInfo").removeClass("active");
    $("#portalMaking").addClass("active");
    $("#site-info").hide();
    $("#menu").show();
});
//省市区接口
function getProvince(accessToken){
    var url ="/pubsrv/provinces?access_token="+accessToken;
    $.ajax({
        url: url,
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (resp) {
            if (resp.code == '0') {
                setOptionLists(resp.data, $('#province'),1);
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
}

function setCity(){
    console.log("344444444");
    var get_args = getArgs(),
        accessToken = get_args['access_token']; //获取access_token
    var provinceId = $("#province").val();
    var url ="/pubsrv/cities?access_token="+accessToken;
    $.ajax({
        url: url,
        type: 'get',
        data: {"parentid": provinceId},
        dataType: 'json',
        success: function (resp) {
            if (resp.code == '0') {
                setOptionLists(resp.data,$('#city'),0);
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
}
/**
 * 获取URL参数
 * @returns {{}}
 */
function getArgs() {
    var args = {};
    var match = null;
    var search = location.search.substring(1);
    var reg = /(?:([^&]+)=([^&]+))/g;
    while((match = reg.exec(search))!==null){
        args[match[1]] = match[2];
    }
    return args;
}
function setOptionLists(data,$obj, flag){
    if(flag){
        $obj.html(" <option value=''>省</option>");
    }else{
        $obj.html(" <option value=''>市</option>");
    }
    var htm = '';
    for(var i=0; i<data.length;i++){
        var item = data[i];
        htm = htm + '<option value='+item.id+'>'+item.areaName+'</option>';
    }
    $obj.append(htm);
}


