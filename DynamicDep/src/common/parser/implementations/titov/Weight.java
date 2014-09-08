package common.parser.implementations.titov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import common.parser.WeightBase;
import common.parser.implementations.map.CoNLLCPOSMap;
import common.parser.implementations.map.CoNLLFeatsMap;
import common.parser.implementations.map.IntMap;
import common.parser.implementations.map.LemmaMap;
import common.parser.implementations.map.TagIntMap;
import common.parser.implementations.map.TagMap;
import common.parser.implementations.map.TagSet2Map;
import common.parser.implementations.map.TagSet3Map;
import common.parser.implementations.map.TagSetOfLabelsMap;
import common.parser.implementations.map.TagTagIntMap;
import common.parser.implementations.map.TaggedWordMap;
import common.parser.implementations.map.TwoTaggedWordsMap;
import common.parser.implementations.map.TwoWordsMap;
import common.parser.implementations.map.WordIntMap;
import common.parser.implementations.map.WordMap;
import common.parser.implementations.map.WordSetOfLabelsMap;
import common.parser.implementations.map.WordTagTagMap;
import common.parser.implementations.map.WordWordIntMap;
import common.parser.implementations.map.WordWordTagMap;

/*
 * @author ZhangXun
 */

public final class Weight extends WeightBase {
	
	public WordMap m_mapSTw;
	public TagMap m_mapSTt;
	public TaggedWordMap m_mapSTwt;

	public WordMap m_mapN0w;
	public TagMap m_mapN0t;
	public TaggedWordMap m_mapN0wt;

	public WordMap m_mapN1w;
	public TagMap m_mapN1t;
	public TaggedWordMap m_mapN1wt;

	public WordMap m_mapN2w;
	public TagMap m_mapN2t;
	public TaggedWordMap m_mapN2wt;

	public WordMap m_mapSTHw;
	public TagMap m_mapSTHt;
	public IntMap m_mapSTi;

	public WordMap m_mapSTHHw;
	public TagMap m_mapSTHHt;
	public IntMap m_mapSTHi;

	public WordMap m_mapSTLDw;
	public TagMap m_mapSTLDt;
	public IntMap m_mapSTLDi;

	public WordMap m_mapSTRDw;
	public TagMap m_mapSTRDt;
	public IntMap m_mapSTRDi;

	public WordMap m_mapN0LDw;
	public TagMap m_mapN0LDt;
	public IntMap m_mapN0LDi;

	public WordMap m_mapSTL2Dw;
	public TagMap m_mapSTL2Dt;
	public IntMap m_mapSTL2Di;

	public WordMap m_mapSTR2Dw;
	public TagMap m_mapSTR2Dt;
	public IntMap m_mapSTR2Di;

	public WordMap m_mapN0L2Dw;
	public TagMap m_mapN0L2Dt;
	public IntMap m_mapN0L2Di;

	public WordMap m_mapHTw;
	public TagMap m_mapHTt;
	public TaggedWordMap m_mapHTwt;

	public TwoTaggedWordsMap m_mapSTwtN0wt;
	public WordWordTagMap m_mapSTwtN0w;
	public WordWordTagMap m_mapSTwN0wt;
	public WordTagTagMap m_mapSTtN0wt;
	public WordTagTagMap m_mapSTwtN0t;
	public TwoWordsMap m_mapSTwN0w;
	
	public TagSet2Map m_mapSTtN0t;
	public TagSet2Map m_mapN0tN1t;
	public TagSet3Map m_mapN0tN1tN2t;
	public TagSet3Map m_mapSTtN0tN1t;
	public TagSet3Map m_mapSTtN0tN0LDt;
	public TagSet3Map m_mapN0tN0LDtN0L2Dt;
	public TagSet3Map m_mapSTHtSTtN0t;
	public TagSet3Map m_mapHTtHT2tN0t;
	public TagSet3Map m_mapSTHHtSTHtSTt;
	public TagSet3Map m_mapSTtSTLDtN0t;
	public TagSet3Map m_mapSTtSTLDtSTL2Dt;
	public TagSet3Map m_mapSTtSTRDtN0t;
	public TagSet3Map m_mapSTtSTRDtSTR2Dt;

