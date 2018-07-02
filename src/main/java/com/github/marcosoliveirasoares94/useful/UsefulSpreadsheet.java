package com.github.marcosoliveirasoares94.useful;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.model.Dependency;

import com.github.marcosoliveirasoares94.external.ExternalDependenciesEnum;
import com.github.marcosoliveirasoares94.external.ExternalDependenciesVO;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Util Class for Excel spreadsheet modifications.
 * 
 * @author Marcos Oliveira Soares
 */
public class UsefulSpreadsheet {

	protected static WritableWorkbook excelFile;

	/**
	 * Responsible for creating the excel file
	 * 
	 * @param nameExcelFile
	 *            - String containing the name of the excel file.
	 * @return
	 */
	public synchronized WritableWorkbook createExcelFile(String destinationExcelFile, String nameExcelFile) {
		try {
			excelFile = Workbook.createWorkbook(new File(destinationExcelFile,nameExcelFile));
			Logger.getGlobal().log(Level.INFO, "Building the dependency worksheet in: " + destinationExcelFile);
			Logger.getGlobal().log(Level.INFO, nameExcelFile);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Error creating excel file.", e);
		}
		return excelFile;
	}

	/**
	 * Create Excel Worksheet
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param positionExcelWorksheet
	 *            - Position of flap.
	 */
	public void createExcelWorksheet(String nameExcelWorksheet, int positionExcelWorksheet) {
		excelFile.createSheet(nameExcelWorksheet, positionExcelWorksheet);
	}

	/**
	 * Save Excel Worksheet Columns
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param array
	 *            - Columns
	 * @param lineExcelWorksheet
	 *            - Line of data to be inserted.
	 * @throws WriteException
	 */
	public void writeDataColumn(String nameExcelWorksheet, String[] array, int lineExcelWorksheet)
			throws WriteException {
		for (int column = 0; column < array.length; column++) {
			Label label = new Label(column, lineExcelWorksheet, array[column]);
			excelFile.getSheet(nameExcelWorksheet).addCell(label);
		}
	}

