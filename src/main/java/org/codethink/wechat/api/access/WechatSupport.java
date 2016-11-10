package org.codethink.wechat.api.access;

import javax.servlet.http.HttpServletRequest;

import org.codethink.wechat.api.access.entity.SignatureParam;
import org.codethink.wechat.api.access.util.ValidateSignatureUtil;
import org.codethink.wechat.api.util.GlobalPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 执行签名验证/验证服务器地址有效性的类
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class WechatSupport {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private HttpServletRequest httpServletRequest;
	
	public WechatSupport(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}
	
	/**
	 * 执行签名校验/认证，验证服务器地址有效性
	 * @return
	 */
	public String execute(){
		logger.debug("WechatSupport run");
		// 从微信服务器请求中获取四个参数
		SignatureParam signatureParam = new SignatureParam(httpServletRequest);
		String signature =signatureParam.getSignature();
		String timestamp = signatureParam.getTimestamp();
		String nonce = signatureParam.getNonce();
		String echostr = signatureParam.getEchostr();
		String token = GlobalPropertiesUtil.getProperty("token");
		
		ValidateSignatureUtil validateSignatureUtil = new ValidateSignatureUtil(signature, timestamp, nonce, token);
		// 执行签名校验过程
		if(!validateSignatureUtil.checkSignature()){
			logger.debug("签名校验失败，接入失败");
			return "error";
		}
		logger.debug("签名校验成功，接入生效");
		return echostr;
	}
}
