package common.parser.implementations.titov;

import include.AgendaBeam;
import include.AgendaSimple;
import include.learning.perceptron.PackedScoreType;
import include.learning.perceptron.Score;
import include.linguistics.CCGTagInt;
import include.linguistics.CCGTagSet3;
import include.linguistics.CCGTagSetOfCCGLabels;
import include.linguistics.CCGTagSetOfDepLabels;
import include.linguistics.CCGTaggedWord;
import include.linguistics.POSCCGTaggedWord;
import include.linguistics.POSTagInt;
import include.linguistics.POSTagPOSTagInt;
import include.linguistics.POSTagSet2;
import include.linguistics.POSTagSet3;
import include.linguistics.POSTagSetOfCCGLabels;
import include.linguistics.POSTagSetOfDepLabels;
import include.linguistics.POSTaggedWord;
import include.linguistics.SetOfCCGLabels;
import include.linguistics.SetOfDepLabels;
import include.linguistics.TwoPOSTaggedWords;
import include.linguistics.TwoStrings;
import include.linguistics.TwoStringsVector;
import include.linguistics.TwoWords;
import include.linguistics.Word;
import include.linguistics.WordInt;
import include.linguistics.WordPOSTagPOSTag;
import include.linguistics.WordSetOfCCGLabels;
import include.linguistics.WordSetOfDepLabels;
import include.linguistics.WordWordCCGTag;
import include.linguistics.WordWordInt;
import include.linguistics.WordWordPOSTag;

import java.util.ArrayList;

