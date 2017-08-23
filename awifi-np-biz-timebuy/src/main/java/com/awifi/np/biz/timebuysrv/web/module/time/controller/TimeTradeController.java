package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.biz.TimeTradeBiz;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "时间交易接口")
@Controller
@RequestMapping("/timebuysrv/time/trade")
public class TimeTradeController extends BaseController {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimeTradeController.class);
    /** * 引入 packageLogicService . */

    /** * 引入 timeBuyService . */
    @Resource
    private TimeBuyService timeBuyService;
    /*** 时间变化 业务接口 **/
    @Resource
    private TimeTradeBiz timeTradeBiz;

    /** * 引入AccessAuth . */

    /**
     * 消费列表接口
     * 
     * @param request
     *            请求
     * @return Map
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年5月3日 下午3:03:26
     */

    @API(summary = "批量赠送时长", description = "通过excel 导入时长  正常返回参数{ \"msg\": \"导入完成，成功导入1条，失败0条。\", \"code\": \"0\" } 异常返回{\"msg\": \"接口无返回值!\", \"code\": \"E2000018\"} ", parameters = {
            @Param(name = "file", description = "通过excel 导入时长  ", dataType = DataType.FILE, required = true),
            @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = true),
            @Param(name = "hours", description = "小时数", dataType = DataType.LONG, required = false), 
            @Param(name = "days", description = "天数", dataType = DataType.LONG, required = false), 
    })
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map uploadSubmit(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "merid") Long merId,
            HttpServletRequest request) throws Exception {
        String hours = request.getParameter("hours");  // 兼容上传小时
        String days = request.getParameter("days"); //兼容上传天数
        float daysInt = 0;
     
        if(StringUtil.isNotBlank(days)){
             daysInt =Integer.valueOf(request.getParameter("days"));
        }else{
            if(StringUtil.isNotBlank(hours)){
                Integer hourInt =Integer.valueOf(request.getParameter("hours"));
                daysInt = hourInt*1f/24;
            }else{
                throw new BizException("E2017626103239","day和hour不能都为空,必须制定一个时间"); 
            }
        }
        
        com.awifi.np.biz.common.security.user.model.SessionUser sessionUser = new com.awifi.np.biz.common.security.user.model.SessionUser();// SessionUtil.getCurSessionUser(request)
        String result = timeTradeBiz.importExcel(file, merId, daysInt, sessionUser.getUserName());
        HashMap<String,Object> resultMap =new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("msg",result);
        return resultMap;
    }

    public TimeTradeBiz getTimeChangeBiz() {
        return timeTradeBiz;
    }

    public void setTimeChangeBiz(TimeTradeBiz timeTradeBiz) {
        this.timeTradeBiz = timeTradeBiz;
    }

}
