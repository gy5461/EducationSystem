package org.hgc.view;

//教务处修改学生信息界面

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import org.hgc.db.*;
import org.hgc.log.SystemLog;
import org.hgc.object.Student;

import java.sql.*;

public class AStuInfoView extends JFrame {

	private JPanel contentPane;
	private JTextField txtSno;
	private JTextField txtName;
	private JTextField txtCountry;
	private JTextField txtNation;
	private JTextField txtMajor;
	private JTextField txtStartYear;
	private JTextArea txtShowStudentInfo;
	private JComboBox cbbxSex;
	private JLabel labelStuInfo;
	private JLabel labelNum;
	private JLabel labelChange;
	private JLabel labelName;
	private JLabel labelSex;
	private JLabel labelCountry;
	private JLabel labelNation;
	private JLabel labelYear;
	private JLabel labelMajor;

	private JButton btnEnsureSelect;
	private JButton btnEnsureChange;

	private String logContent;
	private String tName;
	private Student srcStudent;
	private Student desStudent;
	boolean flag = false; // 标记是否修改信息
	boolean isNormal = true;

	/**
	 * Create the frame. 传入教务处老师的名字
	 */
	public AStuInfoView(String tName) {
		this.tName = tName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “学生信息：”标签
		labelStuInfo = new JLabel("\u5B66\u751F\u4FE1\u606F\uFF1A");
		labelStuInfo.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		labelStuInfo.setBounds(56, 45, 133, 34);
		contentPane.add(labelStuInfo);

		// 学生信息显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(56, 104, 664, 219);
		txtShowStudentInfo = new JTextArea();
		txtShowStudentInfo.setBounds(56, 104, 664, 219);
		txtShowStudentInfo.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		contentPane.add(scroll);
		scroll.setViewportView(txtShowStudentInfo);

		// “对学号为”标签
		labelNum = new JLabel("\u5BF9\u5B66\u53F7\u4E3A");
		labelNum.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelNum.setBounds(56, 362, 81, 21);
		contentPane.add(labelNum);

		// 学号输出框
		txtSno = new JTextField();
		txtSno.setBounds(138, 359, 143, 27);
		contentPane.add(txtSno);
		txtSno.setColumns(10);

		// “的学生信息作以下修改”标签
		labelChange = new JLabel("\u7684\u5B66\u751F\u4FE1\u606F\u4F5C\u4EE5\u4E0B\u4FEE\u6539\uFF1A");
		labelChange.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChange.setBounds(296, 362, 206, 21);
		contentPane.add(labelChange);

		// “姓名”标签
		labelName = new JLabel("\u59D3\u540D\uFF1A");
		labelName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelName.setBounds(56, 414, 81, 21);
		contentPane.add(labelName);

		// 修改姓名输入框
		txtName = new JTextField();
		txtName.setBounds(112, 411, 96, 27);
		contentPane.add(txtName);
		txtName.setColumns(10);

		// “性别”标签
		labelSex = new JLabel("\u6027\u522B\uFF1A");
		labelSex.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelSex.setBounds(225, 414, 81, 21);
		contentPane.add(labelSex);

		// 修改性别选择框
		cbbxSex = new JComboBox();
		cbbxSex.setBounds(273, 411, 41, 27);
		cbbxSex.addItem("男");
		cbbxSex.addItem("女");
		contentPane.add(cbbxSex);

		// “国籍”标签
		labelCountry = new JLabel("\u56FD\u7C4D\uFF1A");
		labelCountry.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelCountry.setBounds(358, 414, 81, 21);
		contentPane.add(labelCountry);

		// 修改国籍输入框
		txtCountry = new JTextField();
		txtCountry.setColumns(10);
		txtCountry.setBounds(408, 411, 96, 27);
		contentPane.add(txtCountry);

		// “民族”标签
		labelNation = new JLabel("\u6C11\u65CF\uFF1A");
		labelNation.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelNation.setBounds(542, 414, 81, 21);
		contentPane.add(labelNation);

		// “确认选择”按钮
		btnEnsureSelect = new JButton("\u786E\u8BA4\u9009\u62E9");
		btnEnsureSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStudentInfo();
				logContent = "";
			}
		});
		btnEnsureSelect.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnEnsureSelect.setBounds(547, 358, 123, 29);
		contentPane.add(btnEnsureSelect);

		// 修改民族输入框
		txtNation = new JTextField();
		txtNation.setColumns(10);
		txtNation.setBounds(591, 411, 96, 27);
		contentPane.add(txtNation);

		// “专业”标签
		labelMajor = new JLabel("\u4E13\u4E1A\uFF1A");
		labelMajor.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelMajor.setBounds(56, 466, 81, 21);
		contentPane.add(labelMajor);

		// 修改专业输入框
		txtMajor = new JTextField();
		txtMajor.setBounds(112, 463, 267, 27);
		contentPane.add(txtMajor);
		txtMajor.setColumns(10);

		// "入学年份"标签
		labelYear = new JLabel("\u5165\u5B66\u5E74\u4EFD\uFF1A");
		labelYear.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelYear.setBounds(394, 466, 96, 21);
		contentPane.add(labelYear);

		// 修改入学年份输入框
		txtStartYear = new JTextField();
		txtStartYear.setColumns(10);
		txtStartYear.setBounds(481, 463, 96, 27);
		contentPane.add(txtStartYear);

		// 确认修改按钮
		btnEnsureChange = new JButton("\u786E\u8BA4\u4FEE\u6539");
		btnEnsureChange.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnEnsureChange.setBounds(615, 482, 110, 29);
		contentPane.add(btnEnsureChange);
		btnEnsureChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStudentInfo();
				if (isNormal) {
					// 添加系统修改日志
					addSysLog();
				}
			}
		});

		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// 根据学号在Student表中找到该学生信息，并显示
	public void showStudentInfo() {
		Database db = Database.getDatabase();
		String sql = "select * from student where sno = " + txtSno.getText();
		ResultSet rSet = db.executeQuery(sql);
		try {
			while (rSet.next()) {
				Student student = new Student(rSet.getInt("sno"), rSet.getString("sName"), rSet.getString("sSex"),
						rSet.getString("sNation"), rSet.getString("sCountry"), rSet.getString("sMajor"),
						rSet.getInt("sStartYear"));
				if (flag == false) {
					// 记录修改前的学生信息
					srcStudent = new Student(student);
				} else {
					// 记录修改后的学生信息
					desStudent = new Student(student);
				}

				String str = student.getHeader();
				str += student;
				txtShowStudentInfo.setText(str);
				txtName.setText(student.getName());
				cbbxSex.setSelectedItem(student.getSex());
				txtCountry.setText(student.getCountry());
				txtNation.setText(student.getNation());
				txtMajor.setText(student.getMajor());
				String s = "" + student.getStartYear();
				txtStartYear.setText(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将学生表里的信息刷新
	public void setStudentInfo() {
		Database db = Database.getDatabase();
		String sql = "Update student" + " set  sName = ?," + " sSex = ?," + " sNation = ?," + " sCountry = ?,"
				+ " sMajor = ?," + " sStartYear = ?" + "	where sno = " + txtSno.getText() + ";";
		String[] ins = new String[6];
		ins[0] = txtName.getText();
		ins[1] = cbbxSex.getSelectedItem().toString();
		ins[2] = txtNation.getText();
		ins[3] = txtCountry.getText();
		ins[4] = txtMajor.getText();
		try {
			int i = Integer.parseInt(txtStartYear.getText());
			ins[5] = txtStartYear.getText();
			db.executeUpdate(sql, ins);
			flag = true;
			isNormal = true;
			// 刷新界面
			showStudentInfo();
			JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// 类型出现异常
			isNormal = false;
			JOptionPane.showMessageDialog(null, "入学年份输入错误！请重新输入正确数字", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// 添加修改日志
	public void addSysLog() {

		// 修改以后记录操作
		// 姓名
		if (!srcStudent.getName().equals(desStudent.getName())) {
			logContent += tName + "将学生" + "\"" + srcStudent.getName() + "\"" + "的姓名从" + "\"" + srcStudent.getName()
					+ "\"" + "改为" + "\"" + desStudent.getName() + "\"" + "\n";
		}

		// 性别
		if (!srcStudent.getSex().equals(desStudent.getSex())) {
			logContent += tName + "将学生" + "\"" + srcStudent.getName() + "\"" + "的性别从" + "\"" + srcStudent.getSex()
					+ "\"" + "改为" + "\"" + desStudent.getSex() + "\"" + "\n";
		}

		// 国籍
		if (!srcStudent.getCountry().equals(desStudent.getCountry())) {
			logContent += tName + "将学生" + "\"" + srcStudent.getName() + "\"" + "的国籍从" + "\"" + srcStudent.getCountry()
					+ "\"" + "改为" + "\"" + desStudent.getCountry() + "\"" + "\n";
		}

		// 民族
		if (!srcStudent.getNation().equals(desStudent.getNation())) {
			logContent += tName + "将学生" + "\"" + srcStudent.getName() + "\"" + "的民族从" + "\"" + srcStudent.getNation()
					+ "\"" + "改为" + "\"" + desStudent.getNation() + "\"" + "\n";
		}

		// 专业
		if (!srcStudent.getMajor().equals(desStudent.getMajor())) {
			logContent += tName + "将学生" + "\"" + srcStudent.getName() + "\"" + "的专业从" + "\"" + srcStudent.getMajor()
					+ "\"" + "改为" + "\"" + desStudent.getMajor() + "\"" + "\n";
		}

		// 入学年份
		if (srcStudent.getStartYear() != desStudent.getStartYear()) {
			logContent += tName + "将学生" + "\"" + srcStudent.getName() + "\"" + "的入学年份从" + "\""
					+ srcStudent.getStartYear() + "\"" + "改为" + "\"" + desStudent.getStartYear() + "\"" + "\n";
		}

		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // 又回到未修改状态
			showStudentInfo();
		}
	}
}
