package org.hgc.object;

/**
 * 教师
 */
public class Teacher {
	private int id; // 教师号
	private String name; // 姓名

	// 使用属性构造
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
