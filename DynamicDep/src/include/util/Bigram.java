package include.util;

/*
 * @author ZhangXun
 */

public abstract class Bigram<Unigram> {

	private int m_nHash;
	private Unigram m_unigram1, m_unigram2;
	
	protected abstract Unigram create_unigram(final Unigram unigram);
	
	public Bigram() {
		m_unigram1 = null;
		m_unigram2 = null;
		m_nHash = 0;
	}
	
	public Bigram(final Bigram<Unigram> bigram) {
		refer(bigram.m_unigram1, bigram.m_unigram2);
	}
	
	public Bigram(final Unigram unigram1, final Unigram unigram2) {
		m_unigram1 = create_unigram(unigram1);
		m_unigram2 = create_unigram(unigram2);
		computehash();
	}
	
	public final void refer(final Unigram unigram1, final Unigram unigram2) {
		m_unigram1 = unigram1;
		m_unigram2 = unigram2;
		computehash();
	}
	
	public final Unigram first() {
		return m_unigram1;
	}
	
	public final Unigram second() {
		return m_unigram2;
	}
	
	@Override
	public final int hashCode() {
		return m_nHash;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean equals(Object o) {
		Bigram<Unigram> bigram = (Bigram<Unigram>)o;
		return m_unigram1.equals(bigram.m_unigram1) &&
				m_unigram2.equals(bigram.m_unigram2);
	}
	
	protected final void computehash() {
		int code1 = m_unigram1.hashCode();
		m_nHash = (code1 << 5) - code1 + m_unigram2.hashCode();
	}
	
}
