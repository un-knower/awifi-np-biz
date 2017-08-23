/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月27日 上午9:31:47
* 创建作者：张智威
* 文件名称：TimeChangeBiz.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.biz;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.timebuysrv.user.service.UserAuthService;
import com.awifi.np.biz.timebuysrv.util.ExcelUtil;
import com.awifi.np.biz.timebuysrv.web.core.path.PathManager;
import com.awifi.np.biz.timebuysrv.web.module.time.controller.TimeController;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserConsumeService;
import com.awifi.np.biz.timebuysrv.web.util.MapUtils;

@Service
public class TimeTradeBiz {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimeTradeBiz.class);

    @Resource
    private UserConsumeService userConsumeService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private TimeBuyService timeBuyService;

    /**
     * 批量到入excel 进行赔付时长 timeChangeController调用
     * 
     * @param file
     * @param merchantId
     * @param days
     * @param operatorName
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年5月27日 上午9:38:12
     */
    public String importExcel(MultipartFile file, long merchantId, float days, String operatorName) throws Exception {

        // 将spring 的file 装成 普通file
        File xlsFile = null;
        if (file != null) {
            try {
                String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
                String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
                Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
                xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
            } catch (Exception e) {
                logger.error("文件上传出错", e);
                throw new BizException("E041412312", "文件上传出错");
            }
        }
        String result = "";
        try {

            // 将导入的中文列名匹配至数据库对应字段
            int success = 0;
            int fail = 0;
            StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
            Map<String, String> colMatch = new HashMap<String, String>();
            colMatch.put("手机", "telphone");
            List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
            for (int i = 0; i < list.size(); i++) {

                Map<String,String> map = list.get(i);
                String telphone = MapUtils.getStringValue(map, "phone");
                // 判断用户手机号码是否存在;

                PubUserAuth pubUser = userAuthService.getUserByLogName(telphone);//检查手机号是否存在

                if (pubUser == null || pubUser.getUserId() == null) {
                    logger.info("packageservice import conf ==> the telphone:" + telphone + " not exist");
                    fail++;
                    errorMsg.append("手机号码:" + telphone + " 不存在;");
                    // return "the telphone:"+telphone+" not exist";
                } else {
                    UserConsume userConsume = new UserConsume();
                    userConsume.setAddDay(days*1f);
                    // 赔付的consumetype 是2
                    userConsume.setConsumeType(2);
                    userConsume.setMerchantId(merchantId);
                    userConsume.setUserId(pubUser.getUserId());//用户id

                    userConsume.setPackageId(202l);// 全局赔付套餐id
                    userConsume.setPackageNum(1);//数量为1
                    userConsume.setTotalNum(0f);//价格为0
                    userConsume.setOrderId("");//没有订单号
                    userConsume.setPayNum(0f);//付款价格为0
                    userConsume.setRemarks(operatorName);//操作人的姓名

                    try {
                        timeBuyService.addConsumeRecordAndUpdateTime(userConsume);
                        success++;//成功数增加
                    } catch (Exception e) {
                        fail++;//失败数增加
                        logger.info("packageservice import conf ==> update fail ==>the telphone:" + telphone + "");
                        errorMsg.append("the telphone:" + telphone + " update fail;");
                    }

                }
            }
            return "导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString();

        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException){
                throw new BizException("E041412313", "导入的excel需为2003版本");
            }else{
                throw new BizException("E041412313", e.getMessage());
            }
        }
    }

}
