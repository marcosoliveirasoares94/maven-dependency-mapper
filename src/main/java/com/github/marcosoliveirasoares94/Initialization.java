package com.github.marcosoliveirasoares94;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.model.Model;

import com.github.marcosoliveirasoares94.external.ExternalDependencies;
import com.github.marcosoliveirasoares94.internal.InternalDependencies;
import com.github.marcosoliveirasoares94.useful.UsefulSpreadsheet;
import com.github.marcosoliveirasoares94.useful.ValidateFileExtension;
import com.github.marcosoliveirasoares94.useful.XmlFileConverterForJavaObject;

import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;

/**
 * Class responsible for the orchestration of program execution.
 * 
 * @author Marcos Oliveira Soares
 */
public class Initialization extends PreInitialization {

	private static final int ARG_POSITION_0 = 0;
	private static final int ARG_POSITION_1 = 1;
	private static final int ARG_POSITION_2 = 2;
	private static final int ARG_POSITION_3 = 3;
	private static final int ARG_POSITION_4 = 4;

	public static final int INITIAL_COLUMN = 0;
	public static final int ABA_INTERNAL_FINAL_COLUMN = 5;
	public static final int ABA_EXTERNAL_FINAL_COLUMN = 3;
	public static final int PROJECT_NAME_LINE = 0;
	public static final int PROJECT_DESCRIPTION_LINE = 1;
	public static final int HEADLIGHT_COLUMNS_LINE = 2;
	public static final int POSITION_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES = 0;
	public static final int POSITION_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES = 1;

	public static final String NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES = "Internal";
	public static final String NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES = "External";
	public static final String DESCRIPTION_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES = "Internal Dependencies";
	public static final String DESCRIPTION_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES = "External Dependencies";

	/**
	 * Responsible for receiving the program's initialization parameters.
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
	 */
	public static void main(String[] args) {
		String argProjectName = args[ARG_POSITION_0];
		String argPathDependencies = args[ARG_POSITION_1];
		String argFileNewDependencies = args[ARG_POSITION_2];
		String argProjectVersion = args[ARG_POSITION_3];
		String argDestinationExcelFile = args[ARG_POSITION_4];

		preProcessing(argProjectName, argPathDependencies, argFileNewDependencies, argProjectVersion, argDestinationExcelFile);

		UsefulSpreadsheet usefulSpreadsheet = new UsefulSpreadsheet();
		usefulSpreadsheet.createExcelFile(destinationExcelFile, projectName + " - " + projectVersion + " - Dependencies.xls");

		InternalDependencies internalDependencies = new InternalDependencies(projectName);
		internalDependencies.createWorksheet();
		internalDependencies.mergeHeaderWorksheet();
		internalDependencies.insertHeaderWorksheet();

		ExternalDependencies externalDependencies = new ExternalDependencies(projectName);
		externalDependencies.createWorksheet();
		externalDependencies.mergeHeaderWorksheet();
		externalDependencies.insertHeaderWorksheet();

		ValidateFileExtension validateFileExtension = new ValidateFileExtension();
		writeDependencies(internalDependencies, externalDependencies, validateFileExtension, getsPomMain());

		internalDependencies.setColumnWidthWorksheet();
		externalDependencies.setColumnWidthWorksheet();
		usefulSpreadsheet.closeExcelFile();

	}

	/**
	 * Responsible for loading the pom's files and reviewing the recording
	 * obligations of the premises.
	 * 
	 * @param internalDependencies
	 *            - Internal Dependency Object
	 * @param externalDependencies
	 *            - External Dependency Object
	 * @param validateFileExtension
	 *            - File Extension Validator Object
	 * @param listPomMain
	 *            - List of archives main pom's project.
	 */
	protected static void writeDependencies(InternalDependencies internalDependencies,
			ExternalDependencies externalDependencies, ValidateFileExtension validateFileExtension,
			Set<Model> listPomMain) {

		XmlFileConverterForJavaObject xmlFileConverterForJavaObject = new XmlFileConverterForJavaObject();
		for (File file : pathFileDependencies.listFiles()) {
			WritableCellFormat cellStyle = defineColorLine(file);
			if (file.isFile() && validateFileExtension.isValidExtensionFile(file, ".pom")) {
				Model model = xmlFileConverterForJavaObject.toConvert(file);
				String[] listDependencyModel = {
						model.getGroupId() + ":" + model.getArtifactId() + ":" + model.getVersion() + ":"
								+ model.getPackaging(),
						model.getGroupId(), model.getArtifactId(), model.getVersion(), model.getPackaging(),
						model.getDescription() };

				internalDependencies.writeInternalDependencies(listDependencyModel, cellStyle);

				listExternalDependenciesHashSetDependency
						.addAll(externalDependencies.addExternalDependencyList(model, listPomMain));
			}
		}
		externalDependencies.saveExternalDependencies(listExternalDependenciesHashSetDependency);
	}

	/**
	 * Responsible for defining the color of the line to be inserted in the
	 * dependency map.
	 * 
	 * @param file
	 *            - Input file.
	 * @return cellStyle
	 */
	private static WritableCellFormat defineColorLine(File file) {
		WritableCellFormat cellStyle = new WritableCellFormat();
		if (listNewDependencies.contains(file.getName())) {
			try {
				cellStyle.setBackground(Colour.YELLOW);
			} catch (WriteException e) {
				Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return cellStyle;
	}
}
