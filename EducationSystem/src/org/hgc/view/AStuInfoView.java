package org.hgc.view;

//�����޸�ѧ����Ϣ����

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
	boolean flag = false; // ����Ƿ��޸���Ϣ
	boolean isNormal = true;

	/**
	 * Create the frame. ���������ʦ������
	 */
	public AStuInfoView(String tName) {
		this.tName = tName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ��ѧ����Ϣ������ǩ
		labelStuInfo = new JLabel("\u5B66\u751F\u4FE1\u606F\uFF1A");
		labelStuInfo.setFont(new Font("΢���ź�", Font.PLAIN, 25));
		labelStuInfo.setBounds(56, 45, 133, 34);
		contentPane.add(labelStuInfo);

		// ѧ����Ϣ��ʾ��
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(56, 104, 664, 219);
		txtShowStudentInfo = new JTextArea();
		txtShowStudentInfo.setBounds(56, 104, 664, 219);
		txtShowStudentInfo.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		contentPane.add(scroll);
		scroll.setViewportView(txtShowStudentInfo);

		// ����ѧ��Ϊ����ǩ
		labelNum = new JLabel("\u5BF9\u5B66\u53F7\u4E3A");
		labelNum.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelNum.setBounds(56, 362, 81, 21);
		contentPane.add(labelNum);

		// ѧ�������
		txtSno = new JTextField();
		txtSno.setBounds(138, 359, 143, 27);
		contentPane.add(txtSno);
		txtSno.setColumns(10);

		// ����ѧ����Ϣ�������޸ġ���ǩ
		labelChange = new JLabel("\u7684\u5B66\u751F\u4FE1\u606F\u4F5C\u4EE5\u4E0B\u4FEE\u6539\uFF1A");
		labelChange.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChange.setBounds(296, 362, 206, 21);
		contentPane.add(labelChange);

		// ����������ǩ
		labelName = new JLabel("\u59D3\u540D\uFF1A");
		labelName.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelName.setBounds(56, 414, 81, 21);
		contentPane.add(labelName);

		// �޸����������
		txtName = new JTextField();
		txtName.setBounds(112, 411, 96, 27);
		contentPane.add(txtName);
		txtName.setColumns(10);

		// ���Ա𡱱�ǩ
		labelSex = new JLabel("\u6027\u522B\uFF1A");
		labelSex.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelSex.setBounds(225, 414, 81, 21);
		contentPane.add(labelSex);

		// �޸��Ա�ѡ���
		cbbxSex = new JComboBox();
		cbbxSex.setBounds(273, 411, 41, 27);
		cbbxSex.addItem("��");
		cbbxSex.addItem("Ů");
		contentPane.add(cbbxSex);

		// ����������ǩ
		labelCountry = new JLabel("\u56FD\u7C4D\uFF1A");
		labelCountry.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelCountry.setBounds(358, 414, 81, 21);
		contentPane.add(labelCountry);

		// �޸Ĺ��������
		txtCountry = new JTextField();
		txtCountry.setColumns(10);
		txtCountry.setBounds(408, 411, 96, 27);
		contentPane.add(txtCountry);

		// �����塱��ǩ
		labelNation = new JLabel("\u6C11\u65CF\uFF1A");
		labelNation.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelNation.setBounds(542, 414, 81, 21);
		contentPane.add(labelNation);

		// ��ȷ��ѡ�񡱰�ť
		btnEnsureSelect = new JButton("\u786E\u8BA4\u9009\u62E9");
		btnEnsureSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStudentInfo();
				logContent = "";
			}
		});
		btnEnsureSelect.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnEnsureSelect.setBounds(547, 358, 123, 29);
		contentPane.add(btnEnsureSelect);

		// �޸����������
		txtNation = new JTextField();
		txtNation.setColumns(10);
		txtNation.setBounds(591, 411, 96, 27);
		contentPane.add(txtNation);

		// ��רҵ����ǩ
		labelMajor = new JLabel("\u4E13\u4E1A\uFF1A");
		labelMajor.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelMajor.setBounds(56, 466, 81, 21);
		contentPane.add(labelMajor);

		// �޸�רҵ�����
		txtMajor = new JTextField();
		txtMajor.setBounds(112, 463, 267, 27);
		contentPane.add(txtMajor);
		txtMajor.setColumns(10);

		// "��ѧ���"��ǩ
		labelYear = new JLabel("\u5165\u5B66\u5E74\u4EFD\uFF1A");
		labelYear.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelYear.setBounds(394, 466, 96, 21);
		contentPane.add(labelYear);

		// �޸���ѧ��������
		txtStartYear = new JTextField();
		txtStartYear.setColumns(10);
		txtStartYear.setBounds(481, 463, 96, 27);
		contentPane.add(txtStartYear);

		// ȷ���޸İ�ť
		btnEnsureChange = new JButton("\u786E\u8BA4\u4FEE\u6539");
		btnEnsureChange.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnEnsureChange.setBounds(615, 482, 110, 29);
		contentPane.add(btnEnsureChange);
		btnEnsureChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStudentInfo();
				if (isNormal) {
					// ���ϵͳ�޸���־
					addSysLog();
				}
			}
		});

		// ���������ڹر�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// ����ѧ����Student�����ҵ���ѧ����Ϣ������ʾ
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
					// ��¼�޸�ǰ��ѧ����Ϣ
					srcStudent = new Student(student);
				} else {
					// ��¼�޸ĺ��ѧ����Ϣ
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

	// ��ѧ���������Ϣˢ��
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
			// ˢ�½���
			showStudentInfo();
			JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// ���ͳ����쳣
			isNormal = false;
			JOptionPane.showMessageDialog(null, "��ѧ����������������������ȷ����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// ����޸���־
	public void addSysLog() {

		// �޸��Ժ��¼����
		// ����
		if (!srcStudent.getName().equals(desStudent.getName())) {
			logContent += tName + "��ѧ��" + "\"" + srcStudent.getName() + "\"" + "��������" + "\"" + srcStudent.getName()
					+ "\"" + "��Ϊ" + "\"" + desStudent.getName() + "\"" + "\n";
		}

		// �Ա�
		if (!srcStudent.getSex().equals(desStudent.getSex())) {
			logContent += tName + "��ѧ��" + "\"" + srcStudent.getName() + "\"" + "���Ա��" + "\"" + srcStudent.getSex()
					+ "\"" + "��Ϊ" + "\"" + desStudent.getSex() + "\"" + "\n";
		}

		// ����
		if (!srcStudent.getCountry().equals(desStudent.getCountry())) {
			logContent += tName + "��ѧ��" + "\"" + srcStudent.getName() + "\"" + "�Ĺ�����" + "\"" + srcStudent.getCountry()
					+ "\"" + "��Ϊ" + "\"" + desStudent.getCountry() + "\"" + "\n";
		}

		// ����
		if (!srcStudent.getNation().equals(desStudent.getNation())) {
			logContent += tName + "��ѧ��" + "\"" + srcStudent.getName() + "\"" + "�������" + "\"" + srcStudent.getNation()
					+ "\"" + "��Ϊ" + "\"" + desStudent.getNation() + "\"" + "\n";
		}

		// רҵ
		if (!srcStudent.getMajor().equals(desStudent.getMajor())) {
			logContent += tName + "��ѧ��" + "\"" + srcStudent.getName() + "\"" + "��רҵ��" + "\"" + srcStudent.getMajor()
					+ "\"" + "��Ϊ" + "\"" + desStudent.getMajor() + "\"" + "\n";
		}

		// ��ѧ���
		if (srcStudent.getStartYear() != desStudent.getStartYear()) {
			logContent += tName + "��ѧ��" + "\"" + srcStudent.getName() + "\"" + "����ѧ��ݴ�" + "\""
					+ srcStudent.getStartYear() + "\"" + "��Ϊ" + "\"" + desStudent.getStartYear() + "\"" + "\n";
		}

		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // �ֻص�δ�޸�״̬
			showStudentInfo();
		}
	}
}
