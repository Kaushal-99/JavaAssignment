/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.Beans;

/**
 *
 * @author Nikita
 */
public class UserPaymentRecords {
    private String flatNumber;
    private String modeOfPayment;
    private String paymentReference;
    private String amount;
    private String firstName;
    private String lastName;
    private String date;
    private String recordNumber;

    public UserPaymentRecords(String recordNumber,String flatNumber, String modeOfPayment, String paymentReference, String amount, String firstName, String lastName, String date) {
        this.flatNumber = flatNumber;
        this.modeOfPayment = modeOfPayment;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.recordNumber =recordNumber;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
    
    public UserPaymentRecords(){
        
    }
    public void setDate(String date) {
        this.date = date;
    }
   public String getDate() {
        return flatNumber;
    }
    
    
    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
