package com.blocks.websocket.services;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
* Spring 工具类
*
*/
@Component
public class SpringUtil  implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	public SpringUtil() {}

	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext=context;
	}
	/**
	 * 根据beanName 获取 spring bean
	 * @param beanName
	 * @return Object
	 */
	public static  Object getBean(String beanName){
		if(beanName==null)return null;
	    return 	applicationContext.getBean(beanName);
	}
	/**
	 * 根据bean type 获取springBean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBeanByType(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
	
	public static ServletContext getServletContext(){
		WebApplicationContext wac = (WebApplicationContext)applicationContext;
		return wac.getServletContext();
	}
	
	/**
	 * 获取 Spring applicationContext
	 * @return
	 */
	public static ApplicationContext getContext() {
		return applicationContext;
	}

}
