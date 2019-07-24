package org.hgc.db;

import java.sql.*;

public class Database {
	private static Database database;

	private Connection connect;// 数据库的链接
	private PreparedStatement pStatement;// 用于向数据库发送对象， PreperedStatement是Statement的孩子
	private ResultSet rSet; // 封装的执行结果

	// 构造函数
	private Database() {

	}

	// 返回Database的对象，保证全局只用一个
	public static Database getDatabase() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}

	// 建立数据库的连接
	private Connection getConnect() {
		try {
			if (connect == null || connect.isClosed()) { // connect未初始化或已关闭
				Class.forName("com.mysql.cj.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
				// System.out.println("Success loading Mysql Driver!");
				connect = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/SystemDb?serverTimezone=Asia/Shanghai", "root", "584034912");
				// 连接URL为jdbc:mysql//服务器地址/数据库名后面的2个参数分别是登陆用户名和密码
				// System.out.println("Success connect Mysql server!");
			}
		} catch (ClassNotFoundException e) {
			// System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connect;
	}

	// 数据库的关闭
	public void close() {
		try {
			if (rSet != null) {
				rSet.close();
			}
			if (pStatement != null) {
				pStatement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 执行某条查询的SQL语句，返回查询结果
	public ResultSet executeQuery(String sql) {
		if (getConnect() == null) {
			return null;
		}
		try {
			pStatement = connect.prepareStatement(sql);
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rSet;
		/*
		 * 用法介绍 while (rs.next()) {
		 * 
		 * int id = rs.getInt("sno"); String name = rs.getString("sName"); // String age
		 * = rs.getString("stuAge"); System.out.println("学号:" + id + "\t" + "姓名:" + name
		 * + "\n" ); }
		 */
	}

	// 执行有传递参数的SQL查询语句，返回查询结果
	public ResultSet executeQuery(String sql, Object[] object) {// Object[] object 指传入的参数
		if (getConnect() == null) {
			return null;
		}
		try {
			pStatement = connect.prepareStatement(sql);
			for (int i = 0; i < object.length; i++) {
				pStatement.setObject(i + 1, object[i]);// 将参数放入SQL语句当中
			}
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rSet;
		/*
		 * 便于理解 String sql = "select * from users where name=? and password=?"; st =
		 * conn.preparedStatement(sql); st.setString(1, username); st.setString(2,
		 * password); st.executeQuery();
		 * 
		 */
	}

	// 执行某条更新操作的SQL语句，返回更新的条数
	public int executeUpdate(String sql) {// 可以使用INSERT、UPDATE 或 DELETE 语句
		int result = -1;
		if (getConnect() == null) {
			return result; // 可通过该值判断是否有数据更新
		}
		try {
			pStatement = connect.prepareStatement(sql);
			result = pStatement.executeUpdate(); // 将受影响的行数赋给result

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// 执行某条更新操作有参数传递的SQL语句，并返回更新的条数
	public int executeUpdate(String sql, Object[] obj) {
		int result = -1;
		if (getConnect() == null) {
			return result;
		}
		try {
			pStatement = connect.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pStatement.setObject(i + 1, obj[i]);// i+1,第几个参数，obj[i],参数的值
			}
			result = pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
