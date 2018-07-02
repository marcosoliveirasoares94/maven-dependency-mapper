package com.github.marcosoliveirasoares94;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import com.github.marcosoliveirasoares94.useful.LoaderFile;
import com.github.marcosoliveirasoares94.useful.ValidateFileExtension;
import com.github.marcosoliveirasoares94.useful.XmlFileConverterForJavaObject;

/**
 * Class that performs the preprocessing of the arguments passed in the program
 * execution.
 * 
 * @author Marcos Oliveira Soares
 */
public class PreInitialization {

	protected static volatile String projectName;
	protected static volatile String projectVersion;
	protected static volatile File pathFileDependencies;
	protected static volatile String destinationExcelFile;
	protected static volatile Set<String> listNewDependencies;
	protected static Set<Dependency> listExternalDependenciesHashSetDependency = new HashSet<>();

	protected PreInitialization() {
	}

	/**
	 * 
	 * Pre-processing input arguments.
	 * 
	 * @param argProjectName
	 *            - Project Name.
	 * 
	 * @param argPathDependencies
	 *            - Location of Dependencies.
	 * 
	 * @param argFileNewDependencies
	 *            - File with list of new dependencies or dependencies to be
	 *            highlighted.
	 * 
	 * @param argProjectVersion
	 *            - Project Version.
	 *            
	 * @param argDestinationExcelFile - Destination of excel file.
	 * 
	 * @throws ExceptionInInitializerError
	 */

	protected static void preProcessing(String projectName, String pathDependencies, String fileNewDependencies,
			String projectVersion, String argDestinationExcelFile) throws ExceptionInInitializerError {
		
		preProcessingProjectName(projectName);

		preProcessingPathDependencies(pathDependencies);

		preProcessingFileNewDependencies(fileNewDependencies);

		preProcessingProjectVersion(projectVersion);
		
		preProcessingDestinationExcelFile(argDestinationExcelFile);
	}

	/**
	 * Performing the argProjectName argument processing.
	 * 
	 * @param argProjectName
	 *            - Project Name.
	 * @throws ExceptionInInitializerError
	 */
	private static void preProcessingProjectName(String argProjectName) throws ExceptionInInitializerError {
		if (argProjectName != null && !argProjectName.isEmpty()) {
			projectName = argProjectName;
		} else {
			throw new ExceptionInInitializerError("The name of the project not informed.");
		}
	}

	/**
	 * Performing the processing of the argPathDependencies argument.
	 * 
	 * @param argPathDependencies
	 *            - Location of Dependencies.
	 * @throws ExceptionInInitializerError
	 */
	private static void preProcessingPathDependencies(String argPathDependencies) throws ExceptionInInitializerError {
		if (argPathDependencies != null && !argPathDependencies.isEmpty()) {
			pathFileDependencies = new File(argPathDependencies);
			if (!pathFileDependencies.isDirectory()) {
				throw new ExceptionInInitializerError(pathFileDependencies + " does not exist.");
			}
		} else {
			throw new ExceptionInInitializerError("The dependencies directory has not been reported.");
		}
	}

	/**
	 * Performing the argProjectVersion argument processing.
	 * 
	 * @param argProjectVersion
	 *            - Project Version.
	 * @throws ExceptionInInitializerError
	 */
	private static void preProcessingProjectVersion(String argProjectVersion) throws ExceptionInInitializerError {
		if (argProjectVersion != null && !argProjectVersion.isEmpty()) {
			projectVersion = argProjectVersion;
		} else {
			throw new ExceptionInInitializerError("The version of the project not informed.");
		}
	}

	/**
	 * Performing the argFileNewDependencies argument processing.
	 * 
	 * @param argFileNewDependencies
	 *            - File with list of new dependencies or dependencies to be
	 *            highlighted.
	 */
	private static void preProcessingFileNewDependencies(String argFileNewDependencies) {
		if (argFileNewDependencies != null) {
			listNewDependencies = LoaderFile.readFile(new File(argFileNewDependencies));
		} else {
			Logger.getGlobal().log(Level.INFO, "The file with the list of new dependencies has not been reported.");
			return;
		}
	}
	
	/**
	 * Performing the argDestinationExcelFile argument processing.
	 * 
	 * @param argDestinationExcelFile
	 *            - Destination of excel file.
	 */
	private static void preProcessingDestinationExcelFile(String argDestinationExcelFile) {
		if (argDestinationExcelFile != null && !argDestinationExcelFile.isEmpty()) {
			destinationExcelFile = argDestinationExcelFile;
		} else {
			Logger.getGlobal().log(Level.INFO, "The destination of the excel file was not reported.");
			return;
		}
	}

	/**
	 *
	 * Getting the maven file (s) with "pom" extension, dirty your settings
	 * match the definitions of the main pom file of a project.
	 * 
	 * @return listPomMain
	 * 
	 */
	protected static Set<Model> getsPomMain() {
		Set<Model> listPomMain = new HashSet<>();
		XmlFileConverterForJavaObject xmlFileConverterForJavaObject = new XmlFileConverterForJavaObject();
		ValidateFileExtension validateFileExtension = new ValidateFileExtension();
		for (File file : pathFileDependencies.listFiles()) {
			if (file.isFile() && validateFileExtension.isValidExtensionFile(file, ".pom")) {
				Model model = xmlFileConverterForJavaObject.toConvert(file);
				if ("pom".equals(model.getPackaging())) {
					listPomMain.add(model);
				}
			}
		}
		return listPomMain;
	}
}
