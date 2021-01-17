/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

/**
 * 注文明細クラス
 * @author 19jz0137
 */
public class OrderDetail {
    int    orderNumber;     //注文番号
    String itemNumber;      //商品番号
    int    orderQuantity;   //注文数

    public OrderDetail() {
    }

    public OrderDetail(int orderNumber, String itemNumber, int orderQuantity) {
        this.orderNumber = orderNumber;
        this.itemNumber = itemNumber;
        this.orderQuantity = orderQuantity;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    
    
    
}
