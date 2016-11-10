package org.codethink.wechat.api.accesstoken.service;

import java.util.Date;

import org.codethink.wechat.api.accesstoken.entity.AccessToken;
import org.codethink.wechat.api.accesstoken.entity.AccessTokenParam;
import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.util.GlobalPropertiesUtil;
import org.codethink.wechat.api.util.HttpUtil;
import org.codethink.wechat.api.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * access_token相关服务接口的实现类
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class AccessTokenServiceImpl implements AccessTokenService {

	private static Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

	private int redundanceTime = 10; // 冗余时间，提前10秒就去请求新的token

	// 获取access_token的url
	// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	private static final String HTTPS_OPEN_WEIXIN_QQ_COM_CGIBIN_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

	// 拼接获取access_token的url
	private static String generateAccessTokenUrl() {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_OPEN_WEIXIN_QQ_COM_CGIBIN_TOKEN);
		url.append("?grant_type=").append("client_credential");
		url.append("&appid=").append(GlobalPropertiesUtil.getProperty("appid"));
		url.append("&secret=").append(GlobalPropertiesUtil.getProperty("appsecret"));
		return url.toString();
	}

	/**
	 * 获取AccessToken对象(包含创建时间)，每次都是申请新的access_token
	 * @return
	 */
	@Override
	public AccessToken getAccessToken() throws WechatException {
		// TODO Auto-generated method stub
		String requestUrl = generateAccessTokenUrl();
		logger.info("获取access_token的url：{}", requestUrl);
		String responseResult = HttpUtil.doGet(requestUrl);
		// 检查响应结果是否正确，不正确则抛出异常
		WechatUtil.checkResponseResult(responseResult);
		AccessTokenParam accessTokenParam = JSONObject.parseObject(responseResult, AccessTokenParam.class);
		// access_token产生时间
		long accessTokenTime = new Date().getTime();
		AccessToken accessToken = new AccessToken(accessTokenParam, accessTokenTime);
		return accessToken;
	}

	/**
	 * 判断access_token是否过期
	 * @return
	 */
	@Override
	public boolean isAccessTokenExpire(AccessToken accessToken) {
		// TODO Auto-generated method stub
		// access_token创建时间 单位：秒
		long accessTokenTime = accessToken.getAccessTokenTime();
		long currentTime = new Date().getTime();
		// access_token超期时间 单位：秒
		long expiresInTime = accessToken.getExpires_in();
		if (currentTime - accessTokenTime > (expiresInTime - redundanceTime) * 1000) {
			return false;
		}
		return true;
	}
}
