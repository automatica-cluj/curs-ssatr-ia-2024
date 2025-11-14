# Quick Start Guide

Get started with ETL exercises in 3 simple steps:

## Step 1: Build the Project

```bash
mvn clean compile
```

## Step 2: Run the Interactive Menu

```bash
mvn exec:java
```

Or use the provided script:

```bash
./run.sh
```

## Step 3: Choose an Exercise

The interactive menu will present 5 exercises:

1. **Exercise 1**: Basic CSV to JSON transformation
2. **Exercise 2**: Data filtering and validation
3. **Exercise 3**: Data aggregation and grouping
4. **Exercise 4**: Joining multiple data sources
5. **Exercise 5**: Complete ETL pipeline with database

## Running Individual Exercises

You can also run exercises directly:

```bash
# Exercise 1
mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise1.Exercise1BasicTransformation"

# Exercise 2
mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise2.Exercise2FilteringValidation"

# Exercise 3
mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise3.Exercise3Aggregation"

# Exercise 4
mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise4.Exercise4DataJoining"

# Exercise 5
mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise5.Exercise5DatabasePipeline"
```

## View Results

After running exercises, check the output folder:

```bash
ls -la src/main/resources/data/output/
```

You'll find:
- JSON files with processed data
- Database file (from Exercise 5)
- Validation error reports

## What's Next?

After completing all exercises:
1. Review the generated output files
2. Read the code to understand the implementation
3. Try modifying the business rules
4. Add your own transformations
5. Read the full README.md for advanced challenges

## Troubleshooting

**Build fails?**
```bash
mvn clean compile
```

**Can't find output files?**
```bash
# Output directory is created automatically
mkdir -p src/main/resources/data/output
```

**Want to reset?**
```bash
# Remove all output files
rm -rf src/main/resources/data/output/*
```

Happy learning!
