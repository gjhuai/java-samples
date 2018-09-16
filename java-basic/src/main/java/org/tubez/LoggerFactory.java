package org.tubez;

public abstract class LoggerFactory {
	public static Logger getLogger(String name){
		return () -> java.util.logging.Logger.getLogger(name);
	}
	
	public static Logger getLogger(Class<?> clazz){
		return getLogger(clazz.getSimpleName());
	}
}
