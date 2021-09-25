package com.revature.util;

import com.revature.models.CharacterModel;
import com.revature.models.CharacterStats;
import com.revature.orm.Configuration;

public class ServletConfiguration {

	private static final Configuration config = Configuration.getInstance();
	private static boolean setup = false;

	public static void setUp() {
		if(!setup) {
			setup = true;
			config.addAnnotatedClass(CharacterModel.class);
			config.addAnnotatedClass(CharacterStats.class);
			config.finalizeConfig();
		}
	}
	
}
