/**
 * 文本组件
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2015-12-29
 */

var _Entity_ = function(state, divId) {
    var _state = state || {
            content: '请输入文本内容',
            textAlign: 'left',
            fontSize: '14',
            display: 'none',
            bgColor: '',
            fontColor: '#333',
            height: 'auto',
            opacity: '1'
        };

    var EntityReact = React.createClass({

        getInitialState: function() {
            return _state;
        },

        render: function() {
            var styles = {textAlign: this.state.textAlign, fontSize: this.state.fontSize, color: this.state.fontColor};
            var styles_text = {opacity: this.state.opacity};
            if (this.state.bgColor) {
                styles.backgroundColor = this.state.bgColor;
            }
            if (this.state.height != 'auto') {
                styles.height = this.state.height + 'px';
            }
            if (this.state.opacity == '0') {
                if (_DEV_ID === '{@devId@}') {
                    styles.opacity = 0.3;
                }
            }
            // 替换文本内容中所有换行符和空隔
            this.state.content = this.state.content.replace(/\n/g, '<br/>');
            this.state.content = this.state.content.replace(/[  ]/g, '&nbsp;');
            return (
                <div className="_Entity_">
                    <div className="article-content">
                        <div style={styles}> <p style={styles_text} dangerouslySetInnerHTML={{__html: this.state.content}}></p></div>
                    </div>
                    <div className="article-more" style={{display: this.state.display}}></div>
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
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
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