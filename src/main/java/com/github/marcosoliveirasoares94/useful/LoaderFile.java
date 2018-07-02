package com.github.marcosoliveirasoares94.useful;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * File Uploader, loads the file containing the list of new dependencies or
 * dependencies to be highlighted.
 * 
 * @author Marcos Oliveira Soares
 */
public class LoaderFile {

	protected LoaderFile() {
		super();
	}

	/**
	 * Returns true if the loaded file line is already contained in the list.
	 * 
	 * @param file
	 *            - File to read
	 * @return
	 */
	public static Set<String> readFile(File file) {

		BufferedReader bufferedReader = null;
		Set<String> newDependency = new HashSet<>();
		ValidateFileExtension validateFileExtension = new ValidateFileExtension();

		if (validateFileExtension.isValidExtensionFile(file, "txt")) {
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			}
			if (bufferedReader != null) {
				readFileLinePerLine(bufferedReader, newDependency);
			}
			return newDependency;
		}
		return newDependency;
	}

	/**
	 * Read line by line from file.
	 * 
	 * @param bufferedReader
	 *            - Object to be used for reading the file.
	 * @param newDependency
	 *            - List of new dependencies.
	 */
	private static void readFileLinePerLine(BufferedReader bufferedReader, Set<String> newDependency) {
		try {
			while (bufferedReader.ready()) {
				newDependency.add(bufferedReader.readLine());
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
		}
		try {
			bufferedReader.close();

		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
