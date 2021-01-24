package sys;

import employeesMenu.EmployeesControl;

/**
 * メインメニューコントローラー
 * @author 19jz0115
 */
public class SystemControl {
    
    private final MainMenuBoundary meinMenuBoundary;
    private final EmployeesControl employeesControl;
    
    public SystemControl(){
        meinMenuBoundary = new MainMenuBoundary();
        employeesControl = new EmployeesControl();
    }
    
    public void start(){
        meinMenuBoundary.setControl(this);
        meinMenuBoundary.setVisible(true);
    }
    
    /**
     * 注文画面を表示する
     */
    public void showOrderBoundary(){
        employeesControl.setControl(this);
        employeesControl.showOrderBoundary();
    }
    
    /**
     * 顧客確認画面を表示する
     */
    public void showCustomerBoundary(){
        employeesControl.setControl(this);
        employeesControl.showCustomerBoundary();
    }
    
    public static void main(String[] args) {
        new SystemControl().start();
    }
}
