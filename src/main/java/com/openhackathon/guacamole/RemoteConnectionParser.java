package com.openhackathon.guacamole;

import org.json.JSONObject;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import java.util.Map;

interface RemoteConnectionParser
{
    public Map<String, GuacamoleConfiguration> parseGuacamoleConfiguration(JSONObject json);
}