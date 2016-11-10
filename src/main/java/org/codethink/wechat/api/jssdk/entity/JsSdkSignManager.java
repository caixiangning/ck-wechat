package org.codethink.wechat.api.jssdk.entity;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.proxy.TokenProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 调用jssdk需要进行的签名算法相关接口
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 * @refer http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.8E.A5.E5.8F.A3.E8.B0.83.E7.94.A8.E8.AF.B4.E6.98.8E
 */
public class JsSdkSignManager {
	private static Logger logger = LoggerFactory.getLogger(JsSdkSignManager.class);

	private static JsSdkSignParam signature(String url) throws WechatException {
		StringBuffer signatureSource = new StringBuffer();
		String jsapiTicket = TokenProxy.getJsApiTicket();
		String nonceStr = "223rfasdfsddfawer";
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		// 参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
		// 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串。这里需要注意的是所有参数名均为小写字符。
		signatureSource.append("jsapi_ticket=").append(jsapiTicket);
		signatureSource.append("&noncestr=").append(nonceStr);
		signatureSource.append("&timestamp=").append(timestamp);
		signatureSource.append("&url=").append(url);
		logger.info("sign source : " + signatureSource);
		
		// 对参数字典序后拼接的字符串作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
		String signature = DigestUtils.sha1Hex(signatureSource.toString());
		logger.info("sign dest : " + signature);
		
		return new JsSdkSignParam(url, jsapiTicket, nonceStr, timestamp, signature);
	}

	/**
	 * 对需要调用调用jssdk请求的Url进行签名并将相关参数注入到HttpServletRequest中
	 * @param httpServletRequest
	 * @throws WechatException
	 */
	public static void executeUrlSignature(HttpServletRequest httpServletRequest) throws WechatException {
		// 获取请求的完整URL
		StringBuffer urlBuffer = httpServletRequest.getRequestURL();
		if (httpServletRequest.getQueryString() != null) {
			urlBuffer.append("?");
			urlBuffer.append(httpServletRequest.getQueryString());
		}
		String requestUrl = urlBuffer.toString();
		logger.info("request url:{}", requestUrl);
		JsSdkSignParam jsSdkSignParam = signature(requestUrl);
		logger.info("jsSdkSignParam : {}", jsSdkSignParam.toString());
		httpServletRequest.setAttribute("jsSdkSignParam", jsSdkSignParam);
	}
}
