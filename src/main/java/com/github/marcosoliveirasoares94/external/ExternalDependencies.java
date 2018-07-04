package com.github.marcosoliveirasoares94.external;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import com.github.marcosoliveirasoares94.IDependency;
import com.github.marcosoliveirasoares94.Initialization;
import com.github.marcosoliveirasoares94.useful.CellCustomizer;
import com.github.marcosoliveirasoares94.useful.UsefulSpreadsheet;

import jxl.write.WritableCellFormat;
import jxl.write.WriteException;

/**
 * External Dependencies
 * 
 * @author Marcos Oliveira Soares
 */
public class ExternalDependencies implements IDependency {

	String projectName;
	UsefulSpreadsheet usefulSpreadsheet = new UsefulSpreadsheet();
	Set<Dependency> listDependencies = new HashSet<>();
	ExternalDependenciesVO externalDependenciesVO = new ExternalDependenciesVO();

	/**
	 * Create a new instance of type External Dependency.
	 * 
	 * @param projectName
	 *            - Project name.
	 */
	public ExternalDependencies(String projectName) {
		super();
		this.projectName = projectName;
	}

	@Override
	public void createWorksheet() {
		usefulSpreadsheet.createExcelWorksheet(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES,
				Initialization.POSITION_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES);
	}

	@Override
	public void mergeHeaderWorksheet() {
		try {
			usefulSpreadsheet.mergeColumns(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES,
					Initialization.INITIAL_COLUMN, Initialization.PROJECT_NAME_LINE,
					Initialization.ABA_EXTERNAL_FINAL_COLUMN, Initialization.PROJECT_NAME_LINE);
			usefulSpreadsheet.mergeColumns(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES,
					Initialization.INITIAL_COLUMN, Initialization.PROJECT_DESCRIPTION_LINE,
					Initialization.ABA_EXTERNAL_FINAL_COLUMN, Initialization.PROJECT_DESCRIPTION_LINE);
		} catch (WriteException e) {
			Logger.getGlobal().log(Level.SEVERE, "Error while merging header from outer sheet.", e);
		}
	}

	@Override
	public void insertHeaderWorksheet() {
		CellCustomizer cellCustomizer = new CellCustomizer();
		try {
			/*
			 * Writing the Project Name in the Excel worksheet.
			 */
			writeProjectName(cellCustomizer.projectName());

			/*
			 * Writing the Description in the Excel Worksheet.
			 */
			writeDescription(cellCustomizer.description());

			/*
			 * Writing the Column Header from the Excel Worksheet.
			 */
			writeColumnHeader(cellCustomizer.column());
		} catch (WriteException e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Writes Project Name to the Excel worksheet.
	 * 
	 * @param customizeCellNameProject
	 *            - Customizing the line to be written in excel.
	 * @throws WriteException
	 */
	private void writeProjectName(WritableCellFormat customizeCellNameProject) throws WriteException {
		usefulSpreadsheet.writeDataLine(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES, projectName,
				Initialization.INITIAL_COLUMN, Initialization.PROJECT_NAME_LINE, customizeCellNameProject);

		String[] arrayHeaderWorksheet = externalDependenciesVO.getHeaderWorksheet();
		if (arrayHeaderWorksheet != null) {
			usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES,
					arrayHeaderWorksheet, Initialization.PROJECT_DESCRIPTION_LINE);
		}
	}

	/**
	 * Write Description in the Excel worksheet.
	 * 
	 * @param customizationCellDescription
	 *            - Customizing the line to be written in excel.
	 * @throws WriteException
	 */
	private void writeDescription(WritableCellFormat customizationCellDescription) throws WriteException {
		usefulSpreadsheet.writeDataLine(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES,
				Initialization.DESCRIPTION_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES, Initialization.INITIAL_COLUMN,
				Initialization.PROJECT_DESCRIPTION_LINE, customizationCellDescription);
	}

	/**
	 * Save Column Header from Excel Spreadsheet.
	 * 
	 * @param customizeCellHeadColumns
	 *            - Customizing the line to be written in excel.
	 * @throws WriteException
	 */
	private void writeColumnHeader(WritableCellFormat customizeCellHeadColumns) {
		usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES,
				externalDependenciesVO.getHeaderWorksheet(), Initialization.HEADLIGHT_COLUMNS_LINE,
				customizeCellHeadColumns);
	}

