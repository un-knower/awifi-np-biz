/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月28日 上午9:28:05
* 创建作者：余红伟
* 文件名称：PubImageServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.image.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.pub.image.dao.PubImageDao;
import com.awifi.np.biz.pub.image.model.PubImage;
import com.awifi.np.biz.pub.image.service.PubImageService;
@Service
public class PubImageServiceImpl implements PubImageService{
    @Resource
    private PubImageDao pubImageDao;
    /**
     * 添加图片
     * @author 余红伟 
     * @date 2017年6月29日 上午10:32:14
     */
    @Override
    public Integer addPubImage(PubImage pubImage) {
        return pubImageDao.addPubImage(pubImage);
    }
    /**
     * 根据id和status更改图片状态 状态 ：临时：0;可以:1;删除：9
     * @author 余红伟 
     * @date 2017年6月30日 上午9:27:01
     */
    @Override
    public Integer updatePubImageByIdAndStatus(Long id, Integer status) {
        
        return pubImageDao.updatePubImageByIdAndStatus(id, status);
    }
    /**
     * 根据id查询图片
     * @author 余红伟 
     * @date 2017年6月30日 上午9:27:13
     */
    @Override
    public PubImage selectById(Long id) {
        return pubImageDao.selectById(id);
    }
    /**
     * 根据id删除图片
     * @author 余红伟 
     * @date 2017年6月30日 上午9:27:24
     */
    @Override
    public Integer deleteById(Long id) {
        return pubImageDao.deleteById(id);
    }
    /**
     * 根据url查询图片
     * @author 余红伟 
     * @date 2017年6月30日 上午10:33:00
     */
    @Override
    public PubImage selectByUrl(String url) {
        return pubImageDao.selectByUrl(url);
    }
    
    /**
     * 删除图片,状态为9和部分为0的
     * @author 余红伟 
     * @date 2017年6月30日 下午2:16:03
     */
    @Override
    public List<PubImage> getDeleteList() {
        return pubImageDao.getDeleteList();
    }
   
}
