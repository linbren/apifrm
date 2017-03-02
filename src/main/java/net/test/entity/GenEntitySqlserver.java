package net.test.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class GenEntitySqlserver {
    
    private String packageOutPath = "net.business.test.entity";//指定实体生成所在包的路径
    private String authorName = "yiting lin";//作者名字
    private String tablename = "TS_FUNCTION";//表名
    private String[] stabletemp; 
    String stable="";
    private String[] colnames; // 列名数组
    private String[] colTypes; //列名类型数组
    private int[] colSizes; //列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
    private String sDatetime =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    
    //数据库连接
     private static final String URL ="jdbc:jtds:sqlserver://192.168.16.14:1433;DatabaseName=frm";
     private static final String NAME = "sa";
     private static final String PASS = "admin";
     private static final String DRIVER ="net.sourceforge.jtds.jdbc.Driver";

    /*
     * 构造函数
     */
    public GenEntitySqlserver(){
        //创建连接
        Connection con;
        //查要生成实体类的表
        String sql = "select * from " + tablename;
        Statement pStemt = null;

        
        stabletemp=tablename.toLowerCase().split("_");
        stable="";
            for(String sc:stabletemp){
                stable+=initcap(sc);   
            }
            
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL,NAME,PASS);
            pStemt = (Statement) con.createStatement();
            ResultSet rs = pStemt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int size = rsmd.getColumnCount();   //统计列
            colnames = new String[size]; //fieldnames
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                
                if(colTypes[i].equalsIgnoreCase("date") || colTypes[i].equalsIgnoreCase("timestamp")){
                    f_util = true;
                }
                if(colTypes[i].equalsIgnoreCase("blob") || colTypes[i].equalsIgnoreCase("char")){
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            
            String content = parse(colnames,colTypes,colSizes);
            
            try {
                File directory = new File("");
                //System.out.println("绝对路径："+directory.getAbsolutePath());
                //System.out.println("相对路径："+directory.getCanonicalPath());
                String path=this.getClass().getResource("").getPath();
                System.out.println(path);
                System.out.println("src/?/"+path.substring(path.lastIndexOf("/net/", path.length())) );
//              String outputPath = directory.getAbsolutePath()+ "/src/"+path.substring(path.lastIndexOf("/com/", path.length()), path.length()) + initcap(tablename) + ".java";
                String outputPath = directory.getAbsolutePath()+ "/src/main/java/"+this.packageOutPath.replace(".", "/")+"/"+stable + ".java";
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
//          try {
//              con.close();
//          } catch (SQLException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//          }
        }
    }

    /**
     * 功能：生成实体类主体代码
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n");
        sb.append("\r\n");        
        
        sb.append("import javax.persistence.Column;\r\n");                  
        sb.append("import javax.persistence.Entity;\r\n");                  
        sb.append("import javax.persistence.GeneratedValue;\r\n");          
        sb.append("import javax.persistence.Id;\r\n");                      
        sb.append("import javax.persistence.Table;\r\n"); 
        sb.append("import org.hibernate.annotations.Cascade;\r\n");        
        sb.append("import org.hibernate.annotations.CascadeType;\r\n");
        sb.append("import org.hibernate.annotations.GenericGenerator;\r\n");

        //判断是否导入工具包
        if(f_util){
            sb.append("import java.util.Date;\r\n");
        }
        if(f_sql){
            sb.append("import java.sql.*;\r\n");
        }
        //注释部分
        sb.append("/**\r\n");
        sb.append("* "+tablename+" 实体类\r\n");
        sb.append("* @author "+this.authorName+"\r\n");            
        sb.append("* created "+sDatetime+"\r\n");
        sb.append("*/ \r\n");
        
        sb.append("@SuppressWarnings(\"serial\")\r\n");
        sb.append("@Entity\r\n");
        sb.append("@Table(name = \""+ tablename + "\", schema = \"\")\r\n");        
        
        
        //实体部分
        sb.append("public class " + stable + " implements java.io.Serializable {\r\n");
        processAllAttrs(sb);//属性
        processAllMethod(sb);//get set方法
        sb.append("}\r\n");
        
        //System.out.println(sb.toString());
        return sb.toString();
    }
    
    /**
     * 功能：生成所有属性
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {
        String[] stemp; 
        String sCol="";
        for (int i = 0; i < colnames.length; i++) {
          sb.append("    /** "+colnames[i]+"*/ \r\n");
            
