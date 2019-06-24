package external;

import lnu.postoffice.Application;
import lnu.postoffice.model.Message;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MessageController_ExternalTest {

    @BeforeClass
    public static void startServer() {
        String[] args = {""};
        Application.main(args);
    }

    @Test
    public void loadReturnsValidStatusCode() throws IOException {

        HttpUriRequest request = new HttpGet( "http://localhost:8090/load" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        Assert.assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    public void requestingToLoadReturnsMessageWithContent() throws IOException, ParseException, NoSuchFieldException {

        HttpUriRequest request = new HttpGet( "http://localhost:8090/load" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        JSONParser jsonParser = new JSONParser();

        Object receiver = jsonParser.parse(httpResponse.getEntity().getContent());

        for(int i = 0; i < ((JSONArray)receiver).size(); i++) {
            assert (((JSONObject) ((JSONArray) receiver).get(i)).get("nickname") != null);
            assert (((JSONObject) ((JSONArray) receiver).get(i)).get("content") != null);
        }
    }

    @Test
    public void sendWithInvalidTokenShouldFail() throws IOException {
        HttpPost request = new HttpPost("http://localhost:8090/send");

        StringEntity params = new StringEntity("{\"content\":\"haha, did it\",\"userAUTH\":{\"hashedSalts\":\"123123123\"}}");

        request.setHeader("content-type", "application/json");

        request.setEntity(params);

        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        assert(EntityUtils.toString(response.getEntity()).equals("An error has occured while processing the message"));
    }

    @Test
    public void sendWithValidTokenShouldSucceed() {

    }
}
