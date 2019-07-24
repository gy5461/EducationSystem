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
	String fileName = "SystemLog.log"; // 在工程目录FinalWork下

	/**
	 * 添加一条日志
	 * 
	 * @param type        日志类型
	 * @param itemContent 日志的内容
	 */
	@Override
	public void addLog(TYPE type, String logContent) {
		String itemContent = "";

		// 初始化日志项
		// 分隔线
		String splitLine = "*************************************************************************" + "\n";
		itemContent += splitLine;

		// 时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		itemContent += df.format(System.currentTimeMillis()) + "\n";

		// 类型
		itemContent += type.toString() + "\n";

		// 内容
		itemContent += logContent + '\n';

		// 分隔线
		itemContent += splitLine;

		// 将日志项累加写入本地文件"SystemLog.log"
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));// 为true表示追加
			out.write(itemContent);
		} catch (IOException e) {
			System.err.println("系统日志文件添加日志失败！");
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
	 * @return 读出日志文件中的所有内容
	 */
	@Override
	public String readLog() {
		// 构建日志项返回，如果文件为空，则返回null
		BufferedReader in = null;
		// 拼接字符串使用StringBuffer类
		StringBuffer sbLog = new StringBuffer();
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
			String data = "";
			while ((data = in.readLine()) != null) {
				sbLog.append(data + "\n");
			}
		} catch (IOException e) {
			System.err.println("读取系统日志失败！");
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
	 * 清空日志文件
	 */
	@Override
	public void emptyLog() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, false)));// 为true表示追加
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
