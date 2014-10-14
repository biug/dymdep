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
import common.parser.implementations.map.CCGTagSet3Map;
import common.parser.implementations.map.IntMap;
import common.parser.implementations.map.POSTagIntMap;
import common.parser.implementations.map.POSTagMap;
import common.parser.implementations.map.POSTagPOSTagIntMap;
import common.parser.implementations.map.POSTagSet2Map;
import common.parser.implementations.map.POSTagSet3Map;
import common.parser.implementations.map.POSTagSetOfCCGLabelsMap;
import common.parser.implementations.map.POSTagSetOfDepLabelsMap;
import common.parser.implementations.map.SetOfCCGLabelsIntMap;
import common.parser.implementations.map.SetOfDepLabelsIntMap;
import common.parser.implementations.map.SyntaxTreePathMap;
import common.parser.implementations.map.TwoIntsMap;
import common.parser.implementations.map.TwoPOSTaggedWordsMap;
import common.parser.implementations.map.TwoWordsMap;
import common.parser.implementations.map.WordIntMap;
import common.parser.implementations.map.WordMap;
import common.parser.implementations.map.WordPOSTagMap;
import common.parser.implementations.map.WordPOSTagPOSTagMap;
import common.parser.implementations.map.WordSetOfCCGLabelsMap;
import common.parser.implementations.map.WordSetOfDepLabelsMap;
import common.parser.implementations.map.WordWordIntMap;
import common.parser.implementations.map.WordWordPOSTagMap;

public class Weight extends WeightBase {
	public WordMap m_mapSTw;
	public POSTagMap m_mapSTpt;
	public WordPOSTagMap m_mapSTwpt;
	public IntMap m_mapSTct;
	public WordIntMap m_mapSTwct;

	public WordMap m_mapN0w;
	public POSTagMap m_mapN0pt;
	public WordPOSTagMap m_mapN0wpt;

	public WordMap m_mapN1w;
	public POSTagMap m_mapN1pt;
	public WordPOSTagMap m_mapN1wpt;

	public WordMap m_mapN2w;
	public POSTagMap m_mapN2pt;
	public WordPOSTagMap m_mapN2wpt;

	public WordMap m_mapSTLHw;
	public POSTagMap m_mapSTLHpt;
	public IntMap m_mapSTLHct;
	public IntMap m_mapSTlhl;

	public WordMap m_mapN0LHw;
	public POSTagMap m_mapN0LHpt;
	public IntMap m_mapN0LHct;
	public IntMap m_mapN0lhl;

	public WordMap m_mapSTRHw;
	public POSTagMap m_mapSTRHpt;
	public IntMap m_mapSTRHct;
	public IntMap m_mapSTrhl;

	public WordMap m_mapSTL2Hw;
	public POSTagMap m_mapSTL2Hpt;
	public IntMap m_mapSTL2Hct;
	public IntMap m_mapSTl2hl;

	public WordMap m_mapN0L2Hw;
	public POSTagMap m_mapN0L2Hpt;
	public IntMap m_mapN0L2Hct;
	public IntMap m_mapN0l2hl;

	public WordMap m_mapSTR2Hw;
	public POSTagMap m_mapSTR2Hpt;
	public IntMap m_mapSTR2Hct;
	public IntMap m_mapSTr2hl;

	public WordMap m_mapSTLHLHw;
	public POSTagMap m_mapSTLHLHpt;
	public IntMap m_mapSTLHLHct;
	public IntMap m_mapSTLHlhl;

	public WordMap m_mapSTLHRHw;
	public POSTagMap m_mapSTLHRHpt;
	public IntMap m_mapSTLHRHct;
	public IntMap m_mapSTLHrhl;

	public WordMap m_mapSTRHLHw;
	public POSTagMap m_mapSTRHLHpt;
	public IntMap m_mapSTRHLHct;
	public IntMap m_mapSTRHlhl;

	public WordMap m_mapSTRHRHw;
	public POSTagMap m_mapSTRHRHpt;
	public IntMap m_mapSTRHRHct;
	public IntMap m_mapSTRHrhl;

	public WordMap m_mapSTLDw;
	public POSTagMap m_mapSTLDpt;
	public IntMap m_mapSTLDct;
	public IntMap m_mapSTldl;

	public WordMap m_mapSTRDw;
	public POSTagMap m_mapSTRDpt;
	public IntMap m_mapSTRDct;
	public IntMap m_mapSTrdl;

	public WordMap m_mapN0LDw;
	public POSTagMap m_mapN0LDpt;
	public IntMap m_mapN0LDct;
	public IntMap m_mapN0ldl;

	public WordMap m_mapSTL2Dw;
	public POSTagMap m_mapSTL2Dpt;
	public IntMap m_mapSTL2Dct;
	public IntMap m_mapSTl2dl;

	public WordMap m_mapSTR2Dw;
	public POSTagMap m_mapSTR2Dpt;
	public IntMap m_mapSTR2Dct;
	public IntMap m_mapSTr2dl;

	public WordMap m_mapN0L2Dw;
	public POSTagMap m_mapN0L2Dpt;
	public IntMap m_mapN0L2Dct;
	public IntMap m_mapN0l2dl;

	public TwoPOSTaggedWordsMap m_mapSTwptN0wpt;
	public WordWordPOSTagMap m_mapSTwptN0w;
	public WordWordPOSTagMap m_mapSTwN0wpt;
	public WordPOSTagPOSTagMap m_mapSTptN0wpt;
	public WordPOSTagPOSTagMap m_mapSTwptN0pt;
	public WordWordIntMap m_mapSTwctN0w;
	public TwoWordsMap m_mapSTwN0w;

