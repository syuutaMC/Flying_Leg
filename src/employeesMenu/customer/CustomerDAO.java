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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Customer> selectCustomerExcute() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        try {
            customerList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {
                Customer customer = new Customer();
                setCustomer(customer, rs);
                customerList.add(customer);
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customerList;
    }
    
    /**
     * 問い合わせ結果をcustomerに格納
     * @param customer 問い合わせ結果を格納
     * @param rs       問い合わせ結果
     */
    public void setCustomer(Customer customer, ResultSet rs) {
        try {
            int    customerNumber = rs.getInt("CUSTOMER_NUMBER");
            String name           = rs.getString("NAME");
            String phoneNumber    = rs.getString("PHONE_NUMBER");
            String address        = rs.getString("ADDRESS");
            String deliveryNote   = rs.getString("DELIVERY_NOTE");
            customer.setCustomerNumber(customerNumber);
            customer.setName(name);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            customer.setDeliveryNote(deliveryNote);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
            
    /**
     * 電話番号検索 
     * @param phoneNumber 電話番号
     * @return 顧客情報
     * @throws java.sql.SQLException
     */
    public List<Customer> dbSearchCustomerPhoneNumber(String phoneNumber) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS " + 
                     " WHERE PHONE_NUMBER = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            customerList = selectCustomerExcute();
        }
        catch (SQLException e) {
            throw e;
        }
        
        return customerList;
    }
    
    /**
     * 顧客追加
     * @param customer 顧客情報 
     */
    public void dbAddCustomer(Customer customer) throws SQLIntegrityConstraintViolationException, SQLException {
        String sql = "INSERT INTO " + 
                     " CUSTOMERS " + 
                     " VALUES( ?, ?, ?, ?) ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customer.getName());            //顧客名
            ps.setString(2, customer.getPhoneNumber());     //電話番号
            ps.setString(3, customer.getAddress());         //住所
            ps.setString(4, customer.getDeliveryNote());    //配達備考
        }
        catch(SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        }
        catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 顧客情報更新
     * @param customer 顧客情報 
     * @throws java.sql.SQLException 
     */
    public void dbUpdateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE CUSTOMERS " +   
                     " SET   NAME = ?," + 
                     "       PHONE_NUMBER = ?," +
                     "       ADDRESS = ?," + 
                     "       DELIVERY_NOTE = ?" + 
                     " WHERE CUSTOMER_NUMBER = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getDeliveryNote());
            ps.setInt(5, customer.getCustomerNumber());
        } catch (SQLException e) {
            throw e;
        }
    }
}
