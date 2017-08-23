package com.awifi.np.biz.toe.portal.component.dao.sql;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月22日 上午10:29:01
 * 创建作者：许尚敏
 * 文件名称：ComponentSqlTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
public class ComponentSqlTest {

    /**被测试类*/
    @InjectMocks
    private ComponentSql componentSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);  
    }
    
    /**
     * 组件列表测试
     * @author 许尚敏
     * @date 2017年4月12日 上午10:00:34
     */
    @Test
    public void testGetCountByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        componentSql.getCountByParam(params);
    }
    
    /**
     * 组件列表测试
     * @author 许尚敏
     * @date 2017年4月12日 上午10:00:34
     */
    @Test
    public void testGetCountByParamByNull() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "");
        componentSql.getCountByParam(params);
    }

    /**
     * 组件列表测试
     * @author 许尚敏 
     * @date 2017年6月22日 上午10:00:34
     */
    @Test
    public void testGetListByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        componentSql.getListByParam(params);
    }
    
    /**
     * 组件列表测试
     * @author 许尚敏 
     * @date 2017年6月22日 上午10:00:34
     */
    @Test
    public void testGetListByParamByNull() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "");
        componentSql.getListByParam(params);
    }
}
