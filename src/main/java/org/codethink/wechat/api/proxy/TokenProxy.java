package org.codethink.wechat.api.proxy;

import org.codethink.wechat.api.accesstoken.server.AccessTokenMemoryServer;
import org.codethink.wechat.api.accesstoken.server.AccessTokenServer;
import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.jsapiticket.server.JsApiTicketMemoryServer;
import org.codethink.wechat.api.jsapiticket.server.JsApiTicketServer;

/**
 * 
 * 获取access_token和jsapi_ticket的代理
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class TokenProxy {
	/**
	 * 通过代理得到accessToken的字符串
	 * @throws WechatException 
	 */
	public static String getAccessToken() throws WechatException{
		AccessTokenServer accessTokenServer = AccessTokenMemoryServer.instance();
		return accessTokenServer.getAccessToken();
	}
	
	/**
	 * 通过代理得到jsapi_ticket的字符串
	 * @throws WechatException 
	 */
	public static String getJsApiTicket() throws WechatException{
		JsApiTicketServer jsApiTicketServer = JsApiTicketMemoryServer.instance();
		return jsApiTicketServer.getJsApiTicket();
	}
}