//            sb.append("    /**\r\n");
//            sb.append("    * "+colnames[i]+"\r\n");
//            sb.append("    */ \r\n");        
//          @Id
//      	@GeneratedValue(strategy=GenerationType.IDENTITY)            
//          @GeneratedValue(generator = "paymentableGenerator")
//          @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")


        
            sb.append("    @Column(name = \""+colnames[i]+"\")\r\n");
            
            stemp=colnames[i].toLowerCase().split("_");
            sCol="";
            for(String sc:stemp){
                sCol+=initcap(sc);   
            }
            sb.append("    private " + sqlType2JavaType(colTypes[i]) + " " + initlow(sCol) + ";\r\n");
        }
        
    }

    /**
     * 功能：生成所有方法
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        String[] stemp; 
        String sCol="";       
        //变更名首字母小写，方法名首字母大写
        for (int i = 0; i < colnames.length; i++) {
            stemp=colnames[i].toLowerCase().split("_");
            sCol="";
            for(String sc:stemp){
                sCol+=initcap(sc);   
            }
            sb.append("    /**\r\n");
            sb.append("    * set"+sCol+"\r\n");
            sb.append("    * @author "+this.authorName+"\r\n");            
            sb.append("    * @created "+sDatetime+"\r\n");
            sb.append("    * @param "+colnames[i]+"\r\n");            
            sb.append("    */ \r\n");            
           // sb.append("    public void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " +
            sb.append("    public void set" + sCol + "(" + sqlType2JavaType(colTypes[i]) + " " + 
                    initlow(sCol) + "){\r\n");
            sb.append("        this." + initlow(sCol) + "=" + initlow(sCol) + ";\r\n");  //indent 4
            sb.append("    }\r\n");
            
            sb.append("    /**\r\n");
            sb.append("    * get"+sCol+"\r\n");
            sb.append("    * @author "+this.authorName+"\r\n");            
            sb.append("    * @created "+sDatetime+"\r\n");
            sb.append("    * @return "+sqlType2JavaType(colTypes[i])+"\r\n");            
            sb.append("    */ \r\n");              
            sb.append("    public " + sqlType2JavaType(colTypes[i]) + " get" + sCol + "(){\r\n");
            sb.append("        return " + initlow(sCol) + ";\r\n"); //indent 4
            sb.append("    }\r\n");
        }
        
    }
    
    /**
     * 功能：将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    private String initcap(String str) {
        
        char[] ch = str.toCharArray();
        if(ch[0] >= 'a' && ch[0] <= 'z'){
            ch[0] = (char)(ch[0] - 32);
        }
        
        return new String(ch);
    }
    /**
     * 功能：将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    private String initlow(String str) {
        
        char[] ch = str.toCharArray();
        if(ch[0] >= 'A' && ch[0] <= 'Z'){
            ch[0] = (char)(ch[0] + 32);
        }
        
        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {
        
        if(sqlType.equalsIgnoreCase("binary_double")){
            return "double";
        }else if(sqlType.equalsIgnoreCase("binary_float")){
            return "float";
        }else if(sqlType.equalsIgnoreCase("blob")){
            return "byte[]";
        }else if(sqlType.equalsIgnoreCase("blob")){
            return "byte[]";
        }else if(sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar2")
        		|| sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("varchar2")
        		|| sqlType.equalsIgnoreCase("numeric identity")){
            return "String";
        }else if(sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("timestamp")
                 || sqlType.equalsIgnoreCase("timestamp with local time zone") 
                 || sqlType.equalsIgnoreCase("timestamp with time zone")){
            return "Date";
        }else if(sqlType.equalsIgnoreCase("number") || sqlType.equalsIgnoreCase("numeric")){
            return "Long";
        }else if(sqlType.equalsIgnoreCase("int")){
            return "Integer";
        }
        else     
            return sqlType;
    }
    
    /**
     * 出口
     * TODO
     * @param args
     */
    public static void main(String[] args) {
        
        new GenEntitySqlserver();
        
    }

}