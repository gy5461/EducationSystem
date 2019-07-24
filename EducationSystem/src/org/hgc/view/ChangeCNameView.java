package org.hgc.view;

//��ʦ�޸�ָ���γ����ƽ���

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.log.SystemLog;
import org.hgc.object.Course;

import java.sql.*;

public class ChangeCNameView extends JFrame {

	private JPanel contentPane;
	private JTextField txtCourseName;
	private JTextArea txtChangeLog;
	private JButton btnEnsureSubmit;
	private JTextArea txtSrcCourseInfo;
	private JLabel labelChangeSitu;
	private JLabel labelThisInfo;
	private JLabel labelChangeName;

	private String logContent;
	private String tName;
	private Course srcCourse;
	private Course desCourse;
	boolean flag = false; // ����Ƿ��޸���Ϣ

	/**
	 * Create the frame. �������γ̺�
	 */
	public ChangeCNameView(int Cno, String tName) {
		this.tName = tName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// �����γ���Ϣ������ǩ
		labelThisInfo = new JLabel("\u672C\u8BFE\u7A0B\u4FE1\u606F\uFF1A");
		labelThisInfo.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelThisInfo.setBounds(70, 106, 139, 21);
		contentPane.add(labelThisInfo);

		// ���γ���Ϣ��ʾ��
		txtSrcCourseInfo = new JTextArea();
		txtSrcCourseInfo.setBounds(70, 158, 658, 65);
		txtSrcCourseInfo.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		contentPane.add(txtSrcCourseInfo);

		// ���޸Ŀγ�����Ϊ����ǩ
		labelChangeName = new JLabel("\u4FEE\u6539\u8BFE\u7A0B\u540D\u79F0\u4E3A");
		labelChangeName.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChangeName.setBounds(70, 272, 139, 21);
		contentPane.add(labelChangeName);

		// �޸Ŀγ����������
		txtCourseName = new JTextField();
		txtCourseName.setBounds(203, 269, 187, 27);
		contentPane.add(txtCourseName);
		txtCourseName.setColumns(10);

		// ���޸����������ǩ
		labelChangeSitu = new JLabel("\u4FEE\u6539\u60C5\u51B5\uFF1A");
		labelChangeSitu.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChangeSitu.setBounds(70, 349, 95, 21);
		contentPane.add(labelChangeSitu);

		// �޸ĺ�γ������ʾ��
		txtChangeLog = new JTextArea();
		txtChangeLog.setBounds(70, 404, 658, 65);
		txtChangeLog.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		contentPane.add(txtChangeLog);

		// ȷ���ύ��ť
		btnEnsureSubmit = new JButton("\u786E\u8BA4\u63D0\u4EA4");
		btnEnsureSubmit.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnEnsureSubmit.setBounds(435, 268, 123, 29);
		contentPane.add(btnEnsureSubmit);
		btnEnsureSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// �޸Ŀγ�����
				setCourseName(Cno);
				// ��ʾ�޸ĺ����Ϣ
				showCourseInfo(Cno, txtSrcCourseInfo);
				// ���ϵͳ�޸���־
				showSysLog(txtChangeLog, logContent);
				// ��ʾ�޸ĺ����Ϣ
				showCourseInfo(Cno, txtSrcCourseInfo);
			}

		});
		// ���������ڹر�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ��ʾ�γ���Ϣ
		showCourseInfo(Cno, txtSrcCourseInfo);
		logContent = "";
	}

	// ���ݿγ̺���ʾ�γ���Ϣ
	public void showCourseInfo(int Cno, JTextArea txtCourseInfo) {
		Database db = Database.getDatabase();
		String sql = "select * from course where cno = " + Cno;
		ResultSet rSet = db.executeQuery(sql);
		try {
			while (rSet.next()) {
				String str = "       " + "�γ̺�" + "\t" + "�γ���" + "\t" + "�ον�ʦ" + "\t" + "��ʱ" + "\t" + "ѡ������" + "\t"
						+ "ѧ��\n";
				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				if (flag == false) {
					// ��¼�޸�ǰ�Ŀγ���Ϣ
					srcCourse = new Course(course);
				} else {
					// ��¼�޸ĺ�Ŀγ���Ϣ
					desCourse = new Course(course);
				}
				str += course;
				txtCourseInfo.setText(str);
				txtCourseName.setText(course.getName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ���γ̱������Ϣˢ��
	public void setCourseName(int Cno) {
		Database db = Database.getDatabase();
		String sql = "Update course" + " set  cName = ?" + "	where cno = " + Cno + ";";
		String[] s = new String[1];
		s[0] = txtCourseName.getText();
		flag = true;
		db.executeUpdate(sql, s);
	}

	// ����޸���־
	public void showSysLog(JTextArea txtChangeLog, String logContent) {
		// �γ���
		if (!(srcCourse.getName().equals(desCourse.getName()))) {
			logContent += tName + "���γ�" + "\"" + srcCourse.getName() + "\"" + "�Ŀγ�����" + "\"" + srcCourse.getName()
					+ "\"" + "��Ϊ" + "\"" + desCourse.getName() + "\"" + "\n";
		}
		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			txtChangeLog.setText(logContent);
			logContent = "";
			flag = false; // �ֻص�δ�޸�״̬
		}
	}
}
