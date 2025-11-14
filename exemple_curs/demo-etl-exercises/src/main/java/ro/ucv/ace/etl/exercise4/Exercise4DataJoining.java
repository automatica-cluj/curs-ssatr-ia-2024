package ro.ucv.ace.etl.exercise4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ucv.ace.etl.model.Customer;
import ro.ucv.ace.etl.model.Product;
import ro.ucv.ace.etl.model.Sale;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Exercise 4: Joining Multiple Data Sources
 *
 * Learning objectives:
 * - Extract: Read data from multiple CSV files
 * - Transform: Join data from different sources (customers, sales, products)
 * - Load: Write enriched data with information from multiple sources
 *
 * This exercise demonstrates:
 * 1. Inner joins (matching records from multiple sources)
 * 2. Left joins (keeping all records from primary source)
 * 3. Data enrichment (adding related information)
 */
public class Exercise4DataJoining {
    private static final Logger logger = LoggerFactory.getLogger(Exercise4DataJoining.class);

    private static final String CUSTOMERS_FILE = "src/main/resources/data/input/customers.csv";
    private static final String SALES_FILE = "src/main/resources/data/input/sales.csv";
    private static final String PRODUCTS_FILE = "src/main/resources/data/input/products.csv";
    private static final String OUTPUT_FILE = "src/main/resources/data/output/enriched_sales.json";

    public static void main(String[] args) {
        logger.info("Starting Exercise 4: Joining Multiple Data Sources");

        try {
            // EXTRACT: Read data from multiple sources
            List<Customer> customers = extractCustomersFromCSV(CUSTOMERS_FILE);
            logger.info("Extracted {} customers", customers.size());

            List<Sale> sales = extractSalesFromCSV(SALES_FILE);
            logger.info("Extracted {} sales", sales.size());

            List<Product> products = extractProductsFromCSV(PRODUCTS_FILE);
            logger.info("Extracted {} products", products.size());

            // TRANSFORM: Create lookup maps for efficient joining
            Map<Integer, Customer> customerMap = customers.stream()
                .collect(Collectors.toMap(Customer::getId, c -> c));

            Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductName, p -> p));

            // TRANSFORM: Join sales with customers and products to create enriched records
            List<EnrichedSale> enrichedSales = new ArrayList<>();
            int missingCustomers = 0;
            int missingProducts = 0;

            for (Sale sale : sales) {
                Customer customer = customerMap.get(sale.getCustomerId());
                Product product = productMap.get(sale.getProductName());

                if (customer != null) {
                    EnrichedSale enrichedSale = new EnrichedSale(
                        sale.getSaleId(),
                        sale.getCustomerId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getCountry(),
                        sale.getProductName(),
                        product != null ? product.getCategory() : "Unknown",
                        product != null ? product.getSupplier() : "Unknown",
                        sale.getQuantity(),
                        sale.getUnitPrice(),
                        sale.getTotalAmount(),
                        sale.getSaleDate()
                    );
                    enrichedSales.add(enrichedSale);
                } else {
                    missingCustomers++;
                }

                if (product == null) {
                    missingProducts++;
                }
            }

            logger.info("Created {} enriched sales records", enrichedSales.size());
            if (missingCustomers > 0) {
                logger.warn("{} sales had no matching customer", missingCustomers);
            }
            if (missingProducts > 0) {
                logger.warn("{} sales had no matching product", missingProducts);
            }

            // TRANSFORM: Create customer sales summary
            Map<String, CustomerSalesSummary> customerSummaries = createCustomerSummaries(enrichedSales);
            logger.info("Created summaries for {} customers", customerSummaries.size());

            // Prepare output data
            Map<String, Object> output = new LinkedHashMap<>();
            output.put("enrichedSales", enrichedSales);
            output.put("customerSummaries", customerSummaries.values());

            // LOAD: Write enriched data to JSON
            loadToJSON(output, OUTPUT_FILE);
            logger.info("Loaded enriched data to: {}", OUTPUT_FILE);

            // Print summary
            printSummary(sales.size(), enrichedSales.size(), missingCustomers);

