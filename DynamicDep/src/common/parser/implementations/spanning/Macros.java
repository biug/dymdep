package common.parser.implementations.spanning;

import java.util.HashMap;

import common.parser.implementations.MacrosCCGDag;

public class Macros extends MacrosCCGDag {
	
	public static final int NO_ACTION = 0;
	public static final int SHIFT = 1;
	public static final int ARC_LEFT = 2;
	public static final int ARC_RIGHT = 3;
	
	public static int SH_FIRST;
	public static int AL_FIRST;
	public static int AR_FIRST;
	public static int ACTION_MAX;

	public static Integer[][] ARC_ACTIONLIST;
	
	public static void calcConstant() {

		SH_FIRST = ARC_RIGHT + 1;
		AL_FIRST = SH_FIRST + CCGTAG_COUNT;
		AR_FIRST = AL_FIRST + DEP_COUNT * MAX_SENTENCE_SIZE;
		ACTION_MAX = AR_FIRST + DEP_COUNT * MAX_SENTENCE_SIZE;
		
		CONST_ACTIONSIZE = 0;
		CONST_ACTIONLIST = null;
		
		ACTIONMAP = new HashMap<String, Integer[]>();
		for (String word : MAP.keySet()) {
			int[] list = MAP.get(word);
			Integer[] ilist = new Integer[list.length];
			for (int i = 0; i < list.length; ++i) {
				ilist[i] = integer_cache[list[i] + SH_FIRST];
			}
			ACTIONMAP.put(word, ilist);
		}
		
		ACTIONPOSMAP = new HashMap<String, Integer[]>();
		for (String tag : POSMAP.keySet()) {
			int[] list = POSMAP.get(tag);
			Integer[] ilist = new Integer[list.length];
			for (int i = 0; i < list.length; ++i) {
				ilist[i] = integer_cache[list[i] + SH_FIRST];
			}
			ACTIONPOSMAP.put(tag, ilist);
		}
		
		ARC_ACTIONLIST = new Integer[MAX_SENTENCE_SIZE][];
		ARC_ACTIONLIST[0] = new Integer[0];
		for (int i = 1; i < MAX_SENTENCE_SIZE; ++i) {
			ARC_ACTIONLIST[i] = new Integer[2 * DEP_COUNT * i];
			for (int j = 0; j < (DEP_COUNT * i); ++j) {
				ARC_ACTIONLIST[i][j] = integer_cache[(((j % DEP_COUNT) << MAX_SENTENCE_SIZE_BITS) | (j / DEP_COUNT)) + AL_FIRST];
			}
			for (int j = 0; j < (DEP_COUNT * i); ++j) {
				ARC_ACTIONLIST[i][j + (DEP_COUNT * i)] = integer_cache[(((j % DEP_COUNT) << MAX_SENTENCE_SIZE_BITS) | (j / DEP_COUNT)) + AR_FIRST];
			}
		}
		
	}
}
