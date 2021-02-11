/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.util.Date;

/**
 * 週・曜日ごとの売り上げ
 * @author 19jz0137
 */
public class SalesAtWeek {
    private Date SalesDate;
    private int  sun;
    private int  mon;
    private int  yue;
    private int  wed;
    private int  thu;
    private int  fry;

    public SalesAtWeek() {
    }

    public Date getSalesDate() {
        return SalesDate;
    }

    public void setSalesDate(Date SalesDate) {
        this.SalesDate = SalesDate;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getYue() {
        return yue;
    }

    public void setYue(int yue) {
        this.yue = yue;
    }

    public int getWed() {
        return wed;
    }

    public void setWed(int wed) {
        this.wed = wed;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public int getFry() {
        return fry;
    }

    public void setFry(int fry) {
        this.fry = fry;
    }
 
}
