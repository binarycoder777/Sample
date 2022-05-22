package com.cqut.atao.minic.util;

//定义四元式的信息
public class QTInfo {

	public static int size = 0;// 四元式全局个数
	private int innerId;// 当前四元式ID
	private String operator;
	private String arg1;
	private String arg2;
	private String result;

	public QTInfo(String operator, String arg1, String arg2, String result) {
		super();
		this.innerId = ++size;
		this.operator = operator;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.result = result;
	}

	public QTInfo(String operator, String arg1, String arg2, int result) {
		this(operator, arg1, arg2, result + "");
	}

	public String getOperator() {
		return this.operator;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setResult(int result) {
		this.result = "" + result;
	}

	public String getResult() {
		return this.result;
	}

	public void setInnerId(int innerID) {
		this.innerId = innerID;
	}

	public int getInnerIdSeqen() {
		return size;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return this.innerId + ":\t(" + this.operator + ",\t" + this.arg1
				+ ",\t" + this.arg2 + ",\t" + this.result + "\t)\n";
	}
}
