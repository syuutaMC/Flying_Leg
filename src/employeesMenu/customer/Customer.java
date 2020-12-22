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
    private String  name;           //顧客名
    private String  phoneNumber;    //電話番号
    private String  address;        //住所
    private int     customorNumber; //顧客番号
    private String  deliveryNote;   //配達備考

    public Customer(String name, String phoneNumber, String address, int customorNumber, String deliveryNote) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setAddress(address);
        setCustomorNumber(customorNumber);
        setDeliveryNote(deliveryNote);
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

    public int getCustomorNumber() {
        return customorNumber;
    }

    public String getDeliveryNote() {
        return deliveryNote;
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

    public void setCustomorNumber(int customorNumber) {
        this.customorNumber = customorNumber;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }
    
    
}
