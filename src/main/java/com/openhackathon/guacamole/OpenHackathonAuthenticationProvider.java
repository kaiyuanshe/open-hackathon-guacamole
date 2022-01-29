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
import org.apache.guacamole.token.TokenName;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.net.MalformedURLException;

public class OpenHackathonAuthenticationProvider extends SimpleAuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(OpenHackathonAuthenticationProvider.class.getClass());
    private RemoteConnectionRetriever retriever;
    private RemoteConnectionParser parser;

    private static final String getOpenHackathonAppId() throws GuacamoleException {
        String value = System.getenv(TokenName.canonicalize(OpenHackathonConstants.OpenHackathonAppIdConfigName));
        if(value != null){
            return value;
        }

        Environment environment = LocalEnvironment.getInstance();
        String appId = environment.getProperty(OpenHackathonGuacamoleProperties.OPEN_HACKATHON_APP_ID);
        return appId;
    }

    @Override
    public String getIdentifier() {
        return "openhackathon";
    }

    public OpenHackathonAuthenticationProvider() {
        this.parser = new DefaultRemoteConnectionParser();
        logger.info("initialize OpenHackathonAuthenticationProvider");
    }

    @Override
    public Map<String, GuacamoleConfiguration> getAuthorizedConfigurations(final Credentials credentials) throws GuacamoleException {
        initializeRetriever();

        final JSONObject json = getRemoteConnections(credentials.getRequest());
        if (json == null) {
            return new HashMap<String, GuacamoleConfiguration>();
        }

        return parser.parseGuacamoleConfiguration(json);
    }

    private JSONObject getRemoteConnections(final HttpServletRequest request) throws GuacamoleException {
        final String hackathon = request.getParameter(OpenHackathonConstants.QueryStringKeyHackathon);
        final String experiment = request.getParameter(OpenHackathonConstants.QueryStringKeyExperiment);
        final String token = request.getParameter(OpenHackathonConstants.QueryStringKeyToken);
        logger.info("Reading guacamole configurations for hackathon: " + hackathon + ", experiment:" + experiment + ", token: " + token);

        if (hackathon == null || experiment == null || token==null) {
            return null;
        }

        try {
            UrlWrapper url = UrlHelper.getRequestUrl(hackathon, experiment);
            return this.retriever.getRemoteConnections(url, token);
        } catch (MalformedURLException e){
            logger.error("fail to build open hackathon url", e);
            return null;
        }
    }

    private synchronized void initializeRetriever() {
        if (this.retriever != null)
            return;
        try {
            final String appId = getOpenHackathonAppId();
            logger.info("Read OPEN_HACKATHON_APP_ID: " + appId);
            this.retriever = new DefaultRemoteConnectionRetriever(appId);
        } catch (GuacamoleException e) {
            logger.error("fail to get open-hackathon-app-id from guacamole.properties", e);
        }
    }
}
