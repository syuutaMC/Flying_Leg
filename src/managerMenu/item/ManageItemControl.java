/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.item;

import sys.SystemControl;
/**
 *
 * @author syuuta
 */
public class ManageItemControl {
    private ManageItemBoundary manageItemBoundary;
    private SystemControl control;
    
    public ManageItemControl(){
        manageItemBoundary = new ManageItemBoundary();
    }
    
    public void setControl(SystemControl control){
        this.control = control;
    }
}
