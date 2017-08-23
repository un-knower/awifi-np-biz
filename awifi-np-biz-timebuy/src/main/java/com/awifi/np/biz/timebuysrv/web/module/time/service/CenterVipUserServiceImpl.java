package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.CenterVipUserDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;

@Service("CenterVipUserService")
public class CenterVipUserServiceImpl {

	/**
	 * @Fields centerVipUserDao
	 */
	@Autowired
	private CenterVipUserDao centerVipUserDao;

	public long add(VipUserObject onlineUser) {
		return centerVipUserDao.add(onlineUser);
	}

	public int update(VipUserObject onlineUser) {
		return centerVipUserDao.update(onlineUser);
	}

	public int queryOnlineUserCount(Map<String, Object> map) {
		return centerVipUserDao.queryOnlineUserCount(map);
	}

	public VipUserObject queryLastOnlineUser(Map<String, Object> map) {
		return centerVipUserDao.queryLastOnlineUser(map);
	}

	public List<VipUserObject> queryVipUserList(Map<String, Object> map) {
		return centerVipUserDao.queryListByMerArea(map);
	}

	public int queryVipUserCount(Map<String, Object> map) {
//		return centerVipUserDao.queryCountByMerArea(map);
	    return centerVipUserDao.queryVipUserCount(map);//换了方法
	}

	public int updateVipUser(VipUserObject onlineUser) {
		return centerVipUserDao.updateVipUser(onlineUser);
	}
	
	
	public List<VipUserObject> queryListByParam(Map<String, Object> map){
	    return centerVipUserDao.queryListByParam(map);
	}
	/**
	 * 根据手机号查询此时此刻(start_tiem/end_time)它是否是VIP用户
	 * 
	 * @param telephone
	 * @return
	 * @author 余红伟 
	 * @date 2017年4月26日 下午5:31:15
	 */
	public boolean isVipUser(Long telephone){
	    return centerVipUserDao.isVipUser(telephone) >0 ? true : false;
	}
	/**
	 *  测试
	 * @param dao
	 * @author 余红伟 
	 * @date 2017年4月21日 上午10:05:56
	 */
	public void setCenterOnlineUserDao(CenterVipUserDao dao){
	    this.centerVipUserDao = dao;
	}
}
