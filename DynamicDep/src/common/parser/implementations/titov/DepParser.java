package common.parser.implementations.titov;

import include.AgendaBeam;
import include.AgendaSimple;
import include.learning.perceptron.PackedScoreType;
import include.learning.perceptron.Score;
import include.linguistics.IntInteger;
import include.linguistics.IntIntegerVector;
import include.linguistics.POSTagSet2;
import include.linguistics.POSTagSet3;
import include.linguistics.POSTagSet4;
import include.linguistics.POSTaggedWord;
import include.linguistics.ThreeWords;
import include.linguistics.TwoStrings;
import include.linguistics.TwoStringsVector;
import include.linguistics.TwoWords;
import include.linguistics.Word;
import include.linguistics.WordPOSTag;
import include.linguistics.WordPOSTagPOSTag;
import include.linguistics.WordPOSTagPOSTagPOSTag;
import include.linguistics.WordWordPOSTag;
import include.linguistics.WordWordPOSTagPOSTag;
import include.linguistics.WordWordWordPOSTag;
import include.util.TreeAnalyzer;

import java.util.ArrayList;

import common.parser.DepParserBase;
import common.parser.ScoredAction;
import common.parser.StateItemBase;
import common.parser.implementations.DependencyDag;
import common.parser.implementations.DependencyDagNode;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class DepParser extends DepParserBase {
	
	private boolean m_bSupertag;
	private boolean m_bPath;
	
	private AgendaBeam m_Agenda;
	private AgendaBeam m_Finish;
	private AgendaSimple m_Beam;
	
	private ArrayList<POSTaggedWord> m_lCache;
	private IntIntegerVector m_lTree;
	
	private int m_nTrainingRound;
	private int m_nTotalErrors;
	private int m_nScoreIndex;
	
	private StateItem itemForState;
	private StateItem itemForStates;
	
	private StateItem pCandidate;
	private StateItem correctState;
	private PackedScoreType packed_scores;
	
	private TwoStringsVector trainSentence;
	private IntIntegerVector trainSyntaxtree;
	
	private TreeAnalyzer analyzer;
	
	private WordPOSTag word_postag;
	private WordWordPOSTag word_word_postag;
	private WordPOSTagPOSTag word_postag_postag;
	private WordWordWordPOSTag word_word_word_postag;
	private WordWordPOSTagPOSTag word_word_postag_postag;
	private WordPOSTagPOSTagPOSTag word_postag_postag_postag;

	private TwoWords bi_word;
	private ThreeWords tri_word;
	private POSTagSet2 set_of_2_postags;
	private POSTagSet3 set_of_3_postags;
	private POSTagSet4 set_of_4_postags;
	
	private ScoredAction scoredaction;

	public static final POSTaggedWord empty_postaggedword = new POSTaggedWord();
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2) {
		return ((tag1.hashCode() << Macros.POSTAG_BITS_SIZE) | tag2.hashCode());
	}
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2, final POSTag tag3) {
		return ((tag1.hashCode() << (Macros.POSTAG_BITS_SIZE << 1)) | (tag2.hashCode() << Macros.POSTAG_BITS_SIZE) | tag3.hashCode());
	}
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2, final POSTag tag3, final POSTag tag4) {
		return ((tag1.hashCode() << (Macros.POSTAG_BITS_SIZE + (Macros.POSTAG_BITS_SIZE << 1))) | (tag2.hashCode() << (Macros.POSTAG_BITS_SIZE << 1)) | (tag3.hashCode() << Macros.POSTAG_BITS_SIZE) | tag4.hashCode());
	}
	
	private static final int minVal(final int n1, final int n2) {
		return n1 < n2 ? n1 : n2; 
	}

	public DepParser(final String sFeatureDBPath, final boolean bTrain, final boolean supertag, final boolean upath) {
		super(sFeatureDBPath, bTrain);
		
		m_bSupertag = supertag;
		m_bPath = upath;
		
		m_Agenda = new AgendaBeam(Macros.AGENDA_SIZE, new StateItem());
		m_Finish = new AgendaBeam(Macros.AGENDA_SIZE, new StateItem());
		m_Beam = new AgendaSimple(Macros.AGENDA_SIZE);
		
		m_lCache = new ArrayList<POSTaggedWord>();
		m_lTree = new IntIntegerVector();
		
		m_weights = new Weight(sFeatureDBPath, bTrain);
		m_nTrainingRound = 0;
		m_nTotalErrors = 0;
		m_nScoreIndex = bTrain ? Score.eNonAverage : Score.eAverage;
		
		itemForState = new StateItem();
		itemForStates = new StateItem();
		pCandidate = new StateItem();
		correctState = new StateItem();
		
		packed_scores = new PackedScoreType(Macros.ACTION_MAX);
		
		trainSentence = new TwoStringsVector();
		trainSyntaxtree = new IntIntegerVector();
		
		analyzer = new TreeAnalyzer();
		
		word_postag = new WordPOSTag();;
		word_word_postag = new WordWordPOSTag();
		word_postag_postag = new WordPOSTagPOSTag();
		word_word_word_postag = new WordWordWordPOSTag();
		word_word_postag_postag = new WordWordPOSTagPOSTag();
		word_postag_postag_postag = new WordPOSTagPOSTagPOSTag();

		bi_word = new TwoWords();
		tri_word = new ThreeWords();
		set_of_2_postags = new POSTagSet2();
		set_of_3_postags = new POSTagSet3();
		set_of_4_postags = new POSTagSet4();
		
		scoredaction = new ScoredAction();
	}
	
	public void getOrUpdateStackScore(final StateItem item, PackedScoreType retval, final int action, final int amount, final int round) {

		final int st_index = item.stacktop();
		final int stlh_index = item.lefthead(st_index);
		final int stld_index = item.leftdep(st_index);
		final int strh_index = item.righthead(st_index);
		final int strd_index = item.rightdep(st_index);
		final int str2h_index = item.rightsubhead(st_index);
		final int str2d_index = item.rightsubdep(st_index);
		final int st2_index = item.stacktop2();
		final int st2rh_index = item.righthead(st2_index);
		final int st2rd_index = item.rightdep(st2_index);
		final int n0_index = item.size() < m_lCache.size() ? item.size() : StateItem.out_index;
		final int n0ld_index = item.leftdep(n0_index);
		final int n0l2d_index = item.leftsubdep(n0_index);
		final int n0lh_index = item.lefthead(n0_index);
		final int n0l2h_index = item.leftsubhead(n0_index);

		final POSTaggedWord st_word_postag = st_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st_index);
		final POSTaggedWord stlh_word_postag = stlh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stlh_index);
		final POSTaggedWord stld_word_postag = stld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stld_index);
		final POSTaggedWord strh_word_postag = strh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(strh_index);
		final POSTaggedWord strd_word_postag = strd_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(strd_index);
		final POSTaggedWord str2h_word_postag = str2h_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(str2h_index);
		final POSTaggedWord str2d_word_postag = str2d_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(str2d_index);
		final POSTaggedWord st2_word_postag = st2_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2_index);
		final POSTaggedWord st2rh_word_postag = st2rh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2rh_index);
		final POSTaggedWord st2rd_word_postag = st2rd_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2rd_index);
		final POSTaggedWord n0_word_postag = n0_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0_index);
		final POSTaggedWord n0ld_word_postag = n0ld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0ld_index);
		final POSTaggedWord n0l2d_word_postag = n0l2d_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0l2d_index);
		final POSTaggedWord n0lh_word_postag = n0lh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0lh_index);
		final POSTaggedWord n0l2h_word_postag = n0l2h_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0l2h_index);

		final Word st_word = st_word_postag.word;
		final Word stlh_word = stlh_word_postag.word;
		final Word stld_word = stld_word_postag.word;
		final Word strh_word = strh_word_postag.word;
		final Word strd_word = strd_word_postag.word;
		final Word str2h_word = str2h_word_postag.word;
		final Word str2d_word = str2d_word_postag.word;
		final Word st2_word = st2_word_postag.word;
		final Word st2rh_word = st2rh_word_postag.word;
		final Word st2rd_word = st2rd_word_postag.word;
		final Word n0_word = n0_word_postag.word;
		final Word n0ld_word = n0ld_word_postag.word;
		final Word n0l2d_word = n0l2d_word_postag.word;
		final Word n0lh_word = n0lh_word_postag.word;
		final Word n0l2h_word = n0l2h_word_postag.word;

		final POSTag st_postag = st_word_postag.tag;
		final POSTag stlh_postag = stlh_word_postag.tag;
		final POSTag stld_postag = stld_word_postag.tag;
		final POSTag strh_postag = strh_word_postag.tag;
		final POSTag strd_postag = strd_word_postag.tag;
		final POSTag str2h_postag = str2h_word_postag.tag;
		final POSTag str2d_postag = str2d_word_postag.tag;
		final POSTag st2_postag = st2_word_postag.tag;
		final POSTag st2rh_postag = st2rh_word_postag.tag;
		final POSTag st2rd_postag = st2rd_word_postag.tag;
		final POSTag n0_postag = n0_word_postag.tag;
		final POSTag n0ld_postag = n0ld_word_postag.tag;
		final POSTag n0l2d_postag = n0l2d_word_postag.tag;
		final POSTag n0lh_postag = n0lh_word_postag.tag;
		final POSTag n0l2h_postag = n0l2h_word_postag.tag;

		Weight weight = (Weight)m_weights;

		weight.m_mapSTw.getOrUpdateScore(retval, st_word, action, m_nScoreIndex, amount, round);
		weight.m_mapSTpt.getOrUpdateScore(retval, st_postag, action, m_nScoreIndex, amount, round);
		weight.m_mapST2w.getOrUpdateScore(retval, st2_word, action, m_nScoreIndex, amount, round);
		weight.m_mapST2pt.getOrUpdateScore(retval, st2_postag, action, m_nScoreIndex, amount, round);
		weight.m_mapN0w.getOrUpdateScore(retval, n0_word, action, m_nScoreIndex, amount, round);
		weight.m_mapN0pt.getOrUpdateScore(retval, n0_postag, action, m_nScoreIndex, amount, round);

		bi_word.refer(st_word, n0_word);
		weight.m_mapSTwN0w.getOrUpdateScore(retval, bi_word, action, m_nScoreIndex, amount, round);
		bi_word.refer(st2_word, n0_word);
		weight.m_mapST2wN0w.getOrUpdateScore(retval, bi_word, action, m_nScoreIndex, amount, round);
		word_postag.refer(st_word, n0_postag);
		weight.m_mapSTwN0pt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(n0_word, st_postag);
		weight.m_mapSTptN0w.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(st2_word, n0_postag);
		weight.m_mapST2wN0pt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(n0_word, st2_postag);
		weight.m_mapST2ptN0w.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		set_of_2_postags.load(encodePOSTags(st_postag, n0_postag));
		weight.m_mapSTptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
		set_of_2_postags.load(encodePOSTags(st2_postag, n0_postag));
		weight.m_mapST2ptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);

		tri_word.refer(st_word, strh_word, n0_word);
		weight.m_mapSTwSTRHwN0w.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);
		tri_word.refer(st_word, strd_word, n0_word);
		weight.m_mapSTwSTRDwN0w.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);
		tri_word.refer(st_word, n0_word, n0lh_word);
		weight.m_mapSTwN0wN0LHw.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);
		tri_word.refer(st_word, n0_word, n0ld_word);
		weight.m_mapSTwN0wN0LDw.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);

		tri_word.refer(st2_word, st2rh_word, n0_word);
		weight.m_mapST2wST2RHwN0w.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);
		tri_word.refer(st2_word, st2rd_word, n0_word);
		weight.m_mapST2wST2RDwN0w.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);
		tri_word.refer(st2_word, n0_word, n0lh_word);
		weight.m_mapST2wN0wN0LHw.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);
		tri_word.refer(st2_word, n0_word, n0ld_word);
		weight.m_mapST2wN0wN0LDw.getOrUpdateScore(retval, tri_word, action, m_nScoreIndex, amount, round);

		word_word_postag.refer(st_word, strh_word, n0_postag);
		weight.m_mapSTwSTRHwN0pt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st_word, n0_word, strh_postag);
		weight.m_mapSTwSTRHptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(strh_word, n0_word, st_postag);
		weight.m_mapSTptSTRHwN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_postag.refer(st2_word, st2rh_word, n0_postag);
		weight.m_mapST2wST2RHwN0pt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2_word, n0_word, st2rh_postag);
		weight.m_mapST2wST2RHptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2rh_word, n0_word, st2_postag);
		weight.m_mapST2ptST2RHwN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st_word, strd_word, n0_postag);
		weight.m_mapSTwSTRDwN0pt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st_word, n0_word, strd_postag);
		weight.m_mapSTwSTRDptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(strd_word, n0_word, st_postag);
		weight.m_mapSTptSTRDwN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2_word, st2rd_word, n0_postag);
		weight.m_mapST2wST2RDwN0pt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2_word, n0_word, st2rd_postag);
		weight.m_mapST2wST2RDptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2rd_word, n0_word, st2_postag);
		weight.m_mapST2ptST2RDwN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st_word, n0_word, n0lh_postag);
		weight.m_mapSTwN0wN0LHpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st_word, n0lh_word, n0_postag);
		weight.m_mapSTwN0ptN0LHw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(n0_word, n0lh_word, st_postag);
		weight.m_mapSTptN0wN0LHw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2_word, n0_word, n0lh_postag);
		weight.m_mapST2wN0wN0LHpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2_word, n0lh_word, n0_postag);
		weight.m_mapST2wN0ptN0LHw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(n0_word, n0lh_word, st2_postag);
		weight.m_mapST2ptN0wN0LHw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_postag.refer(st_word, n0_word, n0ld_postag);
		weight.m_mapSTwN0wN0LDpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st_word, n0ld_word, n0_postag);
		weight.m_mapSTwN0ptN0LDw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(n0_word, n0ld_word, st_postag);
		weight.m_mapSTptN0wN0LDw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_postag.refer(st2_word, n0_word, n0ld_postag);
		weight.m_mapST2wN0wN0LDpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(st2_word, n0ld_word, n0_postag);
		weight.m_mapST2wN0ptN0LDw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_postag.refer(n0_word, n0ld_word, st2_postag);
		weight.m_mapST2ptN0wN0LDw.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag.refer(st_word, strh_postag, n0_postag);
		weight.m_mapSTwSTRHptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st_postag, strh_postag);
		weight.m_mapSTptSTRHptN0w.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(strh_word, st_postag, n0_postag);
		weight.m_mapSTptSTRHwN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag.refer(st2_word, st2rh_postag, n0_postag);
		weight.m_mapST2wST2RHptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st2_postag, st2rh_postag);
		weight.m_mapST2ptST2RHptN0w.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(st2rh_word, st2_postag, n0_postag);
		weight.m_mapST2ptST2RHwN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(st_word, strd_postag, n0_postag);
		weight.m_mapSTwSTRDptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st_postag, strd_postag);
		weight.m_mapSTptSTRDptN0w.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(strd_word, st_postag, n0_postag);
		weight.m_mapSTptSTRDwN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(st2_word, st2rd_postag, n0_postag);
		weight.m_mapST2wST2RDptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st2_postag, st2rd_postag);
		weight.m_mapST2ptST2RDptN0w.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(st2rd_word, st2_postag, n0_postag);
		weight.m_mapST2ptST2RDwN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag.refer(st_word, n0_postag, n0lh_postag);
		weight.m_mapSTwN0ptN0LHpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0lh_word, st_postag, n0_postag);
		weight.m_mapSTptN0ptN0LHw.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st_postag, n0lh_postag);
		weight.m_mapSTptN0wN0LHpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(st2_word, n0_postag, n0lh_postag);
		weight.m_mapST2wN0ptN0LHpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0lh_word, st2_postag, n0_postag);
		weight.m_mapST2ptN0ptN0LHw.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st2_postag, n0lh_postag);
		weight.m_mapST2ptN0wN0LHpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag.refer(st_word, n0_postag, n0ld_postag);
		weight.m_mapSTwN0ptN0LDpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0ld_word, st_postag, n0_postag);
		weight.m_mapSTptN0ptN0LDw.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st_postag, n0ld_postag);
		weight.m_mapSTptN0wN0LDpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(st2_word, n0_postag, n0ld_postag);
		weight.m_mapST2wN0ptN0LDpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0ld_word, st2_postag, n0_postag);
		weight.m_mapST2ptN0ptN0LDw.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag.refer(n0_word, st2_postag, n0ld_postag);
		weight.m_mapST2ptN0wN0LDpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

		set_of_3_postags.load(encodePOSTags(st_postag, strh_postag, n0_postag));
		weight.m_mapSTptSTRHptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st_postag, strd_postag, n0_postag));
		weight.m_mapSTptSTRDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n0lh_postag));
		weight.m_mapSTptN0ptN0LHpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n0ld_postag));
		weight.m_mapSTptN0ptN0LDpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st2_postag, st2rh_postag, n0_postag));
		weight.m_mapST2ptST2RHptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st2_postag, st2rd_postag, n0_postag));
		weight.m_mapST2ptST2RDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st2_postag, n0_postag, n0lh_postag));
		weight.m_mapST2ptN0ptN0LHpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		set_of_3_postags.load(encodePOSTags(st2_postag, n0_postag, n0ld_postag));
		weight.m_mapST2ptN0ptN0LDpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, strh_word, strd_word, n0_postag);
		weight.m_mapSTwSTRHwSTRDwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, strh_word, n0_word, strd_postag);
		weight.m_mapSTwSTRHwSTRDptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, strd_word, n0_word, strh_postag);
		weight.m_mapSTwSTRHptSTRDwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(strh_word, strd_word, n0_word, st_postag);
		weight.m_mapSTptSTRHwSTRDwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, strh_word, str2h_word, n0_postag);
		weight.m_mapSTwSTRHwSTR2HwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, strh_word, n0_word, str2h_postag);
		weight.m_mapSTwSTRHwSTR2HptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, str2h_word, n0_word, strh_postag);
		weight.m_mapSTwSTRHptSTR2HwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(strh_word, str2h_word, n0_word, st_postag);
		weight.m_mapSTptSTRHwSTR2HwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, strh_word, str2d_word, n0_postag);
		weight.m_mapSTwSTRHwSTR2DwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, strh_word, n0_word, str2d_postag);
		weight.m_mapSTwSTRHwSTR2DptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, str2d_word, n0_word, strh_postag);
		weight.m_mapSTwSTRHptSTR2DwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(strh_word, str2d_word, n0_word, st_postag);
		weight.m_mapSTptSTRHwSTR2DwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, n0lh_word, n0ld_word, n0_postag);
		weight.m_mapSTwN0LHwN0LDwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, n0lh_word, n0_word, n0ld_postag);
		weight.m_mapSTwN0LHwN0LDptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, n0ld_word, n0_word, n0lh_postag);
		weight.m_mapSTwN0LHptN0LDwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(n0lh_word, n0ld_word, n0_word, st_postag);
		weight.m_mapSTptN0LHwN0LDwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, n0lh_word, n0l2h_word, n0_postag);
		weight.m_mapSTwN0LHwN0L2HwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, n0lh_word, n0_word, n0l2h_postag);
		weight.m_mapSTwN0LHwN0L2HptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, n0l2h_word, n0_word, n0lh_postag);
		weight.m_mapSTwN0LHptN0L2HwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(n0lh_word, n0l2h_word, n0_word, st_postag);
		weight.m_mapSTptN0LHwN0L2HwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, n0lh_word, n0l2d_word, n0_postag);
		weight.m_mapSTwN0LHwN0L2DwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, n0lh_word, n0_word, n0l2d_postag);
		weight.m_mapSTwN0LHwN0L2DptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, n0l2d_word, n0_word, n0lh_postag);
		weight.m_mapSTwN0LHptN0L2DwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(n0lh_word, n0l2d_word, n0_word, st_postag);
		weight.m_mapSTptN0LHwN0L2DwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, stlh_word, strh_word, n0_postag);
		weight.m_mapSTwSTLHwSTRHwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, stlh_word, n0_word, strh_postag);
		weight.m_mapSTwSTLHwSTRHptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, strh_word, n0_word, stlh_postag);
		weight.m_mapSTwSTLHptSTRHwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(stlh_word, strh_word, n0_word, st_postag);
		weight.m_mapSTptSTLHwSTRHwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_word_postag.refer(st_word, stld_word, strd_word, n0_postag);
		weight.m_mapSTwSTLDwSTRDwN0pt.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, stld_word, n0_word, strd_postag);
		weight.m_mapSTwSTLDwSTRDptN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(st_word, strd_word, n0_word, stld_postag);
		weight.m_mapSTwSTLDptSTRDwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);
		word_word_word_postag.refer(stld_word, strd_word, n0_word, st_postag);
		weight.m_mapSTptSTLDwSTRDwN0w.getOrUpdateScore(retval, word_word_word_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, strh_word, strd_postag, n0_postag);
		weight.m_mapSTwSTRHwSTRDptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, strh_postag, strd_postag);
		weight.m_mapSTwSTRHptSTRDptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strd_word, n0_word, st_postag, strh_postag);
		weight.m_mapSTptSTRHptSTRDwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strh_word, n0_word, st_postag, strd_postag);
		weight.m_mapSTptSTRHwSTRDptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, strd_word, strh_postag, n0_postag);
		weight.m_mapSTwSTRHptSTRDwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strh_word, strd_word, st_postag, n0_postag);
		weight.m_mapSTptSTRHwSTRDwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, strh_word, str2h_postag, n0_postag);
		weight.m_mapSTwSTRHwSTR2HptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, strh_postag, str2h_postag);
		weight.m_mapSTwSTRHptSTR2HptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(str2h_word, n0_word, st_postag, strh_postag);
		weight.m_mapSTptSTRHptSTR2HwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strh_word, n0_word, st_postag, str2h_postag);
		weight.m_mapSTptSTRHwSTR2HptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, str2h_word, strh_postag, n0_postag);
		weight.m_mapSTwSTRHptSTR2HwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strh_word, str2h_word, st_postag, n0_postag);
		weight.m_mapSTptSTRHwSTR2HwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, strd_word, str2d_postag, n0_postag);
		weight.m_mapSTwSTRDwSTR2DptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, strd_postag, str2d_postag);
		weight.m_mapSTwSTRDptSTR2DptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(str2d_word, n0_word, st_postag, strd_postag);
		weight.m_mapSTptSTRDptSTR2DwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strd_word, n0_word, st_postag, str2d_postag);
		weight.m_mapSTptSTRDwSTR2DptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, str2d_word, strd_postag, n0_postag);
		weight.m_mapSTwSTRDptSTR2DwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strd_word, str2d_word, st_postag, n0_postag);
		weight.m_mapSTptSTRDwSTR2DwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, n0lh_word, n0ld_postag, n0_postag);
		weight.m_mapSTwN0LHwN0LDptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, n0lh_postag, n0ld_postag);
		weight.m_mapSTwN0LHptN0LDptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0ld_word, n0_word, st_postag, n0lh_postag);
		weight.m_mapSTptN0LHptN0LDwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0lh_word, n0_word, st_postag, n0ld_postag);
		weight.m_mapSTptN0LHwN0LDptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0ld_word, n0lh_postag, n0_postag);
		weight.m_mapSTwN0LHptN0LDwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0lh_word, n0ld_word, st_postag, n0_postag);
		weight.m_mapSTptN0LHwN0LDwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, n0lh_word, n0l2h_postag, n0_postag);
		weight.m_mapSTwN0LHwN0L2HptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, n0lh_postag, n0l2h_postag);
		weight.m_mapSTwN0LHptN0L2HptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0l2h_word, n0_word, st_postag, n0lh_postag);
		weight.m_mapSTptN0LHptN0L2HwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0lh_word, n0_word, st_postag, n0l2h_postag);
		weight.m_mapSTptN0LHwN0L2HptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0l2h_word, n0lh_postag, n0_postag);
		weight.m_mapSTwN0LHptN0L2HwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0lh_word, n0l2h_word, st_postag, n0_postag);
		weight.m_mapSTptN0LHwN0L2HwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, n0ld_word, n0l2d_postag, n0_postag);
		weight.m_mapSTwN0LDwN0L2DptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, n0ld_postag, n0l2d_postag);
		weight.m_mapSTwN0LDptN0L2DptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0l2d_word, n0_word, st_postag, n0ld_postag);
		weight.m_mapSTptN0LDptN0L2DwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0ld_word, n0_word, st_postag, n0l2d_postag);
		weight.m_mapSTptN0LDwN0L2DptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0l2d_word, n0ld_postag, n0_postag);
		weight.m_mapSTwN0LDptN0L2DwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(n0ld_word, n0l2d_word, st_postag, n0_postag);
		weight.m_mapSTptN0LDwN0L2DwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, stlh_word, strh_postag, n0_postag);
		weight.m_mapSTwSTLHwSTRHptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, stlh_postag, strh_postag);
		weight.m_mapSTwSTLHptSTRHptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strh_word, n0_word, st_postag, stlh_postag);
		weight.m_mapSTptSTLHptSTRHwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(stlh_word, n0_word, st_postag, strh_postag);
		weight.m_mapSTptSTLHwSTRHptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, strh_word, stlh_postag, n0_postag);
		weight.m_mapSTwSTLHptSTRHwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(stlh_word, strh_word, st_postag, n0_postag);
		weight.m_mapSTptSTLHwSTRHwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_word_postag_postag.refer(st_word, stld_word, strd_postag, n0_postag);
		weight.m_mapSTwSTLDwSTRDptN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, n0_word, stld_postag, strd_postag);
		weight.m_mapSTwSTLDptSTRDptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(strd_word, n0_word, st_postag, stld_postag);
		weight.m_mapSTptSTLDptSTRDwN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(stld_word, n0_word, st_postag, strd_postag);
		weight.m_mapSTptSTLDwSTRDptN0w.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(st_word, strd_word, stld_postag, n0_postag);
		weight.m_mapSTwSTLDptSTRDwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);
		word_word_postag_postag.refer(stld_word, strd_word, st_postag, n0_postag);
		weight.m_mapSTptSTLDwSTRDwN0pt.getOrUpdateScore(retval, word_word_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, strh_postag, strd_postag, n0_postag);
		weight.m_mapSTwSTRHptSTRDptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, strh_postag, strd_postag);
		weight.m_mapSTptSTRHptSTRDptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(strd_word, st_postag, strh_postag, n0_postag);
		weight.m_mapSTptSTRHptSTRDwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(strh_word, st_postag, strd_postag, n0_postag);
		weight.m_mapSTptSTRHwSTRDptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, strh_postag, str2h_postag, n0_postag);
		weight.m_mapSTwSTRHptSTR2HptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, strh_postag, str2h_postag);
		weight.m_mapSTptSTRHptSTR2HptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(str2h_word, st_postag, strh_postag, n0_postag);
		weight.m_mapSTptSTRHptSTR2HwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(strh_word, st_postag, str2h_postag, n0_postag);
		weight.m_mapSTptSTRHwSTR2HptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, strh_postag, str2d_postag, n0_postag);
		weight.m_mapSTwSTRHptSTR2DptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, strh_postag, str2d_postag);
		weight.m_mapSTptSTRHptSTR2DptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(str2d_word, st_postag, strh_postag, n0_postag);
		weight.m_mapSTptSTRHptSTR2DwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(strh_word, st_postag, str2d_postag, n0_postag);
		weight.m_mapSTptSTRHwSTR2DptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, n0lh_postag, n0ld_postag, n0_postag);
		weight.m_mapSTwN0LHptN0LDptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, n0lh_postag, n0ld_postag);
		weight.m_mapSTptN0LHptN0LDptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0ld_word, st_postag, n0lh_postag, n0_postag);
		weight.m_mapSTptN0LHptN0LDwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0lh_word, st_postag, n0ld_postag, n0_postag);
		weight.m_mapSTptN0LHwN0LDptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, n0lh_postag, n0l2h_postag, n0_postag);
		weight.m_mapSTwN0LHptN0L2HptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, n0lh_postag, n0l2h_postag);
		weight.m_mapSTptN0LHptN0L2HptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0l2h_word, st_postag, n0lh_postag, n0_postag);
		weight.m_mapSTptN0LHptN0L2HwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0lh_word, st_postag, n0l2h_postag, n0_postag);
		weight.m_mapSTptN0LHwN0L2HptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, n0lh_postag, n0l2d_postag, n0_postag);
		weight.m_mapSTwN0LHptN0L2DptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, n0lh_postag, n0l2d_postag);
		weight.m_mapSTptN0LHptN0L2DptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0l2d_word, st_postag, n0lh_postag, n0_postag);
		weight.m_mapSTptN0LHptN0L2DwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0lh_word, st_postag, n0l2d_postag, n0_postag);
		weight.m_mapSTptN0LHwN0L2DptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, stlh_postag, strh_postag, n0_postag);
		weight.m_mapSTwSTLHptSTRHptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, stlh_postag, strh_postag);
		weight.m_mapSTptSTLHptSTRHptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(strh_word, st_postag, stlh_postag, n0_postag);
		weight.m_mapSTptSTLHptSTRHwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(stlh_word, st_postag, strh_postag, n0_postag);
		weight.m_mapSTptSTLHwSTRHptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		word_postag_postag_postag.refer(st_word, stld_postag, strd_postag, n0_postag);
		weight.m_mapSTwSTLDptSTRDptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(n0_word, st_postag, stld_postag, strd_postag);
		weight.m_mapSTptSTLDptSTRDptN0w.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(strd_word, st_postag, stld_postag, n0_postag);
		weight.m_mapSTptSTLDptSTRDwN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);
		word_postag_postag_postag.refer(stld_word, st_postag, strd_postag, n0_postag);
		weight.m_mapSTptSTLDwSTRDptN0pt.getOrUpdateScore(retval, word_postag_postag_postag, action, m_nScoreIndex, amount, round);

		set_of_4_postags.load(encodePOSTags(st_postag, strh_postag, strd_postag, n0_postag));
		weight.m_mapSTptSTRHptSTRDptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, strh_postag, str2h_postag, n0_postag));
		weight.m_mapSTptSTRHptSTR2HptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, strd_postag, str2d_postag, n0_postag));
		weight.m_mapSTptSTRDptSTR2DptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, n0lh_postag, n0ld_postag, n0_postag));
		weight.m_mapSTptN0LHptN0LDptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, n0lh_postag, n0l2h_postag, n0_postag));
		weight.m_mapSTptN0LHptN0L2HptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, n0ld_postag, n0l2d_postag, n0_postag));
		weight.m_mapSTptN0LDptN0L2DptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, stlh_postag, strh_postag, n0_postag));
		weight.m_mapSTptSTLHptSTRHptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);
		set_of_4_postags.load(encodePOSTags(st_postag, stld_postag, strd_postag, n0_postag));
		weight.m_mapSTptSTLDptSTRDptN0pt.getOrUpdateScore(retval, set_of_4_postags, action, m_nScoreIndex, amount, round);

		if (m_bPath) {

			if (st_index == StateItem.out_index || n0_index == StateItem.out_index) {
				weight.m_mapSTPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
				weight.m_mapSTFPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			} else {
				weight.m_mapSTPOSPath.getOrUpdateScore(retval, analyzer.POSPath[st_index][n0_index], action, m_nScoreIndex, amount, round);
				weight.m_mapSTFPOSPath.getOrUpdateScore(retval, analyzer.FPOSPath[st_index][n0_index], action, m_nScoreIndex, amount, round);
			}

			if (st2_index == StateItem.out_index || n0_index == StateItem.out_index) {
				weight.m_mapST2POSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
				weight.m_mapST2FPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			} else {
				weight.m_mapST2POSPath.getOrUpdateScore(retval, analyzer.POSPath[st2_index][n0_index], action, m_nScoreIndex, amount, round);
				weight.m_mapST2FPOSPath.getOrUpdateScore(retval, analyzer.FPOSPath[st2_index][n0_index], action, m_nScoreIndex, amount, round);
			}

		}

	}

	
	public void getOrUpdateStackScore(final StateItem item, PackedScoreType retval, final int action) {
		getOrUpdateStackScore(item, retval, action, 0, 0);
	}
	
	public void updateScoreForState(final StateItemBase from, final StateItemBase output, final int amount, final int len) {
		itemForState.copy(from);
		while (!itemForState.equals(output)) {
			int action = itemForState.FollowMove(output);
			getOrUpdateStackScore(itemForState, null, action, amount, m_nTrainingRound);
			if (action >= Macros.AL_FIRST) {
				++((StateItem)output).m_lRightArcsSeek[itemForState.m_lStack[itemForState.stack_back]];
			}
			itemForState.Move(action);
		}
	}
	
	public void updateScoreForStates(final StateItemBase output, final StateItemBase correct, final int amount_add, final int amount_subtract, final int len) {
		itemForStates.clear();
		while (!itemForStates.equals(output)) {
			int action = itemForStates.FollowMove(output);
			int correct_action = itemForStates.FollowMove(correct);
			if (action == correct_action) {
				if (action >= Macros.AL_FIRST){
					int back = itemForStates.m_lStack[itemForStates.stack_back];
					++((StateItem)output).m_lRightArcsSeek[back];
					++((StateItem)correct).m_lRightArcsSeek[back];
				}
				itemForStates.Move(action);
			} else {
				break;
			}
		}
		updateScoreForState(itemForStates, correct, amount_add, len);
		updateScoreForState(itemForStates, output, amount_subtract, len);
		++m_nTotalErrors;
	}
	
	public void reduce(final StateItem item, final PackedScoreType scores) {
		scoredaction.action = Macros.REDUCE;
		scoredaction.score = item.score + scores.at(scoredaction.action);
		m_Beam.insertItem(scoredaction);
	}
	
	public void arcleft(final StateItem item, final PackedScoreType scores) {
		for (int label = Macros.DEP_FIRST; label < Macros.DEP_COUNT; ++label) {
			scoredaction.action = Action.encodeAction(Macros.ARC_LEFT, label);
			scoredaction.score = item.score + scores.at(scoredaction.action);
			m_Beam.insertItem(scoredaction);
		}
	}
	
	public void arcright(final StateItem item, final PackedScoreType scores) {
		for (int label = Macros.DEP_FIRST; label < Macros.DEP_COUNT; ++label) {
			scoredaction.action = Action.encodeAction(Macros.ARC_RIGHT, label);
			scoredaction.score = item.score + scores.at(scoredaction.action);
			m_Beam.insertItem(scoredaction);
		}
	}
	
	public void shift(final StateItem item, final PackedScoreType scores) {
		for (int label : Macros.SHIFT_TAGLIST) {
			scoredaction.action = Action.encodeAction(Macros.SHIFT, label);
			scoredaction.score = item.score + scores.at(scoredaction.action);
			m_Beam.insertItem(scoredaction);
		}
	}
	
	public void swap(final StateItem item, final PackedScoreType scores) {
		scoredaction.action = Macros.SWAP;
		scoredaction.score = item.score + scores.at(scoredaction.action);
		m_Beam.insertItem(scoredaction);
	}
	
	public void work(final int round, final boolean bTrain, final TwoStringsVector sentence, final IntIntegerVector syntaxtree, DependencyDag[] retval, final DependencyDag correct, final int nBest, long[] scores) {
		final int length = sentence.size();
		StateItem pGenerator;

		boolean finish = false;
		boolean bCorrect = false;
		
		m_lCache.clear();
		for (int index = 0; index < length; ++index) {
			m_lCache.add(new POSTaggedWord(sentence.get(index).m_string1, sentence.get(index).m_string2));
		}
		m_lTree = syntaxtree;
		analyzer.loadPath(m_lCache, m_lTree);
		m_Agenda.clear();
		m_Finish.clear();
		pCandidate.clear();

		m_Agenda.pushCandidate(pCandidate);

		m_Agenda.nextRound();
		if (bTrain) correctState.clear();

		/*
		 * finish means
		 * 		"no more action for correct state" or
		 * 		"no more action for candidates" or
		 * 		"state correct"
		 */
		while (!finish) {
			if (bTrain) bCorrect = false;
			
			pGenerator = (StateItem)m_Agenda.generatorStart();
			
			for (int j = 0, agenda_size = m_Agenda.generatorSize(); j < agenda_size; ++j) {
				
				if (pGenerator.m_nNextWord < length) {
					POSTaggedWord pw = m_lCache.get(pGenerator.m_nNextWord);
					Macros.SHIFT_TAGLIST = Macros.WORD2TAGSMAP.get(pw.word.toString());
					if (Macros.SHIFT_TAGLIST == null) {
						Macros.SHIFT_TAGLIST = Macros.POS2TAGSMAP.get(pw.tag.toString());
						Macros.SCORED_ACTIONLIST = Macros.POS2ACTIONSMAP.get(pw.tag.toString());
					} else {
						Macros.SCORED_ACTIONLIST = Macros.WORD2ACTIONSMAP.get(pw.word.toString());
					}
				} else {
					Macros.SCORED_ACTIONLIST = Macros.CONST_ACTIONLIST;
				}
				
				m_Beam.clear();
				packed_scores.reset();
				getOrUpdateStackScore(pGenerator, packed_scores, Macros.NO_ACTION);
				/*
				 * if can swap, try swap
				 */
				if (pGenerator.canswap()) {
					swap(pGenerator, packed_scores);
				}
				/*
				 * if buffer not empty
				 * try shift
				 */
				if (pGenerator.size() < length) {
					shift(pGenerator, packed_scores);
					/*
					 * if can arc, try arc
					 */
					if (pGenerator.canarc()) {
						arcright(pGenerator, packed_scores);
						arcleft(pGenerator, packed_scores);
					}
				}
				/*
				 * if stack not empty
				 * try reduce
				 */
				if ((!pGenerator.stackempty())) {
					reduce(pGenerator, packed_scores);
				}
				
				for (int i = 0, beam_size = m_Beam.size(); i < beam_size; ++i) {
					pCandidate.copy(pGenerator);
					pCandidate.score = m_Beam.item(i).score;
					pCandidate.Move(m_Beam.item(i).action);
					m_Agenda.pushCandidate(pCandidate);
				}
				// no action means dag complete
				// push it into finish
				if (m_Beam.size() != 0) {
					finish = false;
				} else if (!bTrain) {
					m_Finish.pushCandidate(pGenerator);
				}
				if (bTrain && correctState.equals(pGenerator)) {
					bCorrect = true;
				}
				pGenerator = (StateItem)m_Agenda.generatorNext();
			}
			if (m_Agenda.candidateSize() == 0) {
				finish = true;
				break;
			}
			if (bTrain) {
				if (!bCorrect) {
					updateScoreForStates(m_Agenda.bestGenerator(), correctState, 1, -1, length);
					return;
				}
				correctState.StandardMoveStep(correct, null);
			}
			m_Agenda.nextRound();
		}
		
		if (bTrain) {
			if (!correctState.equals(m_Agenda.bestGenerator())) {				
				updateScoreForStates(m_Agenda.bestGenerator(), correctState, 1, -1, length);
				return;
			}
		}
		
		if (retval != null) {
			m_Finish.nextRound();
			m_Finish.sortGenerators();
			for (int i = 0, retval_size = minVal(m_Finish.generatorSize(), nBest); i < retval_size; ++i) {
				pGenerator = (StateItem)m_Finish.generator(i);
				if (pGenerator != null) {
					pGenerator.GenerateTree(sentence, syntaxtree, retval[i]);
					if (scores != null) scores[i] = pGenerator.score;
				}
			}
		}
	}

	public void parse(final int round, final TwoStringsVector sentence, final IntIntegerVector syntaxtree, DependencyDag[] retval,
			final int nBest, long[] scores) {
		for (int i = 0; i < nBest; ++i) {
			retval[i].length = 0;
			if (scores != null) scores[i] = 0;
		}
		work(round, false, sentence, syntaxtree, retval, null, nBest, scores);
	}

	/**
	 * Parse one sentence and update parameters if necessary.
	 * @param correct
	 * @param round
	 */
	public void train(final DependencyDag correct, final int round) {
		trainSentence.clear();
		trainSyntaxtree.clear();
		if (correct != null) {
			for (int i = 0; i < correct.length; ++i) {
				DependencyDagNode node = (DependencyDagNode)correct.nodes[i];
				trainSentence.add(new TwoStrings(node.word, node.postag));
				trainSyntaxtree.add(new IntInteger(node.treehead, node.treelabel));
			}
		}
		m_nTrainingRound = round;
		
		work(round, true, trainSentence, trainSyntaxtree, null, correct, 1, null);
	}

	@Override
	public void finishtraning() {
		((Weight)m_weights).computeAverageFeatureWeights(m_nTrainingRound);
		((Weight)m_weights).saveScores();
		System.out.println("Total number of training errors are: " + m_nTotalErrors);
	}
	
	public int totalerror() {
		return m_nTotalErrors;
	}

}