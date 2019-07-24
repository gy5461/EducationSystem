package org.hgc.log;

interface Loggable {
	/**
	 * 日志信息的三种类型 信息、警告、错误
	 */
	enum TYPE {
		INFOMATION, WARNING, ERROR
	};

	/**
	 * 添加一条日志
	 * 
	 * @param type       日志类型
	 * @param logContent 日志的内容
	 */
	void addLog(Loggable.TYPE type, String logContent);

	/**
	 * 
	 * @return 读出日志文件中的所有内容
	 */
	String readLog();

	/**
	 * 清空日志文件
	 */
	void emptyLog();
}
