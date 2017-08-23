/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:37:33
* 创建作者：周颖
* 文件名称：SiteService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.portal.site.model.Site;
import com.awifi.np.biz.toe.portal.site.model.SitePage;

public interface SiteService {

    /***
     * 默认站点列表
     * @param page 页面
     * @param keywords 站点名称关键字
     * @author 周颖  
     * @date 2017年4月17日 下午3:45:29
     */
    void getDefaultListByParam(Page<Site> page, String keywords);

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
    void getLocationListByParam(Page<Site> page, String keywords, Long provinceId, Long cityId) throws Exception;

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
    void getIndustryListByParam(Page<Site> page, String keywords, String priIndustryCode, String secIndustryCode) throws Exception;

    /**
     * 商户站点列表
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
    void getListByParam(SessionUser user,Page<Site> page, String keywords, Long siteId, Long merchantId, Integer status) throws Exception;

    /**
     * 判断默认站点是否存在
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年4月21日 下午2:05:01
     */
    boolean isDefaultSiteExist();

    /**
     * 添加默认站点
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @return 站点id
     * @throws Exception 
     * @date 2017年4月24日 上午10:13:09
     */
    Long addDefault(Map<String, Object> bodyParam,HttpServletRequest request) throws Exception;

    /**
     * 添加行业站点
     * @param bodyParam 参数
     * @param request 请求
     * @return 站点id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 上午9:36:22
     */
    Long addIndustry(Map<String, Object> bodyParam, HttpServletRequest request) throws Exception;

    /**
     * 添加地区站点
     * @param bodyParam 参数
     * @param request 请求
     * @return 站点id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 上午10:06:55
     */
    Long addLocation(Map<String, Object> bodyParam, HttpServletRequest request) throws Exception;
    
    /**
     * 添加站点
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @return 站点id
     * @throws Exception 
     * @date 2017年4月25日 上午9:04:41
     */
    Long add(Map<String, Object> bodyParam, HttpServletRequest request) throws Exception;

    /**
     * 编辑默认站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 下午1:16:34
     */
    void updateDefault(Long id, Map<String, Object> bodyParam,HttpServletRequest request) throws Exception;

    /**
     * 编辑地区站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 下午2:27:49
     */
    void updateLocation(Long id, Map<String, Object> bodyParam, HttpServletRequest request) throws Exception;

    /**
     * 编辑行业站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月25日 下午2:46:07
     */
    void updateIndustry(Long id, Map<String, Object> bodyParam, HttpServletRequest request) throws Exception;

    /**
     * 编辑站点
     * @param id 站点id
     * @param bodyParam 参数
     * @param request 请求
     * @author 周颖  
     * @return 站点id
     * @throws Exception 
     * @date 2017年4月25日 下午3:28:17
     */
    Long update(Long id, Map<String, Object> bodyParam, HttpServletRequest request) throws Exception;

    /**
     * 删除站点
     * @param id 站点id
     * @author 周颖  
     * @date 2017年4月25日 下午7:36:22
     */
    void delete(Long id);

    /**
     * 站点详情
     * @param id 主键id
     * @return 站点详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月26日 上午10:00:29
     */
    Site getById(Long id) throws Exception;

    /**
     * 站点状态修改
     * @param id 站点id
     * @param status 要改变的站点状态
     * @author 周颖  
     * @date 2017年4月26日 下午8:10:31
     */
    void updateStatusById(Long id,Integer status);

    /**
     * 通过站点id获取商户信息
     * @param siteId 站点id
     * @return 商户信息  fk_customer_id,cascade_label 
     * @author 周颖  
     * @date 2017年4月28日 下午2:05:03
     */
    Map<String, Object> getMerchantById(Long siteId);
    
    /**
     * 获取区域站点id
     * @param provinceId 省id
     * @param cityId 市id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:54:04
     */
    Long getLocationSitIdCache(Long provinceId, Long cityId);
    
    /**
     * 获取行业站点id
     * @param priIndustryCode 一级行业编号
     * @param secIndustryCode 二级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:54:04
     */
    Long getIndustrySitIdCache(String priIndustryCode, String secIndustryCode);
    
    /**
     * 获取默认站点id
     * @return 默认站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:29:19
     */
    Long getDefaultSitId();
    
    /**
     * 获取默认站点id(缓存)
     * @return 默认站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:29:19
     */
    Long getDefaultSitIdCache();
    
    /**
     * 获取站点名称
     * @param siteId 站点id
     * @return 站点名称
     * @author 许小满  
     * @date 2017年5月11日 下午8:12:09
     */
    String getSiteName(Long siteId);
    
    /**
     * 获取站点名称(缓存)
     * @param siteId 站点id
     * @return 站点名称
     * @author 许小满  
     * @date 2017年5月11日 下午8:12:09
     */
    String getSiteNameCache(Long siteId);
    
    /**
     * 查询 站点首页面
     * @param siteId 站点id
     * @return 站点页面对象
     * @author 许小满  
     * @date 2017年5月11日 下午8:25:19
     */
    SitePage getFirstSitePage(Long siteId);
    
    /**
     * 查询 站点首页面(缓存)
     * @param siteId 站点id
     * @return 站点页面对象
     * @author 许小满  
     * @date 2017年5月11日 下午8:25:19
     */
    SitePage getFirstSitePageCache(Long siteId);
    
    /**
     * 获取站点下一页
     * @param siteId 站点id
     * @param pageType 页面类型
     * @param num 序号
     * @return 站点页面
     * @author 许小满  
     * @date 2017年5月11日 下午8:18:26
     */
    SitePage getNextPage(Long siteId, Integer pageType, Integer num);
    
    /**
     * 获取站点下一页(缓存)
     * @param siteId 站点id
     * @param pageType 页面类型
     * @param num 序号
     * @return 站点页面
     * @author 许小满  
     * @date 2017年5月11日 下午8:18:26
     */
    SitePage getNextPageCache(Long siteId, Integer pageType, Integer num);
    
    /**
     * 审核获取页面基本信息
     * @param id 站点id
     * @param params 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年5月2日 下午3:16:55
     */
    SitePage getSitePageForPreview(Long id, String params);
    
    /**
     * 获取站点页路径
     * @param id 站点页id
     * @return 路径
     * @author 周颖  
     * @date 2017年5月2日 下午5:15:53
     */
    String getPagePath(Long id);

    /**
     * 获取站点缩略图路径
     * @param id 站点id
     * @return 缩略图路径
     * @author 周颖  
     * @date 2017年5月24日 下午2:36:51
     */
    String getThumbPath(Long id);

    /**
     * 保存站点缩略图路径
     * @param id 站点id
     * @param thumbPath 缩略图路径
     * @author 周颖  
     * @date 2017年6月5日 上午11:06:39
     */
    void saveThumbPath(Long id, String thumbPath);
    
    /**
     * 更新或删除站点前，清空行业站点对应的redis缓存
     * @param id 站点id
     * @author 许小满  
     * @date 2017年6月5日 下午11:41:18
     */
    void clearIndustrySiteCache(Long id);
    
    /**
     * 更新或删除站点前，清空区域站点对应的redis缓存
     * @param id 站点id
     * @author 许小满  
     * @date 2017年6月5日 下午11:41:18
     */
    void clearLocationSiteCache(Long id);
}
