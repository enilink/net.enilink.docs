package net.enilink.docs.ant;

import org.asciidoctor.Asciidoctor;

public class DefaultAsciidoctorFactory implements IAsciidoctorFactory {

	public Asciidoctor create() {
		return Asciidoctor.Factory.create();
	}

}
