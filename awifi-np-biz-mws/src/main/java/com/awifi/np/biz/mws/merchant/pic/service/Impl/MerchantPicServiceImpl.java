/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:36:34
* 创建作者：余红伟
* 文件名称：MerchantPicServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.pic.service.Impl;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.mws.merchant.pic.dao.MerchantPicDao;
import com.awifi.np.biz.mws.merchant.pic.model.MerchantPic;
import com.awifi.np.biz.mws.merchant.pic.service.MerchantPicService;

@Service
public class MerchantPicServiceImpl implements MerchantPicService {
    Logger logger =LoggerFactory.getLogger(MerchantPicServiceImpl.class);
    @Resource
    private MerchantPicDao merchantPicDao;

    /**
     * 获取商户滚动图片接口
     * 
     * @author 余红伟
     * @date 2017年6月8日 上午10:37:37
     */
    @Override
    public List<MerchantPic> getMerCarouselByMerId(Long merchantId) {
        // TODO Auto-generated method stub
        return merchantPicDao.getMerCarouselByMerId(merchantId);
    }

    /**
     * 通过微站接口找数据中心center_pub_merchant_picture获取商户滚动图片信息接口
     * 
     * @author 余红伟
     * @throws UnsupportedEncodingException 
     * @date 2017年6月8日 上午10:37:37
     */
    @Override
    public List<MerchantPic> getMerCarouselByMerIdThroughMws(Long merchantId) throws Exception {
        // 通过微站接口读取对应的数据 因为微站的读取的图片是数据中心的 这就很麻烦 新阶段 数据中心不再提供相应的新的图片接口
        // 意味着老的数据只有dubbo接口可以调用 只有微站的系统能拿到
        // 所以只能通过微站的接口去读取数据
        String url = SysConfigUtil.getParamValue("mws_domain") + "/msv2/mspc/portal/images/list?merchantId="
                + merchantId + "&branchId=" + merchantId;
        ByteBuffer bytes = HttpRequest.sendGetRequest(url, "");
        String jsonStr = new String(bytes.array(), "UTF-8");
        HashMap<String, Object> map = JSON.parseObject(jsonStr, HashMap.class);
        String result = MapUtils.getString(map, "result");

        List<MerchantPic> list = new ArrayList<MerchantPic>();
        // ==============查询出错  指定给定3个默认图片
        if (!result.equals("1")) {
            logger.error(url+"result:"+jsonStr);
            //throw new BizException("E617155630",MapUtils.getString(map, "message"));
            return list;
        }
        // ===========说明有结果=======
        JSONObject jsonObject = (JSONObject) map.get("toplist");
        String domain = "";// http://beta-i.51awifi.com/media_mws/upload/
        // ========== 滚动图片可能有4张以上
        for(int i =1;i<5;i++){
            if(jsonObject.get("pic_"+i)!=null){
                String pic = domain + jsonObject.get("pic_"+i);
                Long pic_id = getId(jsonObject, "pic_"+i+"_id", 0l);
                //滚动图片类型为1
                list.add(new MerchantPic(pic_id, i, null, 1, pic, pic, null));
            }
        }
//        if(jsonObject.get("pic_1")!=null){
//            String pic_1 = domain + jsonObject.get("pic_1");
//            Long pic_1_id = getId(jsonObject, "pic_1_id", 0l);//
//            list.add(new MerchantPic(Long.valueOf(pic_1_id), pic_1, pic_1));
//        }
//        if(jsonObject.get("pic_2")!=null){
//            String pic_2 = domain + jsonObject.get("pic_2");
//            Long pic_2_id = getId(jsonObject, "pic_2_id", 0l);
//            list.add(new MerchantPic(Long.valueOf(pic_2_id), pic_2, pic_2));
//        }
//     
//        if(jsonObject.get("pic_3")!=null){
//            String pic_3 = domain + jsonObject.get("pic_3");
//            Long pic_3_id = getId(jsonObject, "pic_3_id", 0l);
//            list.add(new MerchantPic(Long.valueOf(pic_3_id), pic_3, pic_3));
//        }
//        if(jsonObject.get("pic_4")!=null){
//            String pic_4 = domain + jsonObject.get("pic_4");
//            Long pic_4_id = getId(jsonObject, "pic_4_id", 0l);
//            list.add(new MerchantPic(Long.valueOf(pic_4_id), pic_4, pic_4));
//        }
//      
        return list;
       
     
      
    }
    
