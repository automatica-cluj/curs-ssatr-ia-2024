package utcluj.isp.curs3.liste.sortedset;

import java.util.TreeSet;

public class AccountManager {

    // TreeSet to store Account objects sorted by account number
    private TreeSet<Account> accounts;

    // Constructor
    public AccountManager() {
        accounts = new TreeSet<Account>();
    }

    // Method to add a new Account object to the TreeSet
    public boolean addAccount(Account account) {
        return accounts.add(account);
    }

    // Method to remove an Account object from the TreeSet
    public boolean removeAccount(Account account) {
        return accounts.remove(account);
    }

    // Method to display all Account objects in the TreeSet
    public void displayAllAccounts() {
        System.out.println("All accounts:");
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

    // Method to get the Account object with the highest balance in the TreeSet
    public Account getAccountWithHighestBalance() {
        return accounts.last();
    }

    // Method to get the Account object with the lowest balance in the TreeSet
    public Account getAccountWithLowestBalance() {
        return accounts.first();
    }
}
