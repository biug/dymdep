package common.parser.implementations.titov;

import java.util.HashMap;

import common.parser.implementations.MacrosCCGDag;

/*
 * @author ZhangXun
 */

public class Macros extends MacrosCCGDag {
	
	public final static int NO_ACTION = 0;;
	public final static int SWAP = 1;
	public final static int REDUCE = 2;
	public final static int SHIFT = 3;
	public final static int ARC_LEFT = 4;
	public final static int ARC_RIGHT = 5;
	
	public static int SH_FIRST;
	public static int AL_FIRST;
	public static int AR_FIRST;
	public static int ACTION_MAX;
	
	public static void calcConstant() {

		SH_FIRST = ARC_RIGHT + 1;
		AL_FIRST = SH_FIRST + CCGTAG_COUNT;
		AR_FIRST = AL_FIRST + DEP_COUNT;
		ACTION_MAX = AR_FIRST + DEP_COUNT;
		
		CONST_ACTIONSIZE = SHIFT - 1 + (DEP_COUNT << 1);
		CONST_ACTIONLIST = new Integer[CONST_ACTIONSIZE];
		for (int i = 1; i < SHIFT; ++i) {
			CONST_ACTIONLIST[i - 1] = integer_cache[i];
		}
		for (int i = AL_FIRST; i < ACTION_MAX; ++i) {
			CONST_ACTIONLIST[i - AL_FIRST + SHIFT - 1] = integer_cache[i];
		}
		
		ACTIONMAP = new HashMap<String, Integer[]>();
		for (String word : MAP.keySet()) {
			int[] list = MAP.get(word);
			Integer[] ilist = new Integer[list.length + CONST_ACTIONSIZE];
			for (int i = 0; i < list.length; ++i) {
				ilist[i] = integer_cache[list[i] + SH_FIRST];
			}
			for (int i = list.length; i < list.length + CONST_ACTIONSIZE; ++i) {
				ilist[i] = CONST_ACTIONLIST[i - list.length];
			}
			ACTIONMAP.put(word, ilist);
		}
		
		ACTIONPOSMAP = new HashMap<String, Integer[]>();
		for (String tag : POSMAP.keySet()) {
			int[] list = POSMAP.get(tag);
			Integer[] ilist = new Integer[list.length + CONST_ACTIONSIZE];
			for (int i = 0; i < list.length; ++i) {
				ilist[i] = integer_cache[list[i] + SH_FIRST];
			}
			for (int i = list.length; i < list.length + CONST_ACTIONSIZE; ++i) {
				ilist[i] = CONST_ACTIONLIST[i - list.length];
			}
			ACTIONPOSMAP.put(tag, ilist);
		}
	}
		
}
