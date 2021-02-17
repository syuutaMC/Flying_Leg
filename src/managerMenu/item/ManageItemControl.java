/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.item;

import java.sql.SQLException;
import java.util.List;
import sys.SystemControl;
import managerMenu.ManagerMenuControl;

/**
 *
 * @author syuuta
 */
public class ManageItemControl {
    private final ManageItemBoundary manageItemBoundary;
    private final CategoryDAO categoryDAO;
    private ManagerMenuControl control;
    
    public ManageItemControl(){
        manageItemBoundary = new ManageItemBoundary();
        categoryDAO = new CategoryDAO();
    }
    
    public void setControl(ManagerMenuControl control){
        this.control = control;
    }
    
    
    public void showManageItemBoundary() {
        start();
    }
    
    /**
     * コンボボックスに商品カテゴリを設定
     */
    public void setCategoryComboBox() {
        List<Category> categoryList;
        try {
            categoryList = categoryDAO.dbSearchItemCategoryAll();
            manageItemBoundary.setCategoryComboBox(categoryList);
        } catch (SQLException e) {
            manageItemBoundary.showDBErrorMessage();
        }
    }
    
    public void start() {
        manageItemBoundary.setControl(this);
        manageItemBoundary.setVisible(true);
    }
    
    /**
     * ×ボタン処理
     */
    public void exit() {
        manageItemBoundary.setVisible(false);
        control.exitMediaView();
    }
}
