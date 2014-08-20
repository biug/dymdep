package include;

import common.parser.implementations.arceager.ScoredAction;

/*
 * agenda for ScoredAction
 * @author ZhangXun
 */

public final class AgendaSimple {
	private int m_nMaxSize;
	private ScoredAction m_lBeam[];
	private int m_nBeamSize;
	private boolean m_bItemSorted;
	
	public AgendaSimple(final int nBeamSize) {
		m_nMaxSize = nBeamSize;
		m_lBeam = new ScoredAction[nBeamSize];
		for (int i = 0; i < nBeamSize; ++i) {
			m_lBeam[i] = new ScoredAction();
		}
		clear();
	}
	
	public void clear() {
		m_nBeamSize = 0;
		m_bItemSorted = false;
	}
	
	public ScoredAction item(final int index) {
		return m_lBeam[index];
	}
	
	public int size() {
		return m_nBeamSize;
	}
	
	public void insertItem(final ScoredAction item) {
		if (m_nBeamSize == m_nMaxSize) {
			if (item.more(m_lBeam[0])) {
				pop_heap();
			} else {
				return;
			}
		}
		m_lBeam[m_nBeamSize].copy(item);
		push_heap(m_nBeamSize++);
	}
	
	public void sortItems() {
		int size = m_nBeamSize;
		while (m_nBeamSize > 0) {
			pop_heap();
		}
		m_nBeamSize = size;
		m_bItemSorted = true;
	}
	
	public ScoredAction bestItem(final int index) {
		if (!m_bItemSorted) {
			sortItems();
		}
		return m_lBeam[index];
	}
	
	public ScoredAction bestItem() {
		return this.bestItem(0);
	}
	
	private void swap(final int index1, final int index2) {
		ScoredAction sa = m_lBeam[index1];
		m_lBeam[index1] = m_lBeam[index2];
		m_lBeam[index2] = sa;
	}
	
	private void push_heap(int base) {
		while (base > 0) {
			if (m_lBeam[(base - 1) >> 1].more(m_lBeam[base])) {
				swap(base, (base - 1) >> 1);
				base = (base - 1) >> 1;
			} else {
				break;
			}
		}
	}
	
	private void pop_heap() {
		if (m_nBeamSize <= 0) return;
		swap(0, --m_nBeamSize);
		ScoredAction action = m_lBeam[0];
		int index = 0, child = 1;
		while (child < m_nBeamSize) {
			if (child + 1 < m_nBeamSize && !m_lBeam[child + 1].more(m_lBeam[child])) {
				++child;
			}
			m_lBeam[index] = m_lBeam[child];
			index = child;
			child = (child << 1) + 1;
		}
		m_lBeam[index] = action;
		push_heap(index);
	}
}