package org.hgc.object;

import java.sql.*;

import org.hgc.db.*;

/**
 * �γ�
 */
public class Course {
	private int id; // ѧ��
	private String name; // ����
	private int teacherId; // �ον�ʦ
	private int period; // ��ʱ
	private String selectProperty; // ѡ�����ԣ�����&ѡ�ޣ�
	private double credit; // ѧ��

	// ʹ�����Թ���
	public Course(int id, String name, int teacherId, int period, String selectProperty, double credit) {
		this.id = id;
		this.name = name;
		this.teacherId = teacherId;
		this.period = period;
		this.selectProperty = selectProperty;
		this.credit = credit;
	}

	// ʹ�ö�����
	public Course(Course course) {
		this.id = course.id;
		this.name = course.name;
		this.teacherId = course.teacherId;
		this.period = course.period;
		this.selectProperty = course.selectProperty;
		this.credit = course.credit;
	}

	// set&get

	// id
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	// name
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	// teacherId
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeacherId() {
		return this.teacherId;
	}

	// period
	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPeriod() {
		return this.period;
	}

	// selectProperty
	public void setSelectProperty(String selectProperty) {
		this.selectProperty = selectProperty;
	}

	public String getSelectProperty() {
		return this.selectProperty;
	}

	// credit
	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getCredit() {
		return this.credit;
	}

	public static String getHeader() {
		return "       " + "�γ̺�" + "\t" + "�γ���" + "\t" + "�ον�ʦ" + "\t" + "��ʱ" + "\t" + "ѡ������" + "\t" + "ѧ��\n";
	}

	public String toString() {

		String str = "       " + id + "\t" + name + "\t" + getTeacherName() + "\t" + period + "\t" + selectProperty
				+ "\t" + credit;
		return str;
	}

	// ��teacherId�ڽ�ʦ�����ҵ�teacherName
	public String getTeacherName() {
		Database db = Database.getDatabase();
		String sql = "select * from teacher where tno = " + this.teacherId;
		ResultSet rSet = db.executeQuery(sql);
		String str = null;
		try {
			while (rSet.next()) {
				str = rSet.getString("tName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
}
