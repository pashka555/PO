package external;

import lnu.postoffice.Application;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

public class AuthenticationController_ExternalTest {

    @BeforeClass
    public static void startServer() {
        String[] args = {""};
        Application.main(args);
    }

    @Test
    public void loginWithValidCredentialsShouldReturnSalts() throws IOException, ParseException {
        HttpPost request = new HttpPost("http://localhost:8090/auth");

        StringEntity params = new StringEntity("{\"login\":\"pavlo\",\"password\":\"123456\"}");

        request.setHeader("content-type", "application/json");

        request.setEntity(params);

        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        JSONParser jsonParser = new JSONParser();

        Object receiver = jsonParser.parse(response.getEntity().getContent());

        assert(((JSONObject)receiver).get("hashedSalts") != null);
    }

    @Test
    public void loginWithInvalidCredentialsShouldReturnErrorSalt() throws IOException, ParseException {
        HttpPost request = new HttpPost("http://localhost:8090/auth");

        StringEntity params = new StringEntity("{\"login\":\"pavlo\",\"password\":\"wrongpassword\"}");

        request.setHeader("content-type", "application/json");

        request.setEntity(params);

        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        JSONParser jsonParser = new JSONParser();

        Object receiver = jsonParser.parse(response.getEntity().getContent());

        assert(((JSONObject)receiver).get("hashedSalts") != "error");
    }
}
