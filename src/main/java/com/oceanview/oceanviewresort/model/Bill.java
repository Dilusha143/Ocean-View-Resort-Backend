package com.oceanview.oceanviewresort.model;

public class Bill {

    private int    billID;
    private int    reservationID;
    private int    numberOfNights;
    private double pricePerNight;
    private double subTotal;
    private double taxRate;
    private double taxAmount;
    private double totalAmount;
    private String paymentStatus;  // PENDING | PAID | REFUNDED
    private String billedAt;

    public Bill() {}

    public int    getBillID()                        { return billID; }
    public void   setBillID(int billID)              { this.billID = billID; }

    public int    getReservationID()                 { return reservationID; }
    public void   setReservationID(int id)           { this.reservationID = id; }

    public int    getNumberOfNights()                { return numberOfNights; }
    public void   setNumberOfNights(int n)           { this.numberOfNights = n; }

    public double getPricePerNight()                 { return pricePerNight; }
    public void   setPricePerNight(double p)         { this.pricePerNight = p; }

    public double getSubTotal()                      { return subTotal; }
    public void   setSubTotal(double s)              { this.subTotal = s; }

    public double getTaxRate()                       { return taxRate; }
    public void   setTaxRate(double t)               { this.taxRate = t; }

    public double getTaxAmount()                     { return taxAmount; }
    public void   setTaxAmount(double t)             { this.taxAmount = t; }

    public double getTotalAmount()                   { return totalAmount; }
    public void   setTotalAmount(double t)           { this.totalAmount = t; }

    public String getPaymentStatus()                 { return paymentStatus; }
    public void   setPaymentStatus(String s)         { this.paymentStatus = s; }

    public String getBilledAt()                      { return billedAt; }
    public void   setBilledAt(String billedAt)       { this.billedAt = billedAt; }
}