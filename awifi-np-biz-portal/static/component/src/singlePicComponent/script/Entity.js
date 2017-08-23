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
            imgSrc:"images/defaultPicComponent.png",//图片路径
            oldImgSrc: "", //旧图片路径，防止垃圾数据
            showImgUpload: false //用于控制是否显示图片上传图层
        };

    var EntityReact = React.createClass({
        //返回初始化的信息
        getInitialState: function () {
            return _state;
        },

        //根据state变化来渲染组件
        render: function () {
            return (
                <div className="_Entity_">
                    <img src={this.state.imgSrc} alt=""/>
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
        return React.render(<EntityReact />, document.getElementById(divId));
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
