package net.platform.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
    String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    String theUser = "fjqxj";
    String thePw = "fjqxj";
    Connection c = null;
    Statement conn;
    ResultSet rs = null;
    private static Connection con = null;
    public JdbcUtil() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            c = DriverManager.getConnection(dbUrl, theUser, thePw);
            conn = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        if (con == null) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "fjqxj",
                    "fjqxj");
        }
        return con;
    }
    
    public static void getMenuButton(){
        try {
            String sql = "select * from t_mdcp_data_directory t";
            con = getConnection();
            Statement st = null;
            ResultSet map = null;
            st = con.createStatement();
            map = st.executeQuery(sql);
            while (map.next()) {
                String id = map.getString("DATA_DIRECTORY_ID");
                String name = map.getString("DATA_DIRECTORY_NAME");
                String isd = map.getString("IS_DIRECTORY");
                String pid = map.getString("PARENT_ID");
                String dec = map.getString("DESCRIBE");
                String sn = map.getString("SN");
                String datac = map.getString("DATA_CODE");
                String status = map.getString("STATUS");
                String insert1 = "insert into t_mdcp_data_type values('"+id+"','"+pid+"','"+name+"','"+isd+"','1','','"+sn+"','"+status+"','"+datac+"');";
                System.out.println(insert1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //map = connect(classtype);
    }    
    public boolean executeUpdate(String sql) {
        try {
            conn.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String sql) {
        rs = null;
        try {
            rs = conn.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void close() {
        try {
            conn.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getMenuButton();
    }
}
