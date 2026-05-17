# Scope Highlighter

## What is this project about?
The purpose of this project is to create a Burp Suite extension for endpoints and highlight the endpoints that are already in the scope

## Why did I build it?
I built it for practical reasons and to gain more hands-on experience

## Technologies Used
Java, BurpSuite, Montoya Api, Maven

### Project Structure
**ScopeHighlighter** - entry point (when Burp loads a JAR file it searches for a class that
implements BurpExtension and calls **initialize**. The whole setup starts and connects here - the ScopeManager 
is created, UI tabs and HTTP handlers are registered here)

**ScopeManager** - the application center (saves the list of endpoints that are received from the client and has 3 methods: 
**addEndpoint** for adding a new endpoint to scope,
**isInScope** is a method for checking if the path is already in scope and **getScopeList** returns the list for the UI)

**HttpHandlerImpl** - (Burp calls this every time an HTTP request passes through the proxy. It takes the request path,
asks **ScopeManager** if the request is in the scope, if the answer is true then the request is highlighted in green 
with the "IN SCOPE" comment)

**ScopeTab** - UI component (extends JPanel which is a Swing component that Burp accepts as a tab.
Contains a text area where you paste endpoints one per line, and a Load Scope button that reads
the text, splits it by lines and adds each endpoint to ScopeManager)

## How it works - Testing
Extension was tested on TryHackMe. After loading endpoints into the Scope tab,
all new requests matching the scope were highlighted green with "IN SCOPE" comment
in Proxy → HTTP history. Prefix matching was used so /api/ matches all /api/v2/... endpoints.

## New Features Added
**Repeater Integration** - When a new in-scope endpoint is detected for the first time,
it is automatically sent to a Repeater tab named after the endpoint and highlighted red.
Repeated endpoints stay green.

**Scope Persistence** - Scope list is now saved between Burp sessions using Montoya persistence API.

**Scope List Management** - Added a dialog to view, remove selected, or clear all endpoints from scope.

**HTTP Method Support** - Scope entries now include HTTP method (e.g. GET /api/v1/user,
POST /api/v1/login). Extension matches both method and path when checking if a request is in scope.

**Duplicate Prevention** - Added check in addEndpoint to prevent adding the same endpoint twice.