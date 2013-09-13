/**
 *  This is a database operation program. 
 */
package com.iie.twitter;

/**
 * @author Gingber
 * @date Wednesday April 17th 2013
 *
 */

/** 
 * ���ݿ����ӡ�ѡ�񡢸��¡�ɾ����ʾ 
 */ 
//import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.*;

import com.iie.twitter.WriteTxtFile;


public class DBConnect {
	
/////////////////////////////////////////�������C>>>���ݳ�Ա and ���캯�� 
	private Connection dbconn; 
	private Statement dbstate; 
	private ResultSet dbresult; 
	
	 
	DBConnect() 
	{ 
		dbconn = null; 
		dbstate = null; 
		dbresult = null; 
	} 
	
	 
/////////////////////////////////////////�������C>>>�෽�� 
	public void print(String str)//����� 
	{ 
		System.out.println(str); 
	}//end print(��) 
	
	 
	/** 
	 * ����MySql���ݿ� 
	 * @param host 
	 * @param port 
	 * @param dbaName 
	 * @param usName 
	 * @param psw 
	 * @return boolֵ�����ӳɹ������棬ʧ�ܷ��ؼ� 
	 */ 
	public boolean dbConnection(String host, String port, String dbaName, String usName, String psw) 
	{ 
		String driverName = "com.mysql.jdbc.Driver";//"org.gjt.mm.mysql.Driver"���������������� 
		String dbHost = host;//���ݿ��һЩ��Ϣ 
		String dbPort = port; 
		String dbName = dbaName; 
		String enCoding = "?useUnicode=true&characterEncoding=gb2312"; //���MySql��������,Ҫ����д���ܿո� 
		String userName = usName; 
		String Psw = psw; 
		String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + enCoding; 
		try 
		{ 
			Class.forName(driverName).newInstance(); 
			dbconn = DriverManager.getConnection(url, userName, Psw); 
			//getConnection(url, userName, Psw)�Ӹ���driver��ѡ����ʵ�ȥ�������ݿ� 
			//return a connection to the URL 
		}catch(Exception e){ 
			print("url = " + url); //��������ʱ�����������ݿ���Ϣ��ӡ���� 
			print("userName = " + userName); 
			print("Psw" + Psw); 
			print("Exception: " + e.getMessage());//�õ�������Ϣ 
		} 
		if (dbconn != null)//dbconn != null ��ʾ�������ݿ�ɹ������쳣��֤���� 
			return true; 
		else 
			return false; 
	}// end boolean dbConnection(��) 
	
	
	/** 
	 * �����ݿ������ѡ������� 
	 * @param tableName ���ݿ���� 
	 * @param fieles �ֶ��� 
	 * @param selCondition ѡ������ 
	 * @return һ������map��List���б��� 
	 */ 
	public ArrayList dbSelect(String tableName, ArrayList fields, String selCondition) 
	{ 
		ArrayList mapInList = new ArrayList();	 
		String selFields = ""; 
		for (int i = 0; i<fields.size(); ++i) 
			selFields += fields.get(i) + ", "; 
		String selFieldsTem = selFields.substring(0, selFields.length() - 2);//����String��������ȡ�Ӵ� 
		try{ 
			dbstate = dbconn.createStatement(); 
			String sql = "select " + selFieldsTem + " from " + tableName + selCondition; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			} 
			while(dbresult.next()){ 
				Map selResult = new HashMap(); 
				//selResult.put("message_id", dbresult.getString("message_id")); 
				selResult.put("title", dbresult.getString("title")); 
				//selResult.put("user_id", dbresult.getString("user_id")); 
				mapInList.add(selResult); 
			} 
		}catch(Exception e){ 
			print("Exception: " + e.getMessage()); 
		} 
		return mapInList; 
	}//end String dbSelect(��) 

	 
	/** 
	 * �����ݿ���еļ�¼����ɾ������ 
	 * @param tableName 
	 * @param condition 
	 * @return boolֵ����ʾɾ���ɹ�����ʧ�ܡ� 
	 */ 
	public boolean dbDelete(String tableName, String condition) 
	{//�����C>>>ɾ������ 
		boolean delResult = false; 
		String sql = "delete from " + tableName + " " + condition;
		print ("sql = " + sql);
		try{ 
			dbstate.executeUpdate(sql);	//return int // int delRe = ?? 
			delResult = true; 
		}catch(Exception e){ 
			print ("sql = " + sql); 
			print ("Exception: " + e.getMessage()); 
		} 
		if (delResult) 
			return true; 
		else 
			return false; 
	}//end dbDelete(��) 

	
	/** 
	 * �����ݿ���м�¼���и��²��� 
	 * @param tabName 
	 * @param reCount 
	 * @return boolֵ���ɹ�����true��ʧ�ܷ���false 
	 */ 
	public boolean dbUpdate(String tabName, HashMap reCount, String upCondition) 
	{ 
		boolean updateResult = false; 
		String Values = ""; 
		Iterator keyValues = reCount.entrySet().iterator(); 
		for(int i = 0; i<reCount.size(); ++i) 
		{ 
			Map.Entry entry = (Map.Entry) keyValues.next(); 
			Object key = entry.getKey(); 
			Object value = entry.getValue(); 
			Values += key + "=" + "'" + value + "'" + ", "; 
		} 
		String updateValues = Values.substring(0, Values.length() - 2); 
		String sql = "update " + tabName + " set " + updateValues + " " + upCondition;
		print ("sql = " + sql);
		try 
		{ 
			dbstate.executeUpdate(sql); 
			updateResult = true; 
		}catch(Exception err){ 
			print("sql = " + sql); 
			print("Exception: " + err.getMessage()); 
		} 
		if (updateResult) 
			return true; 
		else 
			return false; 
	}//end dbUpdate(��) 
	 
	/** 
	 * �����ݿ�����в������ 
	 * @param tabName 
	 * @param hm 
	 * @return boolֵ���ɹ�����true��ʧ�ܷ���false 
	 */ 
	public boolean dbInsert(String tabName, HashMap<String, Object> values) 
	{ 
		String sql = ""; 
		String insertFields = "", temFields = ""; 
		String insertValues = "", temValues = ""; 
		boolean insertResult = false; 
		Iterator keyValues = values.entrySet().iterator(); 
		for(int i = 0; i<values.size(); ++i) 
		{ 
			Map.Entry entry = (Map.Entry) keyValues.next(); 
			Object key = entry.getKey(); 
			Object value = entry.getValue(); 
			temFields += key + ", "; 
			temValues += "'" + value + "'" + ", "; 
		} 
		insertFields = temFields.substring(0, temFields.length() - 2); 
		insertValues = temValues.substring(0, temValues.length() - 2); 
		sql += "insert into " + tabName + " (" + insertFields + ") values" + "(" + insertValues + ")";
		try 
		{ 
			dbstate.executeUpdate(sql); 
			insertResult = true; 
		}catch(Exception e){ 
			print("Sql = " + sql); 
			print("Exception: " + e.getMessage()); 
		} 
		if (insertResult) 
			return true; 
		else 
			return false; 
	}//end dbInsert(��) 

	
	/** 
	 * �Ͽ����ݿ� 
	 * @return boolֵ���ɹ�����true��ʧ�ܷ���false 
	 */ 
	public boolean dbClose() 
	{ 
		boolean closeResult = false; 
		try 
		{ 
			dbconn.close(); 
			closeResult = true; 
		}catch(Exception e){ 
			print("Exception: " + e.getMessage()); 
		} 
		if (closeResult) 
			return true; 
		else 
			return false; 
	}//end dbClose() 
	

}

