	public POSTagSet2Map m_mapSTptN0pt;
	public POSTagSet2Map m_mapN0ptN1pt;
	public POSTagSet3Map m_mapN0ptN1ptN2pt;
	public POSTagSet3Map m_mapSTptN0ptN1pt;
	public POSTagSet3Map m_mapSTptN0ptN0LDpt;
	public POSTagSet3Map m_mapN0ptN0LDptN0L2Dpt;
	public POSTagSet3Map m_mapSTLHptSTptN0pt;
	public POSTagSet3Map m_mapSTRHptSTptN0pt;
	public POSTagSet3Map m_mapSTLHLHptSTLHptSTpt;
	public POSTagSet3Map m_mapSTLHRHptSTLHptSTpt;
	public POSTagSet3Map m_mapSTRHLHptSTRHptSTpt;
	public POSTagSet3Map m_mapSTRHRHptSTRHptSTpt;
	public POSTagSet3Map m_mapSTptSTLDptN0pt;
	public POSTagSet3Map m_mapSTptSTLDptSTL2Dpt;
	public POSTagSet3Map m_mapSTptSTRDptN0pt;
	public POSTagSet3Map m_mapSTptSTRDptSTR2Dpt;

	public CCGTagSet3Map m_mapSTLHLHctSTLHctSTct;
	public CCGTagSet3Map m_mapSTLHRHctSTLHctSTct;
	public CCGTagSet3Map m_mapSTRHLHctSTRHctSTct;
	public CCGTagSet3Map m_mapSTRHRHctSTRHctSTct;
	public CCGTagSet3Map m_mapSTctSTLDctSTL2Dct;
	public CCGTagSet3Map m_mapSTctSTRDctSTR2Dct;

	public WordIntMap m_mapSTwd0;
	public POSTagIntMap m_mapSTptd0;
	public TwoIntsMap m_mapSTctd0;
	public WordIntMap m_mapN0wd0;
	public POSTagIntMap m_mapN0ptd0;
	public WordWordIntMap m_mapSTwN0wd0;
	public POSTagPOSTagIntMap m_mapSTptN0ptd0;

	public WordIntMap m_mapSTwrda;
	public POSTagIntMap m_mapSTptrda;
	public TwoIntsMap m_mapSTctrda;
	public WordIntMap m_mapSTwlda;
	public POSTagIntMap m_mapSTptlda;
	public TwoIntsMap m_mapSTctlda;
	public WordIntMap m_mapN0wlda;
	public POSTagIntMap m_mapN0ptlda;

	public WordIntMap m_mapSTwrha;
	public POSTagIntMap m_mapSTptrha;
	public TwoIntsMap m_mapSTctrha;
	public WordIntMap m_mapSTwlha;
	public POSTagIntMap m_mapSTptlha;
	public TwoIntsMap m_mapSTctlha;
	public WordIntMap m_mapN0wlha;
	public POSTagIntMap m_mapN0ptlha;

	public WordSetOfDepLabelsMap m_mapSTwrp;
	public POSTagSetOfDepLabelsMap m_mapSTptrp;
	public SetOfDepLabelsIntMap m_mapSTctrp;

	public WordSetOfDepLabelsMap m_mapSTwlp;
	public WordSetOfCCGLabelsMap m_mapSTwlc;
	public POSTagSetOfDepLabelsMap m_mapSTptlp;
	public POSTagSetOfCCGLabelsMap m_mapSTptlc;
	public SetOfDepLabelsIntMap m_mapSTctlp;
	public SetOfCCGLabelsIntMap m_mapSTctlc;

	public WordSetOfDepLabelsMap m_mapN0wlp;
	public WordSetOfCCGLabelsMap m_mapN0wlc;
	public POSTagSetOfDepLabelsMap m_mapN0ptlp;
	public POSTagSetOfCCGLabelsMap m_mapN0ptlc;

	public SyntaxTreePathMap m_mapSTP;

