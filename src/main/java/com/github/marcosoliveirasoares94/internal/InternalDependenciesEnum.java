package com.github.marcosoliveirasoares94.internal;

/**
 * Enum with the spreadsheet header.
 * 
 * @author Marcos Oliveira Soares
 */
public enum InternalDependenciesEnum
{

      POSITION_0(0, "key"), POSITION_1(1, "groupID"), POSITION_2(2, "artifactID"), POSITION_3(3, "version"), POSITION_4(4,
         "packaging"), POSITION_5(5, "description");

   private int position;
   private String description;

   InternalDependenciesEnum(int position, String description)
   {
      this.position = position;
      this.description = description;
   }

   public int getPosition()
   {
      return position;
   }

   public String getDescription()
   {
      return description;
   }
}
