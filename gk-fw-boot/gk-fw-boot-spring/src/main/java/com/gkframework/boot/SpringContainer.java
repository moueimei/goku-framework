package com.gkframework.boot;


import com.gkframework.boot.core.Container;
import com.gkframework.boot.utils.SystemPropertiesUtils;
import org.apache.log4j.Logger;

/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 * 
 * @author moueimei
 */
public class SpringContainer implements Container {

	private static Logger logger = Logger.getLogger(SpringContainer.class);
    
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:config/*.xml";

    static ClassPathXmlApplicationContext context;
    
	private static long startTime = System.currentTimeMillis();
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
        String configPath = DEFAULT_SPRING_CONFIG;
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
        
        String applicationName = SystemPropertiesUtils.getApplicationName();
        if(null != applicationName) {
        	context.setDisplayName(applicationName);
        }
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

	@Override
	public void restart() {
		try {
			if (context != null) {
                context.stop();
                context.start();
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
	}

	@Override
	public boolean isRunning() {
		if (context != null) {
    		return context.isRunning();
    	}
    	else {
    		return false;
    	}
	}
	
	@Override
	public String getName() {
    	return SystemPropertiesUtils.getApplicationName();
	}
    
	@Override
	public String getType() {
		return "SpringContainer";
	}

	@Override
	public long getStartTime() {
		return startTime;
	}
}