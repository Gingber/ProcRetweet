/**
 * 
 */
package com.iie.simhash;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

import jeasy.analysis.MMAnalyzer;

import kevin.zhang.NLPIR;

/**
 * @author zhangcheng
 *
 */
public class BinaryWordSeg implements IWordSeg {
	   
	public static String[] cutWord(String doc) throws IOException {   
		String[] cutWordResult = null;
		String text = doc;       
		MMAnalyzer analyzer = new MMAnalyzer();        
		//System.out.println("file content: "+text);       
		//System.out.println("cutWordResult: "+analyzer.segment(text, " "));     
		String tempCutWordResult = analyzer.segment(text, " ");
		cutWordResult = tempCutWordResult.split(" ");   
		return cutWordResult;
	    
	}
	
	   
	public static HashMap<String, Integer> normalTF(String[] cutWordResult) {        
		HashMap<String, Integer> tfNormal = new HashMap<String, Integer>();	//没有正规化     
		int wordNum = cutWordResult.length;       
		int wordtf = 0;   
		for (int i = 0; i < wordNum; i++) {      
			wordtf = 0;        
			if (cutWordResult[i] != " ") {              
				for (int j = 0; j < wordNum; j++) {               
					if (i != j) {                  
						if (cutWordResult[i].equals(cutWordResult[j])) {
	                            cutWordResult[j] = " ";
	                            wordtf++;           
						}             
					}    
				}                
				tfNormal.put(cutWordResult[i], ++wordtf);             
				cutWordResult[i] = " ";      
			}  
		}    
		return tfNormal;
	}

	@Override
	public List<String> tokens(String doc) throws IOException {
		List<String> binaryWords = new LinkedList<String>();
		
		NLPIR testNLPIR = new NLPIR();
		
		String argu = "file/";
		//System.out.println("NLPIR_Init");
		if (testNLPIR.NLPIR_Init(argu.getBytes("GB2312"),0) == false)
		{
			System.out.println("Init Fail!");
		}
		
		// lucene.jar去停用词
		/*for(int i = 0; i < doc.length() - 1; i += 1) {
			StringBuilder bui = new StringBuilder();
			bui.append(doc.charAt(i)).append(doc.charAt(i + 1));
			binaryWords.add(bui.toString());
			HashMap<String, Integer> dict = new HashMap<String, Integer>();
			dict = normalTF(cutWord(doc));
            allTheNormalTF.put(fileList.get(i), dict);
            java.util.Iterator<Entry<String, Integer>> iterator =  dict.entrySet().iterator(); ;   
            while (iterator.hasNext()) {
            	java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next(); 
				   // entry.getKey() 返回与此项对应的键 
				   // entry.getValue() 返回与此项对应的值 
				   //System.out.print(entry.getKey()+ " "); 				    
            } 
		}*/
		
		String ProcStr = doc.replace("[^0-9\u4e00-\u9fa5a-zA-Z]", "");	// 剔除字符串中特殊字符
		
		//导入用户词典前
		byte nativeBytes[] = testNLPIR.NLPIR_ParagraphProcess(ProcStr.getBytes("GB2312"), 0);
		String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");

		//System.out.println("\n分词结果为： " + nativeStr);
		
		HashMap<String , Integer> map = new HashMap<String , Integer>();  
		
		String[] resStr = nativeStr.split(" ");
		for (String temp : resStr) {
            if (temp == "" || temp == " ") {
                continue;
            }
            
            char[] words = temp.toCharArray();
            for (char word : words)  {
            	if (word >= 0x4e00 && word <= 0x9fa5) {
                    if (map.containsKey(word)) {
                        Integer count = map.get(word);
                        count++;
                        map.put(String.valueOf(word), count);
                    }
                    else  {
                        map.put(String.valueOf(word), 1);
                    }
                }
                else {
                	if (map.containsKey(temp)) {
                		Integer count = map.get(temp);
                        count++;
                        map.put(temp, count);
                    }
                    else {
                        map.put(temp, 1);
                    }
                    break;
                }
            }
        }
		
		//将Map集合中的映射关系取出。存入到Set集合中。  
        Set<Entry<String, Integer>> entrySet = map.entrySet();  
  
        java.util.Iterator<Entry<String, Integer>> it = entrySet.iterator();  
  
        while(it.hasNext()) {  
            Entry<String, Integer> me = it.next();  
            String key = me.getKey();  
            Integer value = me.getValue(); 
            
			binaryWords.add(key);		
  
            //System.out.println(key+":"+value);  
  
        }  
		
		return binaryWords;
	}

	@Override
	public List<String> tokens(String doc, Set<String> stopWords) {
		return null;
	}

}
