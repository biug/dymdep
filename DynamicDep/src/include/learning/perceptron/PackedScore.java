package include.learning.perceptron;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

public final class PackedScore {
	
	public HashMap<Integer, Score> scores;
	
	public PackedScore() {
		scores = new HashMap<Integer, Score>();
	}
	
	public long score(final int index, final int n) {
		Score s = scores.get(MacrosBase.integer_cache[index]);
		if (s != null) {
			return s.score(n);
		} else {
			return 0;
		}
	}
	
	public void updateCurrent(final int index, final int added, final int round) {
		Integer i = MacrosBase.integer_cache[index];
		Score s = scores.get(i);
		if (s == null) {
			s = new Score();
			scores.put(i, s);
		}
		s.updateCurrent(added, round);
	}
	
	public void updateAverage(final int round) {
		Iterator<Entry<Integer, Score>> itr = scores.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Integer, Score> entry = (Entry<Integer, Score>)itr.next();
			((entry.getValue())).updateAverage(round);
		}
	}
	
	public void reset() {
		Iterator<Entry<Integer, Score>> itr = scores.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Integer, Score> entry = (Entry<Integer, Score>)itr.next();
			((entry.getValue())).reset();
		}
	}
	
	public boolean empty() {
		Iterator<Entry<Integer, Score>> itr = scores.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Integer, Score> entry = (Entry<Integer, Score>)itr.next();
			if (!(entry.getValue()).zero()) {
				return false;
			}
		}
		return true;
	}
	
	public void add(PackedScoreType o, final int which) {
		Iterator<Entry<Integer, Score>> itr = scores.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Integer, Score> entry = (Entry<Integer, Score>)itr.next();
			o.addOne(entry.getKey().intValue(), entry.getValue().score(which));
		}
	}
	
	public Score find(final int index) {
		Integer i = MacrosBase.integer_cache[index];
		Score s = scores.get(i);
		if (s == null) {
			s = new Score();
			scores.put(i, s);
		}
		return s;
	}
	
	public void loadPackedScoreFromString(final String str) {
		String[] args = str.substring(2, str.length() - 2).split(" , ");
		for (String subarg : args) {
			String[] subargs = subarg.split(" : ");
			scores.put(MacrosBase.integer_cache[Integer.parseInt(subargs[0])],
					new Score(Integer.parseInt(subargs[1].split(" / ")[0]), Long.parseLong(subargs[1].split(" / ")[1])));
		}
	}
	
	public void savePackedScoreToFile(BufferedWriter bw) throws IOException {
		Iterator<Entry<Integer, Score>> itr = scores.entrySet().iterator();
		bw.write("{ ");
		boolean notstart = false;
		while (itr.hasNext()) {
			Entry<Integer, Score> entry = (Entry<Integer, Score>)itr.next();
			if (!entry.getValue().zero()) {
				if (notstart) bw.write(" , ");
				notstart = true;
				bw.write(entry.getKey().intValue() + " : " + entry.getValue().current + " / " + entry.getValue().total);
			}
		}
		bw.write(" }");
	}
	
}
