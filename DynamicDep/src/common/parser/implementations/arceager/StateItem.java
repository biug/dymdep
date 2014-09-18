package common.parser.implementations.arceager;

import include.linguistics.SetOfLabels;
import include.linguistics.POSTaggedWord;
import include.linguistics.TwoStringsVector;

import java.util.ArrayList;

import common.dependency.label.DependencyLabel;
import common.parser.DependencyGraphBase;
import common.parser.StateItemBase;
import common.parser.implementations.DependencyTree;
import common.parser.implementations.DependencyTreeNode;
import common.parser.implementations.MacrosTree;

/*
 * @author ZhangXun
 */

public class StateItem extends StateItemBase {
	
	public long score;
	
	public final int OFF_STACK = 0;
	public final int ON_STACK_SHIFT = 1;
	public final int ON_STACK_ARCRIGHT = 2;
	
	private int stack_back;
	private int headstack_back;

	protected int m_nNextWord;
	
	protected int[] m_Stack;
	protected int[] m_HeadStack;
	protected int[] m_lHeads;
	protected int[] m_lDepsL;
	protected int[] m_lDepsR;
	protected int[] m_lDepNumL;
	protected int[] m_lDepNumR;
	
	protected SetOfLabels m_lDepTagL[];
	protected SetOfLabels m_lDepTagR[];
	protected int m_lSibling[];
	
	protected int m_nLastAction;
	protected ArrayList<POSTaggedWord> m_lCache;
	
	protected int m_lLabels[];
	
	public StateItem(ArrayList<POSTaggedWord> cache) {
		stack_back = -1;
		headstack_back = -1;
		m_Stack = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_HeadStack = new int[MacrosTree.MAX_SENTENCE_SIZE << 1];
		m_lHeads = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_lDepsL = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_lDepsR = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_lDepNumL = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_lDepNumR = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_lDepTagL = new SetOfLabels[MacrosTree.MAX_SENTENCE_SIZE];
		m_lDepTagR = new SetOfLabels[MacrosTree.MAX_SENTENCE_SIZE];
		for (int i = 0; i < MacrosTree.MAX_SENTENCE_SIZE; ++i) {
			m_lDepTagL[i] = new SetOfLabels();
			m_lDepTagR[i] = new SetOfLabels();
		}
		m_lSibling = new int[MacrosTree.MAX_SENTENCE_SIZE];
		m_lCache = cache;
		m_lLabels = new int[MacrosTree.MAX_SENTENCE_SIZE];
		clear();
	}
	
	@Override
	public final boolean more(final StateItemBase itembase) {
		return score > ((StateItem)itembase).score;
	}
	
