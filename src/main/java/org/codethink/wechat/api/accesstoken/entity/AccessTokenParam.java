package org.codethink.wechat.api.accesstoken.entity;

/**
 * 
 * 公众号的全局唯一票据access_token参数实体类(公众号调用各接口时都需使用access_token)
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class AccessTokenParam {
	
	private String access_token; // 获取到的凭证

	private long expires_in; // 凭证有效时间，单位：秒

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
}
