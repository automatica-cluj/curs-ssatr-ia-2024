/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package utcluj.isp.curs2;

import utcluj.isp.curs2.oop.bank.BankAccount;

/**
 *
 * @author mihai
 */
public class ExempleCurs2Oop {

    public static void main(String[] args) {
        System.out.println("Hello Objects!");

        BankAccount johnDoeAccount = new BankAccount("1234567890", "John Doe", 1000.0);
        System.out.println(johnDoeAccount.displayAccountInfo());
        johnDoeAccount.deposit(500.0);
        System.out.println(johnDoeAccount.displayAccountInfo());
        johnDoeAccount.withdraw(750.0);
        System.out.println(johnDoeAccount.displayAccountInfo());
        BankAccount janeSmithAccount = new BankAccount("0987654321", "Jane Smith", 2500.0);
        System.out.println(janeSmithAccount.displayAccountInfo());

    }
}
