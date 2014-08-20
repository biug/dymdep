package include.util;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * @author ZhangXun
 */
public class Tokenizer {
	
	protected HashMap<String, Integer> m_mapTokens;
	protected ArrayList<String> m_vecKeys;
	protected int m_nWaterMark;
	protected int m_nStartingToken;
	
	public Tokenizer(int nTokenStartsFrom) {
		m_vecKeys = new ArrayList<String>();
		m_mapTokens = new HashMap<String, Integer>();
		m_nWaterMark = m_nStartingToken = nTokenStartsFrom;
	}
	
	public final int lookup(final String key) {
		Integer retval = m_mapTokens.get(key);
		if (retval == null) {
			retval = new Integer(m_nWaterMark++);
			m_mapTokens.put(key, retval);
			m_vecKeys.add(key);
		}
		return retval.intValue();
	}
	
	public final int find(final String key, final int val) {
		Integer retval = m_mapTokens.get(key);
		return retval == null ? val : retval.intValue();
	}
	
	public final String key(final int token) {
		return m_vecKeys.get(token - m_nStartingToken);
	}
	
	public final int count() {
		return m_nWaterMark;
	}
}
