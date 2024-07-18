# Kadabra Generated Mutants

This directory contains generated mutants for each app. The complete directory structure is as follows:

Basically, for each app, there is a folder with a zip file containing all generated mutants according to a given mutation strategy. For the schemata strategy, once all mutants are encoded in a single project, there is a single project directory. For the traditional strategy, there are several copies of the project, each one containing a single mutation.

```
kadabra-output/
├── Aegis
│   ├── schemata-mutants
│   └── traditional-mutants
├── AmazeFileManager
│   ├── schemata-mutants
│   └── traditional-mutants
├── AntennaPod
│   ├── schemata-mutants
│   └── traditional-mutants
├── keepassdroid
│   ├── schemata-mutants
│   └── traditional-mutants
├── Omni-Notes
│   ├── schemata-mutants
│   └── traditional-mutants
├── README.md
└── simplenote-android
    ├── schemata-mutants
    └── traditional-mutants
```

