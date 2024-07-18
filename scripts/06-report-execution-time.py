#!/usr/bin/python3

###############################################
# This script computes the time report spent for each mutant during execution in the emulator.
# Although there is more steps on running traditional mutants, once we need to compile and install 
# each mutant in the emulator, the scripts uses begin and end marks to compute time.
#
# 06-report-execution-time.py EMULATOR|RUN ids-both.csv full-execution-time.csv
# 
# EMULATOR mode means that we compute time including start and stopping emulator before each mutant execution
# RUN mode means that we only compute the time for running mutants (discarding emulator startup time)
#
# ids-both.csv - file mapping mutant textual ids to int ids (see 03-mutantions-info.py)
###############################################

import pandas as pd
import sys

BEGIN_EMULATOR="STARTING EMULATOR FOR MUTANT"
END_EMULATOR="STOPPING EMULATOR FOR MUTANT"
BEGIN_MUTANT_RUN="BEGIN RUNNING MUTANT"
END_MUTANT_RUN="END RUNNING MUTANT"

def extractData(mode, line):
    data = line.split()
    operator = data[-1] # last element in data is the operator id
    dateTime = data[0] + " " + data[1]
    return operator, dateTime

def mapIds(file_name):
    ids={}
    revIds={}
    count=0
    with open(file_name) as f:
        for line in f:
            data = line.split(";")
            count = count + 1
            ids[data[0].strip()]=count
            if (int(data[1]) == -2):
                revIds[count] = data[0].strip()
            else:
                revIds[int(data[1])] = data[0].strip()

    return ids,revIds

def listExecutionTime(ids,revIds,file_name,mode):
    executionTime=[]
    enable = False
    newMutant = ""
    beginTime = ""
    endTime = ""
    beginStr = BEGIN_EMULATOR
    endStr = END_EMULATOR

    # Start time is different considerig EMULATOR or just RUN time.
    if (mode != None and "RUN" == mode.upper()):
        beginStr = BEGIN_MUTANT_RUN
        endStr = END_MUTANT_RUN

    with open(file_name) as f:
        for line in f:
            if (beginStr in line):
                operator,beginTime = extractData(mode,line)
            elif (endStr in line):
                operator,endTime = extractData(mode,line)

                # Convert operator from int to str
                if (operator.lstrip('-+').isdigit()):
                    operator = revIds[int(operator)]

                start = pd.to_datetime(beginTime, format="%Y-%m-%d %H:%M:%S.%f%z")
                end = pd.to_datetime(endTime, format="%Y-%m-%d %H:%M:%S.%f%z")
                diff = end - start
                index = ids[operator]
                executionTime.append([index,operator,{"start":beginTime,"end":endTime,"diff":diff.seconds}])
    return executionTime

def main():
    # total arguments
    n = len(sys.argv)
    if (n < 3):
        print("Usage: 06-report-execution-time.py EMULATOR|RUN ids-both.txt full-execution-time.csv")
        exit(1)
    else:
        mode = sys.argv[1]
        idsFileName = sys.argv[2]
        fileName = sys.argv[3]
        
        ids,revIds = mapIds(idsFileName)
        executionTime = listExecutionTime(ids,revIds,fileName, mode)
        print("Id_Index;ExecTime(s);Mutant")
        for mutant in executionTime:
            print("%d;%d;%s" % (mutant[0],mutant[2]["diff"],mutant[1]))

if (__name__ == "__main__"):
    main()