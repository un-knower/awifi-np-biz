package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.UserConsumeDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;


/**
 * 用户消费记录接口实现
 * @author 张智威
 * 2017年6月7日 上午10:13:42
 */
@Service(value = "localUserConsumeService")
public class UserConsumeService {

    private static final Logger logger = LoggerFactory.getLogger(UserConsumeService.class);

    @Resource
    private UserConsumeDao userConsumeMapper;

    @Resource
    private UserCutoffService userCutoffService;

    /**
     * 新增消费记录
     * 
     * @param consumeObject
     * @return
     */
    public int add(UserConsume consumeObject) throws Exception {
        if (consumeObject.getMerchantId() == null || consumeObject.getUserId() == null
                || consumeObject.getConsumeType() == null) {
            throw new Exception("新增消费数据出错,必要参数缺少");
        }
        // create_date如果为空默认保存当前日期时间
        if (consumeObject.getCreateDate() == null) {
            consumeObject.setCreateDate(DateUtil.getNowDate());
        }
        return userConsumeMapper.insertSelective(consumeObject);
    }

    /**
     * 查询消费记录列表
     * 
     * @param map
     * @return
     */
    public List<UserConsume> queryListByParam(Map<String, Object> map) throws Exception {
        List<UserConsume> consumeList = null;
        String consumeType = map.get("consumeType") + "";
        if (!"2".equals(consumeType) && !"3".equals(consumeType)) {
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        if (map.get("pageNum") != null && map.get("pageSize") != null) {
            int pageNum = Integer.valueOf(map.get("pageNum").toString());
            int pageSize = Integer.valueOf(map.get("pageSize").toString());
            if (pageNum <= 0 || pageSize <= 0) {
                throw new Exception("根据分页条件查询消费记录出错:分页条件出错");
            }
            map.put("start", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            consumeList = userConsumeMapper.unionListByParams4Page(map);
        } else {
            consumeList = userConsumeMapper.listByParams(map);
        }
        return consumeList;
    }

    /**
     * 查询记录数 目前是用来检查是不是领取过免费礼包
     * @param map
     * @return
     * @throws Exception
     * @author 张智威  
     * @date 2017年6月7日 上午10:14:20
     */
    public int queryCountByParam(Map<String, Object> map) throws Exception {
        String consumeType = map.get("consumeType") + "";
        if (!"2".equals(consumeType) && !"3".equals(consumeType)) {
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        return userConsumeMapper.unionCountByParams(map);
    }

    /**
     * 查询记录列表
     * @param consumeObject
     * @return
     * @throws Exception
     * @author 张智威  
     * @date 2017年6月7日 上午10:14:30
     */
    public List<UserConsume> queryListByParam(UserConsume consumeObject) throws Exception {
        List<UserConsume> consumeList = null;
        if (consumeObject == null) {
            throw new Exception("传入的对象为空");
        }
        String consumeType = consumeObject.getConsumeType() + "";
        if (!"2".equals(consumeType) && !"3".equals(consumeType)) {
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (consumeObject.getId() != null && consumeObject.getId() > 0) {
            map.put("id", consumeObject.getId());
        }
        if (consumeObject.getMerchantId() != null && consumeObject.getMerchantId() > 0) {
            map.put("merchantId", consumeObject.getMerchantId());
        }
        if (consumeObject.getUserId() != null && consumeObject.getUserId() > 0) {
            map.put("userId", consumeObject.getUserId());
        }
        if (consumeObject.getConsumeType() != null && consumeObject.getConsumeType() > 0) {
            map.put("consumeType", consumeObject.getConsumeType());
        }
        if (consumeObject.getPackageId() != null && consumeObject.getPackageId() > 0) {
            map.put("packageId", consumeObject.getPackageId());
        }
        map.put("consumeType", consumeObject.getConsumeType());
        if (!StringUtil.isBlank(consumeObject.getOrderId())) {
            map.put("orderId", consumeObject.getOrderId());
        }
        consumeList = userConsumeMapper.listByParams(map);
        return consumeList;
    }
    
    
    /**
     * 查询记录列表
     * @param consumeObject
     * @param start
     * @param pageSize
     * @return
     * @throws Exception
     * @author 张智威  
     * @date 2017年6月7日 上午10:14:45
     */
    public List<UserConsume> queryListByParam(UserConsume consumeObject,int start,int pageSize) throws Exception {
        List<UserConsume> consumeList = null;
        if (consumeObject == null) {
            throw new Exception("传入的对象为空");
        }
        //String consumeType = consumeObject.getConsumeType() + "";
      /*  if (!"2".equals(consumeType) && !"3".equals(consumeType)) {
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        if (consumeObject.getId() != null && consumeObject.getId() > 0) {
            map.put("id", consumeObject.getId());
        }
        if (consumeObject.getMerchantId() != null && consumeObject.getMerchantId() > 0) {
            map.put("merchantId", consumeObject.getMerchantId());
        }
        if (consumeObject.getUserId() != null && consumeObject.getUserId() > 0) {
            map.put("userId", consumeObject.getUserId());
        }
        if (consumeObject.getConsumeType() != null && consumeObject.getConsumeType() > 0) {
            map.put("consumeType", consumeObject.getConsumeType());
        }
        if (consumeObject.getPackageId() != null && consumeObject.getPackageId() > 0) {
            map.put("packageId", consumeObject.getPackageId());
        }
        map.put("consumeType", consumeObject.getConsumeType());
        if (!StringUtil.isBlank(consumeObject.getOrderId())) {
            map.put("orderId", consumeObject.getOrderId());
        }
        map.put("start", start);
        map.put("pageSize", pageSize);
        consumeList = userConsumeMapper.unionListByParams4Page(map);
        return consumeList;
    }
    
    /**
     * 查询记录数
     * @param consumeObject
     * @return
     * @throws Exception
     * @author 张智威  
     * @date 2017年6月7日 上午10:15:09
     */
    public int queryCountByParam(UserConsume consumeObject) throws Exception {
        if (consumeObject == null) {
            throw new Exception("传入的对象为空");
        }
        String consumeType = consumeObject.getConsumeType() + "";
        /*if (!"2".equals(consumeType) && !"3".equals(consumeType)) {
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        if (consumeObject.getId() != null && consumeObject.getId() > 0) {       //主键
            map.put("id", consumeObject.getId());
        }
        if (consumeObject.getMerchantId() != null && consumeObject.getMerchantId() > 0) {       //商户id
            map.put("merchantId", consumeObject.getMerchantId());
        }
        if (consumeObject.getUserId() != null && consumeObject.getUserId() > 0) {
            map.put("userId", consumeObject.getUserId());
        }
        if (consumeObject.getConsumeType() != null && consumeObject.getConsumeType() > 0) { //消费类型
            map.put("consumeType", consumeObject.getConsumeType());
        }
        if (consumeObject.getPackageId() != null && consumeObject.getPackageId() > 0) { //套餐id
            map.put("packageId", consumeObject.getPackageId());
        }
        map.put("consumeType", consumeObject.getConsumeType());
        if (!StringUtil.isBlank(consumeObject.getOrderId())) {  //订单id
            map.put("orderId", consumeObject.getOrderId());
        }
        return userConsumeMapper.countByParams(map);
    }

    /**
     * 增加消费记录并更新上网时限表
     * @param userConsume
     * @throws Exception
     * @author 张智威  
     * @date 2017年6月7日 上午10:15:17
     */
    public void addCompConsume(UserConsume userConsume) throws Exception {
        if (userConsume.getMerchantId() == null || userConsume.getUserId() == null
                || userConsume.getConsumeType() == null || userConsume.getEndDate() == null) {
            throw new Exception("保存消费记录出错，缺少必要参数");
        }
        try {
            this.add(userConsume);
        } catch (Exception e) {
            logger.error("addCompConsume接口出错：" + e.getMessage());
            throw e;
        }
    }

    /**
     * 获取用户消费总金额
     * @param map 用户消费总金额
     * @return  用户消费总结
     * @throws Exception 异常
     * @author 张智威  
     * @date 2017年6月7日 上午10:15:28
     */
    public Double getUserTotalPayment(Map<String, Object> map) throws Exception {
        String consumeType = map.get("consumeType") + "";
        if (!"2".equals(consumeType) && !"3".equals(consumeType)) {
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        return userConsumeMapper.getUserTotalPayment(map);
    }

   

    /**
     * 用是否有时长消费认证
     * 
     * @param userId 用户id
     * @param merchantId 商户id
     * @return boolean 是否是能领取免费礼包
     * @throws Exception 数据库异常
     * @author 张智威
     * @date 2017年4月10日 下午6:47:35
     */
    public Boolean canGetFreePkg(Long userId, Long merchantId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (userId == null || merchantId == null){
            return false;
        }
        parm.put("userId", userId);
        parm.put("merchantId", merchantId);
        parm.put("consumeType", 2);
        parm.put("packageKey", 201);//查找商户下该用户有没有领取过免费礼包
        //TODO 增加商户有没有配置免费礼包 校验
        if (queryCountByParam(parm) > 0) {
            return false;
        } else {
            return true;
        }
    }
   
   /**
    * 得到所有未同步的商户信息 
    * @param map  现在其实没有任何参数
    * @return 得到所有未同步的商户信息
    * @author 张智威  
    * @date 2017年8月10日 下午3:37:08
    */
    public List<UserConsume> getNotSynMerchantId(Map<String, Object> map){
        return userConsumeMapper.getNotSynMerchantId(map);
    }
    /**
     * 得到所有未同步的用户信息 
     * @param map 现在其实没有任何参数
     * @return List<UserConsume> 查询结果
     * @author 张智威  
     * @date 2017年8月10日 下午3:37:08
     */
    public List<UserConsume> getNotSynUserId(Map<String, Object> map){
        return userConsumeMapper.getNotSynUserId(map);
    }
    /**
     * 根据id拿到记录
     * @param id 主键
     * @return UserConsume 查询结果
     * @author 张智威  
     * @date 2017年8月11日 下午2:16:35
     */
    public UserConsume selectByPrimaryKey(long id){
        return userConsumeMapper.selectByPrimaryKey(id);
    }
    
    
    /**
     * 根据id撤销记录
     * @param id 消费揭露id
     * @return 是否撤销成功
     * @author 张智威  
     * @date 2017年8月11日 下午2:16:35
     */
    public int cancel(long id){
        return userConsumeMapper.cancel(id);
    }
}
