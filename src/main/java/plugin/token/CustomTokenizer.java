package plugin.token;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

import java.io.IOException;

public class CustomTokenizer extends Tokenizer {

    private final CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
    private final StringBuilder buffer = new StringBuilder();

    @Override
    public final boolean incrementToken() throws IOException {
        clearAttributes();
        buffer.setLength(0);

        int cp;
        while ((cp = input.read()) != -1) {
//            if (Character.isWhitespace(cp)) {
//                if (buffer.length() > 0) break; // 공백으로 토큰화
//            } else {
//                buffer.append((char) cp);
//            }
            char ch = (char) cp;
            if (ch == '_') {
                if (buffer.length() > 0) break;  // _ 로 토큰화
            } else {
                buffer.append(ch);
            }
        }

        if (buffer.length() == 0) return false;
        
        charTermAttribute.append(buffer);
        charTermAttribute.setLength(buffer.length());
        return true;
    }
}

class CustomTokenizerFactory extends AbstractTokenizerFactory {

    public CustomTokenizerFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, settings, name);
    }

    @Override
    public Tokenizer create() {
        return new CustomTokenizer();
    }
}