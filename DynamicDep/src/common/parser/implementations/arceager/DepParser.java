package common.parser.implementations.arceager;

import include.AgendaBeam;
import include.AgendaSimple;
import include.learning.perceptron.PackedScoreType;
import include.learning.perceptron.Score;
import include.linguistics.SetOfLabels;
import include.linguistics.TagInt;
import include.linguistics.TagSet2;
import include.linguistics.TagSet3;
import include.linguistics.TagSetOfLabels;
import include.linguistics.TagTagInt;
import include.linguistics.TaggedWord;
import include.linguistics.TwoStrings;
import include.linguistics.TwoStringsVector;
import include.linguistics.TwoTaggedWords;
import include.linguistics.TwoWords;
import include.linguistics.Word;
import include.linguistics.WordInt;
import include.linguistics.WordSetOfLabels;
import include.linguistics.WordTagTag;
import include.linguistics.WordWordInt;
import include.linguistics.WordWordTag;

import java.util.ArrayList;

import common.dependency.label.DependencyLabel;
import common.parser.DepParserBase;
import common.parser.ScoredAction;
import common.parser.StateItemBase;
import common.parser.implementations.DependencyTree;
import common.parser.implementations.DependencyTreeNode;
import common.parser.implementations.MacrosTree;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class DepParser extends DepParserBase {
	
	private AgendaBeam m_Agenda;
	private AgendaSimple m_Beam;
	
	private ArrayList<TaggedWord> m_lCache;
	private ArrayList<DependencyLabel> m_lCacheLabel;
	
	private int m_nTrainingRound;
	private int m_nTotalErrors;
	private int m_nScoreIndex;
	
	private StateItem itemForState;
	private StateItem itemForStates;
	
	private StateItem pCandidate;
	private StateItem correctState;
	private PackedScoreType packed_scores;
	
	private TwoStringsVector trainSentence;
	
	private TwoTaggedWords st_word_tag_n0_word_tag;
	private TwoWords st_word_n0_word;
	
	private WordInt word_int;
	private TagInt tag_int;
	private WordTagTag word_tag_tag;
	private WordWordTag word_word_tag;
	private WordWordInt word_word_int;
	private TagTagInt tag_tag_int;
	private WordSetOfLabels word_tagset;
	private TagSetOfLabels tag_tagset;
	private TagSet2 set_of_2_tags;
	private TagSet3 set_of_3_tags;
	
	private ScoredAction scoredaction;
	
	public static final TaggedWord empty_taggedword = new TaggedWord();
	public static final SetOfLabels empty_setoftags = new SetOfLabels();
	
	private int encodeTags(final POSTag tag1, final POSTag tag2) {
		return ((tag1.hashCode() << (MacrosTree.POSTAG_BITS_SIZE)) | (tag2.hashCode()));
	}
	
	private int encodeTags(final POSTag tag1, final POSTag tag2, final POSTag tag3) {
		return ((tag1.hashCode() << (MacrosTree.POSTAG_BITS_SIZE << 1)) | (tag2.hashCode() << (MacrosTree.POSTAG_BITS_SIZE)) | (tag3.hashCode()));
	}
	
	private int minVal(final int n1, final int n2) {
		return n1 < n2 ? n1 : n2; 
	}

	public DepParser(final String sFeatureDBPath, final boolean bTrain) {
		super(sFeatureDBPath, bTrain);
		
		m_Agenda = new AgendaBeam(MacrosTree.AGENDA_SIZE, new StateItem(null));
		m_Beam = new AgendaSimple(MacrosTree.AGENDA_SIZE);
		
		m_lCache = new ArrayList<TaggedWord>();
		m_lCacheLabel = new ArrayList<DependencyLabel>();
		
		m_weights = new Weight(sFeatureDBPath, bTrain);
		m_nTrainingRound = 0;
		m_nTotalErrors = 0;
		m_nScoreIndex = bTrain ? Score.eNonAverage : Score.eAverage;
		
		itemForState = new StateItem(null);
		itemForStates = new StateItem(m_lCache);
		pCandidate = new StateItem(m_lCache);
		correctState = new StateItem(m_lCache);
		
		packed_scores = new PackedScoreType(MacrosTree.ACTION_MAX);
		
		trainSentence = new TwoStringsVector();
		
		st_word_tag_n0_word_tag = new TwoTaggedWords();
		st_word_n0_word = new TwoWords();
		
		word_int = new WordInt();
		tag_int = new TagInt();
		word_tag_tag = new WordTagTag();
		word_word_tag = new WordWordTag();
		word_word_int = new WordWordInt();
		tag_tag_int = new TagTagInt();
		word_tagset = new WordSetOfLabels();
		tag_tagset = new TagSetOfLabels();
		set_of_2_tags = new TagSet2();
		set_of_3_tags = new TagSet3();
		
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
		
		final TaggedWord st_word_tag = st_index == -1 ? empty_taggedword : m_lCache.get(st_index);
		final TaggedWord sth_word_tag = sth_index == -1 ? empty_taggedword : m_lCache.get(sth_index);
		final TaggedWord sthh_word_tag = sthh_index == -1 ? empty_taggedword : m_lCache.get(sthh_index);
		final TaggedWord stld_word_tag = stld_index == -1 ? empty_taggedword : m_lCache.get(stld_index);
		final TaggedWord strd_word_tag = strd_index == -1 ? empty_taggedword : m_lCache.get(strd_index);
		final TaggedWord stl2d_word_tag = stl2d_index == -1 ? empty_taggedword : m_lCache.get(stl2d_index);
		final TaggedWord str2d_word_tag = str2d_index == -1 ? empty_taggedword : m_lCache.get(str2d_index);
		final TaggedWord n0_word_tag = n0_index == -1 ? empty_taggedword : m_lCache.get(n0_index);
		final TaggedWord n0ld_word_tag = n0ld_index == -1 ? empty_taggedword : m_lCache.get(n0ld_index);
		final TaggedWord n0l2d_word_tag = n0l2d_index == -1 ? empty_taggedword : m_lCache.get(n0l2d_index);
		final TaggedWord n1_word_tag = n1_index == -1 ? empty_taggedword : m_lCache.get(n1_index);
		final TaggedWord n2_word_tag = n2_index == -1 ? empty_taggedword : m_lCache.get(n2_index);
		
		final Word st_word = st_word_tag.word;
		final Word sth_word = sth_word_tag.word;
		final Word sthh_word = sthh_word_tag.word;
		final Word stld_word = stld_word_tag.word;
		final Word strd_word = strd_word_tag.word;
		final Word stl2d_word = stl2d_word_tag.word;
		final Word str2d_word = str2d_word_tag.word;
		final Word n0_word = n0_word_tag.word;
		final Word n0ld_word = n0ld_word_tag.word;
		final Word n0l2d_word = n0l2d_word_tag.word;
		final Word n1_word = n1_word_tag.word;
		final Word n2_word = n2_word_tag.word;
		
		final POSTag st_tag = st_word_tag.tag;
		final POSTag sth_tag = sth_word_tag.tag;
		final POSTag sthh_tag = sthh_word_tag.tag;
		final POSTag stld_tag = stld_word_tag.tag;
		final POSTag strd_tag = strd_word_tag.tag;
		final POSTag stl2d_tag = stl2d_word_tag.tag;
		final POSTag str2d_tag = str2d_word_tag.tag;
		final POSTag n0_tag = n0_word_tag.tag;
		final POSTag n0ld_tag = n0ld_word_tag.tag;
		final POSTag n0l2d_tag = n0l2d_word_tag.tag;
		final POSTag n1_tag = n1_word_tag.tag;
		final POSTag n2_tag = n2_word_tag.tag;
		
		final int st_label = st_index == -1 ? MacrosTree.DEP_NONE : item.label(st_index);
		final int sth_label = sth_index == -1 ? MacrosTree.DEP_NONE : item.label(sth_index);
		final int stld_label = stld_index == -1 ? MacrosTree.DEP_NONE : item.label(stld_index);
		final int strd_label = strd_index == -1 ? MacrosTree.DEP_NONE : item.label(strd_index);
		final int stl2d_label = stl2d_index == -1 ? MacrosTree.DEP_NONE : item.label(stl2d_index);
		final int str2d_label = str2d_index == -1 ? MacrosTree.DEP_NONE : item.label(strd_index); //PROBLEM!
		final int n0ld_label = n0ld_index == -1 ? MacrosTree.DEP_NONE : item.label(n0ld_index);
		final int n0l2d_label = n0l2d_index == -1 ? MacrosTree.DEP_NONE : item.label(n0l2d_index);
		
		final int st_n0_dist = MacrosTree.encodeLinkDistance(st_index, n0_index);
		
		final int st_rarity = st_index == -1 ? 0 : item.rightarity(st_index);
		final int st_larity = st_index == -1 ? 0 : item.leftarity(st_index);
		final int n0_larity = n0_index == -1 ? 0 : item.leftarity(n0_index);
		
		final SetOfLabels st_rtagset = st_index == -1 ? empty_setoftags : new SetOfLabels(item.righttagset(st_index));
		final SetOfLabels st_ltagset = st_index == -1 ? empty_setoftags : new SetOfLabels(item.lefttagset(st_index));
		final SetOfLabels n0_ltagset = n0_index == -1 ? empty_setoftags : new SetOfLabels(item.lefttagset(n0_index));
		
		Weight weight = (Weight)m_weights;
		
		if (st_index != -1) {
			weight.m_mapSTw.getOrUpdateScore(retval, st_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTt.getOrUpdateScore(retval, st_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTwt.getOrUpdateScore(retval, st_word_tag, action, m_nScoreIndex, amount, round);
		}

		if (n0_index != -1) {
			weight.m_mapN0w.getOrUpdateScore(retval, n0_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0t.getOrUpdateScore(retval, n0_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0wt.getOrUpdateScore(retval, n0_word_tag, action, m_nScoreIndex, amount, round);
		}

		if (n1_index != -1) {
			weight.m_mapN1w.getOrUpdateScore(retval, n1_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN1t.getOrUpdateScore(retval, n1_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapN1wt.getOrUpdateScore(retval, n1_word_tag, action, m_nScoreIndex, amount, round);
		}

		if (n2_index != -1) {
			weight.m_mapN2w.getOrUpdateScore(retval, n2_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN2t.getOrUpdateScore(retval, n2_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapN2wt.getOrUpdateScore(retval, n2_word_tag, action, m_nScoreIndex, amount, round);
		}

		if (sth_index != -1) {
			weight.m_mapSTHw.getOrUpdateScore(retval, sth_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTHt.getOrUpdateScore(retval, sth_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTi.getOrUpdateScore(retval, Integer.valueOf(st_label), action, m_nScoreIndex, amount, round);
		}

		if (sthh_index != -1) {
			weight.m_mapSTHHw.getOrUpdateScore(retval, sthh_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTHHt.getOrUpdateScore(retval, sthh_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTHi.getOrUpdateScore(retval, Integer.valueOf(sth_label), action, m_nScoreIndex, amount, round);
		}

		if (stld_index != -1) {
			weight.m_mapSTLDw.getOrUpdateScore(retval, stld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDt.getOrUpdateScore(retval, stld_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTLDi.getOrUpdateScore(retval, Integer.valueOf(stld_label), action, m_nScoreIndex, amount, round);
		}

		if (strd_index != -1) {
			weight.m_mapSTRDw.getOrUpdateScore(retval, strd_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRDt.getOrUpdateScore(retval, strd_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTRDi.getOrUpdateScore(retval, Integer.valueOf(strd_label), action, m_nScoreIndex, amount, round);
		}

		if (n0ld_index != -1) {
			weight.m_mapN0LDw.getOrUpdateScore(retval, n0ld_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDt.getOrUpdateScore(retval, n0ld_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0LDi.getOrUpdateScore(retval, Integer.valueOf(n0ld_label), action, m_nScoreIndex, amount, round);
		}

		if (stl2d_index != -1) {
			weight.m_mapSTL2Dw.getOrUpdateScore(retval, stl2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Dt.getOrUpdateScore(retval, stl2d_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTL2Di.getOrUpdateScore(retval, Integer.valueOf(stl2d_label), action, m_nScoreIndex, amount, round);
		}

		if (str2d_index != -1) {
			weight.m_mapSTR2Dw.getOrUpdateScore(retval, str2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Dt.getOrUpdateScore(retval, str2d_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapSTR2Di.getOrUpdateScore(retval, Integer.valueOf(str2d_label), action, m_nScoreIndex, amount, round);
		}

		if (n0l2d_index != -1) {
			weight.m_mapN0L2Dw.getOrUpdateScore(retval, n0l2d_word, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Dt.getOrUpdateScore(retval, n0l2d_tag, action, m_nScoreIndex, amount, round);
			weight.m_mapN0L2Di.getOrUpdateScore(retval, Integer.valueOf(n0l2d_label), action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1) {
			st_word_tag_n0_word_tag.refer(st_word_tag, n0_word_tag);
			weight.m_mapSTwtN0wt.getOrUpdateScore(retval, st_word_tag_n0_word_tag, action, m_nScoreIndex, amount, round);
			word_word_tag.refer(st_word, n0_word, st_tag);
			weight.m_mapSTwtN0w.getOrUpdateScore(retval, word_word_tag, action, m_nScoreIndex, amount, round);
			word_word_tag.refer(st_word, n0_word, n0_tag);
			weight.m_mapSTwN0wt.getOrUpdateScore(retval, word_word_tag, action, m_nScoreIndex, amount, round);
			word_tag_tag.refer(st_word, st_tag, n0_tag);
			weight.m_mapSTwtN0t.getOrUpdateScore(retval, word_tag_tag, action, m_nScoreIndex, amount, round);
			word_tag_tag.refer(n0_word, st_tag, n0_tag);
			weight.m_mapSTtN0wt.getOrUpdateScore(retval, word_tag_tag, action, m_nScoreIndex, amount, round);
			st_word_n0_word.refer(st_word, n0_word);
			weight.m_mapSTwN0w.getOrUpdateScore(retval, st_word_n0_word, action, m_nScoreIndex, amount, round);
			set_of_2_tags.load(encodeTags(st_tag, n0_tag));
			weight.m_mapSTtN0t.getOrUpdateScore(retval, set_of_2_tags, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1 && n0_index != -1) {
			set_of_2_tags.load(encodeTags(n0_tag, n1_tag));
			weight.m_mapN0tN1t.getOrUpdateScore(retval, set_of_2_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(n0_tag, n1_tag, n2_tag));
			weight.m_mapN0tN1tN2t.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(st_tag, n0_tag, n1_tag));
			weight.m_mapSTtN0tN1t.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(st_tag, n0_tag, n0ld_tag));
			weight.m_mapSTtN0tN0LDt.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(n0_tag, n0ld_tag, n0l2d_tag));
			weight.m_mapN0tN0LDtN0L2Dt.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
		}
		
		if (st_index != -1) {
			set_of_3_tags.load(encodeTags(sth_tag, st_tag, n0_tag));
			weight.m_mapSTHtSTtN0t.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(sthh_tag, sth_tag, st_tag));
			weight.m_mapSTHHtSTHtSTt.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(st_tag, stld_tag, n0_tag));
			weight.m_mapSTtSTLDtN0t.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(st_tag, stld_tag, stl2d_tag));
			weight.m_mapSTtSTLDtSTL2Dt.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(st_tag, strd_tag, n0_tag));
			weight.m_mapSTtSTRDtN0t.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
			set_of_3_tags.load(encodeTags(st_tag, strd_tag, str2d_tag));
			weight.m_mapSTtSTRDtSTR2Dt.getOrUpdateScore(retval, set_of_3_tags, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1 && n0_index != -1) {
			word_int.refer(st_word, st_n0_dist);
			weight.m_mapSTwd.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			tag_int.refer(st_tag, st_n0_dist);
			weight.m_mapSTtd.getOrUpdateScore(retval, tag_int, action, m_nScoreIndex, amount, round);
			word_int.refer(n0_word, st_n0_dist);
			weight.m_mapN0wd.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			tag_int.refer(n0_tag, st_n0_dist);
			weight.m_mapN0td.getOrUpdateScore(retval, tag_int, action, m_nScoreIndex, amount, round);
			word_word_int.refer(st_word, n0_word, st_n0_dist);
			weight.m_mapSTwN0wd.getOrUpdateScore(retval, word_word_int, action, m_nScoreIndex, amount, round);
			tag_tag_int.refer(st_tag, n0_tag, st_n0_dist);
			weight.m_mapSTtN0td.getOrUpdateScore(retval, tag_tag_int, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1) {
			word_int.refer(st_word, st_rarity);
			weight.m_mapSTwra.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			tag_int.refer(st_tag, st_rarity);
			weight.m_mapSTtra.getOrUpdateScore(retval, tag_int, action, m_nScoreIndex, amount, round);
			word_int.refer(st_word, st_larity);
			weight.m_mapSTwla.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			tag_int.refer(st_tag, st_larity);
			weight.m_mapSTtla.getOrUpdateScore(retval, tag_int, action, m_nScoreIndex, amount, round);
		}

		if (n0_index != -1) {
			word_int.refer(n0_word, n0_larity);
			weight.m_mapN0wla.getOrUpdateScore(retval, word_int, action, m_nScoreIndex, amount, round);
			tag_int.refer(n0_tag, n0_larity);
			weight.m_mapN0tla.getOrUpdateScore(retval, tag_int, action, m_nScoreIndex, amount, round);
		}

		if (st_index != -1){
			word_tagset.refer(st_word, st_rtagset);
			weight.m_mapSTwrp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			tag_tagset.refer(st_tag, st_rtagset);
			weight.m_mapSTtrp.getOrUpdateScore(retval, tag_tagset, action, m_nScoreIndex, amount, round);
			word_tagset.refer(st_word, st_ltagset);
			weight.m_mapSTwlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			tag_tagset.refer(st_tag, st_ltagset);
			weight.m_mapSTtlp.getOrUpdateScore(retval, tag_tagset, action, m_nScoreIndex, amount, round);
		}

		if (n0_index != -1){
			word_tagset.refer(n0_word, n0_ltagset);
			weight.m_mapN0wlp.getOrUpdateScore(retval, word_tagset, action, m_nScoreIndex, amount, round);
			tag_tagset.refer(n0_tag, n0_ltagset);
			weight.m_mapN0tlp.getOrUpdateScore(retval, tag_tagset, action, m_nScoreIndex, amount, round);
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
			itemForState.Move(action);
		}
	}
	
	public void updateScoreForStates(final StateItemBase output, final StateItemBase correct, final int amount_add, final int amount_subtract, final int len) {
		itemForStates.clear();
		while (!itemForStates.equals(output)) {
			int action = itemForStates.FollowMove(output);
			int correct_action = itemForStates.FollowMove(correct);
			if (action == correct_action) {
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
		scoredaction.action = MacrosTree.REDUCE;
		scoredaction.score = item.score + scores.at(scoredaction.action);
		m_Beam.insertItem(scoredaction);
	}
	
	public void arcleft(final StateItem item, final PackedScoreType scores) {
		for (int label = MacrosTree.DEP_FIRST; label < MacrosTree.DEP_COUNT; ++label) {
			scoredaction.action = Action.encodeAction(MacrosTree.ARC_LEFT, label);
			scoredaction.score = item.score + scores.at(scoredaction.action);
			m_Beam.insertItem(scoredaction);
		}
	}
	
	public void arcright(final StateItem item, final PackedScoreType scores) {
		for (int label = MacrosTree.DEP_FIRST; label < MacrosTree.DEP_COUNT; ++label) {
			scoredaction.action = Action.encodeAction(MacrosTree.ARC_RIGHT, label);
			scoredaction.score = item.score + scores.at(scoredaction.action);
			m_Beam.insertItem(scoredaction);
		}
	}
	
	public void shift(final StateItem item, final PackedScoreType scores) {
		scoredaction.action = MacrosTree.SHIFT;
		scoredaction.score = item.score + scores.at(scoredaction.action);
		m_Beam.insertItem(scoredaction);
	}
	
	public void poproot(final StateItem item, final PackedScoreType scores) {
		scoredaction.action = MacrosTree.POP_ROOT;
		scoredaction.score = item.score + scores.at(scoredaction.action);
		m_Beam.insertItem(scoredaction);
	}
	
	public void work(final int round, final boolean bTrain, final TwoStringsVector sentence, DependencyTree[] retval, final DependencyTree correct, final int nBest, long[] scores) {
		final int length = sentence.size();
		StateItem pGenerator;
		
		boolean bCorrect = false;
		
		m_lCache.clear();
		for (int index = 0; index < length; ++index) {
			m_lCache.add(new TaggedWord(sentence.get(index).m_string1, sentence.get(index).m_string2));
		}
		
		m_Agenda.clear();
		pCandidate.clear();
		m_Agenda.pushCandidate(pCandidate);
		m_Agenda.nextRound();
		if (bTrain) correctState.clear();
		
		m_lCacheLabel.clear();
		if (bTrain) {
			for (int index = 0; index < length; ++index) {
				m_lCacheLabel.add(new DependencyLabel(((DependencyTreeNode)correct.nodes[index]).label));
			}
		}
		for (int index = 0; index < (length << 1); ++index) {
			
			if (bTrain) bCorrect = false;
			
			pGenerator = (StateItem)m_Agenda.generatorStart();
			
			for (int j = 0, agenda_size = m_Agenda.generatorSize(); j < agenda_size; ++j) {
				m_Beam.clear();
				packed_scores.reset();
				getOrUpdateStackScore(pGenerator, packed_scores, MacrosTree.NO_ACTION);
				if (pGenerator.size() == length) {
					if (pGenerator.stacksize() > 1) {
						reduce(pGenerator, packed_scores);
					} else {
						poproot(pGenerator, packed_scores);
					}
				} else {
					if (!pGenerator.afterreduce()) {
						if ((pGenerator.size() < length - 1 || pGenerator.stackempty())) {
							shift(pGenerator, packed_scores);
						}
					}
					if (!pGenerator.stackempty()) {
						if ((pGenerator.size() < length - 1 || pGenerator.headstacksize() == 1)) {
							arcright(pGenerator, packed_scores);
						}
					}
					if ((!pGenerator.stackempty())) {
						if (pGenerator.head(pGenerator.stacktop()) != DependencyTreeNode.DEPENDENCY_LINK_NO_HEAD) {
							reduce(pGenerator, packed_scores);
						} else {
							arcleft(pGenerator, packed_scores);
						}
					}
				}
				
				for (int i = 0, beam_size = m_Beam.size(); i < beam_size; ++i) {
					pCandidate.copy(pGenerator);
					pCandidate.score = m_Beam.item(i).score;
					pCandidate.Move(m_Beam.item(i).action);
					m_Agenda.pushCandidate(pCandidate);
				}
				if (bTrain && pGenerator.equals(correctState)) {
					bCorrect = true;
				}
				
				pGenerator = (StateItem)m_Agenda.generatorNext();
			}
			if (bTrain) {
				if (!bCorrect) {
					updateScoreForStates(m_Agenda.bestGenerator(), correctState, 1, -1, length);
					return;
				}
				correctState.StandardMoveStep(correct, m_lCacheLabel);
			}
			m_Agenda.nextRound();
		}
		if (bTrain) {
			correctState.StandardFinish();
			if (!m_Agenda.bestGenerator().equals(correctState)) {
				updateScoreForStates(m_Agenda.bestGenerator(), correctState, 1, -1, length);
				return;
			}
		}
		m_Agenda.sortGenerators();
		for (int i = 0, retval_size = minVal(m_Agenda.generatorSize(), nBest); i < retval_size; ++i) {
			pGenerator = (StateItem)m_Agenda.generator(i);
			if (pGenerator != null) {
				if (retval != null) pGenerator.GenerateTree(sentence, retval[i]);
				if (scores != null) scores[i] = pGenerator.score;
			}
		}
	}

	public void parse(final TwoStringsVector sentence, DependencyTree[] retval,
			final int nBest, long[] scores) {
		for (int i = 0; i < nBest; ++i) {
			retval[i].length = 0;
			if (scores != null) scores[i] = 0;
		}
		work(0, false, sentence, retval, null, nBest, scores);
	}

	public void train(final DependencyTree correct, final int round) {
		trainSentence.clear();
		if (correct != null) {
			for (int i = 0; i < correct.length; ++i) {
				DependencyTreeNode node = (DependencyTreeNode)correct.nodes[i];
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