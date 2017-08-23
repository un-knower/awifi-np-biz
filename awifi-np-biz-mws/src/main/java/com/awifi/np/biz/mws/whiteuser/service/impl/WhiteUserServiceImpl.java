/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午5:04:14
* 创建作者：王冬冬
* 文件名称：WhileUserServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceApiService;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.mws.whiteuser.dao.WhiteUserDao;
import com.awifi.np.biz.mws.whiteuser.model.StationMerchantNamelistSendlog;
import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;
import com.awifi.np.biz.mws.whiteuser.service.DeviceBusService;
import com.awifi.np.biz.mws.whiteuser.service.WhiteUserService;
import com.awifi.np.biz.mws.whiteuser.service.WhiteUserServiceSendlogService;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.StaticUserDao;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;

@Service("whiteUserService")
public class WhiteUserServiceImpl implements WhiteUserService{
    
    /**
     * 白名单dao
     */
    @Autowired
    private WhiteUserDao whiteUserDao;
    
    /**
     * 静态用户dao
     */
    @Resource(name="staticUserDao")
    private StaticUserDao staticUserDao;
    
    /**白名单下发服务*/
    @Resource(name = "whiteUserServiceSendlogService")
    private WhiteUserServiceSendlogService whiteUserServiceSendlogService;
    
    /**设备总线服务*/
    @Resource(name = "deviceBusClientService")
    private DeviceBusService deviceBusService;
    
    /**数据中心服务*/
    @Resource(name = "deviceApiService")
    private DeviceApiService deviceApiService;
    
    /**
     * 判断手机号是否加白名单
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 王冬冬  
     * @date 2017年2月13日 上午11:20:35
     */
    public boolean isCellphoneExist(Long merchantId, String cellphone) {
        int count = whiteUserDao.isMobileExist(merchantId,cellphone);
        return count > 0 ? true : false;
    }

    /**
     * 添加白名单
     * @param whiteUser 白名单
     * @author 王冬冬 
     * @throws Exception 异常
     * @date 2017年2月13日 下午1:45:57
     */
    public void add(WhiteUser whiteUser) throws Exception {
        List<Device> deviceList = queryDeviceByMerchantId(whiteUser.getMerchantId());//查询商户下的设备信息
        
        if (deviceList!= null&&!deviceList.isEmpty()) {//设备不为空
            if(StringUtils.isNotBlank(whiteUser.getMac())){
                List<String> macList=null;
                for(Device device:deviceList){//下发到设备总线
                    // 查询以前的设备的下发记录
                    List<StationMerchantNamelistSendlog> logList = whiteUserServiceSendlogService.findByDevId(device.getDeviceId());
                    replaceColon(logList);//兼容老的白名单，替换mac中的:
                    if(logList==null||logList.isEmpty()){
                        macList=new ArrayList<>();
                        macList.add(whiteUser.getMac());
                    }else{
                        macList=assMac(logList,whiteUser.getMac());
                    }
                    deviceBusService.sendWhiteToDevicebus(device.getDeviceId(), device.getDevMac(), macList, null, null, whiteUser.getMerchantId(), whiteUser.getCellPhone(), null);
                }
            }
        }
        whiteUserDao.add(whiteUser);
    }

    /**
     * 兼容老的白名单，替换mac中的:
     * @param logList 白名单下发日志列表
     * @author 王冬冬  
     * @date 2017年8月7日 上午8:49:02
     */
    private void replaceColon(List<StationMerchantNamelistSendlog> logList) {

        for(StationMerchantNamelistSendlog log:logList){
            log.setUserMac(log.getUserMac().replaceAll(":",""));
        }
    }
    
    /**
     * @param logList 白名单下发列表
     * @param mac 设备mac地址
     * @return list
     * @author 王冬冬  
     * @date 2017年4月26日 下午12:09:41
     */
    public List<String> assMac(List<StationMerchantNamelistSendlog> logList, String mac){
        List<String> macList=null;
        if(logList!=null&&logList.size()!=0){
            for(StationMerchantNamelistSendlog merchantNamelistSendlog:logList){
                if(StringUtils.isNoneBlank(merchantNamelistSendlog.getUserMac())){
                    macList=JSONArray.parseArray(merchantNamelistSendlog.getUserMac(), String.class);
                    if(merchantNamelistSendlog.getUserMac().indexOf(mac)==-1){
                        macList.add(mac);
                    }
                }
            }
        }else{
            macList=new ArrayList<>();
            macList.add(mac);
        }
        return macList; 
    }

