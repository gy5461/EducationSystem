package org.hgc.view;
//教务处登录后主页

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hgc.db.Database;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminView extends JFrame {

	private JPanel contentPane;
	private JButton btnSeeScore;
	private JButton btnTeaCourse;
	private JButton btnStuInfo;
	private JButton btnDiary;
	private JButton btnReturn;

	/**
	 * Create the frame.
	 */
	public AdminView(int userID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 查询课程成绩按钮
		btnSeeScore = new JButton("\u67E5\u8BE2\u8BFE\u7A0B\u6210\u7EE9");
		btnSeeScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SeeAllCourseView sCourse = new SeeAllCourseView();
				sCourse.setVisible(true);
			}
		});
		btnSeeScore.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnSeeScore.setBounds(65, 74, 171, 384);
		contentPane.add(btnSeeScore);

		// 教师课程入口按钮
		btnTeaCourse = new JButton("\u6559\u5E08\u8BFE\u7A0B\u5165\u53E3");
		btnTeaCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ATeaCourseView aCourse = new ATeaCourseView(getTeacherName(userID));
				aCourse.setVisible(true);
			}
		});
		btnTeaCourse.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnTeaCourse.setBounds(304, 74, 171, 384);
		contentPane.add(btnTeaCourse);

		// 学生信息入口按钮
		btnStuInfo = new JButton("\u5B66\u751F\u4FE1\u606F\u5165\u53E3");
		btnStuInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 传入教务处老师的名字
				AStuInfoView aInfo = new AStuInfoView(getTeacherName(userID));
				aInfo.setVisible(true);
			}
		});
		btnStuInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnStuInfo.setBounds(541, 74, 171, 384);
		contentPane.add(btnStuInfo);

		// 查看系统日志按钮
		btnDiary = new JButton("\u67E5\u770B\u7CFB\u7EDF\u65E5\u5FD7");
		btnDiary.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnDiary.setBounds(-11, -5, 181, 54);
		contentPane.add(btnDiary);
		btnDiary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DiaryView dView = new DiaryView();
				dView.setVisible(true);
			}
		});

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
	}

	public void back() {
		int exi = JOptionPane.showConfirmDialog(null, "确定要退出登陆吗？", "友情提示", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (exi == JOptionPane.YES_OPTION) {
			LoginView login = new LoginView();
			login.setVisible(true);
			this.dispose();
		}
	}

	// 用teacherId在教师表中找到teacherName
	public String getTeacherName(int userID) {
		Database db = Database.getDatabase();
		String sql = "select * from teacher where tno = " + userID;
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