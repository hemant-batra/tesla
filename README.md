<html>
<body>
<table style="font-family:calibri;font-size 11pt;">
<tr><td><img src="https://github.com/hemant-batra/tesla/blob/master/src/tesla/images/tesla-server.png"/></td><td>
<span style="font-size:16pt;font-weight:bold;color:#aa0000"><b>Tesla</b> - Endpoint & Persistence framework</span><br>
Built on Java<sup style="font-size:6pt;">TM</sup> HTTP Server & Serialization<br>
</td></tr>
</table>
<hr>
<p style="font-family:calibri;font-size:11pt;">
<span style="color:0000cc;font-weight:bold;"><b>Defining the Tesla Server</b></span>
</p>
<div style="font-family:calibri;font-size:11pt;">
<ol type="1">
<li>Create an empty Java<sup style="font-size:6pt;">TM</sup> project and add <font color="#880000"><i><b>tesla-1.4.jar</b></i></font> to its build path. This project now acts as a Tesla Web project.</li>
<li>Create three packages inside this project for keeping the deployment resources. Place your project images inside the image package, web pages inside the pages package and services java files inside the services package.</li><br>
<table border="1" cellpadding="5" style="border:1px solid grey;border-collapse:collapse;">
<tr align="center"><th>Sample package name</th><th>Purpose</th><th>Files to keep</th></tr>
<tr align="center"><td><i>myProject.myWebPages</i></td><td>Keep web pages here</td><td>.htm .html</td></tr>
<tr align="center"><td><i>myProject.myImages</i></td><td>Keep image files here</td><td>.bmp .png .jpg .jpeg .gif</td></tr>
<tr align="center"><td><i>myProject.myServices</i></td><td>Keep service files here</td><td>All files that extend the <i>tesla.core.Service&lt;ReturnType&gt;</i> class</td></tr>
</table><br>
<li>All your service files must extend the <i>tesla.core.Service&lt;ReturnType&gt;</i> class and override the <i>execute</i> and <i>isPasswordProtected</i> methods. The isPasswordProtected method does not accept any parameter and returns a boolean value indicating if password validation is required for this service or not. The execute method accepts the <i>Request</i> object and returns the output as <i>ReturnType</i> data type. 
The purpose of services is to return ReturnType data to the invoking client after accepting and processing text data from it. The Request object contains:
 	<ol type="a">
	<li>The <i>tesla.store.ServerBean</i> object</li>
 	<li>The IP Address of the invoking client.</li>
 	<li>The data that is posted in the body of the request.</li>
 	<li>The parameters that are passed as a part of the request URL.</li>
	<li>A method to forward the request to another service</li>
	</ol>
</li><br>
<li>After you have created all the deployment resources in the deployment packages, you need to define a server and start it. Create a fourth package and create two class files inside it. The first class should extend the class 
<i>tesla.store.ServerBean</i> interface and implement all the methods it has to offer. These methods define the various parameters required to start the server:
 	<ol type="a">
 	<li><font color="#880000"><i><b>public int getPort()</b></i></font>: Returns the port number on which server has to start.</li>
 	<li><font color="#880000"><i><b>public String getPassword()</b></i></font>: Returns the password that will enable access to the internal services <i>/Resources</i> and <i>/Shutdown</i>. Null password means these services are not password protected.</li>
 	<li><font color="#880000"><i><b>public boolean isPasswordProtected()</b></i></font>: Returns true if the service is password protected, returns false otherwise.</li>
 	<li><font color="#880000"><i><b>public String getApplicationName()</b></i></font>: Returns the user assigned name of the application. If it returns null or an empty String, the deployment context name is assumed to be the application name.</li>
 	<li><font color="#880000"><i><b>public String getStartupServiceName()</b></i></font>: Returns the name of service that will be invoked when the server root context URL is opened in browser. In case this method returns a null,
 	the default Tesla Server startup page opens up when server URL is opened in web browser.</li>
 	<li><font color="#880000"><i><b>public String getPagesPackageName()</b></i></font>: Returns the full name of the Web Pages package. No pages are deployed when the server starts if this method returns null or an empty String or 
 	if a package with this name does not exist or if the package exists but does not contain any files.</li>
 	<li><font color="#880000"><i><b>public String getImagesPackageName()</b></i></font>: Returns the full name of the Images package. No images are deployed when the server starts if this method returns null or an empty String or 
 	if a package with this name does not exist or if the package exists but does not contain any files.</li>
 	<li><font color="#880000"><i><b>public String getServicesPackageName()</b></i></font>: Returns the full name of the Services package. No services are deployed when the server starts if this method returns null or an empty String or 
 	if a package with this name does not exist or if the package exists but does not contain any files.</li>
	<li><font color="#880000"><i><b>public String getConsoleOutputFileName()</b></i></font>: Returns the name of the file to which <i>System.out</i> and <i>System.err</i> will be written to. Default PrintStream is not replaced if this method returns null.</li>
	</ol>
	The deployment context name of the server is taken to be same as the name of the ServerBean class.
