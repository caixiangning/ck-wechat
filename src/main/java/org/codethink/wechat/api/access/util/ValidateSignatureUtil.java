package org.codethink.wechat.api.access.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * 签名验证工具类/验证服务器地址有效性工具类
 * 
 * @refer http://mp.weixin.qq.com/wiki/8/f9a0b8382e0b77d87b3bcc1ce6fbc104.html
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class ValidateSignatureUtil {
	
	private String signature; // 微信加密签名
	private String timestamp; // timestamp
	private String nonce; // 随机数
	private String token; // 用作生成签名的token，用户自定义
	
	public ValidateSignatureUtil(String signature, String timestamp, String nonce, String token) {
		super();
		this.signature = signature;
		this.timestamp = timestamp;
		this.nonce = nonce;
		this.token = token;
	}
	
	/**
	 * 签名加密/校验流程
	 * @return true 验证通过，false 验证失败
	 */
	public boolean checkSignature(){
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		String[] sa = {this.token,this.timestamp, this.nonce};
		Arrays.sort(sa);
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String sortStr = sa[0] + sa[1] + sa[2];
		String sha1 = DigestUtils.sha1Hex(sortStr);
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		return sha1.equals(this.signature);
	}
	
}
