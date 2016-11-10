package org.codethink.wechat.api.oauth2.entity;

import org.codethink.wechat.api.util.GlobalPropertiesUtil;

/**
 * 
 * 第一步：用户同意授权，获取code的请求参数实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class RedirectRequestParam {
	// 	公众号的唯一标识
	private String appid = GlobalPropertiesUtil.getProperty("appid");
	
	// 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	private String redirect_uri;
	
	// 返回类型，请填写code
	private String response_type = "code";
	
	// 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。
	// 并且，即使在未关注的情况下，只要用户授权，也能获取其信息） 
	private String scope;
	// 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	private String state;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
