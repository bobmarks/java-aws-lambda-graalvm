package lambda.test;

import static java.lang.String.format;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloLambdaHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        if (input.getPathParameters() != null && input.getPathParameters().containsKey("name")) {
            String name = input.getPathParameters().get("name");
            return hello(name);
        }
        return hello("World");
    }

    private APIGatewayV2HTTPResponse hello(String name) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return APIGatewayV2HTTPResponse.builder()
            .withHeaders(headers)
            .withStatusCode(200)
            .withBody(format("{\"message\": \"Hello %s\"}", name))
            .build();
    }

}
