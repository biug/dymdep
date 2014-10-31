package common.parser;

import include.linguistics.SetOfCCGLabels;
import include.linguistics.SetOfDepLabels;

import java.util.ArrayList;

import common.dependency.label.DependencyLabel;


public abstract class StateItemBase {
	
	public final static int out_index = -1;
	public final static SetOfDepLabels empty_tagset = new SetOfDepLabels();
	public final static SetOfCCGLabels empty_ccgset = new SetOfCCGLabels();
	
	public long score;
	
	public abstract StateItemBase generateItem();
	public abstract boolean more(final StateItemBase itembase);
	public abstract void copy(final StateItemBase itembase);
	
	public abstract void Move(final int action);
	public abstract boolean StandardMoveStep(final DependencyGraphBase graph, final ArrayList<DependencyLabel> m_lCacheLabel);
	public abstract int FollowMove(final StateItemBase itembase);
}
