package common.parser.implementations.twostack;

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
import common.parser.implementations.titov.Macros;
import common.parser.implementations.titov.StateItem;
import common.pos.CCGTag;

public class StateItem extends StateItemBase {
	
	protected int m_nNextWord;
	
	protected int pstack_back;
	protected int sstack_back;
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
	
	protected int[] m_lTreeHead;
	protected int[] m_lTreeLabel;
	
	protected int[] m_lRightArcsBack;
	protected int[] m_lRightArcsSeek;
	protected Arc[][] m_lRightArcs;	//right arcs
	
	protected SetOfDepLabels[] m_lDepTagL;
	protected SetOfDepLabels[] m_lDepTagR;
	protected SetOfCCGLabels[] m_lCCGTagL;
	
	public StateItem() {
		
		pstack_back = -1;
		sstack_back = Macros.MAX_SENTENCE_SIZE;
		
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
		
		m_lTreeHead = new int[Macros.MAX_SENTENCE_SIZE];
		m_lTreeLabel = new int[Macros.MAX_SENTENCE_SIZE];
		
		m_lRightArcsBack = new int[Macros.MAX_SENTENCE_SIZE];
		m_lRightArcsSeek = new int[Macros.MAX_SENTENCE_SIZE];
		m_lRightArcs = new Arc[Macros.MAX_SENTENCE_SIZE][];
		
		m_lDepTagL = new SetOfDepLabels[Macros.MAX_SENTENCE_SIZE];
		m_lDepTagR = new SetOfDepLabels[Macros.MAX_SENTENCE_SIZE];
		m_lCCGTagL = new SetOfCCGLabels[Macros.MAX_SENTENCE_SIZE];
		
		for (int i = 0; i < Macros.MAX_SENTENCE_SIZE; ++i) {
			
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
	public StateItemBase generateItem() {
		return new StateItem();
	}

	@Override
	public boolean more(StateItemBase itembase) {
		return score > ((StateItem)itembase).score;
	}

	@Override
	public void copy(StateItemBase itembase) {
		StateItem item = (StateItem)itembase;
		score = item.score;
		pstack_back = item.pstack_back;
		sstack_back = item.sstack_back;
		m_nNextWord = item.m_nNextWord;
		action_back = item.action_back;
		System.arraycopy(item.m_lStack, 0, m_lStack, 0, pstack_back + 1);
		System.arraycopy(item.m_lStack, sstack_back, m_lStack, sstack_back, Macros.MAX_SENTENCE_SIZE - sstack_back);
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
			System.arraycopy(item.m_lTreeHead, 0, m_lTreeHead, 0, length);
			System.arraycopy(item.m_lTreeLabel, 0, m_lTreeLabel, 0, length);
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

	public final int size() {
		return m_nNextWord;
	}
	
	public final int pstacksize() {
		return primary_stack_back + 1;
	}
	
	public final boolean pstackempty() {
		return primary_stack_back == -1;
	}
	
	public final int pstacktop() {
		return m_lPrimaryStack[primary_stack_back];
	}
	
	public final int pstackitem(final int index) {
		return m_lPrimaryStack[index];
	}

	public final int sstacksize() {
		return secondary_stack_back + 1;
	}
	
	public final boolean sstackempty() {
		return secondary_stack_back == -1;
	}
	
	public final int sstacktop() {
		return m_lSecondaryStack[secondary_stack_back];
	}
	
	public final int sstackitem(final int index) {
		return m_lSecondaryStack[index];
	}
	
	public final boolean canarc() {
		if (primary_stack_back == -1) {
			return false;
		}
		int top = pstacktop(), back = m_lRightArcsBack[top];
		if (back == -1) return true;
		return m_lRightArcs[top][back].other != m_nNextWord;
	}
	
	public final boolean canmem() {
		return !pstackempty() && m_lActionList[action_back] != Macros.RECALL;
	}
	
	public final boolean canrecall() {
		return !sstackempty() && m_lActionList[action_back] != Macros.MEM;
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
		return m_lSibling[index][m_lSiblingBack[index]];
	}
	
	public final int leftarity(final int index) {
		return m_lDepsLBack[index] + 1;
	}
	
	public final int rightarity(final int index) {
		return m_lDepsRBack[index] + 1;
	}
	
	public final SetOfDepLabels lefttagset(final int index) {
		return m_lDepTagL[index];
	}
	
	public final SetOfDepLabels righttagset(final int index) {
		return m_lDepTagR[index];
	}
	
	public final SetOfCCGLabels leftccgset(final int index) {
		return m_lCCGTagL[index];
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
		primary_stack_back = -1;
		secondary_stack_back = -1;
		//reset score
		score = 0L;
		//reset action
		action_back = 0;
		m_lActionList[0] = Macros.NO_ACTION;
		ClearNext();
	}
	
	public void ClearNext() {
		m_lCCGLabels[m_nNextWord] = Macros.CCGTAG_NONE;
		m_lRightArcsBack[m_nNextWord] = -1;
		m_lHeadsBack[m_nNextWord] = m_lDepsLBack[m_nNextWord] = m_lDepsRBack[m_nNextWord] =
				m_lSiblingBack[m_nNextWord] = m_lRightArcsSeek[m_nNextWord] = 0;
		m_lHeads[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lLabels[m_nNextWord][0] = Macros.DEP_NONE;
		m_lDepsL[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepsR[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lSibling[m_nNextWord][0] = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		m_lDepTagL[m_nNextWord].clear();
		m_lDepTagR[m_nNextWord].clear();
		m_lCCGTagL[m_nNextWord].clear();
	}
	
	public void ArcLeft(int label) {
		int left = m_lPrimaryStack[primary_stack_back];
		//add new head for left and add label
		m_lHeads[left][++m_lHeadsBack[left]] = m_nNextWord;
		m_lLabels[left][m_lHeadsBack[left]] = label;
		m_lDepTagL[m_nNextWord].add(label);
		m_lCCGTagL[m_nNextWord].add(m_lCCGLabels[left]);
		//sibling is the previous child of buffer seek
		m_lSibling[left][++m_lSiblingBack[left]] = m_lDepsL[m_nNextWord][m_lDepsLBack[m_nNextWord]];
		//add child for buffer seek
		m_lDepsL[m_nNextWord][++m_lDepsLBack[m_nNextWord]] = left;
		//add right arcs for stack seek
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, label, Macros.LEFT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(Macros.ARC_LEFT, label);
	}
	
	public void ArcRight(int label) {
		int left = m_lPrimaryStack[primary_stack_back];
		m_lHeads[m_nNextWord][++m_lHeadsBack[m_nNextWord]] = left;
		m_lLabels[m_nNextWord][m_lHeadsBack[m_nNextWord]] = label;
		m_lDepTagR[left].add(label);
		m_lSibling[m_nNextWord][++m_lSiblingBack[m_nNextWord]] = m_lDepsR[left][m_lDepsRBack[left]];
		m_lDepsR[left][++m_lDepsRBack[left]] = m_nNextWord;
		m_lRightArcs[left][++m_lRightArcsBack[left]] = new Arc(m_nNextWord, label, Macros.RIGHT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(Macros.ARC_RIGHT, label);
	}

	public void Shift(int label) {
		m_lPrimaryStack[++primary_stack_back] = m_nNextWord;
		m_lCCGLabels[m_nNextWord++] = label;
		m_lActionList[++action_back] = Action.encodeAction(Macros.SHIFT, label);
		ClearNext();
	}
	
	public void Reduce() {
		--primary_stack_back;
		m_lActionList[++action_back] = Macros.REDUCE;
	}
	
	public void Mem() {
		m_lSecondaryStack[++secondary_stack_back] = m_lPrimaryStack[primary_stack_back--];
		m_lActionList[++action_back] = Macros.MEM;
	}
	
	public void Recall() {
		m_lPrimaryStack[++primary_stack_back] = m_lSecondaryStack[secondary_stack_back--];
		m_lActionList[++action_back] = Macros.RECALL;
	}
	

	@Override
	public void Move(int action) {
		switch (Action.getUnlabeledAction(action)) {
		case Macros.NO_ACTION:
			return;
		case Macros.SHIFT:
			Shift(Action.getLabel(action));
			return;
		case Macros.REDUCE:
			Reduce();
			return;
		case Macros.MEM:
			Mem();
			return;
		case Macros.RECALL:
			Recall();
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
	public boolean StandardMoveStep(DependencyGraphBase graph, ArrayList<DependencyLabel> m_lCacheLabel) {
		int top;
		DependencyDag dag = (DependencyDag)graph;
		if (primary_stack_back >= 0) {
			top = pstacktop();
			DependencyDagNode node = (DependencyDagNode)dag.nodes[top];
			//if no rightarcs, reduce
			if (node.rightseek > node.righttail) {
				Reduce();
//				System.out.println("reduce");
				return true;
			}
			//get nearest right arc
			Arc rightarc = node.NearestRight();
			if (rightarc.other == m_nNextWord) {
				++node.rightseek;
				if (rightarc.direction == Macros.LEFT_DIRECTION) {
					ArcLeft(rightarc.label);
//					System.out.println("left" + rightnode.label);
					++node.headsseek;
				} else {
					ArcRight(rightarc.label);
//					System.out.println("right" + rightnode.label);
					++node.childrenseek;
				}
				return true;
			} else {
				for (int i = primary_stack_back - 1; i >= 0; --i) {
					node = (DependencyDagNode)dag.nodes[m_lPrimaryStack[i]];
					for (int j = node.rightseek; j <= node.righttail; ++j) {
						rightarc = node.rightarcs.get(j);
						if (rightarc.other == m_nNextWord) {
							Mem();
							return true;
						}
					}
				}
				for (int i = secondary_stack_back; i >= 0; --i) {
					node = (DependencyDagNode)dag.nodes[m_lSecondaryStack[i]];
					for (int j = node.rightseek; j <= node.righttail; ++j) {
						rightarc = node.rightarcs.get(j);
						if (rightarc.other == m_nNextWord) {
							Recall();
							return true;
						}
					}
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
		assert(primary_stack_back == -1);
	}

	@Override
	public int FollowMove(StateItemBase itembase) {
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
