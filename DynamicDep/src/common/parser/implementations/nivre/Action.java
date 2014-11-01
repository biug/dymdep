package common.parser.implementations.nivre;

public class Action {

	public static int encodeAction(final int action, final int label) {
		if (action == Macros.SHIFT){
			return Macros.SH_FIRST + label;
		} else if (action == Macros.ARC_LEFT) {
			return Macros.AL_FIRST + label;
		} else if (action == Macros.ARC_RIGHT) {
			return Macros.AR_FIRST + label;
		} else {
			return action;
		}
	}
	
	public static int encodeAction(final int action) {
		return action;
	}
	
	public static int getUnlabeledAction(final int action) {
		if (action < Macros.SH_FIRST) {
			return action;
		} else if (action < Macros.AL_FIRST) {
			return Macros.SHIFT;
		} else if (action < Macros.AR_FIRST) {
			return Macros.ARC_LEFT;
		} else {
			return Macros.ARC_RIGHT;
		}
	}
	
	public static int getLabel(final int action) {
		if (action < Macros.SH_FIRST) {
			return 0;
		} else if (action < Macros.AL_FIRST) {
			return action - Macros.SH_FIRST;
		} else if (action < Macros.AR_FIRST) {
			return action - Macros.AL_FIRST;
		} else {
			return action - Macros.AR_FIRST;
		}
	}
	
	public static void print(final int action) {
		switch(getUnlabeledAction(action)) {
		case Macros.SHIFT:
			System.out.println("shift");
			return;
		case Macros.REDUCE:
			System.out.println("reduce");
			return;
		case Macros.SWAP:
			System.out.println("swap");
			return;
		case Macros.ARC_LEFT:
			System.out.println("left");
			return;
		case Macros.ARC_RIGHT:
			System.out.println("right");
			return;
		}
	}
}
