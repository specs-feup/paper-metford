#!/bin/bash

#########################################################
# After running ./gradlew inside a project, this script 
# converts .aar dependency files to .jar dependency file
#
# In projects with subprojects, several 
# dependenciesDirectory is created inside of each 
# subproject. We looking for and convert .aar into .jar
# in all of them.
#
# The scripts create a directory libs-jar and copy all
# .jar file inside it.
# 
# AmazeFileManager
#
# cd ~/feup/tools/Projects/android/AmazeFileManager
# ~/feup/tools/scripts-kadabra/01-aar2jar.sh $PWD
#
#########################################################
if [ "$#" -ne 1 ]
then
  echo "Usage: 01-aar2jar.sh <path-to-android-project>"
  exit 1
else
	basePath=$1

	cd $basePath
	# Generating dependencies
	#./gradlew downloadDependencies
	
	if [ "$?" -eq 0 ]
	then
		OUT=libs-jar
		if [ ! -d "$OUT" ]; then
  		mkdir $OUT
		fi
		
		for prj in $(find ${basePath} -name "dependenciesDirectory")
		do
			# Converting aar dependencies to jar dependencies
			cd ${prj}
			for aarFile in $(ls *.aar)
			do
				echo $aarFile
				jarFile="${aarFile%.*}.jar"
				unzip -d . $aarFile classes.jar
				mv classes.jar $jarFile
				echo "File $jarFile created - File $aarFile excluded"
				rm $aarFile
			done
			cp ${prj}/*.jar ${basePath}/$OUT
			echo "All aar dependencias converted to jar at ${prj}"
		done
	else
		echo "Cannot find ${basePath}"
	fi
fi