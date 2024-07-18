#/bin/bash

# This script allow to execute mutants from a given <project directory>. The project directory must contain a file
# with all mutation ids and a file with random numbers, corresponding to lines in the ids file. We should also provide 
# a to and from numbers corresponding to start and end of index to running mutant ids.
#
# For instance, considering Aeges-schamata project, command below allows the execution of the 10 first mutants considering
# the random ordem sequence of random.txt file:
#
# Aegis example
#
# 05-adb-mutant-exec-traditional-device.sh /home/auri/feup/tools/Projects/android/Aegis-traditional /home/auri/feup/tools/Projects/output-aeges-traditional/mutated_project assemble app/build/outputs/apk/debug/app-debug.apk com.beemdevelopment.aegis.debug assembleAndroidTest app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk com.beemdevelopment.aegis.debug.test ids.txt random.txt test-case-names.txt com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner fullFail RQ8T903EZRX app 5 1 10
#
# AntennaPod example
#
# 05-adb-mutant-exec-traditional-device.sh /home/auri/feup/tools/Projects/android/AntennaPod-traditional /home/auri/feup/tools/Projects/output-antennapod-traditional/mutated_project assembleFreeDebug app/build/outputs/apk/free/debug/app-free-debug.apk assembleAndroidTest app/build/outputs/apk/androidTest/free/debug/app-free-debug-androidTest.apk ids.txt random.txt test-case-names.txt de.danoeh.antennapod.core.tests/androidx.test.runner.AndroidJUnitRunner fullFail RQ8T903EZRX app 5 1 10
#
#

if [ "$#" -ne 18 ]
then
  echo "Usage: 05-adb-mutant-exec-traditional-device.sh <project directory> <mutant source base directory> <app build task> <app apk> <android test build task> <android test apk> <mutation id file list> <random file number list> <test case list> <package/test executor> <fastFail|fullFail> <device> <main app dir name> <timeout in minutes> <from> <to>"
  exit 1
else
	# Script parameters
	PRJ=$1
	MUT=$2
	APPBUILDTASK=$3
	APPAPK=$4
	APPPKG=$5
	TESTBUILDTASK=$6
	TESTAPK=$7
	TESTPKG=$8
	IDS=$9
	RAN=${10}
	TS=${11}
	PKGEXEC=${12}
	STOP=${13}
	DEVICE=${14}
	MAINAPP=${15}
	TIMEOUT=${16}
	FROM=${17}
	TO=${18}

	cd $PRJ
	OUT=output-adb-${FROM}-${TO}
	if [ ! -d "$OUT" ]; then
  		mkdir $OUT
	fi

	date --rfc-3339=ns |tr -d "\n" > $OUT/full-execution-time.csv
	echo " - START TIME" &>> $OUT/full-execution-time.csv
	
	date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
	echo " - BEGIN BUILDING APP AND TEST APKs" &>> $OUT/full-execution-time.csv
	
	# Build android app and test apk
	./gradlew clean $APPBUILDTASK $TESTBUILDTASK &>> $OUT/apks-build.out

	date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
	echo " - END BUILDING APP AND TEST APKs" &>> $OUT/full-execution-time.csv

	randList=$(sed -n "${FROM},${TO}p" < $RAN)

	rm -f $OUT/summary.csv

	# Always run ORIGINAL_PROGRAM and the sequence of mutants
	for r in $(echo "$randList")
	do
		# Recovering mutation ID from position r
		m=$(sed -n "${r}p" < $IDS)
		# Dealing with case of ID equal 0
		#if [ -z "$m" ]
		#then
    #  m=0
		#fi 

		echo "ID Position: $r - Mutant ID: $m"

		if [ ! -d "$OUT/$m" ]; then
  		mkdir $OUT/$m
		fi		

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN INSTALLING TEST APK" &>> $OUT/full-execution-time.csv

		# Install test apk
		echo "ADB TEST APK INSTALL" &>> $OUT/$m/adb-intall.out
		adb -s $DEVICE install -t $TESTAPK &>> $OUT/$m/adb-intall.out

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - END INSTALLING TEST APK" &>> $OUT/full-execution-time.csv

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN RUNNING MUTANT $m" &>> $OUT/full-execution-time.csv

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN BUILDING MUTANT APK $m" &>> $OUT/full-execution-time.csv

		# Copying mutation source code
		rm -rf ${MAINAPP}/src/main/java/*
		cp -r $MUT/$m/* ${MAINAPP}/src/main/java/

		echo "BUILDING APK $m" &>> $OUT/$m/apks-build.out
		./gradlew $APPBUILDTASK &>> $OUT/$m/apks-build.out

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - END BUILDING MUTANT APK $m" &>> $OUT/full-execution-time.csv
		
		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - BEGIN INSTALLING MUTANT APK $m" &>> $OUT/full-execution-time.csv

		# Install app apk
		echo "ADB MUTANT $m" &>> $OUT/$m/adb-intall.out
		adb -s $DEVICE install $APPAPK &>> $OUT/$m/adb-intall.out

		date --rfc-3339=ns |tr -d "\n" &>> $OUT/full-execution-time.csv
		echo " - END INSTALLING MUTANT APK $m" &>> $OUT/full-execution-time.csv

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