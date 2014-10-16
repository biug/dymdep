package include.util;

import include.linguistics.IntIntegerVector;
import include.linguistics.POSTaggedWord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import common.parser.MacrosBase;

public final class TreeAnalyzer {
	
	public int size;
	public int eular_size;
	public int block_size;
	public int block_count;
	
	public int[] Clog2;
	public int[] Flog2;
	
	public int[] Eular;
	public int[] Depth;
	public int[] Index;
	
	public int[] GroupMin;
	public int[] GroupIndex;
	public int[] GroupState;
	
	public boolean[] BeState;
	public int[][][] InGroup;
	public int[][] BetweenGroup;
	
	public String[][] POSPath;
	public String[][] LabelPath;
	
	public int ceillog2(int n) {
		--n;
		int r = 0;
		while (n > 0) {
			n >>= 1;
			++r;
		}
		return r;
	}
	
	public int floorlog2(int n) {
		return ceillog2(n + 1) - 1;
	}
	
	public int analyzeRoot(ArrayList<Integer> heads) {
		for (int i = 0, n = heads.size(); i < n; ++i) {
			if (heads.get(i).intValue() == -1) {
				heads.set(i, MacrosBase.integer_cache[heads.size()]);
			}
		}
		heads.add(MacrosBase.integer_cache[MacrosBase.MAX_INTEGER]);
		return heads.size() - 1;
	}
	
	public TreeAnalyzer() {
		Clog2 = new int[MacrosBase.MAX_SENTENCE_SIZE << 1];
		Flog2 = new int[MacrosBase.MAX_SENTENCE_SIZE << 1];
		
		for (int i = 0, n = MacrosBase.MAX_SENTENCE_SIZE << 1; i < n; ++i) {
			Clog2[i] = ceillog2(i);
			Flog2[i] = floorlog2(i);
		}
		
		Eular = new int[MacrosBase.MAX_SENTENCE_SIZE << 1];
		Depth = new int[MacrosBase.MAX_SENTENCE_SIZE << 1];
		Index = new int[MacrosBase.MAX_SENTENCE_SIZE];
		
		GroupMin = new int[(MacrosBase.MAX_SENTENCE_SIZE << 1)];
		GroupIndex = new int[(MacrosBase.MAX_SENTENCE_SIZE << 1)];
		GroupState = new int[(MacrosBase.MAX_SENTENCE_SIZE << 1)];
		
		BeState = new boolean[MacrosBase.MAX_SENTENCE_SIZE];
		InGroup = new int[MacrosBase.MAX_SENTENCE_SIZE][Clog2[MacrosBase.MAX_SENTENCE_SIZE]][Clog2[MacrosBase.MAX_SENTENCE_SIZE]];
		BetweenGroup = new int[MacrosBase.MAX_SENTENCE_SIZE][Clog2[MacrosBase.MAX_SENTENCE_SIZE]];
		
		POSPath = new String[MacrosBase.MAX_SENTENCE_SIZE][MacrosBase.MAX_SENTENCE_SIZE];
		LabelPath = new String[MacrosBase.MAX_SENTENCE_SIZE][MacrosBase.MAX_SENTENCE_SIZE];
	}
	
