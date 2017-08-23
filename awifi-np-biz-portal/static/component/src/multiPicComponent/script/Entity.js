/** *
 * 单图组件  Entity
 * @param state
 * @param divId
 * @returns {{render: render}}
 * @private
 * Created by Shin on 2016/1/18.
 */

var _Entity_ = function (state, divId) {

    //初始化图片
    var _state = state || {
            showImgUpload: false, //用于控制是否显示图片上传图层
            currentImg: 1, // 当前操作图片标识
            indexImg: 3, // 添加图片序号
            rowSize: 3, // 一行显示几张图片
            titleColor: '', // 图片标题颜色
            uploadImgSrc: '', // 上传图片的路径
            imgData: [{
                'imgId': 1,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            }, {
                'imgId': 2,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            }, {
                'imgId': 3,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            }] // 初始化3张图片
        };

    var EntityItem = React.createClass({
        render: function () {
            // 获取属性值并判断空值
            var prop = this.props;
            var imgSrc = prop.img.imgSrc ? prop.img.imgSrc : './images/default-16-9.png';
            var imgText = prop.img.imgText ? prop.img.imgText : '标题';
            var linkSrc = prop.img.linkSrc ? prop.img.linkSrc : 'javascript:;';
            var aStyle = prop.titleColor ? {color: prop.titleColor} : {};
            return (
                <div className="multiPic">
                    <div className="multiPicBox">
                        <a href={linkSrc} style={aStyle}>
                            <img src={imgSrc} alt={imgText} />
                            {prop.img.imgText ? <p>{imgText}</p> : ''}
                        </a>
                    </div>
                </div>
            )
        }
    });

    var MultiPicReact = React.createClass({
        //返回初始化的信息
        getInitialState: function () {
            return _state;
        },

        //根据state变化来渲染组件
        render: function () {
            // 编历图片组件对象
            var state = this.state;
            var imgList = state.imgData.map(function (img) {
                return <EntityItem img={img} titleColor={state.titleColor} />
            });

            return (
                <div className="_Entity_">
                    <div className={"multiPic-col"+state.rowSize}>
                        {imgList}
                    </div>
                </div>
            );
        }
    });

    // OtherReact 在这里定义

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<MultiPicReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};


// 拉portal调用方式
// _Entity_(state, divId).render(); 组件拉了多次则调用多次

/*
 1) 写入页面中相应的Div元素，每个div的id保证唯一性
 2）合并多个组件js文件至一个js文件中，且在合并的js文件末尾加个要调用 ReactJs 的方法
 3）合并多个组件css文件至一个css文件中
 */
