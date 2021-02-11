/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.item;

import managerMenu.ManagerMenuControl;
/**
 *
 * @author syuuta
 */
public class ManageItemControl {
    private ManageItemBoundary manageItemBoundary;
    private ManagerMenuControl control;
    
    public ManageItemControl(){
        manageItemBoundary = new ManageItemBoundary();
    }
    
    /**
     * コントロールをセット
     * @param control ManagerMenuControl
     */
    public void setControl(ManagerMenuControl control){
        this.control = control;
    }
    
    /**
     * アイテム管理画面の画面切り替え処理
     * @param card カードレイアウト名
     */
    public void changeCardLayout(String card){
        manageItemBoundary.changeCardLayout(card);
    }
    
    /**
     * 商品管理画面スタート処理
     */
    public void start(){
        manageItemBoundary.setVisible(true);
        manageItemBoundary.setControl(this);
    }
}
