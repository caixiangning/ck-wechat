package org.codethink.wechat.api.jssdk.entity;

import org.codethink.wechat.api.util.GlobalPropertiesUtil;

/**
 * 
 * 注入到页面的权限签名相关参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class JsSdkSignParam {
	private String appid = GlobalPropertiesUtil.getProperty("appid");
	
	// 当前网页的URL，不包含#及其后面部分
	private String url;
	
	// 公众号用于调用微信JS接口的临时票据jsapi_ticket
	private String jsapiTicket;
	
	// 随机字符串
	private String nonceStr;
	
	// 时间戳
	private String timeStamp;
	
	// 签名后生成的签名字符串
	private String signature;

	public JsSdkSignParam(String url, String jsapiTicket, String nonceStr, String timeStamp, String signature) {
		this.url = url;
		this.jsapiTicket = jsapiTicket;
		this.nonceStr = nonceStr;
		this.timeStamp = timeStamp;
		this.signature = signature;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "JsSdkSignParam [appid=" + appid + ", url=" + url + ", jsapiTicket=" + jsapiTicket + ", nonceStr=" + nonceStr + ", timeStamp=" + timeStamp
				+ ", signature=" + signature + "]";
	}
}
