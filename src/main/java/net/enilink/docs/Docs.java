package net.enilink.docs;

import static org.asciidoctor.AttributesBuilder.attributes;
import static org.asciidoctor.OptionsBuilder.options;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.Options;
import org.asciidoctor.Placement;

public class Docs {

	private static final Attributes ATTRIBUTES = attributes().backend("html5")
			.showTitle(true).skipFrontMatter(false)
			.tableOfContents(Placement.TOP).tableOfContents(true)
			.sectionNumbers(true).get();

	public static final Options OPTIONS = options().attributes(ATTRIBUTES)
			.get();

	public static Asciidoctor create() {
		return new OSGiAsciidoctorFactory().create();
	}

}
