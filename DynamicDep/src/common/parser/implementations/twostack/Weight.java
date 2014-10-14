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
import common.parser.implementations.titov.Macros;

public class Weight extends WeightBase {
	public WordMap m_mapPSTw;
	public WordMap m_mapSSTw;
	public POSTagMap m_mapPSTpt;
	public POSTagMap m_mapSSTpt;
	public WordPOSTagMap m_mapPSTwpt;
	public WordPOSTagMap m_mapSSTwpt;
	public IntMap m_mapPSTct;
	public IntMap m_mapSSTct;
	public WordIntMap m_mapPSTwct;
	public WordIntMap m_mapSSTwct;

	public WordMap m_mapN0w;
	public POSTagMap m_mapN0pt;
	public WordPOSTagMap m_mapN0wpt;

	public WordMap m_mapN1w;
	public POSTagMap m_mapN1pt;
	public WordPOSTagMap m_mapN1wpt;

	public WordMap m_mapN2w;
	public POSTagMap m_mapN2pt;
	public WordPOSTagMap m_mapN2wpt;

	public WordMap m_mapPSTLHw;
	public WordMap m_mapSSTLHw;
	public POSTagMap m_mapPSTLHpt;
	public POSTagMap m_mapSSTLHpt;
	public IntMap m_mapPSTLHct;
	public IntMap m_mapSSTLHct;
	public IntMap m_mapPSTlhl;
	public IntMap m_mapSSTlhl;

	public WordMap m_mapN0LHw;
	public POSTagMap m_mapN0LHpt;
	public IntMap m_mapN0LHct;
	public IntMap m_mapN0lhl;

	public WordMap m_mapPSTRHw;
	public WordMap m_mapSSTRHw;
	public POSTagMap m_mapPSTRHpt;
	public POSTagMap m_mapSSTRHpt;
	public IntMap m_mapPSTRHct;
	public IntMap m_mapSSTRHct;
	public IntMap m_mapPSTrhl;
	public IntMap m_mapSSTrhl;

	public WordMap m_mapPSTL2Hw;
	public WordMap m_mapSSTL2Hw;
	public POSTagMap m_mapPSTL2Hpt;
	public POSTagMap m_mapSSTL2Hpt;
	public IntMap m_mapPSTL2Hct;
	public IntMap m_mapSSTL2Hct;
	public IntMap m_mapPSTl2hl;
	public IntMap m_mapSSTl2hl;

	public WordMap m_mapN0L2Hw;
	public POSTagMap m_mapN0L2Hpt;
	public IntMap m_mapN0L2Hct;
	public IntMap m_mapN0l2hl;

	public WordMap m_mapPSTR2Hw;
	public WordMap m_mapSSTR2Hw;
	public POSTagMap m_mapPSTR2Hpt;
	public POSTagMap m_mapSSTR2Hpt;
	public IntMap m_mapPSTR2Hct;
	public IntMap m_mapSSTR2Hct;
	public IntMap m_mapPSTr2hl;
	public IntMap m_mapSSTr2hl;

	public WordMap m_mapPSTLHLHw;
	public WordMap m_mapSSTLHLHw;
	public POSTagMap m_mapPSTLHLHpt;
	public POSTagMap m_mapSSTLHLHpt;
	public IntMap m_mapPSTLHLHct;
	public IntMap m_mapSSTLHLHct;
	public IntMap m_mapPSTLHlhl;
	public IntMap m_mapSSTLHlhl;

	public WordMap m_mapPSTLHRHw;
	public WordMap m_mapSSTLHRHw;
	public POSTagMap m_mapPSTLHRHpt;
	public POSTagMap m_mapSSTLHRHpt;
	public IntMap m_mapPSTLHRHct;
	public IntMap m_mapSSTLHRHct;
	public IntMap m_mapPSTLHrhl;
	public IntMap m_mapSSTLHrhl;

	public WordMap m_mapPSTRHLHw;
	public WordMap m_mapSSTRHLHw;
	public POSTagMap m_mapPSTRHLHpt;
	public POSTagMap m_mapSSTRHLHpt;
	public IntMap m_mapPSTRHLHct;
	public IntMap m_mapSSTRHLHct;
	public IntMap m_mapPSTRHlhl;
	public IntMap m_mapSSTRHlhl;

	public WordMap m_mapPSTRHRHw;
	public WordMap m_mapSSTRHRHw;
	public POSTagMap m_mapPSTRHRHpt;
	public POSTagMap m_mapSSTRHRHpt;
	public IntMap m_mapPSTRHRHct;
	public IntMap m_mapSSTRHRHct;
	public IntMap m_mapPSTRHrhl;
	public IntMap m_mapSSTRHrhl;

	public WordMap m_mapPSTLDw;
	public WordMap m_mapSSTLDw;
	public POSTagMap m_mapPSTLDpt;
	public POSTagMap m_mapSSTLDpt;
	public IntMap m_mapPSTLDct;
	public IntMap m_mapSSTLDct;
	public IntMap m_mapPSTldl;
	public IntMap m_mapSSTldl;

	public WordMap m_mapPSTRDw;
	public WordMap m_mapSSTRDw;
	public POSTagMap m_mapPSTRDpt;
	public POSTagMap m_mapSSTRDpt;
	public IntMap m_mapPSTRDct;
	public IntMap m_mapSSTRDct;
	public IntMap m_mapPSTrdl;
	public IntMap m_mapSSTrdl;

	public WordMap m_mapN0LDw;
	public POSTagMap m_mapN0LDpt;
	public IntMap m_mapN0LDct;
	public IntMap m_mapN0ldl;

	public WordMap m_mapPSTL2Dw;
	public WordMap m_mapSSTL2Dw;
	public POSTagMap m_mapPSTL2Dpt;
	public POSTagMap m_mapSSTL2Dpt;
	public IntMap m_mapPSTL2Dct;
	public IntMap m_mapSSTL2Dct;
	public IntMap m_mapPSTl2dl;
	public IntMap m_mapSSTl2dl;

	public WordMap m_mapPSTR2Dw;
	public WordMap m_mapSSTR2Dw;
	public POSTagMap m_mapPSTR2Dpt;
	public POSTagMap m_mapSSTR2Dpt;
	public IntMap m_mapPSTR2Dct;
	public IntMap m_mapSSTR2Dct;
	public IntMap m_mapPSTr2dl;
	public IntMap m_mapSSTr2dl;

	public WordMap m_mapN0L2Dw;
	public POSTagMap m_mapN0L2Dpt;
	public IntMap m_mapN0L2Dct;
	public IntMap m_mapN0l2dl;

	public TwoPOSTaggedWordsMap m_mapPSTwptN0wpt;
	public TwoPOSTaggedWordsMap m_mapSSTwptN0wpt;
	public WordWordPOSTagMap m_mapPSTwptN0w;
	public WordWordPOSTagMap m_mapSSTwptN0w;
	public WordWordPOSTagMap m_mapPSTwN0wpt;
	public WordWordPOSTagMap m_mapSSTwN0wpt;
	public WordPOSTagPOSTagMap m_mapPSTptN0wpt;
	public WordPOSTagPOSTagMap m_mapSSTptN0wpt;
	public WordPOSTagPOSTagMap m_mapPSTwptN0pt;
	public WordPOSTagPOSTagMap m_mapSSTwptN0pt;
	public WordWordIntMap m_mapPSTwctN0w;
	public WordWordIntMap m_mapSSTwctN0w;
	public TwoWordsMap m_mapPSTwN0w;
	public TwoWordsMap m_mapSSTwN0w;

	public POSTagSet2Map m_mapPSTptN0pt;
	public POSTagSet2Map m_mapSSTptN0pt;
	public POSTagSet2Map m_mapN0ptN1pt;
	public POSTagSet3Map m_mapN0ptN1ptN2pt;
	public POSTagSet3Map m_mapPSTptN0ptN1pt;
	public POSTagSet3Map m_mapSSTptN0ptN1pt;
	public POSTagSet3Map m_mapPSTptN0ptN0LDpt;
	public POSTagSet3Map m_mapSSTptN0ptN0LDpt;
	public POSTagSet3Map m_mapPSTptN0ptN0LHpt;
	public POSTagSet3Map m_mapSSTptN0ptN0LHpt;
	public POSTagSet3Map m_mapN0ptN0LDptN0L2Dpt;
	public POSTagSet3Map m_mapN0ptN0LHptN0L2Hpt;
	public POSTagSet3Map m_mapPSTLHptSTptN0pt;
	public POSTagSet3Map m_mapSSTLHptSTptN0pt;
	public POSTagSet3Map m_mapPSTRHptSTptN0pt;
	public POSTagSet3Map m_mapSSTRHptSTptN0pt;
	public POSTagSet3Map m_mapPSTLHLHptSTLHptSTpt;
	public POSTagSet3Map m_mapSSTLHLHptSTLHptSTpt;
	public POSTagSet3Map m_mapPSTLHRHptSTLHptSTpt;
	public POSTagSet3Map m_mapSSTLHRHptSTLHptSTpt;
	public POSTagSet3Map m_mapPSTRHLHptSTRHptSTpt;
	public POSTagSet3Map m_mapSSTRHLHptSTRHptSTpt;
	public POSTagSet3Map m_mapPSTRHRHptSTRHptSTpt;
	public POSTagSet3Map m_mapSSTRHRHptSTRHptSTpt;
	public POSTagSet3Map m_mapPSTptSTLDptN0pt;
	public POSTagSet3Map m_mapSSTptSTLDptN0pt;
	public POSTagSet3Map m_mapPSTptSTLDptSTL2Dpt;
	public POSTagSet3Map m_mapSSTptSTLDptSTL2Dpt;
	public POSTagSet3Map m_mapPSTptSTRDptN0pt;
	public POSTagSet3Map m_mapSSTptSTRDptN0pt;
	public POSTagSet3Map m_mapPSTptSTRDptSTR2Dpt;
	public POSTagSet3Map m_mapSSTptSTRDptSTR2Dpt;

