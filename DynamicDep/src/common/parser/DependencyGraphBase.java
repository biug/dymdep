package common.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class DependencyGraphBase {
	
	public int length;
	public DependencyNodeBase[] nodes;
	
	public abstract boolean readSentenceFromInputStream(BufferedReader br) throws IOException;
	public abstract void writeSentenceToOutputStream(BufferedWriter bw) throws IOException;
}
