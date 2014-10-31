package common.parser.implementations.spanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import common.parser.implementations.DependencyDag;

public final class Train {
	public void auto_train(final String sOutputFile, final String sFeatureFile, final boolean supertag, final boolean upath) throws IOException {
		System.out.println("Training iteration is started...");
		System.out.flush();
		DepParser parser = new DepParser(sFeatureFile, true, supertag, upath);
		DependencyDag ref_sent = new DependencyDag();
		int nCount = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sOutputFile)), "UTF-8"));
		while (ref_sent.readSentenceFromInputStream(br)) {
			++nCount;
			if (nCount % 1000 == 0) {
				System.out.println(parser.totalerror() + "/" + nCount);
			}
			if (!supertag) {
				ref_sent.clearSuperTag();
			}
			parser.train(ref_sent, nCount);
		}
		parser.finishtraning();
		System.out.println("Done.");
	}
	
}
