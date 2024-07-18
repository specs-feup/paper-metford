#/bin/bash

if [ "$#" -ne 2 ]
then
  echo "Usage: 04-mutant-generation-traditional-time-space.sh <output mutant code directory> <data output file>"
  exit 1
else
	DIR=$1
	OUT=$2

	echo "Generating $OUT file"
	echo "Modified;Created;Size;File;Operator" > $OUT

	for m in $(cat $DIR/ids.txt)
	do
		JAVA_FILES=$(find $DIR/$m -name "*.java")

		for f in $JAVA_FILES
		do
			echo -n "."
			data=$(stat -c "%y;%w;%s;%n" $f)
			echo -n "$data;$m" >> $OUT
			echo "" >> $OUT
		done
	done
	echo ""	
	echo "Finish"
fi