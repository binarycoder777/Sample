package com.cqut.atao.minic.miniC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test3 {

//	public static String prefix = "D:\\编译原理\\参考项目\\java-compiler\\src\\main\\java\\com\\hc\\example\\minic\\" ;

	public static void main(String args [])
	  {
	    Scanner in=new Scanner(System.in);
	    System.out.println("输入文件:");
	    String name=in.next();
	    File file = new File( name);
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(file);
			MiniCV1 xx = new MiniCV1(fstream);
			xx.Start();

			// 打印 中间代码
			xx.qtList.printQTTable();
		} catch (FileNotFoundException e1) {
			//
			e1.printStackTrace();
		}
		catch (Exception e)
	    {
	        System.out.println("Oops.");
	        System.out.println(e.getMessage());
	    }
		in.close();
	  }

}
