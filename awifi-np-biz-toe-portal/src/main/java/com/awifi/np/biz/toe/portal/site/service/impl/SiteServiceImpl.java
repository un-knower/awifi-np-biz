/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:38:09
* 创建作者：周颖
* 文件名称：SiteServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.component.model.Component;
import com.awifi.np.biz.toe.portal.component.service.ComponentService;
import com.awifi.np.biz.toe.portal.site.dao.SiteDao;
import com.awifi.np.biz.toe.portal.site.model.Site;
import com.awifi.np.biz.toe.portal.site.model.SitePage;
import com.awifi.np.biz.toe.portal.site.model.SitePageComponent;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@Service("siteService")
@SuppressWarnings("unchecked")
public class SiteServiceImpl implements SiteService {

    /**日志*/
    protected final Log logger = LogFactory.getLog(this.getClass());
    
    /**站点*/
    @Resource(name = "siteDao")
    private SiteDao siteDao;
    
    /**策略*/
    @Resource(name = "strategyService")
    private StrategyService strategyService;
    
    /**组件*/
    @Resource(name = "componentService")
    private ComponentService componentService;
    
    /***
     * 站点列表
     * @param page 页面
     * @param keywords 站点名称关键字
     * @author 周颖  
     * @date 2017年4月17日 下午3:45:29
     */
    public void getDefaultListByParam(Page<Site> page, String keywords){
        int count = siteDao.getDefaultCountByParam(keywords);
        page.setTotalRecord(count);
        if(count<=0){
            return;
        }
        List<Site> siteList = siteDao.getDefaultListByParam(keywords,page.getBegin(),page.getPageSize());
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");
        String thumb = null;
        for(Site site : siteList){
            thumb = site.getThumb();//缩略图相对路径
            site.setThumb(resourcesDomain + thumb);
        }
        page.setRecords(siteList);
    }
    
    /**
     * 地区站点列表
     * @param page 页面
     * @param keywords 站点名称关键字
     * @param provinceId 省id
     * @param cityId 市id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月18日 上午9:12:12
     */
    public void getLocationListByParam(Page<Site> page, String keywords, Long provinceId, Long cityId) throws Exception{
        int count = siteDao.getLocationCountByParam(keywords,provinceId,cityId);//总数
        page.setTotalRecord(count);
        if(count<=0){//小于0 直接返回
            return;
        }
        List<Site> siteList = siteDao.getLocationListByParam(keywords,provinceId,cityId,page.getBegin(),page.getPageSize());//列表
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
        String thumb = null;//缩略图
        Map<Long,String> provinceMap = new HashMap<Long,String>();//省缓存，减少查找次数
        Long provinceIdLong = null;
        Long cityIdLong = null;
        String province = null;
        String city = null;
        String nameParam = "name";//地区名称
        for(Site site : siteList){
            thumb = site.getThumb();//缩略图相对路径
            site.setThumb(resourcesDomain + thumb);
            provinceIdLong = site.getProvinceId();//省id
            province = provinceMap.get(provinceIdLong);//从map获取省 如果为空 从缓存中取
            if(StringUtils.isBlank(province)){
                province = LocationClient.getByIdAndParam(provinceIdLong, nameParam);//缓存中取
                provinceMap.put(provinceIdLong, province);
            }
            site.setProvince(province);
            cityIdLong = site.getCityId();//市id
            if(cityIdLong != null){//不为空 补全市名称
                city = LocationClient.getByIdAndParam(cityIdLong, nameParam);//缓存中取
                site.setCity(city);
            }else{
                city = StringUtils.EMPTY;
            }
            site.setLocationFullName(FormatUtil.locationFullName(province, city, null));//补全地区全称
        }
        page.setRecords(siteList);
    }
    
    /**
     * 行业站点列表
     * @param page 页面
     * @param keywords 站点名称关键字
     * @param priIndustryCode 一级行业
     * @param secIndustryCode 二级行业
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月18日 上午10:37:55
     */
    public void getIndustryListByParam(Page<Site> page, String keywords, String priIndustryCode, String secIndustryCode) throws Exception{
        int count = siteDao.getIndustryCountByParam(keywords,priIndustryCode,secIndustryCode);//总数
        page.setTotalRecord(count);
        if(count<=0){//小于0 直接返回
            return;
        }
        List<Site> siteList = siteDao.getIndustryListByParam(keywords,priIndustryCode,secIndustryCode,page.getBegin(),page.getPageSize());//列表
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
        String thumb = null;//缩略图
        String industryCode = null;
        String industry = null;
        Map<String,String> priIndustryMap = new HashMap<String,String>();//一级行业缓存，减少查找次数
        for(Site site : siteList){//一级行业必填，二级行业选填
            thumb = site.getThumb();//缩略图相对路径
            site.setThumb(resourcesDomain + thumb);
            industryCode = site.getPriIndustryCode();//一级行业编号
            industry = priIndustryMap.get(industryCode);//从map里取 为空再从缓存中取
            if(StringUtils.isBlank(industry)){
                industry = IndustryClient.getNameByCode(industryCode);
                priIndustryMap.put(industryCode, industry);
            }
            site.setPriIndustry(industry);//一级行业名称
            industryCode = site.getSecIndustryCode();//二级行业编号
            if(StringUtils.isNotBlank(industryCode)){//不为空 从缓存中取行业名称
                industry = IndustryClient.getNameByCode(industryCode);
                site.setSecIndustry(industry);
            }
        }
        page.setRecords(siteList);
    }
    
    /**
     * 站点策略列表
     * @param user 登陆用户
     * @param page 页面
     * @param keywords 站点名称关键字
     * @param siteId 站点id
     * @param merchantId 商户id
     * @param status 状态
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月19日 上午10:00:00
     */
    public void getListByParam(SessionUser user,Page<Site> page, String keywords, Long siteId, Long merchantId, Integer status) throws Exception{
        Long[] merchantIds = null;
        String cascadeLabel = StringUtils.EMPTY;
        if(PermissionUtil.isSuperAdmin(user)){//超管 可以看全部
            
        }else if(PermissionUtil.isMerchant(user)){//商户管理员
            cascadeLabel = user.getCascadeLabel();
        }else if(PermissionUtil.isMerchants(user)){//多个商户管理员
            String[] merchantIdsArray = user.getMerchantIds().split(",");
            merchantIds = CastUtil.toLongArray(merchantIdsArray);
        }else if(PermissionUtil.isProject(user) || PermissionUtil.isLocation(user)){//地区或项目管理员 强制选择一个商户
            if(merchantId == null){
                throw new BizException("E2000059", MessageUtil.getMessage("E2000059"));//请选择一个商户!
            }
        }else{//没有维护属性 可视为超管
            
        }
        int count = siteDao.getCountByParam(keywords,siteId,merchantId,status,cascadeLabel,merchantIds);//商户站点总数
        page.setTotalRecord(count);
        if(count <= 0){//总数为0的情况 考虑是否商户登陆 
            if(!PermissionUtil.isMerchant(user)){//不是商户管理员（商户）
                return;
            }
            getInitialSite(page,user.getMerchantId());
            return;
        }
        List<Site> siteList = siteDao.getListByParam(keywords,siteId,merchantId,status,cascadeLabel,merchantIds,page.getBegin(),page.getPageSize());
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
        String thumb = null;//缩略图
        Long merchantIdLong = null;//商户id
        String merchantName = null;//商户名称
        Map<Long,String> merchantNameMap = new HashMap<Long,String>();
        List<Long> siteIdList = new ArrayList<Long>();
        for(Site site : siteList){
            thumb = site.getThumb();//缩略图相对路径
            site.setThumb(resourcesDomain + thumb);
            siteIdList.add(site.getId());//站点id 批量查询站点的策略总数
            merchantIdLong = site.getMerchantId();//商户id
            merchantName = merchantNameMap.get(merchantIdLong);//先从map中获取商户名称
            if(StringUtils.isBlank(merchantName)){//为空 从redis获取
                merchantName = MerchantClient.getNameByIdCache(merchantIdLong);
                merchantNameMap.put(merchantIdLong, merchantName);
            }
            site.setMerchantName(merchantName);
        }
        if(siteIdList!= null && siteIdList.size() > 0){//补充策略数
            Map<Long,Integer> totalMap = strategyService.getTotalBySiteIds(siteIdList);//批量获取站点的策略数
            Integer total = null;
            for(Site site : siteList){
                total = totalMap.get(site.getId());
                if(total != null){
                    site.setStrategyNum(total);
                }else{
                    site.setStrategyNum(0);
                }
            }
        }
        page.setRecords(siteList);
    }
    
