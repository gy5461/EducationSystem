package org.hgc.view;

//�����޸Ŀγ�ѧ�֡���ʱ��ѡ�����Խ���

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
	boolean flag = false; // ����Ƿ��޸���Ϣ
	boolean isNormal = true;
	private JLabel labelCourNum;
	private JLabel labelCourInfo;
	private JLabel labelChange;
	private JLabel labelCredit;
	private JLabel labelProperty;
	private JLabel labelPeriod;

	JButton btnEnsureSelect; // ȷ��ѡ��ť
	JButton btnEnsureChange; // ȷ���޸İ�ť

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

		// ���γ���Ϣ����ǩ
		labelCourInfo = new JLabel("\u8BFE\u7A0B\u4FE1\u606F");
		labelCourInfo.setFont(new Font("΢���ź�", Font.PLAIN, 25));
		labelCourInfo.setBounds(90, 61, 138, 34);
		contentPane.add(labelCourInfo);

		// �γ���Ϣ��ʾ��
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(90, 123, 610, 234);
		txtShowCourseInfo = new JTextArea();
		txtShowCourseInfo.setBounds(90, 123, 610, 234);
		txtShowCourseInfo.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		contentPane.add(scroll);
		scroll.setViewportView(txtShowCourseInfo);

		// ���Կγ̺�Ϊ����ǩ
		labelCourNum = new JLabel("\u5BF9\u8BFE\u7A0B\u53F7\u4E3A");
		labelCourNum.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelCourNum.setBounds(90, 391, 138, 21);
		contentPane.add(labelCourNum);

		// �γ̺������
		txtCno = new JTextField();
		txtCno.setBounds(187, 388, 96, 27);
		contentPane.add(txtCno);
		txtCno.setColumns(10);

		// ���Ŀγ��������޸ġ���ǩ
		labelChange = new JLabel("\u7684\u8BFE\u7A0B\u4F5C\u4EE5\u4E0B\u4FEE\u6539");
		labelChange.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChange.setBounds(298, 391, 161, 21);
		contentPane.add(labelChange);

		// ��ѧ�֣�����ǩ
		labelCredit = new JLabel("\u5B66\u5206\uFF1A");
		labelCredit.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelCredit.setBounds(90, 445, 81, 21);
		contentPane.add(labelCredit);

		// �޸�ѧ�������
		txtCredit = new JTextField();
		txtCredit.setBounds(141, 442, 96, 27);
		contentPane.add(txtCredit);
		txtCredit.setColumns(10);

		// ����ʱ������ǩ
		labelPeriod = new JLabel("\u8BFE\u65F6\uFF1A");
		labelPeriod.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelPeriod.setBounds(261, 445, 81, 21);
		contentPane.add(labelPeriod);

		// �޸Ŀ�ʱ�����
		txtPeriod = new JTextField();
		txtPeriod.setBounds(313, 442, 96, 27);
		contentPane.add(txtPeriod);
		txtPeriod.setColumns(10);

		// ��ѡ�����ԣ�����ǩ
		labelProperty = new JLabel("\u9009\u8BFE\u5C5E\u6027\uFF1A");
		labelProperty.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelProperty.setBounds(442, 445, 90, 21);
		contentPane.add(labelProperty);

		// ѡ������ѡ���
		cbbxSelectProperty = new JComboBox();
		cbbxSelectProperty.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		cbbxSelectProperty.setBounds(534, 442, 68, 27);
		contentPane.add(cbbxSelectProperty);
		cbbxSelectProperty.addItem("����");
		cbbxSelectProperty.addItem("ѡ��");

		// ȷ��ѡ��ť
		btnEnsureSelect = new JButton("\u786E\u8BA4\u9009\u62E9");
		btnEnsureSelect.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnEnsureSelect.setBounds(479, 387, 123, 29);
		this.add(btnEnsureSelect);
		btnEnsureSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ʾ�γ���Ϣ
				showCourseInfo();
				logContent = "";
			}

		});

		// ȷ���޸İ�ť
		btnEnsureChange = new JButton("\u786E\u8BA4\u4FEE\u6539 ");
		btnEnsureChange.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnEnsureChange.setBounds(577, 489, 123, 29);
		contentPane.add(btnEnsureChange);
		btnEnsureChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// �޸Ŀγ̱��е�����
				setCourseInfo();
				if (isNormal) {
					// ���ϵͳ�޸���־
					addSysLog();
				}
			}

		});
		// ���������ڹر�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// ���ݿγ̺���ʾ�γ���Ϣ
	public void showCourseInfo() {
		Database db = Database.getDatabase();
		String sql = "select * from course where cno = " + txtCno.getText();
		ResultSet rSet = db.executeQuery(sql);
		try {
			while (rSet.next()) {

				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				if (flag == false) {
					// ��¼�޸�ǰ�Ŀγ���Ϣ
					srcCourse = new Course(course);
				} else {
					// ��¼�޸ĺ�Ŀγ���Ϣ
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

	// ���γ̱������Ϣˢ��
	public void setCourseInfo() {
		Database db = Database.getDatabase();
		String sql = "Update course" + " set  cCredit = ?," + " cPeriod = ?," + " cSelectProperty = ?"
				+ "	where cno = " + txtCno.getText() + ";";
		String[] ins = new String[3];
		// ���double����
		try {
			double d = Double.parseDouble(txtCredit.getText());
			ins[0] = txtCredit.getText();
			// ���int����
			try {
				int i = Integer.parseInt(txtPeriod.getText());
				ins[1] = txtPeriod.getText();
				ins[2] = cbbxSelectProperty.getSelectedItem().toString();
				isNormal = true;
				flag = true;
				db.executeUpdate(sql, ins);
				// ˢ���棬��ȡdes
				showCourseInfo();
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				// Ӧ����int��
				isNormal = false;
				JOptionPane.showMessageDialog(null, "��ʱ�������������������ȷ����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			// Ӧ����double��
			isNormal = false;
			JOptionPane.showMessageDialog(null, "ѧ���������������������ȷ����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	// ����޸���־
	public void addSysLog() {

		// �޸��Ժ��¼����
		// ѧ��
		if (!(Math.abs(srcCourse.getCredit() - desCourse.getCredit()) < 1e-8)) {
			logContent += tName + "���γ�" + "\"" + srcCourse.getName() + "\"" + "��ѧ�ִ�" + "\"" + srcCourse.getCredit()
					+ "\"" + "��Ϊ" + "\"" + desCourse.getCredit() + "\"" + "\n";
		}

		// ѧʱ
		if (srcCourse.getPeriod() != desCourse.getPeriod()) {
			logContent += tName + "���γ�" + "\"" + srcCourse.getName() + "\"" + "��ѧʱ��" + "\"" + srcCourse.getPeriod()
					+ "\"" + "��Ϊ" + "\"" + desCourse.getPeriod() + "\"" + "\n";
		}

		// ѡ������
		if (!(srcCourse.getSelectProperty().equals(desCourse.getSelectProperty()))) {
			logContent += tName + "���γ�" + "\"" + srcCourse.getName() + "\"" + "��ѡ�����Դ�" + "\""
					+ srcCourse.getSelectProperty() + "\"" + "��Ϊ" + "\"" + desCourse.getSelectProperty() + "\"" + "\n";
		}

		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // �ֻص�δ�޸�״̬
			// ˢ����
			showCourseInfo();
		}
	}
}
