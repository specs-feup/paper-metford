#!/bin/bash

########################################################
# This script is assumed to be run inside the directory 
# containing the .jar files
#
# cd ~/libs-afm
# ~/feup/tools/scripts-kadabra/02-generate-kadabra-classpath.sh
# 
# Observe que path separator for Kadabra is always 
# a semicolon ";"
#
########################################################

# Listing directories
for d in $(ls -d */)
do 
	echo -n "$PWD/$d;"
done

# Listing jar files
for f in $(ls *.jar)
do 
	echo -n "$PWD/$f;"
done