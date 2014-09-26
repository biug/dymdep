package common.parser.implementations.titov;

import include.linguistics.SetOfLabels;
import include.linguistics.TwoStringsVector;

import java.util.ArrayList;

import common.dependency.label.DependencyLabel;
import common.parser.DependencyGraphBase;
import common.parser.StateItemBase;
import common.parser.implementations.Arc;
import common.parser.implementations.DependencyDag;
import common.parser.implementations.DependencyDagNode;
import common.parser.implementations.MacrosDag;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public class StateItem extends StateItemBase {
	
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
	protected int[] m_lCCGLabels;
	
	protected SetOfLabels[] m_lDepTagL;
	protected SetOfLabels[] m_lDepTagR;
	protected int[] m_lSibling;
	
	protected int[] m_lActionList;
	protected int action_back;
	
	public StateItem() {
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
		
		m_lCCGLabels = new int[MacrosDag.MAX_SENTENCE_SIZE];
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
			for (int j = 0; j < MacrosDag.MAX_SENTENCE_SIZE; ++j) {
				m_lRightArcs[i][j] = new Arc();
			}
		}
		m_lSibling = new int[MacrosDag.MAX_SENTENCE_SIZE];
		action_back = 0;
		m_lActionList = new int[MacrosDag.MAX_SENTENCE_SIZE * MacrosDag.MAX_SENTENCE_SIZE];
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
		action_back = item.action_back;
		System.arraycopy(item.m_lStack, 0, m_lStack, 0, stack_back + 1);
		System.arraycopy(item.m_lActionList, 0, m_lActionList, 0, action_back + 1);
		int length = m_nNextWord + 1;
		if (length > 0) {
			System.arraycopy(item.m_lHeadsBack, 0, m_lHeadsBack, 0, length);
			System.arraycopy(item.m_lDepsLBack, 0, m_lDepsLBack, 0, length);
			System.arraycopy(item.m_lDepsRBack, 0, m_lDepsRBack, 0, length);
			System.arraycopy(item.m_lCCGLabels, 0, m_lCCGLabels, 0, length);
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
			System.arraycopy(item.m_lSibling, 0, m_lSibling, 0, length);
			for (int i = 0; i < length; ++i) {
				m_lDepTagL[i].copy(item.m_lDepTagL[i]);
				m_lDepTagR[i].copy(item.m_lDepTagR[i]);
			}
		}
	}
	
	@Override
	public StateItemBase generateItem() {
		return new StateItem();
	}
	
	@Override
	public boolean equals(Object o) {
		StateItem item = (StateItem)o;
		if (action_back != item.action_back) {
			return false;
		}
		for (int i = action_back; i >= 0; --i) {
			if (m_lActionList[i] != item.m_lActionList[i]) {
				return false;
			}
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
	
	public final boolean canswap() {
		return m_lActionList[action_back] != MacrosDag.SWAP && stack_back > 0;
	}
	
	public final boolean canarc() {
		if (stack_back == -1) {
			return false;
		}
		int top = m_lStack[stack_back], back = m_lRightArcsBack[top];
		if (back == -1) return true;
		return m_lRightArcs[top][back].other != m_nNextWord;
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
		return m_lDepsLBack[index] + 1;
	}
	
	public final int rightarity(final int index) {
		return m_lDepsRBack[index] + 1;
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
	
	public final int ccg(final int index) {
		return m_lCCGLabels[index];
	}
	
	public final boolean rightarcempty(final int index) {
		return m_lRightArcsBack[index] == -1;
	}
	
	public final Arc nearestright(final int index) {
		return m_lRightArcs[index][m_lRightArcsSeek[index]];
	}
	
	public void clear() {
		//reset buffer seek
		m_nNextWord = 0;
		//reset stack seek
		stack_back = -1;
		//reset score
		score = 0;
		//reset action
		action_back = 0;
		m_lActionList[action_back] = MacrosDag.NO_ACTION;
		ClearNext();
	}
	
	public void ArcLeft(int label) {
		int left = m_lStack[stack_back];
		//add new head for left and add label
		m_lHeads[left][++m_lHeadsBack[left]] = m_nNextWord;
		m_lLabels[left][m_lHeadsBack[left]] = label;
		m_lDepTagL[m_nNextWord].add(label);
		//sibling is the previous child of buffer seek
		m_lSibling[left] = m_lDepsL[m_nNextWord][m_lDepsLBack[m_nNextWord]];
		//add child for buffer seek
		m_lDepsL[m_nNextWord][++m_lDepsLBack[m_nNextWord]] = left;
		//add right arcs for stack seek
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, label, MacrosDag.LEFT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(MacrosDag.ARC_LEFT, label);
	}
	
	public void ArcRight(int label) {
		int left = m_lStack[stack_back];
		m_lHeads[m_nNextWord][++m_lHeadsBack[m_nNextWord]] = left;
		m_lLabels[m_nNextWord][m_lHeadsBack[m_nNextWord]] = label;
		m_lDepTagR[left].add(label);
		m_lSibling[m_nNextWord] = m_lDepsR[left][m_lDepsRBack[left]];
		m_lDepsR[left][++m_lDepsRBack[left]] = m_nNextWord;
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, label, MacrosDag.RIGHT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(MacrosDag.ARC_RIGHT, label);
	}

	public void Shift(int label) {
		m_lStack[++stack_back] = m_nNextWord;
		m_lCCGLabels[m_nNextWord++] = label;
		m_lActionList[++action_back] = Action.encodeAction(MacrosDag.SHIFT, label);
		ClearNext();
	}
	
	public void Reduce() {
		--stack_back;
		m_lActionList[++action_back] = MacrosDag.REDUCE;
	}
	
	public void Swap() {
		int tail = m_lStack[stack_back];
		m_lStack[stack_back] = m_lStack[stack_back - 1];
		m_lStack[stack_back - 1] = tail;
		m_lActionList[++action_back] = MacrosDag.SWAP;
	}
	
	public void ClearNext() {
		m_lRightArcsBack[m_nNextWord] = -1;
		m_lHeadsBack[m_nNextWord] = m_lDepsLBack[m_nNextWord] = m_lDepsRBack[m_nNextWord] = m_lRightArcsSeek[m_nNextWord] = 0;
		m_lHeads[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lLabels[m_nNextWord][0] = MacrosDag.DEP_NONE;
		m_lDepsL[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepsR[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepTagL[m_nNextWord].clear();
		m_lDepTagR[m_nNextWord].clear();
		m_lSibling[m_nNextWord] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
	}
	
	@Override
	public void Move(final int action) {
		switch (Action.getUnlabeledAction(action)) {
		case MacrosDag.NO_ACTION:
			return;
		case MacrosDag.SHIFT:
			Shift(Action.getLabel(action));
			return;
		case MacrosDag.REDUCE:
			Reduce();
			return;
		case MacrosDag.SWAP:
			Swap();
			return;
		case MacrosDag.ARC_LEFT:
			ArcLeft(Action.getLabel(action));
			return;
		case MacrosDag.ARC_RIGHT:
			ArcRight(Action.getLabel(action));
			return;
		}
	}
	
	@Override
	public boolean StandardMoveStep(final DependencyGraphBase graph, final ArrayList<DependencyLabel> m_lCacheLabel) {
		int top;
		DependencyDag dag = (DependencyDag)graph;
		if (stack_back >= 0) {
			top = m_lStack[stack_back];
			DependencyDagNode node = (DependencyDagNode)dag.nodes[top];
//			System.out.println("top = " + top + " seek = " + node.rightseek + " tail = " + node.righttail);
			// skip no possible rightarcs
			while (node.rightseek <= node.righttail && node.NearestRight().other < m_nNextWord) {
				++node.rightseek;
			}
			//if no rightarcs, reduce
			if (node.rightseek > node.righttail) {
				Reduce();
//				System.out.println("reduce");
				return true;
			}
			//get nearest right arc
			Arc rightnode = node.NearestRight();
			if (rightnode.other == m_nNextWord) {
				++node.rightseek;
				if (rightnode.direction == MacrosDag.LEFT_DIRECTION) {
					ArcLeft(rightnode.label);
//					System.out.println("left" + rightnode.label);
					++node.headsseek;
				} else {
					ArcRight(rightnode.label);
//					System.out.println("right" + rightnode.label);
					++node.childrenseek;
				}
				return true;
			} else if (stack_back >= 1) {
				DependencyDagNode node2nd = (DependencyDagNode)dag.nodes[m_lStack[stack_back - 1]];
				int topseek = node.rightseek, toptail = node.righttail;
				int top2ndseek = node2nd.rightseek, top2ndtail = node2nd.righttail;
				if (topseek <= toptail && top2ndseek <= top2ndtail) {
					if (node.rightarcs.get(topseek).compareTo(node2nd.rightarcs.get(top2ndseek)) > 0) {
//						System.out.println("swap1");
						Swap();
						return true;
					}
				} else if (topseek <= toptail) {
//					System.out.println("swap2");
					Swap();
					return true;
				}
			}
		}
		if (m_nNextWord < dag.length) {
//			System.out.println("shift" + CCGTag.code(((DependencyDagNode)dag.nodes[m_nNextWord]).ccgtag));
			Shift(CCGTag.code(((DependencyDagNode)dag.nodes[m_nNextWord]).ccgtag));
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void StandardFinish() {
		assert (stack_back == -1);
	}
	
	@Override
	public int FollowMove(final StateItemBase itembase) {
		StateItem item = (StateItem)itembase;
		return item.m_lActionList[action_back + 1];
	}
	
	public void GenerateTree(final TwoStringsVector input, DependencyGraphBase output) {
		output.length = 0;
		for (int i = 0, input_size = this.size(); i < input_size; ++i) {
			DependencyDagNode node = new DependencyDagNode(input.get(i).m_string1, input.get(i).m_string2, CCGTag.str(m_lCCGLabels[i]));
			for (int j = 0; j <= m_lRightArcsBack[i]; ++j) {
				node.rightarcs.add(new Arc(m_lRightArcs[i][j]));
			}
			output.nodes[output.length++] = node;
		}
	}
	
	public void print() {
		System.out.println("next word is " + m_nNextWord);
		for (int i = 0; i < m_nNextWord; ++i) {
			System.out.println("node" + i);
			System.out.println("ccg tag is " + m_lCCGLabels[i]);
			for (int j = 0; j <= m_lRightArcsBack[i]; ++j) {
				m_lRightArcs[i][j].print(i);
			}
		}
		for (int i = 1; i <= action_back; ++i) {
			System.out.print("action " + i + " is ");
			Action.print(m_lActionList[i]);
		}
		System.out.println();
	}
	
}