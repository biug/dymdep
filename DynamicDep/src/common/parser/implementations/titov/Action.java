package common.parser.implementations.titov;

import common.parser.implementations.MacrosTree;

/*
 * @author ZhangXun
 */

public final class Action {
	
	public static int encodeAction(final int action, final int label) {
		if (action == MacrosTree.ARC_LEFT) {
			return label == 0 ? MacrosTree.ARC_LEFT : MacrosTree.AL_FIRST + label - 1;
		} else if (action == MacrosTree.ARC_RIGHT) {
			return label == 0 ? MacrosTree.ARC_RIGHT : MacrosTree.AR_FIRST + label - 1;
		} else {
			return action;
		}
	}
	
	public static int encodeAction(final int action) {
		return action;
	}
	
	public static int getUnlabeledAction(final int action) {
		if (action < MacrosTree.AL_FIRST) {
			return action;
		} else if (action < MacrosTree.AR_FIRST) {
			return MacrosTree.ARC_LEFT;
		} else {
			return MacrosTree.ARC_RIGHT;
		}
	}
	
	public static int getLabel(final int action) {
		if (action < MacrosTree.AL_FIRST) {
			return 0;
		} else if (action < MacrosTree.AR_FIRST) {
			return action - MacrosTree.AL_FIRST + 1;
		} else {
			return action - MacrosTree.AR_FIRST + 1;
		}
	}
}
