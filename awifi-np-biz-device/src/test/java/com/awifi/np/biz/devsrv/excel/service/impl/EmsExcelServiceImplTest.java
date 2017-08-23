/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月18日 下午8:06:18
* 创建作者：伍恰
* 文件名称：EmsExcelServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.fatap.service.impl.ImportFatAPService;
@PrepareForTest({BeanUtil.class})
public class EmsExcelServiceImplTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private EmsExcelServiceImpl emsExcelServiceImpl;
    
    /**
     * 服务类
     */
    @Mock
    private ImportFatAPService importFatAPService;
    
    /**
     * excel本地导入数据的持久层
     */
    @Mock(name="emsExcelDao")
    private EmsExcelDao emsExcelDao;
    
    /** 初始化 */
    @Before
    public void before() {
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean("FatAP")).thenReturn(importFatAPService);
    }
    
    /**
     * 解析和导入文件内容
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午5:00:18
     */
    @Test
    public void testImportExcel() throws Exception {
        EmsSysExcel file = new EmsSysExcel();
        SessionUser sessionUser = new SessionUser();
        String entityType="FatAP";//直接获取类型
        ExcelType type=ExcelType.valueOf(entityType);
        emsExcelServiceImpl.importExcel(file, type, sessionUser);
    }

    /**
     * 查找file的情况和插入file(本地数据库) 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午8:09:30
     */
    @Test
    public void testAddSelective() throws Exception {
        EmsSysExcel file = new EmsSysExcel();
        emsExcelServiceImpl.addSelective(file);
    }

    /**
     * 根据id更新file信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午8:09:42
     */
    @Test
    public void testUpdateById() throws Exception {
        EmsSysExcel file = new EmsSysExcel();;
        emsExcelServiceImpl.updateById(file);
    }

    /**
     * 根据用户信息查找excel文件解析情况
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午8:09:54
     */
    @Test
    public void testShowDetail() throws Exception {
        Page<EmsSysExcel> page = new Page<>(1, 10);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserName("admin");
        sessionUser.setId(1L);
        String typeValue = "FatAP";
        emsExcelServiceImpl.showDetail(page, sessionUser, typeValue);;
    }

}
