/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.text.SimpleDateFormat;
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
    private Date orderDate;
    private Date paymentDay;
    private int    amount;

    public Payment() {
    }

    public Payment(int orderNumber, String name, String phonNumber, Date orderDate, Date paymentDay, int amount) {
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

    public String getOrderDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(orderDate);
    }

    public Date getOrderDate() {
        return orderDate;
    }
    
    public String getPaymentDay(String format) {
        if (Objects.equals(this.paymentDay, null)) {
            return "未入金";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return Objects.equals(this.paymentDay, null) ? null : dateFormat.format(orderDate);
    }

    public Date getPaymentDay() {
        return orderDate;
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
        this.orderDate = orderDate;
    }

    public void setPaymentDay(Date paymentDay) {
        if (Objects.isNull(paymentDay)) {
            this.paymentDay = null;
        }
        else {
           this.paymentDay = paymentDay;
        }
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
