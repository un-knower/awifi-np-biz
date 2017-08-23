
    Vue.component('apiParameter', {
        template: '\
            <tr  >\
            <td >\
            {{item.name}}\
            </td>\
             <td >\
            <input :name="item.name"  :type="isFile(item)" width="200px"></input>\
            </td>\
             <td >\
            {{item.description}}\
            </td>\
        	 <td >\
            {{item.in}}\
            </td>\
             <td >\
            {{item.type}}\
            </td>\
              <td >\
            {{item.required}}\
            </td>\
            </tr>\
        ',
        methods:{
        	isFile(item){
        		return item.type=="file"?"file":"text";
        	}
        },
        props: ['item'],
    })