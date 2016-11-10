package org.codethink.wechat.api.jsapiticket.entity;

/**
 * 
 * 公众号用于调用微信JS接口的临时票据jsapi_ticket实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class JsApiTicket extends JsApiTicketParam {

	private long jsApiTicketTime; // jsapi_ticket生成时间

	public JsApiTicket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JsApiTicket(JsApiTicketParam jsApiTicketParam, long jsApiTicketTime) {
		super.setErrcode(jsApiTicketParam.getErrcode());
		super.setErrmsg(jsApiTicketParam.getErrmsg());
		super.setTicket(jsApiTicketParam.getTicket());
		super.setExpires_in(jsApiTicketParam.getExpires_in());
		this.jsApiTicketTime = jsApiTicketTime;
	}

	public long getJsApiTicketTime() {
		return jsApiTicketTime;
	}

	public void setJsApiTicketTime(long jsApiTicketTime) {
		this.jsApiTicketTime = jsApiTicketTime;
	}
}
