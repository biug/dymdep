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
import common.parser.implementations.map.POSTagMap;
import common.parser.implementations.map.POSTagSet2Map;
import common.parser.implementations.map.POSTagSet3Map;
import common.parser.implementations.map.POSTagSet4Map;
import common.parser.implementations.map.StringMap;
import common.parser.implementations.map.ThreeWordsMap;
import common.parser.implementations.map.TwoWordsMap;
import common.parser.implementations.map.WordMap;
import common.parser.implementations.map.WordPOSTagMap;
import common.parser.implementations.map.WordPOSTagPOSTagMap;
import common.parser.implementations.map.WordPOSTagPOSTagPOSTagMap;
import common.parser.implementations.map.WordWordPOSTagMap;
import common.parser.implementations.map.WordWordPOSTagPOSTagMap;
import common.parser.implementations.map.WordWordWordPOSTagMap;

public class Weight extends WeightBase {

	WordMap m_mapSTw;
	POSTagMap m_mapSTpt;
	WordMap m_mapST2w;
	POSTagMap m_mapST2pt;
	WordMap m_mapN0w;
	POSTagMap m_mapN0pt;
	
	TwoWordsMap m_mapSTwN0w;
	TwoWordsMap m_mapST2wN0w;
	WordPOSTagMap m_mapSTwN0pt;
	WordPOSTagMap m_mapSTptN0w;
	WordPOSTagMap m_mapST2wN0pt;
	WordPOSTagMap m_mapST2ptN0w;
	POSTagSet2Map m_mapSTptN0pt;
	POSTagSet2Map m_mapST2ptN0pt;
	
	ThreeWordsMap m_mapSTwSTRHwN0w;
	ThreeWordsMap m_mapSTwSTRDwN0w;
	ThreeWordsMap m_mapSTwN0wN0LHw;
	ThreeWordsMap m_mapSTwN0wN0LDw;
	
	ThreeWordsMap m_mapST2wST2RHwN0w;
	ThreeWordsMap m_mapST2wST2RDwN0w;
	ThreeWordsMap m_mapST2wN0wN0LHw;
	ThreeWordsMap m_mapST2wN0wN0LDw;
	
	WordWordPOSTagMap m_mapSTwSTRHwN0pt;
	WordWordPOSTagMap m_mapSTwSTRHptN0w;
	WordWordPOSTagMap m_mapSTptSTRHwN0w;
	
	WordWordPOSTagMap m_mapST2wST2RHwN0pt;
	WordWordPOSTagMap m_mapST2wST2RHptN0w;
	WordWordPOSTagMap m_mapST2ptST2RHwN0w;

	WordWordPOSTagMap m_mapSTwSTRDwN0pt;
	WordWordPOSTagMap m_mapSTwSTRDptN0w;
	WordWordPOSTagMap m_mapSTptSTRDwN0w;

	WordWordPOSTagMap m_mapST2wST2RDwN0pt;
	WordWordPOSTagMap m_mapST2wST2RDptN0w;
	WordWordPOSTagMap m_mapST2ptST2RDwN0w;

	WordWordPOSTagMap m_mapSTwN0wN0LHpt;
	WordWordPOSTagMap m_mapSTwN0ptN0LHw;
	WordWordPOSTagMap m_mapSTptN0wN0LHw;

	WordWordPOSTagMap m_mapST2wN0wN0LHpt;
	WordWordPOSTagMap m_mapST2wN0ptN0LHw;
	WordWordPOSTagMap m_mapST2ptN0wN0LHw;
	
	WordWordPOSTagMap m_mapSTwN0wN0LDpt;
	WordWordPOSTagMap m_mapSTwN0ptN0LDw;
	WordWordPOSTagMap m_mapSTptN0wN0LDw;
	
	WordWordPOSTagMap m_mapST2wN0wN0LDpt;
	WordWordPOSTagMap m_mapST2wN0ptN0LDw;
	WordWordPOSTagMap m_mapST2ptN0wN0LDw;
	
	WordPOSTagPOSTagMap m_mapSTwSTRHptN0pt;
	WordPOSTagPOSTagMap m_mapSTptSTRHptN0w;
	WordPOSTagPOSTagMap m_mapSTptSTRHwN0pt;
	
	WordPOSTagPOSTagMap m_mapST2wST2RHptN0pt;
	WordPOSTagPOSTagMap m_mapST2ptST2RHptN0w;
	WordPOSTagPOSTagMap m_mapST2ptST2RHwN0pt;

	WordPOSTagPOSTagMap m_mapSTwSTRDptN0pt;
	WordPOSTagPOSTagMap m_mapSTptSTRDptN0w;
	WordPOSTagPOSTagMap m_mapSTptSTRDwN0pt;

	WordPOSTagPOSTagMap m_mapST2wST2RDptN0pt;
	WordPOSTagPOSTagMap m_mapST2ptST2RDptN0w;
	WordPOSTagPOSTagMap m_mapST2ptST2RDwN0pt;
	
	WordPOSTagPOSTagMap m_mapSTwN0ptN0LHpt;
	WordPOSTagPOSTagMap m_mapSTptN0ptN0LHw;
	WordPOSTagPOSTagMap m_mapSTptN0wN0LHpt;

	WordPOSTagPOSTagMap m_mapST2wN0ptN0LHpt;
	WordPOSTagPOSTagMap m_mapST2ptN0ptN0LHw;
	WordPOSTagPOSTagMap m_mapST2ptN0wN0LHpt;
	
	WordPOSTagPOSTagMap m_mapSTwN0ptN0LDpt;
	WordPOSTagPOSTagMap m_mapSTptN0ptN0LDw;
	WordPOSTagPOSTagMap m_mapSTptN0wN0LDpt;

	WordPOSTagPOSTagMap m_mapST2wN0ptN0LDpt;
	WordPOSTagPOSTagMap m_mapST2ptN0ptN0LDw;
	WordPOSTagPOSTagMap m_mapST2ptN0wN0LDpt;
	
	POSTagSet3Map m_mapSTptSTRHptN0pt;
	POSTagSet3Map m_mapSTptSTRDptN0pt;
	POSTagSet3Map m_mapSTptN0ptN0LHpt;
	POSTagSet3Map m_mapSTptN0ptN0LDpt;

	POSTagSet3Map m_mapST2ptST2RHptN0pt;
	POSTagSet3Map m_mapST2ptST2RDptN0pt;
	POSTagSet3Map m_mapST2ptN0ptN0LHpt;
	POSTagSet3Map m_mapST2ptN0ptN0LDpt;
	
	WordWordWordPOSTagMap m_mapSTwSTRHwSTRDwN0pt;
	WordWordWordPOSTagMap m_mapSTwSTRHwSTRDptN0w;
	WordWordWordPOSTagMap m_mapSTwSTRHptSTRDwN0w;
	WordWordWordPOSTagMap m_mapSTptSTRHwSTRDwN0w;
	
	WordWordWordPOSTagMap m_mapSTwSTRHwSTR2HwN0pt;
	WordWordWordPOSTagMap m_mapSTwSTRHwSTR2HptN0w;
	WordWordWordPOSTagMap m_mapSTwSTRHptSTR2HwN0w;
	WordWordWordPOSTagMap m_mapSTptSTRHwSTR2HwN0w;
	
	WordWordWordPOSTagMap m_mapSTwSTRHwSTR2DwN0pt;
	WordWordWordPOSTagMap m_mapSTwSTRHwSTR2DptN0w;
	WordWordWordPOSTagMap m_mapSTwSTRHptSTR2DwN0w;
	WordWordWordPOSTagMap m_mapSTptSTRHwSTR2DwN0w;
	
	WordWordWordPOSTagMap m_mapSTwN0LHwN0LDwN0pt;
	WordWordWordPOSTagMap m_mapSTwN0LHwN0LDptN0w;
	WordWordWordPOSTagMap m_mapSTwN0LHptN0LDwN0w;
	WordWordWordPOSTagMap m_mapSTptN0LHwN0LDwN0w;
	
	WordWordWordPOSTagMap m_mapSTwN0LHwN0L2HwN0pt;
	WordWordWordPOSTagMap m_mapSTwN0LHwN0L2HptN0w;
	WordWordWordPOSTagMap m_mapSTwN0LHptN0L2HwN0w;
	WordWordWordPOSTagMap m_mapSTptN0LHwN0L2HwN0w;
	