    public Long getId(JSONObject object ,String name,long defaultValue){
        
        return object.get(name)==null?0l:object.getLongValue(name);
    }
    /**
     * 调用微站接口获取图片墙
     * @author 余红伟 
     * @throws Exception 
     * @date 2017年6月19日 下午5:21:30
     */
    public List<MerchantPic> getMerWallAlbumByMerIdThroughMws(Long merchantId) throws Exception {
        String domain = SysConfigUtil.getParamValue("mws_wall_domain");
        String url = SysConfigUtil.getParamValue("mws_domain")+"/msv2/mspc/portal/images/list?merchantId="+merchantId+"&branchId="+merchantId;
        ByteBuffer bytes= HttpRequest.sendGetRequest(url, "");
        String jsonStr =new String(bytes.array(), "UTF-8");
        

       HashMap<String,Object> map  =  JSON.parseObject(jsonStr, HashMap.class);//JsonUtil.fromJson(jsonStr, HashMap.class);
       List photoList =(List)map.get("photolist");
       List<MerchantPic> list =new ArrayList<MerchantPic>();
       for(int i=0;i<photoList.size();i++){
           JSONObject record =(JSONObject) photoList.get(i);
           Long id = Long.valueOf(""+record.get("id"));
           String path= ""+record.get("picPath");
           list.add(new MerchantPic(id,domain+path,domain+path));
       }
      
        return list;
    }
    
    /**
     * 查询结果为空，或者查询错误时，返回默认的滚动图片列表
     * @return
     * @author 余红伟 
     * @date 2017年6月19日 上午10:37:08
     */
    public List<MerchantPic> getMerCarouselByDefault(){
        List<MerchantPic> list = new ArrayList<>();
        //构造参数  id orderNum picTitle picType picPath picUrl picLink
        list.add(new MerchantPic(0l,1,null,1,SysConfigUtil.getParamValue("resources_domain")+"/media/picture/default/topfocus/1.png",SysConfigUtil.getParamValue("resources_domain")+"media/picture/default/topfocus/1.png",null));
        list.add(new MerchantPic(0l,2,null,1,SysConfigUtil.getParamValue("resources_domain")+"/media/picture/default/topfocus/2.png",SysConfigUtil.getParamValue("resources_domain")+"media/picture/default/topfocus/2.png",null));
        list.add(new MerchantPic(0l,3,null,1,SysConfigUtil.getParamValue("resources_domain")+"/media/picture/default/topfocus/3.png",SysConfigUtil.getParamValue("resources_domain")+"media/picture/default/topfocus/3.png",null));
        return list;
    }
    /**
     * 获取商户照片墙图片接口
     * 
     * @author 余红伟
     * @date 2017年6月8日 上午10:37:42
     */
    @Override
    public List<MerchantPic> getMerWallAlbumByMerId(Long merchantId) {
        // TODO Auto-generated method stub
        return merchantPicDao.getMerWallAlbumByMerId(merchantId);
    }

    @Override
    public int updateMerchantPic(MerchantPic merchantPic) {
        // TODO Auto-generated method stub
        return merchantPicDao.updateMerchantPic(merchantPic);
    }

    /**
     * 添加图片
     * 
     * @author 余红伟
     * @date 2017年6月14日 下午3:34:46
     */
    @Override
    public int addMerchantPic(MerchantPic merchantPic) {
        // TODO Auto-generated method stub
        return merchantPicDao.addMerchantPic(merchantPic);
    }

    /**
     * 根据商户Id和图片类型批量删除图片
     * 
     * @author 余红伟
     * @date 2017年6月14日 下午3:57:16
     */
    @Override
    public int deleteByMeridAndPicType(Long merid, Integer picType) {
        // TODO Auto-generated method stub
        return merchantPicDao.deleteByMeridAndPicType(merid, picType);
    }
    
    /**
     * 逻辑删除数据库图片，根据图片id
     * @author 余红伟 
     * @date 2017年6月15日 下午4:11:00
     */
    @Override
    public int deleteMerPic(Long id) {
        // TODO Auto-generated method stub
        return merchantPicDao.logicDeleteMerPicById(id);
    }
    
    /**
     * 添加滚动图片
     * @author 余红伟 
     * @date 2017年6月15日 下午5:09:35
     */
    @Override
    public int addMercarousel(MerchantPic merchantPic) {
        // TODO Auto-generated method stub
        return merchantPicDao.addMerchantPic(merchantPic);
    }
    
    /**
     * 添加图片墙图片
     * @author 余红伟 
     * @date 2017年6月15日 下午5:09:40
     */
    @Override
    public int addMerWallalbum(MerchantPic merchantPic) {
        // TODO Auto-generated method stub
        return merchantPicDao.addMerchantPic(merchantPic);
    }

   

}
