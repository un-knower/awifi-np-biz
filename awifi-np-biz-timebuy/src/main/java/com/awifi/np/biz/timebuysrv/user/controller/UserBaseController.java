/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月14日 下午2:09:59
 * 创建作者：尤小平
 * 文件名称：UserBaseController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.user.controller;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.user.service.UserBaseService;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("rawtypes")
@APIs(description = "用户基本信息维护")
@Controller
@RequestMapping(value = "/timebuysrv/user/base")
public class UserBaseController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * UserBaseService
     */
    @Resource
    private UserBaseService userBaseService;

    /**
     * 根据用户id查询用户基本信息.
     *
     * @param userId 用户id
     * @return Map
     * @throws Exception Exception
     * @author 尤小平
     * @date 2017年4月17日 下午3:47:25
     */
    @API(summary = "根据用户id查询用户基本信息", parameters = {
            @Param(name = "userId", description = "用户id", in = "path", dataType = DataType.LONG, required = true) })
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map view(@PathVariable String userId) throws Exception {
        logger.debug("根据userId查询用户基本信息, userId=" + userId);

        ValidUtil.valid("userId", userId, "numeric");

        PubUser pubUser = userBaseService.getByUseId(Long.valueOf(userId));
        logger.debug("根据userId查询用户基本信息结束" + JsonUtil.toJson(pubUser));

        return this.successMsg(pubUser);
    }

    /**
     * 根据手机号码查询用户基本信息.
     * 
     * @param request 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月21日 下午2:59:18
     */
    @API(summary = "根据手机号码查询用户基本信息", parameters = {
            @Param(name = "telephone", description = "手机号码", dataType = DataType.STRING, required = true) })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getUserByTelphone(HttpServletRequest request) throws Exception {
        // 获取参数
        logger.info("request params=" + JsonUtil.toJson(request.getParameterMap()));
        String telephone = request.getParameter("telephone");// 用户手机号码

        // 参数校验
        ValidUtil.valid("用户手机号码", telephone, "required");

        // 获取数据中心--用户id
        Long userId = UserBaseClient.getUserIdByPhone(telephone);
        PubUser pubUser = null;
        if (userId != null) {
            pubUser = userBaseService.getByUseId(Long.valueOf(userId));
        }

        logger.debug("根据手机号码查询用户基本信息结束:" + JsonUtil.toJson(pubUser));

        return this.successMsg(pubUser);
    }

    /**
     * 根据session查询用户基本信息.
     * 
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月5日 上午10:12:55
     */
    @API(summary = "根据session查询用户基本信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getUserInfo(HttpServletRequest request) throws Exception {

        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        logger.debug("根据session查询用户基本信息, sessionDTO=" + JsonUtil.toJson(sessionDTO));

        if (sessionDTO == null) {
            throwValidException("E2000056", "sessionDTO");// sessionDTO不存在
            return this.successMsg();
        }

        SessionUser sessionUser = sessionDTO.getSessionUser();
        if (sessionUser == null) {
            throwValidException("E0000002", "sessionUser");// sessionUser不允许为空!
            return this.successMsg();
        }

        if (sessionUser.getId() == null || sessionUser.getId() < 1) {
            throwValidException("E0000002", "session中用户id");// session中用户id不允许为空!
            return this.successMsg();
        }

        PubUser pubUser = userBaseService.getByUseId(sessionUser.getId());
        logger.debug("根据session查询用户基本信息结束, merchantNews=" + JsonUtil.toJson(pubUser));

        return this.successMsg(pubUser);
    }
    
    /**
     * 添加用户基本信息.
     * 
     * @param request
     *            request
     * @return Map
     * @throws Exception
     *             Exception
     * @author 尤小平
     * @date 2017年4月17日 下午3:47:43
     */
    @API(summary = "添加用户基本信息", parameters = {
            @Param(name = "faceInfo", description = "头像", dataType = DataType.STRING, required = false),
            @Param(name = "userNick", description = "昵称", dataType = DataType.STRING, required = false),
            @Param(name = "sex", description = "性别", dataType = DataType.STRING, required = false),
            @Param(name = "birthday", description = "生日", dataType = DataType.DATE, required = false),
            @Param(name = "address", description = "地址", dataType = DataType.STRING, required = false),
            @Param(name = "telphone", description = "手机号码", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map add(HttpServletRequest request) throws Exception {
        logger.debug("添加用户基本信息,请求参数:" + JsonUtil.toJson(request.getParameterMap()));

        String faceInfo = request.getParameter("faceInfo");
        String userNick = request.getParameter("userNick");
        String sex = request.getParameter("sex");
        String address = request.getParameter("address");
        String birthday = request.getParameter("birthday");
        String telphone = request.getParameter("telphone");

        PubUser pubUser = getPubUser(faceInfo, userNick, sex, birthday, address, telphone);

        Long userId = userBaseService.add(pubUser);
        logger.debug("添加用户基本信息结束, userId=" + userId);

        return this.successMsg(userId);
    }

    /**
     * 根据用户id更新用户基本信息.
     *
     * @param userId userId
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:48:50
     */
    @API(summary = "根据用户id更新用户基本信息", parameters = {
            @Param(name = "userId", description = "用户id", in = "path", dataType = DataType.LONG, required = true),
            @Param(name = "faceInfo", description = "头像", dataType = DataType.STRING, required = false),
            @Param(name = "userNick", description = "昵称", dataType = DataType.STRING, required = false),
            @Param(name = "sex", description = "性别", dataType = DataType.STRING, required = false),
            @Param(name = "birthday", description = "生日", dataType = DataType.DATE, required = false),
            @Param(name = "address", description = "地址", dataType = DataType.STRING, required = false),
            @Param(name = "telphone", description = "手机号码", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map update(@PathVariable(value = "userId", required = true) String userId, HttpServletRequest request)
            throws Exception {
        logger.debug("根据id更新用户基本信息, 请求参数:" + JsonUtil.toJson(request.getParameterMap()) + ", userId=" + userId);

        String faceInfo = request.getParameter("faceInfo");
        String userNick = request.getParameter("userNick");
        String sex = request.getParameter("sex");
        String address = request.getParameter("address");
        String birthday = request.getParameter("birthday");
        String telphone = request.getParameter("telphone");

        ValidUtil.valid("userId", userId, "numeric");

        PubUser pubUser = getPubUser(faceInfo, userNick, sex, birthday, address, telphone);
        pubUser.setId(Long.valueOf(userId));

        userBaseService.update(pubUser);
        logger.debug("根据id更新用户基本信息结束");

        return this.successMsg();
    }

    /**
     * 根据参数返回PubUser对象.
     *
     * @param faceInfo faceInfo
     * @param userNick userNick
     * @param sex sex
     * @param birthday birthday
     * @param address address
     * @param telphone telphone
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:49:02
     */
    private PubUser getPubUser(String faceInfo, String userNick, String sex, String birthday, String address,
            String telphone) throws Exception {
        PubUser pubUser = new PubUser();
        if (faceInfo != null && faceInfo.length() > 0) {
            pubUser.setFaceInfo(faceInfo);
        }
        if (userNick != null && userNick.length() > 0) {
            pubUser.setUserNick(userNick);
        }
        if (sex != null && sex.length() > 0) {
            pubUser.setSex(sex);
        }
        if (birthday != null && birthday.length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(birthday);
            pubUser.setBirthday(birthDate);
        }
        if (address != null && address.length() > 0) {
            pubUser.setAddress(address);
        }
        if (telphone != null && telphone.length() > 0) {
            pubUser.setTelphone(telphone);
        }

        return pubUser;
    }

    /**
     * for testing only.
     * 
     * @param userBaseService UserBaseService
     * @author 尤小平  
     * @date 2017年5月5日 上午10:38:09
     */
    protected void setUserBaseService(UserBaseService userBaseService) {
        this.userBaseService = userBaseService;
    }
    
    /**
     * for testing.
     * 
     * @param code code
     * @param message message
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:15:18
     */
    protected void throwValidException(String code, String message) {
        throw new ValidException(code, MessageUtil.getMessage(code, message));
    }
}