            logger.info("Exercise 4 completed successfully");

        } catch (Exception e) {
            logger.error("Error during ETL process", e);
        }
    }

    /**
     * EXTRACT: Read customers from CSV
     */
    private static List<Customer> extractCustomersFromCSV(String filePath) throws IOException, CsvException {
        List<Customer> customers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

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
     * EXTRACT: Read sales from CSV
     */
    private static List<Sale> extractSalesFromCSV(String filePath) throws IOException, CsvException {
        List<Sale> sales = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

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
     * EXTRACT: Read products from CSV
     */
    private static List<Product> extractProductsFromCSV(String filePath) throws IOException, CsvException {
        List<Product> products = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                Product product = new Product();
                product.setProductId(record[0]);
                product.setProductName(record[1]);
                product.setCategory(record[2]);
                product.setStockQuantity(Integer.parseInt(record[3]));
                product.setSupplier(record[4]);
                product.setReorderLevel(Integer.parseInt(record[5]));
                products.add(product);
            }
        }

        return products;
    }

    /**
     * TRANSFORM: Create customer sales summaries
     */
    private static Map<String, CustomerSalesSummary> createCustomerSummaries(List<EnrichedSale> enrichedSales) {
        return enrichedSales.stream()
            .collect(Collectors.groupingBy(
                EnrichedSale::getCustomerName,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    sales -> {
                        double totalSpent = sales.stream().mapToDouble(EnrichedSale::getTotalAmount).sum();
                        int orderCount = sales.size();
                        String country = sales.get(0).getCustomerCountry();
                        String email = sales.get(0).getCustomerEmail();

                        Set<String> categories = sales.stream()
                            .map(EnrichedSale::getCategory)
                            .collect(Collectors.toSet());

                        return new CustomerSalesSummary(
                            sales.get(0).getCustomerName(),
                            email,
                            country,
                            orderCount,
                            totalSpent,
                            totalSpent / orderCount,
                            new ArrayList<>(categories)
                        );
                    }
                )
            ));
    }

    /**
     * LOAD: Write data to JSON
     */
    private static void loadToJSON(Map<String, Object> data, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, data);
        }
    }

    /**
     * Print summary
     */
    private static void printSummary(int totalSales, int enrichedSales, int missingCustomers) {
        System.out.println("\n--- Data Joining Summary ---");
        System.out.println("Total sales records: " + totalSales);
        System.out.println("Successfully enriched: " + enrichedSales);
        System.out.println("Missing customer data: " + missingCustomers);
        System.out.println("Join success rate: " + String.format("%.1f%%", (enrichedSales * 100.0 / totalSales)));
        System.out.println("---------------------------\n");
    }

    /**
     * Enriched sale record with data from multiple sources
     */
    public static class EnrichedSale {
        private int saleId;
        private int customerId;
        private String customerName;
        private String customerEmail;
        private String customerCountry;
        private String productName;
        private String category;
        private String supplier;
        private int quantity;
        private double unitPrice;
        private double totalAmount;
        private LocalDate saleDate;

        public EnrichedSale() {
        }

        public EnrichedSale(int saleId, int customerId, String customerName, String customerEmail,
                           String customerCountry, String productName, String category, String supplier,
                           int quantity, double unitPrice, double totalAmount, LocalDate saleDate) {
            this.saleId = saleId;
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerEmail = customerEmail;
            this.customerCountry = customerCountry;
            this.productName = productName;
            this.category = category;
            this.supplier = supplier;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalAmount = totalAmount;
            this.saleDate = saleDate;
        }

        // Getters
        public int getSaleId() { return saleId; }
        public int getCustomerId() { return customerId; }
        public String getCustomerName() { return customerName; }
        public String getCustomerEmail() { return customerEmail; }
        public String getCustomerCountry() { return customerCountry; }
        public String getProductName() { return productName; }
        public String getCategory() { return category; }
        public String getSupplier() { return supplier; }
        public int getQuantity() { return quantity; }
        public double getUnitPrice() { return unitPrice; }
        public double getTotalAmount() { return totalAmount; }
        public LocalDate getSaleDate() { return saleDate; }
    }

    /**
     * Customer sales summary
     */
    public static class CustomerSalesSummary {
        private String customerName;
        private String email;
        private String country;
        private int totalOrders;
        private double totalSpent;
        private double averageOrderValue;
        private List<String> categoriesPurchased;

        public CustomerSalesSummary() {
        }

        public CustomerSalesSummary(String customerName, String email, String country, int totalOrders,
                                   double totalSpent, double averageOrderValue, List<String> categoriesPurchased) {
            this.customerName = customerName;
            this.email = email;
            this.country = country;
            this.totalOrders = totalOrders;
            this.totalSpent = totalSpent;
            this.averageOrderValue = averageOrderValue;
            this.categoriesPurchased = categoriesPurchased;
        }

        // Getters
        public String getCustomerName() { return customerName; }
        public String getEmail() { return email; }
        public String getCountry() { return country; }
        public int getTotalOrders() { return totalOrders; }
        public double getTotalSpent() { return totalSpent; }
        public double getAverageOrderValue() { return averageOrderValue; }
        public List<String> getCategoriesPurchased() { return categoriesPurchased; }
    }
}
