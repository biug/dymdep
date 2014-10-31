package common.parser.implementations.spanning;

import include.linguistics.IntIntegerVector;
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

public class StateItem extends StateItemBase {
	
	protected int m_nNextWord;
	
	protected int arc_index;

	protected int action_back;
	protected int[] m_lActionList;
	
	protected int[] m_lHeadL;	//heads for every node
	protected int[] m_lHeadLabelL;	//label for every node
	protected int[] m_lHeadLNum;
	protected int[] m_lHeadR;
	protected int[] m_lHeadLabelR;
	protected int[] m_lHeadRNum;
	protected int[] m_lDepL;		//left dependency children
	protected int[] m_lDepLabelL;
	protected int[] m_lDepLNum;
	protected int[] m_lDepR;		//right dependency children
	protected int[] m_lDepLabelR;
	protected int[] m_lSubDepLabelR;
	protected int[] m_lDepRNum;
	protected int[] m_lCCGLabels;
	
	protected int[] m_lTreeHead;
	protected int[] m_lTreeLabel;
	
	protected int[] m_lLeftArcsBack;
	protected int[] m_lLeftArcsSeek;
	protected Arc[][] m_lLeftArcs;	//right arcs
	
	protected SetOfDepLabels[] m_lDepTagL;
	protected SetOfDepLabels[] m_lDepTagR;
	protected SetOfCCGLabels[] m_lCCGTagL;
	
