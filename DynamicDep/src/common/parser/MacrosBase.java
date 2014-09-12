package common.parser;

public class MacrosBase {

	public static int AGENDA_SIZE;
	public static String SEPARTOR;

	public static String[] POSTAG_STRINGS;
	public final static int POSTAG_NONE = 0;
	public final static int POSTAG_FIRST = 3;
	public static int POSTAG_COUNT;
	public static int POSTAG_BITS_SIZE;
	
	public static String[] DEP_STRINGS;
	public static final int DEP_NONE = 0;
	public static final int DEP_ROOT = 1;
	public static final int DEP_FIRST = 1;
	public static int DEP_COUNT;
	public static int DEP_BITS_SIZE;
	
	public final static int MAX_SENTENCE_SIZE = 256;
	public final static int MAX_SENTENCE_SIZE_BITS = 8;
	public final static int ARITY_DIRECTION_LEFT = 0;
	public final static int ARITY_DIRECTION_RIGHT = 1;
	
}
