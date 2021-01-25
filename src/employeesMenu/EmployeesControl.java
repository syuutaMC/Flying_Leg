/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu;

import sys.MainMenuBoundary;
import employeesMenu.customer.CustomerControl;
import employeesMenu.order.OrderControl;
import sys.SystemControl;

/**
 * 従業員メニューコントロールクラス
 * @author 19jz0115
 */
public class EmployeesControl {
    private final CustomerControl customerControl;
    private final OrderControl orderControl;
    private SystemControl control;
    
    public EmployeesControl() {
        customerControl = new CustomerControl();
        orderControl = new OrderControl();
        control = new SystemControl();
    }
    
    public void setControl(SystemControl control){
        this.control = control;
    }
    
    /**
     * 顧客確認画面を表示する
     */
    public void showCustomerBoundary(){
        control.setVisibleMainMenu(false);
        customerControl.setControl(this);
        customerControl.initControl();
        customerControl.showCustomerCheckBoundary();
    }
    
    /**
     * 注文画面を表示する
     */
    public void showOrderBoundary(){
        control.setVisibleMainMenu(false);
        orderControl.setControl(this);
        orderControl.start();
    }
    
    /**
     * 画面を閉じる処理
     */
    public void exitMediaView(){
        control.setVisibleMainMenu(true);
    }
    
    /**
     * 顧客登録画面を表示する
     */
    public void showCustomerAddBoundary(){
        customerControl.setControl(this);
        customerControl.initControl();
        customerControl.showCustomerAddBoundary();
    }
    /**
     * 顧客登録画面を表示する
     * @param phoneNumber 電話番号
     */
    public void showCustomerAddBoundary(String phoneNumber){
        customerControl.setControl(this);
        customerControl.initControl();
        customerControl.showCustomerAddBoundary(phoneNumber);
    }
}
