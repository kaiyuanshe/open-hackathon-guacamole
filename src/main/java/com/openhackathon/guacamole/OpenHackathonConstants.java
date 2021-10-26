package com.openhackathon.guacamole;

public class OpenHackathonConstants{
    // config names in guacamole.properties
    public final String OpenHackathonApiEndpointConfigName = "open-hackathon-hostname";
    public final String OpenHackathonAppIdConfigName = "open-hackathon-app-id";

    // keys in query string
    public final String QueryStringKeyHackathon = "hackathon";
    public final String QueryStringKeyExperiment = "experiment";
    public final String QueryStringKeyToken = "token";

    // header names while accessing open hackathon APIs
    public final String HeaderNameAuthorization = "Authorization";
    public final String HeaderNameAppId = "x-openhackathon-app-id";
}