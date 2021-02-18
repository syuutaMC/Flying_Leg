/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import managerMenu.item.Item;
import sys.DBManager;

/**
 * 注文DAO
 * @author 19jz0137
 */
public class OrderDAO {
    private static Connection con;
    private static PreparedStatement ps;
    
    /**
     * コンストラクタ
     * データベース接続情報設定
     */
    public OrderDAO() {
        DBManager dBManager = DBManager.getDBManager();
        con = dBManager.getConnection();
    }
    
    /**
     * 注文情報SELECT処理
     * @return SELECT 結果
     * @throws SQLException 
     */
    public List<Order> selectOrderExecute() throws SQLException {
        List<Order> orderList = new ArrayList<>();
        try {
            orderList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                Order order = new Order();
                setOrder(order, rs);
                orderList.add(order);
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderList;
    }
    
    /**
     * 問い合わせ結果をorderに設定
     * @param order 注文情報
     * @param rs    問い合わせ結果
     */
    public void setOrder(Order order, ResultSet rs) {
        try {
            int     orderNumber         = rs.getInt("ORDER_NUMBER");
            int     customerNumber      = rs.getInt("CUSTOMER_NUMBER");
            Date    deliveryTime        = rs.getDate("DELIVERY_TIME");
            String  deliveryToAddress   = rs.getString("DELIVERY_TO_ADDRESS");
            Date    paymentDay          = rs.getDate("PAYMENT_DAY");
            String  storeNumber         = rs.getString("STORE_NUMBER");
            
            order.setOrderNumber(orderNumber);
            order.setCustomerNumber(customerNumber);
            order.setDeliveryTime(deliveryTime);
            order.setDeliveryToAddress(deliveryToAddress);
            order.setPaymentDay(paymentDay);
            order.setStoreNumber(storeNumber);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 注文追加処理
     * @param customerNumber    顧客番号
     * @param deliveryToAddress 配達先住所
     * @param items              注文商品
     * @throws java.sql.SQLException
     */
    public void dbAddOrder(int customerNumber, String deliveryToAddress, List<Item> items) throws SQLException{
        
        String sql1 = "INSERT INTO " + 
                     " ORDERS(customer_number, delivery_to_address, store_number) " + 
                     " VALUES(?, ?, ?) ";
        
        String sql2 = "INSERT INTO " +
                      " ORDER_DETAILS(order_number, item_number, order_quantity) " +
                      " VALUES( (SELECT MAX(order_number) FROM orders), ?, ?) ";
        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, customerNumber);
            ps.setString(2, deliveryToAddress);
            ps.setString(3, "001"); //店舗番号
            
            ps.executeUpdate();
            
            //注文明細表に追加
            ps = con.prepareStatement(sql2);
            for (Item item : items) {
                ps.setString( 1, item.getItemNumber());
                ps.setInt(2, item.getQuantity());
                
                ps.executeUpdate();
            }
            
        }
        catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 注文情報を検索
     * @param orderNumber 注文番号
     * @return 注文情報
     * @throws SQLException 
     */
    public List<Order> dbSearchOrder(int orderNumber) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM ORDERS " +
                     " WHERE ORDER_NUMBER = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderNumber);
            orderList = selectOrderExecute();
        }
        catch (SQLException e) {
            throw e;
        }
        return orderList;
    }
    
    /**
     * 注文をキャンセル（削除）する
     * @param orderNo 注文番号
     * @return true 削除された | false 削除されなかった
     * @throws java.sql.SQLException
     */
    public boolean dbDeleteOrder(int orderNo) throws SQLException {
        String sql = "DELETE ORDERS " +
                     " WHERE ORDER_NUMBER = ? AND " +
                     "       PAYMENT_DAY IS NULL";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderNo);
            ps.setInt(2, orderNo);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 支払済みの注文かどうかチェックする
     * @param orderNo 注文番号
     * @return true 支払済みの注文 | false 未払いの注文
     * @throws java.sql.SQLException
     */
    public boolean dbCheckPaidOrder(int orderNo) throws SQLException {
        String sql = "SELECT * FROM ORDERS " +
                     " WHERE ORDER_MUMBER = ? AND " +
                     "       PAYMENT_DAY IS NOT NULL ";
        try {
            ps = con.prepareCall(sql);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw e; 
        }
    }
}
