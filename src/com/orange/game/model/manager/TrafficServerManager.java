package com.orange.game.model.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.orange.game.model.dao.TrafficServer;


public class TrafficServerManager {

	
	Map<String, TrafficServer> serverList = new ConcurrentHashMap<String, TrafficServer>();
	
    private static TrafficServerManager manager = new TrafficServerManager();     
    
    private TrafficServerManager(){	
    	
    	TrafficServer server1 = new TrafficServer("58.215.188.215", 9000, 1, 0, 1000);
    	TrafficServer server2 = new TrafficServer("58.215.184.219", 9000, 2, 400, 2000);

    	serverList.put(server1.getKey(), server1);
    	serverList.put(server2.getKey(), server2);
	} 	    
    
    public static TrafficServerManager getInstance() { 
    	return manager; 
    } 

    public List<TrafficServer> findAllServers(){
    	List<TrafficServer> retList = new ArrayList<TrafficServer>();
    	synchronized(serverList){
    		retList.addAll(serverList.values());
    	}
    	return retList;
    }
    
    public void createOrUpdateTrafficServer(String addr, int port, int language, int usage, int capacity){
    	TrafficServer server = new TrafficServer(addr, port, language, usage, capacity);
    	serverList.put(server.getKey(), server);
    }
    
    
}
