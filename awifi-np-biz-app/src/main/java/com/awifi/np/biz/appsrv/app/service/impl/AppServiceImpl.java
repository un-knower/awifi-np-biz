package com.awifi.np.biz.appsrv.app.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.appsrv.app.dao.AppDao;
import com.awifi.np.biz.appsrv.app.model.App;
import com.awifi.np.biz.appsrv.app.service.AppService;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.MicroshopService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月10日 下午2:34:41
 * 创建作者：许尚敏
 * 文件名称：AppServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("appService")
@SuppressWarnings({"rawtypes"})
public class AppServiceImpl implements AppService{
    
    /**
     * 应用管理—应用添加接口dao
     */
    @Resource(name="appDao")
    private AppDao appDao;
    
    /** toe用户层 */
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /** 微旺铺业务层 */
    @Resource(name = "microshopService")
    private MicroshopService microShopService;

    /**timestamp有效时间*/
    public static final long TOKEN_TIMEOUT = 300;//5*60 timestamp有效时间 五分钟 秒
    
    /**
     * 应用添加接口
     * @param app 实体类
     * @author 许尚敏  
     * @date 2017年7月10日 下午3:00:31
     */
    public void add(App app){
        appDao.add(app);
    }
    
    /**
     * 应用编辑接口
     * @param app 实体类
     * @author 许尚敏  
     * @date 2017年7月11日 上午11:00:21
     */
    public void update(App app){
        appDao.update(app);
    }
    
    
    /**
     * 查询appId
     * @param appId 应用id
     * @return app
     * @author 王冬冬  
     * @date 2017年7月12日 上午9:36:12
     */
    public App queryAppByAppId(String appId) {
        return appDao.queryAppByAppId(appId);
    }
    /**
     * 查询应用分页信息
     * @author 季振宇  
     * @date Jul 11, 2017 12:21:39 PM
     */
    @Override
    public void getListByParam(SessionUser sessionUser, Page<App> page, String appName, Integer status) {
        int count = appDao.getCountByParam(appName,status);//符合条件的总数
        page.setTotalRecord(count);//page设置总条数
        if(count <= 0){//如果小于0 直接返回
            return;
        }
        
        List<App> appList = appDao.getListByParam(appName, status, page.getBegin(), page.getPageSize());//查询应用信息
        for (App app : appList) {//循环设置statusDsp
            if (app.getStatus() == 1) {//判断status
                app.setStatusDsp("启用");//设置statusDsp
            }else {
                app.setStatusDsp("禁用");//设置statusDsp
            }
        }
        page.setRecords(appList);//返回查询的应用信息
    }
    
    /**
     * 应用管理--应用列表-详情接口
     * @author 季振宇  
     * @date Jul 12, 2017 10:05:33 AM
     */
    @Override
    public App getById(Long id) {
        App app = appDao.getById(id);
        if (app.getStatus() == 1) {//判断status
            app.setStatusDsp("启用");//设置statusDsp
        }else {
            app.setStatusDsp("禁用");//设置statusDsp
        }
        return app; //返回查询到的应用详情
    }

    /**
     * 应用管理--删除应用接口
     * @author 季振宇  
     * @date Jul 12, 2017 11:05:40 AM
     */
    @Override
    public void delete(Long id) throws Exception {
        Integer status = appDao.getStatusById(id);//查询app状态
        if (status == null) {
            return;
        }
        if (status == 1) {
            throw new BizException("E2900008", MessageUtil.getMessage("E2900008"));//该应用已生效，不能删除！
        }else {
            appDao.delete(id);
        }
    }
    
