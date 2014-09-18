package include;

import include.linguistics.TwoStrings;
import include.linguistics.TwoStringsVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import common.parser.MacrosBase;

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
	
}
