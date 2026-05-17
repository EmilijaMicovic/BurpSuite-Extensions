package burpsuite.extension;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class ScopeHighlighter implements BurpExtension{
    @Override
    public void initialize(MontoyaApi montoyaApi) {
        montoyaApi.extension().setName("Scope Highlighte");
        ScopeManager scopeManager = new ScopeManager(montoyaApi);
        ScopeTab  scopeTab = new ScopeTab(scopeManager);
        HttpHandlerImpl httpHandlerImpl = new HttpHandlerImpl(montoyaApi, scopeManager);

        montoyaApi.userInterface().registerSuiteTab("Scope",scopeTab);
        montoyaApi.http().registerHttpHandler(httpHandlerImpl);
    }
}