    /**
     * 获得应用单点登录url
     * @param merchantId 商户id
     * @param appId appid
     * @throws Exception 异常
     * @return map
     * @author 王冬冬  
     * @date 2017年7月12日 下午2:44:03
     */
    public Map<String, String> getSsoUrl(Long merchantId, String appId) throws Exception {
        App app=this.queryAppByAppId(appId);//根据appid查询app信息
        if(app==null){
            throw new BizException("E2900002",MessageUtil.getMessage("E2900002",appId));//通过应用id[{0}]找不到对应的应用！
        }
        String appParam=app.getAppParam();
        if(StringUtils.isBlank(appParam)){
            throw new BizException("E0000002",MessageUtil.getMessage("E0000002","应用参数[appParam]"));//appParam不允许为空!
        }
        Map result=JsonUtil.fromJson(appParam, Map.class);
        String loginUrl=(String) result.get("loginUrl");
        if(StringUtils.isBlank(loginUrl)){
            throw new BizException("E2900003",MessageUtil.getMessage("E2900003"));//该应用尚未配置单点登录地址，请先配置！
        }
        
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String appKey=app.getAppKey();
        String token = EncryUtil.generateAppToken(appId,appKey, timestamp);//生成token
        ToeUser toeUser=toeUserService.getByMerchantId(merchantId);
        if(StringUtils.isEmpty(toeUser.getUserName())){
            throw new BizException("E2900004",MessageUtil.getMessage("E2900004",merchantId));//通过商户id[{0}]未匹配到账号！
        }
       
       //符合条件的记录
        Merchant merchant=MerchantClient.getById(merchantId);//获取merchant信息
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(loginUrl);
        if (loginUrl.indexOf("?") > -1) {
            urlBuffer.append("&token=" + token);
        } else {
            urlBuffer.append("?token=" + token);
        }
        urlBuffer.append("&appid=" + appId);//拼接appid
        urlBuffer.append("&timestamp=" + timestamp);
        urlBuffer.append("&merchantid=" +URLEncoder.encode(EncryUtil.encryptByDes(merchantId.toString(), appKey),"utf-8"));
        urlBuffer.append("&account=" + toeUser.getUserName());
        urlBuffer.append("&merchantname=" + URLEncoder.encode(merchant.getMerchantName(),"utf-8"));
        urlBuffer.append("&cascadelabel=" + URLEncoder.encode(EncryUtil.encryptByDes(merchant.getCascadeLabel(),appKey),"utf-8"));
        urlBuffer.append("&projectid=" + URLEncoder.encode(EncryUtil.encryptByDes(toeUser.getProjectId().toString(),appKey),"utf-8"));
        urlBuffer.append("&telephone=" + URLEncoder.encode(EncryUtil.encryptByDes(merchant.getContactWay(),appKey),"utf-8"));
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("url", urlBuffer.toString());//返回url信息
        return resultMap;
    }

    /**
     * 关联配置
     * @param merchantId 商户id
     * @param queryMerchantId 查询商户id
     * @param appId 应用id
     * @param page 分页对象
     * @throws exception
     * @return
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年7月12日 下午3:18:59
     */
    public void getMerchantListByParam(Long merchantId, Long queryMerchantId, String appId,Page<Map> page) throws Exception {
        //符合条件的记录
        Merchant merchant=MerchantClient.getById(merchantId);//获取merchant信息
        Microshop  microshop=microShopService.getRelateMerIdByMerId(merchantId,appId);//根据当前登录用户的merchantId获取关联应用对应的商户id
        /*获取关联配置商户信息记录数*/
        int count=microShopService.getMerchantCountByParam(merchantId,queryMerchantId,appId,microshop!=null?microshop.getRelateCustomerId():null,merchant.getProjectId());
        /*获取关联配置商户信息*/
        List<Microshop> microshops=microShopService.getMerchantByParam(merchantId,queryMerchantId,appId,microshop!=null?microshop.getRelateCustomerId():null,merchant.getProjectId(),(page.getPageNo()-1)*page.getPageSize(),page.getPageSize());
        List<Map> results=new ArrayList<Map>();
        Map<String,Object> map=new HashMap<String,Object>();
        for(Microshop shop:microshops){
            Merchant relateMerchant=MerchantClient.getById(shop.getCustomerId());//获取merchant信息
            map.put("id", shop.getCustomerId());
            map.put("merchantName",relateMerchant!=null?relateMerchant.getMerchantName():null);
            results.add(map);
        }
        page.setRecords(results);
        page.setTotalRecord(count);
        return;
    }

