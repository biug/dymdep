package include.learning.perceptron;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public abstract class PackedScoreMap<K> extends HashMap<K, PackedScore> {
	
	public String name;
	public int count;

	public PackedScoreMap(final String input_name) {
		super();
		name = input_name;
		count = 0;
	}
	
	public final void getScore(final PackedScoreType o, final K key, final int which) {
		PackedScore ps = super.get(key);
		if (ps != null) {
			ps.add(o, which);
		}
	}
	
	public final void updateScore(final K key, final int index, final int amount, final int round) {
		PackedScore ps = super.get(key);
		if (ps == null) {
			ps = new PackedScore();
			super.put(allocate_key(key), ps);
		}
		ps.updateCurrent(index, amount, round);
	}
	
	public final void getOrUpdateScore(final PackedScoreType out, final K key, final int index, final int which, final int amount, final int round) {
		if (amount == 0) {
			PackedScore ps = super.get(key);
			if (ps != null) {
				ps.add(out, which);
			}
		} else {
			this.updateScore(key, index, amount, round);
		}
	}
	
	public final void getOrUpdateScore(final PackedScoreType out, final K key, final int index, final int which, final int amount) {
		this.getOrUpdateScore(out, key, index, which, amount, 0);
	}
	
	public final void getOrUpdateScore(final PackedScoreType out, final K key, final int index, final int which) {
		this.getScore(out, key, which);
	}
	
	public final void computeAverage(final int round) {
		count = 0;
		Iterator<java.util.Map.Entry<K, PackedScore>> iter = this.entrySet().iterator();
		while (iter.hasNext()) {
			java.util.Map.Entry<K, PackedScore> entry = iter.next();
			((entry.getValue())).updateAverage(round);
			++count;
		}
	}
	
	public final void clearScore() {
		Iterator<java.util.Map.Entry<K, PackedScore>> iter = this.entrySet().iterator();
		while (iter.hasNext()) {
			java.util.Map.Entry<K, PackedScore> entry = iter.next();
			((entry.getValue())).reset();
		}
	}
	
	public final void loadScoresFromFileStream(BufferedReader br) throws IOException {
		String s = br.readLine();
		if (!(s.substring(0, this.name.length()).equals(this.name))) {
			System.out.println("score_map " + this.name + " not matched");
			return;
		}
		while (!(s = br.readLine()).isEmpty()) {
			int middle = s.lastIndexOf("@");
			K key = loadKeyFromString(s.substring(0, middle).trim());
			if (key != null) {
				PackedScore ps = new PackedScore();
				ps.loadPackedScoreFromString(s.substring(middle + 1).trim());
				super.put(key, ps);
			}
		}
	}
	
	public final void saveScoresToFileStream(BufferedWriter bw) throws IOException {
		if (this.count != 0) {
			bw.write(this.name + " " + this.count);
		} else {
			bw.write(this.name);
		}
		bw.newLine();
		Iterator<java.util.Map.Entry<K, PackedScore>> itr = super.entrySet().iterator();
		while (itr.hasNext()) {
			java.util.Map.Entry<K, PackedScore> entry = itr.next();
			if (!entry.getValue().empty()) {
				bw.write(generateStringFromKey(entry.getKey()));
				bw.write("\t@\t");
				entry.getValue().savePackedScoreToFile(bw);
				bw.newLine();
			}
		}
		bw.newLine();
	}
	
	public abstract K allocate_key(final K key);
	public abstract K loadKeyFromString(final String str);
	public abstract String generateStringFromKey(final K key);
	
}