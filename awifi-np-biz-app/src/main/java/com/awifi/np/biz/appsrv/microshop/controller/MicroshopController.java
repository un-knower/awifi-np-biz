package com.awifi.np.biz.appsrv.microshop.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.MicroshopService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 下午2:53:41
 * 创建作者：周颖
 * 文件名称：MenuController.java
 * 版本：  v1.0
 * 功能：菜单控制层
 * 修改记录：
 */
@Controller
@RequestMapping("/appsrv")
@SuppressWarnings("rawtypes")
public class MicroshopController extends BaseController {

    /** 微旺铺业务层 */
    @Resource(name = "microshopService")
    private MicroshopService microShopService;

    /** 应用管理--微托、聚来宝--关联配置接口
     * @param request 请求
     * @param access_token  访问令牌
     * @param bodyParam  参数
     * @return 结果
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月14日 下午3:12:11
     */
    @ResponseBody
    @RequestMapping(value="/microshop/relation", method= RequestMethod.PUT)
    public Map relation(HttpServletRequest request, @RequestParam(value="access_token",required=true) String access_token,
                        @RequestBody(required=true) Map<String, Object> bodyParam) throws Exception {
        String appId = (String) bodyParam.get("appId");//应用id，不允许为空
        Long relateMerchantId = CastUtil.toLong(bodyParam.get("relateMerchantId"));//关联商户id，不允许为空，数字
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Long merchantId = user.getMerchantId();
        if (merchantId == null) {
            throw new BizException("E2900001", MessageUtil.getMessage("E2900001"));// 用户id为空
        }
        microShopService.relate(merchantId,appId,relateMerchantId);
        return this.successMsg();//返回成功信息
    }

    /** 应用管理--微托、聚来宝--模式生效接口
     * @param request 请求
     * @param spreadmodel 1 代表 模式一、2 代表 模式二
     * @param appid 第三方id
     * @param access_token  访问令牌
     * @return 结果
     * @author 梁聪
     * @date 2017年7月17日 下午3:12:11
     */
    @ResponseBody
    @RequestMapping(value="/microshop/{appid}/model/{spreadmodel}", method= RequestMethod.PUT)
    public Map updateSpreadModel(HttpServletRequest request,
                                 @PathVariable int spreadmodel,//推广模式，不允许为空，数字，其中：1代表模式一、2代表模式二
                                 @PathVariable String appid,//应用表主键id，数字，不允许为空
                                 @RequestParam(value="access_token",required=true) String access_token) {
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Long merchantId = user.getMerchantId();
        if (merchantId == null) {
            throw new BizException("E2900006", MessageUtil.getMessage("E2900006"));
        }
        //1.参数校验
        ValidUtil.valid("appId", appid, "required");//验证appid
        if (spreadmodel!=1&&spreadmodel!=2) {
            throw new ValidException("E2000061", MessageUtil.getMessage("E2000061",
                    new Object[]{"推广模式（spreadModel）", spreadmodel, "1|2"}));//{0}[{1}]超出了范围[{2}]!
        }

        Microshop microshop = microShopService.getByParams(merchantId,appid);
        if(microshop == null){
            throw new BizException("E2900007", MessageUtil.getMessage("E2900007"));
        }
        if(spreadmodel==2&&microshop.getRelateCustomerId()==null){
            throw new BizException("E2900009", MessageUtil.getMessage("E2900009"));
        }

        microShopService.updateSpreadModel(spreadmodel,microshop.getId());
        return this.successMsg();//返回成功信息
    }

    /** 应用管理--微托、聚来宝--模式生效接口
     * @param request 请求
     * @param forceattention 强制关注：-1代表非强制关注、1代表强制关注
     * @param appid 第三方id
     * @param access_token  访问令牌
     * @return 结果
     * @author 梁聪
     * @date 2017年7月17日 下午3:12:11
     */
    @ResponseBody
    @RequestMapping(value="/microshop/{appid}/attention/{forceattention}", method= RequestMethod.PUT)
    public Map updateForceAttention(HttpServletRequest request,
                                 @PathVariable Integer forceattention,//推广模式，不允许为空，数字，其中：1代表模式一、2代表模式二
                                 @PathVariable String appid,//应用表主键id，数字，不允许为空
                                 @RequestParam(value="access_token",required=true) String access_token) {
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Long merchantId = user.getMerchantId();
        if (merchantId == null) {
            throw new BizException("E2900006", MessageUtil.getMessage("E2900006"));
        }
        //1.参数校验
        ValidUtil.valid("appId", appid, "required");//验证appid
        if (forceattention!=-1&&forceattention!=1) {
            throw new ValidException("E2000061", MessageUtil.getMessage("E2000061",
                    new Object[]{"推广模式（forceAttention）", forceattention, "-1|1"}));//推广模式[forceAttention]不在范围[-1|1]内
        }

        Microshop microshop = microShopService.getByParams(merchantId,appid);
        if(microshop == null){
            throw new BizException("E2900007", MessageUtil.getMessage("E2900007"));
        }

        microShopService.updateForceAttention(forceattention,microshop.getId());
        return this.successMsg();//返回成功信息
    }
}
