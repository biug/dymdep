package common.parser;

import java.util.HashMap;

public class MacrosBase {
	
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
	public final static int CCGTAG_NONE = -1;
	public static final int CCGTAG_FIRST = 0;
	public static int CCGTAG_COUNT;
	public static int CCGTAG_BITS_SIZE;
	
	public static String[] DEP_STRINGS;
	public static final int DEP_NONE = -1;
	public static final int DEP_FIRST = 0;
	public static int DEP_COUNT;
	public static int DEP_BITS_SIZE;
	
	public final static int MAX_SENTENCE_SIZE = 256;
	public final static int MAX_SENTENCE_SIZE_BITS = 8;
	public final static int ARITY_DIRECTION_LEFT = 0;
	public final static int ARITY_DIRECTION_RIGHT = 1;
	
}
