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

public final class MacrosTree extends MacrosBase {
	
	public final static int DEP_ROOT = 1;
	
	public final static int NO_ACTION = 0;
	public final static int SHIFT = 1;
	public final static int REDUCE = 2;
	public final static int ARC_LEFT = 3;
	public final static int ARC_RIGHT = 4;
	public final static int POP_ROOT = 5;
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
		
		DEP_STRINGS = br.readLine().split(" ");
		DEP_COUNT = DEP_STRINGS.length;
		DEP_BITS_SIZE = 0;
		while ((1 << DEP_BITS_SIZE) < DEP_COUNT) {
			++DEP_BITS_SIZE;
		}
		
		AL_FIRST = POP_ROOT + 1;
		AR_FIRST = AL_FIRST + DEP_COUNT - 1;
		ACTION_MAX = AR_FIRST + DEP_COUNT - 1;
		br.close();
	}
	
}