/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.util.Date;

/**
 * 売り上げ情報クラス
 * @author 19jz0137
 */
public class Salse {
    private Date paymentDay;
    private int  storeNumber;
    private int  orderQuantity;
    private int  salseAmount;
    
    public Salse() {
    }

    public Salse(Date paymentDay, int storeNumber, int orderQuantity, int salseAmount) {
        this.paymentDay = paymentDay;
        this.storeNumber = storeNumber;
        this.orderQuantity = orderQuantity;
        this.salseAmount = salseAmount;
    }

    public Date getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Date paymentDay) {
        this.paymentDay = paymentDay;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getSalseAmount() {
        return salseAmount;
    }

    public void setSalseAmount(int salseAmount) {
        this.salseAmount = salseAmount;
    }
    
    
}
