package com.github.marcosoliveirasoares94.external;

import lombok.Getter;
import lombok.Setter;

/**
 * External Dependencies VO
 * 
 * @author Marcos Oliveira Soares
 */
public class ExternalDependenciesVO {

	@Getter
	@Setter
	protected String groupID;
	@Getter
	@Setter
	protected String artifactID;
	@Getter
	@Setter
	protected String version;
	@Getter
	@Setter
	protected String packaging;
	protected static final int SIZE_ARRAY = 4;

	protected ExternalDependenciesVO() {
		super();
		this.groupID = "";
		this.artifactID = "";
		this.version = "";
		this.packaging = "";
	}

	public int getArraySize() {
		return SIZE_ARRAY;
	}

	protected String[] getHeaderWorksheet() {
		String[] headerFile = new String[SIZE_ARRAY];
		headerFile[ExternalDependenciesEnum.POSITION_0.getPosition()] = ExternalDependenciesEnum.POSITION_0
				.getDescription();
		headerFile[ExternalDependenciesEnum.POSITION_1.getPosition()] = ExternalDependenciesEnum.POSITION_1
				.getDescription();
		headerFile[ExternalDependenciesEnum.POSITION_2.getPosition()] = ExternalDependenciesEnum.POSITION_2
				.getDescription();
		headerFile[ExternalDependenciesEnum.POSITION_3.getPosition()] = ExternalDependenciesEnum.POSITION_3
				.getDescription();
		return headerFile;
	}
}
