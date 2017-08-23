package com.awifi.np.biz.mocksrv.toe.admin.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 2:05:42 PM
 * 创建作者：亢燕翔
 * 文件名称：projectController.java
 * 版本：  v1.0
 * 功能：  项目管理数据模拟
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/mocksrv/project")
public class ProjectController {

	/**
	 * 列表方法
	 * @return map
	 * @author 亢燕翔  
	 * @throws Exception 
	 * @date Jan 10, 2017 2:52:22 PM
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public Map list(HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		System.out.println("=================list");
		resultMap.put("message", "list");
		resultMap.put("result", "OK");
		return resultMap;
	}
	
	/**
	 * 显示方法
	 * @return map
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 2:51:22 PM
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
    public Map show(){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	System.out.println("=================show");
    	resultMap.put("message", "show");
    	resultMap.put("result", "OK");
        return resultMap;
    }
    
	/**
	 * 删除方法
	 * @return map
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 2:52:08 PM
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}",method=RequestMethod.DELETE)
    public Map delete(@RequestParam(value="access_token", required=true) String access_token){
		System.out.println("=================delete");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("message", "delete");
    	resultMap.put("result", "OK");
    	return resultMap;
    }
	
	/**
	 * 更新方法
	 * @return map
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 2:54:40 PM
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.PUT)
	public Map update(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("=================update");
		 System.out.println("update");
    	resultMap.put("message", "update");
    	resultMap.put("result", "OK");
        return resultMap;
	}
	
}
