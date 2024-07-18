#!/usr/bin/python3
import json
import sys

####################################################################
# This python script only presents mutants statistics based on
# MutationInfo.json file.
#
# cd ~/feup/tools/Projects/output-aeges-schemata/mutated_project/
# 03-mutations-info.py MutationInfo.json <-sta | -idstr | idint | idboth | -files >
# 
# Possible options:
# -sta: presents number of mutants per mutation operator
# -ids: presents the list of mutation IDs in the order of 
#       appearancein .json file.
###################################################################

arithmeticOperators = ["+", "-", "*", "/", "%"]
bitwiseOperators = ["&", "|", "^"]
shiftOperators = ["<<", ">>", ">>>"]
relationalOperators = ["==", "!=", ">", "<", ">=", "<="]
logicalOperators = ["&&", "||"]
assignmentOperators = ["=", "+=", "-=", "*=", "/=", "%="]

def countMutations(file_name):
    mutations={}
    
    with open(file_name, 'r', encoding='utf-8') as f:
        try:
            my_data = json.load(f)  # parse the JSON with load()
    
            for line in my_data:
                if ('error' in line):
                    operator_category = 'error'
                    error_msg = line["error"]
                    file_path = line["args"][2].split(",")
                    print(error_msg)
                    print("\t%s" % file_path[1])
                else:
                    operator_category = ''
                    operator = line['mutantion']['operator']
                    if (operator == 'BinaryMutator'):
                        target = line['mutantion']['mutationOperatorArgumentsList'][0]
                        if (target in arithmeticOperators):
                            operator_category = 'Arithmetic Operator'
                        elif (target in bitwiseOperators):
                            operator_category = 'Bitwise Operator'
                        elif (target in shiftOperators):
                            operator_category = 'Shift Operator'
                        elif (target in relationalOperators):
                            operator_category = 'Comparison Operator'
                        else: # (target in logicalOperators)
                            operator_category = 'Logical Operator'
                    elif (operator == 'AssignmentOperatorMutator'):
                        operator_category = 'Assignment Operator'
                    else:
                        operator_category = operator
                        
                if (operator_category in mutations):
                    mutations[operator_category] = mutations[operator_category] + 1
                else:
                    mutations[operator_category] = 1
            return mutations
        except BaseException as e:
            print('The file contains invalid JSON')
            print(repr(e))

def mutationsIDs(file_name):
    ids=[['ORIGINAL_PROGRAM',-1]] # append a string different from mutation ID to run tests on Original Program
    
    with open(file_name, 'r', encoding='utf-8') as f:
        try:
            my_data = json.load(f)  # parse the JSON with load()

            mutantId=""
            mutantIdNumber=-1
            for line in my_data:
                if ('mutantId' in line):
                    mutantId = line['mutantId']
                    mutantIdNumber = -2
                    if ('mutantIdNumber' in line):
                        mutantIdNumber = line['mutantIdNumber']
                    ids.append([mutantId,mutantIdNumber])
            return ids
        except BaseException as e:
            print('The file contains invalid JSON')

def mutationsIDsFiles(file_name):
    ids=[] # append a string different from mutation ID to run tests on Original Program
    
    with open(file_name, 'r', encoding='utf-8') as f:
        try:
            my_data = json.load(f)  # parse the JSON with load()

            mutantId=""
            for line in my_data:
                if ('mutantId' in line):
                    mutantId = line['mutantId']
                    mutantIdNumber = -1
                    if ('mutantIdNumber' in line):
                        mutantIdNumber = line['mutantIdNumber']
                    mutationLine=-1
                    if ('mutationLine' in line):
                        mutationLine = line['mutationLine']
                    filePath=""
                    if ('filePath' in line):
                        filePath = line['filePath']
                    ids.append([mutantId,mutantIdNumber,mutationLine,filePath])
            return ids
        except BaseException as e:
            print('The file contains invalid JSON')


def main():
    # total arguments
    n = len(sys.argv)
    if (n < 3):
        print("Usage: mutations-info.py MutationInfo.json <-sta|-idstr | -idint | -idboth | -files>")
        exit(1)
    else:
        option = sys.argv[2].lower()
        
        if (option == "-sta"):
            info = countMutations(sys.argv[1]) 
            if (info != None):
                for m in info:
                    print(m+";"+str(info[m]))
        elif(option in ["-idstr","-idint","-idboth"]):
            ids = mutationsIDs(sys.argv[1])
        
            if (option == "-idstr"):
                for i in range(len(ids)):
                    print(ids[i][0])
            elif(option == "-idint"):
                for i in range(len(ids)):
                    print(ids[i][1])
            else:
                for i in range(len(ids)):
                    print("%s;%d" % (ids[i][0],ids[i][1]))
        elif(option == "-files"):
            ids = mutationsIDsFiles(sys.argv[1])
            for i in range(len(ids)):
                print("%s;%d;%d;%s" % (ids[i][0],ids[i][1],ids[i][2],ids[i][3]))
        else:
            print("Error: valid options are -sta for mutation statistics or -idstr | idint | idboth | -files for mutations IDs")
            exit(1)    

if (__name__ == "__main__"):
    main()