	public WordIntMap m_mapSTwd;
	public TagIntMap m_mapSTtd;
	public WordIntMap m_mapN0wd;
	public TagIntMap m_mapN0td;
	public WordWordIntMap m_mapSTwN0wd;
	public TagTagIntMap m_mapSTtN0td;

	public WordIntMap m_mapSTwra;
	public TagIntMap m_mapSTtra;
	public WordIntMap m_mapSTwla;
	public TagIntMap m_mapSTtla;
	public WordIntMap m_mapN0wla;
	public TagIntMap m_mapN0tla;

	public WordSetOfLabelsMap m_mapSTwrp;
	public TagSetOfLabelsMap m_mapSTtrp;
	public WordSetOfLabelsMap m_mapSTwlp;
	public TagSetOfLabelsMap m_mapSTtlp;
	public WordSetOfLabelsMap m_mapN0wlp;
	public TagSetOfLabelsMap m_mapN0tlp;

	public LemmaMap m_mapSTl;
	public CoNLLCPOSMap m_mapSTc;
	public CoNLLFeatsMap m_mapSTf;

	public LemmaMap m_mapN0l;
	public CoNLLCPOSMap m_mapN0c;
	public CoNLLFeatsMap m_mapN0f;

	public LemmaMap m_mapN1l;
	public CoNLLCPOSMap m_mapN1c;
	public CoNLLFeatsMap m_mapN1f;
	