	private void loadTree(ArrayList<Integer> heads) {
		
		int root = analyzeRoot(heads);
		
		size = heads.size();
		eular_size = (size << 1) - 1;
		int log_eular_size = ceillog2(eular_size);
		block_size = (log_eular_size >> 1) == 0 ? eular_size : log_eular_size >> 1;
		eular_size = eular_size % block_size == 0 ? eular_size : (eular_size / block_size + 1) * block_size;
		block_count = eular_size / block_size;
		int log_block_size = ceillog2(block_count);
		
		for (int i = 0; i < size; ++i) Index[i] = eular_size;
		ArrayList<Queue<Integer>> children = new ArrayList<Queue<Integer>>();
		for (int i = 0; i < size; ++i) children.add(new LinkedList<Integer>());
		for (int i = 0; i < size; ++i) if (i != root) children.get(heads.get(i).intValue()).offer(MacrosBase.integer_cache[i]);
		
		int seek = root;
		int pivot = 0;
		int depth = 0;
		while (!children.get(root).isEmpty()) {
			Eular[pivot] = seek;
			Depth[pivot] = depth;
			Index[seek] = Math.min(Index[seek], pivot);
			++pivot;
			if (children.get(seek).isEmpty()) {
				seek = heads.get(seek).intValue();
				children.get(seek).poll();
				--depth;
			} else {
				++depth;
				seek = children.get(seek).peek().intValue();
			}
		}
		
		while (++pivot < eular_size) {
			Depth[pivot] = Depth[pivot - 1] + 1;
		}
		
		for (int i = 0, n = 1 << block_size; i < n; ++i) {
			BeState[i] = false;
		}
		
		for (int i = 0, p = 0; i < block_count; ++i) {
			int n = p + block_size, s = 1, min = Depth[p], idx = p;
			
			while (++p < n) {
				
				s <<= 1;
				if (Depth[p] > Depth[p - 1]) {
					++s;
				}
				else if (min > Depth[p]) {
					min = Depth[p];
					idx = p;
				}
			}
			
			GroupMin[i] = min;
			GroupIndex[i] = idx;
			GroupState[i] = s;
			
			p -= block_size;
			
			if (BeState[s] == false) {
				BeState[s] = true;
				for (int j = 0; j < block_size; ++j) {
					InGroup[s][j][j] = j;
				}
				for (int l = 1; l < block_size; ++l) {
					for (int j = 0, m = block_size - l; j < m; ++j) {
						int k = InGroup[s][j][j + l - 1];
						InGroup[s][j][j + l] = Depth[j + l + p] < Depth[k + p] ? j + l : k;
					}
				}
			}
			
			p += block_size;
		}
		
		for (int i = 0; i < block_count; ++i) {
			BetweenGroup[i][0] = i;
			
		}
		
		for (int l = 1; l < log_block_size; ++l) {
			for (int i = 0, n = block_count - (1 << l); i <= n; ++i) {
				int j = BetweenGroup[i][l - 1];
				int k = BetweenGroup[i + (1 << (l - 1))][l - 1];
				BetweenGroup[i][l] = GroupMin[j] < GroupMin[k] ? j : k;				
			}
		}
		
	}
	
	private int query(int i, int j) {
		int l = Index[i], r = Index[j];
		if (l > r) {
			int temp = l;
			l = r;
			r = temp;
		}
		int sl = l / block_size, sr = r / block_size;
		if (sl == sr) {
			int base = sl * block_size;
			return Eular[InGroup[GroupState[sl]][l - base][r - base] + base];
		} else {
			int basel = sl * block_size;
			int baser = sr * block_size;
			int ml = InGroup[GroupState[sl]][l - basel][block_size - 1] + basel;
			int mr = InGroup[GroupState[sr]][0][r - baser] + baser;
			int mi;
			if (Depth[ml] < Depth[mr]) {
				mi = ml;
			} else {
				mi = mr;
			}
			if (sr - sl == 1) {
				return Eular[mi];
			} else {
				int l2 = Flog2[sr - sl - 1];
				int mt = BetweenGroup[sl + 1][l2];
				if (GroupMin[mt] < Depth[mi]) {
					mi = GroupIndex[mt];
				}
				mt = BetweenGroup[sr - (1 << l2)][l2];
				if (GroupMin[mt] < Depth[mi]) {
					mi = GroupIndex[mt];
				}
				return Eular[mi];
			}
		}
	}
	
	public void loadPath(ArrayList<POSTaggedWord> sentence, IntIntegerVector syntaxtree) {
		ArrayList<Integer> heads = new ArrayList<Integer>();
		for (int i = 0, n = syntaxtree.size(); i < n; ++i) {
			heads.add(MacrosBase.integer_cache[syntaxtree.get(i).m_index]);
		}
		loadTree(heads);
		for (int i = 0, n = syntaxtree.size(); i < n; ++i) {
			for (int j = i + 1; j < n; ++j) {
				int si = i, sj = j;
				int r = query(si, sj);
				String lp = "";
				String ll = "";
				while (si != r) {
					lp += sentence.get(si).tag.toString().substring(0, 1) + "#";
					ll += syntaxtree.get(si).m_label.toString() + "#";
					si = heads.get(si).intValue();
				}
				if (r == syntaxtree.size()) {
					lp += "n#";
					ll += "n#";
				} else {
					lp += sentence.get(si).tag.toString().substring(0, 1) + "#";
					ll += syntaxtree.get(si).m_label.toString() + "#";
				}
				String rp = "";
				String rl = "";
				while (sj != r) {
					rp = sentence.get(sj).tag.toString().substring(0, 1) + "#" + rp;
					rl = syntaxtree.get(sj).m_label.toString() + "#" + rl;
					sj = heads.get(sj).intValue();
				}
				POSPath[i][j] = lp + rp;
				LabelPath[i][j] = ll + rl;
			}
		}
	}
}
