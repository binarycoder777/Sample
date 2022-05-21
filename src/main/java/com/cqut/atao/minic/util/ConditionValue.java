package com.cqut.atao.minic.util;


import java.util.ArrayList;
import java.util.Iterator;

public class ConditionValue {
	private ArrayList<QTInfo> trueChain = new ArrayList<QTInfo>();
	private ArrayList<QTInfo> falseChain = new ArrayList<QTInfo>();
	
	public void mergeTrue(QTInfo qtTrue){
		trueChain.add(qtTrue);
	}
	public void mergeFalse(QTInfo qtFalse){
		falseChain.add(qtFalse);
	}
	
	public void mergeTrue(ConditionValue cValue1){
		trueChain.addAll(cValue1.trueChain);
		
	}
	
	public void mergeFalse(ConditionValue cValue1){
		falseChain.addAll(cValue1.falseChain);
		
	}
	
	public void FalseMergeTrue(ConditionValue cValue1) {
		falseChain.addAll(cValue1.trueChain);
	}
	
	public void TrueMergeFalse(ConditionValue cValue1) {
		trueChain.addAll(cValue1.falseChain);
	}
	
	public void backpatchTrueChain(int result){
		Iterator<QTInfo> itr = trueChain.iterator();
		while(itr.hasNext()){
			itr.next().setResult(result);
		}
	}
	public void backpatchFalseChain(int result){
		Iterator<QTInfo> itr = falseChain.iterator();
		while(itr.hasNext()){
			itr.next().setResult(result);
		}
	}
}
