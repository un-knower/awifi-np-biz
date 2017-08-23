package com.awifi.np.biz.devsrv.fatap.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.location.model.CenterPubArea;
import com.awifi.np.biz.common.excel.model.CenterPubCorporation;
import com.awifi.np.biz.common.excel.model.CenterPubModel;
import com.awifi.np.biz.common.excel.model.Validation;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

/**
 * 导入Excel的厂家、型号、地区权限的校验
 * 
 */
/**
 * @ClassName:ExcelValidation
 * @author 李程程
 * 
 */
public class ExcelValidation{
    /**
     * 厂家集合
     */
    private Map<String, CenterPubCorporation> corpMap;
    
    /**
     * 型号id
     */
    private String model;
    /**
     * 厂家-型号集合
     */
    private Map<String, Map<String, CenterPubModel>> corporationAndmodelMap;
    /**
     * 设备型号集合
     */
    private Map<String, CenterPubModel> modelMap;
    /**
     * 用户信息
     */
    private SessionUser userSession;
    /**
     * 地区操作service层
     */
    private PubAreaService pubAreaService;
    
    /**
     * 厂商和型号是否已验证标志
     */
    private boolean flag=false;
    /**
     * 本次导入的厂商编号
     */
    private String corporation=null;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

	/**
     * 构造方法
     * @param userSession 用户信息
     */
    public ExcelValidation(SessionUser userSession){
        this.userSession = userSession;
    }

    public void setPubAreaService(PubAreaService pubAreaService){
        this.pubAreaService = pubAreaService;
    }

    public void setCorpMap(Map<String, CenterPubCorporation> corpMap){
        this.corpMap = corpMap;
    }

    public void setCorporationAndmodelMap(Map<String, Map<String, CenterPubModel>> corporationAndmodelMap) {
        this.corporationAndmodelMap = corporationAndmodelMap;
    }
    /**
     * 根据id 设置 型号 容器
     * @param modelMap 
     * @author 伍恰  
     * @date 2017年6月21日 下午5:06:29
     */
    public void setModelById(Map<String, CenterPubModel> modelMap){
        this.setModelMap(modelMap);
    }

    /**
     * 对信息验证的一个过程
     * @param obj obj
     */
    public void validate(Validation obj){
        if (corpMap != null&&!flag){
            String corpId = corpValidation(obj);//厂家验证
            modelValidation(obj, corpId);//型号和厂家的验证
            model=obj.getModel();
            flag=true;
        }
        if(flag){
            obj.setModel(model);
        }
        String provinceId = provinceValidation(obj);//地区验证
        String cityId = cityValidation(obj, provinceId);
        countyValidation(obj, cityId);
        
        /*去掉 新增地址和厂家发货区域的校验
         * if (modelMap != null){
            areaValidation(obj);
        }*/
    }

    /**
     * 厂家校验
     * @param obj obj
     * @return 厂家信息
     */
    private String corpValidation(Validation obj){
        String value = obj.getCorporation();
        CenterPubCorporation centerPubCorp = corpMap.get(value);
        if (centerPubCorp == null){
            throw new ApplicationException("Excel 中的厂商:" + value + "不存在!");
        }
        else{
            obj.setCorporation(centerPubCorp.getId().toString());
            setCorporation(centerPubCorp.getId().toString());
            //少一个用户类型的校验userSession.getUserptype() != null && userSession.getUserptype() == UserType.corporation_user.getValue()
            if (userSession.getOrgId() != null
                    && userSession.getOrgId() != Long.parseLong(obj.getCorporation())){
                throw new ApplicationException("非法导入，请导入本厂商的数据");
            }
        }
        return obj.getCorporation();
    }

    /**
     * 型号校验
     * @param obj obj 
     * @param corpId 厂家id
     */
    private void modelValidation(Validation obj, String corpId){
        String value = obj.getModel();
        Map<String, CenterPubModel> map = corporationAndmodelMap.get(corpId + value);
        if (map == null || map.isEmpty()){
            throw new ApplicationException("Excel 中的厂商下不存在此型号!");
        }
        CenterPubModel centerPubCorp = map.get(value);
        if (centerPubCorp == null){
            throw new ApplicationException("Excel 中的型号:" + value + "不存在!");
        }
        else{
            if (centerPubCorp.getCorpId() != null && !corpId.equals(centerPubCorp.getCorpId().toString())){
                throw new ApplicationException("Excel 中的厂商下不存在此型号!");
            }
            obj.setModel(centerPubCorp.getId().toString());
        }
    }

    /**
     * 地区 省级校验
     * @param obj obj
     * @return String
     */
    private String provinceValidation(Validation obj){
        String value = obj.getProvince();
        if (value == null || StringUtils.isEmpty(value.toString())){
            throw new ApplicationException("Excel 中省：" + value + "不存在!");
        }

        CenterPubArea centerPubArea = pubAreaService.queryByParam(value.toString(), "PROVINCE", null);
        if (centerPubArea == null){
            throw new ApplicationException("Excel 中省：" + value + "不存在!");
        }else{
            obj.setProvince(centerPubArea.getId().toString());
//            if (userSession.getProvinceId() != null
//                    && !(userSession.getProvinceId()).equals(centerPubArea.getId())){
//                throw new ApplicationException("非法导入，请导入本地区的数据");
//            }
        }
        return obj.getProvince();
    }