    /**
     * 通过商户id[merchantId]、应用id[appId]查询公众号信息
     * @param merchantId 商户id
     * @param appId 应用Id
     * @return map
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月13日 下午3:44:45
     */
    public Map<String, Object> getByParams(Long merchantId, String appId) throws Exception {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Microshop microshop=microShopService.getByParams(merchantId,appId);//通过商户id[merchantId]、应用id[appId]查询公众号信息
        if(microshop==null){
            return null;//记录为空，直接返回
        }
        resultMap.put("id", microshop.getId());//放入id
        resultMap.put("shopName", microshop.getShopName());//放入公众号名称
        resultMap.put("spreadModel", microshop.getSpreadModel());//放入推广模式
        resultMap.put("forceAttention", microshop.getForceAttention());//放入强制关注
        Long relateMerchantId=microshop.getRelateCustomerId();//获取关联商户id
        if(relateMerchantId!=null){
            Merchant merchant=MerchantClient.getById(relateMerchantId);//获取merchant信息 
            Microshop relateMicroshop=microShopService.getByParams(relateMerchantId,appId);//通过商户id[merchantId]、应用id[appId]查询公众号信息
            resultMap.put("relateMerchantId", merchant!=null?merchant.getId():null);//放入关联商户id
            resultMap.put("relateMerchantName",merchant!=null?merchant.getMerchantName():null);//放入关联商户名称
            resultMap.put("relateShopName", relateMicroshop!=null?relateMicroshop.getShopName():null);//放入关联公众号名称
        }
        return resultMap; //返回结果
    }
    
    /**
     * 通过appId获取appKey
     * @param appId appid
     * @return appkey
     * @author 周颖  
     * @date 2017年7月14日 下午2:50:21
     */
    public String getKeyByAppId(String appId){
        if(StringUtils.isBlank(appId)){
            return StringUtils.EMPTY;
        }
        return appDao.getKeyByAppId(appId);
    }
    
    /**
     * 获取access_token
     * @param appId appId
     * @param timestamp 时间戳
     * @param token 令牌
     * @return 结果
     * @author 周颖  
     * @date 2017年7月14日 下午3:16:54
     */
    public Map<String, Object> getAccessToken(String appId, String timestamp,String token){
        String appKey = getKeyByAppId(appId);//获取第三方key
        if(StringUtils.isBlank(appKey)){//如果为空
            throw new BizException("E2000002", MessageUtil.getMessage("E2000002", appId));//抛业务异常 appid[{0}]无效!
        }
        if(EncryUtil.isAppTimeout(timestamp, TOKEN_TIMEOUT)){//如果时间戳超时 秒
            throw new BizException("E2000003", MessageUtil.getMessage("E2000003", token));//抛业务异常 token[{0}]超时!
        }
        String tokenNew = EncryUtil.generateAppToken(appId,appKey,timestamp);//根据传参重新生成token
        if(!tokenNew.equals(token)){//如果两个token不同
            throw new BizException("E2000004", MessageUtil.getMessage("E2000004", token));//抛异常 token[{0}]无效!
        }
        String accessToken = KeyUtil.generateAccessToken(Constants.APP);//生成access_token;
        Map<String,Object> appInfo= appDao.getByAppId(appId);//获取第三方应用详情
        Map<String,Object> value = new HashMap<String,Object>();
        value.put("appId", Constants.APP);//redis 保存appid
        value.put("appInfo", appInfo);//redis 保存第三方应用信息 id,appKey,appName
        int accessTokenTime = Integer.parseInt(SysConfigUtil.getParamValue("access_token_time"));
        RedisAdminUtil.set(accessToken, JsonUtil.toJson(value), accessTokenTime);//access_token保存到redis
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("access_token", accessToken);//access_token
        data.put("expires_in", accessTokenTime);//有效时间
        return data;//返回数据
    }
}
