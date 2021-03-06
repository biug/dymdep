package common.parser.implementations.spanning;

import include.SentenceReader;
import include.linguistics.IntIntegerVector;
import include.linguistics.TwoStringsVector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import common.parser.implementations.DependencyDag;
import common.parser.implementations.MacrosCCGDag;

public class Parse {
	public void process(final String sInputFile, final String sOutputFile, final String sFeatureFile, final int nBest, final boolean bScores, final boolean supertag, final boolean upath) {
		System.out.println("Parsing started");
		
		long time_start = System.currentTimeMillis();
		
		DepParser parser = new DepParser(sFeatureFile, false, supertag, upath);
		SentenceReader input_reader = new SentenceReader(sInputFile);
		int count = 0;
		try {
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sOutputFile)), "UTF-8"));
			BufferedWriter os_scores = null;
			long[] scores = null;
			TwoStringsVector input_sent = new TwoStringsVector();
			IntIntegerVector input_tree = new IntIntegerVector();
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
				if (input_sent.size() > MacrosCCGDag.MAX_SENTENCE_SIZE) {
					for (int index = 0; index < nBest; ++index) {
						output_sent[index].length = 0;
					}
				} else {
					parser.parse(++count, input_sent, input_tree, output_sent, nBest, scores);
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
