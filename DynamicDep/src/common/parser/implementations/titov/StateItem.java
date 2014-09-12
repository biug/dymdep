package common.parser.implementations.titov;

import include.linguistics.SetOfLabels;
import include.linguistics.TaggedWord;
import include.linguistics.TwoStringsVector;

import java.util.ArrayList;
import java.util.Iterator;

import common.dependency.label.DependencyLabel;
import common.parser.DependencyGraphBase;
import common.parser.StateItemBase;
import common.parser.implementations.Arc;
import common.parser.implementations.DependencyDag;
import common.parser.implementations.DependencyDagNode;
import common.parser.implementations.DependencyTreeNode;
import common.parser.implementations.MacrosDag;

/*
 * @author ZhangXun
 */

public class StateItem extends StateItemBase {
	
	public final int OFF_STACK = 0;
	public final int ON_STACK_SHIFT = 1;
	public final int ON_STACK_ARCRIGHT = 2;
	
	protected int stack_back;
	protected int[] m_lHeadsBack;
	protected int[] m_lDepsLBack;
	protected int[] m_lDepsRBack;
	protected int[] m_lRightArcsBack;
	protected int[] m_lRightArcsSeek;
	
	protected int m_nNextWord;
	
	protected int[] m_lStack;		//stack
	protected int[][] m_lHeads;		//heads for every node
	protected int[][] m_lLabels;	//label for every node
	protected int[][] m_lDepsL;		//left dependency children
	protected int[][] m_lDepsR;		//right dependency children
	protected Arc[][] m_lRightArcs;	//right arcs
	protected int[] m_lDepNumL;		//left children number
	protected int[] m_lDepNumR;		//right children number
	
	protected SetOfLabels[] m_lDepTagL;
	protected SetOfLabels[] m_lDepTagR;
	protected int[] m_lSibling;
	
	protected int m_nLastAction;
	protected ArrayList<TaggedWord> m_lCache;
	
	public StateItem(ArrayList<TaggedWord> cache) {
		stack_back = -1;
		
		m_lHeadsBack = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lDepsLBack = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lDepsRBack = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lRightArcsBack = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lRightArcsSeek = new int[MacrosDag.MAX_SENTENCE_SIZE];
		
		m_lStack = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lHeads = new int[MacrosDag.MAX_SENTENCE_SIZE][];
		m_lLabels = new int[MacrosDag.MAX_SENTENCE_SIZE][];
		m_lDepsL = new int[MacrosDag.MAX_SENTENCE_SIZE][];
		m_lDepsR = new int[MacrosDag.MAX_SENTENCE_SIZE][];
		m_lRightArcs = new Arc[MacrosDag.MAX_SENTENCE_SIZE][];
		
		m_lDepNumL = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lDepNumR = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lDepTagL = new SetOfLabels[MacrosDag.MAX_SENTENCE_SIZE];
		m_lDepTagR = new SetOfLabels[MacrosDag.MAX_SENTENCE_SIZE];
		
		for (int i = 0; i < MacrosDag.MAX_SENTENCE_SIZE; ++i) {
			m_lHeadsBack[i] = m_lDepsLBack[i] = m_lDepsRBack[i] = m_lRightArcsBack[i] = -1;
			m_lRightArcsSeek[i] = 0;
			
			m_lHeads[i] = new int[MacrosDag.MAX_SENTENCE_SIZE];
			m_lLabels[i] = new int[MacrosDag.MAX_SENTENCE_SIZE];
			m_lDepsL[i] = new int[MacrosDag.MAX_SENTENCE_SIZE];
			m_lDepsR[i] = new int[MacrosDag.MAX_SENTENCE_SIZE];
			m_lRightArcs[i] = new Arc[MacrosDag.MAX_SENTENCE_SIZE];
			m_lDepTagL[i] = new SetOfLabels();
			m_lDepTagR[i] = new SetOfLabels();
		}
		m_lSibling = new int[MacrosDag.MAX_SENTENCE_SIZE];
		m_lCache = cache;
		clear();
	}
	
