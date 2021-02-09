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
    
    /**
     * 支払情報SELECT処理
     * @return SELECT 結果
     * @throws SQLException 
     */
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
            int     orderNumber = rs.getInt("ORDER_NUMBER");
            String  name        = rs.getString("NAME");
            String  phoneNumber = rs.getString("PHONE_NUMBER");
            Date    orderDate   = rs.getDate("ORDER_DATE");
            Date    paymentDay  = rs.getDate("PAYMENT_DAY");
            int     amount      = rs.getInt("AMOUNT");
            payment.setOrderNumber(orderNumber);
            payment.setName(name);
            payment.setPhoneNumber(phoneNumber);
            payment.setOrderDate(orderDate);
            payment.setPaymentDay(paymentDay);
            payment.setAmount(amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 売上情報SELECT処理
     * @return SELECT 結果
     * @throws SQLException 
     */
    public List<Salse> selectSalseExecute() throws SQLException {
        List<Salse> salseList = new ArrayList<>();
        try {
            salseList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                Salse salse = new Salse();
                setPayment(salse, rs);
                salseList.add(salse);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return salseList;
    }
    
    /**
     * 問い合わせ結果をsalseに格納
     * @param salse   売上情報
     * @param rs      問い合わせ結果
     */
    public void setPayment(Salse salse, ResultSet rs) {
        try {
            Date paymentDay     = rs.getDate("PAYMENT_DAY");
            int  storeNumber    = rs.getInt("STORE_NUMBER");
            int  orderQuantity  = rs.getInt("ORDER_QUANTITY");
            int  salseAmount    = rs.getInt("SALSE_AMOUNT");
            
            salse.setPaymentDay(paymentDay);
            salse.setStoreNumber(storeNumber);
            salse.setOrderQuantity(orderQuantity);
            salse.setSalseAmount(salseAmount);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 支払情報を全件問い合わせ（結果は注文日の降順）
     * @return 支払情報
     * @throws SQLException 
     */
    public List<Payment> dbSearchPaymentAll() throws SQLException {
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
    
    /**
     * 支払い済みの支払情報を問い合わせ
     * @return 支払情報
     * @throws SQLException 
     */
    public List<Payment> dbSearchPaymentPaid() throws SQLException {
        List<Payment> paymentList = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE payment_day IS NOT NULL ";
        try {
            ps = con.prepareStatement(sql);
            paymentList = selectPaymentExecute();
        } catch (SQLException e) {
            throw e;
        }
        
        return paymentList;
    }
    
    /**
     * 未支払いの支払情報を問い合わせ
     * @return 支払い情報
     * @throws SQLException 
     */
    public List<Payment> dbSearchPaymentUnpaid() throws SQLException {
        List<Payment> paymentList = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE payment_day IS NULL ";
        try {
            ps = con.prepareStatement(sql);
            paymentList = selectPaymentExecute();
        } catch (SQLException e) {
            throw e;
        }
        
        return paymentList;
    }
    
    /**
     * 支払情報の注文番号検索
     * @return 支払情報
     * @throws SQLException 
     */
    public List<Payment> dbSearchPaymentOrderNumber(int orderNumber) throws SQLException {
        List<Payment> paymentList = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE order_number = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderNumber);
            paymentList = selectPaymentExecute();
        } catch (SQLException e) {
            throw e;
        }
        
        return paymentList;
    }
    
    /**
     * 支払済み設定処理
     * 当日の日付で入金日を指定し支払い済みにする
     * @param orderNumber   支払い済みにする注文番号
     * @throws SQLException 
     */
    public void dbSetPaymentPaid(int orderNumber) throws SQLException {
        String sql = "UPDATE orders " +
                     " SET payment_day = sysdate " +
                     " WHERE order_number = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderNumber);
            ps.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 当日の売上を検索する
     * @return 当日の売り上げ
     * @throws SQLException 
     */
    public List<Salse> dbSearchSalseToday() throws SQLException {
        List<Salse> salseList = new ArrayList<>();
        String sql = "SELECT * FROM SALSE_PER_DATE " +
                     " WHERE PAYMENT_DAY = TRUNC(sysdate, 'DD') " +
                     " ORDER BY PAYMENT_DAY ";
        try {
            ps = con.prepareStatement(sql);
            ps.executeQuery();
            salseList = selectSalseExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salseList;
    }
    
    /**
     * 今週の売上を検索する
     * @return
     * @throws SQLException 
     */
    public List<Salse> dbSearchSalseThisWeek() throws SQLException {
        List<Salse> salseList = new ArrayList<>();
        String sql = "SELECT * FROM SALSE_PER_DATE " +
                     " WHERE PAYMENT_DAY BETWEEN TRUNC(sysdate, 'DAY') AND NEXT_DAY(TRUNC(sysdate, 'DAY'), '土') " +
                     " ORDER BY PAYMENT_DAY ";
        try {
            ps = con.prepareStatement(sql);
            ps.executeQuery();
            salseList = selectSalseExecute();
        }
        catch (SQLException e) {
            throw e;
        }
        return salseList;
    }
    
    /**
     * 今月の売上を確認する
     * @return 売上情報
     * @throws SQLException 
     */
    public List<Salse> dbSearchSalseThisMonth() throws SQLException {
        List<Salse> salseList = new ArrayList<>();
        String sql = "SELECT * FROM SALSE_PER_DATE " +
                     " WHERE TRUNC(PAYMENT_DAY, 'MM') = TRUNC(sysdate, 'MM') " +
                     " ORDER BY PAYMENT_DAY ";
        try {
            ps = con.prepareStatement(sql);
            ps.executeQuery();
            salseList = selectSalseExecute();
        }
        catch (SQLException e) {
            throw e;
        }
        return salseList;
    }
            
            
}
