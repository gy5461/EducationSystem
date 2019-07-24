package org.hgc.view;

//普通教师登录后主页

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.object.Course;

import java.sql.*;
import java.util.ArrayList;

public class TeacherView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea txtTeachInfo;
	private JComboBox comboBox;
	private JLabel labelCourse;
	private JLabel labelCourNum;
	private JLabel labelChooseType;
	private JButton btnRun;
	private JButton btnReturn;
	private ArrayList<Integer> Cnos;

	/**
	 * Create the frame.
	 */
	public TeacherView(int tno) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “本人任教课程信息”标签
		labelCourse = new JLabel("\u672C\u4EBA\u4EFB\u6559\u8BFE\u7A0B\u4FE1\u606F\uFF1A");
		labelCourse.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelCourse.setBounds(44, 82, 185, 21);
		contentPane.add(labelCourse);

		// 课程信息显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(44, 145, 695, 203);
		txtTeachInfo = new JTextArea();
		txtTeachInfo.setBounds(44, 145, 695, 203);
		txtTeachInfo.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		contentPane.add(scroll);
		scroll.setViewportView(txtTeachInfo);

		// “输入操作课程号”标签
		labelCourNum = new JLabel("\u8F93\u5165\u64CD\u4F5C\u8BFE\u7A0B\u53F7\uFF1A");
		labelCourNum.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelCourNum.setBounds(44, 392, 167, 21);
		contentPane.add(labelCourNum);

		// 操作课程号输入框
		textField = new JTextField();
		textField.setBounds(190, 389, 159, 27);
		contentPane.add(textField);
		textField.setColumns(10);

		// “选择操作类型”标签
		labelChooseType = new JLabel("\u9009\u62E9\u64CD\u4F5C\u7C7B\u578B:");
		labelChooseType.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChooseType.setBounds(65, 443, 131, 21);
		contentPane.add(labelChooseType);

		// 操作类型选择框
		comboBox = new JComboBox();
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		comboBox.setBounds(190, 440, 159, 27);
		contentPane.add(comboBox);
		comboBox.addItem("修改课程名称");
		comboBox.addItem("查看该课程学生成绩情况");

		// 进行操作按钮
		btnRun = new JButton("\u8FDB\u884C\u64CD\u4F5C");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int Cno = Integer.parseInt(textField.getText());
					boolean flag = false; // 标记cNo是否属于该老师所教的课程的课程号
					// 首先判断cNo是否属于该老师所教的课程的课程号
					for (int i : Cnos) {
						if (i == Cno) {
							flag = true;
							break;
						}
					}
					if (flag == true) {
						if (comboBox.getSelectedIndex() == 0) {
							ChangeCNameView cNameView = new ChangeCNameView(Cno, getTeacherName(tno));
							cNameView.setVisible(true);
						} else if (comboBox.getSelectedIndex() == 1) {
							ChangeStuScoreView cStuScoreView = new ChangeStuScoreView(Cno, getTeacherName(tno));
							cStuScoreView.setVisible(true);
						}
					} else {
						// 弹框提示没有操作权限
						JOptionPane.showMessageDialog(null, "您没有对该课程的操作权限!", "提示", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					// 弹框提示未输入课程号
					JOptionPane.showMessageDialog(null, "未输入课程号或课程号输入错误!", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRun.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnRun.setBounds(455, 392, 142, 72);
		contentPane.add(btnRun);

		// 返回按钮
		btnReturn = new JButton("\u8FD4\u56DE");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnReturn.setBounds(657, -5, 123, 41);
		contentPane.add(btnReturn);

		// 显示本人任教信息
		showTeachInfo(tno);
	}

	// 显示本人任教信息
	public void showTeachInfo(int tno) {
		// 根据老师id在课程表中找到该老师任教的所有课程并显示
		Database db = Database.getDatabase();
		String sql = "select * from course where cTeacherId = " + tno;
		ResultSet rSet = db.executeQuery(sql);
		try {
			String str = Course.getHeader();
			// 初始化课程号数组
			Cnos = new ArrayList<Integer>();
			while (rSet.next()) {
				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				str += course + "\n";
				// 将该老师所授课程的课程号写入课程号数组
				Cnos.add(course.getId());
			}
			txtTeachInfo.setText(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 用teacherId在教师表中找到teacherName
	public String getTeacherName(int tno) {
		Database db = Database.getDatabase();
		String sql = "select * from teacher where tno = " + tno;
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

	// 退出登录
	public void back() {
		int exi = JOptionPane.showConfirmDialog(null, "确定要退出登陆吗？", "友情提示", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (exi == JOptionPane.YES_OPTION) {
			LoginView login = new LoginView();
			login.setVisible(true);
			this.dispose();
		}
	}
}
