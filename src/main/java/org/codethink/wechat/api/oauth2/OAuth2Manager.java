package org.codethink.wechat.api.oauth2;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.oauth2.entity.AccessTokenRequestParam;
import org.codethink.wechat.api.oauth2.entity.AccessTokenResponseParam;
import org.codethink.wechat.api.oauth2.entity.RedirectRequestParam;
import org.codethink.wechat.api.oauth2.entity.RefreshAccessTokenRequestParam;
import org.codethink.wechat.api.oauth2.entity.RefreshAccessTokenResponseParam;
import org.codethink.wechat.api.oauth2.entity.SnsapiUserInfoRequestParam;
import org.codethink.wechat.api.oauth2.entity.SnsapiUserInfoResponseParam;
import org.codethink.wechat.api.util.HttpUtil;
import org.codethink.wechat.api.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 网页授权获取用户基本信息的相关接口
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 * @refer http://mp.weixin.qq.com/wiki/4/9ac2e7b1f1d22e9e57260f6553822520.html
 */
public class OAuth2Manager {
	
	/* 生成OAuth重定向URI（用户同意授权，获取code） */
	private static final String HTTPS_OPEN_WEIXIN_QQ_COM_CONNECT_OAUTH2_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/* 通过code换取网页授权access_token */
	private static final String HTTPS_API_WEIXIN_QQ_COM_SNS_OAUTH2_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/* 刷新access_token（如果需要） */
	private static final String HTTPS_API_WEIXIN_QQ_COM_SNS_OAUTH2_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	/* 拉取用户信息(需scope为 snsapi_userinfo) */
	private static final String HTTPS_API_WEIXIN_QQ_COM_SNS_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	/* 检验授权凭证（access_token）是否有效 */
	private static final String HTTPS_API_WEIXIN_QQ_COM_SNS_AUTH = "https://api.weixin.qq.com/sns/auth";

