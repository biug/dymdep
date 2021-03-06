package common.parser;

import java.util.HashMap;

public class MacrosBase {
	
	public static final int MAX_INTEGER = 32767;
	public static Integer[] integer_cache;

	public static int AGENDA_SIZE;
	public static String SEPARTOR;

	public static String[] POSTAG_STRINGS;
	public static HashMap<String, Integer> POSTAG_MAP;
	public final static int POSTAG_FIRST = 0;
	public static int POSTAG_COUNT;
	public static int POSTAG_BITS_SIZE;
	
	public static String[] CCGTAG_STRINGS;
	public static HashMap<String, Integer> CCGTAG_MAP;
	public static int CCGTAG_NONE;
	public static final int CCGTAG_FIRST = 0;
	public static int CCGTAG_COUNT;
	public static int CCGTAG_BITS_SIZE;
	
	public static String[] DEP_STRINGS;
	public static int DEP_NONE;
	public static final int DEP_FIRST = 0;
	public static int DEP_COUNT;
	public static int DEP_BITS_SIZE;
	
	public static String[] TREE_STRINGS;
	public static HashMap<String, Integer> TREE_MAP;
	public static final int TREE_FIRST = 0;
	public static int TREE_COUNT;
	public static int TREE_BITS_SIZE;
	
	public static int CONST_ACTIONSIZE;
	public static Integer[] CONST_ACTIONLIST;
	
	public final static int MAX_SENTENCE_SIZE = 256;
	public final static int MAX_SENTENCE_SIZE_BITS = 8;
	
	public static final int encodeLinkDistance(final int head_index, final int dep_index) {
		if (head_index == -1 || dep_index == -1) {
			return MAX_INTEGER;
		}
		int diff;
		diff = head_index - dep_index;
		if (diff < 0) {
			diff = -diff;
		}
		if (diff > 10) {
			diff = 6;
		} else if (diff > 5) {
			diff = 5;
		}
		return diff;
	}
}
