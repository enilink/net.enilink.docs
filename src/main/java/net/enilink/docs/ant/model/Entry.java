package net.enilink.docs.ant.model;

public class Entry {
	public final String title;
	public final String path;
	
	public Entry(String title, String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		this.title = title;
		this.path = path;
	}

}
