package common.parser.implementations.arceager;

import include.SentenceReader;
import include.linguistics.TwoStringsVector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import common.parser.implementations.DependencyTree;
import common.parser.implementations.MacrosTree;

/*
 * @author ZhangXun
 */

public class Parse {
	
	public void process(final String sInputFile, final String sOutputFile, final String sFeatureFile, final int nBest, final boolean bScores) {
		System.out.println("Parsing started");
		
		long time_start = System.currentTimeMillis();
		
		DepParser parser = new DepParser(sFeatureFile, false);
		SentenceReader input_reader = new SentenceReader(sInputFile);
		try {
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sOutputFile)), "UTF-8"));
			BufferedWriter os_scores = null;
			long[] scores = null;
			TwoStringsVector input_sent = new TwoStringsVector();
			DependencyTree[] output_sent = null;
			
			if (bScores) {
				scores = new long[nBest];
				os_scores = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sOutputFile + ".scores")), "UTF-8"));
			}
			
			output_sent = new DependencyTree[nBest];
			for (int index = 0; index < nBest; ++index) {
				output_sent[index] = new DependencyTree();
			}
			
			while (input_reader.readTaggedSentence(input_sent)) {
				if (input_sent.size() > MacrosTree.MAX_SENTENCE_SIZE) {
					for (int index = 0; index < nBest; ++index) {
						output_sent[index].length = 0;
						if (bScores) {
							scores[index] = 0;
						}
					}
				} else {
					parser.parse(input_sent, output_sent, nBest, scores);
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
