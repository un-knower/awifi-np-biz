/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午5:03:41
* 创建作者：范立松
* 文件名称：ComboServiceImpl.java
* 版本：  v1.0
* 功能：套餐配置接口
* 修改记录：
*/
package com.awifi.np.biz.devsrv.combo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.combo.util.ComboClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.combo.service.ComboService;;

@Service("comboService")
public class ComboServiceImpl implements ComboService {

    /**
     * 查询所有套餐
     * @author 范立松  
     * @date 2017年4月18日 上午11:16:40
     */
    @Override
    public void getComboList(Page<Map<String, Object>> page) throws Exception {
        List<Map<String, Object>> dataList = ComboClient.getComboList();
        page.setRecords(dataList);
    }

    /**
     * 添加套餐信息
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:47
     */
    @Override
    public void addCombo(Map<String, Object> paramsMap) throws Exception {
        // 验证“宽带 + 无感知时间”的唯一性
        int num = ComboClient.countComboByParam(paramsMap);
        if (num != 0) {
            throw new BizException("E2301021", MessageUtil.getMessage("E2301021"));//该宽带和认证时间的组合已经存在!
        }
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HHmmss");
        String numbers = format.format(date);
        paramsMap.put("packageName", "套餐" + numbers);
        ComboClient.addCombo(paramsMap);
    }

    /**
     * 删除套餐信息
     * @author 范立松  
     * @date 2017年4月18日 下午3:29:37
     */
    @Override
    public void removeCombo(Map<String, Object> paramsMap) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("packageId", paramsMap.get("id"));
        // 查询总记录条数
        int total = ComboClient.countComboManageByParam(paramMap);
        if(total > 0) {
            throw new BizException("E2301016", MessageUtil.getMessage("E2301016"));//该套餐已绑定商户不允许删除!
        }
        ComboClient.removeCombo(paramsMap);
    }

    /**
     * 分页查询套餐与商户关系列表
     * @author 范立松  
     * @date 2017年4月18日 下午5:19:10
     */
    @Override
    public void getComboManageList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception {
        // 查询总记录条数
        int total = ComboClient.countComboManageByParam(paramsMap);
        page.setTotalRecord(total);
        if (total > 0) {
            List<Map<String, Object>> dataList = ComboClient.getComboManageList(paramsMap);
            page.setRecords(dataList);
        }
    }

    /**
     * 删除套餐配置信息
     * @author 范立松  
     * @date 2017年4月18日 下午7:27:47
     */
    @Override
    public void removeComboManage(String[] ids) throws Exception {
        for (String accountId : ids) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("id", CastUtil.toLong(accountId));
            ComboClient.removeComboManage(paramsMap);
        }
    }

    /**
     * 添加套餐配置信息
     * @author 范立松  
     * @date 2017年4月18日 下午8:00:40
     */
    @Override
    public void addComboManage(Map<String, Object> paramsMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", paramsMap.get("accountId"));
        int count = ComboClient.countComboManageByParam(map);
        if (count != 0) {
            throw new BizException("E2301022", MessageUtil.getMessage("E2301022"));// 商户id在商户与套餐关系中已经存在
        }
        ComboClient.addComboManage(paramsMap);
    }

    /**
     * 套餐配置续时
     * @author 范立松  
     * @date 2017年5月5日 下午3:23:11
     */
    @Override
    public void continueComboManage(Map<String, Object> paramsMap) throws Exception {
        ComboClient.continueComboManage(paramsMap);
    }

}
