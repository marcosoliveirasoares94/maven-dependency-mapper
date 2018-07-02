package com.github.marcosoliveirasoares94;

import jxl.write.WriteException;

/**
 * Provider of methods to be implemented.
 * 
 * @author Marcos Oliveira Soares
 */
public interface IDependency {

	void createWorksheet();

	void mergeHeaderWorksheet();

	void insertHeaderWorksheet() throws WriteException;

	void setColumnWidthWorksheet();
}
