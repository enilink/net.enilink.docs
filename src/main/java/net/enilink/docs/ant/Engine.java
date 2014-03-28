package net.enilink.docs.ant;

import static org.asciidoctor.AttributesBuilder.attributes;
import static org.asciidoctor.OptionsBuilder.options;

import java.io.File;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.DocumentHeader;
import org.asciidoctor.Options;
import org.asciidoctor.Placement;

public class Engine {
	private final Asciidoctor engine = Asciidoctor.Factory.create();
	private final Attributes attributes = attributes().docType("book")
			.backend("html5").showTitle(true).skipFrontMatter(false)
			.tableOfContents(Placement.TOP).tableOfContents(true)
			.sectionNumbers(true).get();
	private final Options options = options().attributes(attributes).get();

	public String render(File input) {
		return engine.renderFile(input, options);
	}

	public DocumentHeader getHeader(File input) {
		return engine.readDocumentHeader(input);
	}
}
