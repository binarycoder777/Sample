package com.cqut.atao.toMFA.entity;


/**
 * @author atao
 * @version 1.0.0
 * @ClassName Priority.java
 * @Description ��������ȼ�ʵ��
 * @createTime 2022��05��22�� 08:54:00
 */
public class Priority {
	// �����
	private String o1;
	// �����
	private String o2;
	// 1 ����o1>o2
	private int flag;

	public Priority(String o1, String o2, int flag) {
		this.o1 = o1;
		this.o2 = o2;
		this.flag = flag;
	}

	public String getO1() {
		return o1;
	}

	public void setO1(String o1) {
		this.o1 = o1;
	}

	public String getO2() {
		return o2;
	}

	public void setO2(String o2) {
		this.o2 = o2;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
