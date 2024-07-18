#/bin/bash

# This script allow to execute mutants from a given <project directory>. The project directory must contain a file
# with all mutation ids and a file with random numbers, corresponding to lines in the ids file. We should also provide 
# a to and from numbers corresponding to start and end of index to running mutant ids.
#
# For instance, considering Aeges-schamata project, command below allows the execution of the 10 first mutants considering
# the random ordem sequence of random.txt file:
#
# 05-mutant-exec-schemata-emulator.sh /home/auri/feup/tools/Projects/android/Aegis-schemata ids.txt random.txt 1 10 Pixel_6_Pro_API_30
#

if [ "$#" -ne 6 ]
then
  echo "Usage: 05-mutant-exec-schemata-emulator.sh <project directory> <mutation id file list> <random file number list> <from> <to> <emulator>"
  exit 1
else
	PRJ=$1
	IDS=$2
	RAN=$3
	FROM=$4
	TO=$5
	EMULATOR=${6}

	emulator -avd $EMULATOR &
	sleep 5

	cd $PRJ
	OUT=output-gradlew-${FROM}-${TO}
	if [ ! -d "$OUT" ]; then
  		mkdir $OUT
	fi
	
	date --rfc-3339=ns |tr -d "\n" > $OUT/execution-time.txt
	echo " -  START TIME" >> $OUT/execution-time.txt

	randList=$(sed -n "${FROM},${TO}p" < $RAN)

	# Always run ORIGINAL_PROGRAM and the sequence of mutants
	for r in $(echo "1 $randList")
	do
		# Recovering mutation ID from position r
		m=$(sed -n "${r}p" < $IDS)
		echo "ID Position: $r - Mutant ID: $m"

		date --rfc-3339=ns |tr -d "\n" >> $OUT/execution-time.txt
		echo " -  MUTANT $m" >> $OUT/execution-time.txt

		# Use debug.MUID does not demand a rooted device
		adb shell setprop debug.MUID "${m}"
		cmd="./gradlew connectedAndroidTest"
		/usr/bin/time -o $OUT/$m.time --quiet -v -p ${cmd} >& ${OUT}/${m}.out

		# Saving testing report for each mutant
		cp -r app/build/reports/androidTests/connected ${OUT}/${m}.report
	done

	date --rfc-3339=ns |tr -d "\n" >> $OUT/execution-time.txt
	echo " -  END TIME" >> $OUT/execution-time.txt

	# Calculate killing summary by using Gradle exit status: 0 no failures; >0 run with failures
	grep Exit $OUT/*.time | awk 'BEGIN{FS=":"}{s=index($1,".");printf("%s;%d\n",substr($1,0,s-1),$3)}' > $OUT/summary.csv
	
	sleep 2
	# Killing emulator
	ps -ef | grep emulator | grep Sdk | kill `awk '{printf("%s ",$2)}'`
fi