	public CCGTagSet3Map m_mapPSTLHLHctSTLHctSTct;
	public CCGTagSet3Map m_mapSSTLHLHctSTLHctSTct;
	public CCGTagSet3Map m_mapPSTLHRHctSTLHctSTct;
	public CCGTagSet3Map m_mapSSTLHRHctSTLHctSTct;
	public CCGTagSet3Map m_mapPSTRHLHctSTRHctSTct;
	public CCGTagSet3Map m_mapSSTRHLHctSTRHctSTct;
	public CCGTagSet3Map m_mapPSTRHRHctSTRHctSTct;
	public CCGTagSet3Map m_mapSSTRHRHctSTRHctSTct;
	public CCGTagSet3Map m_mapPSTctSTLDctSTL2Dct;
	public CCGTagSet3Map m_mapSSTctSTLDctSTL2Dct;
	public CCGTagSet3Map m_mapPSTctSTRDctSTR2Dct;
	public CCGTagSet3Map m_mapSSTctSTRDctSTR2Dct;

	public WordIntMap m_mapPSTwd0;
	public WordIntMap m_mapSSTwd0;
	public POSTagIntMap m_mapPSTptd0;
	public POSTagIntMap m_mapSSTptd0;
	public TwoIntsMap m_mapPSTctd0;
	public TwoIntsMap m_mapSSTctd0;
	public WordIntMap m_mapN0wd0;
	public POSTagIntMap m_mapN0ptd0;
	public WordWordIntMap m_mapPSTwN0wd0;
	public WordWordIntMap m_mapSSTwN0wd0;
	public POSTagPOSTagIntMap m_mapPSTptN0ptd0;
	public POSTagPOSTagIntMap m_mapSSTptN0ptd0;

	public WordIntMap m_mapPSTwrda;
	public WordIntMap m_mapSSTwrda;
	public POSTagIntMap m_mapPSTptrda;
	public POSTagIntMap m_mapSSTptrda;
	public TwoIntsMap m_mapPSTctrda;
	public TwoIntsMap m_mapSSTctrda;
	public WordIntMap m_mapPSTwlda;
	public WordIntMap m_mapSSTwlda;
	public POSTagIntMap m_mapPSTptlda;
	public POSTagIntMap m_mapSSTptlda;
	public TwoIntsMap m_mapPSTctlda;
	public TwoIntsMap m_mapSSTctlda;
	public WordIntMap m_mapN0wlda;
	public POSTagIntMap m_mapN0ptlda;

	public WordIntMap m_mapPSTwrha;
	public WordIntMap m_mapSSTwrha;
	public POSTagIntMap m_mapPSTptrha;
	public POSTagIntMap m_mapSSTptrha;
	public TwoIntsMap m_mapPSTctrha;
	public TwoIntsMap m_mapSSTctrha;
	public WordIntMap m_mapPSTwlha;
	public WordIntMap m_mapSSTwlha;
	public POSTagIntMap m_mapPSTptlha;
	public POSTagIntMap m_mapSSTptlha;
	public TwoIntsMap m_mapPSTctlha;
	public TwoIntsMap m_mapSSTctlha;
	public WordIntMap m_mapN0wlha;
	public POSTagIntMap m_mapN0ptlha;

	public WordSetOfDepLabelsMap m_mapPSTwrp;
	public WordSetOfDepLabelsMap m_mapSSTwrp;
	public POSTagSetOfDepLabelsMap m_mapPSTptrp;
	public POSTagSetOfDepLabelsMap m_mapSSTptrp;
	public SetOfDepLabelsIntMap m_mapPSTctrp;
	public SetOfDepLabelsIntMap m_mapSSTctrp;

	public WordSetOfDepLabelsMap m_mapPSTwlp;
	public WordSetOfDepLabelsMap m_mapSSTwlp;
	public WordSetOfCCGLabelsMap m_mapPSTwlc;
	public WordSetOfCCGLabelsMap m_mapSSTwlc;
	public POSTagSetOfDepLabelsMap m_mapPSTptlp;
	public POSTagSetOfDepLabelsMap m_mapSSTptlp;
	public POSTagSetOfCCGLabelsMap m_mapPSTptlc;
	public POSTagSetOfCCGLabelsMap m_mapSSTptlc;
	public SetOfDepLabelsIntMap m_mapPSTctlp;
	public SetOfDepLabelsIntMap m_mapSSTctlp;
	public SetOfCCGLabelsIntMap m_mapPSTctlc;
	public SetOfCCGLabelsIntMap m_mapSSTctlc;

	public WordSetOfDepLabelsMap m_mapN0wlp;
	public WordSetOfCCGLabelsMap m_mapN0wlc;
	public POSTagSetOfDepLabelsMap m_mapN0ptlp;
	public POSTagSetOfCCGLabelsMap m_mapN0ptlc;

	public SyntaxTreePathMap m_mapPSTP;
	public SyntaxTreePathMap m_mapSSTP;