	WordWordWordPOSTagMap m_mapSTwN0LHwN0L2DwN0pt;
	WordWordWordPOSTagMap m_mapSTwN0LHwN0L2DptN0w;
	WordWordWordPOSTagMap m_mapSTwN0LHptN0L2DwN0w;
	WordWordWordPOSTagMap m_mapSTptN0LHwN0L2DwN0w;
	
	WordWordWordPOSTagMap m_mapSTwSTLHwSTRHwN0pt;
	WordWordWordPOSTagMap m_mapSTwSTLHwSTRHptN0w;
	WordWordWordPOSTagMap m_mapSTwSTLHptSTRHwN0w;
	WordWordWordPOSTagMap m_mapSTptSTLHwSTRHwN0w;
	
	WordWordWordPOSTagMap m_mapSTwSTLDwSTRDwN0pt;
	WordWordWordPOSTagMap m_mapSTwSTLDwSTRDptN0w;
	WordWordWordPOSTagMap m_mapSTwSTLDptSTRDwN0w;
	WordWordWordPOSTagMap m_mapSTptSTLDwSTRDwN0w;
	
	WordWordPOSTagPOSTagMap m_mapSTwSTRHwSTRDptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwSTRHptSTRDptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTRHptSTRDwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTRHwSTRDptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwSTRHptSTRDwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptSTRHwSTRDwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwSTRHwSTR2HptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwSTRHptSTR2HptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTRHptSTR2HwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTRHwSTR2HptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwSTRHptSTR2HwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptSTRHwSTR2HwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwSTRDwSTR2DptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwSTRDptSTR2DptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTRDptSTR2DwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTRDwSTR2DptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwSTRDptSTR2DwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptSTRDwSTR2DwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwN0LHwN0LDptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwN0LHptN0LDptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptN0LHptN0LDwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptN0LHwN0LDptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwN0LHptN0LDwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptN0LHwN0LDwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwN0LHwN0L2HptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwN0LHptN0L2HptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptN0LHptN0L2HwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptN0LHwN0L2HptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwN0LHptN0L2HwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptN0LHwN0L2HwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwN0LDwN0L2DptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwN0LDptN0L2DptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptN0LDptN0L2DwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptN0LDwN0L2DptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwN0LDptN0L2DwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptN0LDwN0L2DwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwSTLHwSTRHptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwSTLHptSTRHptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTLHptSTRHwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTLHwSTRHptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwSTLHptSTRHwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptSTLHwSTRHwN0pt;
	
	WordWordPOSTagPOSTagMap m_mapSTwSTLDwSTRDptN0pt;
	WordWordPOSTagPOSTagMap m_mapSTwSTLDptSTRDptN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTLDptSTRDwN0w;
	WordWordPOSTagPOSTagMap m_mapSTptSTLDwSTRDptN0w;
	WordWordPOSTagPOSTagMap m_mapSTwSTLDptSTRDwN0pt;
	WordWordPOSTagPOSTagMap m_mapSTptSTLDwSTRDwN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwSTRHptSTRDptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHptSTRDptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHptSTRDwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHwSTRDptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwSTRHptSTR2HptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHptSTR2HptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHptSTR2HwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHwSTR2HptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwSTRHptSTR2DptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHptSTR2DptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHptSTR2DwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTRHwSTR2DptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwN0LHptN0LDptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHptN0LDptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHptN0LDwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHwN0LDptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwN0LHptN0L2HptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHptN0L2HptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHptN0L2HwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHwN0L2HptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwN0LHptN0L2DptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHptN0L2DptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHptN0L2DwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptN0LHwN0L2DptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwSTLHptSTRHptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTLHptSTRHptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTLHptSTRHwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTLHwSTRHptN0pt;
	
	WordPOSTagPOSTagPOSTagMap m_mapSTwSTLDptSTRDptN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTLDptSTRDptN0w;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTLDptSTRDwN0pt;
	WordPOSTagPOSTagPOSTagMap m_mapSTptSTLDwSTRDptN0pt;
	
	POSTagSet4Map m_mapSTptSTRHptSTRDptN0pt;
	POSTagSet4Map m_mapSTptSTRHptSTR2HptN0pt;
	POSTagSet4Map m_mapSTptSTRDptSTR2DptN0pt;
	POSTagSet4Map m_mapSTptN0LHptN0LDptN0pt;
	POSTagSet4Map m_mapSTptN0LHptN0L2HptN0pt;
	POSTagSet4Map m_mapSTptN0LDptN0L2DptN0pt;
	POSTagSet4Map m_mapSTptSTLHptSTRHptN0pt;
	POSTagSet4Map m_mapSTptSTLDptSTRDptN0pt;
	
	StringMap m_mapSTPOSPath;
	StringMap m_mapSTFPOSPath;
	StringMap m_mapST2POSPath;
	StringMap m_mapST2FPOSPath;
	
