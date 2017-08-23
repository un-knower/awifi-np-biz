package com.awifi.np.biz.timebuysrv.web.module.time.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;

/**
 * 用户消费记录dao
 * @author 张智威
 * 2017年4月10日 下午3:43:59
 */
public interface UserConsumeDao {
    
    UserConsume selectByPrimaryKey(Long id);
    
    int insertSelective(UserConsume consume);

    int updateByPrimaryKeySelective(UserConsume consume);

    List<UserConsume> listByParams(Map<String, Object> map);
    
    List<UserConsume> listByParams4Page(Map<String, Object> map);
    
    int countByParams(Map<String, Object> map);
    
    List<UserConsume> unionListByParams4Page(Map<String, Object> map);
    
   
    int unionCountByParams(Map<String, Object> map);

    Double getUserTotalPayment(Map<String, Object> map);
    
    List<UserConsume> getNotSynMerchantId(Map<String, Object> map);
    
    List<UserConsume> getNotSynUserId(Map<String, Object> map);
    
    int cancel(long id);
}
