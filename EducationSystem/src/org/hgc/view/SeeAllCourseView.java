package org.hgc.view;

//显示所有课程界面

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.object.Course;
import org.hgc.object.Student;

import java.sql.*;

public class SeeAllCourseView extends JFrame {

	private JPanel contentPane;
	private JLabel labelAllCourse;
	JTextArea txtCourseInfo;

	/**
	 * Create the frame.
	 */
	public SeeAllCourseView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “所有课程：”标签
		labelAllCourse = new JLabel("\u6240\u6709\u8BFE\u7A0B\uFF1A");
		labelAllCourse.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		labelAllCourse.setBounds(112, 59, 125, 34);
		contentPane.add(labelAllCourse);

		// 所有课程信息显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(112, 124, 592, 361);
		txtCourseInfo = new JTextArea();
		txtCourseInfo.setBounds(112, 124, 592, 361);
		txtCourseInfo.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		contentPane.add(scroll);
		scroll.setViewportView(txtCourseInfo);
		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 显示所有选课成绩信息
		showGradeInfo();
	}

	// 重载函数，按学号显示课程信息
	public SeeAllCourseView(int Sno) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “所有课程：”标签
		labelAllCourse = new JLabel("\u6240\u6709\u8BFE\u7A0B\uFF1A");
		labelAllCourse.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		labelAllCourse.setBounds(112, 59, 125, 34);
		contentPane.add(labelAllCourse);

		// 所有课程信息显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(112, 124, 592, 361);
		txtCourseInfo = new JTextArea();
		txtCourseInfo.setBounds(112, 124, 592, 361);
		txtCourseInfo.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		contentPane.add(scroll);
		scroll.setViewportView(txtCourseInfo);
		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 显示学号为Sno的学生的选课成绩信息
		showGradeInfoBySno(Sno);
	}

	// 显示所有课程信息
	public void showGradeInfo() {
		Database db = Database.getDatabase();
		String sql = "select distinct * from sc,course,student where sc.cno = course.cno and sc.sno = student.sno;";
		ResultSet rSet = db.executeQuery(sql);
		try {
			String str = "        课程号\t课程名\t学生号\t学生名\t成绩\n";
			while (rSet.next()) {
				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				Student student = new Student(rSet.getInt("sno"), rSet.getString("sName"), rSet.getString("sSex"),
						rSet.getString("sNation"), rSet.getString("sCountry"), rSet.getString("sMajor"),
						rSet.getInt("sStartYear"));
				str += "        " + course.getId() + "\t" + course.getName() + "\t" + student.getId() + "\t"
						+ student.getName() + "\t" + rSet.getInt("grade") + "\n";
			}
			txtCourseInfo.setText(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 显示学号为Sno的学生选的所有课的信息
	public void showGradeInfoBySno(int Sno) {
		Database db = Database.getDatabase();
		String sql = "select distinct * from sc,course,student where sc.cno = course.cno and sc.sno = student.sno and sc.sno = "
				+ Sno;
		ResultSet rSet = db.executeQuery(sql);
		try {
			String str = "        课程号\t课程名\t学生号\t学生名\t成绩\n";
			while (rSet.next()) {
				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				Student student = new Student(rSet.getInt("sno"), rSet.getString("sName"), rSet.getString("sSex"),
						rSet.getString("sNation"), rSet.getString("sCountry"), rSet.getString("sMajor"),
						rSet.getInt("sStartYear"));
				str += "        " + course.getId() + "\t" + course.getName() + "\t" + student.getId() + "\t"
						+ student.getName() + "\t" + rSet.getInt("grade") + "\n";
			}
			txtCourseInfo.setText(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
