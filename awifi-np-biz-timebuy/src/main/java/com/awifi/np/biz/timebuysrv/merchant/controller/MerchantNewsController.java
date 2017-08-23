/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 下午4:35:07
 * 创建作者：尤小平
 * 文件名称：MerchantNewsController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.controller.BaseController;
//import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantNewsService;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.Param;
import com.cpj.swagger.annotation.DataType;

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

@APIs(description = "商户介绍")
@Controller
@RequestMapping(value = "/timebuysrv/merchant/news")
@SuppressWarnings("rawtypes")
public class MerchantNewsController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantNewsService
     */
    @Resource
    private MerchantNewsService merchantNewsService;

    /**
     * 添加单个MerchantNews信息.
     *
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午4:08:43
     */
    @API(summary = "添加单个商户介绍信息",
            parameters = {
                    @Param(name = "content", description = "消息", dataType = DataType.STRING, required = false),
                    @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = false),
            })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map add(HttpServletRequest request) throws Exception {
        logger.debug("添加MerchantNews,请求参数:" + JSON.toJSONString(request.getParameterMap()));

        MerchantNews merchantNews = new MerchantNews();
        String content = request.getParameter("content");
        String merid = request.getParameter("merid");

        if (content != null) {
            merchantNews.setContent(content);
        }
        if (merid != null) {
            merchantNews.setMerid(Long.valueOf(merid));
        }

        int result = merchantNewsService.addMerchantNews(merchantNews);
        logger.debug("添加MerchantNews结束, 添加条数：" + result);

        return this.successMsg(result);
    }

    /**
     * 根据商户id获取MerchantNews列表.
     *
     * @param merid 商户id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月6日 下午7:26:36
     */
    @API(summary = "根据商户id获取商户介绍信息列表",
            parameters = {
                    @Param(name = "merid", description = "商户id", in = "path", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/list/{merid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getListByMerid(@PathVariable String merid) throws Exception {
        logger.debug("获取MerchantNews列表,merid=" + merid);

        ValidUtil.valid("merid", merid, "numeric");

        List<MerchantNews> list = merchantNewsService.getListByMerid(Long.valueOf(merid));
        logger.debug("获取MerchantNews列表结束, 列表条数=" + list.size());

        return this.successMsg(list);
    }

    /**
     * MerchantNews列表接口，分页.
     *
     * @param request
     * @return
     * @author 尤小平
     * @date 2017年4月5日 下午4:09:32
     *//*
    @RequestMapping(value = "/list/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getListByParam(HttpServletRequest request) throws Exception {
        logger.debug("获取MerchantNews列表,请求参数:" + JSON.toJSONString(request.getParameterMap()));

        Integer id = CastUtil.toInteger(request.getParameter("id"));
        String content = request.getParameter("content");
        Long merid = CastUtil.toLong(request.getParameter("merid"));
        Integer pageSize = CastUtil.toInteger(request.getParameter("pageSize"));
        Integer pageNo = CastUtil.toInteger(request.getParameter("pageNo"));
        if (pageNo == null) {
            pageNo = 1;
        }

        ValidUtil.valid("curPage", pageNo, "required");
        ValidUtil.valid("curPage", pageNo, "numeric");
        ValidUtil.valid("pageSize", pageSize, "required");
        ValidUtil.valid("pageSize", pageSize, "numeric");

        Page<MerchantNews> page = new Page<MerchantNews>(pageNo, pageSize);

        MerchantNews merchantNews = new MerchantNews();
        merchantNews.setId(id);
        if (content != null) {
            content = "%" + content + "%";
        }
        merchantNews.setContent(content);
        merchantNews.setMerid(merid);

        merchantNewsService.getListByParam(merchantNews, page);
        logger.debug("获取MerchantNews列表结束, 列表条数=" + page.getRecords().size());

        return this.successMsg(page);
    }*/

    /**
     * 根据id查询单个MerchantNews信息.
     *
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午4:07:33
     */
    @API(summary = "根据id查询单个商户介绍信息",
            parameters = {
                    @Param(name = "id", description = "编号id", in = "path", dataType = DataType.INTEGER, required = true),
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map view(@PathVariable String id) throws Exception {
        logger.debug("根据id查询MerchantNews信息, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        MerchantNews merchantNews = merchantNewsService.getMerchantNewsById(Integer.valueOf(id));
        logger.debug("根据id查询MerchantNews信息结束, merchantNews=" + JSON.toJSONString(merchantNews));

        return this.successMsg(merchantNews);
    }

    /**
     * 根据id更新单个MerchantNews信息.
     *
     * @param id id
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午4:59:51
     */
    @API(summary = "根据id更新单个商户介绍信息",
            parameters = {
                    @Param(name = "id", description = "编号id", in = "path", dataType = DataType.INTEGER, required = true),
                    @Param(name = "content", description = "消息", dataType = DataType.STRING, required = false),
                    @Param(name = "merid", description = "商户id", dataType = DataType.LONG, required = false)
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Map update(@PathVariable(value = "id", required = true) String id, HttpServletRequest request) throws Exception {
        logger.debug("根据id更新MerchantNews信息, 请求参数:" + JSON.toJSONString(request.getParameterMap()) + ", id=" + id);

        String content = request.getParameter("content");
        Long merid = CastUtil.toLong(request.getParameter("merid"));

        ValidUtil.valid("id", id, "numeric");

        MerchantNews merchantNews = new MerchantNews();
        merchantNews.setContent(content);
        merchantNews.setMerid(merid);
        merchantNews.setId(Integer.valueOf(id));

        int result = merchantNewsService.updateMerchantNewsById(merchantNews);
        logger.debug("根据id更新MerchantNews信息结束, 更新条数:" + result);

        return this.successMsg(result);
    }

    /**
     * 根据id删除单个MerchantNews信息.
     *
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午5:00:35
     */
    @API(summary = "根据id删除单个商户介绍信息",
            parameters = {
                    @Param(name = "id", description = "编号id", in = "path", dataType = DataType.INTEGER, required = true)
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map delete(@PathVariable(value = "id", required = true) String id) throws Exception {
        logger.debug("根据id删除MerchantNews信息, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        int result = merchantNewsService.deleteMerchantNewsById(Integer.valueOf(id));
        logger.debug("根据id更新MerchantNews信息结束, 删除条数:" + result);

        return this.successMsg(result);
    }

    /**
     * for test only.
     *
     * @param merchantNewsService MerchantNewsService
     * @author 尤小平
     * @date 2017年4月6日 下午8:31:33
     */
    public void setMerchantNewsService(MerchantNewsService merchantNewsService) {
        this.merchantNewsService = merchantNewsService;
    }
}
