package com.awifi.np.biz.pub.security.login.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.admin.suit.service.SuitService;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 上午9:56:38
 * 创建作者：周颖
 * 文件名称：LoginControler.java
 * 版本：  v1.0
 * 功能：用户登录
 * 修改记录：
 */ 
@Controller
@SuppressWarnings("rawtypes")
public class LoginController extends BaseController {
    
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**np用户服务*/
    @Resource(name = "userService")
    private UserService userService;
    
    /**toe用户服务*/
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**套码服务*/
    @Resource(name = "suitService")
    private SuitService suitService;
    
    /**
     * 获取登录页面
     * @param templateCode page(pan)编号
     * @param suitCode 套码
     * @return map template
     * @author 周颖  
     * @date 2017年1月11日 下午3:08:43
     */
    @RequestMapping(method=RequestMethod.GET, value="/pubsrv/login/view/{templatecode}")
    @ResponseBody
    public Map loginView(@PathVariable(value="templatecode") String templateCode,@RequestParam(value="suitcode",required=true) String suitCode){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_pub");//从配置表读取服务代码
        String template = templateService.getByCode(suitCode,serviceCode,templateCode);//获取模板页面
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("code", "0");
        resultMap.put("template", template);
        resultMap.put("uid", KeyUtil.generateUpperUUID());
        return resultMap;
    }
    
    /**
     * 获取验证码接口
     * @param uid 页面唯一标识
     * @param response response
     * @return 验证码内容
     * @author 许小满  
     * @date 2017年3月15日 下午5:03:08
     */
    @RequestMapping(method=RequestMethod.GET, value="/pubsrv/authcode")
    @ResponseBody
    public Map getAuthCode(@RequestParam(value="uid",required=true)String uid, HttpServletResponse response){
        ValidUtil.valid("uid", uid, "required");//uid非空校验
        if(uid.length() != 32){
            throw new ValidException("E2000053", MessageUtil.getMessage("E2000053", new Object[]{"uid",uid}));//{0}[{1}]不符合规范!
        }
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        int width = 80;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 生成随机类
        Random random = new Random();

        // 设定背景色
        // g.setColor(getRandColor(200, 250));
        g.setColor(new Color(104, 104, 104));
        g.fillRect(0, 0, width, height);

        // 设定字体
        g.setFont(new Font("微软雅黑", Font.PLAIN, 23));

        // 画边框
        // g.setColor(Color.black);
        g.drawRect(0, 0, width-1, height-1);

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        // g.setColor(getRandColor(160, 200));
        /*for (int i = 0; i < 10; i++) {
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }*/
        String allcs = "0123456789";
        char[] cs = allcs.toCharArray();
        // 取随机产生的认证码(6位数字)
        String sRand = "";
        Color whileColor = new Color(255, 255, 255);
        for (int i = 0; i < 4; i++) {
            char c = cs[random.nextInt(cs.length)];
            String rand = new Character(c).toString();
            sRand += rand;
            // 将认证码显示到图象中
            // g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.setColor(whileColor);
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 14 * i + 13, 28);
        }

        // 将认证码存入redis中
        String redisKey = RedisConstants.AUTH_CODE + uid;
        RedisAdminUtil.set(redisKey, sRand, RedisConstants.AUTH_CODE_TIME);
        
        // 图象生效
        g.dispose();

