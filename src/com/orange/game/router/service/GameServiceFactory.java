package com.orange.game.router.service;


import com.orange.common.api.service.CommonService;
import com.orange.common.api.service.CommonServiceFactory;
import com.orange.game.constants.ServiceConstant;

public class GameServiceFactory extends CommonServiceFactory {

	@Override
	public CommonService createServiceObjectByMethod(String method) {
		if (method == null){
			return null;
		}
		if (method.equalsIgnoreCase(ServiceConstant.METHOD_GET_TRAFFIC_SERVER_LIST)){
			return new GetTrafficServerService();
		}
		else if (method.equalsIgnoreCase(ServiceConstant.METHOD_UPDATE_TRAFFIC_SERVER_STATUS)){
			return new UpdateTrafficServerStatusService();
		}
		else{
			return null;
		}
	}

}
