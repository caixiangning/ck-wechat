package org.codethink.wechat.api.util;

import org.codethink.wechat.api.exception.WechatException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 微信相关工具类
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class WechatUtil {
	/**
	 * 检查响应结果是否正确，正确：返回原有响应结果字符串，错误：抛出异常
	 * @param responseResult 微信服务器响应结果
	 * @return
	 * @throws WechatException
	 */
	public static String checkResponseResult(String responseResult) throws WechatException {
		JSONObject exception = JSON.parseObject(responseResult);
		String errcode = exception.getString("errcode");
		String errmsg = exception.getString("errmsg");
		if (errmsg != null && !"ok".equals(errmsg)) {
			throw new WechatException(errcode, errmsg);
		}
		return responseResult;
	}
}
