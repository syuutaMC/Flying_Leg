/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

/**
 * 社員情報クラス
 * @author tomo1
 */
public class Employee {
    private String employeeNumber;
    private String employeeName;
    private String employeeType;
    private String password;

    public Employee(String employeeNumber, String employeeName, String employeeType) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeType = employeeType;
    }

    public Employee() {
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public String getPassword() {
        return password;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
