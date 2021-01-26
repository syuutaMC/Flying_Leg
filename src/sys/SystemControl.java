package sys;

import employeesMenu.EmployeesControl;

/**
 * メインメニューコントローラー
 * @author 19jz0115
 */
public class SystemControl {
    
    private final MainMenuBoundary mainMenuBoundary;
    private final EmployeesControl employeesControl;
    
    public SystemControl(){
        mainMenuBoundary = new MainMenuBoundary();
        employeesControl = new EmployeesControl();
    }
    
    public void start(){
        mainMenuBoundary.setControl(this);
        setVisibleMainMenu(true);
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
    
    public void changeCardLayout(String str){
        mainMenuBoundary.showCardLayout(str);
    }
    
    /**
     * メインメニューの可視化設定
     * @param b true : 表示 false : 非表示
     */
    public void setVisibleMainMenu(boolean b) {
        mainMenuBoundary.setVisible(b);
    }
    
    public void login(){
        if(false){
            //マネージャーメニューの表示
        }else{
            mainMenuBoundary.loginFailure();
        }
    }
    
    public static void main(String[] args) {
        new SystemControl().start();
    }
}
