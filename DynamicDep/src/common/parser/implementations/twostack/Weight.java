//package common.parser.implementations.twostack;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//
//import common.parser.WeightBase;
//import common.parser.implementations.map.CCGTagIntMap;
//import common.parser.implementations.map.CCGTagMap;
//import common.parser.implementations.map.CCGTagSet3Map;
//import common.parser.implementations.map.SetOfCCGLabelsIntMap;
//import common.parser.implementations.map.SetOfDepLabelsIntMap;
//import common.parser.implementations.map.CCGTaggedWordMap;
//import common.parser.implementations.map.IntMap;
//import common.parser.implementations.map.POSTagIntMap;
//import common.parser.implementations.map.POSTagMap;
//import common.parser.implementations.map.POSTagPOSTagIntMap;
//import common.parser.implementations.map.POSTagSet2Map;
//import common.parser.implementations.map.POSTagSet3Map;
//import common.parser.implementations.map.POSTagSetOfCCGLabelsMap;
//import common.parser.implementations.map.POSTagSetOfDepLabelsMap;
//import common.parser.implementations.map.POSTaggedWordMap;
//import common.parser.implementations.map.TwoPOSTaggedWordsMap;
//import common.parser.implementations.map.TwoWordsMap;
//import common.parser.implementations.map.WordIntMap;
//import common.parser.implementations.map.WordMap;
//import common.parser.implementations.map.WordPOSTagPOSTagMap;
//import common.parser.implementations.map.WordSetOfCCGLabelsMap;
//import common.parser.implementations.map.WordSetOfDepLabelsMap;
//import common.parser.implementations.map.WordWordCCGTagMap;
//import common.parser.implementations.map.WordWordIntMap;
//import common.parser.implementations.map.WordWordPOSTagMap;
//
//public class Weight extends WeightBase {
//	
//	/*
//	 * stack top
//	 */
//	public WordMap m_mapSTw;
//	public POSTagMap m_mapSTpt;
//	public POSTaggedWordMap m_mapSTwpt;
//	public CCGTagMap m_mapSTct;
//	public CCGTaggedWordMap m_mapSTwct;
//	
//	/*
//	 * second stack top
//	 */
//	public WordMap m_mapSSTw;
//	public POSTagMap m_mapSSTpt;
//	public POSTaggedWordMap m_mapSSTwpt;
//	public CCGTagMap m_mapSSTct;
//	public CCGTaggedWordMap m_mapSSTwct;
//
//	/*
//	 * buffer head
//	 */
//	public WordMap m_mapN0w;
//	public POSTagMap m_mapN0pt;
//	public POSTaggedWordMap m_mapN0wpt;
//
//	/*
//	 * buffer head next
//	 */
//	public WordMap m_mapN1w;
//	public POSTagMap m_mapN1pt;
//	public POSTaggedWordMap m_mapN1wpt;
//
//	/*
//	 * buffer head next next
//	 */
//	public WordMap m_mapN2w;
//	public POSTagMap m_mapN2pt;
//	public POSTaggedWordMap m_mapN2wpt;
//
//	/*
//	 * stack top head
//	 */
//	public WordMap m_mapSTHw;
//	public POSTagMap m_mapSTHpt;
//	public IntMap m_mapSTHct;
//	public IntMap m_mapSTi;
//	
//	/*
//	 * second stack top head
//	 */
//	public WordMap m_mapSSTHw;
//	public POSTagMap m_mapSSTHpt;
//	public IntMap m_mapSSTHct;
//	public IntMap m_mapSSTi;
//
//	/*
//	 * stack top head head
//	 */
//	public WordMap m_mapSTHHw;
//	public POSTagMap m_mapSTHHpt;
//	public IntMap m_mapSTHHct;
//	public IntMap m_mapSTHi;
//
//	/*
//	 * second stack top head head
//	 */
//	public WordMap m_mapSSTHHw;
//	public POSTagMap m_mapSSTHHpt;
//	public IntMap m_mapSSTHHct;
//	public IntMap m_mapSSTHi;
//	
//	/*
//	 * stack top leftmost dependency
//	 */
//	public WordMap m_mapSTLDw;
//	public POSTagMap m_mapSTLDpt;
//	public IntMap m_mapSTLDct;
//	public IntMap m_mapSTLDi;
//	
//	/*
//	 * second stack top leftmost dependency
//	 */
//	public WordMap m_mapSSTLDw;
//	public POSTagMap m_mapSSTLDpt;
//	public IntMap m_mapSSTLDct;
//	public IntMap m_mapSSTLDi;
//	
//	/*
//	 * stack top rightmost dependency
//	 */
//	public WordMap m_mapSTRDw;
//	public POSTagMap m_mapSTRDpt;
//	public IntMap m_mapSTRDct;
//	public IntMap m_mapSTRDi;
//
//	/*
//	 * second stack top rightmost dependency
//	 */
//	public WordMap m_mapSSTRDw;
//	public POSTagMap m_mapSSTRDpt;
//	public IntMap m_mapSSTRDct;
//	public IntMap m_mapSSTRDi;
//
//	/*
//	 * buffer head leftmost dependency
//	 */
//	public WordMap m_mapN0LDw;
//	public POSTagMap m_mapN0LDpt;
//	public IntMap m_mapN0LDct;
//	public IntMap m_mapN0LDi;
//
//	/*
//	 * stack top 2ndleftmost dependency
//	 */
//	public WordMap m_mapSTL2Dw;
//	public POSTagMap m_mapSTL2Dpt;
//	public IntMap m_mapSTL2Dct;
//	public IntMap m_mapSTL2Di;
//
//	/*
//	 * second stack top 2ndleftmost dependency
//	 */
//	public WordMap m_mapSSTL2Dw;
//	public POSTagMap m_mapSSTL2Dpt;
//	public IntMap m_mapSSTL2Dct;
//	public IntMap m_mapSSTL2Di;
//
//	/*
//	 * stack top 2ndrightmost dependency
//	 */
//	public WordMap m_mapSTR2Dw;
//	public POSTagMap m_mapSTR2Dpt;
//	public IntMap m_mapSTR2Dct;
//	public IntMap m_mapSTR2Di;
//
//	/*
//	 * second stack top 2ndrightmost dependency
//	 */
//	public WordMap m_mapSSTR2Dw;
//	public POSTagMap m_mapSSTR2Dpt;
//	public IntMap m_mapSSTR2Dct;
//	public IntMap m_mapSSTR2Di;
//
//	/*
//	 * buffer head 2ndleftmost dependency
//	 */
//	public WordMap m_mapN0L2Dw;
//	public POSTagMap m_mapN0L2Dpt;
//	public IntMap m_mapN0L2Dct;
//	public IntMap m_mapN0L2Di;
//
//	/*
//	 * stack top and buffer head word and postag
//	 */
//	public TwoPOSTaggedWordsMap m_mapSTwptN0wpt;
//	public WordWordPOSTagMap m_mapSTwptN0w;
//	public WordWordPOSTagMap m_mapSTwN0wpt;
//	public WordPOSTagPOSTagMap m_mapSTptN0wpt;
//	public WordPOSTagPOSTagMap m_mapSTwptN0pt;
//	public WordWordCCGTagMap m_mapSTwctN0w;
//	public TwoWordsMap m_mapSTwN0w;
//
//	/*
//	 * second stack top and buffer head word and postag
//	 */
//	public TwoPOSTaggedWordsMap m_mapSSTwptN0wpt;
//	public WordWordPOSTagMap m_mapSSTwptN0w;
//	public WordWordPOSTagMap m_mapSSTwN0wpt;
//	public WordPOSTagPOSTagMap m_mapSSTptN0wpt;
//	public WordPOSTagPOSTagMap m_mapSSTwptN0pt;
//	public WordWordCCGTagMap m_mapSSTwctN0w;
//	public TwoWordsMap m_mapSSTwN0w;
//	
//	/*
//	 * stack top and buffer head postag
//	 */
//	public POSTagSet2Map m_mapSTptN0pt;
//	public POSTagSet2Map m_mapN0ptN1pt;
//	public POSTagSet3Map m_mapN0ptN1ptN2pt;
//	public POSTagSet3Map m_mapSTptN0ptN1pt;
//	public POSTagSet3Map m_mapSTptN0ptN0LDpt;
//	public POSTagSet3Map m_mapN0ptN0LDptN0L2Dpt;
//	public POSTagSet3Map m_mapSTHptSTptN0pt;
//	public POSTagSet3Map m_mapSTHHptSTHptSTpt;
//	public POSTagSet3Map m_mapSTptSTLDptN0pt;
//	public POSTagSet3Map m_mapSTptSTLDptSTL2Dpt;
//	public POSTagSet3Map m_mapSTptSTRDptN0pt;
//	public POSTagSet3Map m_mapSTptSTRDptSTR2Dpt;
//	
//	/*
//	 * second stack top and buffer head postag
//	 */
//	public POSTagSet2Map m_mapSSTptN0pt;
//	public POSTagSet3Map m_mapSSTptN0ptN1pt;
//	public POSTagSet3Map m_mapSSTptN0ptN0LDpt;
//	public POSTagSet3Map m_mapSSTHptSSTptN0pt;
//	public POSTagSet3Map m_mapSSTHHptSSTHptSSTpt;
//	public POSTagSet3Map m_mapSSTptSSTLDptN0pt;
//	public POSTagSet3Map m_mapSSTptSSTLDptSSTL2Dpt;
//	public POSTagSet3Map m_mapSSTptSSTRDptN0pt;
//	public POSTagSet3Map m_mapSSTptSSTRDptSSTR2Dpt;
//	
//	/*
//	 * stack top and second stack top and buffer head postag
//	 */
//	public POSTagSet3Map m_mapSTptSSTptSTHpt;
//	public POSTagSet3Map m_mapSTptSSTptSSTHpt;
//	public POSTagSet3Map m_mapSTptSSTptSTLDpt;
//	public POSTagSet3Map m_mapSTptSSTptSSTLDpt;
//	public POSTagSet3Map m_mapSTptSSTptSTRDpt;
//	public POSTagSet3Map m_mapSTptSSTptSSTRDpt;
//	
//	/*
//	 * stack top and buffer head ccgtag
//	 */
//	public CCGTagSet3Map m_mapSTHHctSTHctSTct;
//	public CCGTagSet3Map m_mapSTctSTLDctSTL2Dct;
//	public CCGTagSet3Map m_mapSTctSTRDctSTR2Dct;
//
//	/*
//	 * stack top and buffer head ccgtag
//	 */
//	public CCGTagSet3Map m_mapSSTHHctSSTHctSSTct;
//	public CCGTagSet3Map m_mapSSTctSSTLDctSSTL2Dct;
//	public CCGTagSet3Map m_mapSSTctSSTRDctSSTR2Dct;
//	
//	/*
//	 * stack top and second stack top and buffer head ccgtag
//	 */
//	public CCGTagSet3Map m_mapSTctSSTctSTHct;
//	public CCGTagSet3Map m_mapSTctSSTctSSTHct;
//	public CCGTagSet3Map m_mapSTctSSTctSTLDct;
//	public CCGTagSet3Map m_mapSTctSSTctSSTLDct;
//	
//	/*
//	 * stack top word_int
//	 */
//	public WordIntMap m_mapSTwd;
//	public POSTagIntMap m_mapSTptd;
//	public CCGTagIntMap m_mapSTctd;
//	
//	/*
//	 * second stack top word_int
//	 */
//	public WordIntMap m_mapSSTwd;
//	public POSTagIntMap m_mapSSTptd;
//	public CCGTagIntMap m_mapSSTctd;
//
//	/*
//	 * stack top and buffer head distance word_int
//	 */
//	public WordIntMap m_mapN0wd;
//	public POSTagIntMap m_mapN0ptd;
//	
//	/*
//	 * second stack top and buffer head distance word_int
//	 */
//	public WordIntMap m_mapSN0wd;
//	public POSTagIntMap m_mapSN0ptd;
//
//	/*
//	 * stack top and buffer head word_int
//	 */
//	public WordWordIntMap m_mapSTwN0wd;
//	public POSTagPOSTagIntMap m_mapSTptN0ptd;
//
//	/*
//	 * second stack top and buffer head word_int
//	 */
//	public WordWordIntMap m_mapSSTwN0wd;
//	public POSTagPOSTagIntMap m_mapSSTptN0ptd;
//
//	/*
//	 * stack top word_int rarity
//	 */
//	public WordIntMap m_mapSTwra;
//	public POSTagIntMap m_mapSTptra;
//	public CCGTagIntMap m_mapSTctra;
//
//	/*
//	 * second stack top word_int rarity
//	 */
//	public WordIntMap m_mapSSTwra;
//	public POSTagIntMap m_mapSSTptra;
//	public CCGTagIntMap m_mapSSTctra;
//	
//	/*
//	 * stack top word_int larity
//	 */
//	public WordIntMap m_mapSTwla;
//	public POSTagIntMap m_mapSTptla;
//	public CCGTagIntMap m_mapSTctla;
//	
//	/*
//	 * second stack top word_int larity
//	 */
//	public WordIntMap m_mapSSTwla;
//	public POSTagIntMap m_mapSSTptla;
//	public CCGTagIntMap m_mapSSTctla;
//	
//	/*
//	 * buffer head word_int larity
//	 */
//	public WordIntMap m_mapN0wla;
//	public POSTagIntMap m_mapN0ptla;
//
//	/*
//	 * stack top deplabel set
//	 */
//	public WordSetOfDepLabelsMap m_mapSTwrp;
//	public POSTagSetOfDepLabelsMap m_mapSTptrp;
//	public SetOfDepLabelsIntMap m_mapSTctrp;
//
//	/*
//	 * second stack top deplabel set
//	 */
//	public WordSetOfDepLabelsMap m_mapSSTwrp;
//	public POSTagSetOfDepLabelsMap m_mapSSTptrp;
//	public SetOfDepLabelsIntMap m_mapSSTctrp;
//	
//	/*
//	 * stack top deplabel set
//	 */
//	public WordSetOfDepLabelsMap m_mapSTwlp;
//	public WordSetOfCCGLabelsMap m_mapSTwlc;
//	public POSTagSetOfDepLabelsMap m_mapSTptlp;
//	public POSTagSetOfCCGLabelsMap m_mapSTptlc;
//	public SetOfDepLabelsIntMap m_mapSTctlp;
//	public SetOfCCGLabelsIntMap m_mapSTctlc;
//
//	/*
//	 * stack top deplabel set
//	 */
//	public WordSetOfDepLabelsMap m_mapSSTwlp;
//	public WordSetOfCCGLabelsMap m_mapSSTwlc;
//	public POSTagSetOfDepLabelsMap m_mapSSTptlp;
//	public POSTagSetOfCCGLabelsMap m_mapSSTptlc;
//	public SetOfDepLabelsIntMap m_mapSSTctlp;
//	public SetOfCCGLabelsIntMap m_mapSSTctlc;
//	
//	/*
//	 * buffer head deplabel set
//	 */
//	public WordSetOfDepLabelsMap m_mapN0wlp;
//	public WordSetOfCCGLabelsMap m_mapN0wlc;
//	public POSTagSetOfDepLabelsMap m_mapN0ptlp;
//	public POSTagSetOfCCGLabelsMap m_mapN0ptlc;
//	
//	public Weight(final String sPath, final boolean bTrain) {
//		
//		super(sPath, bTrain);
//		
//		m_mapSTw = new WordMap("StackWord");
//		m_mapSTpt = new POSTagMap("StackPOSTag");
//		m_mapSTwpt = new POSTaggedWordMap("StackWordPOSTag");
//		m_mapSTct = new CCGTagMap("StackCCGTag");
//		m_mapSTwct = new CCGTaggedWordMap("StackWordCCGTag");
//
//		m_mapSSTw = new WordMap("SecondStackWord");
//		m_mapSSTpt = new POSTagMap("SecondStackPOSTag");
//		m_mapSSTwpt = new POSTaggedWordMap("SecondStackWordPOSTag");
//		m_mapSSTct = new CCGTagMap("SecondStackCCGTag");
//		m_mapSSTwct = new CCGTaggedWordMap("SecondStackWordCCGTag");
//
//		m_mapN0w = new WordMap("NextWord");
//		m_mapN0pt = new POSTagMap("NextPOSTag");
//		m_mapN0wpt = new POSTaggedWordMap("NextWordPOSTag");
//
//		m_mapN1w = new WordMap("Next+1Word");
//		m_mapN1pt = new POSTagMap("Next+1POSTag");
//		m_mapN1wpt = new POSTaggedWordMap("Next+1WordPOSTag");
//
//		m_mapN2w = new WordMap("Next+2Word");
//		m_mapN2pt = new POSTagMap("Next+2POSTag");
//		m_mapN2wpt = new POSTaggedWordMap("Next+2WordPOSTag");
//
//		m_mapSTHw = new WordMap("StackHeadWord");
//		m_mapSTHpt = new POSTagMap("StackHeadPOSTag");
//		m_mapSTHct = new IntMap("StackHeadCCGTag");
//		m_mapSTi = new IntMap("StackLabel");
//
//		m_mapSSTHw = new WordMap("SecondStackHeadWord");
//		m_mapSSTHpt = new POSTagMap("SecondStackHeadPOSTag");
//		m_mapSSTHct = new IntMap("SecondStackHeadCCGTag");
//		m_mapSSTi = new IntMap("SecondStackLabel");
//
//		m_mapSTHHw = new WordMap("StackHeadHeadWord");
//		m_mapSTHHpt = new POSTagMap("StackHeadHeadPOSTag");
//		m_mapSTHHct = new IntMap("StackHeadHeadCCGTag");
//		m_mapSTHi = new IntMap("StackHeadLabel");
//
//		m_mapSSTHHw = new WordMap("SecondStackHeadHeadWord");
//		m_mapSSTHHpt = new POSTagMap("SecondStackHeadHeadPOSTag");
//		m_mapSSTHHct = new IntMap("SecondStackHeadHeadCCGTag");
//		m_mapSSTHi = new IntMap("SecondStackHeadLabel");
//
//		m_mapSTLDw = new WordMap("StackLDWord");
//		m_mapSTLDpt = new POSTagMap("StackLDPOSTag");
//		m_mapSTLDct = new IntMap("StackLDCCGTag");
//		m_mapSTLDi = new IntMap("StackLDLabel");
//
//		m_mapSSTLDw = new WordMap("SecondStackLDWord");
//		m_mapSSTLDpt = new POSTagMap("SecondStackLDPOSTag");
//		m_mapSSTLDct = new IntMap("SecondStackLDCCGTag");
//		m_mapSSTLDi = new IntMap("SecondStackLDLabel");
//
//		m_mapSTRDw = new WordMap("StackRDWord");
//		m_mapSTRDpt = new POSTagMap("StackRDPOSTag");
//		m_mapSTRDct = new IntMap("StackRDCCGTag");
//		m_mapSTRDi = new IntMap("StackRDLabel");
//
//		m_mapSSTRDw = new WordMap("SecondStackRDWord");
//		m_mapSSTRDpt = new POSTagMap("SecondStackRDPOSTag");
//		m_mapSSTRDct = new IntMap("SecondStackRDCCGTag");
//		m_mapSSTRDi = new IntMap("SecondStackRDLabel");
//
//		m_mapN0LDw = new WordMap("NextLDWord");
//		m_mapN0LDpt = new POSTagMap("NextLDPOSTag");
//		m_mapN0LDct = new IntMap("NextLDCCGTag");
//		m_mapN0LDi = new IntMap("NextLDLabel");
//
//		m_mapSTL2Dw = new WordMap("StackL2DWord");
//		m_mapSTL2Dpt = new POSTagMap("StackL2DPOSTag");
//		m_mapSTL2Dct = new IntMap("StackL2DCCGTag");
//		m_mapSTL2Di = new IntMap("StackL2DLabel");
//
//		m_mapSSTL2Dw = new WordMap("SecondStackL2DWord");
//		m_mapSSTL2Dpt = new POSTagMap("SecondStackL2DPOSTag");
//		m_mapSSTL2Dct = new IntMap("SecondStackL2DCCGTag");
//		m_mapSSTL2Di = new IntMap("SecondStackL2DLabel");
//
//		m_mapSTR2Dw = new WordMap("StackR2DWord");
//		m_mapSTR2Dpt = new POSTagMap("StackR2DPOSTag");
//		m_mapSTR2Dct = new IntMap("StackR2DCCGTag");
//		m_mapSTR2Di = new IntMap("StackR2DLabel");
//
//		m_mapSSTR2Dw = new WordMap("SecondStackR2DWord");
//		m_mapSSTR2Dpt = new POSTagMap("SecondStackR2DPOSTag");
//		m_mapSSTR2Dct = new IntMap("SecondStackR2DCCGTag");
//		m_mapSSTR2Di = new IntMap("SecondStackR2DLabel");
//
//		m_mapN0L2Dw = new WordMap("NextL2DWord");
//		m_mapN0L2Dpt = new POSTagMap("NextL2DPOSTag");
//		m_mapN0L2Dct = new IntMap("NextL2DCCGTag");
//		m_mapN0L2Di = new IntMap("NextL2DLabel");
//
//		m_mapSTwptN0wpt = new TwoPOSTaggedWordsMap("StackWordPOSTagNextWordPOSTag");
//		m_mapSTwptN0w = new WordWordPOSTagMap("StackWordPOSTagNextWord");
//		m_mapSTwN0wpt = new WordWordPOSTagMap("StackWordNextWordPOSTag");
//		m_mapSTptN0wpt = new WordPOSTagPOSTagMap("StackPOSTagNextWordPOSTag");
//		m_mapSTwptN0pt = new WordPOSTagPOSTagMap("StackWordPOSTagNextPOSTag");
//		m_mapSTwctN0w = new WordWordCCGTagMap("StackWordCCGTagNextWord");
//		m_mapSTwN0w = new TwoWordsMap("StackWordNextWord");
//
//		m_mapSSTwptN0wpt = new TwoPOSTaggedWordsMap("SecondStackWordPOSTagNextWordPOSTag");
//		m_mapSSTwptN0w = new WordWordPOSTagMap("SecondStackWordPOSTagNextWord");
//		m_mapSSTwN0wpt = new WordWordPOSTagMap("SecondStackWordNextWordPOSTag");
//		m_mapSSTptN0wpt = new WordPOSTagPOSTagMap("SecondStackPOSTagNextWordPOSTag");
//		m_mapSSTwptN0pt = new WordPOSTagPOSTagMap("SecondStackWordPOSTagNextPOSTag");
//		m_mapSSTwctN0w = new WordWordCCGTagMap("SecondStackWordCCGTagNextWord");
//		m_mapSSTwN0w = new TwoWordsMap("SecondStackWordNextWord");
//
//		m_mapSTptN0pt = new POSTagSet2Map("StackPOSTagNextPOSTag");
//		m_mapN0ptN1pt = new POSTagSet2Map("NextPOSTagNext+1POSTag");
//		m_mapN0ptN1ptN2pt = new POSTagSet3Map("NextPOSTagTrigram");
//		m_mapSTptN0ptN1pt = new POSTagSet3Map("StackPOSTagNextPOSTagNext+1POSTag");
//		m_mapSTptN0ptN0LDpt = new POSTagSet3Map("StackPOSTagNextPOSTagNextLDPOSTag");
//		m_mapN0ptN0LDptN0L2Dpt = new POSTagSet3Map("StackPOSTagNextPOSTagNextLDPOSTagNextPOSTagNextL2DPOSTag");
//		m_mapSTHptSTptN0pt = new POSTagSet3Map("StackHeadPOSTagStackPOSTagNextPOSTag");
//		m_mapSTHHptSTHptSTpt = new POSTagSet3Map("StackHeadHeadPOSTagStackHeadPOSTagStackPOSTag");
//		m_mapSTptSTLDptN0pt = new POSTagSet3Map("StackPOSTagStackLDPOSTagNextPOSTag");
//		m_mapSTptSTLDptSTL2Dpt = new POSTagSet3Map("StackPOSTagStackLDPOSTagStackL2DPOSTag");
//		m_mapSTptSTRDptN0pt = new POSTagSet3Map("StackPOSTagStackRDPOSTagNextPOSTag");
//		m_mapSTptSTRDptSTR2Dpt = new POSTagSet3Map("StackPOSTagStackRDPOSTagStackR2DPOSTag");
//
//		m_mapSSTptN0pt = new POSTagSet2Map("SecondStackPOSTagNextPOSTag");
//		m_mapSSTptN0ptN1pt = new POSTagSet3Map("SecondStackPOSTagNextPOSTagNext+1POSTag");
//		m_mapSSTptN0ptN0LDpt = new POSTagSet3Map("SecondStackPOSTagNextPOSTagNextLDPOSTag");
//		m_mapSSTHptSSTptN0pt = new POSTagSet3Map("SecondStackHeadPOSTagStackPOSTagNextPOSTag");
//		m_mapSSTHHptSSTHptSSTpt = new POSTagSet3Map("SecondStackHeadHeadPOSTagStackHeadPOSTagStackPOSTag");
//		m_mapSSTptSSTLDptN0pt = new POSTagSet3Map("SecondStackPOSTagStackLDPOSTagNextPOSTag");
//		m_mapSSTptSSTLDptSSTL2Dpt = new POSTagSet3Map("SecondStackPOSTagStackLDPOSTagStackL2DPOSTag");
//		m_mapSSTptSSTRDptN0pt = new POSTagSet3Map("SecondStackPOSTagStackRDPOSTagNextPOSTag");
//		m_mapSSTptSSTRDptSSTR2Dpt = new POSTagSet3Map("SecondStackPOSTagStackRDPOSTagStackR2DPOSTag");
//
//		m_mapSTptSSTptSTHpt = new POSTagSet3Map("StackPOSTagSecondStackPOSTagStackHeadPOSTag");
//		m_mapSTptSSTptSSTHpt = new POSTagSet3Map("StackPOSTagSecondStackPOSTagSecondStackHeadPOSTag");
//		m_mapSTptSSTptSTLDpt = new POSTagSet3Map("StackPOSTagSecondStackPOSTagStackLDPOSTag");
//		m_mapSTptSSTptSSTLDpt = new POSTagSet3Map("StackPOSTagSecondStackPOSTagSecondStackLDPOSTag");
//		m_mapSTptSSTptSTRDpt = new POSTagSet3Map("StackPOSTagSecondStackPOSTagStackRDPOSTag");
//		m_mapSTptSSTptSSTRDpt = new POSTagSet3Map("StackPOSTagSecondStackPOSTagSecondStackRDPOSTag");
//
//		m_mapSTHHctSTHctSTct = new CCGTagSet3Map("StackHeadHeadCCGTagStackHeadCCGTagStackCCGTag");
//		m_mapSTctSTLDctSTL2Dct = new CCGTagSet3Map("StackCCGTagStackLDCCGTagStackL2DCCGTag");
//		m_mapSTctSTRDctSTR2Dct = new CCGTagSet3Map("StackCCGTagStackRDCCGTagStackR2DCCGTag");
//
//		m_mapSSTHHctSSTHctSSTct = new CCGTagSet3Map("SecondStackHeadHeadCCGTagStackHeadCCGTagStackCCGTag");
//		m_mapSSTctSSTLDctSSTL2Dct = new CCGTagSet3Map("SecondStackCCGTagStackLDCCGTagStackL2DCCGTag");
//		m_mapSSTctSSTRDctSSTR2Dct = new CCGTagSet3Map("SecondStackCCGTagStackRDCCGTagStackR2DCCGTag");
//		
//		m_mapSTctSSTctSTHct = new CCGTagSet3Map("StackCCGTagSecondStackCCGTagStackHeadCCGTag");
//		m_mapSTctSSTctSSTHct = new CCGTagSet3Map("StackCCGTagSecondStackCCGTagSecondStackHeadCCGTag");
//		m_mapSTctSSTctSTLDct = new CCGTagSet3Map("StackCCGTagSecondStackCCGTagStackLDCCGTag");
//		m_mapSTctSSTctSSTLDct = new CCGTagSet3Map("StackCCGTagSecondStackCCGTagSecondStackLDCCGTag");
//
//		m_mapSTwd = new WordIntMap("StackWordDist");
//		m_mapSTptd = new POSTagIntMap("StackPOSTagDist");
//		m_mapSTctd = new CCGTagIntMap("StackCCGTagDist");
//		m_mapN0wd = new WordIntMap("NextWordDist");
//		m_mapN0ptd = new POSTagIntMap("NextPOSTagDist");
//		m_mapSN0wd = new WordIntMap("SecondNextWordDist");
//		m_mapSN0ptd = new POSTagIntMap("SecondPOSTagDist");
//		m_mapSTwN0wd = new WordWordIntMap("StackWordNextWordDist");
//		m_mapSTptN0ptd = new POSTagPOSTagIntMap("StackPOSTagNextPOSTagDist");
//
//		m_mapSSTwd = new WordIntMap("SecondStackWordDist");
//		m_mapSSTptd = new POSTagIntMap("SecondStackPOSTagDist");
//		m_mapSSTctd = new CCGTagIntMap("SecondStackCCGTagDist");
//		m_mapSSTwN0wd = new WordWordIntMap("SecondStackWordNextWordDist");
//		m_mapSSTptN0ptd = new POSTagPOSTagIntMap("SecondStackPOSTagNextPOSTagDist");
//		
//		m_mapSTwra = new WordIntMap("StackWordRightArity");
//		m_mapSTptra = new POSTagIntMap("StackPOSTagRightArity");
//		m_mapSTctra = new CCGTagIntMap("StackCCGTagRightArity");
//		m_mapSTwla = new WordIntMap("StackWordLeftArity");
//		m_mapSTptla = new POSTagIntMap("StackPOSTagLeftArity");
//		m_mapSTctla = new CCGTagIntMap("StackCCGTagLeftArity");
//		m_mapN0wla = new WordIntMap("NextWordRightArity");
//		m_mapN0ptla = new POSTagIntMap("NextPOSTagRightArity");
//
//		m_mapSSTwra = new WordIntMap("SecondStackWordRightArity");
//		m_mapSSTptra = new POSTagIntMap("SecondStackPOSTagRightArity");
//		m_mapSSTctra = new CCGTagIntMap("SecondStackCCGTagRightArity");
//		m_mapSSTwla = new WordIntMap("SecondStackWordLeftArity");
//		m_mapSSTptla = new POSTagIntMap("SecondStackPOSTagLeftArity");
//		m_mapSSTctla = new CCGTagIntMap("SecondStackCCGTagLeftArity");
//		
//		m_mapSTwrp = new WordSetOfDepLabelsMap("StackWordRightSetoftags");
//		m_mapSTptrp = new POSTagSetOfDepLabelsMap("StackPOSTagRightSetoftags");
//		m_mapSTctrp = new SetOfDepLabelsIntMap("StackCCGTagRightSetoftags");
//		
//		m_mapSSTwrp = new WordSetOfDepLabelsMap("SecondStackWordRightSetoftags");
//		m_mapSSTptrp = new POSTagSetOfDepLabelsMap("SecondStackPOSTagRightSetoftags");
//		m_mapSSTctrp = new SetOfDepLabelsIntMap("SecondStackCCGTagRightSetoftags");
//		
//		m_mapSTwlp = new WordSetOfDepLabelsMap("StackWordLeftSetoftags");
//		m_mapSTwlc = new WordSetOfCCGLabelsMap("StackWordLeftSetOfccgs");
//		m_mapSTptlp = new POSTagSetOfDepLabelsMap("StackPOSTagLeftSetoftags");
//		m_mapSTptlc = new POSTagSetOfCCGLabelsMap("StackPOSTagLeftSetofccgs");
//		m_mapSTctlp = new SetOfDepLabelsIntMap("StackCCGTagLeftSetoftags");
//		m_mapSTctlc = new SetOfCCGLabelsIntMap("StackCCGTagLeftSetofccgs");
//
//		m_mapSSTwlp = new WordSetOfDepLabelsMap("SecondStackWordLeftSetoftags");
//		m_mapSSTwlc = new WordSetOfCCGLabelsMap("SecondStackWordLeftSetOfccgs");
//		m_mapSSTptlp = new POSTagSetOfDepLabelsMap("SecondStackPOSTagLeftSetoftags");
//		m_mapSSTptlc = new POSTagSetOfCCGLabelsMap("SecondStackPOSTagLeftSetofccgs");
//		m_mapSSTctlp = new SetOfDepLabelsIntMap("SecondStackCCGTagLeftSetoftags");
//		m_mapSSTctlc = new SetOfCCGLabelsIntMap("SecondStackCCGTagLeftSetofccgs");
//		
//		m_mapN0wlp = new WordSetOfDepLabelsMap("Next0WordLeftSetoftags");
//		m_mapN0wlc = new WordSetOfCCGLabelsMap("Next0WordLeftSetofccgs");
//		m_mapN0ptlp = new POSTagSetOfDepLabelsMap("Next0POSTagLeftSetoftags");
//		m_mapN0ptlc = new POSTagSetOfCCGLabelsMap("Next0POSTagLeftSetofccgs");
//		
//		loadScores();
//	}
//	
//	@Override
//	public void loadScores() {
//		long time_start = System.currentTimeMillis();
//		System.out.print("Loading scores...");
//		System.out.flush();
//		File file = new File(m_sRecordPath);
//		if (!file.exists()) {
//			System.out.println("empty.");
//			return;
//		}
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//			br.readLine();
//			br.readLine();
//			br.readLine();
//			
//			m_mapSTw.loadScoresFromFileStream(br);
//			m_mapSTpt.loadScoresFromFileStream(br);
//			m_mapSTwpt.loadScoresFromFileStream(br);
//			m_mapSTct.loadScoresFromFileStream(br);
//			m_mapSTwct.loadScoresFromFileStream(br);
//
//			m_mapSSTw.loadScoresFromFileStream(br);
//			m_mapSSTpt.loadScoresFromFileStream(br);
//			m_mapSSTwpt.loadScoresFromFileStream(br);
//			m_mapSSTct.loadScoresFromFileStream(br);
//			m_mapSSTwct.loadScoresFromFileStream(br);
//
//			m_mapN0w.loadScoresFromFileStream(br);
//			m_mapN0pt.loadScoresFromFileStream(br);
//			m_mapN0wpt.loadScoresFromFileStream(br);
//
//			m_mapN1w.loadScoresFromFileStream(br);
//			m_mapN1pt.loadScoresFromFileStream(br);
//			m_mapN1wpt.loadScoresFromFileStream(br);
//
//			m_mapN2w.loadScoresFromFileStream(br);
//			m_mapN2pt.loadScoresFromFileStream(br);
//			m_mapN2wpt.loadScoresFromFileStream(br);
//
//			m_mapSTHw.loadScoresFromFileStream(br);
//			m_mapSTHpt.loadScoresFromFileStream(br);
//			m_mapSTHct.loadScoresFromFileStream(br);
//			m_mapSTi.loadScoresFromFileStream(br);
//
//			m_mapSSTHw.loadScoresFromFileStream(br);
//			m_mapSSTHpt.loadScoresFromFileStream(br);
//			m_mapSSTHct.loadScoresFromFileStream(br);
//			m_mapSSTi.loadScoresFromFileStream(br);
//
//			m_mapSTHHw.loadScoresFromFileStream(br);
//			m_mapSTHHpt.loadScoresFromFileStream(br);
//			m_mapSTHHct.loadScoresFromFileStream(br);
//			m_mapSTHi.loadScoresFromFileStream(br);
//
//			m_mapSSTHHw.loadScoresFromFileStream(br);
//			m_mapSSTHHpt.loadScoresFromFileStream(br);
//			m_mapSSTHHct.loadScoresFromFileStream(br);
//			m_mapSSTHi.loadScoresFromFileStream(br);
//
//			m_mapSTLDw.loadScoresFromFileStream(br);
//			m_mapSTLDpt.loadScoresFromFileStream(br);
//			m_mapSTLDct.loadScoresFromFileStream(br);
//			m_mapSTLDi.loadScoresFromFileStream(br);
//
//			m_mapSSTLDw.loadScoresFromFileStream(br);
//			m_mapSSTLDpt.loadScoresFromFileStream(br);
//			m_mapSSTLDct.loadScoresFromFileStream(br);
//			m_mapSSTLDi.loadScoresFromFileStream(br);
//
//			m_mapSTRDw.loadScoresFromFileStream(br);
//			m_mapSTRDpt.loadScoresFromFileStream(br);
//			m_mapSTRDct.loadScoresFromFileStream(br);
//			m_mapSTRDi.loadScoresFromFileStream(br);
//
//			m_mapSSTRDw.loadScoresFromFileStream(br);
//			m_mapSSTRDpt.loadScoresFromFileStream(br);
//			m_mapSSTRDct.loadScoresFromFileStream(br);
//			m_mapSSTRDi.loadScoresFromFileStream(br);
//
//			m_mapN0LDw.loadScoresFromFileStream(br);
//			m_mapN0LDpt.loadScoresFromFileStream(br);
//			m_mapN0LDct.loadScoresFromFileStream(br);
//			m_mapN0LDi.loadScoresFromFileStream(br);
//
//			m_mapSTL2Dw.loadScoresFromFileStream(br);
//			m_mapSTL2Dpt.loadScoresFromFileStream(br);
//			m_mapSTL2Dct.loadScoresFromFileStream(br);
//			m_mapSTL2Di.loadScoresFromFileStream(br);
//
//			m_mapSSTL2Dw.loadScoresFromFileStream(br);
//			m_mapSSTL2Dpt.loadScoresFromFileStream(br);
//			m_mapSSTL2Dct.loadScoresFromFileStream(br);
//			m_mapSSTL2Di.loadScoresFromFileStream(br);
//
//			m_mapSTR2Dw.loadScoresFromFileStream(br);
//			m_mapSTR2Dpt.loadScoresFromFileStream(br);
//			m_mapSTR2Dct.loadScoresFromFileStream(br);
//			m_mapSTR2Di.loadScoresFromFileStream(br);
//
//			m_mapSSTR2Dw.loadScoresFromFileStream(br);
//			m_mapSSTR2Dpt.loadScoresFromFileStream(br);
//			m_mapSSTR2Dct.loadScoresFromFileStream(br);
//			m_mapSSTR2Di.loadScoresFromFileStream(br);
//
//			m_mapN0L2Dw.loadScoresFromFileStream(br);
//			m_mapN0L2Dpt.loadScoresFromFileStream(br);
//			m_mapN0L2Dct.loadScoresFromFileStream(br);
//			m_mapN0L2Di.loadScoresFromFileStream(br);
//
//			m_mapSTwptN0wpt.loadScoresFromFileStream(br);
//			m_mapSTwptN0w.loadScoresFromFileStream(br);
//			m_mapSTwN0wpt.loadScoresFromFileStream(br);
//			m_mapSTptN0wpt.loadScoresFromFileStream(br);
//			m_mapSTwptN0pt.loadScoresFromFileStream(br);
//			m_mapSTwctN0w.loadScoresFromFileStream(br);
//			m_mapSTwN0w.loadScoresFromFileStream(br);
//
//			m_mapSSTwptN0wpt.loadScoresFromFileStream(br);
//			m_mapSSTwptN0w.loadScoresFromFileStream(br);
//			m_mapSSTwN0wpt.loadScoresFromFileStream(br);
//			m_mapSSTptN0wpt.loadScoresFromFileStream(br);
//			m_mapSSTwptN0pt.loadScoresFromFileStream(br);
//			m_mapSSTwctN0w.loadScoresFromFileStream(br);
//			m_mapSSTwN0w.loadScoresFromFileStream(br);
//
//			m_mapSTptN0pt.loadScoresFromFileStream(br);
//			m_mapN0ptN1pt.loadScoresFromFileStream(br);
//			m_mapN0ptN1ptN2pt.loadScoresFromFileStream(br);
//			m_mapSTptN0ptN1pt.loadScoresFromFileStream(br);
//			m_mapSTptN0ptN0LDpt.loadScoresFromFileStream(br);
//			m_mapN0ptN0LDptN0L2Dpt.loadScoresFromFileStream(br);
//			m_mapSTHptSTptN0pt.loadScoresFromFileStream(br);
//			m_mapSTHHptSTHptSTpt.loadScoresFromFileStream(br);
//			m_mapSTptSTLDptN0pt.loadScoresFromFileStream(br);
//			m_mapSTptSTLDptSTL2Dpt.loadScoresFromFileStream(br);
//			m_mapSTptSTRDptN0pt.loadScoresFromFileStream(br);
//			m_mapSTptSTRDptSTR2Dpt.loadScoresFromFileStream(br);
//
//			m_mapSSTptN0pt.loadScoresFromFileStream(br);
//			m_mapSSTptN0ptN1pt.loadScoresFromFileStream(br);
//			m_mapSSTptN0ptN0LDpt.loadScoresFromFileStream(br);
//			m_mapSSTHptSSTptN0pt.loadScoresFromFileStream(br);
//			m_mapSSTHHptSSTHptSSTpt.loadScoresFromFileStream(br);
//			m_mapSSTptSSTLDptN0pt.loadScoresFromFileStream(br);
//			m_mapSSTptSSTLDptSSTL2Dpt.loadScoresFromFileStream(br);
//			m_mapSSTptSSTRDptN0pt.loadScoresFromFileStream(br);
//			m_mapSSTptSSTRDptSSTR2Dpt.loadScoresFromFileStream(br);
//
//			m_mapSTptSSTptSTHpt.loadScoresFromFileStream(br);
//			m_mapSTptSSTptSSTHpt.loadScoresFromFileStream(br);
//			m_mapSTptSSTptSTLDpt.loadScoresFromFileStream(br);
//			m_mapSTptSSTptSSTLDpt.loadScoresFromFileStream(br);
//			m_mapSTptSSTptSTRDpt.loadScoresFromFileStream(br);
//			m_mapSTptSSTptSSTRDpt.loadScoresFromFileStream(br);
//
//			m_mapSTHHctSTHctSTct.loadScoresFromFileStream(br);
//			m_mapSTctSTLDctSTL2Dct.loadScoresFromFileStream(br);
//			m_mapSTctSTRDctSTR2Dct.loadScoresFromFileStream(br);
//
//			m_mapSSTHHctSSTHctSSTct.loadScoresFromFileStream(br);
//			m_mapSSTctSSTLDctSSTL2Dct.loadScoresFromFileStream(br);
//			m_mapSSTctSSTRDctSSTR2Dct.loadScoresFromFileStream(br);
//			
//			m_mapSTctSSTctSTHct.loadScoresFromFileStream(br);
//			m_mapSTctSSTctSSTHct.loadScoresFromFileStream(br);
//			m_mapSTctSSTctSTLDct.loadScoresFromFileStream(br);
//			m_mapSTctSSTctSSTLDct.loadScoresFromFileStream(br);
//
//			m_mapSTwd.loadScoresFromFileStream(br);
//			m_mapSTptd.loadScoresFromFileStream(br);
//			m_mapSTctd.loadScoresFromFileStream(br);
//			m_mapN0wd.loadScoresFromFileStream(br);
//			m_mapN0ptd.loadScoresFromFileStream(br);
//			m_mapSN0wd.loadScoresFromFileStream(br);
//			m_mapSN0ptd.loadScoresFromFileStream(br);
//			m_mapSTwN0wd.loadScoresFromFileStream(br);
//			m_mapSTptN0ptd.loadScoresFromFileStream(br);
//
//			m_mapSSTwd.loadScoresFromFileStream(br);
//			m_mapSSTptd.loadScoresFromFileStream(br);
//			m_mapSSTctd.loadScoresFromFileStream(br);
//			m_mapSSTwN0wd.loadScoresFromFileStream(br);
//			m_mapSSTptN0ptd.loadScoresFromFileStream(br);
//			
//			m_mapSTwra.loadScoresFromFileStream(br);
//			m_mapSTptra.loadScoresFromFileStream(br);
//			m_mapSTctra.loadScoresFromFileStream(br);
//			m_mapSTwla.loadScoresFromFileStream(br);
//			m_mapSTptla.loadScoresFromFileStream(br);
//			m_mapSTctla.loadScoresFromFileStream(br);
//			m_mapN0wla.loadScoresFromFileStream(br);
//			m_mapN0ptla.loadScoresFromFileStream(br);
//
//			m_mapSSTwra.loadScoresFromFileStream(br);
//			m_mapSSTptra.loadScoresFromFileStream(br);
//			m_mapSSTctra.loadScoresFromFileStream(br);
//			m_mapSSTwla.loadScoresFromFileStream(br);
//			m_mapSSTptla.loadScoresFromFileStream(br);
//			m_mapSSTctla.loadScoresFromFileStream(br);
//			
//			m_mapSTwrp.loadScoresFromFileStream(br);
//			m_mapSTptrp.loadScoresFromFileStream(br);
//			m_mapSTctrp.loadScoresFromFileStream(br);
//			
//			m_mapSSTwrp.loadScoresFromFileStream(br);
//			m_mapSSTptrp.loadScoresFromFileStream(br);
//			m_mapSSTctrp.loadScoresFromFileStream(br);
//			
//			m_mapSTwlp.loadScoresFromFileStream(br);
//			m_mapSTwlc.loadScoresFromFileStream(br);
//			m_mapSTptlp.loadScoresFromFileStream(br);
//			m_mapSTptlc.loadScoresFromFileStream(br);
//			m_mapSTctlp.loadScoresFromFileStream(br);
//			m_mapSTctlc.loadScoresFromFileStream(br);
//
//			m_mapSSTwlp.loadScoresFromFileStream(br);
//			m_mapSSTwlc.loadScoresFromFileStream(br);
//			m_mapSSTptlp.loadScoresFromFileStream(br);
//			m_mapSSTptlc.loadScoresFromFileStream(br);
//			m_mapSSTctlp.loadScoresFromFileStream(br);
//			m_mapSSTctlc.loadScoresFromFileStream(br);
//			
//			m_mapN0wlp.loadScoresFromFileStream(br);
//			m_mapN0wlc.loadScoresFromFileStream(br);
//			m_mapN0ptlp.loadScoresFromFileStream(br);
//			m_mapN0ptlc.loadScoresFromFileStream(br);
//			
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("done. (" + ((System.currentTimeMillis() - time_start) / 1000.0) + "s)");
//	}
//	
//	@Override
//	public void saveScores() {
//		System.out.println("Saving scores...");
//		System.out.flush();
//		File file = new File(m_sRecordPath);
//		try {
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//			bw.write("Dependency labels:");
//			bw.newLine();
//			for (int i = Macros.DEP_FIRST; i < Macros.DEP_COUNT; ++i) {
//				bw.write(Macros.DEP_STRINGS[i] + " ");
//			}
//			bw.newLine();
//			bw.newLine();
//			
//			m_mapSTw.saveScoresToFileStream(bw);
//			m_mapSTpt.saveScoresToFileStream(bw);
//			m_mapSTwpt.saveScoresToFileStream(bw);
//			m_mapSTct.saveScoresToFileStream(bw);
//			m_mapSTwct.saveScoresToFileStream(bw);
//
//			m_mapSSTw.saveScoresToFileStream(bw);
//			m_mapSSTpt.saveScoresToFileStream(bw);
//			m_mapSSTwpt.saveScoresToFileStream(bw);
//			m_mapSSTct.saveScoresToFileStream(bw);
//			m_mapSSTwct.saveScoresToFileStream(bw);
//
//			m_mapN0w.saveScoresToFileStream(bw);
//			m_mapN0pt.saveScoresToFileStream(bw);
//			m_mapN0wpt.saveScoresToFileStream(bw);
//
//			m_mapN1w.saveScoresToFileStream(bw);
//			m_mapN1pt.saveScoresToFileStream(bw);
//			m_mapN1wpt.saveScoresToFileStream(bw);
//
//			m_mapN2w.saveScoresToFileStream(bw);
//			m_mapN2pt.saveScoresToFileStream(bw);
//			m_mapN2wpt.saveScoresToFileStream(bw);
//
//			m_mapSTHw.saveScoresToFileStream(bw);
//			m_mapSTHpt.saveScoresToFileStream(bw);
//			m_mapSTHct.saveScoresToFileStream(bw);
//			m_mapSTi.saveScoresToFileStream(bw);
//
//			m_mapSSTHw.saveScoresToFileStream(bw);
//			m_mapSSTHpt.saveScoresToFileStream(bw);
//			m_mapSSTHct.saveScoresToFileStream(bw);
//			m_mapSSTi.saveScoresToFileStream(bw);
//
//			m_mapSTHHw.saveScoresToFileStream(bw);
//			m_mapSTHHpt.saveScoresToFileStream(bw);
//			m_mapSTHHct.saveScoresToFileStream(bw);
//			m_mapSTHi.saveScoresToFileStream(bw);
//
//			m_mapSSTHHw.saveScoresToFileStream(bw);
//			m_mapSSTHHpt.saveScoresToFileStream(bw);
//			m_mapSSTHHct.saveScoresToFileStream(bw);
//			m_mapSSTHi.saveScoresToFileStream(bw);
//
//			m_mapSTLDw.saveScoresToFileStream(bw);
//			m_mapSTLDpt.saveScoresToFileStream(bw);
//			m_mapSTLDct.saveScoresToFileStream(bw);
//			m_mapSTLDi.saveScoresToFileStream(bw);
//
//			m_mapSSTLDw.saveScoresToFileStream(bw);
//			m_mapSSTLDpt.saveScoresToFileStream(bw);
//			m_mapSSTLDct.saveScoresToFileStream(bw);
//			m_mapSSTLDi.saveScoresToFileStream(bw);
//
//			m_mapSTRDw.saveScoresToFileStream(bw);
//			m_mapSTRDpt.saveScoresToFileStream(bw);
//			m_mapSTRDct.saveScoresToFileStream(bw);
//			m_mapSTRDi.saveScoresToFileStream(bw);
//
//			m_mapSSTRDw.saveScoresToFileStream(bw);
//			m_mapSSTRDpt.saveScoresToFileStream(bw);
//			m_mapSSTRDct.saveScoresToFileStream(bw);
//			m_mapSSTRDi.saveScoresToFileStream(bw);
//
//			m_mapN0LDw.saveScoresToFileStream(bw);
//			m_mapN0LDpt.saveScoresToFileStream(bw);
//			m_mapN0LDct.saveScoresToFileStream(bw);
//			m_mapN0LDi.saveScoresToFileStream(bw);
//
//			m_mapSTL2Dw.saveScoresToFileStream(bw);
//			m_mapSTL2Dpt.saveScoresToFileStream(bw);
//			m_mapSTL2Dct.saveScoresToFileStream(bw);
//			m_mapSTL2Di.saveScoresToFileStream(bw);
//
//			m_mapSSTL2Dw.saveScoresToFileStream(bw);
//			m_mapSSTL2Dpt.saveScoresToFileStream(bw);
//			m_mapSSTL2Dct.saveScoresToFileStream(bw);
//			m_mapSSTL2Di.saveScoresToFileStream(bw);
//
//			m_mapSTR2Dw.saveScoresToFileStream(bw);
//			m_mapSTR2Dpt.saveScoresToFileStream(bw);
//			m_mapSTR2Dct.saveScoresToFileStream(bw);
//			m_mapSTR2Di.saveScoresToFileStream(bw);
//
//			m_mapSSTR2Dw.saveScoresToFileStream(bw);
//			m_mapSSTR2Dpt.saveScoresToFileStream(bw);
//			m_mapSSTR2Dct.saveScoresToFileStream(bw);
//			m_mapSSTR2Di.saveScoresToFileStream(bw);
//
//			m_mapN0L2Dw.saveScoresToFileStream(bw);
//			m_mapN0L2Dpt.saveScoresToFileStream(bw);
//			m_mapN0L2Dct.saveScoresToFileStream(bw);
//			m_mapN0L2Di.saveScoresToFileStream(bw);
//
//			m_mapSTwptN0wpt.saveScoresToFileStream(bw);
//			m_mapSTwptN0w.saveScoresToFileStream(bw);
//			m_mapSTwN0wpt.saveScoresToFileStream(bw);
//			m_mapSTptN0wpt.saveScoresToFileStream(bw);
//			m_mapSTwptN0pt.saveScoresToFileStream(bw);
//			m_mapSTwctN0w.saveScoresToFileStream(bw);
//			m_mapSTwN0w.saveScoresToFileStream(bw);
//
//			m_mapSSTwptN0wpt.saveScoresToFileStream(bw);
//			m_mapSSTwptN0w.saveScoresToFileStream(bw);
//			m_mapSSTwN0wpt.saveScoresToFileStream(bw);
//			m_mapSSTptN0wpt.saveScoresToFileStream(bw);
//			m_mapSSTwptN0pt.saveScoresToFileStream(bw);
//			m_mapSSTwctN0w.saveScoresToFileStream(bw);
//			m_mapSSTwN0w.saveScoresToFileStream(bw);
//
//			m_mapSTptN0pt.saveScoresToFileStream(bw);
//			m_mapN0ptN1pt.saveScoresToFileStream(bw);
//			m_mapN0ptN1ptN2pt.saveScoresToFileStream(bw);
//			m_mapSTptN0ptN1pt.saveScoresToFileStream(bw);
//			m_mapSTptN0ptN0LDpt.saveScoresToFileStream(bw);
//			m_mapN0ptN0LDptN0L2Dpt.saveScoresToFileStream(bw);
//			m_mapSTHptSTptN0pt.saveScoresToFileStream(bw);
//			m_mapSTHHptSTHptSTpt.saveScoresToFileStream(bw);
//			m_mapSTptSTLDptN0pt.saveScoresToFileStream(bw);
//			m_mapSTptSTLDptSTL2Dpt.saveScoresToFileStream(bw);
//			m_mapSTptSTRDptN0pt.saveScoresToFileStream(bw);
//			m_mapSTptSTRDptSTR2Dpt.saveScoresToFileStream(bw);
//
//			m_mapSSTptN0pt.saveScoresToFileStream(bw);
//			m_mapSSTptN0ptN1pt.saveScoresToFileStream(bw);
//			m_mapSSTptN0ptN0LDpt.saveScoresToFileStream(bw);
//			m_mapSSTHptSSTptN0pt.saveScoresToFileStream(bw);
//			m_mapSSTHHptSSTHptSSTpt.saveScoresToFileStream(bw);
//			m_mapSSTptSSTLDptN0pt.saveScoresToFileStream(bw);
//			m_mapSSTptSSTLDptSSTL2Dpt.saveScoresToFileStream(bw);
//			m_mapSSTptSSTRDptN0pt.saveScoresToFileStream(bw);
//			m_mapSSTptSSTRDptSSTR2Dpt.saveScoresToFileStream(bw);
//
//			m_mapSTptSSTptSTHpt.saveScoresToFileStream(bw);
//			m_mapSTptSSTptSSTHpt.saveScoresToFileStream(bw);
//			m_mapSTptSSTptSTLDpt.saveScoresToFileStream(bw);
//			m_mapSTptSSTptSSTLDpt.saveScoresToFileStream(bw);
//			m_mapSTptSSTptSTRDpt.saveScoresToFileStream(bw);
//			m_mapSTptSSTptSSTRDpt.saveScoresToFileStream(bw);
//
//			m_mapSTHHctSTHctSTct.saveScoresToFileStream(bw);
//			m_mapSTctSTLDctSTL2Dct.saveScoresToFileStream(bw);
//			m_mapSTctSTRDctSTR2Dct.saveScoresToFileStream(bw);
//
//			m_mapSSTHHctSSTHctSSTct.saveScoresToFileStream(bw);
//			m_mapSSTctSSTLDctSSTL2Dct.saveScoresToFileStream(bw);
//			m_mapSSTctSSTRDctSSTR2Dct.saveScoresToFileStream(bw);
//			
//			m_mapSTctSSTctSTHct.saveScoresToFileStream(bw);
//			m_mapSTctSSTctSSTHct.saveScoresToFileStream(bw);
//			m_mapSTctSSTctSTLDct.saveScoresToFileStream(bw);
//			m_mapSTctSSTctSSTLDct.saveScoresToFileStream(bw);
//
//			m_mapSTwd.saveScoresToFileStream(bw);
//			m_mapSTptd.saveScoresToFileStream(bw);
//			m_mapSTctd.saveScoresToFileStream(bw);
//			m_mapN0wd.saveScoresToFileStream(bw);
//			m_mapN0ptd.saveScoresToFileStream(bw);
//			m_mapSN0wd.saveScoresToFileStream(bw);
//			m_mapSN0ptd.saveScoresToFileStream(bw);
//			m_mapSTwN0wd.saveScoresToFileStream(bw);
//			m_mapSTptN0ptd.saveScoresToFileStream(bw);
//
//			m_mapSSTwd.saveScoresToFileStream(bw);
//			m_mapSSTptd.saveScoresToFileStream(bw);
//			m_mapSSTctd.saveScoresToFileStream(bw);
//			m_mapSSTwN0wd.saveScoresToFileStream(bw);
//			m_mapSSTptN0ptd.saveScoresToFileStream(bw);
//			
//			m_mapSTwra.saveScoresToFileStream(bw);
//			m_mapSTptra.saveScoresToFileStream(bw);
//			m_mapSTctra.saveScoresToFileStream(bw);
//			m_mapSTwla.saveScoresToFileStream(bw);
//			m_mapSTptla.saveScoresToFileStream(bw);
//			m_mapSTctla.saveScoresToFileStream(bw);
//			m_mapN0wla.saveScoresToFileStream(bw);
//			m_mapN0ptla.saveScoresToFileStream(bw);
//
//			m_mapSSTwra.saveScoresToFileStream(bw);
//			m_mapSSTptra.saveScoresToFileStream(bw);
//			m_mapSSTctra.saveScoresToFileStream(bw);
//			m_mapSSTwla.saveScoresToFileStream(bw);
//			m_mapSSTptla.saveScoresToFileStream(bw);
//			m_mapSSTctla.saveScoresToFileStream(bw);
//			
//			m_mapSTwrp.saveScoresToFileStream(bw);
//			m_mapSTptrp.saveScoresToFileStream(bw);
//			m_mapSTctrp.saveScoresToFileStream(bw);
//			
//			m_mapSSTwrp.saveScoresToFileStream(bw);
//			m_mapSSTptrp.saveScoresToFileStream(bw);
//			m_mapSSTctrp.saveScoresToFileStream(bw);
//			
//			m_mapSTwlp.saveScoresToFileStream(bw);
//			m_mapSTwlc.saveScoresToFileStream(bw);
//			m_mapSTptlp.saveScoresToFileStream(bw);
//			m_mapSTptlc.saveScoresToFileStream(bw);
//			m_mapSTctlp.saveScoresToFileStream(bw);
//			m_mapSTctlc.saveScoresToFileStream(bw);
//
//			m_mapSSTwlp.saveScoresToFileStream(bw);
//			m_mapSSTwlc.saveScoresToFileStream(bw);
//			m_mapSSTptlp.saveScoresToFileStream(bw);
//			m_mapSSTptlc.saveScoresToFileStream(bw);
//			m_mapSSTctlp.saveScoresToFileStream(bw);
//			m_mapSSTctlc.saveScoresToFileStream(bw);
//			
//			m_mapN0wlp.saveScoresToFileStream(bw);
//			m_mapN0wlc.saveScoresToFileStream(bw);
//			m_mapN0ptlp.saveScoresToFileStream(bw);
//			m_mapN0ptlc.saveScoresToFileStream(bw);
//			
//			bw.newLine();
//			
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("done.");
//	}
//	
//	@Override
//	public void computeAverageFeatureWeights(final int round) {
//		System.out.println("Computing averaged (total) feature vector...");
//		System.out.flush();
//
//		m_mapSTw.computeAverage(round);
//		m_mapSTpt.computeAverage(round);
//		m_mapSTwpt.computeAverage(round);
//		m_mapSTct.computeAverage(round);
//		m_mapSTwct.computeAverage(round);
//
//		m_mapSSTw.computeAverage(round);
//		m_mapSSTpt.computeAverage(round);
//		m_mapSSTwpt.computeAverage(round);
//		m_mapSSTct.computeAverage(round);
//		m_mapSSTwct.computeAverage(round);
//
//		m_mapN0w.computeAverage(round);
//		m_mapN0pt.computeAverage(round);
//		m_mapN0wpt.computeAverage(round);
//
//		m_mapN1w.computeAverage(round);
//		m_mapN1pt.computeAverage(round);
//		m_mapN1wpt.computeAverage(round);
//
//		m_mapN2w.computeAverage(round);
//		m_mapN2pt.computeAverage(round);
//		m_mapN2wpt.computeAverage(round);
//
//		m_mapSTHw.computeAverage(round);
//		m_mapSTHpt.computeAverage(round);
//		m_mapSTHct.computeAverage(round);
//		m_mapSTi.computeAverage(round);
//
//		m_mapSSTHw.computeAverage(round);
//		m_mapSSTHpt.computeAverage(round);
//		m_mapSSTHct.computeAverage(round);
//		m_mapSSTi.computeAverage(round);
//
//		m_mapSTHHw.computeAverage(round);
//		m_mapSTHHpt.computeAverage(round);
//		m_mapSTHHct.computeAverage(round);
//		m_mapSTHi.computeAverage(round);
//
//		m_mapSSTHHw.computeAverage(round);
//		m_mapSSTHHpt.computeAverage(round);
//		m_mapSSTHHct.computeAverage(round);
//		m_mapSSTHi.computeAverage(round);
//
//		m_mapSTLDw.computeAverage(round);
//		m_mapSTLDpt.computeAverage(round);
//		m_mapSTLDct.computeAverage(round);
//		m_mapSTLDi.computeAverage(round);
//
//		m_mapSSTLDw.computeAverage(round);
//		m_mapSSTLDpt.computeAverage(round);
//		m_mapSSTLDct.computeAverage(round);
//		m_mapSSTLDi.computeAverage(round);
//
//		m_mapSTRDw.computeAverage(round);
//		m_mapSTRDpt.computeAverage(round);
//		m_mapSTRDct.computeAverage(round);
//		m_mapSTRDi.computeAverage(round);
//
//		m_mapSSTRDw.computeAverage(round);
//		m_mapSSTRDpt.computeAverage(round);
//		m_mapSSTRDct.computeAverage(round);
//		m_mapSSTRDi.computeAverage(round);
//
//		m_mapN0LDw.computeAverage(round);
//		m_mapN0LDpt.computeAverage(round);
//		m_mapN0LDct.computeAverage(round);
//		m_mapN0LDi.computeAverage(round);
//
//		m_mapSTL2Dw.computeAverage(round);
//		m_mapSTL2Dpt.computeAverage(round);
//		m_mapSTL2Dct.computeAverage(round);
//		m_mapSTL2Di.computeAverage(round);
//
//		m_mapSSTL2Dw.computeAverage(round);
//		m_mapSSTL2Dpt.computeAverage(round);
//		m_mapSSTL2Dct.computeAverage(round);
//		m_mapSSTL2Di.computeAverage(round);
//
//		m_mapSTR2Dw.computeAverage(round);
//		m_mapSTR2Dpt.computeAverage(round);
//		m_mapSTR2Dct.computeAverage(round);
//		m_mapSTR2Di.computeAverage(round);
//
//		m_mapSSTR2Dw.computeAverage(round);
//		m_mapSSTR2Dpt.computeAverage(round);
//		m_mapSSTR2Dct.computeAverage(round);
//		m_mapSSTR2Di.computeAverage(round);
//
//		m_mapN0L2Dw.computeAverage(round);
//		m_mapN0L2Dpt.computeAverage(round);
//		m_mapN0L2Dct.computeAverage(round);
//		m_mapN0L2Di.computeAverage(round);
//
//		m_mapSTwptN0wpt.computeAverage(round);
//		m_mapSTwptN0w.computeAverage(round);
//		m_mapSTwN0wpt.computeAverage(round);
//		m_mapSTptN0wpt.computeAverage(round);
//		m_mapSTwptN0pt.computeAverage(round);
//		m_mapSTwctN0w.computeAverage(round);
//		m_mapSTwN0w.computeAverage(round);
//
//		m_mapSSTwptN0wpt.computeAverage(round);
//		m_mapSSTwptN0w.computeAverage(round);
//		m_mapSSTwN0wpt.computeAverage(round);
//		m_mapSSTptN0wpt.computeAverage(round);
//		m_mapSSTwptN0pt.computeAverage(round);
//		m_mapSSTwctN0w.computeAverage(round);
//		m_mapSSTwN0w.computeAverage(round);
//
//		m_mapSTptN0pt.computeAverage(round);
//		m_mapN0ptN1pt.computeAverage(round);
//		m_mapN0ptN1ptN2pt.computeAverage(round);
//		m_mapSTptN0ptN1pt.computeAverage(round);
//		m_mapSTptN0ptN0LDpt.computeAverage(round);
//		m_mapN0ptN0LDptN0L2Dpt.computeAverage(round);
//		m_mapSTHptSTptN0pt.computeAverage(round);
//		m_mapSTHHptSTHptSTpt.computeAverage(round);
//		m_mapSTptSTLDptN0pt.computeAverage(round);
//		m_mapSTptSTLDptSTL2Dpt.computeAverage(round);
//		m_mapSTptSTRDptN0pt.computeAverage(round);
//		m_mapSTptSTRDptSTR2Dpt.computeAverage(round);
//
//		m_mapSSTptN0pt.computeAverage(round);
//		m_mapSSTptN0ptN1pt.computeAverage(round);
//		m_mapSSTptN0ptN0LDpt.computeAverage(round);
//		m_mapSSTHptSSTptN0pt.computeAverage(round);
//		m_mapSSTHHptSSTHptSSTpt.computeAverage(round);
//		m_mapSSTptSSTLDptN0pt.computeAverage(round);
//		m_mapSSTptSSTLDptSSTL2Dpt.computeAverage(round);
//		m_mapSSTptSSTRDptN0pt.computeAverage(round);
//		m_mapSSTptSSTRDptSSTR2Dpt.computeAverage(round);
//
//		m_mapSTptSSTptSTHpt.computeAverage(round);
//		m_mapSTptSSTptSSTHpt.computeAverage(round);
//		m_mapSTptSSTptSTLDpt.computeAverage(round);
//		m_mapSTptSSTptSSTLDpt.computeAverage(round);
//		m_mapSTptSSTptSTRDpt.computeAverage(round);
//		m_mapSTptSSTptSSTRDpt.computeAverage(round);
//
//		m_mapSTHHctSTHctSTct.computeAverage(round);
//		m_mapSTctSTLDctSTL2Dct.computeAverage(round);
//		m_mapSTctSTRDctSTR2Dct.computeAverage(round);
//
//		m_mapSSTHHctSSTHctSSTct.computeAverage(round);
//		m_mapSSTctSSTLDctSSTL2Dct.computeAverage(round);
//		m_mapSSTctSSTRDctSSTR2Dct.computeAverage(round);
//		
//		m_mapSTctSSTctSTHct.computeAverage(round);
//		m_mapSTctSSTctSSTHct.computeAverage(round);
//		m_mapSTctSSTctSTLDct.computeAverage(round);
//		m_mapSTctSSTctSSTLDct.computeAverage(round);
//
//		m_mapSTwd.computeAverage(round);
//		m_mapSTptd.computeAverage(round);
//		m_mapSTctd.computeAverage(round);
//		m_mapN0wd.computeAverage(round);
//		m_mapN0ptd.computeAverage(round);
//		m_mapSN0wd.computeAverage(round);
//		m_mapSN0ptd.computeAverage(round);
//		m_mapSTwN0wd.computeAverage(round);
//		m_mapSTptN0ptd.computeAverage(round);
//
//		m_mapSSTwd.computeAverage(round);
//		m_mapSSTptd.computeAverage(round);
//		m_mapSSTctd.computeAverage(round);
//		m_mapSSTwN0wd.computeAverage(round);
//		m_mapSSTptN0ptd.computeAverage(round);
//		
//		m_mapSTwra.computeAverage(round);
//		m_mapSTptra.computeAverage(round);
//		m_mapSTctra.computeAverage(round);
//		m_mapSTwla.computeAverage(round);
//		m_mapSTptla.computeAverage(round);
//		m_mapSTctla.computeAverage(round);
//		m_mapN0wla.computeAverage(round);
//		m_mapN0ptla.computeAverage(round);
//
//		m_mapSSTwra.computeAverage(round);
//		m_mapSSTptra.computeAverage(round);
//		m_mapSSTctra.computeAverage(round);
//		m_mapSSTwla.computeAverage(round);
//		m_mapSSTptla.computeAverage(round);
//		m_mapSSTctla.computeAverage(round);
//		
//		m_mapSTwrp.computeAverage(round);
//		m_mapSTptrp.computeAverage(round);
//		m_mapSTctrp.computeAverage(round);
//		
//		m_mapSSTwrp.computeAverage(round);
//		m_mapSSTptrp.computeAverage(round);
//		m_mapSSTctrp.computeAverage(round);
//		
//		m_mapSTwlp.computeAverage(round);
//		m_mapSTwlc.computeAverage(round);
//		m_mapSTptlp.computeAverage(round);
//		m_mapSTptlc.computeAverage(round);
//		m_mapSTctlp.computeAverage(round);
//		m_mapSTctlc.computeAverage(round);
//
//		m_mapSSTwlp.computeAverage(round);
//		m_mapSSTwlc.computeAverage(round);
//		m_mapSSTptlp.computeAverage(round);
//		m_mapSSTptlc.computeAverage(round);
//		m_mapSSTctlp.computeAverage(round);
//		m_mapSSTctlc.computeAverage(round);
//		
//		m_mapN0wlp.computeAverage(round);
//		m_mapN0wlc.computeAverage(round);
//		m_mapN0ptlp.computeAverage(round);
//		m_mapN0ptlc.computeAverage(round);
//		
//		System.out.println("done.");
//	}
//	
//}
