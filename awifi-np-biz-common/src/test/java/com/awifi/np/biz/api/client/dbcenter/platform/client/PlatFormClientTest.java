package com.awifi.np.biz.api.client.dbcenter.platform.client;


import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import com.awifi.np.biz.api.client.dbcenter.platform.servcie.PlatFormApiServcie;
import com.awifi.np.biz.common.test.MockBase;
import com.awifi.np.biz.common.util.BeanUtil;
/**
 * 
* @ClassName: PlatFormClientTest
* @Description: 省分平台 单例 客户端 单元测试 
* @author wuqia
* @date 2017年6月12日 下午7:45:00
*
 */
@SuppressWarnings("static-access")
public class PlatFormClientTest extends MockBase{
	/**
	 * 被测试类
	 */
	@InjectMocks
	private PlatFormClient platFormClient;
	
	/**
     * 省分平台业务层
     */
	@Mock(name="platFormApiServcie")
    private static PlatFormApiServcie platFormApiServcie;
	/**
	* @Title: befor
	* @Description:初始化
	* @return void 返回类型描述
	* @throws
	* @data 2017年6月12日 下午7:46:17
	* @author wuqia
	 */
    @Before
	public void befor(){
    	PowerMockito.when(BeanUtil.getBean("platFormApiServcie")).thenReturn(platFormApiServcie);
	}
	@Test
	public void testQueryPlatformCountByParam() throws Exception {
		Map<String, Object> param = new HashMap<>();
		platFormClient.queryPlatformById(param);
	}

	@Test
	public void testQueryPlatformListByParam() throws Exception {
		Map<String, Object> params = new HashMap<>();
		platFormClient.queryPlatformListByParam(params);
	}

	@Test
	public void testQueryPlatformById() throws Exception {
		Map<String, Object> param = new HashMap<>();
		platFormClient.queryPlatformById(param);
	}

	@Test
	public void testEditPlatForm() throws Exception {
		Map<String, Object> params = new HashMap<>();
		platFormClient.editPlatForm(params);;
	}

	@Test
	public void testAddPlatForm() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		platFormClient.addPlatForm(params);
	}

	@Test
	public void testDeletePaltform() throws Exception {
		Long id = 1l;
		platFormClient.deletePaltform(id);
	}

}
