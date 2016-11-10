package org.codethink.wechat.api.accesstoken.server;

import org.codethink.wechat.api.exception.WechatException;

/** 
 *
 * 获取access_token的中控服务器接口，可定义不同的服务器实现
 * @author CaiXiangning
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public interface AccessTokenServer {
	/**
	 * 使用中控服务器获取有效的access_token，过期自动申请新的access_token
	 * @return
	 * @throws WechatException
	 */
	public String getAccessToken() throws WechatException;
}
