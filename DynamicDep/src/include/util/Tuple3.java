package include.util;

/*
 * @author ZhangXun
 */

public abstract class Tuple3<Object1, Object2, Object3> {
	protected int m_nHash;
	protected Object1 m_object1;
	protected Object2 m_object2;
	protected Object3 m_object3;
	
	public abstract Object1 create_object1(Object1 object1);
	public abstract Object2 create_object2(Object2 object2);
	public abstract Object3 create_object3(Object3 object3);
	
	public Tuple3() {
		m_object1 = null;
		m_object2 = null;
		m_object3 = null;
		m_nHash = 0;
	}
	
	public Tuple3(final Tuple3<Object1, Object2, Object3> tuple3) {
		refer(tuple3.m_object1, tuple3.m_object2, tuple3.m_object3);
	}
	
	public Tuple3(final Object1 object1, final Object2 object2, final Object3 object3) {
		m_object1 = create_object1(object1);
		m_object2 = create_object2(object2);
		m_object3 = create_object3(object3);
		computehash();
	}
	
	public final void refer(final Object1 object1, final Object2 object2, final Object3 object3) {
		m_object1 = object1;
		m_object2 = object2;
		m_object3 = object3;
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
	
	@Override
	public final int hashCode() {
		return m_nHash;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean equals(final Object o) {
		Tuple3<Object1, Object2, Object3> tuple3 = (Tuple3<Object1, Object2, Object3>)o;
		return m_object1.equals(tuple3.m_object1) &&
				m_object2.equals(tuple3.m_object2) &&
				m_object3.equals(tuple3.m_object3);
	}
	
	protected final void computehash() {
		int code1 = m_object1.hashCode(), code2 = m_object2.hashCode();
		m_nHash = (code1 << 5) - code1 +
				(code2 << 5) - (code2 << 1) - code2 +
				m_object3.hashCode(); 
	}
}