	public Weight(final String sPath, final boolean bTrain) {
		super(sPath, bTrain);
		m_mapSTw = new WordMap("StackWord");
		m_mapSTt = new TagMap("StackTag");
		m_mapSTwt = new TaggedWordMap("StackWordTag");

		m_mapN0w = new WordMap("NextWord");
		m_mapN0t = new TagMap("NextTag");
		m_mapN0wt = new TaggedWordMap("NextWordTag");

		m_mapN1w = new WordMap("Next+1Word");
		m_mapN1t = new TagMap("Next+1Tag");
		m_mapN1wt = new TaggedWordMap("Next+1WordTag");

		m_mapN2w = new WordMap("Next+2Word");
		m_mapN2t = new TagMap("Next+2Tag");
		m_mapN2wt = new TaggedWordMap("Next+2WordTag");

		m_mapSTHw = new WordMap("StackHeadWord");
		m_mapSTHt = new TagMap("StackHeadTag");
		m_mapSTi = new IntMap("StackLabel");

		m_mapSTHHw = new WordMap("StackHeadHeadWord");
		m_mapSTHHt = new TagMap("StackHeadHeadTag");
		m_mapSTHi = new IntMap("StackLabel");

		m_mapSTLDw = new WordMap("StackLDWord");
		m_mapSTLDt = new TagMap("StackLDTag");
		m_mapSTLDi = new IntMap("StackLDLabel");

		m_mapSTRDw = new WordMap("StackRDWord");
		m_mapSTRDt = new TagMap("StackRDTag");
		m_mapSTRDi = new IntMap("StackRDLabel");

		m_mapN0LDw = new WordMap("NextLDWord");
		m_mapN0LDt = new TagMap("NextLDTag");
		m_mapN0LDi = new IntMap("NextLDLabel");

		m_mapSTL2Dw = new WordMap("StackL2DWord");
		m_mapSTL2Dt = new TagMap("StackL2DTag");
		m_mapSTL2Di = new IntMap("StackL2DLabel");

		m_mapSTR2Dw = new WordMap("StackR2DWord");
		m_mapSTR2Dt = new TagMap("StackR2DTag");
		m_mapSTR2Di = new IntMap("StackR2DLabel");

		m_mapN0L2Dw = new WordMap("NextL2DWord");
		m_mapN0L2Dt = new TagMap("NextL2DTag");
		m_mapN0L2Di = new IntMap("NextL2DLabel");

		m_mapHTw = new WordMap("HeadStackWord");
		m_mapHTt = new TagMap("HeadStackTag");
		m_mapHTwt = new TaggedWordMap("HeadStackWordTag");

		m_mapSTwtN0wt = new TwoTaggedWordsMap("StackWordTagNextWordTag");
		m_mapSTwtN0w = new WordWordTagMap("StackWordTagNextWord");
		m_mapSTwN0wt = new WordWordTagMap("StackWordNextWordTag");
		m_mapSTtN0wt = new WordTagTagMap("StackTagNextWordTag");
		m_mapSTwtN0t = new WordTagTagMap("StackWordTagNextTag");
		m_mapSTwN0w = new TwoWordsMap("StackWordNextWord");
		m_mapSTtN0t = new TagSet2Map("StackTagNextTag");
		
		m_mapN0tN1t = new TagSet2Map("NextTagNext+1Tag");
		m_mapN0tN1tN2t = new TagSet3Map("NextTagTrigram");
		m_mapSTtN0tN1t = new TagSet3Map("StackTagNextTagNext+1Tag");
		m_mapSTtN0tN0LDt = new TagSet3Map("StackTagNextTagNextLDTag");
		m_mapN0tN0LDtN0L2Dt = new TagSet3Map("StackTagNextTagNextLDTagNextTagNextL2DTag");
		m_mapSTHtSTtN0t = new TagSet3Map("StackHeadTagStackTagNextTag");
		m_mapHTtHT2tN0t = new TagSet3Map("HeadStackTagHeadStack2TagNextTag");
		m_mapSTHHtSTHtSTt = new TagSet3Map("StackHeadHeadTagStackHeadTagStackTag");
		m_mapSTtSTLDtN0t = new TagSet3Map("StackTagStackLDTagNextTag");
		m_mapSTtSTLDtSTL2Dt = new TagSet3Map("StackTagStackLDTagStackL2DTag");
		m_mapSTtSTRDtN0t = new TagSet3Map("StackTagStackRDTagNextTag");
		m_mapSTtSTRDtSTR2Dt = new TagSet3Map("StackTagStackRDTagStackR2DTag");

		m_mapSTwd = new WordIntMap("StackWordDist");
		m_mapSTtd = new TagIntMap("StackTagDist");
		m_mapN0wd = new WordIntMap("NextWordDist");
		m_mapN0td = new TagIntMap("NextTagDist");
		m_mapSTwN0wd = new WordWordIntMap("StackWordNextWordDist");
		m_mapSTtN0td = new TagTagIntMap("StackTagNextTagDist");

		m_mapSTwra = new WordIntMap("StackWordRightArity");
		m_mapSTtra = new TagIntMap("StackTagRightArity");
		m_mapSTwla = new WordIntMap("StackWordLeftArity");
		m_mapSTtla = new TagIntMap("StackTagLeftArity");
		m_mapN0wla = new WordIntMap("NextWordRightArity");
		m_mapN0tla = new TagIntMap("NextTagRightArity");

		m_mapSTwrp = new WordSetOfLabelsMap("StackWordRightSetoftags");
		m_mapSTtrp = new TagSetOfLabelsMap("StackTagRightSetoftags");
		m_mapSTwlp = new WordSetOfLabelsMap("StackWordLeftSetoftags");
		m_mapSTtlp = new TagSetOfLabelsMap("StackTagLeftSetoftags");
		m_mapN0wlp = new WordSetOfLabelsMap("Next0WordLeftSetoftags");
		m_mapN0tlp = new TagSetOfLabelsMap("Next0TagLeftSetoftags");

		m_mapSTl = new LemmaMap("StackLemma");
		m_mapSTc = new CoNLLCPOSMap("StackCPOS");
		m_mapSTf = new CoNLLFeatsMap("StackFeats");

		m_mapN0l = new LemmaMap("NextLemma");
		m_mapN0c = new CoNLLCPOSMap("NextCPOS");
		m_mapN0f = new CoNLLFeatsMap("NextFeats");

		m_mapN1l = new LemmaMap("Next+1Lemma");
		m_mapN1c = new CoNLLCPOSMap("Next+1CPOS");
		m_mapN1f = new CoNLLFeatsMap("Next+1Feats");
		
		loadScores();
	}
	
