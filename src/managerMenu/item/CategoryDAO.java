/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import sys.DBManager;

/**
 *
 * @author 19jz0137
 */
public class CategoryDAO {
    private static Connection con;
    private static PreparedStatement ps;

    public CategoryDAO() {
        DBManager dBManager = DBManager.getDBManager();
        con = dBManager.getConnection();
    }
    
    public List<Category> selectCategoryExecute() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return categoryList;
    }
    
    /**
     * 問い合わせ結果をcaetgoryに格納
     * @param category 商品カテゴリ情報
     * @param rs       問い合わせ結果
     */
    public void setCategory(Category category, ResultSet rs) {
        try {
            String categoryNumber = rs.getString("CATEGORY_NUMBER");
            String categoryName   = rs.getString("CATEGORY_NAME");
            category.setCategoryNumber(categoryNumber);
            category.setCategoryName(categoryName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 商品カテゴリ全検索
     * @return 
     */
    public List<Category> dbSearchItemCategory() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT category_number " +
                     " FROM categories ";
        try {
            ps = con.prepareStatement(sql);
            categoryList = selectCategoryExecute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
    
    /**
     * カテゴリ追加処理
     * @param categoryNumber カテゴリ番号
     * @param categoryName   カテゴリ名
     */
    public void dbAddCategory(String categoryNumber, String categoryName) {
        String sql = "INSERT INTO categories(category_number, category_name)" + 
                        " VALUES( ?, ?) ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoryNumber);
            ps.setString(2, categoryName);
            ps.executeQuery().close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 商品カテゴリ編集処理
     * @param categoryNumber 編集するカテゴリ番号
     * @param category       新しい商品カテゴリ情報
     */
    public void dbEditCategory(String categoryNumber, Category category) {
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
