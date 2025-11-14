package ro.ucv.ace.etl.exercise3;

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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Exercise 3: Data Aggregation and Grouping
 *
 * Learning objectives:
 * - Extract: Read sales data from CSV
 * - Transform: Aggregate and group data using various dimensions
 * - Load: Write aggregated reports to JSON
 *
 * Aggregations performed:
 * 1. Total sales by category
 * 2. Average order value by customer
 * 3. Sales count and revenue by date
 * 4. Top products by revenue
 */
public class Exercise3Aggregation {
    private static final Logger logger = LoggerFactory.getLogger(Exercise3Aggregation.class);
    private static final String INPUT_FILE = "src/main/resources/data/input/sales.csv";
    private static final String OUTPUT_FILE = "src/main/resources/data/output/sales_aggregations.json";

    public static void main(String[] args) {
        logger.info("Starting Exercise 3: Data Aggregation and Grouping");

        try {
            // EXTRACT: Read sales data from CSV
            List<Sale> sales = extractSalesFromCSV(INPUT_FILE);
            logger.info("Extracted {} sales records", sales.size());

            // TRANSFORM: Perform various aggregations
            Map<String, Object> aggregations = new LinkedHashMap<>();

            // 1. Total sales by category
            Map<String, CategoryStats> categoryStats = aggregateByCategory(sales);
            aggregations.put("salesByCategory", categoryStats);
            logger.info("Computed sales statistics for {} categories", categoryStats.size());

            // 2. Sales by customer
            Map<Integer, CustomerStats> customerStats = aggregateByCustomer(sales);
            aggregations.put("salesByCustomer", customerStats);
            logger.info("Computed sales statistics for {} customers", customerStats.size());

            // 3. Daily sales summary
            Map<LocalDate, DailySales> dailySales = aggregateByDate(sales);
            aggregations.put("dailySales", dailySales);
            logger.info("Computed sales statistics for {} days", dailySales.size());

            // 4. Top products by revenue
            List<ProductRevenue> topProducts = getTopProductsByRevenue(sales, 5);
            aggregations.put("topProducts", topProducts);
            logger.info("Identified top {} products by revenue", topProducts.size());

            // 5. Overall statistics
            OverallStats overallStats = computeOverallStats(sales);
            aggregations.put("overallStatistics", overallStats);

            // LOAD: Write aggregations to JSON
            loadAggregationsToJSON(aggregations, OUTPUT_FILE);
            logger.info("Loaded aggregations to: {}", OUTPUT_FILE);

            // Print summary
            printSummary(overallStats);

            logger.info("Exercise 3 completed successfully");

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
     * TRANSFORM: Aggregate sales by category
     */
    private static Map<String, CategoryStats> aggregateByCategory(List<Sale> sales) {
        return sales.stream()
            .collect(Collectors.groupingBy(
                Sale::getCategory,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        double totalRevenue = list.stream().mapToDouble(Sale::getTotalAmount).sum();
                        int totalQuantity = list.stream().mapToInt(Sale::getQuantity).sum();
                        return new CategoryStats(
                            list.get(0).getCategory(),
                            list.size(),
                            totalRevenue,
                            totalQuantity,
                            totalRevenue / list.size()
                        );
                    }
                )
            ));
    }

