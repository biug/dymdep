package common.parser.implementations.titov;

import include.SentenceReader;
import include.linguistics.IntIntegerVector;
import include.linguistics.TwoStringsVector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import common.parser.implementations.DependencyDag;
import common.parser.implementations.MacrosCCGDag;

public class Parse {
	public void process(final String sInputFile, final String sOutputFile, final String sFeatureFile, final String sSuperFile, final int nBest, final boolean bScores, final boolean supertag) {
		System.out.println("Parsing started");
		
		long time_start = System.currentTimeMillis();
		
		DepParser parser = new DepParser(sFeatureFile, false, supertag);
		SentenceReader input_reader = new SentenceReader(sInputFile);
		SentenceReader super_reader = new SentenceReader(sSuperFile);
		int count = 0;
		try {
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sOutputFile)), "UTF-8"));
			BufferedWriter os_scores = null;
			long[] scores = null;
			TwoStringsVector input_sent = new TwoStringsVector();
			IntIntegerVector input_tree = new IntIntegerVector();
			ArrayList<int[]> superset = new ArrayList<int[]>();
			DependencyDag[] output_sent = null;
			
			if (bScores) {
				scores = new long[nBest];
				os_scores = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sOutputFile + ".scores")), "UTF-8"));
			}
			
			output_sent = new DependencyDag[nBest];
			for (int index = 0; index < nBest; ++index) {
				output_sent[index] = new DependencyDag();
			}
			
			while (input_reader.readCONLL08Sentence(input_sent, input_tree)) {
				super_reader.readSuperTagSentence(superset);
				if (input_sent.size() > MacrosCCGDag.MAX_SENTENCE_SIZE) {
					for (int index = 0; index < nBest; ++index) {
						output_sent[index].length = 0;
					}
				} else {
					parser.parse(++count, input_sent, input_tree, superset, output_sent, nBest, scores);
				}
				for (int index = 0; index < nBest; ++index) {
					output_sent[index].writeSentenceToOutputStream(os);
					if (bScores) {
						os_scores.write(String.valueOf(scores[index]));
						os_scores.newLine();
					}
				}
			}
			os.close();
			if (bScores) {
				os_scores.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Parsing has finished successfully. Total time taken is: " + ((System.currentTimeMillis() - time_start) / 1000.0));
	}
}
