/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月27日 下午8:46:54
* 创建作者：尤小平
* 文件名称：MerchantManagerController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.controller;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantManagerService;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
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

@APIs(description = "管理员维护")
@Controller
@RequestMapping(value = "/timebuysrv/mngmerchant")
public class MerchantManagerController extends BaseController {

    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantManagerService
     */
    @Resource
    private MerchantManagerService merchantManagerService;

    /**
     * 新增管理员.
     * 
     * @param request 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月28日 上午10:53:02
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "新增管理员", parameters = {
            @Param(name = "uid", description = "uid", dataType = DataType.STRING, required = false),
            @Param(name = "mid", description = "mid", dataType = DataType.STRING, required = false),
            @Param(name = "uname", description = "uname", dataType = DataType.STRING, required = false),
            @Param(name = "type", description = "type", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map add(HttpServletRequest request) throws Exception {
        logger.debug("新增管理员, request params: " + JsonUtil.toJson(request.getParameterMap()));

        String uid = request.getParameter("uid");
        String mid = request.getParameter("mid");
        String uname = request.getParameter("uname");
        String type = request.getParameter("type");

        MerchantManager merchantManager = new MerchantManager();
        merchantManager.setUname(uname);
        merchantManager.setUid(uid);
        merchantManager.setMid(mid);
        merchantManager.setType(type);

        boolean success = merchantManagerService.insert(merchantManager);
        logger.debug("新增是否成功: " + success);

        return this.successMsg(success);
    }

    /**
     * 修改管理员.
     *
     * @param id id
     * @param request 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月28日 上午10:54:16
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "修改管理员", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.LONG, required = true),
            @Param(name = "uid", description = "uid", dataType = DataType.STRING, required = false),
            @Param(name = "mid", description = "mid", dataType = DataType.STRING, required = false),
            @Param(name = "uname", description = "uname", dataType = DataType.STRING, required = false),
            @Param(name = "type", description = "type", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map update(@PathVariable(value = "id", required = true) String id, HttpServletRequest request)
            throws Exception {
        logger.debug("修改管理员, request params: " + JsonUtil.toJson(request.getParameterMap()) + ", id=" + id);

        ValidUtil.valid("id", id, "numeric");

        MerchantManager merchantManager = new MerchantManager();
        merchantManager.setId(Long.valueOf(id));
        merchantManager.setUname(request.getParameter("uname"));
        merchantManager.setUid(request.getParameter("uid"));
        merchantManager.setMid(request.getParameter("mid"));
        merchantManager.setType(request.getParameter("type"));

        boolean success = merchantManagerService.update(merchantManager);
        logger.debug("修改是否成功: " + success);

        return this.successMsg(success);
    }

    /**
     * 删除管理员.
     *
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月28日 上午10:54:28
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "删除管理员", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.LONG, required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map delete(@PathVariable(value = "id", required = true) String id) throws Exception {
        logger.debug("删除管理员, id=" + id);

        ValidUtil.valid("id", id, "numeric");

        boolean success = merchantManagerService.deleteById(Long.valueOf(id));
        logger.debug("删除是否成功: " + success);

        return this.successMsg(success);
    }

    /**
     * 根据id获取管理员信息.
     *
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月28日 上午10:55:14
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "根据id获取管理员信息", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.LONG, required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getMerchantManagerById(@PathVariable(value = "id") String id) throws Exception {

        logger.debug("根据id获取管理员信息, request id= " + id);
        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        MerchantManager merchantManager = merchantManagerService.queryById(Long.valueOf(id));
        logger.debug("merchantManager: " + JsonUtil.toJson(merchantManager));

        return this.successMsg(merchantManager);
    }

    /**
     * 获取管理员列表.
     *
     * @param request 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月28日 上午10:54:44
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "获取管理员列表", parameters = {
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = false),
            @Param(name = "uid", description = "uid", dataType = DataType.STRING, required = false),
            @Param(name = "mid", description = "mid", dataType = DataType.STRING, required = false),
            @Param(name = "uname", description = "uname", dataType = DataType.STRING, required = false),
            @Param(name = "type", description = "type", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getListByMerchantManager(HttpServletRequest request) throws Exception {
        logger.debug("获取管理员列表, request params: " + JsonUtil.toJson(request.getParameterMap()));

        Long id = CastUtil.toLong(request.getParameter("id"));
        String uid = request.getParameter("uid");
        String mid = request.getParameter("mid");
        String uname = request.getParameter("uname");
        String type = request.getParameter("type");

        MerchantManager merchantManager = new MerchantManager();
        if (id != null) {
            merchantManager.setId(id);
        }
        if (StringUtil.isNotBlank(uname)) {
            merchantManager.setUname(uname);
        }
        if (StringUtil.isNotBlank(uid)) {
            merchantManager.setUid(uid);
        }
        if (StringUtil.isNotBlank(mid)) {
            merchantManager.setMid(mid);
        }
        if (StringUtil.isNotBlank(type)) {
            merchantManager.setType(type);
        }

        List<MerchantManager> list = merchantManagerService.getListByMerchantManager(merchantManager);
        logger.debug("list: " + JsonUtil.toJson(list));

        return this.successMsg(list);
    }

    /**
     * 获取管理员分页列表.
     *
     * @param request 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月28日 上午10:54:52
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "获取管理员分页列表", parameters = {
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = false),
            @Param(name = "uid", description = "uid", dataType = DataType.STRING, required = false),
            @Param(name = "mid", description = "mid", dataType = DataType.STRING, required = false),
            @Param(name = "uname", description = "uname", dataType = DataType.STRING, required = false),
            @Param(name = "type", description = "type", dataType = DataType.STRING, required = false),
            @Param(name = "pageNo", description = "当前页", dataType = DataType.INTEGER, required = true),
            @Param(name = "pageSize", description = "每页显示条数", dataType = DataType.INTEGER, required = true) })
    @RequestMapping(value = "/list/page", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getListByParams(HttpServletRequest request) throws Exception {
        logger.debug("获取管理员分页列表, request params: " + JsonUtil.toJson(request.getParameterMap()));

        Long id = CastUtil.toLong(request.getParameter("id"));
        String uid = request.getParameter("uid");
        String mid = request.getParameter("mid");
        String uname = request.getParameter("uname");
        String type = request.getParameter("type");
        Integer pageNo = CastUtil.toInteger(request.getParameter("pageNo"));
        Integer pageSize = CastUtil.toInteger(request.getParameter("pageSize"));
        if (pageNo == null) {
            pageNo = 1;
        }

        ValidUtil.valid("pageNo", pageNo, "required");
        ValidUtil.valid("pageNo", pageNo, "numeric");
        ValidUtil.valid("pageSize", pageSize, "required");
        ValidUtil.valid("pageSize", pageSize, "numeric");

        Page<MerchantManager> page = new Page<MerchantManager>(pageNo, pageSize);

        MerchantManager merchantManager = new MerchantManager();
        merchantManager.setId(id);
        if (StringUtil.isNotBlank(uid)) {
            merchantManager.setUid(uid);
        }
        if (StringUtil.isNotBlank(mid)) {
            merchantManager.setMid(mid);
        }
        if (StringUtil.isNotBlank(uname)) {
            merchantManager.setUname(uname);
        }
        if (StringUtil.isNotBlank(type)) {
            merchantManager.setType(type);
        }

        Page<MerchantManager> list = merchantManagerService.getListByParams(merchantManager, page);
        logger.debug("list: " + JsonUtil.toJson(list));

        return this.successMsg(list);
    }

    /**
     * for testing only.
     * 
     * @param merchantManagerService MerchantManagerService
     * @author 尤小平  
     * @date 2017年5月2日 上午10:58:15
     */
    public void setMerchantManagerService(MerchantManagerService merchantManagerService) {
        this.merchantManagerService = merchantManagerService;
    }
}