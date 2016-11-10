package org.codethink.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.oauth2.OAuth2Manager;
import org.codethink.wechat.api.oauth2.entity.AccessTokenRequestParam;
import org.codethink.wechat.api.oauth2.entity.AccessTokenResponseParam;
import org.codethink.wechat.api.oauth2.entity.SnsapiUserInfoResponseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 测试网页授权的控制器(两种：scope为snsapi_base和snsapi_userinfo)
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
@Controller
@RequestMapping(value = "/oauth2")
public class OAuth2Controller {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);
	
	/**********************************网页授权作用域为snsapi_base**********************************/
	
	/**
	 * 重定向到接收code的Url(测试网页授权作用域为snsapi_base)
	 * 测试url：http://codethink.ngrok.cc/ck-wechat/oauth2/redirect_base?path=oauth2%2fsnsapi_base_demo(需要对path进行encode)
	 * @param path
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws WechatException
	 * @throws IOException
	 */
	@RequestMapping(value = "/redirect_base", method = RequestMethod.GET)
	public void redirectBase(@RequestParam(value="path") String path,
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse
			) throws WechatException, IOException {
		// 拼接网络协议+域名+项目名称  http://codethink.ngrok.cc/ck-wechat/
		String basePath = httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+httpServletRequest.getContextPath()+"/";
		logger.info("basePath:{}",basePath); // basePath:http://codethink.ngrok.cc/ck-wechat/
		String redirectUrl = basePath + "oauth2/snsapi_base" + "?path=" + path;
		OAuth2Manager.redirectOnSnsapiBaseScope(redirectUrl, httpServletResponse);
	}
	
	/**
	 * 接收code获取openId后转发到其他业务controller(测试网页授权作用域为snsapi_base)
	 * @param code
	 * @throws WechatException
	 */
	@RequestMapping(value = "/snsapi_base", method = RequestMethod.GET)
	public String snsapiBase(@RequestParam(value="code") String code,
			@RequestParam(value="path") String path,
			HttpServletRequest httpServletRequest) throws WechatException {
		
		AccessTokenRequestParam accessTokenRequestParam = new AccessTokenRequestParam();
		accessTokenRequestParam.setCode(code); // 设置code为接收到的code
		// 调用整合接口：网页授权的作用域为snsapi_base，仅可获取openId(仅第二步即结束)
		AccessTokenResponseParam accessTokenResponseParam = OAuth2Manager.getBySnsapiBaseScope(accessTokenRequestParam);
		logger.info("openid:{}",accessTokenResponseParam.getOpenid());
		httpServletRequest.setAttribute("openid", accessTokenResponseParam.getOpenid());
		return "forward:/" + path;
	}
	
	/**
	 * 业务控制器，转发过来后仍然是同一个HttpServletRequest，可以获得openid(测试网页授权作用域为snsapi_base)
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/snsapi_base_demo", method = RequestMethod.GET)
	public String snsapiBaseDemo(HttpServletRequest httpServletRequest) {
		String openId = httpServletRequest.getAttribute("openid").toString();
		logger.info("业务控制器中的openid为{}",openId); // 业务控制器中的openid为oIV7Qs7h-QT2Ev2Bp-bs0BI3Pntw
		return "snsapibasedemo";
	}
	
	
	/**********************************网页授权作用域为snsapi_userinfo**********************************/
	
	/**
	 * 重定向到接收code的Url(测试网页授权作用域为snsapi_userinfo)
	 * 测试url：http://codethink.ngrok.cc/ck-wechat/oauth2/redirect_userinfo?path=oauth2%2fsnsapi_userinfo_demo(需要对path进行encode)
	 * @param path
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws WechatException
	 * @throws IOException
	 */
	@RequestMapping(value = "/redirect_userinfo", method = RequestMethod.GET)
	public void redirectUserinfo(@RequestParam(value="path") String path,
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse
			) throws WechatException, IOException {
		// 拼接网络协议+域名+项目名称  http://codethink.ngrok.cc/ck-wechat/
		String basePath = httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+httpServletRequest.getContextPath()+"/";
		logger.info("basePath:{}",basePath); // basePath:http://codethink.ngrok.cc/ck-wechat/
		String redirectUrl = basePath + "oauth2/snsapi_userinfo" + "?path=" + path;
		// 重定向的url：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc3029ae1656de167&redirect_uri=http://codethink.ngrok.cc/ck-wechat/oauth2/snsapi_userinfo?path=oauth2/snsapi_userinfo_demo&response_type=code&scope=snsapi_base&state=null#wechat_redirect
		OAuth2Manager.redirectOnSnsapUserinfoScope(redirectUrl, httpServletResponse);
	}
	
	/**
	 * 接收code获取用户基本信息后转发到其他业务controller(测试网页授权作用域为snsapi_userinfo)
	 * @param code
	 * @throws WechatException
	 */
	@RequestMapping(value = "/snsapi_userinfo", method = RequestMethod.GET)
	public String snsapiUserinfo(@RequestParam(value="code") String code,
			@RequestParam(value="path") String path,
			HttpServletRequest httpServletRequest) throws WechatException {
		
		AccessTokenRequestParam accessTokenRequestParam = new AccessTokenRequestParam();
		accessTokenRequestParam.setCode(code); // 设置code为接收到的code
		// 调用整合接口：网页授权作用域为snsapi_userinfo，获取用户基本信息(第二步、第四步)
		SnsapiUserInfoResponseParam snsapiUserInfoResponseParam = OAuth2Manager.getBySnsapiUserInfoScope(accessTokenRequestParam);
		// snsapiUserInfoResponseParam:SnsapiUserInfoResponseParam [openid=oIV7Qs7h-QT2Ev2Bp-bs0BI3Pntw, nickname=你的诗我的远方, sex=1, province=Beijing, city=Changping, country=China, headimgurl=http://wx.qlogo.cn/mmopen/gUAsc5MlrRMyNnP0PpUySScAArVI7gBEc0u83sRNFtdb2kTxtia6XODDgrADbXkfarga5Dy0iaoyyiclWEibptX91SwnjZyK9m68/0, privilege=[], unionid=null]
		logger.info("snsapiUserInfoResponseParam:{}",snsapiUserInfoResponseParam.toString());
		httpServletRequest.setAttribute("snsapiUserInfoResponseParam", snsapiUserInfoResponseParam);
		return "forward:/" + path;
	}
	
	/**
	 * 业务控制器，转发过来后仍然是同一个HttpServletRequest，可以获得用户基本信息(测试网页授权作用域为snsapi_userinfo)
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/snsapi_userinfo_demo", method = RequestMethod.GET)
	public String snsapiUserinfoDemo(HttpServletRequest httpServletRequest) {
		Object snsapiUserInfoResponseParam = httpServletRequest.getAttribute("snsapiUserInfoResponseParam");
		// 业务控制器中的snsapiUserInfoResponseParam为SnsapiUserInfoResponseParam [openid=oIV7Qs7h-QT2Ev2Bp-bs0BI3Pntw, nickname=你的诗我的远方, sex=1, province=Beijing, city=Changping, country=China, headimgurl=http://wx.qlogo.cn/mmopen/gUAsc5MlrRMyNnP0PpUySScAArVI7gBEc0u83sRNFtdb2kTxtia6XODDgrADbXkfarga5Dy0iaoyyiclWEibptX91SwnjZyK9m68/0, privilege=[], unionid=null]
		logger.info("业务控制器中的snsapiUserInfoResponseParam为{}",snsapiUserInfoResponseParam); // 业务控制器中的snsapiUserInfoResponseParam为oIV7Qs7h-QT2Ev2Bp-bs0BI3Pntw
		return "snsapiuserinfodemo";
	}
}
