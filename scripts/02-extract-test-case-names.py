#!/usr/bin/python3
import json
import sys

####################################################################
# This python script extract test case names to run them individualy
# by adb.
# It depends of the execution of adb execution to log test set
# information, similar to the example below:
#
# adb shell am instrument -w -r --no-window-animation -e log true \
# -e package com.beemdevelopment.aegis \
# com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner >& test-cases.info
#
# The output file from the above command above is the input to this
# script.
#
# 02-extract-test-case-names.py test-cases.info
# 
###################################################################

def listTestCases(file_name):
    testCases=[]
    enable = False
    newTC = ""
    with open(file_name) as f:
        for line in f:
            data = line.strip().split(":")
            if (len(data) == 2):
                status = data[0].strip()
                info = data[1].strip()
                if ( (status == "INSTRUMENTATION_STATUS_CODE") and (info == "1") ):
                    enable = True #found a new test case - enable data collection
                else: 
                    if (enable): # extracting and combining test set and test case names
                        desc = info.split("=")
                        if (len(desc) == 2):
                            if (desc[0] == "class"):
                                newTC = desc[1]+"#"
                            elif (desc[0] == "test"):
                                newTC = newTC+desc[1]
                                testCases.append(newTC)
                                enable = False

    return testCases


def main():
    # total arguments
    n = len(sys.argv)
    if (n < 1):
        print("Usage: 02-extract-test-case-names.py test-cases.info")
        exit(1)
    else:
        fileName = sys.argv[1]
        
        testCases = listTestCases(fileName) 
        for tc in testCases:
            print(tc)

if (__name__ == "__main__"):
    main()