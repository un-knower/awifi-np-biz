package com.awifi.np.biz.common.base.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 11, 2017 8:42:22 AM
 * 创建作者：亢燕翔
 * 文件名称：Page.java
 * 版本：  v1.0
 * 功能：  分页实体类
 * 修改记录：
 */
public class Page<T> {
    
    /** 对应的当前页记录 */
    private List<T> records;

	/** 页码，默认是第一页 */
    private Integer pageNo;

    /** 每页显示的记录数 */
    private Integer pageSize;

    /** 开始记录数 */
    private Integer begin;

    /** 总记录数 */
    private Integer totalRecord;

    /** 总页数 */
    private Integer totalPage;

    
    public Page() {
        super();
        this.configBegin();
    }

    public Page(Integer pageNo) {
        super();
        this.pageNo = pageNo;
        this.configBegin();
    }

    public Page(Integer pageNo, Integer pageSize) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.configBegin();
    }
	
    /**
     * 配置begin页码 
     * @author 亢燕翔  
     * @date Jan 11, 2017 8:43:43 AM
     */
    private void configBegin() {
        if (pageNo != null && pageSize != null) {
            this.begin = (this.pageNo - 1) * this.pageSize;
        }
    }
    
    /**
     * 获取数据集合
     * @return List<T>
     * @author 亢燕翔  
     * @date Jan 11, 2017 8:46:22 AM
     */
    public List<T> getRecords() {
        if(records == null){
            records = new ArrayList<T>(); 
        }
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getPageNo() {
        return pageNo;
    }
	
    /**
     * 设置页码
     * @param pageNo 页码
     * @author 亢燕翔  
     * @date Jan 11, 2017 8:44:28 AM
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        this.configBegin();
    }

    public Integer getPageSize() {
        return pageSize;
    }
    
    /**
     * 设置每页数量
     * @param pageSize 每页数量
     * @author 亢燕翔  
     * @date Jan 11, 2017 8:45:04 AM
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.configBegin();
    }

    @JsonIgnore
    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }
    
    /**
     * 设置记录总数
     * @param totalRecord 总页数
     * @author 亢燕翔  
     * @date Jan 11, 2017 8:45:50 AM
     */
    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        // 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        Integer totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Page [pageNo=").append(pageNo).append(", pageSize=").append(pageSize).append(", results=")
                .append(records).append(", totalPage=").append(totalPage).append(", totalRecord=").append(totalRecord)
                .append("]");
        return builder.toString();
    }
    
}
