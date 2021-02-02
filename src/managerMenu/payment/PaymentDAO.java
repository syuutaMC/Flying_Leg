/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import sys.DBManager;

/**
 * 入金確認DAO
 * @author 19jz0137
 */
public class PaymentDAO {
    private static Connection con;
    private static PreparedStatement ps;

    public PaymentDAO() {
        DBManager dBManager = DBManager.getDBManager();
        con = dBManager.getConnection();
    }
    
    
}
