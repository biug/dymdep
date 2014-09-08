package common.parser.implementations.titov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * @author ZhangXun
 */

public final class Macros {

	public static int AGENDA_SIZE;
	public static String SEPARTOR;
	
	public static String[] TAG_STRINGS;
	public final static int TAG_NONE = 0;
	public final static int TAG_FIRST = 3;
	public static int TAG_COUNT;
	public static int TAG_BITS_SIZE;
	
	public static String[] DEP_STRINGS;
	public static final int DEP_NONE = 0;
	public static final int DEP_ROOT = 1;
	public static final int DEP_FIRST = 1;
	public static int DEP_COUNT;
	public static int DEP_BITS_SIZE;
	
	public final static int NO_ACTION = 0;
	public final static int SHIFT = 1;
	public final static int REDUCE = 2;
	public final static int ARC_LEFT = 3;
	public final static int ARC_RIGHT = 4;
	public final static int SWAP = 5;
	
	public static int AL_FIRST;
	public static int AR_FIRST;
	public static int ACTION_MAX;
	
	public final static int MAX_SENTENCE_SIZE = 256;
	public final static int MAX_SENTENCE_SIZE_BITS = 8;
	public final static int ARITY_DIRECTION_LEFT = 0;
	public final static int ARITY_DIRECTION_RIGHT = 1;
	
	public static void loadMacros(String macrosFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(macrosFile)), "UTF-8"));
		AGENDA_SIZE = Integer.parseInt(br.readLine());
		SEPARTOR = br.readLine();
		TAG_STRINGS = br.readLine().split(",");
		TAG_COUNT = TAG_STRINGS.length;
		TAG_BITS_SIZE = 0;
		while ((1 << TAG_BITS_SIZE) < TAG_COUNT) {
			++TAG_BITS_SIZE;
		}
		DEP_STRINGS = br.readLine().split(",");
		DEP_COUNT = DEP_STRINGS.length;
		DEP_BITS_SIZE = 0;
		while ((1 << DEP_BITS_SIZE) < DEP_COUNT) {
			++DEP_BITS_SIZE;
		}
		AL_FIRST = SWAP + 1;
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