#!/bin/bash

#########################################################
# In projects with subprojects, we have several R.jar
# files and all of them must be in the classpat.
# To avoid file name colision, this script rename
# R.jar files by including the subproject name on the
# R.jar file name.
#
# This script must be run after the correct gradlew task
# to build the R.jar files.
#
# In case of AmazeFileManager:
#
# 	./gradlew clean assembleFdroidDebug
#
# cd ~/feup/tools/Projects/android/AmazeFileManager
# ~/feup/tools/scripts-kadabra/01-r-jar-rename.sh $PWD
#
#########################################################
if [ "$#" -ne 1 ]
then
  echo "Usage: 01-r-jar-rename.sh <path-to-android-project>"
  exit 1
else
	basePath=$1

	cd $basePath
	
	if [ "$?" -eq 0 ]
	then
		OUT=libs-jar
		if [ ! -d "$OUT" ]; then
  		mkdir $OUT
		fi
		
		for R in $(find . -name "R.jar")
		do
			prj=$(echo $R | rev | cut -d'/' -f6- | rev | sed 's_\./__g' | sed 's_/_-_g')
			cp $R $OUT/R-${prj}.jar

			echo "File $OUT/R-${prj}.jar found and renamed"
		done
	else
		echo "Cannot find ${basePath}"
	fi
fi