package com.awifi.np.biz.timebuysrv.web.module.time.dao;




import java.util.List;
import java.util.Map;

import com.awifi.np.biz.timebuysrv.web.module.time.model.Order;




public interface CenterOrderDao {
    /**
     * 新增订单
     * @param order 订单对象
     * @return int
     * 插入行数
     */
    int addOrder(Order order);
    /**
     * 更新订单
     * @param order 订单对象
     * @return
     * 更新记录数
     */
    int updateOrder(Order order);
    /**
     * 根据条件查询订单
     * @param map
     * merchantId
     * @return
     * list 订单对象
     */
    List<Order> queryListByParam(Map<String, Object> map);
    /**
     * 根据条件查询订单总记录数
     * @param map
     * merchantId
     * @return int
     * 总记录数
     */
    int queryCountByParam(Map<String, Object> map);
    /**
     * 订单完工
     * @param order 订单对象
     * @return int
     * 更新记录数
     */
    int finishOrder(Order order);
    
}
