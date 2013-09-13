/**
 * 
 */
package com.iie.twitter;

/**
 * @author hadoop
 *
 */

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;


class SelectionSorter
{
    private int min;
    public void Sort(int[] arr)
    {
        for (int i = 0; i < arr.length - 1; ++i)
        {
            min = i;
            for (int j = i + 1; j < arr.length; ++j)
            {
                if (arr[j] < arr[min])
                    min = j;
            }
            
            int t = arr[min];
            arr[min] = arr[i];
            arr[i] = t;
        }
    }
}


public class ReductionStatement {
	
	List<String> matchstr = new ArrayList<String>();
    List<String> match_RemoveRT = new ArrayList<String>();
    List<String> match_RTUserName = new ArrayList<String>();
	
	public ReductionStatement(DBConnect dc) throws IOException {

		// ȥ���ַ�����ת������,ע��˳�򲻿ɵߵ�
	    String str = "RT @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);
	    str = "RT:@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);
	    str = "RT@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);
	    str = "RT: @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);
	    str = "Retweet @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);
	    str = "Retweeting @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);
	    str = "via @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    matchstr.add(str);
	    str = "thx @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    matchstr.add(str);
	    str = "thx@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    matchstr.add(str);
	    str = "Retweeted by [\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    matchstr.add(str);
	    str = "@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}:{0,1}";
	    matchstr.add(str);

	    // ƥ���޳��û���ǰ��ת������ + @���������RT����
	    str = "RT @";
	    match_RemoveRT.add(str);
	    str = "RT:@";
	    match_RemoveRT.add(str);
	    str = "RT@";
	    match_RemoveRT.add(str);
	    str = "RT: @";
	    match_RemoveRT.add(str);
	    str = "Retweet @";
	    match_RemoveRT.add(str);
	    str = "Retweeting @";
	    match_RemoveRT.add(str);
	    str = "Retweeted by ";
	    match_RemoveRT.add(str);
	    str = "via @";
	    match_RemoveRT.add(str);
	    str = "thx @";
	    match_RemoveRT.add(str);
	    str = "thx@";
	    match_RemoveRT.add(str);

	    // ƥ��ת������ + @ +���û�����
	    str = "RT @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "RT:@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "RT@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "RT: @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "Retweet @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "Retweeting @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "Retweeted by [\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "via @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "thx @[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
	    str = "thx@[\u4e00-\u9fa5a-zA-Z0-9_]{1,15}";
	    match_RTUserName.add(str);
		
	    
		ArrayList fields = new ArrayList(); 
		fields.add("message_id"); 
		fields.add("title"); 
		fields.add("user_id");
		Map lmap = new HashMap(); 
		 
		String selCondition = " limit 0,200000"; 
		ArrayList SelResult = dc.dbSelect("process_message", fields, selCondition); //��>>>ѡ���¼
		
		if (SelResult.size() != 0){ 
			dc.print("select OK!"); 
			dc.print("str��s size = " + SelResult.size());
			
			WriteTxtFile wrf = new WriteTxtFile("D:\\blog.dat");
			WriteTxtFile wrfProTw = new WriteTxtFile("D:\\ProcessTweet.txt");
			WriteTxtFile wrfRT = new WriteTxtFile("D:\\Rt.txt");
			WriteTxtFile wrfHeadEndName = new WriteTxtFile("D:\\RtHeadEndName.txt");
			
			// ÿ�ζ�ȡһ����¼
			for(int i = 0; i < SelResult.size(); i++) {
				System.out.println("i = " + i);
				lmap = (HashMap)SelResult.get(i);
				
				String head_name = null;
				String end_name = null;
				String output = null;

				String message_id = lmap.get("message_id").toString();
				String user_id = lmap.get("user_id").toString();
				String title = lmap.get("title").toString();
				
				HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
				List<String> map = new ArrayList<String>();				
				
				for (String temp : match_RTUserName) { 
					Pattern pattern = Pattern.compile(temp);   
					Matcher matcher = pattern.matcher(title); 
					
					while(matcher.find()) {
						//System.out.println(matcher.start());
						//System.out.println(matcher.group());	
						hashmap.put(matcher.start(), matcher.group());
					} 
				}
				
				// ��ȡ���ݼ��к���ת������(��RT��Retweeted by��)����
				for (String temprtUser : match_RTUserName) {
					Pattern pattern = Pattern.compile(temprtUser);   
					Matcher matcher = pattern.matcher(title);
					
					while(matcher.find()) {
						wrf.write(message_id + "&@&");
						wrf.write(user_id + "&@&");
						wrf.write(title + "\r\n");
						//System.out.println(message_id + "\t" + user_id + "\t" + title);
		                break;  //tweet�к���ת�����ţ�����ѭ��
					}
					
					/*if (matcher.groupCount() != 0) {
						wrf.write(message_id + "\t");
						wrf.write(user_id + "\t");
						wrf.write(title + "\n");
		                break;  //tweet�к���ת�����ţ�����ѭ��
					}*/
		        }
				
				int min = 0;
		        int arraynum = 0;
		        arraynum = hashmap.size();
		        SelectionSorter ss = new SelectionSorter();
		        int[] array = new int[arraynum];
		        Set<Entry<Integer, String>> sets = hashmap.entrySet();
		        for (Entry<Integer, String> entry : sets) {
		            array[min] = entry.getKey();
		            min++;
		        }
		        
		        min = 0;
		        ss.Sort(array);
		        
		        for (int j = 0; j < array.length; j++) {
		            map.add(hashmap.get(array[j]));
		        }
		        
		        ArrayList<String> al=new ArrayList();
		        for (int j = 0; j < map.size(); j++) {
		        	String temp = map.get(j);
		        	
		            for (String tempmatch : match_RemoveRT) {
		                output = temp.replace(tempmatch, "");
		                temp = output;
		            }
		            al.add(temp);
		        }
		        
		        // ��������ת����ϵ����ת���� -> ������
		        if (al.size() > 0) {
		            try {
		                head_name = al.get(0);
		                end_name = user_id;
		                wrfRT.write(message_id + "&@&");
		                wrfRT.write(user_id + "&@&");
		                wrfRT.write(al.get(0) + "\r\n");
		                //System.out.println(message_id + "\t" + user_id + "\t" + al.get(0));
		                
		            }
		            catch (Exception ex) {
		                System.out.println("head_name Error:" + ex.toString());
		            }
		        }
		        
		        if (al.size() > 0) {
		        	try {
		            	for (int j = 1; j < map.size(); j++) {
		            		head_name = al.get(j);
		            		wrfRT.write(message_id + "&@&");
			                wrfRT.write(al.get(j-1) + "&@&");
			                wrfRT.write(al.get(j) + "\r\n");
		            		//System.out.println(message_id + "\t" + al.get(j-1) + "\t" + al.get(j));
			                
		            	}   
		            }
		            catch (Exception ex) {
		                System.out.println("head_name Error:" + ex.toString());
		            }
		        }
		        
		        if (head_name != null && end_name != null) {
		        	wrfHeadEndName.write(message_id + "&@&");
		        	wrfHeadEndName.write(head_name + "&@&");
		        	wrfHeadEndName.write(end_name + "\r\n");
		        	//System.out.println(message_id + "\t" + head_name + "\t" + end_name);
                }
		        
		        // �����Ľ���Ԥ�����޳����е�ת������
		        for (String temp : matchstr) {
                    output = title.replaceAll(temp, "");
                    title = output;
                }
		        
                try {
                	if (!output.isEmpty()) {
                		wrfProTw.write(message_id + "&@&");
                    	wrfProTw.write(user_id + "&@&");
                    	wrfProTw.write(output + "\r\n");
                		//System.out.println(message_id + "\t" + user_id + "\t" + output);
                	}	
                }
                catch (Exception ex) {
                    System.out.println("Error: " + ex.toString());
                }  
			}	
		}
		
	}
}

























