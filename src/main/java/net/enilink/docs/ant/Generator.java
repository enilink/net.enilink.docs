package net.enilink.docs.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.enilink.docs.ant.model.Node;
import net.enilink.docs.ant.model.Options;

import org.asciidoctor.DocumentHeader;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class Generator {

	private static final FileFilter DIRECTORIES = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	private static final FileFilter ADOC_FILES = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return !file.isDirectory() && file.getName().endsWith(".adoc");
		}
	};

	private static final FileFilter REST = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return !file.isDirectory() && !file.getName().endsWith(".adoc");
		}
	};

	private final Engine engine;
	private final Path outputPath;
	private final Path inputPath;
	private final Path resourcesPath;

	public static void main(String[] args) {
		new Generator(args[0], args[1], args[2],
				new DefaultAsciidcotorFactory()).generate();
	}

	public Generator(String inputDir, String outputDir, String resourceDir,
			IAsciidoctorFactory factory) {
		this.inputPath = Paths.get(inputDir);
		this.outputPath = Paths.get(outputDir);
		this.resourcesPath = Paths.get(resourceDir);
		this.engine = new Engine(factory);
	}

	public void generate() {
		createIndexDocument(createContentDocuments());
	}

	private void createIndexDocument(Node root) {
		/*
		 * Handle this manually
		 */
	}

	private Node createContentDocuments() {
		Node root = new Node("Root");
		try {
			createContentDocuments(root, inputPath.toFile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return root;
	}

	private void createContentDocuments(Node head, File dir) throws IOException {
		Node node = new Node(dir.getName());
		head.children.add(node);
		for (File directory : dir.listFiles(DIRECTORIES)) {
			createContentDocuments(node, directory);
		}
		STGroup contentSTGroup = new STGroupFile(resourcesPath.toString()
				+ "/content.stg");
		for (File input : dir.listFiles(ADOC_FILES)) {
			DocumentHeader header = engine.getHeader(input);
			String contents = engine.render(input);

			Path outputFilePath = getOutputFilePath(input.toPath(), ".html");
			Options options = new Options(outputPath,
					header.getDocumentTitle(), outputFilePath.toString(),
					contents);
			Files.createDirectories(outputFilePath.getParent());

			ST st = contentSTGroup.getInstanceOf("main");
			st.add("opts", options);
			try (BufferedWriter writer = Files.newBufferedWriter(
					outputFilePath, Charset.forName("UTF-8"))) {
				writer.write(st.render());
			}
			node.entries.add(options);
		}

		for (File input : dir.listFiles(REST)) {
			Path destination = outputPath.resolve(inputPath.relativize(input
					.toPath()));
			if (Files.exists(destination)) {
				Files.delete(destination);
			}
			Files.copy(input.toPath(), destination);
		}
	}

	private Path getOutputFilePath(Path srcFile, String suffix) {
		String out = outputPath.resolve(inputPath.relativize(srcFile))
				.toString();
		out = out.substring(0, out.lastIndexOf(".")) + suffix;
		return Paths.get(out);
	}
}