    /**
     * 站点列表 （商户没有个性化站点）
     * @param page 页面
     * @param merchantId 当前登陆用户商户id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月20日 上午11:20:31
     */
    private void getInitialSite(Page<Site> page, Long merchantId) throws Exception{
        Merchant merchant = MerchantClient.getByIdCache(merchantId);//从缓存中获取
        if(merchant == null){
            return;
        }
        String belongTo = FormatUtil.formatBelongToByProjectId(merchant.getProjectId().intValue());//归属 按项目区分 501:微站 504:园区 507:酒店 其他:项目型
        Site site = null;
        List<Site> siteList = null;
        if(belongTo.equals(Constants.TOE)){//行客 按照地区属性（项目型）
            site = siteDao.getLocationSite(merchant.getProvinceId(),merchant.getCityId());
        }else{//商客 按照行业属性 (微站园区酒店)
            site = siteDao.getIndustrySite(merchant.getPriIndustryCode(),merchant.getSecIndustryCode());
        }
        if(site == null){//找全国默认的站点
            site = siteDao.getDefaultSite();
            if(site == null){//全国默认都为空 直接返回空
                return;
            }
        }
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
        String thumb = site.getThumb();//缩略图相对路径
        site.setThumb(resourcesDomain + thumb);//缩略图地址
        site.setStrategyNum(0);//策略数为0
        siteList = new ArrayList<Site>();
        siteList.add(site);
        page.setTotalRecord(1);
        page.setRecords(siteList);
        return;
    }
    
    /**
     * 判断默认站点是否存在
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年4月21日 下午2:05:01
     */
    public boolean isDefaultSiteExist(){
        int count = siteDao.getDefaultSiteNum();
        return count > 0;
    }
    
    /**
     * 判断一级行业是否存在
     * @param priIndustryCode 一级行业编号
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月25日 上午9:51:16
     */
    public boolean isPriIndustrySiteExist(String priIndustryCode){
        int count = siteDao.getPriIndustrySiteNum(priIndustryCode);
        return count > 0;
    }
    
    /**
     * 判断一级行业是否存在 排除自己
     * @param id 站点id
     * @param priIndustryCode 一级行业编号
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月25日 上午9:51:16
     */
    public boolean isPriIndustrySiteExist(Long id,String priIndustryCode){
        int count = siteDao.getPriIndustrySiteNumById(id,priIndustryCode);
        return count > 0;
    }
   
    /**
     * 判断二级行业站点是否存在
     * @param secIndustryCode 二级行业编号
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月24日 上午9:39:17
     */
    public boolean isSecIndustrySiteExist(String secIndustryCode){
        int count = siteDao.getSecIndustrySiteNum(secIndustryCode);
        return count > 0;
    }
    
    /**
     * 判断二级行业站点是否存在 排除自己
     * @param id 站点主键
     * @param secIndustryCode 二级行业编号
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月24日 上午9:39:17
     */
    public boolean isSecIndustrySiteExist(Long id,String secIndustryCode){
        int count = siteDao.getSecIndustrySiteNumById(id,secIndustryCode);
        return count > 0;
    }
    
    /**
     * 判断省站点是否存在
     * @param provinceId 省id
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月24日 上午9:45:37
     */
    public boolean isProvinceSiteExist(Long provinceId){
        int count = siteDao.getProvinceSiteNum(provinceId);
        return count > 0;
    }
    
    /**
     * 判断省站点是否存在
     * @param id 站点id
     * @param provinceId 省id
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月24日 上午9:45:37
     */
    public boolean isProvinceSiteExist(Long id,Long provinceId){
        int count = siteDao.getProvinceSiteNumById(id,provinceId);
        return count > 0;
    }
    
    /**
     * 判断市站点是否存在
     * @param cityId 市id
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月24日 上午9:45:37
     */
    public boolean isCitySiteExist(Long cityId){
        int count = siteDao.getCitySiteNum(cityId);
        return count > 0;
    }
    
    /**
     * 判断市站点是否存在
     * @param id 站点id
     * @param cityId 市id
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月24日 上午9:45:37
     */
    public boolean isCitySiteExist(Long id,Long cityId){
        int count = siteDao.getCitySiteNumById(id,cityId);
        return count > 0;
    }
    
    /**
     * 判断商户下站点名称是否存在
     * @param siteName 站点名称
     * @param merchantId 商户id
     * @return true 存在
     * @author 周颖  
     * @date 2017年4月25日 下午2:11:13
     */
    public boolean isSiteNameExist(String siteName,Long merchantId){
        int count = siteDao.getSiteNameNum(siteName,merchantId);
        return count > 0;
    }
    
    /**
     * 添加默认站点
     * @param bodyParam 参数
     * @param request 请求
     * @return 站点id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月24日 上午10:13:09
     */
    public Long addDefault(Map<String, Object> bodyParam,HttpServletRequest request) throws Exception{
        String siteName = (String) bodyParam.get("siteName");//站点名称
        //参数校验
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        if(isDefaultSiteExist()){
            throw new BizException("E2600002", MessageUtil.getMessage("E2600002"));//默认站点已存在，不允许再次添加!
        }
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        Site site = new Site();
        site.setSiteName(siteName);
        site.setDefaultSite(1);//默认站点
        site.setStatus(3);//已发布
        siteDao.add(site);;//添加默认站点
        Long siteId = site.getId();
        dealPage(siteId,sitePageList,request);//保存站点页
        return siteId;//返回站点id 
    }
    
