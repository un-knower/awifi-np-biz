/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午8:04:46
* 创建作者：尤小平
* 文件名称：DeviceUpgradeRegionServiceImpl.java
* 版本：  v1.0
* 功能：设备区域升级
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.util.UpgradeRegionClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.upgrade.service.DeviceUpgradeRegionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service(value = "deviceUpgradeRegionService")
public class DeviceUpgradeRegionServiceImpl implements DeviceUpgradeRegionService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * 状态-启用,关闭
     */
    private static final String STATE_START = "启用";
    private static final String STATE_CLOSE = "关闭";
    private static final String STATE_OTHERS = "未知状态";
    private static final String STATE_NULL = "空";

    /**
     * 升级类型-组件版本,固件版本
     */
    private static final String TYPE_CPVERSION = "组件版本";
    private static final String TYPE_FWVERSION = "固件版本";
    private static final String TYPE_OTHERS = "未知类型";
    private static final String TYPE_NULL = "空";
    
    /**
     * 定制终端-获取区域默认升级包列表.
     *
     * @param region DeviceUpgradeRegion
     * @param page   Page
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 下午3:57:45
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getListByParam(DeviceUpgradeRegion region, Page<DeviceUpgradeRegion> page) throws Exception {
        // 打印参数
        logger.debug("params=" + JsonUtil.toJson(region) + ", page=" + JsonUtil.toJson(page));

        // 获取分页列表
        List<DeviceUpgradeRegion> list = UpgradeRegionClient.queryListByParam(region, page.getPageNo(), page.getPageSize());

        // 遍历列表,获取页面需要显示的信息
        List<DeviceUpgradeRegion> regionList = new ArrayList<DeviceUpgradeRegion>();
        for (DeviceUpgradeRegion regionTemp : list) {
            DeviceUpgradeRegion newRegion = new DeviceUpgradeRegion();
            newRegion.setId(regionTemp.getId());// id
            newRegion.setVersions(regionTemp.getVersions());// 版本号
            newRegion.setHdVersions(regionTemp.getHdVersions());// HD版本号

            // 上传用户
            if (regionTemp.getUserName() != null) {
                newRegion.setUserName(regionTemp.getUserName());
            }

            // 状态
            if (regionTemp.getState() != null) {
                if (regionTemp.getState() == 1) {
                    newRegion.setStateName(STATE_START);
                } else if (regionTemp.getState() == 0) {
                    newRegion.setStateName(STATE_CLOSE);
                } else {
                    newRegion.setStateName(STATE_OTHERS);
                }
            } else {
                newRegion.setStateName(STATE_NULL);
            }

            // 升级类型
            if (regionTemp.getType() != null) {
                if (regionTemp.getType().equals(DeviceUpgradeRegion.TYPE_CPVERSION)) {
                    newRegion.setTypeName(TYPE_CPVERSION);
                } else if (regionTemp.getType().equals(DeviceUpgradeRegion.TYPE_FWVERSION)) {
                    newRegion.setTypeName(TYPE_FWVERSION);
                } else {
                    newRegion.setTypeName(TYPE_OTHERS);
                }
            } else {
                newRegion.setTypeName(TYPE_NULL);
            }

            // 启用时间
            if (regionTemp.getState() != null && regionTemp.getState() == 1 && regionTemp.getStartTime() != null) {
                newRegion.setStartTimeStr(DateUtil.formatDate(regionTemp.getStartTime().getTime()));
            }

            // 品牌型号
            String corModelName = "";
            // 品牌型号-厂商名称
            if (regionTemp.getCorporationName() != null) {
                corModelName = regionTemp.getCorporationName();
            }
            // 品牌型号-型号名称
            if (regionTemp.getModelName() != null) {
                if(StringUtil.isBlank(corModelName)) {
                    corModelName = regionTemp.getModelName();
                }else {
                    corModelName += "--" + regionTemp.getModelName();
                }
            }
            if(StringUtil.isBlank(corModelName)) {
                // 品牌型号-厂商名称
                if (regionTemp.getCorporationId() != null) {
                    Map<String, Object> corp = CorporationClient.queryCorpById(regionTemp.getCorporationId());
                    logger.debug("corporation :" + JsonUtil.toJson(corp));
                    corModelName = corp.get("corpName").toString();
                }
                // 品牌型号-型号名称
                if (regionTemp.getModelId() != null && !StringUtil.isBlank(corModelName)) {
                    Map<String, Object> model = CorporationClient.queryModelById(regionTemp.getModelId());
                    logger.debug("model :" + JsonUtil.toJson(model));
                    corModelName += "--" + model.get("modelName").toString();
                } else if(regionTemp.getModelId() != null){
                    Map<String, Object> model = CorporationClient.queryModelById(regionTemp.getModelId());
                    logger.debug("model :" + JsonUtil.toJson(model));
                    corModelName = model.get("modelName").toString();
                }
            }
            newRegion.setCorModelName(corModelName);

            // 升级区域=省名称+市名称+区名称
            String areaName = "";
            // 省名称
            if (regionTemp.getProvince() != null) {
                areaName += LocationClient.getByIdAndParam(regionTemp.getProvince(), "name");
            }
            // 市名称
            if (regionTemp.getCity() != null) {
                areaName += LocationClient.getByIdAndParam(regionTemp.getCity(), "name");
            }
            // 区名称
            if (regionTemp.getCounty() != null) {
                areaName += LocationClient.getByIdAndParam(regionTemp.getCounty(), "name");
            }
            newRegion.setAreaName(areaName);
            regionList.add(newRegion);
        }
        page.setRecords(regionList);

        // 获取总条数
        int count = UpgradeRegionClient.queryCountByParam(region);
        page.setTotalRecord(count);

        logger.debug("page=" + JsonUtil.toJson(page));
    }

    /**
     * 新增终端地区升级.
     *
     * @param region DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月12日 下午5:24:30
     */
    @Override
    public void add(DeviceUpgradeRegion region) throws Exception {
        logger.debug("region=" + JsonUtil.toJson(region));
        UpgradeRegionClient.add(region);
    }

    /**
     * 查看升级情况.
     *
     * @param id id
     * @return 升级情况
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月13日 下午3:03:03
     */
    @Override
    public DeviceUpgradeRegion getById(Long id) throws Exception {
        logger.debug("id=" + id);
        DeviceUpgradeRegion region = UpgradeRegionClient.queryById(id);

        if (region.getStartTime() != null) {
            region.setStartTimeStr(DateUtil.formatDate(region.getStartTime().getTime()));
        }
        String areaName = "";// 升级区域
        if (region.getProvince() != null) {
            areaName += LocationClient.getByIdAndParam(region.getProvince(), "name");// 省名称
        }
        if (region.getCity() != null) {
            areaName += LocationClient.getByIdAndParam(region.getCity(), "name");// 市名称
        }
        if (region.getCounty() != null) {
            areaName += LocationClient.getByIdAndParam(region.getCounty(), "name");// 区名称
        }
        region.setAreaName(areaName);

        if (region.getIssueNum() != null && region.getIssueNum() > 0 && region.getSuccessNum() != null
                && region.getSuccessNum() > 0) {
            double percent = (double) region.getSuccessNum() / (double) region.getIssueNum();
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            region.setSuccessRate(nt.format(percent));
        } else {
            region.setSuccessRate("0.00%");
        }

        logger.debug("region=" + JsonUtil.toJson(region));
        return region;
    }

    /**
     * 删除终端地区升级.
     *
     * @param id id
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月13日 下午3:02:52
     */
    @Override
    public void delete(Long id) throws Exception {
        logger.debug("id=" + id);
        DeviceUpgradeRegion region = UpgradeRegionClient.queryById(id);
        if (region == null) {
            throw new BizException("E2000056", MessageUtil.getMessage("E2000056", "默认升级包"));// 默认升级包不存在
        }

        // 删除升级包,同时删除FTP上的升级包文件
        UpgradeRegionClient.delete(id);
        String resourcesFolderPath = SysConfigUtil.getParamValue("region_resources_folder_path");// 定制终端升级包-文件夹路径
        IOUtil.remove(resourcesFolderPath + region.getPath());
    }

    /**
     * 判断是否已经有启用的升级包.
     * 组件升级包:相同地区和厂商型号下,是否已经有启用前两位版本号相同的其他组件升级包
     * 固件升级包:判断相同地区和厂商型号下,是否已经有启用的固件升级包
     *
     * @param id id
     * @return 是否存在升级包
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月13日 下午3:01:41
     */
    @Override
    public boolean existStartUpgrade(Long id) throws Exception {
        DeviceUpgradeRegion region = UpgradeRegionClient.queryById(id);
        valiableType(region);
        DeviceUpgradeRegion newRegion = checkDeviceUpgradeRegion(region);
        int count = UpgradeRegionClient.queryCountByParam(newRegion);

        return count > 0 ? true : false;
    }

    /**
     * 启用升级包.
     *
     * @param id id
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月13日 下午3:00:05
     */
    @Override
    public void start(Long id) throws Exception {
        DeviceUpgradeRegion region = UpgradeRegionClient.queryById(id);
        valiableType(region);

        String type = region.getType();
        switch (type) {
            case DeviceUpgradeRegion.TYPE_FWVERSION:
                // 启用固件升级包
                startFwUpgrade(region);
                return;
            case DeviceUpgradeRegion.TYPE_CPVERSION:
                // 启用组件升级包
                startCpUpgrade(region);
                return;
            default:
                return;
        }
    }

    /**
     * 启用组件升级包.
     * 
     * @param region 组件升级包
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午5:52:49
     */
    private void startCpUpgrade(DeviceUpgradeRegion region) throws Exception {
        DeviceUpgradeRegion newRegion = checkDeviceUpgradeRegion(region);
        int count = UpgradeRegionClient.queryCountByParam(newRegion);
        List<DeviceUpgradeRegion> list = UpgradeRegionClient.queryListByParam(newRegion, 1, count);
        logger.debug("list.size=" + list.size() + ", " + count);

        List<DeviceUpgradeRegion> needCloseList = new ArrayList<DeviceUpgradeRegion>();
        if (list != null && list.size() > 0) {
            // 组件升级包:相同地区和厂商型号下,是否已经有启用前两位版本号相同的其他组件升级包
            String regionVersion = region.getVersions();
            String[] regionVersionArr = regionVersion.split(DeviceUpgradeRegion.SPLIT);
            if (regionVersionArr.length > 1) {
                regionVersion = regionVersionArr[0] + "." + regionVersionArr[1];
            }

            for (DeviceUpgradeRegion tempRegion : list) {
                String tempRegionVersion = tempRegion.getVersions();
                String[] tempRegionVersionArr = tempRegionVersion.split(DeviceUpgradeRegion.SPLIT);

                if (tempRegionVersionArr.length > 1) {
                    tempRegionVersion = tempRegionVersionArr[0] + "." + tempRegionVersionArr[1];
                }

                if (regionVersion.equals(tempRegionVersion)) {
                    needCloseList.add(tempRegion);
                }
            }
        }

        logger.debug("存在需要关闭的组件升级包:" + needCloseList.size());
        if (needCloseList != null && needCloseList.size() > 0) {// 存在需要关闭的组件升级包
            for (DeviceUpgradeRegion needClose : needCloseList) {
                UpgradeRegionClient.update(needClose.close());// 关闭老的组件升级包
            }
        }
        UpgradeRegionClient.start(region.getId());
        logger.debug("启用组件升级包完成");
    }

    /**
     * 启用固件升级包.
     * 
     * @param region 固件升级包
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午5:53:16
     */
    private void startFwUpgrade(DeviceUpgradeRegion region) throws Exception {
        DeviceUpgradeRegion newRegion = checkDeviceUpgradeRegion(region);
        int count = UpgradeRegionClient.queryCountByParam(newRegion);
        List<DeviceUpgradeRegion> needCloseList = UpgradeRegionClient.queryListByParam(newRegion, 1, count);
        logger.debug("存在需要关闭的固件升级包:" + count + ", " + needCloseList.size());

        if (needCloseList != null && needCloseList.size() > 0) {// 存在需要关闭的固件升级包
            for (DeviceUpgradeRegion needClose : needCloseList) {
                UpgradeRegionClient.update(needClose.close());// 关闭老的固件升级包
            }
        }

        UpgradeRegionClient.start(region.getId());
        logger.debug("启用固件升级包完成");
    }

    /**
     * 校验升级包类型.
     * 
     * @param region 升级包
     * @author 尤小平  
     * @date 2017年7月13日 下午5:53:49
     */
    private void valiableType(DeviceUpgradeRegion region) {
        if (region == null) {
            throw new BizException("E2000056", MessageUtil.getMessage("E2000056", "升级包"));// 升级包不存在
        }
        if (StringUtils.isEmpty(region.getType())) {
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "升级类型")); // 升级类型不允许为空
        }
        if (!(region.getType().equals(DeviceUpgradeRegion.TYPE_FWVERSION)
                || region.getType().equals(DeviceUpgradeRegion.TYPE_CPVERSION))) {
            throw new BizException("E2000056", MessageUtil.getMessage("E2000056", "升级类型")); // 升级类型不存在
        }
    }

    /**
     * 升级包启用前的检查.
     * 
     * @param region 升级包
     * @return DeviceUpgradeRegion
     * @author 尤小平  
     * @date 2017年7月13日 下午5:54:19
     */
    private DeviceUpgradeRegion checkDeviceUpgradeRegion(DeviceUpgradeRegion region) {
        // 获取启用的升级包查询条件
        DeviceUpgradeRegion newRegion = new DeviceUpgradeRegion();
        newRegion.setType(region.getType());
        newRegion.setState(DeviceUpgradeRegion.START_STATUS);
        newRegion.setProvince(region.getProvince());
        newRegion.setCity(region.getCity());
        newRegion.setCounty(region.getCounty());
        newRegion.setCorporationId(region.getCorporationId());
        newRegion.setModelId(region.getModelId());

        return newRegion;
    }
}
