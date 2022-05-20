package com.cqut.atao.minic.util;

/**
 * 变量名产生器
 * 
 */
public class VariableNameGenerator {
	
	private static final String VAR_PREFIX = "T"; // 前缀
	private static int sequenceId = 0;// 序号 T1、T2、T3...
	
	public static String genVariableName() {
		++sequenceId;
		return VAR_PREFIX + sequenceId;
	}

	public void clear() {
		// TODO Auto-generated method stub
		sequenceId = 0;
	}
}