    /**
     * 校验站点页参数
     * @param sitePageList 站点页
     * @author 周颖  
     * @date 2017年6月22日 上午9:32:16
     */
    private void validatePages(List<Map<String,Object>> sitePageList){
        int sitePageSize = sitePageList.size();//站点页数
        
        Map<String,Object> sitePageMap = null;//站点页
        Long pageId = null;//站点页面表主键id
        Integer pageType = null;//页面类型：1 引导页、2 认证页、3 过渡页、4 导航页
        Integer num = null;//序号
        String pageOperation = null;//页面操作：add 新增、edit 编辑、remove 删除
        
        List<Map<String,Object>> componentList = null;//组件列表
        int componentSize;//一页组件个数
        Map<String,Object> componentMap = null;
        
        Long componentId = null;//组件id
        Long sitePageComponentId = null;//站点页面组件表主键id
        String componentOperation = null;//组件操作：add 新增、edit 编辑、remove 删除
        String json = null;//组件配置参数--JSON数据
        Integer orderNo = null;//排序号
        
        //1.循环站点页
        for (int i = 0; i < sitePageSize; i++) {
            sitePageMap = sitePageList.get(i);//站点页
            
            pageOperation = (String) sitePageMap.get("pageOperation");//页面操作：add 新增、edit 编辑、remove 删除
            ValidUtil.valid("页面操作[pageOperation]", pageOperation, "required");
            
            pageId = CastUtil.toLong(sitePageMap.get("pageId"));//站点页面表主键id
            pageType = CastUtil.toInteger(sitePageMap.get("pageType"));//页面类型：1 引导页、2 认证页、3 过渡页、4 导航页
            num = CastUtil.toInteger(sitePageMap.get("num"));//序号
            if(pageOperation.equals("add")){//页面操作[pageOperation]为"新增[add]"时,页面类型和序号必填
                ValidUtil.valid("页面类型[pageType]", pageType, "{'required':true,'numeric':true}");
                ValidUtil.valid("序号[num]", num, "{'required':true,'numeric':true}");
            }else if(pageOperation.equals("edit")){//页面操作[pageOperation]为"编辑[edit]"时,站点页面表主键,页面类型和序号必填
                ValidUtil.valid("站点页面表主键[pageId]", pageId, "{'required':true,'numeric':true}");
                ValidUtil.valid("页面类型[pageType]", pageType, "{'required':true,'numeric':true}");
                ValidUtil.valid("序号[num]", num, "{'required':true,'numeric':true}");
            }else if(pageOperation.equals("remove")){//页面操作[pageOperation]为"删除[remove]"时,站点页面表主键必填
                ValidUtil.valid("站点页面表主键[pageId]", pageId, "{'required':true,'numeric':true}");
            }else{//页面操作不在add/edit/remove范围内
                throw new BizException("E2600015", MessageUtil.getMessage("E2600015"));//站点页面操作类型错误！
            }
            componentList = (List<Map<String, Object>>) sitePageMap.get("components");
            componentSize = componentList.size();
            
            //2.循环组件
            for (int j = 0; j < componentSize; j++) {
                componentMap = componentList.get(j);
                
                componentOperation = (String) componentMap.get("componentOperation");//组件操作：add 新增、edit 编辑、remove 删除
                ValidUtil.valid("组件操作[componentOperation]", componentOperation, "required");
                
                componentId = CastUtil.toLong(componentMap.get("componentId"));//组件表主键id，数字，不允许为空
                sitePageComponentId = CastUtil.toLong(componentMap.get("sitePageComponentId"));//页面组件表主键id
                json = JsonUtil.toJson(componentMap.get("json"));//组件配置参数--JSON数据
                orderNo = CastUtil.toInteger(componentMap.get("orderNo"));//排序号
                if(componentOperation.equals("add")){//组件操作[componentOperation]为"新增[add]"时
                    ValidUtil.valid("组件表主键[componentId]", componentId, "{'required':true,'numeric':true}");
                    ValidUtil.valid("组件配置参数[json]", json, "required");
                    ValidUtil.valid("组件排序号[orderNo]", orderNo, "{'required':true,'numeric':true}");
                }else if(componentOperation.equals("edit")){//组件操作[componentOperation]为"编辑[edit]"时
                    ValidUtil.valid("页面组件表主键[sitePageComponentId]", sitePageComponentId, "{'required':true,'numeric':true}");
                    ValidUtil.valid("组件表主键[componentId]", componentId, "{'required':true,'numeric':true}");
                    ValidUtil.valid("组件配置参数[json]", json, "required");
                    ValidUtil.valid("组件排序号[orderNo]", orderNo, "{'required':true,'numeric':true}");
                }else if(componentOperation.equals("remove")){//组件操作[componentOperation]为"删除[remove]"时
                    ValidUtil.valid("页面组件表主键[sitePageComponentId]", sitePageComponentId, "{'required':true,'numeric':true}");
                }else{
                    throw new BizException("E2600016", MessageUtil.getMessage("E2600016"));//组件操作类型错误！
                }
            }
        }
    }
    
    
    /**
     * 添加行业站点
     * @param bodyParam 参数
     * @param request 请求
     * @return 站点id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 上午9:36:22
     */
    public Long addIndustry(Map<String, Object> bodyParam, HttpServletRequest request) throws Exception{
        String priIndustryCode = (String) bodyParam.get("priIndustryCode");//一级行业编号，不允许为空
        String secIndustryCode = (String) bodyParam.get("secIndustryCode");//二级行业编号，允许为空
        String siteName = (String) bodyParam.get("siteName");//站点名称
        //参数校验
        ValidUtil.valid("一级行业[priIndustryCode]", priIndustryCode, "required");
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        if(StringUtils.isNotBlank(secIndustryCode)){//二级行业不为空 判断二级行业站点是否存在
            if(isSecIndustrySiteExist(secIndustryCode)){
                throw new BizException("E2600003", MessageUtil.getMessage("E2600003", secIndustryCode));//行业站点{0}已存在，不允许再次添加!
            }
        }else if(isPriIndustrySiteExist(priIndustryCode)){
            throw new BizException("E2600003", MessageUtil.getMessage("E2600003", priIndustryCode));//行业站点{0}已存在，不允许再次添加!
        }else{
            secIndustryCode = null;//设置二级行业为空，用于sql判断 is null
        }
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        Site site = new Site();
        site.setSiteName(siteName);
        site.setDefaultSite(2);
        site.setPriIndustryCode(priIndustryCode);
        site.setSecIndustryCode(secIndustryCode);
        site.setStatus(3);//已发布
        siteDao.add(site);;//添加行业站点
        Long siteId = site.getId();
        dealPage(siteId,sitePageList,request);
        return siteId;
    }
    
