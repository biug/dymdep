package common.parser.implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import common.parser.DependencyGraphBase;

public class DependencyDag extends DependencyGraphBase {
	
	public static final int DAG_WORD_COL = 1;
	public static final int DAG_POS_COL = 3;
	public static final int DAG_CCG_COL = 7;
	public static final int DAG_HEAD_COL = 10;
	public static final String DAG_EMPTY_STRING = "_";
	
	public DependencyDag() {
		nodes = new DependencyDagNode[MacrosTree.MAX_SENTENCE_SIZE];
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
			nodes[now] = new DependencyDagNode(lines.get(now)[DAG_WORD_COL], lines.get(now)[DAG_POS_COL], lines.get(now)[DAG_CCG_COL]);
			if (!lines.get(now)[DAG_HEAD_COL].equals(DAG_EMPTY_STRING)) {
				for (int other = 0; other < length; ++other) {
					if (!lines.get(other)[k].equals(DAG_EMPTY_STRING)) {
						int label = Integer.parseInt(lines.get(other)[k]);
						int direction = now < other ? MacrosDag.RIGHT_DIRECTION : MacrosDag.LEFT_DIRECTION;
						((DependencyDagNode)nodes[other]).heads.add(new Arc(now, label, direction));
						((DependencyDagNode)nodes[now]).children.add(new Arc(other, label, direction));
						if (direction == MacrosDag.RIGHT_DIRECTION) {
							((DependencyDagNode)nodes[now]).rightarcs.add(new Arc(other, label, direction));
						} else {
							((DependencyDagNode)nodes[other]).rightarcs.add(new Arc(now, label, direction));
						}
					}
				}
				++k;
			}
		}
		return line != null;
	}

	@Override
	public void writeSentenceToOutputStream(BufferedWriter bw) throws IOException {
		
	}

}
