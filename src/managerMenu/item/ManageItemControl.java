/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.item;

import java.sql.SQLException;
import java.util.List;
import managerMenu.ManagerMenuControl;
import employeesMenu.order.ItemDAO;

/**
 *
 * @author syuuta
 */
public class ManageItemControl {
    private final ManageItemBoundary manageItemBoundary;
    private final CategoryDAO categoryDAO;
    private final ItemDAO itemDAO;
    private final ManageItemPopupBoundary manageItemPopupBoundary;
    private ManagerMenuControl control;
    
    public ManageItemControl(){
        manageItemBoundary = new ManageItemBoundary();
        manageItemPopupBoundary = new ManageItemPopupBoundary();
        categoryDAO = new CategoryDAO();
        itemDAO = new ItemDAO();
        
    }
    
    public void setControl(ManagerMenuControl control){
        this.control = control;
    }
    
    public void showManageItemBoundary() {
        start();
    }
    
    /**
     * 商品編集画面表示
     */
    public void showManageItemPopupBoundary() {
        manageItemPopupBoundary.setControl(this);
        manageItemBoundary.setVisible(false);
        manageItemPopupBoundary.setVisible(true);
    }
    
    public void showMenu(List<Category> categoryList) {
        List<Item> itemList;
        int i = 0;
        for (Category category : categoryList) {
            itemList = itemDAO.dbSearchItemCategory(category.getCategoryNumber());
            manageItemBoundary.showMenuTable(itemList, i++);
        }
    }
    
    /**
     * 商品カテゴリを取得
     * @return 商品カテゴリリスト 
     */
    public List<Category> getCategory(){
        List<Category> categoryList = null;
        try {
            categoryList = categoryDAO.dbSearchItemCategoryAll();
        } catch (SQLException e) {
            manageItemBoundary.showDBErrorMessage();
        }
        
        return categoryList;
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
    
    public void changeCardLayout(String card){
        manageItemBoundary.changeCardLayout(card);
    }
    
    /**
     * 商品選択
     * @param itemNumber 商品番号
     */
    public void selectedItem(String itemNumber) {
        List<Item> itemList;
        try {
            itemList = itemDAO.dbSearchItemItemNumber(itemNumber);
            if (itemList.size() > 0) {
                manageItemPopupBoundary.setItem(itemList.get(0));
                showManageItemPopupBoundary();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 商品編集
     * @param item 商品情報
     */
    public void editItem(Item item) {
        try {
            
            if (itemDAO.dbUpdateItem(item.getItemNumber(), item) > 0) {
                manageItemPopupBoundary.showUpdateSuccessMessage();
                manageItemPopupBoundary.exit();
            }
            else {
                manageItemPopupBoundary.showUpdateFailedMessage();
            }
            
        } catch (SQLException e) {
            manageItemPopupBoundary.showDBErrorMessage();
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
        manageItemBoundary.changeCardLayout("card2");
        manageItemBoundary.setVisible(false);
        control.exitMediaView();
    }
}
