/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

import managerMenu.payment.PaymentControl;
import managerMenu.item.ManageItemControl;
import managerMenu.employees.EmployeeControl;
import sys.SystemControl;

/**
 *
 * @author 19jz0115
 */
public class ManagerMenuControl {
    private SystemControl control;
    private final PaymentControl paymentControl;
    private final ManageItemControl manageItemControl;
    private final EmployeeControl employeeControl;
    private final EmployeeDAO employeeDAO;
    
    public ManagerMenuControl(){
        paymentControl = new PaymentControl();
        manageItemControl = new ManageItemControl();
        employeeControl = new EmployeeControl();
        employeeDAO = new EmployeeDAO();
    }
    
    /**
     * コントロールを設定
     * @param control SystemControl
     */
    public void setControl(SystemControl control){
        this.control = control;
    }
    
    /**
     * 売上管理画面の表示処理
     */
    public void showPaymentBoundary(){
        control.setVisibleMainMenu(false);
        paymentControl.setControl(this);
        paymentControl.start();
    }
    
    /**
     * 商品管理画面の表示処置
     */
    public void showManageitemBoundary(){
        control.setVisibleMainMenu(false);
        manageItemControl.setControl(this);
        manageItemControl.start();
    }
    
    /**
     * 従業員管理画面の表示処理
     */
    public void showManagerboundary(){
        control.setVisibleMainMenu(false);
        employeeControl.setControl(this);
        employeeControl.start();
    }
    
    /**
     * 画面を閉じる処理
     */
    public void exitMediaView(){
        control.setVisibleMainMenu(true);
    }
}
