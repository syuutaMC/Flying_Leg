/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

import java.io.CharArrayReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import managerMenu.employees.EmployeeType;
import sys.DBManager;

/**
 *
 * @author tomo1
 */
public class EmployeeDAO {
    private static Connection con;
    private static PreparedStatement ps;
    
    /**
     * コンストラクタ
     * データベース接続情報設定
     */
    public EmployeeDAO() {
        DBManager dBManager = DBManager.getDBManager();
        con = dBManager.getConnection();
    }
    
    public List<Employee> selectEmployeeExecute() {
        List<Employee> employeeList = new ArrayList<>();
        try {
            employeeList.clear();
            ResultSet rs = ps.executeQuery();
            //結果の格納
            while (rs.next()) {                
                Employee employee = new Employee();
                setEmployee(employee, rs);
                employeeList.add(employee);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return employeeList;
    }
    
    public List<EmployeeType> selectEmployeeTypeExecute(){
        List<EmployeeType> employeeTypeList = new ArrayList<>();
        try{
            employeeTypeList.clear();
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                EmployeeType employeeType = new EmployeeType();
                setEmployeeType(employeeType, rs);
                employeeTypeList.add(employeeType);
            }
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return employeeTypeList;
    }
    
    /**
     * 問い合わせ結果をemployeeに格納
     * @param employee 社員情報
     * @param rs       問い合わせ結果
     */
    public void setEmployee(Employee employee, ResultSet rs) {
        try {
            String employeeNumber = rs.getString("EMPLOYEE_NUMBER");
            String employeeName   = rs.getString("EMPLOYEE_NAME");
            String employeeType   = rs.getString("TYPE_NUMBER");
            employee.setEmployeeNumber(employeeNumber);
            employee.setEmployeeName(employeeName);
            employee.setEmployeeType(employeeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 問い合わせ結果をemployeeTypeに格納
     * @param employeeType 従業員タイプ
     * @param rs           問い合わせ結果
     */
    public void setEmployeeType(EmployeeType employeeType, ResultSet rs){
        try{
            String typeNumber = rs.getString("TYPE_NUMBER");
            String typeName = rs.getString("TYPE_NAME");
            employeeType.setCategoryNumber(typeNumber);
            employeeType.setCategoryName(typeName);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 従業員番号をもとに従業員検索
     * @param employeeNumber 従業員番号
     * @return               従業員情報
     */
    public List<Employee> searchEmployeeNumber(String employeeNumber){
        List<Employee> employeeList = new ArrayList<>();
        
        String sql = "SELECT employee_number, employee_name, type_number " + 
                     " FROM employees " + 
                     " WHERE employee_number = ? ";
        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, employeeNumber);
            employeeList = selectEmployeeExecute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return employeeList;
    }
    
    /**
     * ログイン処理
     * @return ログイン成否
     */
    public List<Employee> dbLogin(String employeeNumber, String password) {
        List<Employee> employeeList = new ArrayList<>();
                
        String sql = "SELECT employee_number, employee_name, type_number " + 
                     " FROM employees " + 
                     " WHERE employee_number = ? AND " +
                     "       password = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, employeeNumber);
            ps.setString(2, password);
            employeeList = selectEmployeeExecute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    
    /**
     * 従業員登録処理
     * @param name 従業員名
     * @param empCategory 従業員役職
     * @param password パスワード
     * @return 登録できたか
     * @throws SQLException 
     */
    public int dbCreateEmployee(String name, String empCategory, char[] password) throws SQLException {
        String sql = "INSERT INTO EMPLOYEES(EMPLOYEE_NAME, TYPE_NUMBER, PASSWORD) " +
                     " VALUES( ?, ?, ?) ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, empCategory);
            ps.setString(3, new String(password));
            
            return ps.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 従業員更新処理
     * @param employee 従業員情報
     * @param password パスワード
     * @throws SQLException  
     */
    public int updateEmployee(Employee employee, char[] password) throws SQLException{
        String sql = "UPDATE employees" + 
                     " SET  employee_name = ?, " + 
                     "      type_number = ?, " +
                     "      password = ? " + 
                     " WHERE emplyee_number = ?";
        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, employee.getEmployeeName());
            ps.setString(2, employee.getEmployeeType());
            ps.setString(3, new String(password));
            ps.setString(4, employee.getEmployeeNumber());
            
            return ps.executeUpdate();
        }
        catch(SQLException e){
            throw e;
        }
    }
     
    public List<EmployeeType> getEmployeeCategory(){
        List<EmployeeType> employeeTypeList = new ArrayList<>();
        
        String sql = "SELECT * FROM employee_types";
        
        try{
            ps = con.prepareStatement(sql);
            employeeTypeList = selectEmployeeTypeExecute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return employeeTypeList;
    }
}
