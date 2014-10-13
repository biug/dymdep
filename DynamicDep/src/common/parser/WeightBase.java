package common.parser;

/*
 * @author ZhangXun
 */

public abstract class WeightBase {
	
	protected boolean m_bTrain;
	protected boolean m_bModified;
	protected String m_sRecordPath;
	
	public WeightBase(final String sFile, boolean bTrain) {
		m_bTrain = bTrain;
		m_sRecordPath = sFile;
		m_bModified = false;
	}
	
	public abstract void loadScores();
	public abstract void saveScores();
	public abstract void computeAverageFeatureWeights(final int round);
	
}
