package net.test.tools;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLServerBackup {
	/**
	 * 获取数据库连接
	 * 
	 * @return Connection 对象
	 */
	public static void main(String argc[]){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		backup("D:\\work\\db","frm"+sdf.format(new Date()),"frm");
	}
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			String url = "jdbc:jtds:sqlserver://192.168.16.14:1433;databaseName=master";
			String username = "sa";
			String password = "admin";
			conn = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 备份数据库
	 * 
	 * @return backup
	 * @throws Exception
	 */
	public static String backup(String toPath, String filename,String dbname) {
		System.out.println("backup begin....."+(new Date()).toString());
		try {
			File file = new File(toPath);
			String path = file.getPath() + "\\" + filename + ".bak";
			String bakSQL = "backup database " + dbname
					+ " to disk=? with init";// SQL语句
			PreparedStatement bak = getConnection().prepareStatement(bakSQL);
			bak.setString(1, path);// path必须是绝对路径
			bak.execute(); // 备份数据库
			bak.close();
			System.out.println("backup end....."+(new Date()).toString());
		} catch (Exception e) {
			System.out.println("backup failed....."+(new Date()).toString());
			e.printStackTrace();
		}
		return "backup";
	}

	/**
	 * 数据库还原
	 * 
	 * @return recovery
	 */
	public static String recovery(String fromPath, String filename, String dbname) {
		try {
			File file = new File(fromPath);
			String path = file.getPath() + "\\" + filename + ".bak";// name文件名
			String recoverySql = "ALTER   DATABASE " + dbname
					+ " SET ONLINE   WITH   ROLLBACK   IMMEDIATE";

			PreparedStatement ps = getConnection()
					.prepareStatement(recoverySql);
			CallableStatement cs = getConnection().prepareCall(
					"{call killrestore(?,?)}");
			cs.setString(1, dbname); // 数据库名
			cs.setString(2, path); // 已备份数据库所在路径
			cs.execute(); // 还原数据库
			ps.execute(); // 恢复数据库连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "recovery";
	}
	
/*	create proc killrestore (@dbname varchar(20),@dbpath varchar(40))         
	as         
	begin         
	declare @sql   nvarchar(500)         
	declare @spid  int         
	set @sql='declare getspid cursor for select spid from sysprocesses where dbid=db_id('''+@dbname+''')'         
	exec (@sql)         
	open getspid         
	fetch next from getspid into @spid         
	while @@fetch_status <> -1         
	begin         
	exec('kill '+@spid)         
	fetch next from getspid into @spid         
	end         
	close getspid         
	deallocate getspid         
	restore database @dbname from disk= @dbpath with replace  
	end    */  	
}
