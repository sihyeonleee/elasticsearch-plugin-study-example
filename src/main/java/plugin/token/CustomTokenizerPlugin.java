package plugin.token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.index.analysis.CharFilterFactory;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;

import plugin.rest.ExampleNocodeAction;

public class CustomTokenizerPlugin extends Plugin implements AnalysisPlugin, ActionPlugin {
	
    @Override
    public List<RestHandler> getRestHandlers(final Settings settings,
        final RestController restController,
        final ClusterSettings clusterSettings,
        final IndexScopedSettings indexScopedSettings,
        final SettingsFilter settingsFilter,
        final IndexNameExpressionResolver indexNameExpressionResolver,
        final Supplier<DiscoveryNodes> nodesInCluster) {
        return Arrays.asList(
            new ExampleNocodeAction(settings, restController));
    }

    @Override
    public Map<String, AnalysisProvider<TokenizerFactory>> getTokenizers() {
        Map<String, AnalysisProvider<TokenizerFactory>> tokenizers = new HashMap<>();
        tokenizers.put("lsh_token", CustomTokenizerFactory::new);
        return tokenizers;
    }
    
    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
    	Map<String, AnalysisProvider<TokenFilterFactory>> tokenFilters = new HashMap<>();
    	tokenFilters.put("lsh_filter", CustomTokenFilterFactory::new);
    	return tokenFilters;
    }
    
    @Override
    public Map<String, AnalysisProvider<CharFilterFactory>> getCharFilters() {
    	Map<String, AnalysisProvider<CharFilterFactory>> charFilters = new HashMap<>();
    	charFilters.put("lsh_char_filter", CustomCharFilterFactory::new);
    	return charFilters;
    }
}