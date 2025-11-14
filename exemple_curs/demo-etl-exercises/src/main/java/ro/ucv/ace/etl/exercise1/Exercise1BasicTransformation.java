package ro.ucv.ace.etl.exercise1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ucv.ace.etl.model.Customer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 1: Basic ETL - CSV to JSON Transformation
 *
 * Learning objectives:
 * - Extract: Read data from CSV file
 * - Transform: Parse CSV data into Java objects
 * - Load: Write Java objects to JSON file
 *
 * This exercise demonstrates the fundamental ETL pattern using simple file formats.
 */
public class Exercise1BasicTransformation {
    private static final Logger logger = LoggerFactory.getLogger(Exercise1BasicTransformation.class);
    private static final String INPUT_FILE = "src/main/resources/data/input/customers.csv";
    private static final String OUTPUT_FILE = "src/main/resources/data/output/customers.json";

    public static void main(String[] args) {
        logger.info("Starting Exercise 1: Basic CSV to JSON Transformation");

        try {
            // EXTRACT: Read data from CSV
            List<Customer> customers = extractCustomersFromCSV(INPUT_FILE);
            logger.info("Extracted {} customers from CSV", customers.size());

            // TRANSFORM: Data is already transformed into Customer objects during extraction
            // In this basic exercise, transformation is minimal (just parsing)

            // LOAD: Write data to JSON
            loadCustomersToJSON(customers, OUTPUT_FILE);
            logger.info("Loaded {} customers to JSON file: {}", customers.size(), OUTPUT_FILE);

            logger.info("Exercise 1 completed successfully");

        } catch (Exception e) {
            logger.error("Error during ETL process", e);
        }
    }

    /**
     * EXTRACT phase: Read customers from CSV file
     *
     * @param filePath Path to the CSV file
     * @return List of Customer objects
     * @throws IOException If file reading fails
     * @throws CsvException If CSV parsing fails
     */
    private static List<Customer> extractCustomersFromCSV(String filePath) throws IOException, CsvException {
        List<Customer> customers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            // Skip header row
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                Customer customer = new Customer();
                customer.setId(Integer.parseInt(record[0]));
                customer.setName(record[1]);
                customer.setEmail(record[2]);
                customer.setAge(Integer.parseInt(record[3]));
                customer.setCity(record[4]);
                customer.setCountry(record[5]);
                customer.setRegistrationDate(LocalDate.parse(record[6]));

                customers.add(customer);
            }
        }

        return customers;
    }

    /**
     * LOAD phase: Write customers to JSON file
     *
     * @param customers List of customers to write
     * @param filePath Path to the output JSON file
     * @throws IOException If file writing fails
     */
    private static void loadCustomersToJSON(List<Customer> customers, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, customers);
        }
    }
}
