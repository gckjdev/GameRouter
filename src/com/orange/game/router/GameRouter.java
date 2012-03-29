package com.orange.game.router;

import com.orange.common.api.CommonApiServer;
import com.orange.common.api.service.ServiceHandler;
import com.orange.common.mongodb.MongoDBClient;
import com.orange.game.constants.DBConstants;
import com.orange.game.router.service.GameServiceFactory;

public class GameRouter extends CommonApiServer {
	
	public static final String VERSION_STRING = "Group Buy API Server Version 0.1";
	public static final String SPRING_CONTEXT_FILE = "classpath:/com/orange/game/router/applicationContext.xml";	
	public static final String LOG4J_FLE = "classpath:/log4j.properties";
//	public static final String MONGO_SERVER = "localhost";
//	public static final String MONGO_DB_NAME = "groupbuy";
//	public static final String MONGO_USER = "";
//	public static final String MONGO_PASSWORD = "";
	public static final GameServiceFactory serviceFactory = new GameServiceFactory();
	
	private static final MongoDBClient mongoClient = new MongoDBClient(DBConstants.D_GAME);	

	@Override
	public String getAppNameVersion() {
		return VERSION_STRING;
	}

	@Override
	public String getLog4jFile() {
		return LOG4J_FLE;
	}

	@Override
	public ServiceHandler getServiceHandler() {
		return ServiceHandler.getServiceHandler(mongoClient, serviceFactory);
	}

	@Override
	public String getSpringContextFile() {
		return SPRING_CONTEXT_FILE;
	}
	
	@Override
	public int getPort() {
		String port = System.getProperty("server.port");
		if (port != null && !port.isEmpty()){
			return Integer.parseInt(port);
		}
		return 8600;
	}
	
    public static void main(String[] args) throws Exception{
    	GameRouter server = new GameRouter();
		server.startServer();
    }


}
