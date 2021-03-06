package include;

import include.linguistics.IntInteger;
import include.linguistics.IntIntegerVector;
import include.linguistics.TwoStrings;
import include.linguistics.TwoStringsVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import common.parser.MacrosBase;
import common.parser.implementations.DependencyDag;
import common.parser.implementations.MacrosCCGDag;
import common.pos.TreeTag;

/*
 * read a sentence
 * @author ZhangXun
 */

public final class SentenceReader {

	protected BufferedReader m_iReader;
	protected int m_nLine;
	
	public SentenceReader(final String sFileName) {
		m_nLine = 0;
		try {
			m_iReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sFileName)), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			m_iReader = null;
		}
	}
	
	public SentenceReader() {
		m_nLine = 0;
		m_iReader = null;
	}
	
	public boolean readTaggedSentence(TwoStringsVector vReturn) {
		if (m_iReader == null) return false;
		try {
			String line = m_iReader.readLine();
			if (line == null || line.isEmpty()) {
				m_iReader.close();
				return false;
			}
			line = line.trim();
			String[] taggedwords = line.split(" ");
			++m_nLine;
			vReturn.clear();
			int index;
			for (String taggedword : taggedwords) {
				if (taggedword.isEmpty()) break;
				index = taggedword.lastIndexOf(MacrosBase.SEPARTOR);
				vReturn.add(new TwoStrings(taggedword.substring(0, index), taggedword.substring(index + 1)));
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean readCONLL08Sentence(TwoStringsVector vReturn, IntIntegerVector tReturn) {
		if (m_iReader == null) return false;
		try {
			vReturn.clear();
			tReturn.clear();
			String line = m_iReader.readLine();
			while (line != null && !line.isEmpty()) {
				vReturn.add(new TwoStrings(line.trim().split("[ \t]+")[DependencyDag.DAG_WORD_COL], line.trim().split("[ \t]+")[DependencyDag.DAG_POS_COL]));
				tReturn.add(new IntInteger(Integer.parseInt(line.trim().split("[ \t]+")[DependencyDag.DAG_TREEHEAD_COL]), MacrosBase.integer_cache[TreeTag.code(line.trim().split("[ \t]+")[DependencyDag.DAG_TREELABEL_COL])]));
				line = m_iReader.readLine();
			}
			return line != null;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean readSuperTagSentence(ArrayList<int[]> superset) {
		if (m_iReader == null) return false;
		try {
			superset.clear();
			String line = m_iReader.readLine();
			while (line != null & !line.isEmpty()) {
				String[] args = line.split("[ \t]+");
				int size = (args.length >> 1) - 1;
				int[] list = new int[size];
				for (int i = 2, j = 0; i < args.length; i += 2) {
					Integer ii = MacrosCCGDag.CCGTAG_MAP.get(args[i]);
					if (ii == null) {
						--size;
					} else {
						list[j++] = ii.intValue();
					}
				}
				if (size == 0) {
					System.out.println("fuck");
				}
				int[] alist = new int[size];
				System.arraycopy(list, 0, alist, 0, size);
				superset.add(alist);
				line = m_iReader.readLine();
			}
			return line != null;
		} catch (IOException e) {
			return false;
		}
	}
	
}
