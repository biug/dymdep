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

public class MacrosCCGDag extends MacrosBase {
	
	public final static String CCGTAG_NONE_STRING = "1234567890";
	
	public final static int LEFT_DIRECTION = 0;
	public final static int RIGHT_DIRECTION = 1;
	
	public static void loadMacros(String macrosFile) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(macrosFile)), "UTF-8"));
		
		AGENDA_SIZE = Integer.parseInt(br.readLine());
		SEPARTOR = br.readLine();
		
		POSTAG_STRINGS = br.readLine().split(" ");
		POSTAG_MAP = new HashMap<String, Integer>();
		POSTAG_COUNT = POSTAG_STRINGS.length;
		POSTAG_BITS_SIZE = 0;
		while ((1 << POSTAG_BITS_SIZE) < POSTAG_COUNT) {
			++POSTAG_BITS_SIZE;
		}
		
		CCGTAG_STRINGS = br.readLine().split(" ");
		CCGTAG_MAP = new HashMap<String, Integer>();
		CCGTAG_COUNT = CCGTAG_STRINGS.length;
		CCGTAG_BITS_SIZE = 0;
		while ((1 << CCGTAG_BITS_SIZE) <= CCGTAG_COUNT) {
			++CCGTAG_BITS_SIZE;
		}
		
		DEP_STRINGS = br.readLine().split(" ");
		DEP_COUNT = DEP_STRINGS.length;
		DEP_BITS_SIZE = 0;
		while ((1 << DEP_BITS_SIZE) < DEP_COUNT) {
			++DEP_BITS_SIZE;
		}
		
		MAX_INTEGER = 10 + CCGTAG_COUNT + DEP_COUNT + DEP_COUNT;
		if (MAX_INTEGER < POSTAG_COUNT) {
			MAX_INTEGER = POSTAG_COUNT;
		}
		
		CCGTAG_NONE = CCGTAG_COUNT;
		String[] temp_strings = new String[CCGTAG_COUNT + 1];
		System.arraycopy(CCGTAG_STRINGS, 0, temp_strings, 0, CCGTAG_COUNT);
		temp_strings[CCGTAG_COUNT] = CCGTAG_NONE_STRING;
		CCGTAG_STRINGS = temp_strings;
		
		integer_cache = new Integer[MAX_INTEGER + 1];
		for (int i = 0; i <= MAX_INTEGER; ++i) {
			integer_cache[i] = new Integer(i);
		}
		integer_cache[MAX_INTEGER] = new Integer(-1);
		
		for (int i = 0; i < POSTAG_STRINGS.length; ++i) {
			POSTAG_MAP.put(POSTAG_STRINGS[i], integer_cache[i]);
		}

		for (int i = 0; i < CCGTAG_STRINGS.length; ++i) {
			CCGTAG_MAP.put(CCGTAG_STRINGS[i], integer_cache[i]);
		}
		
		System.out.println("integer count = " + integer_cache.length);
		br.close();
	}
	
}