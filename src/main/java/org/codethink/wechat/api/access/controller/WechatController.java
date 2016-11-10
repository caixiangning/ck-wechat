package org.codethink.wechat.api.access.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codethink.wechat.api.access.WechatSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 微信接入验证控制器
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 * @refer http://mp.weixin.qq.com/wiki/8/f9a0b8382e0b77d87b3bcc1ce6fbc104.html
 */
@Controller
@RequestMapping(value = "/")
public class WechatController {
	
	/**
	 * 接口配置信息中填入以下两项信息，然后提交验证
	 * URL：http://codethink.ngrok.cc/ck-wechat/accessauth
	 * Token：sdfasdfas345sdgz5sdfg23fd4ssx
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws IOException
	 */
	@RequestMapping(value = "/accessauth", method = RequestMethod.GET)
	public void access(HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) throws IOException{
		WechatSupport wechatSupport = new WechatSupport(httpServletRequest);
		String result = wechatSupport.execute();
		httpServletResponse.getOutputStream().write(result.getBytes());
	}
}
