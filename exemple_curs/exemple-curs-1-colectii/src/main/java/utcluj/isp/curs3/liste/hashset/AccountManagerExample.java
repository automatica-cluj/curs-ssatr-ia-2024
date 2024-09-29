package utcluj.isp.curs3.liste.hashset;

public class AccountManagerExample {
    public static void main(String[] args) {

        // Create a new AccountManager object
        AccountManager accountManager = new AccountManager();

        // Create some Account objects
        Account account1 = new Account("John Smith", "123456", 1000.0);
        Account account2 = new Account("Jane Doe", "234567", 2000.0);
        Account account3 = new Account("Bob Johnson", "345678", 500.0);
        Account account4 = new Account("Samantha Lee", "456789", 2500.0);

        // Add the Account objects to the AccountManager
        boolean added1 = accountManager.addAccount(account1);
        boolean added2 = accountManager.addAccount(account2);
        boolean added3 = accountManager.addAccount(account3);
        boolean added4 = accountManager.addAccount(account4);

        // Display all Account objects in the AccountManager
        accountManager.displayAllAccounts();

        // Remove an Account object from the AccountManager
        boolean removed = accountManager.removeAccount(account3);
        if (removed) {
            System.out.println("\nAccount " + account3.getAccountNumber() + " removed.");
        }

        // Display all Account objects in the AccountManager again
        accountManager.displayAllAccounts();

        // Get the Account object with the highest balance in the AccountManager
        Account highestBalanceAccount = accountManager.getAccountWithHighestBalance();
        System.out.println("\nAccount with highest balance:\n" + highestBalanceAccount.toString());

        // Get the Account object with the lowest balance in the AccountManager
        Account lowestBalanceAccount = accountManager.getAccountWithLowestBalance();
        System.out.println("\nAccount with lowest balance:\n" + lowestBalanceAccount.toString());
    }
}
