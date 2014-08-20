package include.learning.perceptron;

/*
 * @author ZhangXun
 */

public final class Score {
	
	public final static int eNonAverage = 0;
	public final static int eAverage = 1;
	
	protected int lastupdate;
	
	public int current;
	public int total;
	
	public Score() {
		current = total = lastupdate = 0;
	}
	
	public Score(final int c, final int t) {
		current = c;
		total = t;
		lastupdate = 0;
	}
	
	public Score(final Score s) {
		current = s.current;
		total = s.total;
		lastupdate = s.lastupdate;
	}
	
	public void reset() {
		current = total = lastupdate = 0;
	}
	
	public boolean empty() {
		return current == 0 && total == 0 && lastupdate == 0;
	}
	
	public boolean zero() {
		return current == 0 && total == 0;
	}
	
	public int at(final int n) {
		if (n == eNonAverage) {
			return current;
		} else if (n == eAverage) {
			return total;
		} else {
			return -1;
		}
	}
	
	public final int score() {
		return current;
	}
	
	public int score(final int n) {
		if (n == eNonAverage) {
			return current;
		} else {
			return total;
		}
	}
	
	// round is 0
	public void updateCurrent(final int added) {
		if (lastupdate < 0) {
			updateAverage(0);
			lastupdate = 0;
		}
		current += added;
		total += added;
	}
	
	public void updateCurrent(final int added, final int round) {
		if (round > lastupdate) {
			updateAverage(round);
			lastupdate = round;
		}
		current += added;
		total += added;
	}
	
	public void updateAverage() {
		if (lastupdate < 0) {
			total += current * (-lastupdate);
		}
	}
	
	public void updateAverage(final int round) {
		if (round > lastupdate) {
			total += current * (round - lastupdate);
		}
	}
	
}
