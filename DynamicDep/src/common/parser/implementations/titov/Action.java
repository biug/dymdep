package common.parser.implementations.titov;

import common.parser.implementations.arceager.Macros;

/*
 * @author ZhangXun
 */

public final class Action {
	
	public static int encodeAction(final int action, final int label) {
		if (action == Macros.ARC_LEFT) {
			return label == 0 ? Macros.ARC_LEFT : Macros.AL_FIRST + label - 1;
		} else if (action == Macros.ARC_RIGHT) {
			return label == 0 ? Macros.ARC_RIGHT : Macros.AR_FIRST + label - 1;
		} else {
			return action;
		}
	}
	
	public static int encodeAction(final int action) {
		return action;
	}
	
	public static int getUnlabeledAction(final int action) {
		if (action < Macros.AL_FIRST) {
			return action;
		} else if (action < Macros.AR_FIRST) {
			return Macros.ARC_LEFT;
		} else {
			return Macros.ARC_RIGHT;
		}
	}
	
	public static int getLabel(final int action) {
		if (action < Macros.AL_FIRST) {
			return 0;
		} else if (action < Macros.AR_FIRST) {
			return action - Macros.AL_FIRST + 1;
		} else {
			return action - Macros.AR_FIRST + 1;
		}
	}
}
