package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.CenterOrderDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.Order;

@Service
public class CenterOrderServiceImpl {
	/**
	 * centerOrderMapper
	 */
	@Autowired
	private CenterOrderDao centerOrderMapper;

	public int addOrder(Order order) {
		return centerOrderMapper.addOrder(order);
	}

	public int updateOrder(Order order) {
		return centerOrderMapper.updateOrder(order);
	}

	public List<Order> queryListByParam(Map<String, Object> map) {
		return centerOrderMapper.queryListByParam(map);
	}

	public int queryCountByParam(Map<String, Object> map) {
		return centerOrderMapper.queryCountByParam(map);
	}

	public int finishOrder(Order order) {
		return centerOrderMapper.finishOrder(order);
	}

}
