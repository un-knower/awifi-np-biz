/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月14日 上午8:59:14
 * 创建作者：尤小平
 * 文件名称：UserBaseClient.java
 * 版本：  v1.0
 * 功能：用户基础服务
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.user.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.service.UserBaseApiService;
import com.awifi.np.biz.common.util.BeanUtil;

public class UserBaseClient {
    
    /** 日志 */
    private static Log logger = LogFactory.getLog(UserBaseClient.class);
    
    /**
     * 用户基础服务
     */
    @Resource
    private static UserBaseApiService userBaseApiService;

    /**
     * getUserBaseApiService.
     * 
     * @return UserBaseApiService
     * @author 尤小平  
     * @date 2017年4月21日 上午9:24:59
     */
    public static UserBaseApiService getUserBaseApiService() {
        if (userBaseApiService == null) {
            userBaseApiService = (UserBaseApiService) BeanUtil.getBean("userBaseApiService");
        }
        return userBaseApiService;
    }

    /**
     * 新增用户.
     *
     * @param pubUser pubUser
     * @return Long
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:20
     */
    public static Long add(PubUser pubUser) throws Exception {
        return getUserBaseApiService().add(pubUser);
    }

    /**
     * 更新用户.
     *
     * @param pubUser pubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:20
     */
    public static void update(PubUser pubUser) throws Exception {
        getUserBaseApiService().update(pubUser);
    }

    /**
     * 根据userId查询用户.
     *
     * @param userId userId
     * @return pubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:35
     */
    public static PubUser queryByUserId(Long userId) throws Exception {
        return getUserBaseApiService().queryByUserId(userId);
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
    public static List<PubUser> queryByUserIds(String userIds) throws Exception {
        return getUserBaseApiService().queryByUserIds(userIds);
    }

    /**
     * 根据条件查询用户列表条数.
     *
     * @param params 条件参数
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:59:52
     */
    public static int queryCountByParam(Map<String, Object> params) throws Exception {
        return getUserBaseApiService().queryCountByParam(params);
    }
    
    /**
     * 通过用户手机号获取用户id
     * @param userPhone 用户手机号
     * @return 用户id
     * @author 许小满  
     * @date 2017年6月19日 下午10:24:27
     */
    public static Long getUserIdByPhone(String userPhone){
        if(StringUtils.isBlank(userPhone)){
            logger.debug("错误: userPhone 为空！");
            return null;
        }
        Map<String,Object> params = new HashMap<String,Object>(3);//参数map
        params.put("telphone", userPhone);//手机号
        params.put("pageNum", "1");//页码
        params.put("pageSize", "1");//每页记录数
        List<PubUser> pubUserList = getUserBaseApiService().queryListByParam(params);
        if(pubUserList == null || pubUserList.size() <= 0){
            return null;
        }
        PubUser pubUser = pubUserList.get(0);
        if(pubUser == null){
            return null;
        }
        return pubUser.getId();
    }
}
