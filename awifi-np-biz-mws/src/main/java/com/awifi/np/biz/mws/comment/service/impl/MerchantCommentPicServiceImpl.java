/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午7:22:26
* 创建作者：方志伟
* 文件名称：MerchantCommentPicServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.mws.comment.dao.MerchantCommentPicDao;
import com.awifi.np.biz.mws.comment.service.MerchantCommentPicService;

@Service("merchantCommentPicService")
@SuppressWarnings({"rawtypes"})
public class MerchantCommentPicServiceImpl implements MerchantCommentPicService{
    /**
     * 注解
     */
    @Resource(name="merchantCommentPicDao")
    private MerchantCommentPicDao merchantCommentPicDao;
    /**
     * 商户评论——图片添加实现方法
     * @param commentPicUrl 评论图片集合
     * @param commentId 评论表主键id
     * @author 方志伟  
     * @date 2017年6月16日 下午12:16:38
     */
    public void add(ArrayList commentPicUrl, Long commentId) {
        if(commentPicUrl != null && commentPicUrl.size() > 0){//判断上传图片是否为空
            for(int i = 0; i < commentPicUrl.size(); i++){//循环commentPicUrl集合
                String picUrl = (String) commentPicUrl.get(i);//将commentPicUrl值逐一赋值。
                merchantCommentPicDao.add(picUrl, commentId, 1);//插入数据
            }
        }
    }

	/**
	 * 查询商户评图片url
	 * @param commentId 评论id
	 * @return 结果
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:49:14 PM
	 */
    public List<String> getPicListByCommentId(Long commentId){
        List<String> picUrlList = merchantCommentPicDao.getPicListByCommentId(commentId);//查询商户评图片url
        String domain = SysConfigUtil.getParamValue("mws_wall_domain");//获取微站域名http://m-img.51awifi.com/upload/
        List<String> resutList = new ArrayList<String>();
        for (String picUrl : picUrlList) {
            picUrl = domain + picUrl;  //拼装url
            resutList.add(picUrl);
        }
        return resutList;//返回结果
    }
}
