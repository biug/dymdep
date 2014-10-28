package include.learning.perceptron;

/*
 * @author
 */

public final class PackedScoreType {
	
	private int packed_size;
	
	protected long scores[];
	protected long zero_scores[];
	
	public PackedScoreType(final int size) {
		packed_size = size;
		scores = new long[size];
		zero_scores = new long[size];
		for (int i = 0; i < size; ++i) {
			zero_scores[i] = 0L;
		}
	}
	
	public void reset() {
		System.arraycopy(zero_scores, 0, scores, 0, packed_size);
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
	
}