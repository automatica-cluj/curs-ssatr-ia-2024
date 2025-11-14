# ETL Exercises - Learning ETL Concepts

This project contains a comprehensive set of exercises designed to teach students the fundamental concepts of ETL (Extract, Transform, Load) data processing.

## What is ETL?

ETL stands for:
- **Extract**: Reading data from source systems (files, databases, APIs, etc.)
- **Transform**: Cleaning, validating, filtering, aggregating, or enriching the data
- **Load**: Writing the processed data to a destination (files, databases, data warehouses, etc.)

ETL is a critical process in data engineering, data warehousing, and business intelligence.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Basic understanding of Java programming
- Familiarity with CSV and JSON file formats

## Project Structure

```
demo-etl-exercises/
├── src/main/java/ro/ucv/ace/etl/
│   ├── Main.java                          # Interactive menu to run exercises
│   ├── model/                              # Data models
│   │   ├── Customer.java
│   │   ├── Sale.java
│   │   └── Product.java
│   ├── exercise1/                          # Exercise 1: Basic transformation
│   │   └── Exercise1BasicTransformation.java
│   ├── exercise2/                          # Exercise 2: Filtering & validation
│   │   └── Exercise2FilteringValidation.java
│   ├── exercise3/                          # Exercise 3: Aggregation
│   │   └── Exercise3Aggregation.java
│   ├── exercise4/                          # Exercise 4: Data joining
│   │   └── Exercise4DataJoining.java
│   └── exercise5/                          # Exercise 5: Database pipeline
│       └── Exercise5DatabasePipeline.java
├── src/main/resources/data/
│   ├── input/                              # Sample input data
│   │   ├── customers.csv
│   │   ├── sales.csv
│   │   └── products.csv
│   └── output/                             # Generated output files
├── pom.xml
└── README.md
```

## Getting Started

### 1. Build the Project

```bash
cd exemple_curs/demo-etl-exercises
mvn clean compile
```

### 2. Run the Interactive Menu

```bash
mvn exec:java
```

This will launch an interactive menu where you can select which exercise to run.

### 3. Run Individual Exercises

You can also run individual exercises directly:

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

## Exercises Overview

### Exercise 1: Basic CSV to JSON Transformation

**Difficulty**: Beginner

**Learning Objectives**:
- Read data from CSV files
- Parse CSV records into Java objects
- Write data to JSON format
- Understand basic ETL pipeline structure

**What it does**:
- Extracts customer data from `customers.csv`
- Transforms CSV rows into Customer objects
- Loads the data to `customers.json`

**Key Concepts**:
- File I/O operations
- CSV parsing with OpenCSV library
- JSON serialization with Jackson
- Basic ETL pattern

**Output**: `src/main/resources/data/output/customers.json`

---

### Exercise 2: Data Filtering and Validation

**Difficulty**: Intermediate

**Learning Objectives**:
- Apply business rules to filter data
- Validate data quality
- Handle invalid records
- Separate valid and invalid data

**What it does**:
- Extracts sales data from `sales.csv`
- Applies filtering rules:
  - Minimum total amount threshold (50)
  - Category whitelist (Electronics, Furniture)
  - Required field validation
  - Data type validation
- Loads valid sales to one file, errors to another

**Key Concepts**:
- Data quality checks
- Business rule implementation
- Error handling and logging
- Data validation patterns

**Output**:
- `src/main/resources/data/output/filtered_sales.json`
- `src/main/resources/data/output/validation_errors.json`

---

### Exercise 3: Data Aggregation and Grouping

**Difficulty**: Intermediate

**Learning Objectives**:
- Aggregate data using grouping operations
- Calculate statistics (sum, average, count)
- Use Java Streams for data processing
- Generate analytical reports

**What it does**:
- Extracts sales data
- Performs multiple aggregations:
  - Sales by category
  - Sales by customer
  - Daily sales trends
  - Top products by revenue
  - Overall statistics
- Loads aggregated results to JSON

**Key Concepts**:
- Group by operations
- Statistical aggregations
- Stream processing in Java
- Data summarization

**Output**: `src/main/resources/data/output/sales_aggregations.json`

---

### Exercise 4: Joining Multiple Data Sources

**Difficulty**: Advanced

**Learning Objectives**:
- Combine data from multiple sources
- Perform inner and left joins
- Enrich data with related information
- Handle missing relationships

**What it does**:
- Extracts data from three sources:
  - Customers
  - Sales
  - Products
- Joins the data to create enriched sales records
- Creates customer purchase summaries
- Identifies data quality issues (missing references)

**Key Concepts**:
- Data joining patterns (inner join, left join)
- Lookup tables and indexes
- Data enrichment
- Referential integrity

**Output**: `src/main/resources/data/output/enriched_sales.json`

---

