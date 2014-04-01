package net.enilink.docs.ant.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Options extends Entry {
	public String contents;
	private Path destination;

	public Options(Path outputPath, String title, String path, String contents) {
		super(title, path);
		this.destination = outputPath;
		this.contents = contents;
	}

	public String getRootPath() {
		StringBuilder sb = new StringBuilder();
		Path parentPath1 = Paths.get(path).getParent();
		//dirty fix
		Path parentPath2 = Paths.get("/" + path).getParent();
		if (!parentPath1.equals(destination) && !parentPath2.equals(destination)) {
			Path relative = null;
			try {
				relative = destination.relativize(parentPath1);
			} catch (Exception ex) {
				relative = destination.relativize(parentPath2);
			}
			for (int i = 0; i < relative.getNameCount(); i++) {
				sb.append("../");
			}
		}
		return sb.toString();
	}

	public String getIndexPath() {
		if (!"index.html".equals(destination.relativize(Paths.get(path))
				.toString())) {
			return getRootPath() + "index.html";
		}
		return null;
	}
}