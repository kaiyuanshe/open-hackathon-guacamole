/*
 * This file is covered by the LICENSING file in the root of this project.
 */

package com.openhackathon.guacamole;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.environment.Environment;
import org.apache.guacamole.environment.LocalEnvironment;
import org.apache.guacamole.net.auth.simple.SimpleAuthenticationProvider;
import org.apache.guacamole.net.auth.Credentials;
import org.apache.guacamole.properties.StringGuacamoleProperty;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class OpenHackathonAuthenticationProvider extends SimpleAuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(OpenHackathonAuthenticationProvider.class.getClass());
    private RemoteConnectionRetriever retriever;

    private static final String getOpenHackathonAppId() throws GuacamoleException {
        Environment environment = new LocalEnvironment();
        return environment.getProperty(GuacamoleProperties.OPEN_HACKATHON_APP_ID);
    }

    @Override
    public String getIdentifier() {
        return "openhackathon";
    }

    public OpenHackathonAuthenticationProvider() {
        logger.info("initialize OpenHackathonAuthenticationProvider");
    }

    @Override
    public Map<String, GuacamoleConfiguration> getAuthorizedConfigurations(final Credentials credentials) throws GuacamoleException {
        initializeRetriever();

        Map<String, GuacamoleConfiguration> configs = new HashMap<String, GuacamoleConfiguration>();

        final GuacamoleConfiguration config = getGuacamoleConfiguration(credentials.getRequest());
        if (config == null) {
            return configs;
        }

        configs.put(config.getParameter("name"), config);
        return configs;
    }

    private JSONObject getRemoteConnections(final HttpServletRequest request) throws GuacamoleException {

        final String hackathon = request.getParameter(OpenHackathonConstants.QueryStringKeyHackathon);
        final String experiment = request.getParameter(OpenHackathonConstants.QueryStringKeyExperiment);
        final String token = request.getParameter(OpenHackathonConstants.QueryStringKeyToken);
        logger.info("Reading guacamole configurations for hackathon: " + hackathon + ", experiment:" + experiment + ", token: " + token);

        if (hackathon == null || experiment == null || token==null) {
            return null;
        }

        //final String jsonString = this.retriever.getRemoteConnections(connectionName, tokenString);
        String jsonString = null;
        logger.info("get guacamole config json String :" + jsonString);
        if (jsonString == null) {
            logger.info("get null jsonString from openHackathon platform");
            return null;
        }

        // String finalString = jsonString.substring(1, jsonString.length()-1).replace("\\", "");
        return null;
    }

    private GuacamoleConfiguration getGuacamoleConfiguration(final HttpServletRequest request) throws GuacamoleException {

        final String hackathon = request.getParameter(OpenHackathonConstants.QueryStringKeyHackathon);
        final String experiment = request.getParameter(OpenHackathonConstants.QueryStringKeyExperiment);
        final String token = request.getParameter(OpenHackathonConstants.QueryStringKeyToken);
        logger.info("Reading guacamole configurations for hackathon: " + hackathon + ", experiment:" + experiment + ", token: " + token);

        if (hackathon == null || experiment == null || token==null) {
            return null;
        }

        //final String jsonString = this.retriever.getRemoteConnections(connectionName, tokenString);
        String jsonString = null;
        logger.info("get guacamole config json String :" + jsonString);
        if (jsonString == null) {
            logger.info("get null jsonString from openHackathon platform");
            return null;
        }

        // String finalString = jsonString.substring(1, jsonString.length()-1).replace("\\", "");
        return getConfiguration(jsonString);
    }

    private synchronized void initializeRetriever() {
        if (this.retriever != null)
            return;
        try {
            final String appId = getOpenHackathonAppId();
            this.retriever = new DefaultRemoteConnectionRetriever(appId);
        } catch (GuacamoleException e) {
            logger.error("fail to get open-hackathon-app-id from guacamole.properties", e);
        }
    }

    private GuacamoleConfiguration getConfiguration(final String jsonString) {
        try {

            final JSONObject json = new JSONObject(jsonString);
            if (json.has("error")) {
                logger.info("error returned from open hackathon platform");
                return null;
            }

            final GuacamoleConfiguration configuration = new GuacamoleConfiguration();
            final Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                final String key = keys.next();
                if (key.equals("displayname")) {
                    continue;
                }
                if (key.equals("protocol")) {
                    configuration.setProtocol(json.getString("protocol"));
                } else {
                    configuration.setParameter(key, json.getString(key));
                }
            }
            return configuration;

        } catch (Exception e) {
            logger.error("Failed to load GuacamoleConfiguation from json " + jsonString, e);
            return null;
        }
    }
}
