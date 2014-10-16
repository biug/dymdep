package main;

import java.io.IOException;

import common.parser.implementations.MacrosCCGDag;
import common.parser.implementations.titov.Macros;
import common.parser.implementations.titov.Parse;
import common.parser.implementations.titov.Train;

/*
 * @author ZhangXun
 */

public class Main {
	
	public static void train(final String sInputFile, final String sFeatureFile, final int nRound) {
		long start = System.currentTimeMillis();
		Train train = new Train();
		final int training_rounds = nRound;
		System.out.println("Training started");
		for (int i = 0; i < training_rounds; ++i) {
			try {
				train.auto_train(sInputFile, sFeatureFile, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.print("Training has finished successfully. ");
		System.out.println("Total time taken is: " + ((System.currentTimeMillis() - start) / 1000.0));
	}
	
	public static void parse(final String sInputFile, final String sOutputFile, final String sFeatureFile) {
		Parse parse = new Parse();
		parse.process(sInputFile, sOutputFile, sFeatureFile, 1, false, false);
	}
	
	public static void main(String[] args) throws IOException {
		MacrosCCGDag.loadMacros(args[0]);
		Macros.calcConstant();
		if (args[1].equals("train")) {
			train(args[2], args[3], Integer.parseInt(args[4]));
		} else if (args[1].equals("parse")) {
			parse(args[2], args[3], args[4]);
		}
	}
	
}
