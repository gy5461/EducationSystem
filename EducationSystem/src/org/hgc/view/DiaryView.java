//日志内容界面
package org.hgc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JButton;
import org.hgc.log.*;

public class DiaryView extends JFrame {

	private JPanel contentPane;
	private SystemLog sysLog;

	JLabel labelContent; // “日志文件内容：”标签

	JTextArea diaryContent; // 日志文件内容显示处

	JButton btnDelete; // 清除日志文件内容按钮

	/**
	 * Create the frame.
	 */
	public DiaryView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 115, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// “日志文件内容：”标签
		labelContent = new JLabel("\u65E5\u5FD7\u6587\u4EF6\u5185\u5BB9\uFF1A");
		labelContent.setFont(new Font("微软雅黑", Font.PLAIN, 26));
		labelContent.setBounds(90, 39, 182, 60);
		contentPane.add(labelContent);

		// 日志文件内容显示处
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setBounds(94, 127, 612, 323);
		diaryContent = new JTextArea();
		diaryContent.setText("\u4F60\u597D");
		diaryContent.setBounds(94, 127, 612, 323);
		diaryContent.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		scroll.setViewportView(diaryContent);

		// 清除日志文件内容按钮
		btnDelete = new JButton("\u6E05\u9664\u5168\u90E8");
		btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnDelete.setBounds(576, 478, 130, 40);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sysLog.emptyLog();
				// 刷新界面
				showSysLog();
			}

		});

		// 避免主窗口关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 显示日志文件内容
		showSysLog();
	}

	// 显示日志文件内容
	public void showSysLog() {
		sysLog = new SystemLog();
		diaryContent.setText(sysLog.readLog());
	}
}
