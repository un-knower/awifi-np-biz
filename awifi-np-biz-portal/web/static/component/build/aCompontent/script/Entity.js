var _Entity_=function(state,divId){var _state=state||{textAlign:"left",aText:"链接文字",aHref:"javascript:;"};var EntityReact=React.createClass({displayName:"EntityReact",getInitialState:function(){return _state},render:function(){return React.createElement("div",{className:"textlink",style:{textAlign:this.state.textAlign}},React.createElement("a",{href:this.state.aHref,title:this.state.aText},this.state.aText))}});function render(){return React.render(React.createElement(EntityReact,null),document.getElementById(divId))}return{render:render}};