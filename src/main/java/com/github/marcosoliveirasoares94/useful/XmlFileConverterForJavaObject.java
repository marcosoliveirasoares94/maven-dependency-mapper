package com.github.marcosoliveirasoares94.useful;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Converts xml file to java object.
 * 
 * @author Marcos Oliveira Soares
 */
public class XmlFileConverterForJavaObject {

	public XmlFileConverterForJavaObject() {
		super();
	}

	public Model toConvert(File file) {
		MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
		Model model = null;
		try {
			model = mavenXpp3Reader.read(new FileReader(file));
			validData(model);
		} catch (IOException | XmlPullParserException e) {
			Logger.getGlobal().log(Level.SEVERE, "Could not convert " + file.getName(), e);
		}
		return model;
	}

	/**
	 * Validation of data.
	 * 
	 * @param model
	 *            - Object Model.
	 */
	private void validData(Model model) {
		if (model.getGroupId() == null) {
			model.setGroupId(model.getParent().getGroupId());
		}
		if (model.getArtifactId() == null) {
			model.setArtifactId(model.getParent().getArtifactId());
		}
		if (model.getVersion() == null) {
			model.setVersion(model.getParent().getVersion());
		}
	}

}
