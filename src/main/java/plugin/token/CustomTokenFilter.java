package plugin.token;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

import java.io.IOException;

public class CustomTokenFilter extends TokenFilter {
    private final CharTermAttribute charTermAttr = addAttribute(CharTermAttribute.class);

    protected CustomTokenFilter(TokenStream input) {
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            String token = charTermAttr.toString();
            if (token.equals("서울대")) {
                charTermAttr.setEmpty().append("서울대학교");
            }
            return true;
        } else {
            return false;
        }
    }
}

class CustomTokenFilterFactory extends AbstractTokenFilterFactory {

    public CustomTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
    	return new CustomTokenFilter(tokenStream);
    }
}