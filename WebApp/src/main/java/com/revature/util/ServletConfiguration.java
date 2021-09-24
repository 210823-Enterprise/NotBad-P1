package com.revature.util;

import com.revature.orm.Configuration;

public class ServletConfiguration {

	static private final Configuration config = Configuration.getInstance();
	
	static {
		config.addAnnotatedClass(Character.class);

	}
	
	
}
