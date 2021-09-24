package com.revature.util;

import com.revature.orm.Configuration;

public class ServletConfiguration {

	private static final Configuration config = Configuration.getInstance();
	
	public static void addClass(final Class<?> clazz) {
		config.addAnnotatedClass(clazz);
	}
	
}
