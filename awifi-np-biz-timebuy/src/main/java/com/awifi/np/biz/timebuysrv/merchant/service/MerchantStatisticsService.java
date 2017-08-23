/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午9:23:45
* 创建作者：余红伟
* 文件名称：MerchantStatisticsService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantStatisticsDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantStatistics;

@Service
public class MerchantStatisticsService {
    @Resource
    private MerchantStatisticsDao merchantStatisticsDao;
    

    /**
     * 定时统计园区数据
     * 
     * @author 余红伟 
     * @date 2017年5月12日 上午9:11:13
     */
    public void statistics(){
        List<MerchantStatistics> list = merchantStatisticsDao.getListByToday();
        if(list !=null && list.size() >0){
            for(MerchantStatistics m : list){
                merchantStatisticsDao.deleteById(m.getId());
            }
        }
        merchantStatisticsDao.insertStatistics();//插入表部分字段数据
        merchantStatisticsDao.updateStatistics();//补全其他字段统计数据
    }
    /**
     * 根据省市区等条件查询统计数据
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月12日 上午9:11:40
     */
    public List<MerchantStatistics> queryStatistics(Map map){
        return merchantStatisticsDao.queryStatistics(map);
    }
    
    /**
     * 根据查询条件，统计条数
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月22日 上午9:47:46
     */
    public Integer countByParams(Map map){
        return merchantStatisticsDao.countByParams(map);
    }
    /**
     * 查询今天统计数据
     * @return
     * @author 余红伟 
     * @date 2017年5月23日 上午9:51:04
     */
    public List<MerchantStatistics> getListByToday(){
        return merchantStatisticsDao.getListByToday();
    }
    
    /**
     * 删除
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年5月23日 上午9:55:32
     */
    public Integer deleteById(Long id){
        return merchantStatisticsDao.deleteById(id);
    }
}
