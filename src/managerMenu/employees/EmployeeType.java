/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.employees;

/**
 *
 * @author 19jz0115
 */
public class EmployeeType {
    private String categoryName;
    private String categoryNumber;

    public EmployeeType(String categoryName, String categoryNumber) {
        this.categoryName = categoryName;
        this.categoryNumber = categoryNumber;
    }
    
    public EmployeeType(){
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }
    
    public void setEmployeeCategory(String categoryName, String categoryNumber){
        setCategoryName(categoryName);
        setCategoryNumber(categoryNumber);
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }
    
    
}
