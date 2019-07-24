package org.hgc.log;

interface Loggable {
	/**
	 * ��־��Ϣ���������� ��Ϣ�����桢����
	 */
	enum TYPE {
		INFOMATION, WARNING, ERROR
	};

	/**
	 * ���һ����־
	 * 
	 * @param type       ��־����
	 * @param logContent ��־������
	 */
	void addLog(Loggable.TYPE type, String logContent);

	/**
	 * 
	 * @return ������־�ļ��е���������
	 */
	String readLog();

	/**
	 * �����־�ļ�
	 */
	void emptyLog();
}
