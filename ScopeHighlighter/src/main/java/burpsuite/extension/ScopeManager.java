package burpsuite.extension;

import burp.api.montoya.MontoyaApi;

import java.util.ArrayList;
import java.util.List;

public class ScopeManager {
    List<String> scopeList;
    List<String> sentToRepeater = new ArrayList<>();
    MontoyaApi montoyaApi;

    public ScopeManager(MontoyaApi montoyaApi) {
        this.scopeList = new ArrayList<>();
        this.montoyaApi = montoyaApi;
        String saved = montoyaApi.persistence().preferences().getString("scope");
        if(saved != null && !saved.isEmpty()){
            for(String s : saved.split("\n")){
                scopeList.add(s);
            }
        }
    }

    public List<String> getScopeList() {
        return scopeList;
    }
    public void addEndpoint(String endpoint){
        if(!scopeList.contains(endpoint)) {
            scopeList.add(endpoint);
            montoyaApi.persistence().preferences().setString("scope", String.join("\n", scopeList));
        }

    }
    public boolean isInScope(String path,String method){
        return scopeList.stream().anyMatch(endpoint -> {
            String[] parts = endpoint.split(" ",2);
            if(parts.length<2) return false;
            String scopeMetho = parts[0];
            String scopePath = parts[1];
            return path.startsWith(scopePath) && method.equals(scopeMetho);
        });
    }
    public boolean shouldSendToRepeater(String path){
        if(!sentToRepeater.contains(path)){
            sentToRepeater.add(path);
            return true;
        }
        return false;
    }
    public void removeEndpoint(String endpoint) {
        scopeList.remove(endpoint);
        montoyaApi.persistence().preferences().setString("scope", String.join("\n", scopeList));
    }

    public void clearAll() {
        scopeList.clear();
        montoyaApi.persistence().preferences().setString("scope", "");
    }
}
