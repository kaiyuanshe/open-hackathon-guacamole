package com.openhackathon.guacamole;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.openhackathon.guacamole.UrlWrapper;

interface RemoteConnectionRetriever
{
    public JSONObject getRemoteConnections(UrlWrapper url, String token);
}