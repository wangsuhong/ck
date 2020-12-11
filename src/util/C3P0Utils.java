package util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
	//定义一个数据源
	private static ComboPooledDataSource ds = new ComboPooledDataSource("db");
	
	//获取数据源方法
	public static DataSource getDataSource() {
		return ds;
	}
	
	//获取连接方法
	public static Connection getConnection() throws SQLException{
		Connection connection = ds.getConnection();
		return connection;
	}
}
