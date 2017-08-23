/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月13日 下午3:58:03
 * 创建作者：尤小平
 * 文件名称：UserBaseApiServiceImpl.java
 * 版本：  v1.0
 * 功能：用户基础服务
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.service.UserBaseApiService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@Service(value = "userBaseApiService")
public class UserBaseApiServiceImpl implements UserBaseApiService {
    
    /** 日志 */
    private static Log logger = LogFactory.getLog(UserBaseApiServiceImpl.class);

    /**
     * getParamsMap.
     * 
     * @param pubUser PubUser
     * @return Map<String, Object>
     * @author 尤小平
     * @date 2017年4月21日 上午9:29:02
     */
    private Map<String, Object> getParamsMap(PubUser pubUser) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("id", pubUser.getId());
        param.put("authId", pubUser.getAuthId());
        param.put("telphone", pubUser.getTelphone());
        param.put("wechat", pubUser.getWechat());
        param.put("email", pubUser.getEmail());
        param.put("userCard", pubUser.getUserCard());
        param.put("userNick", pubUser.getUserNick());
        param.put("userRealname", pubUser.getUserRealname());
        param.put("sex", pubUser.getSex());
        param.put("political", pubUser.getPolitical());
        param.put("nativeStr", pubUser.getNativeStr());
        param.put("birthday", pubUser.getBirthday());
        param.put("integralUsable", pubUser.getIntegralUsable());
        param.put("integralUsed", pubUser.getIntegralUsed());
        param.put("phone", pubUser.getPhone());
        param.put("address", pubUser.getAddress());
        param.put("faceInfo", pubUser.getFaceInfo());
        param.put("college", pubUser.getCollege());
        param.put("major", pubUser.getMajor());
        param.put("company", pubUser.getCompany());
        param.put("industry", pubUser.getIndustry());
        param.put("post", pubUser.getPost());
        param.put("hobby", pubUser.getHobby());
        param.put("abhor", pubUser.getAbhor());
        param.put("leisurePlace", pubUser.getLeisurePlace());
        param.put("level", pubUser.getLevel());
        param.put("totalExp", pubUser.getTotalExp());
        param.put("badges", pubUser.getBadges());
        param.put("remarks", pubUser.getRemarks());
        param.put("createDate", pubUser.getCreateDate());

        return param;
    }

    /**
     * 新增用户.
     *
     * @param pubUser PubUser
     * @return Long
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:20
     */
    @Override
    public Long add(PubUser pubUser) throws Exception {
        logger.debug("add params:" + JSON.toJSONString(pubUser));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_adduser_url");

        // 请求参数
        Map<String, Object> param = getParamsMap(pubUser);

        // 调用数据中心接口
        logger.debug("url=" + url + ", paramString=" + JsonUtil.toJson(param));
        Map<String, Object> returnMap = CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(param));// 返回成功的数据
        logger.debug("dbcenter return:" + JsonUtil.toJson(returnMap));

        return CastUtil.toLong(returnMap.get("rs"));
    }

    /**
     * 更新用户.
     *
     * @param pubUser PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:20
     */
    @Override
    public void update(PubUser pubUser) throws Exception {
        logger.debug("update params:" + JSON.toJSONString(pubUser));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_updateuser_url");

        // 请求参数
        Map<String, Object> param = getParamsMap(pubUser);

        // 调用数据中心接口
        logger.debug("url=" + url + ", paramString=" + JsonUtil.toJson(param));
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(param));
    }

    /**
     * 根据userId查询用户.
     *
     * @param userId userId
     * @return PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:35
     */
    @SuppressWarnings("unchecked")
    @Override
    public PubUser queryByUserId(Long userId) throws Exception {
        logger.debug("userId:" + userId);

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_getuser_url");

        // 请求参数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", userId);
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(param), "utf-8");

        // 调用数据中心接口
        logger.debug("url=" + url + ", paramString=" + paramString);
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        logger.debug("dbcenter return:" + JsonUtil.toJson(returnMap));

        Map<String, Object> userMap = (Map<String, Object>) returnMap.get("rs");
        if (userMap == null || userMap.isEmpty()) {
            throw new InterfaceException("E2000018", MessageUtil.getMessage("E2000018"), url, paramString);// 接口异常
        }

        return JsonUtil.fromJson(JsonUtil.toJson(userMap), PubUser.class);
    }

    /**
     * 根据userIds查询用户列表.
     *
     * @param userIds userIds
     * @return List<PubUser>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:59:52
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PubUser> queryByUserIds(String userIds) throws Exception {
        logger.debug("userIds:" + userIds);

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_getuserbyids_url");

        // 请求参数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", userIds);
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(param), "utf-8");

        // 调用数据中心接口
        logger.debug("url=" + url + ", paramString=" + paramString);
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        logger.debug("dbcenter return:" + JsonUtil.toJson(returnMap));

        // 用户列表
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");
        int maxSize = returnList.size();
        List<PubUser> userList = new ArrayList<PubUser>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            Map<String, Object> userMap = returnList.get(i);
            PubUser pubUser = JsonUtil.fromJson(JsonUtil.toJson(userMap), PubUser.class);
            userList.add(pubUser);
        }

        return userList;
    }

    /**
     * 根据条件查询用户列表条数.
     *
     * @param params Map<String, Object>
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:59:52
     */
    @Override
    public int queryCountByParam(Map<String, Object> params) throws Exception {
        logger.debug("params:" + JsonUtil.toJson(params));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue("dbc_getusercountbyparam_url");

        // 请求参数
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "utf-8");

        // 调用数据中心接口
        logger.debug("url=" + url + ", paramString=" + paramString);
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        logger.debug("dbcenter return:" + JsonUtil.toJson(returnMap));

        // 用户列表条数
        return Integer.valueOf(JsonUtil.toJson(returnMap.get("rs")));
    }
    
    /**
     * 条件查询用户列表，分页 
     * @param params 参数
     * @return 用户集合
     * @author 许小满  
     * @date 2017年6月19日 下午10:35:53
     */
    @SuppressWarnings("unchecked")
    public List<PubUser> queryListByParam(Map<String,Object> params){
        String url = SysConfigUtil.getParamValue("dbc_getuserlistbyparam_url");//获取请求地址
        String paramString = null;
        try {
            paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            ErrorUtil.printException(e, logger);
        }//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        List<Map<String,Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");//商户列表
        int maxSize = returnList.size();//list
        List<PubUser> pubUserList = new ArrayList<PubUser>(maxSize);//用户集合
        PubUser pubUser = null;
        for(int i=0; i<maxSize; i++){
            Map<String,Object> dataMap = returnList.get(i);
            pubUser = new PubUser();
            pubUser.setId(CastUtil.toLong(dataMap.get("id")));//主键id
            pubUserList.add(pubUser);
        }
        return pubUserList;
    }
}
