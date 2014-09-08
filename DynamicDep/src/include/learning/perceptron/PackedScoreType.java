package include.learning.perceptron;

/*
 * @author
 */

public final class PackedScoreType {
	
	private int packed_size;
	
	protected long scores[];
	
	public PackedScoreType(final int size) {
		packed_size = size;
		scores = new long[size];
	}
	
	public void reset() {
		for (int index = 0; index < packed_size; ++index) {
			scores[index] = 0;
		}
	}
	
	public boolean empty() {
		for (int index = 0; index < packed_size; ++index) {
			if (scores[index] != 0) {
				return false;
			}
		}
		return true;
	}
	
	public final long at(final int index) {
		return scores[index];
	}
	
	public final void set(final int index, final int score) {
		scores[index] = score;
	}
	
	public final void addOne(final int index, final long score) {
		scores[index] += score;
	}
	
	public void add(final PackedScoreType score) {
		for (int index = 0; index < packed_size; ++index) {
			scores[index] += score.scores[index];
		}
	}
	
}