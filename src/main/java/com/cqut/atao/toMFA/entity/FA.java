package com.cqut.atao.toMFA.entity;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName FA.java
 * @Description FAʵ��
 * @createTime 2022��05��23�� 08:54:00
 */
public class FA {

	//��״̬from->varch->to
	private int form;
	private String varch;
	private int to;

	public FA(int form, String varch, int to) {
		this.form = form;
		this.varch = varch;
		this.to = to;
	}

	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getVarch() {
		return varch;
	}

	public void setVarch(String varch) {
		this.varch = varch;
	}

	@Override
	public String toString() {
		return "NFA [form=" + form + ", varch=" + varch + ", to=" + to + "]";
	}
}
