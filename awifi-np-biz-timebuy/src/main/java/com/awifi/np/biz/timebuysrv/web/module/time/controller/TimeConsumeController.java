package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.PubMerchant;
import com.awifi.np.biz.timebuysrv.merchant.service.PubMerchantService;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import com.awifi.np.biz.timebuysrv.user.service.UserAuthService;
import com.awifi.np.biz.timebuysrv.user.service.UserBaseService;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.core.annotation.RequiresUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserConsumeService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserCutoffService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "时间消费接口")
@Controller
@RequestMapping("/timebuysrv/time/consume")
public class TimeConsumeController extends BaseController {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimeConsumeController.class);
    /** 用户消费服务 **/
    /** * 引入 UserConsumeService . */
    @Resource
    private UserConsumeService userConsumeService;

    @Resource
    private PubMerchantService merchantService;
    /** 用户服务 **/

    @Resource
    UserBaseService userBaseService;
    /***用户剩余时长服务**/
    @Resource
    UserCutoffService userCutoffService;
    
    @Resource
    private UserAuthService userAuthService;
    /** 时长购买服务 **/
    @Resource
    private TimeBuyService timeBuyService;

    /**
     * 消费列表接口 只是来查询type为3的consume记录
     * 
     * @param request
     *            请求
     * @return Map
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年5月3日 下午3:03:26
     */
    @API(summary = "个人消费列表", description = "返回当前人消费记录(仅限电渠消费)  "
            + "data:{records:列表数据(consumeType=3是充值 consumeType=2是赔付 addDay是代表充值的天数 payNum=是充值金额)," + "phone:手机号码"
            + "endDate:结束时间,pageNo:当前页,pageSize:页大小,totalRecord:总记录数,totalPage:总页数} " + "", parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
                    @Param(name = "pageNo", description = "当前页", dataType = DataType.INTEGER, required = true), })
    @APIResponse(value = "")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @RequiresUser
    @ResponseBody
    public Map<String, Object> getList(HttpServletRequest request) throws Exception {
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));// 分页大小
        int pagenum = Integer.valueOf(request.getParameter("pageNo"));// 分页当前页
        SessionUser sessionUser = sessionDTO.getSessionUser();// 从session中获取用户的sessionUser
        Long userId = sessionUser.getId();// 从session中获取用户的id
        UserConsume userConsume = new UserConsume();// 查询bean
        userConsume.setUserId(userId); // 设置用户的id
        userConsume.setMerchantId(sessionDTO.getMerchant().getMerchantId());// 设置商户id
        Map<String, Object> returnMap = new HashMap<>();    // 返回数据
        // userConsume.setConsumeType(3);   // 电渠记录
        returnMap.put("pageNo", pagenum);   // 查询当前页
        returnMap.put("pageSize", pageSize);    // 页查询大小
        int count = userConsumeService.queryCountByParam(userConsume); // 记录总数
        returnMap.put("totalRecord", count);    // 换算出总的记录数
        returnMap.put("totalPage", count / pageSize);   // 换算总页数
        returnMap.put("phone", sessionDTO.getSessionUser().getPhone()); // 当前人的手机号码
        returnMap.put("endDate", sessionDTO.getTimeInfo().getEndTime()); // 当前人的时间截止
        returnMap.put("records", userConsumeService.queryListByParam(userConsume, pagenum * pageSize, pageSize));// 消费记录
        logger.info("调用了消费列表查到了");
        return this.successMsg(returnMap);
    }

    /**
     * 消费列表接口 只是来查询type为3的consume记录
     * 
     * @param request
     *            请求
     * @return Map
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年5月3日 下午3:03:26
     */
    @API(summary = "同步商户信息", description = "同步消费表里的所有未同步的商户信息到本地库" + "", parameters = {})
    @APIResponse(value = "")
    @RequestMapping(value = "synMerchant", method = RequestMethod.GET, produces = "application/json")
    @RequiresUser
    @ResponseBody
    public Map<String, Object> synChronizeMerchant(HttpServletRequest request) throws Exception {
        // 查出所有的消费表里的商户信息;

        List<UserConsume> userConsumes = userConsumeService.getNotSynMerchantId(new HashMap<String, Object>());

        for (UserConsume userConsume : userConsumes) {
            Long merchantId = userConsume.getMerchantId();
            // 从数据中心查找商户信息
            if (merchantId != null && merchantId > 0) {// 防止数据中心报错
                try {// 无论如何都要进行下去
                    Merchant merchant = MerchantClient.getById(merchantId);
                    PubMerchant pubMerchant = JsonUtil.fromJson(JsonUtil.toJson(merchant), PubMerchant.class);
                    merchantService.saveMerchant(pubMerchant);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return this.successMsg();
    }

    /**
     * 消费列表接口 只是来查询type为3的consume记录
     * 
     * @param request
     *            请求
     * @return Map
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年5月3日 下午3:03:26
     */
    @API(summary = "同步用户信息", description = "同步消费表里的所有未同步的用户信息到本地库" + "", parameters = {})
    @APIResponse(value = "")
    @RequestMapping(value = "synUser", method = RequestMethod.GET, produces = "application/json")
    @RequiresUser
    @ResponseBody
    public Map<String, Object> synChronizeUser(HttpServletRequest request) throws Exception {
        // 查出所有的消费表里的商户信息;

        List<UserConsume> userConsumes = userConsumeService.getNotSynUserId(new HashMap<String, Object>());

        for (UserConsume userConsume : userConsumes) {
            Long userId = userConsume.getUserId();
            // 从数据中心查找商户信息
            if (userId != null && userId > 0) {// 防止数据中心报错
                try {// 无论如何都要进行下去

                    PubUser pubUser = UserBaseClient.queryByUserId(userId);
                    SysUser sysUser = new SysUser();
                    sysUser = userBaseService.getSysUserFromPubUser(sysUser, pubUser);

                    userBaseService.addSysUser(sysUser);
                } catch (Exception e) {
                    logger.warn(userId+"not found");
                   // e.printStackTrace();
                }

            }

        }
        return this.successMsg();
    }

    /**
     * 园区app消费列表对外接口,查询type为2和3的consume记录
     * 
     * @param request 请求
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月31日 下午2:03:01
     */
    @API(summary = "园区app消费列表", description = "返回当前人消费记录(仅限电渠消费)  "
            + "data:{records:列表数据(consumeType=3是充值 consumeType=2是赔付 addDay是代表充值的天数 payNum=是充值金额)," + "phone:手机号码"
            + "endDate:结束时间,pageNo:当前页,pageSize:页大小,totalRecord:总记录数,totalPage:总页数} " + "", parameters = {
                    @Param(name = "telephone", description = "手机号码", dataType = DataType.PHONE, required = true),
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = false),
                    @Param(name = "pageNo", description = "当前页", dataType = DataType.INTEGER, required = false), })
    @APIResponse(value = "")
    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getListApi(HttpServletRequest request) throws Exception {
        // 获取参数
        logger.info("request params= " + JsonUtil.toJson(request.getParameterMap()));
        String telephone = request.getParameter("telephone");// 用户手机号码
        Integer pageSize = CastUtil.toInteger(request.getParameter("pageSize"));// 分页大小
        Integer pagenum = CastUtil.toInteger(request.getParameter("pageNo"));// 当前页

        // 参数校验
        ValidUtil.valid("用户手机号码", telephone, "required");

        if (pageSize == null || pageSize <= 0) {
            pageSize = 15;
        }
        if (pagenum == null || pagenum < 0) {
            pagenum = 0;
        }

        // 获取用户信息,获取用户的id
        PubUserAuth user = UserAuthClient.getUserByLogName(telephone);
        UserConsume userConsume = new UserConsume();// 查询bean
        userConsume.setUserId(user.getUserId()); // 设置用户的id

        Map<String, Object> returnMap = new HashMap<>(); // 返回数据
        returnMap.put("pageNo", pagenum); // 查询当前页
        returnMap.put("pageSize", pageSize); // 页查询大小
        int count = userConsumeService.queryCountByParam(userConsume); // 记录总数
        returnMap.put("totalRecord", count); // 换算出总的记录数
        returnMap.put("totalPage", count % pageSize == 0 ? count / pageSize : count / pageSize + 1);// 换算总页数
        returnMap.put("phone", telephone); // 当前人的手机号码
        returnMap.put("records", userConsumeService.queryListByParam(userConsume, pagenum * pageSize, pageSize));// 消费记录

        logger.info("调用了消费列表查到了,record count=" + count);
        return this.successMsg(returnMap);
    }
    
    
    /**
     * 园区app消费列表对外接口,查询type为2和3的consume记录
     * 
     * @param request 请求
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 张智威
     * @date 2017年8月14日13:40:43
     */
    @API(summary = "园区app消费赔付记录回退", description = "园区app消费赔付记录回退", parameters = {
                    //@Param(name = "consumeId", description = "消费记录id", in="path",dataType = DataType.PHONE, required = true),
              
                  })
    @APIResponse(value = "")
    @RequestMapping(value = "/returnBack/{consumeId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map<String, Object> returnBack(@PathVariable Long consumeId,HttpServletRequest request) throws Exception {
        // 获取参数
        logger.info("request params= " + JsonUtil.toJson(request.getParameterMap()));
      

        // 参数校验
        ValidUtil.valid("消费记录id", consumeId, "required");

        

        // 获取用户信息,获取用户的id
        UserConsume userConsume = this.userConsumeService.selectByPrimaryKey(consumeId);
        if(userConsume==null){
            logger.warn("该记录已经被删除");
            throw new BizException("E017081893","该记录已经被删除");
        }
        float addDay = userConsume.getAddDay();
        
        /**查找用户和商户下的用户时长记录 减少他的值**/
        if(addDay<=0){
            logger.warn("记录的增加天数为非正整数");
            throw new BizException("E017081894","记录存在数值错误");
        }
        if(userConsume.getUserId()==null  || userConsume.getUserId()<0|| userConsume.getMerchantId() == null || userConsume.getMerchantId() <=0){
            logger.warn("consumeId:"+consumeId+"的记录有错误请核对");
            throw new BizException("E017081895","记录存在数值错误");
        }
       
        List<UserCutoff> cutoffDateList= this.timeBuyService.getUserCutoffList(userConsume.getUserId(), userConsume.getMerchantId());
        
        //一般是有记录的 
        if(cutoffDateList!=null && cutoffDateList.size()>0){
            UserCutoff userCutoff = cutoffDateList.get(0);
            Date userCutoffDate  = userCutoff .getCutoffDate();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(userCutoffDate);
          
            
          
            calendar.add(Calendar.HOUR, (int)(-addDay*24));
            userCutoff.setCutoffDate(calendar.getTime());
            this.userCutoffService.updateByPrimaryKey(userCutoff);
            userConsumeService.cancel(consumeId);
        }else{
            
        }
        return this.successMsg();
    }
    
    
    public UserConsumeService getUserConsumeService() {
        return userConsumeService;
    }

    public void setUserConsumeService(UserConsumeService userConsumeService) {
        this.userConsumeService = userConsumeService;
    }
}
