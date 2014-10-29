package common.parser.implementations.twostack;

import include.AgendaBeam;
import include.AgendaSimple;
import include.learning.perceptron.PackedScoreType;
import include.learning.perceptron.Score;
import include.linguistics.IntInteger;
import include.linguistics.IntIntegerVector;
import include.linguistics.POSTagInt;
import include.linguistics.POSTagPOSTagInt;
import include.linguistics.POSTagSet2;
import include.linguistics.POSTagSet3;
import include.linguistics.POSTagSetOfDepLabels;
import include.linguistics.POSTaggedWord;
import include.linguistics.SetOfDepLabels;
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
	
	private WordInt word_int;
	private POSTagInt postag_int;
	private WordPOSTag word_postag;
	private TwoWords word_word;
	private TwoInts int_int;
	private TwoPOSTaggedWords two_postagged_word;
	private WordPOSTagPOSTag word_postag_postag;
	private WordWordPOSTag word_word_postag;
	private WordWordInt word_word_int;
	private POSTagPOSTagInt postag_postag_int;
	private WordSetOfDepLabels word_tagset;
	private POSTagSetOfDepLabels postag_tagset;
	private POSTagSet2 set_of_2_postags;
	private POSTagSet3 set_of_3_postags;
	
	private ScoredAction scoredaction;

	private static final SetOfDepLabels empty_tagset = new SetOfDepLabels();
	public static final POSTaggedWord empty_postaggedword = new POSTaggedWord();
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2) {
		return ((tag1.hashCode() << Macros.POSTAG_BITS_SIZE) | tag2.hashCode());
	}
	
	private static final int encodePOSTags(final POSTag tag1, final POSTag tag2, final POSTag tag3) {
		return ((tag1.hashCode() << (Macros.POSTAG_BITS_SIZE << 1)) | (tag2.hashCode() << Macros.POSTAG_BITS_SIZE) | tag3.hashCode());
	}
	
	private static final int minVal(final int n1, final int n2) {
		return n1 < n2 ? n1 : n2; 
	}

	public DepParser(final String sFeatureDBPath, final boolean bTrain, final boolean supertag) {
		super(sFeatureDBPath, bTrain);
		
		m_bSupertag = supertag;
		
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
		
		word_int = new WordInt();
		postag_int = new POSTagInt();
		word_postag = new WordPOSTag();
		word_word = new TwoWords();
		int_int = new TwoInts();
		two_postagged_word = new TwoPOSTaggedWords();
		word_postag_postag = new WordPOSTagPOSTag();
		word_word_postag = new WordWordPOSTag();
		word_word_int = new WordWordInt();
		postag_postag_int = new POSTagPOSTagInt();
		word_tagset = new WordSetOfDepLabels();
		postag_tagset = new POSTagSetOfDepLabels();
		set_of_2_postags = new POSTagSet2();
		set_of_3_postags = new POSTagSet3();
		
		scoredaction = new ScoredAction();
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
		
		final int st2_index = item.stacktop2();
		final int st2lh_index = item.lefthead(st2_index);
		final int st2rh_index = item.righthead(st2_index);
		final int st2ld_index = item.leftdep(st2_index);
		final int st2rd_index = item.rightdep(st2_index);

		final int sst_index = item.secstacktop();
		final int sstlh_index = item.lefthead(sst_index);
		final int sstrh_index = item.righthead(sst_index);
		final int sstld_index = item.leftdep(sst_index);
		final int sstrd_index = item.rightdep(sst_index);
		
		final int n0_index = item.size(m_lCache.size());
		final int n0ld_index = item.leftdep(n0_index);
		final int n0l2d_index = item.leftsubdep(n0_index);
		final int n0lh_index = item.lefthead(n0_index);
		final int n0l2h_index = item.leftsubhead(n0_index);
		final int n1_index = item.nextbufferhead(m_lCache.size());
		final int n2_index = item.nextbuffernext(m_lCache.size());
		final int n_1_index = item.beforebufferhead();
		final int n_2_index = item.beforebufferbefore();

		final int st_lh_arity = item.leftheadarity(st_index);
		final int st_rh_arity = item.rightheadarity(st_index);
		final int st_ld_arity = item.leftdeparity(st_index);
		final int st_rd_arity = item.rightdeparity(st_index);
		final int st2_lh_arity = item.leftheadarity(st2_index);
		final int st2_rh_arity = item.rightheadarity(st2_index);
		final int st2_ld_arity = item.leftdeparity(st2_index);
		final int st2_rd_arity = item.rightdeparity(st2_index);
		final int sst_lh_arity = item.leftheadarity(sst_index);
		final int sst_rh_arity = item.rightheadarity(sst_index);
		final int sst_ld_arity = item.leftdeparity(sst_index);
		final int sst_rd_arity = item.rightdeparity(sst_index);
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

		final POSTaggedWord st2_word_postag = st2_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2_index);
		final POSTaggedWord st2ld_word_postag = st2ld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2ld_index);
		final POSTaggedWord st2rd_word_postag = st2rd_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2rd_index);
		final POSTaggedWord st2lh_word_postag = st2lh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2lh_index);
		final POSTaggedWord st2rh_word_postag = st2rh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(st2rh_index);

		final POSTaggedWord sst_word_postag = sst_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(sst_index);
		final POSTaggedWord sstld_word_postag = sstld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(sstld_index);
		final POSTaggedWord sstrd_word_postag = sstrd_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(sstrd_index);
		final POSTaggedWord sstlh_word_postag = sstlh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(sstlh_index);
		final POSTaggedWord sstrh_word_postag = sstrh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(sstrh_index);
		
		final POSTaggedWord n0_word_postag = n0_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0_index);
		final POSTaggedWord n0ld_word_postag = n0ld_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0ld_index);
		final POSTaggedWord n0l2d_word_postag = n0l2d_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0l2d_index);
		final POSTaggedWord n0lh_word_postag = n0lh_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0lh_index);
		final POSTaggedWord n0l2h_word_postag = n0l2h_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n0l2h_index);
		final POSTaggedWord n1_word_postag = n1_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n1_index);
		final POSTaggedWord n2_word_postag = n2_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n2_index);
		final POSTaggedWord n_1_word_postag = n_1_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n_1_index);
		final POSTaggedWord n_2_word_postag = n_2_index == StateItem.out_index ? empty_postaggedword : m_lCache.get(n_2_index);

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
		
		final Word st2_word = st2_word_postag.word;
		final Word st2ld_word = st2ld_word_postag.word;
		final Word st2rd_word = st2rd_word_postag.word;
		final Word st2lh_word = st2lh_word_postag.word;
		final Word st2rh_word = st2rh_word_postag.word;
		
		final Word sst_word = sst_word_postag.word;
		final Word sstld_word = sstld_word_postag.word;
		final Word sstrd_word = sstrd_word_postag.word;
		final Word sstlh_word = sstlh_word_postag.word;
		final Word sstrh_word = sstrh_word_postag.word;
		
		final Word n0_word = n0_word_postag.word;
		final Word n0ld_word = n0ld_word_postag.word;
		final Word n0l2d_word = n0l2d_word_postag.word;
		final Word n0lh_word = n0lh_word_postag.word;
		final Word n0l2h_word = n0l2h_word_postag.word;
		final Word n1_word = n1_word_postag.word;
		final Word n2_word = n2_word_postag.word;
		final Word n_1_word = n_1_word_postag.word;
		final Word n_2_word = n_2_word_postag.word;

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

		final POSTag st2_postag = st2_word_postag.tag;
		final POSTag st2ld_postag = st2ld_word_postag.tag;
		final POSTag st2rd_postag = st2rd_word_postag.tag;
		final POSTag st2lh_postag = st2lh_word_postag.tag;
		final POSTag st2rh_postag = st2rh_word_postag.tag;

		final POSTag sst_postag = sst_word_postag.tag;
		final POSTag sstld_postag = sstld_word_postag.tag;
		final POSTag sstrd_postag = sstrd_word_postag.tag;
		final POSTag sstlh_postag = sstlh_word_postag.tag;
		final POSTag sstrh_postag = sstrh_word_postag.tag;
		
		final POSTag n0_postag = n0_word_postag.tag;
		final POSTag n0ld_postag = n0ld_word_postag.tag;
		final POSTag n0l2d_postag = n0l2d_word_postag.tag;
		final POSTag n0lh_postag = n0lh_word_postag.tag;
		final POSTag n0l2h_postag = n0l2h_word_postag.tag;
		final POSTag n1_postag = n1_word_postag.tag;
		final POSTag n2_postag = n2_word_postag.tag;
		final POSTag n_1_postag = n_1_word_postag.tag;
		final POSTag n_2_postag = n_2_word_postag.tag;

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
		
		final Integer st2_lh_label = Macros.integer_cache[item.leftheadlabel(st2_index)];
		final Integer st2_rh_label = Macros.integer_cache[item.rightheadlabel(st2_index)];
		final Integer st2_ld_label = Macros.integer_cache[item.leftdeplabel(st2_index)];
		final Integer st2_rd_label = Macros.integer_cache[item.rightheadlabel(st2_index)];
		
		final Integer sst_lh_label = Macros.integer_cache[item.leftheadlabel(sst_index)];
		final Integer sst_rh_label = Macros.integer_cache[item.rightheadlabel(sst_index)];
		final Integer sst_ld_label = Macros.integer_cache[item.leftdeplabel(sst_index)];
		final Integer sst_rd_label = Macros.integer_cache[item.rightheadlabel(sst_index)];
		
		final Integer n0_lh_label = Macros.integer_cache[item.leftheadlabel(n0_index)];
		final Integer n0_ld_label = Macros.integer_cache[item.leftdeplabel(n0_index)];
		final Integer n0_l2h_label = Macros.integer_cache[item.leftsubheadlabel(n0_index)];
		final Integer n0_l2d_label = Macros.integer_cache[item.leftsubdeplabel(n0_index)];

		final Integer st_n0_dist0 = Macros.integer_cache[Macros.encodeLinkDistance(st_index, n0_index)];
		final Integer st2_n0_dist1 = Macros.integer_cache[Macros.encodeLinkDistance(st2_index, n0_index)];
		final Integer sst_n0_dist2 = Macros.integer_cache[Macros.encodeLinkDistance(sst_index, n0_index)];

		final SetOfDepLabels st_rtagset = st_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.righttagset(st_index));
		final SetOfDepLabels st_ltagset = st_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.lefttagset(st_index));
		final SetOfDepLabels st2_rtagset = st2_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.righttagset(st2_index));
		final SetOfDepLabels st2_ltagset = st2_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.lefttagset(st2_index));
		final SetOfDepLabels sst_rtagset = sst_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.righttagset(sst_index));
		final SetOfDepLabels sst_ltagset = sst_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.lefttagset(sst_index));
		final SetOfDepLabels n0_ltagset = n0_index == StateItem.out_index ? empty_tagset : new SetOfDepLabels(item.lefttagset(n0_index));

		Weight weight = (Weight)m_weights;

		weight.m_mapSTw.getOrUpdateScore(retval, st_word, action, m_nScoreIndex, amount, round);
		weight.m_mapSTpt.getOrUpdateScore(retval, st_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(st_word, st_postag);
		weight.m_mapSTwpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		
		weight.m_map2STw.getOrUpdateScore(retval, sst_word, action, m_nScoreIndex, amount, round);
		weight.m_map2STpt.getOrUpdateScore(retval, sst_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(sst_word, sst_postag);
		weight.m_map2STwpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		
		word_int.refer(st_word, st_rd_arity);
		weight.m_mapSTwrda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(st_postag, st_rd_arity);
		weight.m_mapSTptrda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		word_int.refer(st_word, st_ld_arity);
		weight.m_mapSTwlda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(st_postag, st_ld_arity);
		weight.m_mapSTptlda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);

		word_int.refer(sst_word, sst_rd_arity);
		weight.m_map2STwrda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(sst_postag, sst_rd_arity);
		weight.m_map2STptrda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		word_int.refer(sst_word, sst_ld_arity);
		weight.m_map2STwlda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(sst_postag, sst_ld_arity);
		weight.m_map2STptlda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		
		word_int.refer(st_word, st_rh_arity);
		weight.m_mapSTwrha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(st_postag, st_rh_arity);
		weight.m_mapSTptrha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		word_int.refer(st_word, st_lh_arity);
		weight.m_mapSTwlha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(st_postag, st_lh_arity);
		weight.m_mapSTptlha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		
		word_int.refer(sst_word, sst_rh_arity);
		weight.m_map2STwrha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(sst_postag, sst_rh_arity);
		weight.m_map2STptrha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
		word_int.refer(sst_word, sst_lh_arity);
		weight.m_map2STwlha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
		postag_int.refer(sst_postag, sst_lh_arity);
		weight.m_map2STptlha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);

		word_tagset.refer(st_word, st_rtagset);
		weight.m_mapSTwrp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
		postag_tagset.refer(st_postag, st_rtagset);
		weight.m_mapSTptrp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);

		word_tagset.refer(st_word, st_ltagset);
		weight.m_mapSTwlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
		postag_tagset.refer(st_postag, st_ltagset);
		weight.m_mapSTptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);

		word_tagset.refer(sst_word, sst_rtagset);
		weight.m_map2STwrp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
		postag_tagset.refer(sst_postag, sst_rtagset);
		weight.m_map2STptrp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);

		word_tagset.refer(sst_word, sst_ltagset);
		weight.m_map2STwlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
		postag_tagset.refer(sst_postag, sst_ltagset);
		weight.m_map2STptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
		
		weight.m_mapN0w.getOrUpdateScore(retval, n0_word, action, m_nScoreIndex, amount, round);
		weight.m_mapN0pt.getOrUpdateScore(retval, n0_postag, action, m_nScoreIndex, amount, round);
		word_postag.refer(n0_word, n0_postag);
		weight.m_mapN0wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
		
		two_postagged_word.refer(st_word_postag, n0_word_postag);
		weight.m_mapSTwptN0wpt.getOrUpdateScore(retval, two_postagged_word, action, m_nScoreIndex, amount, round);
		
		two_postagged_word.refer(sst_word_postag, n0_word_postag);
		weight.m_map2STwptN0wpt.getOrUpdateScore(retval, two_postagged_word, action, m_nScoreIndex, amount, round);
		
		word_word.refer(st_word, n0_word);
		weight.m_mapSTwN0w.getOrUpdateScore(retval, word_word, action, m_nScoreIndex, amount, round);
		set_of_2_postags.load(encodePOSTags(st_postag, n0_postag));
		weight.m_mapSTptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
		
		word_word.refer(sst_word, n0_word);
		weight.m_map2STwN0w.getOrUpdateScore(retval, word_word, action, m_nScoreIndex, amount, round);
		set_of_2_postags.load(encodePOSTags(sst_postag, n0_postag));
		weight.m_map2STptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);

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

		if (st_index == StateItem.out_index || n0_index == StateItem.out_index) {
			
			weight.m_mapPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			weight.m_mapFPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			
		} else {
			
			weight.m_mapPOSPath.getOrUpdateScore(retval, analyzer.POSPath[st_index][n0_index], action, m_nScoreIndex, amount, round);
			weight.m_mapFPOSPath.getOrUpdateScore(retval, analyzer.FPOSPath[st_index][n0_index], action, m_nScoreIndex, amount, round);
			
		}

		if (st2_index == StateItem.out_index || n0_index == StateItem.out_index) {
			
			weight.m_mapSPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			weight.m_mapSFPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			
		} else {
			
			weight.m_mapSPOSPath.getOrUpdateScore(retval, analyzer.POSPath[st2_index][n0_index], action, m_nScoreIndex, amount, round);
			weight.m_mapSFPOSPath.getOrUpdateScore(retval, analyzer.FPOSPath[st2_index][n0_index], action, m_nScoreIndex, amount, round);
			
		}

		if (sst_index == StateItem.out_index || n0_index == StateItem.out_index) {
			
			weight.m_map2POSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			weight.m_map2FPOSPath.getOrUpdateScore(retval, "n#", action, m_nScoreIndex, amount, round);
			
		} else {
			
			weight.m_map2POSPath.getOrUpdateScore(retval, analyzer.POSPath[sst_index][n0_index], action, m_nScoreIndex, amount, round);
			weight.m_map2FPOSPath.getOrUpdateScore(retval, analyzer.FPOSPath[sst_index][n0_index], action, m_nScoreIndex, amount, round);
			
		}
		
		if (st_index != StateItem.out_index) {

			word_word_postag.refer(st_word, n0_word, st_postag);
			weight.m_mapSTwptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(st_word, st_postag, n0_postag);
			weight.m_mapSTwptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

			weight.m_mapST2w.getOrUpdateScore(retval, st2_word, action, m_nScoreIndex, amount, round);
			weight.m_mapST2pt.getOrUpdateScore(retval, st2_postag, action, m_nScoreIndex, amount, round);
			word_postag.refer(st2_word, st2_postag);
			weight.m_mapST2wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);

			weight.m_mapSTLHw.getOrUpdateScore(retval, stlh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLHpt.getOrUpdateScore(retval, stlh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTlhl.getOrUpdateScore(retval, st_lh_label, action, m_nScoreIndex, amount, round);
			
			weight.m_mapSTRHw.getOrUpdateScore(retval, strh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRHpt.getOrUpdateScore(retval, strh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTrhl.getOrUpdateScore(retval, st_rh_label, action, m_nScoreIndex, amount, round);

			weight.m_mapSTLDw.getOrUpdateScore(retval, stld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDpt.getOrUpdateScore(retval, stld_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTldl.getOrUpdateScore(retval, st_ld_label, action, m_nScoreIndex, amount, round);

			weight.m_mapSTRDw.getOrUpdateScore(retval, strd_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRDpt.getOrUpdateScore(retval, strd_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTrdl.getOrUpdateScore(retval, st_rd_label, action, m_nScoreIndex, amount, round);

			word_int.refer(st2_word, st2_rd_arity);
			weight.m_mapST2wrda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st2_postag, st2_rd_arity);
			weight.m_mapST2ptrda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			word_int.refer(st2_word, st2_ld_arity);
			weight.m_mapST2wlda.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st2_postag, st2_ld_arity);
			weight.m_mapST2ptlda.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);

			word_int.refer(st2_word, st2_rh_arity);
			weight.m_mapST2wrha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st2_postag, st2_rh_arity);
			weight.m_mapST2ptrha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);
			word_int.refer(st2_word, st2_lh_arity);
			weight.m_mapST2wlha.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			postag_int.refer(st2_postag, st2_lh_arity);
			weight.m_mapST2ptlha.getOrUpdateScore(retval, postag_int, action, m_nScoreIndex, amount, round);

			word_tagset.refer(st2_word, st2_rtagset);
			weight.m_mapST2wrp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(st2_postag, st2_rtagset);
			weight.m_mapST2ptrp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);
			
			word_tagset.refer(st2_word, st2_ltagset);
			weight.m_mapST2wlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			postag_tagset.refer(st2_postag, st2_ltagset);
			weight.m_mapST2ptlp.getOrUpdateScore(retval, postag_tagset, action, m_nScoreIndex, amount, round);

			word_word_int.refer(st_word, n0_word, st_n0_dist0);
			weight.m_mapSTwN0wd0.getOrUpdateScore(retval, word_word_int, action, m_nScoreIndex, amount, round);
			postag_postag_int.refer(st_postag, n0_postag, st_n0_dist0);
			weight.m_mapSTptN0ptd0.getOrUpdateScore(retval, postag_postag_int, action, m_nScoreIndex, amount, round);
			
			if (stlh_index != StateItem.out_index) {
				
				weight.m_mapSTL2Hw.getOrUpdateScore(retval, stl2h_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTL2Hpt.getOrUpdateScore(retval, stl2h_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTl2hl.getOrUpdateScore(retval, st_l2h_label, action, m_nScoreIndex, amount, round);
				
				weight.m_mapSTLHLHw.getOrUpdateScore(retval, stlhlh_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTLHLHpt.getOrUpdateScore(retval, stlhlh_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTLHlhl.getOrUpdateScore(retval, stlh_lh_label, action, m_nScoreIndex, amount, round);
				
				weight.m_mapSTLHRHw.getOrUpdateScore(retval, stlhrh_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTLHRHpt.getOrUpdateScore(retval, stlhrh_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTLHrhl.getOrUpdateScore(retval, stlh_rh_label, action, m_nScoreIndex, amount, round);
				
				set_of_3_postags.load(encodePOSTags(stlhlh_postag, stlh_postag, st_postag));
				weight.m_mapSTLHLHptSTLHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(stlhrh_postag, stlh_postag, st_postag));
				weight.m_mapSTLHRHptSTLHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				
			}
			
			if (strh_index != StateItem.out_index) {
				
				weight.m_mapSTR2Hw.getOrUpdateScore(retval, str2h_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTR2Hpt.getOrUpdateScore(retval, str2h_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTr2hl.getOrUpdateScore(retval, st_r2h_label, action, m_nScoreIndex, amount, round);
				
				weight.m_mapSTRHLHw.getOrUpdateScore(retval, strhlh_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTRHLHpt.getOrUpdateScore(retval, strhlh_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTRHlhl.getOrUpdateScore(retval, strh_lh_label, action, m_nScoreIndex, amount, round);

				weight.m_mapSTRHRHw.getOrUpdateScore(retval, strhrh_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTRHRHpt.getOrUpdateScore(retval, strhrh_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTRHrhl.getOrUpdateScore(retval, strh_rh_label, action, m_nScoreIndex, amount, round);

				set_of_3_postags.load(encodePOSTags(strhlh_postag, strh_postag, st_postag));
				weight.m_mapSTRHLHptSTRHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(strhrh_postag, strh_postag, st_postag));
				weight.m_mapSTRHRHptSTRHptSTpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				
			}
			
			if (stld_index != StateItem.out_index) {

				weight.m_mapSTL2Dw.getOrUpdateScore(retval, stl2d_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTL2Dpt.getOrUpdateScore(retval, stl2d_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTl2dl.getOrUpdateScore(retval, st_l2d_label, action, m_nScoreIndex, amount, round);
				
				set_of_3_postags.load(encodePOSTags(st_postag, stld_postag, stl2d_postag));
				weight.m_mapSTptSTLDptSTL2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			}
			
			if (strd_index != StateItem.out_index) {

				weight.m_mapSTR2Dw.getOrUpdateScore(retval, str2d_word, action, m_nScoreIndex, amount, round);
				weight.m_mapSTR2Dpt.getOrUpdateScore(retval, str2d_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapSTr2dl.getOrUpdateScore(retval, st_r2d_label, action, m_nScoreIndex, amount, round);
				
				set_of_3_postags.load(encodePOSTags(st_postag, strd_postag, str2d_postag));
				weight.m_mapSTptSTRDptSTR2Dpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				
			}
			
			if (st2_index != StateItem.out_index) {
				
				weight.m_mapST2LHw.getOrUpdateScore(retval, st2lh_word, action, m_nScoreIndex, amount, round);
				weight.m_mapST2LHpt.getOrUpdateScore(retval, st2lh_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapST2lhl.getOrUpdateScore(retval, st2_lh_label, action, m_nScoreIndex, amount, round);
				
				weight.m_mapST2RHw.getOrUpdateScore(retval, st2rh_word, action, m_nScoreIndex, amount, round);
				weight.m_mapST2RHpt.getOrUpdateScore(retval, st2rh_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapST2rhl.getOrUpdateScore(retval, st2_rh_label, action, m_nScoreIndex, amount, round);

				weight.m_mapST2LDw.getOrUpdateScore(retval, st2ld_word, action, m_nScoreIndex, amount, round);
				weight.m_mapST2LDpt.getOrUpdateScore(retval, st2ld_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapST2ldl.getOrUpdateScore(retval, st2_ld_label, action, m_nScoreIndex, amount, round);

				weight.m_mapST2RDw.getOrUpdateScore(retval, st2rd_word, action, m_nScoreIndex, amount, round);
				weight.m_mapST2RDpt.getOrUpdateScore(retval, st2rd_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapST2rdl.getOrUpdateScore(retval, st2_rd_label, action, m_nScoreIndex, amount, round);
				
				two_postagged_word.refer(st2_word_postag, n0_word_postag);
				weight.m_mapST2wptN0wpt.getOrUpdateScore(retval, two_postagged_word, action, m_nScoreIndex, amount, round);
				word_word_postag.refer(st2_word, n0_word, st2_postag);
				weight.m_mapST2wptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
				word_postag_postag.refer(st2_word, st2_postag, n0_postag);
				weight.m_mapST2wptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
				
				word_word.refer(st2_word, n0_word);
				weight.m_mapST2wN0w.getOrUpdateScore(retval, word_word, action, m_nScoreIndex, amount, round);
				set_of_2_postags.load(encodePOSTags(st2_postag, n0_postag));
				weight.m_mapST2ptN0pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
				
			}

		}
		
		if (sst_index != StateItem.out_index) {

			weight.m_map2STLHw.getOrUpdateScore(retval, sstlh_word, action, m_nScoreIndex, amount, round);
			weight.m_map2STLHpt.getOrUpdateScore(retval, sstlh_postag, action, m_nScoreIndex, amount, round);
			weight.m_map2STlhl.getOrUpdateScore(retval, sst_lh_label, action, m_nScoreIndex, amount, round);
			
			weight.m_map2STRHw.getOrUpdateScore(retval, sstrh_word, action, m_nScoreIndex, amount, round);
			weight.m_map2STRHpt.getOrUpdateScore(retval, sstrh_postag, action, m_nScoreIndex, amount, round);
			weight.m_map2STrhl.getOrUpdateScore(retval, sst_rh_label, action, m_nScoreIndex, amount, round);

			weight.m_map2STLDw.getOrUpdateScore(retval, sstld_word, action, m_nScoreIndex, amount, round);
			weight.m_map2STLDpt.getOrUpdateScore(retval, sstld_postag, action, m_nScoreIndex, amount, round);
			weight.m_map2STldl.getOrUpdateScore(retval, sst_ld_label, action, m_nScoreIndex, amount, round);

			weight.m_map2STRDw.getOrUpdateScore(retval, sstrd_word, action, m_nScoreIndex, amount, round);
			weight.m_map2STRDpt.getOrUpdateScore(retval, sstrd_postag, action, m_nScoreIndex, amount, round);
			weight.m_map2STrdl.getOrUpdateScore(retval, sst_rd_label, action, m_nScoreIndex, amount, round);
			
			word_word_postag.refer(sst_word, n0_word, sst_postag);
			weight.m_map2STwptN0w.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(sst_word, sst_postag, n0_postag);
			weight.m_map2STwptN0pt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);
			
			set_of_3_postags.load(encodePOSTags(sst_postag, n_1_postag, n0_postag));
			weight.m_map2STptN_1ptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(sst_postag, n0_postag, n1_postag));
			weight.m_map2STptN0ptN1pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
		
			set_of_3_postags.load(encodePOSTags(sst_postag, n0_postag, n0ld_postag));
			weight.m_map2STptN0ptN0LDpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(sst_postag, n0_postag, n0lh_postag));
			weight.m_map2STptN0ptN0LHpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			set_of_3_postags.load(encodePOSTags(sstlh_postag, sst_postag, n0_postag));
			weight.m_map2STLHpt2STptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(sstrh_postag, sst_postag, n0_postag));
			weight.m_map2STRHpt2STptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(sst_postag, sstld_postag, n0_postag));
			weight.m_map2STpt2STLDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			set_of_3_postags.load(encodePOSTags(sst_postag, sstrd_postag, n0_postag));
			weight.m_map2STpt2STRDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			word_word_int.refer(sst_word, n0_word, sst_n0_dist2);
			weight.m_map2STwN0wd2.getOrUpdateScore(retval, word_word_int, action, m_nScoreIndex, amount, round);
			postag_postag_int.refer(sst_postag, n0_postag, sst_n0_dist2);
			weight.m_map2STptN0ptd2.getOrUpdateScore(retval, postag_postag_int, action, m_nScoreIndex, amount, round);
		
		}
		
		if (n0_index != StateItem.out_index) {

			word_word_postag.refer(st_word, n0_word, n0_postag);
			weight.m_mapSTwN0wpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(n0_word, st_postag, n0_postag);
			weight.m_mapSTptN0wpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

			word_word_postag.refer(st2_word, n0_word, n0_postag);
			weight.m_mapST2wN0wpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(n0_word, st2_postag, n0_postag);
			weight.m_mapST2ptN0wpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

			word_word_postag.refer(sst_word, n0_word, n0_postag);
			weight.m_map2STwN0wpt.getOrUpdateScore(retval, word_word_postag, action, m_nScoreIndex, amount, round);
			word_postag_postag.refer(n0_word, sst_postag, n0_postag);
			weight.m_map2STptN0wpt.getOrUpdateScore(retval, word_postag_postag, action, m_nScoreIndex, amount, round);

			weight.m_mapN0LHw.getOrUpdateScore(retval, n0lh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LHpt.getOrUpdateScore(retval, n0lh_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0lhl.getOrUpdateScore(retval, n0_lh_label, action, m_nScoreIndex, amount, round);

			weight.m_mapN0LDw.getOrUpdateScore(retval, n0ld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDpt.getOrUpdateScore(retval, n0ld_postag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0ldl.getOrUpdateScore(retval, n0_ld_label, action, m_nScoreIndex, amount, round);

			weight.m_mapN_1w.getOrUpdateScore(retval, n_1_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN_1pt.getOrUpdateScore(retval, n_1_postag, action, m_nScoreIndex, amount, round);
			word_postag.refer(n_1_word, n_1_postag);
			weight.m_mapN_1wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
			
			weight.m_mapN1w.getOrUpdateScore(retval, n1_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN1pt.getOrUpdateScore(retval, n1_postag, action, m_nScoreIndex, amount, round);
			word_postag.refer(n1_word, n1_postag);
			weight.m_mapN1wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);

			set_of_2_postags.load(encodePOSTags(n0_postag, n_1_postag));
			weight.m_mapN0ptN_1pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);
			
			set_of_2_postags.load(encodePOSTags(n0_postag, n1_postag));
			weight.m_mapN0ptN1pt.getOrUpdateScore(retval, set_of_2_postags, action, m_nScoreIndex, amount, round);

			set_of_3_postags.load(encodePOSTags(n_1_postag, n0_postag, n1_postag));
			weight.m_mapN_1ptN0ptN1pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

			word_word_int.refer(st2_word, n0_word, st2_n0_dist1);
			weight.m_mapST2wN0wd1.getOrUpdateScore(retval, word_word_int, action, m_nScoreIndex, amount, round);
			postag_postag_int.refer(st2_postag, n0_postag, st2_n0_dist1);
			weight.m_mapST2ptN0ptd1.getOrUpdateScore(retval, postag_postag_int, action, m_nScoreIndex, amount, round);
			
			if (n0lh_index != StateItem.out_index) {

				weight.m_mapN0L2Hw.getOrUpdateScore(retval, n0l2h_word, action, m_nScoreIndex, amount, round);
				weight.m_mapN0L2Hpt.getOrUpdateScore(retval, n0l2h_postag, action, m_nScoreIndex, amount, round);
				weight.m_mapN0l2hl.getOrUpdateScore(retval, n0_l2h_label, action, m_nScoreIndex, amount, round);
				
				set_of_3_postags.load(encodePOSTags(n0_postag, n0lh_postag, n0l2h_postag));
				weight.m_mapN0ptN0LHptN0L2Hpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				
			}
			
			if (n0ld_index != StateItem.out_index) {

				weight.m_mapN0L2Dw.getOrUpdateScore(retval, n0l2d_word, action, m_nScoreIndex, amount, round);
				weight.m_mapN0L2Dpt.getOrUpdateScore(retval, n0l2d_postag, action, m_nScoreIndex, amount, round);
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
			
			if (n_1_index != StateItem.out_index) {
				weight.m_mapN_2w.getOrUpdateScore(retval, n_2_word, action, m_nScoreIndex, amount, round);
				weight.m_mapN_2pt.getOrUpdateScore(retval, n_2_postag, action, m_nScoreIndex, amount, round);
				word_postag.refer(n_2_word, n_2_postag);
				weight.m_mapN_2wpt.getOrUpdateScore(retval, word_postag, action, m_nScoreIndex, amount, round);
				
				set_of_3_postags.load(encodePOSTags(n_2_postag, n_1_postag, n0_postag));
				weight.m_mapN_2ptN_1ptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			}
			
			if (st_index != StateItem.out_index) {

				set_of_3_postags.load(encodePOSTags(st_postag, n_1_postag, n0_postag));
				weight.m_mapSTptN_1ptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(st_postag, n0_postag, n1_postag));
				weight.m_mapSTptN0ptN1pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				
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
			
			if (st2_index != StateItem.out_index) {
				
				set_of_3_postags.load(encodePOSTags(st2_postag, n_1_postag, n0_postag));
				weight.m_mapST2ptN_1ptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(st2_postag, n0_postag, n1_postag));
				weight.m_mapST2ptN0ptN1pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			
				set_of_3_postags.load(encodePOSTags(st2_postag, n0_postag, n0ld_postag));
				weight.m_mapST2ptN0ptN0LDpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(st2_postag, n0_postag, n0lh_postag));
				weight.m_mapST2ptN0ptN0LHpt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);

				set_of_3_postags.load(encodePOSTags(st2lh_postag, st2_postag, n0_postag));
				weight.m_mapST2LHptST2ptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(st2rh_postag, st2_postag, n0_postag));
				weight.m_mapST2RHptST2ptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(st2_postag, st2ld_postag, n0_postag));
				weight.m_mapST2ptST2LDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
				set_of_3_postags.load(encodePOSTags(st2_postag, st2rd_postag, n0_postag));
				weight.m_mapST2ptST2RDptN0pt.getOrUpdateScore(retval, set_of_3_postags, action, m_nScoreIndex, amount, round);
			}
						
		}
		
		if (m_bSupertag) {
			
			int st_1_index = st_index > 0 ? st_index - 1 : StateItem.out_index;
			int st_2_index = st_1_index > 0 ? st_1_index - 1 : StateItem.out_index;
			
			final Integer st_ccgtag = Macros.integer_cache[item.ccg(st_index)];
			final Integer st2_ccgtag = Macros.integer_cache[item.ccg(st2_index)];
			final Integer sst_ccgtag = Macros.integer_cache[item.ccg(sst_index)];
			final Integer n_1_ccgtag = Macros.integer_cache[item.ccg(n_1_index)];
			final Integer n_2_ccgtag = Macros.integer_cache[item.ccg(n_2_index)];
			final Integer st_1_ccgtag = Macros.integer_cache[item.ccg(st_1_index)];
			final Integer st_2_ccgtag = Macros.integer_cache[item.ccg(st_2_index)];
			
			weight.m_mapSTct.getOrUpdateScore(retval, st_ccgtag, action, m_nScoreIndex, amount, round);
			weight.m_map2STct.getOrUpdateScore(retval, sst_ccgtag, action, m_nScoreIndex, amount, round);
			
			word_int.refer(n0_word, st_ccgtag);
			weight.m_mapSTctN0w.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			word_int.refer(n0_word, sst_ccgtag);
			weight.m_map2STctN0w.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			
			if (st_index != StateItem.out_index) {
				
				weight.m_mapST2ct.getOrUpdateScore(retval, st2_ccgtag, action, m_nScoreIndex, amount, round);
				weight.m_mapST_1ct.getOrUpdateScore(retval, st_1_ccgtag, action, m_nScoreIndex, amount, round);
				if (st_1_index != StateItem.out_index) {
					weight.m_mapST_2ct.getOrUpdateScore(retval, st_2_ccgtag, action, m_nScoreIndex, amount, round);
				}
				
				int_int.refer(st_ccgtag, st2_ccgtag);
				weight.m_mapSTctST2ct.getOrUpdateScore(retval, int_int, action, m_nScoreIndex, amount, round);
				
				int_int.refer(st_ccgtag, sst_ccgtag);
				weight.m_mapSTct2STct.getOrUpdateScore(retval, int_int, action, m_nScoreIndex, amount, round);
				
				word_int.refer(n0_word, st2_ccgtag);
				weight.m_mapST2ctN0w.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			}
			
			if (n0_index != StateItem.out_index) {
				
				weight.m_mapN_1ct.getOrUpdateScore(retval, n_1_ccgtag, action, m_nScoreIndex, amount, round);
				
				if (n_1_index != StateItem.out_index) {
					
					weight.m_mapN_2ct.getOrUpdateScore(retval, n_2_ccgtag, action, m_nScoreIndex, amount, round);
					
					int_int.refer(n_1_ccgtag, n_2_ccgtag);
					weight.m_mapN_1ctN_2ct.getOrUpdateScore(retval, int_int, action, m_nScoreIndex, amount, round);
				}
				
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
		for (int label : Macros.SHIFT_LABELLIST) {
			scoredaction.action = Action.encodeAction(Macros.SHIFT, label);
			scoredaction.score = item.score + scores.at(scoredaction.action);
			m_Beam.insertItem(scoredaction);
		}
	}
	
	public void mem(final StateItem item, final PackedScoreType scores) {
		scoredaction.action = Macros.MEM;
		scoredaction.score = item.score + scores.at(scoredaction.action);
		m_Beam.insertItem(scoredaction);
	}
	
	public void recall(final StateItem item, final PackedScoreType scores) {
		scoredaction.action = Macros.RECALL;
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

				if (pGenerator.m_nNextWord < m_lCache.size()) {
					POSTaggedWord pw = m_lCache.get(pGenerator.m_nNextWord);
					Macros.SHIFT_LABELLIST = Macros.MAP.get(pw.word.toString());
					if (Macros.SHIFT_LABELLIST == null) {
						Macros.SHIFT_LABELLIST = Macros.POSMAP.get(pw.tag.toString());
						Macros.SHIFT_ACTIONLIST = Macros.ACTIONPOSMAP.get(pw.tag.toString());
					} else {
						Macros.SHIFT_ACTIONLIST = Macros.ACTIONMAP.get(pw.word.toString());
					}
					
				} else {
					Macros.SHIFT_ACTIONLIST = Macros.CONST_ACTIONLIST;
				}
				
				m_Beam.clear();
				packed_scores.reset();
				getOrUpdateStackScore(pGenerator, packed_scores, Macros.NO_ACTION);
				/*
				 * if can mem, try swap
				 */
				if (pGenerator.canmem()) {
					mem(pGenerator, packed_scores);
				}
				/*
				 * if can recall, try swap
				 */
				if (pGenerator.canrecall()) {
					recall(pGenerator, packed_scores);
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
	
	public int totalerror() {
		return m_nTotalErrors;
	}

}
