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
    private Date salesDate;
    private int  storeNumber;
    private int  weekNumber;
    private int  sun;
    private int  mon;
    private int  yue;
    private int  wed;
    private int  thu;
    private int  fry;
    private int  sat;

    public SalesAtWeek() {
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }
    
    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int SalesDate) {
        this.weekNumber = SalesDate;
    }

    public String getWeekName() {
        String[] week =  { "日", "月", "火", "水", "木", "金", "土"};
        if (7 >= getWeekNumber() && getWeekNumber() >= 1) {
            return week[getWeekNumber() - 1];
        }
        return "?";
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

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }
 
}
