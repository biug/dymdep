package common.parser.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

public class MacrosCCGDag extends MacrosBase {
	
	public final static String DEP_NONE_STRING = "123456";
	public final static String CCGTAG_NONE_STRING = "1234567890";
	
	public final static int LEFT_DIRECTION = 0;
	public final static int RIGHT_DIRECTION = 1;
	
	public static Map<String, ArrayList<Integer>> MIDMAP;
	public static Map<String, ArrayList<Integer>> MIDPOSMAP;
	
	public static Map<String, int[]> MAP;
	public static Map<String, int[]> POSMAP;
	
	public static void loadMacros(String macrosFile) throws IOException {
		
		String line;
		String[] temp_strings;
		
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
		
		TREE_STRINGS = br.readLine().split(" ");
		TREE_MAP = new HashMap<String, Integer>();
		TREE_COUNT = TREE_STRINGS.length;
		TREE_BITS_SIZE = 0;
		while ((1 << TREE_BITS_SIZE) < TREE_COUNT) {
			++TREE_BITS_SIZE;
		}
		
		MAX_INTEGER = 10 + CCGTAG_COUNT + DEP_COUNT + DEP_COUNT;
		if (MAX_INTEGER < POSTAG_COUNT) {
			MAX_INTEGER = POSTAG_COUNT;
		}
		if (MAX_INTEGER < TREE_COUNT) {
			MAX_INTEGER = TREE_COUNT;
		}
		
		DEP_NONE = DEP_COUNT;
		temp_strings = new String[DEP_COUNT + 1];
		System.arraycopy(DEP_STRINGS, 0, temp_strings, 0, DEP_COUNT);
		temp_strings[DEP_COUNT] = DEP_NONE_STRING;
		DEP_STRINGS = temp_strings;
		
		CCGTAG_NONE = CCGTAG_COUNT;
		temp_strings = new String[CCGTAG_COUNT + 1];
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
		
		for (int i = 0; i < TREE_STRINGS.length; ++i) {
			TREE_MAP.put(TREE_STRINGS[i], integer_cache[i]);
		}
		
		System.out.println("integer count = " + integer_cache.length);
		
		br.readLine();
		MIDMAP = new HashMap<String, ArrayList<Integer>>();
		while (!(line = br.readLine()).equals("POSMAP")) {
			temp_strings = line.split("[ \t]+");
			MIDMAP.put(temp_strings[0], new ArrayList<Integer>());
			for (String arg : temp_strings) {
				MIDMAP.get(temp_strings[0]).add(CCGTAG_MAP.get(arg));
			}
			MIDMAP.get(temp_strings[0]).remove(CCGTAG_MAP.get(temp_strings[0]));
		}
		MAP = new HashMap<String, int[]>();
		for (String word : MIDMAP.keySet()) {
			ArrayList<Integer> alist = MIDMAP.get(word);
			int size = alist.size();
			int[] list = new int[size];
			for (int i = 0; i < size; ++i) {
				list[i] = alist.get(i).intValue();
			}
			MAP.put(word, list);
		}
		System.out.println(MAP.size());
		
		
		MIDPOSMAP = new HashMap<String, ArrayList<Integer>>();
		while ((line = br.readLine()) != null) {
			temp_strings = line.split("[ \t]+");
			MIDPOSMAP.put(temp_strings[0], new ArrayList<Integer>());
			for (String arg : temp_strings) {
				MIDPOSMAP.get(temp_strings[0]).add(CCGTAG_MAP.get(arg));
			}
			MIDPOSMAP.get(temp_strings[0]).remove(CCGTAG_MAP.get(temp_strings[0]));
		}
		POSMAP = new HashMap<String, int[]>();
		for (String tag : MIDPOSMAP.keySet()) {
			ArrayList<Integer> alist = MIDPOSMAP.get(tag);
			int size = alist.size();
			int[] list = new int[size];
			for (int i = 0; i < size; ++i) {
				list[i] = alist.get(i).intValue();
			}
			POSMAP.put(tag, list);
		}
		System.out.println(POSMAP.size());
		
		br.close();
	}
	
}