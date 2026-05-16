# Scope Highlighter

## What is this project about?
The purpose of this project is to create a Burp Suite extension for endpoints and highlight the endpoints that are already in the scope

## Why did I build it?
I built it for practical reasons and to gain more hands-on experience

## Technologies Used
Java, BurpSuite, Montoya Api, Maven

### Project parts
**ScopeHighlighter** - entry point (when Burp loads a JAR file it searches for a class that
implements BurpExtension and calls **initialize**. The whole setup starts and connects here - the ScopeManager 
is created, UI tabs and HTTP handlers are registered here)

**ScopeManager** - the application center (saves the list of endpoints that are received from the client and has 3 methods: 
**addEndpoint** for adding a new endpoint to scope,
**isInScope** is a method for checking if the path is already in scope and **getScopeList** returns the list for the UI)

**HttpHandlerImpl** - (Burp calls this every time an HTTP request passes through the proxy. It takes the request path,
asks **ScopeManager** if the request is in the scope, if the answer is true then the request is highlighted in green 
with the "IN SCOPE" comment)