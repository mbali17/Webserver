package com.hw1.client;

import com.hw1.common.Constants;
import com.hw1.common.WebserverDetailsPojo;

import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ALI 1001400462.
 */
public class WebClient {
    public static void main(String []args){
        /**
         * We would need two piece of information to connect to the webserver process.
         * 1)The IpAddress of the server
         * 2)The port on which the webserver is listening.
         */
                
            WebserverDetailsPojo webserverDetailsPojo = ClientHelper.obtainServerDetails(args);
            Socket clientSocket = ClientHelper.connectToserver(webserverDetailsPojo);
            if(args.length == 3) {
                ClientHelper.sendRequestForFile(args[2], clientSocket);
            }else {
                ClientHelper.sendRequestForFile(Constants.DEFAULT_FILE_NAME, clientSocket);
            }


    }
}
