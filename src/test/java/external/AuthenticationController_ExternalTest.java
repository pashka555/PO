package external;

import lnu.postoffice.model.Message;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationController_ExternalTest {

    @Test
    public void loadReturnsValidStatusCode() throws IOException {

        HttpUriRequest request = new HttpGet( "http://localhost:8080/load" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        Assert.assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    public void requestingToLoadReturnsMessageWithContent() throws IOException, ParseException, NoSuchFieldException {

        HttpUriRequest request = new HttpGet( "http://localhost:8080/load" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        List<Message> messageList = new LinkedList<Message>();

        JSONParser jsonParser = new JSONParser();

        Object receiver = jsonParser.parse(httpResponse.getEntity().getContent());

        for(int i = 0 ; i < ((JSONArray)receiver).size(); i++) {
            assert (((JSONObject) ((JSONArray) receiver).get(i)).get("nickname") != null);
            assert (((JSONObject) ((JSONArray) receiver).get(i)).get("content") != null);
        }
    }
}
