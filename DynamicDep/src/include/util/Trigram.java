package include.util;

public abstract class Trigram<Unigram> {

	private int m_nHash;
	private Unigram m_unigram1, m_unigram2, m_unigram3;
	
	protected abstract Unigram create_unigram(final Unigram unigram);
	
	public Trigram() {
		m_unigram1 = null;
		m_unigram2 = null;
		m_unigram3 = null;
		m_nHash = 0;
	}
	
	public Trigram(final Trigram<Unigram> trigram) {
		refer(trigram.m_unigram1, trigram.m_unigram2, trigram.m_unigram3);
	}
	
	public Trigram(final Unigram unigram1, final Unigram unigram2, final Unigram unigram3) {
		m_unigram1 = create_unigram(unigram1);
		m_unigram2 = create_unigram(unigram2);
		m_unigram3 = create_unigram(unigram3);
		computehash();
	}
	
	public final void refer(final Unigram unigram1, final Unigram unigram2, final Unigram unigram3) {
		m_unigram1 = unigram1;
		m_unigram2 = unigram2;
		m_unigram3 = unigram3;
		computehash();
	}
	
	public final Unigram first() {
		return m_unigram1;
	}
	
	public final Unigram second() {
		return m_unigram2;
	}
	
	public final Unigram third() {
		return m_unigram3;
	}
	
	@Override
	public final int hashCode() {
		return m_nHash;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean equals(Object o) {
		Trigram<Unigram> trigram = (Trigram<Unigram>)o;
		return m_unigram1.equals(trigram.m_unigram1) &&
				m_unigram2.equals(trigram.m_unigram2) &&
				m_unigram3.equals(trigram.m_unigram3);
	}
	
	protected final void computehash() {
		int code1 = m_unigram1.hashCode(), code2 = m_unigram2.hashCode();
		int code = (code1 << 5 - code1) + code2;
		m_nHash = (code << 5) - (code << 2) - code + m_unigram3.hashCode();
	}
	
}
