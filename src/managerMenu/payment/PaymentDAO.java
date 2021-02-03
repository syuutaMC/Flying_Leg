/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    public List<Payment> selectPaymentExecute() throws SQLException {
        List<Payment> paymentList = new ArrayList<>();
        try {
            paymentList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                Payment payment = new Payment();
                setPayment(payment, rs);
                paymentList.add(payment);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return paymentList;
    }
    
    /**
     * 問い合わせ結果をpaymentに格納
     * @param payment 支払情報
     * @param rs      問い合わせ結果
     */
    public void setPayment(Payment payment, ResultSet rs) {
        try {
            String  name        = rs.getString("NAME");
            String  phoneNumber = rs.getString("PHONE_NUMBER");
            Date    orderDate   = rs.getDate("ORDER_DATE");
            Date    paymentDay  = rs.getDate("PAYMENT_DAY");
            int     amount      = rs.getInt("AMOUNT");
            payment.setName(name);
            payment.setPhoneNumber(phoneNumber);
            payment.setOrderDate(orderDate);
            payment.setPaymentDay(paymentDay);
            payment.setAmount(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 支払情報を全件問い合わせ（結果は注文日の降順）
     * @return 支払情報
     * @throws SQLException 
     */
    public List<Payment> dbSearchPaymentAll() throws SQLException{
        List<Payment> paymentList = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try {
            ps = con.prepareStatement(sql);
            paymentList = selectPaymentExecute();
        } catch (SQLException e) {
            throw e;
        }
        
        return paymentList;
    }
    
    
}
