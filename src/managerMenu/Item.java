/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

/**
 * 商品クラス
 * @author 19jz0137
 */
public class Item {
    private String itemNumber;
    private String itemName;
    private int    unitPrice;
    private int    quantity;

    public Item() {
    }

    public Item(String itemNumber, String itemName, int unitPrice) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" + "itemNumber=" + itemNumber + ", itemName=" + itemName + ", unitPrice=" + unitPrice + ", quantity=" + quantity + '}';
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
        
}
