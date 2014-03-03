package net.enilink.docs;

import static org.asciidoctor.AttributesBuilder.attributes;
import static org.asciidoctor.OptionsBuilder.options;

import java.io.IOException;
import java.net.URL;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.Options;
import org.asciidoctor.Placement;
import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.FrameworkUtil;

public class Docs {

	private static final Attributes ATTRIBUTES = attributes().backend("html5")
			.showTitle(true).skipFrontMatter(false)
			.tableOfContents(Placement.TOP).tableOfContents(true)
			.sectionNumbers(true).get();

	public static final Options OPTIONS = options().attributes(ATTRIBUTES)
			.get();

	public static Asciidoctor create() {
		URL url = FrameworkUtil.getBundle(Docs.class).findEntries("/lib/",
				"asciidoctor*.jar", false).nextElement();
		try {
			return Asciidoctor.Factory.create(FileLocator.resolve(url)
					.getFile().toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
