/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.util.Date;
import java.util.Objects;

/**
 * 支払情報クラス
 * @author 19jz0137
 */
public class Payment {
    private int    orderNumber;
    private String name;
    private String phoneNumber;
    private String orderDate;
    private String paymentDay;
    private int    amount;

    public Payment() {
    }

    public Payment(int orderNumber, String name, String phonNumber, String orderDate, String paymentDay, int amount) {
        this.orderNumber = orderNumber;
        this.name = name;
        this.phoneNumber = phonNumber;
        this.orderDate = orderDate;
        this.paymentDay = paymentDay;
        this.amount = amount;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOrderDate(Date orderDate) {
        setOrderDate(orderDate.toString());
    }

    public void setPaymentDay(Date paymentDay) {
        if (Objects.isNull(paymentDay)) {
            setPaymentDay("未入金");
        }
        else {
            setPaymentDay(paymentDay.toString());
        }
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setPaymentDay(String paymentDay) {
        this.paymentDay = paymentDay;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
