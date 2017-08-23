/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月7日 上午11:18:23
 * 创建作者：尤小平
 * 文件名称：MerchantNoticeController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantNoticeService;
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
import java.util.List;
import java.util.Map;

@APIs(description = "商户滚动消息")
@Controller
@RequestMapping(value = "/timebuysrv/merchant/notice")
@SuppressWarnings("rawtypes")
public class MerchantNoticeController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantNoticeService
     */
    @Resource
    private MerchantNoticeService merchantNoticeService;

    /**
     * 根据商户id获取商户滚动消息列表.
     *
     * @param merid 商户id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:50:11
     */
    @API(summary = "根据商户id获取商户滚动消息列表", parameters = {
            @Param(name = "merid", description = "商户id", in = "path", dataType = DataType.LONG, required = true), })
    @RequestMapping(value = "/list/{merid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getListByMerid(@PathVariable String merid) throws Exception {
        logger.debug("获取MerchantNotice列表,merid=" + merid);

        ValidUtil.valid("merid", merid, "numeric");

        List<MerchantNotice> list = merchantNoticeService.getListByMerid(Long.valueOf(merid));
        logger.debug("获取MerchantNotice列表结束, 列表条数=" + list.size());

        return this.successMsg(list);
    }

    /**
     * 根据id查询单个商户滚动消息信息.
     *
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:50:28
     */
    @API(summary = "根据id查询单个商户滚动消息信息", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.INTEGER, required = true), })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map view(@PathVariable String id) throws Exception {
        logger.debug("根据id查询MerchantNotice信息, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        MerchantNotice merchantNotice = merchantNoticeService.getMerchantNoticeById(Integer.valueOf(id));
        logger.debug("根据id查询MerchantNotice信息结束, merchantNotice=" + JSON.toJSONString(merchantNotice));

        return this.successMsg(merchantNotice);
    }

    /**
     * 添加单个商户滚动消息信息.
     *
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:50:42
     */
    @API(summary = "添加单个商户滚动消息信息", parameters = {
            @Param(name = "slot", description = "槽位", dataType = DataType.INTEGER, required = false),
            @Param(name = "content", description = "消息", dataType = DataType.STRING, required = false),
            @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = false), })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map add(HttpServletRequest request) throws Exception {
        logger.debug("添加MerchantNotice,请求参数:" + JSON.toJSONString(request.getParameterMap()));

        MerchantNotice merchantNotice = new MerchantNotice();
        Integer slot = CastUtil.toInteger(request.getParameter("slot"));
        String content = request.getParameter("content");
        Long merid = CastUtil.toLong(request.getParameter("merid"));

        if (slot != null) {
            merchantNotice.setSlot(slot);
        }
        if (content != null) {
            merchantNotice.setContent(content);
        }
        if (merid != null) {
            merchantNotice.setMerid(merid);
        }

        int result = merchantNoticeService.addMerchantNotice(merchantNotice);
        logger.debug("添加MerchantNotice结束, 添加条数：" + result);

        return this.successMsg(result);
    }

    /**
     * 根据id更新单个商户滚动消息信息.
     *
     * @param id id
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:50:54
     */
    @API(summary = "根据id更新单个商户滚动消息信息", parameters = {
            @Param(name = "id", description = "编号id", in = "path", dataType = DataType.INTEGER, required = true),
            @Param(name = "slot", description = "槽位", dataType = DataType.INTEGER, required = false),
            @Param(name = "content", description = "消息", dataType = DataType.STRING, required = false),
            @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = false) })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Map update(@PathVariable(value = "id", required = true) String id, HttpServletRequest request)
            throws Exception {
        logger.debug("根据id更新MerchantNotice信息, 请求参数:" + JSON.toJSONString(request.getParameterMap()) + ", id=" + id);

        Integer slot = CastUtil.toInteger(request.getParameter("slot"));
        String content = request.getParameter("content");
        Long merid = CastUtil.toLong(request.getParameter("merid"));

        ValidUtil.valid("id", id, "numeric");

        MerchantNotice merchantNotice = new MerchantNotice();
        merchantNotice.setSlot(slot);
        merchantNotice.setContent(content);
        merchantNotice.setMerid(merid);
        merchantNotice.setId(Integer.valueOf(id));

        int result = merchantNoticeService.updateMerchantNoticeById(merchantNotice);
        logger.debug("根据id更新MerchantNotice信息结束, 更新条数:" + result);

        return this.successMsg(result);
    }

    /**
     * 根据id删除单个商户滚动消息信息.
     *
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:51:15
     */
    @API(summary = "根据id删除单个商户滚动消息信息", parameters = {
            @Param(name = "id", description = "编号id", in = "path", dataType = DataType.INTEGER, required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map delete(@PathVariable(value = "id", required = true) String id) throws Exception {
        logger.debug("根据id删除MerchantNotice, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        int result = merchantNoticeService.deleteMerchantNoticeById(Integer.valueOf(id));
        logger.debug("根据id删除MerchantNotice信息结束, 删除条数:" + result);

        return this.successMsg(result);
    }

    /**
     * for testing only.
     * 
     * @param merchantNoticeService MerchantNoticeService
     * @author 尤小平  
     * @date 2017年4月17日 下午8:33:01
     */
    public void setMerchantNoticeService(MerchantNoticeService merchantNoticeService) {
        this.merchantNoticeService = merchantNoticeService;
    }
}
