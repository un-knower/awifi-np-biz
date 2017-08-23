package com.awifi.np.biz.toe.admin.usrmgr.blackuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.dao.BlackUserDao;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.model.BlackUser;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.service.BlackUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:51:50
 * 创建作者：周颖
 * 文件名称：BlackUserServiceImpl.java
 * 版本：  v1.0
 * 功能：黑名单实现类
 * 修改记录：
 */
@Service("blackUserService")
public class BlackUserServiceImpl implements BlackUserService {

    /**
     * 黑名单dao
     */
    @Resource(name = "blackUserDao")
    private BlackUserDao blackUserDao;
    
    /**
     * 黑名单列表
     * @param sessionUser 当前登陆账号
     * @param page 页面
     * @param merchantId 商户id
     * @param matchRule 匹配规则
     * @param keywords 关键字
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月13日 上午9:20:15
     */
    public void getListByParam(SessionUser sessionUser, Page<BlackUser> page, Long merchantId, Integer matchRule, String keywords) throws Exception{
        String cascadeLabel = null;
        Long[] merchantIds = null;
        if(PermissionUtil.isSuperAdmin(sessionUser)){//超管 可以看全部
            
        }else if(PermissionUtil.isMerchant(sessionUser)){//登陆用户 属性是商户
            Long curMerchantId = sessionUser.getMerchantId();//商户id
            if(curMerchantId == null){//为空 抛异常 当前组织id[{0}]对应的账号必须维护商户id[merchantId]!
                throw new BizException("E2000022", MessageUtil.getMessage("E2000022", curMerchantId));
            }
            cascadeLabel = sessionUser.getCascadeLabel();//商户层级
            if(StringUtils.isBlank(cascadeLabel)){//为空 抛异常 当前组织id[{0}]对应的账号必须维护商户层级[cascadeLabel]!
                throw new BizException("E2000039", MessageUtil.getMessage("E2000039", cascadeLabel));
            }
        }else if(PermissionUtil.isMerchants(sessionUser)){// 属性含多个商户
            String[] merchantIdsArray = sessionUser.getMerchantIds().split(",");
            merchantIds = CastUtil.toLongArray(merchantIdsArray);
        }else if(PermissionUtil.isProject(sessionUser) || PermissionUtil.isLocation(sessionUser)){//地区或项目管理员 强制选择一个商户
            if(merchantId == null){
                throw new BizException("E2000059", MessageUtil.getMessage("E2000059"));//请选择一个商户!
            }
        }else{//没有维护属性 可视为超管
            
        }
        int count = blackUserDao.getCountByParam(Constants.BLACK,merchantId,matchRule,keywords,cascadeLabel,merchantIds);//总数
        page.setTotalRecord(count);
        if(count <=0){//如果没有记录 直接返回
            return;
        }
        List<BlackUser> blackUserList = blackUserDao.getListByParam(Constants.BLACK,merchantId,matchRule,keywords,cascadeLabel,merchantIds,page.getBegin(),page.getPageSize());
        Map<Long,String> merchantNameMap = new HashMap<Long,String>();//缓存商户名称
        for(BlackUser blackUser : blackUserList){//补全信息
            Long merchantIdLong = blackUser.getMerchantId();
            String merchantName = merchantNameMap.get(merchantIdLong);
            if(StringUtils.isBlank(merchantName)){
                merchantName = MerchantClient.getNameByIdCache(merchantIdLong);
                merchantNameMap.put(merchantIdLong, merchantName);
            }
            blackUser.setMerchantName(merchantName);
        }
        page.setRecords(blackUserList);
    }
    
    /**
     * 判断手机号是否加黑名单
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月13日 上午11:20:35
     */
    public boolean isCellphoneExist(Long merchantId, String cellphone){
        int count = blackUserDao.getNumByCellphone(merchantId,cellphone,Constants.BLACK);
        return count > 0 ? true : false;
    }
    
    /**
     * 添加黑名单
     * @param blackUser 黑名单
     * @author 周颖  
     * @date 2017年2月13日 下午1:45:57
     */
    public void add(BlackUser blackUser){
        blackUserDao.add(blackUser);
    }
    
    /**
     * 删除黑名单 逻辑删除
     * @param id 黑名单主键id
     * @author 周颖  
     * @date 2017年2月13日 下午1:58:53
     */
    public void delete(Long id){
        blackUserDao.delete(id);
    }
    
    /**
     * 获取指定客户下所有的匹配规则
     * @param merchantId 商户id
     * @return 匹配规则
     * @author 许小满  
     * @date 2016年10月31日 下午12:25:34
     */
    public List<Integer> getMatchRulesByMerchantId(Long merchantId){
        return blackUserDao.getMatchRulesByMerchantId(merchantId);
    }
    
    /**
     * 判断是否黑名单 - 精准匹配
     * @param cellphone 手机号
     * @param merchantId 商户id
     * @return yes 是黑名单 、no 不是黑名单
     * @author ZhouYing  
     * @date 2016年6月14日 上午9:11:23
     */
    public String isBlackForRule1(String cellphone, Long merchantId) {
        int count = blackUserDao.isBlack(cellphone, merchantId, 1);
        return count > 0 ? "yes" : "no";
    }
    
    /**
     * 判断是否黑名单 - 模糊匹配
     * @param cellphone 手机号
     * @param merchantId 商户id
     * @return yes 是黑名单 、no 不是黑名单
     * @author 许小满  
     * @date 2016年10月31日 下午2:23:54
     */
    public String isBlackForRule2(String cellphone, Long merchantId) {
        List<String> phoneList = this.getCellphonesByMerchantId(merchantId, 2);
        int maxSize = phoneList != null ? phoneList.size() : 0;
        String phone = null;
        String pattern = null;
        for(int i=0; i<maxSize; i++){
            phone = phoneList.get(i);
            if(StringUtils.isBlank(phone)){
                continue;
            }
            pattern = "^" + phone + ".*";
            if(RegexUtil.match(cellphone, pattern)){
                return "yes";
            }
        }
        return "no";
    }
    
    /**
     * 获取指定客户下的所有手机号
     * @param merchantId 商户id
     * @param matchRule 匹配规则：1 精确、2 模糊
     * @return 手机号
     * @author 许小满  
     * @date 2016年10月31日 下午12:36:23
     */
    public List<String> getCellphonesByMerchantId(Long merchantId, Integer matchRule){
        return blackUserDao.getCellphonesByMerchantId(merchantId, matchRule);
    }
    
}