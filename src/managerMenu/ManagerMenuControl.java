/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

import managerMenu.payment.PaymentControl;
import sys.SystemControl;

/**
 *
 * @author 19jz0115
 */
public class ManagerMenuControl {
    private SystemControl control;
    private PaymentControl paymentControl;
    
    public ManagerMenuControl(){
        paymentControl = new PaymentControl();
    }
    
    public void setControl(SystemControl control){
        this.control = control;
    }
}
