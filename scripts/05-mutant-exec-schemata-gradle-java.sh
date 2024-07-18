#/bin/bash

# This script allow to execute mutants from a given <project directory>. The project directory must contain a file
# with all mutation ids and a file with random numbers, corresponding to lines in the ids file. We should also provide 
# a to and from numbers corresponding to start and end of index to running mutant ids.
#
# For instance, considering java-jwt project, command below allows the execution of the 10 first mutants considering
# the random ordem sequence of random.txt file:
#
# 05-mutant-exec-schemata-gradle-java.sh /home/auri/feup/tools/Projects/java/java-jwt-schemata ids.txt random.txt 1 10 fastFail
#

if [ "$#" -ne 5 ]
then
  echo "Usage: 05-mutant-exec-schemata-gradle-java.sh <project directory> <mutation id file list> <random file number list> <from> <to>"
  exit 1
else
	# Script parameters
	PRJ=$1
	IDS=$2
	RAN=$3
	FROM=$4
	TO=$5

	cd $PRJ
	OUT=output-schemata-${FROM}-${TO}
	if [ ! -d "$OUT" ]; then
  		mkdir $OUT
	fi
	
	date --rfc-3339=ns |tr -d "\n" > $OUT/full-execution-time.csv
	echo " - START TIME" >> $OUT/full-execution-time.csv
	
	date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
	echo " - BEGIN BUILDING PROJECT AND TEST CLASSES" &>> $OUT/full-execution-time.csv

	# Build app and android test apks
	./gradlew build &> $OUT/gradle-build-project.out

	date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
	echo " - END BUILDING PROJECT AND TEST CLASSES" &>> $OUT/full-execution-time.csv

	randList=$(sed -n "${FROM},${TO}p" < $RAN)

	rm -f $OUT/summary.csv

	# Always run ORIGINAL_PROGRAM and the sequence of mutants
	for r in $(echo "1 $randList")
	do
		# Recovering mutation ID from position r
		m=$(sed -n "${r}p" < $IDS)
		echo "ID Position: $r - Mutant ID: $m"

		if [ ! -d "$OUT/$m" ]; then
  		mkdir $OUT/$m
		fi

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN RUNNING MUTANT $m" &>> $OUT/full-execution-time.csv

		echo -n "$m" >> $OUT/summary.csv

		cmd="./gradlew test jacocoTestReport -DMUID=$m"
		echo "$cmd"
		/usr/bin/time -o $OUT/$m/$m.time --quiet -v -p ${cmd} >& ${OUT}/${m}/$m.out
		# Calculate killing summary by using Gradle exit status: 0 no failures; >0 run with failures
		grep Exit $OUT/$m/$m.time | awk '{printf(";%d",$3)}' >> $OUT/summary.csv
		
		cp -r $PRJ/lib/build/reports $OUT/$m/

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - END RUNNING MUTANT $m" &>> $OUT/full-execution-time.csv

		echo "" >> $OUT/summary.csv
	done

	date --rfc-3339=ns |tr -d "\n" >> $OUT/full-execution-time.csv
	echo " - END TIME" >> $OUT/full-execution-time.csv
fi