	private static Logger logger = LoggerFactory.getLogger(OAuth2Manager.class);

	
	// 第一步：用户同意授权，获取code的url
	// https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri =REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	private static String generateRedirectUrl(RedirectRequestParam redirectRequestParam) {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_OPEN_WEIXIN_QQ_COM_CONNECT_OAUTH2_AUTHORIZE);
		url.append("?appid=").append(redirectRequestParam.getAppid());
		url.append("&redirect_uri=").append(redirectRequestParam.getRedirect_uri());
		url.append("&response_type=").append(redirectRequestParam.getResponse_type());
		url.append("&scope=").append(redirectRequestParam.getScope());
		url.append("&state=").append(redirectRequestParam.getState());
		url.append("#wechat_redirect");
		return url.toString();
	}

	/**
	 * 第一步：用户同意授权，获取code
	 * 
	 * @param redirectRequestParam
	 * @param httpServletResponse
	 * @throws WechatException
	 * @throws IOException
	 */
	private static void sendRedirectRequest(RedirectRequestParam redirectRequestParam, HttpServletResponse httpServletResponse) throws WechatException, IOException {
		String requestUrl = generateRedirectUrl(redirectRequestParam);
		logger.info("重定向的url：{}", requestUrl);
		
		// 这里需要客户端重定向到该url，而不是由服务器向微信服务器发出请求
		httpServletResponse.sendRedirect(requestUrl);
	}

	// 第二步：通过code换取网页授权access_token的Url
	// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	private static String generateAccessTokenUrl(AccessTokenRequestParam accessTokenRequestParam) {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_API_WEIXIN_QQ_COM_SNS_OAUTH2_ACCESS_TOKEN);
		url.append("?appid=").append(accessTokenRequestParam.getAppid());
		url.append("&secret=").append(accessTokenRequestParam.getSecret());
		url.append("&code=").append(accessTokenRequestParam.getCode());
		url.append("&grant_type=").append(accessTokenRequestParam.getGrant_type());
		return url.toString();
	}
	
	/**
	 * 第二步：通过code换取网页授权access_token
	 * @param accessTokenRequestParam
	 * @return
	 * @throws WechatException
	 */
	private static AccessTokenResponseParam getAccessToken(AccessTokenRequestParam accessTokenRequestParam) throws WechatException {
		String requestUrl = generateAccessTokenUrl(accessTokenRequestParam);
		String responseResult = HttpUtil.doGet(requestUrl);
		WechatUtil.checkResponseResult(responseResult);
		return JSONObject.parseObject(responseResult, AccessTokenResponseParam.class);
	}

	
	// 第三步：刷新access_token（如果需要）的Url
	// https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	private static String generateRefreshAccessTokenUrl(RefreshAccessTokenRequestParam refreshAccessTokenRequestParam) {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_API_WEIXIN_QQ_COM_SNS_OAUTH2_REFRESH_TOKEN);
		url.append("?appid=").append(refreshAccessTokenRequestParam.getAppid());
		url.append("&grant_type=").append(refreshAccessTokenRequestParam.getGrant_type());
		url.append("&refresh_token=").append(refreshAccessTokenRequestParam.getRefresh_token());
		return url.toString();
	}

	/**
	 * 第三步：刷新access_token（如果需要）
	 * 
	 * @param redirectRequestParam
	 * @param httpServletResponse
	 * @throws WechatException
	 * @throws IOException
	 */
	public static RefreshAccessTokenResponseParam getRefreshAccessToken(RefreshAccessTokenRequestParam refreshAccessTokenRequestParam, HttpServletResponse httpServletResponse) throws WechatException,
			IOException {
		String requestUrl = generateRefreshAccessTokenUrl(refreshAccessTokenRequestParam);
		logger.info("重定向的url：{}", requestUrl);
		String responseResult = HttpUtil.doGet(requestUrl);
		// 检查响应结果是否正确，不正确则抛出异常
		WechatUtil.checkResponseResult(responseResult);
		return JSONObject.parseObject(responseResult, RefreshAccessTokenResponseParam.class);
	}



	// 第四步：拉取用户信息(需scope为 snsapi_userinfo)的Url
	// https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	private static String generateSnsapiUserInfoUrl(SnsapiUserInfoRequestParam snsapiUserInfoRequestParam) {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_API_WEIXIN_QQ_COM_SNS_USERINFO);
		url.append("?access_token=").append(snsapiUserInfoRequestParam.getAccess_token());
		url.append("&openid=").append(snsapiUserInfoRequestParam.getOpenid());
		url.append("&lang=").append(snsapiUserInfoRequestParam.getLang());
		return url.toString();
	}

	/**
	 * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	 * 
	 * @param snsapiUserInfoRequestParam
	 * @return
	 * @throws WechatException
	 */
	private static SnsapiUserInfoResponseParam getSnsapiUserInfo(SnsapiUserInfoRequestParam snsapiUserInfoRequestParam) throws WechatException {
		String requestUrl = generateSnsapiUserInfoUrl(snsapiUserInfoRequestParam);
		String responseResult = HttpUtil.doGet(requestUrl);
		WechatUtil.checkResponseResult(responseResult);
		return JSONObject.parseObject(responseResult, SnsapiUserInfoResponseParam.class);
	}

	/**
	 * 整合接口：网页授权的作用域为snsapi_base，重定向指定url(仅第一步)
	 * @param redirectUrl
	 * @param httpServletResponse
	 * @throws WechatException
	 * @throws IOException
	 */
	public static void redirectOnSnsapiBaseScope(String redirectUrl, HttpServletResponse httpServletResponse) throws WechatException, IOException {
		RedirectRequestParam redirectRequestParam = new RedirectRequestParam();
		redirectRequestParam.setScope("snsapi_base");  // 设定网页授权作用域为snsapi_base
		redirectRequestParam.setRedirect_uri(redirectUrl); // 设定重定向的Url
		OAuth2Manager.sendRedirectRequest(redirectRequestParam, httpServletResponse);
	}
	
	/**
	 * 整合接口：网页授权的作用域为snsapi_userinfo，重定向指定url(仅第一步)
	 * @param redirectUrl
	 * @param httpServletResponse
	 * @throws WechatException
	 * @throws IOException
	 */
	public static void redirectOnSnsapUserinfoScope(String redirectUrl, HttpServletResponse httpServletResponse) throws WechatException, IOException {
		RedirectRequestParam redirectRequestParam = new RedirectRequestParam();
		redirectRequestParam.setScope("snsapi_userinfo");  // 设定网页授权作用域为snsapi_userinfo
		redirectRequestParam.setRedirect_uri(redirectUrl); // 设定重定向的Url
		OAuth2Manager.sendRedirectRequest(redirectRequestParam, httpServletResponse);
	}
	
	/**
	 * 整合接口：网页授权的作用域为snsapi_base，仅可获取openId(仅第二步即结束)
	 * 
	 * @param accessTokenRequestParam
	 * @return
	 * @throws WechatException
	 */
	public static AccessTokenResponseParam getBySnsapiBaseScope(AccessTokenRequestParam accessTokenRequestParam) throws WechatException {
		return OAuth2Manager.getAccessToken(accessTokenRequestParam);
	}

	/**
	 * 整合接口：网页授权作用域为snsapi_userinfo，获取用户基本信息(第二步、第四步)
	 * 
	 * @param accessTokenRequestParam
	 * @return
	 * @throws WechatException
	 */
	public static SnsapiUserInfoResponseParam getBySnsapiUserInfoScope(AccessTokenRequestParam accessTokenRequestParam) throws WechatException {
		AccessTokenResponseParam accessTokenResponseParam = OAuth2Manager.getAccessToken(accessTokenRequestParam);
		SnsapiUserInfoRequestParam snsapiUserInfoRequestParam = new SnsapiUserInfoRequestParam();
		snsapiUserInfoRequestParam.setAccess_token(accessTokenResponseParam.getAccess_token());
		snsapiUserInfoRequestParam.setOpenid(accessTokenResponseParam.getOpenid());
		return OAuth2Manager.getSnsapiUserInfo(snsapiUserInfoRequestParam);
	}
  
	// 拼接检验授权凭证（access_token）是否有效的Url
	// https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
	private static String generateCheckValidAccessTokenUrl(AccessTokenResponseParam accessTokenResponseParam) {
		StringBuffer url = new StringBuffer();
		url.append(HTTPS_API_WEIXIN_QQ_COM_SNS_AUTH);
		url.append("?access_token=").append(accessTokenResponseParam.getAccess_token());
		url.append("&openid=").append(accessTokenResponseParam.getOpenid());
		return url.toString();
	}

	/**
	 * 附：检验授权凭证（access_token）是否有效
	 * 
	 * @param accessTokenResponseParam
	 * @throws WechatException
	 */
	public static boolean checkValidAccessToken(AccessTokenResponseParam accessTokenResponseParam) throws WechatException {
		String requestUrl = generateCheckValidAccessTokenUrl(accessTokenResponseParam);
		String responseResult = HttpUtil.doGet(requestUrl);
		WechatUtil.checkResponseResult(responseResult);
		JSONObject exception = JSON.parseObject(responseResult);
		// String errcode = exception.getString("errcode");
		String errmsg = exception.getString("errmsg");
		if (errmsg != null && !"ok".equals(errmsg)) {
			return false;
		}
		return true;
	}
}
