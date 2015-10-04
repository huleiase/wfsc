package com.exttool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportTool {

	private Connection conn;

	private Statement st;

	public static void main(String[] args) {
		ImportTool tool = new ImportTool(); 
		String sql = "select * from c_admin";
		List<Map<String, String>> query = tool.query(sql);
 
		System.out.print(query.size());
	}

	/* 插入数据记录，并输出插入的数据记录数 */
	public int execute(String sql) {
		System.out.println("sql==" + sql); // 输出插入操作的处理结果
		conn = getConnection(); // 首先要获取连接，即连接到数据库
		try {
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			int count = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
			conn.close(); // 关闭数据库连接
			return count;
		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
		}
		return 0;
	}

	/**
	 * 获取查询的结果集，list返回，每条记录封装到一个map里
	 */
	public List<Map<String, String>> query(String sql) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		System.out.println("query sql==" + sql); // 输出插入操作的处理结果
		conn = getConnection(); // 同样先要获取连接，即连接到数据库
		try {
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) { // 判断是否还有下一个数据
				Map<String, String> map = new HashMap<String, String>();
				for (int i=1; i<= columnCount; i++) {
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
				list.add(map);
			}
			System.out.println("查询结果数量："+list.size());
			conn.close(); // 关闭数据库连接
		} catch (SQLException e) {
			System.out.println("查询数据失败");
		}
		return list;
	}


	/* 获取数据库连接的函数 */
	public Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
			con = DriverManager.getConnection("jdbc:mysql://localhost:12581/cupid", "root", "windows#2010");// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}
}
