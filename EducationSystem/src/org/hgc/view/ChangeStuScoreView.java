package org.hgc.view;

//�޸�ĳ�ſγ�ĳ��ѧ���ĳɼ�����

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
	boolean flag = false; // ����Ƿ��޸���Ϣ
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

		// ���ÿγ�ѧ���ɼ��������ǩ
		labelScore = new JLabel("\u8BE5\u8BFE\u7A0B\u5B66\u751F\u6210\u7EE9\u60C5\u51B5\uFF1A");
		labelScore.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelScore.setBounds(59, 80, 194, 21);
		contentPane.add(labelScore);

		// �ÿγ�ѧ���ɼ������ʾ��
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(59, 135, 665, 233);
		txtShow = new JTextArea();
		txtShow.setBounds(59, 135, 665, 233);
		contentPane.add(scroll);
		txtShow.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		scroll.setViewportView(txtShow);

		// ���޸�ѧ��Ϊ����ǩ
		labelChangeNum = new JLabel("\u4FEE\u6539\u5B66\u53F7\u4E3A");
		labelChangeNum.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelChangeNum.setBounds(59, 411, 90, 21);
		contentPane.add(labelChangeNum);

		// Ҫ�޸ĵ�ѡ�μ�¼��������
		txtSno = new JTextField();
		txtSno.setBounds(150, 408, 90, 27);
		contentPane.add(txtSno);
		txtSno.setColumns(10);

		// ����ѡ�μ�¼��ѧ���ɼ�Ϊ����ǩ
		labelTip = new JLabel("\u7684\u9009\u8BFE\u8BB0\u5F55\u7684\u5B66\u751F\u6210\u7EE9\u4E3A");
		labelTip.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		labelTip.setBounds(245, 411, 220, 21);
		contentPane.add(labelTip);

		// �޸ĳɼ������
		txtGrade = new JTextField();
		txtGrade.setBounds(457, 410, 60, 27);
		contentPane.add(txtGrade);
		txtGrade.setColumns(10);

		// �ɼ���ʽ���󾯸��ǩ
		labelWarn = new JLabel("");
		labelWarn.setForeground(Color.RED);
		labelWarn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		labelWarn.setBounds(211, 462, 335, 21);
		contentPane.add(labelWarn);

		// ȷ���ύ�޸İ�ť
		btnSubmit = new JButton("\u786E\u8BA4\u63D0\u4EA4\u4FEE\u6539");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showScore();
				changeGrade();
				addSysLog();
			}
		});
		btnSubmit.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		btnSubmit.setBounds(563, 439, 153, 29);
		contentPane.add(btnSubmit);
		showScore();
		logContent = "";

		// ���������ڹر�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// չʾ�ÿγ̵�ȫ��ѧ����Ϣ
	public void showScore() {
		Database db = Database.getDatabase();
		String sql = "select sName,SC.sno,SC.cno,cName,SC.grade from SC,Student,Course where SC.sno = Student.sno AND SC.cno = Course.cno AND Course.cno = "
				+ Cno;
		ResultSet rSet = db.executeQuery(sql);
		String str = "              ����" + "\t" + "ѧ��" + "\t" + "�γ̺�" + "\t" + "�γ���" + "\t" + "�ɼ�" + "\n";

		try {
			// ��ʼ��ѧ���б�
			Snos = new ArrayList<Integer>();
			while (rSet.next()) {
				sName = rSet.getString("sName");
				int Sno = rSet.getInt("SC.sno");
				int Cno = rSet.getInt("SC.cno");
				String cName = rSet.getString("cName");
				int grade = rSet.getInt("SC.grade");
				str += "              " + sName + "\t" + Sno + "\t" + Cno + "\t" + cName + "\t" + grade + "\n";
				// ���ѧ�ŵ�ѧ���б�
				Snos.add(Sno);
				String sSno = Sno + "";
				if (sSno.equals(txtSno.getText())) {
					// ��¼Ҫ�ĵ�ѧ������
					sChangeName = sName;
					if (flag == false) {
						// ��¼�޸�ǰ��ѧ���ɼ�
						srcStudentScore = grade;
					} else {
						// ��¼�޸ĺ��ѧ���ɼ�
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

	// �޸�ĳλͬѧ�ĳɼ�
	public void changeGrade() {
		Database db = Database.getDatabase();
		try {
			// �޸ĳɼ�ǰ����ѧ���Ƿ�ѡ�˸��ſΣ����������ʾ
			int Sno = Integer.parseInt(txtSno.getText());
			// ���ѧ���Ƿ���ѡ�˸��ſε�ѧ���б���
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
					// ��Χ��ȷ
				} else {
					throw new Exception("�ɼ����ڹ涨��Χ֮��");
				}
				String sql = "update SC set grade = " + Integer.parseInt(txtGrade.getText()) + " where sno = "
						+ Integer.parseInt(txtSno.getText()) + " AND cno = " + Cno;
				int result = db.executeUpdate(sql);
				if (result == 1) {
					flag = true; // �޸Ĺ�
					// ˢ����
					showScore();
					JOptionPane.showMessageDialog(null, "�ɼ��޸ĳɹ�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					if (exist) {
						labelWarn.setText("");
					}
				} else {
					// �޸�ʧ��
					JOptionPane.showMessageDialog(null, "�ɼ��޸�ʧ�ܣ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				// ����ѧ�Ŷ�Ӧѧ��δѡ���ſ�
				JOptionPane.showMessageDialog(null, "����δѡ�˿Σ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			// Ӧ����int�͵ĳɼ�
			labelWarn.setText("�ɼ��������������������ȷ����(0~100)");
			exist = true;
		}
	}

	// ����޸���־
	public void addSysLog() {
		// �޸��Ժ��¼����

		// �ɼ�
		if (srcStudentScore != desStudentScore) {
			logContent += tName + "��ѧ��" + "\"" + sChangeName + "\"" + "�ĳɼ���" + "\"" + srcStudentScore + "\"" + "��Ϊ"
					+ "\"" + desStudentScore + "\"" + "\n";
		}

		if (!logContent.equals("")) {
			SystemLog systemLog = new SystemLog();
			systemLog.addLog(SystemLog.TYPE.INFOMATION, logContent);
			logContent = "";
			flag = false; // �ֻص�δ�޸�״̬
			showScore();
		}
	}
}
