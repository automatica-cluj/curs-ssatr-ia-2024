package utcluj.isp.curs3.liste.sortedset;

public class Account implements Comparable<Account> {

    // Instance variables
    private String accountHolderName;
    private String accountNumber;
    private double balance;

    // Constructor
    public Account(String accountHolderName, String accountNumber, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters and setters
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // toString method to display the Account object as a string
    @Override
    public String toString() {
        return "Account holder name: " + accountHolderName + "\n" +
                "Account number: " + accountNumber + "\n" +
                "Balance: $" + balance;
    }

    @Override
    public int compareTo(Account otherAccount) {
        return Double.compare(this.balance, otherAccount.getBalance());
    }
}
