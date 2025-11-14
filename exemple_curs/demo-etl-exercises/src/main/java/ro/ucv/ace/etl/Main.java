package ro.ucv.ace.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ucv.ace.etl.exercise1.Exercise1BasicTransformation;
import ro.ucv.ace.etl.exercise2.Exercise2FilteringValidation;
import ro.ucv.ace.etl.exercise3.Exercise3Aggregation;
import ro.ucv.ace.etl.exercise4.Exercise4DataJoining;
import ro.ucv.ace.etl.exercise5.Exercise5DatabasePipeline;

import java.util.Scanner;

/**
 * Main entry point for ETL Exercises
 * Provides an interactive menu to run different exercises
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n=============================================");
        System.out.println("   ETL EXERCISES - Learning ETL Concepts");
        System.out.println("=============================================\n");

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        runExercise1();
                        break;
                    case 2:
                        runExercise2();
                        break;
                    case 3:
                        runExercise3();
                        break;
                    case 4:
                        runExercise4();
                        break;
                    case 5:
                        runExercise5();
                        break;
                    case 6:
                        runAllExercises();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting... Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }

                if (running && choice >= 1 && choice <= 6) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine(); // Consume newline
                    scanner.nextLine(); // Wait for Enter
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.\n");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Available Exercises:");
        System.out.println("--------------------");
        System.out.println("1. Exercise 1: Basic CSV to JSON Transformation");
        System.out.println("   - Learn: Extract from CSV, Load to JSON");
        System.out.println();
        System.out.println("2. Exercise 2: Data Filtering and Validation");
        System.out.println("   - Learn: Transform data with filtering and validation rules");
        System.out.println();
        System.out.println("3. Exercise 3: Data Aggregation and Grouping");
        System.out.println("   - Learn: Transform data using aggregations and statistics");
        System.out.println();
        System.out.println("4. Exercise 4: Joining Multiple Data Sources");
        System.out.println("   - Learn: Combine data from multiple files using joins");
        System.out.println();
        System.out.println("5. Exercise 5: Complete ETL Pipeline with Database");
        System.out.println("   - Learn: Full ETL cycle loading data into a database");
        System.out.println();
        System.out.println("6. Run All Exercises");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println();
    }

    private static void runExercise1() {
        System.out.println("=== Running Exercise 1: Basic CSV to JSON Transformation ===\n");
        try {
            Exercise1BasicTransformation.main(new String[]{});
            System.out.println("\nExercise 1 completed! Check output file:");
            System.out.println("  src/main/resources/data/output/customers.json");
        } catch (Exception e) {
            logger.error("Error running Exercise 1", e);
        }
    }

    private static void runExercise2() {
        System.out.println("=== Running Exercise 2: Data Filtering and Validation ===\n");
        try {
            Exercise2FilteringValidation.main(new String[]{});
            System.out.println("\nExercise 2 completed! Check output files:");
            System.out.println("  src/main/resources/data/output/filtered_sales.json");
            System.out.println("  src/main/resources/data/output/validation_errors.json");
        } catch (Exception e) {
            logger.error("Error running Exercise 2", e);
        }
    }

    private static void runExercise3() {
        System.out.println("=== Running Exercise 3: Data Aggregation and Grouping ===\n");
        try {
            Exercise3Aggregation.main(new String[]{});
            System.out.println("\nExercise 3 completed! Check output file:");
            System.out.println("  src/main/resources/data/output/sales_aggregations.json");
        } catch (Exception e) {
            logger.error("Error running Exercise 3", e);
        }
    }

    private static void runExercise4() {
        System.out.println("=== Running Exercise 4: Joining Multiple Data Sources ===\n");
        try {
            Exercise4DataJoining.main(new String[]{});
            System.out.println("\nExercise 4 completed! Check output file:");
            System.out.println("  src/main/resources/data/output/enriched_sales.json");
        } catch (Exception e) {
            logger.error("Error running Exercise 4", e);
        }
    }

    private static void runExercise5() {
        System.out.println("=== Running Exercise 5: Complete ETL Pipeline with Database ===\n");
        try {
            Exercise5DatabasePipeline.main(new String[]{});
            System.out.println("\nExercise 5 completed! Database created at:");
            System.out.println("  src/main/resources/data/output/sales_db.mv.db");
        } catch (Exception e) {
            logger.error("Error running Exercise 5", e);
        }
    }

    private static void runAllExercises() {
        System.out.println("=== Running All Exercises ===\n");
        runExercise1();
        System.out.println("\n" + "=".repeat(60) + "\n");
        runExercise2();
        System.out.println("\n" + "=".repeat(60) + "\n");
        runExercise3();
        System.out.println("\n" + "=".repeat(60) + "\n");
        runExercise4();
        System.out.println("\n" + "=".repeat(60) + "\n");
        runExercise5();
        System.out.println("\n=== All Exercises Completed ===");
    }
}
