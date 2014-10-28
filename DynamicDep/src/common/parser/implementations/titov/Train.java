package common.parser.implementations.titov;

import include.SentenceReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import common.parser.implementations.DependencyDag;

public final class Train {
	public void auto_train(final String sOutputFile, final String sFeatureFile, final String sSuperFile, final boolean supertag) throws IOException {
		System.out.println("Training iteration is started...");
		System.out.flush();
		DepParser parser = new DepParser(sFeatureFile, true, supertag);
		DependencyDag ref_sent = new DependencyDag();
		ArrayList<int[]> superset = new ArrayList<int[]>();
		SentenceReader super_reader = new SentenceReader(sSuperFile);
		int nCount = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sOutputFile)), "UTF-8"));
		while (ref_sent.readSentenceFromInputStream(br)) {
			super_reader.readSuperTagSentence(superset);
			++nCount;
			if (nCount % 1000 == 0) {
				System.out.println(parser.totalerror() + "/" + nCount);
			}
			if (!supertag) {
				ref_sent.clearSuperTag();
			}
			parser.train(ref_sent, superset, nCount);
		}
		parser.finishtraning();
		System.out.println("Done.");
	}
	
}
