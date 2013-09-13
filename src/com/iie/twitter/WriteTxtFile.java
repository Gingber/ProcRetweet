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
			
			file.delete();			// �ļ����ڣ���ɾ��
			file.createNewFile();	// �����ļ�
		} else {
			
			file.createNewFile();	// �����ļ�
		}
		
		//FileWriter wrf = new FileWriter(file,true);
		this.wrfile = filePath;
	}
	
	
	public  Vector<String> write(String data) {
		
		Vector<String> res = new Vector<String>(10);
		BufferedWriter bw = null;
		
		try {
			// ��1024*1024*5 = 5M�Ļ����ȡ�ı��ļ�,��������д���ٶ���
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

