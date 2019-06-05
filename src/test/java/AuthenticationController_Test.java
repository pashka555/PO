import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.*;

import static org.hamcrest.CoreMatchers.equalTo;


public class AuthenticationController_Test {

    @Test
    public void validStatusCode() throws IOException {

        HttpUriRequest request = new HttpGet( "https://api.github.com/events" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        Assert.assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.OK));

    }
}
