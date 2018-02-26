# Webserver
This project is an implementation of the multi threaded webserver based on the client-server architecture using sockets and HTTP. An HTTP webserver system consists of two entities 

a)Client: is the one requesting for resources on the server.

b)Server: Responds to the requests with the resource requested or with an appropriate message.

Hence, This implementation also has the client and the server part.

The client part of the implementation can be found in the package 
com.hw1.client

The server part of the implementation can be found in the package
com.hw1.server

Compiling the program 

Step 0 : create a classes folder in the root folder.(THis is to separate the classes(Compiled unit) with source files.If we want to re-compile then we could just delete the classes files.) 

step 1 : Compile the common package, This package consist of the common code that is used by both the client and the server.
  
    javac -d classes src\com\hw1\common\*.java
    
Server side 
 
    javac -cp classes -d classes src\com\hw1\server\*.java 

classes is a directory consisting of the compiled classes.Since the server program depends on the common package.we add the path of compiled classes to the classpath using the -cp option.

Client Side

    javac -cp classes -d classes src\com\hw1\client\*.java

This completes the compilation part of the program.

Start Server
Step 0:Move to the classes folder
	
	To start the server with default values (port:8080, docroot:current working directory(i,e classes folder))
	java com.hw1.server.WebServer 

	TO start the server with different port and docroot directory
	java com.hw1.server.WebServer <PORT_NO> <Path_To_The_folder_Containing files> 
	eg:java com.hw1.server.WebServer 9000 C:\ALI\coursematerials\cn-1\webserver


At this point the webserver can be accesed using the browser using the url <host>:<port>\URI eg:localhost:9000, this serves the index.html file.

Starting client
Client
	If the server is started with default values then 
   
    java com.hw1.client.WebClient
	If the server is started with different values.
		java com.hw1.client.WebClient <Ip_address> <port> <file_name>. If the file name is not given then index file is used.

The files to be returned must be placed in the folder passed as the docroot or the current working directory from which the program is executed.

The list of file format supported are "html","pdf","png","gif","py","docx"
