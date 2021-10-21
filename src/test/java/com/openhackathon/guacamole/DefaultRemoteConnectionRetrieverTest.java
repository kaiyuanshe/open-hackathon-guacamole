package com.openhackathon.guacamole;

import org.junit.runner.RunWith;  
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;  
import org.json.JSONObject;
import org.powermock.core.classloader.annotations.PrepareForTest;  
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.net.*;
import java.io.IOException;

@RunWith(PowerMockRunner.class)  
@PrepareForTest({URL.class, HttpURLConnection.class, DefaultRemoteConnectionRetriever.class})  
public class DefaultRemoteConnectionRetrieverTest {

    public class UrlWrapper {
        URL url;
        public UrlWrapper(String spec) throws MalformedURLException {
            url = new URL(spec);
        }
        public URLConnection openConnection() throws IOException {
            return url.openConnection();
        }
    }

    @Test
    public void getRemoteConnections() throws Exception {
        String url = "https://test.com";
        HttpURLConnection huc = mock(HttpURLConnection.class);
        UrlWrapper u = PowerMockito.mock(UrlWrapper.class);
        PowerMockito.whenNew(UrlWrapper.class).withArguments(url).thenReturn(u);
        PowerMockito.when(u.openConnection()).thenReturn(huc);
        PowerMockito.when(huc.getResponseCode()).thenReturn(400);

        // DefaultRemoteConnectionRetriever retriever = PowerMockito.mock(DefaultRemoteConnectionRetriever.class);
        // PowerMockito.whenNew(DefaultRemoteConnectionRetriever.class).withArguments(url, "appId").thenReturn(retriever);
        // PowerMockito.when(retriever.getGuacamoleJSONString("token")).thenReturn("");
        // JSONObject json = retriever.getRemoteConnections("token");
        // assertNull(json);
    }
}