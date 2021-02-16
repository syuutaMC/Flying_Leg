/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.employees;

import java.util.List;
import managerMenu.Employee;
import managerMenu.ManagerMenuControl;


/**
 * 従業員管理コントロール
 * @author 19jz0115
 */
public class EmployeeControl {
    private ManagerMenuControl control;
    private final EmployeeBoundary employeeBoundary;
    
    public EmployeeControl() {
        employeeBoundary = new EmployeeBoundary();
    }
    
    /**
     * コントロールをセット
     * @param control ManagaerMenuControl
     */
    public void setControl(ManagerMenuControl control){
        this.control = control;
    }
    
    /**
     * カードレイアウトの変更
     * @param card カード名
     */
    public void changeCardLayout(String card){
        employeeBoundary.changeCardLayout(card);
    }
    
    /**
     * エラーメッセージ表示
     * @param message
     * @param title 
     */
    public void showErroeMessage(String message, String title){
        employeeBoundary.showErrorMessage(message, title);
    }
    
    /**
     * 従業員番号検索
     * @param employeeNumber 
     */
    public void searchEmployeeNumber(String employeeNumber){
        List<Employee> emp = control.searchEmployeeNumber(employeeNumber);
        
        if(emp.size() >= 0){
            employeeBoundary.setEmployee(emp.get(0));
        }
    }
    
    /**
     * マネージャー管理メニュー表示処理
     */
    public void start(){
        employeeBoundary.setVisible(true);
        employeeBoundary.setControl(this);
    }
}
