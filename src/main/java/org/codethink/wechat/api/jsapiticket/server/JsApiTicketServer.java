package org.codethink.wechat.api.jsapiticket.server;

import org.codethink.wechat.api.exception.WechatException;

/**
 * 
 * 获取jsapi_ticket的中控服务器接口，可定义不同的服务器实现
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public interface JsApiTicketServer {
	/**
	 * 使用中控服务器获取有效的jsapi_ticket，过期自动申请新的jsapi_ticket
	 * @return
	 * @throws WechatException
	 */
	public String getJsApiTicket() throws WechatException;
}
