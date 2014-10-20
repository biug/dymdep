package include.linguistics;

/*
 * @author ZhangXun
 */

public final class Word {
	
	private final static int UNKNOWN = 0;
	private final static int EMPTY = 1;
	
	private static StringTokenizer tokenizer = new StringTokenizer();
	
	protected int m_nHash;
	
	public Word() {
		m_nHash = EMPTY;
	}
	
	public Word(final String s) {
		m_nHash = tokenizer.lookup(s);
	}
	
	public Word(final Word word) {
		m_nHash = word.m_nHash;
	}
	
	public Word(final int code) {
		m_nHash = code;
	}
	
	@Override
	public int hashCode() {
		return m_nHash;
	}
	
	@Override
	public boolean equals(final Object o) {
		return m_nHash == ((Word)o).m_nHash;
	}
	
	public void setString(final String s) {
		m_nHash = tokenizer.find(s, UNKNOWN);
	}
	
	public void load(final String s) {
		m_nHash = tokenizer.lookup(s);
	}
	
	@Override
	public String toString() {
		return tokenizer.key(m_nHash);
	}
	
	public boolean empty() {
		return m_nHash == EMPTY;
	}
	
	public boolean unknown() {
		return m_nHash == UNKNOWN;
	}
	
}
