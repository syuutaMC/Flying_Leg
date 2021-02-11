/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 売り上げ情報クラス
 * @author 19jz0137
 */
public class Sales {
    private Date salesDate;
    private int  storeNumber;
    private int  orderQuantity;
    private int  salesAmount;
    
    public Sales() {
    }

    public Sales(Date salesDate, int storeNumber, int orderQuantity, int salesAmount) {
        this.salesDate = salesDate;
        this.storeNumber = storeNumber;
        this.orderQuantity = orderQuantity;
        this.salesAmount = salesAmount;
    }

    public Date getSalesDate() {
        return salesDate;
    }
    
    /**
     * フォーマット指定日付取得
     * @param format フォーマット
     * @return 　　　フォーマットされた日付
     */
    public String getSalesDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(getSalesDate());
    }

    public void setSalesDate(Date paymentDay) {
        this.salesDate = paymentDay;
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

    public int getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(int salesAmount) {
        this.salesAmount = salesAmount;
    }
    
    @Override
    public String toString() {
        return getSalesDate() + "," + getStoreNumber() + "," + getOrderQuantity() + "," + getSalesAmount();
    }
}
