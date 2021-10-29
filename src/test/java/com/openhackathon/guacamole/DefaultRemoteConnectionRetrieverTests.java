package com.openhackathon.guacamole;

import org.junit.runner.RunWith;  
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;  
import org.powermock.core.classloader.annotations.PrepareForTest;  
import static org.mockito.Mockito.*;
import java.net.*;
import java.io.*;

@RunWith(PowerMockRunner.class)  
@PrepareForTest({URL.class, HttpURLConnection.class, DefaultRemoteConnectionRetriever.class})  
public class DefaultRemoteConnectionRetrieverTests {
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

        DefaultRemoteConnectionRetriever retriever = new DefaultRemoteConnectionRetriever(appId);
        JSONObject json = retriever.getRemoteConnections(u, testToken);
        assertNull(json);

        verify(u, times(1)).openConnection();
        verify(huc, times(1)).setRequestMethod("GET");
        verify(huc, times(1)).setUseCaches(false);
        verify(huc, times(1)).setRequestProperty("Authorization", "token " + testToken);
        verify(huc, times(1)).setRequestProperty("x-openhackathon-app", appId);
        verify(huc, times(1)).connect();
        verify(huc, times(1)).getResponseCode();
    }

    @Test
    public void getRemoteConnections_json() throws Exception {
        String url = "https://someurl.com";
        String appId = "someapp";
        String testToken = "sometoken";
        InputStream stream = new ByteArrayInputStream("{\"value\":[{\"protocol\":\"vnc\",\"port\":5901}]}".getBytes());

        UrlWrapper u = PowerMockito.mock(UrlWrapper.class);
        PowerMockito.whenNew(UrlWrapper.class).withArguments(url).thenReturn(u);
        PowerMockito.when(u.getUrl()).thenReturn(url);

        HttpURLConnection huc = mock(HttpURLConnection.class);
        PowerMockito.when(u.openConnection()).thenReturn(huc);
        PowerMockito.when(huc.getResponseCode()).thenReturn(200);
        PowerMockito.when(huc.getInputStream()).thenReturn(stream);

        DefaultRemoteConnectionRetriever retriever = new DefaultRemoteConnectionRetriever(appId);
        JSONObject json = retriever.getRemoteConnections(u, testToken);
        assertNotNull(json);
        assertEquals(1, json.getJSONArray("value").length());
        JSONObject conn = json.getJSONArray("value").getJSONObject(0);
        assertEquals("vnc", conn.getString("protocol"));
        assertEquals(5901, conn.getInt("port"));

        verify(u, times(1)).openConnection();
        verify(huc, times(1)).setRequestMethod("GET");
        verify(huc, times(1)).setUseCaches(false);
        verify(huc, times(1)).setRequestProperty("Authorization", "token " + testToken);
        verify(huc, times(1)).setRequestProperty("x-openhackathon-app", appId);
        verify(huc, times(1)).connect();
        verify(huc, times(1)).getResponseCode();
        verify(huc, times(1)).getInputStream();
    }
}