</li>
<li>The second class file, called the Launcher file contains the logic for starting and stopping the server. It is described in the next section.</li>
</ol>
</div>
<hr>
<p style="font-family:calibri;font-size:11pt;">
<span style="color:0000cc;font-weight:bold;"><b>Working with Servers</b></span>
</p>
<div style="font-family:calibri;font-size:11pt;">
The various operations of the server can be performed using the static methods defined in the <i>tesla.core.ServerManager</i> class. You can start servers on multiple ports in the same java project by creating multiple classes that 
extend the <i>tesla.store.ServerBean</i> interface. Each ServerBean object starts a new server when it is passed to the <i>start()</i> method of the ServerManager class. The same server can be stopped by calling the <i>stop()</i> method
of the ServerManager class and passing the port number to it. The various operations available in ServerManager are:  
<ol type="1">
<li><font color="#880000"><i><b>public static void start(ServerBean serverBean) throws TeslaServerInitializationException, TeslaIOException</b></i></font>: Call this method to start the server with the attributes of the ServerBean object that is passed to it.</li>
<li><font color="#880000"><i><b>public static boolean isRunning(int port)</b></i></font>: Returns a boolean to indicate whether a server is running on the specified port or not.</li>
<li><font color="#880000"><i><b>public static ServerBean getServerBean(int port)</b></i></font>: Returns the ServerBean object associated with a port on which server is running. Modifying this ServerBean object does not modify the attributes of the running server.</li>
<li><font color="#880000"><i><b>public static int[] getActivePorts()</b></i></font>: Returns a list in integer array format of all ports on which Tesla Server is running from the current project.</li>
<li><font color="#880000"><i><b>public static boolean shutdown(int port)</b></i></font>: Shuts down a server that is associated with the current project and started on the specified port. Return <i>true</i> if the shutdown is successful, returns <i>false</i> if no associated server is running on the specified port</li>
<li><font color="#880000"><i><b>public static String getPageContent(int port, String pageName)</b></i></font>: Returns the complete HTML content of the web page which is associated with a server running on the specified port and having a name same as the specified web page name. The web page must be in the pages package of the current project.</li>
<li><font color="#880000"><i><b>public static String getPageContent(int port, String pageName, Map&lt;String,String&gt; parameters)</b></i></font>: Returns the complete HTML content of the web page which is associated with a server running on the specified port and having a name same as the specified web page name after replacing the place holder values specified in the input parameter map. The web page must be in the pages package of the current project.</li>
</ol>
</div>
<hr>
<p style="font-family:calibri;font-size:11pt;">
<span style="color:0000cc;font-weight:bold;"><b>About Tesla Internal Services</b></span>
</p>
<div style="font-family:calibri;font-size:11pt;">
There are four internal Tesla services that are loaded every time a server is started. These services cannot be changed.
<ol type="1">
<li><font color="#880000"><i><b>/Resources</b></i></font>: Returns a webpage that contains lists of all deployed resources. Once the page is loaded, you can access these resources by clicking on their names. This service is password protected. You need to provide the password specified in the ServerBean object to access it. A hyper link for this service is available on the Tesla default web page.</li>
<li><font color="#880000"><i><b>/Documentation</b></i></font>: Returns the webpage that you are currently reading. It is not password protected. A hyper link for this service is available on the Tesla default web page.</li>
<li><font color="#880000"><i><b>/Shutdown</b></i></font>: When invoked, this service shut downs the server. It is password protected.  A hyper link for this service is available on the Tesla default web page.</li>
<li><font color="#880000"><i><b>/Logo</b></i></font>: It returns the Tesla Web Server logo that is displayed on top of this page.</li>
</ol>
</div>
<hr>
<p style="font-family:calibri;font-size:11pt;">
<span style="color:0000cc;font-weight:bold;"><b>Tesla Persistence Framework</b></span>
</p>
<div style="font-family:calibri;font-size:11pt;">
Tesla Web Server has built in persistence framework that provides a database like persistent interface to the applications. Your persistence class must extend <i>tesla.persistence.Persistent</i>.  
The following code illustrates the procedure for reading from a persistent object.
<table><tr><td width="40px"></td><td>
<code><br>
MyPersistentObject myPersistentObject = (MyPersistentObject) Persistence.read(MyPersistentObject.class);
<br><br>
</code>
</td></tr></table>
Any data can be extracted from the variable <i>myPersistentObject</i>. Any modifications done to the <i>myPersistentObject</i> object will not be persisted. Following code describes how to make changes to the myPersistentObject so that
those changes can be persisted.
<table><tr><td width="40px"></td><td>
<code><br>
MyPersistentObject myPersistentObject = (MyPersistentObject) Persistence.acquireLock(MyPersistentObject.class);<br>
/*... make modifications to <i>myPersistentObject</i> ...*/<br>
myPersistentObject.releaseLock();<br>
<br>
</code>
</td></tr></table>
The method <i>Persistence.acquireLock(MyPersistentObject.class)</i> obtains a lock on persistent object MyPersistentObject so that no other process is able to modify it till the lock is released. The code
<i>myPersistentObject.releaseLock();</i> releases the lock on MyPersistentObject after which other processes can obtain a lock on MyPersistentObject.
</div>
<hr>
<p style="font-family:calibri;font-size:11pt;">
<span style="color:0000cc;font-weight:bold;"><b>Using Persistence with Services</b></span>
</p>
<div style="font-family:calibri;font-size:11pt;">
Tesla Web Server provides a mechanism to use the functionalities provided in the persistence framework in the services that it exposes. Two extra service classes called <i>ReadService</i> and <i>WriteService</i> are exposed.
<br><br><font color="#880000"><i><b>ReadService</b></i></font>: This facilitates a user to read a persistent object in a service without bothering about the implementation details.
<table><tr><td width="40px"></td><td>
<code><br>
public class SampleReadService extends ReadService&lt;<b>ReturnType</b>,<b>PersistentType</b>&gt;
<br><br>
</code>
</td></tr></table>
This declares a service class that returns an object of type <i>ReturnType</i>. The return type object has to implement <i>java.io.Serializable</i>. It also declares that the service class makes an object of type PersistentType available for read only purposes
in the <i>execute</i> method. The persistent object has to extend the <i>persistent.Persistent</i> class. The execute method is overloaded to add an extra parameter of <i>PersistentType</i>
<table><tr><td width="40px"></td><td>
<code><br>
@Override<br>
protected <b>ReturnType</b> execute(Request request, <b>PersistentType</b> persistentType) throws TeslaException
<br><br>
</code>
</td></tr></table>
<br><font color="#880000"><i><b>WriteService</b></i></font>: Its usage is same as <i>ReadService</i>. However, it locks the persistent object before passing it to the execute method. User can read and/or modify the object before releasing it back to <i>WriteService</i>. WriteService then
releases the lock and save the object. It then returns on object of <i>ReturnType</i> to the invoking client.
<table><tr><td width="40px"></td><td>
<code><br>
public class SampleWriteService extends WriteService&lt;<b>ReturnType</b>,<b>PersistentType</b>&gt;<br>
/* more code goes here */<br>
@Override<br>
protected <b>ReturnType</b> execute(Request request, <b>PersistentType</b> persistentType) throws TeslaException
<br><br>
</code>
</td></tr></table>
Please note that Tesla Persistence Framework and Tesla Service Framework are two completely unrelated APIs provided by the Tesla Web Server. These two frameworks do not interfere in each others functioning and can be used together or independently depending upon the requirement of the developer.  
</div>
<hr>
<div style="font-family:calibri;font-size:11pt;" align="center"><i>hemant.batra@theinnovationinc.co</i></div>
</body>
</html>
