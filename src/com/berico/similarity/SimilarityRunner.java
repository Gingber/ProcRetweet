package com.berico.similarity;

/**
 * @author Richard C (Berico Technologies)
 */
public class SimilarityRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		/*String one = "面对近年来少有的复杂经济形势，宏观调控犹如“走钢丝”，新一届政府并没有通过短期刺激的方式把经济增速推高，抛弃了只重视速度而忽视质量的“老路子”，保持定力，沉着应对，综合施策，精准发力。特别是突出释放改革最大的红利，激发市场的活力，着力调整经济结构，转变发展方式，使它们和稳增长有机地结合起来。上述的一系列政策措施，犹如政府手中的“平衡木”，应对了大风大浪，没有大起大落。";
		String two = "新一届政府根据经济发展潜力和当前实际，科学确定经济运行的合理区间，守住稳增长、保就业的“下限”，把握好防通胀的“上限”。为中国经济设定了预期调节的预警线。面对近年来少有的复杂经济形势，宏观调控犹如“走钢丝”，新一届政府并没有通过短期刺激的方式把经济增速推高，抛弃了只重视速度而忽视质量的“老路子”，保持定力，沉着应对，综合施策，精准发力。特别是突出释放改革最大的红利，激发市场的活力，着力调整经济结构，转变发展方式，使它们和稳增长有机地结合起来。上述的一系列政策措施，犹如政府手中的“平衡木”，应对了大风大浪，没有大起大落。";
		*/
		String one = "string";
		String two = "gnirts";
		
		long startTime1 = System.currentTimeMillis();   //获取开始时间
		printSimilarity(new CosineSimilarity(), one, two);
		long endTime1 = System.currentTimeMillis();
		System.out.println("数据库读取数据时间： "+ (endTime1-startTime1) +"ms");
		
		long startTime2 = System.currentTimeMillis();   //获取开始时间
		printSimilarity(new JaccardSimilarity(), one, two);
		long endTime2 = System.currentTimeMillis();
		System.out.println("数据库读取数据时间： "+ (endTime2-startTime2) +"ms");
		
		long startTime3 = System.currentTimeMillis();   //获取开始时间
		printSimilarity(new SorensenSimilarity(), one, two);
		long endTime3 = System.currentTimeMillis();
		System.out.println("数据库读取数据时间： "+ (endTime3-startTime3) +"ms");
		
		long startTime4 = System.currentTimeMillis();   //获取开始时间
		printSimilarity(new JaroWinklerSimilarity(), one, two);
		long endTime4 = System.currentTimeMillis();
		System.out.println("数据库读取数据时间： "+ (endTime4-startTime4) +"ms");
	}

	private static void printSimilarity(
			ISimilarityCalculator simCalculator, String one, String two){
		
		double percentSimilar = simCalculator.calculate(one, two) * 100;
	    System.out.println(
	    		String.format("[%s] %s and %s are %s%% similar", 
	    		simCalculator.getClass().getSimpleName(),
	    		one, two, percentSimilar));
	}
	
}
