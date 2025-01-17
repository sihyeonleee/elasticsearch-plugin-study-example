package plugin.rest;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

/**
 * Example action with a plugin.
 */
public class ExampleNocodeAction extends BaseRestHandler {

    public ExampleNocodeAction(final Settings settings,
        final RestController controller) {
        // nothing
    }

    @Override
    public String getName() {
        return "nocode_example_action.";
    }

    @Override
    public List<Route> routes() {
        return Collections.unmodifiableList(Arrays.asList(
            new Route(GET, "/{index}/_nocode"),
            new Route(GET, "/_nocode")));
    }


    @Override
    protected RestChannelConsumer prepareRequest(final RestRequest request,
        final NodeClient client) throws IOException {
        final boolean isPretty = request.hasParam("pretty");
        final String index = request.param("index");
        return channel -> {
            final XContentBuilder builder = JsonXContent.contentBuilder();
            if (isPretty) {
                builder.prettyPrint().lfAtEnd();
            }
            builder.startObject();
            if (index != null) {
                builder.field("index", index);
            }
            builder.field("description",
                "This is a example response: " + new Date().toString());
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(OK, builder));
        };
    }


}