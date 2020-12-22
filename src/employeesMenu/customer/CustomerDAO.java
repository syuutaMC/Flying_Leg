/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import sys.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
