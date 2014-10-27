package common.parser.implementations.twostack;

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
	
	public WordMap m_map2STw;
	public POSTagMap m_map2STpt;
	public WordPOSTagMap m_map2STwpt;
	public IntMap m_map2STct;

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
	
	public WordMap m_map2STLHw;
	public POSTagMap m_map2STLHpt;
	public IntMap m_map2STlhl;

	public WordMap m_mapN0LHw;
	public POSTagMap m_mapN0LHpt;
	public IntMap m_mapN0lhl;

	public WordMap m_mapSTRHw;
	public POSTagMap m_mapSTRHpt;
	public IntMap m_mapSTrhl;

	public WordMap m_mapST2RHw;
	public POSTagMap m_mapST2RHpt;
	public IntMap m_mapST2rhl;
	
	public WordMap m_map2STRHw;
	public POSTagMap m_map2STRHpt;
	public IntMap m_map2STrhl;

	public WordMap m_mapSTL2Hw;
	public POSTagMap m_mapSTL2Hpt;
	public IntMap m_mapSTl2hl;

	public WordMap m_mapN0L2Hw;
	public POSTagMap m_mapN0L2Hpt;
	public IntMap m_mapN0l2hl;

	public WordMap m_mapSTR2Hw;
	public POSTagMap m_mapSTR2Hpt;
	public IntMap m_mapSTr2hl;

	public WordMap m_mapSTLHLHw;
	public POSTagMap m_mapSTLHLHpt;
	public IntMap m_mapSTLHlhl;

	public WordMap m_mapSTLHRHw;
	public POSTagMap m_mapSTLHRHpt;
	public IntMap m_mapSTLHrhl;

	public WordMap m_mapSTRHLHw;
	public POSTagMap m_mapSTRHLHpt;
	public IntMap m_mapSTRHlhl;

	public WordMap m_mapSTRHRHw;
	public POSTagMap m_mapSTRHRHpt;
	public IntMap m_mapSTRHrhl;

	public WordMap m_mapSTLDw;
	public POSTagMap m_mapSTLDpt;
	public IntMap m_mapSTldl;

	public WordMap m_mapST2LDw;
	public POSTagMap m_mapST2LDpt;
	public IntMap m_mapST2ldl;
	
	public WordMap m_map2STLDw;
	public POSTagMap m_map2STLDpt;
	public IntMap m_map2STldl;
	
	public WordMap m_mapSTRDw;
	public POSTagMap m_mapSTRDpt;
	public IntMap m_mapSTrdl;
	
	public WordMap m_mapST2RDw;
	public POSTagMap m_mapST2RDpt;
	public IntMap m_mapST2rdl;
	
	public WordMap m_map2STRDw;
	public POSTagMap m_map2STRDpt;
	public IntMap m_map2STrdl;

	public WordMap m_mapN0LDw;
	public POSTagMap m_mapN0LDpt;
	public IntMap m_mapN0ldl;

	public WordMap m_mapSTL2Dw;
	public POSTagMap m_mapSTL2Dpt;
	public IntMap m_mapSTl2dl;

	public WordMap m_mapSTR2Dw;
	public POSTagMap m_mapSTR2Dpt;
	public IntMap m_mapSTr2dl;

	public WordMap m_mapN0L2Dw;
	public POSTagMap m_mapN0L2Dpt;
	public IntMap m_mapN0l2dl;

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
	
	public TwoPOSTaggedWordsMap m_map2STwptN0wpt;
	public WordWordPOSTagMap m_map2STwptN0w;
	public WordWordPOSTagMap m_map2STwN0wpt;
	public WordPOSTagPOSTagMap m_map2STptN0wpt;
	public WordPOSTagPOSTagMap m_map2STwptN0pt;
	public TwoWordsMap m_map2STwN0w;

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
	public POSTagSet3Map m_mapN0ptN0LDptN0L2Dpt;
	public POSTagSet3Map m_mapN0ptN0LHptN0L2Hpt;
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

	public POSTagSet2Map m_mapST2ptN0pt;
	public POSTagSet3Map m_mapST2ptN_1ptN0pt;
	public POSTagSet3Map m_mapST2ptN0ptN1pt; 
	public POSTagSet3Map m_mapST2ptN0ptN0LDpt;
	public POSTagSet3Map m_mapST2ptN0ptN0LHpt;
	public POSTagSet3Map m_mapST2LHptST2ptN0pt;
	public POSTagSet3Map m_mapST2RHptST2ptN0pt;
	public POSTagSet3Map m_mapST2ptST2LDptN0pt;
	public POSTagSet3Map m_mapST2ptST2RDptN0pt;

	public POSTagSet2Map m_map2STptN0pt;
	public POSTagSet3Map m_map2STptN_1ptN0pt;
	public POSTagSet3Map m_map2STptN0ptN1pt; 
	public POSTagSet3Map m_map2STptN0ptN0LDpt;
	public POSTagSet3Map m_map2STptN0ptN0LHpt;
	public POSTagSet3Map m_map2STLHpt2STptN0pt;
	public POSTagSet3Map m_map2STRHpt2STptN0pt;
	public POSTagSet3Map m_map2STpt2STLDptN0pt;
	public POSTagSet3Map m_map2STpt2STRDptN0pt;
	
	public WordWordIntMap m_mapSTwN0wd0;
	public POSTagPOSTagIntMap m_mapSTptN0ptd0;

	public WordWordIntMap m_mapST2wN0wd1;
	public POSTagPOSTagIntMap m_mapST2ptN0ptd1;

	public WordWordIntMap m_map2STwN0wd2;
	public POSTagPOSTagIntMap m_map2STptN0ptd2;
	
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

	public WordIntMap m_map2STwrda;
	public POSTagIntMap m_map2STptrda;
	public WordIntMap m_map2STwlda;
	public POSTagIntMap m_map2STptlda;
	
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

	public WordIntMap m_map2STwrha;
	public POSTagIntMap m_map2STptrha;
	public WordIntMap m_map2STwlha;
	public POSTagIntMap m_map2STptlha;
	
	public WordSetOfDepLabelsMap m_mapSTwrp;
	public POSTagSetOfDepLabelsMap m_mapSTptrp;

	public WordSetOfDepLabelsMap m_mapST2wrp;
	public POSTagSetOfDepLabelsMap m_mapST2ptrp;

	public WordSetOfDepLabelsMap m_map2STwrp;
	public POSTagSetOfDepLabelsMap m_map2STptrp;
	
	public WordSetOfDepLabelsMap m_mapSTwlp;
	public POSTagSetOfDepLabelsMap m_mapSTptlp;
	
	public WordSetOfDepLabelsMap m_mapST2wlp;
	public POSTagSetOfDepLabelsMap m_mapST2ptlp;
	
	public WordSetOfDepLabelsMap m_map2STwlp;
	public POSTagSetOfDepLabelsMap m_map2STptlp;

	public WordSetOfDepLabelsMap m_mapN0wlp;
	public POSTagSetOfDepLabelsMap m_mapN0ptlp;

	public StringMap m_mapPOSPath;
	public StringMap m_mapFPOSPath;
	public StringMap m_mapSPOSPath;
	public StringMap m_mapSFPOSPath;
	public StringMap m_map2POSPath;
	public StringMap m_map2FPOSPath;
	
	public IntMap m_mapST_1ct;
	public IntMap m_mapST_2ct;
	public TwoIntsMap m_mapSTctST2ct;
	public TwoIntsMap m_mapSTct2STct;
	public TwoIntsMap m_mapN_1ctN_2ct;
	public WordIntMap m_mapSTctN0w;
	public WordIntMap m_mapST2ctN0w;
	public WordIntMap m_map2STctN0w;

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
		
		m_map2STw = new WordMap("a");
		m_map2STpt = new POSTagMap("a");
		m_map2STwpt = new WordPOSTagMap("a");
		m_map2STct = new IntMap("a");

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
		
		m_map2STLHw = new WordMap("a");
		m_map2STLHpt = new POSTagMap("a");
		m_map2STlhl = new IntMap("a");

		m_mapN0LHw = new WordMap("a");
		m_mapN0LHpt = new POSTagMap("a");
		m_mapN0lhl = new IntMap("a");

		m_mapSTRHw = new WordMap("a");
		m_mapSTRHpt = new POSTagMap("a");
		m_mapSTrhl = new IntMap("a");

		m_mapST2RHw = new WordMap("a");
		m_mapST2RHpt = new POSTagMap("a");
		m_mapST2rhl = new IntMap("a");
		
		m_map2STRHw = new WordMap("a");
		m_map2STRHpt = new POSTagMap("a");
		m_map2STrhl = new IntMap("a");

		m_mapSTL2Hw = new WordMap("a");
		m_mapSTL2Hpt = new POSTagMap("a");
		m_mapSTl2hl = new IntMap("a");

		m_mapN0L2Hw = new WordMap("a");
		m_mapN0L2Hpt = new POSTagMap("a");
		m_mapN0l2hl = new IntMap("a");

		m_mapSTR2Hw = new WordMap("a");
		m_mapSTR2Hpt = new POSTagMap("a");
		m_mapSTr2hl = new IntMap("a");

		m_mapSTLHLHw = new WordMap("a");
		m_mapSTLHLHpt = new POSTagMap("a");
		m_mapSTLHlhl = new IntMap("a");

		m_mapSTLHRHw = new WordMap("a");
		m_mapSTLHRHpt = new POSTagMap("a");
		m_mapSTLHrhl = new IntMap("a");

		m_mapSTRHLHw = new WordMap("a");
		m_mapSTRHLHpt = new POSTagMap("a");
		m_mapSTRHlhl = new IntMap("a");

		m_mapSTRHRHw = new WordMap("a");
		m_mapSTRHRHpt = new POSTagMap("a");
		m_mapSTRHrhl = new IntMap("a");

		m_mapSTLDw = new WordMap("a");
		m_mapSTLDpt = new POSTagMap("a");
		m_mapSTldl = new IntMap("a");

		m_mapST2LDw = new WordMap("a");
		m_mapST2LDpt = new POSTagMap("a");
		m_mapST2ldl = new IntMap("a");
		
		m_map2STLDw = new WordMap("a");
		m_map2STLDpt = new POSTagMap("a");
		m_map2STldl = new IntMap("a");
		
		m_mapSTRDw = new WordMap("a");
		m_mapSTRDpt = new POSTagMap("a");
		m_mapSTrdl = new IntMap("a");
		
		m_mapST2RDw = new WordMap("a");
		m_mapST2RDpt = new POSTagMap("a");
		m_mapST2rdl = new IntMap("a");
		
		m_map2STRDw = new WordMap("a");
		m_map2STRDpt = new POSTagMap("a");
		m_map2STrdl = new IntMap("a");

		m_mapN0LDw = new WordMap("a");
		m_mapN0LDpt = new POSTagMap("a");
		m_mapN0ldl = new IntMap("a");

		m_mapSTL2Dw = new WordMap("a");
		m_mapSTL2Dpt = new POSTagMap("a");
		m_mapSTl2dl = new IntMap("a");

		m_mapSTR2Dw = new WordMap("a");
		m_mapSTR2Dpt = new POSTagMap("a");
		m_mapSTr2dl = new IntMap("a");

		m_mapN0L2Dw = new WordMap("a");
		m_mapN0L2Dpt = new POSTagMap("a");
		m_mapN0l2dl = new IntMap("a");

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
		
		m_map2STwptN0wpt = new TwoPOSTaggedWordsMap("a");
		m_map2STwptN0w = new WordWordPOSTagMap("a");
		m_map2STwN0wpt = new WordWordPOSTagMap("a");
		m_map2STptN0wpt = new WordPOSTagPOSTagMap("a");
		m_map2STwptN0pt = new WordPOSTagPOSTagMap("a");
		m_map2STwN0w = new TwoWordsMap("a");

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
		m_mapN0ptN0LDptN0L2Dpt = new POSTagSet3Map("a");
		m_mapN0ptN0LHptN0L2Hpt = new POSTagSet3Map("a");
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

		m_mapST2ptN0pt = new POSTagSet2Map("a");
		m_mapST2ptN_1ptN0pt = new POSTagSet3Map("a");
		m_mapST2ptN0ptN1pt = new POSTagSet3Map("a");
		m_mapST2ptN0ptN0LDpt = new POSTagSet3Map("a");
		m_mapST2ptN0ptN0LHpt = new POSTagSet3Map("a");
		m_mapST2LHptST2ptN0pt = new POSTagSet3Map("a");
		m_mapST2RHptST2ptN0pt = new POSTagSet3Map("a");
		m_mapST2ptST2LDptN0pt = new POSTagSet3Map("a");
		m_mapST2ptST2RDptN0pt = new POSTagSet3Map("a");

		m_map2STptN0pt = new POSTagSet2Map("a");
		m_map2STptN_1ptN0pt = new POSTagSet3Map("a");
		m_map2STptN0ptN1pt = new POSTagSet3Map("a");
		m_map2STptN0ptN0LDpt = new POSTagSet3Map("a");
		m_map2STptN0ptN0LHpt = new POSTagSet3Map("a");
		m_map2STLHpt2STptN0pt = new POSTagSet3Map("a");
		m_map2STRHpt2STptN0pt = new POSTagSet3Map("a");
		m_map2STpt2STLDptN0pt = new POSTagSet3Map("a");
		m_map2STpt2STRDptN0pt = new POSTagSet3Map("a");
		
		m_mapSTwN0wd0 = new WordWordIntMap("a");
		m_mapSTptN0ptd0 = new POSTagPOSTagIntMap("a");

		m_mapST2wN0wd1 = new WordWordIntMap("a");
		m_mapST2ptN0ptd1 = new POSTagPOSTagIntMap("a");

		m_map2STwN0wd2 = new WordWordIntMap("a");
		m_map2STptN0ptd2 = new POSTagPOSTagIntMap("a");
		
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

		m_map2STwrda = new WordIntMap("a");
		m_map2STptrda = new POSTagIntMap("a");
		m_map2STwlda = new WordIntMap("a");
		m_map2STptlda = new POSTagIntMap("a");
		
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

		m_map2STwrha = new WordIntMap("a");
		m_map2STptrha = new POSTagIntMap("a");
		m_map2STwlha = new WordIntMap("a");
		m_map2STptlha = new POSTagIntMap("a");
		
		m_mapSTwrp = new WordSetOfDepLabelsMap("a");
		m_mapSTptrp = new POSTagSetOfDepLabelsMap("a");

		m_mapST2wrp = new WordSetOfDepLabelsMap("a");
		m_mapST2ptrp = new POSTagSetOfDepLabelsMap("a");

		m_map2STwrp = new WordSetOfDepLabelsMap("a");
		m_map2STptrp = new POSTagSetOfDepLabelsMap("a");
		
		m_mapSTwlp = new WordSetOfDepLabelsMap("a");
		m_mapSTptlp = new POSTagSetOfDepLabelsMap("a");
		
		m_mapST2wlp = new WordSetOfDepLabelsMap("a");
		m_mapST2ptlp = new POSTagSetOfDepLabelsMap("a");
		
		m_map2STwlp = new WordSetOfDepLabelsMap("a");
		m_map2STptlp = new POSTagSetOfDepLabelsMap("a");

		m_mapN0wlp = new WordSetOfDepLabelsMap("a");
		m_mapN0ptlp = new POSTagSetOfDepLabelsMap("a");

		m_mapPOSPath = new StringMap("a");
		m_mapFPOSPath = new StringMap("a");
		m_mapSPOSPath = new StringMap("a");
		m_mapSFPOSPath = new StringMap("a");
		m_map2POSPath = new StringMap("a");
		m_map2FPOSPath = new StringMap("a");
		
		m_mapST_1ct = new IntMap("a");
		m_mapST_2ct = new IntMap("a");
		m_mapSTctST2ct = new TwoIntsMap("a");
		m_mapSTct2STct = new TwoIntsMap("a");
		m_mapN_1ctN_2ct = new TwoIntsMap("a");
		m_mapSTctN0w = new WordIntMap("a");
		m_mapST2ctN0w = new WordIntMap("a");
		m_map2STctN0w = new WordIntMap("a");
		
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
			
			m_map2STw.loadScoresFromFileStream(br);
			m_map2STpt.loadScoresFromFileStream(br);
			m_map2STwpt.loadScoresFromFileStream(br);
			m_map2STct.loadScoresFromFileStream(br);

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
			
			m_map2STLHw.loadScoresFromFileStream(br);
			m_map2STLHpt.loadScoresFromFileStream(br);
			m_map2STlhl.loadScoresFromFileStream(br);

			m_mapN0LHw.loadScoresFromFileStream(br);
			m_mapN0LHpt.loadScoresFromFileStream(br);
			m_mapN0lhl.loadScoresFromFileStream(br);

			m_mapSTRHw.loadScoresFromFileStream(br);
			m_mapSTRHpt.loadScoresFromFileStream(br);
			m_mapSTrhl.loadScoresFromFileStream(br);

			m_mapST2RHw.loadScoresFromFileStream(br);
			m_mapST2RHpt.loadScoresFromFileStream(br);
			m_mapST2rhl.loadScoresFromFileStream(br);
			
			m_map2STRHw.loadScoresFromFileStream(br);
			m_map2STRHpt.loadScoresFromFileStream(br);
			m_map2STrhl.loadScoresFromFileStream(br);

			m_mapSTL2Hw.loadScoresFromFileStream(br);
			m_mapSTL2Hpt.loadScoresFromFileStream(br);
			m_mapSTl2hl.loadScoresFromFileStream(br);

			m_mapN0L2Hw.loadScoresFromFileStream(br);
			m_mapN0L2Hpt.loadScoresFromFileStream(br);
			m_mapN0l2hl.loadScoresFromFileStream(br);

			m_mapSTR2Hw.loadScoresFromFileStream(br);
			m_mapSTR2Hpt.loadScoresFromFileStream(br);
			m_mapSTr2hl.loadScoresFromFileStream(br);

			m_mapSTLHLHw.loadScoresFromFileStream(br);
			m_mapSTLHLHpt.loadScoresFromFileStream(br);
			m_mapSTLHlhl.loadScoresFromFileStream(br);

			m_mapSTLHRHw.loadScoresFromFileStream(br);
			m_mapSTLHRHpt.loadScoresFromFileStream(br);
			m_mapSTLHrhl.loadScoresFromFileStream(br);

			m_mapSTRHLHw.loadScoresFromFileStream(br);
			m_mapSTRHLHpt.loadScoresFromFileStream(br);
			m_mapSTRHlhl.loadScoresFromFileStream(br);

			m_mapSTRHRHw.loadScoresFromFileStream(br);
			m_mapSTRHRHpt.loadScoresFromFileStream(br);
			m_mapSTRHrhl.loadScoresFromFileStream(br);

			m_mapSTLDw.loadScoresFromFileStream(br);
			m_mapSTLDpt.loadScoresFromFileStream(br);
			m_mapSTldl.loadScoresFromFileStream(br);

			m_mapST2LDw.loadScoresFromFileStream(br);
			m_mapST2LDpt.loadScoresFromFileStream(br);
			m_mapST2ldl.loadScoresFromFileStream(br);
			
			m_map2STLDw.loadScoresFromFileStream(br);
			m_map2STLDpt.loadScoresFromFileStream(br);
			m_map2STldl.loadScoresFromFileStream(br);
			
			m_mapSTRDw.loadScoresFromFileStream(br);
			m_mapSTRDpt.loadScoresFromFileStream(br);
			m_mapSTrdl.loadScoresFromFileStream(br);
			
			m_mapST2RDw.loadScoresFromFileStream(br);
			m_mapST2RDpt.loadScoresFromFileStream(br);
			m_mapST2rdl.loadScoresFromFileStream(br);
			
			m_map2STRDw.loadScoresFromFileStream(br);
			m_map2STRDpt.loadScoresFromFileStream(br);
			m_map2STrdl.loadScoresFromFileStream(br);

			m_mapN0LDw.loadScoresFromFileStream(br);
			m_mapN0LDpt.loadScoresFromFileStream(br);
			m_mapN0ldl.loadScoresFromFileStream(br);

			m_mapSTL2Dw.loadScoresFromFileStream(br);
			m_mapSTL2Dpt.loadScoresFromFileStream(br);
			m_mapSTl2dl.loadScoresFromFileStream(br);

			m_mapSTR2Dw.loadScoresFromFileStream(br);
			m_mapSTR2Dpt.loadScoresFromFileStream(br);
			m_mapSTr2dl.loadScoresFromFileStream(br);

			m_mapN0L2Dw.loadScoresFromFileStream(br);
			m_mapN0L2Dpt.loadScoresFromFileStream(br);
			m_mapN0l2dl.loadScoresFromFileStream(br);

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
			
			m_map2STwptN0wpt.loadScoresFromFileStream(br);
			m_map2STwptN0w.loadScoresFromFileStream(br);
			m_map2STwN0wpt.loadScoresFromFileStream(br);
			m_map2STptN0wpt.loadScoresFromFileStream(br);
			m_map2STwptN0pt.loadScoresFromFileStream(br);
			m_map2STwN0w.loadScoresFromFileStream(br);

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
			m_mapN0ptN0LDptN0L2Dpt.loadScoresFromFileStream(br);
			m_mapN0ptN0LHptN0L2Hpt.loadScoresFromFileStream(br);
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

			m_mapST2ptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN_1ptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapST2LHptST2ptN0pt.loadScoresFromFileStream(br);
			m_mapST2RHptST2ptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2LDptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2RDptN0pt.loadScoresFromFileStream(br);

			m_map2STptN0pt.loadScoresFromFileStream(br);
			m_map2STptN_1ptN0pt.loadScoresFromFileStream(br);
			m_map2STptN0ptN1pt.loadScoresFromFileStream(br);
			m_map2STptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_map2STptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_map2STLHpt2STptN0pt.loadScoresFromFileStream(br);
			m_map2STRHpt2STptN0pt.loadScoresFromFileStream(br);
			m_map2STpt2STLDptN0pt.loadScoresFromFileStream(br);
			m_map2STpt2STRDptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0wd0.loadScoresFromFileStream(br);
			m_mapSTptN0ptd0.loadScoresFromFileStream(br);

			m_mapST2wN0wd1.loadScoresFromFileStream(br);
			m_mapST2ptN0ptd1.loadScoresFromFileStream(br);

			m_map2STwN0wd2.loadScoresFromFileStream(br);
			m_map2STptN0ptd2.loadScoresFromFileStream(br);
			
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

			m_map2STwrda.loadScoresFromFileStream(br);
			m_map2STptrda.loadScoresFromFileStream(br);
			m_map2STwlda.loadScoresFromFileStream(br);
			m_map2STptlda.loadScoresFromFileStream(br);
			
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

			m_map2STwrha.loadScoresFromFileStream(br);
			m_map2STptrha.loadScoresFromFileStream(br);
			m_map2STwlha.loadScoresFromFileStream(br);
			m_map2STptlha.loadScoresFromFileStream(br);
			
			m_mapSTwrp.loadScoresFromFileStream(br);
			m_mapSTptrp.loadScoresFromFileStream(br);

			m_mapST2wrp.loadScoresFromFileStream(br);
			m_mapST2ptrp.loadScoresFromFileStream(br);

			m_map2STwrp.loadScoresFromFileStream(br);
			m_map2STptrp.loadScoresFromFileStream(br);
			
			m_mapSTwlp.loadScoresFromFileStream(br);
			m_mapSTptlp.loadScoresFromFileStream(br);
			
			m_mapST2wlp.loadScoresFromFileStream(br);
			m_mapST2ptlp.loadScoresFromFileStream(br);
			
			m_map2STwlp.loadScoresFromFileStream(br);
			m_map2STptlp.loadScoresFromFileStream(br);

			m_mapN0wlp.loadScoresFromFileStream(br);
			m_mapN0ptlp.loadScoresFromFileStream(br);

			m_mapPOSPath.loadScoresFromFileStream(br);
			m_mapFPOSPath.loadScoresFromFileStream(br);
			m_mapSPOSPath.loadScoresFromFileStream(br);
			m_mapSFPOSPath.loadScoresFromFileStream(br);
			m_map2POSPath.loadScoresFromFileStream(br);
			m_map2FPOSPath.loadScoresFromFileStream(br);
			
			m_mapST_1ct.loadScoresFromFileStream(br);
			m_mapST_2ct.loadScoresFromFileStream(br);
			m_mapSTctST2ct.loadScoresFromFileStream(br);
			m_mapSTct2STct.loadScoresFromFileStream(br);
			m_mapN_1ctN_2ct.loadScoresFromFileStream(br);
			m_mapSTctN0w.loadScoresFromFileStream(br);
			m_mapST2ctN0w.loadScoresFromFileStream(br);
			m_map2STctN0w.loadScoresFromFileStream(br);
			
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
			
			m_map2STw.saveScoresToFileStream(bw);
			m_map2STpt.saveScoresToFileStream(bw);
			m_map2STwpt.saveScoresToFileStream(bw);
			m_map2STct.saveScoresToFileStream(bw);

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
			
			m_map2STLHw.saveScoresToFileStream(bw);
			m_map2STLHpt.saveScoresToFileStream(bw);
			m_map2STlhl.saveScoresToFileStream(bw);

			m_mapN0LHw.saveScoresToFileStream(bw);
			m_mapN0LHpt.saveScoresToFileStream(bw);
			m_mapN0lhl.saveScoresToFileStream(bw);

			m_mapSTRHw.saveScoresToFileStream(bw);
			m_mapSTRHpt.saveScoresToFileStream(bw);
			m_mapSTrhl.saveScoresToFileStream(bw);

			m_mapST2RHw.saveScoresToFileStream(bw);
			m_mapST2RHpt.saveScoresToFileStream(bw);
			m_mapST2rhl.saveScoresToFileStream(bw);
			
			m_map2STRHw.saveScoresToFileStream(bw);
			m_map2STRHpt.saveScoresToFileStream(bw);
			m_map2STrhl.saveScoresToFileStream(bw);

			m_mapSTL2Hw.saveScoresToFileStream(bw);
			m_mapSTL2Hpt.saveScoresToFileStream(bw);
			m_mapSTl2hl.saveScoresToFileStream(bw);

			m_mapN0L2Hw.saveScoresToFileStream(bw);
			m_mapN0L2Hpt.saveScoresToFileStream(bw);
			m_mapN0l2hl.saveScoresToFileStream(bw);

			m_mapSTR2Hw.saveScoresToFileStream(bw);
			m_mapSTR2Hpt.saveScoresToFileStream(bw);
			m_mapSTr2hl.saveScoresToFileStream(bw);

			m_mapSTLHLHw.saveScoresToFileStream(bw);
			m_mapSTLHLHpt.saveScoresToFileStream(bw);
			m_mapSTLHlhl.saveScoresToFileStream(bw);

			m_mapSTLHRHw.saveScoresToFileStream(bw);
			m_mapSTLHRHpt.saveScoresToFileStream(bw);
			m_mapSTLHrhl.saveScoresToFileStream(bw);

			m_mapSTRHLHw.saveScoresToFileStream(bw);
			m_mapSTRHLHpt.saveScoresToFileStream(bw);
			m_mapSTRHlhl.saveScoresToFileStream(bw);

			m_mapSTRHRHw.saveScoresToFileStream(bw);
			m_mapSTRHRHpt.saveScoresToFileStream(bw);
			m_mapSTRHrhl.saveScoresToFileStream(bw);

			m_mapSTLDw.saveScoresToFileStream(bw);
			m_mapSTLDpt.saveScoresToFileStream(bw);
			m_mapSTldl.saveScoresToFileStream(bw);

			m_mapST2LDw.saveScoresToFileStream(bw);
			m_mapST2LDpt.saveScoresToFileStream(bw);
			m_mapST2ldl.saveScoresToFileStream(bw);
			
			m_map2STLDw.saveScoresToFileStream(bw);
			m_map2STLDpt.saveScoresToFileStream(bw);
			m_map2STldl.saveScoresToFileStream(bw);
			
			m_mapSTRDw.saveScoresToFileStream(bw);
			m_mapSTRDpt.saveScoresToFileStream(bw);
			m_mapSTrdl.saveScoresToFileStream(bw);
			
			m_mapST2RDw.saveScoresToFileStream(bw);
			m_mapST2RDpt.saveScoresToFileStream(bw);
			m_mapST2rdl.saveScoresToFileStream(bw);
			
			m_map2STRDw.saveScoresToFileStream(bw);
			m_map2STRDpt.saveScoresToFileStream(bw);
			m_map2STrdl.saveScoresToFileStream(bw);

			m_mapN0LDw.saveScoresToFileStream(bw);
			m_mapN0LDpt.saveScoresToFileStream(bw);
			m_mapN0ldl.saveScoresToFileStream(bw);

			m_mapSTL2Dw.saveScoresToFileStream(bw);
			m_mapSTL2Dpt.saveScoresToFileStream(bw);
			m_mapSTl2dl.saveScoresToFileStream(bw);

			m_mapSTR2Dw.saveScoresToFileStream(bw);
			m_mapSTR2Dpt.saveScoresToFileStream(bw);
			m_mapSTr2dl.saveScoresToFileStream(bw);

			m_mapN0L2Dw.saveScoresToFileStream(bw);
			m_mapN0L2Dpt.saveScoresToFileStream(bw);
			m_mapN0l2dl.saveScoresToFileStream(bw);

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
			
			m_map2STwptN0wpt.saveScoresToFileStream(bw);
			m_map2STwptN0w.saveScoresToFileStream(bw);
			m_map2STwN0wpt.saveScoresToFileStream(bw);
			m_map2STptN0wpt.saveScoresToFileStream(bw);
			m_map2STwptN0pt.saveScoresToFileStream(bw);
			m_map2STwN0w.saveScoresToFileStream(bw);

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
			m_mapN0ptN0LDptN0L2Dpt.saveScoresToFileStream(bw);
			m_mapN0ptN0LHptN0L2Hpt.saveScoresToFileStream(bw);
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

			m_mapST2ptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN_1ptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapST2LHptST2ptN0pt.saveScoresToFileStream(bw);
			m_mapST2RHptST2ptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2LDptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2RDptN0pt.saveScoresToFileStream(bw);

			m_map2STptN0pt.saveScoresToFileStream(bw);
			m_map2STptN_1ptN0pt.saveScoresToFileStream(bw);
			m_map2STptN0ptN1pt.saveScoresToFileStream(bw);
			m_map2STptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_map2STptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_map2STLHpt2STptN0pt.saveScoresToFileStream(bw);
			m_map2STRHpt2STptN0pt.saveScoresToFileStream(bw);
			m_map2STpt2STLDptN0pt.saveScoresToFileStream(bw);
			m_map2STpt2STRDptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0wd0.saveScoresToFileStream(bw);
			m_mapSTptN0ptd0.saveScoresToFileStream(bw);

			m_mapST2wN0wd1.saveScoresToFileStream(bw);
			m_mapST2ptN0ptd1.saveScoresToFileStream(bw);

			m_map2STwN0wd2.saveScoresToFileStream(bw);
			m_map2STptN0ptd2.saveScoresToFileStream(bw);
			
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

			m_map2STwrda.saveScoresToFileStream(bw);
			m_map2STptrda.saveScoresToFileStream(bw);
			m_map2STwlda.saveScoresToFileStream(bw);
			m_map2STptlda.saveScoresToFileStream(bw);
			
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

			m_map2STwrha.saveScoresToFileStream(bw);
			m_map2STptrha.saveScoresToFileStream(bw);
			m_map2STwlha.saveScoresToFileStream(bw);
			m_map2STptlha.saveScoresToFileStream(bw);
			
			m_mapSTwrp.saveScoresToFileStream(bw);
			m_mapSTptrp.saveScoresToFileStream(bw);

			m_mapST2wrp.saveScoresToFileStream(bw);
			m_mapST2ptrp.saveScoresToFileStream(bw);

			m_map2STwrp.saveScoresToFileStream(bw);
			m_map2STptrp.saveScoresToFileStream(bw);
			
			m_mapSTwlp.saveScoresToFileStream(bw);
			m_mapSTptlp.saveScoresToFileStream(bw);
			
			m_mapST2wlp.saveScoresToFileStream(bw);
			m_mapST2ptlp.saveScoresToFileStream(bw);
			
			m_map2STwlp.saveScoresToFileStream(bw);
			m_map2STptlp.saveScoresToFileStream(bw);

			m_mapN0wlp.saveScoresToFileStream(bw);
			m_mapN0ptlp.saveScoresToFileStream(bw);

			m_mapPOSPath.saveScoresToFileStream(bw);
			m_mapFPOSPath.saveScoresToFileStream(bw);
			m_mapSPOSPath.saveScoresToFileStream(bw);
			m_mapSFPOSPath.saveScoresToFileStream(bw);
			m_map2POSPath.saveScoresToFileStream(bw);
			m_map2FPOSPath.saveScoresToFileStream(bw);
			
			m_mapST_1ct.saveScoresToFileStream(bw);
			m_mapST_2ct.saveScoresToFileStream(bw);
			m_mapSTctST2ct.saveScoresToFileStream(bw);
			m_mapSTct2STct.saveScoresToFileStream(bw);
			m_mapN_1ctN_2ct.saveScoresToFileStream(bw);
			m_mapSTctN0w.saveScoresToFileStream(bw);
			m_mapST2ctN0w.saveScoresToFileStream(bw);
			m_map2STctN0w.saveScoresToFileStream(bw);
			
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
		
		m_map2STw.computeAverage(round);
		m_map2STpt.computeAverage(round);
		m_map2STwpt.computeAverage(round);
		m_map2STct.computeAverage(round);

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
		
		m_map2STLHw.computeAverage(round);
		m_map2STLHpt.computeAverage(round);
		m_map2STlhl.computeAverage(round);

		m_mapN0LHw.computeAverage(round);
		m_mapN0LHpt.computeAverage(round);
		m_mapN0lhl.computeAverage(round);

		m_mapSTRHw.computeAverage(round);
		m_mapSTRHpt.computeAverage(round);
		m_mapSTrhl.computeAverage(round);

		m_mapST2RHw.computeAverage(round);
		m_mapST2RHpt.computeAverage(round);
		m_mapST2rhl.computeAverage(round);
		
		m_map2STRHw.computeAverage(round);
		m_map2STRHpt.computeAverage(round);
		m_map2STrhl.computeAverage(round);

		m_mapSTL2Hw.computeAverage(round);
		m_mapSTL2Hpt.computeAverage(round);
		m_mapSTl2hl.computeAverage(round);

		m_mapN0L2Hw.computeAverage(round);
		m_mapN0L2Hpt.computeAverage(round);
		m_mapN0l2hl.computeAverage(round);

		m_mapSTR2Hw.computeAverage(round);
		m_mapSTR2Hpt.computeAverage(round);
		m_mapSTr2hl.computeAverage(round);

		m_mapSTLHLHw.computeAverage(round);
		m_mapSTLHLHpt.computeAverage(round);
		m_mapSTLHlhl.computeAverage(round);

		m_mapSTLHRHw.computeAverage(round);
		m_mapSTLHRHpt.computeAverage(round);
		m_mapSTLHrhl.computeAverage(round);

		m_mapSTRHLHw.computeAverage(round);
		m_mapSTRHLHpt.computeAverage(round);
		m_mapSTRHlhl.computeAverage(round);

		m_mapSTRHRHw.computeAverage(round);
		m_mapSTRHRHpt.computeAverage(round);
		m_mapSTRHrhl.computeAverage(round);

		m_mapSTLDw.computeAverage(round);
		m_mapSTLDpt.computeAverage(round);
		m_mapSTldl.computeAverage(round);

		m_mapST2LDw.computeAverage(round);
		m_mapST2LDpt.computeAverage(round);
		m_mapST2ldl.computeAverage(round);
		
		m_map2STLDw.computeAverage(round);
		m_map2STLDpt.computeAverage(round);
		m_map2STldl.computeAverage(round);
		
		m_mapSTRDw.computeAverage(round);
		m_mapSTRDpt.computeAverage(round);
		m_mapSTrdl.computeAverage(round);
		
		m_mapST2RDw.computeAverage(round);
		m_mapST2RDpt.computeAverage(round);
		m_mapST2rdl.computeAverage(round);
		
		m_map2STRDw.computeAverage(round);
		m_map2STRDpt.computeAverage(round);
		m_map2STrdl.computeAverage(round);

		m_mapN0LDw.computeAverage(round);
		m_mapN0LDpt.computeAverage(round);
		m_mapN0ldl.computeAverage(round);

		m_mapSTL2Dw.computeAverage(round);
		m_mapSTL2Dpt.computeAverage(round);
		m_mapSTl2dl.computeAverage(round);

		m_mapSTR2Dw.computeAverage(round);
		m_mapSTR2Dpt.computeAverage(round);
		m_mapSTr2dl.computeAverage(round);

		m_mapN0L2Dw.computeAverage(round);
		m_mapN0L2Dpt.computeAverage(round);
		m_mapN0l2dl.computeAverage(round);

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
		
		m_map2STwptN0wpt.computeAverage(round);
		m_map2STwptN0w.computeAverage(round);
		m_map2STwN0wpt.computeAverage(round);
		m_map2STptN0wpt.computeAverage(round);
		m_map2STwptN0pt.computeAverage(round);
		m_map2STwN0w.computeAverage(round);

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
		m_mapN0ptN0LDptN0L2Dpt.computeAverage(round);
		m_mapN0ptN0LHptN0L2Hpt.computeAverage(round);
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

		m_mapST2ptN0pt.computeAverage(round);
		m_mapST2ptN_1ptN0pt.computeAverage(round);
		m_mapST2ptN0ptN1pt.computeAverage(round);
		m_mapST2ptN0ptN0LDpt.computeAverage(round);
		m_mapST2ptN0ptN0LHpt.computeAverage(round);
		m_mapST2LHptST2ptN0pt.computeAverage(round);
		m_mapST2RHptST2ptN0pt.computeAverage(round);
		m_mapST2ptST2LDptN0pt.computeAverage(round);
		m_mapST2ptST2RDptN0pt.computeAverage(round);

		m_map2STptN0pt.computeAverage(round);
		m_map2STptN_1ptN0pt.computeAverage(round);
		m_map2STptN0ptN1pt.computeAverage(round);
		m_map2STptN0ptN0LDpt.computeAverage(round);
		m_map2STptN0ptN0LHpt.computeAverage(round);
		m_map2STLHpt2STptN0pt.computeAverage(round);
		m_map2STRHpt2STptN0pt.computeAverage(round);
		m_map2STpt2STLDptN0pt.computeAverage(round);
		m_map2STpt2STRDptN0pt.computeAverage(round);
		
		m_mapSTwN0wd0.computeAverage(round);
		m_mapSTptN0ptd0.computeAverage(round);

		m_mapST2wN0wd1.computeAverage(round);
		m_mapST2ptN0ptd1.computeAverage(round);

		m_map2STwN0wd2.computeAverage(round);
		m_map2STptN0ptd2.computeAverage(round);
		
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

		m_map2STwrda.computeAverage(round);
		m_map2STptrda.computeAverage(round);
		m_map2STwlda.computeAverage(round);
		m_map2STptlda.computeAverage(round);
		
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

		m_map2STwrha.computeAverage(round);
		m_map2STptrha.computeAverage(round);
		m_map2STwlha.computeAverage(round);
		m_map2STptlha.computeAverage(round);
		
		m_mapSTwrp.computeAverage(round);
		m_mapSTptrp.computeAverage(round);

		m_mapST2wrp.computeAverage(round);
		m_mapST2ptrp.computeAverage(round);

		m_map2STwrp.computeAverage(round);
		m_map2STptrp.computeAverage(round);
		
		m_mapSTwlp.computeAverage(round);
		m_mapSTptlp.computeAverage(round);
		
		m_mapST2wlp.computeAverage(round);
		m_mapST2ptlp.computeAverage(round);
		
		m_map2STwlp.computeAverage(round);
		m_map2STptlp.computeAverage(round);

		m_mapN0wlp.computeAverage(round);
		m_mapN0ptlp.computeAverage(round);

		m_mapPOSPath.computeAverage(round);
		m_mapFPOSPath.computeAverage(round);
		m_mapSPOSPath.computeAverage(round);
		m_mapSFPOSPath.computeAverage(round);
		m_map2POSPath.computeAverage(round);
		m_map2FPOSPath.computeAverage(round);
		
		m_mapST_1ct.computeAverage(round);
		m_mapST_2ct.computeAverage(round);
		m_mapSTctST2ct.computeAverage(round);
		m_mapSTct2STct.computeAverage(round);
		m_mapN_1ctN_2ct.computeAverage(round);
		m_mapSTctN0w.computeAverage(round);
		m_mapST2ctN0w.computeAverage(round);
		m_map2STctN0w.computeAverage(round);
		
		System.out.println("done.");
	}
}
