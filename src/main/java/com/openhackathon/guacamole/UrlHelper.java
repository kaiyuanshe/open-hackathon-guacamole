package com.openhackathon.guacamole;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.environment.Environment;
import org.apache.guacamole.environment.LocalEnvironment;
import java.net.MalformedURLException;

public class UrlHelper {
    public final static UrlWrapper getRequestUrl(String hackathon, String experiment) 
        throws GuacamoleException, MalformedURLException {
        // format: /v2/hackathon/{hackathonName}/experiment/{experimentId}/connections
        // see https://hackathon-api.kaiyuanshe.cn/swagger/index.html for details.
        String baseUrl = getBaseUrl();
        String url = String.format("%sv2/hackathon/%s/experiment/%s/connections", baseUrl, hackathon, experiment);
        return new UrlWrapper(url);
    }

    private static final String getBaseUrl() throws GuacamoleException {
        Environment environment = new LocalEnvironment();
        String baseUrl = environment.getProperty(OpenHackathonGuacamoleProperties.OPEN_HACKATHON_HOSTNAME, OpenHackathonConstants.OpenHackathonDefaultEndpoint);
        if(!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        return baseUrl;
    }
}