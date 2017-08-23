package com.awifi.np.biz.timebuysrv.user.service;

import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;

/**
 * Created by dozen.zhang on 2017/3/10.
 */
public interface IThirdUserService {
    public SessionUser getUserByPhone(String phone)throws  Exception;

    public SessionUser getUserById(Long id);

    public void updateUser(SessionUser sessionUser) throws Exception;
    public void addUser(SessionUser sessionUser) throws Exception;
    public void loginByPhoneAndSMS(SessionDTO sessionDTO, String phone , String captcha) throws Exception;
}
