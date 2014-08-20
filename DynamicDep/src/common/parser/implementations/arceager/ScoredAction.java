package common.parser.implementations.arceager;

/*
 * record action and score
 * @author ZhangXun
 */

public final class ScoredAction {

	private static final long mid = (1L << 32) - 1L;
	public int action;
	public long score;
	
	public ScoredAction() {
		action = 0;
		score = 0;
	}
	
	public ScoredAction(final int a, final long s) {
		action = a;
		score = s;
	}
	
	public boolean more(final ScoredAction sa) {
		return score > sa.score;
	}
	
	@Override
	public boolean equals(final Object o) {
		return score == ((ScoredAction)o).score;
	}
	
	@Override
	public int hashCode() {
		return (int)(score & mid);
	}
	
	public void copy(final ScoredAction sa) {
		action = sa.action;
		score = sa.score;
	}
	
}
