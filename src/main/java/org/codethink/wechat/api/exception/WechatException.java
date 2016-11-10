package org.codethink.wechat.api.exception;

/**
 * 公众号接口调用异常类 
 * 公众号每次调用接口时，可能获得正确或错误的返回码，开发者可以根据返回码信息调试接口，排查错误。
 * 
 * @author CaiXiangNing
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class WechatException extends Exception {

	private static final long serialVersionUID = -5841521849298256803L;

	private String errcode;
	private String errmsg;

	public WechatException() {
		super();
	}

	public WechatException(String errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
