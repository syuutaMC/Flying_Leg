/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import employeesMenu.customer.Customer;
import employeesMenu.customer.CustomerDAO;
import employeesMenu.EmployeesControl;
import java.sql.SQLException;
import java.util.List;
import managerMenu.item.Category;
import managerMenu.item.CategoryDAO;
import managerMenu.item.Item;

/**
 *
 * @author 19jz0115
 */
public class OrderControl {
    private final OrderBoundary     orderBoundary;
    private EmployeesControl    control;
    private final ItemDAO           itemDAO;
    private final CustomerDAO       customerDAO;
    private final OrderDAO          orderDAO;
    private final Category          category;
    private final CategoryDAO       categoryDAO;
    
    public OrderControl() {
        orderBoundary   = new OrderBoundary();
        itemDAO         = new ItemDAO();
        customerDAO     = new CustomerDAO();
        orderDAO        = new OrderDAO();
        category        = new Category();
        categoryDAO     = new CategoryDAO();
        setControl(control);
    }
    
    /**
     * コントロールを設定
     * @param control 従業員メニューコントロール
     */
    public void setControl(EmployeesControl control){
        this.control = control;
    }
    
    public void start() {
        orderBoundary.setControl(this);
        orderBoundary.setVisible(true);
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
            orderBoundary.showDBErrorMessage();
        }
        
        return categoryList;
    }
    
    /**
     * 顧客を検索する
     * @param phoneNumber 電話番号
     */
    public void searchCustomer(String phoneNumber) {
        try {
            List<Customer> customer = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
            if (customer.size() > 0) {
                orderBoundary.showCustomerTextField(customer.get(0));
            }
            else {
                orderBoundary.showCustomerNotFoundErrorMessage(phoneNumber);
            }
        } catch (SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 商品を商品番号で検索する
     * @param itemNumber 商品番号
     */
    public void searchItemItemNumber(String itemNumber) {
        try {
            List<Item> itemList = itemDAO.dbSearchItemItemNumber(itemNumber);
            if (itemList.size() > 0) {
                orderBoundary.addOrderTable(itemList.get(0));
                orderBoundary.showOrderTotalPrice(orderBoundary.calcTotalPrice());
            }
            else {
                orderBoundary.showItemNotFoundErrorMessage();
            }
            
        }
        catch (SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 商品を商品名で検索する
     * @param itemName 商品名
     */
    public void searchItemItemName(String itemName) {
        try {
            List<Item> itemList = itemDAO.dbSearchItemItemName(itemName);
            if (itemList.size() > 0) {
                orderBoundary.addOrderTable(itemList.get(0));
            }
            else {
                orderBoundary.showItemNotFoundErrorMessage();
            }
        }
        catch (SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 他配達先指定
     * @param b true 他配達先指定 | false 顧客情報の配達先
     */
    public void setOtherDeliveryDestination(boolean b) {
        OrderBoundary.setDefaultLookAndFeelDecorated(b);
    }
    
    public void showCustomerAddBoundary(){
        control.showCustomerAddBoundary();
    }
    
    public void showCustomerAddBoundary(String phoneNumber){
        control.showCustomerAddBoundary(phoneNumber);
    }
    
    public boolean checkAddress(){
      return orderBoundary.checkAddress();
    }
   
    public void showMenu(List<Category> categoryList) {
        List<Item> itemList;
        int i = 0;
        for (Category category : categoryList) {
            itemList = itemDAO.dbSearchItemCategory(category.getCategoryNumber());
            orderBoundary.showMenuTable(itemList, i++);
        }
    }
    
    /**
     * 注文商品追加
     * @param itemNumber 商品番号
     */
    public void addOrderItem(String itemNumber) {
        try {
            List<Item> itemList = itemDAO.dbSearchItemItemNumber(itemNumber);
            if (itemList.size() > 0) {
                int row = orderBoundary.searchOrderItem(itemList.get(0).getItemNumber());
                if (row > -1) {
                    orderBoundary.incrementOrderItem(row);
                }else {
                    orderBoundary.addOrderTable(itemList.get(0));
                }
                
                orderBoundary.showOrderTotalPrice(orderBoundary.calcTotalPrice());
            } else {
                orderBoundary.showItemNotFoundErrorMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 注文商品を取り除く
     * @param row 行番号
     */
    public void removeOrderItem(int row) {
        orderBoundary.removeOrderItem(row);
        orderBoundary.showOrderTotalPrice(orderBoundary.calcTotalPrice());
    }
    
    /**
     * 注文確認画面表示
     */
    public void showCardFinalCheck() {
        int totalPrice = orderBoundary.calcTotalPrice();
        if (OrderBoundary.ORDER_TOTAL_PRICE_UNDER_LIMIT > (int)Math.floor(totalPrice * (orderBoundary.TAX + 1))) {
            orderBoundary.setEnabledFinalCheck(false);
            orderBoundary.showTotalPriceErrorMessage();
        }
        else {
            orderBoundary.setEnabledFinalCheck(true);
            orderBoundary.showOrderListTable();
            orderBoundary.showFinalCheckPanel();
        }
        
    }
    
    /**
     * 注文確定処理
     * @param customerNumber    顧客番号
     * @param deliveryToAddress 配達先住所
     * @param items             注文商品
     */
    public void orderFixing(int customerNumber, String deliveryToAddress, List<Item> items) {
        try {
            orderDAO.dbAddOrder(customerNumber, deliveryToAddress, items);
            orderBoundary.showOrderFixingSuccessMessage();
            orderBoundary.orderExit();
        }
        catch(SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 注文キャンセル処理
     */
    public void orderCancel(int orderNumber) {
        List<Order> orderList;
        try {
            orderList = orderDAO.dbSearchOrder(orderNumber);
            if (orderList.size() <= 0) {
                orderBoundary.showErrorMessage("注文が存在しません", "エラー");
                return;
            }
            
            if (orderDAO.dbCheckPaidOrder(orderNumber)) {
                orderDAO.dbDeleteOrder(orderNumber);
                orderBoundary.showConfirmMessage("削除しました", "確認");
            }
            else {
                orderBoundary.showErrorMessage("入金済みの注文です", "エラー");
            }
        }
        catch (SQLException e) {
        }
    }
    
    /**
     * ×ボタン処理
     */
    public void exit(){
        orderBoundary.showCardAShippingAddress();
        orderBoundary.setVisible(false);
        orderBoundary.reset();

        control.exitMediaView();
    }
}
