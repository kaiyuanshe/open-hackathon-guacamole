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
    private String appId = null;

    public DefaultRemoteConnectionRetriever(final String appId) {
        this.appId = appId;
    }

    public JSONObject getRemoteConnections(UrlWrapper url, String token)
    {
        logger.info("get remote connections from openhackathon. remoteConnApiUrl:" + url.getUrl() + ", token:" + token + ", appId:" + this.appId + " ");
        String resp = getGuacamoleJSONString(url, token);
        logger.info("response from open hackathon api:" + resp);
        if(resp == null)
        {
            logger.info("get null jsonString from openHackathon platform");
            return null;
        }

        final JSONObject json = new JSONObject(resp);
        if (json.has("error")) {
            logger.info("error returned from open hackathon platform");
            return null;
        }

        return json;
    }

    public String getGuacamoleJSONString(UrlWrapper url, String token) {

        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            logger.info("getGuacamoleJSONString from:" + url.getUrl());

            HttpURLConnection.setFollowRedirects(false);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty(OpenHackathonConstants.HeaderNameAuthorization, "token " + token);
            conn.setRequestProperty(OpenHackathonConstants.HeaderNameAppId, this.appId);
            conn.connect();

            int status = conn.getResponseCode();

            if (status >= 400) {
                logger.error("Fail to getGuacamoleJSONString from OpenHackathon. The response code is :" + status);
                logger.info("user may have not login , please do it before your request !!!");
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
