package org.codethink.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codethink.wechat.api.access.WechatSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 接入验证的控制器
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
@Controller
@RequestMapping(value = "/")
public class AccessController {
	
	@RequestMapping(value = "/accessauth", method = RequestMethod.GET)
	public void access(HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) throws IOException{
		WechatSupport wechatSupport = new WechatSupport(httpServletRequest);
		String result = wechatSupport.execute();
		httpServletResponse.getOutputStream().write(result.getBytes());
	}
}
