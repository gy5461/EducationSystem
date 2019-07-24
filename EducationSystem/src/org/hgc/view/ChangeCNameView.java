package org.hgc.view;

//教师修改指定课程名称界面

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.log.SystemLog;
import org.hgc.object.Course;

import java.sql.*;

public class ChangeCNameView extends JFrame {

	private JPanel contentPane;
	private JTextField txtCourseName;
	private JTextArea txtChangeLog;
	private JButton btnEnsureSubmit;
	private JTextArea txtSrcCourseInfo;
	private JLabel labelChangeSitu;
	private JLabel labelThisInfo;
	private JLabel labelChangeName;

	private String logContent;
	private String tName;
	private Course srcCourse;
	private Course desCourse;
	boolean flag = false; // 标记是否修改信息

	/**
	 * Create the frame. 参数：课程号
	 */
	public ChangeCNameView(int Cno, String tName) {
		this.tName = tName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “本课程信息：”标签
		labelThisInfo = new JLabel("\u672C\u8BFE\u7A0B\u4FE1\u606F\uFF1A");
		labelThisInfo.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelThisInfo.setBounds(70, 106, 139, 21);
		contentPane.add(labelThisInfo);

		// 本课程信息显示处
		txtSrcCourseInfo = new JTextArea();
		txtSrcCourseInfo.setBounds(70, 158, 658, 65);
		txtSrcCourseInfo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		contentPane.add(txtSrcCourseInfo);

		// “修改课程名称为”标签
		labelChangeName = new JLabel("\u4FEE\u6539\u8BFE\u7A0B\u540D\u79F0\u4E3A");
		labelChangeName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChangeName.setBounds(70, 272, 139, 21);
		contentPane.add(labelChangeName);

		// 修改课程名称输入框
		txtCourseName = new JTextField();
		txtCourseName.setBounds(203, 269, 187, 27);
		contentPane.add(txtCourseName);
		txtCourseName.setColumns(10);

		// “修改情况：”标签
		labelChangeSitu = new JLabel("\u4FEE\u6539\u60C5\u51B5\uFF1A");
		labelChangeSitu.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChangeSitu.setBounds(70, 349, 95, 21);
		contentPane.add(labelChangeSitu);

		// 修改后课程情况显示处
		txtChangeLog = new JTextArea();
		txtChangeLog.setBounds(70, 404, 658, 65);
		txtChangeLog.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		contentPane.add(txtChangeLog);

		// 确认提交按钮
		btnEnsureSubmit = new JButton("\u786E\u8BA4\u63D0\u4EA4");
		btnEnsureSubmit.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnEnsureSubmit.setBounds(435, 268, 123, 29);
		contentPane.add(btnEnsureSubmit);
		btnEnsureSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 修改课程名称
				setCourseName(Cno);
				// 显示修改后的信息
				showCourseInfo(Cno, txtSrcCourseInfo);
				// 添加系统修改日志
				showSysLog(txtChangeLog, logContent);
				// 显示修改后的信息
				showCourseInfo(Cno, txtSrcCourseInfo);
			}

		});
		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 显示课程信息
		showCourseInfo(Cno, txtSrcCourseInfo);
		logContent = "";
	}

	// 根据课程号显示课程信息
	public void showCourseInfo(int Cno, JTextArea txtCourseInfo) {
		Database db = Database.getDatabase();
		String sql = "select * from course where cno = " + Cno;
		ResultSet rSet = db.executeQuery(sql);
		try {
			while (rSet.next()) {
				String str = "       " + "课程号" + "\t" + "课程名" + "\t" + "任课教师" + "\t" + "课时" + "\t" + "选课属性" + "\t"
						+ "学分\n";
				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				if (flag == false) {
					// 记录修改前的课程信息
					srcCourse = new Course(course);
				} else {
					// 记录修改后的课程信息
					desCourse = new Course(course);
				}
				str += course;
				txtCourseInfo.setText(str);
				txtCourseName.setText(course.getName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将课程表里的信息刷新
	public void setCourseName(int Cno) {
		Database db = Database.getDatabase();
		String sql = "Update course" + " set  cName = ?" + "	where cno = " + Cno + ";";
		String[] s = new String[1];
		s[0] = txtCourseName.getText();
		flag = true;
		db.executeUpdate(sql, s);
	}

	// 添加修改日志
	public void showSysLog(JTextArea txtChangeLog, String logContent) {
		// 课程名
		if (!(srcCourse.getName().equals(desCourse.getName()))) {
			logContent += tName + "将课程" + "\"" + srcCourse.getName() + "\"" + "的课程名从" + "\"" + srcCourse.getName()
					+ "\"" + "改为" + "\"" + desCourse.getName() + "\"" + "\n";
		}
		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			txtChangeLog.setText(logContent);
			logContent = "";
			flag = false; // 又回到未修改状态
		}
	}
}
