package org.hgc.object;

import java.sql.*;

import org.hgc.db.*;

/**
 * 课程
 */
public class Course {
	private int id; // 学号
	private String name; // 姓名
	private int teacherId; // 任课教师
	private int period; // 课时
	private String selectProperty; // 选课属性（必修&选修）
	private double credit; // 学分

	// 使用属性构造
	public Course(int id, String name, int teacherId, int period, String selectProperty, double credit) {
		this.id = id;
		this.name = name;
		this.teacherId = teacherId;
		this.period = period;
		this.selectProperty = selectProperty;
		this.credit = credit;
	}

	// 使用对象构造
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
		return "       " + "课程号" + "\t" + "课程名" + "\t" + "任课教师" + "\t" + "课时" + "\t" + "选课属性" + "\t" + "学分\n";
	}

	public String toString() {

		String str = "       " + id + "\t" + name + "\t" + getTeacherName() + "\t" + period + "\t" + selectProperty
				+ "\t" + credit;
		return str;
	}

	// 用teacherId在教师表中找到teacherName
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
