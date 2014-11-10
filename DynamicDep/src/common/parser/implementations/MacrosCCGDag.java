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
	
	public static Map<String, ArrayList<Integer>> WORDMAP;
	public static Map<String, ArrayList<Integer>> POSMAP;
	
	public static Map<String, int[]> WORD2TAGSMAP;
	public static Map<String, int[]> POS2TAGSMAP;
	
	public static Map<String, Integer[]> WORD2ACTIONSMAP;
	public static Map<String, Integer[]> POS2ACTIONSMAP;
	
	public static int[] SHIFT_TAGLIST;
	public static Integer[] SCORED_ACTIONLIST;
	
	public static Integer[] CONST_ACTIONLIST;
	public static int CONST_ACTIONSIZE;
	
	public static void loadMacros(final String macrosFile, final boolean supertag) throws IOException {
		
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
		WORDMAP = new HashMap<String, ArrayList<Integer>>();
		while (!(line = br.readLine()).equals("POSMAP")) {
			temp_strings = line.split("[ \t]+");
			WORDMAP.put(temp_strings[0], new ArrayList<Integer>());
			for (String arg : temp_strings) {
				WORDMAP.get(temp_strings[0]).add(CCGTAG_MAP.get(arg));
			}
			WORDMAP.get(temp_strings[0]).remove(CCGTAG_MAP.get(temp_strings[0]));
		}
		WORD2TAGSMAP = new HashMap<String, int[]>();
		for (String word : WORDMAP.keySet()) {
			if (supertag) {
				ArrayList<Integer> alist = WORDMAP.get(word);
				int size = alist.size();
				int[] list = new int[size];
				for (int i = 0; i < size; ++i) {
					list[i] = alist.get(i).intValue();
				}
				WORD2TAGSMAP.put(word, list);
			}
		}
		System.out.println(WORD2TAGSMAP.size());
		
		POSMAP = new HashMap<String, ArrayList<Integer>>();
		while ((line = br.readLine()) != null) {
			temp_strings = line.split("[ \t]+");
			POSMAP.put(temp_strings[0], new ArrayList<Integer>());
			for (String arg : temp_strings) {
				POSMAP.get(temp_strings[0]).add(CCGTAG_MAP.get(arg));
			}
			POSMAP.get(temp_strings[0]).remove(CCGTAG_MAP.get(temp_strings[0]));
		}
		POS2TAGSMAP = new HashMap<String, int[]>();
		for (String tag : POSMAP.keySet()) {
			if (supertag) {
				ArrayList<Integer> alist = POSMAP.get(tag);
				int size = alist.size();
				int[] list = new int[size];
				for (int i = 0; i < size; ++i) {
					list[i] = alist.get(i).intValue();
				}
				POS2TAGSMAP.put(tag, list);
			} else {
				int[] list = new int[1];
				list[0] = -1;
				POS2TAGSMAP.put(tag, list);
			}
		}
		System.out.println(POS2TAGSMAP.size());
		
		br.close();
	}
	
}