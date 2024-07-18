# Paper METFORD Repository

This repository contains the artifacts used in the METFORD's paper. The high-level directory structure is as follows:

```
paper-metford/
├── AndroidMicroBenchmark
├── bin
├── kadabra-config
├── kadabra-output
├── LICENSE
├── mutation-testing-v2
├── README.md
├── reports
├── scripts
└── subjects
```

Below, we briefly describe each subdirectory's content. On entering a subdirectory, there is another README.md better explaining its content.

## `AndroidMicroBenchmark` directory

This directory contains a Gradle project with the micro-benchmark we used to evaluate alternative approaches to implementing mutant schemata. 

## `bin` directory

The directory contains the Kadabra tool used in our experiment.

## `kadabra-config` directory

The directory contains the Kadabra configuration files for each app and each mutation strategy (schemata or traditional).

## `kadabra-output` directory

The directory contains all generated mutants for each app concerning the Kadabra configuration files.

## `mutation-testing-v2` directory

The directory contains Kadabra mutation operator implementations and patches.

## `reports` directory

The directory contains all generated reports for each app and mutation strategy. Reports are generated after scripts are executed.

## `scripts` directory

The directory contains the complete set of scripts used in the experiment.

## `subjects` directory

The directory contains a frozen version of each app used in the experiment.