package net.enilink.docs;

import java.io.IOException;
import java.net.URL;

import net.enilink.docs.ant.DefaultAsciidoctorFactory;

import org.asciidoctor.Asciidoctor;
import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.FrameworkUtil;

public class OSGiAsciidoctorFactory extends DefaultAsciidoctorFactory {

	@Override
	public Asciidoctor create() {
		URL url = FrameworkUtil.getBundle(Docs.class)
				.findEntries("/lib/", "asciidoctor*.jar", false).nextElement();
		try {
			return Asciidoctor.Factory.create(FileLocator.resolve(url)
					.getFile().toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
