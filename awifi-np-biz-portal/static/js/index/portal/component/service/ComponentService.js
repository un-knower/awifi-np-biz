/**
 * Created by XUXIAOMAN on 2015/12/16.
 */
indexApp.factory('componentService',function($rootScope, componentDao, projectDao) {

    var service = {};

    /**
     * 组件列表
     * @param $scope
     * @author 许小满
     * @date 2015/12/16
     */
    service.list = function($scope){
        componentDao.list($scope);
    };

    /**
     * validateComponentNameFail 校验组件名称（功能：组件注册）
     * @param $scope 校验的参数：componentName
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateComponentNameFail = function($scope){
        var $componentName = $('#componentName');
        var componentName = $scope.component.name;
        if(isBlank(componentName)){
            updateShowTipos($componentName, '组件名称不能为空！');
            return true;
        }
        if(!chkString(componentName, defrules.componentName)){
            updateShowTipos($componentName, '组件名称的格式为：1-20位字母、数字、下划线组成，不含特殊字符');
            return true;
        }
        return false;
    };

    /**
     * 校验选择项目
     * @param $scope
     * @returns {boolean}
     */
    service.validateProjectNameFail = function ($scope) {
        var $fkProjectId = $('#fkProjectId');
        var fkProjectId = $scope.fkProjectId;
        if(isBlank(fkProjectId)){
            updateShowTipos($fkProjectId, '请选择项目');
            return true;
        }
        return false;
    };

    /**
     * 校验组件版本号（功能：组件注册）
     * @param $scope 校验的参数：version
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateVersionFail = function($scope){
        var $version = $('#version');
        var version = $scope.component.version;
        if(isBlank(version)){
            updateShowTipos($version, '组件版本号不能为空！');
            return true;
        }
        if(!chkString(version, defrules.componentVersion)){
            updateShowTipos($version, '组件版本号的格式为：1-20位字母、数字、下划线组成，不含特殊字符');
            return true;
        }
        return false;
    };

    /**
     * 校验组件压缩包（功能：组件注册）
     * @param $scope 校验的参数：componentPath
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateComponentPathFail = function($scope){
        var $componentPath = $('#componentPath');
        var componentPath = $componentPath.val();
        if(isBlank(componentPath)){
            updateShowTipos($componentPath, '组件压缩包不能为空！');
            return true;
        }
        if(componentPath.indexOf('.zip') == -1){
            updateShowTipos($componentPath, '组件压缩包格式不正确！');
            return true;
        }
        return false;
    };

    /**
     * 校验组件压缩包（功能：组件注册）-- 编辑
     * @param $scope 校验的参数：componentPath
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateComponentPathFailOfEdit = function($scope){
        var $componentPath = $('#componentPath');
        var componentPath = $componentPath.val();
        if(isNotBlank(componentPath) && componentPath.indexOf('.zip') == -1){
            updateShowTipos($componentPath, '组件压缩包格式不正确！');
            return true;
        }
        return false;
    };

    /**
     * 校验组件图标（功能：组件注册）
     * @param $scope 校验的参数：iconPath
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateIconPathFail = function(){
        var $iconPath = $('#iconPath');
        var iconPath = $iconPath.val();
        if(isBlank(iconPath)){
            updateShowTipos($iconPath, '组件图标不能为空！');
            return true;
        }
        if(iconPath.indexOf('.png') == -1){
            updateShowTipos($iconPath, '组件图标格式不正确！');
            return true;
        }
        return false;
    };

    /**
     * 校验组件图标（功能：组件注册）-- 编辑
     * @param $scope 校验的参数：iconPath
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateIconPathFailOfEdit = function(){
        var $iconPath = $('#iconPath');
        var iconPath = $iconPath.val();
        if(isNotBlank(iconPath) && iconPath.indexOf('.png') == -1){
            updateShowTipos($iconPath, '组件图标格式不正确！');
            return true;
        }
        return false;
    };

    /**
     * 校验组件缩略图（功能：组件注册）
     * @param $scope 校验的参数：thumb
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateThumbFail = function(){
        var $thumb = $('#thumb');
        var thumb = $thumb.val();
        if(isBlank(thumb)){
            updateShowTipos($thumb, '组件缩略图不能为空！');
            return true;
        }
        var isPic = thumb.indexOf('.png') != -1 || thumb.indexOf('.jpg') != -1;
        if(!isPic){
            updateShowTipos($thumb, '组件缩略图格式不正确！');
            return true;
        }
        return false;
    };

    /**
     * 校验组件缩略图（功能：组件注册） -- 编辑
     * @param $scope 校验的参数：thumb
     * @returns {boolean}
     * @author 许小满
     * @date 2015/12/16
     */
    service.validateThumbFailOfEdit = function(){
        var $thumb = $('#thumb');
        var thumb = $thumb.val();
        var isPic = thumb.indexOf('.png') != -1 || thumb.indexOf('.jpg') != -1;
        if(isNotBlank(thumb) && !isPic){
            updateShowTipos($thumb, '组件缩略图格式不正确！');
            return true;
        }
        return false;
    };

    /**
     * 各校验字段相关信息提示的初始化（功能：组件注册）
     * @author 许小满
     * @date 2015/12/16
     */
    service.initTips = function(){
        var $componentName = $('#componentName');//组件名称
        var $fkProjectId = $('#fkProjectId');//项目名称
        var $classify = $('#classify');
        var $version = $('#version');//组件版本号
        var $componentPath = $('#componentPath');//组件压缩包
        var $iconPath = $('#iconPath');//组件图标
        var $thumb = $('#thumb');//组件缩略图

        var tipsArray = [$componentName, $fkProjectId, $classify,$version,$componentPath,$iconPath,$thumb,$iconPath];
        initTipsArray(tipsArray);
        //页面切换前去除所有提示的Tips
        $rootScope.$on('$locationChangeStart', function(){
            destroyTipsArray(tipsArray);
        });
    };

    /**
     * 组件注册
     * @param $scope
     * @author 许小满
     * @date 2015/12/16
     */
    service.add = function($scope){
        componentDao.add($scope);
    };

    /**
     * 查看
     * @param id 主键id
     * @author 许小满
     * @date 2015/12/16
     */
    service.show = function($scope, id){
        componentDao.show($scope, id);
    };

    /**
     * 组件编辑
     * @param $scope
     * @author 许小满
     * @date 2015/12/21
     */
    service.edit = function($scope){
        componentDao.edit($scope);
    };

    /**
     * 组件删除
     * @para id
     * @author 许小满
     * @date 2015/12/22
     */
    service.delete = function($scope, id){
        componentDao.delete($scope, id);
    };

    service.projectSelect2 = function ($scope, idName) {
        return projectDao.projectSelect2($scope, idName);
    };

    return service;
});