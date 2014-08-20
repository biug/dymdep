package common.parser;

import include.linguistics.TwoStringsVector;

public abstract class DepParserBase {
	
	protected WeightBase m_weights;
	
	protected boolean m_bTrain;
	
	public DepParserBase(final String sFeatureDBPath, final boolean bTrain) {
		m_bTrain = bTrain;
		m_weights = null;
	}
	
	public abstract void parse(final TwoStringsVector sentence, final DependencyParser[] retval, final int nBest, long[] scores);
	public abstract void train(final DependencyParser correct, final int round);
	
	public abstract void finishtraning();
	
}