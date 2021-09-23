package com.revature.diyORM;

import java.sql.Connection;

import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdater;

public class DIYORM {
	
	private final static DIYORM DIYORM = new DIYORM();
	
	private final Connection connection;
	private final ObjectGetter objectGetter;
	private final ObjectSaver objectSaver;
	private final ObjectUpdater objectUpdater;
	private final ObjectRemover objectRemover;
	
	private DIYORM() {
		this.connection = ConnectionFactory.getInstance().getConnection();
		this.objectGetter = ObjectGetter.getInstance();
		this.objectSaver = ObjectSaver.getInstance();
		this.objectUpdater = ObjectUpdater.getInstance();
		this.objectRemover = ObjectRemover.getInstance();
	}
	
	public static DIYORM getInstance() {
		return DIYORM;
	}
	
	public boolean deleteObjFromDB(final Object obj) {
		return this.objectRemover.removeObjectFromDb(obj, this.connection);
	}

}
