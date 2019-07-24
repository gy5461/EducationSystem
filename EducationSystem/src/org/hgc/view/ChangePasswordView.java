package org.hgc.view;

//修改密码界面

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.log.SystemLog;
import org.hgc.object.Course;

import java.sql.*;

public class ChangePasswordView extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField txtOldPassword;
	private JPasswordField txtNewPassword;
	private JButton btnEnsureChange;
	private JLabel labelChangePass;
	private JLabel labelUserName;
	private JLabel labelOldPass;
	private JLabel labelNewPass;

	private String logContent;
	private String srcPassword;
	private String desPassword;
	boolean flag = false; // 标记是否修改信息

	JLabel labelNoUser;
	boolean exist1 = false;
	JLabel labelWrongPass;
	boolean exist2 = false;

	boolean isNormal = true;

	/**
	 * Create the frame.
	 */
	public ChangePasswordView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “修改密码”标签
		labelChangePass = new JLabel("\u4FEE\u6539\u5BC6\u7801");
		labelChangePass.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		labelChangePass.setBounds(333, 103, 142, 29);
		contentPane.add(labelChangePass);

		// “用户名”标签
		labelUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		labelUserName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelUserName.setBounds(240, 191, 81, 21);
		contentPane.add(labelUserName);

		// “旧密码”标签
		labelOldPass = new JLabel("\u65E7\u5BC6\u7801\uFF1A");
		labelOldPass.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelOldPass.setBounds(240, 265, 81, 21);
		contentPane.add(labelOldPass);

		// “新密码”标签
		labelNewPass = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		labelNewPass.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelNewPass.setBounds(240, 334, 81, 21);
		contentPane.add(labelNewPass);

		// 用户名输入框
		txtUserName = new JTextField();
		txtUserName.setBounds(333, 188, 157, 27);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		// 旧密码输入框
		txtOldPassword = new JPasswordField();
		txtOldPassword.setColumns(10);
		txtOldPassword.setBounds(333, 262, 157, 27);
		contentPane.add(txtOldPassword);

		// 新密码输入框
		txtNewPassword = new JPasswordField();
		txtNewPassword.setColumns(10);
		txtNewPassword.setBounds(333, 331, 157, 27);
		contentPane.add(txtNewPassword);

		// "用户不存在"
		labelNoUser = new JLabel("");
		labelNoUser.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelNoUser.setForeground(Color.RED);
		labelNoUser.setBounds(559, 191, 113, 21);
		contentPane.add(labelNoUser);

		// “密码输入错误”
		labelWrongPass = new JLabel("");
		labelWrongPass.setForeground(Color.RED);
		labelWrongPass.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		labelWrongPass.setBounds(559, 265, 113, 21);
		contentPane.add(labelWrongPass);

		// 确认修改按钮
		btnEnsureChange = new JButton("\u786E\u8BA4\u4FEE\u6539");
		btnEnsureChange.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnEnsureChange.setBounds(346, 414, 123, 29);
		contentPane.add(btnEnsureChange);
		btnEnsureChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 点击确认修改后，根据用户名将users表中对应的的密码改为新密码
				changePassword();
				if (isNormal) {
					// 添加系统日志
					addSysLog();
				}
			}
		});

		logContent = "";
		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// 点击确认修改后，根据用户名将users表中对应的密码改为新密码
	public void changePassword() {
		Database db = Database.getDatabase();
		String sql = "select userKey from users where userID = " + txtUserName.getText();

		boolean hasUserID = false;
		try {
			String str = txtUserName.getText();
			int i = Integer.parseInt(str);

			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				hasUserID = true;
				// 旧密码输入正确
				if (rs.getString("userKey").equals(txtOldPassword.getText())) {
					// 更新数据库中的旧密码

					sql = "Update users" + " set userKey = ?" + " where userID = ?;";
					String ins[] = new String[2];
					ins[0] = txtNewPassword.getText();
					ins[1] = txtUserName.getText();
					int result = db.executeUpdate(sql, ins);
					if (result == 1) {
						if (exist1) {
							labelNoUser.setText("");
						}
						if (exist2) {
							labelWrongPass.setText("");
						}
						JOptionPane.showMessageDialog(null, "密码修改成功！！", "提示", JOptionPane.INFORMATION_MESSAGE);
						srcPassword = txtOldPassword.getText();
						desPassword = txtNewPassword.getText();
						flag = true; // 已经修改
						isNormal = true;
					} else {
						// 修改失败
						isNormal = false;
						JOptionPane.showMessageDialog(null, "密码修改失败！！", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					// 密码输入错误
					isNormal = false;
					labelWrongPass.setText("密码输入错误");
					exist2 = true;
				}
			}
			if (!hasUserID) {
				// 用户不存在
				isNormal = false;
				labelNoUser.setText("用户不存在");
				exist1 = true;
			}
		} catch (Exception e) {
			// 用户不存在
			isNormal = false;
			labelNoUser.setText("用户名为整数");
			exist1 = true;
		}
	}

	// 添加修改日志
	public void addSysLog() {
		// 课程名
		if (!(srcPassword.equals(desPassword))) {
			logContent += txtUserName.getText() + "将密码" + "\"" + srcPassword + "\"" + "从" + "\"" + srcPassword + "\""
					+ "改为" + "\"" + desPassword + "\"" + "\n";
		}
		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // 又回到未修改状态
		}
	}

}
