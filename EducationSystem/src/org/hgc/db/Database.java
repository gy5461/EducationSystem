package org.hgc.db;

import java.sql.*;

public class Database {
	private static Database database;

	private Connection connect;// ���ݿ������
	private PreparedStatement pStatement;// ���������ݿⷢ�Ͷ��� PreperedStatement��Statement�ĺ���
	private ResultSet rSet; // ��װ��ִ�н��

	// ���캯��
	private Database() {

	}

	// ����Database�Ķ��󣬱�֤ȫ��ֻ��һ��
	public static Database getDatabase() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}

	// �������ݿ������
	private Connection getConnect() {
		try {
			if (connect == null || connect.isClosed()) { // connectδ��ʼ�����ѹر�
				Class.forName("com.mysql.cj.jdbc.Driver"); // ����MYSQL JDBC��������
				// System.out.println("Success loading Mysql Driver!");
				connect = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/SystemDb?serverTimezone=Asia/Shanghai", "root", "584034912");
				// ����URLΪjdbc:mysql//��������ַ/���ݿ��������2�������ֱ��ǵ�½�û���������
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

	// ���ݿ�Ĺر�
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

	// ִ��ĳ����ѯ��SQL��䣬���ز�ѯ���
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
		 * �÷����� while (rs.next()) {
		 * 
		 * int id = rs.getInt("sno"); String name = rs.getString("sName"); // String age
		 * = rs.getString("stuAge"); System.out.println("ѧ��:" + id + "\t" + "����:" + name
		 * + "\n" ); }
		 */
	}

	// ִ���д��ݲ�����SQL��ѯ��䣬���ز�ѯ���
	public ResultSet executeQuery(String sql, Object[] object) {// Object[] object ָ����Ĳ���
		if (getConnect() == null) {
			return null;
		}
		try {
			pStatement = connect.prepareStatement(sql);
			for (int i = 0; i < object.length; i++) {
				pStatement.setObject(i + 1, object[i]);// ����������SQL��䵱��
			}
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rSet;
		/*
		 * ������� String sql = "select * from users where name=? and password=?"; st =
		 * conn.preparedStatement(sql); st.setString(1, username); st.setString(2,
		 * password); st.executeQuery();
		 * 
		 */
	}

	// ִ��ĳ�����²�����SQL��䣬���ظ��µ�����
	public int executeUpdate(String sql) {// ����ʹ��INSERT��UPDATE �� DELETE ���
		int result = -1;
		if (getConnect() == null) {
			return result; // ��ͨ����ֵ�ж��Ƿ������ݸ���
		}
		try {
			pStatement = connect.prepareStatement(sql);
			result = pStatement.executeUpdate(); // ����Ӱ�����������result

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// ִ��ĳ�����²����в������ݵ�SQL��䣬�����ظ��µ�����
	public int executeUpdate(String sql, Object[] obj) {
		int result = -1;
		if (getConnect() == null) {
			return result;
		}
		try {
			pStatement = connect.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pStatement.setObject(i + 1, obj[i]);// i+1,�ڼ���������obj[i],������ֵ
			}
			result = pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
