package common.parser.implementations;

public class Arc {
	
	public int other;
	public int label;
	public int direction;
	
	public Arc() {
		other = DependencyDagNode.DEPENDENCY_LINK_NO_HEAD;
		label = MacrosDag.DEP_NONE;
		direction = MacrosDag.LEFT_DIRECTION;
	}
	
	public Arc(final int o, final int l, final int d) {
		other = o;
		label = l;
		direction = d;
	}
	
	public Arc(final Arc arc) {
		other = arc.other;
		label = arc.label;
		direction = arc.direction;
	}
	
	public void copy(final Arc arc) {
		other = arc.other;
		label = arc.label;
		direction = arc.direction;
	}
	
	public boolean more(final Arc arc) {
		return other == arc.other ? false : (other > arc.other);
	}
	
	public void print(int now) {
		if (direction == MacrosDag.LEFT_DIRECTION) {
			System.out.println(now + " <- " + other + " : " + label);			
		} else {
			System.out.println(now + " -> " + other + " : " + label);
		}
	}
}
