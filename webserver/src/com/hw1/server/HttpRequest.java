package com.hw1.server;

import com.hw1.common.Constants;
import com.hw1.common.WebserverDetailsPojo;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ALI 1001400462.
 */
public final class HttpRequest implements Runnable {
    private Socket socket;
    private WebserverDetailsPojo webserverDetailsPojo;
    //Parametrized constructor  to attach a request to the socket and pass server details.
    public HttpRequest(Socket socket,WebserverDetailsPojo detailsPojo) {
        System.out.println("Obtained new request");
        this.socket=socket;
        this.webserverDetailsPojo = detailsPojo;
    }

    /**
     * Implementation of the run method of the runnable interface which handles the processing of each thread.
     */
    @Override
    public void run() {
        try {
            //Process the request and provide appropriate response.
            processRequest();
        }catch (Exception e){
            System.out.println("Some exception occured while processing request");
            e.printStackTrace();
        }
    }

    /**
     * Perform the operation of each request.
     * @throws Exception
     */
    private void processRequest() throws Exception {
        //Sockets input and output stream.
        InputStream socketsInputStream = null;
        DataOutputStream socketsOutputStream = null;
        BufferedReader bufferedReader = null;
        try {
            System.out.println("Processing request!");
            socketsInputStream = socket.getInputStream();
            socketsOutputStream = new DataOutputStream (socket.getOutputStream());
            //InputStream reader.
            bufferedReader = new BufferedReader(new InputStreamReader(socketsInputStream));
            //Read request Message.
            String requestMessage = bufferedReader.readLine();
            System.out.println("Message in the request" + requestMessage);
            String fileName = extractFilenameFromRequest(requestMessage);
            System.out.println("Extracted file name "+fileName +" Sending response");
            sendResponse(isExists(fileName),socketsOutputStream,fileName);
            // Get and display the header lines.
            String headerLine = null;
            while ((headerLine = bufferedReader.readLine()).length() != 0) {
                System.out.println(headerLine);
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("java.io.IOException while sending response"+e.getMessage());
        } finally{
            if(socketsInputStream != null){
                socketsInputStream.close();
            }
            if(socketsOutputStream != null){
                socketsOutputStream.close();
            }
            if(bufferedReader != null){
                bufferedReader.close();
            }
        }

    }

    /**
     * Sends response- if file exists,returns the file or returns 404 message.
     * The message has the following format.
     * a)statusline+<code>Constants.CRLF</code>
     * b)contenttype + <code>Constants.CRLF</code>
     * c) <code>Constants.CRLF</code>
     * c)Entity body- which is  the response body
     * @param exists
     * @param socketsOutputStream
     * @param fileName
     */
    private void sendResponse(boolean exists, OutputStream socketsOutputStream, String fileName) throws IOException {
        String statusLine="";
        String contentType= "Content-Type: "+Files.probeContentType(Paths.get(webserverDetailsPojo.getDocRoot(), fileName));
        if(exists){
            statusLine = "HTTP/1.0 200 Found";
            wtiteResponse(statusLine,contentType,socketsOutputStream,fileName);
        }else {
            statusLine = "HTTP/1.0 404 Not Found";
            wtiteResponse(statusLine,contentType,socketsOutputStream,"404.html");
        }
    }

    /**
     *Writes the response to the output stream.
     * @param statusLine
     * @param contentType
     * @param socketsOutputStream
     * @param filename
     * @throws IOException
     */
    private void wtiteResponse(String statusLine, String contentType, OutputStream socketsOutputStream, String filename) throws IOException {
        socketsOutputStream.write((statusLine+ Constants.CRLF).getBytes());
        socketsOutputStream.write((contentType+Constants.CRLF).getBytes());
        socketsOutputStream.write(Constants.CRLF.getBytes());
        writeFileToOutputStream(filename,socketsOutputStream);
        System.out.println("sent response!");
    }

    /**
     *Reads the content of the file and writes to the sockets output stream.
     * @param fileName -- The file to be read.
     * @param socketsOutputStream
     * @return -- String representation of the file contents.
     */
    private void writeFileToOutputStream(String fileName, OutputStream socketsOutputStream) throws IOException {
        Path filePath = Paths.get(webserverDetailsPojo.getDocRoot(), fileName);
        FileInputStream fileInputStream = new FileInputStream(filePath.toString());
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while((bytes = fileInputStream.read(buffer)) != -1){
            socketsOutputStream.write(buffer,0,bytes);
        }
    }


    /**
     * Tokenize request message and obtain file name.
     * @param requestMessage
     * @return {@code String fileName}
     */
    private String extractFilenameFromRequest(String requestMessage) {
        //Sample request with file name GET /something.html HTTP/1.1 -- the Second element is the filename.
        //Sample request without file name GET / HTTP/1.1
        if(requestMessage !=null || !requestMessage.isEmpty()){
            //split request message by space
            String[] splitRequestMessage = requestMessage.split(" ");
            //check if root is requested, if requested return default file name else return filename
            String fileName = splitRequestMessage[1];
            if(fileName.equals(Constants.ROOT)){
                return Constants.DEFAULT_FILE_NAME;
            }else {
                return fileName;
            }
        }
        return Constants.DEFAULT_FILE_NAME;
    }

    /**
     * Checks if the requested file exists.
     * @param fileName
     * @return {@code boolean true} if the file exists or {@code boolean false} if the file does not exist.
     */
    private boolean isExists(String fileName){
        //Regex refer:https://stackoverflow.com/a/4546093/6765884
        String fileExtension = fileName.split("\\.(?=[^\\.]+$)")[1];
        if (!Constants.VALID_FILE_EXTENSIONS_LIST.contains(fileExtension)) {
            System.out.println("The requested file Extension " + fileExtension + " is not validS");
            return false;
        }
        Path filePath = Paths.get(webserverDetailsPojo.getDocRoot(), fileName);
        File requestedFile = new File(filePath.toString());
        return requestedFile.exists();
    }
}