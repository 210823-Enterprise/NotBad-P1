package com.revature.diyORM;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;

public class NotBadAPi {
	
	final private static NotBadAPi api = new NotBadAPi();
	
	private NotBadAPi() {}
	
	public static NotBadAPi getInstance() {
		return api;
	}
	/* *
	// The basic CRUD operations
	 * */
	public boolean addObjectToDB(Object obj) {
		ObjectSaver obj_saver = new ObjectSaver();
		obj_saver.addObject(obj);
		return true;
	}
	
	public Optional<List<Object>> getObjectsFromDB(Class<?> clazz, String params) {
		ObjectGetter obj_getter = new ObjectGetter();
		List<Object> objs = obj_getter.getObjects(obj);
		return objs;
	}
	
	public boolean updateObjectInDB(Object obj, String params) {
		ObjectSaver obj_saver = new ObjectSaver();
		obj_saver.updateObject(obj);
		return true;
	}
	
	public boolean deleteObjFromDB(Object obj) {
		ObjectRemover obj_remover = new ObjectRemover();
		obj_remover.removeObjectFromDb(obj);
		return true;
	}

}