### Exercise 5: Complete ETL Pipeline with Database

**Difficulty**: Advanced

**Learning Objectives**:
- Load data into a relational database
- Create database schemas
- Use batch operations for performance
- Implement transactions
- Query data from database
- Generate reports

**What it does**:
- Creates a complete database schema (customers, products, sales)
- Extracts data from CSV files
- Validates referential integrity
- Loads data using batch inserts
- Generates multiple reports:
  - Sales by category
  - Top customers
  - Daily sales trends
  - Inventory alerts

**Key Concepts**:
- JDBC programming
- Schema creation (DDL)
- Batch inserts for performance
- Transaction management
- Database indexes
- SQL queries and reports
- H2 embedded database

**Output**:
- `src/main/resources/data/output/sales_db.mv.db` (H2 database file)
- Console reports with database insights

---

## Sample Data

The project includes three sample datasets:

### customers.csv
Contains customer information:
- Customer ID, name, email
- Age, city, country
- Registration date

### sales.csv
Contains sales transactions:
- Sale ID, customer ID
- Product name, quantity, unit price
- Sale date, category

### products.csv
Contains product inventory:
- Product ID, product name
- Category, stock quantity
- Supplier, reorder level

## Learning Path

We recommend completing the exercises in order:

1. **Start with Exercise 1** to understand the basic ETL structure
2. **Move to Exercise 2** to learn data transformation and validation
3. **Practice Exercise 3** to master aggregation and analytical transformations
4. **Work through Exercise 4** to understand data joining
5. **Complete Exercise 5** to see a full ETL pipeline with database storage

## Extending the Exercises

After completing all exercises, try these challenges:

### Beginner Challenges
1. Add more validation rules to Exercise 2
2. Create additional aggregations in Exercise 3
3. Export data to CSV format instead of JSON
4. Add error handling for file not found scenarios

### Intermediate Challenges
1. Read data from a REST API endpoint
2. Implement incremental loading (only new records)
3. Add data deduplication logic
4. Create a scheduled ETL job that runs automatically
5. Implement data quality metrics and reporting

### Advanced Challenges
1. Connect to a real MySQL or PostgreSQL database
2. Implement parallel processing for large datasets
3. Add support for streaming data processing
4. Create a data lineage tracker
5. Implement slowly changing dimensions (SCD) Type 2
6. Build a data quality dashboard

## Common ETL Patterns Demonstrated

1. **Full Load**: Loading all data from source to destination (Exercise 1, 5)
2. **Filtering**: Selecting specific records based on criteria (Exercise 2)
3. **Aggregation**: Summarizing data for analysis (Exercise 3)
4. **Joining**: Combining data from multiple sources (Exercise 4)
5. **Batch Processing**: Processing data in batches for efficiency (Exercise 5)
6. **Validation**: Ensuring data quality before loading (Exercise 2, 5)
7. **Error Handling**: Managing invalid data gracefully (Exercise 2)

## Technologies Used

- **Java 17**: Programming language
- **Maven**: Build and dependency management
- **OpenCSV**: CSV file parsing
- **Jackson**: JSON serialization/deserialization
- **SLF4J**: Logging framework
- **H2 Database**: Embedded database for Exercise 5
- **JDBC**: Database connectivity

## Troubleshooting

### Maven Build Fails
```bash
# Clean and rebuild
mvn clean compile
```

### Out of Memory Error
```bash
# Increase heap size
export MAVEN_OPTS="-Xmx1024m"
mvn exec:java
```

### Cannot Find Output Files
Output files are created in `src/main/resources/data/output/`. Make sure you have write permissions to this directory.

### Database Already Exists Error
Delete the database file and rerun Exercise 5:
```bash
rm src/main/resources/data/output/sales_db.mv.db
mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise5.Exercise5DatabasePipeline"
```

## Further Reading

- [ETL Best Practices](https://en.wikipedia.org/wiki/Extract,_transform,_load)
- [Data Quality](https://en.wikipedia.org/wiki/Data_quality)
- [Data Warehousing Concepts](https://en.wikipedia.org/wiki/Data_warehouse)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Java Streams Guide](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)

## Contributing

This is an educational project. Students are encouraged to:
- Add more exercises
- Improve existing code
- Add more comprehensive comments
- Create additional sample datasets
- Share your solutions and improvements

## License

This project is part of the SSATR IA course materials and is provided for educational purposes.

## Support

For questions or issues:
1. Review the code comments in each exercise
2. Check the troubleshooting section above
3. Consult with your course instructor
4. Review Java and Maven documentation

## Summary

These exercises provide a hands-on introduction to ETL concepts that are fundamental in data engineering and business intelligence. By completing these exercises, you will understand how data flows from sources through transformations to destinations, preparing you for real-world data processing challenges.

Happy learning!
