package com.joken.ice.utils;

import java.io.Serializable;
import java.util.Date;

class ExampleBean implements Serializable {
	private static final long serialVersionUID = -8121418052209958014L;

	private int id;
	private Date createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}