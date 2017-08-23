/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:34:33
* 创建作者：余红伟
* 文件名称：MerchantPicController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchantpic.controller;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.merchant.pic.model.MerchantPic;
import com.awifi.np.biz.mws.merchant.pic.service.MerchantPicService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "获取商户图片")
@Controller
@RequestMapping("/mersrv/ms/merpic")
public class MerchantPicController extends BaseController{
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private MerchantPicService merchantPicService;
    
    //最大上传滚动图片数
    private static final int MAX_CAROUSEL_NUMS = 3;
    //最大上传图片墙图片数
    private static final int MAX_WALLALBUM_NUMS = 18;
    /**
     * 获取商户滚动图片列表
     * @param merid
     * @return
     * @author 余红伟 
     * @throws Exception 
     * @throws NumberFormatException 
     * @date 2017年6月9日 上午10:41:41
     */
    @API(summary = "获取商户滚动图片", parameters = {
            @Param(name = "merid", description = "商户id", in = "path", dataType = DataType.LONG, required = true )
    })
    @RequestMapping(value = "/carousel/{merid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String,Object> getMerCarouselByMerId(@PathVariable(value = "merid", required = true) String merid) throws NumberFormatException, Exception{
        logger.debug("获取商户滚动图片,merid= " + merid);
        //商户id，不能为空，且为数字
        ValidUtil.valid("商户id", merid, "numeric");
        //==================调用微站接口获取商户首页图片信息 包括滚动图片 照片墙信息=====================
        List<MerchantPic> list =new ArrayList();
        try{
            list = merchantPicService.getMerCarouselByMerIdThroughMws(Long.valueOf(merid));
            //==============查询出错 指定给定3个默认图片
            if(list.size()==0){
                list = merchantPicService.getMerCarouselByDefault();
            }
        }catch (Exception e) {
            //==============查询出错 指定给定3个默认图片
           list = merchantPicService.getMerCarouselByDefault();
        }
        
        Map<String,Object> resultMap = new HashMap<>();
        //图片前缀从redis中取
        resultMap.put("imageDomain",SysConfigUtil.getParamValue("media_image_domain"));
        resultMap.put("picList", list);
        return this.successMsg(resultMap);
    }
    
  
    /**
     * 获取商户图片墙图片
     * @param merid
     * @return
     * @author 余红伟 
     * @throws Exception 
     * @throws NumberFormatException 
     * @date 2017年6月9日 上午10:42:03
     */
    @API(summary = "获取商户图片墙图片", parameters = {
            @Param(name = "merid", description = "商户id", in ="path", dataType = DataType.LONG, required = true)
    })
    @RequestMapping(value = "/wallalbum/{merid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String,Object> getMerWallAlbumByMerId(@PathVariable(value = "merid", required = true) String merid) throws NumberFormatException, Exception{
        
        String domain = SysConfigUtil.getParamValue("resources_domain") + "/media_mws/upload/";
        logger.debug("获取商户图片墙图片,merid= " + merid);
        //=========商户id，不能为空，且为数字  
        ValidUtil.valid("商户id", merid, "numeric");
        
        List<MerchantPic> list = null;
        // ======================调用微站接口获取图片墙
        try{
            list = merchantPicService.getMerWallAlbumByMerIdThroughMws(Long.valueOf(merid));
            
        }catch (Exception e) {
            list = new ArrayList<>();
        }
       
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("imageDomain",SysConfigUtil.getParamValue("media_image_domain"));
        resultMap.put("picList", list);
        resultMap.put("picAccount", list.size());
        return this.successMsg(resultMap);
    }

