package org.hgc.view;

//修改某门课程某个学生的成绩界面

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.log.SystemLog;
import org.hgc.object.Student;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class ChangeStuScoreView extends JFrame {

	private JPanel contentPane;
	private JTextField txtSno;
	private JTextField txtGrade;
	private JTextArea txtShow;
	private String logContent;
	private String tName;
	private String sName;
	private JLabel labelScore;
	private JLabel labelChangeNum;
	private JLabel labelTip;
	private JButton btnSubmit;
	private ArrayList<Integer> Snos;
	private String sChangeName;
	private int srcStudentScore;
	private int desStudentScore;
	private int Cno;
	boolean flag = false; // 标记是否修改信息
	boolean exist = false;
	JLabel labelWarn;

	/**
	 * Create the frame.
	 */
	public ChangeStuScoreView(int Cno, String tName) {
		this.Cno = Cno;
		this.tName = tName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “该课程学生成绩情况”标签
		labelScore = new JLabel("\u8BE5\u8BFE\u7A0B\u5B66\u751F\u6210\u7EE9\u60C5\u51B5\uFF1A");
		labelScore.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelScore.setBounds(59, 80, 194, 21);
		contentPane.add(labelScore);

		// 该课程学生成绩情况显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(59, 135, 665, 233);
		txtShow = new JTextArea();
		txtShow.setBounds(59, 135, 665, 233);
		contentPane.add(scroll);
		txtShow.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		scroll.setViewportView(txtShow);

		// “修改学号为”标签
		labelChangeNum = new JLabel("\u4FEE\u6539\u5B66\u53F7\u4E3A");
		labelChangeNum.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChangeNum.setBounds(59, 411, 90, 21);
		contentPane.add(labelChangeNum);

		// 要修改的选课记录序号输入框
		txtSno = new JTextField();
		txtSno.setBounds(150, 408, 90, 27);
		contentPane.add(txtSno);
		txtSno.setColumns(10);

		// “的选课记录的学生成绩为”标签
		labelTip = new JLabel("\u7684\u9009\u8BFE\u8BB0\u5F55\u7684\u5B66\u751F\u6210\u7EE9\u4E3A");
		labelTip.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelTip.setBounds(245, 411, 220, 21);
		contentPane.add(labelTip);

		// 修改成绩输入框
		txtGrade = new JTextField();
		txtGrade.setBounds(457, 410, 60, 27);
		contentPane.add(txtGrade);
		txtGrade.setColumns(10);

		// 成绩格式错误警告标签
		labelWarn = new JLabel("");
		labelWarn.setForeground(Color.RED);
		labelWarn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		labelWarn.setBounds(211, 462, 335, 21);
		contentPane.add(labelWarn);

		// 确认提交修改按钮
		btnSubmit = new JButton("\u786E\u8BA4\u63D0\u4EA4\u4FEE\u6539");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showScore();
				changeGrade();
				addSysLog();
			}
		});
		btnSubmit.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnSubmit.setBounds(563, 439, 153, 29);
		contentPane.add(btnSubmit);
		showScore();
		logContent = "";

		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// 展示该课程的全部学生信息
	public void showScore() {
		Database db = Database.getDatabase();
		String sql = "select sName,SC.sno,SC.cno,cName,SC.grade from SC,Student,Course where SC.sno = Student.sno AND SC.cno = Course.cno AND Course.cno = "
				+ Cno;
		ResultSet rSet = db.executeQuery(sql);
		String str = "              姓名" + "\t" + "学号" + "\t" + "课程号" + "\t" + "课程名" + "\t" + "成绩" + "\n";

		try {
			// 初始化学号列表
			Snos = new ArrayList<Integer>();
			while (rSet.next()) {
				sName = rSet.getString("sName");
				int Sno = rSet.getInt("SC.sno");
				int Cno = rSet.getInt("SC.cno");
				String cName = rSet.getString("cName");
				int grade = rSet.getInt("SC.grade");
				str += "              " + sName + "\t" + Sno + "\t" + Cno + "\t" + cName + "\t" + grade + "\n";
				// 添加学号到学号列表
				Snos.add(Sno);
				String sSno = Sno + "";
				if (sSno.equals(txtSno.getText())) {
					// 记录要改的学生姓名
					sChangeName = sName;
					if (flag == false) {
						// 记录修改前的学生成绩
						srcStudentScore = grade;
					} else {
						// 记录修改后的学生成绩
						desStudentScore = grade;
					}
				}
			}
			txtShow.setText(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 修改某位同学的成绩
	public void changeGrade() {
		Database db = Database.getDatabase();
		try {
			// 修改成绩前检查该学生是否选了该门课，否则给予提示
			int Sno = Integer.parseInt(txtSno.getText());
			// 检查学号是否在选了该门课的学生列表中
			boolean hasChoosen = false;
			for (int i : Snos) {
				if (i == Sno) {
					hasChoosen = true;
					break;
				}
			}
			if (hasChoosen == true) {
				int x = Integer.parseInt(txtGrade.getText());
				if (x >= 0 && x <= 100) {
					// 范围正确
				} else {
					throw new Exception("成绩不在规定范围之内");
				}
				String sql = "update SC set grade = " + Integer.parseInt(txtGrade.getText()) + " where sno = "
						+ Integer.parseInt(txtSno.getText()) + " AND cno = " + Cno;
				int result = db.executeUpdate(sql);
				if (result == 1) {
					flag = true; // 修改过
					// 刷界面
					showScore();
					JOptionPane.showMessageDialog(null, "成绩修改成功！！", "提示", JOptionPane.INFORMATION_MESSAGE);
					if (exist) {
						labelWarn.setText("");
					}
				} else {
					// 修改失败
					JOptionPane.showMessageDialog(null, "成绩修改失败！！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				// 输入学号对应学生未选该门课
				JOptionPane.showMessageDialog(null, "该生未选此课！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			// 应输入int型的成绩
			labelWarn.setText("成绩输入错误，请重新输入正确数字(0~100)");
			exist = true;
		}
	}

	// 添加修改日志
	public void addSysLog() {
		// 修改以后记录操作

		// 成绩
		if (srcStudentScore != desStudentScore) {
			logContent += tName + "将学生" + "\"" + sChangeName + "\"" + "的成绩从" + "\"" + srcStudentScore + "\"" + "改为"
					+ "\"" + desStudentScore + "\"" + "\n";
		}

		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // 又回到未修改状态
			showScore();
		}
	}
}