	public StateItem() {
		
		action_back = 0;
		m_lActionList = new int[Macros.MAX_SENTENCE_SIZE * Macros.MAX_SENTENCE_SIZE];
		
		m_lHeadL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadLabelL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadLNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lHeadRNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepLabelL = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepLNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lSubDepLabelR = new int[Macros.MAX_SENTENCE_SIZE];
		m_lDepRNum = new int[Macros.MAX_SENTENCE_SIZE];
		m_lCCGLabels = new int[Macros.MAX_SENTENCE_SIZE];
		
		m_lTreeHead = new int[Macros.MAX_SENTENCE_SIZE];
		m_lTreeLabel = new int[Macros.MAX_SENTENCE_SIZE];
		
		m_lLeftArcsBack = new int[Macros.MAX_SENTENCE_SIZE];
		m_lLeftArcsSeek = new int[Macros.MAX_SENTENCE_SIZE];
		m_lLeftArcs = new Arc[Macros.MAX_SENTENCE_SIZE][];
		
		m_lDepTagL = new SetOfDepLabels[Macros.MAX_SENTENCE_SIZE];
		m_lDepTagR = new SetOfDepLabels[Macros.MAX_SENTENCE_SIZE];
		m_lCCGTagL = new SetOfCCGLabels[Macros.MAX_SENTENCE_SIZE];
		
		for (int i = 0; i < Macros.MAX_SENTENCE_SIZE; ++i) {
			
			m_lLeftArcs[i] = new Arc[Macros.MAX_SENTENCE_SIZE];
			for (int j = 0; j < Macros.MAX_SENTENCE_SIZE; ++j) {
				m_lLeftArcs[i][j] = new Arc();
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
		m_nNextWord = item.m_nNextWord;
		action_back = item.action_back;
		System.arraycopy(item.m_lActionList, 0, m_lActionList, 0, action_back + 1);
		int length = m_nNextWord + 1;
		if (length > 0) {
			System.arraycopy(item.m_lHeadL, 0, m_lHeadL, 0, length);
			System.arraycopy(item.m_lHeadLabelL, 0, m_lHeadLabelL, 0, length);
			System.arraycopy(item.m_lHeadLNum, 0, m_lHeadLNum, 0, length);
			System.arraycopy(item.m_lHeadR, 0, m_lHeadR, 0, length);
			System.arraycopy(item.m_lHeadLabelR, 0, m_lHeadLabelR, 0, length);
			System.arraycopy(item.m_lHeadRNum, 0, m_lHeadRNum, 0, length);
			System.arraycopy(item.m_lDepL, 0, m_lDepL, 0, length);
			System.arraycopy(item.m_lDepLabelL, 0, m_lDepLabelL, 0, length);
			System.arraycopy(item.m_lDepLNum, 0, m_lDepLNum, 0, length);
			System.arraycopy(item.m_lDepR, 0, m_lDepR, 0, length);
			System.arraycopy(item.m_lDepLabelR, 0, m_lDepLabelR, 0, length);
			System.arraycopy(item.m_lSubDepLabelR, 0, m_lSubDepLabelR, 0, length);
			System.arraycopy(item.m_lDepRNum, 0, m_lDepRNum, 0, length);
			System.arraycopy(item.m_lCCGLabels, 0, m_lCCGLabels, 0, length);
			System.arraycopy(item.m_lTreeHead, 0, m_lTreeHead, 0, length);
			System.arraycopy(item.m_lTreeLabel, 0, m_lTreeLabel, 0, length);
			System.arraycopy(item.m_lLeftArcsBack, 0, m_lLeftArcsBack, 0, length);
			System.arraycopy(item.m_lLeftArcsSeek, 0, m_lLeftArcsSeek, 0, length);
			for (int i = 0; i < length; ++i) {
				for (int j = 0; j <= m_lLeftArcsBack[i]; ++j) {
					m_lLeftArcs[i][j].copy(item.m_lLeftArcs[i][j]);
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
		return m_nNextWord;
	}
	
	public final boolean stackempty() {
		return m_nNextWord == 0;
	}
	
	public final int stacktop() {
		return m_nNextWord - 1;
	}
	
	public final int stacktop2() {
		return m_nNextWord > 1 ? m_nNextWord - 2 : out_index;
	}
	
	public final int lefthead(final int index) {
		return index == out_index ? out_index : m_lHeadL[index];
	}
	
	public final int leftheadlabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lHeadLabelL[index];
	}
	
	public final int righthead(final int index) {
		return index == out_index ? out_index : m_lHeadR[index];
	}
	
	public final int rightheadlabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lHeadLabelR[index];
	}
	
	public final int leftdep(final int index) {
		return index == out_index ? out_index : m_lDepL[index];
	}
	
	public final int leftdeplabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lDepLabelL[index];
	}
	
	public final int rightdep(final int index) {
		return index == out_index ? out_index : m_lDepR[index];
	}

	public final int rightdeplabel(final int index) {
		return index == out_index ? Macros.DEP_NONE : m_lDepLabelR[index];
	}
	
	public final int size(final int size) {
		return m_nNextWord < size ? m_nNextWord : out_index;
	}
	
	public final int nextbufferhead(final int size) {
		return m_nNextWord + 1 < size ? m_nNextWord + 1 : out_index;
	}
	
	public final int nextbuffernext(final int size) {
		return m_nNextWord + 2 < size ? m_nNextWord + 2 : out_index;
	}
	
	public final int beforebufferhead() {
		return m_nNextWord > 0 ? m_nNextWord - 1 : out_index;
	}
	
	public final int beforebufferbefore() {
		return m_nNextWord > 1 ? m_nNextWord - 2 : out_index;
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
	
	public final int treehead(final int index) {
		return index == out_index ? 0 : m_lTreeHead[index];
	}
	
	public final int treelabel(final int index) {
		return index == out_index ? 0 : m_lTreeLabel[index];
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
	
	public final boolean leftarcempty(final int index) {
		return m_lLeftArcsBack[index] == -1;
	}
	
	public final Arc nearestleft(final int index) {
		return m_lLeftArcs[index][m_lLeftArcsSeek[index]];
	}
	
	public final void setarcindex(int index) {
		arc_index = index;
	}
	
	public final int getarcindex() {
		return arc_index;
	}
	
	public void clear() {
		//reset buffer seek
		m_nNextWord = 0;
		//reset score
		score = 0;
		//reset action
		action_back = 0;
		m_lActionList[action_back] = Macros.NO_ACTION;
		ClearNext();
	}
	
	public void ArcLeft(int index, int label) {
		int left = index;
		//add new head for left and add label
		m_lHeadR[left] = m_nNextWord;
		m_lHeadLabelR[left] = label;
		++m_lHeadRNum[left];
		m_lDepTagL[m_nNextWord].add(label);
		m_lCCGTagL[m_nNextWord].add(m_lCCGLabels[left]);
		//sibling is the previous child of buffer seek
		//add child for buffer seek
		m_lDepL[m_nNextWord] = left;
		m_lDepLabelL[m_nNextWord] = label;
		++m_lDepLNum[m_nNextWord];
		//add right arcs for stack seek
		m_lLeftArcs[m_nNextWord][++m_lLeftArcsBack[left]] = new Arc(left, label, Macros.LEFT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(Macros.ARC_LEFT, index, label);
	}
	
	public void ArcRight(int index, int label) {
		int left = index;
		m_lHeadL[m_nNextWord] = left;
		m_lHeadLabelL[m_nNextWord] = label;
		++m_lHeadLNum[m_nNextWord];
		m_lDepTagR[left].add(label);
		m_lDepR[left] = m_nNextWord;
		m_lSubDepLabelR[left] = m_lDepLabelR[left];
		m_lDepLabelR[left] = label;
		++m_lDepRNum[left];
		m_lLeftArcs[m_nNextWord][++m_lLeftArcsBack[left]] = new Arc(left, label, Macros.RIGHT_DIRECTION);
		m_lActionList[++action_back] = Action.encodeAction(Macros.ARC_RIGHT, index, label);
	}

	public void Shift(int label) {
		m_lCCGLabels[m_nNextWord++] = label;
		m_lActionList[++action_back] = Action.encodeAction(Macros.SHIFT, 0, label);
		ClearNext();
	}
	
	public void ClearNext() {
		m_lCCGLabels[m_nNextWord] = Macros.CCGTAG_NONE;
		m_lHeadL[m_nNextWord] = out_index;
		m_lHeadLabelL[m_nNextWord] = Macros.DEP_NONE;
		m_lHeadLNum[m_nNextWord] = 0;
		m_lHeadR[m_nNextWord] = out_index;
		m_lHeadLabelR[m_nNextWord] = Macros.DEP_NONE;
		m_lHeadRNum[m_nNextWord] = 0;
		m_lDepL[m_nNextWord] = out_index;
		m_lDepLabelL[m_nNextWord] = Macros.DEP_NONE;
		m_lDepLNum[m_nNextWord] = 0;
		m_lDepR[m_nNextWord] = out_index;
		m_lDepLabelR[m_nNextWord] = Macros.DEP_NONE;
		m_lSubDepLabelR[m_nNextWord] = Macros.DEP_NONE;
		m_lDepRNum[m_nNextWord] = 0;
		m_lDepTagL[m_nNextWord].clear();
		m_lDepTagR[m_nNextWord].clear();
		m_lCCGTagL[m_nNextWord].clear();
		m_lCCGLabels[m_nNextWord] = Macros.CCGTAG_NONE;
		m_lLeftArcsBack[m_nNextWord] = -1;
		m_lLeftArcsSeek[m_nNextWord] = 0;
	}
	
	@Override
	public void Move(final int action) {
		switch (Action.getUnlabeledAction(action)) {
		case Macros.NO_ACTION:
			return;
		case Macros.SHIFT:
			Shift(Action.getLabel(action));
			return;
		case Macros.ARC_LEFT:
			ArcLeft(Action.getIndex(action), Action.getLabel(action));
			return;
		case Macros.ARC_RIGHT:
			ArcRight(Action.getIndex(action), Action.getLabel(action));
			return;
		}
	}
	
	@Override
	public boolean StandardMoveStep(final DependencyGraphBase graph, final ArrayList<DependencyLabel> m_lCacheLabel) {
		DependencyDag dag = (DependencyDag)graph;
		for (int i = m_nNextWord - 1; i >= 0; --i) {
			DependencyDagNode node = (DependencyDagNode)dag.nodes[i];
			if (node.rightseek <= node.righttail) {
				Arc rightnode = node.NearestRight();
				if (rightnode.other == m_nNextWord) {
					++node.rightseek;
					if (rightnode.direction == Macros.LEFT_DIRECTION) {
						ArcLeft(i, rightnode.label);
						++node.headsseek;
					} else {
						ArcRight(i, rightnode.label);
						++node.childrenseek;
					}
					return true;
				}
			}
		}
		if (m_nNextWord < dag.length) {
			Shift(CCGTag.code(((DependencyDagNode)dag.nodes[m_nNextWord]).ccgtag));
			return true;
		} else {
			return false;
		}
	}
	
	public void StandardFinish(int size) {
		assert (m_nNextWord == size);
	}
	
	@Override
	public int FollowMove(final StateItemBase itembase) {
		StateItem item = (StateItem)itembase;
		return item.m_lActionList[action_back + 1];
	}
	
	public void GenerateTree(final TwoStringsVector input, final IntIntegerVector treeinput, DependencyGraphBase output) {
		output.length = 0;
		for (int i = 0, input_size = this.size(Macros.MAX_SENTENCE_SIZE); i < input_size; ++i) {
			DependencyDagNode node = new DependencyDagNode(input.get(i).m_string1, input.get(i).m_string2, treeinput.get(i).m_index, treeinput.get(i).m_label, CCGTag.str(m_lCCGLabels[i]));
			output.nodes[output.length++] = node;
		}
		for (int i = 0, input_size = this.size(Macros.MAX_SENTENCE_SIZE); i < input_size; ++i) {
			for (int j = 0; j <= m_lLeftArcsBack[i]; ++j) {
				((DependencyDagNode)output.nodes[m_lLeftArcs[i][j].other]).rightarcs.add(new Arc(i, m_lLeftArcs[i][j].label, m_lLeftArcs[i][j].direction));
			}
		}
	}
	
}
