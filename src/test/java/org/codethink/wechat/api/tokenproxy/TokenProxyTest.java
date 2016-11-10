package org.codethink.wechat.api.tokenproxy;

import org.codethink.wechat.api.exception.WechatException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 获取access_token和jsapi_ticket的代理的测试类
 * 
 * @author CaiXiangNing
 * @date 2016年11月7日
 * @email caixiangning@gmail.com
 */
public class TokenProxyTest {

	private static final Logger logger = LoggerFactory.getLogger(TokenProxyTest.class);
	
	@Test
	public void testGetAccessToken() throws WechatException {
		logger.info("获取公众号的全局唯一票据access_token的值是：{}", TokenProxy.getAccessToken());
	}

	@Test
	public void testGetJsApiTicket() throws WechatException {
		logger.info("公众号用于调用微信JS接口的临时票据ticket的值是：{}", TokenProxy.getJsApiTicket());
	}
}