	public Weight(final String sPath, final boolean bTrain) {
		super(sPath, bTrain);

		m_mapPSTw = new WordMap("a");
		m_mapSSTw = new WordMap("a");
		m_mapPSTpt = new POSTagMap("a");
		m_mapSSTpt = new POSTagMap("a");
		m_mapPSTwpt = new WordPOSTagMap("a");
		m_mapSSTwpt = new WordPOSTagMap("a");
		m_mapPSTct = new IntMap("a");
		m_mapSSTct = new IntMap("a");
		m_mapPSTwct = new WordIntMap("a");
		m_mapSSTwct = new WordIntMap("a");

		m_mapN0w = new WordMap("a");
		m_mapN0pt = new POSTagMap("a");
		m_mapN0wpt = new WordPOSTagMap("a");

		m_mapN1w = new WordMap("a");
		m_mapN1pt = new POSTagMap("a");
		m_mapN1wpt = new WordPOSTagMap("a");

		m_mapN2w = new WordMap("a");
		m_mapN2pt = new POSTagMap("a");
		m_mapN2wpt = new WordPOSTagMap("a");

		m_mapPSTLHw = new WordMap("a");
		m_mapSSTLHw = new WordMap("a");
		m_mapPSTLHpt = new POSTagMap("a");
		m_mapSSTLHpt = new POSTagMap("a");
		m_mapPSTLHct = new IntMap("a");
		m_mapSSTLHct = new IntMap("a");
		m_mapPSTlhl = new IntMap("a");
		m_mapSSTlhl = new IntMap("a");

		m_mapN0LHw = new WordMap("a");
		m_mapN0LHpt = new POSTagMap("a");
		m_mapN0LHct = new IntMap("a");
		m_mapN0lhl = new IntMap("a");

		m_mapPSTRHw = new WordMap("a");
		m_mapSSTRHw = new WordMap("a");
		m_mapPSTRHpt = new POSTagMap("a");
		m_mapSSTRHpt = new POSTagMap("a");
		m_mapPSTRHct = new IntMap("a");
		m_mapSSTRHct = new IntMap("a");
		m_mapPSTrhl = new IntMap("a");
		m_mapSSTrhl = new IntMap("a");

		m_mapPSTL2Hw = new WordMap("a");
		m_mapSSTL2Hw = new WordMap("a");
		m_mapPSTL2Hpt = new POSTagMap("a");
		m_mapSSTL2Hpt = new POSTagMap("a");
		m_mapPSTL2Hct = new IntMap("a");
		m_mapSSTL2Hct = new IntMap("a");
		m_mapPSTl2hl = new IntMap("a");
		m_mapSSTl2hl = new IntMap("a");

		m_mapN0L2Hw = new WordMap("a");
		m_mapN0L2Hpt = new POSTagMap("a");
		m_mapN0L2Hct = new IntMap("a");
		m_mapN0l2hl = new IntMap("a");

		m_mapPSTR2Hw = new WordMap("a");
		m_mapSSTR2Hw = new WordMap("a");
		m_mapPSTR2Hpt = new POSTagMap("a");
		m_mapSSTR2Hpt = new POSTagMap("a");
		m_mapPSTR2Hct = new IntMap("a");
		m_mapSSTR2Hct = new IntMap("a");
		m_mapPSTr2hl = new IntMap("a");
		m_mapSSTr2hl = new IntMap("a");

		m_mapPSTLHLHw = new WordMap("a");
		m_mapSSTLHLHw = new WordMap("a");
		m_mapPSTLHLHpt = new POSTagMap("a");
		m_mapSSTLHLHpt = new POSTagMap("a");
		m_mapPSTLHLHct = new IntMap("a");
		m_mapSSTLHLHct = new IntMap("a");
		m_mapPSTLHlhl = new IntMap("a");
		m_mapSSTLHlhl = new IntMap("a");

		m_mapPSTLHRHw = new WordMap("a");
		m_mapSSTLHRHw = new WordMap("a");
		m_mapPSTLHRHpt = new POSTagMap("a");
		m_mapSSTLHRHpt = new POSTagMap("a");
		m_mapPSTLHRHct = new IntMap("a");
		m_mapSSTLHRHct = new IntMap("a");
		m_mapPSTLHrhl = new IntMap("a");
		m_mapSSTLHrhl = new IntMap("a");

		m_mapPSTRHLHw = new WordMap("a");
		m_mapSSTRHLHw = new WordMap("a");
		m_mapPSTRHLHpt = new POSTagMap("a");
		m_mapSSTRHLHpt = new POSTagMap("a");
		m_mapPSTRHLHct = new IntMap("a");
		m_mapSSTRHLHct = new IntMap("a");
		m_mapPSTRHlhl = new IntMap("a");
		m_mapSSTRHlhl = new IntMap("a");

		m_mapPSTRHRHw = new WordMap("a");
		m_mapSSTRHRHw = new WordMap("a");
		m_mapPSTRHRHpt = new POSTagMap("a");
		m_mapSSTRHRHpt = new POSTagMap("a");
		m_mapPSTRHRHct = new IntMap("a");
		m_mapSSTRHRHct = new IntMap("a");
		m_mapPSTRHrhl = new IntMap("a");
		m_mapSSTRHrhl = new IntMap("a");

		m_mapPSTLDw = new WordMap("a");
		m_mapSSTLDw = new WordMap("a");
		m_mapPSTLDpt = new POSTagMap("a");
		m_mapSSTLDpt = new POSTagMap("a");
		m_mapPSTLDct = new IntMap("a");
		m_mapSSTLDct = new IntMap("a");
		m_mapPSTldl = new IntMap("a");
		m_mapSSTldl = new IntMap("a");

		m_mapPSTRDw = new WordMap("a");
		m_mapSSTRDw = new WordMap("a");
		m_mapPSTRDpt = new POSTagMap("a");
		m_mapSSTRDpt = new POSTagMap("a");
		m_mapPSTRDct = new IntMap("a");
		m_mapSSTRDct = new IntMap("a");
		m_mapPSTrdl = new IntMap("a");
		m_mapSSTrdl = new IntMap("a");

		m_mapN0LDw = new WordMap("a");
		m_mapN0LDpt = new POSTagMap("a");
		m_mapN0LDct = new IntMap("a");
		m_mapN0ldl = new IntMap("a");

		m_mapPSTL2Dw = new WordMap("a");
		m_mapSSTL2Dw = new WordMap("a");
		m_mapPSTL2Dpt = new POSTagMap("a");
		m_mapSSTL2Dpt = new POSTagMap("a");
		m_mapPSTL2Dct = new IntMap("a");
		m_mapSSTL2Dct = new IntMap("a");
		m_mapPSTl2dl = new IntMap("a");
		m_mapSSTl2dl = new IntMap("a");

		m_mapPSTR2Dw = new WordMap("a");
		m_mapSSTR2Dw = new WordMap("a");
		m_mapPSTR2Dpt = new POSTagMap("a");
		m_mapSSTR2Dpt = new POSTagMap("a");
		m_mapPSTR2Dct = new IntMap("a");
		m_mapSSTR2Dct = new IntMap("a");
		m_mapPSTr2dl = new IntMap("a");
		m_mapSSTr2dl = new IntMap("a");

		m_mapN0L2Dw = new WordMap("a");
		m_mapN0L2Dpt = new POSTagMap("a");
		m_mapN0L2Dct = new IntMap("a");
		m_mapN0l2dl = new IntMap("a");

		m_mapPSTwptN0wpt = new TwoPOSTaggedWordsMap("a");
		m_mapSSTwptN0wpt = new TwoPOSTaggedWordsMap("a");
		m_mapPSTwptN0w = new WordWordPOSTagMap("a");
		m_mapSSTwptN0w = new WordWordPOSTagMap("a");
		m_mapPSTwN0wpt = new WordWordPOSTagMap("a");
		m_mapSSTwN0wpt = new WordWordPOSTagMap("a");
		m_mapPSTptN0wpt = new WordPOSTagPOSTagMap("a");
		m_mapSSTptN0wpt = new WordPOSTagPOSTagMap("a");
		m_mapPSTwptN0pt = new WordPOSTagPOSTagMap("a");
		m_mapSSTwptN0pt = new WordPOSTagPOSTagMap("a");
		m_mapPSTwctN0w = new WordWordIntMap("a");
		m_mapSSTwctN0w = new WordWordIntMap("a");
		m_mapPSTwN0w = new TwoWordsMap("a");
		m_mapSSTwN0w = new TwoWordsMap("a");

		m_mapPSTptN0pt = new POSTagSet2Map("a");
		m_mapSSTptN0pt = new POSTagSet2Map("a");
		m_mapN0ptN1pt = new POSTagSet2Map("a");
		m_mapN0ptN1ptN2pt = new POSTagSet3Map("a");
		m_mapPSTptN0ptN1pt = new POSTagSet3Map("a");
		m_mapSSTptN0ptN1pt = new POSTagSet3Map("a");
		m_mapPSTptN0ptN0LDpt = new POSTagSet3Map("a");
		m_mapSSTptN0ptN0LDpt = new POSTagSet3Map("a");
		m_mapPSTptN0ptN0LHpt = new POSTagSet3Map("a");
		m_mapSSTptN0ptN0LHpt = new POSTagSet3Map("a");
		m_mapN0ptN0LDptN0L2Dpt = new POSTagSet3Map("a");
		m_mapN0ptN0LHptN0L2Hpt = new POSTagSet3Map("a");
		m_mapPSTLHptSTptN0pt = new POSTagSet3Map("a");
		m_mapSSTLHptSTptN0pt = new POSTagSet3Map("a");
		m_mapPSTRHptSTptN0pt = new POSTagSet3Map("a");
		m_mapSSTRHptSTptN0pt = new POSTagSet3Map("a");
		m_mapPSTLHLHptSTLHptSTpt = new POSTagSet3Map("a");
		m_mapSSTLHLHptSTLHptSTpt = new POSTagSet3Map("a");
		m_mapPSTLHRHptSTLHptSTpt = new POSTagSet3Map("a");
		m_mapSSTLHRHptSTLHptSTpt = new POSTagSet3Map("a");
		m_mapPSTRHLHptSTRHptSTpt = new POSTagSet3Map("a");
		m_mapSSTRHLHptSTRHptSTpt = new POSTagSet3Map("a");
		m_mapPSTRHRHptSTRHptSTpt = new POSTagSet3Map("a");
		m_mapSSTRHRHptSTRHptSTpt = new POSTagSet3Map("a");
		m_mapPSTptSTLDptN0pt = new POSTagSet3Map("a");
		m_mapSSTptSTLDptN0pt = new POSTagSet3Map("a");
		m_mapPSTptSTLDptSTL2Dpt = new POSTagSet3Map("a");
		m_mapSSTptSTLDptSTL2Dpt = new POSTagSet3Map("a");
		m_mapPSTptSTRDptN0pt = new POSTagSet3Map("a");
		m_mapSSTptSTRDptN0pt = new POSTagSet3Map("a");
		m_mapPSTptSTRDptSTR2Dpt = new POSTagSet3Map("a");
		m_mapSSTptSTRDptSTR2Dpt = new POSTagSet3Map("a");

		m_mapPSTLHLHctSTLHctSTct = new CCGTagSet3Map("a");
		m_mapSSTLHLHctSTLHctSTct = new CCGTagSet3Map("a");
		m_mapPSTLHRHctSTLHctSTct = new CCGTagSet3Map("a");
		m_mapSSTLHRHctSTLHctSTct = new CCGTagSet3Map("a");
		m_mapPSTRHLHctSTRHctSTct = new CCGTagSet3Map("a");
		m_mapSSTRHLHctSTRHctSTct = new CCGTagSet3Map("a");
		m_mapPSTRHRHctSTRHctSTct = new CCGTagSet3Map("a");
		m_mapSSTRHRHctSTRHctSTct = new CCGTagSet3Map("a");
		m_mapPSTctSTLDctSTL2Dct = new CCGTagSet3Map("a");
		m_mapSSTctSTLDctSTL2Dct = new CCGTagSet3Map("a");
		m_mapPSTctSTRDctSTR2Dct = new CCGTagSet3Map("a");
		m_mapSSTctSTRDctSTR2Dct = new CCGTagSet3Map("a");

		m_mapPSTwd0 = new WordIntMap("a");
		m_mapSSTwd0 = new WordIntMap("a");
		m_mapPSTptd0 = new POSTagIntMap("a");
		m_mapSSTptd0 = new POSTagIntMap("a");
		m_mapPSTctd0 = new TwoIntsMap("a");
		m_mapSSTctd0 = new TwoIntsMap("a");
		m_mapN0wd0 = new WordIntMap("a");
		m_mapN0ptd0 = new POSTagIntMap("a");
		m_mapPSTwN0wd0 = new WordWordIntMap("a");
		m_mapSSTwN0wd0 = new WordWordIntMap("a");
		m_mapPSTptN0ptd0 = new POSTagPOSTagIntMap("a");
		m_mapSSTptN0ptd0 = new POSTagPOSTagIntMap("a");

		m_mapPSTwrda = new WordIntMap("a");
		m_mapSSTwrda = new WordIntMap("a");
		m_mapPSTptrda = new POSTagIntMap("a");
		m_mapSSTptrda = new POSTagIntMap("a");
		m_mapPSTctrda = new TwoIntsMap("a");
		m_mapSSTctrda = new TwoIntsMap("a");
		m_mapPSTwlda = new WordIntMap("a");
		m_mapSSTwlda = new WordIntMap("a");
		m_mapPSTptlda = new POSTagIntMap("a");
		m_mapSSTptlda = new POSTagIntMap("a");
		m_mapPSTctlda = new TwoIntsMap("a");
		m_mapSSTctlda = new TwoIntsMap("a");
		m_mapN0wlda = new WordIntMap("a");
		m_mapN0ptlda = new POSTagIntMap("a");

		m_mapPSTwrha = new WordIntMap("a");
		m_mapSSTwrha = new WordIntMap("a");
		m_mapPSTptrha = new POSTagIntMap("a");
		m_mapSSTptrha = new POSTagIntMap("a");
		m_mapPSTctrha = new TwoIntsMap("a");
		m_mapSSTctrha = new TwoIntsMap("a");
		m_mapPSTwlha = new WordIntMap("a");
		m_mapSSTwlha = new WordIntMap("a");
		m_mapPSTptlha = new POSTagIntMap("a");
		m_mapSSTptlha = new POSTagIntMap("a");
		m_mapPSTctlha = new TwoIntsMap("a");
		m_mapSSTctlha = new TwoIntsMap("a");
		m_mapN0wlha = new WordIntMap("a");
		m_mapN0ptlha = new POSTagIntMap("a");

		m_mapPSTwrp = new WordSetOfDepLabelsMap("a");
		m_mapSSTwrp = new WordSetOfDepLabelsMap("a");
		m_mapPSTptrp = new POSTagSetOfDepLabelsMap("a");
		m_mapSSTptrp = new POSTagSetOfDepLabelsMap("a");
		m_mapPSTctrp = new SetOfDepLabelsIntMap("a");
		m_mapSSTctrp = new SetOfDepLabelsIntMap("a");

		m_mapPSTwlp = new WordSetOfDepLabelsMap("a");
		m_mapSSTwlp = new WordSetOfDepLabelsMap("a");
		m_mapPSTwlc = new WordSetOfCCGLabelsMap("a");
		m_mapSSTwlc = new WordSetOfCCGLabelsMap("a");
		m_mapPSTptlp = new POSTagSetOfDepLabelsMap("a");
		m_mapSSTptlp = new POSTagSetOfDepLabelsMap("a");
		m_mapPSTptlc = new POSTagSetOfCCGLabelsMap("a");
		m_mapSSTptlc = new POSTagSetOfCCGLabelsMap("a");
		m_mapPSTctlp = new SetOfDepLabelsIntMap("a");
		m_mapSSTctlp = new SetOfDepLabelsIntMap("a");
		m_mapPSTctlc = new SetOfCCGLabelsIntMap("a");
		m_mapSSTctlc = new SetOfCCGLabelsIntMap("a");

		m_mapN0wlp = new WordSetOfDepLabelsMap("a");
		m_mapN0wlc = new WordSetOfCCGLabelsMap("a");
		m_mapN0ptlp = new POSTagSetOfDepLabelsMap("a");
		m_mapN0ptlc = new POSTagSetOfCCGLabelsMap("a");

		m_mapPSTP = new SyntaxTreePathMap("a");
		m_mapSSTP = new SyntaxTreePathMap("a");
		
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

			m_mapPSTw.loadScoresFromFileStream(br);
			m_mapSSTw.loadScoresFromFileStream(br);
			m_mapPSTpt.loadScoresFromFileStream(br);
			m_mapSSTpt.loadScoresFromFileStream(br);
			m_mapPSTwpt.loadScoresFromFileStream(br);
			m_mapSSTwpt.loadScoresFromFileStream(br);
			m_mapPSTct.loadScoresFromFileStream(br);
			m_mapSSTct.loadScoresFromFileStream(br);
			m_mapPSTwct.loadScoresFromFileStream(br);
			m_mapSSTwct.loadScoresFromFileStream(br);

			m_mapN0w.loadScoresFromFileStream(br);
			m_mapN0pt.loadScoresFromFileStream(br);
			m_mapN0wpt.loadScoresFromFileStream(br);

			m_mapN1w.loadScoresFromFileStream(br);
			m_mapN1pt.loadScoresFromFileStream(br);
			m_mapN1wpt.loadScoresFromFileStream(br);

			m_mapN2w.loadScoresFromFileStream(br);
			m_mapN2pt.loadScoresFromFileStream(br);
			m_mapN2wpt.loadScoresFromFileStream(br);

			m_mapPSTLHw.loadScoresFromFileStream(br);
			m_mapSSTLHw.loadScoresFromFileStream(br);
			m_mapPSTLHpt.loadScoresFromFileStream(br);
			m_mapSSTLHpt.loadScoresFromFileStream(br);
			m_mapPSTLHct.loadScoresFromFileStream(br);
			m_mapSSTLHct.loadScoresFromFileStream(br);
			m_mapPSTlhl.loadScoresFromFileStream(br);
			m_mapSSTlhl.loadScoresFromFileStream(br);

			m_mapN0LHw.loadScoresFromFileStream(br);
			m_mapN0LHpt.loadScoresFromFileStream(br);
			m_mapN0LHct.loadScoresFromFileStream(br);
			m_mapN0lhl.loadScoresFromFileStream(br);

			m_mapPSTRHw.loadScoresFromFileStream(br);
			m_mapSSTRHw.loadScoresFromFileStream(br);
			m_mapPSTRHpt.loadScoresFromFileStream(br);
			m_mapSSTRHpt.loadScoresFromFileStream(br);
			m_mapPSTRHct.loadScoresFromFileStream(br);
			m_mapSSTRHct.loadScoresFromFileStream(br);
			m_mapPSTrhl.loadScoresFromFileStream(br);
			m_mapSSTrhl.loadScoresFromFileStream(br);

			m_mapPSTL2Hw.loadScoresFromFileStream(br);
			m_mapSSTL2Hw.loadScoresFromFileStream(br);
			m_mapPSTL2Hpt.loadScoresFromFileStream(br);
			m_mapSSTL2Hpt.loadScoresFromFileStream(br);
			m_mapPSTL2Hct.loadScoresFromFileStream(br);
			m_mapSSTL2Hct.loadScoresFromFileStream(br);
			m_mapPSTl2hl.loadScoresFromFileStream(br);
			m_mapSSTl2hl.loadScoresFromFileStream(br);

			m_mapN0L2Hw.loadScoresFromFileStream(br);
			m_mapN0L2Hpt.loadScoresFromFileStream(br);
			m_mapN0L2Hct.loadScoresFromFileStream(br);
			m_mapN0l2hl.loadScoresFromFileStream(br);

			m_mapPSTR2Hw.loadScoresFromFileStream(br);
			m_mapSSTR2Hw.loadScoresFromFileStream(br);
			m_mapPSTR2Hpt.loadScoresFromFileStream(br);
			m_mapSSTR2Hpt.loadScoresFromFileStream(br);
			m_mapPSTR2Hct.loadScoresFromFileStream(br);
			m_mapSSTR2Hct.loadScoresFromFileStream(br);
			m_mapPSTr2hl.loadScoresFromFileStream(br);
			m_mapSSTr2hl.loadScoresFromFileStream(br);

			m_mapPSTLHLHw.loadScoresFromFileStream(br);
			m_mapSSTLHLHw.loadScoresFromFileStream(br);
			m_mapPSTLHLHpt.loadScoresFromFileStream(br);
			m_mapSSTLHLHpt.loadScoresFromFileStream(br);
			m_mapPSTLHLHct.loadScoresFromFileStream(br);
			m_mapSSTLHLHct.loadScoresFromFileStream(br);
			m_mapPSTLHlhl.loadScoresFromFileStream(br);
			m_mapSSTLHlhl.loadScoresFromFileStream(br);

			m_mapPSTLHRHw.loadScoresFromFileStream(br);
			m_mapSSTLHRHw.loadScoresFromFileStream(br);
			m_mapPSTLHRHpt.loadScoresFromFileStream(br);
			m_mapSSTLHRHpt.loadScoresFromFileStream(br);
			m_mapPSTLHRHct.loadScoresFromFileStream(br);
			m_mapSSTLHRHct.loadScoresFromFileStream(br);
			m_mapPSTLHrhl.loadScoresFromFileStream(br);
			m_mapSSTLHrhl.loadScoresFromFileStream(br);

			m_mapPSTRHLHw.loadScoresFromFileStream(br);
			m_mapSSTRHLHw.loadScoresFromFileStream(br);
			m_mapPSTRHLHpt.loadScoresFromFileStream(br);
			m_mapSSTRHLHpt.loadScoresFromFileStream(br);
			m_mapPSTRHLHct.loadScoresFromFileStream(br);
			m_mapSSTRHLHct.loadScoresFromFileStream(br);
			m_mapPSTRHlhl.loadScoresFromFileStream(br);
			m_mapSSTRHlhl.loadScoresFromFileStream(br);

			m_mapPSTRHRHw.loadScoresFromFileStream(br);
			m_mapSSTRHRHw.loadScoresFromFileStream(br);
			m_mapPSTRHRHpt.loadScoresFromFileStream(br);
			m_mapSSTRHRHpt.loadScoresFromFileStream(br);
			m_mapPSTRHRHct.loadScoresFromFileStream(br);
			m_mapSSTRHRHct.loadScoresFromFileStream(br);
			m_mapPSTRHrhl.loadScoresFromFileStream(br);
			m_mapSSTRHrhl.loadScoresFromFileStream(br);

			m_mapPSTLDw.loadScoresFromFileStream(br);
			m_mapSSTLDw.loadScoresFromFileStream(br);
			m_mapPSTLDpt.loadScoresFromFileStream(br);
			m_mapSSTLDpt.loadScoresFromFileStream(br);
			m_mapPSTLDct.loadScoresFromFileStream(br);
			m_mapSSTLDct.loadScoresFromFileStream(br);
			m_mapPSTldl.loadScoresFromFileStream(br);
			m_mapSSTldl.loadScoresFromFileStream(br);

			m_mapPSTRDw.loadScoresFromFileStream(br);
			m_mapSSTRDw.loadScoresFromFileStream(br);
			m_mapPSTRDpt.loadScoresFromFileStream(br);
			m_mapSSTRDpt.loadScoresFromFileStream(br);
			m_mapPSTRDct.loadScoresFromFileStream(br);
			m_mapSSTRDct.loadScoresFromFileStream(br);
			m_mapPSTrdl.loadScoresFromFileStream(br);
			m_mapSSTrdl.loadScoresFromFileStream(br);

			m_mapN0LDw.loadScoresFromFileStream(br);
			m_mapN0LDpt.loadScoresFromFileStream(br);
			m_mapN0LDct.loadScoresFromFileStream(br);
			m_mapN0ldl.loadScoresFromFileStream(br);

			m_mapPSTL2Dw.loadScoresFromFileStream(br);
			m_mapSSTL2Dw.loadScoresFromFileStream(br);
			m_mapPSTL2Dpt.loadScoresFromFileStream(br);
			m_mapSSTL2Dpt.loadScoresFromFileStream(br);
			m_mapPSTL2Dct.loadScoresFromFileStream(br);
			m_mapSSTL2Dct.loadScoresFromFileStream(br);
			m_mapPSTl2dl.loadScoresFromFileStream(br);
			m_mapSSTl2dl.loadScoresFromFileStream(br);

			m_mapPSTR2Dw.loadScoresFromFileStream(br);
			m_mapSSTR2Dw.loadScoresFromFileStream(br);
			m_mapPSTR2Dpt.loadScoresFromFileStream(br);
			m_mapSSTR2Dpt.loadScoresFromFileStream(br);
			m_mapPSTR2Dct.loadScoresFromFileStream(br);
			m_mapSSTR2Dct.loadScoresFromFileStream(br);
			m_mapPSTr2dl.loadScoresFromFileStream(br);
			m_mapSSTr2dl.loadScoresFromFileStream(br);

			m_mapN0L2Dw.loadScoresFromFileStream(br);
			m_mapN0L2Dpt.loadScoresFromFileStream(br);
			m_mapN0L2Dct.loadScoresFromFileStream(br);
			m_mapN0l2dl.loadScoresFromFileStream(br);

			m_mapPSTwptN0wpt.loadScoresFromFileStream(br);
			m_mapSSTwptN0wpt.loadScoresFromFileStream(br);
			m_mapPSTwptN0w.loadScoresFromFileStream(br);
			m_mapSSTwptN0w.loadScoresFromFileStream(br);
			m_mapPSTwN0wpt.loadScoresFromFileStream(br);
			m_mapSSTwN0wpt.loadScoresFromFileStream(br);
			m_mapPSTptN0wpt.loadScoresFromFileStream(br);
			m_mapSSTptN0wpt.loadScoresFromFileStream(br);
			m_mapPSTwptN0pt.loadScoresFromFileStream(br);
			m_mapSSTwptN0pt.loadScoresFromFileStream(br);
			m_mapPSTwctN0w.loadScoresFromFileStream(br);
			m_mapSSTwctN0w.loadScoresFromFileStream(br);
			m_mapPSTwN0w.loadScoresFromFileStream(br);
			m_mapSSTwN0w.loadScoresFromFileStream(br);

			m_mapPSTptN0pt.loadScoresFromFileStream(br);
			m_mapSSTptN0pt.loadScoresFromFileStream(br);
			m_mapN0ptN1pt.loadScoresFromFileStream(br);
			m_mapN0ptN1ptN2pt.loadScoresFromFileStream(br);
			m_mapPSTptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapSSTptN0ptN1pt.loadScoresFromFileStream(br);
			m_mapPSTptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapSSTptN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapPSTptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapSSTptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapN0ptN0LDptN0L2Dpt.loadScoresFromFileStream(br);
			m_mapN0ptN0LHptN0L2Hpt.loadScoresFromFileStream(br);
			m_mapPSTLHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapSSTLHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapPSTRHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapSSTRHptSTptN0pt.loadScoresFromFileStream(br);
			m_mapPSTLHLHptSTLHptSTpt.loadScoresFromFileStream(br);
			m_mapSSTLHLHptSTLHptSTpt.loadScoresFromFileStream(br);
			m_mapPSTLHRHptSTLHptSTpt.loadScoresFromFileStream(br);
			m_mapSSTLHRHptSTLHptSTpt.loadScoresFromFileStream(br);
			m_mapPSTRHLHptSTRHptSTpt.loadScoresFromFileStream(br);
			m_mapSSTRHLHptSTRHptSTpt.loadScoresFromFileStream(br);
			m_mapPSTRHRHptSTRHptSTpt.loadScoresFromFileStream(br);
			m_mapSSTRHRHptSTRHptSTpt.loadScoresFromFileStream(br);
			m_mapPSTptSTLDptN0pt.loadScoresFromFileStream(br);
			m_mapSSTptSTLDptN0pt.loadScoresFromFileStream(br);
			m_mapPSTptSTLDptSTL2Dpt.loadScoresFromFileStream(br);
			m_mapSSTptSTLDptSTL2Dpt.loadScoresFromFileStream(br);
			m_mapPSTptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSSTptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapPSTptSTRDptSTR2Dpt.loadScoresFromFileStream(br);
			m_mapSSTptSTRDptSTR2Dpt.loadScoresFromFileStream(br);

			m_mapPSTLHLHctSTLHctSTct.loadScoresFromFileStream(br);
			m_mapSSTLHLHctSTLHctSTct.loadScoresFromFileStream(br);
			m_mapPSTLHRHctSTLHctSTct.loadScoresFromFileStream(br);
			m_mapSSTLHRHctSTLHctSTct.loadScoresFromFileStream(br);
			m_mapPSTRHLHctSTRHctSTct.loadScoresFromFileStream(br);
			m_mapSSTRHLHctSTRHctSTct.loadScoresFromFileStream(br);
			m_mapPSTRHRHctSTRHctSTct.loadScoresFromFileStream(br);
			m_mapSSTRHRHctSTRHctSTct.loadScoresFromFileStream(br);
			m_mapPSTctSTLDctSTL2Dct.loadScoresFromFileStream(br);
			m_mapSSTctSTLDctSTL2Dct.loadScoresFromFileStream(br);
			m_mapPSTctSTRDctSTR2Dct.loadScoresFromFileStream(br);
			m_mapSSTctSTRDctSTR2Dct.loadScoresFromFileStream(br);

			m_mapPSTwd0.loadScoresFromFileStream(br);
			m_mapSSTwd0.loadScoresFromFileStream(br);
			m_mapPSTptd0.loadScoresFromFileStream(br);
			m_mapSSTptd0.loadScoresFromFileStream(br);
			m_mapPSTctd0.loadScoresFromFileStream(br);
			m_mapSSTctd0.loadScoresFromFileStream(br);
			m_mapN0wd0.loadScoresFromFileStream(br);
			m_mapN0ptd0.loadScoresFromFileStream(br);
			m_mapPSTwN0wd0.loadScoresFromFileStream(br);
			m_mapSSTwN0wd0.loadScoresFromFileStream(br);
			m_mapPSTptN0ptd0.loadScoresFromFileStream(br);
			m_mapSSTptN0ptd0.loadScoresFromFileStream(br);

			m_mapPSTwrda.loadScoresFromFileStream(br);
			m_mapSSTwrda.loadScoresFromFileStream(br);
			m_mapPSTptrda.loadScoresFromFileStream(br);
			m_mapSSTptrda.loadScoresFromFileStream(br);
			m_mapPSTctrda.loadScoresFromFileStream(br);
			m_mapSSTctrda.loadScoresFromFileStream(br);
			m_mapPSTwlda.loadScoresFromFileStream(br);
			m_mapSSTwlda.loadScoresFromFileStream(br);
			m_mapPSTptlda.loadScoresFromFileStream(br);
			m_mapSSTptlda.loadScoresFromFileStream(br);
			m_mapPSTctlda.loadScoresFromFileStream(br);
			m_mapSSTctlda.loadScoresFromFileStream(br);
			m_mapN0wlda.loadScoresFromFileStream(br);
			m_mapN0ptlda.loadScoresFromFileStream(br);

			m_mapPSTwrha.loadScoresFromFileStream(br);
			m_mapSSTwrha.loadScoresFromFileStream(br);
			m_mapPSTptrha.loadScoresFromFileStream(br);
			m_mapSSTptrha.loadScoresFromFileStream(br);
			m_mapPSTctrha.loadScoresFromFileStream(br);
			m_mapSSTctrha.loadScoresFromFileStream(br);
			m_mapPSTwlha.loadScoresFromFileStream(br);
			m_mapSSTwlha.loadScoresFromFileStream(br);
			m_mapPSTptlha.loadScoresFromFileStream(br);
			m_mapSSTptlha.loadScoresFromFileStream(br);
			m_mapPSTctlha.loadScoresFromFileStream(br);
			m_mapSSTctlha.loadScoresFromFileStream(br);
			m_mapN0wlha.loadScoresFromFileStream(br);
			m_mapN0ptlha.loadScoresFromFileStream(br);

			m_mapPSTwrp.loadScoresFromFileStream(br);
			m_mapSSTwrp.loadScoresFromFileStream(br);
			m_mapPSTptrp.loadScoresFromFileStream(br);
			m_mapSSTptrp.loadScoresFromFileStream(br);
			m_mapPSTctrp.loadScoresFromFileStream(br);
			m_mapSSTctrp.loadScoresFromFileStream(br);

			m_mapPSTwlp.loadScoresFromFileStream(br);
			m_mapSSTwlp.loadScoresFromFileStream(br);
			m_mapPSTwlc.loadScoresFromFileStream(br);
			m_mapSSTwlc.loadScoresFromFileStream(br);
			m_mapPSTptlp.loadScoresFromFileStream(br);
			m_mapSSTptlp.loadScoresFromFileStream(br);
			m_mapPSTptlc.loadScoresFromFileStream(br);
			m_mapSSTptlc.loadScoresFromFileStream(br);
			m_mapPSTctlp.loadScoresFromFileStream(br);
			m_mapSSTctlp.loadScoresFromFileStream(br);
			m_mapPSTctlc.loadScoresFromFileStream(br);
			m_mapSSTctlc.loadScoresFromFileStream(br);

			m_mapN0wlp.loadScoresFromFileStream(br);
			m_mapN0wlc.loadScoresFromFileStream(br);
			m_mapN0ptlp.loadScoresFromFileStream(br);
			m_mapN0ptlc.loadScoresFromFileStream(br);

			m_mapPSTP.loadScoresFromFileStream(br);
			m_mapSSTP.loadScoresFromFileStream(br);

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

			m_mapPSTw.saveScoresToFileStream(bw);
			m_mapSSTw.saveScoresToFileStream(bw);
			m_mapPSTpt.saveScoresToFileStream(bw);
			m_mapSSTpt.saveScoresToFileStream(bw);
			m_mapPSTwpt.saveScoresToFileStream(bw);
			m_mapSSTwpt.saveScoresToFileStream(bw);
			m_mapPSTct.saveScoresToFileStream(bw);
			m_mapSSTct.saveScoresToFileStream(bw);
			m_mapPSTwct.saveScoresToFileStream(bw);
			m_mapSSTwct.saveScoresToFileStream(bw);

			m_mapN0w.saveScoresToFileStream(bw);
			m_mapN0pt.saveScoresToFileStream(bw);
			m_mapN0wpt.saveScoresToFileStream(bw);

			m_mapN1w.saveScoresToFileStream(bw);
			m_mapN1pt.saveScoresToFileStream(bw);
			m_mapN1wpt.saveScoresToFileStream(bw);

			m_mapN2w.saveScoresToFileStream(bw);
			m_mapN2pt.saveScoresToFileStream(bw);
			m_mapN2wpt.saveScoresToFileStream(bw);

			m_mapPSTLHw.saveScoresToFileStream(bw);
			m_mapSSTLHw.saveScoresToFileStream(bw);
			m_mapPSTLHpt.saveScoresToFileStream(bw);
			m_mapSSTLHpt.saveScoresToFileStream(bw);
			m_mapPSTLHct.saveScoresToFileStream(bw);
			m_mapSSTLHct.saveScoresToFileStream(bw);
			m_mapPSTlhl.saveScoresToFileStream(bw);
			m_mapSSTlhl.saveScoresToFileStream(bw);

			m_mapN0LHw.saveScoresToFileStream(bw);
			m_mapN0LHpt.saveScoresToFileStream(bw);
			m_mapN0LHct.saveScoresToFileStream(bw);
			m_mapN0lhl.saveScoresToFileStream(bw);

			m_mapPSTRHw.saveScoresToFileStream(bw);
			m_mapSSTRHw.saveScoresToFileStream(bw);
			m_mapPSTRHpt.saveScoresToFileStream(bw);
			m_mapSSTRHpt.saveScoresToFileStream(bw);
			m_mapPSTRHct.saveScoresToFileStream(bw);
			m_mapSSTRHct.saveScoresToFileStream(bw);
			m_mapPSTrhl.saveScoresToFileStream(bw);
			m_mapSSTrhl.saveScoresToFileStream(bw);

			m_mapPSTL2Hw.saveScoresToFileStream(bw);
			m_mapSSTL2Hw.saveScoresToFileStream(bw);
			m_mapPSTL2Hpt.saveScoresToFileStream(bw);
			m_mapSSTL2Hpt.saveScoresToFileStream(bw);
			m_mapPSTL2Hct.saveScoresToFileStream(bw);
			m_mapSSTL2Hct.saveScoresToFileStream(bw);
			m_mapPSTl2hl.saveScoresToFileStream(bw);
			m_mapSSTl2hl.saveScoresToFileStream(bw);

			m_mapN0L2Hw.saveScoresToFileStream(bw);
			m_mapN0L2Hpt.saveScoresToFileStream(bw);
			m_mapN0L2Hct.saveScoresToFileStream(bw);
			m_mapN0l2hl.saveScoresToFileStream(bw);

			m_mapPSTR2Hw.saveScoresToFileStream(bw);
			m_mapSSTR2Hw.saveScoresToFileStream(bw);
			m_mapPSTR2Hpt.saveScoresToFileStream(bw);
			m_mapSSTR2Hpt.saveScoresToFileStream(bw);
			m_mapPSTR2Hct.saveScoresToFileStream(bw);
			m_mapSSTR2Hct.saveScoresToFileStream(bw);
			m_mapPSTr2hl.saveScoresToFileStream(bw);
			m_mapSSTr2hl.saveScoresToFileStream(bw);

			m_mapPSTLHLHw.saveScoresToFileStream(bw);
			m_mapSSTLHLHw.saveScoresToFileStream(bw);
			m_mapPSTLHLHpt.saveScoresToFileStream(bw);
			m_mapSSTLHLHpt.saveScoresToFileStream(bw);
			m_mapPSTLHLHct.saveScoresToFileStream(bw);
			m_mapSSTLHLHct.saveScoresToFileStream(bw);
			m_mapPSTLHlhl.saveScoresToFileStream(bw);
			m_mapSSTLHlhl.saveScoresToFileStream(bw);

			m_mapPSTLHRHw.saveScoresToFileStream(bw);
			m_mapSSTLHRHw.saveScoresToFileStream(bw);
			m_mapPSTLHRHpt.saveScoresToFileStream(bw);
			m_mapSSTLHRHpt.saveScoresToFileStream(bw);
			m_mapPSTLHRHct.saveScoresToFileStream(bw);
			m_mapSSTLHRHct.saveScoresToFileStream(bw);
			m_mapPSTLHrhl.saveScoresToFileStream(bw);
			m_mapSSTLHrhl.saveScoresToFileStream(bw);

			m_mapPSTRHLHw.saveScoresToFileStream(bw);
			m_mapSSTRHLHw.saveScoresToFileStream(bw);
			m_mapPSTRHLHpt.saveScoresToFileStream(bw);
			m_mapSSTRHLHpt.saveScoresToFileStream(bw);
			m_mapPSTRHLHct.saveScoresToFileStream(bw);
			m_mapSSTRHLHct.saveScoresToFileStream(bw);
			m_mapPSTRHlhl.saveScoresToFileStream(bw);
			m_mapSSTRHlhl.saveScoresToFileStream(bw);

			m_mapPSTRHRHw.saveScoresToFileStream(bw);
			m_mapSSTRHRHw.saveScoresToFileStream(bw);
			m_mapPSTRHRHpt.saveScoresToFileStream(bw);
			m_mapSSTRHRHpt.saveScoresToFileStream(bw);
			m_mapPSTRHRHct.saveScoresToFileStream(bw);
			m_mapSSTRHRHct.saveScoresToFileStream(bw);
			m_mapPSTRHrhl.saveScoresToFileStream(bw);
			m_mapSSTRHrhl.saveScoresToFileStream(bw);

			m_mapPSTLDw.saveScoresToFileStream(bw);
			m_mapSSTLDw.saveScoresToFileStream(bw);
			m_mapPSTLDpt.saveScoresToFileStream(bw);
			m_mapSSTLDpt.saveScoresToFileStream(bw);
			m_mapPSTLDct.saveScoresToFileStream(bw);
			m_mapSSTLDct.saveScoresToFileStream(bw);
			m_mapPSTldl.saveScoresToFileStream(bw);
			m_mapSSTldl.saveScoresToFileStream(bw);

			m_mapPSTRDw.saveScoresToFileStream(bw);
			m_mapSSTRDw.saveScoresToFileStream(bw);
			m_mapPSTRDpt.saveScoresToFileStream(bw);
			m_mapSSTRDpt.saveScoresToFileStream(bw);
			m_mapPSTRDct.saveScoresToFileStream(bw);
			m_mapSSTRDct.saveScoresToFileStream(bw);
			m_mapPSTrdl.saveScoresToFileStream(bw);
			m_mapSSTrdl.saveScoresToFileStream(bw);

			m_mapN0LDw.saveScoresToFileStream(bw);
			m_mapN0LDpt.saveScoresToFileStream(bw);
			m_mapN0LDct.saveScoresToFileStream(bw);
			m_mapN0ldl.saveScoresToFileStream(bw);

			m_mapPSTL2Dw.saveScoresToFileStream(bw);
			m_mapSSTL2Dw.saveScoresToFileStream(bw);
			m_mapPSTL2Dpt.saveScoresToFileStream(bw);
			m_mapSSTL2Dpt.saveScoresToFileStream(bw);
			m_mapPSTL2Dct.saveScoresToFileStream(bw);
			m_mapSSTL2Dct.saveScoresToFileStream(bw);
			m_mapPSTl2dl.saveScoresToFileStream(bw);
			m_mapSSTl2dl.saveScoresToFileStream(bw);

			m_mapPSTR2Dw.saveScoresToFileStream(bw);
			m_mapSSTR2Dw.saveScoresToFileStream(bw);
			m_mapPSTR2Dpt.saveScoresToFileStream(bw);
			m_mapSSTR2Dpt.saveScoresToFileStream(bw);
			m_mapPSTR2Dct.saveScoresToFileStream(bw);
			m_mapSSTR2Dct.saveScoresToFileStream(bw);
			m_mapPSTr2dl.saveScoresToFileStream(bw);
			m_mapSSTr2dl.saveScoresToFileStream(bw);

			m_mapN0L2Dw.saveScoresToFileStream(bw);
			m_mapN0L2Dpt.saveScoresToFileStream(bw);
			m_mapN0L2Dct.saveScoresToFileStream(bw);
			m_mapN0l2dl.saveScoresToFileStream(bw);

			m_mapPSTwptN0wpt.saveScoresToFileStream(bw);
			m_mapSSTwptN0wpt.saveScoresToFileStream(bw);
			m_mapPSTwptN0w.saveScoresToFileStream(bw);
			m_mapSSTwptN0w.saveScoresToFileStream(bw);
			m_mapPSTwN0wpt.saveScoresToFileStream(bw);
			m_mapSSTwN0wpt.saveScoresToFileStream(bw);
			m_mapPSTptN0wpt.saveScoresToFileStream(bw);
			m_mapSSTptN0wpt.saveScoresToFileStream(bw);
			m_mapPSTwptN0pt.saveScoresToFileStream(bw);
			m_mapSSTwptN0pt.saveScoresToFileStream(bw);
			m_mapPSTwctN0w.saveScoresToFileStream(bw);
			m_mapSSTwctN0w.saveScoresToFileStream(bw);
			m_mapPSTwN0w.saveScoresToFileStream(bw);
			m_mapSSTwN0w.saveScoresToFileStream(bw);

			m_mapPSTptN0pt.saveScoresToFileStream(bw);
			m_mapSSTptN0pt.saveScoresToFileStream(bw);
			m_mapN0ptN1pt.saveScoresToFileStream(bw);
			m_mapN0ptN1ptN2pt.saveScoresToFileStream(bw);
			m_mapPSTptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapSSTptN0ptN1pt.saveScoresToFileStream(bw);
			m_mapPSTptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapSSTptN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapPSTptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapSSTptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapN0ptN0LDptN0L2Dpt.saveScoresToFileStream(bw);
			m_mapN0ptN0LHptN0L2Hpt.saveScoresToFileStream(bw);
			m_mapPSTLHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapSSTLHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapPSTRHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapSSTRHptSTptN0pt.saveScoresToFileStream(bw);
			m_mapPSTLHLHptSTLHptSTpt.saveScoresToFileStream(bw);
			m_mapSSTLHLHptSTLHptSTpt.saveScoresToFileStream(bw);
			m_mapPSTLHRHptSTLHptSTpt.saveScoresToFileStream(bw);
			m_mapSSTLHRHptSTLHptSTpt.saveScoresToFileStream(bw);
			m_mapPSTRHLHptSTRHptSTpt.saveScoresToFileStream(bw);
			m_mapSSTRHLHptSTRHptSTpt.saveScoresToFileStream(bw);
			m_mapPSTRHRHptSTRHptSTpt.saveScoresToFileStream(bw);
			m_mapSSTRHRHptSTRHptSTpt.saveScoresToFileStream(bw);
			m_mapPSTptSTLDptN0pt.saveScoresToFileStream(bw);
			m_mapSSTptSTLDptN0pt.saveScoresToFileStream(bw);
			m_mapPSTptSTLDptSTL2Dpt.saveScoresToFileStream(bw);
			m_mapSSTptSTLDptSTL2Dpt.saveScoresToFileStream(bw);
			m_mapPSTptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSSTptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapPSTptSTRDptSTR2Dpt.saveScoresToFileStream(bw);
			m_mapSSTptSTRDptSTR2Dpt.saveScoresToFileStream(bw);

			m_mapPSTLHLHctSTLHctSTct.saveScoresToFileStream(bw);
			m_mapSSTLHLHctSTLHctSTct.saveScoresToFileStream(bw);
			m_mapPSTLHRHctSTLHctSTct.saveScoresToFileStream(bw);
			m_mapSSTLHRHctSTLHctSTct.saveScoresToFileStream(bw);
			m_mapPSTRHLHctSTRHctSTct.saveScoresToFileStream(bw);
			m_mapSSTRHLHctSTRHctSTct.saveScoresToFileStream(bw);
			m_mapPSTRHRHctSTRHctSTct.saveScoresToFileStream(bw);
			m_mapSSTRHRHctSTRHctSTct.saveScoresToFileStream(bw);
			m_mapPSTctSTLDctSTL2Dct.saveScoresToFileStream(bw);
			m_mapSSTctSTLDctSTL2Dct.saveScoresToFileStream(bw);
			m_mapPSTctSTRDctSTR2Dct.saveScoresToFileStream(bw);
			m_mapSSTctSTRDctSTR2Dct.saveScoresToFileStream(bw);

			m_mapPSTwd0.saveScoresToFileStream(bw);
			m_mapSSTwd0.saveScoresToFileStream(bw);
			m_mapPSTptd0.saveScoresToFileStream(bw);
			m_mapSSTptd0.saveScoresToFileStream(bw);
			m_mapPSTctd0.saveScoresToFileStream(bw);
			m_mapSSTctd0.saveScoresToFileStream(bw);
			m_mapN0wd0.saveScoresToFileStream(bw);
			m_mapN0ptd0.saveScoresToFileStream(bw);
			m_mapPSTwN0wd0.saveScoresToFileStream(bw);
			m_mapSSTwN0wd0.saveScoresToFileStream(bw);
			m_mapPSTptN0ptd0.saveScoresToFileStream(bw);
			m_mapSSTptN0ptd0.saveScoresToFileStream(bw);

			m_mapPSTwrda.saveScoresToFileStream(bw);
			m_mapSSTwrda.saveScoresToFileStream(bw);
			m_mapPSTptrda.saveScoresToFileStream(bw);
			m_mapSSTptrda.saveScoresToFileStream(bw);
			m_mapPSTctrda.saveScoresToFileStream(bw);
			m_mapSSTctrda.saveScoresToFileStream(bw);
			m_mapPSTwlda.saveScoresToFileStream(bw);
			m_mapSSTwlda.saveScoresToFileStream(bw);
			m_mapPSTptlda.saveScoresToFileStream(bw);
			m_mapSSTptlda.saveScoresToFileStream(bw);
			m_mapPSTctlda.saveScoresToFileStream(bw);
			m_mapSSTctlda.saveScoresToFileStream(bw);
			m_mapN0wlda.saveScoresToFileStream(bw);
			m_mapN0ptlda.saveScoresToFileStream(bw);

			m_mapPSTwrha.saveScoresToFileStream(bw);
			m_mapSSTwrha.saveScoresToFileStream(bw);
			m_mapPSTptrha.saveScoresToFileStream(bw);
			m_mapSSTptrha.saveScoresToFileStream(bw);
			m_mapPSTctrha.saveScoresToFileStream(bw);
			m_mapSSTctrha.saveScoresToFileStream(bw);
			m_mapPSTwlha.saveScoresToFileStream(bw);
			m_mapSSTwlha.saveScoresToFileStream(bw);
			m_mapPSTptlha.saveScoresToFileStream(bw);
			m_mapSSTptlha.saveScoresToFileStream(bw);
			m_mapPSTctlha.saveScoresToFileStream(bw);
			m_mapSSTctlha.saveScoresToFileStream(bw);
			m_mapN0wlha.saveScoresToFileStream(bw);
			m_mapN0ptlha.saveScoresToFileStream(bw);

			m_mapPSTwrp.saveScoresToFileStream(bw);
			m_mapSSTwrp.saveScoresToFileStream(bw);
			m_mapPSTptrp.saveScoresToFileStream(bw);
			m_mapSSTptrp.saveScoresToFileStream(bw);
			m_mapPSTctrp.saveScoresToFileStream(bw);
			m_mapSSTctrp.saveScoresToFileStream(bw);

			m_mapPSTwlp.saveScoresToFileStream(bw);
			m_mapSSTwlp.saveScoresToFileStream(bw);
			m_mapPSTwlc.saveScoresToFileStream(bw);
			m_mapSSTwlc.saveScoresToFileStream(bw);
			m_mapPSTptlp.saveScoresToFileStream(bw);
			m_mapSSTptlp.saveScoresToFileStream(bw);
			m_mapPSTptlc.saveScoresToFileStream(bw);
			m_mapSSTptlc.saveScoresToFileStream(bw);
			m_mapPSTctlp.saveScoresToFileStream(bw);
			m_mapSSTctlp.saveScoresToFileStream(bw);
			m_mapPSTctlc.saveScoresToFileStream(bw);
			m_mapSSTctlc.saveScoresToFileStream(bw);

			m_mapN0wlp.saveScoresToFileStream(bw);
			m_mapN0wlc.saveScoresToFileStream(bw);
			m_mapN0ptlp.saveScoresToFileStream(bw);
			m_mapN0ptlc.saveScoresToFileStream(bw);

			m_mapPSTP.saveScoresToFileStream(bw);
			m_mapSSTP.saveScoresToFileStream(bw);
			
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

		m_mapPSTw.computeAverage(round);
		m_mapSSTw.computeAverage(round);
		m_mapPSTpt.computeAverage(round);
		m_mapSSTpt.computeAverage(round);
		m_mapPSTwpt.computeAverage(round);
		m_mapSSTwpt.computeAverage(round);
		m_mapPSTct.computeAverage(round);
		m_mapSSTct.computeAverage(round);
		m_mapPSTwct.computeAverage(round);
		m_mapSSTwct.computeAverage(round);

		m_mapN0w.computeAverage(round);
		m_mapN0pt.computeAverage(round);
		m_mapN0wpt.computeAverage(round);

		m_mapN1w.computeAverage(round);
		m_mapN1pt.computeAverage(round);
		m_mapN1wpt.computeAverage(round);

		m_mapN2w.computeAverage(round);
		m_mapN2pt.computeAverage(round);
		m_mapN2wpt.computeAverage(round);

		m_mapPSTLHw.computeAverage(round);
		m_mapSSTLHw.computeAverage(round);
		m_mapPSTLHpt.computeAverage(round);
		m_mapSSTLHpt.computeAverage(round);
		m_mapPSTLHct.computeAverage(round);
		m_mapSSTLHct.computeAverage(round);
		m_mapPSTlhl.computeAverage(round);
		m_mapSSTlhl.computeAverage(round);

		m_mapN0LHw.computeAverage(round);
		m_mapN0LHpt.computeAverage(round);
		m_mapN0LHct.computeAverage(round);
		m_mapN0lhl.computeAverage(round);

		m_mapPSTRHw.computeAverage(round);
		m_mapSSTRHw.computeAverage(round);
		m_mapPSTRHpt.computeAverage(round);
		m_mapSSTRHpt.computeAverage(round);
		m_mapPSTRHct.computeAverage(round);
		m_mapSSTRHct.computeAverage(round);
		m_mapPSTrhl.computeAverage(round);
		m_mapSSTrhl.computeAverage(round);

		m_mapPSTL2Hw.computeAverage(round);
		m_mapSSTL2Hw.computeAverage(round);
		m_mapPSTL2Hpt.computeAverage(round);
		m_mapSSTL2Hpt.computeAverage(round);
		m_mapPSTL2Hct.computeAverage(round);
		m_mapSSTL2Hct.computeAverage(round);
		m_mapPSTl2hl.computeAverage(round);
		m_mapSSTl2hl.computeAverage(round);

		m_mapN0L2Hw.computeAverage(round);
		m_mapN0L2Hpt.computeAverage(round);
		m_mapN0L2Hct.computeAverage(round);
		m_mapN0l2hl.computeAverage(round);

		m_mapPSTR2Hw.computeAverage(round);
		m_mapSSTR2Hw.computeAverage(round);
		m_mapPSTR2Hpt.computeAverage(round);
		m_mapSSTR2Hpt.computeAverage(round);
		m_mapPSTR2Hct.computeAverage(round);
		m_mapSSTR2Hct.computeAverage(round);
		m_mapPSTr2hl.computeAverage(round);
		m_mapSSTr2hl.computeAverage(round);

		m_mapPSTLHLHw.computeAverage(round);
		m_mapSSTLHLHw.computeAverage(round);
		m_mapPSTLHLHpt.computeAverage(round);
		m_mapSSTLHLHpt.computeAverage(round);
		m_mapPSTLHLHct.computeAverage(round);
		m_mapSSTLHLHct.computeAverage(round);
		m_mapPSTLHlhl.computeAverage(round);
		m_mapSSTLHlhl.computeAverage(round);

		m_mapPSTLHRHw.computeAverage(round);
		m_mapSSTLHRHw.computeAverage(round);
		m_mapPSTLHRHpt.computeAverage(round);
		m_mapSSTLHRHpt.computeAverage(round);
		m_mapPSTLHRHct.computeAverage(round);
		m_mapSSTLHRHct.computeAverage(round);
		m_mapPSTLHrhl.computeAverage(round);
		m_mapSSTLHrhl.computeAverage(round);

		m_mapPSTRHLHw.computeAverage(round);
		m_mapSSTRHLHw.computeAverage(round);
		m_mapPSTRHLHpt.computeAverage(round);
		m_mapSSTRHLHpt.computeAverage(round);
		m_mapPSTRHLHct.computeAverage(round);
		m_mapSSTRHLHct.computeAverage(round);
		m_mapPSTRHlhl.computeAverage(round);
		m_mapSSTRHlhl.computeAverage(round);

		m_mapPSTRHRHw.computeAverage(round);
		m_mapSSTRHRHw.computeAverage(round);
		m_mapPSTRHRHpt.computeAverage(round);
		m_mapSSTRHRHpt.computeAverage(round);
		m_mapPSTRHRHct.computeAverage(round);
		m_mapSSTRHRHct.computeAverage(round);
		m_mapPSTRHrhl.computeAverage(round);
		m_mapSSTRHrhl.computeAverage(round);

		m_mapPSTLDw.computeAverage(round);
		m_mapSSTLDw.computeAverage(round);
		m_mapPSTLDpt.computeAverage(round);
		m_mapSSTLDpt.computeAverage(round);
		m_mapPSTLDct.computeAverage(round);
		m_mapSSTLDct.computeAverage(round);
		m_mapPSTldl.computeAverage(round);
		m_mapSSTldl.computeAverage(round);

		m_mapPSTRDw.computeAverage(round);
		m_mapSSTRDw.computeAverage(round);
		m_mapPSTRDpt.computeAverage(round);
		m_mapSSTRDpt.computeAverage(round);
		m_mapPSTRDct.computeAverage(round);
		m_mapSSTRDct.computeAverage(round);
		m_mapPSTrdl.computeAverage(round);
		m_mapSSTrdl.computeAverage(round);

		m_mapN0LDw.computeAverage(round);
		m_mapN0LDpt.computeAverage(round);
		m_mapN0LDct.computeAverage(round);
		m_mapN0ldl.computeAverage(round);

		m_mapPSTL2Dw.computeAverage(round);
		m_mapSSTL2Dw.computeAverage(round);
		m_mapPSTL2Dpt.computeAverage(round);
		m_mapSSTL2Dpt.computeAverage(round);
		m_mapPSTL2Dct.computeAverage(round);
		m_mapSSTL2Dct.computeAverage(round);
		m_mapPSTl2dl.computeAverage(round);
		m_mapSSTl2dl.computeAverage(round);

		m_mapPSTR2Dw.computeAverage(round);
		m_mapSSTR2Dw.computeAverage(round);
		m_mapPSTR2Dpt.computeAverage(round);
		m_mapSSTR2Dpt.computeAverage(round);
		m_mapPSTR2Dct.computeAverage(round);
		m_mapSSTR2Dct.computeAverage(round);
		m_mapPSTr2dl.computeAverage(round);
		m_mapSSTr2dl.computeAverage(round);

		m_mapN0L2Dw.computeAverage(round);
		m_mapN0L2Dpt.computeAverage(round);
		m_mapN0L2Dct.computeAverage(round);
		m_mapN0l2dl.computeAverage(round);

		m_mapPSTwptN0wpt.computeAverage(round);
		m_mapSSTwptN0wpt.computeAverage(round);
		m_mapPSTwptN0w.computeAverage(round);
		m_mapSSTwptN0w.computeAverage(round);
		m_mapPSTwN0wpt.computeAverage(round);
		m_mapSSTwN0wpt.computeAverage(round);
		m_mapPSTptN0wpt.computeAverage(round);
		m_mapSSTptN0wpt.computeAverage(round);
		m_mapPSTwptN0pt.computeAverage(round);
		m_mapSSTwptN0pt.computeAverage(round);
		m_mapPSTwctN0w.computeAverage(round);
		m_mapSSTwctN0w.computeAverage(round);
		m_mapPSTwN0w.computeAverage(round);
		m_mapSSTwN0w.computeAverage(round);

		m_mapPSTptN0pt.computeAverage(round);
		m_mapSSTptN0pt.computeAverage(round);
		m_mapN0ptN1pt.computeAverage(round);
		m_mapN0ptN1ptN2pt.computeAverage(round);
		m_mapPSTptN0ptN1pt.computeAverage(round);
		m_mapSSTptN0ptN1pt.computeAverage(round);
		m_mapPSTptN0ptN0LDpt.computeAverage(round);
		m_mapSSTptN0ptN0LDpt.computeAverage(round);
		m_mapPSTptN0ptN0LHpt.computeAverage(round);
		m_mapSSTptN0ptN0LHpt.computeAverage(round);
		m_mapN0ptN0LDptN0L2Dpt.computeAverage(round);
		m_mapN0ptN0LHptN0L2Hpt.computeAverage(round);
		m_mapPSTLHptSTptN0pt.computeAverage(round);
		m_mapSSTLHptSTptN0pt.computeAverage(round);
		m_mapPSTRHptSTptN0pt.computeAverage(round);
		m_mapSSTRHptSTptN0pt.computeAverage(round);
		m_mapPSTLHLHptSTLHptSTpt.computeAverage(round);
		m_mapSSTLHLHptSTLHptSTpt.computeAverage(round);
		m_mapPSTLHRHptSTLHptSTpt.computeAverage(round);
		m_mapSSTLHRHptSTLHptSTpt.computeAverage(round);
		m_mapPSTRHLHptSTRHptSTpt.computeAverage(round);
		m_mapSSTRHLHptSTRHptSTpt.computeAverage(round);
		m_mapPSTRHRHptSTRHptSTpt.computeAverage(round);
		m_mapSSTRHRHptSTRHptSTpt.computeAverage(round);
		m_mapPSTptSTLDptN0pt.computeAverage(round);
		m_mapSSTptSTLDptN0pt.computeAverage(round);
		m_mapPSTptSTLDptSTL2Dpt.computeAverage(round);
		m_mapSSTptSTLDptSTL2Dpt.computeAverage(round);
		m_mapPSTptSTRDptN0pt.computeAverage(round);
		m_mapSSTptSTRDptN0pt.computeAverage(round);
		m_mapPSTptSTRDptSTR2Dpt.computeAverage(round);
		m_mapSSTptSTRDptSTR2Dpt.computeAverage(round);

		m_mapPSTLHLHctSTLHctSTct.computeAverage(round);
		m_mapSSTLHLHctSTLHctSTct.computeAverage(round);
		m_mapPSTLHRHctSTLHctSTct.computeAverage(round);
		m_mapSSTLHRHctSTLHctSTct.computeAverage(round);
		m_mapPSTRHLHctSTRHctSTct.computeAverage(round);
		m_mapSSTRHLHctSTRHctSTct.computeAverage(round);
		m_mapPSTRHRHctSTRHctSTct.computeAverage(round);
		m_mapSSTRHRHctSTRHctSTct.computeAverage(round);
		m_mapPSTctSTLDctSTL2Dct.computeAverage(round);
		m_mapSSTctSTLDctSTL2Dct.computeAverage(round);
		m_mapPSTctSTRDctSTR2Dct.computeAverage(round);
		m_mapSSTctSTRDctSTR2Dct.computeAverage(round);

		m_mapPSTwd0.computeAverage(round);
		m_mapSSTwd0.computeAverage(round);
		m_mapPSTptd0.computeAverage(round);
		m_mapSSTptd0.computeAverage(round);
		m_mapPSTctd0.computeAverage(round);
		m_mapSSTctd0.computeAverage(round);
		m_mapN0wd0.computeAverage(round);
		m_mapN0ptd0.computeAverage(round);
		m_mapPSTwN0wd0.computeAverage(round);
		m_mapSSTwN0wd0.computeAverage(round);
		m_mapPSTptN0ptd0.computeAverage(round);
		m_mapSSTptN0ptd0.computeAverage(round);

		m_mapPSTwrda.computeAverage(round);
		m_mapSSTwrda.computeAverage(round);
		m_mapPSTptrda.computeAverage(round);
		m_mapSSTptrda.computeAverage(round);
		m_mapPSTctrda.computeAverage(round);
		m_mapSSTctrda.computeAverage(round);
		m_mapPSTwlda.computeAverage(round);
		m_mapSSTwlda.computeAverage(round);
		m_mapPSTptlda.computeAverage(round);
		m_mapSSTptlda.computeAverage(round);
		m_mapPSTctlda.computeAverage(round);
		m_mapSSTctlda.computeAverage(round);
		m_mapN0wlda.computeAverage(round);
		m_mapN0ptlda.computeAverage(round);

		m_mapPSTwrha.computeAverage(round);
		m_mapSSTwrha.computeAverage(round);
		m_mapPSTptrha.computeAverage(round);
		m_mapSSTptrha.computeAverage(round);
		m_mapPSTctrha.computeAverage(round);
		m_mapSSTctrha.computeAverage(round);
		m_mapPSTwlha.computeAverage(round);
		m_mapSSTwlha.computeAverage(round);
		m_mapPSTptlha.computeAverage(round);
		m_mapSSTptlha.computeAverage(round);
		m_mapPSTctlha.computeAverage(round);
		m_mapSSTctlha.computeAverage(round);
		m_mapN0wlha.computeAverage(round);
		m_mapN0ptlha.computeAverage(round);

		m_mapPSTwrp.computeAverage(round);
		m_mapSSTwrp.computeAverage(round);
		m_mapPSTptrp.computeAverage(round);
		m_mapSSTptrp.computeAverage(round);
		m_mapPSTctrp.computeAverage(round);
		m_mapSSTctrp.computeAverage(round);

		m_mapPSTwlp.computeAverage(round);
		m_mapSSTwlp.computeAverage(round);
		m_mapPSTwlc.computeAverage(round);
		m_mapSSTwlc.computeAverage(round);
		m_mapPSTptlp.computeAverage(round);
		m_mapSSTptlp.computeAverage(round);
		m_mapPSTptlc.computeAverage(round);
		m_mapSSTptlc.computeAverage(round);
		m_mapPSTctlp.computeAverage(round);
		m_mapSSTctlp.computeAverage(round);
		m_mapPSTctlc.computeAverage(round);
		m_mapSSTctlc.computeAverage(round);

		m_mapN0wlp.computeAverage(round);
		m_mapN0wlc.computeAverage(round);
		m_mapN0ptlp.computeAverage(round);
		m_mapN0ptlc.computeAverage(round);

		m_mapPSTP.computeAverage(round);
		m_mapSSTP.computeAverage(round);
		
		System.out.println("done.");
	}
	
}
