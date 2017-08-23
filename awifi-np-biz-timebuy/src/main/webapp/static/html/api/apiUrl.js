
Vue
		.component(
				'apiUrl',
				{

					template : '\
            <li  class="  zw-panel" :class="content.httpType">\
            <div   class="zw-panel-heading" v-on:click="showOrHide" >\
                <h2 class="zw-panel-title "><span class="zw-row-title-main" >{{content.httpType}}</span><span class="zw-row-title-sub ">{{content.url}}&nbsp;&nbsp;&nbsp;</span><a></a><span class="pull-right" >{{content.summary}}</span></h2>\
            </div>\
            <form class="panel-body" :id=formId v-if="show" v-bind:transition="expand" :action=content.url :method=content.httpType  :enctype=formType(content) >\
                <span>备注</span>\
                <span ><span class="">{{content.description}}</span></span>\
                <table class="api-list table">\
                    <tr>\
                    <th style="width: 100px; max-width: 100px" data-sw-translate="">参数</th>\
                    <th style="width: 310px; max-width: 310px" data-sw-translate="">值</th>\
                    <th style="width: 200px; max-width: 200px" data-sw-translate="">描述</th>\
                    <th style="width: 100px; max-width: 100px" data-sw-translate="">参数位置</th>\
						 <th style="width: 100px; max-width: 100px" data-sw-translate="">参数类型</th>\
                    <th style="width: 220px; max-width: 230px" data-sw-translate="">是否必填</th>\
                    </tr>\
                    <tr   is="apiParameter"   \
                     v-for="item in content.parameters"  \
                     v-bind:item="item" \
                     ></tr> \
                </table>\
                <ul class="panel panel-warning">\
                    <div  class=" panel-heading">\
                        <h3 class="panel-title">\
                        <button type="button" class="btn btn-sm  btn-primary" v-on:click="sendRequest" >发送请求</button>\
                          &nbsp;&nbsp;<button type="button" v-on:click="saveTestData"  class="btn btn-sm  btn-primary" >保存当前数据为测试数据</button>&nbsp;&nbsp;\
                          <button type="button" v-on:click="getTestData"  class="btn btn-sm  btn-primary" >加载测试数据</button>\
                           <button type="button" v-on:click="hideRequestData"  class="btn btn-sm btn-primary" >隐藏</button>\
                          </h3>\
                    </div>\
                    <div v-if="showResponse">\
                    <div class="panel-body">\
                        <div class="panel panel-default">\
                            <div class="panel-heading">测试数据:</div>\
                            <div class="panel-body">\
                              <p v-for="(item,index) in testData"  >{{index}} {{item}}&nbsp;&nbsp;<a v-on:click="setTestData(index)"> 应用</a>&nbsp;&nbsp;  <a v-on:click="deleteTestData(index)">删除</a></p>\
                        </div>\
                      </div>\
                       <div class="panel panel-default">\
                          <div class="panel-heading">请求url:</div>\
                          <div class="panel-body">\
                             {{content.url}}\
                          </div>\
                        </div>\
                      <div class="panel panel-default">\
                          <div class="panel-heading">请求数据:</div>\
                          <div class="panel-body">\
                             {{requestData}}\
                          </div>\
                        </div>\
                      <div class="panel panel-default">\
                          <div class="panel-heading">响应码:</div>\
                          <div class="panel-body">\
                             {{status}}\
                          </div>\
                        </div>\
                    <div class="panel panel-default">\
                      <div class="panel-heading">请求结果</div>\
                      <div class="panel-body">\
                        {{result}}\
                      </div>\
                    </div>\
                    </div>\
                </div>\
            </ul>\
            </form>\
            </li>\
        ',
					data : function() {
						return {
							showResponse : false,
							show : false,
							result : "",
							"formId" : formId++,
							success : "",
							status : "",
							requestData : "",
							testData : [],
							transitionName: 'expand'
						};
					},
					props : [ 'title', "content", "url" ],
					methods : {
						showOrHide : function(event) {

							this.show = !this.show;

						},

						sendRequest : function() {
							console.log("formId" + this.formId);
							console.log("url" + this.content.url);

							var json = changeForm2Jso("#" + this.formId);

							var url = this.content.url;
							if (this.content.url.indexOf("{") > 0) {
								var replaceId = url.substr(
										url.indexOf("{") + 1, url.indexOf("}")
												- url.indexOf("{") - 1);
								url = url.replace("{" + replaceId + "}",
										json[replaceId]);
								//alert(replaceId);
							}//alert(url);
							console.log(json);

							this.requestData = json;
							//如果是文件提交 就用第一种方案
							var isFileSubmit = false;

							for (var i = 0; i < this.content.parameters.length; i++) {
								if (this.content.parameters[i].type == 'file') {
									isFileSubmit = true;
								}
							}
							if (isFileSubmit) {
								var options = {
									url : url,
									success : function(xml) {
										this.result = xml;

									}.Apply(this),
									complete : function(xhr, textStatus) {
										console.log("complete xhr" + xhr);
										this.status = xhr.status;
										this.success = textStatus;
										this.showResponse = true;
										console.log("complete textStatus"
												+ textStatus);
									}.Apply(this)
								};
								$("#" + this.formId).ajaxSubmit(options);
								return;
							}

							$.ajax({
									type : this.content.httpType,
									url : url,
									data : json,
									dataType : "json",
									contentType : "application/x-www-form-urlencoded",
									success : function(xml) {
										this.result = xml;

									}.Apply(this),
									complete : function(xhr, textStatus) {
										console.log("complete xhr" + xhr);
										this.status = xhr.status;
										this.success = textStatus;
										this.showResponse = true;
										console.log("complete textStatus"
												+ textStatus);
									}.Apply(this)
								});

						},

						saveTestData : function() {

							var json = changeForm2Jso("#" + this.formId);
							console.log(json);
							var sendData = {};
							this.testData.push(json);
							sendData.testData = JSON.stringify(this.testData);
							sendData.url = this.content.url;
							Ajax.post("/api/test/data/save", sendData,
									function(data) {

									}.Apply(this));

						},
						hideRequestData : function() {
							this.showResponse = !this.showResponse
						},
						getTestData : function() {
							var sendData = {};

							sendData.url = this.content.url;
							Ajax.getJSON("/api/test/data/get", sendData,
									function(data) {
										this.testData =eval( '('+data+')');
											
									}.Apply(this));
						},
						formType : function(content) {
							console.log(content);
							for (var i = 0; i < content.parameters.length; i++) {
								if (content.parameters[i].type == 'file') {
									return "multipart/form-data";
								}
							}
							return "application/x-www-form-urlencoded";

						},
						setTestData : function(index) {
							console.log(index);
							fillJso2Form("#" + this.formId,
									this.testData[index]);
						},
						deleteTestData : function(index) {

							removeByValue(this.testData, this.testData[index]);
							console.log("开始删除");
							var sendData = {};

							sendData.testData = JSON.stringify(this.testData);
							sendData.url = this.content.url;
							console.log(this.testData);
							Ajax.post("/api/test/data/save", sendData,
									function(data) {
										this.getTestData();
									}.Apply(this));

						}
					}
				})
