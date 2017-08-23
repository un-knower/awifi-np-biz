package com.awifi.np.biz.common.security.user.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 下午1:50:40
 * 创建作者：亢燕翔
 * 文件名称：SessionUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class SessionUtilTest {

    /**被测试类*/
    @InjectMocks
    private SessionUtil sessionUtil;

    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }
    
    /**
     * 在请求中获取用户信息
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:59:12
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetCurSessionUser(){
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("id", 1L);
        secResultMap.put("userName", "superadmin");
        secResultMap.put("roleIds", "1");
        secResultMap.put("provinceId", 31L);
        secResultMap.put("cityId", 383L);
        secResultMap.put("areaId", 3232L);
        secResultMap.put("suitCode", "superadmin_v1");
        Map<String, Object> levResultMap = new LinkedHashMap<String, Object>();
        levResultMap.put("orgId", 1L);
        secResultMap.put("extend", levResultMap);
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("data", secResultMap);
        request.setAttribute("userInfo", resultMap);
        SessionUser su = new SessionUser();
        su.getId();
        su.getUserName();
        su.getOrgId();
        su.getProvinceId();
        su.getCityId();
        su.getAreaId();
        su.getMerchantId();
        su.getCascadeLabel();
        su.getSuitCode();
        sessionUtil.getCurSessionUser(request);
    }
}
