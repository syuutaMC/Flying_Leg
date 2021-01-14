/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
    public void dbAddOrder(Order order)  {
        String sql = "INSERT INTO " + 
                     "ORDERS(customer_number, delivery_time, delivery_to_address, )"
    }
}
