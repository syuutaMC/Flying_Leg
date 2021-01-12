/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu;

import employeesMenu.customer.CustomerControl;
import employeesMenu.order.OrderControl;

/**
 * 従業員メニューコントロールクラス
 * @author 19jz0115
 */
public class EmployeesMenuControl {
    private final EmployeesMenuBoundary employeesMenuBoundary;
    private final CustomerControl customerControl;
    private final OrderControl orderControl;
    
    public EmployeesMenuControl() {
        employeesMenuBoundary = new EmployeesMenuBoundary();
        customerControl = new CustomerControl();
        orderControl = new OrderControl();
    }
    
    public void start(){
        employeesMenuBoundary.setControl(this);
        employeesMenuBoundary.setVisible(true);
    }
    
    public void showCustomerBoundary(){
        employeesMenuBoundary.setVisible(false);
        customerControl.setControl(this);
        customerControl.initControl();
        customerControl.showCustomerCheckBoundary();
    }
    
    public void showOrderBoundary(){
        employeesMenuBoundary.setVisible(false);
        orderControl.setControl(this);
        orderControl.start();
    }
    
    public void exitMediaView(){
        employeesMenuBoundary.setVisible(true);
    }
    
    public void showCustomerAddBoundary(){
        customerControl.setControl(this);
        customerControl.initControl();
        customerControl.showCustomerAddBoundary();
    }
    
    public static void main(String[] args) {
        new EmployeesMenuControl().start();
    }
}
