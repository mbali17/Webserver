package com.hw1.common;

import java.util.*;

/**
 * Created by ALI 1001400462.
 */
public class Constants {
    private Constants(){
        //This class is used to declare constants for the Project and hence a private constructor
        // so no object of this class is created
    }
    //Default port number for the server to start.
    public static final int DEFAULT_PORT = 8080;
    //Carriage return and line feed.
    public static final String CRLF = "\r\n";
    //Default File Name requested.
    public static final String DEFAULT_FILE_NAME="/index.html";
    public static final String ROOT = "/";
    public static final List<String> VALID_FILE_EXTENSIONS_LIST =
            new ArrayList<>(Arrays.asList("html","pdf","png","gif","py","docx"));
    public static final String DEFAULT_HOST = "localhost";
    public static final String METHOD_GET = "GET";
    public static final String HTTP_VERSION = "HTTP/1.1";
}
