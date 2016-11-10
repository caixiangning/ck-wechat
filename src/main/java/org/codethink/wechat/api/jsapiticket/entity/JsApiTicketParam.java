package org.codethink.wechat.api.jsapiticket.entity;
/**
 * 
 * 公众号用于调用微信JS接口的临时票据jsapi_ticket参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class JsApiTicketParam {
	private String errcode;

	private String errmsg;

	// JS-SDK权限验证的签名
	private String ticket;

	// jsapi_ticket的有效期7200秒
	private long expires_in;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
}
