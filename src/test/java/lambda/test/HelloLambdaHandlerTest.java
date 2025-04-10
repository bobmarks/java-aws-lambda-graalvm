package lambda.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class HelloLambdaHandlerTest {

    private HelloLambdaHandler handler = new HelloLambdaHandler();

    @Test
    public void shouldHelloWorld() {
        Map<String,String> params = new HashMap<>();
        params.put("name", "Jimmy");
        APIGatewayV2HTTPEvent event = APIGatewayV2HTTPEvent.builder()
            .withPathParameters(params)
            .build();

        APIGatewayV2HTTPResponse response = handler.handleRequest(event, null);

        assertEquals(response.getBody(), "{\"message\": \"Hello Jimmy\"}");
    }

}
