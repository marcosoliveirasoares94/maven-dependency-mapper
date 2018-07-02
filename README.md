# Maven Dependency Mapper

# Command to generate the maven dependencies of a given project and store in a given directory.
`"C:\Program Files\Maven\apache-maven-3.5.0\bin\mvn" org.apache.maven.plugins:maven-dependency-plugin:2.10:copy-dependencies -Dmdep.addParentPoms=true -Dmdep.copyPom=true -DoutputDirectory="C:\DevTools\Dependencies" -DincludeGroupIds=com.github.marcosoliveirasoares94,jars.com.github.marcosoliveirasoares94`

# Arguments of entry:

# [0] = Project name.
# [1] = Path of Dependencies.
	`Directory existence is required.`
# [2] = File path with Dependencies list highlighted in excel file.
	`The existence of the file "newDependencies.txt" is required, it serves to highlight the dependency found in the file "newDependencies.txt" in the excel worksheet.`

	`If the file "newDependencies.txt" exists, the content should be as follows:`
	`xxxx-xxxxxxx-xxx-0.0.0.0.0.jar`
	`xxxx-xxxxxxx-xxx-0.0.0.0.0.pom`

# [3] = Project Version.
# [4] = Destination of excel file.

Note:

Remember to change the items according to your groupID settings:

startsWith("com.github.marcosoliveirasoares94")
startsWith("jars.com.github.marcosoliveirasoares94")

They are present in the class: ExternalDependencies, method: addListExternalDependency