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
import java.util.List;
import managerMenu.item.Item;
import sys.DBManager;

/**
 * 注文DAO
 * @author 19jz0137
 */
public class ItemDAO {
    private static Connection con;
    private static PreparedStatement ps;
    
    /**
     * コンストラクタ
     * データベース接続情報設定
     */
    public ItemDAO() {
        DBManager dBManager = DBManager.getDBManager();
        con = dBManager.getConnection();
    }
    
    /**
     * Items テーブル検索処理実行
     * @return 商品情報
     * @throws SQLException 
     */
    public List<Item> selectItemExucte() throws SQLException {
        List<Item> itemList = new ArrayList<>();
        try {
            itemList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                Item item = new Item();
                setItem(item, rs);
                itemList.add(item);
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return itemList;
    }
    
    /**
     * 問い合わせ結果をitemに格納
     * @param item 商品情報
     * @param rs   問い合わせ結果 
     */
    public void setItem(Item item, ResultSet rs) {
        try {
            String itemNumber = rs.getString("ITEM_NUMBER");
            String itemName   = rs.getString("ITEM_NAME");
            int    unitPrice  = rs.getInt("UNIT_PRICE");
            item.setItemNumber(itemNumber);
            item.setItemName(itemName);
            item.setUnitPrice(unitPrice);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 商品番号検索
     * @param itemNumber 商品番号
     * @return 検索結果リスト
     * @throws SQLException 
     */
    public List<Item> dbSearchItemItemNumber(String itemNumber) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS " +
                     " WHERE ITEM_NUMBER = ? ";
        try {
            ps = con.prepareStatement(sql);
            //ps.setString(1, itemNumber);
            ps.setString(1, itemNumber);
            itemList = selectItemExucte();
        }
        catch (Exception e) {
            throw e;
        }
        
        return itemList;
    }
    
    /**
     * 商品区別検索
     * @param itemName 商品名
     * @return 商品情報
     * @throws SQLException 
     */
    public List<Item> dbSearchItemItemName(String itemName) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS " + 
                     " WHERE ITEM_NUMBER LIKE ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + itemName + "%");
            itemList = selectItemExucte();
        }
        catch (Exception e) {
            throw e;
        }
        
        return itemList;
    }
    
    /**
     * 商品全件検索
     * @return 商品情報
     * @throws SQLException 
     */
    public List<Item> dbSearchItemAll() {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS ";
        try {
            ps = con.prepareStatement(sql);
            itemList = selectItemExucte();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return itemList;
    }
    
    /**
     * メインメニュー検索
     * @return メイン商品リスト 
     */
    public List<Item> dbSearchItemMainMenu() {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS WHERE ITEM_NUMBER LIKE ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "M%");
            itemList = selectItemExucte();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return itemList;
    }
    
    /**
     * ドリンクメニュー検索
     * @return ドリンク商品リスト
     */
    public List<Item> dbSearchItemDrinkMenu() {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS WHERE ITEM_NUMBER LIKE ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "D%");
            itemList = selectItemExucte();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return itemList;
    }
    
    /**
     * サイドメニュー検索
     * @return サイド商品リスト
     */
    public List<Item> dbSearchItemSideMenu() {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS WHERE ITEM_NUMBER LIKE ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "S%");
            itemList = selectItemExucte();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return itemList;
    }
}

