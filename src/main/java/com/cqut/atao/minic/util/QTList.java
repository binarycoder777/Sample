package com.cqut.atao.minic.util;


import java.util.ArrayList;
import java.util.Iterator;

public class QTList {
	public ArrayList<QTInfo> QTList = new ArrayList<QTInfo>();
	public static boolean flag = true;

	public void addQTInfo(QTInfo info) {
		QTList.add(info);
	}

	public void addQTInfo(int index, QTInfo info) {
		QTList.add(index, info);
	}

	public QTInfo get(int index) {
		return (QTInfo) QTList.get(index);
	}
	
	public void set(int index, QTInfo qi) {
		QTList.set(index, qi);	
    }

	public QTInfo remove(int index) {
		return QTList.remove(index - 1);
	}

	public void clear() {
		QTList.clear();
		QTInfo.size = 0;
	}

	public String printQTTable() {
		String res = "";
		Iterator<QTInfo> itr = QTList.iterator();
		try {
			while (itr.hasNext()) {
				QTInfo tmp = (QTInfo) itr.next();
				System.out.println(tmp.toString());
				res += tmp.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
