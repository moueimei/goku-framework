package com.gkframework.boot.jmx;


import com.gkframework.boot.core.Container;
import com.gkframework.boot.SpringContainer;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author moueimei
 */
public class SpringContainerManager implements SpringContainerManagerMBean {
	
	Container springContainer = new SpringContainer();

	@Override
	public String getName() {
		return springContainer.getName();
	}

	@Override
	public void restart() {
		springContainer.restart();
	}
	
	@Override
	public void start() {
		springContainer.start();
	}

	@Override
	public void stop() {
		springContainer.stop();
		
	}

	@Override
	public boolean isRunning() {
		return springContainer.isRunning();
	}
   
}