package burpsuite.extension;

import java.util.ArrayList;
import java.util.List;

public class ScopeManager {
    List<String> scopeList;

    public ScopeManager() {
        this.scopeList = new ArrayList<>();
    }

    public List<String> getScopeList() {
        return scopeList;
    }
    public void addEndpoint(String endpoint){
        scopeList.add(endpoint);
    }
    public boolean isInScope(String path){
        return scopeList.stream().anyMatch(endpoint -> path.startsWith(endpoint));
    }
}
