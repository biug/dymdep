package common.parser.implementations.spanning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import common.parser.WeightBase;
import common.parser.implementations.map.IntMap;
import common.parser.implementations.map.POSTagIntMap;
import common.parser.implementations.map.POSTagMap;
import common.parser.implementations.map.POSTagPOSTagIntMap;
import common.parser.implementations.map.POSTagSet2Map;
import common.parser.implementations.map.POSTagSet3Map;
import common.parser.implementations.map.POSTagSetOfDepLabelsMap;
import common.parser.implementations.map.StringMap;
import common.parser.implementations.map.TwoIntsMap;
import common.parser.implementations.map.TwoPOSTaggedWordsMap;
import common.parser.implementations.map.TwoWordsMap;
import common.parser.implementations.map.WordIntMap;
import common.parser.implementations.map.WordMap;
import common.parser.implementations.map.WordPOSTagMap;
import common.parser.implementations.map.WordPOSTagPOSTagMap;
import common.parser.implementations.map.WordSetOfDepLabelsMap;
import common.parser.implementations.map.WordWordIntMap;
import common.parser.implementations.map.WordWordPOSTagMap;

public class Weight extends WeightBase {
	public WordMap m_mapSTw;
	public POSTagMap m_mapSTpt;
	public WordPOSTagMap m_mapSTwpt;
	public IntMap m_mapSTct;
	
	public WordMap m_mapST2w;
	public POSTagMap m_mapST2pt;
	public WordPOSTagMap m_mapST2wpt;
	public IntMap m_mapST2ct;

	public WordMap m_mapN0w;
	public POSTagMap m_mapN0pt;
	public WordPOSTagMap m_mapN0wpt;

	public WordMap m_mapN1w;
	public POSTagMap m_mapN1pt;
	public WordPOSTagMap m_mapN1wpt;

	public WordMap m_mapN2w;
	public POSTagMap m_mapN2pt;
	public WordPOSTagMap m_mapN2wpt;

	public WordMap m_mapN_1w;
	public POSTagMap m_mapN_1pt;
	public WordPOSTagMap m_mapN_1wpt;
	public IntMap m_mapN_1ct;

	public WordMap m_mapN_2w;
	public POSTagMap m_mapN_2pt;
	public WordPOSTagMap m_mapN_2wpt;
	public IntMap m_mapN_2ct;

	public WordMap m_mapSTLHw;
	public POSTagMap m_mapSTLHpt;
	public IntMap m_mapSTlhl;
	
	public WordMap m_mapST2LHw;
	public POSTagMap m_mapST2LHpt;
	public IntMap m_mapST2lhl;

	public WordMap m_mapN0LHw;
	public POSTagMap m_mapN0LHpt;
	public IntMap m_mapN0lhl;

	public WordMap m_mapSTRHw;
	public POSTagMap m_mapSTRHpt;
	public IntMap m_mapSTrhl;

	public WordMap m_mapST2RHw;
	public POSTagMap m_mapST2RHpt;
	public IntMap m_mapST2rhl;

	public WordMap m_mapSTLDw;
	public POSTagMap m_mapSTLDpt;
	public IntMap m_mapSTldl;

	public WordMap m_mapST2LDw;
	public POSTagMap m_mapST2LDpt;
	public IntMap m_mapST2ldl;
	
	public WordMap m_mapSTRDw;
	public POSTagMap m_mapSTRDpt;
	public IntMap m_mapSTrdl;
	
	public WordMap m_mapST2RDw;
	public POSTagMap m_mapST2RDpt;
	public IntMap m_mapST2rdl;

	public WordMap m_mapN0LDw;
	public POSTagMap m_mapN0LDpt;
	public IntMap m_mapN0ldl;

	public TwoPOSTaggedWordsMap m_mapSTwptN0wpt;
	public WordWordPOSTagMap m_mapSTwptN0w;
	public WordWordPOSTagMap m_mapSTwN0wpt;
	public WordPOSTagPOSTagMap m_mapSTptN0wpt;
	public WordPOSTagPOSTagMap m_mapSTwptN0pt;
	public TwoWordsMap m_mapSTwN0w;

	public TwoPOSTaggedWordsMap m_mapST2wptN0wpt;
	public WordWordPOSTagMap m_mapST2wptN0w;
	public WordWordPOSTagMap m_mapST2wN0wpt;
	public WordPOSTagPOSTagMap m_mapST2ptN0wpt;
	public WordPOSTagPOSTagMap m_mapST2wptN0pt;
	public TwoWordsMap m_mapST2wN0w;

