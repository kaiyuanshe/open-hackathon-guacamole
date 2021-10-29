package com.openhackathon.guacamole;

public class OpenHackathonConstants{
    // config names in guacamole.properties
    public static final String OpenHackathonApiEndpointConfigName = "open-hackathon-hostname";
    public static final String OpenHackathonAppIdConfigName = "open-hackathon-app-id";

    // keys in query string
    public static final String QueryStringKeyHackathon = "hackathon";
    public static final String QueryStringKeyExperiment = "experiment";
    public static final String QueryStringKeyToken = "token";

    // header names while accessing open hackathon APIs
    public static final String HeaderNameAuthorization = "Authorization";
    public static final String HeaderNameAppId = "x-openhackathon-app-id";
}