
/**
 * 文本组件
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2015-12-29
 */

var _Entity_ = function (state, divId) {

    var _state = state || {
            content: '文本内容',
            textAlign: 'left',
            fontSize: '14',
            display: 'none'
        };

    //var EntityReact = React.createClass({
    //
    //    getInitialState: function() {
    //        return _state;
    //    },
    //
    //    render: function() {
    //        return (
    //            <div className="_gY0T4GhYxXaE2XpAoCl8SpS1">
    //                <div className="article-content">
    //                    <p style={{textAlign: this.state.textAlign, fontSize: this.state.fontSize}}>{this.state.content}</p>
    //                </div>
    //                <div className="article-more" style={{display: this.state.display}}></div>
    //            </div>
    //        );
    //    }
    //});
    // ### Reactjs 写法

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
