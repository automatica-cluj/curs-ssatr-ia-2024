package utcluj.isp.curs3.liste.hashset;

import java.util.HashSet;

public class AccountManager {

    // HashSet to store Account objects
    private HashSet<Account> accounts;

    // Constructor
    public AccountManager() {
        accounts = new HashSet<Account>();
    }

    // Method to add a new Account object to the HashSet
    public boolean addAccount(Account account) {
        return accounts.add(account);
    }

    // Method to remove an Account object from the HashSet
    public boolean removeAccount(Account account) {
        return accounts.remove(account);
    }

    // Method to display all Account objects in the HashSet
    public void displayAllAccounts() {
        System.out.println("All accounts:");
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

    // Method to get the Account object with the highest balance in the HashSet
    public Account getAccountWithHighestBalance() {
        Account highestBalanceAccount = null;
        double highestBalance = Double.NEGATIVE_INFINITY;
        for (Account account : accounts) {
            if (account.getBalance() > highestBalance) {
                highestBalance = account.getBalance();
                highestBalanceAccount = account;
            }
        }
        return highestBalanceAccount;
    }

    // Method to get the Account object with the lowest balance in the HashSet
    public Account getAccountWithLowestBalance() {
        Account lowestBalanceAccount = null;
        double lowestBalance = Double.POSITIVE_INFINITY;
        for (Account account : accounts) {
            if (account.getBalance() < lowestBalance) {
                lowestBalance = account.getBalance();
                lowestBalanceAccount = account;
            }
        }
        return lowestBalanceAccount;
    }
}