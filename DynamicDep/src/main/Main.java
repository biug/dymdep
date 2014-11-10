package main;

import java.io.IOException;

import common.parser.implementations.MacrosCCGDag;

/*
 * @author ZhangXun
 */

public class Main {
	
	public static void train(final String sInputFile, final String sFeatureFile, final int nRound, final String system, final boolean usuper, final boolean upath) {
		long start = System.currentTimeMillis();
		if (system.equals("titov")) {
			common.parser.implementations.titov.Train train = new common.parser.implementations.titov.Train();
			final int training_rounds = nRound;
			System.out.println("Training started");
			for (int i = 0; i < training_rounds; ++i) {
				try {
					System.out.println("iteration " + (i + 1));
					train.auto_train(sInputFile, sFeatureFile, usuper, upath);
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
					System.out.println("iteration " + (i + 1));
					train.auto_train(sInputFile, sFeatureFile, usuper, upath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (system.equals("spanning")) {
			common.parser.implementations.spanning.Train train = new common.parser.implementations.spanning.Train();
			final int training_rounds = nRound;
			System.out.println("Training started");
			for (int i = 0; i < training_rounds; ++i) {
				try {
					System.out.println("iteration " + (i + 1));
					train.auto_train(sInputFile, sFeatureFile, usuper, upath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (system.equals("nivre")) {
			common.parser.implementations.nivre.Train train = new common.parser.implementations.nivre.Train();
			final int training_rounds = nRound;
			System.out.println("Training started");
			for (int i = 0; i < training_rounds; ++i) {
				try {
					System.out.println("iteration " + (i + 1));
					train.auto_train(sInputFile, sFeatureFile, usuper, upath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.print("Training has finished successfully. ");
		System.out.println("Total time taken is: " + ((System.currentTimeMillis() - start) / 1000.0));
	}
	
	public static void parse(final String sInputFile, final String sOutputFile, final String sFeatureFile, final String system, final boolean usuper, final boolean upath) {
		if (system.equals("titov")) {
			common.parser.implementations.titov.Parse parse = new common.parser.implementations.titov.Parse();
			parse.process(sInputFile, sOutputFile, sFeatureFile, 1, false, usuper, upath);
		} else if (system.equals("twostack")) {
			common.parser.implementations.twostack.Parse parse = new common.parser.implementations.twostack.Parse();
			parse.process(sInputFile, sOutputFile, sFeatureFile, 1, false, usuper, upath);
		} else if (system.equals("spanning")) {
			common.parser.implementations.spanning.Parse parse = new common.parser.implementations.spanning.Parse();
			parse.process(sInputFile, sOutputFile, sFeatureFile, 1, false, usuper, upath);
		} else if (system.equals("nivre")) {
			common.parser.implementations.nivre.Parse parse = new common.parser.implementations.nivre.Parse();
			parse.process(sInputFile, sOutputFile, sFeatureFile, 1, false, usuper, upath);
		}
	}
	
	public static void main(String[] args) throws IOException {
		boolean upath = args[7].equals("path");
		boolean usuper = args[6].equals("super");
		MacrosCCGDag.loadMacros(args[0], usuper);
		if (args[1].equals("titov")) {
			common.parser.implementations.titov.Macros.calcConstant(usuper);
		} else if (args[1].equals("twostack")) {
			common.parser.implementations.twostack.Macros.calcConstant();
		} else if (args[1].equals("spanning")) {
			common.parser.implementations.spanning.Macros.calcConstant();
		} else if (args[1].equals("nivre")) {
			common.parser.implementations.nivre.Macros.calcConstant();
		}
		if (args[2].equals("train")) {
			train(args[3], args[4], Integer.parseInt(args[5]), args[1], usuper, upath);
		} else if (args[2].equals("parse")) {
			parse(args[3], args[4], args[5], args[1], usuper, upath);
		}
	}
	
}