    /**
     * 添加地区站点
     * @param bodyParam 参数
     * @param request 请求
     * @return 站点id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 上午10:06:55
     */
    public Long addLocation(Map<String, Object> bodyParam, HttpServletRequest request) throws Exception{
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));
        String siteName = (String) bodyParam.get("siteName");//站点名称
        //参数校验
        ValidUtil.valid("省[provinceId]", provinceId, "{'required':true,'numeric':true}");
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        if(cityId != null){
            if(isCitySiteExist(cityId)){
                throw new BizException("E2600004", MessageUtil.getMessage("E2600004", cityId));//区域站点{0}已存在，不允许再次添加!
            }
        }else if(isProvinceSiteExist(provinceId)){
            throw new BizException("E2600004", MessageUtil.getMessage("E2600004", provinceId));//区域站点{0}已存在，不允许再次添加!
        }
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        Site site = new Site();
        site.setSiteName(siteName);
        site.setDefaultSite(3);
        site.setProvinceId(provinceId);
        site.setCityId(cityId);
        site.setStatus(3);//已发布
        siteDao.add(site);;//添加地区站点
        Long siteId = site.getId();
        dealPage(siteId,sitePageList,request);
        return siteId;
    }
    
    /**
     * 添加站点
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @return 站点id
     * @throws Exception 
     * @date 2017年4月25日 上午9:04:41
     */
    public Long add(Map<String, Object> bodyParam, HttpServletRequest request) throws Exception{
        String siteName = (String) bodyParam.get("siteName");//站点名称
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户id
        String cascadeLabel = (String) bodyParam.get("cascadeLabel");//商户层级关系，不允许为空
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("商户层级[cascadeLabel]", cascadeLabel, "required");
        if(isSiteNameExist(siteName,merchantId)){
            throw new BizException("E2600005", MessageUtil.getMessage("E2600005",siteName));//站点{0}已存在，不允许再次添加!
        }
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        Site site = new Site();
        site.setSiteName(siteName);
        site.setMerchantId(merchantId);
        site.setCascadeLabel(cascadeLabel);
        site.setDefaultSite(-1);//商户站点
        site.setStatus(1);//待审核
        siteDao.add(site);;//添加商户站点
        Long siteId = site.getId();
        dealPage(siteId,sitePageList,request);
        return siteId;
    }
    
    /**
     * 站点页面内容保存、更新、删除
     * @param siteId 站点id
     * @param sitePageList 站点页列表
     * @param request 请求
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月24日 下午7:29:33
     */
    public void dealPage(Long siteId, List<Map<String,Object>> sitePageList,HttpServletRequest request) throws Exception{
        ServletContext servletContext = request.getServletContext();
        String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹路径
        int sitePageSize = sitePageList.size();
        Map<String,Object> sitePageMap = null;
        Map<String,Object> componentMap = null;
        
        Long pageId = null;//站点页面表主键id
        Integer pageType = null;//页面类型：1 引导页、2 认证页、3 过渡页、4 导航页
        Integer num = null;//序号
        String pageOperation = null;//页面操作：add 新增、edit 编辑、remove 删除
        
        String sitePath = getSitePath(siteId);//生成站点相对路径
        
        SitePage sitePage = null;
        SitePageComponent spc = null;//站点页组件
        Long spcId = null;//新建站点页面组件id
        
        String sitePageKey = null;//key
        String sitePagePath = null;//站点页相对路径
        String sitePageFullPath = null;//站点页绝对路径
        String siteHtmlPath = null;//site.html绝对路径
        String sitePageTemplateName = "site.html";
        String sitePageTemplatePath = servletContext.getRealPath("/html/tool/site.html"); //基础的模板页路径
        
        Long componentId = null;//组件id
        Long sitePageComponentId = null;//站点页面组件表主键id
        String componentOperation = null;//组件操作：add 新增、edit 编辑、remove 删除
        String json = null;//组件配置参数--JSON数据
        Integer orderNo = null;//排序号
        
        String componentFullPath = null;//组件绝对路径
        String componentCode = null;//组件唯一码
        
        String cssPath = null;
        String cssFilePath = null;
        String cssBaseFilePath = null;
        
        String imagePath = null;// /service/media/site/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/image
        String imageBasePath = null;// /service/media/component/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/image
        
        String scriptPath = null;// /service/media/site/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/script/
        String scriptFilePath = null;// /service/media/site/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/script/portal.min.js
        String scriptFileBasePath = null;//  /service/media/component/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/script/Entity.js
        String appendScript = null;//5c5a6c7486944328bae247d7191a73ed({},componentDivId_0).render();
        
        
        List<Map<String,Object>> componentList = null;//组件列表
        int componentSize;//一页组件个数
        
        StringBuilder appendHtml = new StringBuilder();
        //1 站点页面json数据循环处理
        for (int i = 0; i < sitePageSize; i++) {
            sitePageMap = sitePageList.get(i);//站点页
            
            pageId = CastUtil.toLong(sitePageMap.get("pageId"));//站点页面表主键id
            pageType = CastUtil.toInteger(sitePageMap.get("pageType"));//页面类型：1 引导页、2 认证页、3 过渡页、4 导航页
            num = CastUtil.toInteger(sitePageMap.get("num"));//序号
            pageOperation = (String) sitePageMap.get("pageOperation");//页面操作：add 新增、edit 编辑、remove 删除
            
            if(pageOperation.equals("add")){//1.1页面操作[pageOperation]为"新增[add]"时
                sitePageKey = KeyUtil.generateKey();//生成站点页面key
                sitePagePath = sitePath + "/" + sitePageKey;//生成站点页面文件相对路径
                sitePageFullPath = resourcesFolderPath + sitePagePath ;//生成站点页面文件绝对路径
                siteHtmlPath = sitePageFullPath + "/" + sitePageTemplateName;//site.html绝对路径
                IOUtil.fileChannelCopy(sitePageTemplatePath, siteHtmlPath);//将站点模板页面[sitePageTemplatePath]拷贝至站点页面文件绝对路径[siteHtmlPath]
                sitePage = new SitePage();
                sitePage.setSiteId(siteId);//站点id
                sitePage.setNum(num);//页面序号
                sitePage.setPageType(pageType);//页面类型
                sitePage.setPagePath(sitePagePath);//页面保存路径
                siteDao.insertSitePage(sitePage);//保存站点页
                pageId = sitePage.getPageId();//站点主键id
                logger.debug("新增站点页，站点页id="+pageId+" 新增站点页的文件数据路径："+sitePageFullPath);
            }else if(pageOperation.equals("edit")){//1.2页面操作[pageOperation]为"编辑[edit]"时
                /*if(pageId == null){//站点页主键id为空 抛异常
                    throw new BizException("E2600001", MessageUtil.getMessage("E2600001", "edit"));//站点页面{0}操作时，pageId不允许为空!
                }*/
                sitePagePath = siteDao.getPagePath(pageId);//通过pageId获取站点页面路径[sitePagePath]
                siteDao.updatePageNum(pageId,num);//更新站点页的序号
                sitePageFullPath = resourcesFolderPath + sitePagePath;
                IOUtil.remove(sitePageFullPath);//删除站点页面路径[sitePagePath]下的文件
                siteHtmlPath = sitePageFullPath +  "/" + sitePageTemplateName;//生成站点页面文件绝对路径
                IOUtil.fileChannelCopy(sitePageTemplatePath, siteHtmlPath);//将站点模板页面[sitePageTemplatePath]拷贝至站点页面文件绝对路径[sitePageFullPath]
                logger.debug("修改站点页，站点页id="+pageId+" 删除站点页的文件数据路径："+sitePageFullPath);
            }else if(pageOperation.equals("remove")){//1.3页面操作[pageOperation]为"删除[remove]"时
                /*if(pageId == null){
                    throw new BizException("E2600001", MessageUtil.getMessage("E2600001", "edit"));//站点页面{0}操作时，pageId不允许为空!
                }*/
                sitePagePath = siteDao.getPagePath(pageId);//通过pageId获取站点页面路径[sitePagePath]
                if(StringUtils.isNotBlank(sitePagePath)){
                    sitePageFullPath = resourcesFolderPath + sitePagePath;//站点页面绝对路径
                    IOUtil.remove(sitePageFullPath);//删除站点页面路径[sitePagePath]下的文件
                    logger.debug("删除站点页下面所有的数据，pageId="+pageId+" 删除模板页数据路径："+sitePageFullPath);
                }
                siteDao.deletePageComponent(pageId);//删除站点页组件
                siteDao.deletePage(pageId);//删除站点页
                continue;
            }
            componentList = (List<Map<String, Object>>) sitePageMap.get("components");
            componentSize = componentList.size();
            //1.4循环组件
            for (int j = 0; j < componentSize; j++) {
                componentMap = componentList.get(j);
                componentId = CastUtil.toLong(componentMap.get("componentId"));
                sitePageComponentId = CastUtil.toLong(componentMap.get("sitePageComponentId"));//组件页面组件表主键id
                componentOperation = (String) componentMap.get("componentOperation");//组件操作：add 新增、edit 编辑、remove 删除
                json = JsonUtil.toJson(componentMap.get("json"));//组件配置参数--JSON数据
                orderNo = CastUtil.toInteger(componentMap.get("orderNo"));//排序号
                if(componentOperation.equals("add")){//组件操作[componentOperation]为"新增[add]"时
                    spc = new SitePageComponent();
                    spc.setJson(json);
                    spc.setOrderNo(orderNo);
                    spc.setSitePageId(pageId);
                    spc.setComponentId(componentId);
                    siteDao.insertSitePageComp(spc);
                    spcId = spc.getSitePageComponentId();
                    siteDao.updateCascadeLabel(spcId, spcId.toString());
                }else if(componentOperation.equals("edit")){//组件操作[componentOperation]为"编辑[edit]"时
                    /*if(sitePageComponentId == null){
                        throw new BizException("E2600012", MessageUtil.getMessage("E2600012", "edit"));//组件{0}操作时，sitePageComponentId不允许为空!
                    }*/
                    siteDao.updateSitePageComp(json,orderNo,sitePageComponentId);
                }else if(componentOperation.equals("remove")){//组件操作[componentOperation]为"删除[remove]"时
                    /*if(sitePageComponentId == null){
                        throw new BizException("E2600012", MessageUtil.getMessage("E2600012", "remove"));//组件{0}操作时，sitePageComponentId不允许为空!
                    }*/
                    siteDao.deletePageComponentById(sitePageComponentId);
                    continue;
                }
                Component component = componentService.getById(componentId);
                componentFullPath = resourcesFolderPath + component.getComponentPath();
                componentCode = component.getCode();
                //==========================处理组建样式文件===================================//
                cssPath = sitePageFullPath+ "/"+"css"+"/"; //拷贝css文件文件到指定目录
                cssFilePath = cssPath +"portal.min.css"; //拷贝的组建样式文件
                cssBaseFilePath = componentFullPath + "/"+"css" + "/" + "component.css"; //基础组件样式路径
                if(new File(cssPath).isDirectory() && new File(cssPath).listFiles().length>0){
                    IOUtil.mergeFiles(cssFilePath, new String[]{cssBaseFilePath});
                }else{
                    IOUtil.fileChannelCopy(cssBaseFilePath, cssFilePath);
                }
                logger.debug("样式文件组装路径："+cssFilePath);
                //==========================处理图片========================================//
                imagePath = sitePageFullPath + "/" + "image";
                imageBasePath = componentFullPath + "/"+"image";//基础组件图片路径
                IOUtil.copyDirectiory(imageBasePath, imagePath);
                logger.debug("图片文件组装路径："+imageBasePath);
                //==========================js处理=========================================//
                //script 把组建中的js拷贝过来 ，然后追加渲染js操作 文本，_5c5a6c7486944328bae247d7191a73ed({json},componentDivId_0).render();
                scriptPath = sitePageFullPath + "/" + "script" + "/";
                scriptFilePath = scriptPath +"portal.min.js"; 
                scriptFileBasePath = componentFullPath + "/"+"script" + "/" + "Entity.js";
                appendScript = componentCode+"("+json+",'divComponent_"+j+"').render();"+ System.getProperty("line.separator");
                if(new File(scriptPath).isDirectory() && new File(scriptPath).listFiles().length>0){
                    IOUtil.mergeFiles(scriptFilePath, new String[]{scriptFileBasePath});
                    logger.debug("组建编号："+j+" 组建目标文件："+scriptFilePath+" ,将要合并的js文件地址："+scriptFileBasePath);
                    IOUtil.fileAppendContent(scriptFilePath, appendScript);
                }else{
                    IOUtil.fileChannelCopy(scriptFileBasePath, scriptFilePath);
                    IOUtil.fileAppendContent(scriptFilePath, appendScript);
                }
                logger.debug("js文件组装路径："+scriptFilePath);
                appendHtml.append("<div id=divComponent_"+j+"></div>");
            }
            if(StringUtils.isNotBlank(appendHtml.toString())){
                IOUtil.replaceContentToFile(siteHtmlPath, "{@componentDivs@}", appendHtml.toString());
                appendHtml.delete(0, appendHtml.length());
            }
        }
    }
    
    /**
     * 获取站点相对路径
     * @param siteId 站点id
     * @return 站点页相对路径
     * @author 周颖  
     * @date 2017年4月24日 下午1:28:30
     */
    private String getSitePath(Long siteId) {
        StringBuffer sitePath = new StringBuffer();
        sitePath.append("/media/site/");
        String date = DateUtil.getTodayDate();
        String[] dataArray = date.split("-");
        String year = dataArray[0];//年
        String month = dataArray[1];//月
        String day = dataArray[2];//日
        sitePath.append(year).append("/").append(month).append("/").append(day).append("/").append(siteId);
        return sitePath.toString();
    }
   
    /**
     * 更新站点状态
     * @param siteId 站点id
     * @param status 状态
     * @author 周颖  
     * @date 2017年4月24日 上午9:26:05
     */
    public void updateStatusById(Long siteId,Integer status){
        siteDao.updateStatusById(siteId,status);
    }
    
    /**
     * 编辑默认站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 下午1:16:34
     */
    public void updateDefault(Long id, Map<String, Object> bodyParam,HttpServletRequest request) throws Exception{
        String siteName = (String) bodyParam.get("siteName");//站点名称
        //参数校验
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        siteDao.updateSiteName(id,siteName);;//修改站点名称
        dealPage(id,sitePageList,request);
    }
    
    /**
     * 编辑地区站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 下午2:27:49
     */
    public void updateLocation(Long id, Map<String, Object> bodyParam, HttpServletRequest request) throws Exception{
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));
        String siteName = (String) bodyParam.get("siteName");//站点名称
        //参数校验
        ValidUtil.valid("省[provinceId]", provinceId, "{'required':true,'numeric':true}");
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        if(cityId != null){
            if(isCitySiteExist(id,cityId)){
                throw new BizException("E2600004", MessageUtil.getMessage("E2600004", cityId));//区域站点{0}已存在，不允许再次添加!
            }
        }else if(isProvinceSiteExist(id,provinceId)){
            throw new BizException("E2600004", MessageUtil.getMessage("E2600004", provinceId));//区域站点{0}已存在，不允许再次添加!
        }
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        clearLocationSiteCache(id);//更新站点前，清空redis缓存
        siteDao.updateLocation(id,siteName,provinceId,cityId);//更新站点基本信息
        dealPage(id,sitePageList,request);
    }
    
    /**
     * 编辑行业站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 下午2:46:07
     */
    public void updateIndustry(Long id, Map<String, Object> bodyParam, HttpServletRequest request) throws Exception{
        String priIndustryCode = (String) bodyParam.get("priIndustryCode");//一级行业编号，不允许为空
        String secIndustryCode = (String) bodyParam.get("secIndustryCode");//二级行业编号，允许为空
        String siteName = (String) bodyParam.get("siteName");//站点名称
        //参数校验
        ValidUtil.valid("一级行业[priIndustryCode]", priIndustryCode, "required");
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        if(StringUtils.isNotBlank(secIndustryCode)){//二级行业不为空 判断二级行业站点是否存在
            if(isSecIndustrySiteExist(id,secIndustryCode)){
                throw new BizException("E2600003", MessageUtil.getMessage("E2600003", secIndustryCode));//行业站点{0}已存在，不允许再次添加!
            }
        }else if(isPriIndustrySiteExist(id,priIndustryCode)){
            throw new BizException("E2600003", MessageUtil.getMessage("E2600003", priIndustryCode));//行业站点{0}已存在，不允许再次添加!
        }else{
            secIndustryCode = null;//设置二级行业为空，用于sql判断 is null
        }
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        clearIndustrySiteCache(id);//更新站点前，清空redis缓存
        siteDao.updateIndustry(id,siteName,priIndustryCode,secIndustryCode);
        dealPage(id,sitePageList,request);
    }
    
    /**
     * 编辑站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖
     * @return 站点id
     * @throws Exception   
     * @date 2017年4月25日 下午3:53:27
     */
    public Long update(Long id, Map<String, Object> bodyParam,HttpServletRequest request) throws Exception{
        String siteName = (String) bodyParam.get("siteName");//站点名称
        ValidUtil.valid("站点名称[siteName]", siteName, "{'required':true,'regex':'"+RegexConstants.SITE_NAME_PATTERN+"'}");
        Integer defaultSite = siteDao.getType(id);
        List<Map<String,Object>> sitePageList = (List<Map<String, Object>>) bodyParam.get("pages");//站点页列表
        validatePages(sitePageList);//校验站点页参数
        if(defaultSite == -1){//编辑自己建的站点
            siteDao.updateSiteName(id,siteName);;//修改站点名称
            dealPage(id,sitePageList,request);
            return id;
        }
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Long merchantId = user.getMerchantId();
        if(merchantId == null){
            throw new BizException("E2000037", MessageUtil.getMessage("E2000037"));//用户信息中的商户id[merchantId]不允许为空!
        }
        Site site = new Site();
        site.setSiteName(siteName);
        site.setMerchantId(merchantId);
        site.setCascadeLabel(user.getCascadeLabel());
        site.setDefaultSite(-1);//商户站点
        site.setStatus(1);//待审核
        siteDao.add(site);;//添加商户站点
        Long siteId = site.getId();
        addPage(siteId,sitePageList,request);
        return siteId;
    }
    
    /**
     * 根据默认、行业、地区站点建的站点
     * @param siteId 站点id
     * @param sitePageList 站点页面数据
     * @param request 请求
     * @author 周颖  
     * @date 2017年4月25日 下午6:13:20
     */
    private void addPage(Long siteId, List<Map<String,Object>> sitePageList,HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹路径
        int sitePageSize = sitePageList.size();
        Map<String,Object> sitePageMap = null;
        Map<String,Object> componentMap = null;
        
        Long pageId = null;//站点页面表主键id
        Integer pageType = null;//页面类型：1 引导页、2 认证页、3 过渡页、4 导航页
        Integer num = null;//序号
        String pageOperation = null;//页面操作：add 新增、edit 编辑、remove 删除
        
        String sitePath = getSitePath(siteId);//生成站点相对路径
        
        SitePage sitePage = null;//站点页
        SitePageComponent spc = null;//站点页组件
        Long spcId = null;//新建站点页面组件主键id
        
        String sitePageKey = null;//key
        String sitePagePath = null;//站点页相对路径
        String sitePageFullPath = null;//站点页绝对路径
        String siteHtmlPath = null;//site.html绝对路径
        String sitePageTemplateName = "site.html";
        String sitePageTemplatePath = servletContext.getRealPath("/html/tool/site.html"); //基础的模板页路径
        
        Long componentId = null;//组件id
        Long sitePageComponentId = null;//站点页面组件表主键id
        String componentOperation = null;//组件操作：add 新增、edit 编辑、remove 删除
        String json = null;//组件配置参数--JSON数据
        Integer orderNo = null;//排序号
        
        String componentFullPath = null;//组件绝对路径
        String componentCode = null;//组件唯一码
        
        String cssPath = null;
        String cssFilePath = null;
        String cssBaseFilePath = null;
        
        String imagePath = null;// /service/media/site/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/image
        String imageBasePath = null;// /service/media/component/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/image
        
        String scriptPath = null;// /service/media/site/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/script/
        String scriptFilePath = null;// /service/media/site/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/script/portal.min.js
        String scriptFileBasePath = null;//  /service/media/component/2015/12/30/_201bf15ea3bf437fa7d22b0283319ee5/script/Entity.js
        String appendScript = null;//5c5a6c7486944328bae247d7191a73ed({},componentDivId_0).render();
        
        
        List<Map<String,Object>> componentList = null;//组件列表
        int componentSize;//一页组件个数
        
        StringBuilder appendHtml = new StringBuilder();
        //1 站点页面json数据循环处理
        for (int i = 0; i < sitePageSize; i++) {
            sitePageMap = sitePageList.get(i);//模板页
            
            //pageId = CastUtil.toLong(sitePageMap.get("pageId"));//站点页面表主键id
            pageType = CastUtil.toInteger(sitePageMap.get("pageType"));//页面类型：1 引导页、2 认证页、3 过渡页、4 导航页
            num = CastUtil.toInteger(sitePageMap.get("num"));//序号
            pageOperation = (String) sitePageMap.get("pageOperation");//页面操作：add 新增、edit 编辑、remove 删除
            
            if(pageOperation.equals("add") || pageOperation.equals("edit")){//1.1页面操作[pageOperation]为"新增[add]或者编辑[edit]"时
                sitePageKey = KeyUtil.generateKey();//生成站点页面key
                sitePagePath = sitePath + "/" + sitePageKey;//生成站点页面文件相对路径
                sitePageFullPath = resourcesFolderPath + sitePagePath ;//生成站点页面文件绝对路径
                siteHtmlPath = sitePageFullPath + "/" + sitePageTemplateName;//site.html绝对路径
                IOUtil.fileChannelCopy(sitePageTemplatePath, siteHtmlPath);//将站点模板页面[sitePageTemplatePath]拷贝至站点页面文件绝对路径[siteHtmlPath]
                sitePage = new SitePage();
                sitePage.setSiteId(siteId);//站点id
                sitePage.setNum(num);//页面序号
                sitePage.setPageType(pageType);//页面类型
                sitePage.setPagePath(sitePagePath);//页面保存路径
                siteDao.insertSitePage(sitePage);//保存站点页
                pageId = sitePage.getPageId();//站点主键id
                logger.debug("新增站点页，站点页id="+pageId+" 新增站点页的文件数据路径："+sitePageFullPath);
            }else{
                continue;
            }
            componentList = (List<Map<String, Object>>) sitePageMap.get("components");
            componentSize = componentList.size();
            //1.4循环组件
            for (int j = 0; j < componentSize; j++) {
                componentMap = componentList.get(j);
                componentId = CastUtil.toLong(componentMap.get("componentId"));
                sitePageComponentId = CastUtil.toLong(componentMap.get("sitePageComponentId"));//组件页面组件表主键id
                componentOperation = (String) componentMap.get("componentOperation");//组件操作：add 新增、edit 编辑、remove 删除
                json = JsonUtil.toJson(componentMap.get("json"));//组件配置参数--JSON数据
                orderNo = CastUtil.toInteger(componentMap.get("orderNo"));//排序号
                if(componentOperation.equals("add") || componentOperation.equals("edit")){//组件操作[componentOperation]为"新增[add]或者编辑[edit]"时
                    spc = new SitePageComponent();
                    spc.setJson(json);
                    spc.setOrderNo(orderNo);
                    spc.setSitePageId(pageId);
                    spc.setComponentId(componentId);
                    siteDao.insertSitePageComp(spc);
                    if(sitePageComponentId != null){//保存层级关系 同步json数据
                        spcId = spc.getSitePageComponentId();
                        siteDao.updateCascadeLabel(spcId,sitePageComponentId + "-" + spcId);
                    }else{
                        siteDao.updateCascadeLabel(spcId, spcId.toString());
                    }
                }else{
                    continue;
                }
                Component component = componentService.getById(componentId);
                componentFullPath =resourcesFolderPath + component.getComponentPath();
                componentCode = component.getCode();
                //==========================处理组建样式文件===================================//
                cssPath = sitePageFullPath+ "/"+"css"+"/"; //拷贝css文件文件到指定目录
                cssFilePath = cssPath +"portal.min.css"; //拷贝的组建样式文件
                cssBaseFilePath = componentFullPath + "/"+"css" + "/" + "component.css"; //基础组件样式路径
                if(new File(cssPath).isDirectory() && new File(cssPath).listFiles().length>0){
                    IOUtil.mergeFiles(cssFilePath, new String[]{cssBaseFilePath});
                }else{
                    IOUtil.fileChannelCopy(cssBaseFilePath, cssFilePath);
                }
                logger.debug("样式文件组装路径："+cssFilePath);
                //==========================处理图片========================================//
                imagePath = sitePageFullPath + "/" + "image";
                imageBasePath = componentFullPath + "/"+"image";//基础组件图片路径
                IOUtil.copyDirectiory(imageBasePath, imagePath);
                logger.debug("图片文件组装路径："+imageBasePath);
                //==========================js处理=========================================//
                //script 把组建中的js拷贝过来 ，然后追加渲染js操作 文本，_5c5a6c7486944328bae247d7191a73ed({json},componentDivId_0).render();
                scriptPath = sitePageFullPath + "/" + "script" + "/";
                scriptFilePath = scriptPath +"portal.min.js"; 
                scriptFileBasePath = componentFullPath + "/"+"script" + "/" + "Entity.js";
                appendScript = componentCode+"("+json+",'divComponent_"+j+"').render();"+ System.getProperty("line.separator");
                if(new File(scriptPath).isDirectory() && new File(scriptPath).listFiles().length>0){
                    IOUtil.mergeFiles(scriptFilePath, new String[]{scriptFileBasePath});
                    logger.debug("组建编号："+j+" 组建目标文件："+scriptFilePath+" ,将要合并的js文件地址："+scriptFileBasePath);
                    IOUtil.fileAppendContent(scriptFilePath, appendScript);
                }else{
                    IOUtil.fileChannelCopy(scriptFileBasePath, scriptFilePath);
                    IOUtil.fileAppendContent(scriptFilePath, appendScript);
                }
                logger.debug("js文件组装路径："+scriptFilePath);
                appendHtml.append("<div id=divComponent_"+j+"></div>");
            }
            if(StringUtils.isNotBlank(appendHtml.toString())){
                IOUtil.replaceContentToFile(siteHtmlPath, "{@componentDivs@}", appendHtml.toString());
                appendHtml.delete(0, appendHtml.length());
            }
        }
    }
    
    /**
     * 删除站点
     * @param id 站点id
     * @author 周颖  
     * @date 2017年4月25日 下午7:36:22
     */
    public void delete(Long id){
        siteDao.delete(id);
    }
    
    /**
     * 站点详情
     * @param id 主键id
     * @return 站点详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月26日 上午10:00:29
     */
    public Site getById(Long id) throws Exception{
        Site site = siteDao.getById(id);
        Long merchantId = site.getMerchantId();
        if(merchantId != null){
            site.setMerchantName(MerchantClient.getNameByIdCache(merchantId));
        }
        List<SitePage> sitePageList = siteDao.getSitePageList(id);//获取站点下的所有页面
        Long sitePageId = null;
        List<SitePageComponent> spcList = null;
        for(SitePage sitePage: sitePageList){//循环站点页
            sitePageId = sitePage.getPageId();
            spcList = siteDao.getSitePageComponentList(sitePageId);//获取一页下的所有组件
            sitePage.setComponets(spcList);
        }
        site.setPages(sitePageList);
        return site;
    }
    
    
    /**
     * 通过站点id获取商户信息
     * @param siteId 站点id
     * @return 商户信息  fk_customer_id,cascade_label 
     * @author 周颖  
     * @date 2017年4月28日 下午2:05:03
     */
    public Map<String, Object> getMerchantById(Long siteId){
        return siteDao.getMerchantById(siteId);
    }
    
    /**
     * 获取区域站点id
     * @param provinceId 省id
     * @param cityId 市id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:54:04
     */
    public Long getLocationSitIdCache(Long provinceId, Long cityId){
        Long siteId = null;
        if(cityId != null){
            siteId = this.getCitySitIdCache(cityId);
        }
        if(siteId == null && provinceId != null){
            siteId = this.getProvinceSitIdCache(provinceId);
        }
        return siteId;
    }
    
    /**
     * 获取区域站点id（省）
     * @param provinceId 省id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getProvinceSitId(Long provinceId){
        return siteDao.getProvinceSitId(provinceId);
    }
    
    /**
     * 获取区域站点id（省）(缓存)
     * @param provinceId 省id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getProvinceSitIdCache(Long provinceId){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_LOCATION_ID + provinceId;
        String siteIdCache = RedisUtil.get(redisKey);
        Long siteId = StringUtils.isNotBlank(siteIdCache) ? Long.parseLong(siteIdCache) : null;
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(siteId == null){
            siteId = this.getProvinceSitId(provinceId);
            if(siteId != null){
                RedisUtil.set(redisKey, siteId.toString(), RedisConstants.SITE_LOCATION_ID_TIME);
            }
        }
        return siteId;
    }
    
    /**
     * 获取区域站点id（市）
     * @param cityId 市id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getCitySitId(Long cityId){
        return siteDao.getCitySitId(cityId);
    }
    
    /**
     * 获取区域站点id（市）(缓存)
     * @param cityId 市id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getCitySitIdCache(Long cityId){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_LOCATION_ID + cityId;
        String siteIdCache = RedisUtil.get(redisKey);
        Long siteId = StringUtils.isNotBlank(siteIdCache) ? Long.parseLong(siteIdCache) : null;
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(siteId == null){
            siteId = this.getCitySitId(cityId);
            if(siteId != null){
                RedisUtil.set(redisKey, siteId.toString(), RedisConstants.SITE_LOCATION_ID_TIME);
            }
        }
        return siteId;
    }
    
    /**
     * 获取行业站点id
     * @param priIndustryCode 一级行业编号
     * @param secIndustryCode 二级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:54:04
     */
    public Long getIndustrySitIdCache(String priIndustryCode, String secIndustryCode){
        Long siteId = null;
        if(StringUtils.isNotBlank(secIndustryCode)){
            siteId = this.getSecIndustrySitIdCache(secIndustryCode);
        }
        if(siteId == null && StringUtils.isNotBlank(priIndustryCode)){
            siteId = this.getPriIndustrySitIdCache(priIndustryCode);
        }
        return siteId;
    }
    
    /**
     * 获取行业站点id（一级行业）
     * @param priIndustryCode 一级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getPriIndustrySitId(String priIndustryCode){
        return siteDao.getPriIndustrySitId(priIndustryCode);
    }
    
    /**
     * 获取行业站点id（一级行业）(缓存)
     * @param priIndustryCode 一级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getPriIndustrySitIdCache(String priIndustryCode){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_INDUSTRY_ID + priIndustryCode;
        String siteIdCache = RedisUtil.get(redisKey);
        Long siteId = StringUtils.isNotBlank(siteIdCache) ? Long.parseLong(siteIdCache) : null;
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(siteId == null){
            siteId = this.getPriIndustrySitId(priIndustryCode);
            if(siteId != null){
                RedisUtil.set(redisKey, siteId.toString(), RedisConstants.SITE_INDUSTRY_ID_TIME);
            }
        }
        return siteId;
    }
    
    /**
     * 获取行业站点id（二级行业）
     * @param secIndustryCode 二级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getSecIndustrySitId(String secIndustryCode){
        return siteDao.getSecIndustrySitId(secIndustryCode);
    }
    
    /**
     * 获取行业站点id（二级行业）(缓存)
     * @param secIndustryCode 二级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:18:25
     */
    private Long getSecIndustrySitIdCache(String secIndustryCode){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_INDUSTRY_ID + secIndustryCode;
        String siteIdCache = RedisUtil.get(redisKey);
        Long siteId = StringUtils.isNotBlank(siteIdCache) ? Long.parseLong(siteIdCache) : null;
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(siteId == null){
            siteId = this.getSecIndustrySitId(secIndustryCode);
            if(siteId != null){
                RedisUtil.set(redisKey, siteId.toString(), RedisConstants.SITE_INDUSTRY_ID_TIME);
            }
        }
        return siteId;
    }
    
    /**
     * 获取默认站点id
     * @return 默认站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:29:19
     */
    public Long getDefaultSitId(){
        return siteDao.getDefaultSitId();
    }
    
    /**
     * 获取默认站点id(缓存)
     * @return 默认站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:29:19
     */
    public Long getDefaultSitIdCache(){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_DEFAULT_ID;
        String defaultSiteIdStr = RedisUtil.get(redisKey);
        Long defaultSiteId = StringUtils.isNotBlank(defaultSiteIdStr) ? Long.parseLong(defaultSiteIdStr) : null;
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(defaultSiteId == null){
            defaultSiteId = this.getDefaultSitId();
            if(defaultSiteId != null){
                RedisUtil.set(redisKey, defaultSiteId.toString(), RedisConstants.SITE_DEFAULT_ID_TIME);
            }
        }
        return defaultSiteId;
    }
    
    /**
     * 获取站点名称
     * @param siteId 站点id
     * @return 站点名称
     * @author 许小满  
     * @date 2017年5月11日 下午8:12:09
     */
    public String getSiteName(Long siteId){
        return siteDao.getSiteName(siteId);
    }
    
    /**
     * 获取站点名称(缓存)
     * @param siteId 站点id
     * @return 站点名称
     * @author 许小满  
     * @date 2017年5月11日 下午8:12:09
     */
    public String getSiteNameCache(Long siteId){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_NAME + siteId;
        String siteName = RedisUtil.get(redisKey);
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(StringUtils.isBlank(siteName)){
            siteName = this.getSiteName(siteId);
            RedisUtil.set(redisKey, StringUtils.defaultString(siteName), RedisConstants.SITE_NAME_TIME);
        }
        return siteName;
    }
    
    /**
     * 查询 站点首页面
     * @param siteId 站点id
     * @return 站点页面对象
     * @author 许小满  
     * @date 2017年5月11日 下午8:25:19
     */
    public SitePage getFirstSitePage(Long siteId){
        SitePage sitePage = siteDao.getFirstPage(siteId);
        if(sitePage == null){
            return null;
        }
        sitePage.setSiteId(siteId);//站点id
        return sitePage;
    }
    
    /**
     * 查询 站点首页面(缓存)
     * @param siteId 站点id
     * @return 站点页面对象
     * @author 许小满  
     * @date 2017年5月11日 下午8:25:19
     */
    public SitePage getFirstSitePageCache(Long siteId){
        //1. 从缓存中获取
        String key = RedisConstants.SITE_FIRST_PAGE + siteId;//redis key
        SitePage sitePage = getSitePageFromCache(key);
        //1.1 如果存在，直接使用缓存中的站点页面信息
        if(sitePage != null){
            return sitePage;
        }
        //1.2 如果不存在，通过接口获取站点页面信息并缓存
        else{
            sitePage = getFirstSitePage(siteId);//从接口中获取
            putSitePageToCache(key, sitePage, RedisConstants.SITE_FIRST_PAGE_TIME);//将站点页面信息存放到缓存中
            return sitePage;
        }
    }
    
    /**
     * 获取站点下一页
     * @param siteId 站点id
     * @param pageType 页面类型
     * @param num 序号
     * @return 站点页面
     * @author 许小满  
     * @date 2017年5月11日 下午8:18:26
     */
    public SitePage getNextPage(Long siteId, Integer pageType, Integer num){
        SitePage sitePage = siteDao.getNextPage(siteId, pageType, num);;
        if(sitePage != null){
            sitePage.setSiteId(siteId);
        }
        return sitePage;
    }
    
    /**
     * 获取站点下一页(缓存)
     * @param siteId 站点id
     * @param pageType 页面类型
     * @param num 序号
     * @return 站点页面
     * @author 许小满  
     * @date 2017年5月11日 下午8:18:26
     */
    public SitePage getNextPageCache(Long siteId, Integer pageType, Integer num){
        //1. 从缓存中获取
        String key = RedisConstants.SITE_NEXT_PAGE + siteId + "_" + (pageType != null ? pageType : StringUtils.EMPTY) + "_" + (num != null ? num : StringUtils.EMPTY);//redis key
        SitePage sitePage = getSitePageFromCache(key);
        //1.1 如果存在，直接使用缓存中的站点页面信息
        if(sitePage != null){
            return sitePage;
        }
        //1.2 如果不存在，通过接口获取站点页面信息并缓存
        else{
            sitePage = getNextPage(siteId, pageType, num);//从接口中获取
            putSitePageToCache(key, sitePage, RedisConstants.SITE_NEXT_PAGE_TIME);//将站点页面信息存放到缓存中
            return sitePage;
        }
    }
    
    /**
     * 从缓存中获取站点页面信息
     * @param key 缓存key
     * @return 站点页面
     * @author 许小满  
     * @date 2016年8月1日 上午9:00:30
     */
    private SitePage getSitePageFromCache(String key){
        List<String> columnList = RedisUtil.hmget(key, "id","pagePath","pageType","num","siteId");
        if(columnList == null || columnList.size() <= 0){
            return null;
        }
        //从缓存中获取站点页面信息
        String id = columnList.get(0);//站点页面id
        if(StringUtils.isBlank(id)){
            return null;
        }
        String pagePath = columnList.get(1);//页面html路径
        String pageTypeStr = columnList.get(2);//页面类别
        String numStr = columnList.get(3);//页面序号
        String siteIdStr = columnList.get(4);//站点id
        
        //封装站点页面信息
        SitePage sitePage = new SitePage();
        sitePage.setPageId(Long.parseLong(id));//站点页面id
        sitePage.setPagePath(StringUtils.defaultString(pagePath));//页面html路径
        sitePage.setPageType(StringUtils.isNotBlank(pageTypeStr) ? Integer.parseInt(pageTypeStr) : null);//页面类别
        sitePage.setNum(StringUtils.isNotBlank(numStr) ? Integer.parseInt(numStr) : null);//页面序号
        sitePage.setSiteId(StringUtils.isNotBlank(siteIdStr) ? Long.parseLong(siteIdStr) : null);//站点id
        return sitePage;
    }
    
    /**
     * 将站点页面信息存入redis缓存中
     * @param key redis key
     * @param sitePage 站点页面
     * @param time 缓存时间
     * @author 许小满  
     * @date 2017年5月11日 下午8:23:37
     */
    private void putSitePageToCache(String key, SitePage sitePage, int time){
        if(sitePage == null){
            return;
        }
        Long sitePageId = sitePage.getPageId();//站点页面id
        Long siteId = sitePage.getSiteId();//站点id
        
        Map<String,String> map = new HashMap<String,String>(16);
        map.put("id", sitePageId != null ? sitePageId.toString() : StringUtils.EMPTY);//站点页面id
        map.put("pagePath", StringUtils.defaultString(sitePage.getPagePath()));//页面html路径
        
        Integer pageTypeDB = sitePage.getPageType();//页面类型
        map.put("pageType", pageTypeDB != null ? pageTypeDB.toString() : StringUtils.EMPTY);//页面类型
        
        Integer numDB = sitePage.getNum();//页面序号
        map.put("num", numDB != null ? numDB.toString() : StringUtils.EMPTY);//页面序号
        
        map.put("siteId", siteId != null ? siteId.toString() : StringUtils.EMPTY);//站点id
        
        RedisUtil.hmset(key, map, time);
    }
    
    /**
     * 审核获取页面基本信息
     * @param id 站点id
     * @param params 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年5月2日 下午3:16:55
     */
    public SitePage getSitePageForPreview(Long id, String params){
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageType = CastUtil.toInteger(paramsMap.get("pageType"));//页面类型
        Integer num = CastUtil.toInteger(paramsMap.get("num"));//页面排序号
        String option = (String)paramsMap.get("option");//标识 下一页nextpage 上一页prevpage
        SitePage sitePage = new SitePage();
        //页面类型和序号都不为空时
        if(pageType != null && num != null){
            if(StringUtils.equals(option,"prevpage")){//拉取站点上一页
                sitePage = siteDao.getPrevSitePage(id, pageType, num);
            }else if(StringUtils.equals(option,"nextpage")){//拉取站点下一页
                sitePage = getNextPageCache(id, pageType, num);
            }
        }else{
            //其余，拉取站点首页
            sitePage = getFirstSitePageCache(id);
        }
        if(sitePage == null){
            throw new BizException("E2600011", MessageUtil.getMessage("E2600011"));//您尚未保存站点信息，请先保存站点，再进行预览！
        }
        Long pageId = sitePage.getPageId();
        sitePage.setUrl("/portalsrv/site/page/" + pageId);//补充显示站点页的URI
        Long firstPageId = getFirstSitePageCache(id).getPageId();
        if(pageId.equals(firstPageId)){//首页
            sitePage.setPosition("first");
            return sitePage;
        }
        Long lastPageId = siteDao.getLastPageId(id);//获取最后一页的id
        if(pageId.equals(lastPageId)){//末页
            sitePage.setPosition("last");
            return sitePage;
        }
        sitePage.setPosition("middle");//中间页
        return sitePage;
    }
    
    /**
     * 获取站点页路径
     * @param id 站点页id
     * @return 路径
     * @author 周颖  
     * @date 2017年5月2日 下午5:15:53
     */
    public String getPagePath(Long id){
        return siteDao.getPagePath(id);
    }
    
    /**
     * 获取站点缩略图路径
     * @param id 站点id
     * @return 缩略图路径
     * @author 周颖  
     * @date 2017年5月24日 下午2:36:51
     */
    public String getThumbPath(Long id){
        return siteDao.getThumbPath(id);
    }
    
    /**
     * 保存站点缩略图路径
     * @param id 站点id
     * @param thumbPath 缩略图路径
     * @author 周颖  
     * @date 2017年6月5日 上午11:06:39
     */
    public void saveThumbPath(Long id, String thumbPath){
        siteDao.saveThumbPath(id,thumbPath);
    }
    
    /**
     * 更新或删除站点前，清空行业站点对应的redis缓存
     * @param id 站点id
     * @author 许小满  
     * @date 2017年6月5日 下午11:41:18
     */
    public void clearIndustrySiteCache(Long id) {
        Site site = siteDao.getById(id);//通过站点id获取站点信息
        String priIndustryCode = site.getPriIndustryCode();//一级行业编号
        String secIndustryCode = site.getSecIndustryCode();//二级行业编号
        String redisKey = null;//缓存key
        if(StringUtils.isNotBlank(secIndustryCode)){//二级行业编号 不为空时
            redisKey = RedisConstants.SITE_INDUSTRY_ID + secIndustryCode;
        } else if (StringUtils.isNotBlank(priIndustryCode)){//一级行业编号  不为空时
            redisKey = RedisConstants.SITE_INDUSTRY_ID + priIndustryCode;
        } else {
            logger.error("错误：站点id["+id+"]对应的一级、二级行业同时为空！");
        }
        if(redisKey == null){//缓存key为空时，流程结束
            return ;
        }
        RedisUtil.del(redisKey);//清空行业站点对应的redis缓存
    }
    
    /**
     * 更新或删除站点前，清空区域站点对应的redis缓存
     * @param id 站点id
     * @author 许小满  
     * @date 2017年6月5日 下午11:41:18
     */
    public void clearLocationSiteCache(Long id) {
        Site site = siteDao.getById(id);//通过站点id获取站点信息
        Long provinceId = site.getProvinceId();//省id
        Long cityId = site.getCityId();//市id
        String redisKey = null;//缓存key
        if(cityId != null){//市id 不为空时
            redisKey = RedisConstants.SITE_LOCATION_ID + cityId;
        } else if (provinceId != null){//省id  不为空时
            redisKey = RedisConstants.SITE_LOCATION_ID + provinceId;
        } else {
            logger.error("错误：站点id["+id+"]对应的省、市同时为空！");
        }
        if(redisKey == null){//缓存key为空时，流程结束
            return ;
        }
        RedisUtil.del(redisKey);//清空行业站点对应的redis缓存
    }
    
}