	@Override
	public final boolean more(final StateItemBase itembase) {
		return score > ((StateItem)itembase).score;
	}
	
	@Override
	public void copy(final StateItemBase itembase) {
		StateItem item = (StateItem)itembase;
		score = item.score;
		stack_back = item.stack_back;
		m_nNextWord = item.m_nNextWord;
		m_nLastAction = item.m_nLastAction;
		m_lCache = item.m_lCache;
		System.arraycopy(item.m_lStack, 0, m_lStack, 0, stack_back + 1);
		int length = m_nNextWord + 1;
		if (length > 0) {
			System.arraycopy(item.m_lHeadsBack, 0, m_lHeadsBack, 0, length);
			System.arraycopy(item.m_lDepsLBack, 0, m_lDepsLBack, 0, length);
			System.arraycopy(item.m_lDepsRBack, 0, m_lDepsRBack, 0, length);
			System.arraycopy(item.m_lRightArcsBack, 0, m_lRightArcsBack, 0, length);
			System.arraycopy(item.m_lRightArcsSeek, 0, m_lRightArcsSeek, 0, length);
			for (int i = 0; i < length; ++i) {
				if (m_lHeadsBack[i] >= 0) {
					System.arraycopy(item.m_lHeads[i], 0, m_lHeads[i], 0, m_lHeadsBack[i] + 1);
					System.arraycopy(item.m_lLabels[i], 0, m_lLabels[i], 0, m_lHeadsBack[i] + 1);
				}
				if (m_lDepsLBack[i] >= 0) System.arraycopy(item.m_lDepsL[i], 0, m_lDepsL[i], 0, m_lDepsLBack[i] + 1);
				if (m_lDepsRBack[i] >= 0) System.arraycopy(item.m_lDepsR[i], 0, m_lDepsR[i], 0, m_lDepsRBack[i] + 1);
				for (int j = 0; j <= m_lRightArcsBack[i]; ++j) {
					m_lRightArcs[i][j].copy(item.m_lRightArcs[i][j]);
				}
			}
			System.arraycopy(item.m_lDepNumL, 0, m_lDepNumL, 0, length);
			System.arraycopy(item.m_lDepNumR, 0, m_lDepNumR, 0, length);
			System.arraycopy(item.m_lSibling, 0, m_lSibling, 0, length);
			for (int i = 0; i < length; ++i) {
				m_lDepTagL[i].copy(item.m_lDepTagL[i]);
				m_lDepTagR[i].copy(item.m_lDepTagR[i]);
			}
		}
	}
	
	@Override
	public StateItemBase generateItem() {
		return new StateItem(null);
	}
	
	@Override
	public boolean equals(Object o) {
		StateItem item = (StateItem)o;
		if (m_nNextWord != item.m_nNextWord) {
			return false;
		}
		for (int i = 0; i < m_nNextWord; ++i) {
			for (int j = 0; j <= m_lHeadsBack[i]; ++j) {
				if (m_lHeads[i][j] != item.m_lHeads[i][j] || m_lLabels[i][j] != item.m_lLabels[i][j]) {
					return false;
				}
			}
		}
		if (stack_back != item.stack_back) {
			return false;
		}
		if (stack_back > 0 && m_lStack[stack_back] != item.m_lStack[stack_back]) {
			return false;
		}
		return true;
	}
	
	public final int stacksize() {
		return stack_back + 1;
	}
	
	public final boolean stackempty() {
		return stack_back == -1;
	}
	
	public final int stacktop() {
		return m_lStack[stack_back];
	}
	
	public final int stackitem(final int index) {
		return m_lStack[index];
	}
	
	public final boolean afterswap() {
		return Action.getUnlabeledAction(m_nLastAction) == MacrosDag.SWAP;
	}
	
	public final int head(final int index) {
		return m_lHeads[index][m_lHeadsBack[index]];
	}
	