	public POSTagSet2Map m_mapSTptN0pt;
	public POSTagSet2Map m_mapN0ptN1pt;
	public POSTagSet2Map m_mapN0ptN_1pt;
	public POSTagSet3Map m_mapN_2ptN_1ptN0pt;
	public POSTagSet3Map m_mapN_1ptN0ptN1pt;
	public POSTagSet3Map m_mapN0ptN1ptN2pt;
	public POSTagSet3Map m_mapSTptN_1ptN0pt;
	public POSTagSet3Map m_mapSTptN0ptN1pt; 
	public POSTagSet3Map m_mapSTptN0ptN0LDpt;
	public POSTagSet3Map m_mapSTptN0ptN0LHpt;
	public POSTagSet3Map m_mapSTLHptSTptN0pt;
	public POSTagSet3Map m_mapSTRHptSTptN0pt;
	public POSTagSet3Map m_mapSTptSTLDptN0pt;
	public POSTagSet3Map m_mapSTptSTRDptN0pt;
	
	public POSTagSet2Map m_mapST2ptN0pt;
	public POSTagSet3Map m_mapST2ptN_1ptN0pt;
	public POSTagSet3Map m_mapST2ptN0ptN1pt; 
	public POSTagSet3Map m_mapST2ptN0ptN0LDpt;
	public POSTagSet3Map m_mapST2ptN0ptN0LHpt;
	public POSTagSet3Map m_mapST2LHptST2ptN0pt;
	public POSTagSet3Map m_mapST2RHptST2ptN0pt;
	public POSTagSet3Map m_mapST2ptST2LDptN0pt;
	public POSTagSet3Map m_mapST2ptST2RDptN0pt;

	public WordWordIntMap m_mapSTwN0wd0;
	public POSTagPOSTagIntMap m_mapSTptN0ptd0;

	public WordWordIntMap m_mapST2wN0wd1;
	public POSTagPOSTagIntMap m_mapST2ptN0ptd1;
	
	public WordIntMap m_mapSTwrda;
	public POSTagIntMap m_mapSTptrda;
	public WordIntMap m_mapSTwlda;
	public POSTagIntMap m_mapSTptlda;
	public WordIntMap m_mapN0wlda;
	public POSTagIntMap m_mapN0ptlda;

	public WordIntMap m_mapST2wrda;
	public POSTagIntMap m_mapST2ptrda;
	public WordIntMap m_mapST2wlda;
	public POSTagIntMap m_mapST2ptlda;
	
	public WordIntMap m_mapSTwrha;
	public POSTagIntMap m_mapSTptrha;
	public WordIntMap m_mapSTwlha;
	public POSTagIntMap m_mapSTptlha;
	public WordIntMap m_mapN0wlha;
	public POSTagIntMap m_mapN0ptlha;

	public WordIntMap m_mapST2wrha;
	public POSTagIntMap m_mapST2ptrha;
	public WordIntMap m_mapST2wlha;
	public POSTagIntMap m_mapST2ptlha;
	
	public WordSetOfDepLabelsMap m_mapSTwrp;
	public POSTagSetOfDepLabelsMap m_mapSTptrp;

	public WordSetOfDepLabelsMap m_mapST2wrp;
	public POSTagSetOfDepLabelsMap m_mapST2ptrp;
	
	public WordSetOfDepLabelsMap m_mapSTwlp;
	public POSTagSetOfDepLabelsMap m_mapSTptlp;
	
	public WordSetOfDepLabelsMap m_mapST2wlp;
	public POSTagSetOfDepLabelsMap m_mapST2ptlp;

	public WordSetOfDepLabelsMap m_mapN0wlp;
	public POSTagSetOfDepLabelsMap m_mapN0ptlp;

	public StringMap m_mapPOSPath;
	public StringMap m_mapFPOSPath;
	public StringMap m_mapSPOSPath;
	public StringMap m_mapSFPOSPath;
	
	public IntMap m_mapST_1ct;
	public IntMap m_mapST_2ct;
	public TwoIntsMap m_mapSTctST2ct;
	public TwoIntsMap m_mapN_1ctN_2ct;
	public WordIntMap m_mapSTctN0w;
	public WordIntMap m_mapST2ctN0w;

