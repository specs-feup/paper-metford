#/bin/bash

if [ "$#" -ne 2 ]
then
  echo "Usage: 04-mutant-generation-schemata-time-space.sh <output mutant code directory> <data output file>"
  exit 1
else
	DIR=$1
	OUT=$2
	JAVA_FILES=$(find $DIR -name "*.java")

	echo "Generating $OUT file"
	echo "Modified;Created;Size;File;Mutants" > $OUT

	for f in $JAVA_FILES
	do
		echo -n "."
		data=$(stat -c "%y;%w;%s;%n" $f)
		echo -n "$data" >> $OUT
		for m in $(cat $DIR/ids.txt)
		do
			mutants=$(grep $m $f | wc -l)
			echo -n ";$mutants" >> $OUT
		done
		echo "" >> $OUT
	done
	echo ""	
	echo "Finish"
fi