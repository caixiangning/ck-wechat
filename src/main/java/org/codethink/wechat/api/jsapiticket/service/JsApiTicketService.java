package org.codethink.wechat.api.jsapiticket.service;

import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.jsapiticket.entity.JsApiTicket;

/**
 * 
 * jsapi_ticket相关的服务接口
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public interface JsApiTicketService {
	/**
	 * 获取jsapi_ticket，包括创建时间
	 * @return
	 */
	public JsApiTicket getJsApiTicket() throws WechatException;
	
	/**
	 * 判断jsapi_ticket是否过期
	 * @return
	 */
	public boolean isJsApiTicketExpire(JsApiTicket jsApiTicket);
}
