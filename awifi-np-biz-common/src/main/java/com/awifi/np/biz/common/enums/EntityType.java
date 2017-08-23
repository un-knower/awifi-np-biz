/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月27日 上午10:16:14
* 创建作者：王冬冬
* 文件名称：EntityType.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.enums;

/**
 * 设备类型枚举类
 * @author 王冬冬
 * 2017年7月27日 上午10:41:52
 */
public enum EntityType {
      /**胖ap*/
      fat("fat","31"),
      /**光猫*/
      gopn("gopn","32,33,34,35"),
      /**热点*/
      hotarea("hotarea",""),
      /**BAS*/
      bas("bas","11"),
      /**瘦ap*/
      fit("fit","41,42,43");
    /**
     * 设备类型
     */
    private String value;
    /**
     * entityType对应数字
     */
    private String entityType;
    EntityType(String value,String entityType){
        this.value=value;
        this.entityType=entityType;
    }
    
    /**
     * 获取entityType对应数字
     * @param entityType 设备类型
     * @return entityType对应数字
     * @author 王冬冬  
     * @date 2017年7月27日 上午10:42:29
     */
    public static String getEntityType(String entityType){
        EntityType result=null;
        try{
            result=valueOf(entityType);
        }catch(IllegalArgumentException e){
            /* 抛异常表示没找到对应的枚举值，直接返回*/
        }
        return result==null?null:result.entityType;
    }
    
   public static void main(String[] args) {
    System.out.println(getEntityType("hehe"));
    System.out.println(getEntityType("fat"));
}
}
