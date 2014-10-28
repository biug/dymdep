package main;

import java.io.IOException;

import common.parser.implementations.MacrosCCGDag;

/*
 * @author ZhangXun
 */

public class Main {
	
	public static void train(final String sInputFile, final String sFeatureFile, final String sSuperFile, final int nRound, final String system) {
		long start = System.currentTimeMillis();
		if (system.equals("titov")) {
			common.parser.implementations.titov.Train train = new common.parser.implementations.titov.Train();
			final int training_rounds = nRound;
			System.out.println("Training started");
			for (int i = 0; i < training_rounds; ++i) {
				try {
					train.auto_train(sInputFile, sFeatureFile, sSuperFile, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (system.equals("twostack")) {
			common.parser.implementations.twostack.Train train = new common.parser.implementations.twostack.Train();
			final int training_rounds = nRound;
			System.out.println("Training started");
			for (int i = 0; i < training_rounds; ++i) {
				try {
					train.auto_train(sInputFile, sFeatureFile, sSuperFile, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.print("Training has finished successfully. ");
		System.out.println("Total time taken is: " + ((System.currentTimeMillis() - start) / 1000.0));
	}
	
	public static void parse(final String sInputFile, final String sOutputFile, final String sFeatureFile, final String sSuperFile, final String system) {
		if (system.equals("titov")) {
			common.parser.implementations.titov.Parse parse = new common.parser.implementations.titov.Parse();
			parse.process(sInputFile, sOutputFile, sFeatureFile, sSuperFile, 1, false, true);
		} else if (system.equals("twostack")) {
			common.parser.implementations.twostack.Parse parse = new common.parser.implementations.twostack.Parse();
			parse.process(sInputFile, sOutputFile, sFeatureFile, sSuperFile, 1, false, true);
		}
	}
	
	public static void main(String[] args) throws IOException {
		MacrosCCGDag.loadMacros(args[0]);
		if (args[1].equals("titov")) {
			common.parser.implementations.titov.Macros.calcConstant();
		} else if (args[1].equals("twostack")) {
			common.parser.implementations.twostack.Macros.calcConstant();
		}
		if (args[2].equals("train")) {
			train(args[3], args[4], args[5], Integer.parseInt(args[6]), args[1]);
		} else if (args[2].equals("parse")) {
			parse(args[3], args[4], args[5], args[6], args[1]);
		}
	}
	
}