	public Weight(final String sPath, final boolean bTrain) {
		super(sPath, bTrain);

		m_mapSTw = new WordMap("a");
		m_mapSTpt = new POSTagMap("a");
		m_mapSTwpt = new WordPOSTagMap("a");
		m_mapSTct = new IntMap("a");
		m_mapSTwct = new WordIntMap("a");

		m_mapN0w = new WordMap("a");
		m_mapN0pt = new POSTagMap("a");
		m_mapN0wpt = new WordPOSTagMap("a");

		m_mapN1w = new WordMap("a");
		m_mapN1pt = new POSTagMap("a");
		m_mapN1wpt = new WordPOSTagMap("a");

		m_mapN2w = new WordMap("a");
		m_mapN2pt = new POSTagMap("a");
		m_mapN2wpt = new WordPOSTagMap("a");

		m_mapSTLHw = new WordMap("a");
		m_mapSTLHpt = new POSTagMap("a");
		m_mapSTLHct = new IntMap("a");
		m_mapSTlhl = new IntMap("a");

		m_mapN0LHw = new WordMap("a");
		m_mapN0LHpt = new POSTagMap("a");
		m_mapN0LHct = new IntMap("a");
		m_mapN0lhl = new IntMap("a");

		m_mapSTRHw = new WordMap("a");
		m_mapSTRHpt = new POSTagMap("a");
		m_mapSTRHct = new IntMap("a");
		m_mapSTrhl = new IntMap("a");

		m_mapSTL2Hw = new WordMap("a");
		m_mapSTL2Hpt = new POSTagMap("a");
		m_mapSTL2Hct = new IntMap("a");
		m_mapSTl2hl = new IntMap("a");

		m_mapN0L2Hw = new WordMap("a");
		m_mapN0L2Hpt = new POSTagMap("a");
		m_mapN0L2Hct = new IntMap("a");
		m_mapN0l2hl = new IntMap("a");

		m_mapSTR2Hw = new WordMap("a");
		m_mapSTR2Hpt = new POSTagMap("a");
		m_mapSTR2Hct = new IntMap("a");
		m_mapSTr2hl = new IntMap("a");

		m_mapSTLHLHw = new WordMap("a");
		m_mapSTLHLHpt = new POSTagMap("a");
		m_mapSTLHLHct = new IntMap("a");
		m_mapSTLHlhl = new IntMap("a");

		m_mapSTLHRHw = new WordMap("a");
		m_mapSTLHRHpt = new POSTagMap("a");
		m_mapSTLHRHct = new IntMap("a");
		m_mapSTLHrhl = new IntMap("a");

		m_mapSTRHLHw = new WordMap("a");
		m_mapSTRHLHpt = new POSTagMap("a");
		m_mapSTRHLHct = new IntMap("a");
		m_mapSTRHlhl = new IntMap("a");

		m_mapSTRHRHw = new WordMap("a");
		m_mapSTRHRHpt = new POSTagMap("a");
		m_mapSTRHRHct = new IntMap("a");
		m_mapSTRHrhl = new IntMap("a");

		m_mapSTLDw = new WordMap("a");
		m_mapSTLDpt = new POSTagMap("a");
		m_mapSTLDct = new IntMap("a");
		m_mapSTldl = new IntMap("a");

		m_mapSTRDw = new WordMap("a");
		m_mapSTRDpt = new POSTagMap("a");
		m_mapSTRDct = new IntMap("a");
		m_mapSTrdl = new IntMap("a");

		m_mapN0LDw = new WordMap("a");
		m_mapN0LDpt = new POSTagMap("a");
		m_mapN0LDct = new IntMap("a");
		m_mapN0ldl = new IntMap("a");

		m_mapSTL2Dw = new WordMap("a");
		m_mapSTL2Dpt = new POSTagMap("a");
		m_mapSTL2Dct = new IntMap("a");
		m_mapSTl2dl = new IntMap("a");

		m_mapSTR2Dw = new WordMap("a");
		m_mapSTR2Dpt = new POSTagMap("a");
		m_mapSTR2Dct = new IntMap("a");
		m_mapSTr2dl = new IntMap("a");

		m_mapN0L2Dw = new WordMap("a");
		m_mapN0L2Dpt = new POSTagMap("a");
		m_mapN0L2Dct = new IntMap("a");
		m_mapN0l2dl = new IntMap("a");

		m_mapSTwptN0wpt = new TwoPOSTaggedWordsMap("a");
		m_mapSTwptN0w = new WordWordPOSTagMap("a");
		m_mapSTwN0wpt = new WordWordPOSTagMap("a");
		m_mapSTptN0wpt = new WordPOSTagPOSTagMap("a");
		m_mapSTwptN0pt = new WordPOSTagPOSTagMap("a");
		m_mapSTwctN0w = new WordWordIntMap("a");
		m_mapSTwN0w = new TwoWordsMap("a");

		m_mapSTptN0pt = new POSTagSet2Map("a");
		m_mapN0ptN1pt = new POSTagSet2Map("a");
		m_mapN0ptN1ptN2pt = new POSTagSet3Map("a");
		m_mapSTptN0ptN1pt = new POSTagSet3Map("a");
		m_mapSTptN0ptN0LDpt = new POSTagSet3Map("a");
		m_mapN0ptN0LDptN0L2Dpt = new POSTagSet3Map("a");
		m_mapSTLHptSTptN0pt = new POSTagSet3Map("a");
		m_mapSTRHptSTptN0pt = new POSTagSet3Map("a");
		m_mapSTLHLHptSTLHptSTpt = new POSTagSet3Map("a");
		m_mapSTLHRHptSTLHptSTpt = new POSTagSet3Map("a");
		m_mapSTRHLHptSTRHptSTpt = new POSTagSet3Map("a");
		m_mapSTRHRHptSTRHptSTpt = new POSTagSet3Map("a");
		m_mapSTptSTLDptN0pt = new POSTagSet3Map("a");
		m_mapSTptSTLDptSTL2Dpt = new POSTagSet3Map("a");
		m_mapSTptSTRDptN0pt = new POSTagSet3Map("a");
		m_mapSTptSTRDptSTR2Dpt = new POSTagSet3Map("a");

		m_mapSTLHLHctSTLHctSTct = new CCGTagSet3Map("a");
		m_mapSTLHRHctSTLHctSTct = new CCGTagSet3Map("a");
		m_mapSTRHLHctSTRHctSTct = new CCGTagSet3Map("a");
		m_mapSTRHRHctSTRHctSTct = new CCGTagSet3Map("a");
		m_mapSTctSTLDctSTL2Dct = new CCGTagSet3Map("a");
		m_mapSTctSTRDctSTR2Dct = new CCGTagSet3Map("a");

		m_mapSTwd0 = new WordIntMap("a");
		m_mapSTptd0 = new POSTagIntMap("a");
		m_mapSTctd0 = new TwoIntsMap("a");
		m_mapN0wd0 = new WordIntMap("a");
		m_mapN0ptd0 = new POSTagIntMap("a");
		m_mapSTwN0wd0 = new WordWordIntMap("a");
		m_mapSTptN0ptd0 = new POSTagPOSTagIntMap("a");

		m_mapSTwrda = new WordIntMap("a");
		m_mapSTptrda = new POSTagIntMap("a");
		m_mapSTctrda = new TwoIntsMap("a");
		m_mapSTwlda = new WordIntMap("a");
		m_mapSTptlda = new POSTagIntMap("a");
		m_mapSTctlda = new TwoIntsMap("a");
		m_mapN0wlda = new WordIntMap("a");
		m_mapN0ptlda = new POSTagIntMap("a");

		m_mapSTwrha = new WordIntMap("a");
		m_mapSTptrha = new POSTagIntMap("a");
		m_mapSTctrha = new TwoIntsMap("a");
		m_mapSTwlha = new WordIntMap("a");
		m_mapSTptlha = new POSTagIntMap("a");
		m_mapSTctlha = new TwoIntsMap("a");
		m_mapN0wlha = new WordIntMap("a");
		m_mapN0ptlha = new POSTagIntMap("a");

		m_mapSTwrp = new WordSetOfDepLabelsMap("a");
		m_mapSTptrp = new POSTagSetOfDepLabelsMap("a");
		m_mapSTctrp = new SetOfDepLabelsIntMap("a");

		m_mapSTwlp = new WordSetOfDepLabelsMap("a");
		m_mapSTwlc = new WordSetOfCCGLabelsMap("a");
		m_mapSTptlp = new POSTagSetOfDepLabelsMap("a");
		m_mapSTptlc = new POSTagSetOfCCGLabelsMap("a");
		m_mapSTctlp = new SetOfDepLabelsIntMap("a");
		m_mapSTctlc = new SetOfCCGLabelsIntMap("a");

		m_mapN0wlp = new WordSetOfDepLabelsMap("a");
		m_mapN0wlc = new WordSetOfCCGLabelsMap("a");
		m_mapN0ptlp = new POSTagSetOfDepLabelsMap("a");
		m_mapN0ptlc = new POSTagSetOfCCGLabelsMap("a");

		m_mapSTP = new SyntaxTreePathMap("a");
		
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
			m_mapSTpt.loadScoresFromFileStream(br);
			m_mapSTwpt.loadScoresFromFileStream(br);
			m_mapSTct.loadScoresFromFileStream(br);
			m_mapSTwct.loadScoresFromFileStream(br);

			m_mapN0w.loadScoresFromFileStream(br);
			m_mapN0pt.loadScoresFromFileStream(br);
			m_mapN0wpt.loadScoresFromFileStream(br);

			m_mapN1w.loadScoresFromFileStream(br);
			m_mapN1pt.loadScoresFromFileStream(br);
			m_mapN1wpt.loadScoresFromFileStream(br);

			m_mapN2w.loadScoresFromFileStream(br);
			m_mapN2pt.loadScoresFromFileStream(br);
			m_mapN2wpt.loadScoresFromFileStream(br);

			m_mapSTLHw.loadScoresFromFileStream(br);
			m_mapSTLHpt.loadScoresFromFileStream(br);
			m_mapSTLHct.loadScoresFromFileStream(br);
			m_mapSTlhl.loadScoresFromFileStream(br);

			m_mapN0LHw.loadScoresFromFileStream(br);
			m_mapN0LHpt.loadScoresFromFileStream(br);
			m_mapN0LHct.loadScoresFromFileStream(br);
			m_mapN0lhl.loadScoresFromFileStream(br);

			m_mapSTRHw.loadScoresFromFileStream(br);
			m_mapSTRHpt.loadScoresFromFileStream(br);
			m_mapSTRHct.loadScoresFromFileStream(br);
			m_mapSTrhl.loadScoresFromFileStream(br);

			m_mapSTL2Hw.loadScoresFromFileStream(br);
			m_mapSTL2Hpt.loadScoresFromFileStream(br);
			m_mapSTL2Hct.loadScoresFromFileStream(br);
			m_mapSTl2hl.loadScoresFromFileStream(br);

			m_mapN0L2Hw.loadScoresFromFileStream(br);
			m_mapN0L2Hpt.loadScoresFromFileStream(br);
			m_mapN0L2Hct.loadScoresFromFileStream(br);
			m_mapN0l2hl.loadScoresFromFileStream(br);

			m_mapSTR2Hw.loadScoresFromFileStream(br);
			m_mapSTR2Hpt.loadScoresFromFileStream(br);
			m_mapSTR2Hct.loadScoresFromFileStream(br);
			m_mapSTr2hl.loadScoresFromFileStream(br);

			m_mapSTLHLHw.loadScoresFromFileStream(br);
			m_mapSTLHLHpt.loadScoresFromFileStream(br);
			m_mapSTLHLHct.loadScoresFromFileStream(br);
			m_mapSTLHlhl.loadScoresFromFileStream(br);

			m_mapSTLHRHw.loadScoresFromFileStream(br);
			m_mapSTLHRHpt.loadScoresFromFileStream(br);
			m_mapSTLHRHct.loadScoresFromFileStream(br);
			m_mapSTLHrhl.loadScoresFromFileStream(br);

			m_mapSTRHLHw.loadScoresFromFileStream(br);
			m_mapSTRHLHpt.loadScoresFromFileStream(br);
			m_mapSTRHLHct.loadScoresFromFileStream(br);
			m_mapSTRHlhl.loadScoresFromFileStream(br);

			m_mapSTRHRHw.loadScoresFromFileStream(br);
			m_mapSTRHRHpt.loadScoresFromFileStream(br);
			m_mapSTRHRHct.loadScoresFromFileStream(br);
			m_mapSTRHrhl.loadScoresFromFileStream(br);

			m_mapSTLDw.loadScoresFromFileStream(br);
			m_mapSTLDpt.loadScoresFromFileStream(br);
			m_mapSTLDct.loadScoresFromFileStream(br);
			m_mapSTldl.loadScoresFromFileStream(br);

			m_mapSTRDw.loadScoresFromFileStream(br);
			m_mapSTRDpt.loadScoresFromFileStream(br);
			m_mapSTRDct.loadScoresFromFileStream(br);
			m_mapSTrdl.loadScoresFromFileStream(br);

			m_mapN0LDw.loadScoresFromFileStream(br);
			m_mapN0LDpt.loadScoresFromFileStream(br);
			m_mapN0LDct.loadScoresFromFileStream(br);
			m_mapN0ldl.loadScoresFromFileStream(br);

			m_mapSTL2Dw.loadScoresFromFileStream(br);
			m_mapSTL2Dpt.loadScoresFromFileStream(br);
			m_mapSTL2Dct.loadScoresFromFileStream(br);
			m_mapSTl2dl.loadScoresFromFileStream(br);

			m_mapSTR2Dw.loadScoresFromFileStream(br);
			m_mapSTR2Dpt.loadScoresFromFileStream(br);
			m_mapSTR2Dct.loadScoresFromFileStream(br);
			m_mapSTr2dl.loadScoresFromFileStream(br);

			m_mapN0L2Dw.loadScoresFromFileStream(br);
			m_mapN0L2Dpt.loadScoresFromFileStream(br);
			m_mapN0L2Dct.loadScoresFromFileStream(br);
			m_mapN0l2dl.loadScoresFromFileStream(br);

			m_mapSTwptN0wpt.loadScoresFromFileStream(br);
			m_mapSTwptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0wpt.loadScoresFromFileStream(br);
			m_mapSTptN0wpt.loadScoresFromFileStream(br);
			m_mapSTwptN0pt.loadScoresFromFileStream(br);
			m_mapSTwctN0w.loadScoresFromFileStream(br);
			m_mapSTwN0w.loadScoresFromFileStream(br);

			m_mapSTptN0pt.loadScoresFromFileStream(br);
			m_mapN0ptN1pt.loadScoresFromFileStream(br);
			m_mapN0ptN1ptN2pt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapN0ptN0LDptN0L2Dpt.loadScoresFromFileStream(br);
			m_mapSTLHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapSTRHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapSTLHLHptSTLHptSTpt.loadScoresFromFileStream(br);
			m_mapSTLHRHptSTLHptSTpt.loadScoresFromFileStream(br);
			m_mapSTRHLHptSTRHptSTpt.loadScoresFromFileStream(br);
			m_mapSTRHRHptSTRHptSTpt.loadScoresFromFileStream(br);
			m_mapSTptSTLDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLDptSTL2Dpt.loadScoresFromFileStream(br);
			m_mapSTptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRDptSTR2Dpt.loadScoresFromFileStream(br);

			m_mapSTLHLHctSTLHctSTct.loadScoresFromFileStream(br);
			m_mapSTLHRHctSTLHctSTct.loadScoresFromFileStream(br);
			m_mapSTRHLHctSTRHctSTct.loadScoresFromFileStream(br);
			m_mapSTRHRHctSTRHctSTct.loadScoresFromFileStream(br);
			m_mapSTctSTLDctSTL2Dct.loadScoresFromFileStream(br);
			m_mapSTctSTRDctSTR2Dct.loadScoresFromFileStream(br);

			m_mapSTwd0.loadScoresFromFileStream(br);
			m_mapSTptd0.loadScoresFromFileStream(br);
			m_mapSTctd0.loadScoresFromFileStream(br);
			m_mapN0wd0.loadScoresFromFileStream(br);
			m_mapN0ptd0.loadScoresFromFileStream(br);
			m_mapSTwN0wd0.loadScoresFromFileStream(br);
			m_mapSTptN0ptd0.loadScoresFromFileStream(br);

			m_mapSTwrda.loadScoresFromFileStream(br);
			m_mapSTptrda.loadScoresFromFileStream(br);
			m_mapSTctrda.loadScoresFromFileStream(br);
			m_mapSTwlda.loadScoresFromFileStream(br);
			m_mapSTptlda.loadScoresFromFileStream(br);
			m_mapSTctlda.loadScoresFromFileStream(br);
			m_mapN0wlda.loadScoresFromFileStream(br);
			m_mapN0ptlda.loadScoresFromFileStream(br);

			m_mapSTwrha.loadScoresFromFileStream(br);
			m_mapSTptrha.loadScoresFromFileStream(br);
			m_mapSTctrha.loadScoresFromFileStream(br);
			m_mapSTwlha.loadScoresFromFileStream(br);
			m_mapSTptlha.loadScoresFromFileStream(br);
			m_mapSTctlha.loadScoresFromFileStream(br);
			m_mapN0wlha.loadScoresFromFileStream(br);
			m_mapN0ptlha.loadScoresFromFileStream(br);

			m_mapSTwrp.loadScoresFromFileStream(br);
			m_mapSTptrp.loadScoresFromFileStream(br);
			m_mapSTctrp.loadScoresFromFileStream(br);

			m_mapSTwlp.loadScoresFromFileStream(br);
			m_mapSTwlc.loadScoresFromFileStream(br);
			m_mapSTptlp.loadScoresFromFileStream(br);
			m_mapSTptlc.loadScoresFromFileStream(br);
			m_mapSTctlp.loadScoresFromFileStream(br);
			m_mapSTctlc.loadScoresFromFileStream(br);

			m_mapN0wlp.loadScoresFromFileStream(br);
			m_mapN0wlc.loadScoresFromFileStream(br);
			m_mapN0ptlp.loadScoresFromFileStream(br);
			m_mapN0ptlc.loadScoresFromFileStream(br);

			m_mapSTP.loadScoresFromFileStream(br);

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
			m_mapSTpt.saveScoresToFileStream(bw);
			m_mapSTwpt.saveScoresToFileStream(bw);
			m_mapSTct.saveScoresToFileStream(bw);
			m_mapSTwct.saveScoresToFileStream(bw);

			m_mapN0w.saveScoresToFileStream(bw);
			m_mapN0pt.saveScoresToFileStream(bw);
			m_mapN0wpt.saveScoresToFileStream(bw);

			m_mapN1w.saveScoresToFileStream(bw);
			m_mapN1pt.saveScoresToFileStream(bw);
			m_mapN1wpt.saveScoresToFileStream(bw);

			m_mapN2w.saveScoresToFileStream(bw);
			m_mapN2pt.saveScoresToFileStream(bw);
			m_mapN2wpt.saveScoresToFileStream(bw);

			m_mapSTLHw.saveScoresToFileStream(bw);
			m_mapSTLHpt.saveScoresToFileStream(bw);
			m_mapSTLHct.saveScoresToFileStream(bw);
			m_mapSTlhl.saveScoresToFileStream(bw);

			m_mapN0LHw.saveScoresToFileStream(bw);
			m_mapN0LHpt.saveScoresToFileStream(bw);
			m_mapN0LHct.saveScoresToFileStream(bw);
			m_mapN0lhl.saveScoresToFileStream(bw);

			m_mapSTRHw.saveScoresToFileStream(bw);
			m_mapSTRHpt.saveScoresToFileStream(bw);
			m_mapSTRHct.saveScoresToFileStream(bw);
			m_mapSTrhl.saveScoresToFileStream(bw);

			m_mapSTL2Hw.saveScoresToFileStream(bw);
			m_mapSTL2Hpt.saveScoresToFileStream(bw);
			m_mapSTL2Hct.saveScoresToFileStream(bw);
			m_mapSTl2hl.saveScoresToFileStream(bw);

			m_mapN0L2Hw.saveScoresToFileStream(bw);
			m_mapN0L2Hpt.saveScoresToFileStream(bw);
			m_mapN0L2Hct.saveScoresToFileStream(bw);
			m_mapN0l2hl.saveScoresToFileStream(bw);

			m_mapSTR2Hw.saveScoresToFileStream(bw);
			m_mapSTR2Hpt.saveScoresToFileStream(bw);
			m_mapSTR2Hct.saveScoresToFileStream(bw);
			m_mapSTr2hl.saveScoresToFileStream(bw);

			m_mapSTLHLHw.saveScoresToFileStream(bw);
			m_mapSTLHLHpt.saveScoresToFileStream(bw);
			m_mapSTLHLHct.saveScoresToFileStream(bw);
			m_mapSTLHlhl.saveScoresToFileStream(bw);

			m_mapSTLHRHw.saveScoresToFileStream(bw);
			m_mapSTLHRHpt.saveScoresToFileStream(bw);
			m_mapSTLHRHct.saveScoresToFileStream(bw);
			m_mapSTLHrhl.saveScoresToFileStream(bw);

			m_mapSTRHLHw.saveScoresToFileStream(bw);
			m_mapSTRHLHpt.saveScoresToFileStream(bw);
			m_mapSTRHLHct.saveScoresToFileStream(bw);
			m_mapSTRHlhl.saveScoresToFileStream(bw);

			m_mapSTRHRHw.saveScoresToFileStream(bw);
			m_mapSTRHRHpt.saveScoresToFileStream(bw);
			m_mapSTRHRHct.saveScoresToFileStream(bw);
			m_mapSTRHrhl.saveScoresToFileStream(bw);

			m_mapSTLDw.saveScoresToFileStream(bw);
			m_mapSTLDpt.saveScoresToFileStream(bw);
			m_mapSTLDct.saveScoresToFileStream(bw);
			m_mapSTldl.saveScoresToFileStream(bw);

			m_mapSTRDw.saveScoresToFileStream(bw);
			m_mapSTRDpt.saveScoresToFileStream(bw);
			m_mapSTRDct.saveScoresToFileStream(bw);
			m_mapSTrdl.saveScoresToFileStream(bw);

			m_mapN0LDw.saveScoresToFileStream(bw);
			m_mapN0LDpt.saveScoresToFileStream(bw);
			m_mapN0LDct.saveScoresToFileStream(bw);
			m_mapN0ldl.saveScoresToFileStream(bw);

			m_mapSTL2Dw.saveScoresToFileStream(bw);
			m_mapSTL2Dpt.saveScoresToFileStream(bw);
			m_mapSTL2Dct.saveScoresToFileStream(bw);
			m_mapSTl2dl.saveScoresToFileStream(bw);

			m_mapSTR2Dw.saveScoresToFileStream(bw);
			m_mapSTR2Dpt.saveScoresToFileStream(bw);
			m_mapSTR2Dct.saveScoresToFileStream(bw);
			m_mapSTr2dl.saveScoresToFileStream(bw);

			m_mapN0L2Dw.saveScoresToFileStream(bw);
			m_mapN0L2Dpt.saveScoresToFileStream(bw);
			m_mapN0L2Dct.saveScoresToFileStream(bw);
			m_mapN0l2dl.saveScoresToFileStream(bw);

			m_mapSTwptN0wpt.saveScoresToFileStream(bw);
			m_mapSTwptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0wpt.saveScoresToFileStream(bw);
			m_mapSTptN0wpt.saveScoresToFileStream(bw);
			m_mapSTwptN0pt.saveScoresToFileStream(bw);
			m_mapSTwctN0w.saveScoresToFileStream(bw);
			m_mapSTwN0w.saveScoresToFileStream(bw);

			m_mapSTptN0pt.saveScoresToFileStream(bw);
			m_mapN0ptN1pt.saveScoresToFileStream(bw);
			m_mapN0ptN1ptN2pt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapN0ptN0LDptN0L2Dpt.saveScoresToFileStream(bw);
			m_mapSTLHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapSTRHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapSTLHLHptSTLHptSTpt.saveScoresToFileStream(bw);
			m_mapSTLHRHptSTLHptSTpt.saveScoresToFileStream(bw);
			m_mapSTRHLHptSTRHptSTpt.saveScoresToFileStream(bw);
			m_mapSTRHRHptSTRHptSTpt.saveScoresToFileStream(bw);
			m_mapSTptSTLDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLDptSTL2Dpt.saveScoresToFileStream(bw);
			m_mapSTptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRDptSTR2Dpt.saveScoresToFileStream(bw);

			m_mapSTLHLHctSTLHctSTct.saveScoresToFileStream(bw);
			m_mapSTLHRHctSTLHctSTct.saveScoresToFileStream(bw);
			m_mapSTRHLHctSTRHctSTct.saveScoresToFileStream(bw);
			m_mapSTRHRHctSTRHctSTct.saveScoresToFileStream(bw);
			m_mapSTctSTLDctSTL2Dct.saveScoresToFileStream(bw);
			m_mapSTctSTRDctSTR2Dct.saveScoresToFileStream(bw);

			m_mapSTwd0.saveScoresToFileStream(bw);
			m_mapSTptd0.saveScoresToFileStream(bw);
			m_mapSTctd0.saveScoresToFileStream(bw);
			m_mapN0wd0.saveScoresToFileStream(bw);
			m_mapN0ptd0.saveScoresToFileStream(bw);
			m_mapSTwN0wd0.saveScoresToFileStream(bw);
			m_mapSTptN0ptd0.saveScoresToFileStream(bw);

			m_mapSTwrda.saveScoresToFileStream(bw);
			m_mapSTptrda.saveScoresToFileStream(bw);
			m_mapSTctrda.saveScoresToFileStream(bw);
			m_mapSTwlda.saveScoresToFileStream(bw);
			m_mapSTptlda.saveScoresToFileStream(bw);
			m_mapSTctlda.saveScoresToFileStream(bw);
			m_mapN0wlda.saveScoresToFileStream(bw);
			m_mapN0ptlda.saveScoresToFileStream(bw);

			m_mapSTwrha.saveScoresToFileStream(bw);
			m_mapSTptrha.saveScoresToFileStream(bw);
			m_mapSTctrha.saveScoresToFileStream(bw);
			m_mapSTwlha.saveScoresToFileStream(bw);
			m_mapSTptlha.saveScoresToFileStream(bw);
			m_mapSTctlha.saveScoresToFileStream(bw);
			m_mapN0wlha.saveScoresToFileStream(bw);
			m_mapN0ptlha.saveScoresToFileStream(bw);

			m_mapSTwrp.saveScoresToFileStream(bw);
			m_mapSTptrp.saveScoresToFileStream(bw);
			m_mapSTctrp.saveScoresToFileStream(bw);

			m_mapSTwlp.saveScoresToFileStream(bw);
			m_mapSTwlc.saveScoresToFileStream(bw);
			m_mapSTptlp.saveScoresToFileStream(bw);
			m_mapSTptlc.saveScoresToFileStream(bw);
			m_mapSTctlp.saveScoresToFileStream(bw);
			m_mapSTctlc.saveScoresToFileStream(bw);

			m_mapN0wlp.saveScoresToFileStream(bw);
			m_mapN0wlc.saveScoresToFileStream(bw);
			m_mapN0ptlp.saveScoresToFileStream(bw);
			m_mapN0ptlc.saveScoresToFileStream(bw);

			m_mapSTP.saveScoresToFileStream(bw);
			
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done.");
	}

