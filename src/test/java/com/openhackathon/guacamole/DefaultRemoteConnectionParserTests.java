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
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import java.util.*;

@RunWith(PowerMockRunner.class)  
public class DefaultRemoteConnectionParserTests {
    @Test
    public void parseGuacamoleConfiguration_noValue() {
        String resp = "{}";
        JSONObject json = new JSONObject(resp);

        DefaultRemoteConnectionParser parser = new DefaultRemoteConnectionParser();
        Map<String, GuacamoleConfiguration> configs = parser.parseGuacamoleConfiguration(json);

        assertNotNull(configs);
        assertEquals(0, configs.size());
    }

    @Test
    public void parseGuacamoleConfiguration_emptyList() {
        String resp = "{\"value\":[]}";
        JSONObject json = new JSONObject(resp);

        DefaultRemoteConnectionParser parser = new DefaultRemoteConnectionParser();
        Map<String, GuacamoleConfiguration> configs = parser.parseGuacamoleConfiguration(json);

        assertNotNull(configs);
        assertEquals(0, configs.size());
    }

    @Test
    public void parseGuacamoleConfiguration_vnc() {
        String resp = "{\"value\":[{\"protocol\":\"vnc\",\"name\":\"test\",\"hostname\":\"10.0.0.1\",\"port\":5901,\"foo\":\"bar\"}]}";
        JSONObject json = new JSONObject(resp);

        DefaultRemoteConnectionParser parser = new DefaultRemoteConnectionParser();
        Map<String, GuacamoleConfiguration> configs = parser.parseGuacamoleConfiguration(json);

        assertNotNull(configs);
        assertEquals(1, configs.size());
        assertTrue(configs.containsKey("test"));
        GuacamoleConfiguration conn = configs.get("test");
        assertEquals("vnc", conn.getProtocol());
        assertEquals(4, conn.getParameters().size());
        assertEquals("test", conn.getParameter("name"));
        assertEquals("5901", conn.getParameter("port"));
        assertEquals("10.0.0.1", conn.getParameter("hostname"));
        assertEquals("bar", conn.getParameter("foo"));
    }
}