    /**
     * 新增商户滚动图片
     * @param bodyParam
     * @return
     * @author 余红伟 
     * @date 2017年6月16日 下午4:08:23
     */
    @API(summary = "新增商户滚动图片",parameters = {
            @Param(name = "url", description = "图片相对路径",dataType = DataType.STRING, required = true),
            @Param(name = "merid",description = "商户id", dataType = DataType.STRING, required =true)
    })
    @RequestMapping(value = "/carousel",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String,Object> addMercarousel(@RequestBody(required = true) Map<String,Object> bodyParam){
        logger.debug("bodyParam参数: "+JSON.toJSONString(bodyParam));
        Long merid = Long.valueOf((String) bodyParam.get("merid"));
        String url = (String) bodyParam.get("url");
        //======== 验证图片路径
        ValidUtil.valid("图片路径", url, "{'required':true,'numeric':true}");
        //=========查询图片张数和图片的orderNum
        List<MerchantPic> list = merchantPicService.getMerCarouselByMerId(merid);
        if(list != null && list.size() > MAX_CAROUSEL_NUMS){
            //不能上传
            throw new BizException("E2300001", MessageUtil.getMessage("E2300001","滚动图片张数超过最大值,请先删除"));
        }
        Integer orderNum = 1;//orderNums默认1，如果是第一章图片
        if(list!=null && list.size() > 0){
            //根据图片的orderNum排序list
            sortList(list);
            //最新的图片排序num加1
            orderNum = list.get(list.size()-1).getOrderNum() +1;
        }
        MerchantPic merchantPic = new MerchantPic();
        merchantPic.setMerchantId(merid);
        merchantPic.setPicType(1);//滚动图片
        merchantPic.setPicPath(url);
        merchantPic.setOrderNum(orderNum);
        merchantPicService.addMercarousel(merchantPic);
        return this.successMsg();
    }
    /**
     * 新增商户图片墙图片
     * @param bodyParam
     * @return
     * @author 余红伟 
     * @date 2017年6月16日 下午4:08:03
     */
    @API(summary = "新增商户图片墙图片",parameters = {
            @Param(name = "url",description = "图片相对路径",dataType = DataType.STRING,required = true),
            @Param(name = "merid",description = "商户id",dataType = DataType.LONG, required = true)
    })
    @RequestMapping(value = "/wallalbum", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String,Object> addMerWallalbum(@RequestBody(required = true) Map<String, Object> bodyParam){
        logger.debug("bodyParam参数: "+JSON.toJSONString(bodyParam));
        Long merid = Long.valueOf((String) bodyParam.get("merid"));
        //======== 验证图片路径
        String url = (String) bodyParam.get("url");
        ValidUtil.valid("图片路径", url, "{'required':true,'numeric':true}");
        //=========查询图片张数和图片的orderNum
        List<MerchantPic> list = merchantPicService.getMerWallAlbumByMerId(merid);
        if(list.size() > MAX_WALLALBUM_NUMS){
            //不能上传
            throw new BizException("E2300002", MessageUtil.getMessage("E2300002", "照片墙图片张数超过最大值,请先删除"));
        }
        Integer orderNum = 1;//orderNums默认1，如果是第一章图片
        if(list!=null && list.size() > 0){
            //============根据图片的orderNum排序list===========
            sortList(list);
            //============最新的图片排序num加1=========
            orderNum = list.get(list.size()-1).getOrderNum() +1;
        }
        MerchantPic merchantPic = new MerchantPic();
        merchantPic.setStatus(1);
        merchantPic.setPicType(1);
        merchantPic.setPicPath(url);
        merchantPic.setOrderNum(orderNum);
        merchantPicService.addMerWallalbum(merchantPic);
        return this.successMsg();
    }
    
    /**
     * 逻辑删除数据库图片，根据图片id
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年6月15日 下午4:46:05
     */
    @API(summary = "逻辑删除数据库图片，根据图片id", parameters = {
            @Param(name = "id",description = "图片id", dataType = DataType.LONG, required = true)
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String,Object> deleteMerPic(@RequestParam(value = "id", required = true) String id){
        logger.debug("图片id: "+ id);
        ValidUtil.valid("图片id", id,"{'required':true,'numeric':true}");
        //删除图片
        merchantPicService.deleteMerPic(Long.valueOf(id));
        return this.successMsg();
    }
    /**
     * 根据图片的orderNum排序list
     * @param list
     * @author 余红伟 
     * @date 2017年6月16日 上午10:57:58
     */
    public static void sortList(List<MerchantPic> list){
        Collections.sort(list, new Comparator<MerchantPic>() {
            //根据图片的orderNum排序,升序
            @Override
            public int compare(MerchantPic o1, MerchantPic o2) {
                if(o1.getOrderNum() > o2.getOrderNum()){
                    return 1;
                }else if(o1.getOrderNum() == o2.getOrderNum()){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
    }
   
}
