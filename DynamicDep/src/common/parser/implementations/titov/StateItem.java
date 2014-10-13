package common.parser.implementations.titov;

import include.linguistics.SetOfCCGLabels;
import include.linguistics.SetOfDepLabels;
import include.linguistics.TwoStringsVector;

import java.util.ArrayList;

import common.dependency.label.DependencyLabel;
import common.parser.DependencyGraphBase;
import common.parser.StateItemBase;
import common.parser.implementations.Arc;
import common.parser.implementations.DependencyDag;
import common.parser.implementations.DependencyDagNode;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public class StateItem extends StateItemBase {
	
	protected int m_nNextWord;
	
	protected int stack_back;
	protected int[] m_lStack;		//stack

	protected int action_back;
	protected int[] m_lActionList;
	
	protected int[] m_lHeadL;	//heads for every node
	protected int[] m_lSubHeadL;
	protected int[] m_lHeadLabelL;	//label for every node
	protected int[] m_lSubHeadLabelL;
	protected int[] m_lHeadLNum;
	protected int[] m_lHeadR;
	protected int[] m_lSubHeadR;
	protected int[] m_lHeadLabelR;
	protected int[] m_lSubHeadLabelR;
	protected int[] m_lHeadRNum;
	protected int[] m_lDepL;		//left dependency children
	protected int[] m_lSubDepL;
	protected int[] m_lDepLabelL;
	protected int[] m_lSubDepLabelL;
	protected int[] m_lDepLNum;
	protected int[] m_lDepR;		//right dependency children
	protected int[] m_lSubDepR;
	protected int[] m_lDepLabelR;
	protected int[] m_lSubDepLabelR;
	protected int[] m_lDepRNum;
	protected int[] m_lCCGLabels;
	
	protected int[] m_lRightArcsBack;
	protected int[] m_lRightArcsSeek;
	protected Arc[][] m_lRightArcs;	//right arcs
	
	protected SetOfDepLabels[] m_lDepTagL;
	protected SetOfDepLabels[] m_lDepTagR;
	protected SetOfCCGLabels[] m_lCCGTagL;
	
	public StateItem() {
		
		stack_back = -1;
		m_lStack = new int[Macros.MAX_SENTENCE_SIZE];
		
		action_back = 0;
		m_lActionList = new int[Macros.MAX_SENTENCE_SIZE * Macros.MAX_SENTENCE_SIZE];
		
		m_lHeadL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubHeadL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadLabelL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubHeadLabelL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadLNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubHeadR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubHeadLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadRNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubDepL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepLabelL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubDepLabelL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepLNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubDepR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubDepLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepRNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lCCGLabels = new int[Macros.MAX_SENTENCE_SIZE];
		
		m_lRightArcsBack = new int[Macros.MAX_SENTENCE_SIZE];
		m_lRightArcsSeek = new int[Macros.MAX_SENTENCE_SIZE];
		m_lRightArcs = new Arc[Macros.MAX_SENTENCE_SIZE][];
		
		m_lDepTagL = new SetOfDepLabels[Macros.MAX_SENTENCE_SIZE];
		m_lDepTagR = new SetOfDepLabels[Macros.MAX_SENTENCE_SIZE];
		m_lCCGTagL = new SetOfCCGLabels[Macros.MAX_SENTENCE_SIZE];
		
		for (int i = 0; i < Macros.MAX_SENTENCE_SIZE; ++i) {
			
			m_lCCGLabels[i] = Macros.CCGTAG_NONE;

			m_lRightArcsBack[i] = -1;
			m_lRightArcsSeek[i] = 0;
			m_lRightArcs[i] = new Arc[Macros.MAX_SENTENCE_SIZE];
			for (int j = 0; j < Macros.MAX_SENTENCE_SIZE; ++j) {
				m_lRightArcs[i][j] = new Arc();
			}
			
			m_lDepTagL[i] = new SetOfDepLabels();
			m_lDepTagR[i] = new SetOfDepLabels();
			m_lCCGTagL[i] = new SetOfCCGLabels();
			
		}
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
			System.arraycopy(item.m_lHeadL, 0, m_lHeadL, 0, length);
			System.arraycopy(item.m_lSubHeadL, 0, m_lSubHeadL, 0, length);
			System.arraycopy(item.m_lHeadLabelL, 0, m_lHeadLabelL, 0, length);
			System.arraycopy(item.m_lSubHeadLabelL, 0, m_lSubHeadLabelL, 0, length);
			System.arraycopy(item.m_lHeadLNum, 0, m_lHeadLNum, 0, length);
			System.arraycopy(item.m_lHeadR, 0, m_lHeadR, 0, length);
			System.arraycopy(item.m_lSubHeadR, 0, m_lSubHeadR, 0, length);
			System.arraycopy(item.m_lHeadLabelR, 0, m_lHeadLabelR, 0, length);
			System.arraycopy(item.m_lSubHeadLabelR, 0, m_lSubHeadLabelR, 0, length);
			System.arraycopy(item.m_lHeadRNum, 0, m_lHeadRNum, 0, length);
			System.arraycopy(item.m_lDepL, 0, m_lDepL, 0, length);
			System.arraycopy(item.m_lSubDepL, 0, m_lSubDepL, 0, length);
			System.arraycopy(item.m_lDepLabelL, 0, m_lDepLabelL, 0, length);
			System.arraycopy(item.m_lSubDepLabelL, 0, m_lSubDepLabelL, 0, length);
			System.arraycopy(item.m_lDepLNum, 0, m_lDepLNum, 0, length);
			System.arraycopy(item.m_lDepR, 0, m_lDepR, 0, length);
			System.arraycopy(item.m_lSubDepR, 0, m_lSubDepR, 0, length);
			System.arraycopy(item.m_lDepLabelR, 0, m_lDepLabelR, 0, length);
			System.arraycopy(item.m_lSubDepLabelR, 0, m_lSubDepLabelR, 0, length);
			System.arraycopy(item.m_lDepRNum, 0, m_lDepRNum, 0, length);
			System.arraycopy(item.m_lCCGLabels, 0, m_lCCGLabels, 0, length);
			System.arraycopy(item.m_lRightArcsBack, 0, m_lRightArcsBack, 0, length);
			System.arraycopy(item.m_lRightArcsSeek, 0, m_lRightArcsSeek, 0, length);
			for (int i = 0; i < length; ++i) {
				for (int j = 0; j <= m_lRightArcsBack[i]; ++j) {
					m_lRightArcs[i][j].copy(item.m_lRightArcs[i][j]);
				}
			}
			for (int i = 0; i < length; ++i) {
				m_lDepTagL[i].copy(item.m_lDepTagL[i]);
				m_lDepTagR[i].copy(item.m_lDepTagR[i]);
				m_lCCGTagL[i].copy(item.m_lCCGTagL[i]);
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
		return stack_back == -1 ? out_index : m_lStack[stack_back];
	}
	
	public final int stackitem(final int index) {
		return index >= 0 && index <= stack_back ? m_lStack[index] : out_index;
	}
	
	public final boolean canswap() {
		return m_lActionList[action_back] != Macros.SWAP && stack_back > 0;
	}
	
	public final boolean canarc() {
		if (stack_back == -1) {
			return false;
		}
		int top = m_lStack[stack_back], back = m_lRightArcsBack[top];
		if (back == -1) return true;
		return m_lRightArcs[top][back].other != m_nNextWord;
	}
	
	public final int lefthead(final int index) {
		return index == out_index ? out_index : m_lHeadL[index];
	}
	
	public final int leftsubhead(final int index) {
		return index == out_index ? out_index : m_lSubHeadL[index];
	}
	
	public final int leftheadlabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lHeadLabelL[index];
	}

	public final int leftsubheadlabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lHeadLabelL[index];
	}
	
	public final int righthead(final int index) {
		return index == out_index ? out_index : m_lHeadR[index];
	}
	
	public final int rightsubhead(final int index) {
		return index == out_index ? out_index : m_lSubHeadR[index];
	}
	
	public final int rightheadlabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lHeadLabelR[index];
	}
	
	public final int rightsubheadlabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lSubHeadLabelR[index];
	}
	
	public final int leftdep(final int index) {
		return index == out_index ? out_index : m_lDepL[index];
	}

	public final int leftsubdep(final int index) {
		return index == out_index ? out_index : m_lSubDepL[index];
	}
	
	public final int leftdeplabel(final int index) {
		return index == out_index ? out_index : m_lDepLabelL[index];
	}
	
	public final int leftsubdeplabel(final int index) {
		return index == out_index ? out_index : m_lSubDepLabelL[index];
	}
	
	public final int rightdep(final int index) {
		return index == out_index ? out_index : m_lDepR[index];
	}
	
	public final int rightsubdep(final int index) {
		return index == out_index ? out_index : m_lSubDepL[index];
	}

	public final int rightdeplabel(final int index) {
		return index == out_index ? out_index : m_lDepLabelR[index];
	}
	
	public final int rightsubdeplabel(final int index) {
		return index == out_index ? out_index : m_lSubDepLabelR[index];
	}
	public final int size() {
		return m_nNextWord;
	}
	
	public final int nextbufferhead() {
		return m_nNextWord + 1;
	}
	
	public final int nextbuffernext() {
		return m_nNextWord + 2;
	}
	
	public final int leftheadarity(final int index) {
		return index == out_index ? 0 : m_lHeadLNum[index];
	}

	public final int rightheadarity(final int index) {
		return index == out_index ? 0 : m_lHeadRNum[index];
	}
	
	public final int leftdeparity(final int index) {
		return index == out_index ? 0 : m_lDepLNum[index];
	}
	
	public final int rightdeparity(final int index) {
		return index == out_index ? 0 : m_lDepRNum[index];
	}
	
	public final SetOfDepLabels lefttagset(final int index) {
		return index == out_index ? empty_tagset : m_lDepTagL[index];
	}
	
	public final SetOfDepLabels righttagset(final int index) {
		return index == out_index ? empty_tagset : m_lDepTagR[index];
	}
	
	public final SetOfCCGLabels leftccgset(final int index) {
		return index == out_index ? empty_ccgset : m_lCCGTagL[index];
	}
	
	public final int ccg(final int index) {
		return index == out_index ? Macros.CCGTAG_NONE : m_lCCGLabels[index];
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
		m_lActionList[action_back] = Macros.NO_ACTION;
		ClearNext();
	}
	
	public void ArcLeft(int label) {
		int left = m_lStack[stack_back];
		//add new head for left and add label
		m_lSubHeadR[left] = m_lHeadR[left];
		m_lHeadR[left] = m_nNextWord;
		m_lSubHeadLabelR[left] = m_lHeadLabelR[left];
		m_lHeadLabelR[left] = label;
		++m_lHeadRNum[left];
		m_lDepTagL[m_nNextWord].add(label);
		m_lCCGTagL[m_nNextWord].add(m_lCCGLabels[left]);
		//sibling is the previous child of buffer seek
		m_lSubDepL[m_nNextWord] = m_lDepL[m_nNextWord];
		//add child for buffer seek
		m_lDepL[m_nNextWord] = left;
		m_lSubDepLabelL[m_nNextWord] = m_lDepLabelL[m_nNextWord];
		m_lDepLabelL[m_nNextWord] = label;
		++m_lDepLNum[m_nNextWord];
		//add right arcs for stack seek
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, label, Macros.LEFT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(Macros.ARC_LEFT, label);
	}
	
	public void ArcRight(int label) {
		int left = m_lStack[stack_back];
		m_lSubHeadL[m_nNextWord] = m_lHeadL[m_nNextWord];
		m_lHeadL[m_nNextWord] = left;
		m_lHeadLabelL[m_nNextWord] = label;
		m_lSubHeadLabelL[m_nNextWord] = label;
		++m_lHeadLNum[m_nNextWord];
		m_lDepTagR[left].add(label);
		m_lSubDepR[left] = m_lDepR[left];
		m_lDepR[left] = m_nNextWord;
		m_lSubDepLabelR[left] = m_lDepLabelR[left];
		m_lDepLabelR[left] = label;
		++m_lDepRNum[left];
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, label, Macros.RIGHT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(Macros.ARC_RIGHT, label);
	}

	public void Shift(int label) {
		m_lStack[++stack_back] = m_nNextWord;
		m_lCCGLabels[m_nNextWord++] = label;
		m_lActionList[++action_back] = Action.encodeAction(Macros.SHIFT, label);
		ClearNext();
	}
	
	public void Reduce() {
		--stack_back;
		m_lActionList[++action_back] = Macros.REDUCE;
	}
	
	public void Swap() {
		int tail = m_lStack[stack_back];
		m_lStack[stack_back] = m_lStack[stack_back - 1];
		m_lStack[stack_back - 1] = tail;
		m_lActionList[++action_back] = Macros.SWAP;
	}
	
	public void ClearNext() {
		m_lCCGLabels[m_nNextWord] = Macros.CCGTAG_NONE;
		m_lHeadL[m_nNextWord] = out_index;
		m_lSubHeadL[m_nNextWord] = out_index;
		m_lHeadLabelL[m_nNextWord] = Macros.DEP_NONE;
		m_lHeadLNum[m_nNextWord] = 0;
		m_lHeadR[m_nNextWord] = out_index;
		m_lSubHeadR[m_nNextWord] = out_index;
		m_lHeadLabelR[m_nNextWord] = Macros.DEP_NONE;
		m_lHeadRNum[m_nNextWord] = 0;
		m_lDepL[m_nNextWord] = out_index;
		m_lSubDepL[m_nNextWord] = out_index;
		m_lDepLabelL[m_nNextWord] = Macros.DEP_NONE;
		m_lSubDepLabelL[m_nNextWord] = Macros.DEP_NONE;
		m_lDepLNum[m_nNextWord] = 0;
		m_lDepR[m_nNextWord] = out_index;
		m_lSubDepR[m_nNextWord] = out_index;
		m_lDepLabelR[m_nNextWord] = Macros.DEP_NONE;
		m_lSubDepLabelR[m_nNextWord] = Macros.DEP_NONE;
		m_lDepRNum[m_nNextWord] = 0;
		m_lDepTagL[m_nNextWord].clear();
		m_lDepTagR[m_nNextWord].clear();
		m_lCCGTagL[m_nNextWord].clear();
	}
	
	@Override
	public void Move(final int action) {
		switch (Action.getUnlabeledAction(action)) {
		case Macros.NO_ACTION:
			return;
		case Macros.SHIFT:
			Shift(Action.getLabel(action));
			return;
		case Macros.REDUCE:
			Reduce();
			return;
		case Macros.SWAP:
			Swap();
			return;
		case Macros.ARC_LEFT:
			ArcLeft(Action.getLabel(action));
			return;
		case Macros.ARC_RIGHT:
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
			// skip impossible rightarcs
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
				if (rightnode.direction == Macros.LEFT_DIRECTION) {
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
		System.out.println("score is " + score);
		for (int i = 0; i < m_nNextWord; ++i) {
			System.out.println("node" + i);
			System.out.println("ccg tag is " + m_lCCGLabels[i]);
			for (int j = 0; j <= m_lRightArcsBack[i]; ++j) {
				m_lRightArcs[i][j].print(i);
			}
		}
		System.out.println();
	}
	
}