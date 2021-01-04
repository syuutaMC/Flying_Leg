/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import employeesMenu.customer.Customer;
import employeesMenu.customer.CustomerDAO;
import java.util.List;

/**
 *
 * @author 19jz0115
 */
public class OrderControl {
    private final OrderBoundary orderBoundary;
    private final OrderDAO      orderDAO;
    private final CustomerDAO   customerDAO;
    
    public OrderControl() {
        orderBoundary   = new OrderBoundary();
        orderDAO        = new OrderDAO();
        customerDAO     = new CustomerDAO();
    }
    
    public void start() {
        orderBoundary.setControl(this);
        orderBoundary.setVisible(true);
    }
    
    /**
     * 顧客を検索する
     * @param phoneNumber 電話番号
     * @return            顧客情報 見つからなければnull
     */
    public Customer searchCustomer(String phoneNumber) {
        List<Customer> customer = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
        if (customer.size() > 0) {
            return customer.get(0);
        }
        else {
            return null;
        }
    }
}
