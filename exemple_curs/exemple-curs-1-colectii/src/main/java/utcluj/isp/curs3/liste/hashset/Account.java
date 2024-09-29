package utcluj.isp.curs3.liste.hashset;

import java.util.Objects;

public class Account  {

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    // toString method to display the Account object as a string
    @Override
    public String toString() {
        return "Account holder name: " + accountHolderName + "\n" +
                "Account number: " + accountNumber + "\n" +
                "Balance: $" + balance;
    }
}

