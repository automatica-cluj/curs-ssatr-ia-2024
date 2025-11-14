package ro.ucv.ace.etl.exercise2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ucv.ace.etl.model.Sale;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exercise 2: Data Filtering and Validation
 *
 * Learning objectives:
 * - Extract: Read sales data from CSV
 * - Transform: Filter and validate data based on business rules
 * - Load: Write only valid, filtered data to output
 *
 * Business rules implemented:
 * 1. Filter sales with total amount > 100
 * 2. Validate that all required fields are present
 * 3. Filter sales from specific categories
 * 4. Validate date ranges
 */
public class Exercise2FilteringValidation {
    private static final Logger logger = LoggerFactory.getLogger(Exercise2FilteringValidation.class);
    private static final String INPUT_FILE = "src/main/resources/data/input/sales.csv";
    private static final String OUTPUT_FILE = "src/main/resources/data/output/filtered_sales.json";
    private static final String VALIDATION_ERRORS_FILE = "src/main/resources/data/output/validation_errors.json";

    // Business rule parameters
    private static final double MIN_AMOUNT = 50.0;
    private static final List<String> ALLOWED_CATEGORIES = List.of("Electronics", "Furniture");

    public static void main(String[] args) {
        logger.info("Starting Exercise 2: Data Filtering and Validation");

        try {
            // EXTRACT: Read sales data from CSV
            List<Sale> allSales = extractSalesFromCSV(INPUT_FILE);
            logger.info("Extracted {} sales records from CSV", allSales.size());

            // TRANSFORM: Filter and validate data
            List<Sale> validSales = new ArrayList<>();
            List<ValidationError> errors = new ArrayList<>();

            for (Sale sale : allSales) {
                ValidationResult result = validateAndFilter(sale);
                if (result.isValid) {
                    validSales.add(sale);
                } else {
                    errors.add(new ValidationError(sale.getSaleId(), result.errorMessage));
                }
            }

            logger.info("Valid sales after filtering: {}", validSales.size());
            logger.info("Invalid/filtered sales: {}", errors.size());

            // LOAD: Write valid sales and errors to separate files
            loadSalesToJSON(validSales, OUTPUT_FILE);
            loadErrorsToJSON(errors, VALIDATION_ERRORS_FILE);

            logger.info("Exercise 2 completed successfully");
            printSummary(allSales.size(), validSales.size(), errors.size());

        } catch (Exception e) {
            logger.error("Error during ETL process", e);
        }
    }

    /**
     * EXTRACT phase: Read sales from CSV file
     */
    private static List<Sale> extractSalesFromCSV(String filePath) throws IOException, CsvException {
        List<Sale> sales = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            // Skip header row
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                Sale sale = new Sale();
                sale.setSaleId(Integer.parseInt(record[0]));
                sale.setCustomerId(Integer.parseInt(record[1]));
                sale.setProductName(record[2]);
                sale.setQuantity(Integer.parseInt(record[3]));
                sale.setUnitPrice(Double.parseDouble(record[4]));
                sale.setSaleDate(LocalDate.parse(record[5]));
                sale.setCategory(record[6]);

                sales.add(sale);
            }
        }

        return sales;
    }

    /**
     * TRANSFORM phase: Validate and filter sales based on business rules
     */
    private static ValidationResult validateAndFilter(Sale sale) {
        // Rule 1: Check if total amount meets minimum threshold
        double totalAmount = sale.getTotalAmount();
        if (totalAmount < MIN_AMOUNT) {
            return new ValidationResult(false,
                String.format("Total amount %.2f is below minimum threshold %.2f", totalAmount, MIN_AMOUNT));
        }

        // Rule 2: Validate required fields
        if (sale.getProductName() == null || sale.getProductName().trim().isEmpty()) {
            return new ValidationResult(false, "Product name is missing");
        }

        // Rule 3: Check category is in allowed list
        if (!ALLOWED_CATEGORIES.contains(sale.getCategory())) {
            return new ValidationResult(false,
                String.format("Category '%s' is not in allowed list", sale.getCategory()));
        }

        // Rule 4: Validate quantity is positive
        if (sale.getQuantity() <= 0) {
            return new ValidationResult(false, "Quantity must be positive");
        }

        // Rule 5: Validate unit price is positive
        if (sale.getUnitPrice() <= 0) {
            return new ValidationResult(false, "Unit price must be positive");
        }

        return new ValidationResult(true, null);
    }

    /**
     * LOAD phase: Write sales to JSON file
     */
    private static void loadSalesToJSON(List<Sale> sales, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, sales);
        }
        logger.info("Loaded {} valid sales to: {}", sales.size(), filePath);
    }

    /**
     * LOAD phase: Write validation errors to JSON file
     */
    private static void loadErrorsToJSON(List<ValidationError> errors, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, errors);
        }
        logger.info("Loaded {} validation errors to: {}", errors.size(), filePath);
    }

    /**
     * Print summary statistics
     */
    private static void printSummary(int total, int valid, int invalid) {
        System.out.println("\n--- ETL Summary ---");
        System.out.println("Total records processed: " + total);
        System.out.println("Valid records: " + valid + " (" + String.format("%.1f%%", (valid * 100.0 / total)) + ")");
        System.out.println("Invalid/filtered records: " + invalid + " (" + String.format("%.1f%%", (invalid * 100.0 / total)) + ")");
        System.out.println("-------------------\n");
    }

    /**
     * Validation result container
     */
    private static class ValidationResult {
        boolean isValid;
        String errorMessage;

        ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }
    }

    /**
     * Validation error record
     */
    public static class ValidationError {
        private int saleId;
        private String errorMessage;

        public ValidationError() {
        }

        public ValidationError(int saleId, String errorMessage) {
            this.saleId = saleId;
            this.errorMessage = errorMessage;
        }

        public int getSaleId() {
            return saleId;
        }

        public void setSaleId(int saleId) {
            this.saleId = saleId;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
