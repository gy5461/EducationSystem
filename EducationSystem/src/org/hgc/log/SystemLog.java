package org.hgc.log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SystemLog implements Loggable {
	String fileName = "SystemLog.log"; // �ڹ���Ŀ¼FinalWork��

	/**
	 * ���һ����־
	 * 
	 * @param type        ��־����
	 * @param itemContent ��־������
	 */
	@Override
	public void addLog(TYPE type, String logContent) {
		String itemContent = "";

		// ��ʼ����־��
		// �ָ���
		String splitLine = "*************************************************************************" + "\n";
		itemContent += splitLine;

		// ʱ��
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		itemContent += df.format(System.currentTimeMillis()) + "\n";

		// ����
		itemContent += type.toString() + "\n";

		// ����
		itemContent += logContent + '\n';

		// �ָ���
		itemContent += splitLine;

		// ����־���ۼ�д�뱾���ļ�"SystemLog.log"
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));// Ϊtrue��ʾ׷��
			out.write(itemContent);
		} catch (IOException e) {
			System.err.println("ϵͳ��־�ļ������־ʧ�ܣ�");
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return ������־�ļ��е���������
	 */
	@Override
	public String readLog() {
		// ������־��أ�����ļ�Ϊ�գ��򷵻�null
		BufferedReader in = null;
		// ƴ���ַ���ʹ��StringBuffer��
		StringBuffer sbLog = new StringBuffer();
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
			String data = "";
			while ((data = in.readLine()) != null) {
				sbLog.append(data + "\n");
			}
		} catch (IOException e) {
			System.err.println("��ȡϵͳ��־ʧ�ܣ�");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sbLog.toString();
	}

	/**
	 * �����־�ļ�
	 */
	@Override
	public void emptyLog() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, false)));// Ϊtrue��ʾ׷��
			out.write("");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
