package common.parser.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

public final class MacrosDag extends MacrosBase {
	
	public final static int LEFT_DIRECTION = 0;
	public final static int RIGHT_DIRECTION = 1;
	
	public final static int NO_ACTION = 0;
	public final static int SHIFT = 1;
	public final static int REDUCE = 2;
	public final static int SWAP = 3;
	public final static int ARC_LEFT = 4;
	public final static int ARC_RIGHT = 5;
	
	public static int AL_FIRST;
	public static int AR_FIRST;
	public static int ACTION_MAX;
	
	public static void loadMacros(String macrosFile) throws IOException {
		
		integer_cache = new Integer[4096];
		for (int i = 0; i < 4096; ++i) {
			integer_cache[i] = new Integer(i);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(macrosFile)), "UTF-8"));
		
		AGENDA_SIZE = Integer.parseInt(br.readLine());
		SEPARTOR = br.readLine();
		
		POSTAG_STRINGS = br.readLine().split(" ");
		POSTAG_MAP = new HashMap<String, Integer>();
		for (int i = 0; i < POSTAG_STRINGS.length; ++i) {
			POSTAG_MAP.put(POSTAG_STRINGS[i], integer_cache[i]);
		}
		POSTAG_COUNT = POSTAG_STRINGS.length;
		POSTAG_BITS_SIZE = 0;
		while ((1 << POSTAG_BITS_SIZE) < POSTAG_COUNT) {
			++POSTAG_BITS_SIZE;
		}
		
		CCGTAG_STRINGS = br.readLine().split(" ");
		CCGTAG_MAP = new HashMap<String, Integer>();
		for (int i = 0; i < CCGTAG_STRINGS.length; ++i) {
			CCGTAG_MAP.put(CCGTAG_STRINGS[i], integer_cache[i]);
		}
		CCGTAG_COUNT = CCGTAG_STRINGS.length;
		CCGTAG_BITS_SIZE = 0;
		while ((1 << CCGTAG_BITS_SIZE) < CCGTAG_COUNT) {
			++CCGTAG_BITS_SIZE;
		}
		
		DEP_STRINGS = br.readLine().split(" ");
		DEP_COUNT = DEP_STRINGS.length;
		DEP_BITS_SIZE = 0;
		while ((1 << DEP_BITS_SIZE) < DEP_COUNT) {
			++DEP_BITS_SIZE;
		}
		
		AL_FIRST = ARC_RIGHT + 1;
		AR_FIRST = AL_FIRST + DEP_COUNT - 1;
		ACTION_MAX = AR_FIRST + DEP_COUNT - 1;
		br.close();
	}
	
	public static int encodeLinkDistance(final int head_index, final int dep_index) {
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