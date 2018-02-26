package com.hw1.client;

import com.hw1.common.Constants;
import com.hw1.common.WebserverDetailsPojo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ALI 1001400462.
 */
public class ClientHelper {
    //Extract details from the command line arguments
    public static WebserverDetailsPojo obtainServerDetails(String[] args) {
        if(args.length >= 2){
            return new WebserverDetailsPojo(args[0],Integer.parseInt(args[1]));
        }else {
            System.out.println("No values passed from command line,Hence using default values");
            return new WebserverDetailsPojo();
        }
    }

    //Connect to the server with details passed.
    public static Socket connectToserver(WebserverDetailsPojo webserverDetailsPojo) {
        try {
            return new Socket(webserverDetailsPojo.getIpAddress(),webserverDetailsPojo.getPort());
        } catch (UnknownHostException e) {
            System.err.println("java.net.UnknownHostException while connecting to server"+e.getMessage());
        } catch (IOException e) {
            System.err.println("java.io.IOException while connecting to server"+e.getMessage());
        }
        return null;
    }
    /**
    *Send request for the file passed from the command line.
    */
    public static void sendRequestForFile(String fileName,Socket socket) {
        //Obtain Input and Output Streams from the socket.
        InputStream socketsInputStream = null;
        DataOutputStream socketsOutputStream = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            socketsInputStream = socket.getInputStream();
            socketsOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketsOutputStream));
            bufferedWriter.write(String.valueOf(Constants.METHOD_GET+" "+fileName+" "+Constants.HTTP_VERSION+Constants.CRLF));
            bufferedWriter.write("Host: "+socket.getInetAddress()+Constants.CRLF);
            bufferedWriter.write(Constants.CRLF);
            bufferedWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(socketsInputStream));
            //Read request Message.
            String requestMessage = bufferedReader.readLine();
            System.out.println("Response Message is \n" + requestMessage);
            System.out.println("headers in the response are");
            String headerLine = null;
            //Read Headers from the response
            while ((headerLine = bufferedReader.readLine()).length() != 0) {
                System.out.println(headerLine);
            }
            //Read the file Written in response.
            System.out.println("The file contents are ");
            int currentCharachter;
            while ((currentCharachter = bufferedReader.read()) != -1) {
                System.out.print((char) currentCharachter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socketsInputStream != null && socketsOutputStream != null){
                try {
                    socketsInputStream.close();
                    socketsOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
