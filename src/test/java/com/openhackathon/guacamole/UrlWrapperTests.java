package com.openhackathon.guacamole;

import org.junit.runner.RunWith;  
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;  
import org.powermock.core.classloader.annotations.PrepareForTest;  
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.net.*;
import java.io.IOException;


@RunWith(PowerMockRunner.class)  
@PrepareForTest({URL.class, UrlWrapper.class})  
public class UrlWrapperTests {
    // powermockito: https://github.com/powermock/powermock/wiki
    // more examples: https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
    // https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html
    @Test
    public void openConnection() throws Exception {
        String url = "https://test.com";
        HttpURLConnection huc = PowerMockito.mock(HttpURLConnection.class);
        UrlWrapper u = PowerMockito.mock(UrlWrapper.class);

        PowerMockito.whenNew(UrlWrapper.class).withArguments(url).thenReturn(u);
        PowerMockito.when(u.openConnection()).thenReturn(huc);

        assertTrue(u.openConnection() instanceof HttpURLConnection);
    }

    @Test
    public void getUrl() throws Exception {
        String url = "https://test.com";

        UrlWrapper u = new UrlWrapper(url);
        assertEquals(url, u.getUrl());
    }
}