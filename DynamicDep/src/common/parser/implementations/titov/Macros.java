package common.parser.implementations.titov;

import java.util.HashMap;

import common.parser.implementations.MacrosCCGDag;

/*
 * @author ZhangXun
 */

public class Macros extends MacrosCCGDag {
	
	public final static int NO_ACTION = 0;
	public static final int SWAP = 1;
	public static final int REDUCE = 2;
	public static final int SHIFT = 3;
	public static final int AL_SW = 4;
	public static final int AR_SW = 5;
	public static final int AL_RE = 6;
	public static final int AR_RE = 7;
	public static final int AL_SH = 8;
	public static final int AR_SH = 9;

	public static int SH_FIRST;
	public static int AL_SW_FIRST;
	public static int AR_SW_FIRST;
	public static int AL_RE_FIRST;
	public static int AR_RE_FIRST;
	public static int AL_SH_FIRST;
	public static int AR_SH_FIRST;
	public static int ACTION_MAX;
	
	public static void calcConstant(final boolean bs) {

		SH_FIRST = AR_SH + 1;
		AL_SW_FIRST = SH_FIRST + CCGTAG_COUNT;
		AR_SW_FIRST = AL_SW_FIRST + DEP_COUNT;
		AL_RE_FIRST = AR_SW_FIRST + DEP_COUNT;
		AR_RE_FIRST = AL_RE_FIRST + DEP_COUNT;
		AL_SH_FIRST = AR_RE_FIRST + DEP_COUNT;
		AR_SH_FIRST = AL_SH_FIRST + CCGTAG_COUNT * DEP_COUNT;
		ACTION_MAX = AR_SH_FIRST + CCGTAG_COUNT * DEP_COUNT;
		
		CONST_ACTIONSIZE = 3;
		CONST_ACTIONLIST = new Integer[CONST_ACTIONSIZE];
		CONST_ACTIONLIST[0] = integer_cache[SWAP];
		CONST_ACTIONLIST[1] = integer_cache[REDUCE];
		CONST_ACTIONLIST[2] = integer_cache[SHIFT];
		
		WORD2ACTIONSMAP = new HashMap<String, Integer[]>();
		for (String word : WORD2TAGSMAP.keySet()) {
			int[] list = WORD2TAGSMAP.get(word);
			Integer[] ilist = new Integer[(DEP_COUNT << 2) + ((list.length * DEP_COUNT) << 1) + list.length + CONST_ACTIONSIZE];
			for (int i = 0; i < DEP_COUNT; ++i) {
				ilist[i] = integer_cache[i + AL_SW_FIRST];
				ilist[i + DEP_COUNT] = integer_cache[i + AR_SW_FIRST];
				ilist[i + (DEP_COUNT << 1)] = integer_cache[i + AL_RE_FIRST];
				ilist[i + DEP_COUNT + (DEP_COUNT << 1)] = integer_cache[i + AR_RE_FIRST];
				for (int j = 0; j < list.length; ++j) {
					ilist[(DEP_COUNT << 2) + j * DEP_COUNT + i] = integer_cache[list[j] * DEP_COUNT + i + AL_SH_FIRST];
					ilist[(DEP_COUNT << 2) + (j +list.length) * DEP_COUNT + i] = integer_cache[list[j] * DEP_COUNT + i + AR_SH_FIRST];
				}
			}
			for (int i = 0; i < list.length; ++i) {
				ilist[i + (DEP_COUNT << 2) + ((list.length * DEP_COUNT) << 1)] = integer_cache[list[i] + SH_FIRST];
			}
			for (int i = 0; i < CONST_ACTIONSIZE; ++i) {
				ilist[i + (DEP_COUNT << 2) + ((list.length * DEP_COUNT) << 1) + list.length] = CONST_ACTIONLIST[i];
			}
			WORD2ACTIONSMAP.put(word, ilist);
		}
		
		POS2ACTIONSMAP = new HashMap<String, Integer[]>();
		for (String tag : POS2TAGSMAP.keySet()) {
			int[] list = POS2TAGSMAP.get(tag);
			Integer[] ilist = new Integer[(DEP_COUNT << 2) + ((list.length * DEP_COUNT) << 1) + list.length + CONST_ACTIONSIZE];
			for (int i = 0; i < DEP_COUNT; ++i) {
				ilist[i] = integer_cache[i + AL_SW_FIRST];
				ilist[i + DEP_COUNT] = integer_cache[i + AR_SW_FIRST];
				ilist[i + (DEP_COUNT << 1)] = integer_cache[i + AL_RE_FIRST];
				ilist[i + DEP_COUNT + (DEP_COUNT << 1)] = integer_cache[i + AR_RE_FIRST];
				for (int j = 0; j < list.length; ++j) {
					ilist[(DEP_COUNT << 2) + j * DEP_COUNT + i] = integer_cache[list[j] * DEP_COUNT + i + AL_SH_FIRST];
					ilist[(DEP_COUNT << 2) + (j +list.length) * DEP_COUNT + i] = integer_cache[list[j] * DEP_COUNT + i + AR_SH_FIRST];
				}
			}
			for (int i = 0; i < list.length; ++i) {
				ilist[i + (DEP_COUNT << 2) + ((list.length * DEP_COUNT) << 1)] = integer_cache[list[i] + SH_FIRST];
			}
			for (int i = 0; i < CONST_ACTIONSIZE; ++i) {
				ilist[i + (DEP_COUNT << 2) + ((list.length * DEP_COUNT) << 1) + list.length] = CONST_ACTIONLIST[i];
			}
			POS2ACTIONSMAP.put(tag, ilist);
		}
	}
		
}