    /**
     * 删除白名单
     * @param id 黑名单主键id
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年2月13日 下午1:58:53
     */
    public void delete(Integer id) throws Exception {
        
        Map<String,String> macMap=new HashMap<String,String>();
        Long[] idArr=new Long[1];
        idArr[0]=id.longValue();
        List<WhiteUser> whiteUsers=whiteUserDao.getListByIds(idArr);
        for(WhiteUser user:whiteUsers){
            macMap.put(user.getMac(), user.getMac());
        }
        if (whiteUsers == null || whiteUsers.size() == 0) {
            throw new BizException("E2500008", MessageUtil.getMessage("E2500008"));
        }
        WhiteUser whiteUser=whiteUsers.get(0);
        if(whiteUser==null||whiteUser.getMerchantId()==null){
            throw new BizException("E2500007", MessageUtil.getMessage("E2500007"));//没有商户id
        }
        Long merchantId=whiteUser.getMerchantId();
        List<Device> deviceList = queryDeviceByMerchantId(merchantId);//查询商户下的设备信息
        if (deviceList == null || deviceList.size() == 0) {
            whiteUserDao.delete(id);
            return;
        }
        for (Device device : deviceList) {// 下发到设备总线
            // 查询以前的设备的下发记录
            List<StationMerchantNamelistSendlog> logList = whiteUserServiceSendlogService.findByDevId(device.getDeviceId());
            if (logList == null || logList.size() == 0) {
                continue;
            }
            String usermac = logList.get(0).getUserMac();
            List<String> macDevList = JSONArray.parseArray(usermac, String.class);
            if (macDevList == null || macDevList.size() == 0) {
                continue;
            }
            Iterator<String> it = macDevList.iterator();
            while (it.hasNext()) {
                String thisMac = (String) it.next();
                if (macMap.get(thisMac) != null) {
                    it.remove();
                    continue;
                }
            }
            // 下发白名单
            deviceBusService.sendWhiteToDevicebus(device.getDeviceId(), device.getDevMac(), macDevList, null, null,merchantId, whiteUser.getCellPhone(), null);
        }
        whiteUserDao.delete(id);
    }

    /**
     * 根据merchantId查询商户设备
     * @param merchantId 商户id
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月26日 上午10:02:35
     */
    public List<Device> queryDeviceByMerchantId(Long merchantId) throws Exception {
        JSONObject ob = new JSONObject();
        ob.put("merchantId", merchantId);
        ob.put("merchantQueryType", "nextAllWithThis");
        ob.put("status", 1);
      
        //开放平台接口调用方式
        List<Device> devices = deviceApiService.getListByParam(ob.toJSONString());
        if (devices!=null&&!devices.isEmpty()) {
            return devices;
        }
        return null;
    }

    /**
     * 批量删除用户
     * @param ids 用户ids
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年2月10日 上午9:26:20
     */
    public void batchDelete(String ids) throws Exception {
        
        Map<String,String> macMap=new HashMap<String,String>();
        String[] idStrings=ids.split(",");
        Long[] idArr=new Long[idStrings.length];
        for(int i=0;i<idStrings.length;i++){
            idArr[i]=Long.parseLong(idStrings[i]);
        }
        List<WhiteUser> whiteUsers=whiteUserDao.getListByIds(idArr);
        for(WhiteUser user:whiteUsers){
            macMap.put(user.getMac(), user.getMac());
        }
        if (whiteUsers == null || whiteUsers.size() == 0) {
            throw new BizException("E2500008", MessageUtil.getMessage("E2500008"));
        }
        for(WhiteUser whiteUser:whiteUsers){
            Long merchantId=whiteUser.getMerchantId();
            if(merchantId==null){
                continue;
            }
            List<Device> deviceList = queryDeviceByMerchantId(merchantId);//查询商户下的设备信息
            if (deviceList == null || deviceList.size() == 0) {
//                whiteUserDao.delete(whiteUser.getId());//下面没有设备直接删除
                continue;
            }
            for(Device device:deviceList){//下发到设备总线
                // 查询以前的设备的下发记录
                List<StationMerchantNamelistSendlog> logList = whiteUserServiceSendlogService.findByDevId(device.getDeviceId());
                if (logList == null || logList.size() == 0) {
                    continue;
                }
                String usermac = logList.get(0).getUserMac();
                List<String> macDevList = JSONArray.parseArray(usermac, String.class);
                if (macDevList == null||macDevList.size() == 0) {
                    continue;
                }
                Iterator<String> it = macDevList.iterator();
                while (it.hasNext()) {
                    String thisMac = (String) it.next();
                    if (StringUtils.isNotBlank(macMap.get(thisMac))) {
                        it.remove();
                        continue;
                    }
                }
                // 下发白名单
                deviceBusService.sendWhiteToDevicebus(device.getDeviceId(), device.getDevMac(), macDevList, null, null,merchantId, whiteUser.getCellPhone(), null);
            }
        }
        whiteUserDao.batchDelete(idArr);
    }

