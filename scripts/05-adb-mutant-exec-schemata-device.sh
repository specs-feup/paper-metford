#!/bin/bash

# This script allow to execute mutants from a given <project directory>. The project directory must contain a file
# with all mutation ids and a file with random numbers, corresponding to lines in the ids file. We should also provide 
# a to and from numbers corresponding to start and end of index to running mutant ids.
#
# For instance, considering Aeges-schamata project, command below allows the execution of the 10 first mutants considering
# the random ordem sequence of random.txt file:
# 
# Aeges device
# 05-adb-mutant-exec-schemata-device.sh /home/auri/feup/tools/Projects/android/Aegis-schemata assemble app/build/outputs/apk/debug/app-debug.apk com.beemdevelopment.aegis.debug assembleAndroidTest app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk com.beemdevelopment.aegis.debug.test ids.txt random.txt test-case-names.txt com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner fullFail RQ8T903EZRX 5 1 10
# 
# AntennaPod
# 05-adb-mutant-exec-schemata-device.sh /home/auri/feup/tools/Projects/android/AntennaPod-schemata assembleFreeDebug app/build/outputs/apk/free/debug/app-free-debug.apk assembleAndroidTest app/build/outputs/apk/androidTest/free/debug/app-free-debug-androidTest.apk ids.txt random.txt test-case-names.txt de.danoeh.antennapod.core.tests/androidx.test.runner.AndroidJUnitRunner fullFail Pixel_6_Pro_API_30 5 1 10
#

if [ "$#" -ne 16 ]
then
  echo "Usage: 05-adb-mutant-exec-schemata-device.sh <project directory> <app build task> <app apk> <apk package name> <android test build task> <android test apk> <test apk package name> <mutation id file list> <random file number list> <test case list> <package/test executor> <fastFail|fullFail> <device> <timeout in minutes> <from> <to>"
  exit 1
else
	# Script parameters
	PRJ=$1
	APPBUILDTASK=$2
	APPAPK=$3
	APPPKG=$4
	TESTBUILDTASK=$5
	TESTAPK=$6
	TESTPKG=$7
	IDS=$8
	RAN=$9
	TS=${10}
	PKGEXEC=${11}
	STOP=${12}
	DEVICE=${13}
	TIMEOUT=${14}
	FROM=${15}
	TO=${16}

	cd $PRJ
	OUT=output-adb-${FROM}-${TO}
	if [ ! -d "$OUT" ]; then
  		mkdir $OUT
	fi
	
	date --rfc-3339=ns |tr -d "\n" > $OUT/full-execution-time.csv
	echo " - START TIME" >> $OUT/full-execution-time.csv
	
	date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
	echo " - BEGIN BUILDING APP AND TEST APK" &>> $OUT/full-execution-time.csv

	# Build app and android test apks
  ./gradlew clean $APPBUILDTASK $TESTBUILDTASK &>> $OUT/apks-build.out

	date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
	echo " - END BUILDING APP AND TEST APK" &>> $OUT/full-execution-time.csv

	randList=$(sed -n "${FROM},${TO}p" < $RAN)

	rm -f $OUT/summary.csv

	# Always run ORIGINAL_PROGRAM and the sequence of mutants
	for r in $(echo "$randList")
	do
		# Recovering mutation ID from position r
		m=$(sed -n "${r}p" < $IDS)
		#if [ -z "$m" ]
		#then
    #  m=0
		#fi 
		echo "ID Position: $r - Mutant ID: $m"

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN INSTALLING APP AND TEST APK" &>> $OUT/full-execution-time.csv

		# Install app and test apk
		echo "ADB APP APK INSTALL" &> $OUT/adb-intall.out
		adb -s $DEVICE install $APPAPK &>> $OUT/adb-intall.out
		echo "ADB TEST APK INSTALL" &>> $OUT/adb-intall.out
		adb -s $DEVICE install -t $TESTAPK &>> $OUT/adb-intall.out

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - END INSTALLING TEST APK" &>> $OUT/full-execution-time.csv

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN RUNNING MUTANT $m" &>> $OUT/full-execution-time.csv

		# Use debug.MUID does not demand a rooted device
		adb -s $DEVICE shell setprop debug.MUID "${m}"

		if [ ! -d "$OUT/$m" ]; then
  		mkdir $OUT/$m
		fi

		echo -n "$m" >> $OUT/summary.csv

		for tc in $(cat $TS)
		do
			cmd="timeout ${TIMEOUT}m adb -s $DEVICE shell am instrument -w -r --no-window-animation -e class $tc $PKGEXEC"
			cmdtime=$(date)
			echo "$cmd - on $cmdtime - ID Position: $r - Mutant ID: $m"
			/usr/bin/time -o $OUT/$m/$tc.time --quiet -v -p ${cmd} >& ${OUT}/${m}/$tc.out
			# Check if command ended by timeout
			if [ "$?" -eq 124 ]
			then
				status=124
			else
				status=$(awk 'BEGIN{cont=0}{if ($1=="INSTRUMENTATION_STATUS_CODE:"){cont++;if(cont==2) print $2}}' ${OUT}/${m}/$tc.out)
				# In error situations there is only one "INSTRUMENTATION_STATUS_CODE:" string 
				if [ -z "$status" ]
				then
					status=$(awk '{if ($1=="INSTRUMENTATION_STATUS_CODE:") print $2}' ${OUT}/${m}/$tc.out)
			  fi
			fi
		  echo -n ";$status" >> $OUT/summary.csv

			if [[ "$STOP" == "fastFail" && $status -ne 0 ]]
   		then
      	break
   		fi
		done
		
		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - END RUNNING MUTANT $m" &>> $OUT/full-execution-time.csv

		# Unistall app and test apk
		echo "ADB APP APK UNINSTALL" &>> $OUT/$m/adb-intall.out
		adb -s $DEVICE uninstall $APPPKG &>> $OUT/$m/adb-intall.out
		echo "ADB TEST APK UNINSTALL" &>> $OUT/$m/adb-intall.out
		adb -s $DEVICE uninstall $TESTPKG &>> $OUT/$m/adb-intall.out

		echo "" >> $OUT/summary.csv
	done

	date --rfc-3339=ns |tr -d "\n" >> $OUT/full-execution-time.csv
	echo " - END TIME" >> $OUT/full-execution-time.csv

fi