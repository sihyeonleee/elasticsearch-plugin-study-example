package plugin.token;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.CharFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractCharFilterFactory;

public class CustomCharFilter extends CharFilter {

	private Reader in;
	
	public CustomCharFilter(Reader input) {
		super(input);
		this.in = input;
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int numCharsRead = in.read(cbuf, off, len);
        if (numCharsRead != -1) {
            for (int i = off; i < off + numCharsRead; i++) {
                cbuf[i] = Character.toLowerCase(cbuf[i]);
            }
        }
        return numCharsRead;
	}
	
	@Override
	protected int correct(int currentOff) {
		// TODO Auto-generated method stub
		return currentOff;
	}
    
}

class CustomCharFilterFactory extends AbstractCharFilterFactory {

	public CustomCharFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
		super(indexSettings, name);
	}

	@Override
	public Reader create(Reader reader) {
		return new CustomCharFilter(reader);
	}

    
}