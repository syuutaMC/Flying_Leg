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
import javax.crypto.SealedObject;
import sys.DBManager;

/**
 * 売上DAO
 * @author 19jz0137
 */
public class SalesDAO {
    private static Connection con;
    private static PreparedStatement ps;

    public SalesDAO() {
        DBManager dbManager = DBManager.getDBManager();
        con = dbManager.getConnection();
    }
    
    /**
     * 売上情報SELECT処理
     * @return SELECT 結果
     * @throws SQLException 
     */
    public List<Sales> selectSalesExecute() throws SQLException {
        List<Sales> salesList = new ArrayList<>();
        try {
            salesList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                Sales sales = new Sales();
                setPayment(sales, rs);
                salesList.add(sales);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return salesList;
    }
    
    /**
     * 問い合わせ結果をsalesに格納
     * @param sales   売上情報
     * @param rs      問い合わせ結果
     */
    public void setPayment(Sales sales, ResultSet rs) {
        try {
            Date salesDate      = rs.getDate("SALES_DATE");
            int  storeNumber    = rs.getInt("STORE_NUMBER");
            int  orderQuantity  = rs.getInt("ORDER_QUANTITY");
            int  salesAmount    = rs.getInt("SALES_AMOUNT");
            
            sales.setSalesDate(salesDate);
            sales.setStoreNumber(storeNumber);
            sales.setOrderQuantity(orderQuantity);
            sales.setSalesAmount(salesAmount);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 週間売り上げ検索処理実行
     * @return 週間売上情報
     * @throws SQLException 
     */
    public List<SalesAtWeek> selectSalesAtWeeksExecute() throws SQLException {
        List<SalesAtWeek> salesAtWeekList = new ArrayList<>();
        try {
            salesAtWeekList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                SalesAtWeek salesAtWeek = new SalesAtWeek();
                setSalesAtWeek(salesAtWeek, rs);
                salesAtWeekList.add(salesAtWeek);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesAtWeekList;
    }
    
    /**
     * 問い合わせ結果をsalesAtWeekに格納
     * @param salesAtWeek 曜日売上
     * @param rs 　　　　　問い合わせ結果
     */
    public void setSalesAtWeek(SalesAtWeek salesAtWeek, ResultSet rs) {
        try {
            Date salesDate      = rs.getDate("SALES_DATE");
            int  weekNumber     = rs.getInt("WEEK_NUMBER");
            int  storeNumber    = rs.getInt("STORE_NUMBER");
            int  sun            = rs.getInt("SUN");
            int  mon            = rs.getInt("MON");
            int  tue            = rs.getInt("TUE");
            int  wed            = rs.getInt("WED");
            int  thu            = rs.getInt("THU");
            int  fry            = rs.getInt("FRY");
            int  sat            = rs.getInt("SAT");
            
            salesAtWeek.setSalesDate(salesDate);
            salesAtWeek.setWeekNumber(weekNumber);
            salesAtWeek.setStoreNumber(storeNumber);
            salesAtWeek.setSun(sun);
            salesAtWeek.setMon(mon);
            salesAtWeek.setTue(tue);
            salesAtWeek.setWed(wed);
            salesAtWeek.setThu(thu);
            salesAtWeek.setFry(fry);
            salesAtWeek.setSat(sat);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 週間売り上げ検索処理実行
     * @return 週間売上情報
     * @throws SQLException 
     */
    public List<SalesAtCategory> selectSalesAtCategoryExecute() throws SQLException {
        List<SalesAtCategory> salesAtCategoryList = new ArrayList<>();
        try {
            salesAtCategoryList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                SalesAtCategory salesAtCategory = new SalesAtCategory();
                setSalesAtCategory(salesAtCategory, rs);
                salesAtCategoryList.add(salesAtCategory);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesAtCategoryList;
    }
    
    /**
     * 問い合わせ結果をsalesAtWeekに格納
     * @param salesAtCategory
     * @param rs 　　　　　問い合わせ結果
     */
    public void setSalesAtCategory(SalesAtCategory salesAtCategory, ResultSet rs) {
        try {
            String itemNumber    = rs.getString("ITEM_MUMBER");
            String itemName      = rs.getString("ITEM_NAME");
            int    storeNumber   = rs.getInt("STORE_NUMBER");
            int    orderQuantity = rs.getInt("ORDER_QUANTITY");
            int    salesAmount   = rs.getInt("SALES_AMOUNT");
            
            salesAtCategory.setItemName(itemName);
            salesAtCategory.setStoreNumber(storeNumber);
            salesAtCategory.setOrderQuantity(orderQuantity);
            salesAtCategory.setSalesAmount(salesAmount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 当日の売上を検索する
     * @return 当日の売り上げ
     * @throws SQLException 
     */
    public List<Sales> dbSearchSalesToday() throws SQLException {
        List<Sales> salesList;
        String sql = "SELECT SALES_DATE, STORE_NUMBER, ORDER_QUANTITY, SALES_AMOUNT " + 
                     " FROM SALES_PER_DATE " +
                     " WHERE SALES_DATE = TRUNC(sysdate, 'DD') " +
                     " ORDER BY SALES_DATE ";
        try {
            ps = con.prepareStatement(sql);
            salesList = selectSalesExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salesList;
    }
    
    /**
     * 今週の売上を検索する
     * @return
     * @throws SQLException 
     */
    public List<Sales> dbSearchSalesThisWeek() throws SQLException {
        List<Sales> salesList;
        String sql = "SELECT SALES_DATE, STORE_NUMBER, ORDER_QUANTITY, SALES_AMOUNT " +
                     " FROM SALES_PER_DATE " +
                     " WHERE SALES_DATE BETWEEN TRUNC(sysdate, 'DAY') AND NEXT_DAY(TRUNC(sysdate, 'DAY'), '土') " +
                     " ORDER BY SALES_DATE ";
        try {
            ps = con.prepareStatement(sql);
            salesList = selectSalesExecute();
        }
        catch (SQLException e) {
            throw e;
        }
        return salesList;
    }
    
    /**
     * 今月の売上を確認する
     * @return 売上情報
     * @throws SQLException 
     */
    public List<Sales> dbSearchSalesThisMonth() throws SQLException {
        List<Sales> salesList;
        String sql = "SELECT SALES_DATE, STORE_NUMBER, ORDER_QUANTITY, SALES_AMOUNT " +
                     " FROM SALES_PER_MONTH " +
                     " WHERE TRUNC(SALES_DATE, 'MM') = TRUNC(sysdate, 'MM') " +
                     " ORDER BY SALES_DATE ";
        try {
            ps = con.prepareStatement(sql);
            salesList = selectSalesExecute();
        }
        catch (SQLException e) {
            throw e;
        }
        return salesList;
    }
    
    /**
     * 今月の各週の売り上げを確認する
     * @return
     * @throws SQLException 
     */
    public List<SalesAtWeek> dbSearchSalesThisMonthEveryWeek() throws SQLException {
        List<SalesAtWeek> salesAtWeekList;
        String sql = //"SELECT SALES_DATE, STORE_NUMBER, SUN, MON, TUE, WED, THU, FRY, SAT " +
                     "SELECT SALES_DATE, WEEK_NUMBER, STORE_NUMBER, SUN, MON, TUE, WED, THU, FRY, SAT " +
                     " FROM SALES_PER_MONTH_EVERY_WEEK" +
                     " WHERE TRUNC(SALES_DATE, 'MM') = TRUNC(sysdate, 'MM') ";
        try {
            ps = con.prepareCall(sql);
            salesAtWeekList = selectSalesAtWeeksExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salesAtWeekList;
    }
    
    /**
     * 今週の各曜日の売り上げを確認
     * @return
     * @throws SQLException 
     */
    public List<SalesAtWeek> dbSearchSalesThisWeekEveryDay() throws SQLException {
        List<SalesAtWeek> salesAtWeekList;
        String sql = //"SELECT WEEK_NUMBER, STORE_NUMBER, SUN, MON, TUE, WED, THU, FRY, SAT " +
                     "SELECT SALES_DATE, STORE_NUMBER, SUN, MON, TUE, WED, THU, FRY, SAT " +
                     " FROM  SALES_PER_DAY " +
                     " WHERE TRUNC(SALES_DATE, 'dd') = TRUNC(sysdate, 'dd') ";
        try {
            ps = con.prepareCall(sql);
            salesAtWeekList = selectSalesAtWeeksExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salesAtWeekList;
    }
    
    /**
     * 今月の商品カテゴリごとの売上を確認
     * @param categoryNumber 商品カテゴリ
     * @return 商品カテゴリ別売上情報
     * @throws SQLException 
     */
    public List<SalesAtCategory> dbSearchSalesAtCategoryThisMonth(String categoryNumber) throws SQLException {
        List<SalesAtCategory> salesAtCategoryList;
        String sql = "SELECT ITEM_NUMBER, ITEM_NAME, STORE_NUMBER, " +
                     " SUM(ORDER_QUANTITY) AS \"ORDER_QUANTITY\", " +
                     " SUM(SALES_AMOUNT) AS \"SALES_AMOUNT\" " +
                     " FROM SALES_AT_WEEK " +
                     " WHERE TRUNC(SALES_DATE, 'MM') = TRUNC(sysdate, 'MM') AND " +
                     " ITEM_NAME LIKE ? " +
                     " GROUP BY ITEM_NUMBER, ITEM_NAME, STORE_NUMBER ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryNumber + "%");
            salesAtCategoryList = selectSalesAtCategoryExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salesAtCategoryList;
    }
    
    /**
     * 今週の商品カテゴリごとの売上を確認
     * @param categoryNumber 商品カテゴリ
     * @return 商品カテゴリ別売上情報
     * @throws SQLException 
     */
    public List<SalesAtCategory> dbSearchSalesAtCategoryThisWeek(String categoryNumber) throws SQLException {
        List<SalesAtCategory> salesAtCategoryList;
        String sql = "SELECT ITEM_NUMBER, ITEM_NAME, STORE_NUMBER, " +
                     " SUM(ORDER_QUANTITY) AS \"ORDER_QUANTITY\", " +
                     " SUM(SALES_AMOUNT) AS \"SALES_AMOUNT\" " +
                     " FROM SALES_AT_WEEK " +
                     " WHERE TRUNC(SALES_DATE, 'dd') = TRUNC(sysdate, 'dd') " + 
                     " ITEM_NAME LIKE ? " +
                     "GROUP BY ITEM_NUMBER, ITEM_NAME, STORE_NUMBER ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryNumber + "%");
            salesAtCategoryList = selectSalesAtCategoryExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salesAtCategoryList;
    }
    
    /**
     * 当日の商品カテゴリごとの売上を確認
     * @param categoryNumber 商品カテゴリ
     * @return 商品カテゴリ別売上情報
     * @throws SQLException 
     */
    public List<SalesAtCategory> dbSearchSalesAtCategoryThisDate(String categoryNumber) throws SQLException {
        List<SalesAtCategory> salesAtCategoryList;
        String sql = "SELECT ITEM_NUMBER, ITEM_NAME, STORE_NUMBER, " +
                     " SUM(ORDER_QUANTITY) AS \"ORDER_QUANTITY\", " +
                     " SUM(SALES_AMOUNT) AS \"SALES_AMOUNT\" " +
                     " FROM SALES_AT_WEEK " +
                     " WHERE SALES_DATE BETWEEN TRUNC(sysdate, 'DAY') AND NEXT_DAY(TRUNC(sysdate, 'DAY'), '土') " + 
                     " ITEM_NAME LIKE ? " +
                     "GROUP BY ITEM_NUMBER, ITEM_NAME, STORE_NUMBER ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryNumber + "%");
            salesAtCategoryList = selectSalesAtCategoryExecute();
        } catch (SQLException e) {
            throw e;
        }
        return salesAtCategoryList;
    }
}
