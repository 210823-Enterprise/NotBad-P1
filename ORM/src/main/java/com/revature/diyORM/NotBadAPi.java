package com.revature.diyORM;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdater;

public class NotBadAPi {
	
	final private static NotBadAPi api = new NotBadAPi();
	
	private NotBadAPi() {}
	
	public static NotBadAPi getInstance() {
		return api;
	}
	
	/* *
	// The basic CRUD operations
	 * */
	// are we to add connections here?
	
	public boolean addObjectToDB(Object obj) {
		ObjectSaver obj_saver = new ObjectSaver();
		obj_saver.addObjectToDb(obj);
		return true;
	}
	
	public List<Object> getObjectsFromDB(Object obj, String params) {
		ObjectGetter obj_getter = new ObjectGetter();
		List<Object> objs = obj_getter.getObjectFromDb(obj);
		return objs;
	}
	
	public boolean updateObjectInDB(Object obj, String params) {
		ObjectUpdater obj_updater = new ObjectUpdater();
		obj_updater.updateObjectInDb(obj);
		return true;
	}
	
	public boolean deleteObjFromDB(Object obj) {
		ObjectRemover obj_remover = new ObjectRemover();
		obj_remover.removeObjectFromDb(obj);
		return true;
	}

}