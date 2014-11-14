package include.util;

public abstract class Tuple4<Object1, Object2, Object3, Object4> {
	protected int m_nHash;
	protected Object1 m_object1;
	protected Object2 m_object2;
	protected Object3 m_object3;
	protected Object4 m_object4;
	
	public abstract Object1 create_object1(Object1 object1);
	public abstract Object2 create_object2(Object2 object2);
	public abstract Object3 create_object3(Object3 object3);
	public abstract Object4 create_object4(Object4 object4);
	
	public Tuple4() {
		m_object1 = null;
		m_object2 = null;
		m_object3 = null;
		m_object4 = null;
		m_nHash = 0;
	}
	
	public Tuple4(final Tuple4<Object1, Object2, Object3, Object4> tuple4) {
		refer(tuple4.m_object1, tuple4.m_object2, tuple4.m_object3, tuple4.m_object4);
	}
	
	public Tuple4(final Object1 object1, final Object2 object2, final Object3 object3, final Object4 object4) {
		m_object1 = create_object1(object1);
		m_object2 = create_object2(object2);
		m_object3 = create_object3(object3);
		m_object4 = create_object4(object4);
		computehash();
	}
	
	public final void refer(final Object1 object1, final Object2 object2, final Object3 object3, final Object4 object4) {
		m_object1 = object1;
		m_object2 = object2;
		m_object3 = object3;
		m_object4 = object4;
		computehash();
	}
	
	public final Object1 first() {
		return m_object1;
	}
	
	public final Object2 second() {
		return m_object2;
	}
	
	public final Object3 third() {
		return m_object3;
	}
	
	public final Object4 forth() {
		return m_object4;
	}
	
	@Override
	public final int hashCode() {
		return m_nHash;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean equals(final Object o) {
		Tuple4<Object1, Object2, Object3, Object4> tuple4 = (Tuple4<Object1, Object2, Object3, Object4>)o;
		return m_object1.equals(tuple4.m_object1) &&
				m_object2.equals(tuple4.m_object2) &&
				m_object3.equals(tuple4.m_object3) &&
				m_object4.equals(tuple4.m_object4);
	}
	
	protected final void computehash() {
		int code1 = m_object1.hashCode(), code2 = m_object2.hashCode(), code3 = m_object3.hashCode();
		int code = (code1 << 5 - code1) + code2;
		code = (code << 5) - (code << 1) - code + code3;
		m_nHash = (code << 4) + (code << 3) - code + m_object4.hashCode();
	}
}
