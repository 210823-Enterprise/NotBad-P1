package com.revature.diyORM;

import java.sql.Connection;

import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectRemover;

public class DIYORM {
	
	private final static DIYORM diyorm = new DIYORM();
	private final Connection conn;
	private final ObjectRemover obj_remover;
	
	private DIYORM() {
		this.conn = ConnectionFactory.getInstance().getConnection();
		this.obj_remover = ObjectRemover.getInstance();
	}
	
	public static DIYORM getInstance() {
		return diyorm;
	}
	
	
	
	public boolean deleteObjFromDB(final Object obj) {
		return this.obj_remover.removeObjectFromDb(obj, conn);
	}

}
