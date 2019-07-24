package org.hgc.view;

//ѧ����¼����ҳ

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.object.Student;

import java.sql.*;

public class StudentView extends JFrame {

	private JPanel contentPane;
	private JButton btnPicture;
	private JLabel labelSnoTag;
	private JLabel labelSNameTag;
	private JLabel labelSName;
	private JLabel labelSno;
	private JLabel labelSexTag;
	private JLabel labelNationTag;
	private JLabel labelCountryTag;
	private JLabel labelMajorTag;
	private JLabel labelStartYearTag;
	private JLabel labelSex;
	private JLabel labelNation;
	private JLabel labelCountry;
	private JLabel labelMajor;
	private JLabel labelStartYear;
	private JButton btnNew;
	private JButton btnBack;
	private ImageIcon icon;

	/**
	 * Create the frame.
	 */
	public StudentView(int mySno) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ��Ƭ��
		icon = new ImageIcon("images/head.png");
		btnPicture = new JButton(icon);
		btnPicture.setBounds(45, 42, 145, 190);
		contentPane.add(btnPicture);

		// ��ѧ�š���ǩ
		labelSnoTag = new JLabel("\u5B66\u53F7\uFF1A");
		labelSnoTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelSnoTag.setBounds(286, 103, 81, 21);
		contentPane.add(labelSnoTag);

		// ����������ǩ
		labelSNameTag = new JLabel("\u59D3\u540D\uFF1A");
		labelSNameTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelSNameTag.setBounds(286, 156, 81, 21);
		contentPane.add(labelSNameTag);

		// ��������
		labelSName = new JLabel("*******");
		labelSName.setBounds(349, 156, 99, 21);
		contentPane.add(labelSName);
		labelSName.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// ѧ������
		labelSno = new JLabel("**");
		labelSno.setBounds(349, 103, 99, 21);
		contentPane.add(labelSno);
		labelSno.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// ���Ա𡱱�ǩ
		labelSexTag = new JLabel("�Ա�");
		labelSexTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelSexTag.setBounds(58, 291, 81, 21);
		contentPane.add(labelSexTag);

		// �����塱��ǩ
		labelNationTag = new JLabel("����");
		labelNationTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelNationTag.setBounds(58, 339, 81, 21);
		contentPane.add(labelNationTag);

		// ����������ǩ
		labelCountryTag = new JLabel("����");
		labelCountryTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelCountryTag.setBounds(58, 387, 81, 21);
		contentPane.add(labelCountryTag);

		// ��רҵ����ǩ
		labelMajorTag = new JLabel("רҵ");
		labelMajorTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelMajorTag.setBounds(58, 433, 81, 21);
		contentPane.add(labelMajorTag);

		// ����ѧ��ݡ���ǩ
		labelStartYearTag = new JLabel("��ѧ���");
		labelStartYearTag.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelStartYearTag.setBounds(58, 480, 99, 21);
		contentPane.add(labelStartYearTag);

		// �Ա�����
		labelSex = new JLabel("****");
		labelSex.setBounds(129, 291, 112, 21);
		contentPane.add(labelSex);
		labelSex.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// ��������
		labelNation = new JLabel("*");
		labelNation.setBounds(129, 339, 112, 21);
		contentPane.add(labelNation);
		labelNation.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// ��������
		labelCountry = new JLabel("*****");
		labelCountry.setBounds(129, 387, 112, 21);
		contentPane.add(labelCountry);
		labelCountry.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// רҵ����
		labelMajor = new JLabel("********");
		labelMajor.setBounds(129, 433, 112, 21);
		contentPane.add(labelMajor);
		labelMajor.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// ��ѧ�������
		labelStartYear = new JLabel("**********");
		labelStartYear.setBounds(161, 480, 112, 21);
		contentPane.add(labelStartYear);
		labelStartYear.setFont(new Font("΢���ź�", Font.PLAIN, 17));

		// �鿴�������пγ̰�ť
		btnNew = new JButton("\u67E5\u770B\u672C\u4EBA\u8BFE\u7A0B >");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SeeAllCourseView sAllCourseView = new SeeAllCourseView(mySno);
				sAllCourseView.setVisible(true);
			}
		});
		btnNew.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		btnNew.setBounds(378, 307, 345, 180);
		contentPane.add(btnNew);
		btnNew.setBorderPainted(false);

		// ���ذ�ť
		btnBack = new JButton("\u8FD4\u56DE");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnBack.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnBack.setBounds(657, -5, 123, 41);
		contentPane.add(btnBack);

		showMessage(mySno);
	}

	// ��ʼ��ѧ������Ϣ
	void showMessage(int mySno) {
		Database db = Database.getDatabase();
		String sql = "select * from Student where sno = " + mySno;
		ResultSet rSet = db.executeQuery(sql);
		Student student = null;
		try {
			while (rSet.next()) {
				student = new Student(rSet.getInt("sno"), rSet.getString("sName"), rSet.getString("sSex"),
						rSet.getString("sNation"), rSet.getString("sCountry"), rSet.getString("sMajor"),
						rSet.getInt("sStartYear"));
			}
			labelSno.setText(String.valueOf(student.getId()));
			labelSName.setText(student.getName());
			labelSex.setText(student.getSex());
			labelNation.setText(student.getNation());
			labelCountry.setText(student.getCountry());
			labelMajor.setText(student.getMajor());
			labelStartYear.setText(String.valueOf(student.getStartYear()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �˳���¼
	public void back() {
		int exi = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�˳���½��", "������ʾ", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (exi == JOptionPane.YES_OPTION) {
			LoginView login = new LoginView();
			login.setVisible(true);
			this.dispose();
		}
	}
}
