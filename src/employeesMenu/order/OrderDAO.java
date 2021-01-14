/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
     */
    public void dbAddOrder(int customerNumber, String deliveryToAddress, OrderDetail orderDetail) throws SQLException  {
        String sql = "INSERT " + 
                     " INTO ORDERS( customer_number, delivery_to_address, store_number) " + 
                     " VALUES( ?, ?, ?) "; 
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, customerNumber);
            ps.setString(2, deliveryToAddress);
            ps.setString(3, "001");
            
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
    }
}
