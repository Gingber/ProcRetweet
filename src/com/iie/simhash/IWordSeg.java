/**
 * 
 */
package com.iie.simhash;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author zhangcheng
 *
 */
public interface IWordSeg {

	public List<String> tokens(String doc) throws IOException;
	
	public List<String> tokens(String doc, Set<String> stopWords);
}
