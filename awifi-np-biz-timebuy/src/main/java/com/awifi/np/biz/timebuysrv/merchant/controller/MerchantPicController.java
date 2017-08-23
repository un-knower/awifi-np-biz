/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午3:03:31
* 创建作者：尤小平
* 文件名称：MerchantPicController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
/**
 * 
 */
package com.awifi.np.biz.timebuysrv.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantPicService;
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

@APIs(description = "商户滚动图片")
@Controller
@RequestMapping(value = "/timebuysrv/merchant/pic")
@SuppressWarnings("rawtypes")
public class MerchantPicController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantPicService
     */
    @Resource
    private MerchantPicService merchantPicService;

    /**
     * 根据商户id获取商户滚动图片列表.
     * 
     * @param merid 商户id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午3:46:10
     */
    @API(summary = "根据商户id获取商户滚动图片列表", parameters = {
            @Param(name = "merid", description = "商户id", in = "path", dataType = DataType.LONG, required = true), })
    @RequestMapping(value = "/list/{merid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getMerchantPicList(@PathVariable String merid) throws Exception {
        logger.debug("获取商户滚动图片列表, merid=" + merid);

        ValidUtil.valid("商户id", merid, "numeric");

        List<MerchantPic> list = merchantPicService.getListByMerid(Long.valueOf(merid));
        logger.debug("获取商户滚动图片列表结束，列表条数=" + list.size());

        return this.successMsg(list);
    }

    /**
     * 添加单个商户滚动图片信息.
     * 
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午3:47:48
     */
    @API(summary = "添加单个商户滚动图片信息", parameters = {
            @Param(name = "slot", description = "槽位", dataType = DataType.INTEGER, required = false),
            @Param(name = "path", description = "路径", dataType = DataType.STRING, required = false),
            @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = false), })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map add(HttpServletRequest request) throws Exception {
        logger.debug("添加商户滚动图片, 请求参数:" + JSON.toJSONString(request.getParameterMap()));

        MerchantPic merchantPic = new MerchantPic();
        Integer slot = CastUtil.toInteger(request.getParameter("slot"));
        String path = request.getParameter("path");
        Long merid = CastUtil.toLong(request.getParameter("merid"));

        if (slot != null) {
            merchantPic.setSlot(slot);
        }
        if (path != null) {
            merchantPic.setPath(path);
        }
        if (merid != null) {
            merchantPic.setMerid(merid);
        }

        int result = merchantPicService.addMerchantPic(merchantPic);
        logger.debug("添加商户滚动图片结束, 添加条数：" + result);

        return this.successMsg(result);
    }

    /**
     * 根据id更新单个商户滚动图片信息.
     * 
     * @param id id
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午3:52:02
     */
    @API(summary = "根据id更新单个商户滚动图片信息", parameters = {
            @Param(name = "id", description = "编号", in = "path", dataType = DataType.INTEGER, required = true),
            @Param(name = "slot", description = "槽位", dataType = DataType.INTEGER, required = false),
            @Param(name = "path", description = "路径", dataType = DataType.STRING, required = false),
            @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = false) })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Map update(@PathVariable String id, HttpServletRequest request) throws Exception {
        logger.debug("根据id更新商户滚动图片, 请求参数:" + JSON.toJSONString(request.getParameterMap()) + ", id=" + id);

        ValidUtil.valid("id", id, "numeric");

        MerchantPic merchantPic = new MerchantPic();
        Integer slot = CastUtil.toInteger(request.getParameter("slot"));
        String path = request.getParameter("path");
        Long merid = CastUtil.toLong(request.getParameter("merid"));
        merchantPic.setId(Integer.valueOf(id));
        merchantPic.setSlot(slot);
        merchantPic.setPath(path);
        merchantPic.setMerid(merid);

        int result = merchantPicService.updateMerchantPicById(merchantPic);
        logger.debug("根据id更新商户滚动图片结束, 更新条数:" + result);

        return this.successMsg(result);
    }

    /**
     * 根据id获取单个商户滚动图片信息.
     * 
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午3:48:38
     */
    @API(summary = "根据id查询单个商户滚动图片信息", parameters = {
            @Param(name = "id", description = "编号", in = "path", dataType = DataType.INTEGER, required = true), })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map view(@PathVariable String id) throws Exception {
        logger.debug("根据id查询商户滚动图片信息, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        MerchantPic merchantPic = merchantPicService.getMerchantPicById(Integer.valueOf(id));
        logger.debug("根据id查询商户滚动图片信息结束, merchantNotice=" + JSON.toJSONString(merchantPic));

        return this.successMsg(merchantPic);
    }

    /**
     * 根据id删除单个商户滚动图片信息.
     * 
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午3:53:08
     */
    @API(summary = "根据id删除单个商户滚动图片信息", parameters = {
            @Param(name = "id", description = "编号", in = "path", dataType = DataType.INTEGER, required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map delete(@PathVariable(value = "id", required = true) String id) throws Exception {
        logger.debug("根据id删除商户滚动图片, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        int result = merchantPicService.deleteMerchantPicById(Integer.valueOf(id));
        logger.debug("根据id删除商户滚动图片结束, 删除条数:" + result);

        return this.successMsg(result);
    }

    /**
     * for testing only.
     * 
     * @param merchantPicService MerchantPicService
     * @author 尤小平
     * @date 2017年4月10日 下午4:23:38
     */

    public void setMerchantPicService(MerchantPicService merchantPicService) {
        this.merchantPicService = merchantPicService;
    }
}