    /**
     * TRANSFORM: Aggregate sales by customer
     */
    private static Map<Integer, CustomerStats> aggregateByCustomer(List<Sale> sales) {
        return sales.stream()
            .collect(Collectors.groupingBy(
                Sale::getCustomerId,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        double totalSpent = list.stream().mapToDouble(Sale::getTotalAmount).sum();
                        return new CustomerStats(
                            list.get(0).getCustomerId(),
                            list.size(),
                            totalSpent,
                            totalSpent / list.size()
                        );
                    }
                )
            ));
    }

    /**
     * TRANSFORM: Aggregate sales by date
     */
    private static Map<LocalDate, DailySales> aggregateByDate(List<Sale> sales) {
        return sales.stream()
            .collect(Collectors.groupingBy(
                Sale::getSaleDate,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        double totalRevenue = list.stream().mapToDouble(Sale::getTotalAmount).sum();
                        return new DailySales(
                            list.get(0).getSaleDate(),
                            list.size(),
                            totalRevenue
                        );
                    }
                )
            ));
    }

    /**
     * TRANSFORM: Get top N products by revenue
     */
    private static List<ProductRevenue> getTopProductsByRevenue(List<Sale> sales, int topN) {
        Map<String, Double> productRevenues = sales.stream()
            .collect(Collectors.groupingBy(
                Sale::getProductName,
                Collectors.summingDouble(Sale::getTotalAmount)
            ));

        return productRevenues.entrySet().stream()
            .map(e -> new ProductRevenue(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(ProductRevenue::getTotalRevenue).reversed())
            .limit(topN)
            .collect(Collectors.toList());
    }

    /**
     * TRANSFORM: Compute overall statistics
     */
    private static OverallStats computeOverallStats(List<Sale> sales) {
        double totalRevenue = sales.stream().mapToDouble(Sale::getTotalAmount).sum();
        double averageOrderValue = totalRevenue / sales.size();
        int totalQuantity = sales.stream().mapToInt(Sale::getQuantity).sum();
        long uniqueCustomers = sales.stream().map(Sale::getCustomerId).distinct().count();
        long uniqueProducts = sales.stream().map(Sale::getProductName).distinct().count();

        return new OverallStats(
            sales.size(),
            totalRevenue,
            averageOrderValue,
            totalQuantity,
            (int) uniqueCustomers,
            (int) uniqueProducts
        );
    }

    /**
     * LOAD phase: Write aggregations to JSON file
     */
    private static void loadAggregationsToJSON(Map<String, Object> aggregations, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, aggregations);
        }
    }

    /**
     * Print summary to console
     */
    private static void printSummary(OverallStats stats) {
        System.out.println("\n--- Sales Analysis Summary ---");
        System.out.println("Total Orders: " + stats.getTotalOrders());
        System.out.println("Total Revenue: $" + String.format("%.2f", stats.getTotalRevenue()));
        System.out.println("Average Order Value: $" + String.format("%.2f", stats.getAverageOrderValue()));
        System.out.println("Total Items Sold: " + stats.getTotalQuantity());
        System.out.println("Unique Customers: " + stats.getUniqueCustomers());
        System.out.println("Unique Products: " + stats.getUniqueProducts());
        System.out.println("------------------------------\n");
    }

    // Data classes for aggregations
    public static class CategoryStats {
        private String category;
        private int orderCount;
        private double totalRevenue;
        private int totalQuantity;
        private double averageOrderValue;

        public CategoryStats() {
        }

        public CategoryStats(String category, int orderCount, double totalRevenue, int totalQuantity, double averageOrderValue) {
            this.category = category;
            this.orderCount = orderCount;
            this.totalRevenue = totalRevenue;
            this.totalQuantity = totalQuantity;
            this.averageOrderValue = averageOrderValue;
        }

        // Getters
        public String getCategory() { return category; }
        public int getOrderCount() { return orderCount; }
        public double getTotalRevenue() { return totalRevenue; }
        public int getTotalQuantity() { return totalQuantity; }
        public double getAverageOrderValue() { return averageOrderValue; }
    }

    public static class CustomerStats {
        private int customerId;
        private int orderCount;
        private double totalSpent;
        private double averageOrderValue;

        public CustomerStats() {
        }

        public CustomerStats(int customerId, int orderCount, double totalSpent, double averageOrderValue) {
            this.customerId = customerId;
            this.orderCount = orderCount;
            this.totalSpent = totalSpent;
            this.averageOrderValue = averageOrderValue;
        }

        // Getters
        public int getCustomerId() { return customerId; }
        public int getOrderCount() { return orderCount; }
        public double getTotalSpent() { return totalSpent; }
        public double getAverageOrderValue() { return averageOrderValue; }
    }

    public static class DailySales {
        private LocalDate date;
        private int orderCount;
        private double totalRevenue;

        public DailySales() {
        }

        public DailySales(LocalDate date, int orderCount, double totalRevenue) {
            this.date = date;
            this.orderCount = orderCount;
            this.totalRevenue = totalRevenue;
        }

        // Getters
        public LocalDate getDate() { return date; }
        public int getOrderCount() { return orderCount; }
        public double getTotalRevenue() { return totalRevenue; }
    }

    public static class ProductRevenue {
        private String productName;
        private double totalRevenue;

        public ProductRevenue() {
        }

        public ProductRevenue(String productName, double totalRevenue) {
            this.productName = productName;
            this.totalRevenue = totalRevenue;
        }

        // Getters
        public String getProductName() { return productName; }
        public double getTotalRevenue() { return totalRevenue; }
    }

    public static class OverallStats {
        private int totalOrders;
        private double totalRevenue;
        private double averageOrderValue;
        private int totalQuantity;
        private int uniqueCustomers;
        private int uniqueProducts;

        public OverallStats() {
        }

        public OverallStats(int totalOrders, double totalRevenue, double averageOrderValue,
                           int totalQuantity, int uniqueCustomers, int uniqueProducts) {
            this.totalOrders = totalOrders;
            this.totalRevenue = totalRevenue;
            this.averageOrderValue = averageOrderValue;
            this.totalQuantity = totalQuantity;
            this.uniqueCustomers = uniqueCustomers;
            this.uniqueProducts = uniqueProducts;
        }

        // Getters
        public int getTotalOrders() { return totalOrders; }
        public double getTotalRevenue() { return totalRevenue; }
        public double getAverageOrderValue() { return averageOrderValue; }
        public int getTotalQuantity() { return totalQuantity; }
        public int getUniqueCustomers() { return uniqueCustomers; }
        public int getUniqueProducts() { return uniqueProducts; }
    }
}
