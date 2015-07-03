package com.gatemonitor.db.tabelas;

public abstract class RegistroAbstract {
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RegistroAbstract(int id) {
		this.id = id;
	}

}
