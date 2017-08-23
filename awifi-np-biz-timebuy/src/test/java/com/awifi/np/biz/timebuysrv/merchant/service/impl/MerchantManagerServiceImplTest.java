/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月2日 上午11:00:03
* 创建作者：尤小平
* 文件名称：MerchantManagerServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantManagerDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MerchantManagerServiceImplTest {
    /**
     * MerchantManagerServiceImpl
     */
    private MerchantManagerServiceImpl merchantManagerService;

    /**
     * MerchantManagerDao
     */
    private MerchantManagerDao mockMerchantManagerDao;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:48:12
     */
    @Before
    public void setUp() throws Exception {
        merchantManagerService = new MerchantManagerServiceImpl();
        mockMerchantManagerDao = Mockito.mock(MerchantManagerDao.class);
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:48:24
     */
    @After
    public void tearDown() throws Exception {
        merchantManagerService = null;
        mockMerchantManagerDao = null;
    }

    /**
     * 测试根据主键获取MerchantManager.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:48:34
     */
    @Test
    public void testQueryByIdSuccess() throws Exception {
        Long id = 2L;
        MerchantManager value = new MerchantManager();
        Mockito.when(mockMerchantManagerDao.selectByPrimaryKey(any(Long.class))).thenReturn(value);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        MerchantManager actual = merchantManagerService.queryById(id);
        Assert.assertNotNull(actual);
        Assert.assertEquals(value, actual);
        Mockito.verify(mockMerchantManagerDao).selectByPrimaryKey(any(Long.class));
    }

    /**
     * 测试根据主键获取MerchantManager.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:48:56
     */
    @Test
    public void testQueryByIdFail() throws Exception {
        Long id = 0L;

        MerchantManager actual = merchantManagerService.queryById(id);
        Assert.assertNull(actual);
    }

    /**
     * 测试新增管理员.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:49:18
     */
    @Test
    public void testInsertSuccess() throws Exception {
        MerchantManager merchantManager = new MerchantManager();
        Mockito.when(mockMerchantManagerDao.insertSelective(any(MerchantManager.class))).thenReturn(1);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        boolean actual = merchantManagerService.insert(merchantManager);
        Assert.assertEquals(true, actual);
        Mockito.verify(mockMerchantManagerDao).insertSelective(any(MerchantManager.class));
    }

    /**
     * 测试新增管理员.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:49:27
     */
    @Test
    public void testInsertFail() throws Exception {
        MerchantManager merchantManager = null;
        boolean actual = merchantManagerService.insert(merchantManager);
        Assert.assertEquals(false, actual);
    }

    /**
     * 测试根据主键修改管理员信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:49:41
     */
    @Test
    public void testUpdateSuccess() throws Exception {
        MerchantManager merchantManager = new MerchantManager();
        merchantManager.setId(2L);
        Mockito.when(mockMerchantManagerDao.updateByPrimaryKey(any(MerchantManager.class))).thenReturn(1);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        boolean actual = merchantManagerService.update(merchantManager);
        Assert.assertEquals(true, actual);
        Mockito.verify(mockMerchantManagerDao).updateByPrimaryKey(any(MerchantManager.class));
    }

    /**
     * 测试根据主键修改管理员信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:49:50
     */
    @Test
    public void testUpdateFailA() throws Exception {
        MerchantManager merchantManager = new MerchantManager();
        merchantManager.setId(2L);
        Mockito.when(mockMerchantManagerDao.updateByPrimaryKey(any(MerchantManager.class))).thenReturn(0);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        boolean actual = merchantManagerService.update(merchantManager);
        Assert.assertEquals(false, actual);
        Mockito.verify(mockMerchantManagerDao).updateByPrimaryKey(any(MerchantManager.class));
    }

    /**
     * 测试根据主键修改管理员信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:50:02
     */
    @Test
    public void testUpdateFailB() throws Exception {
        MerchantManager merchantManager = new MerchantManager();
        merchantManager.setId(0L);
        boolean actual = merchantManagerService.update(merchantManager);
        Assert.assertEquals(false, actual);
    }

    /**
     * 测试根据主键删除管理员信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:50:33
     */
    @Test
    public void testDeleteByIdSuccess() throws Exception {
        Long id = 2L;

        Mockito.when(mockMerchantManagerDao.deleteByPrimaryKey(any(Long.class))).thenReturn(1);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        boolean actual = merchantManagerService.deleteById(id);
        Assert.assertEquals(true, actual);
        Mockito.verify(mockMerchantManagerDao).deleteByPrimaryKey(any(Long.class));
    }

    /**测试根据主键删除管理员信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:50:49
     */
    @Test
    public void testDeleteByIdFail() throws Exception {
        Long id = 2L;

        Mockito.when(mockMerchantManagerDao.deleteByPrimaryKey(any(Long.class))).thenReturn(0);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        boolean actual = merchantManagerService.deleteById(id);
        Assert.assertEquals(false, actual);
        Mockito.verify(mockMerchantManagerDao).deleteByPrimaryKey(any(Long.class));
    }

    /**
     * 测试获取管理员信息列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:51:06
     */
    @Test
    public void testGetListByMerchantManager() throws Exception {
        MerchantManager merchantManager = new MerchantManager();

        List<MerchantManager> list = new ArrayList<MerchantManager>();
        Mockito.when(mockMerchantManagerDao.getListByMerchantManager(any(MerchantManager.class))).thenReturn(list);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        List<MerchantManager> actual = merchantManagerService.getListByMerchantManager(merchantManager);
        Assert.assertEquals(list, actual);
        Mockito.verify(mockMerchantManagerDao).getListByMerchantManager(any(MerchantManager.class));
    }

    /**
     * 测试获取管理员信息列表，分页.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:51:24
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetListByParams() throws Exception {
        MerchantManager merchantManager = new MerchantManager();
        Page<MerchantManager> page = new Page<MerchantManager>();
        page.setPageSize(15);

        List<MerchantManager> list = new ArrayList<MerchantManager>();
        Mockito.when(mockMerchantManagerDao.getListByParams(any(MerchantManager.class), any(Page.class)))
                .thenReturn(list);
        Mockito.when(mockMerchantManagerDao.getCountByParams(any(MerchantManager.class))).thenReturn(20);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        Page<MerchantManager> actual = merchantManagerService.getListByParams(merchantManager, page);
        Assert.assertEquals(list, actual.getRecords());
        Mockito.verify(mockMerchantManagerDao).getListByParams(any(MerchantManager.class), any(Page.class));
        Mockito.verify(mockMerchantManagerDao).getCountByParams(any(MerchantManager.class));
    }

    /**
     * 测试获取管理员信息列表总条数.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午2:51:46
     */
    @Test
    public void testGetCountByParams() throws Exception {
        int count = 20;
        MerchantManager merchantManager = new MerchantManager();

        Mockito.when(mockMerchantManagerDao.getCountByParams(any(MerchantManager.class))).thenReturn(count);
        merchantManagerService.setMerchantManagerDao(mockMerchantManagerDao);

        int actual = merchantManagerService.getCountByParams(merchantManager);
        Assert.assertEquals(count, actual);
        Mockito.verify(mockMerchantManagerDao).getCountByParams(any(MerchantManager.class));
    }

}
