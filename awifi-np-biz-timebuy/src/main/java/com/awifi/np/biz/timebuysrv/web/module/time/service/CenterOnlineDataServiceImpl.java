package com.awifi.np.biz.timebuysrv.web.module.time.service;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.CenterOnlineDataDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.CenterOnlineDataObject;

@Service
public class CenterOnlineDataServiceImpl {

	private CenterOnlineDataDao onlineDataMapper;

	public void add(CenterOnlineDataObject onlineData) {
		onlineDataMapper.add(onlineData);
	}

}
