package org.hgc.object;

/**
 * ��ʦ
 */
public class Teacher {
	private int id; // ��ʦ��
	private String name; // ����

	// ʹ�����Թ���
	public Teacher(int id, String name) {
		this.id = id;
		this.name = new String(name);
	}

	// set&get

	// id
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	// name
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
