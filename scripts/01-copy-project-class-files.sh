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
# ~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD classes intermediates
# ~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD "*ebug" kotlin 
#
#########################################################
if [ "$#" -ne 3 ]
then
  echo "Usage: 01-copy-project-class-files.sh <path-to-android-project> <classes|kotlin-classes> <filter>"
  exit 1
else
	basePath=$1
	clazzPrefix=$2
	filter=$3

	cd $basePath
	
	if [ "$?" -eq 0 ]
	then
		OUT=libs-jar
		if [ ! -d "$OUT" ]; then
  		mkdir $OUT
		fi
		
		for clazz in $(find . -name $clazzPrefix | grep $filter)
		do
			if test -d $clazz; then
  			echo "Directory exists: $clazz"
  			cp -r $clazz $OUT/
  			echo "Copying $clazz to $OUT"
			fi
		done
	else
		echo "Cannot find ${basePath}"
	fi
fi