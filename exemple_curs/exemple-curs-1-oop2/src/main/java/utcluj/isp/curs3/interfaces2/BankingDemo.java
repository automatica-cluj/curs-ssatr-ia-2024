package utcluj.isp.curs3.interfaces2;

interface BankAccount {
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount);
}

class SavingsAccount implements BankAccount {
    private double balance;

    public SavingsAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}

class CheckingAccount implements BankAccount {
    private double balance;

    public CheckingAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds. A penalty of $10 will be charged.");
            balance -= 10;
        }
    }
}

public class BankingDemo {
    public static void main(String[] args) {
        BankAccount savingsAccount = new SavingsAccount(1000);
        BankAccount checkingAccount = new CheckingAccount(2000);

        savingsAccount.deposit(500);
        checkingAccount.deposit(1000);

        savingsAccount.withdraw(100);
        checkingAccount.withdraw(500);

        System.out.println("Savings account balance: $" + savingsAccount.getBalance());
        System.out.println("Checking account balance: $" + checkingAccount.getBalance());
    }
}