        // 输出图象到页面
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);
        } finally {
            // 解决以调用错误
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                ErrorUtil.printException(e, logger);
            }
        }
        return this.successMsg();
    }
    
    /**
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param authCode 验证码
     * @param uid 页面唯一标识
     * @param request 请求
     * @return access_token
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月11日 下午8:19:14
     */
    @RequestMapping(method=RequestMethod.POST, value="/pubsrv/login")
    @ResponseBody
    public Map login(
            @RequestParam(value="username",required=true)String userName,
            @RequestParam(value="password",required=true)String password,
            @RequestParam(value="authcode",required=true)String authCode,
            @RequestParam(value="uid",required=true)String uid,
            HttpServletRequest request) throws Exception{
        ValidUtil.valid("用户名[username]", userName, "required");//userName非空校验
        ValidUtil.valid("密码[password]", password, "required");//password非空校验
        ValidUtil.valid("验证码[authcode]", authCode, "required");//authCode非空校验
        ValidUtil.valid("页面唯一标识[uid]", uid, "required");//uid非空校验
        //验证码有效性校验
        String authCodeKey = RedisConstants.AUTH_CODE + uid;//验证码在redis中对应的key
        String authCodeValue = RedisAdminUtil.get(authCodeKey);//验证码在redis中对应的value
        if(StringUtils.isBlank(authCodeValue) || !authCodeValue.equals(authCode)){
            throw new ValidException("E2000054", MessageUtil.getMessage("E2000054"));//验证码不正确，有效期5分钟!
        }
        
        Map<String,Object> value = new HashMap<String,Object>();
        //先查询np的user表，不存在再查toe的user表
        String passwordMd5 = EncryUtil.getMd5Str(password);//密码md5加密
        User npUser = userService.getByNameAndPwd(userName, passwordMd5);//获取NP用户信息
        if(npUser !=  null){//np的用户
            value.put("userInfo", npUser);//redis 保存用户信息
            value.put("belongTo", "NP");//用户标识 NP/ToE
        }else{
            ToeUser toeUser = toeUserService.getByNameAndPwd(userName, passwordMd5);//获取toe用户信息
            if(toeUser == null){//抛异常，账号密码不匹配！
                throw new BizException("E2010001", MessageUtil.getMessage("E2010001"));
            }
            String roleIds = toeUser.getRoleIds();
            String[] roleIdsArray = roleIds.split(",");
            Long[] roleIdsLong = CastUtil.toLongArray(roleIdsArray);
            String suitCode = suitService.getCodeById(roleIdsLong);//根据角色获取套码
            if(StringUtils.isBlank(suitCode)){//如果套码还为空
                throw new BizException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
            }
            Map<String,Object> userInfo = new HashMap<String,Object>();
            userInfo.put("id", toeUser.getId());
            userInfo.put("userName", toeUser.getUserName());
            userInfo.put("provinceId", toeUser.getProvinceId());
            userInfo.put("cityId", toeUser.getCityId());
            userInfo.put("areaId", toeUser.getAreaId());
            userInfo.put("roleIds", roleIds);
            userInfo.put("suitCode", suitCode);
            Long merchantId = toeUser.getMerchantId();
            if(merchantId != null){//商户管理员
                userInfo.put("merchantId", merchantId);
                Merchant merchant = MerchantClient.getByIdCache(merchantId);
                if(merchant != null){//针对批量导入的商户 用户表没保存地区信息 补全地区信息
                    Map<String,Object> extend = new HashMap<String,Object>();
                    extend.put("merchantType", merchant.getMerchantType());
                    extend.put("cascadeLabel", merchant.getCascadeLabel());
                    userInfo.put("provinceId", merchant.getProvinceId());
                    userInfo.put("cityId", merchant.getCityId());
                    userInfo.put("areaId", merchant.getAreaId());
                    userInfo.put("extend", extend);
                }
            }
            value.put("userInfo", userInfo);//redis 保存用户信息
            value.put("belongTo", "ToE");//用户标识 NP/ToE
        }
        String appId = SysConfigUtil.getParamValue("appid_pub");//从配置表取平台id
        String userIp = IPUtil.getIpAddr(request);//用户ip
        value.put("appId", appId);//redis 保存平台id
        value.put("userIp", userIp);//redis 保存用户ip
        String accessToken = KeyUtil.generateAccessToken(appId);//生成access_token;
        int accessTokenTime = Integer.parseInt(SysConfigUtil.getParamValue("access_token_time"));//access_token有效时间
        RedisAdminUtil.set(accessToken, JsonUtil.toJson(value), accessTokenTime);//access_token保存到和运管共用的redis
        Map<String,Object> data =new HashMap<String,Object>();
        data.put("access_token", accessToken);//返回access_token
        //资源释放，删除redis中缓存的验证码
        RedisAdminUtil.delete(authCodeKey);
        return this.successMsg(data);//返回结果
    }
}