	@Override
	public void computeAverageFeatureWeights(final int round) {
		System.out.println("Computing averaged (total) feature vector...");
		System.out.flush();

		m_mapSTw.computeAverage(round);
		m_mapSTpt.computeAverage(round);
		m_mapSTwpt.computeAverage(round);
		m_mapSTct.computeAverage(round);
		m_mapSTwct.computeAverage(round);

		m_mapN0w.computeAverage(round);
		m_mapN0pt.computeAverage(round);
		m_mapN0wpt.computeAverage(round);

		m_mapN1w.computeAverage(round);
		m_mapN1pt.computeAverage(round);
		m_mapN1wpt.computeAverage(round);

		m_mapN2w.computeAverage(round);
		m_mapN2pt.computeAverage(round);
		m_mapN2wpt.computeAverage(round);

		m_mapSTLHw.computeAverage(round);
		m_mapSTLHpt.computeAverage(round);
		m_mapSTLHct.computeAverage(round);
		m_mapSTlhl.computeAverage(round);

		m_mapN0LHw.computeAverage(round);
		m_mapN0LHpt.computeAverage(round);
		m_mapN0LHct.computeAverage(round);
		m_mapN0lhl.computeAverage(round);

		m_mapSTRHw.computeAverage(round);
		m_mapSTRHpt.computeAverage(round);
		m_mapSTRHct.computeAverage(round);
		m_mapSTrhl.computeAverage(round);

		m_mapSTL2Hw.computeAverage(round);
		m_mapSTL2Hpt.computeAverage(round);
		m_mapSTL2Hct.computeAverage(round);
		m_mapSTl2hl.computeAverage(round);

		m_mapN0L2Hw.computeAverage(round);
		m_mapN0L2Hpt.computeAverage(round);
		m_mapN0L2Hct.computeAverage(round);
		m_mapN0l2hl.computeAverage(round);

		m_mapSTR2Hw.computeAverage(round);
		m_mapSTR2Hpt.computeAverage(round);
		m_mapSTR2Hct.computeAverage(round);
		m_mapSTr2hl.computeAverage(round);

		m_mapSTLHLHw.computeAverage(round);
		m_mapSTLHLHpt.computeAverage(round);
		m_mapSTLHLHct.computeAverage(round);
		m_mapSTLHlhl.computeAverage(round);

		m_mapSTLHRHw.computeAverage(round);
		m_mapSTLHRHpt.computeAverage(round);
		m_mapSTLHRHct.computeAverage(round);
		m_mapSTLHrhl.computeAverage(round);

		m_mapSTRHLHw.computeAverage(round);
		m_mapSTRHLHpt.computeAverage(round);
		m_mapSTRHLHct.computeAverage(round);
		m_mapSTRHlhl.computeAverage(round);

		m_mapSTRHRHw.computeAverage(round);
		m_mapSTRHRHpt.computeAverage(round);
		m_mapSTRHRHct.computeAverage(round);
		m_mapSTRHrhl.computeAverage(round);

		m_mapSTLDw.computeAverage(round);
		m_mapSTLDpt.computeAverage(round);
		m_mapSTLDct.computeAverage(round);
		m_mapSTldl.computeAverage(round);

		m_mapSTRDw.computeAverage(round);
		m_mapSTRDpt.computeAverage(round);
		m_mapSTRDct.computeAverage(round);
		m_mapSTrdl.computeAverage(round);

		m_mapN0LDw.computeAverage(round);
		m_mapN0LDpt.computeAverage(round);
		m_mapN0LDct.computeAverage(round);
		m_mapN0ldl.computeAverage(round);

		m_mapSTL2Dw.computeAverage(round);
		m_mapSTL2Dpt.computeAverage(round);
		m_mapSTL2Dct.computeAverage(round);
		m_mapSTl2dl.computeAverage(round);

		m_mapSTR2Dw.computeAverage(round);
		m_mapSTR2Dpt.computeAverage(round);
		m_mapSTR2Dct.computeAverage(round);
		m_mapSTr2dl.computeAverage(round);

		m_mapN0L2Dw.computeAverage(round);
		m_mapN0L2Dpt.computeAverage(round);
		m_mapN0L2Dct.computeAverage(round);
		m_mapN0l2dl.computeAverage(round);

		m_mapSTwptN0wpt.computeAverage(round);
		m_mapSTwptN0w.computeAverage(round);
		m_mapSTwN0wpt.computeAverage(round);
		m_mapSTptN0wpt.computeAverage(round);
		m_mapSTwptN0pt.computeAverage(round);
		m_mapSTwctN0w.computeAverage(round);
		m_mapSTwN0w.computeAverage(round);

		m_mapSTptN0pt.computeAverage(round);
		m_mapN0ptN1pt.computeAverage(round);
		m_mapN0ptN1ptN2pt.computeAverage(round);
		m_mapSTptN0ptN1pt.computeAverage(round);
		m_mapSTptN0ptN0LDpt.computeAverage(round);
		m_mapN0ptN0LDptN0L2Dpt.computeAverage(round);
		m_mapSTLHptSTptN0pt.computeAverage(round);
		m_mapSTRHptSTptN0pt.computeAverage(round);
		m_mapSTLHLHptSTLHptSTpt.computeAverage(round);
		m_mapSTLHRHptSTLHptSTpt.computeAverage(round);
		m_mapSTRHLHptSTRHptSTpt.computeAverage(round);
		m_mapSTRHRHptSTRHptSTpt.computeAverage(round);
		m_mapSTptSTLDptN0pt.computeAverage(round);
		m_mapSTptSTLDptSTL2Dpt.computeAverage(round);
		m_mapSTptSTRDptN0pt.computeAverage(round);
		m_mapSTptSTRDptSTR2Dpt.computeAverage(round);

		m_mapSTLHLHctSTLHctSTct.computeAverage(round);
		m_mapSTLHRHctSTLHctSTct.computeAverage(round);
		m_mapSTRHLHctSTRHctSTct.computeAverage(round);
		m_mapSTRHRHctSTRHctSTct.computeAverage(round);
		m_mapSTctSTLDctSTL2Dct.computeAverage(round);
		m_mapSTctSTRDctSTR2Dct.computeAverage(round);

		m_mapSTwd0.computeAverage(round);
		m_mapSTptd0.computeAverage(round);
		m_mapSTctd0.computeAverage(round);
		m_mapN0wd0.computeAverage(round);
		m_mapN0ptd0.computeAverage(round);
		m_mapSTwN0wd0.computeAverage(round);
		m_mapSTptN0ptd0.computeAverage(round);

		m_mapSTwrda.computeAverage(round);
		m_mapSTptrda.computeAverage(round);
		m_mapSTctrda.computeAverage(round);
		m_mapSTwlda.computeAverage(round);
		m_mapSTptlda.computeAverage(round);
		m_mapSTctlda.computeAverage(round);
		m_mapN0wlda.computeAverage(round);
		m_mapN0ptlda.computeAverage(round);

		m_mapSTwrha.computeAverage(round);
		m_mapSTptrha.computeAverage(round);
		m_mapSTctrha.computeAverage(round);
		m_mapSTwlha.computeAverage(round);
		m_mapSTptlha.computeAverage(round);
		m_mapSTctlha.computeAverage(round);
		m_mapN0wlha.computeAverage(round);
		m_mapN0ptlha.computeAverage(round);

		m_mapSTwrp.computeAverage(round);
		m_mapSTptrp.computeAverage(round);
		m_mapSTctrp.computeAverage(round);

		m_mapSTwlp.computeAverage(round);
		m_mapSTwlc.computeAverage(round);
		m_mapSTptlp.computeAverage(round);
		m_mapSTptlc.computeAverage(round);
		m_mapSTctlp.computeAverage(round);
		m_mapSTctlc.computeAverage(round);

		m_mapN0wlp.computeAverage(round);
		m_mapN0wlc.computeAverage(round);
		m_mapN0ptlp.computeAverage(round);
		m_mapN0ptlc.computeAverage(round);

		m_mapSTP.computeAverage(round);
		
		System.out.println("done.");
	}
}
