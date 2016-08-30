package org.tubez;

public interface Logger {
	java.util.logging.Logger getRealLogger(); 
	
	default void error(Object... objs){
		debug(objects2Str(objs), new Object[]{});
	}
	default void error(Object obj){
		error(obj.toString(), new Object[]{});
	}
	default void error(String msgFormat, Object... args){
		if (args==null || args.length==0){
			getRealLogger().severe(msgFormat);
		} else {
			getRealLogger().severe(String.format(msgFormat,args));
		}
	}
	
	default void warn(Object... objs){
		debug(objects2Str(objs), new Object[]{});
	}
	default void warn(Object obj){
		warn(obj.toString(), new Object[]{});
	}
	default void warn(String msgFormat, Object... args){
		if (args==null){
			getRealLogger().warning(msgFormat);
		} else {
			getRealLogger().warning(String.format(msgFormat,args));
		}
	}
	
	default void info(Object... objs){
		debug(objects2Str(objs), new Object[]{});
	}
	default void info(Object obj){
		info(obj.toString(), new Object[]{});
	}
	default void info(String msgFormat, Object... args){
		if (args==null){
			getRealLogger().info(msgFormat);
		} else {
			getRealLogger().info(String.format(msgFormat,args));
		}
	}
	
	default void debug(Object... objs){
		debug(objects2Str(objs), new Object[]{});
	}
	default void debug(Object obj){
		debug(obj.toString(), new Object[]{});
	}
	default void debug(String msgFormat, Object... args){
		if (args==null){
			getRealLogger().fine(msgFormat);
		} else {
			getRealLogger().fine(String.format(msgFormat,args));
		}
	}
	
	default String objects2Str(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs){
			sb.append(obj.toString()).append(',').append(' ');
		}
		String msg = sb.substring(0, sb.length()-2);
		return msg;
	}
}
