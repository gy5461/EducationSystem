package org.hgc.view;

//教务处修改课程学分、课时、选课属性界面

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.log.SystemLog;
import org.hgc.object.Course;
import org.hgc.object.Student;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ATeaCourseView extends JFrame {

	private JPanel contentPane;
	private JTextField txtCno;
	private JTextField txtCredit;
	private JTextField txtPeriod;
	private JTextArea txtShowCourseInfo;
	private JComboBox cbbxSelectProperty;
	private String logContent;
	private String tName;
	private Course srcCourse;
	private Course desCourse;
	boolean flag = false; // 标记是否修改信息
	boolean isNormal = true;
	private JLabel labelCourNum;
	private JLabel labelCourInfo;
	private JLabel labelChange;
	private JLabel labelCredit;
	private JLabel labelProperty;
	private JLabel labelPeriod;

	JButton btnEnsureSelect; // 确认选择按钮
	JButton btnEnsureChange; // 确认修改按钮

	/**
	 * Create the frame.
	 */
	public ATeaCourseView(String tName) {
		this.tName = tName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “课程信息”标签
		labelCourInfo = new JLabel("\u8BFE\u7A0B\u4FE1\u606F");
		labelCourInfo.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		labelCourInfo.setBounds(90, 61, 138, 34);
		contentPane.add(labelCourInfo);

		// 课程信息显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(90, 123, 610, 234);
		txtShowCourseInfo = new JTextArea();
		txtShowCourseInfo.setBounds(90, 123, 610, 234);
		txtShowCourseInfo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		contentPane.add(scroll);
		scroll.setViewportView(txtShowCourseInfo);

		// “对课程号为”标签
		labelCourNum = new JLabel("\u5BF9\u8BFE\u7A0B\u53F7\u4E3A");
		labelCourNum.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelCourNum.setBounds(90, 391, 138, 21);
		contentPane.add(labelCourNum);

		// 课程号输入框
		txtCno = new JTextField();
		txtCno.setBounds(187, 388, 96, 27);
		contentPane.add(txtCno);
		txtCno.setColumns(10);

		// “的课程作以下修改”标签
		labelChange = new JLabel("\u7684\u8BFE\u7A0B\u4F5C\u4EE5\u4E0B\u4FEE\u6539");
		labelChange.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChange.setBounds(298, 391, 161, 21);
		contentPane.add(labelChange);

		// “学分：”标签
		labelCredit = new JLabel("\u5B66\u5206\uFF1A");
		labelCredit.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelCredit.setBounds(90, 445, 81, 21);
		contentPane.add(labelCredit);

		// 修改学分输入框
		txtCredit = new JTextField();
		txtCredit.setBounds(141, 442, 96, 27);
		contentPane.add(txtCredit);
		txtCredit.setColumns(10);

		// “课时：”标签
		labelPeriod = new JLabel("\u8BFE\u65F6\uFF1A");
		labelPeriod.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelPeriod.setBounds(261, 445, 81, 21);
		contentPane.add(labelPeriod);

		// 修改课时输入框
		txtPeriod = new JTextField();
		txtPeriod.setBounds(313, 442, 96, 27);
		contentPane.add(txtPeriod);
		txtPeriod.setColumns(10);

		// “选课属性：”标签
		labelProperty = new JLabel("\u9009\u8BFE\u5C5E\u6027\uFF1A");
		labelProperty.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelProperty.setBounds(442, 445, 90, 21);
		contentPane.add(labelProperty);

		// 选课属性选择框
		cbbxSelectProperty = new JComboBox();
		cbbxSelectProperty.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		cbbxSelectProperty.setBounds(534, 442, 68, 27);
		contentPane.add(cbbxSelectProperty);
		cbbxSelectProperty.addItem("必修");
		cbbxSelectProperty.addItem("选修");

		// 确认选择按钮
		btnEnsureSelect = new JButton("\u786E\u8BA4\u9009\u62E9");
		btnEnsureSelect.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnEnsureSelect.setBounds(479, 387, 123, 29);
		this.add(btnEnsureSelect);
		btnEnsureSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 显示课程信息
				showCourseInfo();
				logContent = "";
			}

		});

		// 确认修改按钮
		btnEnsureChange = new JButton("\u786E\u8BA4\u4FEE\u6539 ");
		btnEnsureChange.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnEnsureChange.setBounds(577, 489, 123, 29);
		contentPane.add(btnEnsureChange);
		btnEnsureChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 修改课程表中的数据
				setCourseInfo();
				if (isNormal) {
					// 添加系统修改日志
					addSysLog();
				}
			}

		});
		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// 根据课程号显示课程信息
	public void showCourseInfo() {
		Database db = Database.getDatabase();
		String sql = "select * from course where cno = " + txtCno.getText();
		ResultSet rSet = db.executeQuery(sql);
		try {
			while (rSet.next()) {

				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				if (flag == false) {
					// 记录修改前的课程信息
					srcCourse = new Course(course);
				} else {
					// 记录修改后的课程信息
					desCourse = new Course(course);
				}
				String str = course.getHeader();
				str += course;
				txtShowCourseInfo.setText(str);
				String credit = "" + course.getCredit();
				String period = "" + course.getPeriod();
				txtCredit.setText(credit);
				txtPeriod.setText(period);
				cbbxSelectProperty.setSelectedItem(course.getSelectProperty());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将课程表里的信息刷新
	public void setCourseInfo() {
		Database db = Database.getDatabase();
		String sql = "Update course" + " set  cCredit = ?," + " cPeriod = ?," + " cSelectProperty = ?"
				+ "	where cno = " + txtCno.getText() + ";";
		String[] ins = new String[3];
		// 检查double类型
		try {
			double d = Double.parseDouble(txtCredit.getText());
			ins[0] = txtCredit.getText();
			// 检查int类型
			try {
				int i = Integer.parseInt(txtPeriod.getText());
				ins[1] = txtPeriod.getText();
				ins[2] = cbbxSelectProperty.getSelectedItem().toString();
				isNormal = true;
				flag = true;
				db.executeUpdate(sql, ins);
				// 刷界面，获取des
				showCourseInfo();
				JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				// 应输入int型
				isNormal = false;
				JOptionPane.showMessageDialog(null, "课时输入错误！请重新输入正确数字", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			// 应输入double型
			isNormal = false;
			JOptionPane.showMessageDialog(null, "学分输入错误！请重新输入正确数字", "提示", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	// 添加修改日志
	public void addSysLog() {

		// 修改以后记录操作
		// 学分
		if (!(Math.abs(srcCourse.getCredit() - desCourse.getCredit()) < 1e-8)) {
			logContent += tName + "将课程" + "\"" + srcCourse.getName() + "\"" + "的学分从" + "\"" + srcCourse.getCredit()
					+ "\"" + "改为" + "\"" + desCourse.getCredit() + "\"" + "\n";
		}

		// 学时
		if (srcCourse.getPeriod() != desCourse.getPeriod()) {
			logContent += tName + "将课程" + "\"" + srcCourse.getName() + "\"" + "的学时从" + "\"" + srcCourse.getPeriod()
					+ "\"" + "改为" + "\"" + desCourse.getPeriod() + "\"" + "\n";
		}

		// 选课属性
		if (!(srcCourse.getSelectProperty().equals(desCourse.getSelectProperty()))) {
			logContent += tName + "将课程" + "\"" + srcCourse.getName() + "\"" + "的选课属性从" + "\""
					+ srcCourse.getSelectProperty() + "\"" + "改为" + "\"" + desCourse.getSelectProperty() + "\"" + "\n";
		}

		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // 又回到未修改状态
			// 刷界面
			showCourseInfo();
		}
	}
}