    /**
     * 白名单列表
     * @param sessionUser 用户session
	 * @param page 页码
	 * @param merchantId 商户id
	 * @param keywords 关键词
	 * @author 王冬冬  
     * @throws Exception 异常
	 * @date 2017年4月21日 下午3:47:26
	 */
    public void getListByParam(SessionUser sessionUser,Page<WhiteUser> page, Long merchantId, String keywords) throws Exception {
        String cascadeLabel = null;
        Long[] merchantIds = null;
        if(PermissionUtil.isSuperAdmin(sessionUser)){//超管 可以看全部
            queryByMerchantAndKeyword(page, merchantId, keywords);
        }else if(PermissionUtil.isMerchant(sessionUser)){//登陆用户 属性是商户
            Long curMerchantId = sessionUser.getMerchantId();//商户id
            if(curMerchantId == null){//为空 抛异常 当前组织id[{0}]对应的账号必须维护商户id[merchantId]!
                throw new BizException("E2000022", MessageUtil.getMessage("E2000022", curMerchantId));
            }
            cascadeLabel = sessionUser.getCascadeLabel();//商户层级
            if(StringUtils.isBlank(cascadeLabel)){//为空 抛异常 当前组织id[{0}]对应的账号必须维护商户层级[cascadeLabel]!
                throw new BizException("E2000039", MessageUtil.getMessage("E2000039", cascadeLabel));
            }
            queryByMerchantAndKeyword(page, curMerchantId, keywords);
        }else if(PermissionUtil.isMerchants(sessionUser)){// 属性含多个商户
            String[] merchantIdsArray = sessionUser.getMerchantIds().split(",");
            merchantIds = CastUtil.toLongArray(merchantIdsArray);
            queryByMerchantAndKeyword(page, merchantIds, keywords);

        }else if(PermissionUtil.isProject(sessionUser) || PermissionUtil.isLocation(sessionUser)){//地区或项目管理员 强制选择一个商户
            if(merchantId == null){
                throw new BizException("E2000059", MessageUtil.getMessage("E2000059"));//请选择一个商户!
            }
        }else{//没有维护属性 可视为超管
            queryByMerchantAndKeyword(page, merchantId, keywords);

        }
        
    }

    /**
     * 根据商户id和keywords查询白名单数据
     * @param page 分页对象
     * @param merchantIds 商户id
     * @param keywords 关键词
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月22日 下午2:24:28
     */
    private void queryByMerchantAndKeyword(Page<WhiteUser> page, Long[] merchantIds, String keywords) throws Exception {
        int totals=whiteUserDao.getCountByParams(merchantIds,keywords);
        page.setTotalRecord(totals);
        if(totals <=0){//如果没有记录 直接返回
            return;
        }
        List<WhiteUser> whiteUsers=whiteUserDao.getListByParams(merchantIds,keywords,page.getBegin(),page.getPageSize());
//        whiteUsers=conbindSamePhoneWhiteUser(whiteUsers);
        
        StringBuilder sb=new StringBuilder();
        sb.append("(");
        for(WhiteUser user:whiteUsers){
            if(StringUtils.isNotBlank(user.getCellPhone())){
                sb.append(user.getCellPhone()+",");
            }
        }
        String cellphone=sb.toString();
        if("(".equals(cellphone)){
            cellphone=null;//没有手机号
        }else{
            cellphone=cellphone.substring(0, cellphone.lastIndexOf(","))+")";//替换最后一个,号
        }
        List<StaticUser> staticUsers=staticUserDao.getListByMerchantIdsAndPhone(merchantIds,cellphone);
        
        for(WhiteUser whiteUser : whiteUsers){
            whiteUser.setCreateDate(whiteUser.getCreateDate()==null?null:whiteUser.getCreateDate());
            Long merchantIdOfWhite = whiteUser.getMerchantId();
            Merchant merchant =MerchantClient.getByIdCache(merchantIdOfWhite);
            whiteUser.setMerchantName(merchant.getMerchantName());
            
            String cellphoneOfWhite = whiteUser.getCellPhone();
            if(merchantIdOfWhite == null || StringUtils.isBlank(cellphoneOfWhite)){
                continue;
            }
            for(StaticUser staticUser : staticUsers){
                Long merchantIdOfStatic = staticUser.getMerchantId();
                String cellphoneOfStatic = staticUser.getCellphone();
                if(merchantIdOfStatic == null || StringUtils.isBlank(cellphoneOfStatic)){
                    continue;
                }
                if((merchantIdOfWhite == merchantIdOfStatic) && (cellphoneOfWhite.equals(cellphoneOfStatic))){
                    whiteUser.setUserName(staticUser.getUserName());//用户名
                    whiteUser.setRealName(staticUser.getRealName());//真实姓名
                    whiteUser.setUserType(staticUser.getUserType());//用户类型：1代表普通员工、2代表VIP客户、3代表终端体验区
//                    whiteUser.setMerchantName(staticUser.getMerchantName());
                }
            }
        }
        page.setRecords(whiteUsers);
    }

