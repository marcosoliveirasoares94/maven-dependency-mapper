package com.github.marcosoliveirasoares94.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * Internal Dependencies VO
 * 
 * @author Marcos Oliveira Soares
 */
public class InternalDependenciesVO
{

   @Getter
   @Setter
   protected String key;
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
   @Getter
   @Setter
   protected String description;
   protected static final int SIZE_ARRAY = 6;

   protected InternalDependenciesVO()
   {
      super();
      this.key = "";
      this.groupID = "";
      this.artifactID = "";
      this.version = "";
      this.packaging = "";
      this.description = "";
   }

   public int getArraySize()
   {
      return SIZE_ARRAY;
   }

   protected String[] getHeaderWorksheet()
   {
      String[] headerfile = new String[SIZE_ARRAY];
      headerfile[InternalDependenciesEnum.POSITION_0.getPosition()] = InternalDependenciesEnum.POSITION_0.getDescription();
      headerfile[InternalDependenciesEnum.POSITION_1.getPosition()] = InternalDependenciesEnum.POSITION_1.getDescription();
      headerfile[InternalDependenciesEnum.POSITION_2.getPosition()] = InternalDependenciesEnum.POSITION_2.getDescription();
      headerfile[InternalDependenciesEnum.POSITION_3.getPosition()] = InternalDependenciesEnum.POSITION_3.getDescription();
      headerfile[InternalDependenciesEnum.POSITION_4.getPosition()] = InternalDependenciesEnum.POSITION_4.getDescription();
      headerfile[InternalDependenciesEnum.POSITION_5.getPosition()] = InternalDependenciesEnum.POSITION_5.getDescription();
      return headerfile;
   }
}
