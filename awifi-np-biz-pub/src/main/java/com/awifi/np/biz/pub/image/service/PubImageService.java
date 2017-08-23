/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午4:26:43
* 创建作者：余红伟
* 文件名称：PubImageService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.image.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.pub.image.model.PubImage;

public interface PubImageService {
    /**
     * 添加图片
     * @param pubImage
     * @return
     * @author 余红伟 
     * @date 2017年6月29日 上午10:31:16
     */
    public Integer addPubImage(PubImage pubImage);
    /**
     * 根据id和status更改图片状态 状态 ：临时：0;可以:1;删除：9
     * @param orginalURL
     * @return
     * @author 余红伟 
     * @date 2017年6月29日 上午10:31:29
     */
    public Integer updatePubImageByIdAndStatus(Long id,Integer status);
    
    /**
     * 根据id查询图片
     * @param status
     * @return
     * @author 余红伟 
     * @date 2017年6月29日 上午10:31:39
     */
    public PubImage selectById(Long id);
    /**
     * 根据id删除图片
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年6月30日 上午9:25:03
     */
    public Integer deleteById(Long id);
    
    /**
     * 根据url查询图片
     * @param url
     * @return
     * @author 余红伟 
     * @date 2017年6月30日 上午10:31:09
     */
    public PubImage selectByUrl(String url);
    
    /**
     * 获取删除图片,状态为9和部分为0的
     * @return
     * @author 余红伟 
     * @date 2017年6月30日 下午2:15:04
     */
    public List<PubImage> getDeleteList();
    
}
