package model;

import java.io.Serializable;

public class SimpleProject implements Serializable {
	private static final long serialVersionUID = -2398503995120490647L;
	String name;
	String id;

	public SimpleProject(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}
}
