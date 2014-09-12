package common.parser.implementations;

public class Arc {
	
	public int other;
	public int label;
	public int direction;
	
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
	
	public boolean more(final Arc arc) {
		return other == arc.other ? false : (other > arc.other);
	}
}