	public Weight(final String sPath, final boolean bTrain) {
		super(sPath, bTrain);

		m_mapSTw = new WordMap("a");
		m_mapSTpt = new POSTagMap("a");
		m_mapSTwpt = new WordPOSTagMap("a");
		m_mapSTct = new IntMap("a");
		
		m_mapST2w = new WordMap("a");
		m_mapST2pt = new POSTagMap("a");
		m_mapST2wpt = new WordPOSTagMap("a");
		m_mapST2ct = new IntMap("a");

		m_mapN0w = new WordMap("a");
		m_mapN0pt = new POSTagMap("a");
		m_mapN0wpt = new WordPOSTagMap("a");

		m_mapN1w = new WordMap("a");
		m_mapN1pt = new POSTagMap("a");
		m_mapN1wpt = new WordPOSTagMap("a");

		m_mapN2w = new WordMap("a");
		m_mapN2pt = new POSTagMap("a");
		m_mapN2wpt = new WordPOSTagMap("a");

		m_mapN_1w = new WordMap("a");
		m_mapN_1pt = new POSTagMap("a");
		m_mapN_1wpt = new WordPOSTagMap("a");
		m_mapN_1ct = new IntMap("a");

		m_mapN_2w = new WordMap("a");
		m_mapN_2pt = new POSTagMap("a");
		m_mapN_2wpt = new WordPOSTagMap("a");
		m_mapN_2ct = new IntMap("a");

		m_mapSTLHw = new WordMap("a");
		m_mapSTLHpt = new POSTagMap("a");
		m_mapSTlhl = new IntMap("a");
		
		m_mapST2LHw = new WordMap("a");
		m_mapST2LHpt = new POSTagMap("a");
		m_mapST2lhl = new IntMap("a");

		m_mapN0LHw = new WordMap("a");
		m_mapN0LHpt = new POSTagMap("a");
		m_mapN0lhl = new IntMap("a");

		m_mapSTRHw = new WordMap("a");
		m_mapSTRHpt = new POSTagMap("a");
		m_mapSTrhl = new IntMap("a");

		m_mapST2RHw = new WordMap("a");
		m_mapST2RHpt = new POSTagMap("a");
		m_mapST2rhl = new IntMap("a");

		m_mapSTLDw = new WordMap("a");
		m_mapSTLDpt = new POSTagMap("a");
		m_mapSTldl = new IntMap("a");

		m_mapST2LDw = new WordMap("a");
		m_mapST2LDpt = new POSTagMap("a");
		m_mapST2ldl = new IntMap("a");
		
		m_mapSTRDw = new WordMap("a");
		m_mapSTRDpt = new POSTagMap("a");
		m_mapSTrdl = new IntMap("a");
		
		m_mapST2RDw = new WordMap("a");
		m_mapST2RDpt = new POSTagMap("a");
		m_mapST2rdl = new IntMap("a");

		m_mapN0LDw = new WordMap("a");
		m_mapN0LDpt = new POSTagMap("a");
		m_mapN0ldl = new IntMap("a");
		
		m_mapSTwptN0wpt = new TwoPOSTaggedWordsMap("a");
		m_mapSTwptN0w = new WordWordPOSTagMap("a");
		m_mapSTwN0wpt = new WordWordPOSTagMap("a");
		m_mapSTptN0wpt = new WordPOSTagPOSTagMap("a");
		m_mapSTwptN0pt = new WordPOSTagPOSTagMap("a");
		m_mapSTwN0w = new TwoWordsMap("a");

		m_mapST2wptN0wpt = new TwoPOSTaggedWordsMap("a");
		m_mapST2wptN0w = new WordWordPOSTagMap("a");
		m_mapST2wN0wpt = new WordWordPOSTagMap("a");
		m_mapST2ptN0wpt = new WordPOSTagPOSTagMap("a");
		m_mapST2wptN0pt = new WordPOSTagPOSTagMap("a");
		m_mapST2wN0w = new TwoWordsMap("a");

		m_mapSTptN0pt = new POSTagSet2Map("a");
		m_mapN0ptN1pt = new POSTagSet2Map("a");
		m_mapN0ptN_1pt = new POSTagSet2Map("a");
		m_mapN_2ptN_1ptN0pt = new POSTagSet3Map("a");
		m_mapN_1ptN0ptN1pt = new POSTagSet3Map("a");
		m_mapN0ptN1ptN2pt = new POSTagSet3Map("a");
		m_mapSTptN_1ptN0pt = new POSTagSet3Map("a");
		m_mapSTptN0ptN1pt = new POSTagSet3Map("a");
		m_mapSTptN0ptN0LDpt = new POSTagSet3Map("a");
		m_mapSTptN0ptN0LHpt = new POSTagSet3Map("a");
		m_mapSTLHptSTptN0pt = new POSTagSet3Map("a");
		m_mapSTRHptSTptN0pt = new POSTagSet3Map("a");
		m_mapSTptSTLDptN0pt = new POSTagSet3Map("a");
		m_mapSTptSTRDptN0pt = new POSTagSet3Map("a");

		m_mapST2ptN0pt = new POSTagSet2Map("a");
		m_mapST2ptN_1ptN0pt = new POSTagSet3Map("a");
		m_mapST2ptN0ptN1pt = new POSTagSet3Map("a");
		m_mapST2ptN0ptN0LDpt = new POSTagSet3Map("a");
		m_mapST2ptN0ptN0LHpt = new POSTagSet3Map("a");
		m_mapST2LHptST2ptN0pt = new POSTagSet3Map("a");
		m_mapST2RHptST2ptN0pt = new POSTagSet3Map("a");
		m_mapST2ptST2LDptN0pt = new POSTagSet3Map("a");
		m_mapST2ptST2RDptN0pt = new POSTagSet3Map("a");

		m_mapSTwN0wd0 = new WordWordIntMap("a");
		m_mapSTptN0ptd0 = new POSTagPOSTagIntMap("a");

		m_mapST2wN0wd1 = new WordWordIntMap("a");
		m_mapST2ptN0ptd1 = new POSTagPOSTagIntMap("a");
		
		m_mapSTwrda = new WordIntMap("a");
		m_mapSTptrda = new POSTagIntMap("a");
		m_mapSTwlda = new WordIntMap("a");
		m_mapSTptlda = new POSTagIntMap("a");
		m_mapN0wlda = new WordIntMap("a");
		m_mapN0ptlda = new POSTagIntMap("a");

		m_mapST2wrda = new WordIntMap("a");
		m_mapST2ptrda = new POSTagIntMap("a");
		m_mapST2wlda = new WordIntMap("a");
		m_mapST2ptlda = new POSTagIntMap("a");
		
		m_mapSTwrha = new WordIntMap("a");
		m_mapSTptrha = new POSTagIntMap("a");
		m_mapSTwlha = new WordIntMap("a");
		m_mapSTptlha = new POSTagIntMap("a");
		m_mapN0wlha = new WordIntMap("a");
		m_mapN0ptlha = new POSTagIntMap("a");

		m_mapST2wrha = new WordIntMap("a");
		m_mapST2ptrha = new POSTagIntMap("a");
		m_mapST2wlha = new WordIntMap("a");
		m_mapST2ptlha = new POSTagIntMap("a");
		
		m_mapSTwrp = new WordSetOfDepLabelsMap("a");
		m_mapSTptrp = new POSTagSetOfDepLabelsMap("a");

		m_mapST2wrp = new WordSetOfDepLabelsMap("a");
		m_mapST2ptrp = new POSTagSetOfDepLabelsMap("a");
		
		m_mapSTwlp = new WordSetOfDepLabelsMap("a");
		m_mapSTptlp = new POSTagSetOfDepLabelsMap("a");
		
		m_mapST2wlp = new WordSetOfDepLabelsMap("a");
		m_mapST2ptlp = new POSTagSetOfDepLabelsMap("a");

		m_mapN0wlp = new WordSetOfDepLabelsMap("a");
		m_mapN0ptlp = new POSTagSetOfDepLabelsMap("a");

		m_mapPOSPath = new StringMap("a");
		m_mapFPOSPath = new StringMap("a");
		m_mapSPOSPath = new StringMap("a");
		m_mapSFPOSPath = new StringMap("a");
		
		m_mapST_1ct = new IntMap("a");
		m_mapST_2ct = new IntMap("a");
		m_mapSTctST2ct = new TwoIntsMap("a");
		m_mapN_1ctN_2ct = new TwoIntsMap("a");
		m_mapSTctN0w = new WordIntMap("a");
		m_mapST2ctN0w = new WordIntMap("a");
		
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
			
			m_mapST2w.loadScoresFromFileStream(br);
			m_mapST2pt.loadScoresFromFileStream(br);
			m_mapST2wpt.loadScoresFromFileStream(br);
			m_mapST2ct.loadScoresFromFileStream(br);

			m_mapN0w.loadScoresFromFileStream(br);
			m_mapN0pt.loadScoresFromFileStream(br);
			m_mapN0wpt.loadScoresFromFileStream(br);

			m_mapN1w.loadScoresFromFileStream(br);
			m_mapN1pt.loadScoresFromFileStream(br);
			m_mapN1wpt.loadScoresFromFileStream(br);

			m_mapN2w.loadScoresFromFileStream(br);
			m_mapN2pt.loadScoresFromFileStream(br);
			m_mapN2wpt.loadScoresFromFileStream(br);

			m_mapN_1w.loadScoresFromFileStream(br);
			m_mapN_1pt.loadScoresFromFileStream(br);
			m_mapN_1wpt.loadScoresFromFileStream(br);
			m_mapN_1ct.loadScoresFromFileStream(br);

			m_mapN_2w.loadScoresFromFileStream(br);
			m_mapN_2pt.loadScoresFromFileStream(br);
			m_mapN_2wpt.loadScoresFromFileStream(br);
			m_mapN_2ct.loadScoresFromFileStream(br);

			m_mapSTLHw.loadScoresFromFileStream(br);
			m_mapSTLHpt.loadScoresFromFileStream(br);
			m_mapSTlhl.loadScoresFromFileStream(br);
			
			m_mapST2LHw.loadScoresFromFileStream(br);
			m_mapST2LHpt.loadScoresFromFileStream(br);
			m_mapST2lhl.loadScoresFromFileStream(br);

			m_mapN0LHw.loadScoresFromFileStream(br);
			m_mapN0LHpt.loadScoresFromFileStream(br);
			m_mapN0lhl.loadScoresFromFileStream(br);

			m_mapSTRHw.loadScoresFromFileStream(br);
			m_mapSTRHpt.loadScoresFromFileStream(br);
			m_mapSTrhl.loadScoresFromFileStream(br);

			m_mapST2RHw.loadScoresFromFileStream(br);
			m_mapST2RHpt.loadScoresFromFileStream(br);
			m_mapST2rhl.loadScoresFromFileStream(br);

			m_mapSTLDw.loadScoresFromFileStream(br);
			m_mapSTLDpt.loadScoresFromFileStream(br);
			m_mapSTldl.loadScoresFromFileStream(br);

			m_mapST2LDw.loadScoresFromFileStream(br);
			m_mapST2LDpt.loadScoresFromFileStream(br);
			m_mapST2ldl.loadScoresFromFileStream(br);
			
			m_mapSTRDw.loadScoresFromFileStream(br);
			m_mapSTRDpt.loadScoresFromFileStream(br);
			m_mapSTrdl.loadScoresFromFileStream(br);
			
			m_mapST2RDw.loadScoresFromFileStream(br);
			m_mapST2RDpt.loadScoresFromFileStream(br);
			m_mapST2rdl.loadScoresFromFileStream(br);

			m_mapN0LDw.loadScoresFromFileStream(br);
			m_mapN0LDpt.loadScoresFromFileStream(br);
			m_mapN0ldl.loadScoresFromFileStream(br);
			
			m_mapSTwptN0wpt.loadScoresFromFileStream(br);
			m_mapSTwptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0wpt.loadScoresFromFileStream(br);
			m_mapSTptN0wpt.loadScoresFromFileStream(br);
			m_mapSTwptN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0w.loadScoresFromFileStream(br);

			m_mapST2wptN0wpt.loadScoresFromFileStream(br);
			m_mapST2wptN0w.loadScoresFromFileStream(br);
			m_mapST2wN0wpt.loadScoresFromFileStream(br);
			m_mapST2ptN0wpt.loadScoresFromFileStream(br);
			m_mapST2wptN0pt.loadScoresFromFileStream(br);
			m_mapST2wN0w.loadScoresFromFileStream(br);

			m_mapSTptN0pt.loadScoresFromFileStream(br);
			m_mapN0ptN1pt.loadScoresFromFileStream(br);
			m_mapN0ptN_1pt.loadScoresFromFileStream(br);
			m_mapN_2ptN_1ptN0pt.loadScoresFromFileStream(br);
			m_mapN_1ptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapN0ptN1ptN2pt.loadScoresFromFileStream(br);
			m_mapSTptN_1ptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapSTLHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapSTRHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRDptN0pt.loadScoresFromFileStream(br);
			
			m_mapST2ptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN_1ptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapST2LHptST2ptN0pt.loadScoresFromFileStream(br);
			m_mapST2RHptST2ptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2LDptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2RDptN0pt.loadScoresFromFileStream(br);

			m_mapSTwN0wd0.loadScoresFromFileStream(br);
			m_mapSTptN0ptd0.loadScoresFromFileStream(br);

			m_mapST2wN0wd1.loadScoresFromFileStream(br);
			m_mapST2ptN0ptd1.loadScoresFromFileStream(br);
			
			m_mapSTwrda.loadScoresFromFileStream(br);
			m_mapSTptrda.loadScoresFromFileStream(br);
			m_mapSTwlda.loadScoresFromFileStream(br);
			m_mapSTptlda.loadScoresFromFileStream(br);
			m_mapN0wlda.loadScoresFromFileStream(br);
			m_mapN0ptlda.loadScoresFromFileStream(br);

			m_mapST2wrda.loadScoresFromFileStream(br);
			m_mapST2ptrda.loadScoresFromFileStream(br);
			m_mapST2wlda.loadScoresFromFileStream(br);
			m_mapST2ptlda.loadScoresFromFileStream(br);
			
			m_mapSTwrha.loadScoresFromFileStream(br);
			m_mapSTptrha.loadScoresFromFileStream(br);
			m_mapSTwlha.loadScoresFromFileStream(br);
			m_mapSTptlha.loadScoresFromFileStream(br);
			m_mapN0wlha.loadScoresFromFileStream(br);
			m_mapN0ptlha.loadScoresFromFileStream(br);

			m_mapST2wrha.loadScoresFromFileStream(br);
			m_mapST2ptrha.loadScoresFromFileStream(br);
			m_mapST2wlha.loadScoresFromFileStream(br);
			m_mapST2ptlha.loadScoresFromFileStream(br);
			
			m_mapSTwrp.loadScoresFromFileStream(br);
			m_mapSTptrp.loadScoresFromFileStream(br);

			m_mapST2wrp.loadScoresFromFileStream(br);
			m_mapST2ptrp.loadScoresFromFileStream(br);
			
			m_mapSTwlp.loadScoresFromFileStream(br);
			m_mapSTptlp.loadScoresFromFileStream(br);
			
			m_mapST2wlp.loadScoresFromFileStream(br);
			m_mapST2ptlp.loadScoresFromFileStream(br);

			m_mapN0wlp.loadScoresFromFileStream(br);
			m_mapN0ptlp.loadScoresFromFileStream(br);

			m_mapPOSPath.loadScoresFromFileStream(br);
			m_mapFPOSPath.loadScoresFromFileStream(br);
			m_mapSPOSPath.loadScoresFromFileStream(br);
			m_mapSFPOSPath.loadScoresFromFileStream(br);
			
			m_mapST_1ct.loadScoresFromFileStream(br);
			m_mapST_2ct.loadScoresFromFileStream(br);
			m_mapSTctST2ct.loadScoresFromFileStream(br);
			m_mapN_1ctN_2ct.loadScoresFromFileStream(br);
			m_mapSTctN0w.loadScoresFromFileStream(br);
			m_mapST2ctN0w.loadScoresFromFileStream(br);

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
			
			m_mapST2w.saveScoresToFileStream(bw);
			m_mapST2pt.saveScoresToFileStream(bw);
			m_mapST2wpt.saveScoresToFileStream(bw);
			m_mapST2ct.saveScoresToFileStream(bw);

			m_mapN0w.saveScoresToFileStream(bw);
			m_mapN0pt.saveScoresToFileStream(bw);
			m_mapN0wpt.saveScoresToFileStream(bw);

			m_mapN1w.saveScoresToFileStream(bw);
			m_mapN1pt.saveScoresToFileStream(bw);
			m_mapN1wpt.saveScoresToFileStream(bw);

			m_mapN2w.saveScoresToFileStream(bw);
			m_mapN2pt.saveScoresToFileStream(bw);
			m_mapN2wpt.saveScoresToFileStream(bw);

			m_mapN_1w.saveScoresToFileStream(bw);
			m_mapN_1pt.saveScoresToFileStream(bw);
			m_mapN_1wpt.saveScoresToFileStream(bw);
			m_mapN_1ct.saveScoresToFileStream(bw);

			m_mapN_2w.saveScoresToFileStream(bw);
			m_mapN_2pt.saveScoresToFileStream(bw);
			m_mapN_2wpt.saveScoresToFileStream(bw);
			m_mapN_2ct.saveScoresToFileStream(bw);

			m_mapSTLHw.saveScoresToFileStream(bw);
			m_mapSTLHpt.saveScoresToFileStream(bw);
			m_mapSTlhl.saveScoresToFileStream(bw);
			
			m_mapST2LHw.saveScoresToFileStream(bw);
			m_mapST2LHpt.saveScoresToFileStream(bw);
			m_mapST2lhl.saveScoresToFileStream(bw);

			m_mapN0LHw.saveScoresToFileStream(bw);
			m_mapN0LHpt.saveScoresToFileStream(bw);
			m_mapN0lhl.saveScoresToFileStream(bw);

			m_mapSTRHw.saveScoresToFileStream(bw);
			m_mapSTRHpt.saveScoresToFileStream(bw);
			m_mapSTrhl.saveScoresToFileStream(bw);

			m_mapST2RHw.saveScoresToFileStream(bw);
			m_mapST2RHpt.saveScoresToFileStream(bw);
			m_mapST2rhl.saveScoresToFileStream(bw);

			m_mapSTLDw.saveScoresToFileStream(bw);
			m_mapSTLDpt.saveScoresToFileStream(bw);
			m_mapSTldl.saveScoresToFileStream(bw);

			m_mapST2LDw.saveScoresToFileStream(bw);
			m_mapST2LDpt.saveScoresToFileStream(bw);
			m_mapST2ldl.saveScoresToFileStream(bw);
			
			m_mapSTRDw.saveScoresToFileStream(bw);
			m_mapSTRDpt.saveScoresToFileStream(bw);
			m_mapSTrdl.saveScoresToFileStream(bw);
			
			m_mapST2RDw.saveScoresToFileStream(bw);
			m_mapST2RDpt.saveScoresToFileStream(bw);
			m_mapST2rdl.saveScoresToFileStream(bw);

			m_mapN0LDw.saveScoresToFileStream(bw);
			m_mapN0LDpt.saveScoresToFileStream(bw);
			m_mapN0ldl.saveScoresToFileStream(bw);

			m_mapSTwptN0wpt.saveScoresToFileStream(bw);
			m_mapSTwptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0wpt.saveScoresToFileStream(bw);
			m_mapSTptN0wpt.saveScoresToFileStream(bw);
			m_mapSTwptN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0w.saveScoresToFileStream(bw);
			

			m_mapST2wptN0wpt.saveScoresToFileStream(bw);
			m_mapST2wptN0w.saveScoresToFileStream(bw);
			m_mapST2wN0wpt.saveScoresToFileStream(bw);
			m_mapST2ptN0wpt.saveScoresToFileStream(bw);
			m_mapST2wptN0pt.saveScoresToFileStream(bw);
			m_mapST2wN0w.saveScoresToFileStream(bw);

			m_mapSTptN0pt.saveScoresToFileStream(bw);
			m_mapN0ptN1pt.saveScoresToFileStream(bw);
			m_mapN0ptN_1pt.saveScoresToFileStream(bw);
			m_mapN_2ptN_1ptN0pt.saveScoresToFileStream(bw);
			m_mapN_1ptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapN0ptN1ptN2pt.saveScoresToFileStream(bw);
			m_mapSTptN_1ptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapSTLHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapSTRHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRDptN0pt.saveScoresToFileStream(bw);
			
			m_mapST2ptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN_1ptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapST2LHptST2ptN0pt.saveScoresToFileStream(bw);
			m_mapST2RHptST2ptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2LDptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2RDptN0pt.saveScoresToFileStream(bw);

			m_mapSTwN0wd0.saveScoresToFileStream(bw);
			m_mapSTptN0ptd0.saveScoresToFileStream(bw);

			m_mapST2wN0wd1.saveScoresToFileStream(bw);
			m_mapST2ptN0ptd1.saveScoresToFileStream(bw);
			
			m_mapSTwrda.saveScoresToFileStream(bw);
			m_mapSTptrda.saveScoresToFileStream(bw);
			m_mapSTwlda.saveScoresToFileStream(bw);
			m_mapSTptlda.saveScoresToFileStream(bw);
			m_mapN0wlda.saveScoresToFileStream(bw);
			m_mapN0ptlda.saveScoresToFileStream(bw);

			m_mapST2wrda.saveScoresToFileStream(bw);
			m_mapST2ptrda.saveScoresToFileStream(bw);
			m_mapST2wlda.saveScoresToFileStream(bw);
			m_mapST2ptlda.saveScoresToFileStream(bw);
			
			m_mapSTwrha.saveScoresToFileStream(bw);
			m_mapSTptrha.saveScoresToFileStream(bw);
			m_mapSTwlha.saveScoresToFileStream(bw);
			m_mapSTptlha.saveScoresToFileStream(bw);
			m_mapN0wlha.saveScoresToFileStream(bw);
			m_mapN0ptlha.saveScoresToFileStream(bw);

			m_mapST2wrha.saveScoresToFileStream(bw);
			m_mapST2ptrha.saveScoresToFileStream(bw);
			m_mapST2wlha.saveScoresToFileStream(bw);
			m_mapST2ptlha.saveScoresToFileStream(bw);
			
			m_mapSTwrp.saveScoresToFileStream(bw);
			m_mapSTptrp.saveScoresToFileStream(bw);

			m_mapST2wrp.saveScoresToFileStream(bw);
			m_mapST2ptrp.saveScoresToFileStream(bw);
			
			m_mapSTwlp.saveScoresToFileStream(bw);
			m_mapSTptlp.saveScoresToFileStream(bw);
			
			m_mapST2wlp.saveScoresToFileStream(bw);
			m_mapST2ptlp.saveScoresToFileStream(bw);

			m_mapN0wlp.saveScoresToFileStream(bw);
			m_mapN0ptlp.saveScoresToFileStream(bw);

			m_mapPOSPath.saveScoresToFileStream(bw);
			m_mapFPOSPath.saveScoresToFileStream(bw);
			m_mapSPOSPath.saveScoresToFileStream(bw);
			m_mapSFPOSPath.saveScoresToFileStream(bw);
			
			m_mapST_1ct.saveScoresToFileStream(bw);
			m_mapST_2ct.saveScoresToFileStream(bw);
			m_mapSTctST2ct.saveScoresToFileStream(bw);
			m_mapN_1ctN_2ct.saveScoresToFileStream(bw);
			m_mapSTctN0w.saveScoresToFileStream(bw);
			m_mapST2ctN0w.saveScoresToFileStream(bw);
			
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
		
		m_mapST2w.computeAverage(round);
		m_mapST2pt.computeAverage(round);
		m_mapST2wpt.computeAverage(round);
		m_mapST2ct.computeAverage(round);

		m_mapN0w.computeAverage(round);
		m_mapN0pt.computeAverage(round);
		m_mapN0wpt.computeAverage(round);

		m_mapN1w.computeAverage(round);
		m_mapN1pt.computeAverage(round);
		m_mapN1wpt.computeAverage(round);

		m_mapN2w.computeAverage(round);
		m_mapN2pt.computeAverage(round);
		m_mapN2wpt.computeAverage(round);

		m_mapN_1w.computeAverage(round);
		m_mapN_1pt.computeAverage(round);
		m_mapN_1wpt.computeAverage(round);
		m_mapN_1ct.computeAverage(round);

		m_mapN_2w.computeAverage(round);
		m_mapN_2pt.computeAverage(round);
		m_mapN_2wpt.computeAverage(round);
		m_mapN_2ct.computeAverage(round);

		m_mapSTLHw.computeAverage(round);
		m_mapSTLHpt.computeAverage(round);
		m_mapSTlhl.computeAverage(round);
		
		m_mapST2LHw.computeAverage(round);
		m_mapST2LHpt.computeAverage(round);
		m_mapST2lhl.computeAverage(round);

		m_mapN0LHw.computeAverage(round);
		m_mapN0LHpt.computeAverage(round);
		m_mapN0lhl.computeAverage(round);

		m_mapSTRHw.computeAverage(round);
		m_mapSTRHpt.computeAverage(round);
		m_mapSTrhl.computeAverage(round);

		m_mapST2RHw.computeAverage(round);
		m_mapST2RHpt.computeAverage(round);
		m_mapST2rhl.computeAverage(round);

		m_mapSTLDw.computeAverage(round);
		m_mapSTLDpt.computeAverage(round);
		m_mapSTldl.computeAverage(round);

		m_mapST2LDw.computeAverage(round);
		m_mapST2LDpt.computeAverage(round);
		m_mapST2ldl.computeAverage(round);
		
		m_mapSTRDw.computeAverage(round);
		m_mapSTRDpt.computeAverage(round);
		m_mapSTrdl.computeAverage(round);
		
		m_mapST2RDw.computeAverage(round);
		m_mapST2RDpt.computeAverage(round);
		m_mapST2rdl.computeAverage(round);

		m_mapN0LDw.computeAverage(round);
		m_mapN0LDpt.computeAverage(round);
		m_mapN0ldl.computeAverage(round);

		m_mapSTwptN0wpt.computeAverage(round);
		m_mapSTwptN0w.computeAverage(round);
		m_mapSTwN0wpt.computeAverage(round);
		m_mapSTptN0wpt.computeAverage(round);
		m_mapSTwptN0pt.computeAverage(round);
		m_mapSTwN0w.computeAverage(round);
		

		m_mapST2wptN0wpt.computeAverage(round);
		m_mapST2wptN0w.computeAverage(round);
		m_mapST2wN0wpt.computeAverage(round);
		m_mapST2ptN0wpt.computeAverage(round);
		m_mapST2wptN0pt.computeAverage(round);
		m_mapST2wN0w.computeAverage(round);

		m_mapSTptN0pt.computeAverage(round);
		m_mapN0ptN1pt.computeAverage(round);
		m_mapN0ptN_1pt.computeAverage(round);
		m_mapN_2ptN_1ptN0pt.computeAverage(round);
		m_mapN_1ptN0ptN1pt.computeAverage(round);
		m_mapN0ptN1ptN2pt.computeAverage(round);
		m_mapSTptN_1ptN0pt.computeAverage(round);
		m_mapSTptN0ptN1pt.computeAverage(round);
		m_mapSTptN0ptN0LDpt.computeAverage(round);
		m_mapSTptN0ptN0LHpt.computeAverage(round);
		m_mapSTLHptSTptN0pt.computeAverage(round);
		m_mapSTRHptSTptN0pt.computeAverage(round);
		m_mapSTptSTLDptN0pt.computeAverage(round);
		m_mapSTptSTRDptN0pt.computeAverage(round);

		m_mapST2ptN0pt.computeAverage(round);
		m_mapST2ptN_1ptN0pt.computeAverage(round);
		m_mapST2ptN0ptN1pt.computeAverage(round);
		m_mapST2ptN0ptN0LDpt.computeAverage(round);
		m_mapST2ptN0ptN0LHpt.computeAverage(round);
		m_mapST2LHptST2ptN0pt.computeAverage(round);
		m_mapST2RHptST2ptN0pt.computeAverage(round);
		m_mapST2ptST2LDptN0pt.computeAverage(round);
		m_mapST2ptST2RDptN0pt.computeAverage(round);

		m_mapSTwN0wd0.computeAverage(round);
		m_mapSTptN0ptd0.computeAverage(round);

		m_mapST2wN0wd1.computeAverage(round);
		m_mapST2ptN0ptd1.computeAverage(round);
		
		m_mapSTwrda.computeAverage(round);
		m_mapSTptrda.computeAverage(round);
		m_mapSTwlda.computeAverage(round);
		m_mapSTptlda.computeAverage(round);
		m_mapN0wlda.computeAverage(round);
		m_mapN0ptlda.computeAverage(round);

		m_mapST2wrda.computeAverage(round);
		m_mapST2ptrda.computeAverage(round);
		m_mapST2wlda.computeAverage(round);
		m_mapST2ptlda.computeAverage(round);
		
		m_mapSTwrha.computeAverage(round);
		m_mapSTptrha.computeAverage(round);
		m_mapSTwlha.computeAverage(round);
		m_mapSTptlha.computeAverage(round);
		m_mapN0wlha.computeAverage(round);
		m_mapN0ptlha.computeAverage(round);

		m_mapST2wrha.computeAverage(round);
		m_mapST2ptrha.computeAverage(round);
		m_mapST2wlha.computeAverage(round);
		m_mapST2ptlha.computeAverage(round);
		
		m_mapSTwrp.computeAverage(round);
		m_mapSTptrp.computeAverage(round);

		m_mapST2wrp.computeAverage(round);
		m_mapST2ptrp.computeAverage(round);
		
		m_mapSTwlp.computeAverage(round);
		m_mapSTptlp.computeAverage(round);
		
		m_mapST2wlp.computeAverage(round);
		m_mapST2ptlp.computeAverage(round);

		m_mapN0wlp.computeAverage(round);
		m_mapN0ptlp.computeAverage(round);

		m_mapPOSPath.computeAverage(round);
		m_mapFPOSPath.computeAverage(round);
		m_mapSPOSPath.computeAverage(round);
		m_mapSFPOSPath.computeAverage(round);
		
		m_mapST_1ct.computeAverage(round);
		m_mapST_2ct.computeAverage(round);
		m_mapSTctST2ct.computeAverage(round);
		m_mapN_1ctN_2ct.computeAverage(round);
		m_mapSTctN0w.computeAverage(round);
		m_mapST2ctN0w.computeAverage(round);
		
		System.out.println("done.");
	}
}
