package org.hgc.object;

import java.io.*;

/**
 * ѧ��
 */
public class Student {
	private int id; // ѧ��
	private String name; // ����
	private String sex; // �Ա�
	private String nation; // ����
	private String country; // ����
	private String major; // רҵ
	private int startYear; // ��ѧ���

	// ʹ�����Թ���
	public Student(int id, String name, String sex, String nation, String country, String major, int startYear) {
		this.id = id;
		this.name = new String(name);
		this.sex = sex;
		this.nation = nation;
		this.country = country;
		this.major = major;
		this.startYear = startYear;
	}

	// ʹ�ö�����
	public Student(Student student) {
		this.id = student.id;
		this.name = new String(student.name);
		this.sex = student.sex;
		this.nation = student.nation;
		this.country = student.country;
		this.major = student.major;
		this.startYear = student.startYear;
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

	// sex
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	// nation
	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNation() {
		return this.nation;
	}

	// country
	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return this.country;
	}

	// major
	public void setMajor(String major) {
		this.major = major;
	}

	public String getMajor() {
		return this.major;
	}

	// starYear
	public void setStarYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartYear() {
		return this.startYear;
	}

	public static String getHeader() {
		return "         " + "ѧ��" + "\t" + "����" + "\t" + "�Ա�" + "\t" + "����" + "\t" + "����" + "\t" + "רҵ" + "\t"
				+ "��ѧ���\n";
	}

	public String toString() {
		String str = "         " + id + "\t" + name + "\t" + sex + "\t" + nation + "\t" + country + "\t" + major + "\t"
				+ startYear;
		return str;
	}
}
