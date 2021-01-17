/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import managerMenu.Item;
import sys.DBManager;

/**
 *
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
     * 注文追加処理
     * @param customerNumber    顧客番号
     * @param deliveryToAddress 配達先住所
     * @param items              注文商品
     */
    public void dbAddOrder(int customerNumber, String deliveryToAddress, Item[] items) throws SQLException  {
        String sql1 = "INSERT INTO " + 
                     " ORDERS(customer_number, delivery_to_address, store_number) " + 
                     " VALUES(?, ?, ?) ";
        
        String sql2 = "INSERT INTO " +
                      " ORDER_DETAILS(order_number, item_number, order_quantity)" +
                      " VALUES( orders_order_no_sq, ?, ?)";
        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, customerNumber);
            ps.setString(2, deliveryToAddress);
            ps.setString(3, "001"); //店舗番号
            
            ps.executeUpdate();
            
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
}
