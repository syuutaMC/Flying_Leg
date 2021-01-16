/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import java.util.Date;

/**
 * 注文情報クラス
 * @author 19jz0137
 */
public class Order {
    private int     orderNumber;
    private int     customerNumber;
    private Date    deliveryTime;
    private String  deliveryToAddress;
    private Date    paymentDay;
    private String  storeNumber;

    public Order() {
    }

    public Order(int orderNumber, int customerNumber, Date deliveryTime, String deliveryToAddress, Date paymentDay, String storeNumber) {
        this.orderNumber = orderNumber;
        this.customerNumber = customerNumber;
        this.deliveryTime = deliveryTime;
        this.deliveryToAddress = deliveryToAddress;
        this.paymentDay = paymentDay;
        this.storeNumber = storeNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public String getDeliveryToAddress() {
        return deliveryToAddress;
    }

    public Date getPaymentDay() {
        return paymentDay;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryToAddress(String deliveryToAddress) {
        this.deliveryToAddress = deliveryToAddress;
    }

    public void setPaymentDay(Date paymentDay) {
        this.paymentDay = paymentDay;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }   
}
