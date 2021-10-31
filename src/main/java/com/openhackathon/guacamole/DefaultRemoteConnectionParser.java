package com.openhackathon.guacamole;

import org.json.*;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRemoteConnectionParser implements RemoteConnectionParser
{   
    private Logger logger = LoggerFactory.getLogger(DefaultRemoteConnectionParser.class.getClass());

    public Map<String, GuacamoleConfiguration> parseGuacamoleConfiguration(JSONObject json)
    {
        Map<String, GuacamoleConfiguration> configs = new HashMap<String, GuacamoleConfiguration>();

        if (!json.has("value")) {
            logger.info("unexpected result from open hackathon api. no 'value' found");
            return configs;
        }
        
        JSONArray value = json.getJSONArray("value");
        if( value.length() == 0 )
        {
            logger.info("empty list from open hackathon api.");
            return configs;
        }

        for (int i = 0; i < value.length(); i++) {
            JSONObject connection = value.getJSONObject(i);
            GuacamoleConfiguration configuration = new GuacamoleConfiguration();
            String name = connection.getString("name");

            final Iterator<String> keys = connection.keys();
            while (keys.hasNext()) {
                final String key = keys.next();
                if (key.equals("protocol")) {
                    configuration.setProtocol(connection.getString("protocol"));
                } else {
                    configuration.setParameter(key, connection.getString(key));
                }
            }
            configs.put(name, configuration);
        }

        return configs;
    }
}