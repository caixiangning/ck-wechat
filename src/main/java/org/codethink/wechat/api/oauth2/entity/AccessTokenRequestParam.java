package org.codethink.wechat.api.oauth2.entity;

import org.codethink.wechat.api.util.GlobalPropertiesUtil;

/**
 * 
 * 第二步：通过code换取网页授权access_token的请求参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class AccessTokenRequestParam {
	
	// 	公众号的唯一标识
	private String appid = GlobalPropertiesUtil.getProperty("appid");
	
	// 公众号的appsecret
	private String secret = GlobalPropertiesUtil.getProperty("appsecret");
	
	// 填写第一步获取的code参数
	private String code;
	
	// 填写为authorization_code
	private String grant_type = "authorization_code";
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
}
