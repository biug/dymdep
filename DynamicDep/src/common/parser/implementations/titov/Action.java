package common.parser.implementations.titov;

import common.parser.implementations.MacrosDag;

/*
 * @author ZhangXun
 */

public final class Action {
	
	public static int encodeAction(final int action, final int label) {
		if (action == MacrosDag.SHIFT){
			return MacrosDag.SH_FIRST + label;
		} else if (action == MacrosDag.ARC_LEFT) {
			return MacrosDag.AL_FIRST + label;
		} else if (action == MacrosDag.ARC_RIGHT) {
			return MacrosDag.AR_FIRST + label;
		} else {
			return action;
		}
	}
	
	public static int encodeAction(final int action) {
		return action;
	}
	
	public static int getUnlabeledAction(final int action) {
		if (action < MacrosDag.SH_FIRST) {
			return action;
		} else if (action < MacrosDag.AL_FIRST) {
			return MacrosDag.SHIFT;
		} else if (action < MacrosDag.AR_FIRST) {
			return MacrosDag.ARC_LEFT;
		} else {
			return MacrosDag.ARC_RIGHT;
		}
	}
	
	public static int getLabel(final int action) {
		if (action < MacrosDag.SH_FIRST) {
			return 0;
		} else if (action < MacrosDag.AL_FIRST) {
			return action - MacrosDag.SH_FIRST;
		} else if (action < MacrosDag.AR_FIRST) {
			return action - MacrosDag.AL_FIRST;
		} else {
			return action - MacrosDag.AR_FIRST;
		}
	}
	
	public static void print(final int action) {
		switch(getUnlabeledAction(action)) {
		case MacrosDag.SHIFT:
			System.out.println("shift");
			return;
		case MacrosDag.REDUCE:
			System.out.println("reduce");
			return;
		case MacrosDag.SWAP:
			System.out.println("swap");
			return;
		case MacrosDag.ARC_LEFT:
			System.out.println("left");
			return;
		case MacrosDag.ARC_RIGHT:
			System.out.println("right");
			return;
		}
	}
}
