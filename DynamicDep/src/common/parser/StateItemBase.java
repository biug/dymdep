package common.parser;

import java.util.ArrayList;

import common.dependency.label.DependencyLabel;


public abstract class StateItemBase {
	public long score;
	
	public abstract StateItemBase generateItem();
	public abstract boolean more(final StateItemBase itembase);
	public abstract void copy(final StateItemBase itembase);
	
	public abstract void Move(final int action);
	public abstract boolean StandardMoveStep(final DependencyGraphBase graph, final ArrayList<DependencyLabel> m_lCacheLabel);
	public abstract void StandardFinish();
	public abstract int FollowMove(final StateItemBase itembase);
}
