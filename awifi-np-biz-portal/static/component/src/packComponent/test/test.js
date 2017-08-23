/**
 * 说明：
 *
 * 首页：index-1、 配置：index-2、 统计：index-3、 管理：index-4、 园区代付：index-5、 企业管理员：index-6、 新增企业管理员：index-7、 登录：index-8、 个人中心：index-9、 帮助文档：index-10
 *
 *
 */

/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {

    /**
     * 商户滚动图片
     * timebuysrv/merchant/pic
     * @type {string}
     */
    var pic_list_url = '/timebuysrv/merchant/pic/list/'; //根据商户id获取商户滚动图片列表
    var pic_url = '/timebuysrv/merchant/pic/';

    /**
     * 商户滚动消息
     * timebuysrv/merchant/notice
     * @type {string}
     */
    var notice_list_url = '/timebuysrv/merchant/notice/list/'; //根据商户id获取商户滚动消息列表
    var notice_url ='/timebuysrv/merchant/notice/';

    /**
     * 商户信息
     * timebuysrv/merchant/news
     * @type {string}
     */
    var news_list_url = '/timebuysrv/merchant/news/list/'; //根据商户id获取商户动态信息列表
    var news_url = '/timebuysrv/merchant/news/';


    var _state = state || {

            index_show:'index-1',
            navname:'xxx园区',
            content: '请输入文本内容',
            openpage:'',
            pageList:['index-1', 'index-2', 'index-3', 'index-4', 'index-5', 'index-6', 'index-7', 'index-8', 'index-9', 'index-10'],
            merid:'1', //商户id

            //园区介绍
            config_id:'',
            config_note:'',
            config_scorll:'',

            //园区动态
            dynamicadd_list:[],

        };

    var EntityReact = React.createClass({
        getInitialState: function () {

            return _state;
        },
        componentDidMount: function(){
            // alert('componentDidMount');
            $.each(_state.pageList, function(index){
                if(index == '0') {
                    $('.' + this).show();
                }else {
                    $('.' + this).hide();
                }
            });
        },

        routerCallBack: function (tabTag) {
            // alert('routerCallBack~~~~~' + tabTag);
            var _this = this;
            $.each(_state.pageList, function(index){
                // alert(this);
                if(this == tabTag) {
                    $('.' + this).show();

                    _state.index_show = tabTag;
                    _this.setState(_state);
                    console.log(_state);
                }else {
                    $('.' + this).hide();
                }
            });
        },
        configSaveClick: function () {
            console.log(_state);

            $.ajax({
                type:'PUT',
                url:news_url + _state.config_id,
                data:{id:_state.config_id, content:_state.config_note, merid:_state.merid},
                success:function (data) {
                    console.log(data);
                },
                error:function () {

                }
            });

            $.each(_state.dynamicadd_list, function (i, item) {
                $.ajax({
                    type:'POST',
                    url:notice_url,
                    data:{slot:i, content:item, merid:_state.merid},
                    success:function (data) {
                        console.log(data);
                    },
                    error:function () {

                    }
                });
            });


            this.setState(_state);
        },
        render: function () {
            return (
            <div className="_Entity_">
                {this.state.index_show == 'index-1' ?
                    <div className="index-1">
                        <NavBarComponent navname={this.state.navname} nav_callback={this.routerCallBack} />
                        <ScorllBarComponent scrollname={this.state.content}/>
                        <SwiperComponent />
                        <ConnectionComponent />
                        <NoteComponent />
                        <HelpComponent push_callback={this.routerCallBack}/>
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div> : ''}
                {this.state.index_show == 'index-2' ?
                    <div className="index-2">
                        <BackBarComponent backbar_title="配置" backbar_callback={this.routerCallBack} backbar_tag="index-1"/>
                        <AdAddComponent />
                        <NoteAddComponent />
                        <DynamicAddComponent />
                        <div className="index-2-save">
                            <button className="save" onClick={this.configSaveClick}>保存</button>
                        </div>
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div> : ''}
                {this.state.index_show == 'index-3' ?
                    <div className="index-3">
                        <BackBarComponent backbar_title="统计" backbar_callback={this.routerCallBack} backbar_tag="index-1"/>
                        <TotalAndAverageComponent />
                        <UserStatisticsComponent />
                        <UserDealDetailComponent />
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div> : ''}
                {this.state.index_show == 'index-4' ?
                    <div className="index-4">
                        <BackBarComponent backbar_title="管理" backbar_callback={this.routerCallBack} backbar_tag="index-1"/>
                        <AdminAndPayComponent push_callback={this.routerCallBack}/>
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div> : ''}
                {this.state.index_show == 'index-5' ?
                    <div className="index-5">
                        <BackBarComponent backbar_title="园区代付" backbar_callback={this.routerCallBack} backbar_tag="index-4"/>
                        <PayForAnotherComponent />
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div>
                    : ''}
                {this.state.index_show == 'index-6' ?
                    <div className="index-6">
                        <BackBarComponent backbar_title="企业管理员" backbar_callback={this.routerCallBack} backbar_tag="index-4" right_title="新增" right_tag="index-7" right_callback={this.routerCallBack}/>
                        <AdminListComponent />
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div>: ''}
                {this.state.index_show == 'index-7' ?
                    <div className="index-7">
                        <BackBarComponent backbar_title="新增" backbar_callback={this.routerCallBack} backbar_tag="index-6"/>
                        <AdminAddComponent />
                        <TabBarComponent tabbar_callback={this.routerCallBack}/>
                    </div>: ''}
                {this.state.index_show == 'index-8' ?
                    <div className="index-8">
                        <BackBarComponent backbar_title="登录" backbar_callback={this.routerCallBack} backbar_tag="index-1"/>
                        <LoginComponent />
                    </div>: ''}
                {this.state.index_show == 'index-9' ?
                    <div className="index-9">
                        <BackBarComponent backbar_title="个人中心" backbar_callback={this.routerCallBack} backbar_tag="index-1"/>
                        <UserCenterComponent />
                        <MealDetailComponent />
                        <UserMealListComponent />
                    </div>: ''}
                {this.state.index_show == 'index-10' ?
                    <div className="index-10">
                        <BackBarComponent backbar_title="帮助" backbar_callback={this.routerCallBack} backbar_tag="index-1"/>
                        <HelpDetailComponent />
                    </div>: ''}
            </div>
            );
        }
    });

    /**
     *  顶部导航栏组件
     */
    var NavBarComponent = React.createClass({
        getInitialState: function () {
            return null;
        },
        componentDidMount: function(){

        },
        navBarCallBack: function (tag) {
            this.props.nav_callback(tag);
        },
        render: function () {
            return (
                <div className="nav_bar">
                    <div className="left" onClick={this.navBarCallBack.bind(this, 'index-8')}><img src="http://msp-img.51awifi.com/V1/img/header/wifi.png"></img></div>
                    <span>{this.props.navname}</span>
                    <div className="right" onClick={this.navBarCallBack.bind(this, 'index-9')}><img src="http://msp-img.51awifi.com/V1/img/header/information.png"></img></div>
                </div>
            )
        }
    });

    /**
     *  消息滚动组件
     */
    var ScorllBarComponent = React.createClass({
        componentDidMount: function () {
            $.ajax({
                type:'GET',
                url:notice_list_url + _state.merid,
                data:{},
                success: function (data) {
                    console.log(data);
                    var arr = new Array();
                    $.each(data.data, function (i, item) {
                        arr.push(item.content);
                    });

                    var content = arr.join("&nbsp;&nbsp;&nbsp;");
                    var marquee = "<marquee style='padding-top: 2px' direction='left' behavior='scorll' scrollamount='10' scrolldelay='100' loop='-1' bgcolor=''>" + content + "</marquee>"
                    $(".marquee").html(marquee);

                },
                error: function () {

                }
            })
        },
        render: function () {
            return (
            <div className="scorll_bar">
                <div className="marquee"></div>
            </div>
            )
        }
    });

    /**
     *  图片轮播组件
     * @type {*}
     */
    var SwiperComponent = React.createClass({
        getInitialState: function () {
            return null;
        },
        componentDidMount: function(){
            $.ajax({
                type:'GET',
                url:pic_list_url + _state.merid,
                data:{},
                success:function (data) {
                    console.log(data);

                    $.each(data.data, function (i, item) {
                        var li;
                        if(i == 0) {
                            li = "<li data-target='#myCarousel' data-slide-to=\"" + i +"\" class='active' style='min-height:10px; border-color:white'></li>"
                        }else {
                            li = "<li data-target='#myCarousel' data-slide-to=\"" + i +"\" style='min-height:10px; border-color:white'></li>"
                        }
                        $(".carousel-indicators").append(li);

                        var img;
                        if(i == 0) {
                            img = "<div class='item active' style='height: 200px'><img src=\""+ item.path +"\" alt=\"" + i +"\"></div>"
                        }else {
                            img = "<div class='item' style='height: 200px'><img src=\""+ item.path +"\" alt=\"" + i +"\"></div>"
                        }
                        $(".carousel-inner").append(img);


                    });
                },
                error:function () {

                }
            });

            $("#myCarousel").carousel('cycle');
        },
        render: function () {
            return(
            <div id="myCarousel" className="carousel slide">
                <ol className="carousel-indicators">
                    {/*<li data-target="#myCarousel" data-slide-to="0" className="active" style={{minHeight:'10px', borderColor:'white'}}></li>*/}
                    {/*<li data-target="#myCarousel" data-slide-to="1" style={{minHeight:'10px', borderColor:'white'}}></li>*/}
                    {/*<li data-target="#myCarousel" data-slide-to="2" style={{minHeight:'10px', borderColor:'white'}}></li>*/}
                </ol>
                <div className="carousel-inner">
                    {/*<div className="item active">*/}
                        {/*<img src="../../../../images/slider/1.jpg" alt="First slide"></img>*/}
                    {/*</div>*/}
                    {/*<div className="item">*/}
                        {/*<img src="../../../../images/slider/2.jpg" alt="Second slide"></img>*/}
                    {/*</div>*/}
                    {/*<div className="item">*/}
                        {/*<img src="../../../../images/slider/3.jpg" alt="Third slide"></img>*/}
                    {/*</div>*/}
                </div>
            </div>
            )
        }
    });

    /**
     * 请求上网组件
     * @type {*}
     */
    var ConnectionComponent = React.createClass({
        render: function () {
            return(
                <div className="connection">
                    <div className="img">
                        <img src="http://msp-img.51awifi.com/V1/img/index/shalouon.png"></img>
                    </div>
                    <div className="title">
                        <div>上网上网上网上网上网上网上网上网上网</div>
                    </div>
                    <div className="buybtn">
                        <button id="buy" type="button">时长购买</button>
                    </div>
                </div>
            )
        }
    });

    /**
     * 园区介绍组件
     * @type {*}
     */
    var  NoteComponent = React.createClass({
        componentDidMount:function () {
            $.ajax({
                type:'GET',
                url:news_list_url + _state.merid,
                data:{},
                success: function (data) {
                    console.log(data);
                    $(".infotext span").html(data.data[0].content);
                    _state.config_id = data.data[0].id;
                    _state.config_note = data.data[0].content;
                },
                error:function () {

                }
            })
        },
        render: function () {
            return(
                <div className="note">
                    <div className="title">
                        <span>园区介绍</span>
                    </div>
                    <div className="infotext">
                        <span>{_state.config_note}</span>
                    </div>
                </div>
            )
        }
    });

    /**
     * 帮助文档组件
     * @type {*}
     */
    var HelpComponent =React.createClass({
        callBack: function (tag) {
            this.props.push_callback(tag);
        },
        render: function () {
            return(
                <div className="help">
                    <div className="title">
                        <span>帮助文档</span>
                    </div>
                    <div className="helptext">
                        <a onClick={this.callBack.bind(this, 'index-10')}>帮助文档</a>
                    </div>
                </div>
            )
        }
    });

    /**
     * 底部栏组件
     */
    var TabBarComponent = React.createClass({
        getInitialState: function () {
            // alert('TabBar-getInitialState');
            return null;
        },

        tabClick: function (tag) {
            // alert(tag);
            this.props.tabbar_callback(tag);

        },

        render: function () {
            return (
                <div className="tab_bar fix-bottom">
                    <div className="tabbar-content" onClick={this.tabClick.bind(this, 'index-1')}>
                        <div className="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/1.png"></img></div>
                        <div className="nav-title">首页</div>
                    </div>
                    <div className="tabbar-content" onClick={this.tabClick.bind(this, 'index-2')}>
                        <div className="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/2.png"></img></div>
                        <div className="nav-title">配置</div>
                    </div>
                    <div className="tabbar-content" onClick={this.tabClick.bind(this, 'index-3')}>
                        <div className="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/3.png"></img></div>
                        <div className="nav-title">统计</div>
                    </div>
                    <div className="tabbar-content" onClick={this.tabClick.bind(this, 'index-4')}>
                        <div className="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/3.png"></img></div>
                        <div className="nav-title">管理</div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 配置页面添加广告位组件
     */
    var AdAddComponent = React.createClass({
        componentDidMount: function () {
            var nowImg = null;
            $(".img-upload").click(function() {
                nowImg = this;
                $(this).parent().find("input").trigger("click");
            });

            var adcount = 0;
            var total_pic_count = 3;
            $("#inputfile").change(function () {
                var formData = new FormData();
                console.log($('#inputfile')[0].files[0]);
                formData.append('file', $('#inputfile')[0].files[0]);
                $.ajax({
                    type:'POST',
                    url:'/timebuysrv/image/submit',
                    data:formData,
                    cache: false,
                    contentType: false,    //不可缺
                    processData: false,    //不可缺
                    success:function (data) {
                        console.log(data);

                        data=eval("("+data+")"); //转成json输出
                        var img_src = "/" + data.data;

                        if(adcount < total_pic_count) {
                            $("#selectadd").before("<div class='info'><div class='info-input'><img class='image' src=\"" + img_src + "\"></div></div>");
                            if(adcount == total_pic_count -1) {
                                $("#selectadd").hide();
                            }
                            adcount += 1;
                        }
                    },
                    error:function (data) {

                    }
                });
            });
        },

        render: function () {
            return(
                <div className="adadd">
                    <div className="title">
                        <span>广告位添加</span>
                    </div>
                    <div className="info" id="selectadd">
                        <div className="info-input" >
                            <input type="file" style={{display:'none'}} id="inputfile"></input>
                            <img className="image img-upload" src="http://msp-img.51awifi.com/V1/img/carousel/add.png"></img>
                        </div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 园区介绍添加组件
     */
    var NoteAddComponent = React.createClass({
        componentDidMount: function () {
            /**
             * 字数限制
             */
            $("#introduce").on("keyup", function() {
                limitText($(this), 150, $("#introLimit"));
            });
            $("#introduce").on("keydown", function() {
                limitText($(this), 150, $("#introLimit"));
            });
            $("#introduce").on("change", function() {
                limitText($(this), 150, $("#introLimit"));
            });
        },
        handleNoteChange: function(event) {
            _state.config_note = event.target.value;
        },
        render:function () {
            return(
                <div className="noteadd">
                    <div className="title">
                        <span>园区介绍</span>
                    </div>
                    <div className="info">
                        <textarea type="text" maxLength="150" id="introduce" onChange={this.handleNoteChange}></textarea>
                        <div className="introLimit" id="introLimit">150</div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 园区动态添加组件
     */
    var DynamicAddComponent = React.createClass({
        dynamic_text:'',
        list:[],
        getInitialState: function () {

            return null;
        },
        componentDidMount: function () {
            var _this = this;
            /**
             * 字数限制
             */
            $("#activeMsg").on("keyup", function() {
                limitText($(this), 50, $("#activeLimit"));
            });
            $("#activeMsg").on("keydown", function() {
                limitText($(this), 50, $("#activeLimit"));
            });
            $("#activeMsg").on("change", function() {
                limitText($(this), 50, $("#activeLimit"));
            });

            //因.delActive为新增，需取到document这一层级来监听所有的.delActive
            $(document).on("click", ".delActive", function () {
                var delIndex = $(this).attr('value');
                _this.list.splice(delIndex,1);

                _this.updateActiveList(_this.list);
            });

        },
        dynamicChange: function (event) {
            this.dynamic_text = event.target.value;
        },
        saveAndAddClick:function () {
            this.list.push(this.dynamic_text);
            this.dynamic_text = '';
            $("#activeMsg").val('');

            this.updateActiveList(this.list);

        },
        updateActiveList: function (list) {
            var html ='';
            $.each(list, function (i, item) {
                // alert(item);
                html += "<div class='activeList-content' name='activeDiv'>" +
                    "<div class='content-title'><div class='title-name'>" + item +"</div>" +
                    "<a style='color:#2C8FFF; float:right;' class='delActive' value="+ i +">删除</a>" +
                    "</div>" +
                    "</div>" +
                    "<div class='bottomline'></div>"
            });

            $(".activeList").html(html);

            // alert(list);
            _state.dynamicadd_list = list;
        },
        render:function () {
            return(
                <div className="dynamicadd">
                    <div className="title">
                        <span>园区动态</span>
                    </div>
                    <div className="info">
                        <textarea type="text" maxLength="50" id="activeMsg" onChange={this.dynamicChange}></textarea>
                        <div className="activeLimit" id="activeLimit">50</div>
                    </div>
                    <div className="edit-add">
                        <div className="edit-inner-div">
                            <button id="addActive" className="edit-save" onClick={this.saveAndAddClick}>保存并新增</button>
                        </div>
                    </div>
                    <div className="activeList">
                        {/*<div className='activeList-content' name='activeDiv'>*/}
                            {/*<div className='content-title'>*/}
                                {/*<div className='title-name'>哈哈哈哈</div>*/}
                                    {/*<a style={{color:'#2C8FFF', float:'right'}} className='delActive'>删除</a>*/}
                                {/*</div>*/}
                            {/*</div>*/}
                        {/*<div className='bottomline'></div>*/}
                        {/*<div className='activeList-content' name='activeDiv'>*/}
                            {/*<div className='content-title'>*/}
                                {/*<div className='title-name'>哈哈哈哈</div>*/}
                                {/*<a style={{color:'#2C8FFF', float:'right'}} className='delActive'>删除</a>*/}
                            {/*</div>*/}
                        {/*</div>*/}
                        {/*<div className='bottomline'></div>*/}
                    </div>
                </div>
            )
        }
    });

    /**
     *  顶部返回组件
     */
    var BackBarComponent = React.createClass({
        getInitialState: function () {
            return null;
        },
        componentDidMount: function(){
            if($('.right').text()) {
                $('.right').show();
            }else {
                $('.right').hide();
            }
        },
        callBack: function () {
            var tag = this.props.backbar_tag;
            this.props.backbar_callback(tag);
        },
        rightCallBack: function () {
            var tag = this.props.right_tag;
            this.props.right_callback(tag);
        },
        render: function () {
            return (
                <div className="back_bar">
                    <div className="left" onClick={this.callBack}><img src="http://msp-img.51awifi.com/V1/img/header/back.png"></img></div>
                    <span>{this.props.backbar_title}</span>
                    <div className="right" onClick={this.rightCallBack}>{this.props.right_title}</div>
                </div>
            )
        }
    });

    /**
     * 园区统计金额组件
     */
    var TotalAndAverageComponent = React.createClass({
        render: function () {
            return(
                <div className="statistics-total">
                    <div className="total">
                        <div className="content">0</div>
                        <div className="title">充值总金额(元)</div>
                    </div>
                    <div className="average">
                        <div className="content">0.00</div>
                        <div className="title">平均充值金额(元)</div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 用户使用信息组件
     */
    var UserStatisticsComponent = React.createClass({
        render: function () {
            return(
                <div className="staistitcs-user">
                    <div className="visit">
                        <div className="title"></div>
                        <div className="content">
                            <div className="msg">当前付费用户</div>
                            <div className="total">0</div>
                        </div>
                    </div>
                    <div className="visit">
                        <div className="title"></div>
                        <div className="content">
                            <div className="msg">注册用户</div>
                            <div className="total">0</div>
                        </div>
                    </div>
                    <div className="cutline"></div>
                    <div className="visit">
                        <div className="content">
                            <div className="msg">付费用户</div>
                            <div className="total">0</div>
                        </div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 付费用户明细组件
     */
    var UserDealDetailComponent = React.createClass({
        render: function () {
            return(
                <div className="statistitcs-deal">
                    <div className="title">
                        <span>付费用户明细</span>
                    </div>
                    <div className="list">
                        <span>测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试</span>
                    </div>
                </div>
            )
        }
    });

    /**
     * 企业管理员、园区代付按钮组件
     */
    var AdminAndPayComponent = React.createClass({
        callBack: function (tag) {
            this.props.push_callback(tag);
        },
        render: function () {
            return(
                <div className="manage-admin-pay">
                    <div className="admin">
                        <button className="manage" type="button" onClick={this.callBack.bind(this, 'index-6')}>企业管理员</button>
                    </div>
                    <div className="admin">
                        <button className="manage" type="button" onClick={this.callBack.bind(this, 'index-5')}>园区代付</button>
                    </div>
                </div>
            )
        }
    });

    /**
     * 园区代付组件
     */
    var PayForAnotherComponent = React.createClass({
        render: function () {
            return(
                <div className="payforanohter">
                    <div className="payforanohter-content">
                        <div className="name">园区名称：</div>
                        <div className="merchant">xxxxxx</div>
                    </div>
                    <div className="payforanohter-content">
                        <div className="name">请输入手机号：</div>
                        <input type="text" placeholder="请输入手机号" maxLength="11"/>
                    </div>
                    <div className="payforanohter-save">
                        <button className="save">时长代购</button>
                    </div>
                </div>

            )
        }
    });

    /**
     * 企业管理员列表组件
     */
    var AdminListComponent = React.createClass({
        render: function () {
            return(
                <div className="adminlist">
                    <table className="adminlist-table">
                        <thead>
                            <tr style={{width:'100%'}}>
                                <td className="table-td">姓名</td>
                                <td className="table-td">手机号</td>
                                <td className="table-td">操作</td>
                            </tr>
                        </thead>
                        <tbody id="showEnterList"></tbody>
                    </table>
                </div>
            )
        }
    });

    /**
     * 企业管理员新增组件
     */
    var AdminAddComponent = React.createClass({
        render: function () {
            return(
                <div className="adminadd">
                    <div className="adminadd-content">
                        <input type="text" placeholder="请输入手机账号" maxLength="11"/>
                    </div>
                    <div className="adminadd-content">
                        <input type="text" placeholder="请输入姓名" maxLength="11"/>
                    </div>
                    <div className="index-2-save">
                        <button className="save">添加</button>
                    </div>
                </div>
            )
        }
    });

    /**
     * 登录组件
     */
    var LoginComponent = React.createClass({
        render: function () {
            return(
                <div className="login">
                    <div className="login-content">
                        <input className="login-telephone" type="text" placeholder="输入手机号码" maxLength="11"/>
                    </div>
                    <div className="login-content">
                        <input className="login-code" type="text" placeholder="输入验证码" maxLength="4"/>
                        <button className="login-get-code">获取验证码</button>
                    </div>
                    <div className="index-2-save">
                        <button className="save">登录</button>
                    </div>
                </div>
            )
        }
    });

    /**
     * 个人中心组件
     */
    var UserCenterComponent = React.createClass({
        render: function () {
            return(
                <div className="usercenter">
                    <div className="usercenter-edit">
                        <img className="edit-photo" src="http://msp-img.51awifi.com/V1/img/messageboard/avatar.png"/>
                        <div className="edit-user">个人资料</div>
                        <img className="edit-enter" src="http://msp-img.51awifi.com/V1/img/visit/next.png"/>
                    </div>
                </div>
            )
        }
    });

    /**
     * 套餐详情组件
     */
    var MealDetailComponent = React.createClass({
        render: function () {
            return(
                <div className="mealdetail">
                    <div className="title">
                        <span>套餐信息</span>
                    </div>
                    <div className="visit">
                        <div className="content">
                            <div className="msg">套餐：</div>
                            <div className="total">0</div>
                        </div>
                    </div>
                    <div className="cutline"></div>
                    <div className="visit">
                        <div className="content">
                            <div className="msg">有效期至：</div>
                            <div className="total">0</div>
                        </div>
                    </div>
                </div>

            )
        }
    });

    /**
     * 个人中心用户消费记录组件
     */
    var UserMealListComponent = React.createClass({
        render: function () {
            return(
                <div className="statistitcs-deal">
                    <div className="title">
                        <span>消费记录</span>
                    </div>
                    <div className="list">
                        <span>xxxxxxxxx</span>
                    </div>
                </div>
            )
        }
    });

    /**
     * 帮助文档详情组件
     */
    var HelpDetailComponent = React.createClass({
        componentDidMount: function () {

        },
        render: function () {
            return(
                <div className="common_wrapper">
                    <div className="common_header">
                        <h2 style={{textAlign:'center'}} className="common_title">
                            爱WiFi园区项目FAQ（V1.0）
                            {/*<span className="faq_button_wrapper"></span>*/}
                            {/*<i style={{left: '901.5px'}} id="triangle_up"></i>*/}
                            {/*<s style={{left: '900.5px'}} id="triangle_down"></s>*/}
                        </h2>
                    </div>
                    <div id="faq_page">
                        <div style={{display: 'block'}} className="iphone">

                            <div className="blackberry">
                                <div className="question_item">
                                    <h3 className="question">1.园区宿舍的爱WiFi网络是不是免费的？</h3>
                                    <div className="answer">答：不是。园区宿舍的爱WiFi网络是前向收费的，用户需购买时长才能使用。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">2.如何登录园区爱WiFi？</h3>
                                    <div className="answer">答：1. 打开手机Wi-Fi功能，连接所在园区对应的aWiFi热点，自动弹出园区首页。如不能自动弹出，请打开浏览器点击（或输入）任意网站。 2. 点击首页左上角［请连网］，进入认证页面，输入手机号，并获取短信验证码，点击登录。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">3.如何获取免费上网体验包？</h3>
                                    <div className="answer">答：首次登录园区aWiFi的用户，点击登录，待页面跳转后可直接在页面领取5天免费上网体验包。
                                        <br />
                                            <img className="col-sm-4" data="pic1.png" src="../image/faq/pic1.png"></img>
                                            <img className="col-sm-4" data="pic2.png" src="../image/faq/pic2.png"></img>
                                    </div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">4.免费体验时长到期后，如何购买WiFi时长？</h3>
                                    <div className="answer">答：登录后，点击页面“时长购买”按钮，进入购买页面选择套餐后点击“购买”按钮后进入支付页面进行支付。支付成功后，即可通过WiFi上网。<br />
                                        <img className="col-sm-8" data="pic3.png"></img>

                                    </div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">5.园区爱WiFi的时长套餐种类有哪几种？</h3>
                                    <div className="answer">答：1：付费套餐：时长套餐分“按天1.5元/天”、“按月30元/30天”、“按年330元/390天”3类，用户可根据自身需要选择购买。<br /> 2：免费套餐：电信59以上的乐享套餐的手机，可享受免费上网。请联系相应客户经理提前办理后方可享受。
                                    </div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">6.购买时长的时候支持哪些支付方式？</h3>
                                    <div className="answer">答：目前支持支付宝、银联、翼支付。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">7.反复弹出园区页面，左上角显示“已登录”，但无法上网。</h3>
                                    <div className="answer">答：该问题一般由浏览器缓存问题引起，需清除浏览器的缓存记录，重新打开浏览器登录可解决。<br /> 安卓手机清除缓存方法如下：<br />
                                        <img className="col-sm-8" data="pic4.png" src="../image/faq/pic4.png"></img>
                                        <br /> 苹果手机清除缓存方法如下：<br />
                                            <img className="col-sm-8" data="pic5.png" src="../image/faq/pic5.png"></img>
                                    </div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">8.看视频或者下载时，1个小时左右就会断线。</h3>
                                    <div className="answer">答：为确保网络安全高效使用，目前系统控制一次连接流量超过2G（包含上下行）自动断网，需要重新连接。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">9.点击“时长购买”之后，购买页面无法跳出。</h3>
                                    <div className="answer">答：可能是购买超时，请重新连接购买。如果还不可以购买，请提供URL，手机MAC，设备MAC提交电信相关人员处理。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">10.支付购买成功，但是没有时长，无法上网。</h3>
                                    <div className="answer">答：客户支付成功后银行到账确认时长1-10分钟，烦请耐心等待。仍有问题请提供您的购买付费成功短信通知等凭证给电信相关人员进行核实确认。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">11.已购买了园区上网时长，是否可以在同一个园区不同的aWiFi信号之间使用？</h3>
                                    <div className="answer">答：可以，但同一个账号同一时间只能一台终端在线。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">12.同一个账号可以用手机和电脑同时登录吗？</h3>
                                    <div className="answer">答：不可以，同一个账号同一时间只能一台终端在线。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">13.OPPO等部分手机，通过支付宝购买，点击立即购买，一直在跳转中，不能跳转到支付宝。</h3>
                                    <div className="answer">答：该问题为手机兼容性问题。暂时通过电脑或者其他手机进行购买，用同一个手机号在电脑上购买后，可直接在手机上登录使用。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">14.用户已购买园区上网时长，连接同一园区其他房间aWiFi信号，弹出的portal，提示无时长需要购买。</h3>
                                    <div className="answer">答：目前爱WiFi信号切换时，会弹出Portal需要重新登录才可上网，如未登录则显示无时长。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">15.点击“立即购买”后，网页跳转，提示“商品参数不完整”，无法购买。</h3>
                                    <div className="answer">
                                        <img className="col-sm-4" data="pic6.png" src="../image/faq/pic6.png"></img>
                                        <img className="col-sm-4" data="pic7.png" src="../image/faq/pic7.png"></img>
                                        <p>答：该问题是手机兼容性问题，请回退后重新勾选“购买时长”和“购买数量”，再点击 “立即购买”即可。</p>
                                    </div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">16.连接aWiFi信号后，没有自动弹出portal，不能上网。</h3>
                                    <div className="answer">答：因手机兼容性问题，部分手机无法自动弹出园区portal页面，请打开浏览器点击（或输入）任意网站，即可跳转至portal页面。</div>
                                </div>
                                <div className="question_item">
                                    <h3 className="question">17.由于苹果手机自动检索最强无线信号，用户在使用过程中经常自动跳转到其他网络的信号，从而引起断网。</h3>
                                    <div className="answer">答：在苹果手机的无线网络设置里，把其他信号设置为“忽略此网络”即可解决。<br />
                                        <img className="col-sm-4" data="pic8.png" src="../image/faq/pic8.png"></img>
                                        <img className="col-sm-4" data="pic9.png" src="../image/faq/pic9.png"></img>
                                    </div>
                                </div>

                                <br className="clear" />
                                    <h2>本文档最终解释权归爱WiFi中心所有  2016年6月23日</h2>
                            </div>
                        </div>
                    </div>
                </div>
            )
        }
    });


    /**
     *
     * @param {Object} field  输入框$对象 必须
     * @param {Object} max    最大长度    必须
     * @param {Object} num    限制提示$对象(div)
     */
    function limitText(field, max, num) {
        var v = field.val() || '';
        if (v.length > max) {
            var n = v.substr(0, max);
            field.val(n);
        } else if (num) {
            var l = max - v.length;
            num.html(l);
        }
    }

    function zImageUtil2(config) {
        var o = {
            imgDom: null, //回显的image的id
            maxHeight: null, //预设的最大高度
            maxWidth: null, //预设的最大宽度
            postUrl: null, //提交的url"/calendar/image/upload.json"
            preShow: false,
            callback: null,
            fileInput:null,
            fileChange: function(e) {
                var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
                var FR = new FileReader();
                var _this = this;

                FR.onload = function(f) {

                    _this.compressImg(this.result, 300, function(data) { //压缩完成后执行的callback
                        console.log("压缩完成后执行的callback");
                        //document.getElementById('imgData').value = data;//写到form元素待提交服务器
                        //document.getElementById('myImg').src = data;//压缩结果验证
                        if (_this.preShow) {
                            console.log("img pre show");
                            $(_this.imgDom).attr("src",data);

                            //console.log(_this.imgDom);

                        }
                        console.log("begin send img");
                        var json = {};
                        // json.imageName= "myImage.png";
                        data ="+"+ data;//.substring(22);
                        //alert(data.substring(0,100));
                        // alert(data)
                        json.imageData = encodeURIComponent(data);
                        console.log("begin post");
                        dialog.showWait();
                        alert(_this.postUrl);
                        Ajax.post(_this.postUrl,
                            json,
                            function(data) {
                                dialog.hideWait();

                                if (data.r == AJAX_SUCC) {
                                    /*
                                     console.log(data.r+":"+AJAX_SUCC);
                                     console.log(data);
                                     console.log(_this.imgDom);
                                     console.log("hello:"+PATH+"/" + data.data);*/
                                    $(_this.imgDom).attr("src", PATH+"/" + data.data);
                                    /*	$(_this).parent().find("#picurl");
                                     console.log("imgUrl:" + data.data);*/
                                } else {
                                    //	                        		zalert(data.msg);
                                }
                                if (_this.callback != null)
                                    _this.callback(data);
                            }
                        );
                    });
                };
                FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
            },
            compressImg: function(imgData, maxHeight, onCompress) {
                var _this = this;
                if (!imgData)
                    return;
                onCompress = onCompress || function() {};
                maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
                var canvas = document.createElement('canvas');
                var img = new Image();
                console.log("maxHeight:" + maxHeight);
                img.onload = function() {
                    if (img.height > maxHeight) { //按最大高度等比缩放
                        img.width *= maxHeight / img.height;
                        img.height = maxHeight;
                    }
                    canvas.width = img.width;
                    canvas.height = img.height;
                    var ctx = canvas.getContext("2d");

                    ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

                    //重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
                    console.log("width:" + img.width + "height:" + img.height);
                    ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
                    console.log("begin compress img");
                    onCompress(canvas.toDataURL("image/png")); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
                };
                // 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
                console.log("begin origin data load:");
                img.src = imgData;

            },
            init: function(jso) {
                this.imgDom = $("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
                this.maxHeight = jso.maxHeight||633;
                this.maxWidth = jso.maxWidth||300;
                this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
                var that =this;
                this.callback = jso.callback||function(result){
                        $(that.input).val(result.data);
                    };
                this.fileInput=$("<input type=\"file\" style=\"display:none\"/>");
                this.input =$("#"+jso.input);
                this.imgDom.attr("src",$(this.input).val())
                $(this.input).parent().append(this.imgDom);
                $(this.input).parent().append(this.fileInput);

                $(this.fileInput).change(function(){

                    //  console.log("imgDom:"+nowImg);
                    //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                    that.fileChange(this);
                });

                $(this.imgDom).click(function(){
                    $(that.fileInput).trigger("click");
                });
            }
        };
        o.init(config);
        return o;
    }

    // OtherReact 在这里定义

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};

/** ---------------------------------------  中间内容区域 结束  ----------------------------------------------------------------------------*/

/** ---------------------------------------  右侧配置区域 开始  ----------------------------------------------------------------------------*/


var _Setting_ = function (entity, divId) {
    var setting = null;

    var _state = entity.state || {

        };

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {
            return _state;
        },
        componentDidMount: function () {

        },

        render: function () {
            return (
                <div className="container _Setting_">
                </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

/** ---------------------------------------  右侧配置区域 结束  ----------------------------------------------------------------------------*/

/**
 * DOM 渲染
 */
var entity = _Entity_('', 'entity').render();
var setting = _Setting_(entity, 'setting').render();




