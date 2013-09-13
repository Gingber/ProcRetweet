/*****************************************************
 * 
 * This is a class of reading txt-file. 
 *
 *****************************************************
 * 
 * @author: Ginger
 * @Date: Friday 03-29 2013
 *
 *****************************************************
 */

package com.iie.twitter;

import java.util.Vector;
import java.io.*;

public class ReadTxtFile {
	
	String file = null;
	
	public ReadTxtFile(String file) {
		
		this.file = file;
	}

	public  Vector<String> read() {
		
		Vector<String> res = new Vector<String>(10);
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"), 5*1024*1024);  // 用5M的缓冲读取文本文件
			
		} catch (UnsupportedEncodingException ce) {
			
			// TODO Auto-generated catch block
			ce.printStackTrace();	
			
		} catch (FileNotFoundException fe) {
			
			fe.printStackTrace();
			
		}
		
		try {
			
			String t;
			while((t=br.readLine()) != null) {
				res.add(t);
			}
			
			br.close();
			
		} catch (IOException ioe) {
			
			ioe.printStackTrace();
			
		} finally {
			
		}
		
		return res;
		
	}

}