/**
 * 
 */
package com.iie.twitter;

import java.io.IOException;

/**
 * @author Gingber
 *
 */
public class Levenshtein {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String sNew = "GAMBOL";
        String sOld = "GUMBO";
        
		long startTime1=System.currentTimeMillis();   //获取开始时间
		
		float dist = iLD(sNew, sOld);
		System.out.println("dist = " + dist);
		
		long endTime1=System.currentTimeMillis(); //获取结束时间  
		System.out.println("数据选取运行时间： "+(endTime1-startTime1)+"ms"); 
		
		
	}
	
	///*****************************
    /// Compute Levenshtein distance 
    /// Memory efficient version
    ///*****************************
    ///
    public static float iLD(String sRow, String sCol)
    {
        int RowLen = sRow.length();  // length of sRow
        int ColLen = sCol.length();  // length of sCol
        int RowIdx;                	 // iterates through sRow
        int ColIdx;                  // iterates through sCol
        char Row_i;                  // ith character of sRow
        char Col_j;                  // jth character of sCol
        int cost;                    // cost

        /// Test string length
        if (Math.max(sRow.length(), sCol.length()) > Math.pow(2, 31))
			try {
				throw (new Exception("\nMaximum string length in Levenshtein.iLD is " + Math.pow(2, 31) + ".\nYours is " + Math.max(sRow.length(), sCol.length()) + "."));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        // Step 1
        if (RowLen == 0)
        {
            return ColLen;
        }

        if (ColLen == 0)
        {
            return RowLen;
        }

        /// Create the two vectors
        int[] v0 = new int[RowLen + 1];
        int[] v1 = new int[RowLen + 1];
        int[] vTmp;

        /// Step 2
        /// 
        /// Initialize the first vector
        for (RowIdx = 1; RowIdx <= RowLen; RowIdx++)
        {
            v0[RowIdx] = RowIdx;
        }

        /// Step 3
        /// 
        /// Fore each column
        for (ColIdx = 1; ColIdx <= ColLen; ColIdx++)
        {
            /// Set the 0'th element to the column number
            v1[0] = ColIdx;

            Col_j = sCol.charAt(ColIdx-1);


            /// Step 4
            /// 
            /// Fore each row
            for (RowIdx = 1; RowIdx <= RowLen; RowIdx++)
            {
                Row_i = sRow.charAt(RowIdx - 1);


               /// Step 5

                if (Row_i == Col_j)
                {
                    cost = 0;
                }
                else
                {
                    cost = 1;
                }

                /// Step 6
                /// 
                /// Find minimum
                int m_min = v0[RowIdx] + 1;
                int b = v1[RowIdx - 1] + 1;
                int c = v0[RowIdx - 1] + cost;

                if (b < m_min)
                {
                    m_min = b;
                }
                if (c < m_min)
                {
                    m_min = c;
                }

                v1[RowIdx] = m_min;
            }

            /// Swap the vectors
            vTmp = v0;
            v0 = v1;
            v1 = vTmp;
        }

        // Step 7 
          /// Value between 0 - 100
         /// 0==perfect match 100==totaly different
         /// 
         /// The vectors where swaped one last time at the end of the last loop,
         /// that is why the result is now in v0 rather than in v1
          //System.out.println("iDist=" + v0[RowLen]);
          int max = Math.max(RowLen, ColLen);
         return 1.0f-((float)v0[RowLen] / max);
    }
}
