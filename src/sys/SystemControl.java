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
    
    /**
     * メインメニューの画面切り替え処理
     * @param str CardName
     */
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
    
    /**
     * ログイン処理
     */
    public void login(){
        if(true){   //ログイン成功
            mainMenuBoundary.setEmployee("number", "Name", "position");
            mainMenuBoundary.setLoginStatus(true);
            mainMenuBoundary.login("");
        }else{  //ログイン失敗
            mainMenuBoundary.loginFailure();
        }
    }
    
    /**
     * ログアウト処理
     */
    public void logout(){
        mainMenuBoundary.setEmployee("", "", "");
        mainMenuBoundary.setLoginStatus(false);
        mainMenuBoundary.logout();
    }
    
    public static void main(String[] args) {
        new SystemControl().start();
    }
}
