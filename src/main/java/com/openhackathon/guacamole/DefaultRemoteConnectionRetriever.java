package com.openhackathon.guacamole;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.openhackathon.guacamole.UrlWrapper;

class DefaultRemoteConnectionRetriever implements RemoteConnectionRetriever
{
    private Logger logger = LoggerFactory.getLogger(DefaultRemoteConnectionRetriever.class.getClass());
    private UrlWrapper remoteConnApiUrl = null;
    private String appId = null;

    public DefaultRemoteConnectionRetriever(final UrlWrapper remoteConnApiUrl, final String appId) {
        this.remoteConnApiUrl = remoteConnApiUrl;
        this.appId = appId;
    }

    public JSONObject getRemoteConnections(String token)
    {
        logger.debug("get remote connections from openhackathon. remoteConnApiUrl:" + this.remoteConnApiUrl.getUrl() + ", token:" + token);
        String resp = getGuacamoleJSONString(token);
        if(resp == null)
        {
            return null;
        }

        final JSONObject json = new JSONObject(resp);
        if (json.has("error")) {
            logger.info("error returned from open hackathon platform");
            return null;
        }

        return json;
    }

    public String getGuacamoleJSONString(final String token) {

        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            logger.debug("getGuacamoleJSONString from:" + this.remoteConnApiUrl.getUrl());

            HttpURLConnection.setFollowRedirects(false);
            conn = (HttpURLConnection) this.remoteConnApiUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("Authorization", "token " + token);
            conn.setRequestProperty("x-openhackathon-app", this.appId);
            conn.connect();

            int status = conn.getResponseCode();

            if (status >= 400) {
                logger.error("Fail to getGuacamoleJSONString from OpenHackathon. The response code is :" + status);
                logger.debug("user may have not login , please do it before your request !!!");
                return null;
            }

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;

        } catch (Exception e) {
            logger.error("Exception when getGuacamoleJSONString from openHackathon", e);
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
    }
}