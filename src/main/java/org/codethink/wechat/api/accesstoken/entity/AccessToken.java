package org.codethink.wechat.api.accesstoken.entity;

/**
 * 公众号的全局唯一票据access_token实体类
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class AccessToken extends AccessTokenParam {

	private long accessTokenTime; // access_token生成时间

	public AccessToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessToken(AccessTokenParam accessTokenParam, long accessTokenTime) {
		super.setAccess_token(accessTokenParam.getAccess_token());
		super.setExpires_in(accessTokenParam.getExpires_in());
		this.accessTokenTime = accessTokenTime;
	}

	public long getAccessTokenTime() {
		return accessTokenTime;
	}

	public void setAccessTokenTime(long accessTokenTime) {
		this.accessTokenTime = accessTokenTime;
	}
}
