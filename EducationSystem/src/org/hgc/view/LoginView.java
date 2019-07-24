package org.hgc.view;

//��¼����

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

		// "��ӭ��������ϵͳ"��ǩ
		JLabel label = new JLabel("\u6B22\u8FCE\u6765\u5230\u6559\u52A1\u7CFB\u7EDF\uFF01");
		label.setFont(new Font("΢���ź�", Font.PLAIN, 22));
		label.setBounds(300, 80, 200, 48);
		contentPane.add(label);

		// ��¼���ѡ���
		cbbxUserKind = new JComboBox();
		cbbxUserKind.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		cbbxUserKind.setBounds(320, 183, 200, 27);
		contentPane.add(cbbxUserKind);
		cbbxUserKind.addItem("ѧ��");
		cbbxUserKind.addItem("��ͨ��ʦ");
		cbbxUserKind.addItem("������ʦ");

		// ��ѡ����ݡ���ǩ
		labelChooseID = new JLabel("\u9009\u62E9\u8EAB\u4EFD\uFF1A");
		labelChooseID.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChooseID.setBounds(229, 186, 90, 21);
		contentPane.add(labelChooseID);

		// ���û�������ǩ
		labelUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		labelUserName.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelUserName.setBounds(247, 247, 81, 21);
		contentPane.add(labelUserName);

		// �û��������
		txtUserName = new JTextField();
		txtUserName.setBounds(320, 245, 200, 27);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		// ���������
		txtUserKey = new JPasswordField();
		txtUserKey.setBounds(320, 297, 200, 27);
		contentPane.add(txtUserKey);
		txtUserKey.setColumns(10);

		// �����롱��ǩ
		labelPassword = new JLabel("\u5BC6\u7801\uFF1A");
		labelPassword.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelPassword.setBounds(265, 300, 54, 21);
		contentPane.add(labelPassword);

		// �޸����밴ť
		btnChange = new JButton("\u4FEE\u6539\u5BC6\u7801");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePasswordView cPwView = new ChangePasswordView();
				cPwView.setVisible(true);
			}
		});
		btnChange.setBounds(340, 420, 120, 29);
		contentPane.add(btnChange);

		// ���û������������
		labelWrong1 = new JLabel("");
		labelWrong1.setForeground(Color.RED);
		labelWrong1.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelWrong1.setBounds(576, 252, 162, 33);
		contentPane.add(labelWrong1);

		// ����������
		labelWrong2 = new JLabel("");
		labelWrong2.setForeground(Color.RED);
		labelWrong2.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelWrong2.setBounds(604, 300, 104, 21);
		contentPane.add(labelWrong2);

		// ��¼��ť
		btnLogin = new JButton("\u767B\u5F55");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����������Ϣȷ����¼
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
				// �û��������������ʾ���ݣ��û����������������������
				labelWrong1.setText("�û������������");
				labelWrong2.setText("����������");
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
				// kindID��userKind���Ӧ��kindMessage�͵�ǰѡ�����һ�²����������룬���ݵ�ǰ��ݵ�����Ӧ����
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
						labelWrong1.setText("�û����ѡ�����");
						labelWrong2.setText("������ѡ��");
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
		// ѧ����¼
		if (kindID == 1) {
			StudentView sView = new StudentView(userID);
			sView.setVisible(true);
			this.dispose();
		}
		// ��ͨ��ʦ��¼
		else if (kindID == 2) {
			TeacherView tView = new TeacherView(userID);
			tView.setVisible(true);
			this.dispose();
		}
		// ������ʦ��¼
		else if (kindID == 3) {
			AdminView aView = new AdminView(userID);
			aView.setVisible(true);
			this.dispose();
		}
	}
}
