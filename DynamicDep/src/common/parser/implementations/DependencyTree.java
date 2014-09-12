package common.parser.implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import common.parser.DependencyGraphBase;

public final class DependencyTree extends DependencyGraphBase {
	
	public DependencyTree() {
		nodes = new DependencyTreeNode[MacrosTree.MAX_SENTENCE_SIZE];
	}
	
	@Override
	public boolean readSentenceFromInputStream(BufferedReader br) throws IOException {
		length = 0;
		String line;
		line = br.readLine();
		while (line != null && !line.isEmpty()) {
			String[] args = line.split("\t");
			nodes[length++] = new DependencyTreeNode(args[0], args[1], Integer.parseInt(args[2]), args[3]);
			line = br.readLine();
		}
		return line != null;
	}
	
	@Override
	public void writeSentenceToOutputStream(BufferedWriter bw) throws IOException {
		for (int i = 0; i < length; ++i) {
			bw.write(nodes[i].toString());
			bw.newLine();
		}
		bw.newLine();
	}
	
}