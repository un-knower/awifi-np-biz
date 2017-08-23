package com.awifi.np.biz.timebuysrv.web.module.time.service;

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.UserCutoffDateDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;

/**
 * 用户剩余时长service
 * 
 * @author 张智威 2017年4月10日 下午5:03:59
 */
@Service
public class UserCutoffService {
    private static final Logger logger = LoggerFactory.getLogger(UserCutoffService.class);

    @Resource
    private UserCutoffDateDao userCutoffMapper;

    /**
     * 
     * 迭代XX：根据id删除一条时长信息
     * 
     * @Description: deleteByPrimaryKey
     * @param id
     * @return
     * @return Integer
     * @author 张智威
     * @date 2016-11-30 上午9:47:53
     */
    @Deprecated
    public Integer deleteByPrimaryKey(Long id) {

        int a = userCutoffMapper.deleteByPrimaryKey(id);
        return a;
    }

    /**
     * 
     * 迭代XX：添加一条时长信息
     * 
     * @Description: insertSelective
     * @param merchantId
     * @param userId
     * @param cutoffDate
     * @param remarks
     * @return
     * @return Integer 0-失败 1-成功
     * @author 张智威
     * @date 2016-11-30 上午10:04:26
     */
    public Integer insertSelective(Long merchantId, Long userId, Date cutoffDate, String remarks) {
        int result = 0;
        if (merchantId == null || userId == null) {
            logger.info("添加时长时商户id和用户id不能为空" + "商户id：" + merchantId + "用户id：" + userId);
            return result;
        }
        UserCutoff md = new UserCutoff();
        md.setCutoffDate(cutoffDate);
        md.setMerchantId(merchantId);
        md.setRemarks(remarks);
        md.setUserId(userId);
        result = userCutoffMapper.insertSelective(md);
        if (result > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 添加一条用户倒计时信息
     * 
     * @param userCutoff
     * @return 操作结果
     * @author 张智威
     * @date 2017年4月25日 上午10:47:11
     */
    public Integer insert(UserCutoff userCutoff) {
        int result = 0;
        if (userCutoff.getMerchantId() == null || userCutoff.getUserId() == null) {
            logger.info("添加时长时商户id和用户id不能为空");
            return result;
        }
        result = userCutoffMapper.insertSelective(userCutoff);
        if (result > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 
     * 迭代XX：根据id查询时长记录
     * 
     * @Description: selectByPrimaryKey
     * @param id
     * @return
     * @return MerchantUserCutoffDate
     * @author zcj
     * @date 2016-11-30 上午10:15:50
     */
    public UserCutoff selectByPrimaryKey(Long id) {
        UserCutoff result;
        result = userCutoffMapper.selectByPrimaryKey(id);
        return result;
    }

    /**
     * 
     * 迭代XX：TODO 根据商户id和用户id修改一条时长记录
     * 
     * @Description: updateByPrimaryKeySelective
     * @param merchantId
     * @param userId
     * @param cutoffDate
     * @param remarks
     * @return
     * @return Integer 0-失败 1-成功
     * @author zcj
     * @date 2016-11-30 上午10:21:48
     */
    public Integer updateByPrimaryKeySelective(Long id, Long merchantId, Long userId, Date cutoffDate, String remarks) {
        int result = 0;
        if (merchantId == null || userId == null) {
            logger.info("添加时长时商户id和用户id不能为空" + "商户id：" + merchantId + "用户id：" + userId);
            return result;
        }
        UserCutoff md = new UserCutoff();
        md.setId(id);
        md.setCutoffDate(cutoffDate);
        md.setMerchantId(merchantId);
        md.setRemarks(remarks);
        md.setUserId(userId);
        result = userCutoffMapper.updateByPrimaryKeySelective(md);
        if (result > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 
     * 迭代XX：TODO 根据商户id和用户id修改一条时长记录
     * 
     * @Description: updateByPrimaryKeySelective
     * @param merchantId
     * @param userId
     * @param cutoffDate
     * @param remarks
     * @return
     * @return Integer 0-失败 1-成功
     * @author 张智威
     * @date 2016-11-30 上午10:21:48
     */
    public int updateEndDate(Long id, Date cutoffDate) {
      

        return userCutoffMapper.updateEndDate(id, cutoffDate);

    }

    /**
     * 
     * 迭代XX：TODO 根据商户id和用户id修改一条时长记录
     * 
     * @Description: updateByPrimaryKeySelective
     * @param merchantId
     * @param userId
     * @param cutoffDate
     * @param remarks
     * @return
     * @return Integer 0-失败 1-成功
     * @author zcj
     * @date 2016-11-30 上午10:21:48
     */
    /*
     * public Integer updateByPrimaryKeySelective(Long id,Long cutoffDate) { int
     * result = 0 ;
     * 
     * UserCutoff md = new UserCutoff(); md.setId(id);
     * md.setCutoffDate(cutoffDate);
     * 
     * result = userCutoffMapper.updateEndDate(id,cutoffDate); if(result>0){
     * result = 1 ; } return result; }
     */
    /**
     * 
     * 迭代XX：根据id修改一条记录
     * 
     * @Description: updateByPrimaryKey
     * @param record
     * @return
     * @return Integer
     * @author zcj
     * @date 2016-11-30 上午10:23:30
     */
    public Integer updateByPrimaryKey(UserCutoff record) {
        int a = userCutoffMapper.updateByPrimaryKey(record);
        return a;
    }

    /**
     * 
     * 迭代XX：根据商户id和用户id查询时长记录
     * 
     * @Description: selectByPrimaryKey
     * @param map
     * @return
     * @return MerchantUserCutoffDate
     * @author zcj
     * @date 2016-11-30 上午10:15:50
     */
    public List<UserCutoff> selectBymap(Map<String, Object> map) {
        List<UserCutoff> result = userCutoffMapper.selectByMap(map);
        return result;
    }

    /**
     * 
     * 迭代XX：统计数据条数
     * 
     * @Description: queryCountByParam
     * @param id
     * @param merchantId
     * @param userId
     * @param cutoffDate
     * @param remarks
     * @return
     * @return Integer
     * @author zcj
     * @date 2016-12-7 下午4:52:18
     */
    public Integer queryCountByParam(/* Long id, */Long merchantId,
            Long userId/* Date cutoffDate,String remarks */) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        /*
         * map.put("cutoffDate", cutoffDate); map.put("remarks", remarks);
         */
        int a = userCutoffMapper.queryCountByParam(map);
        return a;
    }

    /**
     * 
     * 跟新用户时长前要获取用户当前事件 另外查询用户还有没有事件也要查
     * 
     * @Description: queryCountByParam
     * @param id
     * @param merchantId
     * @param userId
     * @param cutoffDate
     * @param remarks
     * @return
     * @return Integer
     * @author 张智威
     * @date 2016-12-7 下午4:52:18
     */
    public UserCutoff selectByMerIdAndUserId(Long merchantId, Long userId) {
        UserCutoff a = userCutoffMapper.selectByUserIdAndMerId(userId, merchantId);
        return a;
    }

    /**
     * 更新用户时长
     * 
     * @param cutoff_date
     * @return
     * @author 张智威
     * @date 2017年4月10日 下午5:07:04
     */
    /*
     * @Deprecated private int update(Map<String, Object> cutoff_date) { Integer
     * result = 0; if (cutoff_date != null && !cutoff_date.equals("")) { Long
     * merchantId = Long.parseLong(cutoff_date.get("merchantId") + ""); Long
     * userId = Long.parseLong(cutoff_date.get("userId") + ""); Long id =
     * Long.parseLong(cutoff_date.get("id") + ""); SimpleDateFormat format = new
     * SimpleDateFormat("yyyy-M-dd HH:mm:ss"); Date cutoffDate = null; try {
     * cutoffDate = format.parse(cutoff_date.get("cutoffDate").toString()); }
     * catch (ParseException e) { logger.error(e.getMessage()); } String remarks
     * = cutoff_date.get("remarks") + ""; return updateByPrimaryKeySelective(id,
     * merchantId, userId, cutoffDate, remarks); } else { return result; } }
     */

    /**
     * 更新用户时长信息
     * 
     * @param cutoff_date
     * @return
     * @author 张智威
     * @date 2017年4月10日 下午5:28:29
     */
    /*
     * public int updateEndDate(Long id,Long endDate) { return
     * userCutoffService.updateEndDate(id, endDate); }
     */
    /**
     * 增加用户时长 区别于别的增加消费记录 这个是原子式样的增加时长记录记录
     * 
     * @param cutoff_date
     * @return
     * @author 张智威
     * @date 2017年4月10日 下午5:07:04
     */
    /*
     * @Deprecated public int add(Map<String, Object> cutoff_date) { Integer
     * result = 0; if (cutoff_date != null && !cutoff_date.equals("")) { Long
     * merchantId = Long.parseLong(cutoff_date.get("cutoff_date") + ""); Long
     * userId = Long.parseLong(cutoff_date.get("userId") + ""); SimpleDateFormat
     * format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss"); Date cutoffDate =
     * null; try { cutoffDate =
     * format.parse(cutoff_date.get("cutoffDate").toString()); } catch
     * (ParseException e) { logger.error(e.getMessage()); } String remarks =
     * cutoff_date.get("remarks") + ""; result =
     * userCutoffService.insertSelective(merchantId, userId, cutoffDate,
     * remarks); return result; } else { return result; } }
     */

    /**
     * 增加用户时长
     * 
     * @param cutoff_date
     * @return
     * @author 张智威
     * @date 2017年4月10日 下午5:07:04
     */

    /*
     * public int add(UserCutoff cutoff_date) { return
     * userCutoffService.insertSelective(cutoff_date.getMerchantId(),
     * cutoff_date.getUserId(), cutoff_date.getCutoffDate(),
     * cutoff_date.getRemarks()); }
     */
}
