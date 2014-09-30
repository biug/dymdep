package common.parser.implementations.twostack;

import common.parser.implementations.MacrosCCGDag;

public class Macros extends MacrosCCGDag {
	
	public final static int NO_ACTION = 0;;
	public final static int REDUCE = 1;
	public final static int MEM = 2;
	public final static int RECALL = 3;
	public final static int SHIFT = 4;
	public final static int ARC_LEFT = 5;
	public final static int ARC_RIGHT = 6;
	
	public static int SH_FIRST;
	public static int AL_FIRST;
	public static int AR_FIRST;
	public static int ACTION_MAX;
	
	public static void calcConstant() {

		SH_FIRST = ARC_RIGHT + 1;
		AL_FIRST = SH_FIRST + CCGTAG_COUNT;
		AR_FIRST = AL_FIRST + DEP_COUNT;
		ACTION_MAX = AR_FIRST + DEP_COUNT;
		
	}
}