	/**
	 * Create list of external dependencies.
	 * 
	 * @param model
	 *            - Model Object
	 * @param listPomMain
	 *            - List of POM's Main Files.
	 * @return listDependencies
	 */
	public Set<Dependency> addExternalDependencyList(Model model, Set<Model> listPomMain) {
		if (model.getDependencies() != null && model.getDependencyManagement() != null) {
			modelGetDependency(model, listPomMain);
			modelGetDependencyManagement(model, listPomMain);
		} else if (model.getDependencyManagement() != null && model.getDependencies() == null) {
			modelGetDependencyManagement(model, listPomMain);
		} else if (model.getDependencyManagement() == null && model.getDependencies() != null) {
			modelGetDependency(model, listPomMain);
		}
		return listDependencies;
	}

	/**
	 * Gets dependencies on the <Dependencies> tag
	 * 
	 * @param model
	 *            - Model Object
	 * @param listPomMain
	 *            - List of POM's Main Files.
	 */
	private void modelGetDependency(Model model, Set<Model> listPomMain) {
		for (Dependency dependency : model.getDependencies()) {
			addListExternalDependency(listPomMain, dependency);
		}
	}

	/**
	 * Gets dependencies on the <DependencyManagement> tag
	 * 
	 * @param model
	 *            - Model Object
	 * @param listPomMain
	 *            - List of POM's Main Files.
	 */
	private void modelGetDependencyManagement(Model model, Set<Model> listPomMain) {
		for (Dependency dependency : model.getDependencyManagement().getDependencies()) {
			addListExternalDependency(listPomMain, dependency);
		}
	}

	/**
	 * Add External Dependencies that are not com.github.marcosoliveirasoares94
	 * or jars.com.github.marcosoliveirasoares94
	 * 
	 * @param listPomMain
	 *            - List of POM's Main Files.
	 * @param dependency
	 *            - Dependency Object
	 */
	private void addListExternalDependency(Set<Model> listPomMain, Dependency dependency) {
		if (!(dependency.getGroupId().startsWith("com.github.marcosoliveirasoares94")
				|| dependency.getGroupId().startsWith("jars.com.github.marcosoliveirasoares94"))) {
			if (dependency.getVersion() == null || dependency.getVersion().startsWith("$")) {
				getDependencyManagementOrGetDependecies(listPomMain, dependency);
			}
			if (!(isDuplicado(dependency))) {
				listDependencies.add(dependency);
			}
		}
	}

	/**
	 * Responsible for the first validation of the getDependencyManagement and
	 * getDependencies
	 * 
	 * @param listPomMain
	 *            - List of POM's Main Files.
	 * @param dependency
	 *            - Dependency Object
	 */
	private void getDependencyManagementOrGetDependecies(Set<Model> listPomMain, Dependency dependency) {
		for (Model pomMain : listPomMain) {
			if (pomMain.getDependencyManagement() != null && pomMain.getDependencies() != null) {
				getDependencyManagement(dependency, pomMain);
				getDependency(dependency, pomMain);
			} else if (pomMain.getDependencyManagement() != null && pomMain.getDependencies() == null) {
				getDependencyManagement(dependency, pomMain);
			} else if (pomMain.getDependencyManagement() == null && pomMain.getDependencies() != null) {
				getDependency(dependency, pomMain);
			}
		}
	}

	/**
	 * Gets information about dependency from the getDependencyManagement()
	 * method getDependencies()
	 * 
	 * @param dependency
	 *            - Dependency Object
	 * @param pomMain
	 *            - Main POM Archive
	 */
	private void getDependencyManagement(Dependency dependency, Model pomMain) {
		final int subStringAux = 2;
		for (int j = 0; j < pomMain.getDependencyManagement().getDependencies().size(); j++) {
			if (pomMain.getDependencyManagement().getDependencies().get(j).getGroupId().equals(dependency.getGroupId())
					&& pomMain.getDependencyManagement().getDependencies().get(j).getArtifactId()
							.equals(dependency.getArtifactId())) {
				if (pomMain.getDependencyManagement().getDependencies().get(j).getVersion() == null)
	            {
	               dependency.setVersion("");
	            }
            	else if (pomMain.getDependencyManagement().getDependencies().get(j).getVersion().startsWith("$")) {
					getDependencyManagementAuxiliary(dependency, pomMain, subStringAux, j);
				} else {
					dependency.setVersion(pomMain.getDependencyManagement().getDependencies().get(j).getVersion());
				}
			}
		}
	}