    /**
     * 地区 市级校验
     * @param obj obj
     * @param provinceId provinceId
     * @return String
     */
    private String cityValidation(Validation obj, String provinceId){
        String value = obj.getCity();
        if (value == null || StringUtils.isEmpty(value.toString())){
            throw new ApplicationException("Excel 中市：" + value + "不存在!");
        }

        CenterPubArea centerPubArea = pubAreaService.queryByParam(value.toString(), "CITY",
                Long.parseLong(provinceId));
        if (centerPubArea == null){
            throw new ApplicationException("Excel 中市：" + value + "不存在!");
        }
        else{
            obj.setCity(centerPubArea.getId().toString());
//            if (userSession.getCityId() != null
//                    && !(userSession.getCityId()).equals(centerPubArea.getId())){
//                throw new ApplicationException("非法导入，请导入本地区的数据");
//            }
        }
        return obj.getCity();
    }

    /**
     * 地区 区/县级校验
     * 
     * @Description:
     * @param obj obj
     * @param cityId cityId
     * @throws
     */
    private void countyValidation(Validation obj, String cityId){
        String value = obj.getCounty();
        if (value == null || StringUtils.isEmpty(value.toString())){
            throw new ApplicationException("Excel 中区/县：" + value + "不存在!");
        }

        CenterPubArea centerPubArea = pubAreaService.queryByParam(value.toString(), "COUNTY",
                Long.parseLong(cityId));
        if (centerPubArea == null){
            throw new ApplicationException("Excel 中区/县：" + value + "不存在!");
        }
        else{
            obj.setCounty(centerPubArea.getId().toString());
//            if (userSession.getAreaId() != null
//                    && !(userSession.getAreaId()).equals(centerPubArea.getId())){
//                throw new ApplicationException("非法导入，请导入本地区的数据");
//            }
        }
    }

    public Map<String, CenterPubModel> getModelMap() {
        return modelMap;
    }

    public void setModelMap(Map<String, CenterPubModel> modelMap) {
        this.modelMap = modelMap;
    }

    /**
     * 新增地址和厂家发货区域的校验
     * 
     * @author cjl
     * @date 2015年12月23日下午7:39:45
     * @param obj 待验证的信息
     */
    /*private void areaValidation(Validation obj){
        String value = obj.getModel();
        String provinceId = obj.getProvince();
        String cityId = obj.getCity();
        String countyId = obj.getCounty();
        // 根据传入的型号编号 查询型号信息 并且附带发货区域
        CenterPubModel centerPubModel = modelMap.get(value);
        if (centerPubModel == null){
            throw new ApplicationException("根据型号编号查询信号信息为空！");
        }
        // 地区校验
        boolean addrP = false;
        boolean addrPC = false;
        boolean addrPCC = false;
        JSONArray info = (JSONArray) JSON.toJSON(centerPubModel.getOrigins());
        Map<String, Object> mapArea = new HashMap<String, Object>();
        for (int i = 0; i < info.size(); i++){
            JSONObject areaList = info.getJSONObject(i);
            if (!StringUtils.isEmpty(areaList.getString("province"))
                    && !StringUtils.isEmpty(areaList.getString("city"))
                    && !StringUtils.isEmpty(areaList.getString("county"))){
                // 省市区校验
                String provinceCityCounty = provinceId + cityId + countyId;
                String areaValid = areaList.getString("province") + areaList.getString("city")
                        + areaList.getString("county");
                mapArea.put(areaValid, areaValid);
                addrPCC = mapArea.containsKey(provinceCityCounty);
            }
            else if (!StringUtils.isEmpty(areaList.getString("province"))
                    && !StringUtils.isEmpty(areaList.getString("city"))
                    && StringUtils.isEmpty(areaList.getString("county"))){
                // 只有省市地区
                String provinceCity = provinceId + cityId;
                String areaValid = areaList.getString("province") + areaList.getString("city");
                mapArea.put(areaValid, areaValid);
                addrPC = mapArea.containsKey(provinceCity);
            }
            else if (!StringUtils.isEmpty(areaList.getString("province"))
                    && StringUtils.isEmpty(areaList.getString("city"))
                    && StringUtils.isEmpty(areaList.getString("county"))){
                // 只有省地区
                String province = provinceId;
                String areaValid = areaList.getString("province");
                mapArea.put(areaValid, areaValid);
                addrP = mapArea.containsKey(province);
            }
            else{
                // 说明发货区域为空 既可以发送全国
                addrP = true;
                addrPC = true;
                addrPCC = true;
            }
        }
        // 校验结果的判定
        if (addrPCC || addrPC || addrP){
            return;
        }
        throw new ApplicationException("文件中的省市区与型号管理中添加的省市区不相符，请核查!");
    }*/

}