	@Override
	public void loadScores() {
		long time_start = System.currentTimeMillis();
		System.out.print("Loading scores...");
		System.out.flush();
		File file = new File(m_sRecordPath);
		if (!file.exists()) {
			System.out.println("empty.");
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			br.readLine();
			br.readLine();
			br.readLine();
			
			m_mapSTw.loadScoresFromFileStream(br);
			m_mapSTt.loadScoresFromFileStream(br);
			m_mapSTwt.loadScoresFromFileStream(br);

			m_mapN0w.loadScoresFromFileStream(br);
			m_mapN0t.loadScoresFromFileStream(br);
			m_mapN0wt.loadScoresFromFileStream(br);

			m_mapN1w.loadScoresFromFileStream(br);
			m_mapN1t.loadScoresFromFileStream(br);
			m_mapN1wt.loadScoresFromFileStream(br);

			m_mapN2w.loadScoresFromFileStream(br);
			m_mapN2t.loadScoresFromFileStream(br);
			m_mapN2wt.loadScoresFromFileStream(br);

			m_mapSTHw.loadScoresFromFileStream(br);
			m_mapSTHt.loadScoresFromFileStream(br);
			m_mapSTi.loadScoresFromFileStream(br);

			m_mapSTHHw.loadScoresFromFileStream(br);
			m_mapSTHHt.loadScoresFromFileStream(br);
			m_mapSTHi.loadScoresFromFileStream(br);

			m_mapSTLDw.loadScoresFromFileStream(br);
			m_mapSTLDt.loadScoresFromFileStream(br);
			m_mapSTLDi.loadScoresFromFileStream(br);

			m_mapSTRDw.loadScoresFromFileStream(br);
			m_mapSTRDt.loadScoresFromFileStream(br);
			m_mapSTRDi.loadScoresFromFileStream(br);

			m_mapN0LDw.loadScoresFromFileStream(br);
			m_mapN0LDt.loadScoresFromFileStream(br);
			m_mapN0LDi.loadScoresFromFileStream(br);

			m_mapSTL2Dw.loadScoresFromFileStream(br);
			m_mapSTL2Dt.loadScoresFromFileStream(br);
			m_mapSTL2Di.loadScoresFromFileStream(br);

			m_mapSTR2Dw.loadScoresFromFileStream(br);
			m_mapSTR2Dt.loadScoresFromFileStream(br);
			m_mapSTR2Di.loadScoresFromFileStream(br);

			m_mapN0L2Dw.loadScoresFromFileStream(br);
			m_mapN0L2Dt.loadScoresFromFileStream(br);
			m_mapN0L2Di.loadScoresFromFileStream(br);

			m_mapHTw.loadScoresFromFileStream(br);
			m_mapHTt.loadScoresFromFileStream(br);
			m_mapHTwt.loadScoresFromFileStream(br);

			m_mapSTwtN0wt.loadScoresFromFileStream(br);
			m_mapSTwtN0w.loadScoresFromFileStream(br);
			m_mapSTwN0wt.loadScoresFromFileStream(br);
			m_mapSTtN0wt.loadScoresFromFileStream(br);
			m_mapSTwtN0t.loadScoresFromFileStream(br);
			m_mapSTwN0w.loadScoresFromFileStream(br);
			m_mapSTtN0t.loadScoresFromFileStream(br);

			m_mapN0tN1t.loadScoresFromFileStream(br);
			m_mapN0tN1tN2t.loadScoresFromFileStream(br);
			m_mapSTtN0tN1t.loadScoresFromFileStream(br);
			m_mapSTtN0tN0LDt.loadScoresFromFileStream(br);
			m_mapN0tN0LDtN0L2Dt.loadScoresFromFileStream(br);
			m_mapSTHtSTtN0t.loadScoresFromFileStream(br);
			m_mapHTtHT2tN0t.loadScoresFromFileStream(br);
			m_mapSTHHtSTHtSTt.loadScoresFromFileStream(br);
			m_mapSTtSTLDtN0t.loadScoresFromFileStream(br);
			m_mapSTtSTLDtSTL2Dt.loadScoresFromFileStream(br);
			m_mapSTtSTRDtN0t.loadScoresFromFileStream(br);
			m_mapSTtSTRDtSTR2Dt.loadScoresFromFileStream(br);

			m_mapSTwd.loadScoresFromFileStream(br);
			m_mapSTtd.loadScoresFromFileStream(br);
			m_mapN0wd.loadScoresFromFileStream(br);
			m_mapN0td.loadScoresFromFileStream(br);
			m_mapSTwN0wd.loadScoresFromFileStream(br);
			m_mapSTtN0td.loadScoresFromFileStream(br);

			m_mapSTwra.loadScoresFromFileStream(br);
			m_mapSTtra.loadScoresFromFileStream(br);
			m_mapSTwla.loadScoresFromFileStream(br);
			m_mapSTtla.loadScoresFromFileStream(br);
			m_mapN0wla.loadScoresFromFileStream(br);
			m_mapN0tla.loadScoresFromFileStream(br);

			m_mapSTwrp.loadScoresFromFileStream(br);
			m_mapSTtrp.loadScoresFromFileStream(br);
			m_mapSTwlp.loadScoresFromFileStream(br);
			m_mapSTtlp.loadScoresFromFileStream(br);
			m_mapN0wlp.loadScoresFromFileStream(br);
			m_mapN0tlp.loadScoresFromFileStream(br);

			m_mapSTl.loadScoresFromFileStream(br);
			m_mapSTc.loadScoresFromFileStream(br);
			m_mapSTf.loadScoresFromFileStream(br);

			m_mapN0l.loadScoresFromFileStream(br);
			m_mapN0c.loadScoresFromFileStream(br);
			m_mapN0f.loadScoresFromFileStream(br);

			m_mapN1l.loadScoresFromFileStream(br);
			m_mapN1c.loadScoresFromFileStream(br);
			m_mapN1f.loadScoresFromFileStream(br);
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done. (" + ((System.currentTimeMillis() - time_start) / 1000.0) + "s)");
	}
	
	@Override
	public void saveScores() {
		System.out.println("Saving scores...");
		System.out.flush();
		File file = new File(m_sRecordPath);
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			bw.write("Dependency labels:");
			bw.newLine();
			for (int i = Macros.DEP_FIRST; i < Macros.DEP_COUNT; ++i) {
				bw.write(Macros.DEP_STRINGS[i] + " ");
			}
			bw.newLine();
			bw.newLine();
			
			m_mapSTw.saveScoresToFileStream(bw);
			m_mapSTt.saveScoresToFileStream(bw);
			m_mapSTwt.saveScoresToFileStream(bw);

			m_mapN0w.saveScoresToFileStream(bw);
			m_mapN0t.saveScoresToFileStream(bw);
			m_mapN0wt.saveScoresToFileStream(bw);

			m_mapN1w.saveScoresToFileStream(bw);
			m_mapN1t.saveScoresToFileStream(bw);
			m_mapN1wt.saveScoresToFileStream(bw);

			m_mapN2w.saveScoresToFileStream(bw);
			m_mapN2t.saveScoresToFileStream(bw);
			m_mapN2wt.saveScoresToFileStream(bw);

			m_mapSTHw.saveScoresToFileStream(bw);
			m_mapSTHt.saveScoresToFileStream(bw);
			m_mapSTi.saveScoresToFileStream(bw);

			m_mapSTHHw.saveScoresToFileStream(bw);
			m_mapSTHHt.saveScoresToFileStream(bw);
			m_mapSTHi.saveScoresToFileStream(bw);

			m_mapSTLDw.saveScoresToFileStream(bw);
			m_mapSTLDt.saveScoresToFileStream(bw);
			m_mapSTLDi.saveScoresToFileStream(bw);

			m_mapSTRDw.saveScoresToFileStream(bw);
			m_mapSTRDt.saveScoresToFileStream(bw);
			m_mapSTRDi.saveScoresToFileStream(bw);

			m_mapN0LDw.saveScoresToFileStream(bw);
			m_mapN0LDt.saveScoresToFileStream(bw);
			m_mapN0LDi.saveScoresToFileStream(bw);

			m_mapSTL2Dw.saveScoresToFileStream(bw);
			m_mapSTL2Dt.saveScoresToFileStream(bw);
			m_mapSTL2Di.saveScoresToFileStream(bw);

			m_mapSTR2Dw.saveScoresToFileStream(bw);
			m_mapSTR2Dt.saveScoresToFileStream(bw);
			m_mapSTR2Di.saveScoresToFileStream(bw);

			m_mapN0L2Dw.saveScoresToFileStream(bw);
			m_mapN0L2Dt.saveScoresToFileStream(bw);
			m_mapN0L2Di.saveScoresToFileStream(bw);

			m_mapHTw.saveScoresToFileStream(bw);
			m_mapHTt.saveScoresToFileStream(bw);
			m_mapHTwt.saveScoresToFileStream(bw);

			m_mapSTwtN0wt.saveScoresToFileStream(bw);
			m_mapSTwtN0w.saveScoresToFileStream(bw);
			m_mapSTwN0wt.saveScoresToFileStream(bw);
			m_mapSTtN0wt.saveScoresToFileStream(bw);
			m_mapSTwtN0t.saveScoresToFileStream(bw);
			m_mapSTwN0w.saveScoresToFileStream(bw);
			m_mapSTtN0t.saveScoresToFileStream(bw);

			m_mapN0tN1t.saveScoresToFileStream(bw);
			m_mapN0tN1tN2t.saveScoresToFileStream(bw);
			m_mapSTtN0tN1t.saveScoresToFileStream(bw);
			m_mapSTtN0tN0LDt.saveScoresToFileStream(bw);
			m_mapN0tN0LDtN0L2Dt.saveScoresToFileStream(bw);
			m_mapSTHtSTtN0t.saveScoresToFileStream(bw);
			m_mapHTtHT2tN0t.saveScoresToFileStream(bw);
			m_mapSTHHtSTHtSTt.saveScoresToFileStream(bw);
			m_mapSTtSTLDtN0t.saveScoresToFileStream(bw);
			m_mapSTtSTLDtSTL2Dt.saveScoresToFileStream(bw);
			m_mapSTtSTRDtN0t.saveScoresToFileStream(bw);
			m_mapSTtSTRDtSTR2Dt.saveScoresToFileStream(bw);

			m_mapSTwd.saveScoresToFileStream(bw);
			m_mapSTtd.saveScoresToFileStream(bw);
			m_mapN0wd.saveScoresToFileStream(bw);
			m_mapN0td.saveScoresToFileStream(bw);
			m_mapSTwN0wd.saveScoresToFileStream(bw);
			m_mapSTtN0td.saveScoresToFileStream(bw);

			m_mapSTwra.saveScoresToFileStream(bw);
			m_mapSTtra.saveScoresToFileStream(bw);
			m_mapSTwla.saveScoresToFileStream(bw);
			m_mapSTtla.saveScoresToFileStream(bw);
			m_mapN0wla.saveScoresToFileStream(bw);
			m_mapN0tla.saveScoresToFileStream(bw);

			m_mapSTwrp.saveScoresToFileStream(bw);
			m_mapSTtrp.saveScoresToFileStream(bw);
			m_mapSTwlp.saveScoresToFileStream(bw);
			m_mapSTtlp.saveScoresToFileStream(bw);
			m_mapN0wlp.saveScoresToFileStream(bw);
			m_mapN0tlp.saveScoresToFileStream(bw);

			m_mapSTl.saveScoresToFileStream(bw);
			m_mapSTc.saveScoresToFileStream(bw);
			m_mapSTf.saveScoresToFileStream(bw);

			m_mapN0l.saveScoresToFileStream(bw);
			m_mapN0c.saveScoresToFileStream(bw);
			m_mapN0f.saveScoresToFileStream(bw);

			m_mapN1l.saveScoresToFileStream(bw);
			m_mapN1c.saveScoresToFileStream(bw);
			m_mapN1f.saveScoresToFileStream(bw);
			
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done.");
	}
	
	public void computeAverageFeatureWeights(final int round) {
		System.out.println("Computing averaged (total) feature vector...");
		System.out.flush();

		m_mapSTw.computeAverage(round);
		m_mapSTt.computeAverage(round);
		m_mapSTwt.computeAverage(round);

		m_mapN0w.computeAverage(round);
		m_mapN0t.computeAverage(round);
		m_mapN0wt.computeAverage(round);

		m_mapN1w.computeAverage(round);
		m_mapN1t.computeAverage(round);
		m_mapN1wt.computeAverage(round);

		m_mapN2w.computeAverage(round);
		m_mapN2t.computeAverage(round);
		m_mapN2wt.computeAverage(round);

		m_mapSTHw.computeAverage(round);
		m_mapSTHt.computeAverage(round);
		m_mapSTi.computeAverage(round);

		m_mapSTHHw.computeAverage(round);
		m_mapSTHHt.computeAverage(round);
		m_mapSTHi.computeAverage(round);

		m_mapSTLDw.computeAverage(round);
		m_mapSTLDt.computeAverage(round);
		m_mapSTLDi.computeAverage(round);

		m_mapSTRDw.computeAverage(round);
		m_mapSTRDt.computeAverage(round);
		m_mapSTRDi.computeAverage(round);

		m_mapN0LDw.computeAverage(round);
		m_mapN0LDt.computeAverage(round);
		m_mapN0LDi.computeAverage(round);

		m_mapSTL2Dw.computeAverage(round);
		m_mapSTL2Dt.computeAverage(round);
		m_mapSTL2Di.computeAverage(round);

		m_mapSTR2Dw.computeAverage(round);
		m_mapSTR2Dt.computeAverage(round);
		m_mapSTR2Di.computeAverage(round);

		m_mapN0L2Dw.computeAverage(round);
		m_mapN0L2Dt.computeAverage(round);
		m_mapN0L2Di.computeAverage(round);

		m_mapHTw.computeAverage(round);
		m_mapHTt.computeAverage(round);
		m_mapHTwt.computeAverage(round);

		m_mapSTwtN0wt.computeAverage(round);
		m_mapSTwtN0w.computeAverage(round);
		m_mapSTwN0wt.computeAverage(round);
		m_mapSTtN0wt.computeAverage(round);
		m_mapSTwtN0t.computeAverage(round);
		m_mapSTwN0w.computeAverage(round);
		m_mapSTtN0t.computeAverage(round);

		m_mapN0tN1t.computeAverage(round);
		m_mapN0tN1tN2t.computeAverage(round);
		m_mapSTtN0tN1t.computeAverage(round);
		m_mapSTtN0tN0LDt.computeAverage(round);
		m_mapN0tN0LDtN0L2Dt.computeAverage(round);
		m_mapSTHtSTtN0t.computeAverage(round);
		m_mapHTtHT2tN0t.computeAverage(round);
		m_mapSTHHtSTHtSTt.computeAverage(round);
		m_mapSTtSTLDtN0t.computeAverage(round);
		m_mapSTtSTLDtSTL2Dt.computeAverage(round);
		m_mapSTtSTRDtN0t.computeAverage(round);
		m_mapSTtSTRDtSTR2Dt.computeAverage(round);

		m_mapSTwd.computeAverage(round);
		m_mapSTtd.computeAverage(round);
		m_mapN0wd.computeAverage(round);
		m_mapN0td.computeAverage(round);
		m_mapSTwN0wd.computeAverage(round);
		m_mapSTtN0td.computeAverage(round);

		m_mapSTwra.computeAverage(round);
		m_mapSTtra.computeAverage(round);
		m_mapSTwla.computeAverage(round);
		m_mapSTtla.computeAverage(round);
		m_mapN0wla.computeAverage(round);
		m_mapN0tla.computeAverage(round);

		m_mapSTwrp.computeAverage(round);
		m_mapSTtrp.computeAverage(round);
		m_mapSTwlp.computeAverage(round);
		m_mapSTtlp.computeAverage(round);
		m_mapN0wlp.computeAverage(round);
		m_mapN0tlp.computeAverage(round);

		m_mapSTl.computeAverage(round);
		m_mapSTc.computeAverage(round);
		m_mapSTf.computeAverage(round);

		m_mapN0l.computeAverage(round);
		m_mapN0c.computeAverage(round);
		m_mapN0f.computeAverage(round);

		m_mapN1l.computeAverage(round);
		m_mapN1c.computeAverage(round);
		m_mapN1f.computeAverage(round);
		
		System.out.println("done.");
	}
	
}