import common.parser.DepParserBase;
import common.parser.MacrosBase;
import common.parser.ScoredAction;
import common.parser.StateItemBase;
import common.parser.implementations.DependencyDag;
import common.parser.implementations.DependencyDagNode;
import common.pos.CCGTag;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class DepParser extends DepParserBase {
	
	private AgendaBeam m_Agenda;
	private AgendaBeam m_Finish;
	private AgendaSimple m_Beam;
	
	private ArrayList<POSTaggedWord> m_lCache;
	
	private int m_nTrainingRound;
	private int m_nTotalErrors;
	private int m_nScoreIndex;
	
	private StateItem itemForState;
	private StateItem itemForStates;
	
	private StateItem pCandidate;
	private StateItem correctState;
	private PackedScoreType packed_scores;
	
	private TwoStringsVector trainSentence;
	
	private TwoPOSTaggedWords st_word_postag_n0_word_postag;
	private TwoWords st_word_n0_word;
	
	private CCGTag ccgtag;
	private CCGTaggedWord word_ccgtag;
	
	private WordInt word_int;
	private POSTagInt postag_int;
	private CCGTagInt ccgtag_int;
	private WordPOSTagPOSTag word_postag_postag;
	private WordWordPOSTag word_word_postag;
	private WordWordCCGTag word_word_ccgtag;
	private WordWordInt word_word_int;
	private POSTagPOSTagInt postag_postag_int;
	private WordSetOfDepLabels word_tagset;
	private POSTagSetOfDepLabels postag_tagset;
	private CCGTagSetOfDepLabels ccgtag_tagset;
	private WordSetOfCCGLabels word_ccgset;
	private POSTagSetOfCCGLabels postag_ccgset;
	private CCGTagSetOfCCGLabels ccgtag_ccgset;
	private POSTagSet2 set_of_2_postags;
	private POSTagSet3 set_of_3_postags;
	private CCGTagSet3 set_of_3_ccgtags;
	
	private ScoredAction scoredaction;
	
	public static final POSCCGTaggedWord empty_posccgtaggedword = new POSCCGTaggedWord();
	public static final POSTaggedWord empty_postaggedword = new POSTaggedWord();
	
	public static final int encodePOSTags(final POSTag tag1, final POSTag tag2) {
		return ((tag1.hashCode() << (Macros.POSTAG_BITS_SIZE)) | (tag2.hashCode()));
	}
	
	public static final int encodePOSTags(final POSTag tag1, final POSTag tag2, final POSTag tag3) {
		return ((tag1.hashCode() << (Macros.POSTAG_BITS_SIZE << 1)) | (tag2.hashCode() << (Macros.POSTAG_BITS_SIZE)) | (tag3.hashCode()));
	}
	
	public static final long encodeCCGTags(final CCGTag tag1, final CCGTag tag2) {
		return ((tag1.hashCode() << (Macros.CCGTAG_BITS_SIZE)) | (tag2.hashCode()));
	}
	
	public static final long encodeCCGTags(final CCGTag tag1, final CCGTag tag2, final CCGTag tag3) {
		long code = tag1.hashCode();
		code <<= (Macros.CCGTAG_BITS_SIZE << 1);
		return code | (tag2.hashCode() << (Macros.CCGTAG_BITS_SIZE)) | (tag3.hashCode());
	}
	
	private int minVal(final int n1, final int n2) {
		return n1 < n2 ? n1 : n2; 
	}

	public DepParser(final String sFeatureDBPath, final boolean bTrain) {
		super(sFeatureDBPath, bTrain);
		
		m_Agenda = new AgendaBeam(Macros.AGENDA_SIZE, new StateItem());
		m_Finish = new AgendaBeam(Macros.AGENDA_SIZE, new StateItem());
		m_Beam = new AgendaSimple(Macros.AGENDA_SIZE);
		
		m_lCache = new ArrayList<POSTaggedWord>();
		
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
		
		st_word_postag_n0_word_postag = new TwoPOSTaggedWords();
		st_word_n0_word = new TwoWords();
		
		ccgtag = new CCGTag();
		word_ccgtag = new CCGTaggedWord();
		
		word_int = new WordInt();
		postag_int = new POSTagInt();
		ccgtag_int = new CCGTagInt();
		word_postag_postag = new WordPOSTagPOSTag();
		word_word_postag = new WordWordPOSTag();
		word_word_ccgtag = new WordWordCCGTag();
		word_word_int = new WordWordInt();
		postag_postag_int = new POSTagPOSTagInt();
		word_tagset = new WordSetOfDepLabels();
		postag_tagset = new POSTagSetOfDepLabels();
		ccgtag_tagset = new CCGTagSetOfDepLabels();
		word_ccgset = new WordSetOfCCGLabels();
		postag_ccgset = new POSTagSetOfCCGLabels();
		ccgtag_ccgset = new CCGTagSetOfCCGLabels();
		set_of_2_postags = new POSTagSet2();
		set_of_3_postags = new POSTagSet3();
		set_of_3_ccgtags = new CCGTagSet3();
		
		scoredaction = new ScoredAction();
	}
	
	public void getOrUpdateStackScore(final StateItem item, PackedScoreType retval, final int action, final int amount, final int round) {
		
		final int st_index = item.stackempty() ? -1 : item.stacktop();
		final int sth_index = st_index == -1 ? -1 : item.head(st_index);
		final int sthh_index = sth_index == -1 ? -1 : item.head(sth_index);
		final int stld_index = st_index == -1 ? -1 : item.leftdep(st_index);
		final int strd_index = st_index == -1 ? -1 : item.rightdep(st_index);
		final int stl2d_index = stld_index == -1 ? -1 : item.sibling(stld_index);
		final int str2d_index = strd_index == -1 ? -1 : item.sibling(strd_index);
		final int n0_index = ((item.size() == m_lCache.size()) ? -1 : item.size());
		final int n0ld_index = n0_index == -1 ? -1 : item.leftdep(n0_index);
		final int n0l2d_index = n0ld_index == -1 ? -1 : item.sibling(n0ld_index);
		final int n1_index = ((n0_index + 1 < m_lCache.size()) ? n0_index + 1 : -1);
		final int n2_index = ((n0_index + 2 < m_lCache.size()) ? n0_index + 2 : -1);
		
		final POSTaggedWord st_word_postag = st_index == -1 ? empty_postaggedword : m_lCache.get(st_index);
		final POSTaggedWord sth_word_postag = sth_index == -1 ? empty_postaggedword : m_lCache.get(sth_index);
		final POSTaggedWord sthh_word_postag = sthh_index == -1 ? empty_postaggedword : m_lCache.get(sthh_index);
		final POSTaggedWord stld_word_postag = stld_index == -1 ? empty_postaggedword : m_lCache.get(stld_index);
		final POSTaggedWord strd_word_postag = strd_index == -1 ? empty_postaggedword : m_lCache.get(strd_index);
		final POSTaggedWord stl2d_word_postag = stl2d_index == -1 ? empty_postaggedword : m_lCache.get(stl2d_index);
		final POSTaggedWord str2d_word_postag = str2d_index == -1 ? empty_postaggedword : m_lCache.get(str2d_index);
		final POSTaggedWord n0_word_postag = n0_index == -1 ? empty_postaggedword : m_lCache.get(n0_index);
		final POSTaggedWord n0ld_word_postag = n0ld_index == -1 ? empty_postaggedword : m_lCache.get(n0ld_index);
		final POSTaggedWord n0l2d_word_postag = n0l2d_index == -1 ? empty_postaggedword : m_lCache.get(n0l2d_index);
		final POSTaggedWord n1_word_postag = n1_index == -1 ? empty_postaggedword : m_lCache.get(n1_index);
		final POSTaggedWord n2_word_postag = n2_index == -1 ? empty_postaggedword : m_lCache.get(n2_index);
		
		final Word st_word = st_word_postag.word;
		final Word sth_word = sth_word_postag.word;
		final Word sthh_word = sthh_word_postag.word;
		final Word stld_word = stld_word_postag.word;
		final Word strd_word = strd_word_postag.word;
		final Word stl2d_word = stl2d_word_postag.word;
		final Word str2d_word = str2d_word_postag.word;
		final Word n0_word = n0_word_postag.word;
		final Word n0ld_word = n0ld_word_postag.word;
		final Word n0l2d_word = n0l2d_word_postag.word;
		final Word n1_word = n1_word_postag.word;
		final Word n2_word = n2_word_postag.word;
		
		final POSTag st_postag = st_word_postag.tag;
		final POSTag sth_postag = sth_word_postag.tag;
		final POSTag sthh_postag = sthh_word_postag.tag;
		final POSTag stld_postag = stld_word_postag.tag;
		final POSTag strd_postag = strd_word_postag.tag;
		final POSTag stl2d_postag = stl2d_word_postag.tag;
		final POSTag str2d_postag = str2d_word_postag.tag;
		final POSTag n0_postag = n0_word_postag.tag;
		final POSTag n0ld_postag = n0ld_word_postag.tag;
		final POSTag n0l2d_postag = n0l2d_word_postag.tag;
		final POSTag n1_postag = n1_word_postag.tag;
		final POSTag n2_postag = n2_word_postag.tag;
		
		final int st_label = st_index == -1 ? Macros.DEP_NONE : item.label(st_index);
		final int sth_label = sth_index == -1 ? Macros.DEP_NONE : item.label(sth_index);
		final int stld_label = stld_index == -1 ? Macros.DEP_NONE : item.label(stld_index);
		final int strd_label = strd_index == -1 ? Macros.DEP_NONE : item.label(strd_index);
		final int stl2d_label = stl2d_index == -1 ? Macros.DEP_NONE : item.label(stl2d_index);
		final int str2d_label = str2d_index == -1 ? Macros.DEP_NONE : item.label(str2d_index); //PROBLEM!
		final int n0ld_label = n0ld_index == -1 ? Macros.DEP_NONE : item.label(n0ld_index);
		final int n0l2d_label = n0l2d_index == -1 ? Macros.DEP_NONE : item.label(n0l2d_index);
		
		final int st_ccg = st_index == -1 ? Macros.CCGTAG_NONE : item.ccg(st_index);
		final int sth_ccg = sth_index == -1 ? Macros.CCGTAG_NONE : item.ccg(sth_index);
		final int sthh_ccg = sthh_index == -1 ? Macros.CCGTAG_NONE : item.ccg(sthh_index);
		final int stld_ccg = stld_index == -1 ? Macros.CCGTAG_NONE : item.ccg(stld_index);
		final int strd_ccg = strd_index == -1 ? Macros.CCGTAG_NONE : item.ccg(strd_index);
		final int stl2d_ccg = stl2d_index == -1 ? Macros.CCGTAG_NONE : item.ccg(stl2d_index);
		final int str2d_ccg = str2d_index == -1 ? Macros.CCGTAG_NONE : item.ccg(str2d_index);
		final int n0ld_ccg = n0ld_index == -1 ? Macros.CCGTAG_NONE : item.ccg(n0ld_index);
		final int n0l2d_ccg = n0l2d_index == -1 ? Macros.CCGTAG_NONE : item.ccg(n0l2d_index);
		
		final int st_n0_dist = Macros.encodeLinkDistance(st_index, n0_index);
		
		final int st_rarity = st_index == -1 ? 0 : item.rightarity(st_index);
		final int st_larity = st_index == -1 ? 0 : item.leftarity(st_index);
		final int n0_larity = n0_index == -1 ? 0 : item.leftarity(n0_index);
		
		final SetOfDepLabels st_rtagset = st_index == -1 ? null : new SetOfDepLabels(item.righttagset(st_index));
		final SetOfDepLabels st_ltagset = st_index == -1 ? null : new SetOfDepLabels(item.lefttagset(st_index));
		final SetOfCCGLabels st_lccgset = st_index == -1 ? null : new SetOfCCGLabels(item.leftccgset(st_index));
		final SetOfDepLabels n0_ltagset = n0_index == -1 ? null : new SetOfDepLabels(item.lefttagset(n0_index));
		final SetOfCCGLabels n0_lccgset = n0_index == -1 ? null : new SetOfCCGLabels(item.leftccgset(n0_index));
		
		Weight weight = (Weight)m_weights;
		
		if (st_index != -1) {
			weight.m_mapSTw.getOrUpdateScore(retval, st_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTpt.getOrUpdateScore(retval, st_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTwpt.getOrUpdateScore(retval, st_word_postag, action, m_nScoreIndex, amount, round);
			ccgtag.load(st_ccg);
			weight.m_mapSTct.getOrUpdateScore(retval, ccgtag, action, m_nScoreIndex, amount, round);
			word_ccgtag.load(st_word, ccgtag);
			weight.m_mapSTwct.getOrUpdateScore(retval, word_ccgtag, action, m_nScoreIndex, amount, round);
		}

		if (n0_index != -1) {
			weight.m_mapN0w.getOrUpdateScore(retval, n0_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0pt.getOrUpdateScore(retval, n0_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0wpt.getOrUpdateScore(retval, n0_word_postag, action, m_nScoreIndex, amount, round);
		}

		if (n1_index != -1) {
			weight.m_mapN1w.getOrUpdateScore(retval, n1_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN1pt.getOrUpdateScore(retval, n1_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN1wpt.getOrUpdateScore(retval, n1_word_postag, action, m_nScoreIndex, amount, round);
		}

		if (n2_index != -1) {
			weight.m_mapN2w.getOrUpdateScore(retval, n2_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN2pt.getOrUpdateScore(retval, n2_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN2wpt.getOrUpdateScore(retval, n2_word_postag, action, m_nScoreIndex, amount, round);
		}

		if (sth_index != -1) {
			weight.m_mapSTHw.getOrUpdateScore(retval, sth_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTHpt.getOrUpdateScore(retval, sth_postag, action, m_nScoreIndex, amount, round);
			if (sth_ccg >= 0) {
				weight.m_mapSTHct.getOrUpdateScore(retval, MacrosBase.integer_cache[sth_ccg], action, m_nScoreIndex, amount, round);
			}
			weight.m_mapSTi.getOrUpdateScore(retval, MacrosBase.integer_cache[st_label], action, m_nScoreIndex, amount, round);
		}

		if (sthh_index != -1) {
			weight.m_mapSTHHw.getOrUpdateScore(retval, sthh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTHHpt.getOrUpdateScore(retval, sthh_postag, action, m_nScoreIndex, amount, round);
			if (sthh_ccg >= 0) {
				weight.m_mapSTHHct.getOrUpdateScore(retval, MacrosBase.integer_cache[sthh_ccg], action, m_nScoreIndex, amount, round);
			}
			weight.m_mapSTHi.getOrUpdateScore(retval, MacrosBase.integer_cache[sth_label], action, m_nScoreIndex, amount, round);
		}

		if (stld_index != -1) {
			weight.m_mapSTLDw.getOrUpdateScore(retval, stld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDpt.getOrUpdateScore(retval, stld_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDct.getOrUpdateScore(retval, MacrosBase.integer_cache[stld_ccg], action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDi.getOrUpdateScore(retval, MacrosBase.integer_cache[stld_label], action, m_nScoreIndex, amount, round);
		}

		if (strd_index != -1) {
			weight.m_mapSTRDw.getOrUpdateScore(retval, strd_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRDpt.getOrUpdateScore(retval, strd_postag, action, m_nScoreIndex, amount, round);
			if (strd_ccg >= 0) {
				weight.m_mapSTRDct.getOrUpdateScore(retval, MacrosBase.integer_cache[strd_ccg], action, m_nScoreIndex, amount, round);
			}
			weight.m_mapSTRDi.getOrUpdateScore(retval, MacrosBase.integer_cache[strd_label], action, m_nScoreIndex, amount, round);
		}

		if (n0ld_index != -1) {
			weight.m_mapN0LDw.getOrUpdateScore(retval, n0ld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDpt.getOrUpdateScore(retval, n0ld_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDct.getOrUpdateScore(retval, MacrosBase.integer_cache[n0ld_ccg], action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDi.getOrUpdateScore(retval, MacrosBase.integer_cache[n0ld_label], action, m_nScoreIndex, amount, round);
		}

		if (stl2d_index != -1) {
			weight.m_mapSTL2Dw.getOrUpdateScore(retval, stl2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Dpt.getOrUpdateScore(retval, stl2d_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Dct.getOrUpdateScore(retval, MacrosBase.integer_cache[stl2d_ccg], action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Di.getOrUpdateScore(retval, MacrosBase.integer_cache[stl2d_label], action, m_nScoreIndex, amount, round);
		}

		if (str2d_index != -1) {
			weight.m_mapSTR2Dw.getOrUpdateScore(retval, str2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Dpt.getOrUpdateScore(retval, str2d_postag, action, m_nScoreIndex, amount, round);
			if (str2d_ccg >= 0) {
				weight.m_mapSTRDct.getOrUpdateScore(retval, MacrosBase.integer_cache[str2d_ccg], action, m_nScoreIndex, amount, round);
			}
			weight.m_mapSTR2Di.getOrUpdateScore(retval, MacrosBase.integer_cache[str2d_label], action, m_nScoreIndex, amount, round);
		}

		if (n0l2d_index != -1) {
			weight.m_mapN0L2Dw.getOrUpdateScore(retval, n0l2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Dpt.getOrUpdateScore(retval, n0l2d_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Dct.getOrUpdateScore(retval, MacrosBase.integer_cache[n0l2d_ccg], action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Di.getOrUpdateScore(retval, MacrosBase.integer_cache[n0l2d_label], action, m_nScoreIndex, amount, round);
		}
		
		if (st_index != -1) {
			st_word_postag_n0_word_postag.refer(st_word_postag, n0_word_postag);
			weight.m_mapSTwptN0wpt.getOrUpdateScore(retval, st_word_postag_n0_word_postag, action, m_nScoreIndex, amount, round);
			word_word_postag.refer(st_word, n0_word, st_postag);
			weight.m_mapSTwptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_word_postag.refer(st_word, n0_word, n0_postag);
			weight.m_mapSTwN0wpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(st_word, st_postag, n0_postag);
			weight.m_mapSTwptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(n0_word, st_postag, n0_postag);
			weight.m_mapSTptN0wpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
			set_of_2_postags.load(encodePOSTags(st_postag, n0_postag));
			weight.m_mapSTptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
			
			word_word_ccgtag.refer(st_word, n0_word, ccgtag);
			weight.m_mapSTwctN0w.getOrUpdateScore(retval, word_word_ccgtag, action, m_nScoreIndex, amount, round);

			st_word_n0_word.refer(st_word, n0_word);
			weight.m_mapSTwN0w.getOrUpdateScore(retval, st_word_n0_word, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1 && n0_index != -1) {
			set_of_2_postags.load(encodePOSTags(n0_postag, n1_postag));
			weight.m_mapN0ptN1pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(n0_postag, n1_postag, n2_postag));
			weight.m_mapN0ptN1ptN2pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n1_postag));
			weight.m_mapSTptN0ptN1pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n0ld_postag));
			weight.m_mapSTptN0ptN0LDpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(n0_postag, n0ld_postag, n0l2d_postag));
			weight.m_mapN0ptN0LDptN0L2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		}
		
		if (st_index != -1) {
			set_of_3_postags.load(encodePOSTags(sth_postag, st_postag, n0_postag));
			weight.m_mapSTHptSTptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(sthh_postag, sth_postag, st_postag));
			weight.m_mapSTHHptSTHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, stld_postag, n0_postag));
			weight.m_mapSTptSTLDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, stld_postag, stl2d_postag));
			weight.m_mapSTptSTLDptSTL2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, strd_postag, n0_postag));
			weight.m_mapSTptSTRDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, strd_postag, str2d_postag));
			weight.m_mapSTptSTRDptSTR2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			
			if (sth_index != -1 && sthh_index != -1 &&
					item.ccg(sth_index) != Macros.CCGTAG_NONE && item.ccg(sthh_index) != Macros.CCGTAG_NONE) {
				set_of_3_ccgtags.clear();
				set_of_3_ccgtags.attach(item.ccg(sthh_index));
				set_of_3_ccgtags.attach(item.ccg(sth_index));
				set_of_3_ccgtags.attach(item.ccg(st_index));
				weight.m_mapSTHHctSTHctSTct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			}
			
			if (stld_index != -1 && stl2d_index != -1) {
				set_of_3_ccgtags.clear();
				set_of_3_ccgtags.attach(item.ccg(st_index));
				set_of_3_ccgtags.attach(item.ccg(stld_index));
				set_of_3_ccgtags.attach(item.ccg(stl2d_index));
				weight.m_mapSTctSTLDctSTL2Dct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			}
			
			if (strd_index != -1 && str2d_index != -1 &&
					item.ccg(strd_index) != Macros.CCGTAG_NONE && item.ccg(str2d_index) != Macros.CCGTAG_NONE) {
				set_of_3_ccgtags.clear();
				set_of_3_ccgtags.attach(item.ccg(st_index));
				set_of_3_ccgtags.attach(item.ccg(strd_index));
				set_of_3_ccgtags.attach(item.ccg(str2d_index));
				weight.m_mapSTctSTRDctSTR2Dct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			}
		}

		if (st_index != -1 && n0_index != -1) {
			word_int.refer(st_word, st_n0_dist);
			weight.m_mapSTwd.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_n0_dist);
			weight.m_mapSTptd.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(ccgtag, st_n0_dist);
			weight.m_mapSTctd.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
			
			word_int.refer(n0_word, st_n0_dist);
			weight.m_mapN0wd.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(n0_postag, st_n0_dist);
			weight.m_mapN0ptd.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			
			word_word_int.refer(st_word, n0_word, st_n0_dist);
			weight.m_mapSTwN0wd.getOrUpdateScore(retval, word_word_int, action, m_nScoreIndex, amount, round);
			postag_postag_int.refer(st_postag, n0_postag, st_n0_dist);
			weight.m_mapSTptN0ptd.getOrUpdateScore(retval, postag_postag_int, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1) {
			word_int.refer(st_word, st_rarity);
			weight.m_mapSTwra.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_rarity);
			weight.m_mapSTptra.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(ccgtag, st_rarity);
			weight.m_mapSTctra.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
			
			word_int.refer(st_word, st_larity);
			weight.m_mapSTwla.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_larity);
			weight.m_mapSTptla.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(ccgtag, st_larity);
			weight.m_mapSTctla.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
		}

		if (n0_index != -1) {
			word_int.refer(n0_word, n0_larity);
			weight.m_mapN0wla.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(n0_postag, n0_larity);
			weight.m_mapN0ptla.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1){
			word_tagset.refer(st_word, st_rtagset);
			weight.m_mapSTwrp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(st_postag, st_rtagset);
			weight.m_mapSTptrp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
			ccgtag_tagset.refer(ccgtag, st_rtagset);
			weight.m_mapSTctrp.getOrUpdateScore(retval, ccgtag_tagset, action, m_nScoreIndex, amount, round);
			
			word_tagset.refer(st_word, st_ltagset);
			weight.m_mapSTwlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(st_postag, st_ltagset);
			weight.m_mapSTptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
			ccgtag_tagset.refer(ccgtag, st_ltagset);
			weight.m_mapSTctlp.getOrUpdateScore(retval, ccgtag_tagset, action, m_nScoreIndex, amount, round);
			
			word_ccgset.refer(st_word, st_lccgset);
			weight.m_mapSTwlc.getOrUpdateScore(retval, word_ccgset, action, m_nScoreIndex, amount, round);
			postag_ccgset.refer(st_postag, st_lccgset);
			weight.m_mapSTptlc.getOrUpdateScore(retval, postag_ccgset, action, m_nScoreIndex, amount, round);
			ccgtag_ccgset.refer(ccgtag, st_lccgset);
			weight.m_mapSTctlc.getOrUpdateScore(retval, ccgtag_ccgset, action, m_nScoreIndex, amount, round);			
		}

		if (n0_index != -1){
			word_tagset.refer(n0_word, n0_ltagset);
			weight.m_mapN0wlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(n0_postag, n0_ltagset);
			weight.m_mapN0ptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);

			word_ccgset.refer(n0_word, n0_lccgset);
			weight.m_mapN0wlc.getOrUpdateScore(retval, word_ccgset, action, m_nScoreIndex, amount, round);
			postag_ccgset.refer(n0_postag, n0_lccgset);
			weight.m_mapN0ptlc.getOrUpdateScore(retval, postag_ccgset, action, m_nScoreIndex, amount, round);
		}
	}

	public void getOrUpdateStackScore(final StateItem item, PackedScoreType retval, final int action) {
		getOrUpdateStackScore(item, retval, action, 0, 0);
	}
	
	public void updateScoreForState(final StateItemBase from, final StateItemBase output, final int amount, final int len, final String str) {
		itemForState.copy(from);
//		((StateItem)output).print();
		while (!itemForState.equals(output)) {
			int action = itemForState.FollowMove(output);
//			System.out.print("action " + str + " = ");
//			Action.print(action);
			getOrUpdateStackScore(itemForState, null, action, amount, m_nTrainingRound);
			if (action >= Macros.AL_FIRST) {
//				System.out.println("action = " + action);
//				System.out.println("stack back seek = " + itemForStates.stack_back);
//				System.out.println("stack back = " + itemForStates.m_lStack[itemForState.stack_back]);
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
//				System.out.print("action = ");
//				Action.print(action);
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
//		((StateItem)correct).print();
		updateScoreForState(itemForStates, correct, amount_add, len, "correct");
//		((StateItem)output).print();
		updateScoreForState(itemForStates, output, amount_subtract, len, "output");
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
		for (int label = Macros.CCGTAG_FIRST; label < Macros.CCGTAG_COUNT; ++label) {
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
	
	public void work(final int round, final boolean bTrain, final TwoStringsVector sentence, DependencyDag[] retval, final DependencyDag correct, final int nBest, long[] scores) {
		final int length = sentence.size();
		StateItem pGenerator;

		boolean finish = false;
		boolean bCorrect = false;
		
		m_lCache.clear();
		for (int index = 0; index < length; ++index) {
			m_lCache.add(new POSTaggedWord(sentence.get(index).m_string1, sentence.get(index).m_string2));
		}
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
			correctState.StandardFinish();
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
					pGenerator.GenerateTree(sentence, retval[i]);
					if (scores != null) scores[i] = pGenerator.score;
				}
			}
		}
	}

	public void parse(final int round, final TwoStringsVector sentence, DependencyDag[] retval,
			final int nBest, long[] scores) {
		for (int i = 0; i < nBest; ++i) {
			retval[i].length = 0;
			if (scores != null) scores[i] = 0;
		}
		work(round, false, sentence, retval, null, nBest, scores);
	}

	public void train(final DependencyDag correct, final int round) {
		trainSentence.clear();
		if (correct != null) {
			for (int i = 0; i < correct.length; ++i) {
				DependencyDagNode node = (DependencyDagNode)correct.nodes[i];
				trainSentence.add(new TwoStrings(node.word, node.postag));
			}
		}
		m_nTrainingRound = round;
		work(round, true, trainSentence, null, correct, 1, null);
	}

	@Override
	public void finishtraning() {
		((Weight)m_weights).computeAverageFeatureWeights(m_nTrainingRound);
		((Weight)m_weights).saveScores();
		System.out.println("Total number of training errors are: " + m_nTotalErrors);
	}

}
