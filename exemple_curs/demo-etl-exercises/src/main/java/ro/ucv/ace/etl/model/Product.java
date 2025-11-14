package ro.ucv.ace.etl.model;

/**
 * Product inventory data model
 */
public class Product {
    private String productId;
    private String productName;
    private String category;
    private int stockQuantity;
    private String supplier;
    private int reorderLevel;

    public Product() {
    }

    public Product(String productId, String productName, String category, int stockQuantity, String supplier, int reorderLevel) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.supplier = supplier;
        this.reorderLevel = reorderLevel;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public boolean needsReorder() {
        return stockQuantity <= reorderLevel;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", supplier='" + supplier + '\'' +
                ", reorderLevel=" + reorderLevel +
                ", needsReorder=" + needsReorder() +
                '}';
    }
}
