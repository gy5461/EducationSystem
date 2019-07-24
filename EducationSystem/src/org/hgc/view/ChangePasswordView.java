package org.hgc.view;

//�޸��������

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
	boolean flag = false; // ����Ƿ��޸���Ϣ

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

		// ���޸����롱��ǩ
		labelChangePass = new JLabel("\u4FEE\u6539\u5BC6\u7801");
		labelChangePass.setFont(new Font("΢���ź�", Font.PLAIN, 22));
		labelChangePass.setBounds(333, 103, 142, 29);
		contentPane.add(labelChangePass);

		// ���û�������ǩ
		labelUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		labelUserName.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelUserName.setBounds(240, 191, 81, 21);
		contentPane.add(labelUserName);

		// �������롱��ǩ
		labelOldPass = new JLabel("\u65E7\u5BC6\u7801\uFF1A");
		labelOldPass.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelOldPass.setBounds(240, 265, 81, 21);
		contentPane.add(labelOldPass);

		// �������롱��ǩ
		labelNewPass = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		labelNewPass.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelNewPass.setBounds(240, 334, 81, 21);
		contentPane.add(labelNewPass);

		// �û��������
		txtUserName = new JTextField();
		txtUserName.setBounds(333, 188, 157, 27);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		// �����������
		txtOldPassword = new JPasswordField();
		txtOldPassword.setColumns(10);
		txtOldPassword.setBounds(333, 262, 157, 27);
		contentPane.add(txtOldPassword);

		// �����������
		txtNewPassword = new JPasswordField();
		txtNewPassword.setColumns(10);
		txtNewPassword.setBounds(333, 331, 157, 27);
		contentPane.add(txtNewPassword);

		// "�û�������"
		labelNoUser = new JLabel("");
		labelNoUser.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelNoUser.setForeground(Color.RED);
		labelNoUser.setBounds(559, 191, 113, 21);
		contentPane.add(labelNoUser);

		// �������������
		labelWrongPass = new JLabel("");
		labelWrongPass.setForeground(Color.RED);
		labelWrongPass.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelWrongPass.setBounds(559, 265, 113, 21);
		contentPane.add(labelWrongPass);

		// ȷ���޸İ�ť
		btnEnsureChange = new JButton("\u786E\u8BA4\u4FEE\u6539");
		btnEnsureChange.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnEnsureChange.setBounds(346, 414, 123, 29);
		contentPane.add(btnEnsureChange);
		btnEnsureChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ���ȷ���޸ĺ󣬸����û�����users���ж�Ӧ�ĵ������Ϊ������
				changePassword();
				if (isNormal) {
					// ���ϵͳ��־
					addSysLog();
				}
			}
		});

		logContent = "";
		// ���������ڹر�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// ���ȷ���޸ĺ󣬸����û�����users���ж�Ӧ�������Ϊ������
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
				// ������������ȷ
				if (rs.getString("userKey").equals(txtOldPassword.getText())) {
					// �������ݿ��еľ�����

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
						JOptionPane.showMessageDialog(null, "�����޸ĳɹ�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						srcPassword = txtOldPassword.getText();
						desPassword = txtNewPassword.getText();
						flag = true; // �Ѿ��޸�
						isNormal = true;
					} else {
						// �޸�ʧ��
						isNormal = false;
						JOptionPane.showMessageDialog(null, "�����޸�ʧ�ܣ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					// �����������
					isNormal = false;
					labelWrongPass.setText("�����������");
					exist2 = true;
				}
			}
			if (!hasUserID) {
				// �û�������
				isNormal = false;
				labelNoUser.setText("�û�������");
				exist1 = true;
			}
		} catch (Exception e) {
			// �û�������
			isNormal = false;
			labelNoUser.setText("�û���Ϊ����");
			exist1 = true;
		}
	}

	// ����޸���־
	public void addSysLog() {
		// �γ���
		if (!(srcPassword.equals(desPassword))) {
			logContent += txtUserName.getText() + "������" + "\"" + srcPassword + "\"" + "��" + "\"" + srcPassword + "\""
					+ "��Ϊ" + "\"" + desPassword + "\"" + "\n";
		}
		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // �ֻص�δ�޸�״̬
		}
	}

}
