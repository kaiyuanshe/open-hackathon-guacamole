package com.openhackathon.guacamole;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.*;

public class UrlHelperTests {
    @Test
    public void getRequestUrl() throws Exception {
        String hackathon = "hack";
        String experiment = "exp";

        UrlWrapper url = UrlHelper.getRequestUrl(hackathon, experiment);
        assertEquals(OpenHackathonConstants.OpenHackathonDefaultEndpoint + "/v2/hackathon/hack/experiment/exp/connections", url.getUrl());
    }
}