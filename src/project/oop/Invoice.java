/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice {
      private Reservation reservation;
    private PaymentMethod paymentMethod;
    private double totalAmount;
    private ArrayList<Payment> payments= new ArrayList<>();
    private LocalDate paymentDate;

    public Invoice(Reservation reservation ,PaymentMethod method) {
        this.reservation = reservation;
        this.paymentMethod = method;
        this.payments = new ArrayList<>();

        double pricePerNight = reservation.getRoom().getType().getPrice();
        this.totalAmount = pricePerNight * reservation.getNumberOfNights();
    }

    public Invoice(Reservation res1) {
        this(res1, null);
    }

    //  add payment
    public void addPayment(double amount, PaymentMethod method) {
        Payment p = new Payment(amount, method);
        payments.add(p);

        if (getPaidAmount() >= totalAmount) {
            paymentDate = LocalDate.now();
            reservation.setStatus(ReservationStatus.COMPLETED);
        }
    }

    // total funds
    public double getPaidAmount() {
        double sum = 0;

        for (int i = 0; i < payments.size(); i++) {
            sum += payments.get(i).getAmount();
        }

        return sum;
    }

    // remaining funds
    public double getRemainingAmount() {
        return totalAmount - getPaidAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    //  toString
    @Override
    public String toString() {
        return "Invoice{" +
                "total=" + totalAmount +
                ", paid=" + getPaidAmount() +
                ", remaining=" + getRemainingAmount() +
                '}';
    }    
}
