var SettingEntityComponent=React.createClass({displayName:"SettingEntityComponent",handleSave:function(){var option={};var content=$("textarea#content").val();if(content.length>300){alert("输入内容不能超过300个字，请重新输入！");return false}if(content==""||content==null){alert("输入内容不能为空！");return false}option.content=content;var align=$('input:radio[name="textalign"]:checked').val();option.align=align;var size=$('input:radio[name="fontsize"]:checked').val();option.fontsize=size;var show=$('input:radio[name="showstyle"]:checked').val();option.show=show;console.log("setting--->",option);window.postMessage(JSON.stringify({op:"setting_update",msg:option}),"*")},render:function(){return React.createElement("div",{className:"container"},React.createElement("form",{className:"form-horizontal"},React.createElement("div",{className:"form-group"},React.createElement("label",{className:"col-sm-2 control-label"},"内容："),React.createElement("div",{className:"col-sm-9"},React.createElement("textarea",{className:"form-control",rows:"3",id:"content",placeholder:"请输入文本内容"}))),React.createElement("div",{className:"form-group"},React.createElement("label",{className:"col-sm-2 control-label"},"对齐样式："),React.createElement("div",{className:"col-sm-9"},React.createElement("label",{className:"radio-inline"},React.createElement("input",{type:"radio",name:"textalign",value:"left",defaultChecked:true}),"左对齐"),React.createElement("label",{className:"radio-inline"},React.createElement("input",{type:"radio",name:"textalign",value:"center"}),"居 中"),React.createElement("label",{className:"radio-inline"},React.createElement("input",{type:"radio",name:"textalign",value:"right"}),"右对齐"))),React.createElement("div",{className:"form-group"},React.createElement("label",{className:"col-sm-2 control-label"},"字体大小："),React.createElement("div",{className:"col-sm-9"},React.createElement("label",{className:"radio-inline fnnormal"},React.createElement("input",{type:"radio",name:"fontsize",value:"14",defaultChecked:true}),"正常"),React.createElement("label",{className:"radio-inline fnbigger"},React.createElement("input",{type:"radio",name:"fontsize",value:"16"}),"较大"),React.createElement("label",{className:"radio-inline fnbig"},React.createElement("input",{type:"radio",name:"fontsize",value:"18"}),"大"))),React.createElement("div",{className:"form-group"},React.createElement("label",{className:"col-sm-2 control-label"},"展示样式："),React.createElement("div",{className:"col-sm-9"},React.createElement("label",{className:"radio-inline"},React.createElement("input",{type:"radio",name:"showstyle",value:"all",defaultChecked:true}),"全部展示"),React.createElement("label",{className:"radio-inline"},React.createElement("input",{type:"radio",name:"showstyle",value:"part"}),"缩进展示"))),React.createElement("div",{className:"form-group"},React.createElement("div",{className:"col-sm-offset-2 col-sm-9"},React.createElement("button",{id:"save",type:"button",className:"btn btn-danger btn-sm",onClick:this.handleSave},"保 存")))))}});ReactDOM.render(React.createElement(SettingEntityComponent,null),document.getElementById("settingEntityDiv"));