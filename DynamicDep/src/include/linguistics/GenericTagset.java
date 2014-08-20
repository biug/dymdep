package include.linguistics;

import include.util.Tokenizer;

/*
 * @author ZhangXun
 */

public class GenericTagset extends Tokenizer {
	public GenericTagset() {
		super(0);
		lookup("-NONE-");
	}
}
