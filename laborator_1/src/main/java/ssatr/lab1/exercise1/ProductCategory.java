package ssatr.lab1.exercise1;

/**
 * @author Radu Miron
 */
public enum ProductCategory {
    ELECTRONICS("Electronics & Gadgets"),
    FASHION("Fashion");
    // todo: add the rest of the constants

    private String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
