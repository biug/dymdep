package common.parser.implementations.titov;


/*
 * @author ZhangXun
 */

public final class Action {
	
	public static int encodeAction(final int action, final int tag, final int label) {
		if (tag == Macros.CCGTAG_NONE && label == Macros.DEP_NONE) {
			return action;
		} else if (tag == Macros.CCGTAG_NONE) {
			switch (action) {
			case Macros.AL_SW:
				return Macros.AL_SW_FIRST + label;
			case Macros.AR_SW:
				return Macros.AR_SW_FIRST + label;
			case Macros.AL_RE:
				return Macros.AL_RE_FIRST + label;
			case Macros.AR_RE:
				return Macros.AR_RE_FIRST + label;
			}
		} else if (label == Macros.DEP_NONE) {
			return Macros.SH_FIRST + tag;
		} else {
			switch (action) {
				case Macros.AL_SH:
					return Macros.AL_SH_FIRST + tag * Macros.DEP_COUNT + label;
				case Macros.AR_SH:
					return Macros.AR_SH_FIRST + tag * Macros.DEP_COUNT + label;
			}
		}
		return Macros.NO_ACTION;
	}
	
	public static int getUnlabeledAction(final int action) {
		if (action < Macros.SH_FIRST) {
			return action;
		} else if (action < Macros.AL_SW_FIRST) {
			return Macros.SHIFT;
		} else if (action < Macros.AR_SW_FIRST) {
			return Macros.AL_SW;
		} else if (action < Macros.AL_RE_FIRST) {
			return Macros.AR_SW;
		} else if (action < Macros.AR_RE_FIRST) {
			return Macros.AL_RE;
		} else if (action < Macros.AL_SH_FIRST) {
			return Macros.AR_RE;
		} else if (action < Macros.AR_SH_FIRST) {
			return Macros.AL_SH;
		} else {
			return Macros.AR_SH;
		}
	}
	
	public static int getLabel(final int action) {
		if (action < Macros.AL_SW_FIRST) {
			return Macros.DEP_NONE;
		} else if (action < Macros.AR_SW_FIRST) {
			return action - Macros.AL_SW_FIRST;
		} else if (action < Macros.AL_RE_FIRST) {
			return action - Macros.AR_SW_FIRST;
		} else if (action < Macros.AR_RE_FIRST) {
			return action - Macros.AL_RE_FIRST;
		} else if (action < Macros.AL_SH_FIRST) {
			return action - Macros.AR_RE_FIRST;
		} else if (action < Macros.AR_SH_FIRST) {
			return (action - Macros.AL_SH_FIRST) % Macros.DEP_COUNT;
		}
		return (action - Macros.AR_SH_FIRST) % Macros.DEP_COUNT;
	}
	
	public static int getTag(final int action) {
		if (action < Macros.SH_FIRST) {
			return Macros.CCGTAG_NONE;
		}  else if (action < Macros.AL_SW_FIRST) {
			return action - Macros.SH_FIRST;
		} else if (action < Macros.AL_SH_FIRST) {
			return Macros.CCGTAG_NONE;
		} else if (action < Macros.AR_SH_FIRST) {
			return (action - Macros.AL_SH_FIRST) / Macros.DEP_COUNT;
		}
		return (action - Macros.AR_SH_FIRST) / Macros.DEP_COUNT;
	}
}
