package com.cqut.atao.minic.miniC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class test1 {

	public static void main(String args [])
	  {
	    Scanner in=new Scanner(System.in);
	    System.out.println("Reading from file:");
	    String name=in.nextLine();
	    try
	    {
	      FileReader fr=new FileReader(name);
	      BufferedReader br=new BufferedReader(fr);
	      SimpleCharStream input_stream=new SimpleCharStream(br);
	      MiniCTokenManager tm=new MiniCTokenManager(input_stream);
	      Token token=null;
	      token=tm.getNextToken();
	      while(token.kind!=0)
	      {
	        System.out.println(token.kind+" "+token.image);
	        token=tm.getNextToken();
	      }
	    }
	    catch (Exception e)
	    {
	      System.out.println("Oops.");
	      System.out.println(e.getMessage());
	    }
	    in.close();
	  }


}
