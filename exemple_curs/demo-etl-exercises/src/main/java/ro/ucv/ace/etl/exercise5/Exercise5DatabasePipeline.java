package ro.ucv.ace.etl.exercise5;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 5: Complete ETL Pipeline with Database
 *
 * Learning objectives:
 * - Extract: Read data from CSV files
 * - Transform: Clean, validate, and prepare data for database storage
 * - Load: Insert data into a relational database (H2)
 *
 * This exercise demonstrates:
 * 1. Creating database schema
 * 2. Batch inserts for performance
 * 3. Transaction management
 * 4. Data integrity checks
 * 5. Querying and reporting from the database
 */
public class Exercise5DatabasePipeline {
    private static final Logger logger = LoggerFactory.getLogger(Exercise5DatabasePipeline.class);

    // Database connection
    private static final String DB_URL = "jdbc:h2:./src/main/resources/data/output/sales_db";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    // Input files
    private static final String CUSTOMERS_FILE = "src/main/resources/data/input/customers.csv";
    private static final String PRODUCTS_FILE = "src/main/resources/data/input/products.csv";
    private static final String SALES_FILE = "src/main/resources/data/input/sales.csv";

    public static void main(String[] args) {
        logger.info("Starting Exercise 5: Complete ETL Pipeline with Database");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false); // Use transactions

            // Step 1: Create database schema
            createSchema(conn);
            logger.info("Database schema created");

            // Step 2: EXTRACT and LOAD customers
            List<CustomerRecord> customers = extractCustomers(CUSTOMERS_FILE);
            loadCustomers(conn, customers);
            logger.info("Loaded {} customers to database", customers.size());

            // Step 3: EXTRACT and LOAD products
            List<ProductRecord> products = extractProducts(PRODUCTS_FILE);
            loadProducts(conn, products);
            logger.info("Loaded {} products to database", products.size());

            // Step 4: EXTRACT, TRANSFORM, and LOAD sales
            List<SaleRecord> sales = extractSales(SALES_FILE);
            int loadedSales = loadSales(conn, sales);
            logger.info("Loaded {} out of {} sales to database", loadedSales, sales.size());

            // Commit all changes
            conn.commit();
            logger.info("All data committed to database");

            // Step 5: Generate reports from database
            generateReports(conn);

