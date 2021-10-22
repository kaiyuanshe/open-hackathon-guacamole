package com.openhackathon.guacamole;

import org.junit.runner.RunWith;  
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;  
import org.powermock.core.classloader.annotations.PrepareForTest;  
import static org.mockito.Mockito.*;
import java.net.*;
import java.io.IOException;

@RunWith(PowerMockRunner.class)  
@PrepareForTest({URL.class, HttpURLConnection.class, DefaultRemoteConnectionRetriever.class})  
public class DefaultRemoteConnectionRetrieverTest {
    @Test
    public void getRemoteConnections_null() throws Exception {
        String url = "https://someurl.com";
        String appId = "someapp";
        String testToken = "sometoken";

        UrlWrapper u = PowerMockito.mock(UrlWrapper.class);
        PowerMockito.whenNew(UrlWrapper.class).withArguments(url).thenReturn(u);
        PowerMockito.when(u.getUrl()).thenReturn(url);

        HttpURLConnection huc = mock(HttpURLConnection.class);
        PowerMockito.when(u.openConnection()).thenReturn(huc);
        PowerMockito.when(huc.getResponseCode()).thenReturn(400);

        DefaultRemoteConnectionRetriever retriever = new DefaultRemoteConnectionRetriever(u, appId);
        JSONObject json = retriever.getRemoteConnections(testToken);
        assertNull(json);

        verify(u, times(1)).openConnection();
        verify(huc, times(1)).setRequestMethod("GET");
        verify(huc, times(1)).setUseCaches(false);
        verify(huc, times(1)).setRequestProperty("Authorization", "token " + testToken);
        verify(huc, times(1)).setRequestProperty("x-openhackathon-app", appId);
        verify(huc, times(1)).connect();
        verify(huc, times(1)).getResponseCode();
    }
}