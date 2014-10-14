package common.parser.implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import common.dependency.label.DependencyLabel;
import common.parser.DependencyGraphBase;
import common.parser.MacrosBase;
import common.pos.TreeTag;

public class DependencyDag extends DependencyGraphBase {
	
	public static final int DAG_WORD_COL = 1;
	public static final int DAG_POS_COL = 3;
	public static final int DAG_CCG_COL = 7;
	public static final int DAG_TREEHEAD_COL = 8;
	public static final int DAG_TREELABEL_COL = 9;
	public static final int DAG_HEAD_COL = 10;
	public static final String DAG_EMPTY_STRING = "_";
	
	public DependencyDag() {
		length = 0;
		nodes = new DependencyDagNode[MacrosCCGDag.MAX_SENTENCE_SIZE];
	}
	
	@Override
	public boolean readSentenceFromInputStream(BufferedReader br) throws IOException {
		length = 0;
		String line;
		ArrayList<String[]> lines = new ArrayList<String[]>();
		line = br.readLine();
		while (line != null && !line.isEmpty()) {
			lines.add(line.trim().split("[ \t]+"));
			line = br.readLine();
			++length;
		}
		int k = DAG_HEAD_COL + 1;
		for (int now = 0; now < length; ++now) {
			nodes[now] = new DependencyDagNode(lines.get(now)[DAG_WORD_COL], lines.get(now)[DAG_POS_COL],
					Integer.parseInt(lines.get(now)[DAG_TREEHEAD_COL]),	MacrosBase.integer_cache[TreeTag.code(lines.get(now)[DAG_TREELABEL_COL])],
					lines.get(now)[DAG_CCG_COL]);
		}
		for (int now = 0; now < length; ++now) {
			if (!lines.get(now)[DAG_HEAD_COL].equals(DAG_EMPTY_STRING)) {
				for (int other = 0; other < length; ++other) {
					if (!lines.get(other)[k].equals(DAG_EMPTY_STRING)) {
						int label = DependencyLabel.code(lines.get(other)[k]);
						int direction = now < other ? MacrosCCGDag.RIGHT_DIRECTION : MacrosCCGDag.LEFT_DIRECTION;
						((DependencyDagNode)nodes[other]).heads.add(new Arc(now, label, direction));
						((DependencyDagNode)nodes[now]).children.add(new Arc(other, label, direction));
						if (direction == MacrosCCGDag.RIGHT_DIRECTION) {
							((DependencyDagNode)nodes[now]).rightarcs.add(new Arc(other, label, direction));
							++((DependencyDagNode)nodes[now]).righttail;
						} else {
							((DependencyDagNode)nodes[other]).rightarcs.add(new Arc(now, label, direction));
							++((DependencyDagNode)nodes[other]).righttail;
						}
					}
				}
				++k;
			}
		}
		for (int now = 0; now < length; ++now) {
			Collections.sort(((DependencyDagNode)nodes[now]).rightarcs);
		}
		return line != null;
	}

	@Override
	public void writeSentenceToOutputStream(BufferedWriter bw) throws IOException {
		TreeSet<Integer> heads = new TreeSet<Integer>();
		for (int i = 0; i < length; ++i) {
			DependencyDagNode node = (DependencyDagNode)nodes[i];
			for (int j = 0, n = node.rightarcs.size(); j < n; ++j) {
				Arc arc = node.rightarcs.get(j);
				int head = (arc.direction == MacrosCCGDag.RIGHT_DIRECTION ? i : arc.other);
				heads.add(MacrosBase.integer_cache[head]);
			}
		}
		int head_count = 0;
		Iterator<Integer> itr = heads.iterator();
		HashMap<Integer, Integer> heads_map = new HashMap<Integer, Integer>();
		while (itr.hasNext()) {
			heads_map.put(itr.next(), MacrosBase.integer_cache[head_count++]);
		}
		String[] line = new String[head_count];
		for (int i = 0; i < length; ++i) {
			for (int j = 0; j < head_count; ++j) {
				line[j] = "_";
			}
			DependencyDagNode node = (DependencyDagNode)nodes[i];
			bw.write(String.valueOf(i + 1));
			bw.write(" " + node.word + " " + node.word + " " + node.postag + " " + node.postag + " _ _ " + node.ccgtag + Integer.toString(node.treehead) + " " + TreeTag.str(node.treelabel.intValue()) + " ");
			bw.write(heads.contains(MacrosBase.integer_cache[i]) ? node.word : "_");
			Arc arc = null;
			for (int j = 0; j < i; ++j) {
				boolean find = false;
				DependencyDagNode subnode = (DependencyDagNode)nodes[j];
				for (int k = 0, n = subnode.rightarcs.size(); k < n; ++k) {
					arc = subnode.rightarcs.get(k);
					if (arc.other == i && arc.direction == MacrosCCGDag.RIGHT_DIRECTION) {
						find = true;
						break;
					}
				}
				if (find) {
//					arc.print(j);
					line[heads_map.get(MacrosBase.integer_cache[j]).intValue()] = DependencyLabel.str(arc.label);
				}
			}
			for (int j = 0, n = node.rightarcs.size(); j < n; ++j) {
				arc = node.rightarcs.get(j);
				if (arc.direction == MacrosCCGDag.LEFT_DIRECTION) {
//					arc.print(i);
					line[heads_map.get(MacrosBase.integer_cache[arc.other]).intValue()] = DependencyLabel.str(arc.label);
				}
			}
			for (int j = 0; j < head_count; ++j) {
				bw.write(" " + line[j]);
			}
			bw.newLine();
		}
		bw.newLine();
	}
	
	public void print() {
		for (int i = 0; i < length; ++i) {
			System.out.println(nodes[i].word + " " + nodes[i].postag + " " + ((DependencyDagNode)nodes[i]).ccgtag);
			DependencyDagNode node = (DependencyDagNode)nodes[i];
			Iterator<Arc> itr = node.rightarcs.iterator();
			while (itr.hasNext()) {
				Arc arc = itr.next();
				arc.print(i);
			}
			System.out.println();
		}
	}

}
