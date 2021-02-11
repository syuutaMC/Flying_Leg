/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.employeeBoundary;

import managerMenu.ManagerMenuControl;
/**
 * 従業員管理コントロール
 * @author 19jz0115
 */
public class EmployeeControl {
    private ManagerMenuControl control;
    private EmployeesBoundary employeesBoundary;
    
    public EmployeeControl() {
        employeesBoundary = new EmployeesBoundary();
    }
    
    public void setControl(ManagerMenuControl control){
        this.control = control;
    }
    
    public void showMaagerMenuBoundary(){
        employeesBoundary.setVisible(true);
        employeesBoundary.setControl(this);
    }
}
