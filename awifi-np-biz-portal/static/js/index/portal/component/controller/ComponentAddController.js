/**
 * Created by XUXIAOMAN on 2015/12/16.
 */
indexApp.controller('componentAddController', function($scope, componentService){

    /**
     * validateFail 检验添加组件注册所涉及到的参数是否验证通过
     * @param $scope
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    function validateFail($scope){
        //校验组件名称
        if(componentService.validateComponentNameFail($scope)){
            return true;
        }
        // 校验项目名称
        //if (componentService.validateProjectNameFail($scope)) {
            //return true;
        //}
        //校验组件版本号
        if(componentService.validateVersionFail($scope)){
            return true;
        }
        //组件压缩包
        if(componentService.validateComponentPathFail()){
            return true;
        }
        //组件图标
        if(componentService.validateIconPathFail()){
            return true;
        }
        //组件缩略图
        if(componentService.validateThumbFail()){
            return true;
        }
        return false;
    }

    /**
     * 组件注册
     * @author 许小满
     * @date 2015/12/16
     */
    $scope.add = function (){
        if(validateFail($scope)){
            return;
        }
        componentService.add($scope);
    };

    /**
     * 初始化函数
     * @author 许小满
     * @date 2015/12/16
     */
    function init(){
        var component = {};
        $scope.classifys = [{id:1,name:'通用'},{id:2,name:'认证组件'},{id:3,name:'过渡跳转组件'}];
        $scope.classify_id = 1;
        $scope.canUniqueYes = false;
        $scope.component = component;
        //初始化Tips
        componentService.initTips();
        // 项目名称select2
        componentService.projectSelect2($scope, 'projectId');
        componentService.projectSelect2($scope, 'filterProjectId');
    }

    init();
});