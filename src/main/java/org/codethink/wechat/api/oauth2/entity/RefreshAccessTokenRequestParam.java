package org.codethink.wechat.api.oauth2.entity;

import org.codethink.wechat.api.util.GlobalPropertiesUtil;

/**
 * 
 * 第三步：刷新access_token（如果需要）请求参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class RefreshAccessTokenRequestParam {
	
	// 公众号的唯一标识
	private String appid = GlobalPropertiesUtil.getProperty("appid");
	
	// 填写为refresh_token
	private String grant_type = "refresh_token";
	
	// 填写通过access_token获取到的refresh_token参数
	private String refresh_token;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