	public Weight(final String sPath, final boolean bTrain) {
		super(sPath, bTrain);

		
		m_mapSTw = new WordMap("m_mapSTw");
		m_mapSTpt = new POSTagMap("m_mapSTpt");
		m_mapST2w = new WordMap("m_mapST2w");
		m_mapST2pt = new POSTagMap("m_mapST2pt");
		m_mapN0w = new WordMap("m_mapN0w");
		m_mapN0pt = new POSTagMap("m_mapN0pt");
		
		m_mapSTwN0w = new TwoWordsMap("m_mapSTwN0w");
		m_mapST2wN0w = new TwoWordsMap("m_mapST2wN0w");
		m_mapSTwN0pt = new WordPOSTagMap("m_mapSTwN0pt");
		m_mapSTptN0w = new WordPOSTagMap("m_mapSTptN0w");
		m_mapST2wN0pt = new WordPOSTagMap("m_mapST2wN0pt");
		m_mapST2ptN0w = new WordPOSTagMap("m_mapST2ptN0w");
		m_mapSTptN0pt = new POSTagSet2Map("m_mapSTptN0pt");
		m_mapST2ptN0pt = new POSTagSet2Map("m_mapST2ptN0pt");
		
		m_mapSTwSTRHwN0w = new ThreeWordsMap("m_mapSTwSTRHwN0w");
		m_mapSTwSTRDwN0w = new ThreeWordsMap("m_mapSTwSTRDwN0w");
		m_mapSTwN0wN0LHw = new ThreeWordsMap("m_mapSTwN0wN0LHw");
		m_mapSTwN0wN0LDw = new ThreeWordsMap("m_mapSTwN0wN0LDw");
		
		m_mapST2wST2RHwN0w = new ThreeWordsMap("m_mapST2wST2RHwN0w");
		m_mapST2wST2RDwN0w = new ThreeWordsMap("m_mapST2wST2RDwN0w");
		m_mapST2wN0wN0LHw = new ThreeWordsMap("m_mapST2wN0wN0LHw");
		m_mapST2wN0wN0LDw = new ThreeWordsMap("m_mapST2wN0wN0LDw");
		
		m_mapSTwSTRHwN0pt = new WordWordPOSTagMap("m_mapSTwSTRHwN0pt");
		m_mapSTwSTRHptN0w = new WordWordPOSTagMap("m_mapSTwSTRHptN0w");
		m_mapSTptSTRHwN0w = new WordWordPOSTagMap("m_mapSTptSTRHwN0w");
		
		m_mapST2wST2RHwN0pt = new WordWordPOSTagMap("m_mapST2wST2RHwN0pt");
		m_mapST2wST2RHptN0w = new WordWordPOSTagMap("m_mapST2wST2RHptN0w");
		m_mapST2ptST2RHwN0w = new WordWordPOSTagMap("m_mapST2ptST2RHwN0w");

		m_mapSTwSTRDwN0pt = new WordWordPOSTagMap("m_mapSTwSTRDwN0pt");
		m_mapSTwSTRDptN0w = new WordWordPOSTagMap("m_mapSTwSTRDptN0w");
		m_mapSTptSTRDwN0w = new WordWordPOSTagMap("m_mapSTptSTRDwN0w");

		m_mapST2wST2RDwN0pt = new WordWordPOSTagMap("m_mapST2wST2RDwN0pt");
		m_mapST2wST2RDptN0w = new WordWordPOSTagMap("m_mapST2wST2RDptN0w");
		m_mapST2ptST2RDwN0w = new WordWordPOSTagMap("m_mapST2ptST2RDwN0w");

		m_mapSTwN0wN0LHpt = new WordWordPOSTagMap("m_mapSTwN0wN0LHpt");
		m_mapSTwN0ptN0LHw = new WordWordPOSTagMap("m_mapSTwN0ptN0LHw");
		m_mapSTptN0wN0LHw = new WordWordPOSTagMap("m_mapSTptN0wN0LHw");

		m_mapST2wN0wN0LHpt = new WordWordPOSTagMap("m_mapST2wN0wN0LHpt");
		m_mapST2wN0ptN0LHw = new WordWordPOSTagMap("m_mapST2wN0ptN0LHw");
		m_mapST2ptN0wN0LHw = new WordWordPOSTagMap("m_mapST2ptN0wN0LHw");
		
		m_mapSTwN0wN0LDpt = new WordWordPOSTagMap("m_mapSTwN0wN0LDpt");
		m_mapSTwN0ptN0LDw = new WordWordPOSTagMap("m_mapSTwN0ptN0LDw");
		m_mapSTptN0wN0LDw = new WordWordPOSTagMap("m_mapSTptN0wN0LDw");
		
		m_mapST2wN0wN0LDpt = new WordWordPOSTagMap("m_mapST2wN0wN0LDpt");
		m_mapST2wN0ptN0LDw = new WordWordPOSTagMap("m_mapST2wN0ptN0LDw");
		m_mapST2ptN0wN0LDw = new WordWordPOSTagMap("m_mapST2ptN0wN0LDw");
		
		m_mapSTwSTRHptN0pt = new WordPOSTagPOSTagMap("m_mapSTwSTRHptN0pt");
		m_mapSTptSTRHptN0w = new WordPOSTagPOSTagMap("m_mapSTptSTRHptN0w");
		m_mapSTptSTRHwN0pt = new WordPOSTagPOSTagMap("m_mapSTptSTRHwN0pt");
		
		m_mapST2wST2RHptN0pt = new WordPOSTagPOSTagMap("m_mapST2wST2RHptN0pt");
		m_mapST2ptST2RHptN0w = new WordPOSTagPOSTagMap("m_mapST2ptST2RHptN0w");
		m_mapST2ptST2RHwN0pt = new WordPOSTagPOSTagMap("m_mapST2ptST2RHwN0pt");

		m_mapSTwSTRDptN0pt = new WordPOSTagPOSTagMap("m_mapSTwSTRDptN0pt");
		m_mapSTptSTRDptN0w = new WordPOSTagPOSTagMap("m_mapSTptSTRDptN0w");
		m_mapSTptSTRDwN0pt = new WordPOSTagPOSTagMap("m_mapSTptSTRDwN0pt");

		m_mapST2wST2RDptN0pt = new WordPOSTagPOSTagMap("m_mapST2wST2RDptN0pt");
		m_mapST2ptST2RDptN0w = new WordPOSTagPOSTagMap("m_mapST2ptST2RDptN0w");
		m_mapST2ptST2RDwN0pt = new WordPOSTagPOSTagMap("m_mapST2ptST2RDwN0pt");
		
		m_mapSTwN0ptN0LHpt = new WordPOSTagPOSTagMap("m_mapSTwN0ptN0LHpt");
		m_mapSTptN0ptN0LHw = new WordPOSTagPOSTagMap("m_mapSTptN0ptN0LHw");
		m_mapSTptN0wN0LHpt = new WordPOSTagPOSTagMap("m_mapSTptN0wN0LHpt");

		m_mapST2wN0ptN0LHpt = new WordPOSTagPOSTagMap("m_mapST2wN0ptN0LHpt");
		m_mapST2ptN0ptN0LHw = new WordPOSTagPOSTagMap("m_mapST2ptN0ptN0LHw");
		m_mapST2ptN0wN0LHpt = new WordPOSTagPOSTagMap("m_mapST2ptN0wN0LHpt");
		
		m_mapSTwN0ptN0LDpt = new WordPOSTagPOSTagMap("m_mapSTwN0ptN0LDpt");
		m_mapSTptN0ptN0LDw = new WordPOSTagPOSTagMap("m_mapSTptN0ptN0LDw");
		m_mapSTptN0wN0LDpt = new WordPOSTagPOSTagMap("m_mapSTptN0wN0LDpt");

		m_mapST2wN0ptN0LDpt = new WordPOSTagPOSTagMap("m_mapST2wN0ptN0LDpt");
		m_mapST2ptN0ptN0LDw = new WordPOSTagPOSTagMap("m_mapST2ptN0ptN0LDw");
		m_mapST2ptN0wN0LDpt = new WordPOSTagPOSTagMap("m_mapST2ptN0wN0LDpt");
		
		m_mapSTptSTRHptN0pt = new POSTagSet3Map("m_mapSTptSTRHptN0pt");
		m_mapSTptSTRDptN0pt = new POSTagSet3Map("m_mapSTptSTRDptN0pt");
		m_mapSTptN0ptN0LHpt = new POSTagSet3Map("m_mapSTptN0ptN0LHpt");
		m_mapSTptN0ptN0LDpt = new POSTagSet3Map("m_mapSTptN0ptN0LDpt");

		m_mapST2ptST2RHptN0pt = new POSTagSet3Map("m_mapST2ptST2RHptN0pt");
		m_mapST2ptST2RDptN0pt = new POSTagSet3Map("m_mapST2ptST2RDptN0pt");
		m_mapST2ptN0ptN0LHpt = new POSTagSet3Map("m_mapST2ptN0ptN0LHpt");
		m_mapST2ptN0ptN0LDpt = new POSTagSet3Map("m_mapST2ptN0ptN0LDpt");
		
		m_mapSTwSTRHwSTRDwN0pt = new WordWordWordPOSTagMap("m_mapSTwSTRHwSTRDwN0pt");
		m_mapSTwSTRHwSTRDptN0w = new WordWordWordPOSTagMap("m_mapSTwSTRHwSTRDptN0w");
		m_mapSTwSTRHptSTRDwN0w = new WordWordWordPOSTagMap("m_mapSTwSTRHptSTRDwN0w");
		m_mapSTptSTRHwSTRDwN0w = new WordWordWordPOSTagMap("m_mapSTptSTRHwSTRDwN0w");
		
		m_mapSTwSTRHwSTR2HwN0pt = new WordWordWordPOSTagMap("m_mapSTwSTRHwSTR2HwN0pt");
		m_mapSTwSTRHwSTR2HptN0w = new WordWordWordPOSTagMap("m_mapSTwSTRHwSTR2HptN0w");
		m_mapSTwSTRHptSTR2HwN0w = new WordWordWordPOSTagMap("m_mapSTwSTRHptSTR2HwN0w");
		m_mapSTptSTRHwSTR2HwN0w = new WordWordWordPOSTagMap("m_mapSTptSTRHwSTR2HwN0w");
		
		m_mapSTwSTRHwSTR2DwN0pt = new WordWordWordPOSTagMap("m_mapSTwSTRHwSTR2DwN0pt");
		m_mapSTwSTRHwSTR2DptN0w = new WordWordWordPOSTagMap("m_mapSTwSTRHwSTR2DptN0w");
		m_mapSTwSTRHptSTR2DwN0w = new WordWordWordPOSTagMap("m_mapSTwSTRHptSTR2DwN0w");
		m_mapSTptSTRHwSTR2DwN0w = new WordWordWordPOSTagMap("m_mapSTptSTRHwSTR2DwN0w");
		
		m_mapSTwN0LHwN0LDwN0pt = new WordWordWordPOSTagMap("m_mapSTwN0LHwN0LDwN0pt");
		m_mapSTwN0LHwN0LDptN0w = new WordWordWordPOSTagMap("m_mapSTwN0LHwN0LDptN0w");
		m_mapSTwN0LHptN0LDwN0w = new WordWordWordPOSTagMap("m_mapSTwN0LHptN0LDwN0w");
		m_mapSTptN0LHwN0LDwN0w = new WordWordWordPOSTagMap("m_mapSTptN0LHwN0LDwN0w");
		
		m_mapSTwN0LHwN0L2HwN0pt = new WordWordWordPOSTagMap("m_mapSTwN0LHwN0L2HwN0pt");
		m_mapSTwN0LHwN0L2HptN0w = new WordWordWordPOSTagMap("m_mapSTwN0LHwN0L2HptN0w");
		m_mapSTwN0LHptN0L2HwN0w = new WordWordWordPOSTagMap("m_mapSTwN0LHptN0L2HwN0w");
		m_mapSTptN0LHwN0L2HwN0w = new WordWordWordPOSTagMap("m_mapSTptN0LHwN0L2HwN0w");
		
		m_mapSTwN0LHwN0L2DwN0pt = new WordWordWordPOSTagMap("m_mapSTwN0LHwN0L2DwN0pt");
		m_mapSTwN0LHwN0L2DptN0w = new WordWordWordPOSTagMap("m_mapSTwN0LHwN0L2DptN0w");
		m_mapSTwN0LHptN0L2DwN0w = new WordWordWordPOSTagMap("m_mapSTwN0LHptN0L2DwN0w");
		m_mapSTptN0LHwN0L2DwN0w = new WordWordWordPOSTagMap("m_mapSTptN0LHwN0L2DwN0w");
		
		m_mapSTwSTLHwSTRHwN0pt = new WordWordWordPOSTagMap("m_mapSTwSTLHwSTRHwN0pt");
		m_mapSTwSTLHwSTRHptN0w = new WordWordWordPOSTagMap("m_mapSTwSTLHwSTRHptN0w");
		m_mapSTwSTLHptSTRHwN0w = new WordWordWordPOSTagMap("m_mapSTwSTLHptSTRHwN0w");
		m_mapSTptSTLHwSTRHwN0w = new WordWordWordPOSTagMap("m_mapSTptSTLHwSTRHwN0w");
		
		m_mapSTwSTLDwSTRDwN0pt = new WordWordWordPOSTagMap("m_mapSTwSTLDwSTRDwN0pt");
		m_mapSTwSTLDwSTRDptN0w = new WordWordWordPOSTagMap("m_mapSTwSTLDwSTRDptN0w");
		m_mapSTwSTLDptSTRDwN0w = new WordWordWordPOSTagMap("m_mapSTwSTLDptSTRDwN0w");
		m_mapSTptSTLDwSTRDwN0w = new WordWordWordPOSTagMap("m_mapSTptSTLDwSTRDwN0w");
		
		m_mapSTwSTRHwSTRDptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTRHwSTRDptN0pt");
		m_mapSTwSTRHptSTRDptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwSTRHptSTRDptN0w");
		m_mapSTptSTRHptSTRDwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTRHptSTRDwN0w");
		m_mapSTptSTRHwSTRDptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTRHwSTRDptN0w");
		m_mapSTwSTRHptSTRDwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTRHptSTRDwN0pt");
		m_mapSTptSTRHwSTRDwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptSTRHwSTRDwN0pt");
		
		m_mapSTwSTRHwSTR2HptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTRHwSTR2HptN0pt");
		m_mapSTwSTRHptSTR2HptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwSTRHptSTR2HptN0w");
		m_mapSTptSTRHptSTR2HwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTRHptSTR2HwN0w");
		m_mapSTptSTRHwSTR2HptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTRHwSTR2HptN0w");
		m_mapSTwSTRHptSTR2HwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTRHptSTR2HwN0pt");
		m_mapSTptSTRHwSTR2HwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptSTRHwSTR2HwN0pt");
		
		m_mapSTwSTRDwSTR2DptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTRDwSTR2DptN0pt");
		m_mapSTwSTRDptSTR2DptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwSTRDptSTR2DptN0w");
		m_mapSTptSTRDptSTR2DwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTRDptSTR2DwN0w");
		m_mapSTptSTRDwSTR2DptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTRDwSTR2DptN0w");
		m_mapSTwSTRDptSTR2DwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTRDptSTR2DwN0pt");
		m_mapSTptSTRDwSTR2DwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptSTRDwSTR2DwN0pt");
		
		m_mapSTwN0LHwN0LDptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwN0LHwN0LDptN0pt");
		m_mapSTwN0LHptN0LDptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwN0LHptN0LDptN0w");
		m_mapSTptN0LHptN0LDwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptN0LHptN0LDwN0w");
		m_mapSTptN0LHwN0LDptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptN0LHwN0LDptN0w");
		m_mapSTwN0LHptN0LDwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwN0LHptN0LDwN0pt");
		m_mapSTptN0LHwN0LDwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptN0LHwN0LDwN0pt");
		
		m_mapSTwN0LHwN0L2HptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwN0LHwN0L2HptN0pt");
		m_mapSTwN0LHptN0L2HptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwN0LHptN0L2HptN0w");
		m_mapSTptN0LHptN0L2HwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptN0LHptN0L2HwN0w");
		m_mapSTptN0LHwN0L2HptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptN0LHwN0L2HptN0w");
		m_mapSTwN0LHptN0L2HwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwN0LHptN0L2HwN0pt");
		m_mapSTptN0LHwN0L2HwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptN0LHwN0L2HwN0pt");
		
		m_mapSTwN0LDwN0L2DptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwN0LDwN0L2DptN0pt");
		m_mapSTwN0LDptN0L2DptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwN0LDptN0L2DptN0w");
		m_mapSTptN0LDptN0L2DwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptN0LDptN0L2DwN0w");
		m_mapSTptN0LDwN0L2DptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptN0LDwN0L2DptN0w");
		m_mapSTwN0LDptN0L2DwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwN0LDptN0L2DwN0pt");
		m_mapSTptN0LDwN0L2DwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptN0LDwN0L2DwN0pt");
		
		m_mapSTwSTLHwSTRHptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTLHwSTRHptN0pt");
		m_mapSTwSTLHptSTRHptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwSTLHptSTRHptN0w");
		m_mapSTptSTLHptSTRHwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTLHptSTRHwN0w");
		m_mapSTptSTLHwSTRHptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTLHwSTRHptN0w");
		m_mapSTwSTLHptSTRHwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTLHptSTRHwN0pt");
		m_mapSTptSTLHwSTRHwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptSTLHwSTRHwN0pt");
		
		m_mapSTwSTLDwSTRDptN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTLDwSTRDptN0pt");
		m_mapSTwSTLDptSTRDptN0w = new WordWordPOSTagPOSTagMap("m_mapSTwSTLDptSTRDptN0w");
		m_mapSTptSTLDptSTRDwN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTLDptSTRDwN0w");
		m_mapSTptSTLDwSTRDptN0w = new WordWordPOSTagPOSTagMap("m_mapSTptSTLDwSTRDptN0w");
		m_mapSTwSTLDptSTRDwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTwSTLDptSTRDwN0pt");
		m_mapSTptSTLDwSTRDwN0pt = new WordWordPOSTagPOSTagMap("m_mapSTptSTLDwSTRDwN0pt");
		
		m_mapSTwSTRHptSTRDptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwSTRHptSTRDptN0pt");
		m_mapSTptSTRHptSTRDptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHptSTRDptN0w");
		m_mapSTptSTRHptSTRDwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHptSTRDwN0pt");
		m_mapSTptSTRHwSTRDptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHwSTRDptN0pt");
		
		m_mapSTwSTRHptSTR2HptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwSTRHptSTR2HptN0pt");
		m_mapSTptSTRHptSTR2HptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHptSTR2HptN0w");
		m_mapSTptSTRHptSTR2HwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHptSTR2HwN0pt");
		m_mapSTptSTRHwSTR2HptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHwSTR2HptN0pt");
		
		m_mapSTwSTRHptSTR2DptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwSTRHptSTR2DptN0pt");
		m_mapSTptSTRHptSTR2DptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHptSTR2DptN0w");
		m_mapSTptSTRHptSTR2DwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHptSTR2DwN0pt");
		m_mapSTptSTRHwSTR2DptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTRHwSTR2DptN0pt");
		
		m_mapSTwN0LHptN0LDptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwN0LHptN0LDptN0pt");
		m_mapSTptN0LHptN0LDptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHptN0LDptN0w");
		m_mapSTptN0LHptN0LDwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHptN0LDwN0pt");
		m_mapSTptN0LHwN0LDptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHwN0LDptN0pt");
		
		m_mapSTwN0LHptN0L2HptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwN0LHptN0L2HptN0pt");
		m_mapSTptN0LHptN0L2HptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHptN0L2HptN0w");
		m_mapSTptN0LHptN0L2HwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHptN0L2HwN0pt");
		m_mapSTptN0LHwN0L2HptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHwN0L2HptN0pt");
		
		m_mapSTwN0LHptN0L2DptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwN0LHptN0L2DptN0pt");
		m_mapSTptN0LHptN0L2DptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHptN0L2DptN0w");
		m_mapSTptN0LHptN0L2DwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHptN0L2DwN0pt");
		m_mapSTptN0LHwN0L2DptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptN0LHwN0L2DptN0pt");
		
		m_mapSTwSTLHptSTRHptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwSTLHptSTRHptN0pt");
		m_mapSTptSTLHptSTRHptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTLHptSTRHptN0w");
		m_mapSTptSTLHptSTRHwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTLHptSTRHwN0pt");
		m_mapSTptSTLHwSTRHptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTLHwSTRHptN0pt");
		
		m_mapSTwSTLDptSTRDptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTwSTLDptSTRDptN0pt");
		m_mapSTptSTLDptSTRDptN0w = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTLDptSTRDptN0w");
		m_mapSTptSTLDptSTRDwN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTLDptSTRDwN0pt");
		m_mapSTptSTLDwSTRDptN0pt = new WordPOSTagPOSTagPOSTagMap("m_mapSTptSTLDwSTRDptN0pt");
		
		m_mapSTptSTRHptSTRDptN0pt = new POSTagSet4Map("m_mapSTptSTRHptSTRDptN0pt");
		m_mapSTptSTRHptSTR2HptN0pt = new POSTagSet4Map("m_mapSTptSTRHptSTR2HptN0pt");
		m_mapSTptSTRDptSTR2DptN0pt = new POSTagSet4Map("m_mapSTptSTRDptSTR2DptN0pt");
		m_mapSTptN0LHptN0LDptN0pt = new POSTagSet4Map("m_mapSTptN0LHptN0LDptN0pt");
		m_mapSTptN0LHptN0L2HptN0pt = new POSTagSet4Map("m_mapSTptN0LHptN0L2HptN0pt");
		m_mapSTptN0LDptN0L2DptN0pt = new POSTagSet4Map("m_mapSTptN0LDptN0L2DptN0pt");
		m_mapSTptSTLHptSTRHptN0pt = new POSTagSet4Map("m_mapSTptSTLHptSTRHptN0pt");
		m_mapSTptSTLDptSTRDptN0pt = new POSTagSet4Map("m_mapSTptSTLDptSTRDptN0pt");
		
		m_mapSTPOSPath = new StringMap("m_mapSTPOSPath");
		m_mapSTFPOSPath = new StringMap("m_mapSTFPOSPath");
		m_mapST2POSPath = new StringMap("m_mapST2POSPath");
		m_mapST2FPOSPath = new StringMap("m_mapST2FPOSPath");

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
			m_mapST2w.loadScoresFromFileStream(br);
			m_mapST2pt.loadScoresFromFileStream(br);
			m_mapN0w.loadScoresFromFileStream(br);
			m_mapN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0w.loadScoresFromFileStream(br);
			m_mapST2wN0w.loadScoresFromFileStream(br);
			m_mapSTwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0w.loadScoresFromFileStream(br);
			m_mapST2wN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRDwN0w.loadScoresFromFileStream(br);
			m_mapSTwN0wN0LHw.loadScoresFromFileStream(br);
			m_mapSTwN0wN0LDw.loadScoresFromFileStream(br);
			
			m_mapST2wST2RHwN0w.loadScoresFromFileStream(br);
			m_mapST2wST2RDwN0w.loadScoresFromFileStream(br);
			m_mapST2wN0wN0LHw.loadScoresFromFileStream(br);
			m_mapST2wN0wN0LDw.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRHptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwN0w.loadScoresFromFileStream(br);
			
			m_mapST2wST2RHwN0pt.loadScoresFromFileStream(br);
			m_mapST2wST2RHptN0w.loadScoresFromFileStream(br);
			m_mapST2ptST2RHwN0w.loadScoresFromFileStream(br);

			m_mapSTwSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRDwN0w.loadScoresFromFileStream(br);

			m_mapST2wST2RDwN0pt.loadScoresFromFileStream(br);
			m_mapST2wST2RDptN0w.loadScoresFromFileStream(br);
			m_mapST2ptST2RDwN0w.loadScoresFromFileStream(br);

			m_mapSTwN0wN0LHpt.loadScoresFromFileStream(br);
			m_mapSTwN0ptN0LHw.loadScoresFromFileStream(br);
			m_mapSTptN0wN0LHw.loadScoresFromFileStream(br);

			m_mapST2wN0wN0LHpt.loadScoresFromFileStream(br);
			m_mapST2wN0ptN0LHw.loadScoresFromFileStream(br);
			m_mapST2ptN0wN0LHw.loadScoresFromFileStream(br);
			
			m_mapSTwN0wN0LDpt.loadScoresFromFileStream(br);
			m_mapSTwN0ptN0LDw.loadScoresFromFileStream(br);
			m_mapSTptN0wN0LDw.loadScoresFromFileStream(br);
			
			m_mapST2wN0wN0LDpt.loadScoresFromFileStream(br);
			m_mapST2wN0ptN0LDw.loadScoresFromFileStream(br);
			m_mapST2ptN0wN0LDw.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwN0pt.loadScoresFromFileStream(br);
			
			m_mapST2wST2RHptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2RHptN0w.loadScoresFromFileStream(br);
			m_mapST2ptST2RHwN0pt.loadScoresFromFileStream(br);

			m_mapSTwSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRDwN0pt.loadScoresFromFileStream(br);

			m_mapST2wST2RDptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2RDptN0w.loadScoresFromFileStream(br);
			m_mapST2ptST2RDwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LHw.loadScoresFromFileStream(br);
			m_mapSTptN0wN0LHpt.loadScoresFromFileStream(br);

			m_mapST2wN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LHw.loadScoresFromFileStream(br);
			m_mapST2ptN0wN0LHpt.loadScoresFromFileStream(br);
			
			m_mapSTwN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LDw.loadScoresFromFileStream(br);
			m_mapSTptN0wN0LDpt.loadScoresFromFileStream(br);

			m_mapST2wN0ptN0LDpt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LDw.loadScoresFromFileStream(br);
			m_mapST2ptN0wN0LDpt.loadScoresFromFileStream(br);
			
			m_mapSTptSTRHptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapSTptN0ptN0LDpt.loadScoresFromFileStream(br);

			m_mapST2ptST2RHptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptST2RDptN0pt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LHpt.loadScoresFromFileStream(br);
			m_mapST2ptN0ptN0LDpt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRHwSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTRDwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTRDwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwSTR2HwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRHwSTR2HptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTR2HwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTR2HwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwSTR2DwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRHwSTR2DptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTR2DwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTR2DwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHwN0LDwN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0LHwN0LDptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0LDwN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0LDwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHwN0L2HwN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0LHwN0L2HptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0L2HwN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0L2HwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHwN0L2DwN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0LHwN0L2DptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0L2DwN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0L2DwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwSTLHwSTRHwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTLHwSTRHptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTLHptSTRHwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLHwSTRHwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwSTLDwSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTLDwSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTLDptSTRDwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLDwSTRDwN0w.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTRDwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTRDwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHwSTR2HptN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTR2HptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTR2HwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTR2HptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRHptSTR2HwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTR2HwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRDwSTR2DptN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTRDptSTR2DptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRDptSTR2DwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRDwSTR2DptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTRDptSTR2DwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRDwSTR2DwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHwN0LDptN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0LDptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0LDwN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0LDptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0LDwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0LDwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHwN0L2HptN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0L2HptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0L2HwN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0L2HptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0LHptN0L2HwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0L2HwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0LDwN0L2DptN0pt.loadScoresFromFileStream(br);
			m_mapSTwN0LDptN0L2DptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LDptN0L2DwN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LDwN0L2DptN0w.loadScoresFromFileStream(br);
			m_mapSTwN0LDptN0L2DwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LDwN0L2DwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTLHwSTRHptN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTLHptSTRHptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLHptSTRHwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLHwSTRHptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTLHptSTRHwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLHwSTRHwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTLDwSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTwSTLDptSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLDptSTRDwN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLDwSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTwSTLDptSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLDwSTRDwN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTRDptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHptSTR2HptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTR2HptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTR2HwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTR2HptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTRHptSTR2DptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTR2DptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTR2DwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHwSTR2DptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHptN0LDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0LDptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0LDwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0LDptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHptN0L2HptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0L2HptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0L2HwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0L2HptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwN0LHptN0L2DptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0L2DptN0w.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0L2DwN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHwN0L2DptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTLHptSTRHptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLHptSTRHptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLHptSTRHwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLHwSTRHptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTwSTLDptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLDptSTRDptN0w.loadScoresFromFileStream(br);
			m_mapSTptSTLDptSTRDwN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLDwSTRDptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTptSTRHptSTRDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRHptSTR2HptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTRDptSTR2DptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0LDptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LHptN0L2HptN0pt.loadScoresFromFileStream(br);
			m_mapSTptN0LDptN0L2DptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLHptSTRHptN0pt.loadScoresFromFileStream(br);
			m_mapSTptSTLDptSTRDptN0pt.loadScoresFromFileStream(br);
			
			m_mapSTPOSPath.loadScoresFromFileStream(br);
			m_mapSTFPOSPath.loadScoresFromFileStream(br);
			m_mapST2POSPath.loadScoresFromFileStream(br);
			m_mapST2FPOSPath.loadScoresFromFileStream(br);
			
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
			m_mapST2w.saveScoresToFileStream(bw);
			m_mapST2pt.saveScoresToFileStream(bw);
			m_mapN0w.saveScoresToFileStream(bw);
			m_mapN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0w.saveScoresToFileStream(bw);
			m_mapST2wN0w.saveScoresToFileStream(bw);
			m_mapSTwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0w.saveScoresToFileStream(bw);
			m_mapST2wN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRDwN0w.saveScoresToFileStream(bw);
			m_mapSTwN0wN0LHw.saveScoresToFileStream(bw);
			m_mapSTwN0wN0LDw.saveScoresToFileStream(bw);
			
			m_mapST2wST2RHwN0w.saveScoresToFileStream(bw);
			m_mapST2wST2RDwN0w.saveScoresToFileStream(bw);
			m_mapST2wN0wN0LHw.saveScoresToFileStream(bw);
			m_mapST2wN0wN0LDw.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRHptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwN0w.saveScoresToFileStream(bw);
			
			m_mapST2wST2RHwN0pt.saveScoresToFileStream(bw);
			m_mapST2wST2RHptN0w.saveScoresToFileStream(bw);
			m_mapST2ptST2RHwN0w.saveScoresToFileStream(bw);

			m_mapSTwSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRDwN0w.saveScoresToFileStream(bw);

			m_mapST2wST2RDwN0pt.saveScoresToFileStream(bw);
			m_mapST2wST2RDptN0w.saveScoresToFileStream(bw);
			m_mapST2ptST2RDwN0w.saveScoresToFileStream(bw);

			m_mapSTwN0wN0LHpt.saveScoresToFileStream(bw);
			m_mapSTwN0ptN0LHw.saveScoresToFileStream(bw);
			m_mapSTptN0wN0LHw.saveScoresToFileStream(bw);

			m_mapST2wN0wN0LHpt.saveScoresToFileStream(bw);
			m_mapST2wN0ptN0LHw.saveScoresToFileStream(bw);
			m_mapST2ptN0wN0LHw.saveScoresToFileStream(bw);
			
			m_mapSTwN0wN0LDpt.saveScoresToFileStream(bw);
			m_mapSTwN0ptN0LDw.saveScoresToFileStream(bw);
			m_mapSTptN0wN0LDw.saveScoresToFileStream(bw);
			
			m_mapST2wN0wN0LDpt.saveScoresToFileStream(bw);
			m_mapST2wN0ptN0LDw.saveScoresToFileStream(bw);
			m_mapST2ptN0wN0LDw.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwN0pt.saveScoresToFileStream(bw);
			
			m_mapST2wST2RHptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2RHptN0w.saveScoresToFileStream(bw);
			m_mapST2ptST2RHwN0pt.saveScoresToFileStream(bw);

			m_mapSTwSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRDwN0pt.saveScoresToFileStream(bw);

			m_mapST2wST2RDptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2RDptN0w.saveScoresToFileStream(bw);
			m_mapST2ptST2RDwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LHw.saveScoresToFileStream(bw);
			m_mapSTptN0wN0LHpt.saveScoresToFileStream(bw);

			m_mapST2wN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LHw.saveScoresToFileStream(bw);
			m_mapST2ptN0wN0LHpt.saveScoresToFileStream(bw);
			
			m_mapSTwN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LDw.saveScoresToFileStream(bw);
			m_mapSTptN0wN0LDpt.saveScoresToFileStream(bw);

			m_mapST2wN0ptN0LDpt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LDw.saveScoresToFileStream(bw);
			m_mapST2ptN0wN0LDpt.saveScoresToFileStream(bw);
			
			m_mapSTptSTRHptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapSTptN0ptN0LDpt.saveScoresToFileStream(bw);

			m_mapST2ptST2RHptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptST2RDptN0pt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LHpt.saveScoresToFileStream(bw);
			m_mapST2ptN0ptN0LDpt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRHwSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTRDwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTRDwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwSTR2HwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRHwSTR2HptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTR2HwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTR2HwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwSTR2DwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRHwSTR2DptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTR2DwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTR2DwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHwN0LDwN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0LHwN0LDptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0LDwN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0LDwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHwN0L2HwN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0LHwN0L2HptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0L2HwN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0L2HwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHwN0L2DwN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0LHwN0L2DptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0L2DwN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0L2DwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwSTLHwSTRHwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTLHwSTRHptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTLHptSTRHwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLHwSTRHwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwSTLDwSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTLDwSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTLDptSTRDwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLDwSTRDwN0w.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTRDwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTRDwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHwSTR2HptN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTR2HptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTR2HwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTR2HptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRHptSTR2HwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTR2HwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRDwSTR2DptN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTRDptSTR2DptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRDptSTR2DwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRDwSTR2DptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTRDptSTR2DwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRDwSTR2DwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHwN0LDptN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0LDptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0LDwN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0LDptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0LDwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0LDwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHwN0L2HptN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0L2HptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0L2HwN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0L2HptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0LHptN0L2HwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0L2HwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0LDwN0L2DptN0pt.saveScoresToFileStream(bw);
			m_mapSTwN0LDptN0L2DptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LDptN0L2DwN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LDwN0L2DptN0w.saveScoresToFileStream(bw);
			m_mapSTwN0LDptN0L2DwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LDwN0L2DwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTLHwSTRHptN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTLHptSTRHptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLHptSTRHwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLHwSTRHptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTLHptSTRHwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLHwSTRHwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTLDwSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTwSTLDptSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLDptSTRDwN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLDwSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTwSTLDptSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLDwSTRDwN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTRDptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHptSTR2HptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTR2HptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTR2HwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTR2HptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTRHptSTR2DptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTR2DptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTR2DwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHwSTR2DptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHptN0LDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0LDptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0LDwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0LDptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHptN0L2HptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0L2HptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0L2HwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0L2HptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwN0LHptN0L2DptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0L2DptN0w.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0L2DwN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHwN0L2DptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTLHptSTRHptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLHptSTRHptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLHptSTRHwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLHwSTRHptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTwSTLDptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLDptSTRDptN0w.saveScoresToFileStream(bw);
			m_mapSTptSTLDptSTRDwN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLDwSTRDptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTptSTRHptSTRDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRHptSTR2HptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTRDptSTR2DptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0LDptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LHptN0L2HptN0pt.saveScoresToFileStream(bw);
			m_mapSTptN0LDptN0L2DptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLHptSTRHptN0pt.saveScoresToFileStream(bw);
			m_mapSTptSTLDptSTRDptN0pt.saveScoresToFileStream(bw);
			
			m_mapSTPOSPath.saveScoresToFileStream(bw);
			m_mapSTFPOSPath.saveScoresToFileStream(bw);
			m_mapST2POSPath.saveScoresToFileStream(bw);
			m_mapST2FPOSPath.saveScoresToFileStream(bw);

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
		m_mapST2w.computeAverage(round);
		m_mapST2pt.computeAverage(round);
		m_mapN0w.computeAverage(round);
		m_mapN0pt.computeAverage(round);
		
		m_mapSTwN0w.computeAverage(round);
		m_mapST2wN0w.computeAverage(round);
		m_mapSTwN0pt.computeAverage(round);
		m_mapSTptN0w.computeAverage(round);
		m_mapST2wN0pt.computeAverage(round);
		m_mapST2ptN0w.computeAverage(round);
		m_mapSTptN0pt.computeAverage(round);
		m_mapST2ptN0pt.computeAverage(round);
		
		m_mapSTwSTRHwN0w.computeAverage(round);
		m_mapSTwSTRDwN0w.computeAverage(round);
		m_mapSTwN0wN0LHw.computeAverage(round);
		m_mapSTwN0wN0LDw.computeAverage(round);
		
		m_mapST2wST2RHwN0w.computeAverage(round);
		m_mapST2wST2RDwN0w.computeAverage(round);
		m_mapST2wN0wN0LHw.computeAverage(round);
		m_mapST2wN0wN0LDw.computeAverage(round);
		
		m_mapSTwSTRHwN0pt.computeAverage(round);
		m_mapSTwSTRHptN0w.computeAverage(round);
		m_mapSTptSTRHwN0w.computeAverage(round);
		
		m_mapST2wST2RHwN0pt.computeAverage(round);
		m_mapST2wST2RHptN0w.computeAverage(round);
		m_mapST2ptST2RHwN0w.computeAverage(round);

		m_mapSTwSTRDwN0pt.computeAverage(round);
		m_mapSTwSTRDptN0w.computeAverage(round);
		m_mapSTptSTRDwN0w.computeAverage(round);

		m_mapST2wST2RDwN0pt.computeAverage(round);
		m_mapST2wST2RDptN0w.computeAverage(round);
		m_mapST2ptST2RDwN0w.computeAverage(round);

		m_mapSTwN0wN0LHpt.computeAverage(round);
		m_mapSTwN0ptN0LHw.computeAverage(round);
		m_mapSTptN0wN0LHw.computeAverage(round);

		m_mapST2wN0wN0LHpt.computeAverage(round);
		m_mapST2wN0ptN0LHw.computeAverage(round);
		m_mapST2ptN0wN0LHw.computeAverage(round);
		
		m_mapSTwN0wN0LDpt.computeAverage(round);
		m_mapSTwN0ptN0LDw.computeAverage(round);
		m_mapSTptN0wN0LDw.computeAverage(round);
		
		m_mapST2wN0wN0LDpt.computeAverage(round);
		m_mapST2wN0ptN0LDw.computeAverage(round);
		m_mapST2ptN0wN0LDw.computeAverage(round);
		
		m_mapSTwSTRHptN0pt.computeAverage(round);
		m_mapSTptSTRHptN0w.computeAverage(round);
		m_mapSTptSTRHwN0pt.computeAverage(round);
		
		m_mapST2wST2RHptN0pt.computeAverage(round);
		m_mapST2ptST2RHptN0w.computeAverage(round);
		m_mapST2ptST2RHwN0pt.computeAverage(round);

		m_mapSTwSTRDptN0pt.computeAverage(round);
		m_mapSTptSTRDptN0w.computeAverage(round);
		m_mapSTptSTRDwN0pt.computeAverage(round);

		m_mapST2wST2RDptN0pt.computeAverage(round);
		m_mapST2ptST2RDptN0w.computeAverage(round);
		m_mapST2ptST2RDwN0pt.computeAverage(round);
		
		m_mapSTwN0ptN0LHpt.computeAverage(round);
		m_mapSTptN0ptN0LHw.computeAverage(round);
		m_mapSTptN0wN0LHpt.computeAverage(round);

		m_mapST2wN0ptN0LHpt.computeAverage(round);
		m_mapST2ptN0ptN0LHw.computeAverage(round);
		m_mapST2ptN0wN0LHpt.computeAverage(round);
		
		m_mapSTwN0ptN0LDpt.computeAverage(round);
		m_mapSTptN0ptN0LDw.computeAverage(round);
		m_mapSTptN0wN0LDpt.computeAverage(round);

		m_mapST2wN0ptN0LDpt.computeAverage(round);
		m_mapST2ptN0ptN0LDw.computeAverage(round);
		m_mapST2ptN0wN0LDpt.computeAverage(round);
		
		m_mapSTptSTRHptN0pt.computeAverage(round);
		m_mapSTptSTRDptN0pt.computeAverage(round);
		m_mapSTptN0ptN0LHpt.computeAverage(round);
		m_mapSTptN0ptN0LDpt.computeAverage(round);

		m_mapST2ptST2RHptN0pt.computeAverage(round);
		m_mapST2ptST2RDptN0pt.computeAverage(round);
		m_mapST2ptN0ptN0LHpt.computeAverage(round);
		m_mapST2ptN0ptN0LDpt.computeAverage(round);
		
		m_mapSTwSTRHwSTRDwN0pt.computeAverage(round);
		m_mapSTwSTRHwSTRDptN0w.computeAverage(round);
		m_mapSTwSTRHptSTRDwN0w.computeAverage(round);
		m_mapSTptSTRHwSTRDwN0w.computeAverage(round);
		
		m_mapSTwSTRHwSTR2HwN0pt.computeAverage(round);
		m_mapSTwSTRHwSTR2HptN0w.computeAverage(round);
		m_mapSTwSTRHptSTR2HwN0w.computeAverage(round);
		m_mapSTptSTRHwSTR2HwN0w.computeAverage(round);
		
		m_mapSTwSTRHwSTR2DwN0pt.computeAverage(round);
		m_mapSTwSTRHwSTR2DptN0w.computeAverage(round);
		m_mapSTwSTRHptSTR2DwN0w.computeAverage(round);
		m_mapSTptSTRHwSTR2DwN0w.computeAverage(round);
		
		m_mapSTwN0LHwN0LDwN0pt.computeAverage(round);
		m_mapSTwN0LHwN0LDptN0w.computeAverage(round);
		m_mapSTwN0LHptN0LDwN0w.computeAverage(round);
		m_mapSTptN0LHwN0LDwN0w.computeAverage(round);
		
		m_mapSTwN0LHwN0L2HwN0pt.computeAverage(round);
		m_mapSTwN0LHwN0L2HptN0w.computeAverage(round);
		m_mapSTwN0LHptN0L2HwN0w.computeAverage(round);
		m_mapSTptN0LHwN0L2HwN0w.computeAverage(round);
		
		m_mapSTwN0LHwN0L2DwN0pt.computeAverage(round);
		m_mapSTwN0LHwN0L2DptN0w.computeAverage(round);
		m_mapSTwN0LHptN0L2DwN0w.computeAverage(round);
		m_mapSTptN0LHwN0L2DwN0w.computeAverage(round);
		
		m_mapSTwSTLHwSTRHwN0pt.computeAverage(round);
		m_mapSTwSTLHwSTRHptN0w.computeAverage(round);
		m_mapSTwSTLHptSTRHwN0w.computeAverage(round);
		m_mapSTptSTLHwSTRHwN0w.computeAverage(round);
		
		m_mapSTwSTLDwSTRDwN0pt.computeAverage(round);
		m_mapSTwSTLDwSTRDptN0w.computeAverage(round);
		m_mapSTwSTLDptSTRDwN0w.computeAverage(round);
		m_mapSTptSTLDwSTRDwN0w.computeAverage(round);
		
		m_mapSTwSTRHwSTRDptN0pt.computeAverage(round);
		m_mapSTwSTRHptSTRDptN0w.computeAverage(round);
		m_mapSTptSTRHptSTRDwN0w.computeAverage(round);
		m_mapSTptSTRHwSTRDptN0w.computeAverage(round);
		m_mapSTwSTRHptSTRDwN0pt.computeAverage(round);
		m_mapSTptSTRHwSTRDwN0pt.computeAverage(round);
		
		m_mapSTwSTRHwSTR2HptN0pt.computeAverage(round);
		m_mapSTwSTRHptSTR2HptN0w.computeAverage(round);
		m_mapSTptSTRHptSTR2HwN0w.computeAverage(round);
		m_mapSTptSTRHwSTR2HptN0w.computeAverage(round);
		m_mapSTwSTRHptSTR2HwN0pt.computeAverage(round);
		m_mapSTptSTRHwSTR2HwN0pt.computeAverage(round);
		
		m_mapSTwSTRDwSTR2DptN0pt.computeAverage(round);
		m_mapSTwSTRDptSTR2DptN0w.computeAverage(round);
		m_mapSTptSTRDptSTR2DwN0w.computeAverage(round);
		m_mapSTptSTRDwSTR2DptN0w.computeAverage(round);
		m_mapSTwSTRDptSTR2DwN0pt.computeAverage(round);
		m_mapSTptSTRDwSTR2DwN0pt.computeAverage(round);
		
		m_mapSTwN0LHwN0LDptN0pt.computeAverage(round);
		m_mapSTwN0LHptN0LDptN0w.computeAverage(round);
		m_mapSTptN0LHptN0LDwN0w.computeAverage(round);
		m_mapSTptN0LHwN0LDptN0w.computeAverage(round);
		m_mapSTwN0LHptN0LDwN0pt.computeAverage(round);
		m_mapSTptN0LHwN0LDwN0pt.computeAverage(round);
		
		m_mapSTwN0LHwN0L2HptN0pt.computeAverage(round);
		m_mapSTwN0LHptN0L2HptN0w.computeAverage(round);
		m_mapSTptN0LHptN0L2HwN0w.computeAverage(round);
		m_mapSTptN0LHwN0L2HptN0w.computeAverage(round);
		m_mapSTwN0LHptN0L2HwN0pt.computeAverage(round);
		m_mapSTptN0LHwN0L2HwN0pt.computeAverage(round);
		
		m_mapSTwN0LDwN0L2DptN0pt.computeAverage(round);
		m_mapSTwN0LDptN0L2DptN0w.computeAverage(round);
		m_mapSTptN0LDptN0L2DwN0w.computeAverage(round);
		m_mapSTptN0LDwN0L2DptN0w.computeAverage(round);
		m_mapSTwN0LDptN0L2DwN0pt.computeAverage(round);
		m_mapSTptN0LDwN0L2DwN0pt.computeAverage(round);
		
		m_mapSTwSTLHwSTRHptN0pt.computeAverage(round);
		m_mapSTwSTLHptSTRHptN0w.computeAverage(round);
		m_mapSTptSTLHptSTRHwN0w.computeAverage(round);
		m_mapSTptSTLHwSTRHptN0w.computeAverage(round);
		m_mapSTwSTLHptSTRHwN0pt.computeAverage(round);
		m_mapSTptSTLHwSTRHwN0pt.computeAverage(round);
		
		m_mapSTwSTLDwSTRDptN0pt.computeAverage(round);
		m_mapSTwSTLDptSTRDptN0w.computeAverage(round);
		m_mapSTptSTLDptSTRDwN0w.computeAverage(round);
		m_mapSTptSTLDwSTRDptN0w.computeAverage(round);
		m_mapSTwSTLDptSTRDwN0pt.computeAverage(round);
		m_mapSTptSTLDwSTRDwN0pt.computeAverage(round);
		
		m_mapSTwSTRHptSTRDptN0pt.computeAverage(round);
		m_mapSTptSTRHptSTRDptN0w.computeAverage(round);
		m_mapSTptSTRHptSTRDwN0pt.computeAverage(round);
		m_mapSTptSTRHwSTRDptN0pt.computeAverage(round);
		
		m_mapSTwSTRHptSTR2HptN0pt.computeAverage(round);
		m_mapSTptSTRHptSTR2HptN0w.computeAverage(round);
		m_mapSTptSTRHptSTR2HwN0pt.computeAverage(round);
		m_mapSTptSTRHwSTR2HptN0pt.computeAverage(round);
		
		m_mapSTwSTRHptSTR2DptN0pt.computeAverage(round);
		m_mapSTptSTRHptSTR2DptN0w.computeAverage(round);
		m_mapSTptSTRHptSTR2DwN0pt.computeAverage(round);
		m_mapSTptSTRHwSTR2DptN0pt.computeAverage(round);
		
		m_mapSTwN0LHptN0LDptN0pt.computeAverage(round);
		m_mapSTptN0LHptN0LDptN0w.computeAverage(round);
		m_mapSTptN0LHptN0LDwN0pt.computeAverage(round);
		m_mapSTptN0LHwN0LDptN0pt.computeAverage(round);
		
		m_mapSTwN0LHptN0L2HptN0pt.computeAverage(round);
		m_mapSTptN0LHptN0L2HptN0w.computeAverage(round);
		m_mapSTptN0LHptN0L2HwN0pt.computeAverage(round);
		m_mapSTptN0LHwN0L2HptN0pt.computeAverage(round);
		
		m_mapSTwN0LHptN0L2DptN0pt.computeAverage(round);
		m_mapSTptN0LHptN0L2DptN0w.computeAverage(round);
		m_mapSTptN0LHptN0L2DwN0pt.computeAverage(round);
		m_mapSTptN0LHwN0L2DptN0pt.computeAverage(round);
		
		m_mapSTwSTLHptSTRHptN0pt.computeAverage(round);
		m_mapSTptSTLHptSTRHptN0w.computeAverage(round);
		m_mapSTptSTLHptSTRHwN0pt.computeAverage(round);
		m_mapSTptSTLHwSTRHptN0pt.computeAverage(round);
		
		m_mapSTwSTLDptSTRDptN0pt.computeAverage(round);
		m_mapSTptSTLDptSTRDptN0w.computeAverage(round);
		m_mapSTptSTLDptSTRDwN0pt.computeAverage(round);
		m_mapSTptSTLDwSTRDptN0pt.computeAverage(round);
		
		m_mapSTptSTRHptSTRDptN0pt.computeAverage(round);
		m_mapSTptSTRHptSTR2HptN0pt.computeAverage(round);
		m_mapSTptSTRDptSTR2DptN0pt.computeAverage(round);
		m_mapSTptN0LHptN0LDptN0pt.computeAverage(round);
		m_mapSTptN0LHptN0L2HptN0pt.computeAverage(round);
		m_mapSTptN0LDptN0L2DptN0pt.computeAverage(round);
		m_mapSTptSTLHptSTRHptN0pt.computeAverage(round);
		m_mapSTptSTLDptSTRDptN0pt.computeAverage(round);
		
		m_mapSTPOSPath.computeAverage(round);
		m_mapSTFPOSPath.computeAverage(round);
		m_mapST2POSPath.computeAverage(round);
		m_mapST2FPOSPath.computeAverage(round);

		System.out.println("done.");
	}
}