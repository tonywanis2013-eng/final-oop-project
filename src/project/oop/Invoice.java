/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice implements payable {
     private Reservation reservation;
    private PaymentMethod paymentMethod;
    private double totalAmount;
    private ArrayList<Payment> payments=new ArrayList<>();
    private LocalDate paymentDate;

    // Constructor
    public Invoice(Reservation reservation, PaymentMethod method) {
        this.reservation = reservation;
        this.paymentMethod = method;
        this.payments = new ArrayList<>();

        double pricePerNight = reservation.getRoom().getType().getPrice();
        this.totalAmount = pricePerNight * reservation.getNumberOfNights();
    }

    
    public Invoice(Reservation reservation) {
        this(reservation, null);
    }

    
    @Override
    public double calculatePayment() {
        return totalAmount;
    }

    
    public void addPayment(double amount, PaymentMethod method) {
        Payment p = new Payment(amount, method);
        payments.add(p);

        if (getPaidAmount() >= totalAmount) {
            paymentDate = LocalDate.now();
            reservation.setStatus(ReservationStatus.COMPLETED);
        }
    }

    
    public double getPaidAmount() {
        double sum = 0;

        for (Payment p : payments) {
            sum += p.getAmount();
        }

        return sum;
    }

   
    public double getRemainingAmount() {
        return totalAmount - getPaidAmount();
    }

    // Getter
    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    // toString
    @Override
    public String toString() {
        return "Invoice{" +
                "total=" + totalAmount +
                ", paid=" + getPaidAmount() +
                ", remaining=" + getRemainingAmount() +
                ", date=" + paymentDate +
                '}';
    }
}
