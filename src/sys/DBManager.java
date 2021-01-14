package sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * シングルトーンパターンを適応した
 * データーベース接続管理クラス
 * @author 19jz0115
 */
public class DBManager {
    private static DBManager dbManager = null;
    private static Connection con = null;
    private static final String driverurl = "jdbc:oracle:thin:@//10.40.112.11:1521/dbsys";
    private static final String dbUserName = "G1909";
    private static final String dbUserPassword = "pass";
    
    /**
     * コンストラクタ
     * ドライバマネージャーにJDBCドライバを登録し、URLで指定された場所に存在するデーターベースに接続する
     * アクセス修飾子がprivateなので、内部メソッドからしか生成することができない
     */
    private DBManager(){
        try{
            con = DriverManager.getConnection(driverurl, dbUserName, dbUserPassword);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * DB接続管理インスタンスの生成を兼ねたゲッター
     * @return DB接続管理インスタンス
     */
    public static DBManager getDBManager(){
        if(DBManager.dbManager == null){    //接続情報に誤りがあり、接続できなかった場合のcatch
            DBManager.dbManager = new DBManager();
        }
        
        return DBManager.dbManager;
    }
    /**
     * コネクションのゲッター
     * @return コネクション
     */
    public Connection getConnection(){
        return con;
    }
    /**
     * テスト用メイン
     * @param args 
     */
    public static void main(String[] args) {
        DBManager dbManager = DBManager.getDBManager();
        con = dbManager.getConnection();
        System.out.println("Finish!!");
    }
}