            logger.info("Exercise 5 completed successfully");

        } catch (Exception e) {
            logger.error("Error during ETL process", e);
        }
    }

    /**
     * Create database schema
     */
    private static void createSchema(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Drop tables if they exist
            stmt.execute("DROP TABLE IF EXISTS sales");
            stmt.execute("DROP TABLE IF EXISTS products");
            stmt.execute("DROP TABLE IF EXISTS customers");

            // Create customers table
            stmt.execute("""
                CREATE TABLE customers (
                    id INT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    age INT,
                    city VARCHAR(50),
                    country VARCHAR(50),
                    registration_date DATE
                )
            """);

            // Create products table
            stmt.execute("""
                CREATE TABLE products (
                    product_id VARCHAR(10) PRIMARY KEY,
                    product_name VARCHAR(100) NOT NULL,
                    category VARCHAR(50),
                    stock_quantity INT,
                    supplier VARCHAR(100),
                    reorder_level INT
                )
            """);

            // Create sales table
            stmt.execute("""
                CREATE TABLE sales (
                    sale_id INT PRIMARY KEY,
                    customer_id INT NOT NULL,
                    product_name VARCHAR(100) NOT NULL,
                    quantity INT NOT NULL,
                    unit_price DECIMAL(10, 2) NOT NULL,
                    total_amount DECIMAL(10, 2) NOT NULL,
                    sale_date DATE NOT NULL,
                    category VARCHAR(50),
                    FOREIGN KEY (customer_id) REFERENCES customers(id)
                )
            """);

            // Create indexes for better query performance
            stmt.execute("CREATE INDEX idx_sales_customer ON sales(customer_id)");
            stmt.execute("CREATE INDEX idx_sales_date ON sales(sale_date)");
            stmt.execute("CREATE INDEX idx_sales_category ON sales(category)");
        }
    }

    /**
     * EXTRACT: Read customers from CSV
     */
    private static List<CustomerRecord> extractCustomers(String filePath) throws IOException, CsvException {
        List<CustomerRecord> customers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                customers.add(new CustomerRecord(
                    Integer.parseInt(record[0]),
                    record[1],
                    record[2],
                    Integer.parseInt(record[3]),
                    record[4],
                    record[5],
                    LocalDate.parse(record[6])
                ));
            }
        }

        return customers;
    }

    /**
     * LOAD: Insert customers into database using batch operations
     */
    private static void loadCustomers(Connection conn, List<CustomerRecord> customers) throws SQLException {
        String sql = "INSERT INTO customers (id, name, email, age, city, country, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (CustomerRecord customer : customers) {
                pstmt.setInt(1, customer.id);
                pstmt.setString(2, customer.name);
                pstmt.setString(3, customer.email);
                pstmt.setInt(4, customer.age);
                pstmt.setString(5, customer.city);
                pstmt.setString(6, customer.country);
                pstmt.setDate(7, Date.valueOf(customer.registrationDate));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        }
    }

    /**
     * EXTRACT: Read products from CSV
     */
    private static List<ProductRecord> extractProducts(String filePath) throws IOException, CsvException {
        List<ProductRecord> products = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                products.add(new ProductRecord(
                    record[0],
                    record[1],
                    record[2],
                    Integer.parseInt(record[3]),
                    record[4],
                    Integer.parseInt(record[5])
                ));
            }
        }

        return products;
    }

    /**
     * LOAD: Insert products into database
     */
    private static void loadProducts(Connection conn, List<ProductRecord> products) throws SQLException {
        String sql = "INSERT INTO products (product_id, product_name, category, stock_quantity, supplier, reorder_level) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (ProductRecord product : products) {
                pstmt.setString(1, product.productId);
                pstmt.setString(2, product.productName);
                pstmt.setString(3, product.category);
                pstmt.setInt(4, product.stockQuantity);
                pstmt.setString(5, product.supplier);
                pstmt.setInt(6, product.reorderLevel);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        }
    }

    /**
     * EXTRACT: Read sales from CSV
     */
    private static List<SaleRecord> extractSales(String filePath) throws IOException, CsvException {
        List<SaleRecord> sales = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                int quantity = Integer.parseInt(record[3]);
                double unitPrice = Double.parseDouble(record[4]);

                sales.add(new SaleRecord(
                    Integer.parseInt(record[0]),
                    Integer.parseInt(record[1]),
                    record[2],
                    quantity,
                    unitPrice,
                    quantity * unitPrice, // Calculate total
                    LocalDate.parse(record[5]),
                    record[6]
                ));
            }
        }

        return sales;
    }

    /**
     * TRANSFORM and LOAD: Validate and insert sales into database
     */
    private static int loadSales(Connection conn, List<SaleRecord> sales) throws SQLException {
        String sql = "INSERT INTO sales (sale_id, customer_id, product_name, quantity, unit_price, total_amount, sale_date, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int successCount = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (SaleRecord sale : sales) {
                // TRANSFORM: Validate that customer exists
                if (!customerExists(conn, sale.customerId)) {
                    logger.warn("Sale {} skipped: Customer {} not found", sale.saleId, sale.customerId);
                    continue;
                }

                pstmt.setInt(1, sale.saleId);
                pstmt.setInt(2, sale.customerId);
                pstmt.setString(3, sale.productName);
                pstmt.setInt(4, sale.quantity);
                pstmt.setDouble(5, sale.unitPrice);
                pstmt.setDouble(6, sale.totalAmount);
                pstmt.setDate(7, Date.valueOf(sale.saleDate));
                pstmt.setString(8, sale.category);
                pstmt.addBatch();
                successCount++;
            }

            pstmt.executeBatch();
        }

        return successCount;
    }

    /**
     * Check if customer exists in database
     */
    private static boolean customerExists(Connection conn, int customerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    /**
     * Generate reports from the database
     */
    private static void generateReports(Connection conn) throws SQLException {
        System.out.println("\n========== DATABASE REPORTS ==========\n");

        // Report 1: Total sales by category
        System.out.println("1. Total Sales by Category:");
        String sql1 = """
            SELECT category, COUNT(*) as order_count, SUM(total_amount) as total_revenue
            FROM sales
            GROUP BY category
            ORDER BY total_revenue DESC
        """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql1)) {
            while (rs.next()) {
                System.out.printf("   %s: %d orders, $%.2f revenue%n",
                    rs.getString("category"),
                    rs.getInt("order_count"),
                    rs.getDouble("total_revenue"));
            }
        }

        // Report 2: Top customers by spending
        System.out.println("\n2. Top 5 Customers by Total Spending:");
        String sql2 = """
            SELECT c.name, c.country, COUNT(*) as orders, SUM(s.total_amount) as total_spent
            FROM customers c
            JOIN sales s ON c.id = s.customer_id
            GROUP BY c.id, c.name, c.country
            ORDER BY total_spent DESC
            LIMIT 5
        """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql2)) {
            int rank = 1;
            while (rs.next()) {
                System.out.printf("   %d. %s (%s): %d orders, $%.2f spent%n",
                    rank++,
                    rs.getString("name"),
                    rs.getString("country"),
                    rs.getInt("orders"),
                    rs.getDouble("total_spent"));
            }
        }

        // Report 3: Daily sales trend
        System.out.println("\n3. Daily Sales Summary:");
        String sql3 = """
            SELECT sale_date, COUNT(*) as orders, SUM(total_amount) as revenue
            FROM sales
            GROUP BY sale_date
            ORDER BY sale_date
        """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql3)) {
            while (rs.next()) {
                System.out.printf("   %s: %d orders, $%.2f revenue%n",
                    rs.getDate("sale_date"),
                    rs.getInt("orders"),
                    rs.getDouble("revenue"));
            }
        }

        // Report 4: Products needing reorder
        System.out.println("\n4. Products Below Reorder Level:");
        String sql4 = """
            SELECT product_name, stock_quantity, reorder_level, supplier
            FROM products
            WHERE stock_quantity <= reorder_level
            ORDER BY stock_quantity
        """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql4)) {
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("   %s: Stock=%d, Reorder=%d, Supplier=%s%n",
                    rs.getString("product_name"),
                    rs.getInt("stock_quantity"),
                    rs.getInt("reorder_level"),
                    rs.getString("supplier"));
            }
            if (!found) {
                System.out.println("   All products are adequately stocked");
            }
        }

        System.out.println("\n======================================\n");
    }

    // Record classes
    private record CustomerRecord(int id, String name, String email, int age, String city, String country, LocalDate registrationDate) {}
    private record ProductRecord(String productId, String productName, String category, int stockQuantity, String supplier, int reorderLevel) {}
    private record SaleRecord(int saleId, int customerId, String productName, int quantity, double unitPrice, double totalAmount, LocalDate saleDate, String category) {}
}
