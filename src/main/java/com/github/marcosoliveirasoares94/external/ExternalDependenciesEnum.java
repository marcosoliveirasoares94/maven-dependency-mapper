package com.github.marcosoliveirasoares94.external;

/**
 * Enum with the spreadsheet header.
 * 
 * @author Marcos Oliveira Soares
 */
public enum ExternalDependenciesEnum {

	POSITION_0(0, "groupID"), POSITION_1(1, "artifactID"), POSITION_2(2, "version"), POSITION_3(3, "packaging");

	private int position;
	private String description;

	ExternalDependenciesEnum(int position, String description) {
		this.position = position;
		this.description = description;
	}

	public int getPosition() {
		return position;
	}

	public String getDescription() {
		return description;
	}
}