	/**
	 * Auxiliary of the getDependencyManagement
	 * 
	 * @param dependency
	 *            - Dependency Object
	 * @param pomMain
	 *            - Main POM Archive
	 * @param subStringAux
	 *            - Substring
	 * @param j
	 *            - Local variable
	 */
	private void getDependencyManagementAuxiliary(Dependency dependency, Model pomMain, final int subStringAux, int j) {
		if (pomMain.getProperties()
				.getProperty(pomMain.getDependencyManagement().getDependencies().get(j).getVersion().substring(
						subStringAux, pomMain.getDependencyManagement().getDependencies().get(j).getVersion().length()
								- 1)) != null) {
			dependency.setVersion(pomMain.getProperties()
					.getProperty(pomMain.getDependencyManagement().getDependencies().get(j).getVersion().substring(
							subStringAux, pomMain.getDependencyManagement().getDependencies().get(j).getVersion()
									.length() - 1)));
		}
	}

	/**
	 * Auxiliary method getDependencyManagement Or getDependencies
	 * 
	 * @param dependency
	 *            - Dependency Object
	 * @param modelAux
	 *            - Main POM file for validation.
	 */
	private void getDependency(Dependency dependency, Model modelAux) {
		final int subStringAux = 2;
		for (int j = 0; j < modelAux.getDependencies().size(); j++) {
			if (modelAux.getDependencies().get(j).getGroupId().equals(dependency.getGroupId())
					&& modelAux.getDependencies().get(j).getArtifactId().equals(dependency.getArtifactId())) {
				if (modelAux.getDependencies().get(j).getVersion().startsWith("$") == null)
	            {
	               dependency.setVersion("");
	            }
	            else if (modelAux.getDependencies().get(j).getVersion().startsWith("$")) {
					getDependenciesAuxiliar(dependency, modelAux, subStringAux, j);
				} else {
					dependency.setVersion(modelAux.getDependencies().get(j).getVersion());
				}
			}
		}
	}

	/**
	 * Auxiliary method getDependencies
	 * 
	 * @param dependency
	 *            - Dependency Object
	 * @param modelAux
	 *            - Main POM file for validation.
	 * @param subStringAux
	 *            - Substring
	 * @param j
	 *            - Local variable
	 */
	private void getDependenciesAuxiliar(Dependency dependency, Model modelAux, final int subStringAux, int j) {
		if (modelAux.getProperties().getProperty(modelAux.getDependencies().get(j).getVersion().substring(subStringAux,
				modelAux.getDependencies().get(j).getVersion().length() - 1)) != null) {
			dependency.setVersion(modelAux.getProperties().getProperty(modelAux.getDependencies().get(j).getVersion()
					.substring(subStringAux, modelAux.getDependencies().get(j).getVersion().length() - 1)));
		}
	}

	/**
	 * Save External Dependencies.
	 * 
	 * @param dependencies
	 *            - List of dependencies
	 */
	public void saveExternalDependencies(Set<Dependency> dependencies) {
		try {
			usefulSpreadsheet.writeDataColumnExternalDependency(
					Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES, dependencies, externalDependenciesVO);
		} catch (WriteException e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void setColumnWidthWorksheet() {
		usefulSpreadsheet.defineLengthColumns(Initialization.NAME_WORKSHEET_EXCEL_EXTERNAL_DEPENDENCIES);
	}

	/**
	 * Checks whether dependency is already contained in the hashSetDependency
	 * list.
	 * 
	 * @param dependency
	 *            - Dependency
	 * @return
	 */
	private boolean isDuplicado(Dependency dependency) {
		for (Dependency dependencyComparative : listDependencies) {
			try {
				if (checkDuplicity(dependency, dependencyComparative)) {
					return true;
				}
			} catch (RuntimeException e) {
				Logger.getGlobal().log(Level.WARNING,
						("Dependency " + dependencyComparative.getGroupId() + "-"
								+ dependencyComparative.getArtifactId() + ":" + dependencyComparative.getType()
								+ " no version declared in any POM file."),
						e);
				return false;
			}
		}
		return false;
	}

	/**
	 * Check Duplicity
	 * 
	 * @param dependency
	 *            - Dependency
	 * @param dependencyComparative
	 *            - Dependency Comparative
	 * @return
	 */
	private boolean checkDuplicity(Dependency dependency, Dependency dependencyComparative) {
		return dependencyComparative.getGroupId().equals(dependency.getGroupId())
				&& dependencyComparative.getArtifactId().equals(dependency.getArtifactId())
				&& dependencyComparative.getType().equals(dependency.getType())
				&& (dependencyComparative.getVersion().equals(dependency.getVersion()));
	}
}
