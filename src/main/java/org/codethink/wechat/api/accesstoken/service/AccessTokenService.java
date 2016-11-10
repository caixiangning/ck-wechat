package org.codethink.wechat.api.accesstoken.service;

import org.codethink.wechat.api.accesstoken.entity.AccessToken;
import org.codethink.wechat.api.exception.WechatException;

/**
 * 
 * access_token相关的服务接口
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public interface AccessTokenService {
	/**
	 * 获取AccessToken对象(包含创建时间)，每次都是申请新的access_token
	 * @return
	 */
	public AccessToken getAccessToken() throws WechatException;
	
	/**
	 * 判断access_token是否过期
	 * @return
	 */
	public boolean isAccessTokenExpire(AccessToken accessToken);
}
