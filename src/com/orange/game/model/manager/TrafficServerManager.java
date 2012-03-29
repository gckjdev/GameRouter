package com.orange.game.model.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orange.game.model.dao.TrafficServer;


public class TrafficServerManager extends CommonManager {

	
	Map<String, TrafficServer> serverList = new ConcurrentHashMap<String, TrafficServer>();
	Object dataLock = new Object();
	
    private static TrafficServerManager manager = new TrafficServerManager();    
    
    public long configFileLastModified = 0;
    
    private TrafficServerManager(){	
    	
//    	TrafficServer server1 = new TrafficServer("58.215.188.215", 9000, 1, 10, 1000);
//    	TrafficServer server2 = new TrafficServer("58.215.184.219", 9000, 2, 400, 2000);
//    	TrafficServer server3 = new TrafficServer("106.187.89.232", 9000, 1, 20, 2000);
//
//    	serverList.put(server1.getKey(), server1);
//    	serverList.put(server2.getKey(), server2);
//    	serverList.put(server3.getKey(), server3);
	} 	    
    
    public static TrafficServerManager getInstance() { 
    	return manager; 
    } 

    @SuppressWarnings("unchecked")
	public List<TrafficServer> findAllServers(){
    	List<TrafficServer> retList = new ArrayList<TrafficServer>();
    	synchronized(dataLock){
    		retList.addAll(serverList.values());
    	}
    	Collections.sort(retList);
    	return retList;
    }
    
    public void createOrUpdateTrafficServer(String addr, int port, int language, int usage, int capacity){
    	TrafficServer server = new TrafficServer(addr, port, language, usage, capacity);
    	serverList.put(server.getKey(), server);
    }
    
    public String getConfigFile(){
		String file = System.getProperty("config.file");
		if (file != null && !file.isEmpty()){
			return file;
		}
		return "/root/game/server_list.txt"; // default
    }
    
    public void loadTrafficServerFromFiles(){
    	
    	log.debug("loadTrafficServerFromFiles "+this.getConfigFile());
    	
    	File f = new File(this.getConfigFile());
    	if (f == null || f.exists() == false){
    		log.error("Fail to open file " + this.getConfigFile());
    		return;
    	}
    	
    	long lastModified = f.lastModified();
    	if (configFileLastModified < lastModified){
    		
    		// read data from file    		
    		if (readDataFromFile(f)){
    			configFileLastModified = lastModified;
    		}
    	}    	
    	else{
    		log.debug("loadTrafficServerFromFiles but no data change"+this.getConfigFile());
    	}
    }

	private boolean readDataFromFile(File f) {
		
		StringBuffer stringBuffer = new StringBuffer();
		String line = null ;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			if (br == null){
			    log.error("<readDataFromFile> cannot read file");
				return false;
			}
			while( (line = br.readLine())!= null ){
				stringBuffer.append(line);
			} 			
			br.close();
		} catch (FileNotFoundException e) {
		    log.error("<readDataFromFile> exception = "+e.toString(), e);
		    return false;
		} catch (IOException e) {
		    log.error("<readDataFromFile> exception = "+e.toString(), e);
		    return false;
		} finally{
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
				    log.error("<readDataFromFile> exception = "+e.toString(), e);
				}
			}
		}
		
		JSONObject obj = JSONObject.fromObject(stringBuffer.toString());
		if (obj == null){
			log.error("readDataFromFile but fail to create JSON from string");
			return false;
		}
		
		JSONArray serverArray = obj.getJSONArray("servers");
		if (serverArray == null){
			log.error("readDataFromFile but fail to get server JSON array");
			return false;
		}
		
		synchronized (dataLock){
			int size = serverArray.size();
			long lastModified = System.currentTimeMillis();
			for (int i=0; i<size; i++){
				JSONObject server = serverArray.getJSONObject(i);
				
				String key = TrafficServer.createKey(server.getString("server_address"), server.getInt("server_port"));			
				TrafficServer trafficServer = serverList.get(key);
				if (trafficServer == null){
					trafficServer = new TrafficServer(server.getString("server_address"), 
							server.getInt("server_port"), server.getInt("language"), 
							0, server.getInt("capacity"));
					serverList.put(key, trafficServer);
					trafficServer.setLastModified(lastModified);
					
					log.info("Add Server " + trafficServer.toString() + " in list");
				}
				else{
					trafficServer.setLastModified(lastModified);	
					trafficServer.setCapacity(server.getInt("capacity"));
					trafficServer.setLanguage(server.getInt("language"));
					log.info("Update Server " + trafficServer.toString() + " in list");
				}
			}
			
			// if there are some servers which are not modified, remove them
			List<String> removeList = new ArrayList<String>();
			for (TrafficServer server : serverList.values()){
				if (server.getLastModified() != lastModified){
					removeList.add(server.getKey());
				}				
			}
	
			for (String key : removeList){
				
				log.info("Remove Server " + serverList.get(key).toString() + " from list");
				serverList.remove(key);
			}
		}
		
		return true;
	}
    
    
}
