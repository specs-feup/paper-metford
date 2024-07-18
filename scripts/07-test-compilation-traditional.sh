#/bin/bash

# This script checks wether traditional mutants compile successful before running experiment.
#
# 07-test-compilation-traditional.sh /home/auri/feup/tools/Projects/android/Aegis-traditional /home/auri/feup/tools/Projects/output-aeges-traditional/mutated_project ids.txt :app:compileDebugJavaWithJavac
#

if [ "$#" -ne 5 ]
then
  echo "Usage: 07-test-compilation-traditional.sh <project directory> <mutant source base directory> <ids> <gradle task> <main app>" 
  exit 1
else
	# Script parameters
	PRJ=$1
	MUT=$2
	IDS=$3
	TASK=$4
	APP=$5

  cd $PRJ

	OUT=compilation-test
	if [ ! -d "$OUT" ]; then
  		mkdir $OUT
	fi
  
  echo -n "" > $OUT/compilation-summary.csv

	# Always run ORIGINAL_PROGRAM and the sequence of mutants
	r=1
	for m in $(cat ${IDS})
	do
		echo "ID Position: $r - Mutant ID: $m"
		echo -n "$m" >> $OUT/compilation-summary.csv

		# Copying mutation source code
		rm -rf $APP/src/main/java/*
		cp -r $MUT/$m/* $APP/src/main/java/

		./gradlew $TASK &>> $OUT/$m-gradlew.out

		echo ";$?" >> $OUT/compilation-summary.csv
		r=$((r+1))
	done
fi