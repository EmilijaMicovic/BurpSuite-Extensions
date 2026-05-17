package burpsuite.extension;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.http.handler.*;

public class HttpHandlerImpl implements HttpHandler {
    private MontoyaApi montoyaApi;
    private ScopeManager scopeManager;

    public HttpHandlerImpl(MontoyaApi montoyaApi, ScopeManager scopeManager) {
        this.montoyaApi = montoyaApi;
        this.scopeManager = scopeManager;
    }

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent httpRequestToBeSent) {
        String path = httpRequestToBeSent.path().split("\\?")[0];
        String method = httpRequestToBeSent.method();
        String[] parts = path.split("/");
        String tabName = "/" + (parts.length > 1 ? parts[1] : "") +
                (parts.length > 2 ? "/" + parts[2] : "");
        montoyaApi.logging().logToOutput("Path: " + httpRequestToBeSent.path());
        if(scopeManager.isInScope(path, method)) {
            if(scopeManager.shouldSendToRepeater(path)){
                montoyaApi.repeater().sendToRepeater(httpRequestToBeSent, tabName);
                return RequestToBeSentAction.continueWith(httpRequestToBeSent, Annotations.annotations("IN SCOPE - NEW", HighlightColor.RED));
            }
            return RequestToBeSentAction.continueWith(httpRequestToBeSent, Annotations.annotations("IN SCOPE", HighlightColor.GREEN));
        }
        return RequestToBeSentAction.continueWith(httpRequestToBeSent);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived httpResponseReceived) {
        return ResponseReceivedAction.continueWith(httpResponseReceived);
    }
}
