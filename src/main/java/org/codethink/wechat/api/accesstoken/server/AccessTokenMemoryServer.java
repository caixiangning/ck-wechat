package org.codethink.wechat.api.accesstoken.server;

import org.codethink.wechat.api.accesstoken.entity.AccessToken;
import org.codethink.wechat.api.accesstoken.service.AccessTokenService;
import org.codethink.wechat.api.accesstoken.service.AccessTokenServiceImpl;
import org.codethink.wechat.api.exception.WechatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 *
 * 获取access_token的内存中控服务器(使用内存来存储access_token的中控服务器实现)
 * @author CaiXiangning
 * @date 2016年10月13日
 * @email caixiangning@gmail.com
 */
public class AccessTokenMemoryServer implements AccessTokenServer{

	private static Logger logger = LoggerFactory.getLogger(AccessTokenMemoryServer.class);
	
	// 公众号的全局唯一票据access_token实体类
	private AccessToken accessToken;
	
	// access_token相关的服务接口
	private AccessTokenService accessTokenService = new AccessTokenServiceImpl();
	
	private int requestTimes = 1;//请求access_token失败后重新请求的次数
	
	// 饿汉式单例类实现,饿汉式提前实例化，没有懒汉式中多线程问题，但不管我们是不是调用getInstance()都会存在一个实例在内存中
	// http://blog.csdn.net/yuxin6866/article/details/52203074
	private static AccessTokenMemoryServer accessTokenMemoryServer = new AccessTokenMemoryServer();
	
	// 虽然构造函数是私有的，但是类在初始化的时候仍然调用
	private AccessTokenMemoryServer() {
		// TODO Auto-generated constructor stub
		try{
			// 在构造函数中请求获取access_token保证了JVM在启动时实例化内存中控服务器的同时也初始化了AccessToken对象
			refreshAccessToken();
		}
		catch(WechatException we){
			logger.error("内存中控服务器获取access_token失败");
		}
	}
	
	public static AccessTokenMemoryServer instance(){
		return accessTokenMemoryServer;
	}
	
	/**
	 * 重新请求access_token，如果失败则按指定请求失败后重新请求的次数来多次请求，
	 * 只要在请求次数范围内请求成功则中断请求获取access_token
	 * @return
	 * @throws WechatException
	 */
	private AccessToken refreshAccessToken() throws WechatException{
		for(int i=0;i<requestTimes;i++){
			this.accessToken = this.accessTokenService.getAccessToken();
			//请求成功则退出
			if(accessToken != null){
				break;
			}
		}
		return accessToken;
	}
	
	/**
	 * 使用中控服务器获取有效的access_token，过期自动申请新的access_token
	 * @return
	 * @throws WechatException
	 */
	@Override
	public String getAccessToken() throws WechatException {
		// TODO Auto-generated method stub
		// 在构造AccessTokenMemoryServer对象后已经初始化了AccessToken对象，所以这一步基本上不会调用到
		if(this.accessToken == null){
			this.refreshAccessToken();
		}
		// 如果access_token超期则重新申请access_token
		if(!this.accessTokenService.isAccessTokenExpire(this.accessToken)){
			this.refreshAccessToken();
		}
		return this.accessToken.getAccess_token();
	}

}
