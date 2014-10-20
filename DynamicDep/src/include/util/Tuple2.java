package include.util;

/*
 * @author ZhangXun
 */

public abstract class Tuple2<Object1, Object2> {
	protected int m_nHash;
	protected Object1 m_object1;
	protected Object2 m_object2;
	
	public abstract Object1 create_object1(final Object1 a);
	public abstract Object2 create_object2(final Object2 b);
	
	public Tuple2() {
		m_object1 = null;
		m_object2 = null;
		m_nHash = 0;
	}
	
	public Tuple2(final Tuple2<Object1, Object2> tuple2) {
		refer(tuple2.m_object1, tuple2.m_object2);
	}
	
	public Tuple2(final Object1 object1, final Object2 object2) {
		m_object1 = create_object1(object1);
		m_object2 = create_object2(object2);
		computehash();
	}
	
	public final void refer(final Object1 object1, final Object2 object2) {
		m_object1 = object1;
		m_object2 = object2;
		computehash();
	}
	
	public final Object1 first() {
		return m_object1;
	}
	
	public final Object2 second() {
		return m_object2;
	}
	
	@Override
	public final int hashCode() {
		return m_nHash;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean equals(final Object o) {
		Tuple2<Object1, Object2> tuple2 = (Tuple2<Object1, Object2>)o;
		return m_object1.equals(tuple2.m_object1) &&
				m_object2.equals(tuple2.m_object2);
	}
	
	protected final void computehash() {
		int code1 = m_object1.hashCode();
		m_nHash = (code1 << 5) - code1 + m_object2.hashCode(); 
	}
}
