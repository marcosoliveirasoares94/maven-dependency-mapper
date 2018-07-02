package com.github.marcosoliveirasoares94.useful;

import java.io.File;

/**
 * Validate File Extension
 * 
 * @author Marcos Oliveira Soares
 */
public class ValidateFileExtension {

	/**
	 * Creates a new instance of type ValidateFileExtension
	 */
	public ValidateFileExtension() {
		super();
	}

	/**
	 * Validate file extension
	 * 
	 * @param file
	 *            - Archive
	 * @param fileExtension
	 *            - Archive extension.
	 * @return
	 */
	public boolean isValidExtensionFile(File file, String fileExtension) {
		return file.getName().endsWith(fileExtension);
	}
}
