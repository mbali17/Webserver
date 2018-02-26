package com.hw1.common;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ALI 1001400462.A POJO object consisting the values for the webserver.
 */
public class WebserverDetailsPojo {
    private int port;
    private String docRoot;
    private String ipAddress;

    public WebserverDetailsPojo() {
        this.port = Constants.DEFAULT_PORT;
        this.docRoot = getCurrentDirectory();
        this.ipAddress = Constants.DEFAULT_HOST;
    }

    public WebserverDetailsPojo(int port, String docRoot) {
        this.port = port;
        this.docRoot = docRoot;
    }

    public WebserverDetailsPojo(String ipAddress,int port){
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDocRoot() {
        return docRoot;
    }

    public void setDocRoot(String docRoot) {
        this.docRoot = docRoot;
    }

    /**
     * Obtain the current working directory.
     * @return {@code String} The current working directory of the user.
     * Reference https://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
     */
    private static String getCurrentDirectory(){
        Path currentWorkingDirectory = Paths.get("");
        return currentWorkingDirectory.toAbsolutePath().toString();
    }
}