	public final int leftdep(final int index) {
		return m_lDepsL[index][m_lDepsLBack[index]];
	}
	
	public final int rightdep(final int index) {
		return m_lDepsR[index][m_lDepsRBack[index]];
	}
	
	public final int sibling(final int index) {
		return m_lSibling[index];
	}
	
	public final int size() {
		return m_nNextWord;
	}
	
	public final int leftarity(final int index) {
		return m_lDepNumL[index];
	}
	
	public final int rightarity(final int index) {
		return m_lDepNumR[index];
	}
	
	public final SetOfLabels lefttagset(final int index) {
		return m_lDepTagL[index];
	}
	
	public final SetOfLabels righttagset(final int index) {
		return m_lDepTagR[index];
	}
	
	public final int label(final int index) {
		return m_lLabels[index][m_lHeadsBack[index]];
	}
	
	public final Arc nearestright(final int index) {
		return m_lRightArcs[index][m_lRightArcsBack[index]];
	}
	
	public void clear() {
		m_nNextWord = 0;
		stack_back = -1;
		score = 0;
		m_nLastAction = MacrosDag.NO_ACTION;
		ClearNext();
	}
	
	public void ArcLeft(int lab) {
		int left = m_lStack[stack_back];
		m_lHeads[left][++m_lHeadsBack[left]] = m_nNextWord;
		m_lLabels[left][m_lHeadsBack[left]] = lab;
		m_lDepTagL[m_nNextWord].add(lab);
		m_lSibling[left] = m_lDepsL[m_nNextWord][m_lDepsLBack[m_nNextWord]];
		m_lDepsL[m_nNextWord][++m_lDepsLBack[m_nNextWord]] = left;
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, lab, MacrosDag.LEFT_DIRECTION);
		++m_lDepNumL[m_nNextWord];
		m_nLastAction = Action.encodeAction(MacrosDag.ARC_LEFT, lab);
	}
	
	public void ArcRight(int lab) {
		int left = m_lStack[stack_back];
		m_lHeads[m_nNextWord][++m_lHeadsBack[m_nNextWord]] = left;
		m_lLabels[m_nNextWord][m_lHeadsBack[m_nNextWord]] = lab;
		m_lDepTagR[left].add(lab);
		m_lSibling[m_nNextWord] = m_lDepsR[left][m_lDepsRBack[left]];
		m_lDepsR[left][++m_lDepsRBack[left]] = m_nNextWord;
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, lab, MacrosDag.RIGHT_DIRECTION);
		++m_lDepNumR[left];
		m_nLastAction = Action.encodeAction(MacrosDag.ARC_RIGHT, lab);
	}

	public void Shift() {
		m_lStack[++stack_back] = m_nNextWord++;
		ClearNext();
		m_nLastAction = Action.encodeAction(MacrosDag.SHIFT);
	}
	
	public void Reduce() {
		--stack_back;
		m_nLastAction = Action.encodeAction(MacrosDag.REDUCE);
	}
	
	public void Swap() {
		int tail = m_lStack[stack_back];
		m_lStack[stack_back] = m_lStack[stack_back - 1];
		m_lStack[stack_back - 1] = tail;
	}
	
	public void ClearNext() {
		m_lHeadsBack[m_nNextWord] = m_lDepsLBack[m_nNextWord] = m_lDepsRBack[m_nNextWord] = 0;
		m_lHeads[m_nNextWord][0] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lLabels[m_nNextWord][0] = MacrosDag.DEP_NONE;
		m_lDepsL[m_nNextWord][0] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepsR[m_nNextWord][0] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepNumL[m_nNextWord] = 0;
		m_lDepNumR[m_nNextWord] = 0;
		m_lDepTagL[m_nNextWord].clear();
		m_lDepTagR[m_nNextWord].clear();
		m_lSibling[m_nNextWord] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
	}
	
	@Override
	public void Move(final int action) {
		switch (Action.getUnlabeledAction(action)) {
		case MacrosDag.NO_ACTION:
			return;
		case MacrosDag.SHIFT:
			Shift();
			return;
		case MacrosDag.REDUCE:
			Reduce();
			return;
		case MacrosDag.ARC_LEFT:
			ArcLeft(Action.getLabel(action));
			return;
		case MacrosDag.ARC_RIGHT:
			ArcRight(Action.getLabel(action));
			return;
		case MacrosDag.SWAP:
			Swap();
			return;
		}
	}
	
	@Override
	public void StandardMoveStep(final DependencyGraphBase graph, final ArrayList<DependencyLabel> m_lCacheLabel) {
		int top;
		DependencyDag dag = (DependencyDag)graph;
		if (stack_back >= 0) {
			top = m_lStack[stack_back];
			DependencyDagNode node = ((DependencyDagNode)dag.nodes[top]);
			if (node.rightseek > node.righttail) {
				Reduce();
				return;
			}
			Arc rightnode = node.NearestRight();
			if (rightnode.other == m_nNextWord) {
				++node.rightseek;
				if (rightnode.direction == MacrosDag.LEFT_DIRECTION) {
					ArcLeft(rightnode.label);
					++node.headsseek;
				} else {
					ArcRight(rightnode.label);
					++node.childrenseek;
				}
				return;
			} else if (stack_back >= 1) {
				int top2nd = m_lStack[stack_back - 1];
				Iterator<Arc> itrtop = node.rightarcs.iterator();
				Iterator<Arc> itrtop2nd = ((DependencyDagNode)dag.nodes[top2nd]).rightarcs.iterator();
				while (itrtop.hasNext() && itrtop2nd.hasNext()) {
					if (itrtop.next().more(itrtop2nd.next())) {
						Swap();
						return;
					}
				}
				if (itrtop.hasNext()) {
					Swap();
					return;
				}
			}
		}
		Shift();
	}
	
	@Override
	public void StandardFinish() {
		assert (stack_back == -1);
	}
	
	@Override
	public int FollowMove(final StateItemBase itembase) {
		StateItem item = (StateItem)itembase;
		if (stack_back >= 0) {
			int top = m_lStack[stack_back];
			if (item.m_lRightArcsBack[top] == -1) {
				return MacrosDag.REDUCE;
			}
			Arc rightarc = item.nearestright(top);
			if (m_nNextWord == rightarc.other) {
				if (rightarc.direction == MacrosDag.LEFT_DIRECTION) {
					return Action.encodeAction(MacrosDag.ARC_LEFT, rightarc.label);
				} else {
					return Action.encodeAction(MacrosDag.ARC_RIGHT, rightarc.label);
				}
			} else if (stack_back >= 1) {
				int top2nd = m_lStack[stack_back - 1];
				int itrtop = item.m_lRightArcsSeek[top], itrtop2nd = item.m_lRightArcsSeek[top2nd];
				int itrtoptail = item.m_lRightArcsBack[top], itrtop2ndtail = item.m_lRightArcsBack[top2nd];
				while (itrtop <= itrtoptail && itrtop2nd <= itrtop2ndtail) {
					if (item.m_lRightArcs[top][itrtop++].more(item.m_lRightArcs[top2nd][itrtop2nd++])) {
						return MacrosDag.SWAP;
					}
				}
				if (itrtop <= item.m_lRightArcsBack[top]) {
					return MacrosDag.SWAP;
				}
			}
		}
		return MacrosDag.SHIFT;
	}
	
	public void GenerateTree(final TwoStringsVector input, DependencyGraphBase output) {
		output.length = 0;
		for (int i = 0, input_size = this.size(); i < input_size; ++i) {
			DependencyDagNode node = new DependencyDagNode(input.get(i).m_string1, input.get(i).m_string2, "");
			for (int j = 0; j <= m_lRightArcsBack[i]; ++j) {
				node.rightarcs.add(new Arc(m_lRightArcs[i][j]));
			}
			output.nodes[output.length++] = node;
		}
	}
	
}