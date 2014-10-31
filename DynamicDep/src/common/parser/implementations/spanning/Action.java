package common.parser.implementations.spanning;

public class Action {
	public static int encodeAction(final int action, final int index, final int label) {
		if (action == Macros.SHIFT){
			return Macros.SH_FIRST + label;
		} else if (action == Macros.ARC_LEFT) {
			return Macros.AL_FIRST + ((label << Macros.MAX_SENTENCE_SIZE_BITS) | index);
		} else if (action == Macros.ARC_RIGHT) {
			return Macros.AR_FIRST + ((label << Macros.MAX_SENTENCE_SIZE_BITS) | index);
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
			return (action - Macros.AL_FIRST) >> Macros.MAX_SENTENCE_SIZE_BITS;
		} else {
			return (action - Macros.AR_FIRST) >> Macros.MAX_SENTENCE_SIZE_BITS;
		}
	}
	
	public static int getIndex(final int action) {
		if (action < Macros.AL_FIRST) {
			return 0;
		} else if (action < Macros.AR_FIRST) {
			return (action - Macros.AL_FIRST) & (Macros.MAX_SENTENCE_SIZE - 1);
		} else {
			return (action - Macros.AR_FIRST) & (Macros.MAX_SENTENCE_SIZE - 1);
		}
	}
}
