/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月10日 下午5:30:03
 * 创建作者：尤小平
 * 文件名称：UpgradeRegionServiceImpl.java
 * 版本：  v1.0
 * 功能：区域默认升级service
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service.UpgradeRegionService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "upgradeRegionService")
public class UpgradeRegionServiceImpl implements UpgradeRegionService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 查询终端地区升级列表 url
     */
    private String queryListByParam = "dbc_querylistupgraderegion_url";

    /**
     * 查询终端地区升级总数 url
     */
    private String queryCountByParam = "dbc_querycountupgraderegion_url";

    /**
     * 新增终端地区升级url
     */
    private String addUrl = "dbc_addupgraderegion_url";

    /**
     * 根据主键查询终端地区升级包url
     */
    private String queryByIdUrl = "dbc_querybyidupgraderegion_url";

    /**
     * 更新终端地区升级 url
     */
    private String updateUrl = "dbc_updateupgraderegion_url";

    /**
     * 删除终端地区升级 url
     */
    private String deleteUrl = "dbc_deleteupgraderegion_url";

    /**
     * 启用 url
     */
    private String startUrl = "dbc_startupgraderegion_url";

    /**
     * 根据条件查询列表.
     *
     * @param region   DeviceUpgradeRegion
     * @param begin    起始页
     * @param pageSize 每页条数
     * @return List<DeviceUpgradeRegion>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:25
     */
    @Override
    public List<DeviceUpgradeRegion> queryListByParam(DeviceUpgradeRegion region, int begin, int pageSize)
            throws Exception {
        // 返回的分页列表
        List<DeviceUpgradeRegion> resultList = new ArrayList<DeviceUpgradeRegion>();
        // 打印参数
        logger.debug("region=" + JsonUtil.toJson(region) + ", begin=" + begin + ", pageSize=" + pageSize);

        // 组装数据中心入参
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(region);
        jsonObject.put("pageNum", begin);
        jsonObject.put("pageSize", pageSize);
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(jsonObject), "utf-8");

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(queryListByParam);
        logger.debug("url=" + url + ", params=" + jsonObject.toJSONString());

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendGetRequest(url, paramString);
        logger.debug("dbcenter return :" + JsonUtil.toJson(resultMap));

        // 解析数据中心返回结果
        JSONArray result = (JSONArray) resultMap.get("rs");
        for (int i = 0; i < result.size(); i++) {
            JSONObject object = (JSONObject) result.get(i);
            DeviceUpgradeRegion regionTemp = JSONObject.toJavaObject(object, DeviceUpgradeRegion.class);
            resultList.add(regionTemp);
        }

        return resultList;
    }

    /**
     * 根据条件统计条数.
     *
     * @param region DeviceUpgradeRegion
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:32
     */
    @Override
    public int queryCountByParam(DeviceUpgradeRegion region) throws Exception {
        logger.debug("region=" + JsonUtil.toJson(region));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(queryCountByParam);
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(region), "utf-8");
        logger.debug("url=" + url + ", params=" + paramString);

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendGetRequest(url, paramString);
        logger.debug("dbcenter return :" + JsonUtil.toJson(resultMap));

        return CastUtil.toInteger(resultMap.get("rs"));
    }

    /**
     * 根据id查询.
     *
     * @param id 主键
     * @return DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:38
     */
    @Override
    public DeviceUpgradeRegion queryById(Long id) throws Exception {
        logger.debug("id=" + id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        String params = "params=" + URLEncoder.encode(JsonUtil.toJson(paramMap), "UTF-8");

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(queryByIdUrl);
        logger.debug("url=" + url + ", params=" + params);

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendGetRequest(url, params);
        logger.debug("dbcenter result :" + JsonUtil.toJson(resultMap));

        JSONObject jsonObject = (JSONObject) resultMap.get("rs");
        DeviceUpgradeRegion region = JSONObject.toJavaObject(jsonObject, DeviceUpgradeRegion.class);

        return region;
    }

    /**
     * 新增终端地区升级.
     *
     * @param region DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:43
     */
    @Override
    public void add(DeviceUpgradeRegion region) throws Exception {
        logger.debug("region=" + JsonUtil.toJson(region));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(addUrl);
        logger.debug("url=" + url);

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(region));
        logger.debug("dbcenter result :" + JsonUtil.toJson(resultMap));
    }

    /**
     * 修改终端地区升级.
     *
     * @param region DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:50
     */
    @Override
    public void update(DeviceUpgradeRegion region) throws Exception {
        logger.debug("region=" + JsonUtil.toJson(region));

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(updateUrl);
        logger.debug("url=" + url);

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(region));
        logger.debug("dbcenter result :" + JsonUtil.toJson(resultMap));
    }

    /**
     * 删除终端地区升级.
     *
     * @param id 主键
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:54
     */
    @Override
    public void delete(Long id) throws Exception {
        logger.debug("id=" + id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        String params = "params=" + URLEncoder.encode(JsonUtil.toJson(paramMap), "UTF-8");

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(deleteUrl);
        logger.debug("url=" + url + ", params=" + params);

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendDeleteRequest(url, params);
        logger.debug("dbcenter result :" + JsonUtil.toJson(resultMap));
    }

    /**
     * 启用.
     *
     * @param id 主键
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:58
     */
    @Override
    public void start(Long id) throws Exception {
        logger.debug("id=" + id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        // 获取请求地址
        String url = SysConfigUtil.getParamValue(startUrl);
        logger.debug("url=" + url + ", params=" + JsonUtil.toJson(paramMap));

        // 调用数据中心接口
        Map<String, Object> resultMap = CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramMap));
        logger.debug("dbcenter result :" + JsonUtil.toJson(resultMap));
    }
}
