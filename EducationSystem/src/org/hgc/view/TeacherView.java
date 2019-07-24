package org.hgc.view;

//��ͨ��ʦ��¼����ҳ

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hgc.db.*;
import org.hgc.object.Course;

import java.sql.*;
import java.util.ArrayList;

public class TeacherView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea txtTeachInfo;
	private JComboBox comboBox;
	private JLabel labelCourse;
	private JLabel labelCourNum;
	private JLabel labelChooseType;
	private JButton btnRun;
	private JButton btnReturn;
	private ArrayList<Integer> Cnos;

	/**
	 * Create the frame.
	 */
	public TeacherView(int tno) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// �������ν̿γ���Ϣ����ǩ
		labelCourse = new JLabel("\u672C\u4EBA\u4EFB\u6559\u8BFE\u7A0B\u4FE1\u606F\uFF1A");
		labelCourse.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelCourse.setBounds(44, 82, 185, 21);
		contentPane.add(labelCourse);

		// �γ���Ϣ��ʾ��
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(44, 145, 695, 203);
		txtTeachInfo = new JTextArea();
		txtTeachInfo.setBounds(44, 145, 695, 203);
		txtTeachInfo.setFont(new Font("΢���ź�", Font.PLAIN, 17));
		contentPane.add(scroll);
		scroll.setViewportView(txtTeachInfo);

		// ����������γ̺š���ǩ
		labelCourNum = new JLabel("\u8F93\u5165\u64CD\u4F5C\u8BFE\u7A0B\u53F7\uFF1A");
		labelCourNum.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelCourNum.setBounds(44, 392, 167, 21);
		contentPane.add(labelCourNum);

		// �����γ̺������
		textField = new JTextField();
		textField.setBounds(190, 389, 159, 27);
		contentPane.add(textField);
		textField.setColumns(10);

		// ��ѡ��������͡���ǩ
		labelChooseType = new JLabel("\u9009\u62E9\u64CD\u4F5C\u7C7B\u578B:");
		labelChooseType.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChooseType.setBounds(65, 443, 131, 21);
		contentPane.add(labelChooseType);

		// ��������ѡ���
		comboBox = new JComboBox();
		comboBox.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		comboBox.setBounds(190, 440, 159, 27);
		contentPane.add(comboBox);
		comboBox.addItem("�޸Ŀγ�����");
		comboBox.addItem("�鿴�ÿγ�ѧ���ɼ����");

		// ���в�����ť
		btnRun = new JButton("\u8FDB\u884C\u64CD\u4F5C");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int Cno = Integer.parseInt(textField.getText());
					boolean flag = false; // ���cNo�Ƿ����ڸ���ʦ���̵Ŀγ̵Ŀγ̺�
					// �����ж�cNo�Ƿ����ڸ���ʦ���̵Ŀγ̵Ŀγ̺�
					for (int i : Cnos) {
						if (i == Cno) {
							flag = true;
							break;
						}
					}
					if (flag == true) {
						if (comboBox.getSelectedIndex() == 0) {
							ChangeCNameView cNameView = new ChangeCNameView(Cno, getTeacherName(tno));
							cNameView.setVisible(true);
						} else if (comboBox.getSelectedIndex() == 1) {
							ChangeStuScoreView cStuScoreView = new ChangeStuScoreView(Cno, getTeacherName(tno));
							cStuScoreView.setVisible(true);
						}
					} else {
						// ������ʾû�в���Ȩ��
						JOptionPane.showMessageDialog(null, "��û�жԸÿγ̵Ĳ���Ȩ��!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					// ������ʾδ����γ̺�
					JOptionPane.showMessageDialog(null, "δ����γ̺Ż�γ̺��������!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRun.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnRun.setBounds(455, 392, 142, 72);
		contentPane.add(btnRun);

		// ���ذ�ť
		btnReturn = new JButton("\u8FD4\u56DE");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnReturn.setBounds(657, -5, 123, 41);
		contentPane.add(btnReturn);

		// ��ʾ�����ν���Ϣ
		showTeachInfo(tno);
	}

	// ��ʾ�����ν���Ϣ
	public void showTeachInfo(int tno) {
		// ������ʦid�ڿγ̱����ҵ�����ʦ�ν̵����пγ̲���ʾ
		Database db = Database.getDatabase();
		String sql = "select * from course where cTeacherId = " + tno;
		ResultSet rSet = db.executeQuery(sql);
		try {
			String str = Course.getHeader();
			// ��ʼ���γ̺�����
			Cnos = new ArrayList<Integer>();
			while (rSet.next()) {
				Course course = new Course(rSet.getInt("cno"), rSet.getString("cName"), rSet.getInt("cTeacherId"),
						rSet.getInt("cPeriod"), rSet.getString("cSelectProperty"), rSet.getDouble("cCredit"));
				str += course + "\n";
				// ������ʦ���ڿγ̵Ŀγ̺�д��γ̺�����
				Cnos.add(course.getId());
			}
			txtTeachInfo.setText(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��teacherId�ڽ�ʦ�����ҵ�teacherName
	public String getTeacherName(int tno) {
		Database db = Database.getDatabase();
		String sql = "select * from teacher where tno = " + tno;
		ResultSet rSet = db.executeQuery(sql);
		String str = null;
		try {
			while (rSet.next()) {
				str = rSet.getString("tName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
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
