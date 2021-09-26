package com.revature.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletInitilizer implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		try {
			ServletConfiguration.setUp();
		} catch(final Exception e) {
			
		}
	}

}
