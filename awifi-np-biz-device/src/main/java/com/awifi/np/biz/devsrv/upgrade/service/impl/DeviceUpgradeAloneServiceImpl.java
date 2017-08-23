/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午8:07:07
* 创建作者：尤小平
* 文件名称：DeviceUpgradeAloneServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.service.impl;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.util.UpgradeAloneClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.upgrade.service.DeviceUpgradeAloneService;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service(value = "deviceUpgradeAloneService")
public class DeviceUpgradeAloneServiceImpl implements DeviceUpgradeAloneService {
    /**
     * 定制终端升级 --查询主界面 -- 个性化任务列表
     * @author 余红伟 
     * @date 2017年7月11日 上午11:15:04
     */
    @Override
    public void getPersonalizedUpgradeTaskList(Map<String, Object> params, Page<Map<String, Object>> page)throws Exception {
        //任务总数
        Integer totalRecord =  UpgradeAloneClient.getPersonalizedUpgradeTaskCount(params);
        if(totalRecord == null){
            totalRecord = 0;
        }
        Integer totalPage = totalRecord % page.getPageSize() == 0?totalRecord /page.getPageSize():totalRecord / page.getPageSize() + 1;
        
        //设置页码和每页条数
        params.put("pageNum", page.getPageNo());
        params.put("pageSize", page.getPageSize());
        
        //获取当前页记录
        List<Map<String, Object>> returnList= UpgradeAloneClient.getPersonalizedUpgradeTaskList(params);
        page.setRecords(returnList);//当前记录
        page.setTotalRecord(totalRecord);
        page.setTotalPage(totalPage);//总页码
        
    }
    /**
     * 定制终端升级--个性化任务根据id查询
     * @author 余红伟 
     * @date 2017年7月11日 上午11:17:55
     */
    @Override
    public Map<String, Object> getPersonalizedUpgradeTaskById(Long id) throws Exception {
        return UpgradeAloneClient.getPersonalizedUpgradeTaskById(id);
    }
    /**
     * 定制终端升级--个性化升级任务添加
     * @param params 异常/参数
     * @author 余红伟  
     * @date 2017年6月27日 下午8:28:45
     */
    @Override
    public void addPersonalizedUpgradeTask(Map<String, Object> params) throws Exception {
        UpgradeAloneClient.addPersonalizedUpgradeTask(params);
    }
    /**
     * 定制终端升级--个性化升级任务删除
     * @param ids id数组
     * @author 余红伟  
     * @throws Exception 
     * @date 2017年6月27日 下午8:29:34
     */
    @Override
    public void deletePersonalizedUpgradeTask(Long[] ids) throws Exception {
        ValidUtil.valid("任务id数组[ids]", ids, "arrayNotBlank");
        for(int i=0;i<ids.length;i++){
            ValidUtil.valid("任务id", ids[i], "{'required':true,'numeric':true}");
        }
        for(Long id: ids){
            UpgradeAloneClient.deletePersonalizedUpgradeTask(id);
        }
    }
    /**
     * 定制终端升级--个性化升级任务统计
     * @author 余红伟 
     * @date 2017年7月11日 上午9:44:49
     */
    @Override
    public Integer getPersonalizedUpgradeTaskCount(Map<String, Object> params) throws Exception {
        return UpgradeAloneClient.getPersonalizedUpgradeTaskCount(params);
    }
    /**
     * 根据mac地址查询设备
     * @author 余红伟 
     * @date 2017年7月11日 下午2:15:38
     */
    @Override
    public Map<String, Object> getDeviceByMac(String mac) throws Exception {
        
        return UpgradeAloneClient.getDeviceByMac(mac);
    }
    /**
     * 个性化升级包 列表查询
     * @param params
     * @param page
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:18:27
     */
    @Override
    public void getPersonalizUpgradePatchList(Map<String, Object> params, Page<Map<String,Object>> page) throws Exception {
        //升级包总数
        Integer totalRecord =  UpgradeAloneClient.getPersonalizUpgradePatchCount(params);
        if(totalRecord == null){
            totalRecord = 0;
        }
        Integer totalPage = totalRecord % page.getPageSize() == 0?totalRecord /page.getPageSize():totalRecord / page.getPageSize() + 1;
        
        //设置页码和每页条数
        params.put("pageNum", page.getPageNo());
        params.put("pageSize", page.getPageSize());
        
        //获取当前页记录
        List<Map<String, Object>> returnList= UpgradeAloneClient.getPersonalizUpgradePatchList(params);
        page.setRecords(returnList);//当前记录
        page.setTotalRecord(totalRecord);
        page.setTotalPage(totalPage);//总页码
    }
    /**
     * 个性化升级包 统计
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:02:07
     */
    @Override
    public Integer getPersonalizUpgradePatchCount(Map<String, Object> params) throws Exception {
        
        return UpgradeAloneClient.getPersonalizUpgradePatchCount(params);
    }
    /**
     * 个性化升级包 删除
     * @param id
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:03:16
     */
    @Override
    public void deletePersonalizedUpgradePatch(Long id) throws Exception {
        UpgradeAloneClient.deletePersonalizedUpgradePatch(id);
    }
    /**
     * 个人性化升级包 添加
     * @param params
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:03:46
     */
    @Override
    public void addPersonalizedUpgradePatch(Map<String, Object> params) throws Exception {
        UpgradeAloneClient.addPersonalizedUpgradePatch(params);
        
    }
    /**
     * 定制终端升级--个性化升级包查询 根据 id
     * @param id
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:18:31
     */
    @Override
    public Map<String, Object> getPersonalizUpgradePatchById(Long id) throws Exception {
        
        return UpgradeAloneClient.getPersonalizUpgradePatchById(id);
    }
    /**
     * 定制终端升级--个性化升级设备查询  添加任务界面
     * @param params 参数
     * @param page 分页实体
     * @author 余红伟  
     * @date 2017年6月27日 下午8:26:36
     */
    @Override
    public void getUpgradeDeviceList(Map<String, Object> params, Page<Map<String, Object>> page)throws Exception {
        //设备总数
        Integer totalRecord =  UpgradeAloneClient.queryDeviceCountByParam(params);
        if(totalRecord == null){
            totalRecord = 0;
        }
        Integer totalPage = totalRecord % page.getPageSize() == 0?totalRecord /page.getPageSize():totalRecord / page.getPageSize() + 1;
        //设置页码和每页条数
        params.put("pageNum", page.getPageNo());
        params.put("pageSize", page.getPageSize());
        
        //获取当前页记录
        List<Map<String, Object>> returnList= UpgradeAloneClient.getUpgradeDeviceList(params);
        page.setRecords(returnList);//当前记录
        page.setTotalRecord(totalRecord);
        page.setTotalPage(totalPage);//总页码
        
    }
    /**
     * 定制终端升级--个性化升级设备统计  添加任务界面
     * @author 余红伟 
     * @date 2017年7月12日 上午10:29:23
     */
    @Override
    public Integer queryDeviceCountByParam(Map<String, Object> params)throws Exception {
 
        return UpgradeAloneClient.queryDeviceCountByParam(params);
    }

}
