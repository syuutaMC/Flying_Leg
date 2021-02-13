package sys;

import employeesMenu.EmployeesControl;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import managerMenu.ManagerMenuControl;
import managerMenu.item.ManageItemControl;
import java.util.List;
import javax.swing.JFrame;
import managerMenu.Employee;
import managerMenu.EmployeeDAO;

/**
 * メインメニューコントローラー
 * @author 19jz0115
 */
public class SystemControl {
    
    private final MainMenuBoundary mainMenuBoundary;
    private final EmployeesControl employeesControl;
    private final ManagerMenuControl managerMenuControl;
    private final ManageItemControl manageItemControl;
    private final EmployeeDAO employeeDAO;
    public boolean LoginStatus = false;
    public Employee emp;
    
    public SystemControl(){
        mainMenuBoundary = new MainMenuBoundary();
        employeesControl = new EmployeesControl();
        managerMenuControl = new ManagerMenuControl();
        manageItemControl = new ManageItemControl();
        employeeDAO = new EmployeeDAO();
        emp = new Employee();
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
     * 売上管理画面を開く
     */
    public void showPaymentboundary(){
        managerMenuControl.setControl(this);
        managerMenuControl.showPaymentBoundary();
    }
    
    /**
     * 商品管理画面の表示
     */
    public void showIManageItemBoundary(){
        managerMenuControl.setControl(this);
        managerMenuControl.showManageitemBoundary();
    }
    
    /**
     * 従業員管理画面の表示
     */
    public void showEmployeeBoundary(){
        managerMenuControl.setControl(this);
        managerMenuControl.showManagerboundary();
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
    public void login(String EmployeeNumber, char[] Password){
        
        List<Employee> empList = employeeDAO.dbLogin(EmployeeNumber, new String(Password));
        
        if(empList.size() > 0){   //ログイン成功
            emp = empList.get(0);
            mainMenuBoundary.login(emp.getEmployeeType());
            setLoginStatus(true);
        }else{  //ログイン失敗
            mainMenuBoundary.loginFailure();
        }
    }
    
    /**
     * ログイン状況の設定
     * @param b 
     */
    public void setLoginStatus(boolean b){
        LoginStatus = b;
    }
    
    /**
     * ログアウト処理
     */
    public void logout(){
        emp.setEmployee("", "", "");
        setLoginStatus(false);
        mainMenuBoundary.logout();
    }
    
    public static void main(String[] args) {
        new SystemControl().start();
    }
}