	@Override
	public void copy(final StateItemBase itembase) {
		int length;
		StateItem item = (StateItem)itembase;
		stack_back = item.stack_back;
		headstack_back = item.headstack_back;
		System.arraycopy(item.m_Stack, 0, m_Stack, 0, stack_back + 1);
		System.arraycopy(item.m_HeadStack, 0, m_HeadStack, 0, headstack_back + 1);
		m_nNextWord = item.m_nNextWord;
		m_nLastAction = item.m_nLastAction;
		m_lCache = item.m_lCache;
		score = item.score;
		length = m_nNextWord + 1;
		System.arraycopy(item.m_lHeads, 0, m_lHeads, 0, length);
		System.arraycopy(item.m_lDepsL, 0, m_lDepsL, 0, length);
		System.arraycopy(item.m_lDepsR, 0, m_lDepsR, 0, length);
		System.arraycopy(item.m_lDepNumL, 0, m_lDepNumL, 0, length);
		System.arraycopy(item.m_lDepNumR, 0, m_lDepNumR, 0, length);
		System.arraycopy(item.m_lSibling, 0, m_lSibling, 0, length);
		System.arraycopy(item.m_lLabels, 0, m_lLabels, 0, length);
		for (int i = 0; i < length; ++i) {
			m_lDepTagL[i].copy(item.m_lDepTagL[i]);
			m_lDepTagR[i].copy(item.m_lDepTagR[i]);
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
			if (m_lHeads[i] != item.m_lHeads[i]) {
				return false;
			}
		}
		for (int i = 0; i < m_nNextWord; ++i) {
			if (m_lLabels[i] != item.m_lLabels[i]) {
				return false;
			}
		}
		if (stack_back != item.stack_back) {
			return false;
		}
		if (stack_back > 0 && m_Stack[stack_back] != item.m_Stack[stack_back]) {
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
		return m_Stack[stack_back];
	}
	
	public final int stackbottom() {
		return m_Stack[0];
	}
	
	public final boolean headstackempty() {
		return headstack_back == -1;
	}
	
	public final int headstacktop() {
		return m_HeadStack[headstack_back];
	}
	
	public final int headstacksize() {
		return headstack_back + 1;
	}
	
	public final boolean afterreduce() {
		return Action.getUnlabeledAction(m_nLastAction) == MacrosTree.REDUCE;
	}
	
	public final int head(final int index) {
		return m_lHeads[index];
	}
	
	public final int leftdep(final int index) {
		return m_lDepsL[index];
	}
	
	public final int rightdep(final int index) {
		return m_lDepsR[index];
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
		return m_lLabels[index];
	}
	
	public void clear() {
		m_nNextWord = 0;
		stack_back = -1;
		headstack_back = -1;
		score = 0;
		m_nLastAction = MacrosTree.NO_ACTION;
		ClearNext();
	}
	
	public void ArcLeft(int lab) {
		int left = m_Stack[stack_back--];
		--headstack_back;
		m_lHeads[left] = m_nNextWord;
		m_lLabels[left] = lab;
		m_lDepTagL[m_nNextWord].add(lab);
		m_lSibling[left] = m_lDepsL[m_nNextWord];
		m_lDepsL[m_nNextWord] = left;
		++m_lDepNumL[m_nNextWord];
		m_nLastAction = Action.encodeAction(MacrosTree.ARC_LEFT, lab);
	}
	
	public void ArcRight(int lab) {
		int left = m_Stack[stack_back];
		m_Stack[++stack_back] = m_nNextWord;
		m_lHeads[m_nNextWord] = left;
		m_lLabels[m_nNextWord] = lab;
		m_lDepTagR[left].add(lab);
		m_lSibling[m_nNextWord] = m_lDepsR[left];
		m_lDepsR[left] = m_nNextWord++;
		++m_lDepNumR[left];
		ClearNext();
		m_nLastAction = Action.encodeAction(MacrosTree.ARC_RIGHT, lab);
	}

	public void Shift() {
		m_Stack[++stack_back] = m_nNextWord;
		m_HeadStack[++headstack_back] = m_nNextWord++;
		ClearNext();
		m_nLastAction = Action.encodeAction(MacrosTree.SHIFT);
	}
	
	public void Reduce() {
		--stack_back;
		m_nLastAction = Action.encodeAction(MacrosTree.REDUCE);
	}
	
	public void PopRoot() {
		m_lLabels[m_Stack[stack_back--]] = MacrosTree.DEP_ROOT;
		m_nLastAction = Action.encodeAction(MacrosTree.POP_ROOT);
	}
	
	public void ClearNext() {
		m_lHeads[m_nNextWord] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepsL[m_nNextWord] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepsR[m_nNextWord] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepNumL[m_nNextWord] = 0;
		m_lDepNumR[m_nNextWord] = 0;
		m_lDepTagL[m_nNextWord].clear();
		m_lDepTagR[m_nNextWord].clear();
		m_lSibling[m_nNextWord] = DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD;
		m_lLabels[m_nNextWord] = MacrosTree.DEP_NONE;
	}
	
	@Override
	public void Move(final int action) {
		switch (Action.getUnlabeledAction(action)) {
		case MacrosTree.NO_ACTION:
			return;
		case MacrosTree.SHIFT:
			Shift();
			return;
		case MacrosTree.REDUCE:
			Reduce();
			return;
		case MacrosTree.ARC_LEFT:
			ArcLeft(Action.getLabel(action));
			return;
		case MacrosTree.ARC_RIGHT:
			ArcRight(Action.getLabel(action));
			return;
		case MacrosTree.POP_ROOT:
			PopRoot();
			return;
		}
	}
	
	@Override
	public void StandardMoveStep(final DependencyGraphBase graph, final ArrayList<DependencyLabel> m_lCacheLabel) {
		int top;
		DependencyTree tree = (DependencyTree)graph;
		if (m_nNextWord == tree.length) {
			if (stack_back > 0) {
				Reduce();
				return;
			} else {
				PopRoot();
				return;
			}
		}
		if (stack_back >= 0) {
			top = m_Stack[stack_back];
			while (!(m_lHeads[top] == DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD)) {
				top = m_lHeads[top];
			}
			if (((DependencyTreeNode)tree.nodes[top]).head == m_nNextWord) {
				if (top == m_Stack[stack_back]) {
					ArcLeft(m_lCacheLabel.get(top).hashCode());
					return;
				} else {
					Reduce();
					return;
				}
			}
		}
		if (((DependencyTreeNode)tree.nodes[m_nNextWord]).head == DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD ||
				((DependencyTreeNode)tree.nodes[m_nNextWord]).head > m_nNextWord) {
			Shift();
			return;
		} else {
			top = m_Stack[stack_back];
			if (((DependencyTreeNode)tree.nodes[m_nNextWord]).head == top) {
				ArcRight(m_lCacheLabel.get(m_nNextWord).hashCode());
			} else {
				Reduce();
			}
		}
	}
	
	@Override
	public void StandardFinish() {
		assert (stack_back == -1);
	}
	
	@Override
	public int FollowMove(final StateItemBase itembase) {
		int top;
		StateItem item = (StateItem)itembase;
		if (m_nNextWord == item.m_nNextWord) {
			top = m_Stack[stack_back];
			if (item.m_lHeads[top] == m_nNextWord) {
				return Action.encodeAction(MacrosTree.ARC_LEFT, item.m_lLabels[top]);
			} else if (item.m_lHeads[top] != DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD) {
				return Action.encodeAction(MacrosTree.REDUCE);
			} else {
				return Action.encodeAction(MacrosTree.POP_ROOT);
			}
		}
		if (stack_back >= 0) {
			top = m_Stack[stack_back];
			while (!(m_lHeads[top] == DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD)) {
				top = m_lHeads[top];
			}
			if (item.head(top) == m_nNextWord) {
				if (top == m_Stack[stack_back]) {
					return Action.encodeAction(MacrosTree.ARC_LEFT, item.m_lLabels[top]);
				} else {
					return Action.encodeAction(MacrosTree.REDUCE);
				}
			}
		}
		if (item.head(m_nNextWord) == DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD ||
				item.head(m_nNextWord) > m_nNextWord) {
			return Action.encodeAction(MacrosTree.SHIFT);
		} else {
			top = m_Stack[stack_back];
			if (item.head(m_nNextWord) == top) {
				return Action.encodeAction(MacrosTree.ARC_RIGHT, item.m_lLabels[m_nNextWord]);
			} else {
				return Action.encodeAction(MacrosTree.REDUCE);
			}
		}
	}
	
	public void GenerateTree(final TwoStringsVector input, DependencyGraphBase output) {
		output.length = 0;
		for (int i = 0, input_size = this.size(); i < input_size; ++i) {
			output.nodes[output.length++] = new DependencyTreeNode(input.get(i).m_string1, input.get(i).m_string2, m_lHeads[i], DependencyLabel.str(m_lLabels[i]));
		}
	}

}