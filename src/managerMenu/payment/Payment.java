/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.util.Date;

/**
 * 支払情報クラス
 * @author 19jz0137
 */
public class Payment {
    private String name;
    private String phoneNumber;
    private Date   orderDate;
    private Date   paymentDay;
    private int    amount;

    public Payment() {
    }

    public Payment(String name, String phonNumber, Date orderDate, Date paymentDay, int amount) {
        this.name = name;
        this.phoneNumber = phonNumber;
        this.orderDate = orderDate;
        this.paymentDay = paymentDay;
        this.amount = amount;
    }
    
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getPaymentDay() {
        return paymentDay;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setPaymentDay(Date paymentDay) {
        this.paymentDay = paymentDay;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
