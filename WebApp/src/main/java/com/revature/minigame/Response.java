package com.revature.minigame;

import java.util.LinkedList;
import java.util.List;

public class Response {
	
	private String encounter;
	private final List<String> actions;
	
	Response() {
		this.encounter = "An error occured";
		this.actions = new LinkedList<String>();
	}

	public void setEncounter(final String encounter) {
		this.encounter = encounter;
	}

	public String getEncounter() {
		return this.encounter;
	}

	public List<String> getActions() {
		return this.actions;
	}

	public void addAction(final String action) {
		this.actions.add(action);
	}
	
	
}
