package org.codethink.wechat.api.jsapiticket.service;

import java.util.Date;

import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.jsapiticket.entity.JsApiTicket;
import org.codethink.wechat.api.jsapiticket.entity.JsApiTicketParam;
import org.codethink.wechat.api.tokenproxy.TokenProxy;
import org.codethink.wechat.api.util.HttpUtil;
import org.codethink.wechat.api.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * jsapi_ticket相关的服务接口的实现类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 * @refer http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
 */
public class JsApiTicketServiceImpl implements JsApiTicketService {

	private static Logger logger = LoggerFactory.getLogger(JsApiTicketServiceImpl.class);

	private int redundanceTime = 10; // 冗余时间，提前10秒就去请求新的jsapi_ticket

	// 获取jsapi_ticket的Url
	// https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
	private static final String HTTPS_OPEN_WEIXIN_QQ_COM_CGIBIN_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	// 拼接获取jsapi_ticket的Url
	private static String generateJsApiTicketUrl() throws WechatException {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_OPEN_WEIXIN_QQ_COM_CGIBIN_TICKET);
		url.append("?access_token=").append(TokenProxy.getAccessToken());
		url.append("&type=").append("jsapi");
		return url.toString();
	}

	/**
	 * 获取jsapi_ticket，包括创建时间
	 * 
	 * @return
	 */
	@Override
	public JsApiTicket getJsApiTicket() throws WechatException {
		// TODO Auto-generated method stub
		String requestUrl = generateJsApiTicketUrl();
		logger.info("获取jsapi_ticket的url：{}", requestUrl);
		String responseResult = HttpUtil.doGet(requestUrl);
		// 检查响应结果是否正确，不正确则抛出异常
		WechatUtil.checkResponseResult(responseResult);
		JsApiTicketParam jsApiTicketParam = JSONObject.parseObject(responseResult, JsApiTicketParam.class);
		// jsapi_ticket生成时间
		long jsApiTicketTime = new Date().getTime();
		JsApiTicket jsApiTicket = new JsApiTicket(jsApiTicketParam, jsApiTicketTime);
		return jsApiTicket;
	}

	/**
	 * 判断jsapi_ticket是否过期
	 * 
	 * @return
	 */
	@Override
	public boolean isJsApiTicketExpire(JsApiTicket jsApiTicket) {
		// TODO Auto-generated method stub
		// jsapi_ticket创建时间 单位：秒
		long jsApiTicketTime = jsApiTicket.getJsApiTicketTime();
		long currentTime = new Date().getTime();
		// jsapi_ticket超期时间 单位：秒
		long expiresInTime = jsApiTicket.getExpires_in();
		if (currentTime - jsApiTicketTime > (expiresInTime - redundanceTime) * 1000) {
			return false;
		}
		return true;
	}
}
