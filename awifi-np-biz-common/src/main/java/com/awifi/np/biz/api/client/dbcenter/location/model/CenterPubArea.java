package com.awifi.np.biz.api.client.dbcenter.location.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: CenterPubArea
 * @Description: 公共资源地区表实体类
 * @author: Dingxc
 * @date: 2015年7月22日 下午7:22:05
 * @version: 0.0.1
 */
/**
 *@ClassName:CenterPubArea
 *@Description:
 *@author root
 *
 */
public class CenterPubArea implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2623877948752730516L;
    /**
     * center_pub_area.ID:
     * <p>
     * <code>
     * 内部关联使用，AUTO_INCREMENT<br>
     * </code>
     */
    private Long id;

    /**
     * center_pub_area.PARENT_ID:
     * <p>
     */
    private Long parentId;

    /**
     * center_pub_area.AREA_NAME:
     * <p>
     */
    private String areaName;
    /**
     * 
     */
    private String areaFullName;

    public String getAreaFullName()
    {
        return areaFullName;
    }

    public void setAreaFullName(String areaFullName)
    {
        this.areaFullName = areaFullName;
    }

    /**
     * center_pub_area.AREA_TYPE:
     * <p>
     * <code>
     * COUNTRY; 国家PROVINCE;省（直辖市）CITY;（市）COUNTY（县）<br>
     * </code>
     */
    private String areaType;

    /**
     * center_pub_area.AREA_CN_CODE:
     * <p>
     */
    private String areaCnCode;

    /**
     * center_pub_area.POST_CN_CODE:
     * <p>
     */
    private String postCnCode;

    /**
     * center_pub_area.CRM_CODE:
     * <p>
     */
    private String crmCode;

    /**
     * center_pub_area.STATUS:
     * <p>
     * <code>
     * 关联字典表（COMMON-STATUS）1:正常9:作废<br>
     * </code>
     */
    private Integer status;

    /**
     * center_pub_area.CREATE_DATE:
     * <p>
     */
    private Date createDate;

    /**
     * center_pub_area.MODIFY_DATE:
     * <p>
     */
    private Date modifyDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAreaType()
    {
        return areaType;
    }

    public void setAreaType(String areaType)
    {
        this.areaType = areaType == null ? null : areaType.trim();
    }

    public String getAreaCnCode()
    {
        return areaCnCode;
    }

    public void setAreaCnCode(String areaCnCode)
    {
        this.areaCnCode = areaCnCode == null ? null : areaCnCode.trim();
    }

    public String getPostCnCode()
    {
        return postCnCode;
    }

    public void setPostCnCode(String postCnCode)
    {
        this.postCnCode = postCnCode == null ? null : postCnCode.trim();
    }

    public String getCrmCode()
    {
        return crmCode;
    }

    public void setCrmCode(String crmCode)
    {
        this.crmCode = crmCode == null ? null : crmCode.trim();
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getModifyDate()
    {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate)
    {
        this.modifyDate = modifyDate;
    }
}