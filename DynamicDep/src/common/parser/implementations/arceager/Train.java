package common.parser.implementations.arceager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import common.parser.implementations.DependencyTree;

/*
 * @author ZhangXun
 */

public class Train {
	
	public void auto_train(final String sOutputFile, final String sFeatureFile, final boolean bRules) throws IOException {
		System.out.println("Training iteration is started...");
		System.out.flush();
		DepParser parser = new DepParser(sFeatureFile, true);
		DependencyTree ref_sent = new DependencyTree();
		int nCount = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sOutputFile)), "UTF-8"));
		while (ref_sent.readSentenceFromInputStream(br)) {
			++nCount;
			parser.train(ref_sent, nCount);
		}
		parser.finishtraning();
		System.out.println("Done.");
	}
	
}
