package net.enilink.docs.ant.model;

import java.util.ArrayList;
import java.util.List;

public class Node {

	public List<Node> children = new ArrayList<>();

	public List<Entry> entries = new ArrayList<>();

	public String title;

	public Node(String title) {
		this.title = title;
	}

	public void add(Node node) {
		children.add(node);
	}

	@Override
	public String toString() {
		return title;
	}

}
