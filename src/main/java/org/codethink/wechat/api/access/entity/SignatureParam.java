package org.codethink.wechat.api.access.entity;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 微信接入认证参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class SignatureParam {
	
	private String signature; // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	private String timestamp; // 时间戳
	private String nonce; // 随机数
	private String echostr; // 随机字符串

	// 从微信服务器请求中获取这四个请求参数并构造SignatureParam对象
	public SignatureParam(HttpServletRequest httpServletRequest) {
		signature = httpServletRequest.getParameter(WechatParamEnum.SIGNATURE.getValue());
		timestamp = httpServletRequest.getParameter(WechatParamEnum.TIMESTAMP.getValue());
		nonce = httpServletRequest.getParameter(WechatParamEnum.NONCE.getValue());
		echostr = httpServletRequest.getParameter(WechatParamEnum.ECHOSTR.getValue());
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
}
