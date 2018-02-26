package com.hw1.server;

import com.hw1.common.WebserverDetailsPojo;

import java.net.*;

/**
 * ALi 1001400462.This class set's the socket to listen continuously for the incoming requests.
 */
public final class WebServer {
    public static void main(String argv[]) throws Exception {
        ServerSocket listeningSocket = null;
        try {
            System.out.println("Welcome to the multi-threaded server");
            WebserverDetailsPojo detailsPojo=null;
            /*Check if the user has passed command line params.If passed then the first param is the port
                number  for the server to start and second parameter is the folder path to search for the files*/
            if (argv.length == 2) {
                detailsPojo = new WebserverDetailsPojo(Integer.parseInt(argv[0]),argv[1]);
            }else{
                //Create object with default values.
                detailsPojo = new WebserverDetailsPojo();
            }
            System.out.println("Starting server on port "+ detailsPojo.getPort());
            //Create server socket which listens to the incoming requests on the network.
            listeningSocket = new ServerSocket(detailsPojo.getPort());
            System.out.println("server Started on port "+ detailsPojo.getPort() +" and docroot is: "+detailsPojo.getDocRoot());
            //Listen infinitely for the incoming requests.
            while (true){
                //Accept the request from the socket.
                Socket incomingRequest = listeningSocket.accept();
                //create request object.
                HttpRequest request = new HttpRequest(incomingRequest,detailsPojo);
                //Process request in separate thread.
                Thread processRequest = new Thread(request);
                processRequest.start();
            }

        }catch (NumberFormatException numFormatExcpetion){
            System.out.println("java.lang.NumberFormatException while parsing user input"+numFormatExcpetion.getMessage());
        }finally {
            if(listeningSocket != null) {
                listeningSocket.close();
            }
        }
        }
    }
