package org.codethink.wechat.api.access.entity;
/**
 * 
 * 微信接入认证参数枚举类
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public enum WechatParamEnum {
	
	SIGNATURE("signature"), // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	TIMESTAMP("timestamp"), // 时间戳
	NONCE("nonce"), // 随机数
	ECHOSTR("echostr"); // 随机字符串
	
	private final String value;
	
	private WechatParamEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
