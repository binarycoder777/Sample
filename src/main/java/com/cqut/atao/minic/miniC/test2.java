package com.cqut.atao.minic.miniC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test2 {

	public static String prefix = "D:\\编译原理\\参考项目\\java-compiler\\src\\main\\java\\com\\hc\\example\\minic\\" ;


	public static void main(String args [])
	  {
	    Scanner in=new Scanner(System.in);
	    System.out.println("Reading from file:");
	    String name=in.nextLine();
	    File file = new File(prefix + name);
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(file);
			MiniCV1 xx = new MiniCV1(fstream);
			SimpleNode n = xx.Start();
		    n.dump("");
		} catch (FileNotFoundException e1) {
			// TODO 自动生成的 catch 块
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