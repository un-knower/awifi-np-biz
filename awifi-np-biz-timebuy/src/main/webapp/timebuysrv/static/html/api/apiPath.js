
    Vue.component('apiPath', {

        template: '\
            <li   class=" zw-row">\
            <div class="">\
            <h2 v-on:click="showOrHide" class=" zw-row-title"><a>{{title}} </a><ul  class="pull-right"><li>显示/隐藏</li> </ul></h2>\
            </div>\
            <ul v-if="show"class="api-list panel-body">\
                <li   is="apiUrl"   \
         v-for="(value, key) in content"  \
         v-bind:title="key" \
         v-bind:content="value"\
              v-bind:url="title" \
         ></li> \
            </ul>\
            </li>\
        ',
        data:function(){
       return {show:false};
        },



        props: ['title',"content"],
         methods: {
            showOrHide: function (event) {


              this.show=!this.show;
            }
          }
    })


