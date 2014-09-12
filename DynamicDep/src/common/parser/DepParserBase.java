package common.parser;

public abstract class DepParserBase {
	
	protected WeightBase m_weights;
	
	protected boolean m_bTrain;
	
	public DepParserBase(final String sFeatureDBPath, final boolean bTrain) {
		m_bTrain = bTrain;
		m_weights = null;
	}
	
	public abstract void finishtraning();
	
}