/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utcluj.isp.curs.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import utcluj.isp.curs2.oop.bank.BankAccount;

public class BankAccountTest {
    
    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("1234567890", "John Doe", 1000.0);
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance(), 0.0);
    }
    
    @Test
    public void testWithdrawSufficientFunds() {
        BankAccount account = new BankAccount("1234567890", "John Doe", 1000.0);
        account.withdraw(500.0);
        assertEquals(500.0, account.getBalance(), 0.0);
    }
    
    @Test
    public void testWithdrawInsufficientFunds() {
        BankAccount account = new BankAccount("1234567890", "John Doe", 1000.0);
        account.withdraw(1500.0);
        assertEquals(1000.0, account.getBalance(), 0.0);
    }
    
    @Test
    public void testDisplayAccountInfo() {
        BankAccount account = new BankAccount("1234567890", "John Doe", 1000.0);
        String expectedOutput = "Account number: 1234567890\nAccount holder name: John Doe\nBalance: $1000.0\n";
        assertEquals(expectedOutput, account.displayAccountInfo());
    }
}
