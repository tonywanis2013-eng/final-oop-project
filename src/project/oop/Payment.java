/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;

public class Payment {
    
     private double amount;
    private PaymentMethod method;
    private LocalDate date;

    public Payment(double amount, PaymentMethod method) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        this.amount = amount;
        this.method = method;
        this.date = LocalDate.now();
    }

    public double getAmount() {
        return amount;
    }   
}
