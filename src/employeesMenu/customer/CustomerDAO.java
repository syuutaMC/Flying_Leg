/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import sys.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 顧客DAO
 * @author 19jz0137
 */
public class CustomerDAO {
    private static Connection con;
    private static PreparedStatement ps;
    
    /**
     * コンストラクタ
     * データーベース接続情報設定
     */
    public CustomerDAO(){
        DBManager dbManager = DBManager.getDBManager();
        con = dbManager.getConnection();
    }
    
    /**
     * Customer テーブル検索処理実行
     * @return 顧客情報
     * @throws SQLException 
     */
    public Customer selectCustomerExcute() throws SQLException {
        Customer customer = new Customer();
        try {
            ResultSet rs = ps.executeQuery();
            setCustomer(customer, rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
    
    /**
     * 問い合わせ結果をcustomerに格納
     * @param customer 問い合わせ結果を格納
     * @param rs       問い合わせ結果
     */
    public void setCustomer(Customer customer, ResultSet rs) {
        try {
            String name = rs.getString("NAME");
            String phoneNumber = rs.getString("PHONE_NUMBER");
            String address = rs.getString("ADDRESS");
            String deliveryNote = rs.getString("DELIVERY_NOTE");
            customer.setName(name);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            customer.setDeliveryNote(deliveryNote);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        
    }
            
    
    public Customer dbSearchCustomerPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM CUSTOMERS " + 
                     " WHERE PHONE_NUMBER = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            return 
        }
        catch (SQLException e) {
        }
    }
}