	/**
	 * Save worksheet columns excel
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param array
	 *            - Columns
	 * @param lineExcelWorksheet
	 *            - Line of data to be inserted.
	 * @param cellStyle
	 *            - Cell Style.
	 */
	public void writeDataColumn(String nameExcelWorksheet, String[] array, int lineExcelWorksheet,
			WritableCellFormat cellStyle) {
		for (int column = 0; column < array.length; column++) {
			Label label = new Label(column, lineExcelWorksheet, array[column], cellStyle);
			try {
				excelFile.getSheet(nameExcelWorksheet).addCell(label);
			} catch (WriteException e) {
				Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	/**
	 * Save worksheet columns excel
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param dependencies
	 *            - Dependencies
	 * @param externalDependencyVO
	 *            - Object ExternalDependenciesVO
	 * @throws WriteException
	 */
	public void writeDataColumnExternalDependency(String nameExcelWorksheet, Set<Dependency> dependencies,
			ExternalDependenciesVO externalDependencyVO) throws WriteException {
		for (Dependency dependency : dependencies) {
			String[] arrayListDependency = new String[externalDependencyVO.getArraySize()];
			arrayListDependency[ExternalDependenciesEnum.POSITION_0.getPosition()] = dependency.getGroupId();
			arrayListDependency[ExternalDependenciesEnum.POSITION_1.getPosition()] = dependency.getArtifactId();
			arrayListDependency[ExternalDependenciesEnum.POSITION_2.getPosition()] = dependency.getVersion();
			arrayListDependency[ExternalDependenciesEnum.POSITION_3.getPosition()] = dependency.getType();
			int lineExcelWorksheet = getLastLineFilled(nameExcelWorksheet);
			writeDataColumn(nameExcelWorksheet, arrayListDependency, lineExcelWorksheet);
		}
	}

	/**
	 * Save row spreadsheet excel
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param value
	 *            - Value
	 * @param columnExcelWorksheet
	 *            - Columns
	 * @param lineExcelWorksheet
	 *            - Line of data to be inserted.
	 * @throws WriteException
	 */
	protected void writeDataLine(String nameExcelWorksheet, String value, int columnExcelWorksheet,
			int lineExcelWorksheet) throws WriteException {
		Label label = new Label(columnExcelWorksheet, lineExcelWorksheet, value);
		excelFile.getSheet(nameExcelWorksheet).addCell(label);
	}

	/**
	 * Save row spreadsheet excel
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param value
	 *            - Value
	 * @param columnExcelWorksheet
	 *            - Columns
	 * @param lineExcelWorksheet
	 *            - Line of data to be inserted.
	 * @param cellStyle
	 *            - Cell Style.
	 * @throws WriteException
	 */
	public void writeDataLine(String nameExcelWorksheet, String value, int column, int lineExcelWorksheet,
			WritableCellFormat cellStyle) throws WriteException {
		Label label = new Label(column, lineExcelWorksheet, value, cellStyle);
		excelFile.getSheet(nameExcelWorksheet).addCell(label);
	}

	/**
	 * Get last row filled in spreadsheet.
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @return
	 */
	public int getLastLineFilled(String nameExcelWorksheet) {
		return excelFile.getSheet(nameExcelWorksheet).getRows();
	}

	/**
	 * Merge columns
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 * @param initialColumn
	 *            - Initial Column
	 * @param initialRow
	 *            - Initial Row
	 * @param lastColumn
	 *            - Last Column
	 * @param lastLine
	 *            - Last Line
	 * @throws WriteException
	 */
	public void mergeColumns(String nameExcelWorksheet, int initialColumn, int initialRow, int lastColumn, int lastLine)
			throws WriteException {
		excelFile.getSheet(nameExcelWorksheet).mergeCells(initialColumn, initialRow, lastColumn, lastLine);
	}

	/**
	 * Set column width.
	 * 
	 * @param nameExcelWorksheet
	 *            - String containing the tab name.
	 */
	public void defineLengthColumns(String nameExcelWorksheet) {
		final int maximumWidth = 255;
		final int uniCell = 256;
		final int dimensionCell = 100;
		for (int column = 0; column < excelFile.getSheet(nameExcelWorksheet).getColumns(); column++) {
			Cell[] cell = excelFile.getSheet(nameExcelWorksheet).getColumn(column);
			int cellSize = -1;

			/*
			 * Find the widest cell in the column.
			 */
			cellSize = findLargeCell(cell, cellSize);

			/*
			 * If it is wider than the maximum width, the width of the column
			 */
			if (cellSize > maximumWidth) {
				cellSize = maximumWidth;
			}

			CellView cellView = excelFile.getSheet(nameExcelWorksheet).getColumnView(column);

			/*
			 * Each cell is 256 units wide, then size it.
			 */
			cellView.setSize(cellSize * uniCell + dimensionCell);
			excelFile.getSheet(nameExcelWorksheet).setColumnView(column, cellView);
		}
	}

	/**
	 * Find the widest cell in the column and resize the rest.
	 * 
	 * @param cell
	 *            - Cell
	 * @param cellSize
	 *            - Cell Size
	 * @return
	 */
	protected int findLargeCell(Cell[] cell, int cellSize) {
		for (int cellMoreLargeColumn = 0; cellMoreLargeColumn < cell.length; cellMoreLargeColumn++) {
			if (cell[cellMoreLargeColumn].getContents().length() > cellSize) {
				String str = cell[cellMoreLargeColumn].getContents();
				if (str == null || str.isEmpty()) {
					continue;
				}
				cellSize = str.trim().length();
			}
		}
		return cellSize;
	}

	/**
	 * Exit file excel.
	 */
	public void closeExcelFile() {
		try {
			excelFile.write();
			excelFile.close();
			Logger.getGlobal().log(Level.INFO, "Built-in dependency worksheet.");
		} catch (WriteException | IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Error saving or exiting the excel file.", e);
		}

	}
}
