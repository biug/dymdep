package common.parser;

import include.linguistics.DependencyTree;
import include.linguistics.DependencyTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class DependencyParser extends DependencyTree {
	
	public boolean readSentenceFromInputStream(BufferedReader br) throws IOException {
		this.clear();
		String line;
		line = br.readLine();
		while (line != null && !line.isEmpty()) {
			String[] args = line.split("\t");
			this.add(new DependencyTreeNode(args[0], args[1], Integer.parseInt(args[2]), args[3]));
			line = br.readLine();
		}
		return line != null;
	}
	
	public void writeSentenceToOutputStream(BufferedWriter bw) throws IOException {
		Iterator<DependencyTreeNode> itr = this.iterator();
		while (itr.hasNext()) {
			DependencyTreeNode node = itr.next();
			bw.write(node.toString());
			bw.newLine();
		}
		bw.newLine();
	}
	
}
