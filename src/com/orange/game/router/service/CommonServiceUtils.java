package com.orange.game.router.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.orange.game.constants.ServiceConstant;
import com.orange.game.model.dao.TrafficServer;

public class CommonServiceUtils {

//	public static JSONObject userToJSON(DBObject user) {
//		JSONObject obj = new JSONObject();
//		obj.put(ServiceConstant.PARA_USERID, user.get(DBConstants.F_ID)
//				.toString());
//		return obj;
//	}
	
	
	
	
	public static JSONArray serverListToJSON(List<TrafficServer> serverList) {
		if (serverList == null){
			return null;
		}
		
		JSONArray objList = new JSONArray();
		for (TrafficServer server : serverList) {
			JSONObject object = new JSONObject();
			safePut(object, ServiceConstant.PARA_SERVER_ADDRESS, server.getServerAddress());
			safePut(object, ServiceConstant.PARA_SERVER_PORT, server.getServerPort());
			safePut(object, ServiceConstant.PARA_SERVER_LANGUAGE, server.getLanguage());
			safePut(object, ServiceConstant.PARA_SERVER_USAGE, server.getUsage());
			safePut(object, ServiceConstant.PARA_SERVER_CAPACITY, server.getCapacity());
			objList.add(object);
		}
		return objList;
	}

	private static void safePut(JSONObject object, String key, Object value) {
		if (value == null)
			return;

		object.put(key, value);
	}

	
	
}
