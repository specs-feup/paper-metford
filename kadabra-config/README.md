# Kadabra Configuration Files

The directory `kadabra-config` contains each program's Kadabra configuration file.

The complete directory structure is as follows:

```
kadabra-config/
├── aeges-schemata.json
├── aeges-schemata.kadabra
├── aeges-traditional.json
├── aeges-traditional.kadabra
├── amazefilemanager-schemata.json
├── amazefilemanager-schemata.kadabra
├── amazefilemanager-traditional.json
├── amazefilemanager-traditional.kadabra
├── antennapod-schemata.json
├── antennapod-schemata.kadabra
├── antennapod-traditional.json
├── antennapod-traditional.kadabra
├── keepassdroid-schemata.json
├── keepassdroid-schemata.kadabra
├── keepassdroid-traditional.json
├── keepassdroid-traditional.kadabra
├── omni-notes-schemata.json
├── omni-notes-schemata.kadabra
├── omni-notes-traditional.json
├── omni-notes-traditional.kadabra
├── simplenote-schemata.json
├── simplenote-schemata.kadabra
├── simplenote-traditional.json
└── simplenote-traditional.kadabra
```

Each program (app) and each mutation strategy has two files. The one with the extension `.kadabra` contains information about the Kadabra general options and a reference for the corresponding `.json` file. The file with the extension `.json` contains all configurations about external dependencies (libraries) the app depends on to run, which mutation operators one decides to use, and which set of files will be mutated.

Moreover, the `.json` file also includes information about possible patches required for this program or which `.java` files we excluded from our analysis due to Spool limitations.

## How are these files used?

Before running Kadabra on a given program, you must copy both files (`.kadabra` and `.json`) to the `mutation-testing-v2\Kadabra` directory. Configuration files are supposed to be there to run correctly.

Observe that this is only necessary if you like to regenerate all mutants from the original program. Considering the configuration files mentioned above, the directory `kadabra-output` contains all the mutants that have already been generated and used in our experiment.