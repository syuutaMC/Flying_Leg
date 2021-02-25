/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.employees;

import java.sql.SQLException;
import java.util.List;
import managerMenu.Employee;
import managerMenu.EmployeeDAO;
import managerMenu.ManagerMenuControl;


/**
 * 従業員管理コントロール
 * @author 19jz0115
 */
public class EmployeeControl {
    private ManagerMenuControl control;
    private final EmployeeBoundary employeeBoundary;
    private final EmployeeDAO employeeDAO;
    
    public EmployeeControl() {
        employeeBoundary = new EmployeeBoundary();
        employeeDAO = new EmployeeDAO();
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
        List<Employee> emp = employeeDAO.searchEmployeeNumber(employeeNumber);
        
        if(emp.size() > 0){
            employeeBoundary.setEmployee(emp.get(0));
            employeeBoundary.showEmployee();
        }else{
            employeeBoundary.showMessageDialog("従業員番号" + employeeNumber + "が見つかりません" );
        }
    }
    
    /**
     * 従業員登録処理
     * @param employeeName 従業員名
     * @param empCategory 役職
     * @param password パスワード
     */
    public void registarEmployee(String employeeName, String empCategory, char[] password){
        try{
            int id = employeeDAO.dbCreateEmployee(employeeName, empCategory, password);
            if(id != -1){
                employeeBoundary.showMessageDialog("登録されました！\n従業員番号は" + id +"番です");
                employeeBoundary.initAddEmployeePanel();
            }else{
                employeeBoundary.showErrorMessage("登録できませんでした", "エラー");
            }
        }
        catch(SQLException e){
           e.printStackTrace();
        }
    }
    
    /**
     * 従業員更新処理
     * @param emp 従業員情報
     * @param password パスワード
     */
    public void updateEmployee(Employee emp, char[] password){
        try{
            int cnt = 0;
            if(new String(password).equals("")){
                cnt = employeeDAO.updateEmployee(emp);
            }else{
                cnt = employeeDAO.updateEmployeePass(emp, password);
            }
            if(cnt > 0){
                employeeBoundary.showMessageDialog("更新しました");
            }
            else{
                employeeBoundary.showErrorMessage("更新できませんでした", "エラー");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * 従業員番号をもとに削除処理
     * @param employeeNumber 従業員番号
     * @throws SQLException 
     */
    public void deleteEmployee(String employeeNumber){
        try{
            int cnt = employeeDAO.deleteEmployee(employeeNumber);
            if(cnt > 0){
                employeeBoundary.showMessageDialog("削除しました");
            }else{
                employeeBoundary.showErrorMessage("削除できませんでした", "エラー");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * 従業員カテゴリを取得
     * @return カテゴリリスト
     */
    public List<EmployeeType> getEmployeeType(){
        return employeeDAO.getEmployeeCategory();
    }
    
    /**
     * 従業員管理メニュー表示処理
     */
    public void start(){
        employeeBoundary.setVisible(true);
        employeeBoundary.setControl(this);
    }
    
    public void exit(){
        employeeBoundary.setVisible(false);
        control.exitMediaView();
    }
}
