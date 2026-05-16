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
        String path = httpRequestToBeSent.path();
        montoyaApi.logging().logToOutput("Path: " + httpRequestToBeSent.path());
        if(scopeManager.isInScope(path)) {
            return RequestToBeSentAction.continueWith(httpRequestToBeSent, Annotations.annotations("IN SCOPE", HighlightColor.GREEN));
        }
        return RequestToBeSentAction.continueWith(httpRequestToBeSent);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived httpResponseReceived) {
        return ResponseReceivedAction.continueWith(httpResponseReceived);
    }
}
