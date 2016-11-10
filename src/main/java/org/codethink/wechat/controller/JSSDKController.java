package org.codethink.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.jssdk.JsSdkSignManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * JSSDK控制器(测试JS-SDK的使用)
 * 
 * @author CaiXiangNing
 * @date 2016年11月7日
 * @email caixiangning@gmail.com
 * @reference http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html
 */
@Controller
@RequestMapping(value="/jssdk")
public class JSSDKController {
	
	/**
	 * 测试JS-SDK接口调用
	 * http://codethink.ngrok.cc/ck-wechat/jssdk/demo
	 * @param httpServletRequest
	 * @return
	 * @throws WechatException
	 */
	@RequestMapping(value="/demo", method= RequestMethod.GET)
	public String access(HttpServletRequest httpServletRequest) throws WechatException{
		JsSdkSignManager.executeUrlSignature(httpServletRequest);
		return "jssdkdemo";
	}
}
