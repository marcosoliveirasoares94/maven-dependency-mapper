package com.github.marcosoliveirasoares94.useful;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

/**
 * Custom Excel Cell.
 * 
 * @author Marcos Oliveira Soares
 */
public class CellCustomizer {

	protected WritableCellFormat cellCustomizerProjectName;
	protected WritableCellFormat cellCustomizerDescription;
	protected WritableCellFormat customizeCellHeaderColumns;

	/**
	 * Enum Font Size
	 * 
	 * @author Marcos Oliveira Soares
	 */
	enum FontSize {
		SIZE_10(10), SIZE_12(12);

		private final int value;

		FontSize(int fontSize) {
			value = fontSize;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * Create a new instance of type CellCustomizer.
	 */
	public CellCustomizer() {
		super();
		cellCustomizerProjectName = new WritableCellFormat();
		cellCustomizerDescription = new WritableCellFormat();
		customizeCellHeaderColumns = new WritableCellFormat();
	}

	/**
	 * Customizing header columns.
	 * 
	 * @return
	 * @throws WriteException
	 */
	public WritableCellFormat column() throws WriteException {

		customizeCellHeaderColumns.setBackground(Colour.GREEN);
		customizeCellHeaderColumns.setAlignment(Alignment.CENTRE);
		customizeCellHeaderColumns.setVerticalAlignment(VerticalAlignment.CENTRE);
		customizeCellHeaderColumns.setFont(new WritableFont(WritableFont.ARIAL, FontSize.SIZE_10.getValue(),
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE));
		return customizeCellHeaderColumns;
	}

	/**
	 * Customization of the description.
	 * 
	 * @return
	 * @throws WriteException
	 */
	public WritableCellFormat description() throws WriteException {

		cellCustomizerDescription.setBackground(Colour.GREEN);
		cellCustomizerDescription.setAlignment(Alignment.CENTRE);
		cellCustomizerDescription.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellCustomizerDescription.setFont(new WritableFont(WritableFont.ARIAL, FontSize.SIZE_12.getValue(),
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE));
		return cellCustomizerDescription;
	}

	/**
	 * Customizing the project name.
	 * 
	 * @return
	 * @throws WriteException
	 */
	public WritableCellFormat projectName() throws WriteException {

		cellCustomizerProjectName.setBackground(Colour.YELLOW);
		cellCustomizerProjectName.setAlignment(Alignment.CENTRE);
		cellCustomizerProjectName.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellCustomizerProjectName.setFont(new WritableFont(WritableFont.ARIAL, FontSize.SIZE_12.getValue(),
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
		return cellCustomizerProjectName;
	}
}