    /**
     * 根据商户id和keywords查询白名单数据
     * @param page 分页对象
     * @param merchantId 商户id
     * @param keywords 关键词
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月22日 下午2:24:28
     */
    private void queryByMerchantAndKeyword(Page<WhiteUser> page, Long merchantId, String keywords)
            throws Exception {
        int totals=whiteUserDao.getCountByParam(merchantId,keywords);
        page.setTotalRecord(totals);
        if(totals <=0){//如果没有记录 直接返回
            return;
        }
        List<WhiteUser> whiteUsers=whiteUserDao.getListByParam(merchantId,keywords,page.getBegin(),page.getPageSize());
//        whiteUsers=conbindSamePhoneWhiteUser(whiteUsers);
        
        StringBuilder sb=new StringBuilder();
        sb.append("(");
        for(WhiteUser user:whiteUsers){
            if(StringUtils.isNotBlank(user.getCellPhone())){
                sb.append(user.getCellPhone()+",");
            }
        }
        String cellphone=sb.toString();
        if("(".equals(cellphone)){
            cellphone=null;//没有手机号
        }else{
            cellphone=cellphone.substring(0, cellphone.lastIndexOf(","))+")";//替换最后一个,号
        }
        List<StaticUser> staticUsers=staticUserDao.getListByMerchantIdAndCellPhone(merchantId,cellphone);
        
        for(WhiteUser whiteUser : whiteUsers){
            whiteUser.setCreateDate(whiteUser.getCreateDate()==null?null:whiteUser.getCreateDate());
            Long merchantIdOfWhite = whiteUser.getMerchantId();
            Merchant merchant =MerchantClient.getByIdCache(merchantIdOfWhite);
            whiteUser.setMerchantName(merchant.getMerchantName());
            
            String cellphoneOfWhite = whiteUser.getCellPhone();
            if(merchantIdOfWhite == null || StringUtils.isBlank(cellphoneOfWhite)){
                continue;
            }
            for(StaticUser staticUser : staticUsers){
                Long merchantIdOfStatic = staticUser.getMerchantId();
                String cellphoneOfStatic = staticUser.getCellphone();
                if(merchantIdOfStatic == null || StringUtils.isBlank(cellphoneOfStatic)){
                    continue;
                }
                if((merchantIdOfWhite == merchantIdOfStatic) && (cellphoneOfWhite.equals(cellphoneOfStatic))){
                    whiteUser.setUserName(staticUser.getUserName());//用户名
                    whiteUser.setRealName(staticUser.getRealName());//真实姓名
                    whiteUser.setUserType(staticUser.getUserType());//用户类型：1代表普通员工、2代表VIP客户、3代表终端体验区
//                    whiteUser.setMerchantName(staticUser.getMerchantName());
                }
            }
        }
        page.setRecords(whiteUsers);
    }

//    /**
//     * 根据手机号分组
//     * @param whiteUsers
//     * @return
//     * @author 王冬冬  
//     * @date 2017年6月28日 下午2:53:01
//     */
//    private List<WhiteUser> conbindSamePhoneWhiteUser(List<WhiteUser> whiteUsers) {
//        Map<String, List<WhiteUser>> map=new HashMap<String,List<WhiteUser>>();
//        List<WhiteUser> temp=new ArrayList<>();
//        List<WhiteUser> result=new ArrayList<>();
//        for(WhiteUser user:temp){
//            temp.clear();
//            if(StringUtils.isBlank(user.getCellPhone())){
//                result.add(user);
//            }else{
//                
//                if(map.containsKey(user.getCellPhone())){
//                    List<WhiteUser> users=map.get(user.getCellPhone());
//                    if(users!=null){
//                        users.add(user);
//                        map.put(user.getCellPhone(), users);
//                    }
//                }
//            }
//        }        
//        for(String key:map.keySet()){
//            
//            List<WhiteUser> wUsers=map.get(key);
//            StringBuilder sb=new StringBuilder();
//            for(int i=0;i<wUsers.size()-1;i++){
//                
//                sb.append(wUsers.get(i).getMac()+",");
//            }
//            sb.append(wUsers.get(wUsers.size()-1).getMac());
//            
//            WhiteUser user=wUsers.get(0);
//            user.setMac(sb.toString());
//            result.add(user);
//        } 
//        
//        
//        return result;
//    }

