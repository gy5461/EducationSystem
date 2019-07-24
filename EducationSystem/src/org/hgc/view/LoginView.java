package org.hgc.view;

//登录界面

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;

import java.sql.*;

public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField txtUserKey;
	private JComboBox cbbxUserKind;
	private JLabel labelWrong1;
	private JLabel labelWrong2;
	private JButton btnLogin;
	private JLabel labelChooseID;
	private JLabel labelUserName;
	private JLabel labelPassword;
	private JButton btnChange;
	boolean exist = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// "欢迎来到教务系统"标签
		JLabel label = new JLabel("\u6B22\u8FCE\u6765\u5230\u6559\u52A1\u7CFB\u7EDF\uFF01");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		label.setBounds(300, 80, 200, 48);
		contentPane.add(label);

		// 登录身份选择框
		cbbxUserKind = new JComboBox();
		cbbxUserKind.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		cbbxUserKind.setBounds(320, 183, 200, 27);
		contentPane.add(cbbxUserKind);
		cbbxUserKind.addItem("学生");
		cbbxUserKind.addItem("普通老师");
		cbbxUserKind.addItem("教务处老师");

		// “选择身份”标签
		labelChooseID = new JLabel("\u9009\u62E9\u8EAB\u4EFD\uFF1A");
		labelChooseID.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelChooseID.setBounds(229, 186, 90, 21);
		contentPane.add(labelChooseID);

		// “用户名”标签
		labelUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		labelUserName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelUserName.setBounds(247, 247, 81, 21);
		contentPane.add(labelUserName);

		// 用户名输入框
		txtUserName = new JTextField();
		txtUserName.setBounds(320, 245, 200, 27);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		// 密码输入框
		txtUserKey = new JPasswordField();
		txtUserKey.setBounds(320, 297, 200, 27);
		contentPane.add(txtUserKey);
		txtUserKey.setColumns(10);

		// “密码”标签
		labelPassword = new JLabel("\u5BC6\u7801\uFF1A");
		labelPassword.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelPassword.setBounds(265, 300, 54, 21);
		contentPane.add(labelPassword);

		// 修改密码按钮
		btnChange = new JButton("\u4FEE\u6539\u5BC6\u7801");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePasswordView cPwView = new ChangePasswordView();
				cPwView.setVisible(true);
			}
		});
		btnChange.setBounds(340, 420, 120, 29);
		contentPane.add(btnChange);

		// “用户名或密码错误”
		labelWrong1 = new JLabel("");
		labelWrong1.setForeground(Color.RED);
		labelWrong1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelWrong1.setBounds(576, 252, 162, 33);
		contentPane.add(labelWrong1);

		// 请重新输入
		labelWrong2 = new JLabel("");
		labelWrong2.setForeground(Color.RED);
		labelWrong2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelWrong2.setBounds(604, 300, 104, 21);
		contentPane.add(labelWrong2);

		// 登录按钮
		btnLogin = new JButton("\u767B\u5F55");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 根据三个信息确定登录
				ensureLogin();
			}
		});
		btnLogin.setBounds(355, 366, 90, 29);
		contentPane.add(btnLogin);
	}

	public void ensureLogin() {
		Database db = Database.getDatabase();
		String sql = "select * from Users where userID = ? and userKey = ?";
		String[] str;
		str = new String[2];
		str[0] = txtUserName.getText();
		str[1] = txtUserKey.getText();
		ResultSet rSet = db.executeQuery(sql, str);
		try {
			if (rSet.getFetchSize() == 0) {
				// 用户名或密码错误，提示内容：用户名或密码错误，请重新输入
				labelWrong1.setText("用户名或密码错误，");
				labelWrong2.setText("请重新输入");
				exist = true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (rSet.next()) {
				int kindID = rSet.getInt("kindID");
				int userID = rSet.getInt("userID");
				// kindID在userKind里对应的kindMessage和当前选中身份一致才能正常登入，根据当前身份登入相应界面
				sql = "select * from userKind where kindID = " + kindID;
				ResultSet rSet2 = db.executeQuery(sql);
				while (rSet2.next()) {
					String kindMessage = rSet2.getString("kindMessage");
					if (kindMessage.equals(cbbxUserKind.getSelectedItem())) {
						change(kindID, userID);
						if (exist) {
							labelWrong1.setText("");
							labelWrong2.setText("");
						}
					} else {
						labelWrong1.setText("用户身份选择错误，");
						labelWrong2.setText("请重新选择");
						exist = true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void change(int kindID, int userID) {
		// 学生登录
		if (kindID == 1) {
			StudentView sView = new StudentView(userID);
			sView.setVisible(true);
			this.dispose();
		}
		// 普通老师登录
		else if (kindID == 2) {
			TeacherView tView = new TeacherView(userID);
			tView.setVisible(true);
			this.dispose();
		}
		// 教务处老师登录
		else if (kindID == 3) {
			AdminView aView = new AdminView(userID);
			aView.setVisible(true);
			this.dispose();
		}
	}
}
