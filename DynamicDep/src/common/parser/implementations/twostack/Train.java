package common.parser.implementations.twostack;

import include.SentenceReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import common.parser.implementations.DependencyDag;

public class Train {
	public void auto_train(final String sOutputFile, final String sFeatureFile, final String sSuperFile, final boolean supertag) throws IOException {
		System.out.println("Training iteration is started...");
		System.out.flush();
		DepParser parser = new DepParser(sFeatureFile, true, supertag);
		DependencyDag ref_sent = new DependencyDag();
		SentenceReader super_reader = new SentenceReader(sSuperFile);
		ArrayList<int[]> superset = new ArrayList<int[]>();
		int nCount = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sOutputFile)), "UTF-8"));
		while (ref_sent.readSentenceFromInputStream(br)) {
			++nCount;
			super_reader.readSuperTagSentence(superset);
			parser.train(ref_sent, superset, nCount);
		}
		parser.finishtraning();
		System.out.println("Done.");
	}
	
}
