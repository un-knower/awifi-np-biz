/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 上午9:53:46
* 创建作者：尤小平
* 文件名称：UserAuthApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.service.UserAuthApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@Service(value = "userAuthApiService")
public class UserAuthApiServiceImpl implements UserAuthApiService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据条件查询认证用户.
     *
     * @param pubUserAuth PubUserAuth
     * @return 认证用户列表
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月24日 上午10:04:12
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PubUserAuth> queryUserAuthByParam(PubUserAuth pubUserAuth) throws Exception {
        logger.debug("PubUserAuth:" + JsonUtil.toJson(pubUserAuth));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_queryAuthUserByParam_url");

        // 请求参数
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(pubUserAuth), "utf-8");

        // 调用数据中心接口
        logger.debug("url=" + url + ", paramString=" + paramString);
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        logger.debug("dbcenter return:" + JsonUtil.toJson(returnMap));

        // 认证用户列表
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");
        int maxSize = returnList.size();
        List<PubUserAuth> userAuthList = new ArrayList<PubUserAuth>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            Map<String, Object> userMap = returnList.get(i);
            PubUserAuth pubUserAuth1 = JsonUtil.fromJson(JsonUtil.toJson(userMap), PubUserAuth.class);
            userAuthList.add(pubUserAuth1);
        }

        return userAuthList;
    }

    /**
     * 新增认证用户.
     *
     * @param pubUserAuth PubUserAuth
     * @return 新增认证用户id
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月24日 上午10:04:28
     */
    @Override
    public Long addUserAuth(PubUserAuth pubUserAuth) throws Exception {
        //logger.debug("add UserAuth params:" + JsonUtil.toJson(pubUserAuth));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_addAuthUser_url");

        // 调用数据中心接口
        //logger.debug("url=" + url + ", params=" + JsonUtil.toJson(pubUserAuth));
        Map<String, Object> returnMap = CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(pubUserAuth));// 返回成功的数据
        //logger.debug("dbcenter return:" + JsonUtil.toJson(returnMap));

        return CastUtil.toLong(returnMap.get("rs"));
    }

    /**
     * 根据用户userId修改认证用户的密码.
     *
     * @param pubUserAuth PubUserAuth
     * @return 操作是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月24日 上午10:05:40
     */
    @Override
    public boolean updateUserPswd(PubUserAuth pubUserAuth) throws Exception {
        return false;
    }
    
    /**
     * 条件查询用户列表，分页 
     * @param params 参数
     * @return 用户集合
     * @author 许小满  
     * @date 2017年7月19日 下午6:29:57
     */
    @SuppressWarnings("unchecked")
    public List<PubUserAuth> queryListByParam(Map<String,Object> params){
        String url = SysConfigUtil.getParamValue("dbc_queryAuthUserByParam_url");//获取请求地址
        String paramString = null;
        try {
            paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            ErrorUtil.printException(e, logger);
        }//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        List<Map<String,Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");//商户列表
        int maxSize = returnList.size();//list
        List<PubUserAuth> pubUserAuthList = new ArrayList<PubUserAuth>(maxSize);//用户集合
        PubUserAuth pubUserAuth = null;
        for(int i=0; i<maxSize; i++){
            Map<String,Object> dataMap = returnList.get(i);
            pubUserAuth = new PubUserAuth();
            pubUserAuth.setId(CastUtil.toLong(dataMap.get("id")));//主键id
            pubUserAuth.setUserId(CastUtil.toLong(dataMap.get("userId")));//主键id
            pubUserAuthList.add(pubUserAuth);
        }
        return pubUserAuthList;
    }
}
