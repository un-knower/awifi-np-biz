/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午7:22:10
* 创建作者：方志伟
* 文件名称：MerchantCommentPicService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.service;

import java.util.List;
import java.util.ArrayList;
@SuppressWarnings({"rawtypes"})
public interface MerchantCommentPicService {
	
    /**
     * 商户评论图片列表
     * @param commentId 评论Id
     * @return 结果
     * @author 季振宇  
     * @throws Exception 异常
     * @date 2017年6月16日 上午10:50:14
     */
    List<String> getPicListByCommentId(Long commentId) throws Exception;

    /**
     * 商户评论——图片添加接口
     * @param commentPicUrl 评论图片集合
     * @param commentId 评论表主键id
     * @author 方志伟  
     * @date 2017年6月16日 上午11:16:25
     */
    void add(ArrayList commentPicUrl,Long commentId);
}
