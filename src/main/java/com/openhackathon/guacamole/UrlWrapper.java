package com.openhackathon.guacamole;

import java.net.*;
import java.io.IOException;

public class UrlWrapper {
    URL url;
    public UrlWrapper(String spec) throws MalformedURLException {
        url = new URL(spec);
    }
    public URLConnection openConnection() throws IOException {
        return url.openConnection();
    }
}