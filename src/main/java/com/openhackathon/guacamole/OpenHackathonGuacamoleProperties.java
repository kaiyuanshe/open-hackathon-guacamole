package com.openhackathon.guacamole;

import org.apache.guacamole.properties.StringGuacamoleProperty;

/**
 * Utility class containing all properties used by the custom authentication
 * tutorial. The properties defined here must be specified within
 * guacamole.properties to configure the tutorial authentication provider.
 */
public class OpenHackathonGuacamoleProperties {

    /**
     * This class should not be instantiated.
     */
    private OpenHackathonGuacamoleProperties() {}

    /**
     * The endpoint of open hackathon api.
     */
    public static final StringGuacamoleProperty OPEN_HACKATHON_HOSTNAME = new StringGuacamoleProperty() {
        @Override
        public String getName() { 
            return OpenHackathonConstants.OpenHackathonApiEndpointConfigName;
        }
    };

    /**
     * The app id required by open hackathon api.
     */
    public static final StringGuacamoleProperty OPEN_HACKATHON_APP_ID = new StringGuacamoleProperty() {
        @Override
        public String getName() { 
            return OpenHackathonConstants.OpenHackathonAppIdConfigName; 
        }
    };
}