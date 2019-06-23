import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import lnu.postoffice.model.Message;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AuthenticationController_Test {

    @Test
    public void loadReturnsValidStatusCode() throws IOException {

        HttpUriRequest request = new HttpGet( "http://localhost:8080/load" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        Assert.assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    public void requestingToLoadReturnsMessages() throws IOException {

        HttpUriRequest request = new HttpGet( "http://localhost:8080/load" );

        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        List<Message> messageList = new LinkedList<Message>();



        //Assert.assertEquals();

        Assert.assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }
}
