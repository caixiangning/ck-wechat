package org.codethink.wechat.api.jsapiticket.server;

import org.codethink.wechat.api.exception.WechatException;
import org.codethink.wechat.api.jsapiticket.entity.JsApiTicket;
import org.codethink.wechat.api.jsapiticket.service.JsApiTicketService;
import org.codethink.wechat.api.jsapiticket.service.JsApiTicketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 获取jsapi_ticket的内存中控服务器(使用内存来存储jsapi_ticket的中控服务器实现)
 * 
 * @author CaiXiangNing
 * @date 2016年10月17日
 * @email caixiangning@gmail.com
 */
public class JsApiTicketMemoryServer implements JsApiTicketServer{
	
	private static Logger logger = LoggerFactory.getLogger(JsApiTicketMemoryServer.class);
	
	// 公众号用于调用微信JS接口的临时票据jsapi_ticket实体类
	private JsApiTicket jsApiTicket;
	
	// jsapi_ticket相关的服务接口
	private JsApiTicketService jsApiTicketService = new JsApiTicketServiceImpl(); 
	
	private int requestTimes = 1; // 请求jsapi_ticket失败后重新请求的次数
	
	// 恶汉式单例类实现,饿汉式提前实例化，没有懒汉式中多线程问题，但不管我们是不是调用getInstance()都会存在一个实例在内存中
	// http://blog.csdn.net/yuxin6866/article/details/52203074
	private static JsApiTicketMemoryServer jsApiTicketMemoryServer = new JsApiTicketMemoryServer();
	
	//虽然构造函数是私有的，但是类在初始化的时候仍然调用
	private JsApiTicketMemoryServer() {
		// TODO Auto-generated constructor stub
		try{
			// 在构造函数中请求获取jsapi_ticket保证了JVM在启动时实例化内存中控服务器的同时也初始化了JsApiTicket对象
			refreshJsApiTicket();
		}
		catch(WechatException we){
			logger.error("内存中控服务器获取jsapi_ticket失败");
		}
	}

	public static JsApiTicketMemoryServer instance(){
		return jsApiTicketMemoryServer;
	}

	/**
	 * 重新请求jsapi_ticket，如果失败则按指定请求失败后重新请求的次数来多次请求，
	 * 只要在请求次数范围内请求成功则中断请求获取jsapi_ticket
	 * @return
	 * @throws WechatException
	 */
	private JsApiTicket refreshJsApiTicket() throws WechatException{
		for(int i=0;i<requestTimes;i++){
			this.jsApiTicket = this.jsApiTicketService.getJsApiTicket();
			//请求成功则退出
			if(this.jsApiTicket != null){
				break;
			}
		}
		return jsApiTicket;
	}

	/**
	 * 使用中控服务器获取有效的jsapi_ticket，过期自动申请新的jsapi_ticket
	 * @return
	 * @throws WechatException
	 */
	@Override
	public String getJsApiTicket() throws WechatException {
		// TODO Auto-generated method stub
		// 在构造JsApiTicketMemoryServer对象后已经初始化了JsApiTicket对象，所以这一步基本上不会调用到
		if(this.jsApiTicket == null){
			this.refreshJsApiTicket();
		}
		
		// 如果jsapi_ticket超期则重新申请jsapi_ticket
		if(!this.jsApiTicketService.isJsApiTicketExpire(this.jsApiTicket)){
			this.refreshJsApiTicket();
		}
		
		return this.jsApiTicket.getTicket();
	}
}
