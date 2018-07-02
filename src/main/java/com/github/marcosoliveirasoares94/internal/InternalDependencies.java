package com.github.marcosoliveirasoares94.internal;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.marcosoliveirasoares94.IDependency;
import com.github.marcosoliveirasoares94.useful.CellCustomizer;
import com.github.marcosoliveirasoares94.useful.UsefulSpreadsheet;
import com.github.marcosoliveirasoares94.Initialization;

import jxl.write.WritableCellFormat;
import jxl.write.WriteException;

/**
 * Internal Dependencies
 * 
 * @author Marcos Oliveira Soares
 */
public class InternalDependencies implements IDependency {

	String projectName;
	UsefulSpreadsheet usefulSpreadsheet = new UsefulSpreadsheet();
	InternalDependenciesVO internalDependenciesVO = new InternalDependenciesVO();

	private static final int COL_KEY = 0;
	private static final int COL_PACKAGING = 4;
	private static final int SIZE_EXTENSION_PACKAGING = 3;

	public InternalDependencies(String projectName) {
		super();
		this.projectName = projectName;
	}

	@Override
	public void createWorksheet() {
		usefulSpreadsheet.createExcelWorksheet(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
				Initialization.POSITION_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES);
	}

	@Override
	public void mergeHeaderWorksheet() {
		try {
			usefulSpreadsheet.mergeColumns(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
					Initialization.INITIAL_COLUMN, Initialization.PROJECT_NAME_LINE,
					Initialization.ABA_INTERNAL_FINAL_COLUMN, Initialization.PROJECT_NAME_LINE);
			usefulSpreadsheet.mergeColumns(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
					Initialization.INITIAL_COLUMN, Initialization.PROJECT_DESCRIPTION_LINE,
					Initialization.ABA_INTERNAL_FINAL_COLUMN, Initialization.PROJECT_DESCRIPTION_LINE);
		} catch (WriteException e) {
			Logger.getGlobal().log(Level.SEVERE, "Error while merging header from internal worksheet.", e);
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
		usefulSpreadsheet.writeDataLine(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES, projectName,
				Initialization.INITIAL_COLUMN, Initialization.PROJECT_NAME_LINE, customizeCellNameProject);

		String[] listHeaderWorksheet = internalDependenciesVO.getHeaderWorksheet();
		if (listHeaderWorksheet != null) {
			usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
					listHeaderWorksheet, Initialization.HEADLIGHT_COLUMNS_LINE);
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
		usefulSpreadsheet.writeDataLine(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
				Initialization.DESCRIPTION_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES, Initialization.INITIAL_COLUMN,
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
		usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
				internalDependenciesVO.getHeaderWorksheet(), Initialization.HEADLIGHT_COLUMNS_LINE,
				customizeCellHeadColumns);
	}

	/**
	 * Write Internal Dependencies.
	 * 
	 * @param listDependencies
	 *            - List of Dependencies.
	 * @param cellStyle
	 *            - Customizing the cell to insert into the excel worksheet.
	 * 
	 * @param lineExcelWorksheet
	 *            - Line spreadsheet excel.
	 * @throws WriteException
	 */
	public void writeInternalDependencies(String[] listDependencies, WritableCellFormat cellStyle) {
		int lineExcelWorksheet = usefulSpreadsheet
				.getLastLineFilled(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES);
		if (listDependencies != null) {
			if (!("pom".equals(listDependencies[COL_PACKAGING]))) {
				writeInternalDependenciesAuxiliary(listDependencies, cellStyle, lineExcelWorksheet);
			} else {
				usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES,
						listDependencies, lineExcelWorksheet, cellStyle);
			}
		}
	}

	/**
	 * Continued from the Internal Dependencies tab.
	 * 
	 * @param listDependences
	 *            - List of Dependencies.
	 * @param cellStyle
	 *            - Customizing the cell to insert into the excel worksheet.
	 * 
	 * @param lineExcelWorksheet
	 *            - Line spreadsheet excel.
	 */
	private void writeInternalDependenciesAuxiliary(String[] listDependencies, WritableCellFormat cellStyle,
			int lineExcelWorksheet) {
		usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES, listDependencies,
				lineExcelWorksheet, cellStyle);

		listDependencies[COL_KEY] = listDependencies[COL_KEY].substring(0,
				listDependencies[COL_KEY].length() - SIZE_EXTENSION_PACKAGING) + "pom";
		listDependencies[COL_PACKAGING] = "pom";

		int newLine = lineExcelWorksheet;
		++newLine;
		usefulSpreadsheet.writeDataColumn(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES, listDependencies,
				newLine, cellStyle);
	}

	@Override
	public void setColumnWidthWorksheet() {
		usefulSpreadsheet.defineLengthColumns(Initialization.NAME_WORKSHEET_EXCEL_INTERNAL_DEPENDENCIES);
	}
}