    /**
     * 判断mac地址是否存在
     * @param merchantId 商户id
     * @param mac mac地址
     * @return boolean
     * @author 王冬冬  
     * @date 2017年4月25日 上午9:53:48
     */
    public boolean isMacExist(Long merchantId, String mac) {
        int count = whiteUserDao.isMacExist(merchantId,mac);
        return count > 0 ? true : false;
    }

    @Override
    public void add(List<WhiteUser> whiteUserList,Long merchantId) throws Exception {
        List<Device> deviceList = queryDeviceByMerchantId(merchantId);//查询商户下的设备信息
        List<String> macList=null;
        if (deviceList!= null&&!deviceList.isEmpty()) {//设备不为空
            for(Device device:deviceList){//下发到设备总线
                // 查询以前的设备的下发记录
                List<StationMerchantNamelistSendlog> logList = whiteUserServiceSendlogService.findByDevId(device.getDeviceId());
                if(logList==null||logList.isEmpty()){
                    macList=addMacList(macList,whiteUserList);
                }else{
                    macList=assMac(logList,whiteUserList);
                }
                deviceBusService.sendWhiteToDevicebus(device.getDeviceId(), device.getDevMac(), macList, null, null,whiteUserList, null);
            }
        }
        for(WhiteUser whiteUser:whiteUserList){
            whiteUserDao.add(whiteUser);
        }
    }

    /**
     * 生成macList
     * @param logList 下发日志列表
     * @param whiteUserList 白名单列表
     * @return mac列表
     * @author 王冬冬  
     * @date 2017年6月14日 下午2:24:43
     */
    private List<String> assMac(List<StationMerchantNamelistSendlog> logList, List<WhiteUser> whiteUserList) {
        Map<String,String> macMap=new HashMap<String,String>();
        for(WhiteUser whiteUser:whiteUserList){
            String mac=whiteUser.getMac();
            if(StringUtils.isNoneBlank(mac)){
                macMap.put(mac,mac);
            }
        }
        List<String> macList=null;
        for(StationMerchantNamelistSendlog merchantNamelistSendlog:logList){
            if(StringUtils.isNoneBlank(merchantNamelistSendlog.getUserMac())){
                macList=JSONArray.parseArray(merchantNamelistSendlog.getUserMac(), String.class);
                for(Map.Entry<String, String> entity:macMap.entrySet()){
                    if(merchantNamelistSendlog.getUserMac().indexOf(entity.getKey())==-1){
                        macList.add(entity.getKey());
                    }
                }
            }
        }
        return macList;
    }

    /**
     * 返回macList
     * @param macList mac列表
     * @param whiteUserList 白名单列表
     * @return 新的mac列表
     * @author 王冬冬  
     * @date 2017年6月14日 下午1:48:57
     */
    private List<String> addMacList(List<String> macList, List<WhiteUser> whiteUserList) {
        macList=new ArrayList<String>();
        for(WhiteUser whiteUser:whiteUserList){
            if(StringUtils.isNoneBlank(whiteUser.getMac())){
                macList.add(whiteUser.getMac());
            }
        }
        return macList;
    }
}
