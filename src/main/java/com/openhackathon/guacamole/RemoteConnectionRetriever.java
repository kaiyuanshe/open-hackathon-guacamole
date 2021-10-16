package com.openhackathon.guacamole;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface RemoteConnectionRetriever
{
    public JSONObject GetRemoteConnections(String token);
}