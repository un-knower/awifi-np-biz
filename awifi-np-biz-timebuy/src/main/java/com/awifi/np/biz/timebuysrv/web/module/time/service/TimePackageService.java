package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.TimePackageDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.util.MapUtils;

@Service
public class TimePackageService {
    /* 引入 MerchantServiceApi . */
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 套餐dao
     */
    @Autowired(required = true)
    private TimePackageDao packageDao;

    public TimePackageDao getPackageDao() {
        return packageDao;
    }

    public void setPackageDao(TimePackageDao packageDao) {
        this.packageDao = packageDao;
    }

    /**
     * 迭代4：TODO 根据条件查询套餐
     * 
     * @param map
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月19日 下午4:48:17
     */
    public List<TimePackage> queryListByParam(Map<String, Object> map) throws Exception {
        if (map == null) {
            throw new Exception("套餐查询参数为空");
        }
        int pageNum = 1;
        int pageSize = 10;
    
        if (map.get("pageNum") != null) {
            pageNum = Integer.valueOf(String.valueOf(map.get("pageNum")));
        }
        if (map.get("pageSize") != null) {
            pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
        }

        if (StringUtils.isBlank(MapUtils.getStringValue(map, "pageNum"))
                || StringUtils.isBlank(MapUtils.getStringValue(map, "pageSize"))) {
            pageNum = 1;
            pageSize = 10;
        }

       
        if (pageNum < 0 || pageSize < 0) {
            throw new Exception("根据分页条件查询套餐出错:分页条件出错");
        }
        map.put("beginIndex", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        return packageDao.queryListByParam(map);
    }

    /**
     * 根据参数查询数量
     * 
     * @param map
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月19日 下午4:44:32
     */
    public int queryCountByParam(Map<String, Object> map) throws Exception {
        if (map == null) {
            throw new Exception("套餐查询参数为空");
        }
        return packageDao.queryCountByParam(map);
    }

    /**
     * 根据id来查询数据
     * 
     * @param id
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月19日 下午4:45:00
     */
    public TimePackage queryById(Long id) throws Exception {

        if (id <= 0) {
            throw new Exception("根据主键查询套餐出错:主键参数错误");
        }
        return packageDao.queryById(id);
    }

    /**
     * 插入套餐礼包
     * 
     * @param merchantPackage
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月19日 下午4:45:17
     */
    public int add(TimePackage merchantPackage) throws Exception {
        if (merchantPackage == null) {
            throw new Exception("新增套餐出错:参数为空");
        }
        if (merchantPackage.getMerchantId() == null || merchantPackage.getPackageType() == null
                || merchantPackage.getMerchantId() == 0 || merchantPackage.getPackageType() <= 0
                || merchantPackage.getPackageKey() <= 0 || merchantPackage.getPackageValue() < 0
                /*
                 * || merchantPackage.getEffectDatetime() == null ||
                 * merchantPackage.getExpiredDatetime() == null
                 */
                || merchantPackage.getCreateDate() == null) {
            throw new Exception("新增套餐数据出错:缺少必要参数");

        }

        return packageDao.add(merchantPackage);
    }

    /**
     * 更新套餐
     * 
     * @param timePackage
     *            套餐跟新参数
     * @return 删除的个数
     * @throws Exception
     *             参数异常
     * @author 张智威
     * @date 2017年4月19日 下午4:45:31
     */
    public int update(TimePackage timePackage) throws Exception {
        if (timePackage == null || timePackage.getId() == null || timePackage.getMerchantId() <= 0) {
            throw new Exception("更新套餐出错:缺少必要参数");
        }

        return packageDao.update(timePackage);
    }

    /**
     * 逻辑删除套餐
     * 
     * @param map
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月19日 下午4:45:44
     */
    public int delete(Long id) throws Exception {
        if (id == null || id == 0) {
            throw new Exception("删除套餐出错:参数错误");
        }
        return packageDao.logicDelete(id);
    }

    /**
     * 根据商户id 套餐类型 查询套餐
     * 
     * @param merchantId
     * @param packageType
     * @return List<TimePackage>
     * @throws Exception
     */
    public List<TimePackage> queryListByParam(Long merchantId, Integer packageType) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (merchantId == null) {
            return null;
        }
        parm.put("merchantId", merchantId);
        parm.put("status", 1);
        parm.put("packageType", packageType);
        if (packageType == 2) {
            parm.put("packageKey", "201");
        }
        return queryListByParam(parm);
    }

    /**
     * 商户套餐查询
     * @param merchantId
     * @param packageType
     * @return List<TimePackage>
     * @throws Exception
     * @author 张智威  
     * @date 2017年6月8日 下午4:29:26
     */
    public List<TimePackage> packageListSearch(Long merchantId, Integer packageType) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (merchantId == null) {
            return null;
        }
        parm.put("merchantId", merchantId);
        parm.put("status", 1);
        parm.put("packageType", packageType);
        if (packageType == 2) {
            parm.put("packageKey", "201");//免费礼包
        }
        List<TimePackage> pkg = queryListByParam(parm);
        List<TimePackage> result = new ArrayList<TimePackage>();
        for (TimePackage ret : pkg) {
            if(ret.getEffectDatetime()!=null && ret.getExpiredDatetime()!=null){
                if (((new Date().getTime()) > (ret.getEffectDatetime().getTime()))
                        && ((ret.getExpiredDatetime().getTime()) > (new Date().getTime()))) {
                    result.add(ret);
                }
            }
        }

        if (result.size() == 0) {// 去查默认配置免费套餐赠送天数
            parm.put("merchantId", Constants.DEFAULT_FREE_PKG_KEY);
            parm.put("status", 1);
            parm.put("packageType", packageType);
            if (packageType == 2) {
                parm.put("packageKey", "201");
            }
            pkg = queryListByParam(parm);
            for (TimePackage ret : pkg) {
                result.add(ret);
            }
        }

        return result;
    }

    /**
     * 商户免费套餐查询
     * 
     * @param merchantId
     * @return String
     * @author 张智威
     * @throws Exception
     */
    public TimePackage queryFreePkgByMerId(Long merchantId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (merchantId == null) {
            return null;
        }
        parm.put("merchantId", merchantId);
        parm.put("status", 1);
        parm.put("packageType", 2);

        parm.put("packageKey", "201");
        List<TimePackage> pkgList = queryListByParam(parm);
        if (pkgList != null && pkgList.size() > 0) {
            if (pkgList.size() > 1) {
                logger.error("merchantid:" + merchantId + "has more than one freepackage");
            }
            return pkgList.get(0);
        } else {
            logger.debug("该商户未设置免费套餐礼包!");
            // throw new BizException("E2011188811","该商户未设置免费套餐礼包!");
        }

        return null;
    }

    /**
     * 逻辑删除套餐礼包
     * 
     * @param packageId
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月10日 下午2:30:21
     */
    public int logicDelete(Long packageId) throws Exception {
        TimePackage pkg = null;
        pkg = queryById(packageId);
        if (pkg == null) {
            throw new Exception("套餐不存在");
        }
        pkg.setStatus(9);
        pkg.setStatusDate(new Date());

        int i = update(pkg);

        if (i != 1) {
            logger.error("packageId:" + packageId + ":逻辑删除失败");
            throw new Exception("逻辑删除套餐失败");
        } else {
            return 1;
        }

    }
}
