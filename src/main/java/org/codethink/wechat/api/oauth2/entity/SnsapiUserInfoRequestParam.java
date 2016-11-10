package org.codethink.wechat.api.oauth2.entity;

/**
 * 
 * 第四步：拉取用户信息(需scope为 snsapi_userinfo)的请求参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class SnsapiUserInfoRequestParam {
	
	// 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	private String access_token;
	
	// 用户的唯一标识
	private String openid;
	
	// 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	private String lang;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
