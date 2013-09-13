/**
 * 
 */
package com.iie.twitter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

import com.iie.simhash.BinaryWordSeg;
import com.iie.simhash.Simhash;

import com.berico.similarity.ISimilarityCalculator;
import com.berico.similarity.JaccardSimilarity;
import com.berico.similarity.JaroWinklerSimilarity;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

/**
 * @author Gingber
 *
 */
public class Twitter {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		long startTime1=System.currentTimeMillis();   //获取开始时间
		
		/**
		 * database operation
		*/
		DBConnect dc = new DBConnect(); 
		//连接数据库用到的一些参数. 
		String dbHost = "localhost"; 
		String dbPort = "3306"; 
		String dbName = "http_twitter130814"; 
		String dbuserName = "root"; 
		String dbpsw = "hadoop"; 
		 
		boolean con = dc.dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw);	//连接数据库 
		
		if (con)
		{	
			System.out.println("DataBase connection success!");
		}
		else dc.print("fail");
		
		//ReductionStatement rs = new ReductionStatement(dc);	// 调用还原语句函数
		
		ArrayList fields = new ArrayList(); 
		//fields.add("message_id"); 
		fields.add("title"); 
		//fields.add("user_id");
		Map lmap = new HashMap(); 
		String selCondition = " WHERE (title like '%薄案%' OR title like '%薄熙来%') LIMIT 0, 100"; 
		ArrayList SelResult = dc.dbSelect("message", fields, selCondition);
		
		long endTime1=System.currentTimeMillis(); //获取结束时间  
		System.out.println("数据选取运行时间： "+(endTime1-startTime1)+"ms"); 
		
		//System.out.println("欢迎光临我的JAVA世纪网www.sina.net".replaceAll("[^\u4E00-\u9FA5]", ""));
		
		System.out.println("开始相似度比较...");
		//ISimilarityCalculator simCalculator = new JaroWinklerSimilarity();
		//ISimilarityCalculator simCalculator = new JaccardSimilarity();
		//StrikeAMatch strikeamatch = null; 
		//AbstractStringMetric metric = new MongeElkan();
        //AbstractStringMetric metric = new NeedlemanWunch();
        //AbstractStringMetric metric = new CosineSimilarity();
        //AbstractStringMetric metric = new EuclideanDistance();
        //AbstractStringMetric metric = new Levenshtein();
		//AbstractStringMetric metric = new SmithWaterman();
		//AbstractStringMetric metric = new SmithWatermanGotoh();
		//AbstractStringMetric metric = new SmithWatermanGotohWindowedAffine();
		//AbstractStringMetric metric = new OverlapCoefficient();
		//AbstractStringMetric metric = new DiceSimilarity();
		//AbstractStringMetric metric = new Jaro();
		//AbstractStringMetric metric = new JaroWinkler();
		//AbstractStringMetric metric = new MatchingCoefficient();
		//AbstractStringMetric metric = new QGramsDistance();
		//AbstractStringMetric metric = new Soundex();
		//AbstractStringMetric metric = new TagLink();
		
		Levenshtein ls = null;
		
		
		for (int i = 0; i < SelResult.size(); i++) {
			String[] doc1 = SelResult.get(i).toString().split("=");
			String str1 = doc1[1].replaceAll("[^\u4E00-\u9FA5]", "");
			if (str1.length() >  0 ) {
				//ArrayList pairs1 = strikeamatch.wordLetterPairs(str1);
				for (int j = i + 1; j < SelResult.size(); j++) {
					String[] doc2 = SelResult.get(j).toString().split("=");
					String str2 = doc2[1].replaceAll("[^\u4E00-\u9FA5]", "");
					if (str2.length() > 0) {
						//ArrayList pairs2 = strikeamatch.wordLetterPairs(str2);
						//double percentSimilar = simCalculator.calculate(doc1, doc2) * 100;
						//double percentSimilar = strikeamatch.compareStrings(pairs1, pairs2);
						
						//this single line performs the similarity test
			            //float result = metric.getSimilarity(str1, str2);
						float sim = ls.iLD(str1, str2);
					}
					/*if (percentSimilar > 90) {
						System.out.println(
					    		String.format("[%s] %s and %s are %s%% similar", 
					    		simCalculator.getClass().getSimpleName(),
					    		doc1, doc2, percentSimilar));
					}*/
				}
			}
			
		}

       /* Simhash simHash = new Simhash(new BinaryWordSeg());
		ArrayList<Long> hashes = Lists.newArrayList();
		Map<String, HashSet<Integer>> hashIndex = Maps.newHashMap();
		List<String> lines = Files.readLines(new File("D:\\ProcessTweet.txt"), Charsets.UTF_8);
		
		int idx = 0;
		System.out.println("start to build index");
		for(String line : lines) {
			String[] Record = line.split("&@&");
			long hash = simHash.simhash64(Record[2]);
			System.out.println(line + " " + hash);
			hashes.add(hash);
			StringBuilder bui = new StringBuilder();
			int step = 0;
			for(int i = 0; i < 64; ++i) {
				bui.append((hash >> i) & 1);
				++step;
				if (step % 12 == 0) {
					String key = bui.toString();
					bui = new StringBuilder();
					if (hashIndex.containsKey(key)) {
						hashIndex.get(key).add(idx);
					} else {
						HashSet<Integer> vector = new HashSet<Integer>();
						vector.add(idx);
						hashIndex.put(key, vector);
					}
				}
			}
			++idx;
		}
		System.out.println("build index done");
		File output = new File("D:\\RepeatTweet.txt");
		idx = 0;
		int[] bits = new int[lines.size()];
		for(String line : lines) {
			if (bits[idx] == 1) {
				++idx;
				continue;
			}
			String[] Record = line.split("&@&");
			long hash = simHash.simhash64(Record[2]);
			StringBuilder bui = new StringBuilder();
			int step = 0;
			HashSet<Integer> mayNos = Sets.newHashSet();
			for(int i = 0; i < 64; ++i) {
				bui.append((hash >> i) & 1);
				++step;
				if (step % 12 == 0) {
					String key = bui.toString();
					bui = new StringBuilder();
					if (hashIndex.containsKey(key)) {
						mayNos.addAll(hashIndex.get(key));
					}
				}
			}
			List<Integer> sameNos = Lists.newLinkedList();
			Map<Integer, Integer> dists = Maps.newHashMap();
			for(Integer i : mayNos) {
				int dist = simHash.hammingDistance(hash, hashes.get(i));
				if (dist <= 3) {
					sameNos.add(i);
					bits[i] = 1;
					dists.put(i, dist);
				}
			}
			if (!sameNos.isEmpty()) {
				Files.append("start\n", output, Charsets.UTF_8);
				Files.append(lines.get(idx) + "\n", output, Charsets.UTF_8);
				for(int i : sameNos) {
					if (i == idx) continue;
					Files.append(lines.get(i) + "\t" + dists.get(i) + "\n", output, Charsets.UTF_8);
				}
				Files.append("end\n", output, Charsets.UTF_8);
			}
			bits[idx] = 1;
			++idx;
		}*/
        
		boolean close = dc.dbClose();	//—–>断开数据库 
		if (close) 
			dc.print("close OK"); 
		else 
			dc.print("close fail");
		
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("程序运行时间： "+(endTime-startTime1)+"ms");   

	}
}
