/*****************************************************
 * 
 * This is a class of reading txt-file. 
 *
 *****************************************************
 * 
 * @author: Ginger
 * @Date: Tuesday 04-02 2013
 *
 *****************************************************
 */

package com.iie.twitter;

import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import com.google.common.base.Charsets;

public class WriteTxtFile {
	
	String wrfile = null;
	
	public WriteTxtFile(String filePath) throws IOException {
		
		File file =new File(filePath); 
		
		if (file.exists()) {
			
			file.delete();			// 文件存在，则删除
			file.createNewFile();	// 创建文件
		} else {
			
			file.createNewFile();	// 创建文件
		}
		
		//FileWriter wrf = new FileWriter(file,true);
		this.wrfile = filePath;
	}
	
	
	public  Vector<String> write(String data) {
		
		Vector<String> res = new Vector<String>(10);
		BufferedWriter bw = null;
		
		try {
			// 用1024*1024*5 = 5M的缓冲读取文本文件,导致数据写入速度慢
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wrfile, true), Charsets.UTF_8));  
			
		} catch (FileNotFoundException fe) {
			
			fe.printStackTrace();
			
		}
		
		try {
			
			bw.write(data);
			bw.flush();
			bw.close();
			
		} catch (IOException ioe) {
			
			ioe.printStackTrace();
			
		} 
		
		return res;
		
	}

}

