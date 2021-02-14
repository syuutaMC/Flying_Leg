/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sys.DBManager;

/**
 * 商品カテゴリDAO
 * @author 19jz0137
 */
public class CategoryDAO {
    private static Connection con;
    private static PreparedStatement ps;

    public CategoryDAO() {
        DBManager dBManager = DBManager.getDBManager();
        con = dBManager.getConnection();
    }
    
    private List<Category> selectCategoryExecute() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {
                Category category = new Category();
                setCategory(category, rs);
                categoryList.add(category);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return categoryList;
    }
    
    /**
     * 問い合わせ結果をcaetgoryに格納
     * @param category 商品カテゴリ情報
     * @param rs       問い合わせ結果
     * @throws java.sql.SQLException
     */
    private void setCategory(Category category, ResultSet rs) throws SQLException {
        try {
            String categoryNumber = rs.getString("CATEGORY_NUMBER");
            String categoryName   = rs.getString("CATEGORY_NAME");
            category.setCategoryNumber(categoryNumber);
            category.setCategoryName(categoryName);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 商品カテゴリ全検索
     * @return 商品カテゴリ情報
     * @throws java.sql.SQLException 
     */
    public List<Category> dbSearchItemCategoryAll() throws SQLException {
        List<Category> categoryList;
        String sql = "SELECT * " +
                     " FROM item_categories ";
        try {
            ps = con.prepareStatement(sql);
            categoryList = selectCategoryExecute();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return categoryList;
    }
    
    /**
     * カテゴリ追加処理
     * @param categoryNumber カテゴリ番号
     * @param categoryName   カテゴリ名
     * @throws java.sql.SQLException
     */
    public void dbAddCategory(String categoryNumber, String categoryName) throws SQLException {
        String sql = "INSERT INTO categories(category_number, category_name)" + 
                        " VALUES( ?, ?) ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryNumber);
            ps.setString(2, categoryName);
            ps.executeQuery().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 商品カテゴリ編集処理
     * @param categoryNumber 編集するカテゴリ番号
     * @param category       新しい商品カテゴリ情報
     * @throws java.sql.SQLException
     */
    public void dbEditCategory(String categoryNumber, Category category) throws SQLException {
        String sql = "UPDATE category " + 
                     " SET category_number = ?, " +
                     "     category_name   = ? " +
                     " WHERE category_number = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryNumber);
            ps.setString(2, category.getCategoryNumber());
            ps.setString(3, category.getCategoryName());
            ps.executeQuery().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
