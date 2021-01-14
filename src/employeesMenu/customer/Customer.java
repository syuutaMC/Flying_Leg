/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

/**
 * 顧客クラス
 * @author 19jz0137
 */
public class Customer {
    private int     customerNumber; //顧客番号
    private String  name;           //顧客名
    private String  phoneNumber;    //電話番号
    private String  address;        //住所
    private String  deliveryNote;   //配達備考
    
    public Customer(int customerNumber ,String name, String phoneNumber, String address, String deliveryNote) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.deliveryNote = deliveryNote;
    }

    public Customer(String name, String phoneNumber, String address, String deliveryNote) {
        this(0, name, phoneNumber, address, deliveryNote);
    }
    
    public Customer() {
        this(0, "", "", "", "");
    }
    
    /**
     * 有効な顧客情報かチェック
     * @return 有効 true | 無効 false
     */
    public boolean isValid() {
        return !(customerNumber <= 0 || name.equals("") || phoneNumber.equals("") || address.equals("") || deliveryNote.equals(""));
    }
    
    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }  
    
}
