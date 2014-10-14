package common.parser.implementations.titov;

import include.AgendaBeam;
import include.AgendaSimple;
import include.learning.perceptron.PackedScoreType;
import include.learning.perceptron.Score;
import include.linguistics.CCGTagSet3;
import include.linguistics.IntInteger;
import include.linguistics.IntIntegerVector;
import include.linguistics.POSTagInt;
import include.linguistics.POSTagPOSTagInt;
import include.linguistics.POSTagSet2;
import include.linguistics.POSTagSet3;
import include.linguistics.POSTagSetOfDepLabels;
import include.linguistics.POSTaggedWord;
import include.linguistics.SetOfDepLabels;
import include.linguistics.SetOfDepLabelsInt;
import include.linguistics.SyntaxTreePath;
import include.linguistics.TwoInts;
import include.linguistics.TwoPOSTaggedWords;
import include.linguistics.TwoStrings;
import include.linguistics.TwoStringsVector;
import include.linguistics.TwoWords;
import include.linguistics.Word;
import include.linguistics.WordInt;
import include.linguistics.WordPOSTag;
import include.linguistics.WordPOSTagPOSTag;
import include.linguistics.WordSetOfDepLabels;
import include.linguistics.WordWordInt;
import include.linguistics.WordWordPOSTag;

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
	
	private WordInt word_int;
	private POSTagInt postag_int;
	private TwoInts ccgtag_int;
	private WordPOSTag word_postag;
	private WordInt word_ccgtag;
	private TwoWords word_word;
	private TwoPOSTaggedWords two_postagged_word;
	private WordPOSTagPOSTag word_postag_postag;
	private WordWordPOSTag word_word_postag;
	private WordWordInt word_word_ccgtag;
	private WordWordInt word_word_int;
	private POSTagPOSTagInt postag_postag_int;
	private WordSetOfDepLabels word_tagset;
	private POSTagSetOfDepLabels postag_tagset;
	private SetOfDepLabelsInt ccgtag_tagset;
	private POSTagSet2 set_of_2_postags;
	private POSTagSet3 set_of_3_postags;
	private CCGTagSet3 set_of_3_ccgtags;
	
	private ScoredAction scoredaction;
	
	private int[] st_id_list;
	private int[] n0_id_list;

	private static final SetOfDepLabels empty_tagset = new SetOfDepLabels();
	public static final POSTaggedWord empty_postaggedword = new POSTaggedWord();
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2) {
		return ((tag1.hashCode() << Macros.POSTAG_BITS_SIZE) | tag2.hashCode());
	}
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2, final POSTag tag3) {
		return ((tag1.hashCode() << (Macros.POSTAG_BITS_SIZE << 1)) | (tag2.hashCode() << Macros.POSTAG_BITS_SIZE) | tag3.hashCode());
	}
	
	private static final long encodeCCGTags(final long code1, final long code2, final long code3) {
		return ((code1 << (Macros.CCGTAG_BITS_SIZE << 1)) | (code2 << Macros.CCGTAG_BITS_SIZE) | code3);
	}
	
	private static final int minVal(final int n1, final int n2) {
		return n1 < n2 ? n1 : n2; 
	}
	
	private final SyntaxTreePath getPath(final StateItem item) {
		SyntaxTreePath path = new SyntaxTreePath();
		int st_index = item.stacktop();
		int n0_index = item.size(m_lCache.size());
		int st_back = 0;
		int n0_back = 0;
		int common = 1;
		while (st_index != StateItem.out_index) {
			st_id_list[st_back++] = st_index;
			st_index = m_lTree.get(st_index).m_index;
		}
		while (n0_index != StateItem.out_index) {
			n0_id_list[n0_back++] = n0_index;
			n0_index = m_lTree.get(n0_index).m_index;
		}
		while (common <= st_back && common <= n0_back && st_id_list[st_back - common] == n0_id_list[n0_back - common]) {
			++common;
		}
		for (int i = 0, n = st_back - common; i <= n; ++i) {
			path.addPos(m_lCache.get(st_id_list[i]).tag.toString().substring(0, 1));
			path.addLabel(m_lTree.get(st_id_list[i]).m_label);
		}
		if (common > 1) {
			path.addPos(m_lCache.get(st_id_list[st_back - common + 1]).tag.toString().substring(0, 1));
			path.addLabel(m_lTree.get(st_id_list[st_back - common + 1]).m_label);
		}
		for (int i = 0, n = n0_back - common; i <= n; ++i) {
			path.addPos(m_lCache.get(n0_id_list[i]).tag.toString().substring(0, 1));
			path.addLabel(m_lTree.get(n0_id_list[i]).m_label);
		}
		return path;
	}

	public DepParser(final String sFeatureDBPath, final boolean bTrain) {
		super(sFeatureDBPath, bTrain);
		
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
		
		word_int = new WordInt();
		postag_int = new POSTagInt();
		ccgtag_int = new TwoInts();
		word_postag = new WordPOSTag();
		word_ccgtag = new WordInt();
		word_word = new TwoWords();
		two_postagged_word = new TwoPOSTaggedWords();
		word_postag_postag = new WordPOSTagPOSTag();
		word_word_postag = new WordWordPOSTag();
		word_word_ccgtag = new WordWordInt();
		word_word_int = new WordWordInt();
		postag_postag_int = new POSTagPOSTagInt();
		word_tagset = new WordSetOfDepLabels();
		postag_tagset = new POSTagSetOfDepLabels();
		ccgtag_tagset = new SetOfDepLabelsInt();
		set_of_2_postags = new POSTagSet2();
		set_of_3_postags = new POSTagSet3();
		set_of_3_ccgtags = new CCGTagSet3();
		
		scoredaction = new ScoredAction();
		
		st_id_list = new int[Macros.MAX_SENTENCE_SIZE];
		n0_id_list = new int[Macros.MAX_SENTENCE_SIZE];
	}
	
	public void getOrUpdateStackScore(final StateItem item, PackedScoreType retval, final int action, final int amount, final int round) {

		final int st_index = item.stacktop();
		final int stlh_index = item.lefthead(st_index);
		final int stl2h_index = item.leftsubhead(st_index);
		final int strh_index = item.righthead(st_index);
		final int str2h_index = item.rightsubhead(st_index);
		final int stlhlh_index = item.lefthead(stlh_index);
		final int stlhrh_index = item.righthead(stlh_index);
		final int strhlh_index = item.lefthead(strh_index);
		final int strhrh_index = item.righthead(strh_index);
		final int stld_index = item.leftdep(st_index);
		final int strd_index = item.rightdep(st_index);
		final int stl2d_index = item.leftsubdep(st_index);
		final int str2d_index = item.rightsubdep(st_index);
		final int n0_index = item.size(m_lCache.size());
		final int n0ld_index = item.leftdep(n0_index);
		final int n0l2d_index = item.leftsubdep(n0_index);
		final int n0lh_index = item.lefthead(n0_index);
		final int n0l2h_index = item.leftsubhead(n0_index);
		final int n1_index = item.nextbufferhead(m_lCache.size());
		final int n2_index = item.nextbuffernext(m_lCache.size());

		final int st_lh_arity = item.leftheadarity(st_index);
		final int st_rh_arity = item.rightheadarity(st_index);
		final int st_ld_arity = item.leftdeparity(st_index);
		final int st_rd_arity = item.rightdeparity(st_index);
		final int n0_lh_arity = item.leftheadarity(n0_index);
		final int n0_ld_arity = item.leftdeparity(n0_index);

		final POSTaggedWord st_word_postag = st_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st_index);
		final POSTaggedWord stlh_word_postag = stlh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stlh_index);
		final POSTaggedWord stl2h_word_postag = stl2h_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stl2h_index);
		final POSTaggedWord strh_word_postag = strh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(strh_index);
		final POSTaggedWord str2h_word_postag = str2h_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(str2h_index);
		final POSTaggedWord stlhlh_word_postag = stlhlh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stlhlh_index);
		final POSTaggedWord stlhrh_word_postag = stlhrh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stlhrh_index);
		final POSTaggedWord strhlh_word_postag = strhlh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(strhlh_index);
		final POSTaggedWord strhrh_word_postag = strhrh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(strhrh_index);
		final POSTaggedWord stld_word_postag = stld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stld_index);
		final POSTaggedWord strd_word_postag = strd_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(strd_index);
		final POSTaggedWord stl2d_word_postag = stl2d_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(stl2d_index);
		final POSTaggedWord str2d_word_postag = str2d_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(str2d_index);
		final POSTaggedWord n0_word_postag = n0_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0_index);
		final POSTaggedWord n0ld_word_postag = n0ld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0ld_index);
		final POSTaggedWord n0l2d_word_postag = n0l2d_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0l2d_index);
		final POSTaggedWord n0lh_word_postag = n0lh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0lh_index);
		final POSTaggedWord n0l2h_word_postag = n0l2h_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0l2h_index);
		final POSTaggedWord n1_word_postag = n1_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n1_index);
		final POSTaggedWord n2_word_postag = n2_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n2_index);

		final Word st_word = st_word_postag.word;
		final Word stlh_word = stlh_word_postag.word;
		final Word stl2h_word = stl2h_word_postag.word;
		final Word strh_word = strh_word_postag.word;
		final Word str2h_word = str2h_word_postag.word;
		final Word stlhlh_word = stlhlh_word_postag.word;
		final Word stlhrh_word = stlhrh_word_postag.word;
		final Word strhlh_word = strhlh_word_postag.word;
		final Word strhrh_word = strhrh_word_postag.word;
		final Word stld_word = stld_word_postag.word;
		final Word strd_word = strd_word_postag.word;
		final Word stl2d_word = stl2d_word_postag.word;
		final Word str2d_word = str2d_word_postag.word;
		final Word n0_word = n0_word_postag.word;
		final Word n0ld_word = n0ld_word_postag.word;
		final Word n0l2d_word = n0l2d_word_postag.word;
		final Word n0lh_word = n0lh_word_postag.word;
		final Word n0l2h_word = n0l2h_word_postag.word;
		final Word n1_word = n1_word_postag.word;
		final Word n2_word = n2_word_postag.word;

		final POSTag st_postag = st_word_postag.tag;
		final POSTag stlh_postag = stlh_word_postag.tag;
		final POSTag stl2h_postag = stl2h_word_postag.tag;
		final POSTag strh_postag = strh_word_postag.tag;
		final POSTag str2h_postag = str2h_word_postag.tag;
		final POSTag stlhlh_postag = stlhlh_word_postag.tag;
		final POSTag stlhrh_postag = stlhrh_word_postag.tag;
		final POSTag strhlh_postag = strhlh_word_postag.tag;
		final POSTag strhrh_postag = strhrh_word_postag.tag;
		final POSTag stld_postag = stld_word_postag.tag;
		final POSTag strd_postag = strd_word_postag.tag;
		final POSTag stl2d_postag = stl2d_word_postag.tag;
		final POSTag str2d_postag = str2d_word_postag.tag;
		final POSTag n0_postag = n0_word_postag.tag;
		final POSTag n0ld_postag = n0ld_word_postag.tag;
		final POSTag n0l2d_postag = n0l2d_word_postag.tag;
		final POSTag n0lh_postag = n0lh_word_postag.tag;
		final POSTag n0l2h_postag = n0l2h_word_postag.tag;
		final POSTag n1_postag = n1_word_postag.tag;
		final POSTag n2_postag = n2_word_postag.tag;

		final Integer st_lh_label = Macros.integer_cache[item.leftheadlabel(st_index)];
		final Integer st_l2h_label = Macros.integer_cache[item.leftsubheadlabel(st_index)];
		final Integer st_rh_label = Macros.integer_cache[item.rightheadlabel(st_index)];
		final Integer st_r2h_label = Macros.integer_cache[item.rightsubheadlabel(st_index)];
		final Integer st_ld_label = Macros.integer_cache[item.leftdeplabel(st_index)];
		final Integer st_l2d_label = Macros.integer_cache[item.leftsubdeplabel(st_index)];
		final Integer st_rd_label = Macros.integer_cache[item.rightdeplabel(st_index)];
		final Integer st_r2d_label = Macros.integer_cache[item.rightsubdeplabel(st_index)];
		final Integer stlh_lh_label = Macros.integer_cache[item.leftheadlabel(stlh_index)];
		final Integer stlh_rh_label = Macros.integer_cache[item.rightheadlabel(stlh_index)];
		final Integer strh_lh_label = Macros.integer_cache[item.leftheadlabel(strh_index)];
		final Integer strh_rh_label = Macros.integer_cache[item.rightheadlabel(strh_index)];
		final Integer n0_lh_label = Macros.integer_cache[item.leftheadlabel(n0_index)];
		final Integer n0_ld_label = Macros.integer_cache[item.leftdeplabel(n0_index)];
		final Integer n0_l2h_label = Macros.integer_cache[item.leftsubheadlabel(n0_index)];
		final Integer n0_l2d_label = Macros.integer_cache[item.leftsubdeplabel(n0_index)];

		final Integer st_ccgtag = Macros.integer_cache[item.ccg(st_index)];
		final Integer stlh_ccgtag = Macros.integer_cache[item.ccg(stlh_index)];
		final Integer stl2h_ccgtag = Macros.integer_cache[item.ccg(stl2h_index)];
		final Integer strh_ccgtag = Macros.integer_cache[item.ccg(strh_index)];
		final Integer str2h_ccgtag = Macros.integer_cache[item.ccg(str2h_index)];
		final Integer stlhlh_ccgtag = Macros.integer_cache[item.ccg(stlhlh_index)];
		final Integer stlhrh_ccgtag = Macros.integer_cache[item.ccg(stlhrh_index)];
		final Integer strhlh_ccgtag = Macros.integer_cache[item.ccg(strhlh_index)];
		final Integer strhrh_ccgtag = Macros.integer_cache[item.ccg(strhrh_index)];
		final Integer stld_ccgtag = Macros.integer_cache[item.ccg(stld_index)];
		final Integer strd_ccgtag = Macros.integer_cache[item.ccg(strd_index)];
		final Integer stl2d_ccgtag = Macros.integer_cache[item.ccg(stl2d_index)];
		final Integer str2d_ccgtag = Macros.integer_cache[item.ccg(str2d_index)];
		final Integer n0ld_ccgtag = Macros.integer_cache[item.ccg(n0ld_index)];
		final Integer n0l2d_ccgtag = Macros.integer_cache[item.ccg(n0l2d_index)];
		final Integer n0lh_ccgtag = Macros.integer_cache[item.ccg(n0lh_index)];
		final Integer n0l2h_ccgtag = Macros.integer_cache[item.ccg(n0l2h_index)];

		final Integer st_dist0 = Macros.integer_cache[Macros.encodeLinkDistance(st_index, n0_index)];
		final Integer n0_dist0 = Macros.integer_cache[Macros.encodeLinkDistance(st_index, n0_index)];

		final SetOfDepLabels st_rtagset = st_index == -1 ? empty_tagset : new SetOfDepLabels(item.righttagset(st_index));
		final SetOfDepLabels st_ltagset = st_index == -1 ? empty_tagset : new SetOfDepLabels(item.lefttagset(st_index));
		final SetOfDepLabels n0_ltagset = n0_index == -1 ? empty_tagset : new SetOfDepLabels(item.lefttagset(n0_index));

		Weight weight = (Weight)m_weights;

		weight.m_mapSTw.getOrUpdateScore(retval, st_word, action, m_nScoreIndex, amount, round);
		weight.m_mapSTpt.getOrUpdateScore(retval, st_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(st_word, st_postag);
		weight.m_mapSTwpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		weight.m_mapSTct.getOrUpdateScore(retval, st_ccgtag, action, m_nScoreIndex, amount, round);
		word_ccgtag.refer(st_word, st_ccgtag);
		weight.m_mapSTwct.getOrUpdateScore(retval, word_ccgtag, action, m_nScoreIndex, amount, round);

		weight.m_mapN0w.getOrUpdateScore(retval, n0_word, action, m_nScoreIndex, amount, round);
		weight.m_mapN0pt.getOrUpdateScore(retval, n0_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(n0_word, n0_postag);
		weight.m_mapN0wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		
		weight.m_mapSTP.getOrUpdateScore(retval, getPath(item), action, m_nScoreIndex, amount, round);
		
		if (st_index != StateItem.out_index) {
			
			weight.m_mapSTLHw.getOrUpdateScore(retval, stlh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHpt.getOrUpdateScore(retval, stlh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHct.getOrUpdateScore(retval, stlh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTlhl.getOrUpdateScore(retval, st_lh_label, action, m_nScoreIndex, amount, round);
			
			weight.m_mapSTRHw.getOrUpdateScore(retval, strh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHpt.getOrUpdateScore(retval, strh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHct.getOrUpdateScore(retval, strh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTrhl.getOrUpdateScore(retval, st_rh_label, action, m_nScoreIndex, amount, round);

			weight.m_mapSTLDw.getOrUpdateScore(retval, stld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDpt.getOrUpdateScore(retval, stld_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDct.getOrUpdateScore(retval, stld_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTldl.getOrUpdateScore(retval, st_ld_label, action, m_nScoreIndex, amount, round);

			weight.m_mapSTRDw.getOrUpdateScore(retval, strd_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRDpt.getOrUpdateScore(retval, strd_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRDct.getOrUpdateScore(retval, strd_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTrdl.getOrUpdateScore(retval, st_rd_label, action, m_nScoreIndex, amount, round);

			word_int.refer(st_word, st_dist0);
			weight.m_mapSTwd0.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_dist0);
			weight.m_mapSTptd0.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(st_dist0, st_ccgtag);
			weight.m_mapSTctd0.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
			
			word_int.refer(st_word, st_rd_arity);
			weight.m_mapSTwrda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_rd_arity);
			weight.m_mapSTptrda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(st_rd_arity, st_ccgtag);
			weight.m_mapSTctrda.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
			word_int.refer(st_word, st_ld_arity);
			weight.m_mapSTwlda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_ld_arity);
			weight.m_mapSTptlda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(st_ld_arity, st_ccgtag);
			weight.m_mapSTctlda.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
			
			word_int.refer(st_word, st_rh_arity);
			weight.m_mapSTwrha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_rh_arity);
			weight.m_mapSTptrha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(st_rh_arity, st_ccgtag);
			weight.m_mapSTctrha.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);
			word_int.refer(st_word, st_lh_arity);
			weight.m_mapSTwlha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st_postag, st_lh_arity);
			weight.m_mapSTptlha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			ccgtag_int.refer(st_lh_arity, st_ccgtag);
			weight.m_mapSTctlha.getOrUpdateScore(retval, ccgtag_int, action, m_nScoreIndex, amount, round);

			word_tagset.refer(st_word, st_rtagset);
			weight.m_mapSTwrp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(st_postag, st_rtagset);
			weight.m_mapSTptrp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
			ccgtag_tagset.refer(st_rtagset, st_ccgtag);
			weight.m_mapSTctrp.getOrUpdateScore(retval, ccgtag_tagset, action, m_nScoreIndex, amount, round);

			word_tagset.refer(st_word, st_ltagset);
			weight.m_mapSTwlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(st_postag, st_ltagset);
			weight.m_mapSTptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
			ccgtag_tagset.refer(st_ltagset, st_ccgtag);
			weight.m_mapSTctlp.getOrUpdateScore(retval, ccgtag_tagset, action, m_nScoreIndex, amount, round);
		}
		
		if (stlh_index != StateItem.out_index) {
			
			weight.m_mapSTL2Hw.getOrUpdateScore(retval, stl2h_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Hpt.getOrUpdateScore(retval, stl2h_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Hct.getOrUpdateScore(retval, stl2h_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTl2hl.getOrUpdateScore(retval, st_l2h_label, action, m_nScoreIndex, amount, round);
			
			weight.m_mapSTLHLHw.getOrUpdateScore(retval, stlhlh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHLHpt.getOrUpdateScore(retval, stlhlh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHLHct.getOrUpdateScore(retval, stlhlh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHlhl.getOrUpdateScore(retval, stlh_lh_label, action, m_nScoreIndex, amount, round);
			
			weight.m_mapSTLHRHw.getOrUpdateScore(retval, stlhrh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHRHpt.getOrUpdateScore(retval, stlhrh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHRHct.getOrUpdateScore(retval, stlhrh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHrhl.getOrUpdateScore(retval, stlh_rh_label, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(stlhlh_postag, stlh_postag, st_postag));
			weight.m_mapSTLHLHptSTLHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(stlhrh_postag, stlh_postag, st_postag));
			weight.m_mapSTLHRHptSTLHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			set_of_3_ccgtags.load(encodeCCGTags(stlhlh_ccgtag, stlh_ccgtag, st_ccgtag));
			weight.m_mapSTLHLHctSTLHctSTct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			set_of_3_ccgtags.load(encodeCCGTags(stlhrh_ccgtag, stlh_ccgtag, st_ccgtag));
			weight.m_mapSTLHRHctSTLHctSTct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			
		}
		
		if (strh_index != StateItem.out_index) {

			weight.m_mapSTR2Hw.getOrUpdateScore(retval, str2h_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Hpt.getOrUpdateScore(retval, str2h_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Hct.getOrUpdateScore(retval, str2h_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTr2hl.getOrUpdateScore(retval, st_r2h_label, action, m_nScoreIndex, amount, round);
			
			weight.m_mapSTRHLHw.getOrUpdateScore(retval, strhlh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHLHpt.getOrUpdateScore(retval, strhlh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHLHct.getOrUpdateScore(retval, strhlh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHlhl.getOrUpdateScore(retval, strh_lh_label, action, m_nScoreIndex, amount, round);

			weight.m_mapSTRHRHw.getOrUpdateScore(retval, strhrh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHRHpt.getOrUpdateScore(retval, strhrh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHRHct.getOrUpdateScore(retval, strhrh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHrhl.getOrUpdateScore(retval, strh_rh_label, action, m_nScoreIndex, amount, round);

			set_of_3_postags.load(encodePOSTags(strhlh_postag, strh_postag, st_postag));
			weight.m_mapSTRHLHptSTRHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(strhrh_postag, strh_postag, st_postag));
			weight.m_mapSTRHRHptSTRHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			
			set_of_3_ccgtags.load(encodeCCGTags(strhlh_ccgtag, strh_ccgtag, st_ccgtag));
			weight.m_mapSTRHLHctSTRHctSTct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			set_of_3_ccgtags.load(encodeCCGTags(strhrh_ccgtag, strh_ccgtag, st_ccgtag));
			weight.m_mapSTRHRHctSTRHctSTct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			
		}
		
		if (stld_index != StateItem.out_index) {

			weight.m_mapSTL2Dw.getOrUpdateScore(retval, stl2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Dpt.getOrUpdateScore(retval, stl2d_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Dct.getOrUpdateScore(retval, stl2d_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTl2dl.getOrUpdateScore(retval, st_l2d_label, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(st_postag, stld_postag, stl2d_postag));
			weight.m_mapSTptSTLDptSTL2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			set_of_3_ccgtags.load(encodeCCGTags(st_ccgtag, stld_ccgtag, stl2d_ccgtag));
			weight.m_mapSTctSTLDctSTL2Dct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			
		}
		
		if (strd_index != StateItem.out_index) {

			weight.m_mapSTR2Dw.getOrUpdateScore(retval, str2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Dpt.getOrUpdateScore(retval, str2d_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Dct.getOrUpdateScore(retval, str2d_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTr2dl.getOrUpdateScore(retval, st_r2d_label, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(st_postag, strd_postag, str2d_postag));
			weight.m_mapSTptSTRDptSTR2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			set_of_3_ccgtags.load(encodeCCGTags(st_ccgtag, strd_ccgtag, str2d_ccgtag));
			weight.m_mapSTctSTRDctSTR2Dct.getOrUpdateScore(retval, set_of_3_ccgtags, action, m_nScoreIndex, amount, round);
			
		}
		
		if (n0_index != StateItem.out_index) {

			weight.m_mapN0LHw.getOrUpdateScore(retval, n0lh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LHpt.getOrUpdateScore(retval, n0lh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LHct.getOrUpdateScore(retval, n0lh_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0lhl.getOrUpdateScore(retval, n0_lh_label, action, m_nScoreIndex, amount, round);

			weight.m_mapN0LDw.getOrUpdateScore(retval, n0ld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDpt.getOrUpdateScore(retval, n0ld_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDct.getOrUpdateScore(retval, n0ld_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0ldl.getOrUpdateScore(retval, n0_ld_label, action, m_nScoreIndex, amount, round);
			
			weight.m_mapN1w.getOrUpdateScore(retval, n1_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN1pt.getOrUpdateScore(retval, n1_postag, action, m_nScoreIndex, amount, round);
			word_postag.refer(n1_word, n1_postag);
			weight.m_mapN1wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
			
			set_of_2_postags.load(encodePOSTags(n0_postag, n1_postag));
			weight.m_mapN0ptN1pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n1_postag));
			weight.m_mapSTptN0ptN1pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			word_int.refer(n0_word, n0_dist0);
			weight.m_mapN0wd0.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(n0_postag, n0_dist0);
			weight.m_mapN0ptd0.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);

			word_int.refer(n0_word, n0_ld_arity);
			weight.m_mapN0wlda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(n0_postag, n0_ld_arity);
			weight.m_mapN0ptlda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);

			word_int.refer(n0_word, n0_lh_arity);
			weight.m_mapN0wlha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(n0_postag, n0_lh_arity);
			weight.m_mapN0ptlha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			
			word_tagset.refer(n0_word, n0_ltagset);
			weight.m_mapN0wlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(n0_postag, n0_ltagset);
			weight.m_mapN0ptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
		}
		
		if (n0lh_index != StateItem.out_index) {

			weight.m_mapN0L2Hw.getOrUpdateScore(retval, n0l2h_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Hpt.getOrUpdateScore(retval, n0l2h_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Hct.getOrUpdateScore(retval, n0l2h_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0l2hl.getOrUpdateScore(retval, n0_l2h_label, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(n0_postag, n0lh_postag, n0l2h_postag));
			weight.m_mapN0ptN0LHptN0L2Hpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			
		}
		
		if (n0ld_index != StateItem.out_index) {

			weight.m_mapN0L2Dw.getOrUpdateScore(retval, n0l2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Dpt.getOrUpdateScore(retval, n0l2d_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Dct.getOrUpdateScore(retval, n0l2d_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0l2dl.getOrUpdateScore(retval, n0_l2d_label, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(n0_postag, n0ld_postag, n0l2d_postag));
			weight.m_mapN0ptN0LDptN0L2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			
		}
		
		if (n1_index != StateItem.out_index) {
			weight.m_mapN2w.getOrUpdateScore(retval, n2_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN2pt.getOrUpdateScore(retval, n2_postag, action, m_nScoreIndex, amount, round);
			word_postag.refer(n2_word, n2_postag);
			weight.m_mapN2wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);

			set_of_3_postags.load(encodePOSTags(n0_postag, n1_postag, n2_postag));
			weight.m_mapN0ptN1ptN2pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		}
		
		if (st_index != StateItem.out_index && n0_index != StateItem.out_index) {
			
			word_word_int.refer(st_word, n0_word, n0_dist0);
			weight.m_mapSTwN0wd0.getOrUpdateScore(retval, word_word_int, action, m_nScoreIndex, amount, round);
			postag_postag_int.refer(st_postag, n0_postag, n0_dist0);
			weight.m_mapSTptN0ptd0.getOrUpdateScore(retval, postag_postag_int, action, m_nScoreIndex, amount, round);

			two_postagged_word.refer(st_word_postag, n0_word_postag);
			weight.m_mapSTwptN0wpt.getOrUpdateScore(retval, two_postagged_word, action, m_nScoreIndex, amount, round);
			word_word_postag.refer(st_word, n0_word, st_postag);
			weight.m_mapSTwptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_word_postag.refer(st_word, n0_word, n0_postag);
			weight.m_mapSTwN0wpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(n0_word, st_postag, n0_postag);
			weight.m_mapSTptN0wpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(st_word, st_postag, n0_postag);
			weight.m_mapSTwptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
			word_word_ccgtag.refer(st_word, n0_word, st_ccgtag);
			weight.m_mapSTwctN0w.getOrUpdateScore(retval, word_word_ccgtag, action, m_nScoreIndex, amount, round);
			word_word.refer(st_word, n0_word);
			weight.m_mapSTwN0w.getOrUpdateScore(retval, word_word, action, m_nScoreIndex, amount, round);

			set_of_2_postags.load(encodePOSTags(st_postag, n0_postag));
			weight.m_mapSTptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n0ld_postag));
			weight.m_mapSTptN0ptN0LDpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n0lh_postag));
			weight.m_mapSTptN0ptN0LHpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(stlh_postag, st_postag, n0_postag));
			weight.m_mapSTLHptSTptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(strh_postag, st_postag, n0_postag));
			weight.m_mapSTRHptSTptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, stld_postag, n0_postag));
			weight.m_mapSTptSTLDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(st_postag, strd_postag, n0_postag));
			weight.m_mapSTptSTRDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		}

	}

	
	public void getOrUpdateStackScore(final StateItem item, PackedScoreType retval, final int action) {
		getOrUpdateStackScore(item, retval, action, 0, 0);
	}
	
	public void updateScoreForState(final StateItemBase from, final StateItemBase output, final int amount, final int len, final String str) {
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
		updateScoreForState(itemForStates, correct, amount_add, len, "correct");
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
				if (pGenerator.size(Macros.MAX_SENTENCE_SIZE) < length) {
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

}
