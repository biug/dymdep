package include.linguistics;

public final class CoNLLFeats extends GenericTag {
	
	private static GenericTagset tagset = new GenericTagset();
	
	public CoNLLFeats() {
		super();
	}
	
	public CoNLLFeats(final String s) {
		load(s);
	}
	
	public CoNLLFeats(final int i) {
		super(i);
	}
	
	public CoNLLFeats(final CoNLLFeats c) {
		super(c);
	}
	
	@Override
	public void load(final String s) {
		if (s.isEmpty()) {
			m_code = NONE;
		} else {
			m_code = tagset.lookup(s);
		}
	}
	
	@Override
	public String toString() {
		return tagset.key(m_code);
	}
	
}